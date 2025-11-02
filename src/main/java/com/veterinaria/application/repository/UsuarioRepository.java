package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.enums.TipoUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Usuario.
 * Proporciona métodos optimizados con índices para consultas frecuentes.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su username.
     *
     * @param username Username del usuario
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> findByUsername(String username);

    /**
     * Busca un usuario por su email.
     *
     * @param email Email del usuario
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Busca un usuario por su DNI.
     *
     * @param dni DNI del usuario
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> findByDni(String dni);

    /**
     * Verifica si existe un usuario con el username dado.
     *
     * @param username Username a verificar
     * @return true si existe
     */
    boolean existsByUsername(String username);

    /**
     * Verifica si existe un usuario con el email dado.
     *
     * @param email Email a verificar
     * @return true si existe
     */
    boolean existsByEmail(String email);

    /**
     * Verifica si existe un usuario con el DNI dado.
     *
     * @param dni DNI a verificar
     * @return true si existe
     */
    boolean existsByDni(String dni);

    /**
     * Busca usuarios por tipo.
     *
     * @param tipoUsuario Tipo de usuario
     * @param pageable    Configuración de paginación
     * @return Página de usuarios
     */
    Page<Usuario> findByTipoUsuario(TipoUsuario tipoUsuario, Pageable pageable);

    /**
     * Busca usuarios activos.
     *
     * @param pageable Configuración de paginación
     * @return Página de usuarios activos
     */
    Page<Usuario> findByIsActiveTrue(Pageable pageable);

    /**
     * Busca usuarios con cuenta bloqueada.
     *
     * @return Lista de usuarios bloqueados
     */
    List<Usuario> findByCuentaBloqueadaTrue();

    /**
     * Busca usuarios cuyas credenciales han expirado.
     *
     * @return Lista de usuarios con credenciales expiradas
     */
    List<Usuario> findByCredencialesExpiradasTrue();

    /**
     * Busca usuarios que no han accedido desde una fecha determinada.
     *
     * @param fecha Fecha límite
     * @return Lista de usuarios inactivos
     */
    @Query("SELECT u FROM Usuario u WHERE u.ultimoAcceso < :fecha OR u.ultimoAcceso IS NULL")
    List<Usuario> findUsuariosInactivosDesde(@Param("fecha") LocalDateTime fecha);

    /**
     * Busca usuarios por nombre o apellido (búsqueda parcial, case insensitive).
     *
     * @param nombre   Nombre a buscar
     * @param apellido Apellido a buscar
     * @param pageable Configuración de paginación
     * @return Página de usuarios que coinciden
     */
    @Query("SELECT u FROM Usuario u WHERE " +
            "LOWER(u.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) OR " +
            "LOWER(u.apellido) LIKE LOWER(CONCAT('%', :apellido, '%'))")
    Page<Usuario> buscarPorNombreOApellido(@Param("nombre") String nombre,
                                            @Param("apellido") String apellido,
                                            Pageable pageable);

    /**
     * Busca usuarios que tienen un rol específico.
     *
     * @param nombreRol Nombre del rol
     * @param pageable  Configuración de paginación
     * @return Página de usuarios con ese rol
     */
    @Query("SELECT u FROM Usuario u JOIN u.roles r WHERE r.nombre = :nombreRol")
    Page<Usuario> findByRolNombre(@Param("nombreRol") String nombreRol, Pageable pageable);

    /**
     * Busca usuarios con cuenta expirada.
     *
     * @return Lista de usuarios con cuenta expirada
     */
    List<Usuario> findByCuentaExpiradaTrue();

    /**
     * Cuenta usuarios activos por tipo.
     *
     * @param tipoUsuario Tipo de usuario
     * @return Cantidad de usuarios activos de ese tipo
     */
    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.tipoUsuario = :tipo AND u.isActive = true")
    long contarActivosPorTipo(@Param("tipo") TipoUsuario tipoUsuario);
}
