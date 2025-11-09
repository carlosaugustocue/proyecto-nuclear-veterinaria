package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.medical.Consulta;
import com.veterinaria.domain.entity.security.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de Consultas médicas.
 * Implementa Repository Pattern.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    /**
     * Busca consultas por historial clínico
     * @param historialClinicoId ID del historial clínico
     * @return lista de consultas ordenadas por fecha descendente
     */
    @Query("SELECT c FROM Consulta c WHERE c.historialClinico.id = :historialClinicoId " +
           "AND c.isActive = true ORDER BY c.fechaConsulta DESC, c.horaInicio DESC")
    List<Consulta> findByHistorialClinicoId(@Param("historialClinicoId") Long historialClinicoId);

    /**
     * Busca consultas por paciente
     * @param pacienteId ID del paciente
     * @return lista de consultas del paciente
     */
    @Query("SELECT c FROM Consulta c WHERE c.historialClinico.paciente.id = :pacienteId " +
           "AND c.isActive = true ORDER BY c.fechaConsulta DESC, c.horaInicio DESC")
    List<Consulta> findByPacienteId(@Param("pacienteId") Long pacienteId);

    /**
     * Busca consultas por veterinario
     * @param veterinario el veterinario
     * @return lista de consultas del veterinario
     */
    @Query("SELECT c FROM Consulta c WHERE c.veterinario = :veterinario " +
           "AND c.isActive = true ORDER BY c.fechaConsulta DESC, c.horaInicio DESC")
    List<Consulta> findByVeterinario(@Param("veterinario") Usuario veterinario);

    /**
     * Busca consultas por veterinario en un rango de fechas
     * @param veterinario el veterinario
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de consultas
     */
    @Query("SELECT c FROM Consulta c WHERE c.veterinario = :veterinario " +
           "AND c.fechaConsulta BETWEEN :fechaInicio AND :fechaFin " +
           "AND c.isActive = true ORDER BY c.fechaConsulta DESC, c.horaInicio DESC")
    List<Consulta> findByVeterinarioAndFechaBetween(@Param("veterinario") Usuario veterinario,
                                                      @Param("fechaInicio") LocalDate fechaInicio,
                                                      @Param("fechaFin") LocalDate fechaFin);

    /**
     * Busca consultas por fecha
     * @param fecha la fecha
     * @return lista de consultas en esa fecha
     */
    @Query("SELECT c FROM Consulta c WHERE c.fechaConsulta = :fecha " +
           "AND c.isActive = true ORDER BY c.horaInicio")
    List<Consulta> findByFecha(@Param("fecha") LocalDate fecha);

    /**
     * Busca consultas en curso (iniciadas pero no finalizadas)
     * @return lista de consultas en curso
     */
    @Query("SELECT c FROM Consulta c WHERE c.horaInicio IS NOT NULL " +
           "AND c.horaFin IS NULL AND c.isActive = true ORDER BY c.horaInicio")
    List<Consulta> findConsultasEnCurso();

    /**
     * Busca consultas finalizadas en un rango de fechas
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de consultas finalizadas
     */
    @Query("SELECT c FROM Consulta c WHERE c.horaFin IS NOT NULL " +
           "AND c.fechaConsulta BETWEEN :fechaInicio AND :fechaFin " +
           "AND c.isActive = true ORDER BY c.fechaConsulta DESC")
    List<Consulta> findConsultasFinalizadasEnRango(@Param("fechaInicio") LocalDate fechaInicio,
                                                     @Param("fechaFin") LocalDate fechaFin);

    /**
     * Busca consultas que requieren seguimiento
     * @return lista de consultas que requieren seguimiento
     */
    @Query("SELECT c FROM Consulta c WHERE c.requiereSeguimiento = true " +
           "AND c.fechaSeguimiento IS NOT NULL " +
           "AND c.isActive = true ORDER BY c.fechaSeguimiento")
    List<Consulta> findConsultasConSeguimiento();

    /**
     * Busca consultas con seguimiento vencido
     * @param fechaActual fecha actual
     * @return lista de consultas con seguimiento vencido
     */
    @Query("SELECT c FROM Consulta c WHERE c.requiereSeguimiento = true " +
           "AND c.fechaSeguimiento < :fechaActual " +
           "AND c.isActive = true ORDER BY c.fechaSeguimiento")
    List<Consulta> findConsultasConSeguimientoVencido(@Param("fechaActual") LocalDate fechaActual);

    /**
     * Busca consultas con seguimiento próximo (en los próximos N días)
     * @param fechaInicio fecha inicial (hoy)
     * @param fechaFin fecha límite
     * @return lista de consultas
     */
    @Query("SELECT c FROM Consulta c WHERE c.requiereSeguimiento = true " +
           "AND c.fechaSeguimiento BETWEEN :fechaInicio AND :fechaFin " +
           "AND c.isActive = true ORDER BY c.fechaSeguimiento")
    List<Consulta> findConsultasConSeguimientoProximo(@Param("fechaInicio") LocalDate fechaInicio,
                                                        @Param("fechaFin") LocalDate fechaFin);

    /**
     * Busca la última consulta de un paciente
     * @param pacienteId ID del paciente
     * @return última consulta del paciente
     */
    @Query("SELECT c FROM Consulta c WHERE c.historialClinico.paciente.id = :pacienteId " +
           "AND c.isActive = true ORDER BY c.fechaConsulta DESC, c.horaInicio DESC LIMIT 1")
    Optional<Consulta> findUltimaConsultaByPaciente(@Param("pacienteId") Long pacienteId);

    /**
     * Cuenta consultas por veterinario en un rango de fechas
     * @param veterinarioId ID del veterinario
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return cantidad de consultas
     */
    @Query("SELECT COUNT(c) FROM Consulta c WHERE c.veterinario.id = :veterinarioId " +
           "AND c.fechaConsulta BETWEEN :fechaInicio AND :fechaFin " +
           "AND c.isActive = true")
    Long countByVeterinarioAndFechaBetween(@Param("veterinarioId") Long veterinarioId,
                                            @Param("fechaInicio") LocalDate fechaInicio,
                                            @Param("fechaFin") LocalDate fechaFin);

    /**
     * Cuenta consultas por paciente
     * @param pacienteId ID del paciente
     * @return cantidad de consultas del paciente
     */
    @Query("SELECT COUNT(c) FROM Consulta c WHERE c.historialClinico.paciente.id = :pacienteId " +
           "AND c.isActive = true")
    Long countByPacienteId(@Param("pacienteId") Long pacienteId);

    /**
     * Busca consultas por cita
     * @param citaId ID de la cita
     * @return consulta asociada a la cita
     */
    @Query("SELECT c FROM Consulta c WHERE c.cita.id = :citaId AND c.isActive = true")
    Optional<Consulta> findByCitaId(@Param("citaId") Long citaId);

    /**
     * Busca consultas sin diagnósticos
     * @return lista de consultas sin diagnósticos
     */
    @Query("SELECT c FROM Consulta c WHERE SIZE(c.diagnosticos) = 0 " +
           "AND c.isActive = true ORDER BY c.fechaConsulta DESC")
    List<Consulta> findConsultasSinDiagnosticos();

    /**
     * Busca consultas sin tratamientos
     * @return lista de consultas sin tratamientos
     */
    @Query("SELECT c FROM Consulta c WHERE SIZE(c.tratamientos) = 0 " +
           "AND c.isActive = true ORDER BY c.fechaConsulta DESC")
    List<Consulta> findConsultasSinTratamientos();

    /**
     * Busca consultas por motivo (búsqueda por texto)
     * @param motivo texto a buscar en el motivo
     * @return lista de consultas
     */
    @Query("SELECT c FROM Consulta c WHERE LOWER(c.motivo) LIKE LOWER(CONCAT('%', :motivo, '%')) " +
           "AND c.isActive = true ORDER BY c.fechaConsulta DESC")
    List<Consulta> findByMotivoContaining(@Param("motivo") String motivo);

    /**
     * Verifica si existe una consulta
     * @param id ID de la consulta
     * @return true si existe
     */
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Consulta c " +
           "WHERE c.id = :id AND c.isActive = true")
    boolean existsByIdAndActive(@Param("id") Long id);

    /**
     * Busca una consulta por ID solo si está activa
     * @param id ID de la consulta
     * @return Optional con la consulta si está activa
     */
    @Query("SELECT c FROM Consulta c WHERE c.id = :id AND c.isActive = true")
    Optional<Consulta> findByIdAndActive(@Param("id") Long id);
}
