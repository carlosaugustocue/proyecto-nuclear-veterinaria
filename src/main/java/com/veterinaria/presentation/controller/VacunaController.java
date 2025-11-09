package com.veterinaria.presentation.controller;

import com.veterinaria.application.dto.medical.CreateVacunaRequest;
import com.veterinaria.application.dto.medical.VacunaDTO;
import com.veterinaria.application.service.VacunaService;
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
 * Controlador REST para gestión de Vacunas.
 * Maneja endpoints para registro y consulta de vacunación.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/vacunas")
@RequiredArgsConstructor
public class VacunaController {

    private final VacunaService vacunaService;

    /**
     * Registrar nueva vacuna
     * POST /api/v1/vacunas
     */
    @PostMapping
    public ResponseEntity<VacunaDTO> registrar(@Valid @RequestBody CreateVacunaRequest request) {
        log.info("REST request to register Vacuna for historial: {}", request.getHistorialClinicoId());
        VacunaDTO created = vacunaService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Obtener vacuna por ID
     * GET /api/v1/vacunas/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<VacunaDTO> obtenerPorId(@PathVariable Long id) {
        log.debug("REST request to get Vacuna: {}", id);
        VacunaDTO vacuna = vacunaService.obtenerPorId(id);
        return ResponseEntity.ok(vacuna);
    }

    /**
     * Listar vacunas por paciente
     * GET /api/v1/vacunas/paciente/{pacienteId}
     */
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<VacunaDTO>> listarPorPaciente(@PathVariable Long pacienteId) {
        log.debug("REST request to get Vacunas by patient: {}", pacienteId);
        List<VacunaDTO> vacunas = vacunaService.listarPorPaciente(pacienteId);
        return ResponseEntity.ok(vacunas);
    }

    /**
     * Listar vacunas por tipo
     * GET /api/v1/vacunas/tipo/{tipoVacuna}
     */
    @GetMapping("/tipo/{tipoVacuna}")
    public ResponseEntity<List<VacunaDTO>> listarPorTipo(@PathVariable String tipoVacuna) {
        log.debug("REST request to get Vacunas by tipo: {}", tipoVacuna);
        List<VacunaDTO> vacunas = vacunaService.listarPorTipo(tipoVacuna);
        return ResponseEntity.ok(vacunas);
    }

    /**
     * Listar vacunas antirrábicas
     * GET /api/v1/vacunas/antirrabicas
     */
    @GetMapping("/antirrabicas")
    public ResponseEntity<List<VacunaDTO>> listarAntirrábicas() {
        log.debug("REST request to get antirrábicas Vacunas");
        List<VacunaDTO> vacunas = vacunaService.listarAntirrábicas();
        return ResponseEntity.ok(vacunas);
    }

    /**
     * Listar vacunas con refuerzo próximo
     * GET /api/v1/vacunas/refuerzo-proximo?dias={dias}
     */
    @GetMapping("/refuerzo-proximo")
    public ResponseEntity<List<VacunaDTO>> listarConRefuerzoProximo(
            @RequestParam(defaultValue = "30") int dias) {
        log.debug("REST request to get Vacunas with refuerzo próximo ({} dias)", dias);
        List<VacunaDTO> vacunas = vacunaService.listarConRefuerzoProximo(dias);
        return ResponseEntity.ok(vacunas);
    }

    /**
     * Listar vacunas con refuerzo vencido
     * GET /api/v1/vacunas/refuerzo-vencido
     */
    @GetMapping("/refuerzo-vencido")
    public ResponseEntity<List<VacunaDTO>> listarConRefuerzoVencido() {
        log.debug("REST request to get Vacunas with refuerzo vencido");
        List<VacunaDTO> vacunas = vacunaService.listarConRefuerzoVencido();
        return ResponseEntity.ok(vacunas);
    }

    /**
     * Listar vacunas con serie incompleta
     * GET /api/v1/vacunas/serie-incompleta
     */
    @GetMapping("/serie-incompleta")
    public ResponseEntity<List<VacunaDTO>> listarConSerieIncompleta() {
        log.debug("REST request to get Vacunas with serie incompleta");
        List<VacunaDTO> vacunas = vacunaService.listarConSerieIncompleta();
        return ResponseEntity.ok(vacunas);
    }

    /**
     * Listar vacunas con serie incompleta por paciente
     * GET /api/v1/vacunas/paciente/{pacienteId}/serie-incompleta
     */
    @GetMapping("/paciente/{pacienteId}/serie-incompleta")
    public ResponseEntity<List<VacunaDTO>> listarConSerieIncompletaPorPaciente(@PathVariable Long pacienteId) {
        log.debug("REST request to get Vacunas with serie incompleta for patient: {}", pacienteId);
        List<VacunaDTO> vacunas = vacunaService.listarConSerieIncompletaPorPaciente(pacienteId);
        return ResponseEntity.ok(vacunas);
    }

    /**
     * Listar vacunas con reacciones adversas
     * GET /api/v1/vacunas/con-reacciones-adversas
     */
    @GetMapping("/con-reacciones-adversas")
    public ResponseEntity<List<VacunaDTO>> listarConReaccionesAdversas() {
        log.debug("REST request to get Vacunas with reacciones adversas");
        List<VacunaDTO> vacunas = vacunaService.listarConReaccionesAdversas();
        return ResponseEntity.ok(vacunas);
    }

    /**
     * Listar vacunas por rango de fechas
     * GET /api/v1/vacunas/rango-fechas?fechaInicio={fecha}&fechaFin={fecha}
     */
    @GetMapping("/rango-fechas")
    public ResponseEntity<List<VacunaDTO>> listarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        log.debug("REST request to get Vacunas between {} and {}", fechaInicio, fechaFin);
        List<VacunaDTO> vacunas = vacunaService.listarPorRangoFechas(fechaInicio, fechaFin);
        return ResponseEntity.ok(vacunas);
    }

    /**
     * Calcular próxima dosis
     * PATCH /api/v1/vacunas/{id}/calcular-proxima-dosis
     */
    @PatchMapping("/{id}/calcular-proxima-dosis")
    public ResponseEntity<VacunaDTO> calcularProximaDosis(@PathVariable Long id) {
        log.info("REST request to calculate próxima dosis for Vacuna: {}", id);
        VacunaDTO vacuna = vacunaService.calcularProximaDosis(id);
        return ResponseEntity.ok(vacuna);
    }

    /**
     * Completar serie de vacunación
     * PATCH /api/v1/vacunas/{id}/completar-serie
     */
    @PatchMapping("/{id}/completar-serie")
    public ResponseEntity<VacunaDTO> completarSerie(@PathVariable Long id) {
        log.info("REST request to completar serie for Vacuna: {}", id);
        VacunaDTO vacuna = vacunaService.completarSerie(id);
        return ResponseEntity.ok(vacuna);
    }

    /**
     * Eliminar vacuna (soft delete)
     * DELETE /api/v1/vacunas/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("REST request to delete Vacuna: {}", id);
        vacunaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
