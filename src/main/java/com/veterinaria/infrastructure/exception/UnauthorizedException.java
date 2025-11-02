package com.veterinaria.infrastructure.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción para errores de autenticación y autorización.
 */
public class UnauthorizedException extends VeterinariaException {

    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED");
    }
}
