package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.medical.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio para la gestión de Exámenes médicos.
 * Implementa Repository Pattern.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {

    /**
     * Busca exámenes por historial clínico
     * @param historialClinicoId ID del historial clínico
     * @return lista de exámenes ordenados por fecha descendente
     */
    @Query("SELECT e FROM Examen e WHERE e.historialClinico.id = :historialClinicoId " +
           "AND e.isActive = true ORDER BY e.fechaSolicitud DESC")
    List<Examen> findByHistorialClinicoId(@Param("historialClinicoId") Long historialClinicoId);

    /**
     * Busca exámenes por paciente
     * @param pacienteId ID del paciente
     * @return lista de exámenes del paciente
     */
    @Query("SELECT e FROM Examen e WHERE e.historialClinico.paciente.id = :pacienteId " +
           "AND e.isActive = true ORDER BY e.fechaSolicitud DESC")
    List<Examen> findByPacienteId(@Param("pacienteId") Long pacienteId);

    /**
     * Busca exámenes por consulta
     * @param consultaId ID de la consulta
     * @return lista de exámenes de la consulta
     */
    @Query("SELECT e FROM Examen e WHERE e.consulta.id = :consultaId " +
           "AND e.isActive = true ORDER BY e.fechaSolicitud")
    List<Examen> findByConsultaId(@Param("consultaId") Long consultaId);

    /**
     * Busca exámenes por tipo
     * @param tipoExamen tipo de examen
     * @return lista de exámenes de ese tipo
     */
    @Query("SELECT e FROM Examen e WHERE e.tipoExamen = :tipoExamen " +
           "AND e.isActive = true ORDER BY e.fechaSolicitud DESC")
    List<Examen> findByTipoExamen(@Param("tipoExamen") String tipoExamen);

    /**
     * Busca exámenes por estado
     * @param estado el estado
     * @return lista de exámenes con ese estado
     */
    @Query("SELECT e FROM Examen e WHERE e.estado = :estado " +
           "AND e.isActive = true ORDER BY e.fechaSolicitud DESC")
    List<Examen> findByEstado(@Param("estado") String estado);

    /**
     * Busca exámenes pendientes (solicitados, programados o en proceso)
     * @return lista de exámenes pendientes
     */
    @Query("SELECT e FROM Examen e WHERE e.estado IN ('SOLICITADO', 'PROGRAMADO', 'EN_PROCESO') " +
           "AND e.isActive = true ORDER BY e.fechaSolicitud")
    List<Examen> findExamenesPendientes();

    /**
     * Busca exámenes completados
     * @return lista de exámenes completados
     */
    @Query("SELECT e FROM Examen e WHERE e.estado = 'COMPLETADO' " +
           "AND e.isActive = true ORDER BY e.fechaRealizacion DESC")
    List<Examen> findExamenesCompletados();

    /**
     * Busca exámenes con resultados anormales
     * @return lista de exámenes con resultados anormales
     */
    @Query("SELECT e FROM Examen e WHERE e.resultadoAnormal = true " +
           "AND e.isActive = true ORDER BY e.fechaRealizacion DESC")
    List<Examen> findExamenesConResultadosAnormales();

    /**
     * Busca exámenes urgentes
     * @return lista de exámenes marcados como urgentes
     */
    @Query("SELECT e FROM Examen e WHERE e.esUrgente = true " +
           "AND e.isActive = true ORDER BY e.fechaSolicitud")
    List<Examen> findExamenesUrgentes();

    /**
     * Busca exámenes de laboratorio
     * @return lista de exámenes de laboratorio
     */
    @Query("SELECT e FROM Examen e WHERE (e.tipoExamen LIKE '%SANGRE%' " +
           "OR e.tipoExamen LIKE '%ORINA%' " +
           "OR e.tipoExamen LIKE '%HECES%' " +
           "OR e.tipoExamen LIKE '%BIOPSIA%' " +
           "OR e.tipoExamen LIKE '%CITOLOGÍA%') " +
           "AND e.isActive = true ORDER BY e.fechaSolicitud DESC")
    List<Examen> findExamenesLaboratorio();

    /**
     * Busca exámenes de imagenología
     * @return lista de exámenes de imagenología
     */
    @Query("SELECT e FROM Examen e WHERE (e.tipoExamen LIKE '%RAYOS_X%' " +
           "OR e.tipoExamen LIKE '%ULTRASONIDO%' " +
           "OR e.tipoExamen LIKE '%TOMOGRAFÍA%' " +
           "OR e.tipoExamen LIKE '%RESONANCIA%' " +
           "OR e.tipoExamen LIKE '%ENDOSCOPIA%') " +
           "AND e.isActive = true ORDER BY e.fechaSolicitud DESC")
    List<Examen> findExamenesImagenologia();

    /**
     * Busca exámenes por veterinario solicitante
     * @param veterinarioId ID del veterinario
     * @return lista de exámenes solicitados por el veterinario
     */
    @Query("SELECT e FROM Examen e WHERE e.veterinarioSolicitante.id = :veterinarioId " +
           "AND e.isActive = true ORDER BY e.fechaSolicitud DESC")
    List<Examen> findByVeterinarioSolicitanteId(@Param("veterinarioId") Long veterinarioId);

    /**
     * Busca exámenes externos (realizados en laboratorio externo)
     * @return lista de exámenes externos
     */
    @Query("SELECT e FROM Examen e WHERE e.laboratorioExterno IS NOT NULL " +
           "AND LENGTH(TRIM(e.laboratorioExterno)) > 0 " +
           "AND e.isActive = true ORDER BY e.fechaSolicitud DESC")
    List<Examen> findExamenesExternos();

    /**
     * Busca exámenes por laboratorio externo
     * @param laboratorio el laboratorio
     * @return lista de exámenes de ese laboratorio
     */
    @Query("SELECT e FROM Examen e WHERE LOWER(e.laboratorioExterno) LIKE LOWER(CONCAT('%', :laboratorio, '%')) " +
           "AND e.isActive = true ORDER BY e.fechaSolicitud DESC")
    List<Examen> findByLaboratorioExternoContaining(@Param("laboratorio") String laboratorio);

    /**
     * Busca exámenes con archivo adjunto
     * @return lista de exámenes con archivo
     */
    @Query("SELECT e FROM Examen e WHERE e.archivoRuta IS NOT NULL " +
           "AND LENGTH(TRIM(e.archivoRuta)) > 0 " +
           "AND e.isActive = true ORDER BY e.fechaSolicitud DESC")
    List<Examen> findExamenesConArchivo();

    /**
     * Busca exámenes solicitados en un rango de fechas
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de exámenes
     */
    @Query("SELECT e FROM Examen e WHERE e.fechaSolicitud BETWEEN :fechaInicio AND :fechaFin " +
           "AND e.isActive = true ORDER BY e.fechaSolicitud DESC")
    List<Examen> findByFechaSolicitudBetween(@Param("fechaInicio") LocalDate fechaInicio,
                                               @Param("fechaFin") LocalDate fechaFin);

    /**
     * Busca exámenes realizados en un rango de fechas
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de exámenes
     */
    @Query("SELECT e FROM Examen e WHERE e.fechaRealizacion BETWEEN :fechaInicio AND :fechaFin " +
           "AND e.isActive = true ORDER BY e.fechaRealizacion DESC")
    List<Examen> findByFechaRealizacionBetween(@Param("fechaInicio") LocalDate fechaInicio,
                                                 @Param("fechaFin") LocalDate fechaFin);

    /**
     * Busca exámenes demorados (pendientes por más de N días)
     * @param diasLimite días límite
     * @param fechaLimite fecha límite (hoy - diasLimite)
     * @return lista de exámenes demorados
     */
    @Query("SELECT e FROM Examen e WHERE e.estado IN ('SOLICITADO', 'PROGRAMADO', 'EN_PROCESO') " +
           "AND e.fechaSolicitud < :fechaLimite " +
           "AND e.isActive = true ORDER BY e.fechaSolicitud")
    List<Examen> findExamenesDemorados(@Param("fechaLimite") LocalDate fechaLimite);

    /**
     * Busca exámenes que requieren ayuno
     * @return lista de exámenes que requieren ayuno
     */
    @Query("SELECT e FROM Examen e WHERE e.requiereAyuno = true " +
           "AND e.isActive = true ORDER BY e.fechaSolicitud DESC")
    List<Examen> findExamenesQueRequierenAyuno();

    /**
     * Cuenta exámenes por paciente
     * @param pacienteId ID del paciente
     * @return cantidad de exámenes del paciente
     */
    @Query("SELECT COUNT(e) FROM Examen e WHERE e.historialClinico.paciente.id = :pacienteId " +
           "AND e.isActive = true")
    Long countByPacienteId(@Param("pacienteId") Long pacienteId);

    /**
     * Cuenta exámenes por estado
     * @param estado el estado
     * @return cantidad de exámenes con ese estado
     */
    @Query("SELECT COUNT(e) FROM Examen e WHERE e.estado = :estado " +
           "AND e.isActive = true")
    Long countByEstado(@Param("estado") String estado);

    /**
     * Verifica si existe un examen
     * @param id ID del examen
     * @return true si existe
     */
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Examen e " +
           "WHERE e.id = :id AND e.isActive = true")
    boolean existsByIdAndActive(@Param("id") Long id);
}
