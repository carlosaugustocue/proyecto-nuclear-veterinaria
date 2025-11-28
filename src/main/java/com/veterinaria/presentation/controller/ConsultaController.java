package com.veterinaria.presentation.controller;

import com.veterinaria.application.dto.medical.*;
import com.veterinaria.application.service.ConsultaService;
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
 * Controlador REST para gestión de Consultas médicas.
 * Maneja endpoints para CRUD y operaciones de consultas veterinarias.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService consultaService;

    /**
     * Crear nueva consulta médica
     * POST /api/v1/consultas
     */
    @PostMapping
    public ResponseEntity<ConsultaDTO> crear(@Valid @RequestBody CreateConsultaRequest request) {
        log.info("REST request to create Consulta for historial: {}", request.getHistorialClinicoId());
        ConsultaDTO created = consultaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Actualizar consulta existente
     * PUT /api/v1/consultas/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ConsultaDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UpdateConsultaRequest request) {
        log.info("REST request to update Consulta: {}", id);
        ConsultaDTO updated = consultaService.actualizar(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Finalizar consulta
     * PATCH /api/v1/consultas/{id}/finalizar
     */
    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<ConsultaDTO> finalizar(
            @PathVariable Long id,
            @Valid @RequestBody FinalizarConsultaRequest request) {
        log.info("REST request to finalize Consulta: {}", id);
        ConsultaDTO finalizada = consultaService.finalizar(id, request);
        return ResponseEntity.ok(finalizada);
    }

    /**
     * Obtener consulta por ID
     * GET /api/v1/consultas/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO> obtenerPorId(@PathVariable Long id) {
        log.debug("REST request to get Consulta: {}", id);
        ConsultaDTO consulta = consultaService.obtenerPorId(id);
        return ResponseEntity.ok(consulta);
    }

    /**
     * Listar consultas por paciente
     * GET /api/v1/consultas/paciente/{pacienteId}
     */
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ConsultaDTO>> listarPorPaciente(@PathVariable Long pacienteId) {
        log.debug("REST request to get Consultas by patient: {}", pacienteId);
        List<ConsultaDTO> consultas = consultaService.listarPorPaciente(pacienteId);
        return ResponseEntity.ok(consultas);
    }

    /**
     * Listar consultas por historial clínico
     * GET /api/v1/consultas/historial/{historialId}
     */
    @GetMapping("/historial/{historialId}")
    public ResponseEntity<List<ConsultaDTO>> listarPorHistorialClinico(@PathVariable Long historialId) {
        log.debug("REST request to get Consultas by historial clinico: {}", historialId);
        List<ConsultaDTO> consultas = consultaService.listarPorHistorialClinico(historialId);
        return ResponseEntity.ok(consultas);
    }

    /**
     * Listar consultas por veterinario
     * GET /api/v1/consultas/veterinario/{veterinarioId}
     */
    @GetMapping("/veterinario/{veterinarioId}")
    public ResponseEntity<List<ConsultaDTO>> listarPorVeterinario(@PathVariable Long veterinarioId) {
        log.debug("REST request to get Consultas by veterinario: {}", veterinarioId);
        List<ConsultaDTO> consultas = consultaService.listarPorVeterinario(veterinarioId);
        return ResponseEntity.ok(consultas);
    }

    /**
     * Listar consultas por fecha
     * GET /api/v1/consultas/fecha/{fecha}
     */
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<ConsultaDTO>> listarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        log.debug("REST request to get Consultas by date: {}", fecha);
        List<ConsultaDTO> consultas = consultaService.listarPorFecha(fecha);
        return ResponseEntity.ok(consultas);
    }

    /**
     * Listar consultas en curso
     * GET /api/v1/consultas/en-curso
     */
    @GetMapping("/en-curso")
    public ResponseEntity<List<ConsultaDTO>> listarEnCurso() {
        log.debug("REST request to get Consultas en curso");
        List<ConsultaDTO> consultas = consultaService.listarEnCurso();
        return ResponseEntity.ok(consultas);
    }

    /**
     * Listar consultas con seguimiento
     * GET /api/v1/consultas/con-seguimiento
     */
    @GetMapping("/con-seguimiento")
    public ResponseEntity<List<ConsultaDTO>> listarConSeguimiento() {
        log.debug("REST request to get Consultas con seguimiento");
        List<ConsultaDTO> consultas = consultaService.listarConSeguimiento();
        return ResponseEntity.ok(consultas);
    }

    /**
     * Obtener última consulta del paciente
     * GET /api/v1/consultas/paciente/{pacienteId}/ultima
     */
    @GetMapping("/paciente/{pacienteId}/ultima")
    public ResponseEntity<ConsultaDTO> obtenerUltimaConsultaPaciente(@PathVariable Long pacienteId) {
        log.debug("REST request to get last Consulta for patient: {}", pacienteId);
        ConsultaDTO consulta = consultaService.obtenerUltimaConsultaPaciente(pacienteId);
        return ResponseEntity.ok(consulta);
    }

    /**
     * Agregar diagnóstico a consulta
     * POST /api/v1/consultas/{consultaId}/diagnosticos
     */
    @PostMapping("/{consultaId}/diagnosticos")
    public ResponseEntity<DiagnosticoDTO> agregarDiagnostico(
            @PathVariable Long consultaId,
            @Valid @RequestBody CreateDiagnosticoRequest request) {
        log.info("REST request to add Diagnostico to Consulta: {}", consultaId);
        DiagnosticoDTO diagnostico = consultaService.agregarDiagnostico(consultaId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(diagnostico);
    }

    /**
     * Agregar tratamiento a consulta
     * POST /api/v1/consultas/{consultaId}/tratamientos
     */
    @PostMapping("/{consultaId}/tratamientos")
    public ResponseEntity<TratamientoDTO> agregarTratamiento(
            @PathVariable Long consultaId,
            @Valid @RequestBody CreateTratamientoRequest request) {
        log.info("REST request to add Tratamiento to Consulta: {}", consultaId);
        TratamientoDTO tratamiento = consultaService.agregarTratamiento(consultaId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(tratamiento);
    }

    /**
     * Eliminar consulta (soft delete)
     * DELETE /api/v1/consultas/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("REST request to delete Consulta: {}", id);
        consultaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
