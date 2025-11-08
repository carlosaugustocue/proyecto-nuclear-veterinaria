package com.veterinaria.domain.entity.appointments;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import com.veterinaria.domain.entity.patients.Cliente;
import com.veterinaria.domain.entity.patients.Paciente;
import com.veterinaria.domain.entity.security.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Entidad que representa una Cita médica veterinaria.
 * Implementa lógica de negocio para evitar Anemic Domain Model.
 * Usa State Pattern para gestión de estados.
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "citas", indexes = {
        @Index(name = "idx_cita_fecha", columnList = "fecha"),
        @Index(name = "idx_cita_veterinario", columnList = "veterinario_id"),
        @Index(name = "idx_cita_paciente", columnList = "paciente_id"),
        @Index(name = "idx_cita_cliente", columnList = "cliente_id"),
        @Index(name = "idx_cita_estado", columnList = "estado")
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Cita extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Fecha de la cita
     */
    @NotNull(message = "La fecha de la cita es obligatoria")
    @Column(nullable = false)
    private LocalDate fecha;

    /**
     * Hora de inicio de la cita
     */
    @NotNull(message = "La hora de la cita es obligatoria")
    @Column(nullable = false)
    private LocalTime hora;

    /**
     * Motivo de la cita
     */
    @NotBlank(message = "El motivo de la cita es obligatorio")
    @Size(min = 5, max = 500, message = "El motivo debe tener entre 5 y 500 caracteres")
    @Column(nullable = false, length = 500)
    private String motivo;

    /**
     * Estado actual de la cita (usando State Pattern)
     */
    @NotNull(message = "El estado es obligatorio")
    @Convert(converter = EstadoCitaConverter.class)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EstadoCita estado = EstadoProgramada.getInstance();

    /**
     * Veterinario asignado a la cita
     */
    @NotNull(message = "El veterinario es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veterinario_id", nullable = false)
    private Usuario veterinario;

    /**
     * Paciente que será atendido
     */
    @NotNull(message = "El paciente es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    /**
     * Cliente propietario del paciente
     */
    @NotNull(message = "El cliente es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    /**
     * Tipo de servicio a prestar
     */
    @NotNull(message = "El tipo de servicio es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_servicio_id", nullable = false)
    private TipoServicio tipoServicio;

    /**
     * Notas adicionales sobre la cita
     */
    @Size(max = 1000, message = "Las notas no pueden exceder 1000 caracteres")
    @Column(length = 1000)
    private String notas;

    /**
     * Duración estimada en minutos (se copia del TipoServicio al crear)
     */
    @Column(name = "duracion_estimada")
    private Integer duracionEstimada;

    /**
     * Motivo de cancelación (solo si estado es CANCELADA)
     */
    @Size(max = 500, message = "El motivo de cancelación no puede exceder 500 caracteres")
    @Column(name = "motivo_cancelacion", length = 500)
    private String motivoCancelacion;

    /**
     * Fecha y hora en que se confirmó la cita
     */
    @Column(name = "fecha_confirmacion")
    private LocalDateTime fechaConfirmacion;

    /**
     * Fecha y hora en que se inició la atención
     */
    @Column(name = "fecha_inicio_atencion")
    private LocalDateTime fechaInicioAtencion;

    /**
     * Fecha y hora en que finalizó la atención
     */
    @Column(name = "fecha_fin_atencion")
    private LocalDateTime fechaFinAtencion;

    // ========================================
    // LÓGICA DE NEGOCIO (evitar Anemic Domain Model)
    // ========================================

    /**
     * Cambia el estado de la cita validando las transiciones permitidas
     * @param nuevoEstado el nuevo estado
     * @throws IllegalStateException si la transición no es válida
     */
    public void cambiarEstado(EstadoCita nuevoEstado) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El nuevo estado no puede ser null");
        }

        if (!this.estado.puedeTransicionarA(nuevoEstado)) {
            throw new IllegalStateException(
                    String.format("No se puede cambiar de estado %s a %s",
                            this.estado.getNombre(), nuevoEstado.getNombre())
            );
        }

        this.estado = nuevoEstado;
        nuevoEstado.manejar(this);
    }

    /**
     * Confirma la cita (transición de PROGRAMADA a CONFIRMADA)
     * @throws IllegalStateException si no está en estado PROGRAMADA
     */
    public void confirmar() {
        cambiarEstado(EstadoConfirmada.getInstance());
        this.fechaConfirmacion = LocalDateTime.now();
    }

    /**
     * Cancela la cita con motivo obligatorio
     * @param motivo razón de la cancelación
     * @throws IllegalArgumentException si el motivo está vacío
     * @throws IllegalStateException si ya está en estado final
     */
    public void cancelar(String motivo) {
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("Debe proporcionar un motivo de cancelación");
        }

        if (this.estado.esEstadoFinal()) {
            throw new IllegalStateException("No se puede cancelar una cita en estado " + this.estado.getNombre());
        }

        this.motivoCancelacion = motivo;
        cambiarEstado(EstadoCancelada.getInstance());
    }

    /**
     * Inicia la atención (transición a EN_PROGRESO)
     * @throws IllegalStateException si no está confirmada
     */
    public void iniciar() {
        cambiarEstado(EstadoEnProgreso.getInstance());
        this.fechaInicioAtencion = LocalDateTime.now();
    }

    /**
     * Completa la cita (transición a COMPLETADA)
     * @throws IllegalStateException si no está en progreso
     */
    public void completar() {
        cambiarEstado(EstadoCompletada.getInstance());
        this.fechaFinAtencion = LocalDateTime.now();
    }

    /**
     * Reagenda la cita a una nueva fecha y hora
     * @param nuevaFecha nueva fecha
     * @param nuevaHora nueva hora
     * @throws IllegalStateException si la cita ya inició o está completada/cancelada
     */
    public void reagendar(LocalDate nuevaFecha, LocalTime nuevaHora) {
        if (this.estado instanceof EstadoEnProgreso ||
            this.estado instanceof EstadoCompletada ||
            this.estado instanceof EstadoCancelada) {
            throw new IllegalStateException(
                    "No se puede reagendar una cita en estado " + this.estado.getNombre()
            );
        }

        if (nuevaFecha == null || nuevaHora == null) {
            throw new IllegalArgumentException("La nueva fecha y hora son obligatorias");
        }

        if (nuevaFecha.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("No se puede reagendar a una fecha pasada");
        }

        this.fecha = nuevaFecha;
        this.hora = nuevaHora;
    }

    /**
     * Verifica si la cita está activa (ni completada ni cancelada)
     * @return true si está activa
     */
    public boolean estaActiva() {
        return !(estado instanceof EstadoCompletada) &&
               !(estado instanceof EstadoCancelada);
    }

    /**
     * Verifica si la cita puede ser modificada
     * @return true si puede modificarse
     */
    public boolean esModificable() {
        return (estado instanceof EstadoProgramada) ||
               (estado instanceof EstadoConfirmada);
    }

    /**
     * Obtiene el fecha-hora completo de la cita
     * @return LocalDateTime combinando fecha y hora
     */
    public LocalDateTime getFechaHoraCompleta() {
        return LocalDateTime.of(fecha, hora);
    }

    /**
     * Calcula la hora de fin estimada basada en la duración
     * @return hora de fin estimada
     */
    public LocalTime getHoraFinEstimada() {
        if (duracionEstimada == null) {
            duracionEstimada = tipoServicio.getDuracionEstimada();
        }
        return hora.plusMinutes(duracionEstimada);
    }

    /**
     * Verifica si hay solapamiento con otra cita
     * @param otraCita la cita a comparar
     * @return true si hay conflicto de horario
     */
    public boolean tieneSolapamientoCon(Cita otraCita) {
        if (!this.fecha.equals(otraCita.getFecha())) {
            return false;
        }

        LocalTime miInicio = this.hora;
        LocalTime miFin = this.getHoraFinEstimada();
        LocalTime otroInicio = otraCita.getHora();
        LocalTime otroFin = otraCita.getHoraFinEstimada();

        // Verificar solapamiento
        return !(miFin.isBefore(otroInicio) || miInicio.isAfter(otroFin) ||
                 miFin.equals(otroInicio) || miInicio.equals(otroFin));
    }

    /**
     * Obtiene información resumida de la cita
     * @return string con datos principales
     */
    public String obtenerResumen() {
        return String.format("Cita #%d - %s %s - %s - Estado: %s",
                id,
                fecha,
                hora,
                tipoServicio != null ? tipoServicio.getNombre() : "Sin servicio",
                estado.getNombre()
        );
    }

    @Override
    public String toString() {
        return "Cita{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", estado=" + (estado != null ? estado.getNombre() : "null") +
                ", paciente=" + (paciente != null ? paciente.getNombre() : "null") +
                ", veterinario=" + (veterinario != null ? veterinario.getNombreCompleto() : "null") +
                '}';
    }
}
