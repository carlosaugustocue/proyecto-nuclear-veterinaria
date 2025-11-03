package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.security.AuditoriaAcceso;
import com.veterinaria.domain.entity.security.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio para la entidad AuditoriaAcceso.
 * Proporciona métodos para consultar y analizar el historial de accesos.
 */
@Repository
public interface AuditoriaAccesoRepository extends JpaRepository<AuditoriaAcceso, Long> {

    /**
     * Busca registros de auditoría por usuario.
     *
     * @param usuario  Usuario
     * @param pageable Configuración de paginación
     * @return Página de registros de auditoría
     */
    Page<AuditoriaAcceso> findByUsuarioOrderByFechaHoraDesc(Usuario usuario, Pageable pageable);

    /**
     * Busca registros de auditoría por username.
     *
     * @param username Username
     * @param pageable Configuración de paginación
     * @return Página de registros de auditoría
     */
    Page<AuditoriaAcceso> findByUsernameOrderByFechaHoraDesc(String username, Pageable pageable);

    /**
     * Busca intentos fallidos por username.
     *
     * @param username Username
     * @param desde    Fecha desde
     * @return Lista de intentos fallidos
     */
    @Query("SELECT a FROM AuditoriaAcceso a WHERE a.username = :username AND " +
            "a.exitoso = false AND a.fechaHora >= :desde ORDER BY a.fechaHora DESC")
    List<AuditoriaAcceso> findIntentosFallidosPorUsername(@Param("username") String username,
                                                           @Param("desde") LocalDateTime desde);

    /**
     * Busca registros de auditoría por acción.
     *
     * @param accion   Acción realizada
     * @param pageable Configuración de paginación
     * @return Página de registros
     */
    Page<AuditoriaAcceso> findByAccionOrderByFechaHoraDesc(String accion, Pageable pageable);

    /**
     * Busca registros sospechosos.
     *
     * @param pageable Configuración de paginación
     * @return Página de registros sospechosos
     */
    Page<AuditoriaAcceso> findBySospechosoTrueOrderByFechaHoraDesc(Pageable pageable);

    /**
     * Busca registros por dirección IP.
     *
     * @param ipAddress Dirección IP
     * @param pageable  Configuración de paginación
     * @return Página de registros
     */
    Page<AuditoriaAcceso> findByIpAddressOrderByFechaHoraDesc(String ipAddress, Pageable pageable);

    /**
     * Busca registros en un rango de fechas.
     *
     * @param desde    Fecha desde
     * @param hasta    Fecha hasta
     * @param pageable Configuración de paginación
     * @return Página de registros
     */
    @Query("SELECT a FROM AuditoriaAcceso a WHERE a.fechaHora BETWEEN :desde AND :hasta " +
            "ORDER BY a.fechaHora DESC")
    Page<AuditoriaAcceso> findByFechaHoraBetween(@Param("desde") LocalDateTime desde,
                                                  @Param("hasta") LocalDateTime hasta,
                                                  Pageable pageable);

    /**
     * Cuenta intentos fallidos por IP en un período.
     *
     * @param ipAddress Dirección IP
     * @param desde     Fecha desde
     * @return Cantidad de intentos fallidos
     */
    @Query("SELECT COUNT(a) FROM AuditoriaAcceso a WHERE a.ipAddress = :ipAddress AND " +
            "a.exitoso = false AND a.fechaHora >= :desde")
    long countIntentosFallidosPorIp(@Param("ipAddress") String ipAddress,
                                     @Param("desde") LocalDateTime desde);

    /**
     * Busca actividad sospechosa (múltiples intentos fallidos desde la misma IP).
     *
     * @param desde   Fecha desde
     * @param minIntentos Mínimo de intentos para considerar sospechoso
     * @return Lista de IPs sospechosas con cantidad de intentos
     */
    @Query("SELECT a.ipAddress, COUNT(a) as intentos FROM AuditoriaAcceso a " +
            "WHERE a.exitoso = false AND a.fechaHora >= :desde " +
            "GROUP BY a.ipAddress HAVING COUNT(a) >= :minIntentos " +
            "ORDER BY intentos DESC")
    List<Object[]> findActividadSospechosaPorIp(@Param("desde") LocalDateTime desde,
                                                 @Param("minIntentos") long minIntentos);

    /**
     * Obtiene estadísticas de acceso por usuario.
     *
     * @param usuario Usuario
     * @param desde   Fecha desde
     * @param hasta   Fecha hasta
     * @return Lista con estadísticas [acción, cantidad]
     */
    @Query("SELECT a.accion, COUNT(a) FROM AuditoriaAcceso a " +
            "WHERE a.usuario = :usuario AND a.fechaHora BETWEEN :desde AND :hasta " +
            "GROUP BY a.accion ORDER BY COUNT(a) DESC")
    List<Object[]> obtenerEstadisticasPorUsuario(@Param("usuario") Usuario usuario,
                                                  @Param("desde") LocalDateTime desde,
                                                  @Param("hasta") LocalDateTime hasta);

    /**
     * Elimina registros antiguos (limpieza periódica).
     *
     * @param fechaLimite Fecha límite
     */
    @Modifying
    @Query("DELETE FROM AuditoriaAcceso a WHERE a.fechaHora < :fechaLimite AND a.sospechoso = false")
    void eliminarRegistrosAntiguos(@Param("fechaLimite") LocalDateTime fechaLimite);

    /**
     * Busca último acceso exitoso de un usuario.
     *
     * @param usuario Usuario
     * @return Último registro de acceso exitoso
     */
    @Query("SELECT a FROM AuditoriaAcceso a WHERE a.usuario = :usuario AND " +
            "a.exitoso = true AND a.accion = 'LOGIN' ORDER BY a.fechaHora DESC LIMIT 1")
    AuditoriaAcceso findUltimoAccesoExitoso(@Param("usuario") Usuario usuario);
}
