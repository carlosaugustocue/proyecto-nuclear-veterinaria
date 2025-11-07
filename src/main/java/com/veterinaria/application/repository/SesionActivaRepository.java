package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.security.SesionActiva;
import com.veterinaria.domain.entity.security.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad SesionActiva.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface SesionActivaRepository extends JpaRepository<SesionActiva, Long> {

    /**
     * Busca una sesión activa por token
     *
     * @param token token JWT
     * @return Optional con la sesión si existe
     */
    Optional<SesionActiva> findByToken(String token);

    /**
     * Busca todas las sesiones activas de un usuario
     *
     * @param usuario usuario a buscar
     * @return lista de sesiones activas
     */
    List<SesionActiva> findByUsuarioAndActivaTrue(Usuario usuario);

    /**
     * Busca todas las sesiones de un usuario (activas e inactivas)
     *
     * @param usuario usuario a buscar
     * @return lista de sesiones
     */
    List<SesionActiva> findByUsuario(Usuario usuario);

    /**
     * Cuenta sesiones activas de un usuario
     *
     * @param usuario usuario a buscar
     * @return número de sesiones activas
     */
    long countByUsuarioAndActivaTrue(Usuario usuario);

    /**
     * Busca sesiones expiradas (más de 24 horas sin actividad)
     *
     * @param fechaLimite fecha límite de última actividad
     * @return lista de sesiones expiradas
     */
    @Query("SELECT s FROM SesionActiva s WHERE s.activa = true AND s.ultimaActividad < :fechaLimite")
    List<SesionActiva> findSesionesExpiradas(LocalDateTime fechaLimite);

    /**
     * Elimina sesiones inactivas antiguas
     *
     * @param fechaLimite fecha límite
     */
    void deleteByActivaFalseAndInicioSesionBefore(LocalDateTime fechaLimite);
}
