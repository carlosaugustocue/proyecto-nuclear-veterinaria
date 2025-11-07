package com.veterinaria.application.service;

import com.veterinaria.application.repository.DispositivoConfiableRepository;
import com.veterinaria.application.repository.UsuarioRepository;
import com.veterinaria.domain.entity.security.DispositivoConfiable;
import com.veterinaria.domain.entity.security.Permiso;
import com.veterinaria.domain.entity.security.Rol;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.service.*;
import com.veterinaria.infrastructure.security.jwt.JwtTokenProvider;
import com.veterinaria.presentation.dto.auth.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio de autenticación que orquesta todo el proceso de login, logout y gestión de sesiones.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ServicioAutenticacion {

    private final UsuarioRepository usuarioRepository;
    private final DispositivoConfiableRepository dispositivoRepository;
    private final EncriptadorContrasena encriptadorContrasena;
    private final JwtTokenProvider jwtTokenProvider;
    private final GestorSesiones gestorSesiones;
    private final GestorIntentosFallidos gestorIntentosFallidos;
    private final AuditoriaAcceso auditoriaAcceso;

    /**
     * Realiza el proceso de login de un usuario
     *
     * @param request datos de login
     * @param direccionIP IP del cliente
     * @return respuesta con token y datos del usuario
     */
    @Transactional
    public LoginResponse login(LoginRequest request, String direccionIP) {
        String email = request.getEmail();

        try {
            // 1. Verificar si la cuenta está bloqueada por intentos fallidos
            if (gestorIntentosFallidos.estaBloqueado(email)) {
                long minutosRestantes = gestorIntentosFallidos.obtenerMinutosRestantesBloqueo(email);
                auditoriaAcceso.registrarAccesoBloqueado(email, direccionIP);
                throw new RuntimeException("Cuenta bloqueada. Intente nuevamente en " + minutosRestantes + " minutos");
            }

            // 2. Buscar usuario por email
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> {
                        gestorIntentosFallidos.registrarIntentoFallido(email);
                        auditoriaAcceso.registrarLoginFallido(email, direccionIP, "Usuario no encontrado");
                        return new RuntimeException("Credenciales inválidas");
                    });

            // 3. Verificar si la cuenta del usuario está bloqueada
            if (!usuario.isCuentaHabilitada()) {
                auditoriaAcceso.registrarAccesoDenegado(usuario, "LOGIN", "AUTENTICACION", 
                        email, direccionIP, "Cuenta deshabilitada o bloqueada");
                throw new RuntimeException("Cuenta deshabilitada o bloqueada");
            }

            // 4. Verificar contraseña
            if (!encriptadorContrasena.verificar(request.getPassword(), usuario.getPassword())) {
                gestorIntentosFallidos.registrarIntentoFallido(email);
                auditoriaAcceso.registrarLoginFallido(email, direccionIP, "Contraseña incorrecta");
                throw new RuntimeException("Credenciales inválidas");
            }

            // 5. Login exitoso - reiniciar intentos fallidos
            gestorIntentosFallidos.reiniciarIntentos(email);
            usuario.resetearIntentosFallidos();
            usuario.actualizarUltimoAcceso();
            usuarioRepository.save(usuario);

            // 6. Verificar dispositivo confiable
            boolean dispositivoNuevo = false;
            if (request.getHuellaDispositivo() != null) {
                dispositivoNuevo = verificarYRegistrarDispositivo(usuario, request);
            }

            // 7. Generar token JWT
            List<String> roles = usuario.getRoles().stream()
                    .map(Rol::getNombre)
                    .collect(Collectors.toList());

            List<String> permisos = usuario.getRoles().stream()
                    .flatMap(rol -> rol.getPermisos().stream())
                    .map(Permiso::getCodigo)
                    .distinct()
                    .collect(Collectors.toList());

            String token = jwtTokenProvider.generarToken(email, roles, permisos);

            // 8. Registrar sesión
            gestorSesiones.registrarSesion(token, usuario, direccionIP);

            // 9. Auditar login exitoso
            auditoriaAcceso.registrarLoginExitoso(usuario, direccionIP);

            log.info("Login exitoso para usuario: {}", email);

            // 10. Construir respuesta
            return LoginResponse.builder()
                    .token(token)
                    .tipo("Bearer")
                    .email(usuario.getEmail())
                    .nombreCompleto(usuario.getNombreCompleto())
                    .roles(roles)
                    .permisos(permisos)
                    .dispositivoNuevo(dispositivoNuevo)
                    .mensaje(usuario.getRequiereCambioPassword() ? 
                            "Debe cambiar su contraseña" : "Login exitoso")
                    .expiracionSegundos(jwtTokenProvider.obtenerTiempoExpiracion() / 1000)
                    .build();

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error en login para {}: {}", email, e.getMessage(), e);
            auditoriaAcceso.registrarLoginFallido(email, direccionIP, "Error interno: " + e.getMessage());
            throw new RuntimeException("Error al procesar el login");
        }
    }

    /**
     * Realiza el proceso de logout
     *
     * @param token token de la sesión
     * @param logoutRequest configuración del logout
     * @param direccionIP IP del cliente
     */
    @Transactional
    public void logout(String token, LogoutRequest logoutRequest, String direccionIP) {
        try {
            String email = jwtTokenProvider.obtenerEmailDelToken(token);
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (logoutRequest.isCerrarTodasLasSesiones()) {
                gestorSesiones.cerrarTodasLasSesionesDelUsuario(usuario);
                log.info("Todas las sesiones cerradas para usuario: {}", email);
            } else {
                gestorSesiones.cerrarSesion(token);
                log.info("Sesión cerrada para usuario: {}", email);
            }

            auditoriaAcceso.registrarLogout(usuario, direccionIP);

        } catch (Exception e) {
            log.error("Error en logout: {}", e.getMessage(), e);
            throw new RuntimeException("Error al cerrar sesión");
        }
    }

    /**
     * Cambia la contraseña de un usuario
     *
     * @param email email del usuario
     * @param request datos del cambio
     * @param direccionIP IP del cliente
     */
    @Transactional
    public void cambiarPassword(String email, CambiarPasswordRequest request, String direccionIP) {
        try {
            // 1. Validar que las contraseñas coincidan
            if (!request.passwordsCoinciden()) {
                throw new RuntimeException("Las contraseñas no coinciden");
            }

            // 2. Buscar usuario
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // 3. Verificar contraseña actual
            if (!encriptadorContrasena.verificar(request.getPasswordActual(), usuario.getPassword())) {
                auditoriaAcceso.registrarAccesoDenegado(usuario, "CAMBIAR_PASSWORD", 
                        "AUTENTICACION", email, direccionIP, "Contraseña actual incorrecta");
                throw new RuntimeException("Contraseña actual incorrecta");
            }

            // 4. Validar seguridad de nueva contraseña
            if (!encriptadorContrasena.esContrasenaSegura(request.getPasswordNueva())) {
                throw new RuntimeException(encriptadorContrasena.obtenerRequisitosContrasena());
            }

            // 5. Encriptar y actualizar contraseña
            String passwordEncriptada = encriptadorContrasena.encriptar(request.getPasswordNueva());
            usuario.setPassword(passwordEncriptada);
            usuario.setFechaCambioPassword(LocalDateTime.now());
            usuario.setRequiereCambioPassword(false);
            usuarioRepository.save(usuario);

            // 6. Cerrar todas las sesiones del usuario
            gestorSesiones.cerrarTodasLasSesionesDelUsuario(usuario);

            // 7. Auditar cambio
            auditoriaAcceso.registrarAccesoExitoso(usuario, "CAMBIAR_PASSWORD", 
                    "AUTENTICACION", email, direccionIP);

            log.info("Contraseña cambiada exitosamente para usuario: {}", email);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error cambiando contraseña: {}", e.getMessage(), e);
            throw new RuntimeException("Error al cambiar contraseña");
        }
    }

    /**
     * Valida un token JWT
     *
     * @param token token a validar
     * @return usuario si el token es válido
     */
    public Optional<Usuario> validarToken(String token) {
        try {
            if (!jwtTokenProvider.validarToken(token)) {
                return Optional.empty();
            }

            if (!gestorSesiones.esSesionValida(token)) {
                return Optional.empty();
            }

            String email = jwtTokenProvider.obtenerEmailDelToken(token);
            Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

            // Renovar sesión si es válida
            if (usuario.isPresent() && usuario.get().isCuentaHabilitada()) {
                gestorSesiones.renovarSesion(token);
                return usuario;
            }

            return Optional.empty();

        } catch (Exception e) {
            log.error("Error validando token: {}", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Obtiene el usuario autenticado desde el request
     *
     * @param request request HTTP
     * @return usuario autenticado
     */
    public Optional<Usuario> obtenerUsuarioAutenticado(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return Optional.empty();
        }

        String token = jwtTokenProvider.extraerTokenDelHeader(authHeader);
        if (token == null) {
            return Optional.empty();
        }

        return validarToken(token);
    }

    /**
     * Extrae la dirección IP del request
     *
     * @param request request HTTP
     * @return dirección IP
     */
    public String obtenerDireccionIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * Verifica y registra un dispositivo confiable
     *
     * @param usuario usuario
     * @param request datos del login
     * @return true si es un dispositivo nuevo
     */
    private boolean verificarYRegistrarDispositivo(Usuario usuario, LoginRequest request) {
        Optional<DispositivoConfiable> dispositivoExistente = 
                dispositivoRepository.findByUsuarioAndHuella(usuario, request.getHuellaDispositivo());

        if (dispositivoExistente.isPresent()) {
            return false; // Dispositivo conocido
        }

        // Registrar nuevo dispositivo si el usuario lo solicita
        if (request.isRecordarDispositivo()) {
            DispositivoConfiable nuevoDispositivo = DispositivoConfiable.builder()
                    .usuario(usuario)
                    .huella(request.getHuellaDispositivo())
                    .nombreDispositivo(request.getNombreDispositivo())
                    .fechaRegistro(LocalDateTime.now())
                    .build();
            dispositivoRepository.save(nuevoDispositivo);
            log.info("Nuevo dispositivo registrado para usuario: {}", usuario.getEmail());
        }

        return true; // Dispositivo nuevo
    }
}
