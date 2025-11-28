package com.veterinaria.application.dto.appointments;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para actualizar un TipoServicio existente.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTipoServicioRequest {

    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;

    @Min(value = 5, message = "La duración mínima es 5 minutos")
    @Max(value = 480, message = "La duración máxima es 480 minutos (8 horas)")
    private Integer duracionEstimada;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private Double precioBase;

    @Size(max = 50, message = "La categoría no puede exceder 50 caracteres")
    private String categoria;

    private Boolean requiereConfirmacion;
}
