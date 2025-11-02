package com.veterinaria.domain.enums;

/**
 * Estados posibles de una sesión de usuario.
 * Patrón State - Cada estado representa un comportamiento diferente de la sesión.
 */
public enum EstadoSesion {
    ACTIVA,
    EXPIRADA,
    CERRADA_MANUAL,
    CERRADA_POR_INACTIVIDAD,
    REVOCADA
}
