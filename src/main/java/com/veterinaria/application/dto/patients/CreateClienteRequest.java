package com.veterinaria.application.dto.patients;

import jakarta.validation.constraints.*;

/**
 * DTO para crear un nuevo cliente (propietario).
 *
 * @author Sistema Veterinaria
 */
public record CreateClienteRequest(
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    String nombre,

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    String apellido,

    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 7, max = 20, message = "El DNI debe tener entre 7 y 20 caracteres")
    String dni,

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    String email,

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[+]?[0-9]{7,15}$", message = "El teléfono debe tener entre 7 y 15 dígitos")
    String telefono,

    @NotBlank(message = "La dirección es obligatoria")
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
