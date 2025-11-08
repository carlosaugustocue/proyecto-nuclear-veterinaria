package com.veterinaria.domain.entity.appointments;

import lombok.extern.slf4j.Slf4j;

/**
 * Estado En Progreso: La consulta o servicio está siendo realizado actualmente.
 * El veterinario está atendiendo al paciente.
 *
 * Patrón de Diseño: State Pattern (implementación concreta)
 *
 * @author Sistema Veterinaria
 */
@Slf4j
public class EstadoEnProgreso implements EstadoCita {

    private static final EstadoEnProgreso INSTANCE = new EstadoEnProgreso();

    private EstadoEnProgreso() {
        // Constructor privado para Singleton
    }

    public static EstadoEnProgreso getInstance() {
        return INSTANCE;
    }

    @Override
    public void manejar(Cita cita) {
        log.info("Cita {} EN PROGRESO - atención iniciada", cita.getId());
        // Lógica específica: registrar hora de inicio, bloquear reagendamiento
    }

    @Override
    public boolean puedeTransicionarA(EstadoCita nuevoEstado) {
        // Desde EN_PROGRESO solo puede ir a: COMPLETADA
        // No se puede cancelar una cita que ya comenzó
        return nuevoEstado instanceof EstadoCompletada;
    }

    @Override
    public EstadoCita siguienteEstado() {
        return EstadoCompletada.getInstance();
    }

    @Override
    public String getNombre() {
        return "EN_PROGRESO";
    }

    @Override
    public boolean esEstadoFinal() {
        return false;
    }

    @Override
    public String toString() {
        return getNombre();
    }
}
