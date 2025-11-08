package com.veterinaria.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción lanzada cuando se intenta crear un recurso que ya existe.
 * Mapea automáticamente al código HTTP 409 CONFLICT.
 *
 * Ejemplos de uso:
 * - Intentar crear un tipo de servicio con un nombre que ya existe
 * - Intentar crear un cliente con un email duplicado
 * - Intentar crear un usuario con un username que ya existe
 *
 * @author Sistema Veterinaria
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateResourceException extends RuntimeException {

    /**
     * Constructor con mensaje
     * @param message el mensaje describiendo el recurso duplicado
     */
    public DuplicateResourceException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa
     * @param message el mensaje de error
     * @param cause la causa de la excepción
     */
    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor para recurso duplicado por campo específico
     * @param resourceName nombre del recurso
     * @param fieldName nombre del campo
     * @param fieldValue valor del campo duplicado
     */
    public DuplicateResourceException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s ya existe con %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
