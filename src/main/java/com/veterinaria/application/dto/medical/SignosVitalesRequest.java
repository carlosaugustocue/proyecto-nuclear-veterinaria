package com.veterinaria.application.dto.medical;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para signos vitales en las solicitudes.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignosVitalesRequest {

    @DecimalMin(value = "30.0", message = "La temperatura no puede ser menor a 30°C")
    @DecimalMax(value = "45.0", message = "La temperatura no puede ser mayor a 45°C")
    private Double temperatura;

    @DecimalMin(value = "0.1", message = "El peso debe ser mayor a 0.1 kg")
    @DecimalMax(value = "200.0", message = "El peso no puede exceder 200 kg")
    private Double pesoKg;

    @Min(value = 30, message = "La frecuencia cardíaca no puede ser menor a 30 lpm")
    @Max(value = 300, message = "La frecuencia cardíaca no puede exceder 300 lpm")
    private Integer frecuenciaCardiaca;

    @Min(value = 5, message = "La frecuencia respiratoria no puede ser menor a 5 rpm")
    @Max(value = 100, message = "La frecuencia respiratoria no puede exceder 100 rpm")
    private Integer frecuenciaRespiratoria;

    @DecimalMin(value = "30.0", message = "La temperatura rectal no puede ser menor a 30°C")
    @DecimalMax(value = "45.0", message = "La temperatura rectal no puede ser mayor a 45°C")
    private Double temperaturaRectal;

    @Min(value = 1, message = "La condición corporal debe estar entre 1 y 9")
    @Max(value = 9, message = "La condición corporal debe estar entre 1 y 9")
    private Integer condicionCorporal;
}
