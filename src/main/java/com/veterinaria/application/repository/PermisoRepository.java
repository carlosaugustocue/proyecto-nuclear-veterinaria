package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.security.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Permiso.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Long> {

    /**
     * Busca un permiso por su código
     *
     * @param codigo código del permiso
     * @return Optional con el permiso si existe
     */
    Optional<Permiso> findByCodigo(String codigo);

    /**
     * Busca todos los permisos de un módulo
     *
     * @param modulo nombre del módulo
     * @return lista de permisos del módulo
     */
    List<Permiso> findByModulo(String modulo);

    /**
     * Busca todos los permisos activos
     *
     * @return lista de permisos activos
     */
    List<Permiso> findByIsActiveTrue();

    /**
     * Verifica si existe un permiso con el código dado
     *
     * @param codigo código del permiso
     * @return true si existe
     */
    boolean existsByCodigo(String codigo);

    /**
     * Busca permisos por código que contenga el texto dado
     *
     * @param texto texto a buscar
     * @return lista de permisos que coinciden
     */
    List<Permiso> findByCodigoContaining(String texto);
}
