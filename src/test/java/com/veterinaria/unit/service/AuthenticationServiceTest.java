package com.veterinaria.unit.service;

import com.veterinaria.application.dto.auth.LoginRequest;
import com.veterinaria.application.dto.auth.LoginResponse;
import com.veterinaria.application.dto.auth.RefreshTokenRequest;
import com.veterinaria.application.dto.auth.RegisterRequest;
import com.veterinaria.application.repository.AuditoriaAccesoRepository;
import com.veterinaria.application.repository.RolRepository;
import com.veterinaria.application.repository.SesionRepository;
import com.veterinaria.application.repository.UsuarioRepository;
import com.veterinaria.application.service.AuthenticationService;
import com.veterinaria.domain.entity.security.Rol;
import com.veterinaria.domain.entity.security.Sesion;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.enums.EstadoSesion;
import com.veterinaria.domain.enums.TipoUsuario;
import com.veterinaria.infrastructure.exception.DuplicateResourceException;
import com.veterinaria.infrastructure.exception.UnauthorizedException;
import com.veterinaria.infrastructure.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios para AuthenticationService.
 * Prueba todos los flujos de autenticación y manejo de sesiones.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AuthenticationService Tests")
class AuthenticationServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private SesionRepository sesionRepository;

    @Mock
    private AuditoriaAccesoRepository auditoriaAccesoRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private HttpServletRequest request;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthenticationService authenticationService;

    private Usuario usuarioMock;
    private Rol rolMock;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        // Configurar usuario mock
        usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setUsername("testuser");
        usuarioMock.setEmail("test@example.com");
        usuarioMock.setPassword("encodedPassword");
        usuarioMock.setIsActive(true);
        usuarioMock.setCuentaBloqueada(false);
        usuarioMock.setIntentosFallidos(0);
        usuarioMock.setTipoUsuario(TipoUsuario.VETERINARIO);

        // Configurar rol mock
        rolMock = new Rol();
        rolMock.setId(1L);
        rolMock.setNombre("ROLE_VETERINARIO");

        Set<Rol> roles = new HashSet<>();
        roles.add(rolMock);
        usuarioMock.setRoles(roles);

        // Configurar login request
        loginRequest = LoginRequest.builder()
                .username("testuser")
                .password("password123")
                .build();
    }

    @Test
    @DisplayName("Login exitoso debe retornar token y datos del usuario")
    void loginExitoso_DebeRetornarTokenYDatos() {
        // Arrange
        when(request.getHeader("X-Forwarded-For")).thenReturn(null);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        when(usuarioRepository.findByUsername(loginRequest.getUsername()))
                .thenReturn(Optional.of(usuarioMock));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtTokenProvider.generateToken(any(Authentication.class)))
                .thenReturn("jwt-token");
        when(jwtTokenProvider.generateRefreshToken(anyString()))
                .thenReturn("refresh-token");
        when(sesionRepository.save(any(Sesion.class)))
                .thenReturn(new Sesion());
        when(auditoriaAccesoRepository.save(any()))
                .thenReturn(null);

        // Act
        LoginResponse response = authenticationService.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("refresh-token", response.getRefreshToken());
        assertEquals("Bearer", response.getTokenType());
        assertEquals(1L, response.getUserId());
        assertEquals("testuser", response.getUsername());
        assertNotNull(response.getRoles());

        // Verificar que se resetearon los intentos fallidos
        verify(usuarioRepository).save(argThat(usuario ->
            usuario.getIntentosFallidos() == 0
        ));

        // Verificar que se creó la sesión
        verify(sesionRepository).save(any(Sesion.class));

        // Verificar que se registró en auditoría
        verify(auditoriaAccesoRepository).save(any());
    }

    @Test
    @DisplayName("Login con credenciales incorrectas debe lanzar excepción")
    void loginConCredencialesIncorrectas_DebeLanzarExcepcion() {
        // Arrange
        when(request.getHeader("X-Forwarded-For")).thenReturn(null);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        when(usuarioRepository.findByUsername(loginRequest.getUsername()))
                .thenReturn(Optional.of(usuarioMock));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Credenciales inválidas"));
        when(auditoriaAccesoRepository.save(any()))
                .thenReturn(null);

        // Act & Assert
        assertThrows(UnauthorizedException.class, () ->
            authenticationService.login(loginRequest)
        );

        // Verificar que se incrementaron los intentos fallidos
        verify(usuarioRepository).save(argThat(usuario ->
            usuario.getIntentosFallidos() == 1
        ));

        // Verificar que se registró el intento fallido en auditoría
        verify(auditoriaAccesoRepository).save(any());
    }

    @Test
    @DisplayName("Login con usuario bloqueado debe lanzar excepción")
    void loginConUsuarioBloqueado_DebeLanzarExcepcion() {
        // Arrange
        when(request.getHeader("X-Forwarded-For")).thenReturn(null);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        usuarioMock.setCuentaBloqueada(true);

        when(usuarioRepository.findByUsername(loginRequest.getUsername()))
                .thenReturn(Optional.of(usuarioMock));
        when(auditoriaAccesoRepository.save(any()))
                .thenReturn(null);

        // Act & Assert
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () ->
            authenticationService.login(loginRequest)
        );

        assertTrue(exception.getMessage().contains("bloqueada"));

        // Verificar que NO se intentó autenticar
        verify(authenticationManager, never()).authenticate(any());

        // Verificar que se registró en auditoría
        verify(auditoriaAccesoRepository).save(any());
    }

    @Test
    @DisplayName("Login con usuario inactivo debe lanzar excepción")
    void loginConUsuarioInactivo_DebeLanzarExcepcion() {
        // Arrange
        when(request.getHeader("X-Forwarded-For")).thenReturn(null);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        usuarioMock.setIsActive(false);

        when(usuarioRepository.findByUsername(loginRequest.getUsername()))
                .thenReturn(Optional.of(usuarioMock));
        when(auditoriaAccesoRepository.save(any()))
                .thenReturn(null);

        // Act & Assert
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () ->
            authenticationService.login(loginRequest)
        );

        assertTrue(exception.getMessage().contains("inactiva"));

        // Verificar que NO se intentó autenticar
        verify(authenticationManager, never()).authenticate(any());
    }

    @Test
    @DisplayName("Bloqueo automático después de 5 intentos fallidos")
    void bloqueoAutomaticoDespuesDe5IntentosFallidos() {
        // Arrange
        when(request.getHeader("X-Forwarded-For")).thenReturn(null);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        usuarioMock.setIntentosFallidos(4); // Ya tiene 4 intentos previos

        when(usuarioRepository.findByUsername(loginRequest.getUsername()))
                .thenReturn(Optional.of(usuarioMock));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Credenciales inválidas"));
        when(auditoriaAccesoRepository.save(any()))
                .thenReturn(null);

        // Act
        assertThrows(UnauthorizedException.class, () ->
            authenticationService.login(loginRequest)
        );

        // Assert - Verificar que se bloqueó la cuenta
        verify(usuarioRepository).save(argThat(usuario ->
            usuario.getIntentosFallidos() == 5 &&
            usuario.getCuentaBloqueada()
        ));
    }

    @Test
    @DisplayName("Registro de usuario exitoso")
    void registroDeUsuarioExitoso() {
        // Arrange
        RegisterRequest registerRequest = RegisterRequest.builder()
                .username("newuser")
                .email("newuser@example.com")
                .password("Password123!")
                .nombre("Test")
                .apellido("User")
                .telefono("1234567890")
                .tipoUsuario(TipoUsuario.VETERINARIO)
                .build();

        when(usuarioRepository.existsByUsername(registerRequest.getUsername()))
                .thenReturn(false);
        when(usuarioRepository.existsByEmail(registerRequest.getEmail()))
                .thenReturn(false);
        when(passwordEncoder.encode(registerRequest.getPassword()))
                .thenReturn("encodedPassword");
        when(rolRepository.findByNombre("ROLE_VETERINARIO"))
                .thenReturn(Optional.of(rolMock));
        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(usuarioMock);

        // Act
        Usuario resultado = authenticationService.register(registerRequest);

        // Assert
        assertNotNull(resultado);
        verify(usuarioRepository).save(any(Usuario.class));
        verify(passwordEncoder).encode(registerRequest.getPassword());
    }

    @Test
    @DisplayName("Registro con username duplicado debe lanzar excepción")
    void registroConUsernameDuplicado_DebeLanzarExcepcion() {
        // Arrange
        RegisterRequest registerRequest = RegisterRequest.builder()
                .username("testuser") // Username ya existe
                .email("newuser@example.com")
                .password("Password123!")
                .nombre("Test")
                .apellido("User")
                .telefono("1234567890")
                .tipoUsuario(TipoUsuario.VETERINARIO)
                .build();

        when(usuarioRepository.existsByUsername(registerRequest.getUsername()))
                .thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateResourceException.class, () ->
            authenticationService.register(registerRequest)
        );

        // Verificar que NO se guardó el usuario
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Refresh token exitoso debe generar nuevo token")
    void refreshTokenExitoso_DebeGenerarNuevoToken() {
        // Arrange
        RefreshTokenRequest refreshRequest = new RefreshTokenRequest();
        refreshRequest.setRefreshToken("valid-refresh-token");

        Sesion sesionActiva = new Sesion();
        sesionActiva.setId(1L);
        sesionActiva.setUsuario(usuarioMock);
        sesionActiva.setEstado(EstadoSesion.ACTIVA);
        sesionActiva.setFechaExpiracion(java.time.LocalDateTime.now().plusHours(1));

        when(jwtTokenProvider.validateToken("valid-refresh-token"))
                .thenReturn(true);
        when(sesionRepository.findByRefreshToken("valid-refresh-token"))
                .thenReturn(Optional.of(sesionActiva));
        when(jwtTokenProvider.generateTokenFromUsername(anyString(), anyList()))
                .thenReturn("new-jwt-token");
        when(sesionRepository.save(any(Sesion.class)))
                .thenReturn(sesionActiva);

        // Act
        LoginResponse response = authenticationService.refreshToken(refreshRequest);

        // Assert
        assertNotNull(response);
        assertEquals("new-jwt-token", response.getToken());
        assertEquals("valid-refresh-token", response.getRefreshToken());
        assertEquals(1L, response.getUserId());
    }

    @Test
    @DisplayName("Refresh token inválido debe lanzar excepción")
    void refreshTokenInvalido_DebeLanzarExcepcion() {
        // Arrange
        RefreshTokenRequest refreshRequest = new RefreshTokenRequest();
        refreshRequest.setRefreshToken("invalid-refresh-token");

        when(jwtTokenProvider.validateToken("invalid-refresh-token"))
                .thenReturn(false);

        // Act & Assert
        assertThrows(UnauthorizedException.class, () ->
            authenticationService.refreshToken(refreshRequest)
        );
    }

    @Test
    @DisplayName("Logout debe cerrar sesión activa")
    void logout_DebeCerrarSesionActiva() {
        // Arrange
        when(request.getHeader("X-Forwarded-For")).thenReturn(null);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        String token = "valid-jwt-token";
        Sesion sesionActiva = new Sesion();
        sesionActiva.setId(1L);
        sesionActiva.setToken("valid-jwt-token");
        sesionActiva.setEstado(EstadoSesion.ACTIVA);
        sesionActiva.setUsuario(usuarioMock);

        when(sesionRepository.findByToken("valid-jwt-token"))
                .thenReturn(Optional.of(sesionActiva));
        when(auditoriaAccesoRepository.save(any()))
                .thenReturn(null);

        // Act
        authenticationService.logout(token);

        // Assert
        verify(sesionRepository).save(argThat(sesion ->
            sesion.getEstado() != EstadoSesion.ACTIVA && sesion.getFechaFin() != null
        ));
    }

    @Test
    @DisplayName("Login exitoso debe resetear intentos fallidos")
    void loginExitosoDebeResetearIntentosFallidos() {
        // Arrange
        when(request.getHeader("X-Forwarded-For")).thenReturn(null);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        usuarioMock.setIntentosFallidos(3); // Tenía intentos fallidos previos

        when(usuarioRepository.findByUsername(loginRequest.getUsername()))
                .thenReturn(Optional.of(usuarioMock));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtTokenProvider.generateToken(any(Authentication.class)))
                .thenReturn("jwt-token");
        when(jwtTokenProvider.generateRefreshToken(anyString()))
                .thenReturn("refresh-token");
        when(sesionRepository.save(any(Sesion.class)))
                .thenReturn(new Sesion());
        when(auditoriaAccesoRepository.save(any()))
                .thenReturn(null);

        // Act
        LoginResponse response = authenticationService.login(loginRequest);

        // Assert
        assertNotNull(response);

        // Verificar que se resetearon los intentos fallidos
        verify(usuarioRepository).save(argThat(usuario ->
            usuario.getIntentosFallidos() == 0
        ));
    }
}
