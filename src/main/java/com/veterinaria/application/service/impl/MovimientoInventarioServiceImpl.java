package com.veterinaria.application.service.impl;

import com.veterinaria.application.dto.inventory.CreateMovimientoRequest;
import com.veterinaria.application.dto.inventory.MovimientoInventarioDTO;
import com.veterinaria.application.mapper.MovimientoInventarioMapper;
import com.veterinaria.application.repository.MovimientoInventarioRepository;
import com.veterinaria.application.repository.ProductoRepository;
import com.veterinaria.application.repository.TratamientoRepository;
import com.veterinaria.application.repository.UsuarioRepository;
import com.veterinaria.application.service.MovimientoInventarioService;
import com.veterinaria.domain.entity.inventory.MovimientoInventario;
import com.veterinaria.domain.entity.inventory.Producto;
import com.veterinaria.domain.entity.medical.Tratamiento;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.enums.TipoMovimiento;
import com.veterinaria.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación del servicio de Movimientos de Inventario.
 *
 * @author Sistema Veterinaria
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MovimientoInventarioServiceImpl implements MovimientoInventarioService {

    private final MovimientoInventarioRepository movimientoRepository;
    private final ProductoRepository productoRepository;
    private final TratamientoRepository tratamientoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MovimientoInventarioMapper movimientoMapper;

    @Override
    public MovimientoInventarioDTO registrarMovimiento(CreateMovimientoRequest request) {
        log.info("Registrando movimiento de inventario: {} - Producto ID: {}",
                request.tipo(), request.productoId());

        // Buscar producto
        Producto producto = productoRepository.findById(request.productoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto no encontrado con ID: " + request.productoId()));

        // Guardar stock anterior
        Integer stockAnterior = producto.getStockActual();

        // Actualizar stock según tipo de movimiento
        switch (request.tipo()) {
            case ENTRADA -> producto.incrementarStock(request.cantidad());
            case SALIDA -> producto.decrementarStock(request.cantidad());
            case AJUSTE -> {
                // El ajuste puede ser positivo o negativo
                producto.setStockActual(request.cantidad());
            }
        }

        Integer stockPosterior = producto.getStockActual();
        productoRepository.save(producto);

        // Crear movimiento
        MovimientoInventario.MovimientoInventarioBuilder builder = MovimientoInventario.builder()
                .producto(producto)
                .tipo(request.tipo())
                .cantidad(request.cantidad())
                .fechaMovimiento(request.fechaMovimiento() != null ?
                        request.fechaMovimiento() : LocalDateTime.now())
                .costoUnitario(request.costoUnitario())
                .motivo(request.motivo())
                .observaciones(request.observaciones())
                .stockAnterior(stockAnterior)
                .stockPosterior(stockPosterior)
                .numeroDocumento(request.numeroDocumento())
                .usuario(obtenerUsuarioActual());

        // Si el movimiento está asociado a un tratamiento
        if (request.tratamientoId() != null) {
            Tratamiento tratamiento = tratamientoRepository.findById(request.tratamientoId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Tratamiento no encontrado con ID: " + request.tratamientoId()));
            builder.tratamiento(tratamiento);
        }

        MovimientoInventario movimiento = builder.build();
        MovimientoInventario saved = movimientoRepository.save(movimiento);

        log.info("Movimiento registrado exitosamente. Stock anterior: {}, Stock posterior: {}",
                stockAnterior, stockPosterior);

        return movimientoMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public MovimientoInventarioDTO buscarPorId(Long id) {
        log.debug("Buscando movimiento por ID: {}", id);
        MovimientoInventario movimiento = encontrarMovimiento(id);
        return movimientoMapper.toDTO(movimiento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoInventarioDTO> listarTodos() {
        log.debug("Listando todos los movimientos");
        List<MovimientoInventario> movimientos = movimientoRepository.findUltimosMovimientos();
        return movimientoMapper.toDTOList(movimientos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoInventarioDTO> listarPorProducto(Long productoId) {
        log.debug("Listando movimientos del producto ID: {}", productoId);
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto no encontrado con ID: " + productoId));

        List<MovimientoInventario> movimientos = movimientoRepository.findByProducto(producto);
        return movimientoMapper.toDTOList(movimientos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoInventarioDTO> listarPorTipo(TipoMovimiento tipo) {
        log.debug("Listando movimientos por tipo: {}", tipo);
        List<MovimientoInventario> movimientos = movimientoRepository.findByTipo(tipo);
        return movimientoMapper.toDTOList(movimientos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoInventarioDTO> listarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        log.debug("Listando movimientos entre {} y {}", fechaInicio, fechaFin);
        List<MovimientoInventario> movimientos =
                movimientoRepository.findByFechaBetween(fechaInicio, fechaFin);
        return movimientoMapper.toDTOList(movimientos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoInventarioDTO> listarMovimientosDeTratamientos() {
        log.debug("Listando movimientos asociados a tratamientos");
        List<MovimientoInventario> movimientos = movimientoRepository.findMovimientosDeTratamientos();
        return movimientoMapper.toDTOList(movimientos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoInventarioDTO> listarPorTratamiento(Long tratamientoId) {
        log.debug("Listando movimientos del tratamiento ID: {}", tratamientoId);
        List<MovimientoInventario> movimientos = movimientoRepository.findByTratamientoId(tratamientoId);
        return movimientoMapper.toDTOList(movimientos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoInventarioDTO> listarUltimos() {
        log.debug("Listando últimos movimientos");
        List<MovimientoInventario> movimientos = movimientoRepository.findUltimosMovimientos();
        return movimientoMapper.toDTOList(movimientos);
    }

    private MovimientoInventario encontrarMovimiento(Long id) {
        return movimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Movimiento no encontrado con ID: " + id));
    }

    private Usuario obtenerUsuarioActual() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return usuarioRepository.findByUsername(username)
                    .orElse(null);
        } catch (Exception e) {
            log.warn("No se pudo obtener el usuario actual: {}", e.getMessage());
            return null;
        }
    }
}
