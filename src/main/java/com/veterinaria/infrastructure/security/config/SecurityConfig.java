package com.veterinaria.infrastructure.security.config;

import com.veterinaria.infrastructure.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuración de seguridad de Spring Security.
 * Configura JWT, CORS, y las reglas de autorización.
 *
 * @author Sistema Veterinaria
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Bean de PasswordEncoder usando BCrypt
     * Usado para encriptar y validar contraseñas
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    /**
     * Configura la cadena de filtros de seguridad
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitar CSRF ya que usamos tokens JWT
                .csrf(AbstractHttpConfigurer::disable)

                // Configurar CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Política de sesiones stateless (sin sesiones del servidor)
                .sessionManagement(session -> 
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Configurar autorización de peticiones
                .authorizeHttpRequests(auth -> auth
                        // ==================== RUTAS PÚBLICAS ====================

                        // Autenticación y recuperación de contraseña
                        .requestMatchers("/api/auth/login", "/api/auth/recuperar-password").permitAll()
                        .requestMatchers("/api/public/**").permitAll()

                        // Confirmación pública de citas por token
                        .requestMatchers("/api/v1/citas/confirmar-por-token").permitAll()

                        // TEST: Endpoint temporal para probar emails (ELIMINAR EN PRODUCCIÓN)
                        .requestMatchers("/api/test/**").permitAll()

                        // Documentación API (Swagger/OpenAPI)
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/api-docs/**").permitAll()
                        .requestMatchers("/swagger-resources/**", "/webjars/**").permitAll()

                        // Health check de Actuator
                        .requestMatchers("/actuator/health").permitAll()

                        // ==================== MÓDULO ADMINISTRACIÓN ====================

                        // Gestión de usuarios - Solo ADMIN (ya tiene @PreAuthorize en controller)
                        .requestMatchers("/api/usuarios/**").authenticated()

                        // Configuración del sistema - Solo ADMIN
                        .requestMatchers("/api/config/**").hasRole("ADMIN")

                        // ==================== MÓDULO MÉDICO ====================

                        // Historiales Clínicos - ADMIN y VETERINARIO
                        .requestMatchers("/api/v1/historiales-clinicos/**").hasAnyRole("ADMIN", "VETERINARIO")
                        .requestMatchers("/api/historial/**").hasAnyRole("ADMIN", "VETERINARIO")

                        // Consultas Médicas - ADMIN y VETERINARIO
                        .requestMatchers("/api/v1/consultas/**").hasAnyRole("ADMIN", "VETERINARIO")

                        // Diagnósticos - ADMIN y VETERINARIO
                        .requestMatchers("/api/v1/diagnosticos/**").hasAnyRole("ADMIN", "VETERINARIO")

                        // Tratamientos - ADMIN y VETERINARIO
                        .requestMatchers("/api/v1/tratamientos/**").hasAnyRole("ADMIN", "VETERINARIO")

                        // Vacunas - ADMIN y VETERINARIO
                        .requestMatchers("/api/v1/vacunas/**").hasAnyRole("ADMIN", "VETERINARIO")

                        // Exámenes Médicos - ADMIN y VETERINARIO
                        .requestMatchers("/api/v1/examenes/**").hasAnyRole("ADMIN", "VETERINARIO")

                        // ==================== MÓDULO PACIENTES ====================

                        // Pacientes - ADMIN, VETERINARIO y RECEPCIONISTA
                        .requestMatchers("/api/pacientes/**").hasAnyRole("ADMIN", "VETERINARIO", "RECEPCIONISTA")

                        // ==================== MÓDULO CLIENTES ====================

                        // Clientes - ADMIN, VETERINARIO y RECEPCIONISTA
                        .requestMatchers("/api/clientes/**").hasAnyRole("ADMIN", "VETERINARIO", "RECEPCIONISTA")

                        // ==================== MÓDULO CITAS ====================

                        // Citas V1 - ADMIN, VETERINARIO y RECEPCIONISTA
                        .requestMatchers("/api/v1/citas/**").hasAnyRole("ADMIN", "VETERINARIO", "RECEPCIONISTA")

                        // Citas legacy - ADMIN, VETERINARIO y RECEPCIONISTA
                        .requestMatchers("/api/citas/**").hasAnyRole("ADMIN", "VETERINARIO", "RECEPCIONISTA")

                        // ==================== MÓDULO FACTURACIÓN ====================

                        // Facturas V1 - ADMIN y RECEPCIONISTA
                        .requestMatchers("/api/v1/facturas/**").hasAnyRole("ADMIN", "RECEPCIONISTA")

                        // Facturas legacy - ADMIN y RECEPCIONISTA
                        .requestMatchers("/api/facturas/**").hasAnyRole("ADMIN", "RECEPCIONISTA")

                        // ==================== MÓDULO INVENTARIO ====================

                        // Productos - ADMIN y VETERINARIO (pueden ver)
                        .requestMatchers(HttpMethod.GET, "/api/v1/productos/**").hasAnyRole("ADMIN", "VETERINARIO", "RECEPCIONISTA")
                        .requestMatchers("/api/v1/productos/**").hasRole("ADMIN")

                        // Movimientos de Inventario - ADMIN
                        .requestMatchers("/api/v1/movimientos-inventario/**").hasRole("ADMIN")

                        // Inventario legacy - ADMIN
                        .requestMatchers("/api/inventario/**").hasRole("ADMIN")

                        // ==================== REPORTES ====================

                        // Reportes - ADMIN y VETERINARIO
                        .requestMatchers("/api/reportes/**").hasAnyRole("ADMIN", "VETERINARIO")

                        // ==================== FALLBACK ====================

                        // Todas las demás peticiones requieren autenticación
                        .anyRequest().authenticated()
                )

                // Agregar filtro JWT antes del filtro de autenticación estándar
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configura CORS para permitir peticiones desde el frontend
     * Nota: setAllowedOriginPatterns permite wildcards pero requiere setAllowCredentials(false)
     * Para desarrollo con ngrok, usamos patrones sin credenciales (JWT no requiere cookies)
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Orígenes permitidos usando patrones (permite wildcards)
        // Esto permite localhost en cualquier puerto y dominios de ngrok
        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:*",           // Localhost en cualquier puerto
                "https://*.ngrok-free.app",     // Dominios de ngrok (free)
                "https://*.ngrok.io",           // Dominios de ngrok (paid)
                "https://*.ngrok.app"           // Dominios de ngrok (alternativo)
        ));
        
        // Métodos HTTP permitidos
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        
        // Headers permitidos
        configuration.setAllowedHeaders(List.of("*"));
        
        // No permitir credenciales cuando usamos patrones con wildcards
        // Esto es compatible con JWT ya que no usamos cookies
        configuration.setAllowCredentials(false);
        
        // Headers expuestos
        configuration.setExposedHeaders(List.of("Authorization", "Content-Type"));
        
        // Tiempo de cache de preflight
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}
