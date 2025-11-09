package com.veterinaria.presentation.controller;

import com.veterinaria.application.dto.medical.CreateExamenRequest;
import com.veterinaria.application.dto.medical.ExamenDTO;
import com.veterinaria.application.dto.medical.RegistrarResultadosExamenRequest;
import com.veterinaria.application.service.ExamenService;
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
 * Controlador REST para gestión de Exámenes médicos.
 * Maneja endpoints para solicitud y seguimiento de exámenes.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/examenes")
@RequiredArgsConstructor
public class ExamenController {

    private final ExamenService examenService;

    /**
     * Solicitar nuevo examen
     * POST /api/v1/examenes
     */
    @PostMapping
    public ResponseEntity<ExamenDTO> solicitar(@Valid @RequestBody CreateExamenRequest request) {
        log.info("REST request to solicitar Examen for historial: {}", request.getHistorialClinicoId());
        ExamenDTO created = examenService.solicitar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Obtener examen por ID
     * GET /api/v1/examenes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExamenDTO> obtenerPorId(@PathVariable Long id) {
        log.debug("REST request to get Examen: {}", id);
        ExamenDTO examen = examenService.obtenerPorId(id);
        return ResponseEntity.ok(examen);
    }

    /**
     * Listar exámenes por paciente
     * GET /api/v1/examenes/paciente/{pacienteId}
     */
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ExamenDTO>> listarPorPaciente(@PathVariable Long pacienteId) {
        log.debug("REST request to get Examenes by patient: {}", pacienteId);
        List<ExamenDTO> examenes = examenService.listarPorPaciente(pacienteId);
        return ResponseEntity.ok(examenes);
    }

    /**
     * Listar exámenes por consulta
     * GET /api/v1/examenes/consulta/{consultaId}
     */
    @GetMapping("/consulta/{consultaId}")
    public ResponseEntity<List<ExamenDTO>> listarPorConsulta(@PathVariable Long consultaId) {
        log.debug("REST request to get Examenes by consulta: {}", consultaId);
        List<ExamenDTO> examenes = examenService.listarPorConsulta(consultaId);
        return ResponseEntity.ok(examenes);
    }

    /**
     * Listar exámenes por tipo
     * GET /api/v1/examenes/tipo/{tipoExamen}
     */
    @GetMapping("/tipo/{tipoExamen}")
    public ResponseEntity<List<ExamenDTO>> listarPorTipo(@PathVariable String tipoExamen) {
        log.debug("REST request to get Examenes by tipo: {}", tipoExamen);
        List<ExamenDTO> examenes = examenService.listarPorTipo(tipoExamen);
        return ResponseEntity.ok(examenes);
    }

    /**
     * Listar exámenes por estado
     * GET /api/v1/examenes/estado/{estado}
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ExamenDTO>> listarPorEstado(@PathVariable String estado) {
        log.debug("REST request to get Examenes by estado: {}", estado);
        List<ExamenDTO> examenes = examenService.listarPorEstado(estado);
        return ResponseEntity.ok(examenes);
    }

    /**
     * Listar exámenes pendientes
     * GET /api/v1/examenes/pendientes
     */
    @GetMapping("/pendientes")
    public ResponseEntity<List<ExamenDTO>> listarPendientes() {
        log.debug("REST request to get Examenes pendientes");
        List<ExamenDTO> examenes = examenService.listarPendientes();
        return ResponseEntity.ok(examenes);
    }

    /**
     * Listar exámenes completados
     * GET /api/v1/examenes/completados
     */
    @GetMapping("/completados")
    public ResponseEntity<List<ExamenDTO>> listarCompletados() {
        log.debug("REST request to get Examenes completados");
        List<ExamenDTO> examenes = examenService.listarCompletados();
        return ResponseEntity.ok(examenes);
    }

    /**
     * Listar exámenes con resultados anormales
     * GET /api/v1/examenes/resultados-anormales
     */
    @GetMapping("/resultados-anormales")
    public ResponseEntity<List<ExamenDTO>> listarConResultadosAnormales() {
        log.debug("REST request to get Examenes with resultados anormales");
        List<ExamenDTO> examenes = examenService.listarConResultadosAnormales();
        return ResponseEntity.ok(examenes);
    }

    /**
     * Listar exámenes urgentes
     * GET /api/v1/examenes/urgentes
     */
    @GetMapping("/urgentes")
    public ResponseEntity<List<ExamenDTO>> listarUrgentes() {
        log.debug("REST request to get Examenes urgentes");
        List<ExamenDTO> examenes = examenService.listarUrgentes();
        return ResponseEntity.ok(examenes);
    }

    /**
     * Listar exámenes de laboratorio
     * GET /api/v1/examenes/laboratorio
     */
    @GetMapping("/laboratorio")
    public ResponseEntity<List<ExamenDTO>> listarLaboratorio() {
        log.debug("REST request to get Examenes de laboratorio");
        List<ExamenDTO> examenes = examenService.listarLaboratorio();
        return ResponseEntity.ok(examenes);
    }

    /**
     * Listar exámenes de imagenología
     * GET /api/v1/examenes/imagenologia
     */
    @GetMapping("/imagenologia")
    public ResponseEntity<List<ExamenDTO>> listarImagenologia() {
        log.debug("REST request to get Examenes de imagenología");
        List<ExamenDTO> examenes = examenService.listarImagenologia();
        return ResponseEntity.ok(examenes);
    }

    /**
     * Listar exámenes demorados
     * GET /api/v1/examenes/demorados?dias={dias}
     */
    @GetMapping("/demorados")
    public ResponseEntity<List<ExamenDTO>> listarDemorados(
            @RequestParam(defaultValue = "7") int dias) {
        log.debug("REST request to get Examenes demorados (más de {} días)", dias);
        List<ExamenDTO> examenes = examenService.listarDemorados(dias);
        return ResponseEntity.ok(examenes);
    }

    /**
     * Listar exámenes por rango de fechas de solicitud
     * GET /api/v1/examenes/rango-fechas?fechaInicio={fecha}&fechaFin={fecha}
     */
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<ExamenDTO>> listarPorRangoFechasSolicitud(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        log.debug("REST request to get Examenes between {} and {}", fechaInicio, fechaFin);
        List<ExamenDTO> examenes = examenService.listarPorRangoFechasSolicitud(fechaInicio, fechaFin);
        return ResponseEntity.ok(examenes);
    }

    /**
     * Registrar resultados de examen
     * PATCH /api/v1/examenes/{id}/resultados
     */
    @PatchMapping("/{id}/resultados")
    public ResponseEntity<ExamenDTO> registrarResultados(
            @PathVariable Long id,
            @Valid @RequestBody RegistrarResultadosExamenRequest request) {
        log.info("REST request to register resultados for Examen: {}", id);
        ExamenDTO examen = examenService.registrarResultados(id, request);
        return ResponseEntity.ok(examen);
    }

    /**
     * Marcar examen como realizado
     * PATCH /api/v1/examenes/{id}/marcar-realizado
     */
    @PatchMapping("/{id}/marcar-realizado")
    public ResponseEntity<ExamenDTO> marcarRealizado(@PathVariable Long id) {
        log.info("REST request to marcar realizado Examen: {}", id);
        ExamenDTO examen = examenService.marcarRealizado(id);
        return ResponseEntity.ok(examen);
    }

    /**
     * Cancelar examen
     * PATCH /api/v1/examenes/{id}/cancelar
     */
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ExamenDTO> cancelar(
            @PathVariable Long id,
            @RequestParam String motivo) {
        log.info("REST request to cancelar Examen: {} with motivo: {}", id, motivo);
        ExamenDTO examen = examenService.cancelar(id, motivo);
        return ResponseEntity.ok(examen);
    }

    /**
     * Eliminar examen (soft delete)
     * DELETE /api/v1/examenes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("REST request to delete Examen: {}", id);
        examenService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
