package com.veterinaria.application.service.impl;

import com.veterinaria.application.dto.billing.*;
import com.veterinaria.application.exception.BusinessRuleException;
import com.veterinaria.application.exception.ResourceNotFoundException;
import com.veterinaria.application.mapper.DescuentoMapper;
import com.veterinaria.application.mapper.DetalleFacturaMapper;
import com.veterinaria.application.mapper.FacturaMapper;
import com.veterinaria.application.mapper.PagoMapper;
import com.veterinaria.application.repository.*;
import com.veterinaria.application.service.FacturaService;
import com.veterinaria.application.service.MovimientoInventarioService;
import com.veterinaria.application.service.notification.EmailService;
import com.veterinaria.application.dto.inventory.CreateMovimientoRequest;
import com.veterinaria.domain.entity.appointments.Cita;
import com.veterinaria.domain.entity.appointments.TipoServicio;
import com.veterinaria.domain.entity.billing.*;
import com.veterinaria.domain.entity.inventory.Producto;
import com.veterinaria.domain.entity.patients.Cliente;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.enums.EstadoFactura;
import com.veterinaria.domain.enums.TipoMovimiento;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementación del servicio de Facturas.
 * Contiene toda la lógica de negocio para gestión de facturación.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FacturaServiceImpl implements FacturaService {

    private final FacturaRepository facturaRepository;
    private final DetalleFacturaRepository detalleFacturaRepository;
    private final PagoRepository pagoRepository;
    private final DescuentoRepository descuentoRepository;
    private final ClienteRepository clienteRepository;
    private final CitaRepository citaRepository;
    private final UsuarioRepository usuarioRepository;
    private final TipoServicioRepository tipoServicioRepository;
    private final ProductoRepository productoRepository;
    private final MovimientoInventarioService movimientoInventarioService;
    private final FacturaMapper facturaMapper;
    private final DetalleFacturaMapper detalleFacturaMapper;
    private final PagoMapper pagoMapper;
    private final DescuentoMapper descuentoMapper;
    private final EmailService emailService;

    @Override
    @Transactional
    public FacturaDTO crear(CreateFacturaRequest request) {
        log.info("Creando nueva factura para cliente ID: {}", request.getClienteId());

        // 1. Validar cliente
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente no encontrado con ID: " + request.getClienteId()
                ));

        // 2. Validar usuario emisor
        Usuario usuarioEmisor = usuarioRepository.findById(request.getUsuarioEmisorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario emisor no encontrado con ID: " + request.getUsuarioEmisorId()
                ));

        // 3. Validar cita si se proporciona
        Cita cita = null;
        if (request.getCitaId() != null) {
            cita = citaRepository.findById(request.getCitaId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Cita no encontrada con ID: " + request.getCitaId()
                    ));

            // Verificar que no exista ya una factura para esta cita
            if (facturaRepository.findByCitaId(request.getCitaId()).isPresent()) {
                throw new BusinessRuleException(
                        "Ya existe una factura para la cita ID: " + request.getCitaId()
                );
            }
        }

        // 4. Generar número de factura
        String numeroFactura = generarNumeroFactura();

        // 5. Crear la factura usando mapper
        Factura factura = facturaMapper.toEntity(request);
        factura.setNumeroFactura(numeroFactura);
        factura.setCliente(cliente);
        factura.setCita(cita);
        factura.setUsuarioEmisor(usuarioEmisor);

        // 6. Guardar
        Factura guardada = facturaRepository.save(factura);

        // NOTA: El email se enviará cuando se agregue el primer detalle a la factura
        // para asegurar que la factura tenga información completa antes de notificar al cliente

        log.info("Factura creada exitosamente: {}", guardada.getNumeroFactura());
        return facturaMapper.toDTO(guardada);
    }

    /**
     * Genera un resumen detallado de los detalles de la factura en formato HTML
     */
    private String generarResumenDetalles(Factura factura) {
        StringBuilder resumen = new StringBuilder();
        List<DetalleFactura> detalles = detalleFacturaRepository.findByFacturaId(factura.getId());

        if (detalles == null || detalles.isEmpty()) {
            return "<p>No hay detalles registrados en esta factura.</p>";
        }

        // Crear tabla HTML para los detalles
        resumen.append("<table style='width: 100%; border-collapse: collapse; margin: 10px 0;'>");
        resumen.append("<thead>");
        resumen.append("<tr style='background-color: #f5f5f5;'>");
        resumen.append("<th style='padding: 8px; text-align: left; border-bottom: 2px solid #ddd;'>Cant.</th>");
        resumen.append("<th style='padding: 8px; text-align: left; border-bottom: 2px solid #ddd;'>Descripción</th>");
        resumen.append("<th style='padding: 8px; text-align: right; border-bottom: 2px solid #ddd;'>Precio Unit.</th>");
        resumen.append("<th style='padding: 8px; text-align: right; border-bottom: 2px solid #ddd;'>Subtotal</th>");
        resumen.append("</tr>");
        resumen.append("</thead>");
        resumen.append("<tbody>");

        for (DetalleFactura detalle : detalles) {
            if (detalle.getIsActive()) {
                resumen.append("<tr>");
                resumen.append(String.format("<td style='padding: 8px; border-bottom: 1px solid #eee;'>%d</td>", 
                        detalle.getCantidad()));
                resumen.append(String.format("<td style='padding: 8px; border-bottom: 1px solid #eee;'>%s</td>", 
                        detalle.getDescripcion() != null ? detalle.getDescripcion() : "Sin descripción"));
                resumen.append(String.format("<td style='padding: 8px; text-align: right; border-bottom: 1px solid #eee;'>S/ %.2f</td>", 
                        detalle.getPrecioUnitario() != null ? detalle.getPrecioUnitario() : 0.0));
                resumen.append(String.format("<td style='padding: 8px; text-align: right; border-bottom: 1px solid #eee; font-weight: bold;'>S/ %.2f</td>", 
                        detalle.getTotal() != null ? detalle.getTotal() : 0.0));
                resumen.append("</tr>");
            }
        }

        resumen.append("</tbody>");
        resumen.append("</table>");

        // Agregar resumen financiero si hay información disponible
        if (factura.getSubtotal() != null || factura.getTotalDescuentos() != null || 
            factura.getTotalImpuestos() != null) {
            resumen.append("<div style='margin-top: 15px; padding: 10px; background-color: #f9f9f9; border-radius: 4px;'>");
            resumen.append("<table style='width: 100%; border-collapse: collapse;'>");
            
            if (factura.getSubtotal() != null) {
                resumen.append(String.format(
                    "<tr><td style='padding: 5px; text-align: right;'><strong>Subtotal:</strong></td>" +
                    "<td style='padding: 5px; text-align: right;'>S/ %.2f</td></tr>",
                    factura.getSubtotal()));
            }
            
            if (factura.getTotalDescuentos() != null && factura.getTotalDescuentos() > 0) {
                resumen.append(String.format(
                    "<tr><td style='padding: 5px; text-align: right;'><strong>Descuentos:</strong></td>" +
                    "<td style='padding: 5px; text-align: right; color: #d32f2f;'>-S/ %.2f</td></tr>",
                    factura.getTotalDescuentos()));
            }
            
            if (factura.getTotalImpuestos() != null && factura.getTotalImpuestos() > 0) {
                resumen.append(String.format(
                    "<tr><td style='padding: 5px; text-align: right;'><strong>Impuestos:</strong></td>" +
                    "<td style='padding: 5px; text-align: right;'>S/ %.2f</td></tr>",
                    factura.getTotalImpuestos()));
            }
            
            resumen.append("</table>");
            resumen.append("</div>");
        }

        return resumen.toString();
    }

    @Override
    @Transactional
    public FacturaDTO actualizar(Long id, UpdateFacturaRequest request) {
        log.info("Actualizando factura ID: {}", id);

        Factura factura = facturaRepository.findByIdAndActive(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Factura no encontrada con ID: " + id
                ));

        // Validar que se pueda modificar
        if (!factura.puedeSerModificada()) {
            throw new BusinessRuleException(
                    "No se puede modificar una factura en estado: " + factura.getEstado()
            );
        }

        // Actualizar usando mapper
        facturaMapper.updateEntityFromDTO(request, factura);

        Factura actualizada = facturaRepository.save(factura);

        log.info("Factura actualizada exitosamente: {}", actualizada.getNumeroFactura());
        return facturaMapper.toDTO(actualizada);
    }

    @Override
    public FacturaDTO obtenerPorId(Long id) {
        log.debug("Buscando factura con ID: {}", id);

        Factura factura = facturaRepository.findByIdAndActive(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Factura no encontrada con ID: " + id
                ));

        return facturaMapper.toDTO(factura);
    }

    @Override
    public FacturaDTO obtenerPorNumero(String numeroFactura) {
        log.debug("Buscando factura con número: {}", numeroFactura);

        Factura factura = facturaRepository.findByNumeroFactura(numeroFactura)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Factura no encontrada con número: " + numeroFactura
                ));

        return facturaMapper.toDTO(factura);
    }

    @Override
    public List<FacturaDTO> listarTodas() {
        log.debug("Listando todas las facturas activas");

        List<Factura> facturas = facturaRepository.findAllActive();
        return facturaMapper.toDTOList(facturas);
    }

    @Override
    public List<FacturaDTO> listarPorCliente(Long clienteId) {
        log.debug("Listando facturas del cliente ID: {}", clienteId);

        List<Factura> facturas = facturaRepository.findByClienteId(clienteId);
        return facturaMapper.toDTOList(facturas);
    }

    @Override
    public List<FacturaDTO> listarPorEstado(EstadoFactura estado) {
        log.debug("Listando facturas con estado: {}", estado);

        List<Factura> facturas = facturaRepository.findByEstado(estado);
        return facturaMapper.toDTOList(facturas);
    }

    @Override
    public List<FacturaDTO> listarPorFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        log.debug("Listando facturas entre {} y {}", fechaInicio, fechaFin);

        List<Factura> facturas = facturaRepository.findByFechaEmisionBetween(fechaInicio, fechaFin);
        return facturaMapper.toDTOList(facturas);
    }

    @Override
    public List<FacturaDTO> listarVencidas() {
        log.debug("Listando facturas vencidas");

        List<Factura> facturas = facturaRepository.findFacturasVencidas(LocalDate.now());
        return facturaMapper.toDTOList(facturas);
    }

    @Override
    public List<FacturaDTO> listarPendientes() {
        log.debug("Listando facturas pendientes");

        List<Factura> facturas = facturaRepository.findFacturasPendientes();
        return facturaMapper.toDTOList(facturas);
    }

    @Override
    public List<FacturaDTO> listarConSaldoPendiente() {
        log.debug("Listando facturas con saldo pendiente");

        List<Factura> facturas = facturaRepository.findFacturasConSaldoPendiente();
        return facturaMapper.toDTOList(facturas);
    }

    @Override
    @Transactional
    public DetalleFacturaDTO agregarDetalle(Long facturaId, CreateDetalleFacturaRequest request) {
        log.info("Agregando detalle a factura ID: {}", facturaId);

        // 1. Obtener factura
        Factura factura = facturaRepository.findByIdAndActive(facturaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Factura no encontrada con ID: " + facturaId
                ));

        // 2. Validar que se pueda modificar
        if (!factura.puedeSerModificada()) {
            throw new BusinessRuleException(
                    "No se pueden agregar detalles a una factura en estado: " + factura.getEstado()
                );
        }

        // 3. Validar tipo de servicio si se proporciona
        TipoServicio tipoServicio = null;
        if (request.getTipoServicioId() != null) {
            tipoServicio = tipoServicioRepository.findById(request.getTipoServicioId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Tipo de servicio no encontrado con ID: " + request.getTipoServicioId()
                    ));
        }

        // 3.1. Validar producto del inventario si se proporciona
        com.veterinaria.domain.entity.inventory.Producto producto = null;
        if (request.getProductoId() != null) {
            producto = productoRepository.findById(request.getProductoId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Producto no encontrado con ID: " + request.getProductoId()
                    ));
            
            // Validar que el producto tenga stock suficiente
            if (producto.getStockActual() < request.getCantidad()) {
                throw new BusinessRuleException(
                        String.format("Stock insuficiente. Disponible: %d, Solicitado: %d",
                                producto.getStockActual(), request.getCantidad())
                );
            }
            
            // Si no se proporciona precio, usar el precio de venta del producto
            if (request.getPrecioUnitario() == null || request.getPrecioUnitario() == 0) {
                request.setPrecioUnitario(producto.getPrecioVenta());
            }
            
            // Si no se proporciona descripción, usar el nombre del producto
            if (request.getDescripcion() == null || request.getDescripcion().trim().isEmpty()) {
                request.setDescripcion(producto.getNombre());
            }
        }

        // 4. Crear detalle usando mapper
        DetalleFactura detalle = detalleFacturaMapper.toEntity(request);
        detalle.setTipoServicio(tipoServicio);
        detalle.setProducto(producto);

        // 5. Calcular subtotal y total del detalle
        calcularTotalesDetalle(detalle);

        // 6. Agregar detalle a la factura
        factura.agregarDetalle(detalle);

        // 7. Recalcular totales de la factura
        recalcularTotalesFactura(factura);

        // 8. Guardar
        detalleFacturaRepository.save(detalle);
        facturaRepository.save(factura);

        // NOTA: El email se enviará cuando se registre el primer pago para asegurar
        // que la factura tenga todos sus detalles (servicios y productos) antes de notificar

        log.info("Detalle agregado exitosamente a factura: {}", factura.getNumeroFactura());
        return detalleFacturaMapper.toDTO(detalle);
    }

    @Override
    @Transactional
    public void eliminarDetalle(Long facturaId, Long detalleId) {
        log.info("Eliminando detalle ID: {} de factura ID: {}", detalleId, facturaId);

        // 1. Obtener factura
        Factura factura = facturaRepository.findByIdAndActive(facturaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Factura no encontrada con ID: " + facturaId
                ));

        // 2. Validar que se pueda modificar
        if (!factura.puedeSerModificada()) {
            throw new BusinessRuleException(
                    "No se pueden eliminar detalles de una factura en estado: " + factura.getEstado()
                );
        }

        // 3. Obtener detalle
        DetalleFactura detalle = detalleFacturaRepository.findById(detalleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Detalle no encontrado con ID: " + detalleId
                ));

        // 4. Validar que el detalle pertenece a la factura
        if (!detalle.getFactura().getId().equals(facturaId)) {
            throw new BusinessRuleException(
                    "El detalle no pertenece a la factura especificada"
            );
        }

        // 5. Eliminar (soft delete)
        detalle.setIsActive(false);
        detalleFacturaRepository.save(detalle);

        // 6. Recalcular totales de la factura
        recalcularTotalesFactura(factura);
        facturaRepository.save(factura);

        log.info("Detalle eliminado exitosamente");
    }

    @Override
    @Transactional
    public DescuentoDTO aplicarDescuento(Long facturaId, CreateDescuentoRequest request) {
        log.info("Aplicando descuento a factura ID: {}", facturaId);

        // 1. Obtener factura
        Factura factura = facturaRepository.findByIdAndActive(facturaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Factura no encontrada con ID: " + facturaId
                ));

        // 2. Validar que se pueda modificar
        if (!factura.puedeSerModificada()) {
            throw new BusinessRuleException(
                    "No se pueden aplicar descuentos a una factura en estado: " + factura.getEstado()
                );
        }

        // 3. Validar código único si se proporciona
        if (request.getCodigo() != null && descuentoRepository.existsByCodigo(request.getCodigo())) {
            throw new BusinessRuleException(
                    "Ya existe un descuento con el código: " + request.getCodigo()
            );
        }

        // 4. Crear descuento usando mapper
        Descuento descuento = descuentoMapper.toEntity(request);

        // 5. Calcular monto del descuento usando Strategy Pattern del enum
        Double montoDescuento = request.getTipo().calcularDescuento(
                request.getValor(),
                factura.getSubtotal()
        );
        descuento.setMonto(montoDescuento);

        // 6. Agregar descuento a la factura
        factura.agregarDescuento(descuento);

        // 7. Recalcular totales de la factura
        recalcularTotalesFactura(factura);

        // 8. Guardar
        descuentoRepository.save(descuento);
        facturaRepository.save(factura);

        log.info("Descuento aplicado exitosamente: {} por {}", request.getTipo(), montoDescuento);
        return descuentoMapper.toDTO(descuento);
    }

    @Override
    @Transactional
    public void eliminarDescuento(Long facturaId, Long descuentoId) {
        log.info("Eliminando descuento ID: {} de factura ID: {}", descuentoId, facturaId);

        // 1. Obtener factura
        Factura factura = facturaRepository.findByIdAndActive(facturaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Factura no encontrada con ID: " + facturaId
                ));

        // 2. Validar que se pueda modificar
        if (!factura.puedeSerModificada()) {
            throw new BusinessRuleException(
                    "No se pueden eliminar descuentos de una factura en estado: " + factura.getEstado()
            );
        }

        // 3. Obtener descuento
        Descuento descuento = descuentoRepository.findById(descuentoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Descuento no encontrado con ID: " + descuentoId
                ));

        // 4. Validar que el descuento pertenece a la factura
        if (!descuento.getFactura().getId().equals(facturaId)) {
            throw new BusinessRuleException(
                    "El descuento no pertenece a la factura especificada"
            );
        }

        // 5. Eliminar (soft delete)
        descuento.setIsActive(false);
        descuentoRepository.save(descuento);

        // 6. Recalcular totales de la factura
        recalcularTotalesFactura(factura);
        facturaRepository.save(factura);

        log.info("Descuento eliminado exitosamente");
    }

    @Override
    @Transactional
    public PagoDTO registrarPago(Long facturaId, CreatePagoRequest request) {
        log.info("Registrando pago de {} para factura ID: {}", request.getMonto(), facturaId);

        // 1. Obtener factura
        Factura factura = facturaRepository.findByIdAndActive(facturaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Factura no encontrada con ID: " + facturaId
                ));

        // 2. Validar que se puedan recibir pagos
        if (!factura.puedeRecibirPagos()) {
            throw new BusinessRuleException(
                    "La factura no puede recibir pagos en estado: " + factura.getEstado()
            );
        }

        // 3. Validar que el monto no exceda el saldo pendiente
        if (request.getMonto() > factura.getSaldoPendiente()) {
            throw new BusinessRuleException(
                    String.format("El monto del pago (%.2f) excede el saldo pendiente (%.2f)",
                            request.getMonto(), factura.getSaldoPendiente())
            );
        }

        // 4. Validar usuario
        Usuario usuarioRegistro = usuarioRepository.findById(request.getUsuarioRegistroId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario no encontrado con ID: " + request.getUsuarioRegistroId()
                ));

        // 5. Crear pago usando mapper
        Pago pago = pagoMapper.toEntity(request);
        pago.setUsuarioRegistro(usuarioRegistro);

        // 6. Calcular comisión según el método de pago
        Double comision = request.getMetodoPago().getComision() * request.getMonto();
        Double montoNeto = request.getMonto() - comision;
        pago.setComision(comision);
        pago.setMontoNeto(montoNeto);

        // 7. Agregar pago a la factura
        factura.agregarPago(pago);

        // 8. Actualizar totales de pago y estado de la factura
        actualizarEstadoFacturaPorPagos(factura);

        // 9. Guardar
        pagoRepository.save(pago);
        facturaRepository.save(factura);

        // 10. Si es el primer pago registrado, enviar email de notificación con la factura completa
        long cantidadPagos = pagoRepository.findByFacturaId(facturaId)
                .stream()
                .filter(p -> p.getIsActive())
                .count();
        
        if (cantidadPagos == 1) {
            // Es el primer pago, enviar email con la factura completa (incluyendo todos los detalles: servicios y productos)
            try {
                Cliente cliente = factura.getCliente();
                if (cliente != null && cliente.getEmail() != null && !cliente.getEmail().trim().isEmpty()) {
                    // Recargar la factura para obtener los totales actualizados
                    Factura facturaActualizada = facturaRepository.findById(facturaId)
                            .orElse(factura);
                    
                    String detallesHtml = generarResumenDetalles(facturaActualizada);
                    emailService.notificarFacturaGenerada(
                            cliente.getEmail(),
                            cliente.getNombreCompleto(),
                            facturaActualizada.getNumeroFactura(),
                            facturaActualizada.getFechaEmision(),
                            facturaActualizada.getSubtotal() != null ? facturaActualizada.getSubtotal() : 0.0,
                            facturaActualizada.getTotalDescuentos() != null ? facturaActualizada.getTotalDescuentos() : 0.0,
                            facturaActualizada.getTotalImpuestos() != null ? facturaActualizada.getTotalImpuestos() : 0.0,
                            facturaActualizada.getTotal() != null ? facturaActualizada.getTotal() : 0.0,
                            detallesHtml
                    );
                    log.info("Email de factura enviado a: {} (primer pago registrado - incluye todos los detalles)", cliente.getEmail());
                } else {
                    log.warn("No se puede enviar email de factura: cliente sin email válido");
                }
            } catch (Exception e) {
                log.error("Error al enviar email de factura después de registrar primer pago: {}", e.getMessage(), e);
                // No interrumpir el flujo si falla el email
            }
        }

        // 11. Si la factura quedó completamente pagada, descontar productos del inventario
        if (factura.getSaldoPendiente() == 0.0 && factura.getEstado() == EstadoFactura.PAGADA) {
            descontarProductosDelInventario(factura, usuarioRegistro.getId());
        }

        log.info("Pago registrado exitosamente. Nuevo saldo pendiente: {}", factura.getSaldoPendiente());
        return pagoMapper.toDTO(pago);
    }

    /**
     * Descuenta productos del inventario cuando una factura es pagada completamente
     */
    private void descontarProductosDelInventario(Factura factura, Long usuarioId) {
        log.info("Descontando productos del inventario para factura: {}", factura.getNumeroFactura());
        
        // Obtener todos los detalles de la factura que tienen productos asociados
        List<DetalleFactura> detalles = detalleFacturaRepository.findByFacturaId(factura.getId());
        
        for (DetalleFactura detalle : detalles) {
            if (detalle.getProducto() != null && detalle.getIsActive()) {
                Producto producto = detalle.getProducto();
                Integer cantidad = detalle.getCantidad();
                
                try {
                    // Crear movimiento de salida para el inventario
                    CreateMovimientoRequest movimientoRequest = CreateMovimientoRequest.builder()
                            .productoId(producto.getId())
                            .tipo(TipoMovimiento.SALIDA)
                            .cantidad(cantidad)
                            .motivo(String.format("Venta - Factura %s", factura.getNumeroFactura()))
                            .observaciones(String.format("Producto vendido en factura %s, detalle ID: %d", 
                                    factura.getNumeroFactura(), detalle.getId()))
                            .build();
                    
                    movimientoInventarioService.registrarMovimiento(movimientoRequest);
                    log.info("Producto {} (ID: {}) descontado del inventario. Cantidad: {}", 
                            producto.getNombre(), producto.getId(), cantidad);
                } catch (Exception e) {
                    log.error("Error al descontar producto {} del inventario: {}", 
                            producto.getNombre(), e.getMessage(), e);
                    // No lanzar excepción para no interrumpir el proceso de pago
                    // El movimiento de inventario se puede registrar manualmente después
                }
            }
        }
    }

    @Override
    @Transactional
    public FacturaDTO anular(Long id, AnularFacturaRequest request) {
        log.info("Anulando factura ID: {}", id);

        Factura factura = facturaRepository.findByIdAndActive(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Factura no encontrada con ID: " + id
                ));

        // Validar que no esté ya anulada
        if (factura.estaAnulada()) {
            throw new BusinessRuleException("La factura ya está anulada");
        }

        // Validar que no tenga pagos (o permitir anulación con reembolso)
        if (factura.tienePagos()) {
            throw new BusinessRuleException(
                    "No se puede anular una factura con pagos registrados. " +
                    "Debe gestionar los reembolsos primero."
            );
        }

        // Anular la factura
        factura.setEstado(EstadoFactura.ANULADA);
        factura.setMotivoAnulacion(request.getMotivoAnulacion());
        factura.setFechaAnulacion(LocalDate.now());

        Factura anulada = facturaRepository.save(factura);

        log.info("Factura anulada exitosamente: {}", anulada.getNumeroFactura());
        return facturaMapper.toDTO(anulada);
    }

    @Override
    @Transactional
    public void marcarFacturasVencidas() {
        log.info("Marcando facturas vencidas");

        List<Factura> facturasVencidas = facturaRepository.findFacturasVencidas(LocalDate.now());

        for (Factura factura : facturasVencidas) {
            factura.setEstado(EstadoFactura.VENCIDA);
            facturaRepository.save(factura);
        }

        log.info("Se marcaron {} facturas como vencidas", facturasVencidas.size());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando factura ID: {}", id);

        Factura factura = facturaRepository.findByIdAndActive(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Factura no encontrada con ID: " + id
                ));

        // Validar que se pueda eliminar
        if (factura.tienePagos()) {
            throw new BusinessRuleException(
                    "No se puede eliminar una factura con pagos registrados"
            );
        }

        // Soft delete
        factura.setIsActive(false);
        facturaRepository.save(factura);

        log.info("Factura eliminada (soft delete) exitosamente");
    }

    // ==================== MÉTODOS PRIVADOS DE LÓGICA DE NEGOCIO ====================

    /**
     * Genera un número único de factura en formato FAC-YYYY-NNNNNN
     */
    private String generarNumeroFactura() {
        int year = LocalDate.now().getYear();
        String ultimoNumero = facturaRepository.findUltimoNumeroFacturaDelAnio(year)
                .orElse("FAC-" + year + "-000000");

        // Extraer el número secuencial
        String[] partes = ultimoNumero.split("-");
        int secuencial = Integer.parseInt(partes[2]) + 1;

        // Generar nuevo número
        return String.format("FAC-%d-%06d", year, secuencial);
    }

    /**
     * Calcula el subtotal y total de un detalle de factura
     */
    private void calcularTotalesDetalle(DetalleFactura detalle) {
        // Subtotal = cantidad * precio unitario
        Double subtotal = detalle.getCantidad() * detalle.getPrecioUnitario();
        detalle.setSubtotal(subtotal);

        // Calcular descuento si aplica
        Double descuentoMonto = 0.0;
        if (detalle.getDescuentoPorcentaje() != null && detalle.getDescuentoPorcentaje() > 0) {
            descuentoMonto = subtotal * (detalle.getDescuentoPorcentaje() / 100.0);
        }
        detalle.setDescuentoMonto(descuentoMonto);

        // Total = subtotal - descuento
        Double total = subtotal - descuentoMonto;
        detalle.setTotal(total);
    }

    /**
     * Recalcula todos los totales de una factura
     */
    private void recalcularTotalesFactura(Factura factura) {
        // 1. Calcular subtotal (suma de todos los detalles activos)
        Double subtotal = detalleFacturaRepository.findByFacturaId(factura.getId())
                .stream()
                .filter(d -> d.getIsActive())
                .mapToDouble(DetalleFactura::getTotal)
                .sum();
        factura.setSubtotal(subtotal);

        // 2. Calcular total de descuentos aplicados
        Double totalDescuentos = descuentoRepository.findByFacturaId(factura.getId())
                .stream()
                .filter(d -> d.getIsActive() && d.estaActivo())
                .mapToDouble(Descuento::getMonto)
                .sum();
        factura.setTotalDescuentos(totalDescuentos);

        // 3. Por ahora no calculamos impuestos (IGV), pero se puede agregar
        // Double totalImpuestos = (subtotal - totalDescuentos) * 0.18; // 18% IGV
        factura.setTotalImpuestos(0.0);

        // 4. Total final = subtotal - descuentos + impuestos
        Double total = subtotal - totalDescuentos + factura.getTotalImpuestos();
        factura.setTotal(total);

        // 5. Recalcular saldo pendiente
        Double totalPagado = factura.getTotalPagado() != null ? factura.getTotalPagado() : 0.0;
        Double saldoPendiente = total - totalPagado;
        factura.setSaldoPendiente(saldoPendiente);
    }

    /**
     * Actualiza el estado de una factura según los pagos recibidos
     */
    private void actualizarEstadoFacturaPorPagos(Factura factura) {
        // 1. Calcular total pagado (suma de todos los pagos activos)
        Double totalPagado = pagoRepository.findByFacturaId(factura.getId())
                .stream()
                .filter(p -> p.getIsActive())
                .mapToDouble(Pago::getMonto)
                .sum();
        factura.setTotalPagado(totalPagado);

        // 2. Calcular saldo pendiente
        Double saldoPendiente = factura.getTotal() - totalPagado;
        factura.setSaldoPendiente(saldoPendiente);

        // 3. Actualizar estado según el saldo
        if (saldoPendiente <= 0.01) { // Considerar 0.01 como margen de redondeo
            factura.setEstado(EstadoFactura.PAGADA);
            factura.setSaldoPendiente(0.0);
        } else if (totalPagado > 0) {
            factura.setEstado(EstadoFactura.PARCIAL);
        } else {
            factura.setEstado(EstadoFactura.PENDIENTE);
        }
    }
}
