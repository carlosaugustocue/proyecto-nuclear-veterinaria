package com.veterinaria.application.dto.cita;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO para reagendar una Cita.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReagendarCitaRequest {

    @NotNull(message = "La nueva fecha es obligatoria")
    @Future(message = "La nueva fecha debe ser futura")
    private LocalDate nuevaFecha;

    @NotNull(message = "La nueva hora es obligatoria")
    private LocalTime nuevaHora;

    @NotBlank(message = "El motivo del reagendamiento es obligatorio")
    @Size(min = 5, max = 500, message = "El motivo debe tener entre 5 y 500 caracteres")
    private String motivoReagendamiento;
}
