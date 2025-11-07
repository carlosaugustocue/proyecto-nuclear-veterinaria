package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.security.DispositivoConfiable;
import com.veterinaria.domain.entity.security.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad DispositivoConfiable.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface DispositivoConfiableRepository extends JpaRepository<DispositivoConfiable, Long> {

    /**
     * Busca dispositivos confiables de un usuario
     *
     * @param usuario usuario a buscar
     * @return lista de dispositivos confiables
     */
    List<DispositivoConfiable> findByUsuario(Usuario usuario);

    /**
     * Busca un dispositivo por huella
     *
     * @param huella huella del dispositivo
     * @return Optional con el dispositivo si existe
     */
    Optional<DispositivoConfiable> findByHuella(String huella);

    /**
     * Busca un dispositivo de un usuario por huella
     *
     * @param usuario usuario propietario
     * @param huella huella del dispositivo
     * @return Optional con el dispositivo si existe
     */
    Optional<DispositivoConfiable> findByUsuarioAndHuella(Usuario usuario, String huella);

    /**
     * Verifica si un usuario tiene un dispositivo confiable con esa huella
     *
     * @param usuario usuario a verificar
     * @param huella huella a verificar
     * @return true si existe
     */
    boolean existsByUsuarioAndHuella(Usuario usuario, String huella);

    /**
     * Cuenta dispositivos confiables de un usuario
     *
     * @param usuario usuario a buscar
     * @return número de dispositivos
     */
    long countByUsuario(Usuario usuario);
}
