package com.veterinaria.application.service;

import com.veterinaria.application.repository.SesionRepository;
import com.veterinaria.application.repository.UsuarioRepository;
import com.veterinaria.domain.entity.security.Sesion;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.enums.EstadoSesion;
import com.veterinaria.infrastructure.constants.SecurityConstants;
import com.veterinaria.infrastructure.exception.BusinessException;
import com.veterinaria.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servicio de gestión de sesiones.
 * Maneja sesiones activas, expiraciones y limpieza automática.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SesionService {

    private final SesionRepository sesionRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * Obtiene todas las sesiones activas de un usuario.
     *
     * @param usuarioId ID del usuario
     * @return Lista de sesiones activas
     */
    @Transactional(readOnly = true)
    public List<Sesion> obtenerSesionesActivas(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));

        return sesionRepository.findByUsuarioAndEstado(usuario, EstadoSesion.ACTIVA);
    }

    /**
     * Obtiene todas las sesiones de un usuario (activas e inactivas).
     *
     * @param usuarioId ID del usuario
     * @return Lista de todas las sesiones
     */
    @Transactional(readOnly = true)
    public List<Sesion> obtenerTodasLasSesiones(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));

        return sesionRepository.findByUsuarioOrderByFechaInicioDesc(usuario);
    }

    /**
     * Cuenta las sesiones activas de un usuario.
     *
     * @param usuarioId ID del usuario
     * @return Cantidad de sesiones activas
     */
    @Transactional(readOnly = true)
    public long contarSesionesActivas(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));

        return sesionRepository.countByUsuarioAndEstado(usuario, EstadoSesion.ACTIVA);
    }

    /**
     * Cierra una sesión específica.
     *
     * @param sesionId ID de la sesión
     */
    @Transactional
    public void cerrarSesion(Long sesionId) {
        Sesion sesion = sesionRepository.findById(sesionId)
                .orElseThrow(() -> new ResourceNotFoundException("Sesión", "id", sesionId));

        sesion.cerrarManual();
        sesionRepository.save(sesion);

        log.info("Sesión {} cerrada manualmente", sesionId);
    }

    /**
     * Cierra todas las sesiones activas de un usuario excepto la actual.
     *
     * @param usuarioId ID del usuario
     * @param tokenActual Token de la sesión actual (no se cerrará)
     */
    @Transactional
    public void cerrarOtrasSesiones(Long usuarioId, String tokenActual) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));

        List<Sesion> sesionesActivas = sesionRepository.findByUsuarioAndEstado(
                usuario, EstadoSesion.ACTIVA
        );

        sesionesActivas.stream()
                .filter(sesion -> !sesion.getToken().equals(tokenActual))
                .forEach(sesion -> {
                    sesion.cerrarManual();
                    sesionRepository.save(sesion);
                });

        log.info("Cerradas {} sesiones para usuario {}",
                sesionesActivas.size() - 1, usuario.getUsername());
    }

    /**
     * Cierra todas las sesiones activas de un usuario.
     *
     * @param usuarioId ID del usuario
     */
    @Transactional
    public void cerrarTodasLasSesiones(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));

        sesionRepository.cerrarSesionesActivas(
                usuario,
                EstadoSesion.CERRADA_MANUAL,
                LocalDateTime.now()
        );

        log.info("Todas las sesiones cerradas para usuario {}", usuario.getUsername());
    }

    /**
     * Revoca una sesión (por ejemplo, por actividad sospechosa).
     *
     * @param sesionId ID de la sesión
     */
    @Transactional
    public void revocarSesion(Long sesionId) {
        Sesion sesion = sesionRepository.findById(sesionId)
                .orElseThrow(() -> new ResourceNotFoundException("Sesión", "id", sesionId));

        sesion.revocar();
        sesionRepository.save(sesion);

        log.warn("Sesión {} revocada", sesionId);
    }

    /**
     * Detecta sesiones sospechosas (múltiples IPs en corto tiempo).
     *
     * @param usuarioId ID del usuario
     * @return Map con IPs y cantidad de sesiones
     */
    @Transactional(readOnly = true)
    public Map<String, Long> detectarActividadSospechosa(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));

        // Buscar sesiones de las últimas 24 horas
        LocalDateTime hace24Horas = LocalDateTime.now().minusHours(24);
        List<Sesion> sesionesRecientes = sesionRepository
                .findSesionesPorUsuarioDesde(usuario, hace24Horas);

        // Agrupar por IP y contar
        return sesionesRecientes.stream()
                .collect(Collectors.groupingBy(
                        Sesion::getIpAddress,
                        Collectors.counting()
                ));
    }

    /**
     * Valida si un usuario puede crear una nueva sesión.
     * Verifica el límite máximo de sesiones activas.
     *
     * @param usuarioId ID del usuario
     * @return true si puede crear una nueva sesión
     */
    @Transactional(readOnly = true)
    public boolean puedeCrearNuevaSesion(Long usuarioId) {
        long sesionesActivas = contarSesionesActivas(usuarioId);
        return sesionesActivas < SecurityConstants.MAX_ACTIVE_SESSIONS;
    }

    /**
     * Limpia sesiones expiradas (ejecutado automáticamente).
     * Se ejecuta cada 30 minutos.
     */
    @Scheduled(fixedRate = 1800000) // 30 minutos
    @Transactional
    public void limpiarSesionesExpiradas() {
        LocalDateTime now = LocalDateTime.now();

        // Marcar sesiones expiradas
        List<Sesion> sesionesExpiradas = sesionRepository.findSesionesExpiradas(now);
        sesionesExpiradas.forEach(sesion -> {
            sesion.expirar();
            sesionRepository.save(sesion);
        });

        if (!sesionesExpiradas.isEmpty()) {
            log.info("Marcadas {} sesiones como expiradas", sesionesExpiradas.size());
        }

        // Cerrar sesiones inactivas
        LocalDateTime limiteInactividad = now.minusMinutes(
                SecurityConstants.SESSION_TIMEOUT_MINUTES
        );
        List<Sesion> sesionesInactivas = sesionRepository.findSesionesInactivas(limiteInactividad);
        sesionesInactivas.forEach(sesion -> {
            sesion.cerrarPorInactividad();
            sesionRepository.save(sesion);
        });

        if (!sesionesInactivas.isEmpty()) {
            log.info("Cerradas {} sesiones por inactividad", sesionesInactivas.size());
        }
    }

    /**
     * Elimina sesiones antiguas (más de 90 días).
     * Se ejecuta diariamente a las 3 AM.
     */
    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void eliminarSesionesAntiguas() {
        LocalDateTime hace90Dias = LocalDateTime.now().minusDays(90);
        sesionRepository.eliminarSesionesAntiguas(hace90Dias);
        log.info("Sesiones antiguas eliminadas (anteriores a {})", hace90Dias);
    }

    /**
     * Actualiza la actividad de una sesión.
     *
     * @param token Token de la sesión
     */
    @Transactional
    public void actualizarActividad(String token) {
        sesionRepository.findByToken(token).ifPresent(sesion -> {
            sesion.actualizarActividad();
            sesionRepository.save(sesion);
        });
    }

    /**
     * Obtiene información detallada de una sesión.
     *
     * @param sesionId ID de la sesión
     * @return Sesión
     */
    @Transactional(readOnly = true)
    public Sesion obtenerSesion(Long sesionId) {
        return sesionRepository.findById(sesionId)
                .orElseThrow(() -> new ResourceNotFoundException("Sesión", "id", sesionId));
    }
}
