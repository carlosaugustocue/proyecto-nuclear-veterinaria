package com.veterinaria.application.service;

import com.veterinaria.application.repository.AuditoriaAccesoRepository;
import com.veterinaria.application.repository.UsuarioRepository;
import com.veterinaria.domain.entity.security.AuditoriaAcceso;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servicio de auditoría de accesos.
 * Gestiona consultas y análisis de registros de auditoría para seguridad y trazabilidad.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuditoriaService {

    private final AuditoriaAccesoRepository auditoriaAccesoRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * Mínimo de intentos fallidos desde una IP para considerarla sospechosa.
     */
    private static final int MIN_INTENTOS_SOSPECHOSOS = 5;

    /**
     * Obtiene el historial de auditoría de un usuario.
     *
     * @param usuarioId ID del usuario
     * @param pageable  Configuración de paginación
     * @return Página de registros de auditoría
     */
    @Transactional(readOnly = true)
    public Page<AuditoriaAcceso> obtenerHistorialUsuario(Long usuarioId, Pageable pageable) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));

        return auditoriaAccesoRepository.findByUsuarioOrderByFechaHoraDesc(usuario, pageable);
    }

    /**
     * Obtiene registros de auditoría por username.
     *
     * @param username Username
     * @param pageable Configuración de paginación
     * @return Página de registros de auditoría
     */
    @Transactional(readOnly = true)
    public Page<AuditoriaAcceso> obtenerPorUsername(String username, Pageable pageable) {
        return auditoriaAccesoRepository.findByUsernameOrderByFechaHoraDesc(username, pageable);
    }

    /**
     * Obtiene intentos fallidos de login de un usuario.
     *
     * @param username Username
     * @param desde    Fecha desde
     * @return Lista de intentos fallidos
     */
    @Transactional(readOnly = true)
    public List<AuditoriaAcceso> obtenerIntentosFallidos(String username, LocalDateTime desde) {
        return auditoriaAccesoRepository.findIntentosFallidosPorUsername(username, desde);
    }

    /**
     * Obtiene registros de auditoría por acción.
     *
     * @param accion   Acción realizada
     * @param pageable Configuración de paginación
     * @return Página de registros
     */
    @Transactional(readOnly = true)
    public Page<AuditoriaAcceso> obtenerPorAccion(String accion, Pageable pageable) {
        return auditoriaAccesoRepository.findByAccionOrderByFechaHoraDesc(accion, pageable);
    }

    /**
     * Obtiene todos los registros sospechosos.
     *
     * @param pageable Configuración de paginación
     * @return Página de registros sospechosos
     */
    @Transactional(readOnly = true)
    public Page<AuditoriaAcceso> obtenerActividadSospechosa(Pageable pageable) {
        return auditoriaAccesoRepository.findBySospechosoTrueOrderByFechaHoraDesc(pageable);
    }

    /**
     * Obtiene registros de auditoría por dirección IP.
     *
     * @param ipAddress Dirección IP
     * @param pageable  Configuración de paginación
     * @return Página de registros
     */
    @Transactional(readOnly = true)
    public Page<AuditoriaAcceso> obtenerPorIP(String ipAddress, Pageable pageable) {
        return auditoriaAccesoRepository.findByIpAddressOrderByFechaHoraDesc(ipAddress, pageable);
    }

    /**
     * Obtiene registros en un rango de fechas.
     *
     * @param desde    Fecha desde
     * @param hasta    Fecha hasta
     * @param pageable Configuración de paginación
     * @return Página de registros
     */
    @Transactional(readOnly = true)
    public Page<AuditoriaAcceso> obtenerPorRangoFechas(LocalDateTime desde,
                                                        LocalDateTime hasta,
                                                        Pageable pageable) {
        return auditoriaAccesoRepository.findByFechaHoraBetween(desde, hasta, pageable);
    }

    /**
     * Detecta IPs sospechosas con múltiples intentos fallidos.
     *
     * @param horasAtras Horas hacia atrás para analizar
     * @return Map con IPs sospechosas y cantidad de intentos
     */
    @Transactional(readOnly = true)
    public Map<String, Long> detectarIPsSospechosas(int horasAtras) {
        LocalDateTime desde = LocalDateTime.now().minusHours(horasAtras);

        List<Object[]> resultados = auditoriaAccesoRepository.findActividadSospechosaPorIp(
                desde,
                MIN_INTENTOS_SOSPECHOSOS
        );

        return resultados.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],  // IP Address
                        row -> (Long) row[1]      // Cantidad de intentos
                ));
    }

    /**
     * Cuenta intentos fallidos desde una IP en un período.
     *
     * @param ipAddress Dirección IP
     * @param horasAtras Horas hacia atrás
     * @return Cantidad de intentos fallidos
     */
    @Transactional(readOnly = true)
    public long contarIntentosFallidosPorIP(String ipAddress, int horasAtras) {
        LocalDateTime desde = LocalDateTime.now().minusHours(horasAtras);
        return auditoriaAccesoRepository.countIntentosFallidosPorIp(ipAddress, desde);
    }

    /**
     * Obtiene estadísticas de acceso de un usuario.
     *
     * @param usuarioId ID del usuario
     * @param desde     Fecha desde
     * @param hasta     Fecha hasta
     * @return Map con estadísticas [acción -> cantidad]
     */
    @Transactional(readOnly = true)
    public Map<String, Long> obtenerEstadisticasUsuario(Long usuarioId,
                                                          LocalDateTime desde,
                                                          LocalDateTime hasta) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));

        List<Object[]> resultados = auditoriaAccesoRepository.obtenerEstadisticasPorUsuario(
                usuario, desde, hasta
        );

        return resultados.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],  // Acción
                        row -> (Long) row[1]      // Cantidad
                ));
    }

    /**
     * Obtiene el último acceso exitoso de un usuario.
     *
     * @param usuarioId ID del usuario
     * @return Último registro de acceso exitoso
     */
    @Transactional(readOnly = true)
    public AuditoriaAcceso obtenerUltimoAccesoExitoso(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));

        return auditoriaAccesoRepository.findUltimoAccesoExitoso(usuario);
    }

    /**
     * Analiza patrones de acceso anormales para un usuario.
     *
     * @param usuarioId ID del usuario
     * @param horasAtras Horas hacia atrás para analizar
     * @return Análisis de actividad
     */
    @Transactional(readOnly = true)
    public ActividadAnalisis analizarActividad(Long usuarioId, int horasAtras) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));

        LocalDateTime desde = LocalDateTime.now().minusHours(horasAtras);

        // Obtener todas las acciones en el período
        List<AuditoriaAcceso> registros = auditoriaAccesoRepository
                .findIntentosFallidosPorUsername(usuario.getUsername(), desde);

        // Analizar IPs únicas
        long ipsUnicas = registros.stream()
                .map(AuditoriaAcceso::getIpAddress)
                .distinct()
                .count();

        // Contar intentos fallidos
        long intentosFallidos = registros.stream()
                .filter(r -> !r.getExitoso())
                .count();

        // Detectar accesos desde múltiples IPs
        boolean accesoMultiplesIPs = ipsUnicas > 3;

        return ActividadAnalisis.builder()
                .usuarioId(usuarioId)
                .username(usuario.getUsername())
                .periodoAnalizado(horasAtras)
                .totalAccesos(registros.size())
                .intentosFallidos(intentosFallidos)
                .ipsUnicas(ipsUnicas)
                .actividadSospechosa(accesoMultiplesIPs || intentosFallidos > 3)
                .build();
    }

    /**
     * Limpia registros antiguos de auditoría (más de 1 año).
     * Se ejecuta mensualmente el primer día a las 2 AM.
     */
    @Scheduled(cron = "0 0 2 1 * *")
    @Transactional
    public void limpiarRegistrosAntiguos() {
        LocalDateTime hace1Año = LocalDateTime.now().minusYears(1);
        auditoriaAccesoRepository.eliminarRegistrosAntiguos(hace1Año);
        log.info("Registros de auditoría antiguos eliminados (anteriores a {})", hace1Año);
    }

    /**
     * Clase interna para análisis de actividad.
     */
    @lombok.Builder
    @lombok.Data
    public static class ActividadAnalisis {
        private Long usuarioId;
        private String username;
        private int periodoAnalizado;
        private long totalAccesos;
        private long intentosFallidos;
        private long ipsUnicas;
        private boolean actividadSospechosa;
    }
}
