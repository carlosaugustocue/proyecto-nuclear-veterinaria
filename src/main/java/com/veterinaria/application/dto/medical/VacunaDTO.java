package com.veterinaria.application.dto.medical;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO para representar una Vacuna en las respuestas de la API.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacunaDTO {

    private Long id;
    private String nombre;
    private String tipoVacuna;
    private LocalDate fechaAplicacion;
    private LocalDate fechaProximaDosis;
    private String lote;
    private String laboratorio;
    private String dosis;
    private String viaAdministracion;
    private Integer numeroDosis;
    private Integer intervaloDias;
    private Boolean esRefuerzo;
    private String observaciones;
    private String reaccionesAdversas;
    private Double pesoAplicacion;
    private Boolean serieCompleta;

    // Información del historial clínico
    private Long historialClinicoId;

    // Información del paciente
    private Long pacienteId;
    private String pacienteNombre;

    // Información del veterinario
    private Long veterinarioId;
    private String veterinarioNombre;

    // Campos calculados
    private Boolean esObligatoria;
    private Boolean esAntirrábica;
    private Boolean refuerzoVencido;
    private Boolean refuerzoProximo;
    private Boolean tuvoReacciones;
    private String estado;
    private String resumen;

    // Metadata
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
