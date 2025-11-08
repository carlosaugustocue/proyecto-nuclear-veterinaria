package com.veterinaria.presentation.controller;

import com.veterinaria.application.dto.cita.*;
import com.veterinaria.application.service.CitaService;
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
 * Controlador REST para gestión de Citas.
 * Maneja endpoints para CRUD y operaciones especiales de citas médicas.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    /**
     * Crear nueva cita
     * POST /api/v1/citas
     */
    @PostMapping
    public ResponseEntity<CitaDTO> crear(@Valid @RequestBody CreateCitaRequest request) {
        log.info("REST request to create Cita for patient: {}", request.getPacienteId());
        CitaDTO created = citaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Actualizar cita existente (solo motivo y notas)
     * PUT /api/v1/citas/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<CitaDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCitaRequest request) {
        log.info("REST request to update Cita: {}", id);
        CitaDTO updated = citaService.actualizar(id, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Obtener cita por ID
     * GET /api/v1/citas/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<CitaDTO> obtenerPorId(@PathVariable Long id) {
        log.debug("REST request to get Cita: {}", id);
        CitaDTO cita = citaService.obtenerPorId(id);
        return ResponseEntity.ok(cita);
    }

    /**
     * Listar todas las citas activas
     * GET /api/v1/citas
     */
    @GetMapping
    public ResponseEntity<List<CitaDTO>> listarTodas() {
        log.debug("REST request to get all Citas");
        List<CitaDTO> citas = citaService.listarTodas();
        return ResponseEntity.ok(citas);
    }

    /**
     * Listar citas por fecha
     * GET /api/v1/citas/fecha/{fecha}
     */
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<CitaDTO>> listarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        log.debug("REST request to get Citas by date: {}", fecha);
        List<CitaDTO> citas = citaService.listarPorFecha(fecha);
        return ResponseEntity.ok(citas);
    }

    /**
     * Listar citas por veterinario y fecha
     * GET /api/v1/citas/veterinario/{veterinarioId}/fecha/{fecha}
     */
    @GetMapping("/veterinario/{veterinarioId}/fecha/{fecha}")
    public ResponseEntity<List<CitaDTO>> listarPorVeterinarioYFecha(
            @PathVariable Long veterinarioId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        log.debug("REST request to get Citas by veterinario: {} and date: {}", veterinarioId, fecha);
        List<CitaDTO> citas = citaService.listarPorVeterinarioYFecha(veterinarioId, fecha);
        return ResponseEntity.ok(citas);
    }

    /**
     * Listar citas por paciente
     * GET /api/v1/citas/paciente/{pacienteId}
     */
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<CitaDTO>> listarPorPaciente(@PathVariable Long pacienteId) {
        log.debug("REST request to get Citas by patient: {}", pacienteId);
        List<CitaDTO> citas = citaService.listarPorPaciente(pacienteId);
        return ResponseEntity.ok(citas);
    }

    /**
     * Listar citas por cliente
     * GET /api/v1/citas/cliente/{clienteId}
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CitaDTO>> listarPorCliente(@PathVariable Long clienteId) {
        log.debug("REST request to get Citas by client: {}", clienteId);
        List<CitaDTO> citas = citaService.listarPorCliente(clienteId);
        return ResponseEntity.ok(citas);
    }

    /**
     * Listar próximas citas del paciente
     * GET /api/v1/citas/paciente/{pacienteId}/proximas
     */
    @GetMapping("/paciente/{pacienteId}/proximas")
    public ResponseEntity<List<CitaDTO>> listarProximasCitasPaciente(@PathVariable Long pacienteId) {
        log.debug("REST request to get upcoming Citas for patient: {}", pacienteId);
        List<CitaDTO> citas = citaService.listarProximasCitasPaciente(pacienteId);
        return ResponseEntity.ok(citas);
    }

    /**
     * Confirmar cita
     * PATCH /api/v1/citas/{id}/confirmar
     */
    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<CitaDTO> confirmar(@PathVariable Long id) {
        log.info("REST request to confirm Cita: {}", id);
        CitaDTO confirmada = citaService.confirmar(id);
        return ResponseEntity.ok(confirmada);
    }

    /**
     * Cancelar cita
     * PATCH /api/v1/citas/{id}/cancelar
     */
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<CitaDTO> cancelar(
            @PathVariable Long id,
            @Valid @RequestBody CancelarCitaRequest request) {
        log.info("REST request to cancel Cita: {}", id);
        CitaDTO cancelada = citaService.cancelar(id, request);
        return ResponseEntity.ok(cancelada);
    }

    /**
     * Iniciar atención de cita
     * PATCH /api/v1/citas/{id}/iniciar
     */
    @PatchMapping("/{id}/iniciar")
    public ResponseEntity<CitaDTO> iniciar(@PathVariable Long id) {
        log.info("REST request to start Cita: {}", id);
        CitaDTO iniciada = citaService.iniciar(id);
        return ResponseEntity.ok(iniciada);
    }

    /**
     * Completar cita
     * PATCH /api/v1/citas/{id}/completar
     */
    @PatchMapping("/{id}/completar")
    public ResponseEntity<CitaDTO> completar(@PathVariable Long id) {
        log.info("REST request to complete Cita: {}", id);
        CitaDTO completada = citaService.completar(id);
        return ResponseEntity.ok(completada);
    }

    /**
     * Reagendar cita
     * PATCH /api/v1/citas/{id}/reagendar
     */
    @PatchMapping("/{id}/reagendar")
    public ResponseEntity<CitaDTO> reagendar(
            @PathVariable Long id,
            @Valid @RequestBody ReagendarCitaRequest request) {
        log.info("REST request to reschedule Cita: {}", id);
        CitaDTO reagendada = citaService.reagendar(id, request);
        return ResponseEntity.ok(reagendada);
    }

    /**
     * Obtener horarios disponibles
     * GET /api/v1/citas/disponibilidad/veterinario/{veterinarioId}
     *     ?fecha={fecha}&duracion={duracion}
     */
    @GetMapping("/disponibilidad/veterinario/{veterinarioId}")
    public ResponseEntity<List<String>> obtenerHorariosDisponibles(
            @PathVariable Long veterinarioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(defaultValue = "30") Integer duracion) {
        log.debug("REST request to get available times for veterinario: {} on date: {}",
                veterinarioId, fecha);
        List<String> horarios = citaService.obtenerHorariosDisponibles(veterinarioId, fecha, duracion);
        return ResponseEntity.ok(horarios);
    }

    /**
     * Eliminar cita (soft delete)
     * DELETE /api/v1/citas/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("REST request to delete Cita: {}", id);
        citaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
