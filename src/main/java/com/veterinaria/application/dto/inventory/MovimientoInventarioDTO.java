package com.veterinaria.application.dto.inventory;

import com.veterinaria.domain.enums.TipoMovimiento;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * DTO para transferir información de Movimiento de Inventario.
 *
 * @author Sistema Veterinaria
 */
@Builder
public record MovimientoInventarioDTO(
        Long id,
        Long productoId,
        String productoNombre,
        String productoCodigo,
        TipoMovimiento tipo,
        Integer cantidad,
        LocalDateTime fechaMovimiento,
        Double costoUnitario,
        Double costoTotal,
        String motivo,
        String observaciones,
        Long usuarioId,
        String usuarioNombre,
        Long tratamientoId,
        Integer stockAnterior,
        Integer stockPosterior,
        String numeroDocumento,
        LocalDateTime createdAt
) {}
