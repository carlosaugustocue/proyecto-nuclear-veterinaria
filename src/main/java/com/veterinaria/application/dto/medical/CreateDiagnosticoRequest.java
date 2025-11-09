package com.veterinaria.application.dto.medical;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para crear un nuevo Diagnóstico.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDiagnosticoRequest {

    @NotNull(message = "El ID de la consulta es obligatorio")
    private Long consultaId;

    @NotBlank(message = "El tipo de diagnóstico es obligatorio")
    @Size(max = 50, message = "El tipo no puede exceder 50 caracteres")
    @Pattern(regexp = "PRESUNTIVO|DEFINITIVO|DIFERENCIAL",
             message = "El tipo debe ser: PRESUNTIVO, DEFINITIVO o DIFERENCIAL")
    private String tipoDiagnostico;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;

    @Size(max = 10, message = "El código CIE-10 no puede exceder 10 caracteres")
    private String codigoCie10;

    @Size(max = 20, message = "La gravedad no puede exceder 20 caracteres")
    @Pattern(regexp = "LEVE|MODERADO|GRAVE|CRÍTICO",
             message = "La gravedad debe ser: LEVE, MODERADO, GRAVE o CRÍTICO")
    private String gravedad;

    @Size(max = 5000, message = "Las notas no pueden exceder 5000 caracteres")
    private String notas;

    private Boolean esPrincipal;
}
