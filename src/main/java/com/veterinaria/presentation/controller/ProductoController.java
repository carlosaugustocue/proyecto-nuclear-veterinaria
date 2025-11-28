package com.veterinaria.presentation.controller;

import com.veterinaria.application.dto.inventory.CreateProductoRequest;
import com.veterinaria.application.dto.inventory.ProductoDTO;
import com.veterinaria.application.dto.inventory.UpdateProductoRequest;
import com.veterinaria.application.service.ProductoService;
import com.veterinaria.domain.enums.CategoriaProducto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestión de Productos de Inventario.
 *
 * @author Sistema Veterinaria
 */
@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    /**
     * Crea un nuevo producto
     */
    @PostMapping
    public ResponseEntity<ProductoDTO> crear(@Valid @RequestBody CreateProductoRequest request) {
        ProductoDTO created = productoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Actualiza un producto existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductoRequest request) {
        ProductoDTO updated = productoService.actualizar(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Busca un producto por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> buscarPorId(@PathVariable Long id) {
        ProductoDTO producto = productoService.buscarPorId(id);
        return ResponseEntity.ok(producto);
    }

    /**
     * Busca un producto por código
     */
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<ProductoDTO> buscarPorCodigo(@PathVariable String codigo) {
        ProductoDTO producto = productoService.buscarPorCodigo(codigo);
        return ResponseEntity.ok(producto);
    }

    /**
     * Lista todos los productos activos
     */
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarTodos() {
        List<ProductoDTO> productos = productoService.listarTodos();
        return ResponseEntity.ok(productos);
    }

    /**
     * Lista productos por categoría
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoDTO>> listarPorCategoria(
            @PathVariable CategoriaProducto categoria) {
        List<ProductoDTO> productos = productoService.listarPorCategoria(categoria);
        return ResponseEntity.ok(productos);
    }

    /**
     * Lista productos con stock bajo
     */
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<ProductoDTO>> listarConStockBajo() {
        List<ProductoDTO> productos = productoService.listarConStockBajo();
        return ResponseEntity.ok(productos);
    }

    /**
     * Lista productos por vencer (próximos 30 días)
     */
    @GetMapping("/por-vencer")
    public ResponseEntity<List<ProductoDTO>> listarPorVencer() {
        List<ProductoDTO> productos = productoService.listarPorVencer();
        return ResponseEntity.ok(productos);
    }

    /**
     * Lista productos vencidos
     */
    @GetMapping("/vencidos")
    public ResponseEntity<List<ProductoDTO>> listarVencidos() {
        List<ProductoDTO> productos = productoService.listarVencidos();
        return ResponseEntity.ok(productos);
    }

    /**
     * Busca productos por nombre
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoDTO>> buscarPorNombre(
            @RequestParam String nombre) {
        List<ProductoDTO> productos = productoService.buscarPorNombre(nombre);
        return ResponseEntity.ok(productos);
    }

    /**
     * Calcula el valor total del inventario
     */
    @GetMapping("/valor-total")
    public ResponseEntity<Double> calcularValorTotal() {
        Double valorTotal = productoService.calcularValorTotal();
        return ResponseEntity.ok(valorTotal);
    }

    /**
     * Elimina un producto (soft delete)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
