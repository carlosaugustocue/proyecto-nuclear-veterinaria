package com.veterinaria.infrastructure.constants;

/**
 * Constantes generales de la aplicación.
 * Centraliza valores constantes para evitar hard coding.
 */
public final class AppConstants {

    private AppConstants() {
        throw new UnsupportedOperationException("Esta es una clase de constantes y no debe ser instanciada");
    }

    // Paginación
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "20";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "ASC";
    public static final int MAX_PAGE_SIZE = 100;

    // Formatos de fecha
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT = "HH:mm";

    // Validaciones
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 100;
    public static final int MIN_USERNAME_LENGTH = 3;
    public static final int MAX_USERNAME_LENGTH = 50;
    public static final int MAX_NAME_LENGTH = 100;
    public static final int MAX_EMAIL_LENGTH = 100;
    public static final int MAX_PHONE_LENGTH = 20;
    public static final int MAX_ADDRESS_LENGTH = 255;
    public static final int MAX_DESCRIPTION_LENGTH = 500;
    public static final int MAX_NOTES_LENGTH = 2000;

    // Regex patterns
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String PHONE_PATTERN = "^[+]?[0-9]{7,15}$";
    public static final String DNI_PATTERN = "^[0-9]{8}$";

    // Mensajes
    public static final String RESOURCE_NOT_FOUND = "Recurso no encontrado";
    public static final String OPERATION_SUCCESS = "Operación realizada exitosamente";
    public static final String OPERATION_FAILED = "La operación ha fallado";

    // Cache
    public static final String CACHE_USUARIOS = "usuarios";
    public static final String CACHE_PACIENTES = "pacientes";
    public static final String CACHE_PRODUCTOS = "productos";
    public static final String CACHE_CITAS = "citas";

    // Stock
    public static final int STOCK_MINIMO_DEFAULT = 10;
    public static final int STOCK_CRITICO = 5;
}
