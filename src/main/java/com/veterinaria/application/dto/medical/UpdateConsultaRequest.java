package com.veterinaria.application.dto.medical;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para actualizar una Consulta médica.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateConsultaRequest {

    @Size(max = 500, message = "El motivo no puede exceder 500 caracteres")
    private String motivo;

    @Size(max = 5000, message = "La anamnesis no puede exceder 5000 caracteres")
    private String anamnesis;

    @Size(max = 5000, message = "El examen físico no puede exceder 5000 caracteres")
    private String examenFisico;

    @Size(max = 5000, message = "Las observaciones no pueden exceder 5000 caracteres")
    private String observaciones;

    @Size(max = 5000, message = "El plan de tratamiento no puede exceder 5000 caracteres")
    private String planTratamiento;

    @Size(max = 100, message = "El pronóstico no puede exceder 100 caracteres")
    private String pronostico;

    private Boolean requiereSeguimiento;

    private LocalDate fechaSeguimiento;

    @Valid
    private SignosVitalesRequest signosVitales;
}
