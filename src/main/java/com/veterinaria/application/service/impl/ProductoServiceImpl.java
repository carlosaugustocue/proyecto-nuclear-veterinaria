package com.veterinaria.application.service.impl;

import com.veterinaria.application.dto.inventory.CreateProductoRequest;
import com.veterinaria.application.dto.inventory.ProductoDTO;
import com.veterinaria.application.dto.inventory.UpdateProductoRequest;
import com.veterinaria.application.mapper.ProductoMapper;
import com.veterinaria.application.repository.ProductoRepository;
import com.veterinaria.application.service.ProductoService;
import com.veterinaria.domain.entity.inventory.Producto;
import com.veterinaria.domain.enums.CategoriaProducto;
import com.veterinaria.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementación del servicio de Productos.
 *
 * @author Sistema Veterinaria
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override
    public ProductoDTO crear(CreateProductoRequest request) {
        log.info("Creando nuevo producto con código: {}", request.codigo());

        // Validar código único
        if (productoRepository.existsByCodigo(request.codigo())) {
            throw new IllegalArgumentException("Ya existe un producto con el código: " + request.codigo());
        }

        Producto producto = productoMapper.toEntity(request);
        Producto saved = productoRepository.save(producto);

        log.info("Producto creado exitosamente: ID {}", saved.getId());
        return productoMapper.toDTO(saved);
    }

    @Override
    public ProductoDTO actualizar(Long id, UpdateProductoRequest request) {
        log.info("Actualizando producto ID: {}", id);

        Producto producto = encontrarProducto(id);
        productoMapper.updateEntityFromDTO(request, producto);
        Producto updated = productoRepository.save(producto);

        log.info("Producto actualizado exitosamente: ID {}", id);
        return productoMapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoDTO buscarPorId(Long id) {
        log.debug("Buscando producto por ID: {}", id);
        Producto producto = encontrarProducto(id);
        return productoMapper.toDTO(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoDTO buscarPorCodigo(String codigo) {
        log.debug("Buscando producto por código: {}", codigo);
        Producto producto = productoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto no encontrado con código: " + codigo));
        return productoMapper.toDTO(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> listarTodos() {
        log.debug("Listando todos los productos");
        List<Producto> productos = productoRepository.findAllActive();
        return productoMapper.toDTOList(productos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> listarPorCategoria(CategoriaProducto categoria) {
        log.debug("Listando productos por categoría: {}", categoria);
        List<Producto> productos = productoRepository.findByCategoria(categoria);
        return productoMapper.toDTOList(productos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> listarConStockBajo() {
        log.debug("Listando productos con stock bajo");
        List<Producto> productos = productoRepository.findProductosConStockBajo();
        return productoMapper.toDTOList(productos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> listarPorVencer() {
        log.debug("Listando productos por vencer");
        LocalDate hoy = LocalDate.now();
        LocalDate fechaLimite = hoy.plusDays(30);
        List<Producto> productos = productoRepository.findProductosPorVencer(hoy, fechaLimite);
        return productoMapper.toDTOList(productos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> listarVencidos() {
        log.debug("Listando productos vencidos");
        List<Producto> productos = productoRepository.findProductosVencidos(LocalDate.now());
        return productoMapper.toDTOList(productos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> buscarPorNombre(String nombre) {
        log.debug("Buscando productos por nombre: {}", nombre);
        List<Producto> productos = productoRepository.findByNombreContaining(nombre);
        return productoMapper.toDTOList(productos);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando producto ID: {}", id);
        Producto producto = encontrarProducto(id);
        producto.setIsActive(false);
        productoRepository.save(producto);
        log.info("Producto eliminado exitosamente: ID {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Double calcularValorTotal() {
        log.debug("Calculando valor total del inventario");
        Double valor = productoRepository.calcularValorTotalInventario();
        return valor != null ? valor : 0.0;
    }

    private Producto encontrarProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto no encontrado con ID: " + id));
    }
}
