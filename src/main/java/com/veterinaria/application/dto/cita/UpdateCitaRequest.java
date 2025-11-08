package com.veterinaria.application.dto.cita;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para actualizar una Cita existente.
 * Solo permite actualizar campos no críticos (motivo y notas).
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCitaRequest {

    @Size(min = 5, max = 500, message = "El motivo debe tener entre 5 y 500 caracteres")
    private String motivo;

    @Size(max = 1000, message = "Las notas no pueden exceder 1000 caracteres")
    private String notas;
}
