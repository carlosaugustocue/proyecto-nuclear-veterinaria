package com.veterinaria.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción lanzada cuando se viola una regla de negocio.
 * Mapea automáticamente al código HTTP 400 BAD REQUEST.
 *
 * Ejemplos de uso:
 * - Intentar crear una cita en un horario no disponible
 * - Intentar cambiar el estado de una cita cuando no está permitido
 * - Intentar realizar una operación sobre un recurso inactivo
 *
 * @author Sistema Veterinaria
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessRuleException extends RuntimeException {

    /**
     * Constructor con mensaje
     * @param message el mensaje describiendo la regla de negocio violada
     */
    public BusinessRuleException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa
     * @param message el mensaje de error
     * @param cause la causa de la excepción
     */
    public BusinessRuleException(String message, Throwable cause) {
        super(message, cause);
    }
}
