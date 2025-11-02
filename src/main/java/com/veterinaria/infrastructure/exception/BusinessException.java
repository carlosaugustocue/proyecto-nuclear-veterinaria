package com.veterinaria.infrastructure.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción para errores de lógica de negocio.
 */
public class BusinessException extends VeterinariaException {

    public BusinessException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "BUSINESS_ERROR");
    }

    public BusinessException(String message, String errorCode) {
        super(message, HttpStatus.BAD_REQUEST, errorCode);
    }
}
