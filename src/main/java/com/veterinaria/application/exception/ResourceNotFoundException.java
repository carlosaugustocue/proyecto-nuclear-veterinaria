package com.veterinaria.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción lanzada cuando un recurso solicitado no se encuentra.
 * Mapea automáticamente al código HTTP 404 NOT FOUND.
 *
 * @author Sistema Veterinaria
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor con mensaje
     * @param message el mensaje de error
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa
     * @param message el mensaje de error
     * @param cause la causa de la excepción
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor para recurso no encontrado por ID
     * @param resourceName nombre del recurso
     * @param fieldName nombre del campo
     * @param fieldValue valor del campo
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s no encontrado con %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
