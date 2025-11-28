package com.veterinaria.application.dto.patients;

import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * Request DTO para actualizar una Raza existente.
 * Todos los campos son opcionales.
 *
 * @author Sistema Veterinaria
 */
@Builder
public record UpdateRazaRequest(

    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    String nombre,

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    String descripcion,

    Boolean esMestizo,

    @Size(max = 20, message = "El tamaño típico no puede exceder 20 caracteres")
    String tamanioTipico,

    Double pesoPromedioKg,

    Boolean isActive
) {
}
