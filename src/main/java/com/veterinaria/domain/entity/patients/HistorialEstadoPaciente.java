package com.veterinaria.domain.entity.patients;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import com.veterinaria.domain.enums.EstadoPaciente;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * Entidad que registra el historial de cambios de estado de un paciente.
 * Permite trazabilidad completa de los estados por los que ha pasado un paciente.
 *
 * Implementa HU-23: Registro de Mascotas Fallecidas o Inactivas
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "historial_estado_pacientes", indexes = {
    @Index(name = "idx_historial_paciente", columnList = "paciente_id"),
    @Index(name = "idx_historial_fecha", columnList = "fecha_cambio"),
    @Index(name = "idx_historial_estado", columnList = "nuevo_estado")
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HistorialEstadoPaciente extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Paciente al que pertenece este registro
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "El paciente es obligatorio")
    private Paciente paciente;

    /**
     * Estado anterior del paciente
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_anterior", length = 20)
    private EstadoPaciente estadoAnterior;

    /**
     * Nuevo estado del paciente
     */
    @NotNull(message = "El nuevo estado es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "nuevo_estado", nullable = false, length = 20)
    private EstadoPaciente nuevoEstado;

    /**
     * Motivo del cambio de estado
     */
    @NotBlank(message = "El motivo es obligatorio")
    @Size(max = 500, message = "El motivo no puede exceder 500 caracteres")
    @Column(nullable = false, length = 500)
    private String motivo;

    /**
     * Fecha en que ocurrió el cambio
     */
    @NotNull(message = "La fecha de cambio es obligatoria")
    @Column(name = "fecha_cambio", nullable = false)
    private LocalDate fechaCambio;

    @Override
    public String toString() {
        return "HistorialEstadoPaciente{" +
                "id=" + id +
                ", pacienteId=" + (paciente != null ? paciente.getId() : null) +
                ", estadoAnterior=" + estadoAnterior +
                ", nuevoEstado=" + nuevoEstado +
                ", fechaCambio=" + fechaCambio +
                '}';
    }
}
