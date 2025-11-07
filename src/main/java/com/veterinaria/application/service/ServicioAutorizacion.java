package com.veterinaria.application.service;

import com.veterinaria.domain.entity.security.Permiso;
import com.veterinaria.domain.entity.security.Rol;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.enums.ResultadoAccion;
import com.veterinaria.domain.service.AuditoriaAcceso;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Servicio de autorización que valida permisos y roles de usuarios.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ServicioAutorizacion {

    private final AuditoriaAcceso auditoriaAcceso;

    /**
     * Verifica si un usuario tiene un permiso específico
     *
     * @param usuario usuario a verificar
     * @param codigoPermiso código del permiso
     * @return true si tiene el permiso
     */
    public boolean tienePermiso(Usuario usuario, String codigoPermiso) {
        if (usuario == null || codigoPermiso == null) {
            return false;
        }

        return usuario.getRoles().stream()
                .filter(Rol::getIsActive)
                .flatMap(rol -> rol.getPermisos().stream())
                .filter(Permiso::getIsActive)
                .anyMatch(permiso -> permiso.getCodigo().equals(codigoPermiso));
    }

    /**
     * Verifica si un usuario tiene al menos uno de los permisos especificados
     *
     * @param usuario usuario a verificar
     * @param codigosPermisos códigos de permisos
     * @return true si tiene al menos uno
     */
    public boolean tieneAlgunoDeEstosPermisos(Usuario usuario, String... codigosPermisos) {
        if (usuario == null || codigosPermisos == null || codigosPermisos.length == 0) {
            return false;
        }

        for (String codigo : codigosPermisos) {
            if (tienePermiso(usuario, codigo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si un usuario tiene todos los permisos especificados
     *
     * @param usuario usuario a verificar
     * @param codigosPermisos códigos de permisos
     * @return true si tiene todos
     */
    public boolean tieneTodosLosPermisos(Usuario usuario, String... codigosPermisos) {
        if (usuario == null || codigosPermisos == null || codigosPermisos.length == 0) {
            return false;
        }

        for (String codigo : codigosPermisos) {
            if (!tienePermiso(usuario, codigo)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica si un usuario tiene un rol específico
     *
     * @param usuario usuario a verificar
     * @param nombreRol nombre del rol
     * @return true si tiene el rol
     */
    public boolean tieneRol(Usuario usuario, String nombreRol) {
        if (usuario == null || nombreRol == null) {
            return false;
        }

        return usuario.getRoles().stream()
                .filter(Rol::getIsActive)
                .anyMatch(rol -> rol.getNombre().equals(nombreRol));
    }

    /**
     * Verifica si un usuario tiene al menos uno de los roles especificados
     *
     * @param usuario usuario a verificar
     * @param nombresRoles nombres de roles
     * @return true si tiene al menos uno
     */
    public boolean tieneAlgunoDeEstosRoles(Usuario usuario, String... nombresRoles) {
        if (usuario == null || nombresRoles == null || nombresRoles.length == 0) {
            return false;
        }

        for (String nombreRol : nombresRoles) {
            if (tieneRol(usuario, nombreRol)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene todos los permisos de un usuario
     *
     * @param usuario usuario
     * @return lista de códigos de permisos
     */
    public List<String> obtenerPermisosDelUsuario(Usuario usuario) {
        if (usuario == null) {
            return List.of();
        }

        return usuario.getRoles().stream()
                .filter(Rol::getIsActive)
                .flatMap(rol -> rol.getPermisos().stream())
                .filter(Permiso::getIsActive)
                .map(Permiso::getCodigo)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los roles de un usuario
     *
     * @param usuario usuario
     * @return lista de nombres de roles
     */
    public List<String> obtenerRolesDelUsuario(Usuario usuario) {
        if (usuario == null) {
            return List.of();
        }

        return usuario.getRoles().stream()
                .filter(Rol::getIsActive)
                .map(Rol::getNombre)
                .collect(Collectors.toList());
    }

    /**
     * Valida acceso a un recurso con auditoría
     *
     * @param usuario usuario que intenta acceder
     * @param codigoPermiso permiso requerido
     * @param accion acción a realizar
     * @param modulo módulo del sistema
     * @param recurso recurso al que se intenta acceder
     * @param direccionIP dirección IP del cliente
     * @return resultado de la validación
     */
    public ResultadoAccion validarAccesoConAuditoria(Usuario usuario, String codigoPermiso,
                                                      String accion, String modulo, 
                                                      String recurso, String direccionIP) {
        // Usuario no autenticado
        if (usuario == null) {
            auditoriaAcceso.registrarAccesoSinAutenticacion(accion, modulo, direccionIP, 
                    "Usuario no autenticado intentando acceder a " + recurso);
            return ResultadoAccion.DENEGADO_SIN_AUTENTICACION;
        }

        // Cuenta bloqueada
        if (!usuario.isCuentaHabilitada()) {
            auditoriaAcceso.registrarAccesoDenegado(usuario, accion, modulo, recurso, 
                    direccionIP, "Cuenta bloqueada o deshabilitada");
            return ResultadoAccion.DENEGADO_BLOQUEADO;
        }

        // Sin permiso
        if (!tienePermiso(usuario, codigoPermiso)) {
            auditoriaAcceso.registrarAccesoDenegado(usuario, accion, modulo, recurso, 
                    direccionIP, "Usuario no tiene el permiso: " + codigoPermiso);
            log.warn("Acceso denegado: {} intenta {} sin permiso {}", 
                    usuario.getEmail(), accion, codigoPermiso);
            return ResultadoAccion.DENEGADO_SIN_PERMISO;
        }

        // Acceso exitoso
        auditoriaAcceso.registrarAccesoExitoso(usuario, accion, modulo, recurso, direccionIP);
        return ResultadoAccion.EXITOSO;
    }

    /**
     * Valida acceso y lanza excepción si no está autorizado
     *
     * @param usuario usuario
     * @param codigoPermiso permiso requerido
     * @param accion acción
     * @param modulo módulo
     * @param recurso recurso
     * @param direccionIP IP
     * @throws RuntimeException si el acceso es denegado
     */
    public void requierePermiso(Usuario usuario, String codigoPermiso, String accion, 
                               String modulo, String recurso, String direccionIP) {
        ResultadoAccion resultado = validarAccesoConAuditoria(usuario, codigoPermiso, 
                accion, modulo, recurso, direccionIP);

        if (resultado != ResultadoAccion.EXITOSO) {
            throw new RuntimeException(obtenerMensajeError(resultado));
        }
    }

    /**
     * Valida que el usuario tenga al menos uno de los permisos
     *
     * @param usuario usuario
     * @param codigosPermisos permisos requeridos
     * @param accion acción
     * @param modulo módulo
     * @param recurso recurso
     * @param direccionIP IP
     * @throws RuntimeException si no tiene ninguno de los permisos
     */
    public void requiereAlgunoDeEstosPermisos(Usuario usuario, String[] codigosPermisos,
                                             String accion, String modulo, 
                                             String recurso, String direccionIP) {
        if (usuario == null) {
            auditoriaAcceso.registrarAccesoSinAutenticacion(accion, modulo, direccionIP, 
                    "Usuario no autenticado");
            throw new RuntimeException("Usuario no autenticado");
        }

        if (!tieneAlgunoDeEstosPermisos(usuario, codigosPermisos)) {
            auditoriaAcceso.registrarAccesoDenegado(usuario, accion, modulo, recurso, 
                    direccionIP, "Usuario no tiene ninguno de los permisos requeridos");
            throw new RuntimeException("No tiene los permisos necesarios");
        }

        auditoriaAcceso.registrarAccesoExitoso(usuario, accion, modulo, recurso, direccionIP);
    }

    /**
     * Valida que el usuario tenga un rol específico
     *
     * @param usuario usuario
     * @param nombreRol rol requerido
     * @throws RuntimeException si no tiene el rol
     */
    public void requiereRol(Usuario usuario, String nombreRol) {
        if (usuario == null) {
            throw new RuntimeException("Usuario no autenticado");
        }

        if (!tieneRol(usuario, nombreRol)) {
            log.warn("Acceso denegado: {} no tiene el rol {}", usuario.getEmail(), nombreRol);
            throw new RuntimeException("No tiene el rol necesario: " + nombreRol);
        }
    }

    /**
     * Obtiene mensaje de error según el resultado
     *
     * @param resultado resultado de la acción
     * @return mensaje descriptivo
     */
    private String obtenerMensajeError(ResultadoAccion resultado) {
        return switch (resultado) {
            case DENEGADO_SIN_AUTENTICACION -> "Usuario no autenticado";
            case DENEGADO_BLOQUEADO -> "Cuenta bloqueada o deshabilitada";
            case DENEGADO_SIN_PERMISO -> "No tiene permisos para realizar esta acción";
            case ERROR -> "Error al validar permisos";
            default -> "Acceso denegado";
        };
    }

    /**
     * Verifica si un usuario es administrador
     *
     * @param usuario usuario
     * @return true si es administrador
     */
    public boolean esAdministrador(Usuario usuario) {
        return tieneRol(usuario, "ROLE_ADMIN") || tieneRol(usuario, "ROLE_SUPER_ADMIN");
    }

    /**
     * Verifica si un usuario puede gestionar otros usuarios
     *
     * @param usuario usuario
     * @return true si puede gestionar usuarios
     */
    public boolean puedeGestionarUsuarios(Usuario usuario) {
        return tieneAlgunoDeEstosPermisos(usuario, "USUARIOS_CREAR", "USUARIOS_EDITAR", 
                "USUARIOS_ELIMINAR");
    }
}
