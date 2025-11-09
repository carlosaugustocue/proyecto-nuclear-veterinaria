package com.veterinaria.application.service;

import com.veterinaria.application.dto.medical.CreateExamenRequest;
import com.veterinaria.application.dto.medical.ExamenDTO;
import com.veterinaria.application.dto.medical.RegistrarResultadosExamenRequest;

import java.time.LocalDate;
import java.util.List;

/**
 * Interfaz de servicio para la gestión de Exámenes médicos.
 *
 * @author Sistema Veterinaria
 */
public interface ExamenService {

    /**
     * Solicita un nuevo examen
     * @param request datos del examen
     * @return el DTO del examen creado
     */
    ExamenDTO solicitar(CreateExamenRequest request);

    /**
     * Busca un examen por ID
     * @param id el ID
     * @return el DTO del examen
     */
    ExamenDTO obtenerPorId(Long id);

    /**
     * Lista exámenes por paciente
     * @param pacienteId ID del paciente
     * @return lista de DTOs
     */
    List<ExamenDTO> listarPorPaciente(Long pacienteId);

    /**
     * Lista exámenes por consulta
     * @param consultaId ID de la consulta
     * @return lista de DTOs
     */
    List<ExamenDTO> listarPorConsulta(Long consultaId);

    /**
     * Lista exámenes por tipo
     * @param tipoExamen tipo de examen
     * @return lista de DTOs
     */
    List<ExamenDTO> listarPorTipo(String tipoExamen);

    /**
     * Lista exámenes por estado
     * @param estado el estado
     * @return lista de DTOs
     */
    List<ExamenDTO> listarPorEstado(String estado);

    /**
     * Lista exámenes pendientes
     * @return lista de DTOs
     */
    List<ExamenDTO> listarPendientes();

    /**
     * Lista exámenes completados
     * @return lista de DTOs
     */
    List<ExamenDTO> listarCompletados();

    /**
     * Lista exámenes con resultados anormales
     * @return lista de DTOs
     */
    List<ExamenDTO> listarConResultadosAnormales();

    /**
     * Lista exámenes urgentes
     * @return lista de DTOs
     */
    List<ExamenDTO> listarUrgentes();

    /**
     * Lista exámenes de laboratorio
     * @return lista de DTOs
     */
    List<ExamenDTO> listarLaboratorio();

    /**
     * Lista exámenes de imagenología
     * @return lista de DTOs
     */
    List<ExamenDTO> listarImagenologia();

    /**
     * Lista exámenes demorados
     * @param diasLimite días límite
     * @return lista de DTOs
     */
    List<ExamenDTO> listarDemorados(int diasLimite);

    /**
     * Registra los resultados de un examen
     * @param id ID del examen
     * @param request datos de los resultados
     * @return el DTO del examen actualizado
     */
    ExamenDTO registrarResultados(Long id, RegistrarResultadosExamenRequest request);

    /**
     * Marca un examen como realizado
     * @param id ID del examen
     * @return el DTO del examen actualizado
     */
    ExamenDTO marcarRealizado(Long id);

    /**
     * Cancela un examen
     * @param id ID del examen
     * @param motivo motivo de la cancelación
     * @return el DTO del examen cancelado
     */
    ExamenDTO cancelar(Long id, String motivo);

    /**
     * Lista exámenes solicitados en un rango de fechas
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de DTOs
     */
    List<ExamenDTO> listarPorRangoFechasSolicitud(LocalDate fechaInicio, LocalDate fechaFin);

    /**
     * Elimina un examen (soft delete)
     * @param id ID del examen
     */
    void eliminar(Long id);
}
