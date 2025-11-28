package com.veterinaria.presentation.controller;

import com.veterinaria.application.dto.appointments.CreateTipoServicioRequest;
import com.veterinaria.application.dto.appointments.TipoServicioDTO;
import com.veterinaria.application.dto.appointments.UpdateTipoServicioRequest;
import com.veterinaria.application.service.TipoServicioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestión de Tipos de Servicio.
 * Maneja endpoints para CRUD de servicios veterinarios.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/tipos-servicio")
@RequiredArgsConstructor
public class TipoServicioController {

    private final TipoServicioService tipoServicioService;

    /**
     * Crear nuevo tipo de servicio
     * POST /api/v1/tipos-servicio
     */
    @PostMapping
    public ResponseEntity<TipoServicioDTO> crear(@Valid @RequestBody CreateTipoServicioRequest request) {
        log.info("REST request to create TipoServicio: {}", request.getNombre());
        TipoServicioDTO created = tipoServicioService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Actualizar tipo de servicio
     * PUT /api/v1/tipos-servicio/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TipoServicioDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTipoServicioRequest request) {
        log.info("REST request to update TipoServicio: {}", id);
        TipoServicioDTO updated = tipoServicioService.actualizar(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Obtener tipo de servicio por ID
     * GET /api/v1/tipos-servicio/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TipoServicioDTO> obtenerPorId(@PathVariable Long id) {
        log.debug("REST request to get TipoServicio: {}", id);
        TipoServicioDTO tipoServicio = tipoServicioService.obtenerPorId(id);
        return ResponseEntity.ok(tipoServicio);
    }

    /**
     * Listar todos los tipos de servicio activos
     * GET /api/v1/tipos-servicio
     */
    @GetMapping
    public ResponseEntity<List<TipoServicioDTO>> listarTodos() {
        log.debug("REST request to get all TiposServicio");
        List<TipoServicioDTO> servicios = tipoServicioService.listarTodos();
        return ResponseEntity.ok(servicios);
    }

    /**
     * Listar tipos de servicio por categoría
     * GET /api/v1/tipos-servicio/categoria/{categoria}
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<TipoServicioDTO>> listarPorCategoria(@PathVariable String categoria) {
        log.debug("REST request to get TiposServicio by category: {}", categoria);
        List<TipoServicioDTO> servicios = tipoServicioService.listarPorCategoria(categoria);
        return ResponseEntity.ok(servicios);
    }

    /**
     * Obtener todas las categorías
     * GET /api/v1/tipos-servicio/categorias
     */
    @GetMapping("/categorias")
    public ResponseEntity<List<String>> obtenerCategorias() {
        log.debug("REST request to get all categories");
        List<String> categorias = tipoServicioService.obtenerCategorias();
        return ResponseEntity.ok(categorias);
    }

    /**
     * Desactivar tipo de servicio
     * DELETE /api/v1/tipos-servicio/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        log.info("REST request to deactivate TipoServicio: {}", id);
        tipoServicioService.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Activar tipo de servicio
     * PATCH /api/v1/tipos-servicio/{id}/activar
     */
    @PatchMapping("/{id}/activar")
    public ResponseEntity<Void> activar(@PathVariable Long id) {
        log.info("REST request to activate TipoServicio: {}", id);
        tipoServicioService.activar(id);
        return ResponseEntity.noContent().build();
    }
}
