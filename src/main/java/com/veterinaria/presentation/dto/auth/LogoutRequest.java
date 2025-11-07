package com.veterinaria.presentation.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para solicitud de cierre de sesión.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogoutRequest {

    /**
     * Cerrar todas las sesiones activas del usuario
     */
    @Builder.Default
    private boolean cerrarTodasLasSesiones = false;
}
