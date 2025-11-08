package com.veterinaria.domain.entity.appointments;

import lombok.extern.slf4j.Slf4j;

/**
 * Estado Completada: La cita ha sido finalizada exitosamente.
 * El servicio fue prestado y la consulta ha concluido.
 * Este es un estado final.
 *
 * Patrón de Diseño: State Pattern (implementación concreta)
 *
 * @author Sistema Veterinaria
 */
@Slf4j
public class EstadoCompletada implements EstadoCita {

    private static final EstadoCompletada INSTANCE = new EstadoCompletada();

    private EstadoCompletada() {
        // Constructor privado para Singleton
    }

    public static EstadoCompletada getInstance() {
        return INSTANCE;
    }

    @Override
    public void manejar(Cita cita) {
        log.info("Cita {} COMPLETADA exitosamente", cita.getId());
        // Lógica específica: generar factura, enviar encuesta de satisfacción
    }

    @Override
    public boolean puedeTransicionarA(EstadoCita nuevoEstado) {
        // Estado final - no puede transicionar a ningún otro estado
        return false;
    }

    @Override
    public EstadoCita siguienteEstado() {
        // No hay siguiente estado (es estado final)
        return null;
    }

    @Override
    public String getNombre() {
        return "COMPLETADA";
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
