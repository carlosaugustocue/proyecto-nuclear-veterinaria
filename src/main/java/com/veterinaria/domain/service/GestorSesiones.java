package com.veterinaria.domain.service;

import com.veterinaria.application.repository.SesionActivaRepository;
import com.veterinaria.domain.entity.security.SesionActiva;
import com.veterinaria.domain.entity.security.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gestor de sesiones activas implementando el patrón Singleton.
 * Gestiona sesiones en memoria y las persiste en base de datos.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@Service
public class GestorSesiones {

    private final SesionActivaRepository sesionActivaRepository;
    private final Map<String, SesionActiva> sesionesActivas;

    private static final int MAX_SESIONES_POR_USUARIO = 5;

    /**
     * Constructor que inicializa el gestor
     */
    public GestorSesiones(SesionActivaRepository sesionActivaRepository) {
        this.sesionActivaRepository = sesionActivaRepository;
        this.sesionesActivas = new ConcurrentHashMap<>();
        log.info("GestorSesiones inicializado");
    }

    /**
     * Registra una nueva sesión activa
     *
     * @param token token JWT
     * @param usuario usuario de la sesión
     * @param direccionIP dirección IP del cliente
     * @return sesión creada
     */
    @Transactional
    public SesionActiva registrarSesion(String token, Usuario usuario, String direccionIP) {
        // Verificar límite de sesiones por usuario
        long sesionesUsuario = sesionActivaRepository.countByUsuarioAndActivaTrue(usuario);
        if (sesionesUsuario >= MAX_SESIONES_POR_USUARIO) {
            log.warn("Usuario {} ha alcanzado el límite de sesiones. Cerrando sesión más antigua.", 
                     usuario.getEmail());
            cerrarSesionMasAntiguaDelUsuario(usuario);
        }

        LocalDateTime ahora = LocalDateTime.now();
        SesionActiva sesion = SesionActiva.builder()
                .token(token)
                .usuario(usuario)
                .direccionIP(direccionIP)
                .inicioSesion(ahora)
                .ultimaActividad(ahora)
                .activa(true)
                .build();

        sesion = sesionActivaRepository.save(sesion);
        sesionesActivas.put(token, sesion);

        log.info("Sesión registrada para usuario: {}", usuario.getEmail());
        return sesion;
    }

    /**
     * Busca una sesión por token
     *
     * @param token token a buscar
     * @return Optional con la sesión si existe y está activa
     */
    public Optional<SesionActiva> buscarSesion(String token) {
        // Primero buscar en caché
        SesionActiva sesion = sesionesActivas.get(token);
        if (sesion != null && sesion.isActiva() && !sesion.esExpirada()) {
            return Optional.of(sesion);
        }

        // Si no está en caché, buscar en base de datos
        Optional<SesionActiva> sesionDB = sesionActivaRepository.findByToken(token);
        if (sesionDB.isPresent() && sesionDB.get().isActiva() && !sesionDB.get().esExpirada()) {
            sesionesActivas.put(token, sesionDB.get());
            return sesionDB;
        }

        return Optional.empty();
    }

    /**
     * Valida si una sesión es válida
     *
     * @param token token a validar
     * @return true si la sesión existe, está activa y no ha expirado
     */
    public boolean esSesionValida(String token) {
        Optional<SesionActiva> sesion = buscarSesion(token);
        return sesion.isPresent();
    }

    /**
     * Renueva la actividad de una sesión
     *
     * @param token token de la sesión
     */
    @Transactional
    public void renovarSesion(String token) {
        Optional<SesionActiva> sesionOpt = buscarSesion(token);
        if (sesionOpt.isPresent()) {
            SesionActiva sesion = sesionOpt.get();
            sesion.renovar();
            sesionActivaRepository.save(sesion);
            sesionesActivas.put(token, sesion);
            log.debug("Sesión renovada: {}", token.substring(0, Math.min(20, token.length())));
        }
    }

    /**
     * Cierra una sesión específica
     *
     * @param token token de la sesión a cerrar
     */
    @Transactional
    public void cerrarSesion(String token) {
        Optional<SesionActiva> sesionOpt = sesionActivaRepository.findByToken(token);
        if (sesionOpt.isPresent()) {
            SesionActiva sesion = sesionOpt.get();
            sesion.cerrar();
            sesionActivaRepository.save(sesion);
            sesionesActivas.remove(token);
            log.info("Sesión cerrada para token: {}", token.substring(0, Math.min(20, token.length())));
        }
    }

    /**
     * Cierra todas las sesiones de un usuario
     *
     * @param usuario usuario cuyas sesiones se cerrarán
     */
    @Transactional
    public void cerrarTodasLasSesionesDelUsuario(Usuario usuario) {
        List<SesionActiva> sesiones = sesionActivaRepository.findByUsuarioAndActivaTrue(usuario);
        for (SesionActiva sesion : sesiones) {
            sesion.cerrar();
            sesionActivaRepository.save(sesion);
            sesionesActivas.remove(sesion.getToken());
        }
        log.info("Todas las sesiones cerradas para usuario: {}", usuario.getEmail());
    }

    /**
     * Cierra la sesión más antigua de un usuario
     *
     * @param usuario usuario a buscar
     */
    @Transactional
    protected void cerrarSesionMasAntiguaDelUsuario(Usuario usuario) {
        List<SesionActiva> sesiones = sesionActivaRepository.findByUsuarioAndActivaTrue(usuario);
        sesiones.stream()
                .min((s1, s2) -> s1.getInicioSesion().compareTo(s2.getInicioSesion()))
                .ifPresent(sesion -> {
                    sesion.cerrar();
                    sesionActivaRepository.save(sesion);
                    sesionesActivas.remove(sesion.getToken());
                });
    }

    /**
     * Limpia sesiones expiradas
     * Debe ejecutarse periódicamente
     */
    @Transactional
    public void limpiarSesionesExpiradas() {
        LocalDateTime fechaLimite = LocalDateTime.now().minusHours(24);
        List<SesionActiva> expiradas = sesionActivaRepository.findSesionesExpiradas(fechaLimite);

        for (SesionActiva sesion : expiradas) {
            sesion.cerrar();
            sesionActivaRepository.save(sesion);
            sesionesActivas.remove(sesion.getToken());
        }

        if (!expiradas.isEmpty()) {
            log.info("Sesiones expiradas cerradas: {}", expiradas.size());
        }
    }

    /**
     * Obtiene el número de sesiones activas de un usuario
     *
     * @param usuario usuario a consultar
     * @return número de sesiones activas
     */
    public long contarSesionesActivasDelUsuario(Usuario usuario) {
        return sesionActivaRepository.countByUsuarioAndActivaTrue(usuario);
    }

    /**
     * Obtiene todas las sesiones activas de un usuario
     *
     * @param usuario usuario a consultar
     * @return lista de sesiones activas
     */
    public List<SesionActiva> obtenerSesionesDelUsuario(Usuario usuario) {
        return sesionActivaRepository.findByUsuarioAndActivaTrue(usuario);
    }
}
