package com.veterinaria.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Configuración manual de email para producción.
 * Solo se configura si EMAIL_HOST está disponible.
 */
@Configuration
@Profile("prod")
public class MailConfig {

    @Value("${spring.mail.host:}")
    private String mailHost;

    @Value("${spring.mail.port:587}")
    private int mailPort;

    @Value("${spring.mail.username:}")
    private String mailUsername;

    @Value("${spring.mail.password:}")
    private String mailPassword;

    @Bean
    @ConditionalOnProperty(name = "EMAIL_HOST", matchIfMissing = false)
    public JavaMailSender javaMailSender() {
        // Verificar que EMAIL_HOST esté configurado
        String emailHost = System.getProperty("EMAIL_HOST", System.getenv("EMAIL_HOST"));
        if ((emailHost == null || emailHost.trim().isEmpty()) && 
            (mailHost == null || mailHost.trim().isEmpty() || "disabled".equals(mailHost))) {
            return null;
        }
        
        // Usar el valor de la variable de entorno si está disponible, sino el de la propiedad
        String host = (emailHost != null && !emailHost.trim().isEmpty()) ? emailHost : mailHost;

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(mailPort);
        
        // Obtener username y password de variables de entorno si están disponibles
        String username = System.getProperty("EMAIL_USERNAME", System.getenv("EMAIL_USERNAME"));
        String password = System.getProperty("EMAIL_PASSWORD", System.getenv("EMAIL_PASSWORD"));
        mailSender.setUsername(username != null ? username : mailUsername);
        mailSender.setPassword(password != null ? password : mailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }
}

