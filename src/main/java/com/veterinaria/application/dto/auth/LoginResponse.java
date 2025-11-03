package com.veterinaria.application.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO para respuesta de login exitoso.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Long expiresIn;

    // Información del usuario
    private Long userId;
    private String username;
    private String email;
    private String nombreCompleto;
    private Set<String> roles;
    private Set<String> permisos;

    private LocalDateTime loginTime;
}
