package com.veterinaria.domain.enums;

import lombok.Getter;

/**
 * Estados de un tratamiento médico.
 * Patrón State - Controla el flujo y transiciones de un tratamiento.
 */
@Getter
public enum EstadoTratamiento {
    PRESCRITO("Prescrito", "Tratamiento prescrito pero no iniciado"),
    EN_CURSO("En Curso", "Tratamiento en ejecución"),
    COMPLETADO("Completado", "Tratamiento completado exitosamente"),
    SUSPENDIDO("Suspendido", "Tratamiento suspendido temporalmente"),
    CANCELADO("Cancelado", "Tratamiento cancelado");

    private final String displayName;
    private final String descripcion;

    EstadoTratamiento(String displayName, String descripcion) {
        this.displayName = displayName;
        this.descripcion = descripcion;
    }

    /**
     * Verifica si se puede transicionar al estado destino.
     *
     * @param targetState Estado destino
     * @return true si la transición es válida
     */
    public boolean canTransitionTo(EstadoTratamiento targetState) {
        return switch (this) {
            case PRESCRITO -> targetState == EN_CURSO ||
                    targetState == CANCELADO;
            case EN_CURSO -> targetState == COMPLETADO ||
                    targetState == SUSPENDIDO ||
                    targetState == CANCELADO;
            case SUSPENDIDO -> targetState == EN_CURSO ||
                    targetState == CANCELADO;
            case COMPLETADO, CANCELADO -> false;
        };
    }
}
