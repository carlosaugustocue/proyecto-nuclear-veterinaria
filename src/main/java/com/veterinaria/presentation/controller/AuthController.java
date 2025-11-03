package com.veterinaria.presentation.controller;

import com.veterinaria.application.dto.auth.LoginRequest;
import com.veterinaria.application.dto.auth.LoginResponse;
import com.veterinaria.application.dto.auth.RefreshTokenRequest;
import com.veterinaria.application.dto.auth.RegisterRequest;
import com.veterinaria.application.dto.usuario.UsuarioDTO;
import com.veterinaria.application.service.AuthenticationService;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.infrastructure.constants.SecurityConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para endpoints de autenticación.
 * Maneja login, registro, refresh token y logout.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Autenticación", description = "Endpoints para autenticación y registro de usuarios")
public class AuthController {

    private final AuthenticationService authenticationService;

    /**
     * Endpoint de login.
     *
     * @param loginRequest Credenciales de usuario
     * @return LoginResponse con tokens JWT
     */
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y genera tokens JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("POST /auth/login - Usuario: {}", loginRequest.getUsername());

        LoginResponse response = authenticationService.login(loginRequest);

        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint de registro de nuevos usuarios.
     *
     * @param registerRequest Datos del nuevo usuario
     * @return Usuario creado
     */
    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "409", description = "El usuario ya existe"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<UsuarioDTO> register(@Valid @RequestBody RegisterRequest registerRequest) {
        log.info("POST /auth/register - Usuario: {}", registerRequest.getUsername());

        Usuario usuario = authenticationService.register(registerRequest);

        UsuarioDTO usuarioDTO = convertirAUsuarioDTO(usuario);

        return new ResponseEntity<>(usuarioDTO, HttpStatus.CREATED);
    }

    /**
     * Endpoint para refrescar el token JWT.
     *
     * @param refreshTokenRequest Refresh token
     * @return LoginResponse con nuevo token
     */
    @PostMapping("/refresh-token")
    @Operation(summary = "Refrescar token", description = "Genera un nuevo token JWT usando el refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refrescado exitosamente"),
            @ApiResponse(responseCode = "401", description = "Refresh token inválido o expirado")
    })
    public ResponseEntity<LoginResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        log.info("POST /auth/refresh-token");

        LoginResponse response = authenticationService.refreshToken(refreshTokenRequest);

        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint de logout.
     *
     * @param request HttpServletRequest para extraer el token
     * @return Mensaje de confirmación
     */
    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesión", description = "Cierra la sesión actual del usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout exitoso"),
            @ApiResponse(responseCode = "401", description = "Token inválido")
    })
    public ResponseEntity<MessageResponse> logout(HttpServletRequest request) {
        log.info("POST /auth/logout");

        String token = extractTokenFromRequest(request);

        if (token != null) {
            authenticationService.logout(token);
        }

        return ResponseEntity.ok(new MessageResponse("Sesión cerrada exitosamente"));
    }

    /**
     * Endpoint de validación de token.
     *
     * @return Mensaje de confirmación
     */
    @GetMapping("/validate")
    @Operation(summary = "Validar token", description = "Valida si el token JWT actual es válido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token válido"),
            @ApiResponse(responseCode = "401", description = "Token inválido")
    })
    public ResponseEntity<MessageResponse> validateToken() {
        log.info("GET /auth/validate");
        return ResponseEntity.ok(new MessageResponse("Token válido"));
    }

    /**
     * Extrae el token JWT del header Authorization.
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(SecurityConstants.JWT_HEADER);
        if (bearerToken != null && bearerToken.startsWith(SecurityConstants.JWT_PREFIX)) {
            return bearerToken.substring(SecurityConstants.JWT_PREFIX.length());
        }
        return null;
    }

    /**
     * Convierte una entidad Usuario a UsuarioDTO.
     */
    private UsuarioDTO convertirAUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .nombreCompleto(usuario.getNombreCompleto())
                .dni(usuario.getDni())
                .telefono(usuario.getTelefono())
                .direccion(usuario.getDireccion())
                .tipoUsuario(usuario.getTipoUsuario())
                .roles(usuario.getRoles().stream()
                        .map(rol -> rol.getNombre())
                        .collect(java.util.stream.Collectors.toSet()))
                .cuentaBloqueada(usuario.getCuentaBloqueada())
                .isActive(usuario.getIsActive())
                .createdAt(usuario.getCreatedAt())
                .build();
    }

    /**
     * Clase interna para respuestas de mensajes simples.
     */
    @lombok.Data
    @lombok.AllArgsConstructor
    public static class MessageResponse {
        private String message;
    }
}
