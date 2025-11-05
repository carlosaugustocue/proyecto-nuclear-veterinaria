package com.veterinaria.infrastructure.constants;

/**
 * Constantes relacionadas con el Value Object Telefono.
 * Evita magic numbers y hard coding.
 *
 * @author Sistema Veterinaria
 */
public final class TelefonoConstants {

    private TelefonoConstants() {
        throw new UnsupportedOperationException("Clase de constantes no instanciable");
    }

    /**
     * Mínimo de dígitos requeridos para un número telefónico válido.
     */
    public static final int MIN_DIGITOS = 7;

    /**
     * Longitud estándar de un número celular en Colombia.
     */
    public static final int LONGITUD_CELULAR_COLOMBIA = 10;

    /**
     * Código de país por defecto (Colombia).
     * TODO: Esto debería venir de application.properties en el futuro
     */
    public static final String CODIGO_PAIS_DEFAULT = "+57";
}
