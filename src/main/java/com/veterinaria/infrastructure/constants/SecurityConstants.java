package com.veterinaria.infrastructure.constants;

/**
 * Constantes de seguridad para el sistema.
 * Evita el antipatrón de Magic Numbers usando constantes con nombres descriptivos.
 */
public final class SecurityConstants {

    private SecurityConstants() {
        throw new UnsupportedOperationException("Esta es una clase de constantes y no debe ser instanciada");
    }

    // JWT
    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_PREFIX = "Bearer ";
    public static final String JWT_AUTHORITIES_KEY = "authorities";
    public static final String JWT_USER_ID_KEY = "userId";
    public static final String JWT_USERNAME_KEY = "username";

    // Endpoints públicos
    public static final String[] PUBLIC_ENDPOINTS = {
            "/auth/**",
            "/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/actuator/health"
    };

    // Roles
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_VETERINARIO = "ROLE_VETERINARIO";
    public static final String ROLE_ASISTENTE = "ROLE_ASISTENTE";
    public static final String ROLE_RECEPCIONISTA = "ROLE_RECEPCIONISTA";
    public static final String ROLE_CLIENTE = "ROLE_CLIENTE";

    // Permisos
    public static final String PERMISSION_READ = "READ";
    public static final String PERMISSION_WRITE = "WRITE";
    public static final String PERMISSION_DELETE = "DELETE";
    public static final String PERMISSION_UPDATE = "UPDATE";

    // Sesión
    public static final int MAX_ACTIVE_SESSIONS = 3;
    public static final int SESSION_TIMEOUT_MINUTES = 30;
}
