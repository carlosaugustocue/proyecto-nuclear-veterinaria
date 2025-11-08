package com.veterinaria.domain.entity.appointments;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter de JPA para persistir objetos EstadoCita como String.
 * Permite trabajar con el State Pattern en el código y almacenar como VARCHAR en BD.
 *
 * @author Sistema Veterinaria
 */
@Converter(autoApply = true)
public class EstadoCitaConverter implements AttributeConverter<EstadoCita, String> {

    @Override
    public String convertToDatabaseColumn(EstadoCita estado) {
        if (estado == null) {
            return null;
        }
        return estado.getNombre();
    }

    @Override
    public EstadoCita convertToEntityAttribute(String nombreEstado) {
        if (nombreEstado == null || nombreEstado.isBlank()) {
            return null;
        }

        return switch (nombreEstado.toUpperCase()) {
            case "PROGRAMADA" -> EstadoProgramada.getInstance();
            case "CONFIRMADA" -> EstadoConfirmada.getInstance();
            case "EN_PROGRESO" -> EstadoEnProgreso.getInstance();
            case "COMPLETADA" -> EstadoCompletada.getInstance();
            case "CANCELADA" -> EstadoCancelada.getInstance();
            default -> throw new IllegalArgumentException("Estado de cita desconocido: " + nombreEstado);
        };
    }
}
