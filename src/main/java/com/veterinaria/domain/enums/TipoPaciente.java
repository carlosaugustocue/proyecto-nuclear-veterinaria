package com.veterinaria.domain.enums;

import lombok.Getter;

/**
 * Tipos de paciente según su clasificación.
 */
@Getter
public enum TipoPaciente {
    PERRO("Perro", "Canis lupus familiaris"),
    GATO("Gato", "Felis silvestris catus"),
    AVE("Ave", "Aves diversas"),
    ROEDOR("Roedor", "Pequeños mamíferos roedores"),
    REPTIL("Reptil", "Reptiles varios"),
    EXOTICO("Exótico", "Animales exóticos"),
    OTRO("Otro", "Otros tipos de animales");

    private final String displayName;
    private final String descripcion;

    TipoPaciente(String displayName, String descripcion) {
        this.displayName = displayName;
        this.descripcion = descripcion;
    }
}
