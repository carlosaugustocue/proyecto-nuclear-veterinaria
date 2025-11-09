package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.medical.HistorialClinico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de Historiales Clínicos.
 * Implementa Repository Pattern.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface HistorialClinicoRepository extends JpaRepository<HistorialClinico, Long> {

    /**
     * Busca el historial clínico por ID del paciente
     * @param pacienteId ID del paciente
     * @return historial clínico del paciente
     */
    @Query("SELECT h FROM HistorialClinico h WHERE h.paciente.id = :pacienteId AND h.isActive = true")
    Optional<HistorialClinico> findByPacienteId(@Param("pacienteId") Long pacienteId);

    /**
     * Busca historiales con alergias registradas
     * @return lista de historiales con alergias
     */
    @Query("SELECT h FROM HistorialClinico h WHERE h.alergias IS NOT NULL " +
           "AND LENGTH(TRIM(h.alergias)) > 0 AND h.isActive = true")
    List<HistorialClinico> findHistorialesConAlergias();

    /**
     * Busca historiales con condiciones crónicas
     * @return lista de historiales con condiciones crónicas
     */
    @Query("SELECT h FROM HistorialClinico h WHERE h.condicionesCronicas IS NOT NULL " +
           "AND LENGTH(TRIM(h.condicionesCronicas)) > 0 AND h.isActive = true")
    List<HistorialClinico> findHistorialesConCondicionesCronicas();

    /**
     * Busca historiales creados en un rango de fechas
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de historiales
     */
    @Query("SELECT h FROM HistorialClinico h WHERE h.fechaApertura BETWEEN :fechaInicio AND :fechaFin " +
           "AND h.isActive = true ORDER BY h.fechaApertura DESC")
    List<HistorialClinico> findByFechaAperturaBetween(@Param("fechaInicio") LocalDate fechaInicio,
                                                        @Param("fechaFin") LocalDate fechaFin);

    /**
     * Busca historiales por grupo sanguíneo
     * @param grupoSanguineo el grupo sanguíneo
     * @return lista de historiales
     */
    @Query("SELECT h FROM HistorialClinico h WHERE h.grupoSanguineo = :grupoSanguineo " +
           "AND h.isActive = true")
    List<HistorialClinico> findByGrupoSanguineo(@Param("grupoSanguineo") String grupoSanguineo);

    /**
     * Cuenta los historiales activos
     * @return cantidad de historiales activos
     */
    @Query("SELECT COUNT(h) FROM HistorialClinico h WHERE h.isActive = true")
    Long countHistorialesActivos();

    /**
     * Busca historiales sin consultas recientes (más de X meses)
     * @param fechaLimite fecha límite para considerar inactivos
     * @return lista de historiales inactivos
     */
    @Query("SELECT h FROM HistorialClinico h WHERE h.isActive = true " +
           "AND (SELECT MAX(c.fechaConsulta) FROM Consulta c WHERE c.historialClinico = h) < :fechaLimite")
    List<HistorialClinico> findHistorialesSinConsultasRecientes(@Param("fechaLimite") LocalDate fechaLimite);

    /**
     * Busca historiales con consultas recientes
     * @param fechaLimite fecha límite
     * @return lista de historiales activos
     */
    @Query("SELECT DISTINCT h FROM HistorialClinico h JOIN h.consultas c " +
           "WHERE c.fechaConsulta >= :fechaLimite AND h.isActive = true " +
           "ORDER BY c.fechaConsulta DESC")
    List<HistorialClinico> findHistorialesConConsultasRecientes(@Param("fechaLimite") LocalDate fechaLimite);

    /**
     * Verifica si existe un historial para un paciente
     * @param pacienteId ID del paciente
     * @return true si existe
     */
    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM HistorialClinico h " +
           "WHERE h.paciente.id = :pacienteId AND h.isActive = true")
    boolean existsByPacienteId(@Param("pacienteId") Long pacienteId);

    /**
     * Busca historiales con más de N consultas
     * @param cantidadConsultas cantidad mínima de consultas
     * @return lista de historiales
     */
    @Query("SELECT h FROM HistorialClinico h WHERE SIZE(h.consultas) >= :cantidadConsultas " +
           "AND h.isActive = true ORDER BY SIZE(h.consultas) DESC")
    List<HistorialClinico> findHistorialesConMinConsultas(@Param("cantidadConsultas") int cantidadConsultas);
}
