package com.veterinaria.application.service;

import com.veterinaria.application.dto.patients.CambiarEstadoPacienteRequest;
import com.veterinaria.application.dto.patients.CreatePacienteRequest;
import com.veterinaria.application.dto.patients.PacienteDTO;
import com.veterinaria.application.dto.patients.UpdatePacienteRequest;
import com.veterinaria.domain.enums.EstadoPaciente;
import com.veterinaria.domain.enums.TipoEspecie;

import java.util.List;

/**
 * Interfaz de servicio para gestión de Pacientes (mascotas).
 * Define operaciones de negocio para el módulo de pacientes.
 *
 * @author Sistema Veterinaria
 */
public interface PacienteService {

    /**
     * Crea un nuevo paciente en el sistema.
     *
     * @param request Datos del paciente a crear
     * @return DTO con información del paciente creado
     * @throws com.veterinaria.infrastructure.exception.ResourceNotFoundException si el cliente no existe
     * @throws IllegalArgumentException si el microchip ya existe
     */
    PacienteDTO crearPaciente(CreatePacienteRequest request);

    /**
     * Busca un paciente por su ID.
     *
     * @param id ID del paciente
     * @return DTO con información del paciente
     * @throws com.veterinaria.infrastructure.exception.ResourceNotFoundException si no existe
     */
    PacienteDTO obtenerPacientePorId(Long id);

    /**
     * Obtiene todos los pacientes registrados.
     *
     * @return Lista de DTOs de pacientes
     */
    List<PacienteDTO> obtenerTodosLosPacientes();

    /**
     * Actualiza información de un paciente existente.
     *
     * @param id ID del paciente a actualizar
     * @param request Datos actualizados
     * @return DTO con información actualizada
     * @throws com.veterinaria.infrastructure.exception.ResourceNotFoundException si no existe
     * @throws IllegalArgumentException si el nuevo microchip ya existe en otro paciente
     */
    PacienteDTO actualizarPaciente(Long id, UpdatePacienteRequest request);

    /**
     * Elimina un paciente del sistema.
     *
     * @param id ID del paciente a eliminar
     * @throws com.veterinaria.infrastructure.exception.ResourceNotFoundException si no existe
     */
    void eliminarPaciente(Long id);

    /**
     * Busca pacientes por nombre.
     *
     * @param nombre Nombre del paciente (búsqueda parcial)
     * @return Lista de pacientes que coinciden
     */
    List<PacienteDTO> buscarPorNombre(String nombre);

    /**
     * Busca pacientes por especie.
     *
     * @param especie Tipo de especie
     * @return Lista de pacientes de la especie especificada
     */
    List<PacienteDTO> buscarPorEspecie(TipoEspecie especie);

    /**
     * Busca pacientes por estado.
     *
     * @param estado Estado del paciente
     * @return Lista de pacientes con el estado especificado
     */
    List<PacienteDTO> buscarPorEstado(EstadoPaciente estado);

    /**
     * Busca pacientes de un cliente específico.
     *
     * @param clienteId ID del cliente
     * @return Lista de pacientes del cliente
     * @throws com.veterinaria.infrastructure.exception.ResourceNotFoundException si el cliente no existe
     */
    List<PacienteDTO> buscarPorCliente(Long clienteId);

    /**
     * Busca pacientes activos de un cliente.
     *
     * @param clienteId ID del cliente
     * @return Lista de pacientes activos del cliente
     * @throws com.veterinaria.infrastructure.exception.ResourceNotFoundException si el cliente no existe
     */
    List<PacienteDTO> buscarPacientesActivosPorCliente(Long clienteId);

    /**
     * Busca paciente por microchip.
     *
     * @param microchip Número de microchip
     * @return DTO con información del paciente
     * @throws com.veterinaria.infrastructure.exception.ResourceNotFoundException si no existe
     */
    PacienteDTO buscarPorMicrochip(String microchip);

    /**
     * Marca un paciente como fallecido.
     *
     * @param id ID del paciente
     * @return DTO con información actualizada
     * @throws com.veterinaria.infrastructure.exception.ResourceNotFoundException si no existe
     * @deprecated Usar {@link #cambiarEstado(Long, CambiarEstadoPacienteRequest)} en su lugar
     */
    @Deprecated
    PacienteDTO marcarComoFallecido(Long id);

    /**
     * Reactiva un paciente (cambia estado de FALLECIDO/INACTIVO a ACTIVO).
     *
     * @param id ID del paciente
     * @return DTO con información actualizada
     * @throws com.veterinaria.infrastructure.exception.ResourceNotFoundException si no existe
     * @deprecated Usar {@link #cambiarEstado(Long, CambiarEstadoPacienteRequest)} en su lugar
     */
    @Deprecated
    PacienteDTO reactivarPaciente(Long id);

    /**
     * Cambia el estado de un paciente registrando motivo y fecha.
     * Implementa HU-23: Registro de Mascotas Fallecidas o Inactivas
     *
     * @param id ID del paciente
     * @param request Datos del cambio de estado
     * @return DTO con información actualizada
     * @throws com.veterinaria.infrastructure.exception.ResourceNotFoundException si no existe
     * @throws IllegalStateException si el cambio de estado no es válido
     */
    PacienteDTO cambiarEstado(Long id, CambiarEstadoPacienteRequest request);

    /**
     * Cuenta pacientes por estado.
     *
     * @param estado Estado a contar
     * @return Cantidad de pacientes en ese estado
     */
    long contarPorEstado(EstadoPaciente estado);

    /**
     * Busca pacientes por especie y estado.
     *
     * @param especie Tipo de especie
     * @param estado Estado del paciente
     * @return Lista de pacientes que cumplen ambos criterios
     */
    List<PacienteDTO> buscarPorEspecieYEstado(TipoEspecie especie, EstadoPaciente estado);
}
