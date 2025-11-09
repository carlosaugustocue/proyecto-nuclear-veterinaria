package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.medical.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio para la gestión de Tratamientos médicos.
 * Implementa Repository Pattern.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {

    /**
     * Busca tratamientos por consulta
     * @param consultaId ID de la consulta
     * @return lista de tratamientos de la consulta
     */
    @Query("SELECT t FROM Tratamiento t WHERE t.consulta.id = :consultaId " +
           "AND t.isActive = true ORDER BY t.createdAt")
    List<Tratamiento> findByConsultaId(@Param("consultaId") Long consultaId);

    /**
     * Busca tratamientos por paciente
     * @param pacienteId ID del paciente
     * @return lista de tratamientos del paciente
     */
    @Query("SELECT t FROM Tratamiento t WHERE t.consulta.historialClinico.paciente.id = :pacienteId " +
           "AND t.isActive = true ORDER BY t.fechaInicio DESC")
    List<Tratamiento> findByPacienteId(@Param("pacienteId") Long pacienteId);

    /**
     * Busca tratamientos activos por paciente
     * @param pacienteId ID del paciente
     * @return lista de tratamientos activos
     */
    @Query("SELECT t FROM Tratamiento t WHERE t.consulta.historialClinico.paciente.id = :pacienteId " +
           "AND t.activo = true AND t.isActive = true ORDER BY t.fechaInicio DESC")
    List<Tratamiento> findTratamientosActivosByPaciente(@Param("pacienteId") Long pacienteId);

    /**
     * Busca tratamientos vigentes (activos y dentro del rango de fechas)
     * @param pacienteId ID del paciente
     * @param fechaActual fecha actual
     * @return lista de tratamientos vigentes
     */
    @Query("SELECT t FROM Tratamiento t WHERE t.consulta.historialClinico.paciente.id = :pacienteId " +
           "AND t.activo = true " +
           "AND t.fechaInicio <= :fechaActual " +
           "AND (t.fechaFin IS NULL OR t.fechaFin >= :fechaActual) " +
           "AND t.isActive = true ORDER BY t.fechaInicio DESC")
    List<Tratamiento> findTratamientosVigentesByPaciente(@Param("pacienteId") Long pacienteId,
                                                           @Param("fechaActual") LocalDate fechaActual);

    /**
     * Busca tratamientos por tipo
     * @param tipoTratamiento tipo de tratamiento
     * @return lista de tratamientos de ese tipo
     */
    @Query("SELECT t FROM Tratamiento t WHERE t.tipoTratamiento = :tipoTratamiento " +
           "AND t.isActive = true ORDER BY t.fechaInicio DESC")
    List<Tratamiento> findByTipoTratamiento(@Param("tipoTratamiento") String tipoTratamiento);

    /**
     * Busca tratamientos medicamentosos activos
     * @return lista de medicamentos activos
     */
    @Query("SELECT t FROM Tratamiento t WHERE t.tipoTratamiento = 'MEDICAMENTO' " +
           "AND t.activo = true AND t.isActive = true ORDER BY t.fechaInicio DESC")
    List<Tratamiento> findMedicamentosActivos();

    /**
     * Busca tratamientos que requieren supervisión
     * @return lista de tratamientos que requieren supervisión
     */
    @Query("SELECT t FROM Tratamiento t WHERE t.requiereSupervision = true " +
           "AND t.activo = true AND t.isActive = true ORDER BY t.fechaInicio DESC")
    List<Tratamiento> findTratamientosConSupervision();

    /**
     * Busca tratamientos finalizados en un rango de fechas
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de tratamientos finalizados
     */
    @Query("SELECT t FROM Tratamiento t WHERE t.fechaFin BETWEEN :fechaInicio AND :fechaFin " +
           "AND t.isActive = true ORDER BY t.fechaFin DESC")
    List<Tratamiento> findTratamientosFinalizadosEnRango(@Param("fechaInicio") LocalDate fechaInicio,
                                                           @Param("fechaFin") LocalDate fechaFin);

    /**
     * Busca tratamientos por nombre o medicamento
     * @param nombre texto a buscar
     * @return lista de tratamientos
     */
    @Query("SELECT t FROM Tratamiento t WHERE LOWER(t.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) " +
           "AND t.isActive = true ORDER BY t.fechaInicio DESC")
    List<Tratamiento> findByNombreContaining(@Param("nombre") String nombre);

    /**
     * Busca tratamientos próximos a finalizar (en los próximos N días)
     * @param fechaInicio fecha inicial (hoy)
     * @param fechaFin fecha límite
     * @return lista de tratamientos
     */
    @Query("SELECT t FROM Tratamiento t WHERE t.activo = true " +
           "AND t.fechaFin BETWEEN :fechaInicio AND :fechaFin " +
           "AND t.isActive = true ORDER BY t.fechaFin")
    List<Tratamiento> findTratamientosProximosAFinalizar(@Param("fechaInicio") LocalDate fechaInicio,
                                                           @Param("fechaFin") LocalDate fechaFin);

    /**
     * Busca tratamientos suspendidos
     * @return lista de tratamientos suspendidos
     */
    @Query("SELECT t FROM Tratamiento t WHERE t.activo = false " +
           "AND t.isActive = true ORDER BY t.fechaFin DESC")
    List<Tratamiento> findTratamientosSuspendidos();

    /**
     * Busca tratamientos en un rango de fechas de inicio
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de tratamientos
     */
    @Query("SELECT t FROM Tratamiento t WHERE t.fechaInicio BETWEEN :fechaInicio AND :fechaFin " +
           "AND t.isActive = true ORDER BY t.fechaInicio DESC")
    List<Tratamiento> findByFechaInicioBetween(@Param("fechaInicio") LocalDate fechaInicio,
                                                 @Param("fechaFin") LocalDate fechaFin);

    /**
     * Cuenta tratamientos activos de un paciente
     * @param pacienteId ID del paciente
     * @return cantidad de tratamientos activos
     */
    @Query("SELECT COUNT(t) FROM Tratamiento t WHERE t.consulta.historialClinico.paciente.id = :pacienteId " +
           "AND t.activo = true AND t.isActive = true")
    Long countTratamientosActivosByPaciente(@Param("pacienteId") Long pacienteId);

    /**
     * Cuenta tratamientos por tipo
     * @param tipoTratamiento tipo de tratamiento
     * @return cantidad de tratamientos
     */
    @Query("SELECT COUNT(t) FROM Tratamiento t WHERE t.tipoTratamiento = :tipoTratamiento " +
           "AND t.isActive = true")
    Long countByTipoTratamiento(@Param("tipoTratamiento") String tipoTratamiento);

    /**
     * Verifica si existe un tratamiento
     * @param id ID del tratamiento
     * @return true si existe
     */
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Tratamiento t " +
           "WHERE t.id = :id AND t.isActive = true")
    boolean existsByIdAndActive(@Param("id") Long id);
}
