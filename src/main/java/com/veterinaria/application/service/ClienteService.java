package com.veterinaria.application.service;

import com.veterinaria.application.dto.cliente.ClienteDTO;
import com.veterinaria.application.dto.cliente.CreateClienteRequest;
import com.veterinaria.application.dto.cliente.UpdateClienteRequest;

import java.util.List;

/**
 * Interfaz de servicio para gestión de Clientes (propietarios).
 * Define operaciones de negocio para el módulo de clientes.
 *
 * @author Sistema Veterinaria
 */
public interface ClienteService {

    /**
     * Crea un nuevo cliente en el sistema.
     *
     * @param request Datos del cliente a crear
     * @return DTO con información del cliente creado
     * @throws IllegalArgumentException si el DNI o email ya existe
     */
    ClienteDTO crearCliente(CreateClienteRequest request);

    /**
     * Busca un cliente por su ID.
     *
     * @param id ID del cliente
     * @return DTO con información del cliente
     * @throws com.veterinaria.infrastructure.exception.ResourceNotFoundException si no existe
     */
    ClienteDTO obtenerClientePorId(Long id);

    /**
     * Obtiene todos los clientes registrados.
     *
     * @return Lista de DTOs de clientes
     */
    List<ClienteDTO> obtenerTodosLosClientes();

    /**
     * Actualiza información de un cliente existente.
     *
     * @param id ID del cliente a actualizar
     * @param request Datos actualizados
     * @return DTO con información actualizada
     * @throws com.veterinaria.infrastructure.exception.ResourceNotFoundException si no existe
     * @throws IllegalArgumentException si el nuevo DNI/email ya existe en otro cliente
     */
    ClienteDTO actualizarCliente(Long id, UpdateClienteRequest request);

    /**
     * Elimina un cliente del sistema.
     *
     * @param id ID del cliente a eliminar
     * @throws com.veterinaria.infrastructure.exception.ResourceNotFoundException si no existe
     * @throws IllegalStateException si el cliente tiene pacientes activos
     */
    void eliminarCliente(Long id);

    /**
     * Busca clientes por nombre o apellido.
     *
     * @param termino Término de búsqueda
     * @return Lista de clientes que coinciden
     */
    List<ClienteDTO> buscarPorNombreOApellido(String termino);

    /**
     * Busca cliente por DNI.
     *
     * @param dni DNI del cliente
     * @return DTO con información del cliente
     * @throws com.veterinaria.infrastructure.exception.ResourceNotFoundException si no existe
     */
    ClienteDTO buscarPorDni(String dni);

    /**
     * Busca cliente por email.
     *
     * @param email Email del cliente
     * @return DTO con información del cliente
     * @throws com.veterinaria.infrastructure.exception.ResourceNotFoundException si no existe
     */
    ClienteDTO buscarPorEmail(String email);

    /**
     * Obtiene clientes que tienen pacientes activos.
     *
     * @return Lista de clientes con pacientes activos
     */
    List<ClienteDTO> obtenerClientesConPacientesActivos();

    /**
     * Busca clientes por ciudad.
     *
     * @param ciudad Nombre de la ciudad
     * @return Lista de clientes en la ciudad especificada
     */
    List<ClienteDTO> buscarPorCiudad(String ciudad);
}
