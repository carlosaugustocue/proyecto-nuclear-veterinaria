package com.veterinaria.application.dto.medical;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para representar una Consulta médica en las respuestas de la API.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaDTO {

    private Long id;
    private LocalDate fechaConsulta;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private String motivo;
    private String anamnesis;
    private String examenFisico;
    private String observaciones;
    private String planTratamiento;
    private String pronostico;
    private Boolean requiereSeguimiento;
    private LocalDate fechaSeguimiento;

    // Información del historial clínico
    private Long historialClinicoId;

    // Información del paciente
    private Long pacienteId;
    private String pacienteNombre;
    private String pacienteEspecie;

    // Información del veterinario
    private Long veterinarioId;
    private String veterinarioNombre;
    private String veterinarioEspecialidad;

    // Información de la cita asociada (si existe)
    private Long citaId;

    // Signos vitales
    private SignosVitalesDTO signosVitales;

    // Diagnósticos y tratamientos
    private List<DiagnosticoDTO> diagnosticos;
    private List<TratamientoDTO> tratamientos;

    // Campos calculados
    private Long duracionMinutos;
    private Boolean estaEnCurso;
    private Boolean estaFinalizada;
    private String resumen;

    // Metadata
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
