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
        // Configurar propiedades de base de datos antes de que Spring Boot las procese
        configureDatabaseProperties();
        
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
            
            // Reconfigurar propiedades de base de datos después de cargar .env
            configureDatabaseProperties();
            
            // Mapear variables específicas de email a propiedades de Spring Boot
            // Primero verificar variables de entorno del sistema (Railway las proporciona directamente)
            System.out.println("=== Diagnóstico de Email ===");
            String emailHost = System.getenv("EMAIL_HOST");
            System.out.println("EMAIL_HOST (System.getenv): " + (emailHost != null ? emailHost : "NO ENCONTRADO"));
            if (emailHost == null || emailHost.trim().isEmpty()) {
                emailHost = dotenv.get("EMAIL_HOST");
                System.out.println("EMAIL_HOST (dotenv): " + (emailHost != null ? emailHost : "NO ENCONTRADO"));
            }
            if (emailHost == null || emailHost.trim().isEmpty()) {
                emailHost = System.getProperty("EMAIL_HOST");
                System.out.println("EMAIL_HOST (System.getProperty): " + (emailHost != null ? emailHost : "NO ENCONTRADO"));
            }
            
            String emailUsername = System.getenv("EMAIL_USERNAME");
            if (emailUsername == null || emailUsername.trim().isEmpty()) {
                emailUsername = dotenv.get("EMAIL_USERNAME", System.getProperty("EMAIL_USERNAME"));
            }
            
            String emailPassword = System.getenv("EMAIL_PASSWORD");
            if (emailPassword == null || emailPassword.trim().isEmpty()) {
                emailPassword = dotenv.get("EMAIL_PASSWORD", System.getProperty("EMAIL_PASSWORD"));
            }
            
            String emailFrom = System.getenv("EMAIL_FROM");
            if (emailFrom == null || emailFrom.trim().isEmpty()) {
                emailFrom = dotenv.get("EMAIL_FROM", System.getProperty("EMAIL_FROM"));
            }
            
            // Mapear EMAIL_HOST a spring.mail.host si está configurado
            if (emailHost != null && !emailHost.trim().isEmpty() && !"disabled".equals(emailHost)) {
                System.setProperty("spring.mail.host", emailHost);
                System.out.println("✓ EMAIL_HOST configurado: " + emailHost);
                System.out.println("✓ spring.mail.host establecido como propiedad del sistema");
            } else {
                System.out.println("⚠️ EMAIL_HOST NO está configurado o está deshabilitado");
                System.out.println("⚠️ El servicio de email NO estará disponible");
            }
            
            if (emailUsername != null && !emailUsername.trim().isEmpty() && System.getProperty("spring.mail.username") == null) {
                System.setProperty("spring.mail.username", emailUsername);
            }
            if (emailPassword != null && !emailPassword.trim().isEmpty() && System.getProperty("spring.mail.password") == null) {
                System.setProperty("spring.mail.password", emailPassword);
            }
            if (emailFrom != null && !emailFrom.trim().isEmpty() && System.getProperty("app.email.from") == null) {
                System.setProperty("app.email.from", emailFrom);
            }
            
            System.out.println("✓ Variables de entorno cargadas desde .env");
            if (emailHost != null && !emailHost.trim().isEmpty()) {
                System.out.println("  - EMAIL_HOST: " + emailHost);
            }
            if (emailUsername != null && !emailUsername.trim().isEmpty()) {
                System.out.println("  - EMAIL_USERNAME: " + emailUsername);
                System.out.println("  - EMAIL_FROM: " + (emailFrom != null && !emailFrom.trim().isEmpty() ? emailFrom : emailUsername));
            }
        } catch (Exception e) {
            System.err.println("⚠️ Advertencia: No se pudo cargar el archivo .env: " + e.getMessage());
            System.err.println("   Las variables de entorno del sistema se usarán en su lugar.");
        }
        
        SpringApplication.run(VeterinariaApplication.class, args);
    }
    
    /**
     * Configura las propiedades de base de datos resolviendo correctamente las variables de entorno.
     * Soporta variables de Railway (MYSQL_*) y variables estándar (DB_*).
     * Railway resuelve las referencias ${{MySQL.*}} antes de que la aplicación las vea.
     */
    private static void configureDatabaseProperties() {
        // Obtener variables de entorno (tienen prioridad sobre propiedades del sistema)
        // Railway puede usar tanto MYSQL_URL como referencias resueltas
        String mysqlUrl = getEnvOrProperty("MYSQL_URL");
        String dbUrl = getEnvOrProperty("DB_URL");
        
        // Railway puede usar MYSQLHOST o MYSQL_HOST (con guion bajo)
        String mysqlHost = getEnvOrProperty("MYSQLHOST");
        if (mysqlHost == null || mysqlHost.isEmpty() || mysqlHost.startsWith("${")) {
            mysqlHost = getEnvOrProperty("MYSQL_HOST");
        }
        String dbHost = getEnvOrProperty("DB_HOST");
        
        // Railway puede usar MYSQLPORT o MYSQL_PORT
        String mysqlPort = getEnvOrProperty("MYSQLPORT", "3306");
        if (mysqlPort == null || mysqlPort.isEmpty() || mysqlPort.startsWith("${")) {
            mysqlPort = getEnvOrProperty("MYSQL_PORT", "3306");
        }
        String dbPort = getEnvOrProperty("DB_PORT", "3306");
        
        // Railway puede usar MYSQLDATABASE o MYSQL_DATABASE
        String mysqlDatabase = getEnvOrProperty("MYSQLDATABASE");
        if (mysqlDatabase == null || mysqlDatabase.isEmpty() || mysqlDatabase.startsWith("${")) {
            mysqlDatabase = getEnvOrProperty("MYSQL_DATABASE");
        }
        String dbName = getEnvOrProperty("DB_NAME");
        
        // Railway puede usar MYSQLUSER o MYSQL_USER
        String mysqlUser = getEnvOrProperty("MYSQLUSER");
        if (mysqlUser == null || mysqlUser.isEmpty() || mysqlUser.startsWith("${")) {
            mysqlUser = getEnvOrProperty("MYSQL_USER");
        }
        String dbUsername = getEnvOrProperty("DB_USERNAME");
        
        // Railway puede usar MYSQLPASSWORD, MYSQL_PASSWORD o MYSQL_ROOT_PASSWORD
        String mysqlPassword = getEnvOrProperty("MYSQLPASSWORD");
        if (mysqlPassword == null || mysqlPassword.isEmpty() || mysqlPassword.startsWith("${")) {
            mysqlPassword = getEnvOrProperty("MYSQL_PASSWORD");
        }
        if (mysqlPassword == null || mysqlPassword.isEmpty() || mysqlPassword.startsWith("${")) {
            mysqlPassword = getEnvOrProperty("MYSQL_ROOT_PASSWORD");
        }
        String dbPassword = getEnvOrProperty("DB_PASSWORD");
        
        // Construir URL de conexión con prioridad: MYSQL_URL > DB_URL > variables individuales
        String jdbcUrl = null;
        
        if (mysqlUrl != null && !mysqlUrl.isEmpty() && !mysqlUrl.startsWith("${")) {
            // Railway puede proporcionar URLs con formato mysql:// en lugar de jdbc:mysql://
            jdbcUrl = normalizeJdbcUrl(mysqlUrl);
            System.out.println("✓ Usando MYSQL_URL para la conexión a la base de datos");
            System.out.println("  - URL original: " + maskPassword(mysqlUrl));
            System.out.println("  - URL normalizada: " + maskPassword(jdbcUrl));
            // Extraer hostname para diagnóstico
            try {
                String hostname = extractHostname(jdbcUrl);
                System.out.println("  - Hostname detectado: " + hostname);
                System.out.println("  ⚠️  Si la conexión falla, verifica que:");
                System.out.println("     1. El servicio MySQL esté iniciado en Railway");
                System.out.println("     2. El servicio backend esté vinculado al servicio MySQL");
                System.out.println("     3. El hostname '" + hostname + "' sea correcto");
            } catch (Exception e) {
                // Ignorar errores al extraer hostname
            }
        } else if (dbUrl != null && !dbUrl.isEmpty() && !dbUrl.startsWith("${")) {
            // Normalizar también DB_URL por si acaso
            jdbcUrl = normalizeJdbcUrl(dbUrl);
            System.out.println("✓ Usando DB_URL para la conexión a la base de datos");
            System.out.println("  - URL: " + maskPassword(jdbcUrl));
        } else {
            // Construir desde variables individuales
            String host = (mysqlHost != null && !mysqlHost.isEmpty() && !mysqlHost.startsWith("${")) 
                         ? mysqlHost 
                         : ((dbHost != null && !dbHost.isEmpty() && !dbHost.startsWith("${")) ? dbHost : "localhost");
            String port = (mysqlPort != null && !mysqlPort.isEmpty() && !mysqlPort.startsWith("${")) 
                         ? mysqlPort 
                         : ((dbPort != null && !dbPort.isEmpty() && !dbPort.startsWith("${")) ? dbPort : "3306");
            String database = (mysqlDatabase != null && !mysqlDatabase.isEmpty() && !mysqlDatabase.startsWith("${")) 
                             ? mysqlDatabase 
                             : ((dbName != null && !dbName.isEmpty() && !dbName.startsWith("${")) ? dbName : "railway");
            
            jdbcUrl = String.format(
                "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true&connectTimeout=60000&socketTimeout=60000&autoReconnect=true&failOverReadOnly=false&maxReconnects=3",
                host, port, database
            );
            System.out.println("✓ Construyendo URL de base de datos desde variables individuales");
            System.out.println("  - Host: " + host);
            System.out.println("  - Port: " + port);
            System.out.println("  - Database: " + database);
        }
        
        // Configurar usuario y contraseña
        String username = (mysqlUser != null && !mysqlUser.isEmpty() && !mysqlUser.startsWith("${")) 
                         ? mysqlUser 
                         : ((dbUsername != null && !dbUsername.isEmpty() && !dbUsername.startsWith("${")) ? dbUsername : "root");
        String password = (mysqlPassword != null && !mysqlPassword.isEmpty() && !mysqlPassword.startsWith("${")) 
                         ? mysqlPassword 
                         : (dbPassword != null && !dbPassword.isEmpty() && !dbPassword.startsWith("${") ? dbPassword : "");
        
        System.out.println("✓ Configuración de base de datos:");
        System.out.println("  - Usuario: " + username);
        System.out.println("  - Password: " + (password.isEmpty() ? "(vacío)" : "***"));
        
        // Establecer propiedades del sistema para que Spring Boot las use
        if (jdbcUrl != null && System.getProperty("spring.datasource.url") == null) {
            System.setProperty("spring.datasource.url", jdbcUrl);
        }
        if (System.getProperty("spring.datasource.username") == null) {
            System.setProperty("spring.datasource.username", username);
        }
        if (System.getProperty("spring.datasource.password") == null) {
            System.setProperty("spring.datasource.password", password);
        }
    }
    
    /**
     * Obtiene una variable de entorno o propiedad del sistema.
     * Las variables de entorno tienen prioridad.
     */
    private static String getEnvOrProperty(String key) {
        String value = System.getenv(key);
        if (value == null || value.isEmpty()) {
            value = System.getProperty(key);
        }
        return value;
    }
    
    /**
     * Obtiene una variable de entorno o propiedad del sistema con un valor por defecto.
     */
    private static String getEnvOrProperty(String key, String defaultValue) {
        String value = getEnvOrProperty(key);
        return (value != null && !value.isEmpty()) ? value : defaultValue;
    }
    
    /**
     * Normaliza una URL de MySQL para asegurar que tenga el prefijo jdbc: requerido por el driver JDBC.
     * Railway puede proporcionar URLs con formato mysql:// en lugar de jdbc:mysql://
     * También agrega parámetros de conexión adicionales para manejar mejor los timeouts en Railway.
     */
    private static String normalizeJdbcUrl(String url) {
        if (url == null || url.isEmpty()) {
            return url;
        }
        
        // Si la URL comienza con mysql:// pero no con jdbc:mysql://, agregar el prefijo jdbc:
        if (url.startsWith("mysql://") && !url.startsWith("jdbc:mysql://")) {
            url = "jdbc:" + url;
        }
        
        // Agregar parámetros de conexión adicionales si no están presentes
        // Estos parámetros ayudan a manejar mejor los timeouts y reconexiones en Railway
        if (url.contains("?")) {
            // La URL ya tiene parámetros, verificar si tiene los que necesitamos
            if (!url.contains("connectTimeout")) {
                url += "&connectTimeout=60000";
            }
            if (!url.contains("socketTimeout")) {
                url += "&socketTimeout=60000";
            }
            if (!url.contains("autoReconnect")) {
                url += "&autoReconnect=true";
            }
            if (!url.contains("maxReconnects")) {
                url += "&maxReconnects=3";
            }
        } else {
            // La URL no tiene parámetros, agregarlos
            url += "?connectTimeout=60000&socketTimeout=60000&autoReconnect=true&maxReconnects=3";
        }
        
        return url;
    }
    
    /**
     * Oculta la contraseña en una URL de conexión para logging seguro.
     */
    private static String maskPassword(String url) {
        if (url == null) return null;
        // Ocultar contraseña en la URL si está presente
        return url.replaceAll("password=[^&;]*", "password=***")
                  .replaceAll(":([^:@/]+)@", ":***@");
    }
    
    /**
     * Extrae el hostname de una URL JDBC para diagnóstico.
     * Maneja URLs con formato: jdbc:mysql://[user:password@]hostname:port/database
     */
    private static String extractHostname(String jdbcUrl) {
        if (jdbcUrl == null || jdbcUrl.isEmpty()) {
            return "unknown";
        }
        try {
            // Buscar el inicio después de "://"
            int start = jdbcUrl.indexOf("://");
            if (start == -1) return "unknown";
            start += 3;
            
            // Si hay credenciales (usuario:password@), buscar después del @
            int atIndex = jdbcUrl.indexOf("@", start);
            if (atIndex != -1) {
                start = atIndex + 1;
            }
            
            // Buscar el final del hostname (puede ser :puerto o /database o ?params)
            int end = jdbcUrl.indexOf(":", start);
            if (end == -1) {
                end = jdbcUrl.indexOf("/", start);
                if (end == -1) {
                    end = jdbcUrl.indexOf("?", start);
                }
            }
            
            if (end == -1) {
                return jdbcUrl.substring(start);
            }
            return jdbcUrl.substring(start, end);
        } catch (Exception e) {
            return "unknown";
        }
    }
}
