package com.veterinaria.application.dto.medical;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para representar un Historial Clínico en las respuestas de la API.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistorialClinicoDTO {

    private Long id;
    private LocalDate fechaApertura;
    private String grupoSanguineo;
    private String alergias;
    private String condicionesCronicas;
    private String notasImportantes;

    // Información del paciente
    private Long pacienteId;
    private String pacienteNombre;
    private String pacienteEspecie;
    private String pacienteRaza;

    // Consultas, vacunas y exámenes (opcional, para vista completa)
    private List<ConsultaDTO> consultas;
    private List<VacunaDTO> vacunas;
    private List<ExamenDTO> examenes;

    // Resumen de consulta más reciente
    private ConsultaDTO ultimaConsulta;

    // Campos calculados
    private Integer totalConsultas;
    private Integer totalVacunas;
    private Integer totalExamenes;
    private Boolean tieneAlergias;
    private Boolean tieneCondicionesCronicas;

    // Metadata
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
