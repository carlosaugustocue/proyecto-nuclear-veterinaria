package com.veterinaria.domain.enums;

import lombok.Getter;

/**
 * Enum que define el sexo biológico de los pacientes (mascotas).
 */
@Getter
public enum Sexo {
    MACHO("Macho", "M"),
    HEMBRA("Hembra", "H");

    private final String displayName;
    private final String abreviacion;

    Sexo(String displayName, String abreviacion) {
        this.displayName = displayName;
        this.abreviacion = abreviacion;
    }
}
