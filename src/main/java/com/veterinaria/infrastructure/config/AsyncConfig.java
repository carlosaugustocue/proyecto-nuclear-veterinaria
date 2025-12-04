package com.veterinaria.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Configuración para habilitar procesamiento asíncrono.
 * Permite el envío de emails en segundo plano.
 *
 * @author Sistema Veterinaria
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * Configura un ThreadPoolTaskExecutor personalizado para tareas asíncronas.
     * Esto asegura que los emails se envíen en un hilo separado sin bloquear
     * las respuestas HTTP.
     */
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("async-email-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        return executor;
    }
}
