package com.veterinaria.application.dto.medical;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para representar un Diagnóstico médico en las respuestas de la API.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticoDTO {

    private Long id;
    private String tipoDiagnostico;
    private String descripcion;
    private String codigoCie10;
    private String gravedad;
    private String notas;
    private Boolean esPrincipal;

    // Información de la consulta
    private Long consultaId;

    // Campos calculados
    private Boolean esPresuntivo;
    private Boolean esDefinitivo;
    private Boolean esGraveOCritico;
    private Boolean tieneCie10;

    // Metadata
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
