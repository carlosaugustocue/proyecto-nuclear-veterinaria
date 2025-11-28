package com.veterinaria.presentation.controller;

import com.veterinaria.application.dto.patients.ClienteDTO;
import com.veterinaria.application.dto.patients.CreateClienteRequest;
import com.veterinaria.application.dto.patients.UpdateClienteRequest;
import com.veterinaria.application.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestión de Clientes (propietarios).
 * Expone endpoints para operaciones CRUD y consultas específicas.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    /**
     * Crea un nuevo cliente.
     *
     * POST /api/v1/clientes
     */
    @PostMapping
    public ResponseEntity<ClienteDTO> crearCliente(@Valid @RequestBody CreateClienteRequest request) {
        log.info("POST /api/v1/clientes - Creando cliente con DNI: {}", request.dni());
        ClienteDTO clienteCreado = clienteService.crearCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCreado);
    }

    /**
     * Obtiene un cliente por su ID.
     *
     * GET /api/v1/clientes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtenerClientePorId(@PathVariable Long id) {
        log.info("GET /api/v1/clientes/{} - Obteniendo cliente", id);
        ClienteDTO cliente = clienteService.obtenerClientePorId(id);
        return ResponseEntity.ok(cliente);
    }

    /**
     * Obtiene todos los clientes.
     *
     * GET /api/v1/clientes
     */
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> obtenerTodosLosClientes() {
        log.info("GET /api/v1/clientes - Obteniendo todos los clientes");
        List<ClienteDTO> clientes = clienteService.obtenerTodosLosClientes();
        return ResponseEntity.ok(clientes);
    }

    /**
     * Actualiza un cliente existente.
     *
     * PUT /api/v1/clientes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody UpdateClienteRequest request) {
        log.info("PUT /api/v1/clientes/{} - Actualizando cliente", id);
        ClienteDTO clienteActualizado = clienteService.actualizarCliente(id, request);
        return ResponseEntity.ok(clienteActualizado);
    }

    /**
     * Elimina un cliente.
     *
     * DELETE /api/v1/clientes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        log.info("DELETE /api/v1/clientes/{} - Eliminando cliente", id);
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Busca clientes por nombre o apellido.
     *
     * GET /api/v1/clientes/buscar?termino=xxx
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<ClienteDTO>> buscarPorNombreOApellido(
            @RequestParam String termino) {
        log.info("GET /api/v1/clientes/buscar?termino={}", termino);
        List<ClienteDTO> clientes = clienteService.buscarPorNombreOApellido(termino);
        return ResponseEntity.ok(clientes);
    }

    /**
     * Busca cliente por DNI.
     *
     * GET /api/v1/clientes/dni/{dni}
     */
    @GetMapping("/dni/{dni}")
    public ResponseEntity<ClienteDTO> buscarPorDni(@PathVariable String dni) {
        log.info("GET /api/v1/clientes/dni/{}", dni);
        ClienteDTO cliente = clienteService.buscarPorDni(dni);
        return ResponseEntity.ok(cliente);
    }

    /**
     * Busca cliente por email.
     *
     * GET /api/v1/clientes/email?email=xxx
     */
    @GetMapping("/email")
    public ResponseEntity<ClienteDTO> buscarPorEmail(@RequestParam String email) {
        log.info("GET /api/v1/clientes/email?email={}", email);
        ClienteDTO cliente = clienteService.buscarPorEmail(email);
        return ResponseEntity.ok(cliente);
    }

    /**
     * Obtiene clientes que tienen pacientes activos.
     *
     * GET /api/v1/clientes/con-pacientes-activos
     */
    @GetMapping("/con-pacientes-activos")
    public ResponseEntity<List<ClienteDTO>> obtenerClientesConPacientesActivos() {
        log.info("GET /api/v1/clientes/con-pacientes-activos");
        List<ClienteDTO> clientes = clienteService.obtenerClientesConPacientesActivos();
        return ResponseEntity.ok(clientes);
    }

    /**
     * Busca clientes por ciudad.
     *
     * GET /api/v1/clientes/ciudad/{ciudad}
     */
    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<ClienteDTO>> buscarPorCiudad(@PathVariable String ciudad) {
        log.info("GET /api/v1/clientes/ciudad/{}", ciudad);
        List<ClienteDTO> clientes = clienteService.buscarPorCiudad(ciudad);
        return ResponseEntity.ok(clientes);
    }
}
