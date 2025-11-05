package com.veterinaria.domain.enums;

import lombok.Getter;

/**
 * Enum que define los estados posibles de un paciente en el sistema.
 * Permite gestionar el ciclo de vida de las mascotas registradas.
 */
@Getter
public enum EstadoPaciente {
    ACTIVO("Activo", "Paciente con seguimiento activo", true),
    INACTIVO("Inactivo", "Paciente sin actividad reciente", false),
    FALLECIDO("Fallecido", "Paciente fallecido", false);

    private final String displayName;
    private final String description;
    private final boolean puedeAgendarCitas;

    EstadoPaciente(String displayName, String description, boolean puedeAgendarCitas) {
        this.displayName = displayName;
        this.description = description;
        this.puedeAgendarCitas = puedeAgendarCitas;
    }

    /**
     * Verifica si el paciente puede recibir atención médica.
     */
    public boolean puedeRecibirAtencion() {
        return this == ACTIVO;
    }
}
