package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.security.Sesion;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.enums.EstadoSesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Sesion.
 */
@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {

    /**
     * Busca una sesión por su token.
     *
     * @param token Token de la sesión
     * @return Optional con la sesión si existe
     */
    Optional<Sesion> findByToken(String token);

    /**
     * Busca una sesión por su refresh token.
     *
     * @param refreshToken Refresh token
     * @return Optional con la sesión si existe
     */
    Optional<Sesion> findByRefreshToken(String refreshToken);

    /**
     * Busca sesiones activas de un usuario.
     *
     * @param usuario Usuario
     * @return Lista de sesiones activas
     */
    List<Sesion> findByUsuarioAndEstado(Usuario usuario, EstadoSesion estado);

    /**
     * Busca todas las sesiones de un usuario.
     *
     * @param usuario Usuario
     * @return Lista de sesiones
     */
    List<Sesion> findByUsuarioOrderByFechaInicioDesc(Usuario usuario);

    /**
     * Cuenta sesiones activas de un usuario.
     *
     * @param usuario Usuario
     * @param estado  Estado de la sesión
     * @return Cantidad de sesiones activas
     */
    long countByUsuarioAndEstado(Usuario usuario, EstadoSesion estado);

    /**
     * Busca sesiones expiradas que aún están marcadas como activas.
     *
     * @param now Fecha y hora actual
     * @return Lista de sesiones expiradas
     */
    @Query("SELECT s FROM Sesion s WHERE s.estado = 'ACTIVA' AND s.fechaExpiracion < :now")
    List<Sesion> findSesionesExpiradas(@Param("now") LocalDateTime now);

    /**
     * Busca sesiones inactivas.
     *
     * @param fechaLimite Fecha límite de última actividad
     * @return Lista de sesiones inactivas
     */
    @Query("SELECT s FROM Sesion s WHERE s.estado = 'ACTIVA' AND s.ultimaActividad < :fechaLimite")
    List<Sesion> findSesionesInactivas(@Param("fechaLimite") LocalDateTime fechaLimite);

    /**
     * Cierra todas las sesiones activas de un usuario.
     *
     * @param usuario Usuario
     * @param estado  Nuevo estado
     */
    @Modifying
    @Query("UPDATE Sesion s SET s.estado = :estado, s.fechaFin = :fechaFin " +
            "WHERE s.usuario = :usuario AND s.estado = 'ACTIVA'")
    void cerrarSesionesActivas(@Param("usuario") Usuario usuario,
                                @Param("estado") EstadoSesion estado,
                                @Param("fechaFin") LocalDateTime fechaFin);

    /**
     * Busca sesiones por dirección IP.
     *
     * @param ipAddress Dirección IP
     * @return Lista de sesiones desde esa IP
     */
    List<Sesion> findByIpAddress(String ipAddress);

    /**
     * Busca sesiones sospechosas (múltiples IPs para un mismo usuario en poco tiempo).
     *
     * @param usuario      Usuario
     * @param fechaInicio  Fecha de inicio del período
     * @return Lista de sesiones
     */
    @Query("SELECT s FROM Sesion s WHERE s.usuario = :usuario AND s.fechaInicio >= :fechaInicio " +
            "ORDER BY s.fechaInicio DESC")
    List<Sesion> findSesionesPorUsuarioDesde(@Param("usuario") Usuario usuario,
                                              @Param("fechaInicio") LocalDateTime fechaInicio);

    /**
     * Elimina sesiones antiguas (limpieza periódica).
     *
     * @param fechaLimite Fecha límite
     */
    @Modifying
    @Query("DELETE FROM Sesion s WHERE s.fechaFin < :fechaLimite")
    void eliminarSesionesAntiguas(@Param("fechaLimite") LocalDateTime fechaLimite);
}
