package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.security.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Rol.
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    /**
     * Busca un rol por su nombre.
     *
     * @param nombre Nombre del rol
     * @return Optional con el rol si existe
     */
    Optional<Rol> findByNombre(String nombre);

    /**
     * Verifica si existe un rol con el nombre dado.
     *
     * @param nombre Nombre a verificar
     * @return true si existe
     */
    boolean existsByNombre(String nombre);

    /**
     * Busca roles activos.
     *
     * @return Lista de roles activos
     */
    List<Rol> findByIsActiveTrue();

    /**
     * Busca roles por nivel jerárquico.
     *
     * @param nivelJerarquico Nivel jerárquico
     * @return Lista de roles con ese nivel
     */
    List<Rol> findByNivelJerarquico(Integer nivelJerarquico);

    /**
     * Busca roles con nivel jerárquico mayor o igual al especificado.
     *
     * @param nivelJerarquico Nivel mínimo
     * @return Lista de roles
     */
    @Query("SELECT r FROM Rol r WHERE r.nivelJerarquico >= :nivel AND r.isActive = true ORDER BY r.nivelJerarquico")
    List<Rol> findRolesConNivelMayorOIgual(@Param("nivel") Integer nivelJerarquico);

    /**
     * Busca roles que tienen un permiso específico.
     *
     * @param permiso Permiso a buscar
     * @return Lista de roles con ese permiso
     */
    @Query("SELECT r FROM Rol r JOIN r.permisos p WHERE p = :permiso")
    List<Rol> findByPermiso(@Param("permiso") String permiso);
}
