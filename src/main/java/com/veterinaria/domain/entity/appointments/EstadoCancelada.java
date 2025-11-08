package com.veterinaria.domain.entity.appointments;

import lombok.extern.slf4j.Slf4j;

/**
 * Estado Cancelada: La cita ha sido cancelada por el cliente o la clínica.
 * Este es un estado final.
 *
 * Patrón de Diseño: State Pattern (implementación concreta)
 *
 * @author Sistema Veterinaria
 */
@Slf4j
public class EstadoCancelada implements EstadoCita {

    private static final EstadoCancelada INSTANCE = new EstadoCancelada();

    private EstadoCancelada() {
        // Constructor privado para Singleton
    }

    public static EstadoCancelada getInstance() {
        return INSTANCE;
    }

    @Override
    public void manejar(Cita cita) {
        log.info("Cita {} ha sido CANCELADA. Motivo: {}", cita.getId(), cita.getMotivoCancelacion());
        // Lógica específica: liberar horario, notificar al cliente y veterinario
    }

    @Override
    public boolean puedeTransicionarA(EstadoCita nuevoEstado) {
        // Estado final - no puede transicionar a ningún otro estado
        // Una cita cancelada no puede reactivarse, debe crearse una nueva
        return false;
    }

    @Override
    public EstadoCita siguienteEstado() {
        // No hay siguiente estado (es estado final)
        return null;
    }

    @Override
    public String getNombre() {
        return "CANCELADA";
    }

    @Override
    public boolean esEstadoFinal() {
        return true;
    }

    @Override
    public String toString() {
        return getNombre();
    }
}
