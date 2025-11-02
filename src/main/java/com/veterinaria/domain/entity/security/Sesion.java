package com.veterinaria.domain.entity.security;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import com.veterinaria.domain.enums.EstadoSesion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad que representa una sesión de usuario en el sistema.
 * Permite rastrear sesiones activas y gestionar seguridad de múltiples sesiones.
 * Patrón State - El estado de la sesión determina su comportamiento.
 */
@Entity
@Table(name = "sesiones", indexes = {
        @Index(name = "idx_sesion_usuario", columnList = "usuario_id"),
        @Index(name = "idx_sesion_token", columnList = "token"),
        @Index(name = "idx_sesion_estado", columnList = "estado")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sesion extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El usuario es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotBlank(message = "El token es obligatorio")
    @Size(max = 500, message = "El token no puede exceder 500 caracteres")
    @Column(name = "token", nullable = false, unique = true, length = 500)
    private String token;

    @Column(name = "refresh_token", length = 500)
    private String refreshToken;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fecha de expiración es obligatoria")
    @Column(name = "fecha_expiracion", nullable = false)
    private LocalDateTime fechaExpiracion;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 30)
    @Builder.Default
    private EstadoSesion estado = EstadoSesion.ACTIVA;

    @Size(max = 45, message = "La dirección IP no puede exceder 45 caracteres")
    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Size(max = 255, message = "El user agent no puede exceder 255 caracteres")
    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "ultima_actividad")
    private LocalDateTime ultimaActividad;

    /**
     * Verifica si la sesión está activa y no ha expirado.
     *
     * @return true si la sesión es válida
     */
    public boolean isValid() {
        return this.estado == EstadoSesion.ACTIVA &&
                this.fechaExpiracion.isAfter(LocalDateTime.now());
    }

    /**
     * Marca la sesión como expirada.
     */
    public void expirar() {
        this.estado = EstadoSesion.EXPIRADA;
        this.fechaFin = LocalDateTime.now();
    }

    /**
     * Cierra la sesión manualmente.
     */
    public void cerrarManual() {
        this.estado = EstadoSesion.CERRADA_MANUAL;
        this.fechaFin = LocalDateTime.now();
    }

    /**
     * Cierra la sesión por inactividad.
     */
    public void cerrarPorInactividad() {
        this.estado = EstadoSesion.CERRADA_POR_INACTIVIDAD;
        this.fechaFin = LocalDateTime.now();
    }

    /**
     * Revoca la sesión (por ejemplo, cuando se detecta actividad sospechosa).
     */
    public void revocar() {
        this.estado = EstadoSesion.REVOCADA;
        this.fechaFin = LocalDateTime.now();
    }

    /**
     * Actualiza la actividad de la sesión.
     */
    public void actualizarActividad() {
        this.ultimaActividad = LocalDateTime.now();
    }

    /**
     * Renueva el token de la sesión.
     *
     * @param nuevoToken        Nuevo token JWT
     * @param nuevaFechaExpiracion Nueva fecha de expiración
     */
    public void renovarToken(String nuevoToken, LocalDateTime nuevaFechaExpiracion) {
        this.token = nuevoToken;
        this.fechaExpiracion = nuevaFechaExpiracion;
        this.actualizarActividad();
    }

    /**
     * Verifica si la sesión ha estado inactiva por más tiempo del permitido.
     *
     * @param minutosMaximosInactividad Minutos máximos de inactividad permitidos
     * @return true si la sesión está inactiva por más tiempo del permitido
     */
    public boolean estaInactiva(int minutosMaximosInactividad) {
        if (this.ultimaActividad == null) {
            return false;
        }
        LocalDateTime tiempoLimite = LocalDateTime.now().minusMinutes(minutosMaximosInactividad);
        return this.ultimaActividad.isBefore(tiempoLimite);
    }

    @Override
    public String toString() {
        return "Sesion{" +
                "id=" + id +
                ", usuario=" + (usuario != null ? usuario.getUsername() : "null") +
                ", estado=" + estado +
                ", fechaInicio=" + fechaInicio +
                ", fechaExpiracion=" + fechaExpiracion +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }
}
