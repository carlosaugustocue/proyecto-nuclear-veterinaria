package com.veterinaria.presentation.controller;

import com.veterinaria.application.dto.inventory.CreateMovimientoRequest;
import com.veterinaria.application.dto.inventory.MovimientoInventarioDTO;
import com.veterinaria.application.service.MovimientoInventarioService;
import com.veterinaria.domain.enums.TipoMovimiento;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador REST para gestión de Movimientos de Inventario.
 *
 * @author Sistema Veterinaria
 */
@RestController
@RequestMapping("/api/v1/movimientos-inventario")
@RequiredArgsConstructor
public class MovimientoInventarioController {

    private final MovimientoInventarioService movimientoService;

    /**
     * Registra un nuevo movimiento de inventario
     */
    @PostMapping
    public ResponseEntity<MovimientoInventarioDTO> registrar(
            @Valid @RequestBody CreateMovimientoRequest request) {
        MovimientoInventarioDTO movimiento = movimientoService.registrarMovimiento(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimiento);
    }

    /**
     * Busca un movimiento por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<MovimientoInventarioDTO> buscarPorId(@PathVariable Long id) {
        MovimientoInventarioDTO movimiento = movimientoService.buscarPorId(id);
        return ResponseEntity.ok(movimiento);
    }

    /**
     * Lista todos los movimientos
     */
    @GetMapping
    public ResponseEntity<List<MovimientoInventarioDTO>> listarTodos() {
        List<MovimientoInventarioDTO> movimientos = movimientoService.listarTodos();
        return ResponseEntity.ok(movimientos);
    }

    /**
     * Lista movimientos por producto
     */
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<MovimientoInventarioDTO>> listarPorProducto(
            @PathVariable Long productoId) {
        List<MovimientoInventarioDTO> movimientos =
                movimientoService.listarPorProducto(productoId);
        return ResponseEntity.ok(movimientos);
    }

    /**
     * Lista movimientos por tipo
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<MovimientoInventarioDTO>> listarPorTipo(
            @PathVariable TipoMovimiento tipo) {
        List<MovimientoInventarioDTO> movimientos = movimientoService.listarPorTipo(tipo);
        return ResponseEntity.ok(movimientos);
    }

    /**
     * Lista movimientos en un rango de fechas
     */
    @GetMapping("/fecha-rango")
    public ResponseEntity<List<MovimientoInventarioDTO>> listarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<MovimientoInventarioDTO> movimientos =
                movimientoService.listarPorRangoFechas(fechaInicio, fechaFin);
        return ResponseEntity.ok(movimientos);
    }

    /**
     * Lista movimientos asociados a tratamientos
     */
    @GetMapping("/tratamientos")
    public ResponseEntity<List<MovimientoInventarioDTO>> listarMovimientosDeTratamientos() {
        List<MovimientoInventarioDTO> movimientos =
                movimientoService.listarMovimientosDeTratamientos();
        return ResponseEntity.ok(movimientos);
    }

    /**
     * Lista movimientos de un tratamiento específico
     */
    @GetMapping("/tratamiento/{tratamientoId}")
    public ResponseEntity<List<MovimientoInventarioDTO>> listarPorTratamiento(
            @PathVariable Long tratamientoId) {
        List<MovimientoInventarioDTO> movimientos =
                movimientoService.listarPorTratamiento(tratamientoId);
        return ResponseEntity.ok(movimientos);
    }

    /**
     * Lista últimos movimientos
     */
    @GetMapping("/ultimos")
    public ResponseEntity<List<MovimientoInventarioDTO>> listarUltimos() {
        List<MovimientoInventarioDTO> movimientos = movimientoService.listarUltimos();
        return ResponseEntity.ok(movimientos);
    }
}
