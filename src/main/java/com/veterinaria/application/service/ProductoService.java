package com.veterinaria.application.service;

import com.veterinaria.application.dto.inventory.CreateProductoRequest;
import com.veterinaria.application.dto.inventory.ProductoDTO;
import com.veterinaria.application.dto.inventory.UpdateProductoRequest;
import com.veterinaria.domain.enums.CategoriaProducto;

import java.util.List;

/**
 * Servicio para gestión de Productos.
 *
 * @author Sistema Veterinaria
 */
public interface ProductoService {

    /**
     * Crea un nuevo producto
     */
    ProductoDTO crear(CreateProductoRequest request);

    /**
     * Actualiza un producto existente
     */
    ProductoDTO actualizar(Long id, UpdateProductoRequest request);

    /**
     * Busca un producto por ID
     */
    ProductoDTO buscarPorId(Long id);

    /**
     * Busca un producto por código
     */
    ProductoDTO buscarPorCodigo(String codigo);

    /**
     * Lista todos los productos activos
     */
    List<ProductoDTO> listarTodos();

    /**
     * Lista productos por categoría
     */
    List<ProductoDTO> listarPorCategoria(CategoriaProducto categoria);

    /**
     * Lista productos con stock bajo
     */
    List<ProductoDTO> listarConStockBajo();

    /**
     * Lista productos por vencer (próximos 30 días)
     */
    List<ProductoDTO> listarPorVencer();

    /**
     * Lista productos vencidos
     */
    List<ProductoDTO> listarVencidos();

    /**
     * Busca productos por nombre
     */
    List<ProductoDTO> buscarPorNombre(String nombre);

    /**
     * Elimina un producto (soft delete)
     */
    void eliminar(Long id);

    /**
     * Calcula el valor total del inventario
     */
    Double calcularValorTotal();
}
