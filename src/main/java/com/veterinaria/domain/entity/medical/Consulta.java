package com.veterinaria.domain.entity.medical;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import com.veterinaria.domain.entity.appointments.Cita;
import com.veterinaria.domain.entity.security.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa una consulta médica veterinaria.
 * Una consulta se genera cuando se completa una cita y contiene
 * toda la información médica de la visita.
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "consultas", indexes = {
        @Index(name = "idx_consulta_fecha", columnList = "fecha_consulta"),
        @Index(name = "idx_consulta_historial", columnList = "historial_clinico_id")
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Consulta extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Historial clínico al que pertenece esta consulta
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "historial_clinico_id", nullable = false)
    private HistorialClinico historialClinico;

    /**
     * Cita que generó esta consulta (opcional, puede ser consulta sin cita previa)
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cita_id")
    private Cita cita;

    /**
     * Veterinario que realizó la consulta
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veterinario_id", nullable = false)
    private Usuario veterinario;

    /**
     * Fecha de la consulta
     */
    @NotNull(message = "La fecha de consulta es obligatoria")
    @Column(name = "fecha_consulta", nullable = false)
    private LocalDate fechaConsulta;

    /**
     * Hora de inicio de la consulta
     */
    @Column(name = "hora_inicio")
    private LocalDateTime horaInicio;

    /**
     * Hora de fin de la consulta
     */
    @Column(name = "hora_fin")
    private LocalDateTime horaFin;

    /**
     * Motivo de la consulta
     */
    @NotBlank(message = "El motivo de consulta es obligatorio")
    @Size(max = 500, message = "El motivo no puede exceder 500 caracteres")
    @Column(name = "motivo", nullable = false, length = 500)
    private String motivo;

    /**
     * Anamnesis: Historia clínica relatada por el propietario
     */
    @Column(name = "anamnesis", columnDefinition = "TEXT")
    private String anamnesis;

    /**
     * Signos vitales tomados durante la consulta
     */
    @Embedded
    private SignosVitales signosVitales;

    /**
     * Examen físico realizado
     */
    @Column(name = "examen_fisico", columnDefinition = "TEXT")
    private String examenFisico;

    /**
     * Observaciones y notas del veterinario
     */
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    /**
     * Plan de tratamiento y recomendaciones
     */
    @Column(name = "plan_tratamiento", columnDefinition = "TEXT")
    private String planTratamiento;

    /**
     * Pronóstico del paciente
     */
    @Column(name = "pronostico", length = 100)
    private String pronostico;

    /**
     * Indica si requiere seguimiento
     */
    @Column(name = "requiere_seguimiento")
    @Builder.Default
    private Boolean requiereSeguimiento = false;

    /**
     * Fecha programada para seguimiento
     */
    @Column(name = "fecha_seguimiento")
    private LocalDate fechaSeguimiento;

    /**
     * Diagnósticos asociados a esta consulta
     */
    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Diagnostico> diagnosticos = new ArrayList<>();

    /**
     * Tratamientos prescritos en esta consulta
     */
    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Tratamiento> tratamientos = new ArrayList<>();

    // ==================== MÉTODOS DE NEGOCIO ====================

    /**
     * Agrega un diagnóstico a la consulta
     * @param diagnostico el diagnóstico a agregar
     */
    public void agregarDiagnostico(Diagnostico diagnostico) {
        diagnosticos.add(diagnostico);
        diagnostico.setConsulta(this);
    }

    /**
     * Agrega un tratamiento a la consulta
     * @param tratamiento el tratamiento a agregar
     */
    public void agregarTratamiento(Tratamiento tratamiento) {
        tratamientos.add(tratamiento);
        tratamiento.setConsulta(this);
    }

    /**
     * Finaliza la consulta estableciendo la hora de fin
     */
    public void finalizar() {
        if (this.horaFin == null) {
            this.horaFin = LocalDateTime.now();
        }
    }

    /**
     * Calcula la duración de la consulta en minutos
     * @return duración en minutos o null si no está finalizada
     */
    public Long calcularDuracionMinutos() {
        if (horaInicio == null || horaFin == null) {
            return null;
        }
        return java.time.Duration.between(horaInicio, horaFin).toMinutes();
    }

    /**
     * Verifica si la consulta está en curso
     * @return true si está en curso
     */
    public boolean estaEnCurso() {
        return horaInicio != null && horaFin == null;
    }

    /**
     * Verifica si la consulta está finalizada
     * @return true si está finalizada
     */
    public boolean estaFinalizada() {
        return horaFin != null;
    }

    /**
     * Verifica si tiene diagnósticos registrados
     * @return true si tiene al menos un diagnóstico
     */
    public boolean tieneDiagnosticos() {
        return !diagnosticos.isEmpty();
    }

    /**
     * Verifica si tiene tratamientos prescritos
     * @return true si tiene al menos un tratamiento
     */
    public boolean tieneTratamientos() {
        return !tratamientos.isEmpty();
    }

    /**
     * Obtiene un resumen de la consulta
     * @return resumen de la consulta
     */
    public String obtenerResumen() {
        return String.format("Consulta del %s - Motivo: %s - Diagnósticos: %d - Tratamientos: %d",
                fechaConsulta, motivo, diagnosticos.size(), tratamientos.size());
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "id=" + id +
                ", fechaConsulta=" + fechaConsulta +
                ", motivo='" + motivo + '\'' +
                ", veterinarioId=" + (veterinario != null ? veterinario.getId() : null) +
                '}';
    }
}
