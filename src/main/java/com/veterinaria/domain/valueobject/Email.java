package com.veterinaria.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.regex.Pattern;

/**
 * Value Object que representa un email.
 * Patrón: Value Object - Inmutable y con validación encapsulada.
 */
@Getter
@EqualsAndHashCode
public final class Email {

    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final String valor;

    /**
     * Crea un nuevo Email validando el formato.
     * @param valor el email a validar
     * @throws IllegalArgumentException si el email no es válido
     */
    public Email(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }

        String emailLimpio = valor.trim().toLowerCase();

        if (!EMAIL_PATTERN.matcher(emailLimpio).matches()) {
            throw new IllegalArgumentException("El formato del email no es válido: " + valor);
        }

        this.valor = emailLimpio;
    }

    /**
     * Obtiene el dominio del email.
     * @return el dominio (ejemplo: gmail.com)
     */
    public String obtenerDominio() {
        return valor.substring(valor.indexOf('@') + 1);
    }

    /**
     * Obtiene el nombre de usuario del email.
     * @return el nombre antes del @
     */
    public String obtenerNombreUsuario() {
        return valor.substring(0, valor.indexOf('@'));
    }

    @Override
    public String toString() {
        return valor;
    }
}
