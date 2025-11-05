package com.veterinaria.domain.valueobject;

import com.veterinaria.infrastructure.constants.TelefonoConstants;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value Object que representa un número telefónico.
 * Patrón: Value Object - Inmutable y con validación encapsulada.
 */
@Getter
@EqualsAndHashCode
public final class Telefono {

    private final String numero;
    private final String codigoPais;

    /**
     * Crea un teléfono con código de país.
     * @param numero el número telefónico (mínimo 7 dígitos)
     * @param codigoPais código de país (ejemplo: "+57")
     */
    public Telefono(String numero, String codigoPais) {
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("El número de teléfono no puede estar vacío");
        }

        String numeroLimpio = numero.replaceAll("[^0-9]", "");

        if (numeroLimpio.length() < TelefonoConstants.MIN_DIGITOS) {
            throw new IllegalArgumentException(
                "El número debe tener al menos " + TelefonoConstants.MIN_DIGITOS + " dígitos"
            );
        }

        this.numero = numeroLimpio;
        this.codigoPais = (codigoPais != null && !codigoPais.isBlank())
            ? codigoPais
            : TelefonoConstants.CODIGO_PAIS_DEFAULT;
    }

    /**
     * Crea un teléfono con código de país por defecto.
     * @param numero el número telefónico
     */
    public Telefono(String numero) {
        this(numero, TelefonoConstants.CODIGO_PAIS_DEFAULT);
    }

    /**
     * Formatea el número con guiones.
     * @return número formateado (ejemplo: 300-123-4567)
     */
    public String formatear() {
        if (numero.length() == TelefonoConstants.LONGITUD_CELULAR_COLOMBIA) {
            return String.format("%s-%s-%s",
                numero.substring(0, 3),
                numero.substring(3, 6),
                numero.substring(6));
        }
        return numero;
    }

    /**
     * Obtiene el número completo con código de país.
     * @return ejemplo: +57 3001234567
     */
    public String obtenerNumeroCompleto() {
        return codigoPais + " " + numero;
    }

    @Override
    public String toString() {
        return obtenerNumeroCompleto();
    }
}
