package com.veterinaria.application.dto.patients;

import com.veterinaria.domain.enums.EstadoPaciente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

/**
 * Request DTO para cambiar el estado de un paciente.
 * Implementa HU-23: Registro de Mascotas Fallecidas o Inactivas
 *
 * @author Sistema Veterinaria
 */
@Builder
public record CambiarEstadoPacienteRequest(

    @NotNull(message = "El nuevo estado es obligatorio")
    EstadoPaciente nuevoEstado,

    @NotBlank(message = "El motivo del cambio de estado es obligatorio")
    @Size(min = 5, max = 500, message = "El motivo debe tener entre 5 y 500 caracteres")
    String motivo,

    LocalDate fechaCambio
) {
}
