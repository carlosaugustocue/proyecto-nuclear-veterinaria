package com.veterinaria.presentation.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para solicitud de cambio de contraseña.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CambiarPasswordRequest {

    /**
     * Contraseña actual del usuario
     */
    @NotBlank(message = "La contraseña actual es obligatoria")
    private String passwordActual;

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
     * Valida que la nueva contraseña y su confirmación coincidan
     *
     * @return true si coinciden
     */
    public boolean passwordsCoinciden() {
        return passwordNueva != null && passwordNueva.equals(passwordConfirmacion);
    }
}
