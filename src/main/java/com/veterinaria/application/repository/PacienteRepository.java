package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.patients.Paciente;
import com.veterinaria.domain.enums.EstadoPaciente;
import com.veterinaria.domain.enums.TipoEspecie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gestión de Pacientes.
 * Proporciona métodos de consulta específicos del dominio.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    /**
     * Busca pacientes por nombre (búsqueda parcial, case-insensitive).
     */
    List<Paciente> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Busca pacientes por especie.
     */
    List<Paciente> findByEspecie(TipoEspecie especie);

    /**
     * Busca pacientes por estado.
     */
    List<Paciente> findByEstado(EstadoPaciente estado);

    /**
     * Busca pacientes por cliente.
     */
    List<Paciente> findByClienteId(Long clienteId);

    /**
     * Busca pacientes activos de un cliente.
     */
    @Query("SELECT p FROM Paciente p WHERE p.cliente.id = :clienteId AND p.estado = 'ACTIVO'")
    List<Paciente> findPacientesActivosByClienteId(@Param("clienteId") Long clienteId);

    /**
     * Busca paciente por microchip.
     */
    Optional<Paciente> findByMicrochip(String microchip);

    /**
     * Verifica si existe un paciente con un microchip específico.
     */
    boolean existsByMicrochip(String microchip);

    /**
     * Cuenta pacientes activos.
     */
    long countByEstado(EstadoPaciente estado);

    /**
     * Busca pacientes por especie y estado.
     */
    List<Paciente> findByEspecieAndEstado(TipoEspecie especie, EstadoPaciente estado);
}
