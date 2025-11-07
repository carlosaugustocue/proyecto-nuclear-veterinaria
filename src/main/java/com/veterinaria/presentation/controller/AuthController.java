package com.veterinaria.presentation.controller;

import com.veterinaria.application.service.ServicioAutenticacion;
import com.veterinaria.infrastructure.security.jwt.JwtTokenProvider;
import com.veterinaria.presentation.dto.auth.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador REST para endpoints de autenticación.
 * Maneja login, logout, cambio de contraseña, etc.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ServicioAutenticacion servicioAutenticacion;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Endpoint de login
     *
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {
        
        try {
            System.out.println("Esto es una prueba");
            String direccionIP = servicioAutenticacion.obtenerDireccionIP(httpRequest);
            LoginResponse response = servicioAutenticacion.login(request, direccionIP);
            
            log.info("Login exitoso para: {}", request.getEmail());
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            log.error("Error en login: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(crearMensajeError(e.getMessage()));
        }
    }

    @GetMapping("/ping")
    public ResponseEntity<?> ping() {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "AuthController funcionando correctamente 🚀");
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }


    /**
     * Endpoint de logout
     *
     * POST /api/auth/logout
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestBody(required = false) LogoutRequest request,
            HttpServletRequest httpRequest) {
        
        try {
            String authHeader = httpRequest.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(crearMensajeError("Token no proporcionado"));
            }

            String token = jwtTokenProvider.extraerTokenDelHeader(authHeader);
            String direccionIP = servicioAutenticacion.obtenerDireccionIP(httpRequest);

            LogoutRequest logoutRequest = request != null ? request : LogoutRequest.builder().build();
            servicioAutenticacion.logout(token, logoutRequest, direccionIP);

            log.info("Logout exitoso");
            return ResponseEntity.ok(crearMensajeExito("Sesión cerrada exitosamente"));
            
        } catch (Exception e) {
            log.error("Error en logout: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearMensajeError("Error al cerrar sesión"));
        }
    }

    /**
     * Endpoint para cambiar contraseña
     *
     * POST /api/auth/cambiar-password
     */
    @PostMapping("/cambiar-password")
    public ResponseEntity<?> cambiarPassword(
            @Valid @RequestBody CambiarPasswordRequest request,
            HttpServletRequest httpRequest) {
        
        try {
            // Obtener email del usuario autenticado desde el token
            String authHeader = httpRequest.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(crearMensajeError("Usuario no autenticado"));
            }

            String token = jwtTokenProvider.extraerTokenDelHeader(authHeader);
            String email = jwtTokenProvider.obtenerEmailDelToken(token);
            String direccionIP = servicioAutenticacion.obtenerDireccionIP(httpRequest);

            servicioAutenticacion.cambiarPassword(email, request, direccionIP);

            log.info("Contraseña cambiada exitosamente para: {}", email);
            return ResponseEntity.ok(crearMensajeExito(
                    "Contraseña cambiada exitosamente. Todas las sesiones han sido cerradas."));
            
        } catch (RuntimeException e) {
            log.error("Error cambiando contraseña: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(crearMensajeError(e.getMessage()));
        }
    }

    /**
     * Endpoint para validar token
     *
     * GET /api/auth/validar
     */
    @GetMapping("/validar")
    public ResponseEntity<?> validarToken(HttpServletRequest httpRequest) {
        try {
            String authHeader = httpRequest.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(crearMensajeError("Token no proporcionado"));
            }

            String token = jwtTokenProvider.extraerTokenDelHeader(authHeader);
            
            if (jwtTokenProvider.validarToken(token)) {
                String email = jwtTokenProvider.obtenerEmailDelToken(token);
                Map<String, Object> response = new HashMap<>();
                response.put("valido", true);
                response.put("email", email);
                response.put("expiracion", jwtTokenProvider.obtenerFechaExpiracion(token));
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(crearMensajeError("Token inválido o expirado"));
            }
            
        } catch (Exception e) {
            log.error("Error validando token: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearMensajeError("Error al validar token"));
        }
    }

    /**
     * Endpoint para obtener información del usuario autenticado
     *
     * GET /api/auth/me
     */
    @GetMapping("/me")
    public ResponseEntity<?> obtenerUsuarioActual(HttpServletRequest httpRequest) {
        try {
            return servicioAutenticacion.obtenerUsuarioAutenticado(httpRequest)
                    .map(usuario -> {
                        Map<String, Object> response = new HashMap<>();
                        response.put("email", usuario.getEmail());
                        response.put("nombreCompleto", usuario.getNombreCompleto());
                        response.put("username", usuario.getUsername());
                        response.put("roles", usuario.getRoles().stream()
                                .map(rol -> rol.getNombre()).toList());
                        return ResponseEntity.ok(response);
                    })
                    .orElseGet(() -> ResponseEntity
                            .status(HttpStatus.UNAUTHORIZED)
                            .body(crearMensajeError("Usuario no autenticado")));
                            
        } catch (Exception e) {
            log.error("Error obteniendo usuario actual: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(crearMensajeError("Error al obtener información del usuario"));
        }
    }

    /**
     * Endpoint para solicitar recuperación de contraseña
     *
     * POST /api/auth/recuperar-password
     */
    @PostMapping("/recuperar-password")
    public ResponseEntity<?> recuperarPassword(
            @Valid @RequestBody RecuperarPasswordRequest request) {
        
        try {
            // TODO: Implementar lógica de recuperación de contraseña
            // 1. Generar token de recuperación
            // 2. Enviar email con enlace de recuperación
            // 3. Guardar token temporalmente
            
            log.info("Solicitud de recuperación de contraseña para: {}", request.getEmail());
            
            return ResponseEntity.ok(crearMensajeExito(
                    "Si el email existe, recibirás instrucciones para recuperar tu contraseña"));
                    
        } catch (Exception e) {
            log.error("Error en recuperación de contraseña: {}", e.getMessage());
            // No revelar si el email existe por seguridad
            return ResponseEntity.ok(crearMensajeExito(
                    "Si el email existe, recibirás instrucciones para recuperar tu contraseña"));
        }
    }

    /**
     * Crea un mensaje de error estándar
     */
    private Map<String, Object> crearMensajeError(String mensaje) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", true);
        response.put("mensaje", mensaje);
        return response;
    }

    /**
     * Crea un mensaje de éxito estándar
     */
    private Map<String, Object> crearMensajeExito(String mensaje) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", false);
        response.put("mensaje", mensaje);
        return response;
    }
}
