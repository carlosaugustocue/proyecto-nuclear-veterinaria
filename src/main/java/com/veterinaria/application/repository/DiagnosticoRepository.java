package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.medical.Diagnostico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio para la gestión de Diagnósticos médicos.
 * Implementa Repository Pattern.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Long> {

    /**
     * Busca diagnósticos por consulta
     * @param consultaId ID de la consulta
     * @return lista de diagnósticos de la consulta
     */
    @Query("SELECT d FROM Diagnostico d WHERE d.consulta.id = :consultaId " +
           "AND d.isActive = true ORDER BY d.esPrincipal DESC, d.createdAt")
    List<Diagnostico> findByConsultaId(@Param("consultaId") Long consultaId);

    /**
     * Busca diagnósticos por paciente
     * @param pacienteId ID del paciente
     * @return lista de diagnósticos del paciente
     */
    @Query("SELECT d FROM Diagnostico d WHERE d.consulta.historialClinico.paciente.id = :pacienteId " +
           "AND d.isActive = true ORDER BY d.consulta.fechaConsulta DESC")
    List<Diagnostico> findByPacienteId(@Param("pacienteId") Long pacienteId);

    /**
     * Busca diagnósticos por tipo
     * @param tipoDiagnostico tipo de diagnóstico
     * @return lista de diagnósticos de ese tipo
     */
    @Query("SELECT d FROM Diagnostico d WHERE d.tipoDiagnostico = :tipoDiagnostico " +
           "AND d.isActive = true ORDER BY d.createdAt DESC")
    List<Diagnostico> findByTipoDiagnostico(@Param("tipoDiagnostico") String tipoDiagnostico);

    /**
     * Busca diagnósticos por gravedad
     * @param gravedad la gravedad
     * @return lista de diagnósticos con esa gravedad
     */
    @Query("SELECT d FROM Diagnostico d WHERE d.gravedad = :gravedad " +
           "AND d.isActive = true ORDER BY d.consulta.fechaConsulta DESC")
    List<Diagnostico> findByGravedad(@Param("gravedad") String gravedad);

    /**
     * Busca diagnósticos graves o críticos
     * @return lista de diagnósticos graves/críticos
     */
    @Query("SELECT d FROM Diagnostico d WHERE (d.gravedad = 'GRAVE' OR d.gravedad = 'CRÍTICO') " +
           "AND d.isActive = true ORDER BY d.consulta.fechaConsulta DESC")
    List<Diagnostico> findDiagnosticosGravesOCriticos();

    /**
     * Busca diagnósticos principales
     * @return lista de diagnósticos marcados como principales
     */
    @Query("SELECT d FROM Diagnostico d WHERE d.esPrincipal = true " +
           "AND d.isActive = true ORDER BY d.consulta.fechaConsulta DESC")
    List<Diagnostico> findDiagnosticosPrincipales();

    /**
     * Busca diagnósticos con código CIE-10
     * @param codigoCie10 código CIE-10
     * @return lista de diagnósticos con ese código
     */
    @Query("SELECT d FROM Diagnostico d WHERE d.codigoCie10 = :codigoCie10 " +
           "AND d.isActive = true ORDER BY d.consulta.fechaConsulta DESC")
    List<Diagnostico> findByCodigoCie10(@Param("codigoCie10") String codigoCie10);

    /**
     * Busca diagnósticos por descripción (búsqueda por texto)
     * @param descripcion texto a buscar
     * @return lista de diagnósticos
     */
    @Query("SELECT d FROM Diagnostico d WHERE LOWER(d.descripcion) LIKE LOWER(CONCAT('%', :descripcion, '%')) " +
           "AND d.isActive = true ORDER BY d.consulta.fechaConsulta DESC")
    List<Diagnostico> findByDescripcionContaining(@Param("descripcion") String descripcion);

    /**
     * Busca diagnósticos en un rango de fechas
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de diagnósticos
     */
    @Query("SELECT d FROM Diagnostico d WHERE d.consulta.fechaConsulta BETWEEN :fechaInicio AND :fechaFin " +
           "AND d.isActive = true ORDER BY d.consulta.fechaConsulta DESC")
    List<Diagnostico> findByFechaBetween(@Param("fechaInicio") LocalDate fechaInicio,
                                          @Param("fechaFin") LocalDate fechaFin);

    /**
     * Cuenta diagnósticos por tipo
     * @param tipoDiagnostico tipo de diagnóstico
     * @return cantidad de diagnósticos
     */
    @Query("SELECT COUNT(d) FROM Diagnostico d WHERE d.tipoDiagnostico = :tipoDiagnostico " +
           "AND d.isActive = true")
    Long countByTipoDiagnostico(@Param("tipoDiagnostico") String tipoDiagnostico);

    /**
     * Cuenta diagnósticos de un paciente
     * @param pacienteId ID del paciente
     * @return cantidad de diagnósticos
     */
    @Query("SELECT COUNT(d) FROM Diagnostico d WHERE d.consulta.historialClinico.paciente.id = :pacienteId " +
           "AND d.isActive = true")
    Long countByPacienteId(@Param("pacienteId") Long pacienteId);

    /**
     * Verifica si existe un diagnóstico
     * @param id ID del diagnóstico
     * @return true si existe
     */
    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM Diagnostico d " +
           "WHERE d.id = :id AND d.isActive = true")
    boolean existsByIdAndActive(@Param("id") Long id);
}
