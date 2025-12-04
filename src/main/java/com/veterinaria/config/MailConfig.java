package com.veterinaria.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Configuración manual de email para producción.
 * Solo se configura si EMAIL_HOST está disponible y no está deshabilitado.
 */
@Slf4j
@Configuration
@Profile("prod")
public class MailConfig {

    @Value("${spring.mail.host:disabled}")
    private String mailHost;
    
    // Constructor para logging de diagnóstico
    public MailConfig() {
        String emailHostEnv = System.getenv("EMAIL_HOST");
        String emailHostProp = System.getProperty("EMAIL_HOST");
        log.info("=== MailConfig - Diagnóstico ===");
        log.info("EMAIL_HOST (env): {}", emailHostEnv != null ? emailHostEnv : "NO CONFIGURADO");
        log.info("EMAIL_HOST (property): {}", emailHostProp != null ? emailHostProp : "NO CONFIGURADO");
    }

    @Value("${spring.mail.port:587}")
    private int mailPort;

    @Value("${spring.mail.username:}")
    private String mailUsername;

    @Value("${spring.mail.password:}")
    private String mailPassword;

    /**
     * Crea el bean JavaMailSender solo si EMAIL_HOST está configurado y no está deshabilitado.
     * La condición verifica directamente la variable de entorno EMAIL_HOST.
     */
    @Bean
    @ConditionalOnExpression(
        "T(java.lang.System).getenv('EMAIL_HOST') != null && " +
        "!T(java.lang.System).getenv('EMAIL_HOST').trim().isEmpty() && " +
        "!T(java.lang.System).getenv('EMAIL_HOST').equals('disabled')"
    )
    public JavaMailSender javaMailSender() {
        log.info("=== Creando bean JavaMailSender ===");
        // Verificar que EMAIL_HOST esté configurado (prioridad: variable de entorno > propiedad del sistema > propiedad de Spring)
        String emailHost = System.getenv("EMAIL_HOST");
        if (emailHost == null || emailHost.trim().isEmpty()) {
            emailHost = System.getProperty("EMAIL_HOST");
        }
        if (emailHost == null || emailHost.trim().isEmpty()) {
            emailHost = mailHost;
        }
        
        // Si el host está deshabilitado o vacío, lanzar excepción (no debería llegar aquí si la condición funciona)
        if (emailHost == null || emailHost.trim().isEmpty() || "disabled".equals(emailHost)) {
            log.error("❌ ERROR: EMAIL_HOST está configurado pero tiene un valor inválido: {}", emailHost);
            throw new IllegalStateException("EMAIL_HOST debe estar configurado con un host SMTP válido");
        }
        
        log.info("✓ Configurando JavaMailSender con host: {}", emailHost);
        
        // Usar el valor de la variable de entorno si está disponible, sino el de la propiedad
        String host = emailHost;

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(mailPort);
        
        // Obtener username y password de variables de entorno si están disponibles
        String username = System.getenv("EMAIL_USERNAME");
        if (username == null || username.trim().isEmpty()) {
            username = System.getProperty("EMAIL_USERNAME");
        }
        if (username == null || username.trim().isEmpty()) {
            username = mailUsername;
        }
        
        String password = System.getenv("EMAIL_PASSWORD");
        if (password == null || password.trim().isEmpty()) {
            password = System.getProperty("EMAIL_PASSWORD");
        }
        if (password == null || password.trim().isEmpty()) {
            password = mailPassword;
        }
        
        mailSender.setUsername(username);
        mailSender.setPassword(password != null ? password : "");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.debug", "false");

        log.info("✓ JavaMailSender configurado - Host: {}, Port: {}, Username: {}", 
                host, mailPort, username != null && !username.isEmpty() ? username : "NO CONFIGURADO");

        return mailSender;
    }
}

