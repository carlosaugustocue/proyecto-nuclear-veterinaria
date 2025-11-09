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
                        // Rutas públicas (sin autenticación)
                        .requestMatchers("/api/auth/login", "/api/auth/recuperar-password").permitAll()
                        .requestMatchers("/api/public/**").permitAll()

                        // Documentación API (Swagger/OpenAPI)
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/api-docs/**").permitAll()
                        .requestMatchers("/swagger-resources/**", "/webjars/**").permitAll()
                        
                        // Health check de Actuator
                        .requestMatchers("/actuator/health").permitAll()
                        
                        // Endpoints de gestión de usuarios - requieren permisos específicos
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").hasAuthority("USUARIOS_CREAR")
                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").hasAuthority("USUARIOS_EDITAR")
                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasAuthority("USUARIOS_ELIMINAR")
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/**").hasAuthority("USUARIOS_VER")
                        
                        // Endpoints de pacientes
                        .requestMatchers(HttpMethod.POST, "/api/pacientes").hasAuthority("PACIENTES_CREAR")
                        .requestMatchers(HttpMethod.PUT, "/api/pacientes/**").hasAuthority("PACIENTES_EDITAR")
                        .requestMatchers(HttpMethod.DELETE, "/api/pacientes/**").hasAuthority("PACIENTES_ELIMINAR")
                        .requestMatchers(HttpMethod.GET, "/api/pacientes/**").hasAuthority("PACIENTES_VER")
                        
                        // Endpoints de clientes
                        .requestMatchers(HttpMethod.POST, "/api/clientes").hasAuthority("CLIENTES_CREAR")
                        .requestMatchers(HttpMethod.PUT, "/api/clientes/**").hasAuthority("CLIENTES_EDITAR")
                        .requestMatchers(HttpMethod.DELETE, "/api/clientes/**").hasAuthority("CLIENTES_ELIMINAR")
                        .requestMatchers(HttpMethod.GET, "/api/clientes/**").hasAuthority("CLIENTES_VER")
                        
                        // Endpoints de citas
                        .requestMatchers(HttpMethod.POST, "/api/citas").hasAuthority("CITAS_CREAR")
                        .requestMatchers(HttpMethod.PUT, "/api/citas/**").hasAnyAuthority("CITAS_EDITAR", "CITAS_REAGENDAR")
                        .requestMatchers(HttpMethod.DELETE, "/api/citas/**").hasAuthority("CITAS_CANCELAR")
                        .requestMatchers(HttpMethod.GET, "/api/citas/**").hasAuthority("CITAS_VER")
                        
                        // Endpoints de historial clínico (legacy)
                        .requestMatchers(HttpMethod.POST, "/api/historial").hasAuthority("HISTORIAL_CREAR")
                        .requestMatchers(HttpMethod.PUT, "/api/historial/**").hasAuthority("HISTORIAL_EDITAR")
                        .requestMatchers(HttpMethod.GET, "/api/historial/**").hasAuthority("HISTORIAL_VER")

                        // ==================== MÓDULO MÉDICO (V1) ====================

                        // Endpoints de Historiales Clínicos
                        .requestMatchers(HttpMethod.POST, "/api/v1/historiales-clinicos/**").hasAuthority("HISTORIAL_CREAR")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/historiales-clinicos/**").hasAuthority("HISTORIAL_EDITAR")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/historiales-clinicos/**").hasAuthority("HISTORIAL_ELIMINAR")
                        .requestMatchers(HttpMethod.GET, "/api/v1/historiales-clinicos/**").hasAuthority("HISTORIAL_VER")

                        // Endpoints de Consultas Médicas
                        .requestMatchers(HttpMethod.POST, "/api/v1/consultas").hasAuthority("CONSULTAS_CREAR")
                        .requestMatchers(HttpMethod.POST, "/api/v1/consultas/*/diagnosticos").hasAuthority("DIAGNOSTICOS_CREAR")
                        .requestMatchers(HttpMethod.POST, "/api/v1/consultas/*/tratamientos").hasAuthority("TRATAMIENTOS_CREAR")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/consultas/**").hasAuthority("CONSULTAS_EDITAR")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/consultas/*/finalizar").hasAuthority("CONSULTAS_FINALIZAR")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/consultas/**").hasAuthority("CONSULTAS_ELIMINAR")
                        .requestMatchers(HttpMethod.GET, "/api/v1/consultas/**").hasAuthority("CONSULTAS_VER")

                        // Endpoints de Diagnósticos
                        .requestMatchers(HttpMethod.POST, "/api/v1/diagnosticos").hasAuthority("DIAGNOSTICOS_CREAR")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/diagnosticos/**").hasAuthority("DIAGNOSTICOS_ELIMINAR")
                        .requestMatchers(HttpMethod.GET, "/api/v1/diagnosticos/**").hasAuthority("DIAGNOSTICOS_VER")

                        // Endpoints de Tratamientos
                        .requestMatchers(HttpMethod.POST, "/api/v1/tratamientos").hasAuthority("TRATAMIENTOS_CREAR")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/tratamientos/*/suspender").hasAuthority("TRATAMIENTOS_EDITAR")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/tratamientos/*/calcular-fecha-fin").hasAuthority("TRATAMIENTOS_EDITAR")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/tratamientos/**").hasAuthority("TRATAMIENTOS_ELIMINAR")
                        .requestMatchers(HttpMethod.GET, "/api/v1/tratamientos/**").hasAuthority("TRATAMIENTOS_VER")

                        // Endpoints de Vacunas
                        .requestMatchers(HttpMethod.POST, "/api/v1/vacunas").hasAuthority("VACUNAS_REGISTRAR")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/vacunas/*/calcular-proxima-dosis").hasAuthority("VACUNAS_EDITAR")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/vacunas/*/completar-serie").hasAuthority("VACUNAS_EDITAR")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/vacunas/**").hasAuthority("VACUNAS_ELIMINAR")
                        .requestMatchers(HttpMethod.GET, "/api/v1/vacunas/**").hasAuthority("VACUNAS_VER")

                        // Endpoints de Exámenes Médicos
                        .requestMatchers(HttpMethod.POST, "/api/v1/examenes").hasAuthority("EXAMENES_SOLICITAR")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/examenes/*/resultados").hasAuthority("EXAMENES_REGISTRAR_RESULTADOS")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/examenes/*/marcar-realizado").hasAuthority("EXAMENES_EDITAR")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/examenes/*/cancelar").hasAuthority("EXAMENES_CANCELAR")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/examenes/**").hasAuthority("EXAMENES_ELIMINAR")
                        .requestMatchers(HttpMethod.GET, "/api/v1/examenes/**").hasAuthority("EXAMENES_VER")

                        // ==================== FIN MÓDULO MÉDICO ====================

                        // Endpoints de inventario
                        .requestMatchers(HttpMethod.POST, "/api/inventario").hasAuthority("INVENTARIO_INGRESAR")
                        .requestMatchers(HttpMethod.PUT, "/api/inventario/**").hasAuthority("INVENTARIO_ASIGNAR")
                        .requestMatchers(HttpMethod.DELETE, "/api/inventario/**").hasAuthority("INVENTARIO_DAR_BAJA")
                        .requestMatchers(HttpMethod.GET, "/api/inventario/**").hasAuthority("INVENTARIO_VER")
                        
                        // Endpoints de facturación
                        .requestMatchers(HttpMethod.POST, "/api/facturas").hasAuthority("FACTURA_CREAR")
                        .requestMatchers(HttpMethod.GET, "/api/facturas/**").hasAuthority("FACTURA_VER")
                        .requestMatchers(HttpMethod.DELETE, "/api/facturas/**").hasAuthority("FACTURA_ANULAR")
                        
                        // Reportes
                        .requestMatchers("/api/reportes/ventas/**").hasAuthority("REPORTES_VENTAS")
                        .requestMatchers("/api/reportes/inventario/**").hasAuthority("REPORTES_INVENTARIO")
                        .requestMatchers("/api/reportes/citas/**").hasAuthority("REPORTES_CITAS")
                        .requestMatchers("/api/reportes/auditoria/**").hasAuthority("REPORTES_AUDITORIA")
                        
                        // Configuración del sistema - solo administradores
                        .requestMatchers("/api/config/**").hasAuthority("CONFIG_EDITAR")
                        
                        // Todas las demás peticiones requieren autenticación
                        .anyRequest().authenticated()
                )

                // Agregar filtro JWT antes del filtro de autenticación estándar
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configura CORS para permitir peticiones desde el frontend
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Orígenes permitidos (ajustar según el entorno)
        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",  // React dev server
                "http://localhost:4200",  // Angular dev server
                "http://localhost:8080"   // Mismo origen
        ));
        
        // Métodos HTTP permitidos
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        
        // Headers permitidos
        configuration.setAllowedHeaders(List.of("*"));
        
        // Permitir credenciales
        configuration.setAllowCredentials(true);
        
        // Headers expuestos
        configuration.setExposedHeaders(List.of("Authorization", "Content-Type"));
        
        // Tiempo de cache de preflight
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}
