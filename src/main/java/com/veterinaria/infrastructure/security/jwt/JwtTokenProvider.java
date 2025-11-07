package com.veterinaria.infrastructure.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Proveedor de tokens JWT.
 * Genera, valida y extrae información de tokens JWT.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret:VeterinariaSecretKeyDefaultShouldBeChangedInProductionEnvironment2024!@#$%}")
    private String jwtSecret;

    @Value("${jwt.expiration:86400000}") // 24 horas por defecto
    private long jwtExpiration;

    private SecretKey key;

    /**
     * Inicializa la clave de firma JWT
     */
    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(
            java.util.Base64.getEncoder().encodeToString(jwtSecret.getBytes())
        );
        this.key = Keys.hmacShaKeyFor(keyBytes);
        log.info("JwtTokenProvider inicializado correctamente");
    }

    /**
     * Genera un token JWT para un usuario
     *
     * @param email email del usuario
     * @param roles roles del usuario
     * @param permisos permisos del usuario
     * @return token JWT
     */
    public String generarToken(String email, List<String> roles, List<String> permisos) {
        Date ahora = new Date();
        Date fechaExpiracion = new Date(ahora.getTime() + jwtExpiration);

        return Jwts.builder()
                .subject(email)
                .claim("roles", roles)
                .claim("permisos", permisos)
                .issuedAt(ahora)
                .expiration(fechaExpiracion)
                .signWith(key, Jwts.SIG.HS512)
                .compact();
    }

    /**
     * Genera un token con claims personalizados
     *
     * @param email email del usuario
     * @param claims claims adicionales
     * @return token JWT
     */
    public String generarTokenConClaims(String email, Map<String, Object> claims) {
        Date ahora = new Date();
        Date fechaExpiracion = new Date(ahora.getTime() + jwtExpiration);

        return Jwts.builder()
                .subject(email)
                .claims(claims)
                .issuedAt(ahora)
                .expiration(fechaExpiracion)
                .signWith(key, Jwts.SIG.HS512)
                .compact();
    }

    /**
     * Extrae el email del token
     *
     * @param token token JWT
     * @return email del usuario
     */
    public String obtenerEmailDelToken(String token) {
        Claims claims = extraerClaims(token);
        return claims.getSubject();
    }

    /**
     * Extrae los roles del token
     *
     * @param token token JWT
     * @return lista de roles
     */
    @SuppressWarnings("unchecked")
    public List<String> obtenerRolesDelToken(String token) {
        Claims claims = extraerClaims(token);
        return (List<String>) claims.get("roles");
    }

    /**
     * Extrae los permisos del token
     *
     * @param token token JWT
     * @return lista de permisos
     */
    @SuppressWarnings("unchecked")
    public List<String> obtenerPermisosDelToken(String token) {
        Claims claims = extraerClaims(token);
        return (List<String>) claims.get("permisos");
    }

    /**
     * Valida un token JWT
     *
     * @param token token a validar
     * @return true si es válido
     */
    public boolean validarToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Token JWT malformado: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Token JWT expirado: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Token JWT no soportado: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string está vacío: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error validando token JWT: {}", e.getMessage());
        }
        return false;
    }

    /**
     * Verifica si un token está expirado
     *
     * @param token token a verificar
     * @return true si está expirado
     */
    public boolean esTokenExpirado(String token) {
        try {
            Claims claims = extraerClaims(token);
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            log.error("Error verificando expiración del token: {}", e.getMessage());
            return true;
        }
    }

    /**
     * Obtiene la fecha de expiración del token
     *
     * @param token token JWT
     * @return fecha de expiración
     */
    public Date obtenerFechaExpiracion(String token) {
        Claims claims = extraerClaims(token);
        return claims.getExpiration();
    }

    /**
     * Obtiene el tiempo de expiración configurado en milisegundos
     *
     * @return tiempo de expiración
     */
    public long obtenerTiempoExpiracion() {
        return jwtExpiration;
    }

    /**
     * Extrae todos los claims del token
     *
     * @param token token JWT
     * @return claims del token
     */
    private Claims extraerClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Extrae el token del header Authorization
     *
     * @param authorizationHeader header Authorization
     * @return token sin el prefijo Bearer
     */
    public String extraerTokenDelHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
