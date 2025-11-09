package com.veterinaria.application.dto.medical;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO para representar un Examen médico en las respuestas de la API.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamenDTO {

    private Long id;
    private String tipoExamen;
    private String nombre;
    private String descripcion;
    private LocalDate fechaSolicitud;
    private LocalDate fechaRealizacion;
    private LocalDateTime fechaResultados;
    private String estado;
    private String resultados;
    private String valoresReferencia;
    private String interpretacion;
    private String hallazgos;
    private String archivoRuta;
    private String archivoTipo;
    private String laboratorioExterno;
    private String referenciaExterna;
    private Double costo;
    private String notas;
    private Boolean requiereAyuno;
    private Boolean esUrgente;
    private Boolean resultadoAnormal;

    // Información del historial clínico
    private Long historialClinicoId;

    // Información del paciente
    private Long pacienteId;
    private String pacienteNombre;

    // Información del veterinario solicitante
    private Long veterinarioSolicitanteId;
    private String veterinarioSolicitanteNombre;

    // Información de la consulta asociada
    private Long consultaId;

    // Campos calculados
    private Boolean estaPendiente;
    private Boolean estaCompletado;
    private Boolean esLaboratorio;
    private Boolean esImagenologia;
    private Boolean tieneArchivo;
    private Boolean esExterno;
    private Long diasDesdeSolicitud;
    private String estadoDescriptivo;
    private String resumen;

    // Metadata
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
