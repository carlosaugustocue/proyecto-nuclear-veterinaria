package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.appointments.Cita;
import com.veterinaria.domain.entity.appointments.EstadoCita;
import com.veterinaria.domain.entity.security.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de Citas.
 * Implementa Repository Pattern.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    /**
     * Busca citas por fecha
     * @param fecha la fecha a buscar
     * @return lista de citas en esa fecha
     */
    @Query("SELECT c FROM Cita c WHERE c.fecha = :fecha AND c.isActive = true ORDER BY c.hora")
    List<Cita> findByFecha(@Param("fecha") LocalDate fecha);

    /**
     * Busca citas por veterinario en una fecha específica
     * @param veterinario el veterinario
     * @param fecha la fecha
     * @return lista de citas del veterinario en esa fecha
     */
    @Query("SELECT c FROM Cita c WHERE c.veterinario = :veterinario AND c.fecha = :fecha " +
           "AND c.isActive = true ORDER BY c.hora")
    List<Cita> findByVeterinarioAndFecha(@Param("veterinario") Usuario veterinario,
                                          @Param("fecha") LocalDate fecha);

    /**
     * Busca citas por veterinario entre fechas
     * @param veterinario el veterinario
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de citas en el rango
     */
    @Query("SELECT c FROM Cita c WHERE c.veterinario = :veterinario " +
           "AND c.fecha BETWEEN :fechaInicio AND :fechaFin " +
           "AND c.isActive = true ORDER BY c.fecha, c.hora")
    List<Cita> findByVeterinarioAndFechaBetween(@Param("veterinario") Usuario veterinario,
                                                  @Param("fechaInicio") LocalDate fechaInicio,
                                                  @Param("fechaFin") LocalDate fechaFin);

    /**
     * Busca citas por estado en un rango de fechas
     * @param estado el estado de la cita
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de citas con ese estado
     */
    @Query("SELECT c FROM Cita c WHERE c.estado = :estado " +
           "AND c.fecha BETWEEN :fechaInicio AND :fechaFin " +
           "AND c.isActive = true ORDER BY c.fecha, c.hora")
    List<Cita> findByEstadoAndFechaBetween(@Param("estado") EstadoCita estado,
                                            @Param("fechaInicio") LocalDate fechaInicio,
                                            @Param("fechaFin") LocalDate fechaFin);

    /**
     * Busca citas por cliente
     * @param clienteId ID del cliente
     * @return lista de citas del cliente
     */
    @Query("SELECT c FROM Cita c WHERE c.cliente.id = :clienteId " +
           "AND c.isActive = true ORDER BY c.fecha DESC, c.hora DESC")
    List<Cita> findByClienteId(@Param("clienteId") Long clienteId);

    /**
     * Busca citas por paciente
     * @param pacienteId ID del paciente
     * @return lista de citas del paciente
     */
    @Query("SELECT c FROM Cita c WHERE c.paciente.id = :pacienteId " +
           "AND c.isActive = true ORDER BY c.fecha DESC, c.hora DESC")
    List<Cita> findByPacienteId(@Param("pacienteId") Long pacienteId);

    /**
     * Busca citas activas (ni completadas ni canceladas) del veterinario en una fecha
     * @param veterinario el veterinario
     * @param fecha la fecha
     * @return citas activas
     */
    @Query("SELECT c FROM Cita c WHERE c.veterinario = :veterinario " +
           "AND c.fecha = :fecha " +
           "AND c.estado NOT IN ('COMPLETADA', 'CANCELADA') " +
           "AND c.isActive = true ORDER BY c.hora")
    List<Cita> findCitasActivasByVeterinarioAndFecha(@Param("veterinario") Usuario veterinario,
                                                       @Param("fecha") LocalDate fecha);

    /**
     * Cuenta las citas del veterinario en una fecha
     * @param veterinario el veterinario
     * @param fecha la fecha
     * @return cantidad de citas
     */
    @Query("SELECT COUNT(c) FROM Cita c WHERE c.veterinario = :veterinario " +
           "AND c.fecha = :fecha AND c.isActive = true")
    Long countByVeterinarioAndFecha(@Param("veterinario") Usuario veterinario,
                                      @Param("fecha") LocalDate fecha);

    /**
     * Verifica si existe conflicto de horario para un veterinario
     * @param veterinario el veterinario
     * @param fecha fecha de la cita
     * @param horaInicio hora de inicio
     * @param horaFin hora de fin
     * @param citaIdExcluir ID de cita a excluir (para reagendamiento)
     * @return lista de citas en conflicto
     */
    @Query("SELECT c FROM Cita c WHERE c.veterinario = :veterinario " +
           "AND c.fecha = :fecha " +
           "AND c.id != :citaIdExcluir " +
           "AND c.estado != 'CANCELADA' " +
           "AND ((c.hora < :horaFin AND FUNCTION('ADDTIME', c.hora, FUNCTION('SEC_TO_TIME', c.duracionEstimada * 60)) > :horaInicio)) " +
           "AND c.isActive = true")
    List<Cita> findConflictosHorario(@Param("veterinario") Usuario veterinario,
                                       @Param("fecha") LocalDate fecha,
                                       @Param("horaInicio") LocalTime horaInicio,
                                       @Param("horaFin") LocalTime horaFin,
                                       @Param("citaIdExcluir") Long citaIdExcluir);

    /**
     * Busca próximas citas del paciente
     * @param pacienteId ID del paciente
     * @param fechaActual fecha actual
     * @return lista de próximas citas
     */
    @Query("SELECT c FROM Cita c WHERE c.paciente.id = :pacienteId " +
           "AND c.fecha >= :fechaActual " +
           "AND c.estado != 'CANCELADA' " +
           "AND c.isActive = true ORDER BY c.fecha, c.hora")
    List<Cita> findProximasCitasByPaciente(@Param("pacienteId") Long pacienteId,
                                             @Param("fechaActual") LocalDate fechaActual);

    /**
     * Busca citas pendientes de confirmación (más de 24h sin confirmar)
     * @param fechaLimite fecha límite para considerar pendientes
     * @return lista de citas pendientes
     */
    @Query("SELECT c FROM Cita c WHERE c.estado = :estadoProgramada " +
           "AND c.fechaConfirmacion IS NULL " +
           "AND c.fecha >= :fechaLimite " +
           "AND c.isActive = true")
    List<Cita> findCitasPendientesConfirmacion(@Param("estadoProgramada") EstadoCita estadoProgramada,
                                                 @Param("fechaLimite") LocalDate fechaLimite);

    /**
     * Busca citas por tipo de servicio
     * @param tipoServicioId ID del tipo de servicio
     * @return lista de citas
     */
    @Query("SELECT c FROM Cita c WHERE c.tipoServicio.id = :tipoServicioId " +
           "AND c.isActive = true ORDER BY c.fecha DESC, c.hora DESC")
    List<Cita> findByTipoServicioId(@Param("tipoServicioId") Long tipoServicioId);

    /**
     * Verifica si existe una cita
     * @param id ID de la cita
     * @return true si existe
     */
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Cita c " +
           "WHERE c.id = :id AND c.isActive = true")
    boolean existsByIdAndActive(@Param("id") Long id);

    /**
     * Busca una cita por token de confirmación
     * @param token el token de confirmación
     * @return Optional con la cita si existe
     */
    @Query("SELECT c FROM Cita c WHERE c.tokenConfirmacion = :token AND c.isActive = true")
    Optional<Cita> findByTokenConfirmacion(@Param("token") String token);
}
