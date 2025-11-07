package com.veterinaria.application.dto.paciente;

import com.veterinaria.domain.enums.EstadoPaciente;
import jakarta.validation.constraints.*;

/**
 * DTO para actualizar un paciente existente.
 * Solo incluye campos actualizables.
 *
 * @author Sistema Veterinaria
 */
public record UpdatePacienteRequest(
    @Size(max = 100, message = "La raza no puede exceder 100 caracteres")
    String raza,

    @Size(max = 50, message = "El color no puede exceder 50 caracteres")
    String color,

    @DecimalMin(value = "0.1", message = "El peso debe ser mayor a 0.1 kg")
    @DecimalMax(value = "500.0", message = "El peso no puede exceder 500 kg")
    Double pesoKg,

    EstadoPaciente estado,

    @Size(max = 500, message = "La URL de la foto no puede exceder 500 caracteres")
    String fotoUrl,

    @Size(max = 1000, message = "Las observaciones no pueden exceder 1000 caracteres")
    String observaciones,

    @Positive(message = "El ID del cliente debe ser positivo")
    Long clienteId
) {
}
