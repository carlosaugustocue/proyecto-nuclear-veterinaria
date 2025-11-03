package com.veterinaria.application.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO para actualizar un usuario existente.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUsuarioRequest {

    @Email(message = "El email debe ser válido")
    private String email;

    private String nombre;
    private String apellido;

    @Size(max = 8, message = "El DNI debe tener 8 dígitos")
    private String dni;

    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    private String telefono;

    @Size(max = 255, message = "La dirección no puede exceder 255 caracteres")
    private String direccion;

    private Set<Long> roleIds;
    private String fotoperfilUrl;
}
