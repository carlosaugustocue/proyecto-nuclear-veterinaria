package com.veterinaria.unit.service;

import com.veterinaria.application.dto.usuario.ChangePasswordRequest;
import com.veterinaria.application.dto.usuario.CreateUsuarioRequest;
import com.veterinaria.application.dto.usuario.UpdateUsuarioRequest;
import com.veterinaria.application.dto.usuario.UsuarioDTO;
import com.veterinaria.application.repository.RolRepository;
import com.veterinaria.application.repository.UsuarioRepository;
import com.veterinaria.application.service.SesionService;
import com.veterinaria.application.service.UsuarioService;
import com.veterinaria.domain.entity.security.Rol;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.enums.TipoUsuario;
import com.veterinaria.infrastructure.exception.BusinessException;
import com.veterinaria.infrastructure.exception.DuplicateResourceException;
import com.veterinaria.infrastructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios para UsuarioService.
 * Prueba todas las operaciones CRUD y gestión de usuarios.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UsuarioService Tests")
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SesionService sesionService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioMock;
    private Rol rolVeterinario;
    private Rol rolAdmin;

    @BeforeEach
    void setUp() {
        // Configurar usuario mock
        usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setUsername("testuser");
        usuarioMock.setEmail("test@example.com");
        usuarioMock.setPassword("encodedPassword");
        usuarioMock.setNombre("Test");
        usuarioMock.setApellido("User");
        usuarioMock.setTelefono("1234567890");
        usuarioMock.setIsActive(true);
        usuarioMock.setTipoUsuario(TipoUsuario.VETERINARIO);

        // Configurar roles
        rolVeterinario = new Rol();
        rolVeterinario.setId(1L);
        rolVeterinario.setNombre("ROLE_VETERINARIO");

        rolAdmin = new Rol();
        rolAdmin.setId(2L);
        rolAdmin.setNombre("ROLE_ADMIN");

        Set<Rol> roles = new HashSet<>();
        roles.add(rolVeterinario);
        usuarioMock.setRoles(roles);

        // Configurar SecurityContext
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @DisplayName("Crear usuario exitosamente")
    void crearUsuario_DebeCrearUsuarioExitosamente() {
        // Arrange
        CreateUsuarioRequest request = CreateUsuarioRequest.builder()
                .username("newuser")
                .email("newuser@example.com")
                .password("Password123!")
                .nombre("New")
                .apellido("User")
                .telefono("9876543210")
                .tipoUsuario(TipoUsuario.VETERINARIO)
                .build();

        when(usuarioRepository.existsByUsername(request.getUsername()))
                .thenReturn(false);
        when(usuarioRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);
        when(passwordEncoder.encode(request.getPassword()))
                .thenReturn("encodedNewPassword");
        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(usuarioMock);

        // Act
        UsuarioDTO resultado = usuarioService.crear(request);

        // Assert
        assertNotNull(resultado);
        verify(usuarioRepository).save(any(Usuario.class));
        verify(passwordEncoder).encode(request.getPassword());
    }

    @Test
    @DisplayName("Crear usuario con username duplicado debe lanzar excepción")
    void crearUsuarioConUsernameDuplicado_DebeLanzarExcepcion() {
        // Arrange
        CreateUsuarioRequest request = CreateUsuarioRequest.builder()
                .username("testuser") // Username ya existe
                .email("another@example.com")
                .password("Password123!")
                .nombre("Another")
                .apellido("User")
                .telefono("9876543210")
                .tipoUsuario(TipoUsuario.VETERINARIO)
                .build();

        when(usuarioRepository.existsByUsername(request.getUsername()))
                .thenReturn(true);

        // Act & Assert
        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class, () ->
            usuarioService.crear(request)
        );

        assertTrue(exception.getMessage().contains("username"));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Crear usuario con email duplicado debe lanzar excepción")
    void crearUsuarioConEmailDuplicado_DebeLanzarExcepcion() {
        // Arrange
        CreateUsuarioRequest request = CreateUsuarioRequest.builder()
                .username("newuser")
                .email("test@example.com") // Email ya existe
                .password("Password123!")
                .nombre("New")
                .apellido("User")
                .telefono("9876543210")
                .tipoUsuario(TipoUsuario.VETERINARIO)
                .build();

        when(usuarioRepository.existsByUsername(request.getUsername()))
                .thenReturn(false);
        when(usuarioRepository.existsByEmail(request.getEmail()))
                .thenReturn(true);

        // Act & Assert
        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class, () ->
            usuarioService.crear(request)
        );

        assertTrue(exception.getMessage().contains("email"));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Buscar usuario por ID existente debe retornar el usuario")
    void buscarUsuarioPorIdExistente_DebeRetornarUsuario() {
        // Arrange
        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuarioMock));

        // Act
        UsuarioDTO resultado = usuarioService.obtenerPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("testuser", resultado.getUsername());
        assertEquals("test@example.com", resultado.getEmail());
        verify(usuarioRepository).findById(1L);
    }

    @Test
    @DisplayName("Buscar usuario por ID inexistente debe lanzar excepción")
    void buscarUsuarioPorIdInexistente_DebeLanzarExcepcion() {
        // Arrange
        when(usuarioRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () ->
            usuarioService.obtenerPorId(999L)
        );
    }

    @Test
    @DisplayName("Listar todos los usuarios con paginación")
    void listarTodosLosUsuarios_DebeRetornarPaginaDeUsuarios() {
        // Arrange
        List<Usuario> usuarios = Arrays.asList(usuarioMock);
        Page<Usuario> page = new PageImpl<>(usuarios);
        Pageable pageable = PageRequest.of(0, 10);

        when(usuarioRepository.findByIsActiveTrue(pageable))
                .thenReturn(page);

        // Act
        Page<UsuarioDTO> resultado = usuarioService.obtenerTodos(pageable);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals("testuser", resultado.getContent().get(0).getUsername());
        verify(usuarioRepository).findByIsActiveTrue(pageable);
    }

    @Test
    @DisplayName("Actualizar usuario exitosamente")
    void actualizarUsuario_DebeActualizarExitosamente() {
        // Arrange
        UpdateUsuarioRequest request = UpdateUsuarioRequest.builder()
                .nombre("Updated")
                .apellido("Name")
                .telefono("1111111111")
                .build();

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuarioMock));
        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(usuarioMock);

        // Act
        UsuarioDTO resultado = usuarioService.actualizar(1L, request);

        // Assert
        assertNotNull(resultado);
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Eliminar usuario debe marcarlo como inactivo")
    void eliminarUsuario_DebeMarcarloComoInactivo() {
        // Arrange
        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuarioMock));
        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(usuarioMock);
        doNothing().when(sesionService).cerrarTodasLasSesiones(1L);

        // Act
        usuarioService.eliminar(1L);

        // Assert
        verify(usuarioRepository).save(argThat(usuario ->
            !usuario.getIsActive()
        ));
        verify(sesionService).cerrarTodasLasSesiones(1L);
    }

    @Test
    @DisplayName("Cambiar contraseña con contraseña actual correcta")
    void cambiarContrasenaConContrasenaActualCorrecta_DebeCambiarContrasena() {
        // Arrange
        ChangePasswordRequest request = ChangePasswordRequest.builder()
                .currentPassword("oldPassword")
                .newPassword("NewPassword123!")
                .confirmPassword("NewPassword123!")
                .build();

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuarioMock));
        when(passwordEncoder.matches("oldPassword", usuarioMock.getPassword()))
                .thenReturn(true);
        when(passwordEncoder.encode("NewPassword123!"))
                .thenReturn("encodedNewPassword");
        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(usuarioMock);

        // Act
        usuarioService.cambiarPassword(1L, request);

        // Assert
        verify(passwordEncoder).encode("NewPassword123!");
        verify(usuarioRepository).save(argThat(usuario ->
            "encodedNewPassword".equals(usuario.getPassword())
        ));
    }

    @Test
    @DisplayName("Cambiar contraseña con contraseña actual incorrecta debe lanzar excepción")
    void cambiarContrasenaConContrasenaActualIncorrecta_DebeLanzarExcepcion() {
        // Arrange
        ChangePasswordRequest request = ChangePasswordRequest.builder()
                .currentPassword("wrongPassword")
                .newPassword("NewPassword123!")
                .confirmPassword("NewPassword123!")
                .build();

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuarioMock));
        when(passwordEncoder.matches("wrongPassword", usuarioMock.getPassword()))
                .thenReturn(false);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () ->
            usuarioService.cambiarPassword(1L, request)
        );

        assertTrue(exception.getMessage().contains("incorrecta") || exception.getMessage().contains("contraseña"));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Buscar usuarios por tipo debe retornar lista filtrada")
    void buscarUsuariosPorTipo_DebeRetornarListaFiltrada() {
        // Arrange
        List<Usuario> usuarios = Arrays.asList(usuarioMock);
        Page<Usuario> page = new PageImpl<>(usuarios);
        Pageable pageable = PageRequest.of(0, 10);

        when(usuarioRepository.findByTipoUsuario(TipoUsuario.VETERINARIO, pageable))
                .thenReturn(page);

        // Act
        Page<UsuarioDTO> resultado = usuarioService.obtenerPorTipo(TipoUsuario.VETERINARIO, pageable);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        verify(usuarioRepository).findByTipoUsuario(TipoUsuario.VETERINARIO, pageable);
    }

    @Test
    @DisplayName("Desbloquear cuenta debe resetear intentos y quitar bloqueo")
    void desbloquearCuenta_DebeResetearIntentosYQuitarBloqueo() {
        // Arrange
        usuarioMock.setCuentaBloqueada(true);
        usuarioMock.setIntentosFallidos(5);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuarioMock));
        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(usuarioMock);

        // Act
        usuarioService.desbloquearCuenta(1L);

        // Assert
        verify(usuarioRepository).save(argThat(usuario ->
            !usuario.getCuentaBloqueada() &&
            usuario.getIntentosFallidos() == 0
        ));
    }
}
