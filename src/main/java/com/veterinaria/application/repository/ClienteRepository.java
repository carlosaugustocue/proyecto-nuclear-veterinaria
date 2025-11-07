package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.patients.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para gestión de Clientes (propietarios).
 * Proporciona métodos de consulta específicos del dominio.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Busca cliente por DNI.
     */
    Optional<Cliente> findByDni(String dni);

    /**
     * Busca cliente por email.
     */
    Optional<Cliente> findByEmail(String email);

    /**
     * Verifica si existe un cliente con el DNI especificado.
     */
    boolean existsByDni(String dni);

    /**
     * Verifica si existe un cliente con el email especificado.
     */
    boolean existsByEmail(String email);

    /**
     * Busca clientes por apellido (búsqueda parcial, case-insensitive).
     */
    List<Cliente> findByApellidoContainingIgnoreCase(String apellido);

    /**
     * Busca clientes por nombre o apellido (búsqueda parcial, case-insensitive).
     */
    @Query("SELECT c FROM Cliente c WHERE " +
           "LOWER(c.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
           "LOWER(c.apellido) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Cliente> buscarPorNombreOApellido(String termino);

    /**
     * Busca clientes que tienen pacientes activos.
     */
    @Query("SELECT DISTINCT c FROM Cliente c " +
           "JOIN c.pacientes p " +
           "WHERE p.estado = 'ACTIVO'")
    List<Cliente> findClientesConPacientesActivos();

    /**
     * Busca clientes por ciudad.
     */
    List<Cliente> findByCiudad(String ciudad);
}
