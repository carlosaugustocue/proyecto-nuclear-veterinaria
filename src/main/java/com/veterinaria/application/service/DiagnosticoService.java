package com.veterinaria.application.service;

import com.veterinaria.application.dto.medical.CreateDiagnosticoRequest;
import com.veterinaria.application.dto.medical.DiagnosticoDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * Interfaz de servicio para la gestión de Diagnósticos médicos.
 *
 * @author Sistema Veterinaria
 */
public interface DiagnosticoService {

    /**
     * Crea un nuevo diagnóstico
     * @param request datos del diagnóstico
     * @return el DTO del diagnóstico creado
     */
    DiagnosticoDTO crear(CreateDiagnosticoRequest request);

    /**
     * Busca un diagnóstico por ID
     * @param id el ID
     * @return el DTO del diagnóstico
     */
    DiagnosticoDTO obtenerPorId(Long id);

    /**
     * Lista diagnósticos por paciente
     * @param pacienteId ID del paciente
     * @return lista de DTOs
     */
    List<DiagnosticoDTO> listarPorPaciente(Long pacienteId);

    /**
     * Lista diagnósticos por tipo
     * @param tipoDiagnostico tipo de diagnóstico
     * @return lista de DTOs
     */
    List<DiagnosticoDTO> listarPorTipo(String tipoDiagnostico);

    /**
     * Lista diagnósticos por gravedad
     * @param gravedad la gravedad
     * @return lista de DTOs
     */
    List<DiagnosticoDTO> listarPorGravedad(String gravedad);

    /**
     * Lista diagnósticos graves o críticos
     * @return lista de DTOs
     */
    List<DiagnosticoDTO> listarGravesOCriticos();

    /**
     * Lista diagnósticos principales
     * @return lista de DTOs
     */
    List<DiagnosticoDTO> listarPrincipales();

    /**
     * Lista diagnósticos por código CIE-10
     * @param codigoCie10 código CIE-10
     * @return lista de DTOs
     */
    List<DiagnosticoDTO> listarPorCodigoCie10(String codigoCie10);

    /**
     * Busca diagnósticos por descripción (texto)
     * @param descripcion texto a buscar
     * @return lista de DTOs
     */
    List<DiagnosticoDTO> buscarPorDescripcion(String descripcion);

    /**
     * Lista diagnósticos en un rango de fechas
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de DTOs
     */
    List<DiagnosticoDTO> listarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin);

    /**
     * Elimina un diagnóstico (soft delete)
     * @param id ID del diagnóstico
     */
    void eliminar(Long id);
}
