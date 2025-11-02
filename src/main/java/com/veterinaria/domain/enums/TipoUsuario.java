package com.veterinaria.domain.enums;

import lombok.Getter;

/**
 * Enum que define los tipos de usuario en el sistema.
 * Cada tipo tiene roles y permisos específicos.
 */
@Getter
public enum TipoUsuario {
    ADMINISTRADOR("Administrador del Sistema", "Acceso completo al sistema"),
    VETERINARIO("Médico Veterinario", "Atención médica y diagnóstico"),
    ASISTENTE("Asistente Veterinario", "Apoyo en procedimientos médicos"),
    RECEPCIONISTA("Recepcionista", "Gestión de citas y atención al cliente"),
    PROPIETARIO("Propietario de Mascota", "Visualización de información de sus mascotas");

    private final String displayName;
    private final String description;

    TipoUsuario(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
}
