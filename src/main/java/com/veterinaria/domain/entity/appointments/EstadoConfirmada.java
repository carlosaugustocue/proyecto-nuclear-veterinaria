package com.veterinaria.domain.entity.appointments;

import lombok.extern.slf4j.Slf4j;

/**
 * Estado Confirmada: La cita ha sido confirmada por el cliente.
 * Está lista para ser atendida en la fecha programada.
 *
 * Patrón de Diseño: State Pattern (implementación concreta)
 *
 * @author Sistema Veterinaria
 */
@Slf4j
public class EstadoConfirmada implements EstadoCita {

    private static final EstadoConfirmada INSTANCE = new EstadoConfirmada();

    private EstadoConfirmada() {
        // Constructor privado para Singleton
    }

    public static EstadoConfirmada getInstance() {
        return INSTANCE;
    }

    @Override
    public void manejar(Cita cita) {
        log.info("Cita {} está CONFIRMADA", cita.getId());
        // Lógica específica: enviar recordatorio 24h antes
    }

    @Override
    public boolean puedeTransicionarA(EstadoCita nuevoEstado) {
        // Desde CONFIRMADA puede ir a: EN_PROGRESO o CANCELADA
        return nuevoEstado instanceof EstadoEnProgreso ||
               nuevoEstado instanceof EstadoCancelada;
    }

    @Override
    public EstadoCita siguienteEstado() {
        return EstadoEnProgreso.getInstance();
    }

    @Override
    public String getNombre() {
        return "CONFIRMADA";
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
