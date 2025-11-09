package com.veterinaria.application.service;

import com.veterinaria.application.dto.billing.*;
import com.veterinaria.domain.enums.EstadoFactura;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio para la gestión de Facturas.
 * Define las operaciones de negocio para el módulo de facturación.
 *
 * @author Sistema Veterinaria
 */
public interface FacturaService {

    /**
     * Crea una nueva factura
     * @param request datos para crear la factura
     * @return la factura creada
     */
    FacturaDTO crear(CreateFacturaRequest request);

    /**
     * Actualiza una factura existente
     * @param id ID de la factura
     * @param request datos para actualizar
     * @return la factura actualizada
     */
    FacturaDTO actualizar(Long id, UpdateFacturaRequest request);

    /**
     * Obtiene una factura por su ID
     * @param id ID de la factura
     * @return la factura
     */
    FacturaDTO obtenerPorId(Long id);

    /**
     * Obtiene una factura por su número
     * @param numeroFactura número de la factura
     * @return la factura
     */
    FacturaDTO obtenerPorNumero(String numeroFactura);

    /**
     * Lista todas las facturas de un cliente
     * @param clienteId ID del cliente
     * @return lista de facturas
     */
    List<FacturaDTO> listarPorCliente(Long clienteId);

    /**
     * Lista facturas por estado
     * @param estado estado de la factura
     * @return lista de facturas
     */
    List<FacturaDTO> listarPorEstado(EstadoFactura estado);

    /**
     * Lista facturas por rango de fechas
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de facturas
     */
    List<FacturaDTO> listarPorFechas(LocalDate fechaInicio, LocalDate fechaFin);

    /**
     * Lista facturas vencidas
     * @return lista de facturas vencidas
     */
    List<FacturaDTO> listarVencidas();

    /**
     * Lista facturas pendientes de pago
     * @return lista de facturas pendientes
     */
    List<FacturaDTO> listarPendientes();

    /**
     * Lista facturas con saldo pendiente
     * @return lista de facturas con saldo
     */
    List<FacturaDTO> listarConSaldoPendiente();

    /**
     * Agrega un detalle (item) a la factura
     * @param facturaId ID de la factura
     * @param request datos del detalle
     * @return el detalle agregado
     */
    DetalleFacturaDTO agregarDetalle(Long facturaId, CreateDetalleFacturaRequest request);

    /**
     * Elimina un detalle de la factura
     * @param facturaId ID de la factura
     * @param detalleId ID del detalle
     */
    void eliminarDetalle(Long facturaId, Long detalleId);

    /**
     * Aplica un descuento a la factura
     * @param facturaId ID de la factura
     * @param request datos del descuento
     * @return el descuento aplicado
     */
    DescuentoDTO aplicarDescuento(Long facturaId, CreateDescuentoRequest request);

    /**
     * Elimina un descuento de la factura
     * @param facturaId ID de la factura
     * @param descuentoId ID del descuento
     */
    void eliminarDescuento(Long facturaId, Long descuentoId);

    /**
     * Registra un pago para la factura
     * @param facturaId ID de la factura
     * @param request datos del pago
     * @return el pago registrado
     */
    PagoDTO registrarPago(Long facturaId, CreatePagoRequest request);

    /**
     * Anula una factura
     * @param id ID de la factura
     * @param request motivo de anulación
     * @return la factura anulada
     */
    FacturaDTO anular(Long id, AnularFacturaRequest request);

    /**
     * Marca facturas vencidas como VENCIDA
     * (Método para tarea programada)
     */
    void marcarFacturasVencidas();

    /**
     * Elimina una factura (soft delete)
     * @param id ID de la factura
     */
    void eliminar(Long id);
}
