package com.veterinaria.application.dto.inventory;

import com.veterinaria.domain.enums.TipoMovimiento;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * DTO para registrar un nuevo movimiento de inventario.
 *
 * @author Sistema Veterinaria
 */
@Builder
public record CreateMovimientoRequest(
        @NotNull(message = "El producto es obligatorio")
        Long productoId,

        @NotNull(message = "El tipo de movimiento es obligatorio")
        TipoMovimiento tipo,

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad debe ser al menos 1")
        Integer cantidad,

        LocalDateTime fechaMovimiento,

        @DecimalMin(value = "0.0", message = "El costo unitario no puede ser negativo")
        Double costoUnitario,

        @NotBlank(message = "El motivo es obligatorio")
        @Size(max = 500, message = "El motivo no puede exceder 500 caracteres")
        String motivo,

        @Size(max = 1000, message = "Las observaciones no pueden exceder 1000 caracteres")
        String observaciones,

        Long tratamientoId,

        @Size(max = 100, message = "El número de documento no puede exceder 100 caracteres")
        String numeroDocumento
) {}
