package com.veterinaria.application.service;

import com.veterinaria.application.dto.appointments.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Interfaz de servicio para la gestión de Citas.
 *
 * @author Sistema Veterinaria
 */
public interface CitaService {

    /**
     * Crea una nueva cita
     * @param request datos de la nueva cita
     * @return el DTO de la cita creada
     */
    CitaDTO crear(CreateCitaRequest request);

    /**
     * Actualiza una cita existente (solo motivo y notas)
     * @param id ID de la cita
     * @param request datos a actualizar
     * @return el DTO de la cita actualizada
     */
    CitaDTO actualizar(Long id, UpdateCitaRequest request);

    /**
     * Busca una cita por ID
     * @param id el ID
     * @return el DTO de la cita
     */
    CitaDTO obtenerPorId(Long id);

    /**
     * Lista todas las citas activas
     * @return lista de DTOs
     */
    List<CitaDTO> listarTodas();

    /**
     * Lista citas por fecha
     * @param fecha la fecha
     * @return lista de DTOs
     */
    List<CitaDTO> listarPorFecha(LocalDate fecha);

    /**
     * Lista citas por veterinario en una fecha
     * @param veterinarioId ID del veterinario
     * @param fecha la fecha
     * @return lista de DTOs
     */
    List<CitaDTO> listarPorVeterinarioYFecha(Long veterinarioId, LocalDate fecha);

    /**
     * Lista citas por paciente
     * @param pacienteId ID del paciente
     * @return lista de DTOs
     */
    List<CitaDTO> listarPorPaciente(Long pacienteId);

    /**
     * Lista citas por cliente
     * @param clienteId ID del cliente
     * @return lista de DTOs
     */
    List<CitaDTO> listarPorCliente(Long clienteId);

    /**
     * Lista próximas citas del paciente
     * @param pacienteId ID del paciente
     * @return lista de DTOs
     */
    List<CitaDTO> listarProximasCitasPaciente(Long pacienteId);

    /**
     * Confirma una cita (cambia estado de PROGRAMADA a CONFIRMADA)
     * @param id ID de la cita
     * @return el DTO de la cita confirmada
     */
    CitaDTO confirmar(Long id);

    /**
     * Confirma una cita usando el token de confirmación (acceso público)
     * @param token el token de confirmación
     * @return el DTO de la cita confirmada
     */
    CitaDTO confirmarPorToken(String token);

    /**
     * Cancela una cita
     * @param id ID de la cita
     * @param request datos de cancelación (motivo)
     * @return el DTO de la cita cancelada
     */
    CitaDTO cancelar(Long id, CancelarCitaRequest request);

    /**
     * Inicia la atención de una cita (cambia a EN_PROGRESO)
     * @param id ID de la cita
     * @return el DTO de la cita iniciada
     */
    CitaDTO iniciar(Long id);

    /**
     * Completa una cita (cambia a COMPLETADA)
     * @param id ID de la cita
     * @return el DTO de la cita completada
     */
    CitaDTO completar(Long id);

    /**
     * Reagenda una cita a nueva fecha y hora
     * @param id ID de la cita
     * @param request datos del reagendamiento
     * @return el DTO de la cita reagendada
     */
    CitaDTO reagendar(Long id, ReagendarCitaRequest request);

    /**
     * Obtiene horarios disponibles del veterinario en una fecha
     * @param veterinarioId ID del veterinario
     * @param fecha la fecha
     * @param duracionMinutos duración de la cita
     * @return lista de horarios disponibles
     */
    List<String> obtenerHorariosDisponibles(Long veterinarioId, LocalDate fecha, Integer duracionMinutos);

    /**
     * Elimina una cita (soft delete)
     * @param id ID de la cita
     */
    void eliminar(Long id);
}
