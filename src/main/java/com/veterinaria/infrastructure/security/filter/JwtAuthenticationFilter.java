package com.veterinaria.infrastructure.security.filter;

import com.veterinaria.application.service.ServicioAutenticacion;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.infrastructure.security.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Filtro de autenticación JWT.
 * Intercepta todas las peticiones HTTP y valida el token JWT si está presente.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ServicioAutenticacion servicioAutenticacion;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            // Obtener token del header Authorization
            String authHeader = request.getHeader("Authorization");
            
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            // Extraer token
            String token = jwtTokenProvider.extraerTokenDelHeader(authHeader);
            
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // Validar token y obtener usuario
            Optional<Usuario> usuarioOpt = servicioAutenticacion.validarToken(token);
            
            if (usuarioOpt.isEmpty()) {
                log.debug("Token inválido o usuario no encontrado");
                filterChain.doFilter(request, response);
                return;
            }

            Usuario usuario = usuarioOpt.get();

            // Verificar que no haya autenticación previa
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                filterChain.doFilter(request, response);
                return;
            }

            // Obtener authorities (roles y permisos)
            List<SimpleGrantedAuthority> authorities = obtenerAuthorities(token);

            // Crear authentication token
            UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(
                            usuario.getEmail(),
                            null,
                            authorities
                    );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Establecer autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authToken);

            log.debug("Usuario autenticado: {} con {} authorities", 
                     usuario.getEmail(), authorities.size());

        } catch (Exception e) {
            log.error("Error en filtro de autenticación: {}", e.getMessage(), e);
            // No lanzar excepción, solo continuar sin autenticar
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Obtiene las authorities del token (roles + permisos)
     *
     * @param token token JWT
     * @return lista de authorities
     */
    private List<SimpleGrantedAuthority> obtenerAuthorities(String token) {
        List<SimpleGrantedAuthority> authorities = new java.util.ArrayList<>();

        // Agregar roles
        List<String> roles = jwtTokenProvider.obtenerRolesDelToken(token);
        if (roles != null) {
            authorities.addAll(roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList()));
        }

        // Agregar permisos
        List<String> permisos = jwtTokenProvider.obtenerPermisosDelToken(token);
        if (permisos != null) {
            authorities.addAll(permisos.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList()));
        }

        return authorities;
    }

    /**
     * No filtrar rutas públicas
     */
    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/login") ||
               path.startsWith("/api/auth/recuperar-password") ||
               path.startsWith("/api/public/") ||
               path.startsWith("/swagger-ui/") ||
               path.startsWith("/v3/api-docs/") ||
               path.startsWith("/actuator/health");
    }
}
