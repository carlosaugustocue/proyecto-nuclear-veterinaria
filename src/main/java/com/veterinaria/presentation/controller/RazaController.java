package com.veterinaria.presentation.controller;

import com.veterinaria.application.dto.raza.CreateRazaRequest;
import com.veterinaria.application.dto.raza.RazaDTO;
import com.veterinaria.application.dto.raza.UpdateRazaRequest;
import com.veterinaria.application.service.RazaService;
import com.veterinaria.domain.enums.TipoEspecie;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestión de Razas.
 * Expone endpoints para el catálogo de tipos de mascotas y razas.
 *
 * Implementa HU-09: Tipos de Mascotas
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/razas")
@RequiredArgsConstructor
public class RazaController {

    private final RazaService razaService;

    /**
     * Crea una nueva raza.
     *
     * POST /api/v1/razas
     */
    @PostMapping
    public ResponseEntity<RazaDTO> crearRaza(@Valid @RequestBody CreateRazaRequest request) {
        log.info("POST /api/v1/razas - Creando raza: {} para especie: {}", request.nombre(), request.especie());
        RazaDTO razaCreada = razaService.crearRaza(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(razaCreada);
    }

    /**
     * Obtiene una raza por su ID.
     *
     * GET /api/v1/razas/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<RazaDTO> obtenerRazaPorId(@PathVariable Long id) {
        log.info("GET /api/v1/razas/{} - Obteniendo raza", id);
        RazaDTO raza = razaService.obtenerRazaPorId(id);
        return ResponseEntity.ok(raza);
    }

    /**
     * Obtiene todas las razas.
     *
     * GET /api/v1/razas
     */
    @GetMapping
    public ResponseEntity<List<RazaDTO>> obtenerTodasLasRazas() {
        log.info("GET /api/v1/razas - Obteniendo todas las razas");
        List<RazaDTO> razas = razaService.obtenerTodasLasRazas();
        return ResponseEntity.ok(razas);
    }

    /**
     * Obtiene todas las razas activas.
     *
     * GET /api/v1/razas/activas
     */
    @GetMapping("/activas")
    public ResponseEntity<List<RazaDTO>> obtenerRazasActivas() {
        log.info("GET /api/v1/razas/activas - Obteniendo razas activas");
        List<RazaDTO> razas = razaService.obtenerRazasActivas();
        return ResponseEntity.ok(razas);
    }

    /**
     * Obtiene razas por especie.
     *
     * GET /api/v1/razas/especie/{especie}
     */
    @GetMapping("/especie/{especie}")
    public ResponseEntity<List<RazaDTO>> obtenerRazasPorEspecie(@PathVariable TipoEspecie especie) {
        log.info("GET /api/v1/razas/especie/{} - Obteniendo razas de especie", especie);
        List<RazaDTO> razas = razaService.obtenerRazasPorEspecie(especie);
        return ResponseEntity.ok(razas);
    }

    /**
     * Obtiene razas activas por especie.
     *
     * GET /api/v1/razas/especie/{especie}/activas
     */
    @GetMapping("/especie/{especie}/activas")
    public ResponseEntity<List<RazaDTO>> obtenerRazasActivasPorEspecie(@PathVariable TipoEspecie especie) {
        log.info("GET /api/v1/razas/especie/{}/activas - Obteniendo razas activas de especie", especie);
        List<RazaDTO> razas = razaService.obtenerRazasActivasPorEspecie(especie);
        return ResponseEntity.ok(razas);
    }

    /**
     * Obtiene razas específicas (no mestizas) activas por especie.
     *
     * GET /api/v1/razas/especie/{especie}/especificas
     */
    @GetMapping("/especie/{especie}/especificas")
    public ResponseEntity<List<RazaDTO>> obtenerRazasEspecificasActivasPorEspecie(
            @PathVariable TipoEspecie especie) {
        log.info("GET /api/v1/razas/especie/{}/especificas - Obteniendo razas específicas", especie);
        List<RazaDTO> razas = razaService.obtenerRazasEspecificasActivasPorEspecie(especie);
        return ResponseEntity.ok(razas);
    }

    /**
     * Busca razas por nombre.
     *
     * GET /api/v1/razas/buscar?nombre=xxx
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<RazaDTO>> buscarPorNombre(@RequestParam String nombre) {
        log.info("GET /api/v1/razas/buscar?nombre={}", nombre);
        List<RazaDTO> razas = razaService.buscarPorNombre(nombre);
        return ResponseEntity.ok(razas);
    }

    /**
     * Obtiene razas predefinidas del sistema.
     *
     * GET /api/v1/razas/predefinidas
     */
    @GetMapping("/predefinidas")
    public ResponseEntity<List<RazaDTO>> obtenerRazasPredefinidas() {
        log.info("GET /api/v1/razas/predefinidas - Obteniendo razas predefinidas");
        List<RazaDTO> razas = razaService.obtenerRazasPredefinidas();
        return ResponseEntity.ok(razas);
    }

    /**
     * Obtiene razas personalizadas (creadas por usuarios).
     *
     * GET /api/v1/razas/personalizadas
     */
    @GetMapping("/personalizadas")
    public ResponseEntity<List<RazaDTO>> obtenerRazasPersonalizadas() {
        log.info("GET /api/v1/razas/personalizadas - Obteniendo razas personalizadas");
        List<RazaDTO> razas = razaService.obtenerRazasPersonalizadas();
        return ResponseEntity.ok(razas);
    }

    /**
     * Actualiza una raza existente.
     *
     * PUT /api/v1/razas/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<RazaDTO> actualizarRaza(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRazaRequest request) {
        log.info("PUT /api/v1/razas/{} - Actualizando raza", id);
        RazaDTO razaActualizada = razaService.actualizarRaza(id, request);
        return ResponseEntity.ok(razaActualizada);
    }

    /**
     * Activa una raza.
     *
     * PATCH /api/v1/razas/{id}/activar
     */
    @PatchMapping("/{id}/activar")
    public ResponseEntity<RazaDTO> activarRaza(@PathVariable Long id) {
        log.info("PATCH /api/v1/razas/{}/activar - Activando raza", id);
        RazaDTO razaActualizada = razaService.activarRaza(id);
        return ResponseEntity.ok(razaActualizada);
    }

    /**
     * Desactiva una raza (no la elimina).
     *
     * PATCH /api/v1/razas/{id}/desactivar
     */
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<RazaDTO> desactivarRaza(@PathVariable Long id) {
        log.info("PATCH /api/v1/razas/{}/desactivar - Desactivando raza", id);
        RazaDTO razaActualizada = razaService.desactivarRaza(id);
        return ResponseEntity.ok(razaActualizada);
    }

    /**
     * Elimina una raza (eliminación física).
     * Solo funciona si no hay pacientes asociados.
     *
     * DELETE /api/v1/razas/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRaza(@PathVariable Long id) {
        log.info("DELETE /api/v1/razas/{} - Eliminando raza", id);
        razaService.eliminarRaza(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Cuenta razas por especie.
     *
     * GET /api/v1/razas/contar/especie/{especie}
     */
    @GetMapping("/contar/especie/{especie}")
    public ResponseEntity<Long> contarRazasPorEspecie(@PathVariable TipoEspecie especie) {
        log.info("GET /api/v1/razas/contar/especie/{}", especie);
        long cantidad = razaService.contarRazasPorEspecie(especie);
        return ResponseEntity.ok(cantidad);
    }

    /**
     * Obtiene estadísticas de razas por especie.
     *
     * GET /api/v1/razas/estadisticas
     */
    @GetMapping("/estadisticas")
    public ResponseEntity<Map<TipoEspecie, Long>> obtenerEstadisticasPorEspecie() {
        log.info("GET /api/v1/razas/estadisticas - Obteniendo estadísticas por especie");
        Map<TipoEspecie, Long> estadisticas = razaService.obtenerEstadisticasPorEspecie();
        return ResponseEntity.ok(estadisticas);
    }
}
