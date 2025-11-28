package com.veterinaria.application.service.impl;

import com.veterinaria.application.dto.patients.ClienteDTO;
import com.veterinaria.application.dto.patients.CreateClienteRequest;
import com.veterinaria.application.dto.patients.UpdateClienteRequest;
import com.veterinaria.application.mapper.ClienteMapper;
import com.veterinaria.application.repository.ClienteRepository;
import com.veterinaria.application.service.ClienteService;
import com.veterinaria.domain.entity.patients.Cliente;
import com.veterinaria.domain.valueobject.Direccion;
import com.veterinaria.domain.valueobject.Email;
import com.veterinaria.domain.valueobject.Telefono;
import com.veterinaria.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de gestión de Clientes.
 * Contiene lógica de negocio para operaciones CRUD y consultas específicas.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    private static final String CLIENTE_NO_ENCONTRADO = "Cliente no encontrado con ID: %d";
    private static final String CLIENTE_NO_ENCONTRADO_DNI = "Cliente no encontrado con DNI: %s";
    private static final String CLIENTE_NO_ENCONTRADO_EMAIL = "Cliente no encontrado con email: %s";
    private static final String DNI_YA_EXISTE = "Ya existe un cliente con el DNI: %s";
    private static final String EMAIL_YA_EXISTE = "Ya existe un cliente con el email: %s";
    private static final String CLIENTE_CON_PACIENTES_ACTIVOS = "No se puede eliminar el cliente porque tiene %d pacientes activos";

    @Override
    @Transactional
    public ClienteDTO crearCliente(CreateClienteRequest request) {
        log.info("Creando nuevo cliente con DNI: {}", request.dni());

        // Validar que no exista el DNI
        if (clienteRepository.existsByDni(request.dni())) {
            log.warn("Intento de crear cliente con DNI duplicado: {}", request.dni());
            throw new IllegalArgumentException(String.format(DNI_YA_EXISTE, request.dni()));
        }

        // Validar que no exista el email
        if (clienteRepository.existsByEmail(request.email())) {
            log.warn("Intento de crear cliente con email duplicado: {}", request.email());
            throw new IllegalArgumentException(String.format(EMAIL_YA_EXISTE, request.email()));
        }

        // Crear Value Objects
        Email emailVO = new Email(request.email());
        Telefono telefonoVO = new Telefono(request.telefono());
        Direccion direccionVO = new Direccion(
            request.direccion(),
            request.ciudad(),
            request.departamento(),
            request.codigoPostal()
        );

        // Construir entidad Cliente
        Cliente cliente = Cliente.builder()
            .nombre(request.nombre())
            .apellido(request.apellido())
            .dni(request.dni())
            .email(emailVO.getValor())
            .telefono(telefonoVO.obtenerNumeroCompleto())
            .direccion(direccionVO.getCalle())
            .ciudad(direccionVO.getCiudad())
            .departamento(direccionVO.getDepartamento())
            .codigoPostal(direccionVO.getCodigoPostal())
            .observaciones(request.observaciones())
            .build();

        Cliente clienteGuardado = clienteRepository.save(cliente);
        log.info("Cliente creado exitosamente con ID: {}", clienteGuardado.getId());

        return clienteMapper.toDTO(clienteGuardado);
    }

    @Override
    public ClienteDTO obtenerClientePorId(Long id) {
        log.debug("Buscando cliente con ID: {}", id);

        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("Cliente no encontrado con ID: {}", id);
                return new ResourceNotFoundException(String.format(CLIENTE_NO_ENCONTRADO, id));
            });

        return clienteMapper.toDTO(cliente);
    }

    @Override
    public List<ClienteDTO> obtenerTodosLosClientes() {
        log.debug("Obteniendo todos los clientes");

        List<Cliente> clientes = clienteRepository.findAll();
        log.info("Se encontraron {} clientes", clientes.size());

        return clientes.stream()
            .map(clienteMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ClienteDTO actualizarCliente(Long id, UpdateClienteRequest request) {
        log.info("Actualizando cliente con ID: {}", id);

        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("Cliente no encontrado con ID: {}", id);
                return new ResourceNotFoundException(String.format(CLIENTE_NO_ENCONTRADO, id));
            });

        // Validar email si cambió
        if (request.email() != null && !request.email().equals(cliente.getEmail())) {
            if (clienteRepository.existsByEmail(request.email())) {
                log.warn("Intento de actualizar con email duplicado: {}", request.email());
                throw new IllegalArgumentException(String.format(EMAIL_YA_EXISTE, request.email()));
            }
            Email emailVO = new Email(request.email());
            cliente.setEmail(emailVO.getValor());
        }

        // Actualizar teléfono
        if (request.telefono() != null) {
            Telefono telefonoVO = new Telefono(request.telefono());
            cliente.setTelefono(telefonoVO.obtenerNumeroCompleto());
        }

        // Actualizar dirección si algún campo está presente
        if (request.direccion() != null || request.ciudad() != null ||
            request.departamento() != null || request.codigoPostal() != null) {

            Direccion direccionVO = new Direccion(
                request.direccion() != null ? request.direccion() : cliente.getDireccion(),
                request.ciudad() != null ? request.ciudad() : cliente.getCiudad(),
                request.departamento() != null ? request.departamento() : cliente.getDepartamento(),
                request.codigoPostal() != null ? request.codigoPostal() : cliente.getCodigoPostal()
            );

            cliente.setDireccion(direccionVO.getCalle());
            cliente.setCiudad(direccionVO.getCiudad());
            cliente.setDepartamento(direccionVO.getDepartamento());
            cliente.setCodigoPostal(direccionVO.getCodigoPostal());
        }

        // Actualizar observaciones
        if (request.observaciones() != null) {
            cliente.setObservaciones(request.observaciones());
        }

        Cliente clienteActualizado = clienteRepository.save(cliente);
        log.info("Cliente actualizado exitosamente con ID: {}", clienteActualizado.getId());

        return clienteMapper.toDTO(clienteActualizado);
    }

    @Override
    @Transactional
    public void eliminarCliente(Long id) {
        log.info("Eliminando cliente con ID: {}", id);

        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("Cliente no encontrado con ID: {}", id);
                return new ResourceNotFoundException(String.format(CLIENTE_NO_ENCONTRADO, id));
            });

        // Validar que no tenga pacientes activos
        long pacientesActivos = cliente.contarPacientesActivos();
        if (pacientesActivos > 0) {
            log.warn("Intento de eliminar cliente con {} pacientes activos", pacientesActivos);
            throw new IllegalStateException(
                String.format(CLIENTE_CON_PACIENTES_ACTIVOS, pacientesActivos)
            );
        }

        clienteRepository.delete(cliente);
        log.info("Cliente eliminado exitosamente con ID: {}", id);
    }

    @Override
    public List<ClienteDTO> buscarPorNombreOApellido(String termino) {
        log.debug("Buscando clientes por término: {}", termino);

        List<Cliente> clientes = clienteRepository.buscarPorNombreOApellido(termino);
        log.info("Se encontraron {} clientes con el término: {}", clientes.size(), termino);

        return clientes.stream()
            .map(clienteMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO buscarPorDni(String dni) {
        log.debug("Buscando cliente con DNI: {}", dni);

        Cliente cliente = clienteRepository.findByDni(dni)
            .orElseThrow(() -> {
                log.warn("Cliente no encontrado con DNI: {}", dni);
                return new ResourceNotFoundException(String.format(CLIENTE_NO_ENCONTRADO_DNI, dni));
            });

        return clienteMapper.toDTO(cliente);
    }

    @Override
    public ClienteDTO buscarPorEmail(String email) {
        log.debug("Buscando cliente con email: {}", email);

        Cliente cliente = clienteRepository.findByEmail(email)
            .orElseThrow(() -> {
                log.warn("Cliente no encontrado con email: {}", email);
                return new ResourceNotFoundException(String.format(CLIENTE_NO_ENCONTRADO_EMAIL, email));
            });

        return clienteMapper.toDTO(cliente);
    }

    @Override
    public List<ClienteDTO> obtenerClientesConPacientesActivos() {
        log.debug("Obteniendo clientes con pacientes activos");

        List<Cliente> clientes = clienteRepository.findClientesConPacientesActivos();
        log.info("Se encontraron {} clientes con pacientes activos", clientes.size());

        return clientes.stream()
            .map(clienteMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<ClienteDTO> buscarPorCiudad(String ciudad) {
        log.debug("Buscando clientes en la ciudad: {}", ciudad);

        List<Cliente> clientes = clienteRepository.findByCiudad(ciudad);
        log.info("Se encontraron {} clientes en {}", clientes.size(), ciudad);

        return clientes.stream()
            .map(clienteMapper::toDTO)
            .collect(Collectors.toList());
    }
}
