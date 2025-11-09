package com.veterinaria.application.service;

import com.veterinaria.application.dto.medical.CreateTratamientoRequest;
import com.veterinaria.application.dto.medical.TratamientoDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * Interfaz de servicio para la gestión de Tratamientos médicos.
 *
 * @author Sistema Veterinaria
 */
public interface TratamientoService {

    /**
     * Crea un nuevo tratamiento
     * @param request datos del tratamiento
     * @return el DTO del tratamiento creado
     */
    TratamientoDTO crear(CreateTratamientoRequest request);

    /**
     * Busca un tratamiento por ID
     * @param id el ID
     * @return el DTO del tratamiento
     */
    TratamientoDTO obtenerPorId(Long id);

    /**
     * Lista tratamientos por paciente
     * @param pacienteId ID del paciente
     * @return lista de DTOs
     */
    List<TratamientoDTO> listarPorPaciente(Long pacienteId);

    /**
     * Lista tratamientos activos por paciente
     * @param pacienteId ID del paciente
     * @return lista de DTOs
     */
    List<TratamientoDTO> listarActivosPorPaciente(Long pacienteId);

    /**
     * Lista tratamientos vigentes por paciente
     * @param pacienteId ID del paciente
     * @return lista de DTOs
     */
    List<TratamientoDTO> listarVigentesPorPaciente(Long pacienteId);

    /**
     * Lista tratamientos por tipo
     * @param tipoTratamiento tipo de tratamiento
     * @return lista de DTOs
     */
    List<TratamientoDTO> listarPorTipo(String tipoTratamiento);

    /**
     * Lista medicamentos activos
     * @return lista de DTOs
     */
    List<TratamientoDTO> listarMedicamentosActivos();

    /**
     * Lista tratamientos que requieren supervisión
     * @return lista de DTOs
     */
    List<TratamientoDTO> listarConSupervision();

    /**
     * Lista tratamientos próximos a finalizar
     * @param dias días de anticipación
     * @return lista de DTOs
     */
    List<TratamientoDTO> listarProximosAFinalizar(int dias);

    /**
     * Lista tratamientos suspendidos
     * @return lista de DTOs
     */
    List<TratamientoDTO> listarSuspendidos();

    /**
     * Suspende un tratamiento
     * @param id ID del tratamiento
     * @return el DTO del tratamiento suspendido
     */
    TratamientoDTO suspender(Long id);

    /**
     * Calcula y establece la fecha de fin de un tratamiento
     * @param id ID del tratamiento
     * @return el DTO del tratamiento actualizado
     */
    TratamientoDTO calcularFechaFin(Long id);

    /**
     * Lista tratamientos en un rango de fechas de inicio
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de DTOs
     */
    List<TratamientoDTO> listarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin);

    /**
     * Elimina un tratamiento (soft delete)
     * @param id ID del tratamiento
     */
    void eliminar(Long id);
}
