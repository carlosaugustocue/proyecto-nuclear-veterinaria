package com.veterinaria.application.service;

import com.veterinaria.application.dto.medical.HistorialClinicoDTO;
import com.veterinaria.application.dto.medical.UpdateHistorialClinicoRequest;

import java.util.List;

/**
 * Interfaz de servicio para la gestión de Historiales Clínicos.
 *
 * @author Sistema Veterinaria
 */
public interface HistorialClinicoService {

    /**
     * Crea un historial clínico para un paciente
     * @param pacienteId ID del paciente
     * @return el DTO del historial creado
     */
    HistorialClinicoDTO crear(Long pacienteId);

    /**
     * Actualiza un historial clínico
     * @param id ID del historial
     * @param request datos a actualizar
     * @return el DTO del historial actualizado
     */
    HistorialClinicoDTO actualizar(Long id, UpdateHistorialClinicoRequest request);

    /**
     * Busca un historial clínico por ID
     * @param id el ID
     * @return el DTO del historial
     */
    HistorialClinicoDTO obtenerPorId(Long id);

    /**
     * Busca el historial clínico de un paciente
     * @param pacienteId ID del paciente
     * @return el DTO del historial
     */
    HistorialClinicoDTO obtenerPorPaciente(Long pacienteId);

    /**
     * Lista todos los historiales clínicos
     * @return lista de DTOs
     */
    List<HistorialClinicoDTO> listarTodos();

    /**
     * Lista historiales con alergias registradas
     * @return lista de DTOs
     */
    List<HistorialClinicoDTO> listarConAlergias();

    /**
     * Lista historiales con condiciones crónicas
     * @return lista de DTOs
     */
    List<HistorialClinicoDTO> listarConCondicionesCronicas();

    /**
     * Verifica si un paciente tiene historial clínico
     * @param pacienteId ID del paciente
     * @return true si existe
     */
    boolean existeHistorialPaciente(Long pacienteId);

    /**
     * Elimina un historial clínico (soft delete)
     * @param id ID del historial
     */
    void eliminar(Long id);
}
