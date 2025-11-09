package com.veterinaria.presentation.controller;

import com.veterinaria.application.dto.medical.CreateTratamientoRequest;
import com.veterinaria.application.dto.medical.TratamientoDTO;
import com.veterinaria.application.service.TratamientoService;
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
 * Controlador REST para gestión de Tratamientos médicos.
 * Maneja endpoints para CRUD y seguimiento de tratamientos.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/tratamientos")
@RequiredArgsConstructor
public class TratamientoController {

    private final TratamientoService tratamientoService;

    /**
     * Crear nuevo tratamiento
     * POST /api/v1/tratamientos
     */
    @PostMapping
    public ResponseEntity<TratamientoDTO> crear(@Valid @RequestBody CreateTratamientoRequest request) {
        log.info("REST request to create Tratamiento for consulta: {}", request.getConsultaId());
        TratamientoDTO created = tratamientoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Obtener tratamiento por ID
     * GET /api/v1/tratamientos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TratamientoDTO> obtenerPorId(@PathVariable Long id) {
        log.debug("REST request to get Tratamiento: {}", id);
        TratamientoDTO tratamiento = tratamientoService.obtenerPorId(id);
        return ResponseEntity.ok(tratamiento);
    }

    /**
     * Listar tratamientos por paciente
     * GET /api/v1/tratamientos/paciente/{pacienteId}
     */
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<TratamientoDTO>> listarPorPaciente(@PathVariable Long pacienteId) {
        log.debug("REST request to get Tratamientos by patient: {}", pacienteId);
        List<TratamientoDTO> tratamientos = tratamientoService.listarPorPaciente(pacienteId);
        return ResponseEntity.ok(tratamientos);
    }

    /**
     * Listar tratamientos activos por paciente
     * GET /api/v1/tratamientos/paciente/{pacienteId}/activos
     */
    @GetMapping("/paciente/{pacienteId}/activos")
    public ResponseEntity<List<TratamientoDTO>> listarActivosPorPaciente(@PathVariable Long pacienteId) {
        log.debug("REST request to get active Tratamientos for patient: {}", pacienteId);
        List<TratamientoDTO> tratamientos = tratamientoService.listarActivosPorPaciente(pacienteId);
        return ResponseEntity.ok(tratamientos);
    }

    /**
     * Listar tratamientos vigentes por paciente
     * GET /api/v1/tratamientos/paciente/{pacienteId}/vigentes
     */
    @GetMapping("/paciente/{pacienteId}/vigentes")
    public ResponseEntity<List<TratamientoDTO>> listarVigentesPorPaciente(@PathVariable Long pacienteId) {
        log.debug("REST request to get vigentes Tratamientos for patient: {}", pacienteId);
        List<TratamientoDTO> tratamientos = tratamientoService.listarVigentesPorPaciente(pacienteId);
        return ResponseEntity.ok(tratamientos);
    }

    /**
     * Listar tratamientos por tipo
     * GET /api/v1/tratamientos/tipo/{tipoTratamiento}
     */
    @GetMapping("/tipo/{tipoTratamiento}")
    public ResponseEntity<List<TratamientoDTO>> listarPorTipo(@PathVariable String tipoTratamiento) {
        log.debug("REST request to get Tratamientos by tipo: {}", tipoTratamiento);
        List<TratamientoDTO> tratamientos = tratamientoService.listarPorTipo(tipoTratamiento);
        return ResponseEntity.ok(tratamientos);
    }

    /**
     * Listar medicamentos activos
     * GET /api/v1/tratamientos/medicamentos-activos
     */
    @GetMapping("/medicamentos-activos")
    public ResponseEntity<List<TratamientoDTO>> listarMedicamentosActivos() {
        log.debug("REST request to get medicamentos activos");
        List<TratamientoDTO> tratamientos = tratamientoService.listarMedicamentosActivos();
        return ResponseEntity.ok(tratamientos);
    }

    /**
     * Listar tratamientos que requieren supervisión
     * GET /api/v1/tratamientos/con-supervision
     */
    @GetMapping("/con-supervision")
    public ResponseEntity<List<TratamientoDTO>> listarConSupervision() {
        log.debug("REST request to get Tratamientos with supervisión");
        List<TratamientoDTO> tratamientos = tratamientoService.listarConSupervision();
        return ResponseEntity.ok(tratamientos);
    }

    /**
     * Listar tratamientos próximos a finalizar
     * GET /api/v1/tratamientos/proximos-a-finalizar?dias={dias}
     */
    @GetMapping("/proximos-a-finalizar")
    public ResponseEntity<List<TratamientoDTO>> listarProximosAFinalizar(
            @RequestParam(defaultValue = "7") int dias) {
        log.debug("REST request to get Tratamientos próximos a finalizar ({} días)", dias);
        List<TratamientoDTO> tratamientos = tratamientoService.listarProximosAFinalizar(dias);
        return ResponseEntity.ok(tratamientos);
    }

    /**
     * Listar tratamientos suspendidos
     * GET /api/v1/tratamientos/suspendidos
     */
    @GetMapping("/suspendidos")
    public ResponseEntity<List<TratamientoDTO>> listarSuspendidos() {
        log.debug("REST request to get Tratamientos suspendidos");
        List<TratamientoDTO> tratamientos = tratamientoService.listarSuspendidos();
        return ResponseEntity.ok(tratamientos);
    }

    /**
     * Listar tratamientos por rango de fechas
     * GET /api/v1/tratamientos/rango-fechas?fechaInicio={fecha}&fechaFin={fecha}
     */
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<TratamientoDTO>> listarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        log.debug("REST request to get Tratamientos between {} and {}", fechaInicio, fechaFin);
        List<TratamientoDTO> tratamientos = tratamientoService.listarPorRangoFechas(fechaInicio, fechaFin);
        return ResponseEntity.ok(tratamientos);
    }

    /**
     * Suspender tratamiento
     * PATCH /api/v1/tratamientos/{id}/suspender
     */
    @PatchMapping("/{id}/suspender")
    public ResponseEntity<TratamientoDTO> suspender(@PathVariable Long id) {
        log.info("REST request to suspender Tratamiento: {}", id);
        TratamientoDTO tratamiento = tratamientoService.suspender(id);
        return ResponseEntity.ok(tratamiento);
    }

    /**
     * Calcular fecha fin
     * PATCH /api/v1/tratamientos/{id}/calcular-fecha-fin
     */
    @PatchMapping("/{id}/calcular-fecha-fin")
    public ResponseEntity<TratamientoDTO> calcularFechaFin(@PathVariable Long id) {
        log.info("REST request to calcular fecha fin for Tratamiento: {}", id);
        TratamientoDTO tratamiento = tratamientoService.calcularFechaFin(id);
        return ResponseEntity.ok(tratamiento);
    }

    /**
     * Eliminar tratamiento (soft delete)
     * DELETE /api/v1/tratamientos/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("REST request to delete Tratamiento: {}", id);
        tratamientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
