package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.medical.Vacuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio para la gestión de Vacunas.
 * Implementa Repository Pattern.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface VacunaRepository extends JpaRepository<Vacuna, Long> {

    /**
     * Busca vacunas por historial clínico
     * @param historialClinicoId ID del historial clínico
     * @return lista de vacunas ordenadas por fecha descendente
     */
    @Query("SELECT v FROM Vacuna v WHERE v.historialClinico.id = :historialClinicoId " +
           "AND v.isActive = true ORDER BY v.fechaAplicacion DESC")
    List<Vacuna> findByHistorialClinicoId(@Param("historialClinicoId") Long historialClinicoId);

    /**
     * Busca vacunas por paciente
     * @param pacienteId ID del paciente
     * @return lista de vacunas del paciente
     */
    @Query("SELECT v FROM Vacuna v WHERE v.historialClinico.paciente.id = :pacienteId " +
           "AND v.isActive = true ORDER BY v.fechaAplicacion DESC")
    List<Vacuna> findByPacienteId(@Param("pacienteId") Long pacienteId);

    /**
     * Busca vacunas por tipo
     * @param tipoVacuna tipo de vacuna
     * @return lista de vacunas de ese tipo
     */
    @Query("SELECT v FROM Vacuna v WHERE v.tipoVacuna = :tipoVacuna " +
           "AND v.isActive = true ORDER BY v.fechaAplicacion DESC")
    List<Vacuna> findByTipoVacuna(@Param("tipoVacuna") String tipoVacuna);

    /**
     * Busca vacunas antirrábicas
     * @return lista de vacunas antirrábicas
     */
    @Query("SELECT v FROM Vacuna v WHERE (LOWER(v.nombre) LIKE '%rabia%' OR LOWER(v.tipoVacuna) LIKE '%rabia%') " +
           "AND v.isActive = true ORDER BY v.fechaAplicacion DESC")
    List<Vacuna> findVacunasAntirrábicas();

    /**
     * Busca vacunas con refuerzo próximo (en los próximos N días)
     * @param fechaInicio fecha inicial (hoy)
     * @param fechaFin fecha límite
     * @return lista de vacunas con refuerzo próximo
     */
    @Query("SELECT v FROM Vacuna v WHERE v.fechaProximaDosis BETWEEN :fechaInicio AND :fechaFin " +
           "AND v.isActive = true ORDER BY v.fechaProximaDosis")
    List<Vacuna> findVacunasConRefuerzoProximo(@Param("fechaInicio") LocalDate fechaInicio,
                                                 @Param("fechaFin") LocalDate fechaFin);

    /**
     * Busca vacunas con refuerzo vencido
     * @param fechaActual fecha actual
     * @return lista de vacunas con refuerzo vencido
     */
    @Query("SELECT v FROM Vacuna v WHERE v.fechaProximaDosis < :fechaActual " +
           "AND v.isActive = true ORDER BY v.fechaProximaDosis")
    List<Vacuna> findVacunasConRefuerzoVencido(@Param("fechaActual") LocalDate fechaActual);

    /**
     * Busca vacunas con serie incompleta
     * @return lista de vacunas con serie incompleta
     */
    @Query("SELECT v FROM Vacuna v WHERE v.serieCompleta = false " +
           "AND v.isActive = true ORDER BY v.fechaAplicacion DESC")
    List<Vacuna> findVacunasConSerieIncompleta();

    /**
     * Busca vacunas con serie incompleta por paciente
     * @param pacienteId ID del paciente
     * @return lista de vacunas con serie incompleta del paciente
     */
    @Query("SELECT v FROM Vacuna v WHERE v.historialClinico.paciente.id = :pacienteId " +
           "AND v.serieCompleta = false " +
           "AND v.isActive = true ORDER BY v.fechaAplicacion DESC")
    List<Vacuna> findVacunasConSerieIncompletaByPaciente(@Param("pacienteId") Long pacienteId);

    /**
     * Busca vacunas con reacciones adversas
     * @return lista de vacunas con reacciones adversas reportadas
     */
    @Query("SELECT v FROM Vacuna v WHERE v.reaccionesAdversas IS NOT NULL " +
           "AND LENGTH(TRIM(v.reaccionesAdversas)) > 0 " +
           "AND v.isActive = true ORDER BY v.fechaAplicacion DESC")
    List<Vacuna> findVacunasConReaccionesAdversas();

    /**
     * Busca vacunas aplicadas en un rango de fechas
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de vacunas
     */
    @Query("SELECT v FROM Vacuna v WHERE v.fechaAplicacion BETWEEN :fechaInicio AND :fechaFin " +
           "AND v.isActive = true ORDER BY v.fechaAplicacion DESC")
    List<Vacuna> findByFechaAplicacionBetween(@Param("fechaInicio") LocalDate fechaInicio,
                                                @Param("fechaFin") LocalDate fechaFin);

    /**
     * Busca vacunas por veterinario
     * @param veterinarioId ID del veterinario
     * @return lista de vacunas aplicadas por el veterinario
     */
    @Query("SELECT v FROM Vacuna v WHERE v.veterinario.id = :veterinarioId " +
           "AND v.isActive = true ORDER BY v.fechaAplicacion DESC")
    List<Vacuna> findByVeterinarioId(@Param("veterinarioId") Long veterinarioId);

    /**
     * Busca vacunas por lote
     * @param lote el lote
     * @return lista de vacunas de ese lote
     */
    @Query("SELECT v FROM Vacuna v WHERE v.lote = :lote " +
           "AND v.isActive = true ORDER BY v.fechaAplicacion DESC")
    List<Vacuna> findByLote(@Param("lote") String lote);

    /**
     * Busca vacunas por laboratorio
     * @param laboratorio el laboratorio
     * @return lista de vacunas de ese laboratorio
     */
    @Query("SELECT v FROM Vacuna v WHERE LOWER(v.laboratorio) LIKE LOWER(CONCAT('%', :laboratorio, '%')) " +
           "AND v.isActive = true ORDER BY v.fechaAplicacion DESC")
    List<Vacuna> findByLaboratorioContaining(@Param("laboratorio") String laboratorio);

    /**
     * Busca refuerzos (vacunas marcadas como refuerzo)
     * @return lista de refuerzos
     */
    @Query("SELECT v FROM Vacuna v WHERE v.esRefuerzo = true " +
           "AND v.isActive = true ORDER BY v.fechaAplicacion DESC")
    List<Vacuna> findRefuerzos();

    /**
     * Cuenta vacunas por paciente
     * @param pacienteId ID del paciente
     * @return cantidad de vacunas del paciente
     */
    @Query("SELECT COUNT(v) FROM Vacuna v WHERE v.historialClinico.paciente.id = :pacienteId " +
           "AND v.isActive = true")
    Long countByPacienteId(@Param("pacienteId") Long pacienteId);

    /**
     * Cuenta vacunas por tipo
     * @param tipoVacuna tipo de vacuna
     * @return cantidad de vacunas de ese tipo
     */
    @Query("SELECT COUNT(v) FROM Vacuna v WHERE v.tipoVacuna = :tipoVacuna " +
           "AND v.isActive = true")
    Long countByTipoVacuna(@Param("tipoVacuna") String tipoVacuna);

    /**
     * Verifica si existe una vacuna
     * @param id ID de la vacuna
     * @return true si existe
     */
    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM Vacuna v " +
           "WHERE v.id = :id AND v.isActive = true")
    boolean existsByIdAndActive(@Param("id") Long id);
}
