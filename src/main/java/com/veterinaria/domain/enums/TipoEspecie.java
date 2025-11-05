package com.veterinaria.domain.enums;

import lombok.Getter;

/**
 * Enum que define los tipos de especies de mascotas atendidas.
 * Cada especie puede tener características y necesidades específicas.
 */
@Getter
public enum TipoEspecie {
    PERRO("Canino", "Canis lupus familiaris", "Mamífero doméstico carnívoro"),
    GATO("Felino", "Felis catus", "Mamífero doméstico carnívoro"),
    AVE("Aviar", "Aves", "Vertebrado ovíparo con plumas"),
    REPTIL("Reptil", "Reptilia", "Vertebrado de sangre fría con escamas"),
    ROEDOR("Roedor", "Rodentia", "Mamífero con dientes incisivos de crecimiento continuo"),
    OTRO("Otro", "Otra especie", "Otras especies no categorizadas");

    private final String nombreComun;
    private final String nombreCientifico;
    private final String descripcion;

    TipoEspecie(String nombreComun, String nombreCientifico, String descripcion) {
        this.nombreComun = nombreComun;
        this.nombreCientifico = nombreCientifico;
        this.descripcion = descripcion;
    }
}
