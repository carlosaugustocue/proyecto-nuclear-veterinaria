package com.veterinaria.domain.service;

import com.veterinaria.application.repository.AuditoriaRepository;
import com.veterinaria.domain.entity.security.RegistroAuditoria;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.enums.ResultadoAccion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio para registro de auditoría de accesos y acciones.
 * Permite trazabilidad completa de todas las operaciones del sistema.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuditoriaAcceso {

    private final AuditoriaRepository auditoriaRepository;

    /**
     * Registra un acceso exitoso
     *
     * @param usuario usuario que realiza la acción
     * @param accion descripción de la acción
     * @param modulo módulo del sistema
     * @param recurso recurso afectado
     * @param direccionIP dirección IP del cliente
     */
    @Async
    @Transactional
    public void registrarAccesoExitoso(Usuario usuario, String accion, String modulo, 
                                       String recurso, String direccionIP) {
        registrarAccion(usuario, accion, modulo, recurso, ResultadoAccion.EXITOSO, 
                       direccionIP, null, null);
    }

    /**
     * Registra un acceso denegado por falta de permisos
     *
     * @param usuario usuario que intenta la acción
     * @param accion acción intentada
     * @param modulo módulo del sistema
     * @param recurso recurso solicitado
     * @param direccionIP dirección IP del cliente
     * @param detalles detalles adicionales
     */
    @Async
    @Transactional
    public void registrarAccesoDenegado(Usuario usuario, String accion, String modulo, 
                                        String recurso, String direccionIP, String detalles) {
        registrarAccion(usuario, accion, modulo, recurso, 
                       ResultadoAccion.DENEGADO_SIN_PERMISO, direccionIP, detalles, null);
    }

    /**
     * Registra un intento de acceso sin autenticación
     *
     * @param accion acción intentada
     * @param modulo módulo del sistema
     * @param direccionIP dirección IP del cliente
     * @param detalles detalles del intento
     */
    @Async
    @Transactional
    public void registrarAccesoSinAutenticacion(String accion, String modulo, 
                                                String direccionIP, String detalles) {
        registrarAccion(null, accion, modulo, null, 
                       ResultadoAccion.DENEGADO_SIN_AUTENTICACION, direccionIP, detalles, null);
    }

    /**
     * Registra un intento de acceso con cuenta bloqueada
     *
     * @param email email del usuario bloqueado
     * @param direccionIP dirección IP del cliente
     */
    @Async
    @Transactional
    public void registrarAccesoBloqueado(String email, String direccionIP) {
        registrarAccion(null, "LOGIN_BLOQUEADO", "AUTENTICACION", email, 
                       ResultadoAccion.DENEGADO_BLOQUEADO, direccionIP, 
                       "Cuenta bloqueada por intentos fallidos", null);
    }

    /**
     * Registra un error en una operación
     *
     * @param usuario usuario que realiza la operación
     * @param accion acción realizada
     * @param modulo módulo del sistema
     * @param direccionIP dirección IP del cliente
     * @param detalles detalles del error
     */
    @Async
    @Transactional
    public void registrarError(Usuario usuario, String accion, String modulo, 
                              String direccionIP, String detalles) {
        registrarAccion(usuario, accion, modulo, null, ResultadoAccion.ERROR, 
                       direccionIP, detalles, null);
    }

    /**
     * Registra un login exitoso
     *
     * @param usuario usuario que inicia sesión
     * @param direccionIP dirección IP del cliente
     */
    @Async
    @Transactional
    public void registrarLoginExitoso(Usuario usuario, String direccionIP) {
        registrarAccion(usuario, "LOGIN", "AUTENTICACION", usuario.getEmail(), 
                       ResultadoAccion.EXITOSO, direccionIP, "Login exitoso", null);
    }

    /**
     * Registra un login fallido
     *
     * @param email email del usuario
     * @param direccionIP dirección IP del cliente
     * @param motivo motivo del fallo
     */
    @Async
    @Transactional
    public void registrarLoginFallido(String email, String direccionIP, String motivo) {
        registrarAccion(null, "LOGIN_FALLIDO", "AUTENTICACION", email, 
                       ResultadoAccion.DENEGADO_SIN_AUTENTICACION, direccionIP, motivo, null);
    }

    /**
     * Registra un logout
     *
     * @param usuario usuario que cierra sesión
     * @param direccionIP dirección IP del cliente
     */
    @Async
    @Transactional
    public void registrarLogout(Usuario usuario, String direccionIP) {
        registrarAccion(usuario, "LOGOUT", "AUTENTICACION", usuario.getEmail(), 
                       ResultadoAccion.EXITOSO, direccionIP, "Logout exitoso", null);
    }

    /**
     * Método genérico para registrar una acción
     *
     * @param usuario usuario (puede ser null)
     * @param accion descripción de la acción
     * @param modulo módulo del sistema
     * @param recurso recurso afectado
     * @param resultado resultado de la acción
     * @param direccionIP dirección IP
     * @param detalles detalles adicionales
     * @param duracionMs duración en milisegundos
     */
    @Transactional
    public void registrarAccion(Usuario usuario, String accion, String modulo, String recurso,
                                ResultadoAccion resultado, String direccionIP, 
                                String detalles, Long duracionMs) {
        try {
            RegistroAuditoria registro = RegistroAuditoria.builder()
                    .usuario(usuario)
                    .fechaHora(LocalDateTime.now())
                    .accion(accion)
                    .modulo(modulo)
                    .recurso(recurso)
                    .resultado(resultado)
                    .direccionIP(direccionIP)
                    .detalles(detalles)
                    .duracionMs(duracionMs)
                    .build();

            auditoriaRepository.save(registro);

            if (registro.esAccesoNoAutorizado()) {
                log.warn("Acceso no autorizado registrado: {} - {} desde {}", 
                        accion, recurso, direccionIP);
            }

            if (registro.esOperacionCritica()) {
                log.warn("Operación crítica registrada: {} - {} por {}", 
                        accion, recurso, usuario != null ? usuario.getEmail() : "N/A");
            }

        } catch (Exception e) {
            log.error("Error al registrar auditoría: {}", e.getMessage(), e);
        }
    }

    /**
     * Busca registros de auditoría de un usuario
     *
     * @param usuario usuario a buscar
     * @return lista de registros
     */
    public List<RegistroAuditoria> buscarPorUsuario(Usuario usuario) {
        return auditoriaRepository.findByUsuario(usuario);
    }

    /**
     * Busca registros de auditoría por módulo
     *
     * @param modulo módulo a buscar
     * @return lista de registros
     */
    public List<RegistroAuditoria> buscarPorModulo(String modulo) {
        return auditoriaRepository.findByModulo(modulo);
    }

    /**
     * Busca accesos no autorizados en un rango de fechas
     *
     * @param inicio fecha de inicio
     * @param fin fecha de fin
     * @return lista de accesos no autorizados
     */
    public List<RegistroAuditoria> buscarAccesosNoAutorizados(LocalDateTime inicio, LocalDateTime fin) {
        return auditoriaRepository.findAccesosNoAutorizadosBetween(inicio, fin);
    }

    /**
     * Busca operaciones críticas en un rango de fechas
     *
     * @param inicio fecha de inicio
     * @param fin fecha de fin
     * @return lista de operaciones críticas
     */
    public List<RegistroAuditoria> buscarOperacionesCriticas(LocalDateTime inicio, LocalDateTime fin) {
        return auditoriaRepository.findOperacionesCriticasBetween(inicio, fin);
    }
}
