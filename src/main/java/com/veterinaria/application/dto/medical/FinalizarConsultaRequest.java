package com.veterinaria.application.dto.medical;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para finalizar una Consulta médica.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinalizarConsultaRequest {

    @Size(max = 5000, message = "Las observaciones no pueden exceder 5000 caracteres")
    private String observacionesFinales;

    @Size(max = 5000, message = "El plan de tratamiento no puede exceder 5000 caracteres")
    private String planTratamiento;

    @Size(max = 100, message = "El pronóstico no puede exceder 100 caracteres")
    private String pronostico;

    private Boolean requiereSeguimiento;

    private LocalDate fechaSeguimiento;
}
