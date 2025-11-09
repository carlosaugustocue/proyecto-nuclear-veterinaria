package com.veterinaria.application.dto.medical;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO para representar un Tratamiento médico en las respuestas de la API.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TratamientoDTO {

    private Long id;
    private String tipoTratamiento;
    private String nombre;
    private String descripcion;
    private String dosis;
    private String frecuencia;
    private String viaAdministracion;
    private Integer duracionDias;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String indicaciones;
    private String efectosSecundarios;
    private Double cantidad;
    private String unidad;
    private Boolean activo;
    private Boolean requiereSupervision;

    // Información de la consulta
    private Long consultaId;

    // Campos calculados
    private Boolean esMedicamento;
    private Boolean haFinalizado;
    private Boolean estaVigente;
    private String resumen;

    // Metadata
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
