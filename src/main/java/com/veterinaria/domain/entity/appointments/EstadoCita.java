package com.veterinaria.domain.entity.appointments;

/**
 * Interface que define el contrato del State Pattern para los estados de una Cita.
 * Cada estado concreto implementará su propia lógica de manejo y transiciones.
 *
 * Patrón de Diseño: State Pattern
 *
 * @author Sistema Veterinaria
 */
public interface EstadoCita {

    /**
     * Maneja las acciones específicas de este estado
     * @param cita la cita sobre la cual actuar
     */
    void manejar(Cita cita);

    /**
     * Verifica si se puede transicionar a otro estado
     * @param nuevoEstado el estado destino
     * @return true si la transición es válida
     */
    boolean puedeTransicionarA(EstadoCita nuevoEstado);

    /**
     * Obtiene el siguiente estado lógico en el flujo
     * @return el siguiente estado, o null si es un estado final
     */
    EstadoCita siguienteEstado();

    /**
     * Obtiene el nombre del estado
     * @return nombre del estado
     */
    String getNombre();

    /**
     * Verifica si es un estado terminal (no puede transicionar)
     * @return true si es un estado final
     */
    boolean esEstadoFinal();
}
