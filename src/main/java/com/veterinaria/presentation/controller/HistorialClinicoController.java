package com.veterinaria.presentation.controller;

import com.veterinaria.application.dto.medical.HistorialClinicoDTO;
import com.veterinaria.application.dto.medical.UpdateHistorialClinicoRequest;
import com.veterinaria.application.service.HistorialClinicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestión de Historiales Clínicos.
 * Maneja endpoints para CRUD de historiales clínicos de pacientes.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/historiales-clinicos")
@RequiredArgsConstructor
public class HistorialClinicoController {

    private final HistorialClinicoService historialClinicoService;

    /**
     * Crear historial clínico para un paciente
     * POST /api/v1/historiales-clinicos/paciente/{pacienteId}
     */
    @PostMapping("/paciente/{pacienteId}")
    public ResponseEntity<HistorialClinicoDTO> crear(@PathVariable Long pacienteId) {
        log.info("REST request to create HistorialClinico for patient: {}", pacienteId);
        HistorialClinicoDTO created = historialClinicoService.crear(pacienteId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Actualizar historial clínico
     * PUT /api/v1/historiales-clinicos/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<HistorialClinicoDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UpdateHistorialClinicoRequest request) {
        log.info("REST request to update HistorialClinico: {}", id);
        HistorialClinicoDTO updated = historialClinicoService.actualizar(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Obtener historial clínico por ID
     * GET /api/v1/historiales-clinicos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<HistorialClinicoDTO> obtenerPorId(@PathVariable Long id) {
        log.debug("REST request to get HistorialClinico: {}", id);
        HistorialClinicoDTO historial = historialClinicoService.obtenerPorId(id);
        return ResponseEntity.ok(historial);
    }

    /**
     * Obtener historial clínico por paciente
     * GET /api/v1/historiales-clinicos/paciente/{pacienteId}
     */
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<HistorialClinicoDTO> obtenerPorPaciente(@PathVariable Long pacienteId) {
        log.debug("REST request to get HistorialClinico by patient: {}", pacienteId);
        HistorialClinicoDTO historial = historialClinicoService.obtenerPorPaciente(pacienteId);
        return ResponseEntity.ok(historial);
    }

    /**
     * Listar todos los historiales clínicos
     * GET /api/v1/historiales-clinicos
     */
    @GetMapping
    public ResponseEntity<List<HistorialClinicoDTO>> listarTodos() {
        log.debug("REST request to get all HistorialesC linicos");
        List<HistorialClinicoDTO> historiales = historialClinicoService.listarTodos();
        return ResponseEntity.ok(historiales);
    }

    /**
     * Listar historiales con alergias
     * GET /api/v1/historiales-clinicos/con-alergias
     */
    @GetMapping("/con-alergias")
    public ResponseEntity<List<HistorialClinicoDTO>> listarConAlergias() {
        log.debug("REST request to get HistorialesC linicos with alergias");
        List<HistorialClinicoDTO> historiales = historialClinicoService.listarConAlergias();
        return ResponseEntity.ok(historiales);
    }

    /**
     * Listar historiales con condiciones crónicas
     * GET /api/v1/historiales-clinicos/con-condiciones-cronicas
     */
    @GetMapping("/con-condiciones-cronicas")
    public ResponseEntity<List<HistorialClinicoDTO>> listarConCondicionesCronicas() {
        log.debug("REST request to get HistorialesC linicos with condiciones cronicas");
        List<HistorialClinicoDTO> historiales = historialClinicoService.listarConCondicionesCronicas();
        return ResponseEntity.ok(historiales);
    }

    /**
     * Verificar si existe historial para un paciente
     * GET /api/v1/historiales-clinicos/paciente/{pacienteId}/existe
     */
    @GetMapping("/paciente/{pacienteId}/existe")
    public ResponseEntity<Boolean> existeHistorialPaciente(@PathVariable Long pacienteId) {
        log.debug("REST request to check if HistorialClinico exists for patient: {}", pacienteId);
        boolean existe = historialClinicoService.existeHistorialPaciente(pacienteId);
        return ResponseEntity.ok(existe);
    }

    /**
     * Eliminar historial clínico (soft delete)
     * DELETE /api/v1/historiales-clinicos/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("REST request to delete HistorialClinico: {}", id);
        historialClinicoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
