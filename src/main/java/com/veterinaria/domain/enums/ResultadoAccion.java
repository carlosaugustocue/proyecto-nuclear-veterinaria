package com.veterinaria.domain.enums;

/**
 * Enum para representar el resultado de una acción en el sistema.
 * Utilizado principalmente en auditoría de accesos.
 *
 * @author Sistema Veterinaria
 */
public enum ResultadoAccion {
    /**
     * La acción se completó exitosamente
     */
    EXITOSO,

    /**
     * La acción fue denegada por falta de permisos
     */
    DENEGADO_SIN_PERMISO,

    /**
     * La acción fue denegada por falta de autenticación
     */
    DENEGADO_SIN_AUTENTICACION,

    /**
     * La acción fue denegada porque el usuario está bloqueado
     */
    DENEGADO_BLOQUEADO,

    /**
     * Ocurrió un error durante la ejecución de la acción
     */
    ERROR
}
