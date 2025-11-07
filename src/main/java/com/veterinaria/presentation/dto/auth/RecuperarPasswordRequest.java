package com.veterinaria.presentation.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para solicitud de recuperación de contraseña.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecuperarPasswordRequest {

    /**
     * Email del usuario que solicita recuperar contraseña
     */
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    private String email;
}
