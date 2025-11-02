package com.veterinaria.domain.enums;

import lombok.Getter;

/**
 * Estados posibles de una cita.
 * Patrón State - Cada estado define transiciones válidas y comportamientos específicos.
 */
@Getter
public enum EstadoCita {
    PROGRAMADA("Programada", "La cita ha sido agendada"),
    CONFIRMADA("Confirmada", "El propietario ha confirmado la cita"),
    EN_ATENCION("En Atención", "El paciente está siendo atendido"),
    COMPLETADA("Completada", "La cita ha sido completada exitosamente"),
    CANCELADA("Cancelada", "La cita fue cancelada"),
    NO_ASISTIO("No Asistió", "El paciente no se presentó a la cita");

    private final String displayName;
    private final String description;

    EstadoCita(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    /**
     * Verifica si se puede transicionar al estado destino desde el estado actual.
     *
     * @param targetState Estado destino
     * @return true si la transición es válida
     */
    public boolean canTransitionTo(EstadoCita targetState) {
        return switch (this) {
            case PROGRAMADA -> targetState == CONFIRMADA ||
                    targetState == CANCELADA ||
                    targetState == NO_ASISTIO;
            case CONFIRMADA -> targetState == EN_ATENCION ||
                    targetState == CANCELADA ||
                    targetState == NO_ASISTIO;
            case EN_ATENCION -> targetState == COMPLETADA ||
                    targetState == CANCELADA;
            case COMPLETADA, CANCELADA, NO_ASISTIO -> false;
        };
    }
}
