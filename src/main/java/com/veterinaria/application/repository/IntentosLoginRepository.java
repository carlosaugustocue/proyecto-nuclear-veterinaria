package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.security.IntentosLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad IntentosLogin.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface IntentosLoginRepository extends JpaRepository<IntentosLogin, Long> {

    /**
     * Busca registro de intentos por email
     *
     * @param email email del usuario
     * @return Optional con el registro si existe
     */
    Optional<IntentosLogin> findByEmail(String email);

    /**
     * Busca cuentas actualmente bloqueadas
     *
     * @param fechaActual fecha actual para comparar
     * @return lista de intentos con bloqueo activo
     */
    @Query("SELECT i FROM IntentosLogin i WHERE i.bloqueadoHasta IS NOT NULL AND i.bloqueadoHasta > :fechaActual")
    List<IntentosLogin> findCuentasBloqueadas(LocalDateTime fechaActual);

    /**
     * Verifica si un email está bloqueado
     *
     * @param email email a verificar
     * @param fechaActual fecha actual
     * @return true si está bloqueado
     */
    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM IntentosLogin i " +
           "WHERE i.email = :email AND i.bloqueadoHasta IS NOT NULL AND i.bloqueadoHasta > :fechaActual")
    boolean estaEmailBloqueado(String email, LocalDateTime fechaActual);

    /**
     * Elimina registros de intentos antiguos (más de 30 días)
     *
     * @param fechaLimite fecha límite
     */
    void deleteByUltimoIntentoBefore(LocalDateTime fechaLimite);
}
