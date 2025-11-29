package com.veterinaria.presentation.controller;

import com.veterinaria.application.dto.billing.*;
import com.veterinaria.application.service.FacturaService;
import com.veterinaria.domain.enums.EstadoFactura;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador REST para gestión de Facturas.
 * Maneja endpoints para CRUD y operaciones de facturación.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/facturas")
@RequiredArgsConstructor
public class FacturaController {

    private final FacturaService facturaService;

    /**
     * Crear nueva factura
     * POST /api/v1/facturas
     */
    @PostMapping
    public ResponseEntity<FacturaDTO> crear(@Valid @RequestBody CreateFacturaRequest request) {
        log.info("REST request to create Factura for cliente: {}", request.getClienteId());
        FacturaDTO created = facturaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Actualizar factura existente
     * PUT /api/v1/facturas/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<FacturaDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UpdateFacturaRequest request) {
        log.info("REST request to update Factura: {}", id);
        FacturaDTO updated = facturaService.actualizar(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Listar todas las facturas activas
     * GET /api/v1/facturas
     */
    @GetMapping
    public ResponseEntity<List<FacturaDTO>> listarTodas() {
        log.debug("REST request to get all Facturas");
        List<FacturaDTO> facturas = facturaService.listarTodas();
        return ResponseEntity.ok(facturas);
    }

    /**
     * Obtener factura por ID
     * GET /api/v1/facturas/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<FacturaDTO> obtenerPorId(@PathVariable Long id) {
        log.debug("REST request to get Factura: {}", id);
        FacturaDTO factura = facturaService.obtenerPorId(id);
        return ResponseEntity.ok(factura);
    }

    /**
     * Obtener factura por número
     * GET /api/v1/facturas/numero/{numeroFactura}
     */
    @GetMapping("/numero/{numeroFactura}")
    public ResponseEntity<FacturaDTO> obtenerPorNumero(@PathVariable String numeroFactura) {
        log.debug("REST request to get Factura by number: {}", numeroFactura);
        FacturaDTO factura = facturaService.obtenerPorNumero(numeroFactura);
        return ResponseEntity.ok(factura);
    }

    /**
     * Listar facturas por cliente
     * GET /api/v1/facturas/cliente/{clienteId}
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<FacturaDTO>> listarPorCliente(@PathVariable Long clienteId) {
        log.debug("REST request to get Facturas by cliente: {}", clienteId);
        List<FacturaDTO> facturas = facturaService.listarPorCliente(clienteId);
        return ResponseEntity.ok(facturas);
    }

    /**
     * Listar facturas por estado
     * GET /api/v1/facturas/estado/{estado}
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<FacturaDTO>> listarPorEstado(@PathVariable EstadoFactura estado) {
        log.debug("REST request to get Facturas by estado: {}", estado);
        List<FacturaDTO> facturas = facturaService.listarPorEstado(estado);
        return ResponseEntity.ok(facturas);
    }

    /**
     * Listar facturas por rango de fechas
     * GET /api/v1/facturas/fechas?fechaInicio=2024-01-01&fechaFin=2024-12-31
     */
    @GetMapping("/fechas")
    public ResponseEntity<List<FacturaDTO>> listarPorFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        log.debug("REST request to get Facturas between {} and {}", fechaInicio, fechaFin);
        List<FacturaDTO> facturas = facturaService.listarPorFechas(fechaInicio, fechaFin);
        return ResponseEntity.ok(facturas);
    }

    /**
     * Listar facturas vencidas
     * GET /api/v1/facturas/vencidas
     */
    @GetMapping("/vencidas")
    public ResponseEntity<List<FacturaDTO>> listarVencidas() {
        log.debug("REST request to get Facturas vencidas");
        List<FacturaDTO> facturas = facturaService.listarVencidas();
        return ResponseEntity.ok(facturas);
    }

    /**
     * Listar facturas pendientes
     * GET /api/v1/facturas/pendientes
     */
    @GetMapping("/pendientes")
    public ResponseEntity<List<FacturaDTO>> listarPendientes() {
        log.debug("REST request to get Facturas pendientes");
        List<FacturaDTO> facturas = facturaService.listarPendientes();
        return ResponseEntity.ok(facturas);
    }

    /**
     * Listar facturas con saldo pendiente
     * GET /api/v1/facturas/con-saldo-pendiente
     */
    @GetMapping("/con-saldo-pendiente")
    public ResponseEntity<List<FacturaDTO>> listarConSaldoPendiente() {
        log.debug("REST request to get Facturas con saldo pendiente");
        List<FacturaDTO> facturas = facturaService.listarConSaldoPendiente();
        return ResponseEntity.ok(facturas);
    }

    /**
     * Agregar detalle (item) a factura
     * POST /api/v1/facturas/{facturaId}/detalles
     */
    @PostMapping("/{facturaId}/detalles")
    public ResponseEntity<DetalleFacturaDTO> agregarDetalle(
            @PathVariable Long facturaId,
            @Valid @RequestBody CreateDetalleFacturaRequest request) {
        log.info("REST request to add Detalle to Factura: {}", facturaId);
        DetalleFacturaDTO detalle = facturaService.agregarDetalle(facturaId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalle);
    }

    /**
     * Eliminar detalle de factura
     * DELETE /api/v1/facturas/{facturaId}/detalles/{detalleId}
     */
    @DeleteMapping("/{facturaId}/detalles/{detalleId}")
    public ResponseEntity<Void> eliminarDetalle(
            @PathVariable Long facturaId,
            @PathVariable Long detalleId) {
        log.info("REST request to delete Detalle {} from Factura {}", detalleId, facturaId);
        facturaService.eliminarDetalle(facturaId, detalleId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Aplicar descuento a factura
     * POST /api/v1/facturas/{facturaId}/descuentos
     */
    @PostMapping("/{facturaId}/descuentos")
    public ResponseEntity<DescuentoDTO> aplicarDescuento(
            @PathVariable Long facturaId,
            @Valid @RequestBody CreateDescuentoRequest request) {
        log.info("REST request to apply Descuento to Factura: {}", facturaId);
        DescuentoDTO descuento = facturaService.aplicarDescuento(facturaId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(descuento);
    }

    /**
     * Eliminar descuento de factura
     * DELETE /api/v1/facturas/{facturaId}/descuentos/{descuentoId}
     */
    @DeleteMapping("/{facturaId}/descuentos/{descuentoId}")
    public ResponseEntity<Void> eliminarDescuento(
            @PathVariable Long facturaId,
            @PathVariable Long descuentoId) {
        log.info("REST request to delete Descuento {} from Factura {}", descuentoId, facturaId);
        facturaService.eliminarDescuento(facturaId, descuentoId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Registrar pago para factura
     * POST /api/v1/facturas/{facturaId}/pagos
     */
    @PostMapping("/{facturaId}/pagos")
    public ResponseEntity<PagoDTO> registrarPago(
            @PathVariable Long facturaId,
            @Valid @RequestBody CreatePagoRequest request) {
        log.info("REST request to register Pago for Factura: {}", facturaId);
        PagoDTO pago = facturaService.registrarPago(facturaId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(pago);
    }

    /**
     * Anular factura
     * PATCH /api/v1/facturas/{id}/anular
     */
    @PatchMapping("/{id}/anular")
    public ResponseEntity<FacturaDTO> anular(
            @PathVariable Long id,
            @Valid @RequestBody AnularFacturaRequest request) {
        log.info("REST request to anular Factura: {}", id);
        FacturaDTO anulada = facturaService.anular(id, request);
        return ResponseEntity.ok(anulada);
    }

    /**
     * Eliminar factura (soft delete)
     * DELETE /api/v1/facturas/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("REST request to delete Factura: {}", id);
        facturaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
