package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.security.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Rol.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    /**
     * Busca un rol por su nombre
     *
     * @param nombre nombre del rol
     * @return Optional con el rol si existe
     */
    Optional<Rol> findByNombre(String nombre);

    /**
     * Busca todos los roles activos
     *
     * @return lista de roles activos
     */
    List<Rol> findByIsActiveTrue();

    /**
     * Verifica si existe un rol con el nombre dado
     *
     * @param nombre nombre del rol
     * @return true si existe
     */
    boolean existsByNombre(String nombre);

    /**
     * Busca roles por módulo de permisos
     *
     * @param modulo nombre del módulo
     * @return lista de roles con permisos en ese módulo
     */
    @Query("SELECT DISTINCT r FROM Rol r JOIN r.permisos p WHERE p.modulo = :modulo")
    List<Rol> findByPermisosModulo(String modulo);
}
