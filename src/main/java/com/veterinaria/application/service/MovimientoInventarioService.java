package com.veterinaria.application.service;

import com.veterinaria.application.dto.inventory.CreateMovimientoRequest;
import com.veterinaria.application.dto.inventory.MovimientoInventarioDTO;
import com.veterinaria.domain.enums.TipoMovimiento;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio para gestión de Movimientos de Inventario.
 *
 * @author Sistema Veterinaria
 */
public interface MovimientoInventarioService {

    /**
     * Registra un nuevo movimiento de inventario
     * Actualiza automáticamente el stock del producto
     */
    MovimientoInventarioDTO registrarMovimiento(CreateMovimientoRequest request);

    /**
     * Busca un movimiento por ID
     */
    MovimientoInventarioDTO buscarPorId(Long id);

    /**
     * Lista todos los movimientos
     */
    List<MovimientoInventarioDTO> listarTodos();

    /**
     * Lista movimientos por producto
     */
    List<MovimientoInventarioDTO> listarPorProducto(Long productoId);

    /**
     * Lista movimientos por tipo
     */
    List<MovimientoInventarioDTO> listarPorTipo(TipoMovimiento tipo);

    /**
     * Lista movimientos en un rango de fechas
     */
    List<MovimientoInventarioDTO> listarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    /**
     * Lista movimientos asociados a tratamientos
     */
    List<MovimientoInventarioDTO> listarMovimientosDeTratamientos();

    /**
     * Lista movimientos de un tratamiento específico
     */
    List<MovimientoInventarioDTO> listarPorTratamiento(Long tratamientoId);

    /**
     * Lista últimos movimientos
     */
    List<MovimientoInventarioDTO> listarUltimos();
}
