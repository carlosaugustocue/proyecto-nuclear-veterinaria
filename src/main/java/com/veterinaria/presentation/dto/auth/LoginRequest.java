package com.veterinaria.presentation.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para solicitud de inicio de sesión.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    /**
     * Email del usuario
     */
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    private String email;

    /**
     * Contraseña del usuario
     */
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    /**
     * Huella digital del dispositivo (opcional)
     */
    private String huellaDispositivo;

    /**
     * Nombre del dispositivo (opcional)
     */
    private String nombreDispositivo;

    /**
     * Recordar sesión en este dispositivo
     */
    @Builder.Default
    private boolean recordarDispositivo = false;
}
