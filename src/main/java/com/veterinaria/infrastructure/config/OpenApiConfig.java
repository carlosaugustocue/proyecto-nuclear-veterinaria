package com.veterinaria.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de OpenAPI/Swagger para la documentación de la API.
 * Incluye configuración de JWT Bearer token.
 *
 * @author Sistema Veterinaria
 */
@Configuration
public class OpenApiConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "Bearer Authentication";

        return new OpenAPI()
                .info(new Info()
                        .title(applicationName + " - API REST")
                        .version("1.0.0")
                        .description("""
                                API REST para Sistema de Gestión Veterinaria.

                                Incluye módulos de:
                                - Autenticación y Usuarios
                                - Gestión de Clientes
                                - Gestión de Pacientes (Mascotas)
                                - Gestión de Citas
                                - Historial Clínico Médico
                                - Facturación y Pagos

                                ### Autenticación
                                La API usa JWT (JSON Web Tokens) para autenticación.

                                1. Obtén un token haciendo login en `/api/auth/login`
                                2. Usa el token en el header Authorization: `Bearer {token}`
                                3. Usa el botón "Authorize" arriba para configurar el token
                                """)
                        .contact(new Contact()
                                .name("Sistema Veterinaria")
                                .email("soporte@veterinaria.com")
                                .url("https://veterinaria.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desarrollo"),
                        new Server()
                                .url("https://api.veterinaria.com")
                                .description("Servidor de Producción")))
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Ingresa el token JWT obtenido del endpoint de login")));
    }
}
