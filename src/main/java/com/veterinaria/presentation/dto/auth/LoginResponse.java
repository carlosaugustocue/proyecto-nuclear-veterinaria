package com.veterinaria.presentation.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO para respuesta de inicio de sesión.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    /**
     * Token JWT de acceso
     */
    private String token;

    /**
     * Tipo de token (Bearer)
     */
    @Builder.Default
    private String tipo = "Bearer";

    /**
     * Email del usuario autenticado
     */
    private String email;

    /**
     * Nombre completo del usuario
     */
    private String nombreCompleto;

    /**
     * Roles del usuario
     */
    private List<String> roles;

    /**
     * Permisos del usuario
     */
    private List<String> permisos;

    /**
     * Indica si es un dispositivo nuevo
     */
    private boolean dispositivoNuevo;

    /**
     * Mensaje adicional (opcional)
     */
    private String mensaje;

    /**
     * Duración del token en segundos
     */
    private long expiracionSegundos;
}
