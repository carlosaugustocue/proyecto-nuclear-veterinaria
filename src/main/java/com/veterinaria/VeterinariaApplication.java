package com.veterinaria;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Clase principal de la aplicación del Sistema de Gestión Veterinaria.
 *
 * Este sistema implementa 12 patrones de diseño:
 * 1. Factory Method - Creación de notificaciones
 * 2. State - Estados de citas y tratamientos
 * 3. Builder - Construcción de objetos complejos
 * 4. Template Method - Procesamiento de pagos
 * 5. Composite - Estructura de tratamientos compuestos
 * 6. Strategy - Estrategias de descuentos y fidelización
 * 7. Decorator - Extensión dinámica de servicios
 * 8. Observer - Sistema de notificaciones
 * 9. Adapter - Integración con sistemas externos
 * 10. Facade - Simplificación de operaciones complejas
 * 11. Singleton - Gestión de configuración
 * 12. Proxy - Control de acceso y caché
 *
 * @author Sistema Veterinaria
 * @version 1.0.0
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class VeterinariaApplication {

    public static void main(String[] args) {
        // Cargar variables de entorno desde archivo .env
        try {
            Dotenv dotenv = Dotenv.configure()
                    .ignoreIfMissing() // No fallar si el archivo .env no existe
                    .load();
            
            // Establecer las variables como propiedades del sistema
            // Spring Boot puede leer tanto variables de entorno como propiedades del sistema
            dotenv.entries().forEach(entry -> {
                String key = entry.getKey();
                String value = entry.getValue();
                // Solo establecer si no existe ya (las variables del sistema tienen prioridad)
                if (System.getProperty(key) == null && System.getenv(key) == null) {
                    System.setProperty(key, value);
                }
            });
            
            // Mapear variables específicas de email a propiedades de Spring Boot
            String emailUsername = dotenv.get("EMAIL_USERNAME", System.getProperty("EMAIL_USERNAME"));
            String emailPassword = dotenv.get("EMAIL_PASSWORD", System.getProperty("EMAIL_PASSWORD"));
            String emailFrom = dotenv.get("EMAIL_FROM", System.getProperty("EMAIL_FROM"));
            
            if (emailUsername != null && System.getProperty("spring.mail.username") == null) {
                System.setProperty("spring.mail.username", emailUsername);
            }
            if (emailPassword != null && System.getProperty("spring.mail.password") == null) {
                System.setProperty("spring.mail.password", emailPassword);
            }
            if (emailFrom != null && System.getProperty("app.email.from") == null) {
                System.setProperty("app.email.from", emailFrom);
            }
            
            System.out.println("✓ Variables de entorno cargadas desde .env");
            if (emailUsername != null) {
                System.out.println("  - EMAIL_USERNAME: " + emailUsername);
                System.out.println("  - EMAIL_FROM: " + (emailFrom != null ? emailFrom : emailUsername));
            }
        } catch (Exception e) {
            System.err.println("⚠️ Advertencia: No se pudo cargar el archivo .env: " + e.getMessage());
            System.err.println("   Las variables de entorno del sistema se usarán en su lugar.");
        }
        
        SpringApplication.run(VeterinariaApplication.class, args);
    }
}
