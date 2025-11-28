package com.veterinaria.application.dto.appointments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para representar un TipoServicio en las respuestas de la API.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoServicioDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Integer duracionEstimada;
    private Double precioBase;
    private String categoria;
    private Boolean requiereConfirmacion;
    private Boolean estaDisponible;

    // Metadata
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
