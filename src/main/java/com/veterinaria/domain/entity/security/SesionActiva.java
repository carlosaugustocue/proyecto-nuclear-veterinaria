package com.veterinaria.domain.entity.security;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad que representa una sesión activa de un usuario.
 * Utilizado para gestionar sesiones concurrentes y validación de tokens.
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "sesiones_activas", indexes = {
    @Index(name = "idx_usuario_activa", columnList = "usuario_id,activa")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SesionActiva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Token JWT de la sesión
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String token;

    /**
     * Usuario propietario de la sesión
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /**
     * Dirección IP desde donde se inició la sesión
     */
    @Column(length = 45)
    private String direccionIP;

    /**
     * Fecha y hora de inicio de la sesión
     */
    @Column(nullable = false)
    private LocalDateTime inicioSesion;

    /**
     * Fecha y hora de la última actividad
     */
    @Column(nullable = false)
    private LocalDateTime ultimaActividad;

    /**
     * Indica si la sesión está activa
     */
    @Column(nullable = false)
    @Builder.Default
    private boolean activa = true;

    /**
     * Renueva la actividad de la sesión
     */
    public void renovar() {
        this.ultimaActividad = LocalDateTime.now();
    }

    /**
     * Cierra la sesión
     */
    public void cerrar() {
        this.activa = false;
    }

    /**
     * Verifica si la sesión ha expirado (más de 24 horas sin actividad)
     */
    public boolean esExpirada() {
        return ultimaActividad != null &&
               ultimaActividad.plusHours(24).isBefore(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "SesionActiva{" +
                "id=" + id +
                ", usuario=" + (usuario != null ? usuario.getEmail() : "N/A") +
                ", activa=" + activa +
                ", inicioSesion=" + inicioSesion +
                '}';
    }
}
