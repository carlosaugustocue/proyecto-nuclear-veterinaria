package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.patients.HistorialEstadoPaciente;
import com.veterinaria.domain.enums.EstadoPaciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio para el historial de estados de pacientes.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface HistorialEstadoPacienteRepository extends JpaRepository<HistorialEstadoPaciente, Long> {

    /**
     * Busca el historial completo de un paciente ordenado por fecha descendente
     *
     * @param pacienteId ID del paciente
     * @return lista de cambios de estado
     */
    @Query("SELECT h FROM HistorialEstadoPaciente h WHERE h.paciente.id = :pacienteId ORDER BY h.fechaCambio DESC")
    List<HistorialEstadoPaciente> findByPacienteIdOrderByFechaCambioDesc(@Param("pacienteId") Long pacienteId);

    /**
     * Busca cambios de estado por un estado específico
     *
     * @param estado estado a buscar
     * @return lista de cambios a ese estado
     */
    List<HistorialEstadoPaciente> findByNuevoEstado(EstadoPaciente estado);

    /**
     * Busca cambios de estado en un rango de fechas
     *
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de cambios en ese rango
     */
    List<HistorialEstadoPaciente> findByFechaCambioBetween(LocalDate fechaInicio, LocalDate fechaFin);

    /**
     * Obtiene el último cambio de estado de un paciente
     *
     * @param pacienteId ID del paciente
     * @return último cambio de estado
     */
    @Query("SELECT h FROM HistorialEstadoPaciente h WHERE h.paciente.id = :pacienteId ORDER BY h.fechaCambio DESC, h.createdAt DESC LIMIT 1")
    HistorialEstadoPaciente findUltimoCambioPorPaciente(@Param("pacienteId") Long pacienteId);
}
