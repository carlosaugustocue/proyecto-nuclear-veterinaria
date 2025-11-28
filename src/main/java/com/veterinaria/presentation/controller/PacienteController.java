package com.veterinaria.presentation.controller;

import com.veterinaria.application.dto.patients.CambiarEstadoPacienteRequest;
import com.veterinaria.application.dto.patients.CreatePacienteRequest;
import com.veterinaria.application.dto.patients.PacienteDTO;
import com.veterinaria.application.dto.patients.UpdatePacienteRequest;
import com.veterinaria.application.service.PacienteService;
import com.veterinaria.domain.enums.EstadoPaciente;
import com.veterinaria.domain.enums.TipoEspecie;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestión de Pacientes (mascotas).
 * Expone endpoints para operaciones CRUD y consultas específicas.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    /**
     * Crea un nuevo paciente.
     *
     * POST /api/v1/pacientes
     */
    @PostMapping
    public ResponseEntity<PacienteDTO> crearPaciente(@Valid @RequestBody CreatePacienteRequest request) {
        log.info("POST /api/v1/pacientes - Creando paciente: {}", request.nombre());
        PacienteDTO pacienteCreado = pacienteService.crearPaciente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteCreado);
    }

    /**
     * Obtiene un paciente por su ID.
     *
     * GET /api/v1/pacientes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> obtenerPacientePorId(@PathVariable Long id) {
        log.info("GET /api/v1/pacientes/{} - Obteniendo paciente", id);
        PacienteDTO paciente = pacienteService.obtenerPacientePorId(id);
        return ResponseEntity.ok(paciente);
    }

    /**
     * Obtiene todos los pacientes.
     *
     * GET /api/v1/pacientes
     */
    @GetMapping
    public ResponseEntity<List<PacienteDTO>> obtenerTodosLosPacientes() {
        log.info("GET /api/v1/pacientes - Obteniendo todos los pacientes");
        List<PacienteDTO> pacientes = pacienteService.obtenerTodosLosPacientes();
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Actualiza un paciente existente.
     *
     * PUT /api/v1/pacientes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> actualizarPaciente(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePacienteRequest request) {
        log.info("PUT /api/v1/pacientes/{} - Actualizando paciente", id);
        PacienteDTO pacienteActualizado = pacienteService.actualizarPaciente(id, request);
        return ResponseEntity.ok(pacienteActualizado);
    }

    /**
     * Elimina un paciente.
     *
     * DELETE /api/v1/pacientes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id) {
        log.info("DELETE /api/v1/pacientes/{} - Eliminando paciente", id);
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Busca pacientes por nombre.
     *
     * GET /api/v1/pacientes/buscar?nombre=xxx
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<PacienteDTO>> buscarPorNombre(@RequestParam String nombre) {
        log.info("GET /api/v1/pacientes/buscar?nombre={}", nombre);
        List<PacienteDTO> pacientes = pacienteService.buscarPorNombre(nombre);
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Busca pacientes por especie.
     *
     * GET /api/v1/pacientes/especie/{especie}
     */
    @GetMapping("/especie/{especie}")
    public ResponseEntity<List<PacienteDTO>> buscarPorEspecie(@PathVariable TipoEspecie especie) {
        log.info("GET /api/v1/pacientes/especie/{}", especie);
        List<PacienteDTO> pacientes = pacienteService.buscarPorEspecie(especie);
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Busca pacientes por estado.
     *
     * GET /api/v1/pacientes/estado/{estado}
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PacienteDTO>> buscarPorEstado(@PathVariable EstadoPaciente estado) {
        log.info("GET /api/v1/pacientes/estado/{}", estado);
        List<PacienteDTO> pacientes = pacienteService.buscarPorEstado(estado);
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Busca pacientes de un cliente específico.
     *
     * GET /api/v1/pacientes/cliente/{clienteId}
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PacienteDTO>> buscarPorCliente(@PathVariable Long clienteId) {
        log.info("GET /api/v1/pacientes/cliente/{}", clienteId);
        List<PacienteDTO> pacientes = pacienteService.buscarPorCliente(clienteId);
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Busca pacientes activos de un cliente.
     *
     * GET /api/v1/pacientes/cliente/{clienteId}/activos
     */
    @GetMapping("/cliente/{clienteId}/activos")
    public ResponseEntity<List<PacienteDTO>> buscarPacientesActivosPorCliente(
            @PathVariable Long clienteId) {
        log.info("GET /api/v1/pacientes/cliente/{}/activos", clienteId);
        List<PacienteDTO> pacientes = pacienteService.buscarPacientesActivosPorCliente(clienteId);
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Busca paciente por microchip.
     *
     * GET /api/v1/pacientes/microchip/{microchip}
     */
    @GetMapping("/microchip/{microchip}")
    public ResponseEntity<PacienteDTO> buscarPorMicrochip(@PathVariable String microchip) {
        log.info("GET /api/v1/pacientes/microchip/{}", microchip);
        PacienteDTO paciente = pacienteService.buscarPorMicrochip(microchip);
        return ResponseEntity.ok(paciente);
    }

    /**
     * Marca un paciente como fallecido.
     *
     * PATCH /api/v1/pacientes/{id}/marcar-fallecido
     * @deprecated Usar {@link #cambiarEstado(Long, CambiarEstadoPacienteRequest)} en su lugar
     */
    @Deprecated
    @PatchMapping("/{id}/marcar-fallecido")
    public ResponseEntity<PacienteDTO> marcarComoFallecido(@PathVariable Long id) {
        log.info("PATCH /api/v1/pacientes/{}/marcar-fallecido", id);
        PacienteDTO pacienteActualizado = pacienteService.marcarComoFallecido(id);
        return ResponseEntity.ok(pacienteActualizado);
    }

    /**
     * Reactiva un paciente.
     *
     * PATCH /api/v1/pacientes/{id}/reactivar
     * @deprecated Usar {@link #cambiarEstado(Long, CambiarEstadoPacienteRequest)} en su lugar
     */
    @Deprecated
    @PatchMapping("/{id}/reactivar")
    public ResponseEntity<PacienteDTO> reactivarPaciente(@PathVariable Long id) {
        log.info("PATCH /api/v1/pacientes/{}/reactivar", id);
        PacienteDTO pacienteActualizado = pacienteService.reactivarPaciente(id);
        return ResponseEntity.ok(pacienteActualizado);
    }

    /**
     * Cambia el estado de un paciente con motivo y fecha.
     * Implementa HU-23: Registro de Mascotas Fallecidas o Inactivas
     *
     * PATCH /api/v1/pacientes/{id}/cambiar-estado
     */
    @PatchMapping("/{id}/cambiar-estado")
    public ResponseEntity<PacienteDTO> cambiarEstado(
            @PathVariable Long id,
            @Valid @RequestBody CambiarEstadoPacienteRequest request) {
        log.info("PATCH /api/v1/pacientes/{}/cambiar-estado a {}", id, request.nuevoEstado());
        PacienteDTO pacienteActualizado = pacienteService.cambiarEstado(id, request);
        return ResponseEntity.ok(pacienteActualizado);
    }

    /**
     * Cuenta pacientes por estado.
     *
     * GET /api/v1/pacientes/contar/estado/{estado}
     */
    @GetMapping("/contar/estado/{estado}")
    public ResponseEntity<Long> contarPorEstado(@PathVariable EstadoPaciente estado) {
        log.info("GET /api/v1/pacientes/contar/estado/{}", estado);
        long cantidad = pacienteService.contarPorEstado(estado);
        return ResponseEntity.ok(cantidad);
    }

    /**
     * Busca pacientes por especie y estado.
     *
     * GET /api/v1/pacientes/filtrar?especie=xxx&estado=xxx
     */
    @GetMapping("/filtrar")
    public ResponseEntity<List<PacienteDTO>> buscarPorEspecieYEstado(
            @RequestParam TipoEspecie especie,
            @RequestParam EstadoPaciente estado) {
        log.info("GET /api/v1/pacientes/filtrar?especie={}&estado={}", especie, estado);
        List<PacienteDTO> pacientes = pacienteService.buscarPorEspecieYEstado(especie, estado);
        return ResponseEntity.ok(pacientes);
    }
}
