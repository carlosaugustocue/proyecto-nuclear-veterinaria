package com.veterinaria.infrastructure.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción lanzada cuando se intenta crear un recurso que ya existe.
 */
public class DuplicateResourceException extends VeterinariaException {

    public DuplicateResourceException(String resourceName, String fieldName, Object fieldValue) {
        super(
                String.format("%s ya existe con %s : '%s'", resourceName, fieldName, fieldValue),
                HttpStatus.CONFLICT,
                "DUPLICATE_RESOURCE"
        );
    }

    public DuplicateResourceException(String message) {
        super(message, HttpStatus.CONFLICT, "DUPLICATE_RESOURCE");
    }
}
