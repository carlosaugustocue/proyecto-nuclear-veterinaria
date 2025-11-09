package com.veterinaria.application.service;

import com.veterinaria.application.dto.medical.CreateVacunaRequest;
import com.veterinaria.application.dto.medical.VacunaDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * Interfaz de servicio para la gestión de Vacunas.
 *
 * @author Sistema Veterinaria
 */
public interface VacunaService {

    /**
     * Registra una nueva vacuna
     * @param request datos de la vacuna
     * @return el DTO de la vacuna creada
     */
    VacunaDTO registrar(CreateVacunaRequest request);

    /**
     * Busca una vacuna por ID
     * @param id el ID
     * @return el DTO de la vacuna
     */
    VacunaDTO obtenerPorId(Long id);

    /**
     * Lista vacunas por paciente
     * @param pacienteId ID del paciente
     * @return lista de DTOs
     */
    List<VacunaDTO> listarPorPaciente(Long pacienteId);

    /**
     * Lista vacunas por tipo
     * @param tipoVacuna tipo de vacuna
     * @return lista de DTOs
     */
    List<VacunaDTO> listarPorTipo(String tipoVacuna);

    /**
     * Lista vacunas antirrábicas
     * @return lista de DTOs
     */
    List<VacunaDTO> listarAntirrábicas();

    /**
     * Lista vacunas con refuerzo próximo
     * @param dias días de anticipación
     * @return lista de DTOs
     */
    List<VacunaDTO> listarConRefuerzoProximo(int dias);

    /**
     * Lista vacunas con refuerzo vencido
     * @return lista de DTOs
     */
    List<VacunaDTO> listarConRefuerzoVencido();

    /**
     * Lista vacunas con serie incompleta
     * @return lista de DTOs
     */
    List<VacunaDTO> listarConSerieIncompleta();

    /**
     * Lista vacunas con serie incompleta por paciente
     * @param pacienteId ID del paciente
     * @return lista de DTOs
     */
    List<VacunaDTO> listarConSerieIncompletaPorPaciente(Long pacienteId);

    /**
     * Lista vacunas con reacciones adversas
     * @return lista de DTOs
     */
    List<VacunaDTO> listarConReaccionesAdversas();

    /**
     * Calcula la próxima dosis de una vacuna
     * @param id ID de la vacuna
     * @return la vacuna con fecha calculada
     */
    VacunaDTO calcularProximaDosis(Long id);

    /**
     * Marca la serie de vacunación como completa
     * @param id ID de la vacuna
     * @return la vacuna actualizada
     */
    VacunaDTO completarSerie(Long id);

    /**
     * Lista vacunas aplicadas en un rango de fechas
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de DTOs
     */
    List<VacunaDTO> listarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin);

    /**
     * Elimina una vacuna (soft delete)
     * @param id ID de la vacuna
     */
    void eliminar(Long id);
}
