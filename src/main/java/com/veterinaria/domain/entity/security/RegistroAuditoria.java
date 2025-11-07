package com.veterinaria.domain.entity.security;

import com.veterinaria.domain.enums.ResultadoAccion;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad que registra todas las acciones realizadas en el sistema.
 * Utilizado para auditoría y trazabilidad.
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "auditoria_accesos", indexes = {
    @Index(name = "idx_usuario", columnList = "usuario_id"),
    @Index(name = "idx_fecha_hora", columnList = "fecha_hora"),
    @Index(name = "idx_resultado", columnList = "resultado")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Usuario que realizó la acción (puede ser null en intentos fallidos)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    /**
     * Fecha y hora de la acción
     */
    @Column(nullable = false)
    private LocalDateTime fechaHora;

    /**
     * Descripción de la acción realizada
     */
    @Column(nullable = false, length = 100)
    private String accion;

    /**
     * Módulo del sistema donde se realizó la acción
     */
    @Column(length = 50)
    private String modulo;

    /**
     * Recurso específico afectado (ej: ID de paciente, cita, etc)
     */
    @Column(length = 100)
    private String recurso;

    /**
     * Resultado de la acción
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ResultadoAccion resultado;

    /**
     * Dirección IP desde donde se realizó la acción
     */
    @Column(length = 45)
    private String direccionIP;

    /**
     * Detalles adicionales de la acción
     */
    @Column(length = 500)
    private String detalles;

    /**
     * Duración de la operación en milisegundos
     */
    private Long duracionMs;

    /**
     * Verifica si el acceso fue no autorizado
     */
    public boolean esAccesoNoAutorizado() {
        return resultado == ResultadoAccion.DENEGADO_SIN_PERMISO ||
               resultado == ResultadoAccion.DENEGADO_SIN_AUTENTICACION ||
               resultado == ResultadoAccion.DENEGADO_BLOQUEADO;
    }

    /**
     * Verifica si es una operación crítica que requiere revisión
     */
    public boolean esOperacionCritica() {
        return accion != null && (
            accion.contains("DELETE") ||
            accion.contains("ANULAR") ||
            accion.contains("ELIMINAR") ||
            accion.contains("BAJA")
        );
    }

    @Override
    public String toString() {
        return "RegistroAuditoria{" +
                "id=" + id +
                ", usuario=" + (usuario != null ? usuario.getEmail() : "N/A") +
                ", accion='" + accion + '\'' +
                ", resultado=" + resultado +
                ", fechaHora=" + fechaHora +
                '}';
    }
}
