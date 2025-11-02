package com.veterinaria.infrastructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Excepción base del sistema.
 * Todas las excepciones personalizadas deben heredar de esta clase.
 * Evita el antipatrón de "No manejo de excepciones".
 */
@Getter
public class VeterinariaException extends RuntimeException {

    private final HttpStatus status;
    private final String errorCode;

    public VeterinariaException(String message) {
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.errorCode = "VET_ERROR";
    }

    public VeterinariaException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.errorCode = "VET_ERROR";
    }

    public VeterinariaException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }

    public VeterinariaException(String message, Throwable cause) {
        super(message, cause);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.errorCode = "VET_ERROR";
    }

    public VeterinariaException(String message, Throwable cause, HttpStatus status, String errorCode) {
        super(message, cause);
        this.status = status;
        this.errorCode = errorCode;
    }
}
