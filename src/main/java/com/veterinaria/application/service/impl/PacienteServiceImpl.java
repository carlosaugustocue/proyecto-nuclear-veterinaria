package com.veterinaria.application.service.impl;

import com.veterinaria.application.dto.patients.CambiarEstadoPacienteRequest;
import com.veterinaria.application.dto.patients.CreatePacienteRequest;
import com.veterinaria.application.dto.patients.PacienteDTO;
import com.veterinaria.application.dto.patients.UpdatePacienteRequest;
import com.veterinaria.application.mapper.PacienteMapper;
import com.veterinaria.application.repository.ClienteRepository;
import com.veterinaria.application.repository.HistorialEstadoPacienteRepository;
import com.veterinaria.application.repository.PacienteRepository;
import com.veterinaria.application.service.PacienteService;
import com.veterinaria.domain.entity.patients.Cliente;
import com.veterinaria.domain.entity.patients.HistorialEstadoPaciente;
import com.veterinaria.domain.entity.patients.Paciente;
import com.veterinaria.domain.enums.EstadoPaciente;
import com.veterinaria.domain.enums.TipoEspecie;
import com.veterinaria.domain.factory.PacienteFactory;
import com.veterinaria.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de gestión de Pacientes.
 * Contiene lógica de negocio para operaciones CRUD y consultas específicas.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final ClienteRepository clienteRepository;
    private final HistorialEstadoPacienteRepository historialEstadoRepository;
    private final PacienteMapper pacienteMapper;
    private final PacienteFactory pacienteFactory;

    private static final String PACIENTE_NO_ENCONTRADO = "Paciente no encontrado con ID: %d";
    private static final String PACIENTE_NO_ENCONTRADO_MICROCHIP = "Paciente no encontrado con microchip: %s";
    private static final String CLIENTE_NO_ENCONTRADO = "Cliente no encontrado con ID: %d";
    private static final String MICROCHIP_YA_EXISTE = "Ya existe un paciente con el microchip: %s";

    @Override
    @Transactional
    public PacienteDTO crearPaciente(CreatePacienteRequest request) {
        log.info("Creando nuevo paciente con nombre: {} para cliente ID: {}",
            request.nombre(), request.clienteId());

        // Validar que el cliente exista
        Cliente cliente = clienteRepository.findById(request.clienteId())
            .orElseThrow(() -> {
                log.warn("Cliente no encontrado con ID: {}", request.clienteId());
                return new ResourceNotFoundException(
                    String.format(CLIENTE_NO_ENCONTRADO, request.clienteId())
                );
            });

        // Validar microchip único si se proporciona
        if (request.microchip() != null && !request.microchip().isBlank()) {
            if (pacienteRepository.existsByMicrochip(request.microchip())) {
                log.warn("Intento de crear paciente con microchip duplicado: {}", request.microchip());
                throw new IllegalArgumentException(
                    String.format(MICROCHIP_YA_EXISTE, request.microchip())
                );
            }
        }

        // Construir Map con los datos del paciente para el Factory
        Map<String, Object> datosPaciente = new HashMap<>();
        datosPaciente.put("nombre", request.nombre());
        datosPaciente.put("raza", request.raza());
        datosPaciente.put("fechaNacimiento", request.fechaNacimiento());
        datosPaciente.put("sexo", request.sexo());
        datosPaciente.put("color", request.color());
        datosPaciente.put("pesoKg", request.pesoKg());
        datosPaciente.put("microchip", request.microchip());
        datosPaciente.put("observaciones", request.observaciones());
        datosPaciente.put("fotoUrl", request.fotoUrl());

        // Usar Factory para crear el paciente correcto (Perro o Gato)
        Paciente paciente = pacienteFactory.crearPaciente(request.especie(), datosPaciente);

        // Establecer relación con cliente
        cliente.agregarPaciente(paciente);

        // Guardar (cascada guardará el paciente también)
        clienteRepository.save(cliente);

        log.info("Paciente creado exitosamente con ID: {}", paciente.getId());

        return pacienteMapper.toDTO(paciente);
    }

    @Override
    public PacienteDTO obtenerPacientePorId(Long id) {
        log.debug("Buscando paciente con ID: {}", id);

        Paciente paciente = pacienteRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("Paciente no encontrado con ID: {}", id);
                return new ResourceNotFoundException(String.format(PACIENTE_NO_ENCONTRADO, id));
            });

        return pacienteMapper.toDTO(paciente);
    }

    @Override
    public List<PacienteDTO> obtenerTodosLosPacientes() {
        log.debug("Obteniendo todos los pacientes");

        List<Paciente> pacientes = pacienteRepository.findAll();
        log.info("Se encontraron {} pacientes", pacientes.size());

        return pacientes.stream()
            .map(pacienteMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PacienteDTO actualizarPaciente(Long id, UpdatePacienteRequest request) {
        log.info("Actualizando paciente con ID: {}", id);

        Paciente paciente = pacienteRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("Paciente no encontrado con ID: {}", id);
                return new ResourceNotFoundException(String.format(PACIENTE_NO_ENCONTRADO, id));
            });

        // Actualizar campos opcionales
        if (request.raza() != null) {
            paciente.setRaza(request.raza());
        }

        if (request.color() != null) {
            paciente.setColor(request.color());
        }

        if (request.pesoKg() != null) {
            paciente.setPesoKg(request.pesoKg());
        }

        if (request.estado() != null) {
            paciente.setEstado(request.estado());
        }

        if (request.fotoUrl() != null) {
            paciente.setFotoUrl(request.fotoUrl());
        }

        if (request.observaciones() != null) {
            paciente.setObservaciones(request.observaciones());
        }

        // Actualizar cliente si se proporciona un nuevo ID
        if (request.clienteId() != null && !request.clienteId().equals(paciente.getCliente().getId())) {
            Cliente nuevoCliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> {
                    log.warn("Cliente no encontrado con ID: {}", request.clienteId());
                    return new ResourceNotFoundException(
                        String.format(CLIENTE_NO_ENCONTRADO, request.clienteId())
                    );
                });

            // Remover del cliente anterior
            Cliente clienteAnterior = paciente.getCliente();
            if (clienteAnterior != null) {
                clienteAnterior.removerPaciente(paciente);
            }

            // Agregar al nuevo cliente
            nuevoCliente.agregarPaciente(paciente);
        }

        Paciente pacienteActualizado = pacienteRepository.save(paciente);
        log.info("Paciente actualizado exitosamente con ID: {}", pacienteActualizado.getId());

        return pacienteMapper.toDTO(pacienteActualizado);
    }

    @Override
    @Transactional
    public void eliminarPaciente(Long id) {
        log.info("Eliminando paciente con ID: {}", id);

        Paciente paciente = pacienteRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("Paciente no encontrado con ID: {}", id);
                return new ResourceNotFoundException(String.format(PACIENTE_NO_ENCONTRADO, id));
            });

        // Remover relación con cliente antes de eliminar
        Cliente cliente = paciente.getCliente();
        if (cliente != null) {
            cliente.removerPaciente(paciente);
        }

        pacienteRepository.delete(paciente);
        log.info("Paciente eliminado exitosamente con ID: {}", id);
    }

    @Override
    public List<PacienteDTO> buscarPorNombre(String nombre) {
        log.debug("Buscando pacientes por nombre: {}", nombre);

        List<Paciente> pacientes = pacienteRepository.findByNombreContainingIgnoreCase(nombre);
        log.info("Se encontraron {} pacientes con el nombre: {}", pacientes.size(), nombre);

        return pacientes.stream()
            .map(pacienteMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<PacienteDTO> buscarPorEspecie(TipoEspecie especie) {
        log.debug("Buscando pacientes por especie: {}", especie);

        List<Paciente> pacientes = pacienteRepository.findByEspecie(especie);
        log.info("Se encontraron {} pacientes de especie {}", pacientes.size(), especie);

        return pacientes.stream()
            .map(pacienteMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<PacienteDTO> buscarPorEstado(EstadoPaciente estado) {
        log.debug("Buscando pacientes por estado: {}", estado);

        List<Paciente> pacientes = pacienteRepository.findByEstado(estado);
        log.info("Se encontraron {} pacientes con estado {}", pacientes.size(), estado);

        return pacientes.stream()
            .map(pacienteMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<PacienteDTO> buscarPorCliente(Long clienteId) {
        log.debug("Buscando pacientes del cliente ID: {}", clienteId);

        // Validar que el cliente exista
        if (!clienteRepository.existsById(clienteId)) {
            log.warn("Cliente no encontrado con ID: {}", clienteId);
            throw new ResourceNotFoundException(String.format(CLIENTE_NO_ENCONTRADO, clienteId));
        }

        List<Paciente> pacientes = pacienteRepository.findByClienteId(clienteId);
        log.info("Se encontraron {} pacientes para el cliente ID: {}", pacientes.size(), clienteId);

        return pacientes.stream()
            .map(pacienteMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<PacienteDTO> buscarPacientesActivosPorCliente(Long clienteId) {
        log.debug("Buscando pacientes activos del cliente ID: {}", clienteId);

        // Validar que el cliente exista
        if (!clienteRepository.existsById(clienteId)) {
            log.warn("Cliente no encontrado con ID: {}", clienteId);
            throw new ResourceNotFoundException(String.format(CLIENTE_NO_ENCONTRADO, clienteId));
        }

        List<Paciente> pacientes = pacienteRepository.findPacientesActivosByClienteId(clienteId);
        log.info("Se encontraron {} pacientes activos para el cliente ID: {}",
            pacientes.size(), clienteId);

        return pacientes.stream()
            .map(pacienteMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public PacienteDTO buscarPorMicrochip(String microchip) {
        log.debug("Buscando paciente con microchip: {}", microchip);

        Paciente paciente = pacienteRepository.findByMicrochip(microchip)
            .orElseThrow(() -> {
                log.warn("Paciente no encontrado con microchip: {}", microchip);
                return new ResourceNotFoundException(
                    String.format(PACIENTE_NO_ENCONTRADO_MICROCHIP, microchip)
                );
            });

        return pacienteMapper.toDTO(paciente);
    }

    @Override
    @Transactional
    public PacienteDTO marcarComoFallecido(Long id) {
        log.info("Marcando paciente como fallecido con ID: {}", id);

        Paciente paciente = pacienteRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("Paciente no encontrado con ID: {}", id);
                return new ResourceNotFoundException(String.format(PACIENTE_NO_ENCONTRADO, id));
            });

        paciente.marcarComoFallecido();
        Paciente pacienteActualizado = pacienteRepository.save(paciente);

        log.info("Paciente marcado como fallecido con ID: {}", pacienteActualizado.getId());

        return pacienteMapper.toDTO(pacienteActualizado);
    }

    @Override
    @Transactional
    public PacienteDTO reactivarPaciente(Long id) {
        log.info("Reactivando paciente con ID: {}", id);

        Paciente paciente = pacienteRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("Paciente no encontrado con ID: {}", id);
                return new ResourceNotFoundException(String.format(PACIENTE_NO_ENCONTRADO, id));
            });

        paciente.reactivar();
        Paciente pacienteActualizado = pacienteRepository.save(paciente);

        log.info("Paciente reactivado con ID: {}", pacienteActualizado.getId());

        return pacienteMapper.toDTO(pacienteActualizado);
    }

    @Override
    @Transactional
    public PacienteDTO cambiarEstado(Long id, CambiarEstadoPacienteRequest request) {
        log.info("Cambiando estado del paciente ID: {} a {}", id, request.nuevoEstado());

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Paciente no encontrado con ID: {}", id);
                    return new ResourceNotFoundException(String.format(PACIENTE_NO_ENCONTRADO, id));
                });

        // Guardar estado anterior
        EstadoPaciente estadoAnterior = paciente.getEstado();

        // Validar cambio de estado
        validarCambioEstado(estadoAnterior, request.nuevoEstado());

        // Cambiar estado del paciente
        paciente.setEstado(request.nuevoEstado());

        // Registrar en historial
        HistorialEstadoPaciente historial = HistorialEstadoPaciente.builder()
                .paciente(paciente)
                .estadoAnterior(estadoAnterior)
                .nuevoEstado(request.nuevoEstado())
                .motivo(request.motivo())
                .fechaCambio(request.fechaCambio() != null ? request.fechaCambio() : LocalDate.now())
                .build();

        historialEstadoRepository.save(historial);

        Paciente pacienteActualizado = pacienteRepository.save(paciente);

        log.info("Estado del paciente actualizado de {} a {}. Motivo: {}",
                estadoAnterior, request.nuevoEstado(), request.motivo());

        return pacienteMapper.toDTO(pacienteActualizado);
    }

    /**
     * Valida que el cambio de estado sea válido
     */
    private void validarCambioEstado(EstadoPaciente estadoAnterior, EstadoPaciente nuevoEstado) {
        // Un paciente fallecido no puede cambiar a otro estado
        if (estadoAnterior == EstadoPaciente.FALLECIDO) {
            throw new IllegalStateException(
                    "No se puede cambiar el estado de un paciente fallecido. " +
                    "Un paciente fallecido debe permanecer en ese estado permanentemente."
            );
        }

        // No se puede cambiar al mismo estado
        if (estadoAnterior == nuevoEstado) {
            throw new IllegalStateException(
                    String.format("El paciente ya se encuentra en estado %s", nuevoEstado)
            );
        }
    }

    @Override
    public long contarPorEstado(EstadoPaciente estado) {
        log.debug("Contando pacientes por estado: {}", estado);

        long cantidad = pacienteRepository.countByEstado(estado);
        log.info("Se encontraron {} pacientes con estado {}", cantidad, estado);

        return cantidad;
    }

    @Override
    public List<PacienteDTO> buscarPorEspecieYEstado(TipoEspecie especie, EstadoPaciente estado) {
        log.debug("Buscando pacientes por especie: {} y estado: {}", especie, estado);

        List<Paciente> pacientes = pacienteRepository.findByEspecieAndEstado(especie, estado);
        log.info("Se encontraron {} pacientes de especie {} con estado {}",
            pacientes.size(), especie, estado);

        return pacientes.stream()
            .map(pacienteMapper::toDTO)
            .collect(Collectors.toList());
    }
}
