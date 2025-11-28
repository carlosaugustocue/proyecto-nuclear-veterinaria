package com.veterinaria.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Configuración para habilitar procesamiento asíncrono.
 * Permite el envío de emails en segundo plano.
 *
 * @author Sistema Veterinaria
 */
@Configuration
@EnableAsync
public class AsyncConfig {
    // La configuración por defecto de Spring Boot es suficiente para nuestro caso
    // Se puede personalizar el ThreadPoolTaskExecutor si es necesario
}
