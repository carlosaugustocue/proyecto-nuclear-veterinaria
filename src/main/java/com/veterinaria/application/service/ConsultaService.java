package com.veterinaria.application.service;

import com.veterinaria.application.dto.medical.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Interfaz de servicio para la gestión de Consultas médicas.
 *
 * @author Sistema Veterinaria
 */
public interface ConsultaService {

    /**
     * Crea una nueva consulta médica
     * @param request datos de la consulta
     * @return el DTO de la consulta creada
     */
    ConsultaDTO crear(CreateConsultaRequest request);

    /**
     * Actualiza una consulta existente
     * @param id ID de la consulta
     * @param request datos a actualizar
     * @return el DTO de la consulta actualizada
     */
    ConsultaDTO actualizar(Long id, UpdateConsultaRequest request);

    /**
     * Finaliza una consulta
     * @param id ID de la consulta
     * @param request datos de finalización
     * @return el DTO de la consulta finalizada
     */
    ConsultaDTO finalizar(Long id, FinalizarConsultaRequest request);

    /**
     * Busca una consulta por ID
     * @param id el ID
     * @return el DTO de la consulta
     */
    ConsultaDTO obtenerPorId(Long id);

    /**
     * Lista consultas por paciente
     * @param pacienteId ID del paciente
     * @return lista de DTOs
     */
    List<ConsultaDTO> listarPorPaciente(Long pacienteId);

    /**
     * Lista consultas por veterinario
     * @param veterinarioId ID del veterinario
     * @return lista de DTOs
     */
    List<ConsultaDTO> listarPorVeterinario(Long veterinarioId);

    /**
     * Lista consultas por fecha
     * @param fecha la fecha
     * @return lista de DTOs
     */
    List<ConsultaDTO> listarPorFecha(LocalDate fecha);

    /**
     * Lista consultas en curso
     * @return lista de DTOs
     */
    List<ConsultaDTO> listarEnCurso();

    /**
     * Lista consultas que requieren seguimiento
     * @return lista de DTOs
     */
    List<ConsultaDTO> listarConSeguimiento();

    /**
     * Obtiene la última consulta de un paciente
     * @param pacienteId ID del paciente
     * @return el DTO de la última consulta
     */
    ConsultaDTO obtenerUltimaConsultaPaciente(Long pacienteId);

    /**
     * Agrega un diagnóstico a la consulta
     * @param consultaId ID de la consulta
     * @param request datos del diagnóstico
     * @return el DTO del diagnóstico creado
     */
    DiagnosticoDTO agregarDiagnostico(Long consultaId, CreateDiagnosticoRequest request);

    /**
     * Agrega un tratamiento a la consulta
     * @param consultaId ID de la consulta
     * @param request datos del tratamiento
     * @return el DTO del tratamiento creado
     */
    TratamientoDTO agregarTratamiento(Long consultaId, CreateTratamientoRequest request);

    /**
     * Elimina una consulta (soft delete)
     * @param id ID de la consulta
     */
    void eliminar(Long id);
}
