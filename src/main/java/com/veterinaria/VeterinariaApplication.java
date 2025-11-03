package com.veterinaria;

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
        SpringApplication.run(VeterinariaApplication.class, args);
    }
}
