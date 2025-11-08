package com.veterinaria.domain.entity.appointments;

import lombok.extern.slf4j.Slf4j;

/**
 * Estado Programada: Primera etapa del ciclo de vida de una cita.
 * La cita ha sido creada pero aún no ha sido confirmada por el cliente.
 *
 * Patrón de Diseño: State Pattern (implementación concreta)
 *
 * @author Sistema Veterinaria
 */
@Slf4j
public class EstadoProgramada implements EstadoCita {

    private static final EstadoProgramada INSTANCE = new EstadoProgramada();

    private EstadoProgramada() {
        // Constructor privado para Singleton
    }

    public static EstadoProgramada getInstance() {
        return INSTANCE;
    }

    @Override
    public void manejar(Cita cita) {
        log.info("Cita {} está en estado PROGRAMADA", cita.getId());
        // Lógica específica: enviar recordatorio de confirmación
    }

    @Override
    public boolean puedeTransicionarA(EstadoCita nuevoEstado) {
        // Desde PROGRAMADA puede ir a: CONFIRMADA o CANCELADA
        return nuevoEstado instanceof EstadoConfirmada ||
               nuevoEstado instanceof EstadoCancelada;
    }

    @Override
    public EstadoCita siguienteEstado() {
        return EstadoConfirmada.getInstance();
    }

    @Override
    public String getNombre() {
        return "PROGRAMADA";
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
