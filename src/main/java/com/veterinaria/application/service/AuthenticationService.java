package com.veterinaria.application.service;

import com.veterinaria.application.dto.auth.LoginRequest;
import com.veterinaria.application.dto.auth.LoginResponse;
import com.veterinaria.application.dto.auth.RefreshTokenRequest;
import com.veterinaria.application.dto.auth.RegisterRequest;
import com.veterinaria.application.repository.AuditoriaAccesoRepository;
import com.veterinaria.application.repository.RolRepository;
import com.veterinaria.application.repository.SesionRepository;
import com.veterinaria.application.repository.UsuarioRepository;
import com.veterinaria.domain.entity.security.AuditoriaAcceso;
import com.veterinaria.domain.entity.security.Rol;
import com.veterinaria.domain.entity.security.Sesion;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.enums.EstadoSesion;
import com.veterinaria.infrastructure.constants.SecurityConstants;
import com.veterinaria.infrastructure.exception.BusinessException;
import com.veterinaria.infrastructure.exception.DuplicateResourceException;
import com.veterinaria.infrastructure.exception.ResourceNotFoundException;
import com.veterinaria.infrastructure.exception.UnauthorizedException;
import com.veterinaria.infrastructure.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Servicio de autenticación.
 * Gestiona login, registro, refresh tokens y auditoría de accesos.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final SesionRepository sesionRepository;
    private final AuditoriaAccesoRepository auditoriaAccesoRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletRequest request;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    @Value("${jwt.refresh-expiration}")
    private long jwtRefreshExpirationMs;

    /**
     * Máximo de intentos fallidos antes de bloquear la cuenta.
     */
    private static final int MAX_INTENTOS_FALLIDOS = 5;

    /**
     * Autentica un usuario y genera tokens JWT.
     *
     * @param loginRequest Credenciales de login
     * @return LoginResponse con tokens y datos del usuario
     */
    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String ipAddress = getClientIP();
        String userAgent = request.getHeader("User-Agent");

        log.info("Intento de login para usuario: {} desde IP: {}", username, ipAddress);

        try {
            // Verificar si el usuario existe y está activo
            Usuario usuario = usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new UnauthorizedException("Credenciales inválidas"));

            // Verificar si la cuenta está bloqueada
            if (Boolean.TRUE.equals(usuario.getCuentaBloqueada())) {
                log.warn("Intento de login con cuenta bloqueada: {}", username);
                registrarAuditoriaFallida(username, ipAddress, userAgent,
                        "Cuenta bloqueada por exceso de intentos fallidos");
                throw new UnauthorizedException("Cuenta bloqueada. Contacte al administrador.");
            }

            // Verificar si la cuenta está activa
            if (!Boolean.TRUE.equals(usuario.getIsActive())) {
                log.warn("Intento de login con cuenta inactiva: {}", username);
                registrarAuditoriaFallida(username, ipAddress, userAgent, "Cuenta inactiva");
                throw new UnauthorizedException("Cuenta inactiva");
            }

            // Autenticar con Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, loginRequest.getPassword())
            );

            // Login exitoso - resetear intentos fallidos
            usuario.resetearIntentosFallidos();
            usuario.actualizarUltimoAcceso();
            usuarioRepository.save(usuario);

            // Generar tokens
            String token = jwtTokenProvider.generateToken(authentication);
            String refreshToken = jwtTokenProvider.generateRefreshToken(username);

            // Crear sesión
            Sesion sesion = crearSesion(usuario, token, refreshToken, ipAddress, userAgent);

            // Registrar auditoría exitosa
            AuditoriaAcceso auditoria = AuditoriaAcceso.loginExitoso(
                    usuario, ipAddress, userAgent, sesion.getId()
            );
            auditoriaAccesoRepository.save(auditoria);

            log.info("Login exitoso para usuario: {}", username);

            // Construir respuesta
            return construirLoginResponse(usuario, token, refreshToken);

        } catch (AuthenticationException e) {
            // Login fallido
            log.error("Login fallido para usuario: {} - {}", username, e.getMessage());

            // Buscar usuario para incrementar intentos fallidos
            usuarioRepository.findByUsername(username).ifPresent(usuario -> {
                usuario.incrementarIntentosFallidos(MAX_INTENTOS_FALLIDOS);
                usuarioRepository.save(usuario);

                if (Boolean.TRUE.equals(usuario.getCuentaBloqueada())) {
                    log.warn("Cuenta bloqueada automáticamente por intentos fallidos: {}", username);
                }
            });

            // Registrar auditoría fallida
            registrarAuditoriaFallida(username, ipAddress, userAgent, "Credenciales inválidas");

            throw new UnauthorizedException("Credenciales inválidas");
        }
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param registerRequest Datos del nuevo usuario
     * @return Usuario creado
     */
    @Transactional
    public Usuario register(RegisterRequest registerRequest) {
        log.info("Registrando nuevo usuario: {}", registerRequest.getUsername());

        // Validar que no exista el username
        if (usuarioRepository.existsByUsername(registerRequest.getUsername())) {
            throw new DuplicateResourceException("Usuario", "username", registerRequest.getUsername());
        }

        // Validar que no exista el email
        if (usuarioRepository.existsByEmail(registerRequest.getEmail())) {
            throw new DuplicateResourceException("Usuario", "email", registerRequest.getEmail());
        }

        // Validar DNI si está presente
        if (registerRequest.getDni() != null && usuarioRepository.existsByDni(registerRequest.getDni())) {
            throw new DuplicateResourceException("Usuario", "dni", registerRequest.getDni());
        }

        // Crear usuario
        Usuario usuario = Usuario.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .nombre(registerRequest.getNombre())
                .apellido(registerRequest.getApellido())
                .dni(registerRequest.getDni())
                .telefono(registerRequest.getTelefono())
                .direccion(registerRequest.getDireccion())
                .tipoUsuario(registerRequest.getTipoUsuario())
                .intentosFallidos(0)
                .cuentaBloqueada(false)
                .cuentaExpirada(false)
                .credencialesExpiradas(false)
                .requiereCambioPassword(false)
                .build();

        // Asignar rol por defecto según tipo de usuario
        asignarRolPorDefecto(usuario);

        Usuario savedUsuario = usuarioRepository.save(usuario);

        log.info("Usuario registrado exitosamente: {}", savedUsuario.getUsername());

        return savedUsuario;
    }

    /**
     * Renueva el token JWT usando el refresh token.
     *
     * @param refreshTokenRequest Refresh token
     * @return LoginResponse con nuevos tokens
     */
    @Transactional
    public LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        log.debug("Renovando token con refresh token");

        // Validar refresh token
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new UnauthorizedException("Refresh token inválido o expirado");
        }

        // Buscar sesión por refresh token
        Sesion sesion = sesionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new UnauthorizedException("Sesión no encontrada"));

        // Verificar que la sesión esté activa
        if (!sesion.isValid()) {
            throw new UnauthorizedException("Sesión expirada o inválida");
        }

        // Obtener usuario
        Usuario usuario = sesion.getUsuario();

        // Verificar que el usuario esté activo
        if (!Boolean.TRUE.equals(usuario.getIsActive())) {
            throw new UnauthorizedException("Usuario inactivo");
        }

        // Generar nuevo token
        String newToken = jwtTokenProvider.generateTokenFromUsername(
                usuario.getUsername(),
                usuario.getRoles().stream()
                        .map(Rol::getNombre)
                        .collect(Collectors.toList())
        );

        // Actualizar sesión
        LocalDateTime nuevaExpiracion = LocalDateTime.now()
                .plusSeconds(jwtExpirationMs / 1000);
        sesion.renovarToken(newToken, nuevaExpiracion);
        sesionRepository.save(sesion);

        log.info("Token renovado exitosamente para usuario: {}", usuario.getUsername());

        return construirLoginResponse(usuario, newToken, refreshToken);
    }

    /**
     * Cierra la sesión del usuario.
     *
     * @param token Token JWT
     */
    @Transactional
    public void logout(String token) {
        log.debug("Cerrando sesión");

        // Buscar sesión por token
        sesionRepository.findByToken(token).ifPresent(sesion -> {
            sesion.cerrarManual();
            sesionRepository.save(sesion);

            // Registrar auditoría
            AuditoriaAcceso auditoria = AuditoriaAcceso.logout(
                    sesion.getUsuario(),
                    getClientIP(),
                    sesion.getId()
            );
            auditoriaAccesoRepository.save(auditoria);

            log.info("Sesión cerrada para usuario: {}", sesion.getUsuario().getUsername());
        });
    }

    /**
     * Crea una nueva sesión para el usuario.
     */
    private Sesion crearSesion(Usuario usuario, String token, String refreshToken,
                                String ipAddress, String userAgent) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationDate = now.plusSeconds(jwtExpirationMs / 1000);

        Sesion sesion = Sesion.builder()
                .usuario(usuario)
                .token(token)
                .refreshToken(refreshToken)
                .fechaInicio(now)
                .fechaExpiracion(expirationDate)
                .estado(EstadoSesion.ACTIVA)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .ultimaActividad(now)
                .build();

        return sesionRepository.save(sesion);
    }

    /**
     * Construye la respuesta de login.
     */
    private LoginResponse construirLoginResponse(Usuario usuario, String token, String refreshToken) {
        Set<String> roles = usuario.getRoles().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toSet());

        Set<String> permisos = usuario.getRoles().stream()
                .flatMap(rol -> rol.getPermisos().stream())
                .collect(Collectors.toSet());

        return LoginResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtExpirationMs / 1000)
                .userId(usuario.getId())
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .nombreCompleto(usuario.getNombreCompleto())
                .roles(roles)
                .permisos(permisos)
                .loginTime(LocalDateTime.now())
                .build();
    }

    /**
     * Registra una auditoría de login fallido.
     */
    private void registrarAuditoriaFallida(String username, String ipAddress,
                                            String userAgent, String mensajeError) {
        AuditoriaAcceso auditoria = AuditoriaAcceso.loginFallido(
                username, ipAddress, userAgent, mensajeError
        );
        auditoriaAccesoRepository.save(auditoria);
    }

    /**
     * Asigna un rol por defecto según el tipo de usuario.
     */
    private void asignarRolPorDefecto(Usuario usuario) {
        String nombreRol = switch (usuario.getTipoUsuario()) {
            case VETERINARIO -> SecurityConstants.ROLE_VETERINARIO;
            case RECEPCIONISTA -> SecurityConstants.ROLE_RECEPCIONISTA;
            case ADMINISTRADOR -> SecurityConstants.ROLE_ADMIN;
            case ASISTENTE -> SecurityConstants.ROLE_ASISTENTE;
            case PROPIETARIO -> SecurityConstants.ROLE_CLIENTE;
            default -> SecurityConstants.ROLE_CLIENTE;
        };

        Rol rol = rolRepository.findByNombre(nombreRol)
                .orElseThrow(() -> new ResourceNotFoundException("Rol", "nombre", nombreRol));

        usuario.agregarRol(rol);
    }

    /**
     * Obtiene la dirección IP del cliente.
     */
    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
