package com.veterinaria.application.dto.cliente;

import jakarta.validation.constraints.*;

/**
 * DTO para actualizar un cliente existente.
 *
 * @author Sistema Veterinaria
 */
public record UpdateClienteRequest(
    @Email(message = "El formato del email no es válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    String email,

    @Pattern(regexp = "^[+]?[0-9]{7,15}$", message = "El teléfono debe tener entre 7 y 15 dígitos")
    String telefono,

    @Size(max = 300, message = "La dirección no puede exceder 300 caracteres")
    String direccion,

    @Size(max = 100, message = "La ciudad no puede exceder 100 caracteres")
    String ciudad,

    @Size(max = 100, message = "El departamento no puede exceder 100 caracteres")
    String departamento,

    @Size(max = 20, message = "El código postal no puede exceder 20 caracteres")
    String codigoPostal,

    @Size(max = 500, message = "Las observaciones no pueden exceder 500 caracteres")
    String observaciones
) {
}
