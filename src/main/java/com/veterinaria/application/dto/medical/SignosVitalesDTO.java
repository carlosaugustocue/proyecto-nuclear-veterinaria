package com.veterinaria.application.dto.medical;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para representar signos vitales en las respuestas de la API.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignosVitalesDTO {

    private Double temperatura;
    private Double pesoKg;
    private Integer frecuenciaCardiaca;
    private Integer frecuenciaRespiratoria;
    private Double temperaturaRectal;
    private Integer condicionCorporal;

    // Campos calculados
    private String condicionCorporalDescripcion;
    private Boolean tieneParametrosAnormales;
    private Boolean tieneFiebre;
    private Boolean tieneHipotermia;
}
