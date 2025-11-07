package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.security.RegistroAuditoria;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.enums.ResultadoAccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio para la entidad RegistroAuditoria.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface AuditoriaRepository extends JpaRepository<RegistroAuditoria, Long> {

    /**
     * Busca registros de auditoría por usuario
     *
     * @param usuario usuario a buscar
     * @return lista de registros del usuario
     */
    List<RegistroAuditoria> findByUsuario(Usuario usuario);

    /**
     * Busca registros de auditoría por módulo
     *
     * @param modulo nombre del módulo
     * @return lista de registros del módulo
     */
    List<RegistroAuditoria> findByModulo(String modulo);

    /**
     * Busca registros de auditoría por resultado
     *
     * @param resultado resultado de la acción
     * @return lista de registros con ese resultado
     */
    List<RegistroAuditoria> findByResultado(ResultadoAccion resultado);

    /**
     * Busca registros en un rango de fechas
     *
     * @param inicio fecha de inicio
     * @param fin fecha de fin
     * @return lista de registros en el rango
     */
    List<RegistroAuditoria> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);

    /**
     * Busca registros de un usuario en un rango de fechas
     *
     * @param usuario usuario a buscar
     * @param inicio fecha de inicio
     * @param fin fecha de fin
     * @return lista de registros
     */
    List<RegistroAuditoria> findByUsuarioAndFechaHoraBetween(Usuario usuario, LocalDateTime inicio, LocalDateTime fin);

    /**
     * Busca accesos no autorizados en un rango de fechas
     *
     * @param inicio fecha de inicio
     * @param fin fecha de fin
     * @return lista de accesos no autorizados
     */
    @Query("SELECT r FROM RegistroAuditoria r WHERE " +
           "r.fechaHora BETWEEN :inicio AND :fin AND " +
           "r.resultado IN ('DENEGADO_SIN_PERMISO', 'DENEGADO_SIN_AUTENTICACION', 'DENEGADO_BLOQUEADO')")
    List<RegistroAuditoria> findAccesosNoAutorizadosBetween(LocalDateTime inicio, LocalDateTime fin);

    /**
     * Busca operaciones críticas en un rango de fechas
     *
     * @param inicio fecha de inicio
     * @param fin fecha de fin
     * @return lista de operaciones críticas
     */
    @Query("SELECT r FROM RegistroAuditoria r WHERE " +
           "r.fechaHora BETWEEN :inicio AND :fin AND " +
           "(r.accion LIKE '%DELETE%' OR r.accion LIKE '%ANULAR%' OR " +
           "r.accion LIKE '%ELIMINAR%' OR r.accion LIKE '%BAJA%')")
    List<RegistroAuditoria> findOperacionesCriticasBetween(LocalDateTime inicio, LocalDateTime fin);
}
