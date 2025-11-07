package com.veterinaria.presentation.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para restablecer contraseña con token.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestablecerPasswordRequest {

    /**
     * Token de recuperación enviado por email
     */
    @NotBlank(message = "El token es obligatorio")
    private String token;

    /**
     * Nueva contraseña
     */
    @NotBlank(message = "La nueva contraseña es obligatoria")
    private String passwordNueva;

    /**
     * Confirmación de la nueva contraseña
     */
    @NotBlank(message = "La confirmación de contraseña es obligatoria")
    private String passwordConfirmacion;

    /**
     * Valida que las contraseñas coincidan
     *
     * @return true si coinciden
     */
    public boolean passwordsCoinciden() {
        return passwordNueva != null && passwordNueva.equals(passwordConfirmacion);
    }
}
