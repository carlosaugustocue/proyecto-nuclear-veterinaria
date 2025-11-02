package com.veterinaria.domain.entity.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad que registra todos los accesos e intentos de acceso al sistema.
 * Permite auditoría completa para cumplir con requerimientos de seguridad y trazabilidad.
 * Implementa logging apropiado para auditoría según las especificaciones.
 */
@Entity
@Table(name = "auditoria_acceso", indexes = {
        @Index(name = "idx_auditoria_usuario", columnList = "usuario_id"),
        @Index(name = "idx_auditoria_fecha", columnList = "fecha_hora"),
        @Index(name = "idx_auditoria_accion", columnList = "accion"),
        @Index(name = "idx_auditoria_exitoso", columnList = "exitoso")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditoriaAcceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    /**
     * Username utilizado en el intento de acceso.
     * Se guarda incluso si el usuario no existe para detectar intentos de intrusión.
     */
    @NotBlank(message = "El username es obligatorio")
    @Size(max = 100, message = "El username no puede exceder 100 caracteres")
    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @NotNull(message = "La fecha y hora son obligatorias")
    @Column(name = "fecha_hora", nullable = false)
    @Builder.Default
    private LocalDateTime fechaHora = LocalDateTime.now();

    @NotBlank(message = "La acción es obligatoria")
    @Size(max = 50, message = "La acción no puede exceder 50 caracteres")
    @Column(name = "accion", nullable = false, length = 50)
    private String accion;

    @NotNull(message = "El campo exitoso es obligatorio")
    @Column(name = "exitoso", nullable = false)
    private Boolean exitoso;

    @Size(max = 45, message = "La dirección IP no puede exceder 45 caracteres")
    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Size(max = 255, message = "El user agent no puede exceder 255 caracteres")
    @Column(name = "user_agent")
    private String userAgent;

    @Size(max = 500, message = "Los detalles no pueden exceder 500 caracteres")
    @Column(name = "detalles", length = 500)
    private String detalles;

    @Size(max = 255, message = "El mensaje de error no puede exceder 255 caracteres")
    @Column(name = "mensaje_error")
    private String mensajeError;

    /**
     * Recurso al que se intentó acceder (URL, endpoint, etc.).
     */
    @Column(name = "recurso_accedido")
    private String recursoAccedido;

    /**
     * Método HTTP utilizado (GET, POST, PUT, DELETE, etc.).
     */
    @Size(max = 10, message = "El método HTTP no puede exceder 10 caracteres")
    @Column(name = "metodo_http", length = 10)
    private String metodoHttp;

    /**
     * ID de sesión asociada si existe.
     */
    @Column(name = "sesion_id")
    private Long sesionId;

    /**
     * Indica si la acción fue considerada sospechosa.
     */
    @Column(name = "sospechoso")
    @Builder.Default
    private Boolean sospechoso = false;

    /**
     * Crea un registro de auditoría para un login exitoso.
     *
     * @param usuario   Usuario que inició sesión
     * @param ipAddress Dirección IP
     * @param userAgent User agent del navegador
     * @param sesionId  ID de la sesión creada
     * @return Registro de auditoría
     */
    public static AuditoriaAcceso loginExitoso(Usuario usuario, String ipAddress,
                                                String userAgent, Long sesionId) {
        return AuditoriaAcceso.builder()
                .usuario(usuario)
                .username(usuario.getUsername())
                .accion("LOGIN")
                .exitoso(true)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .sesionId(sesionId)
                .detalles("Login exitoso")
                .build();
    }

    /**
     * Crea un registro de auditoría para un login fallido.
     *
     * @param username      Username utilizado
     * @param ipAddress     Dirección IP
     * @param userAgent     User agent del navegador
     * @param mensajeError  Mensaje de error
     * @return Registro de auditoría
     */
    public static AuditoriaAcceso loginFallido(String username, String ipAddress,
                                                String userAgent, String mensajeError) {
        return AuditoriaAcceso.builder()
                .username(username)
                .accion("LOGIN_FALLIDO")
                .exitoso(false)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .mensajeError(mensajeError)
                .detalles("Intento de login fallido")
                .sospechoso(true)
                .build();
    }

    /**
     * Crea un registro de auditoría para logout.
     *
     * @param usuario   Usuario que cerró sesión
     * @param ipAddress Dirección IP
     * @param sesionId  ID de la sesión cerrada
     * @return Registro de auditoría
     */
    public static AuditoriaAcceso logout(Usuario usuario, String ipAddress, Long sesionId) {
        return AuditoriaAcceso.builder()
                .usuario(usuario)
                .username(usuario.getUsername())
                .accion("LOGOUT")
                .exitoso(true)
                .ipAddress(ipAddress)
                .sesionId(sesionId)
                .detalles("Logout exitoso")
                .build();
    }

    /**
     * Crea un registro de auditoría para acceso denegado.
     *
     * @param usuario          Usuario que intentó acceder
     * @param recurso          Recurso al que intentó acceder
     * @param ipAddress        Dirección IP
     * @param mensajeError     Mensaje de error
     * @return Registro de auditoría
     */
    public static AuditoriaAcceso accesoDenegado(Usuario usuario, String recurso,
                                                  String ipAddress, String mensajeError) {
        return AuditoriaAcceso.builder()
                .usuario(usuario)
                .username(usuario.getUsername())
                .accion("ACCESO_DENEGADO")
                .exitoso(false)
                .ipAddress(ipAddress)
                .recursoAccedido(recurso)
                .mensajeError(mensajeError)
                .detalles("Intento de acceso a recurso sin permisos")
                .sospechoso(true)
                .build();
    }

    @Override
    public String toString() {
        return "AuditoriaAcceso{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fechaHora=" + fechaHora +
                ", accion='" + accion + '\'' +
                ", exitoso=" + exitoso +
                ", ipAddress='" + ipAddress + '\'' +
                ", sospechoso=" + sospechoso +
                '}';
    }
}
