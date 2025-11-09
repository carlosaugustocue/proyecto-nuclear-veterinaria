package com.veterinaria.presentation.controller;

import com.veterinaria.application.dto.medical.CreateDiagnosticoRequest;
import com.veterinaria.application.dto.medical.DiagnosticoDTO;
import com.veterinaria.application.service.DiagnosticoService;
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
 * Controlador REST para gestión de Diagnósticos médicos.
 * Maneja endpoints para CRUD y consulta de diagnósticos.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/diagnosticos")
@RequiredArgsConstructor
public class DiagnosticoController {

    private final DiagnosticoService diagnosticoService;

    /**
     * Crear nuevo diagnóstico
     * POST /api/v1/diagnosticos
     */
    @PostMapping
    public ResponseEntity<DiagnosticoDTO> crear(@Valid @RequestBody CreateDiagnosticoRequest request) {
        log.info("REST request to create Diagnostico for consulta: {}", request.getConsultaId());
        DiagnosticoDTO created = diagnosticoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Obtener diagnóstico por ID
     * GET /api/v1/diagnosticos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<DiagnosticoDTO> obtenerPorId(@PathVariable Long id) {
        log.debug("REST request to get Diagnostico: {}", id);
        DiagnosticoDTO diagnostico = diagnosticoService.obtenerPorId(id);
        return ResponseEntity.ok(diagnostico);
    }

    /**
     * Listar diagnósticos por paciente
     * GET /api/v1/diagnosticos/paciente/{pacienteId}
     */
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<DiagnosticoDTO>> listarPorPaciente(@PathVariable Long pacienteId) {
        log.debug("REST request to get Diagnosticos by patient: {}", pacienteId);
        List<DiagnosticoDTO> diagnosticos = diagnosticoService.listarPorPaciente(pacienteId);
        return ResponseEntity.ok(diagnosticos);
    }

    /**
     * Listar diagnósticos por tipo
     * GET /api/v1/diagnosticos/tipo/{tipoDiagnostico}
     */
    @GetMapping("/tipo/{tipoDiagnostico}")
    public ResponseEntity<List<DiagnosticoDTO>> listarPorTipo(@PathVariable String tipoDiagnostico) {
        log.debug("REST request to get Diagnosticos by tipo: {}", tipoDiagnostico);
        List<DiagnosticoDTO> diagnosticos = diagnosticoService.listarPorTipo(tipoDiagnostico);
        return ResponseEntity.ok(diagnosticos);
    }

    /**
     * Listar diagnósticos por gravedad
     * GET /api/v1/diagnosticos/gravedad/{gravedad}
     */
    @GetMapping("/gravedad/{gravedad}")
    public ResponseEntity<List<DiagnosticoDTO>> listarPorGravedad(@PathVariable String gravedad) {
        log.debug("REST request to get Diagnosticos by gravedad: {}", gravedad);
        List<DiagnosticoDTO> diagnosticos = diagnosticoService.listarPorGravedad(gravedad);
        return ResponseEntity.ok(diagnosticos);
    }

    /**
     * Listar diagnósticos graves o críticos
     * GET /api/v1/diagnosticos/graves-criticos
     */
    @GetMapping("/graves-criticos")
    public ResponseEntity<List<DiagnosticoDTO>> listarGravesOCriticos() {
        log.debug("REST request to get Diagnosticos graves o críticos");
        List<DiagnosticoDTO> diagnosticos = diagnosticoService.listarGravesOCriticos();
        return ResponseEntity.ok(diagnosticos);
    }

    /**
     * Listar diagnósticos principales
     * GET /api/v1/diagnosticos/principales
     */
    @GetMapping("/principales")
    public ResponseEntity<List<DiagnosticoDTO>> listarPrincipales() {
        log.debug("REST request to get Diagnosticos principales");
        List<DiagnosticoDTO> diagnosticos = diagnosticoService.listarPrincipales();
        return ResponseEntity.ok(diagnosticos);
    }

    /**
     * Listar diagnósticos por código CIE-10
     * GET /api/v1/diagnosticos/cie10/{codigoCie10}
     */
    @GetMapping("/cie10/{codigoCie10}")
    public ResponseEntity<List<DiagnosticoDTO>> listarPorCodigoCie10(@PathVariable String codigoCie10) {
        log.debug("REST request to get Diagnosticos by CIE-10: {}", codigoCie10);
        List<DiagnosticoDTO> diagnosticos = diagnosticoService.listarPorCodigoCie10(codigoCie10);
        return ResponseEntity.ok(diagnosticos);
    }

    /**
     * Buscar diagnósticos por descripción
     * GET /api/v1/diagnosticos/buscar?descripcion={texto}
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<DiagnosticoDTO>> buscarPorDescripcion(
            @RequestParam String descripcion) {
        log.debug("REST request to search Diagnosticos by descripcion: {}", descripcion);
        List<DiagnosticoDTO> diagnosticos = diagnosticoService.buscarPorDescripcion(descripcion);
        return ResponseEntity.ok(diagnosticos);
    }

    /**
     * Listar diagnósticos por rango de fechas
     * GET /api/v1/diagnosticos/rango-fechas?fechaInicio={fecha}&fechaFin={fecha}
     */
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<DiagnosticoDTO>> listarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        log.debug("REST request to get Diagnosticos between {} and {}", fechaInicio, fechaFin);
        List<DiagnosticoDTO> diagnosticos = diagnosticoService.listarPorRangoFechas(fechaInicio, fechaFin);
        return ResponseEntity.ok(diagnosticos);
    }

    /**
     * Eliminar diagnóstico (soft delete)
     * DELETE /api/v1/diagnosticos/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("REST request to delete Diagnostico: {}", id);
        diagnosticoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
