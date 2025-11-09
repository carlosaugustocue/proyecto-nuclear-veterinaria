package com.veterinaria.domain.entity.medical;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import com.veterinaria.domain.entity.security.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * Entidad que representa una vacuna aplicada a un paciente.
 * Registra el historial de vacunación con información completa
 * sobre cada aplicación.
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "vacunas", indexes = {
        @Index(name = "idx_vacuna_historial", columnList = "historial_clinico_id"),
        @Index(name = "idx_vacuna_fecha", columnList = "fecha_aplicacion"),
        @Index(name = "idx_vacuna_tipo", columnList = "tipo_vacuna"),
        @Index(name = "idx_vacuna_proxima_dosis", columnList = "fecha_proxima_dosis")
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Vacuna extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Historial clínico al que pertenece esta vacuna
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "historial_clinico_id", nullable = false)
    private HistorialClinico historialClinico;

    /**
     * Veterinario que aplicó la vacuna
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veterinario_id", nullable = false)
    private Usuario veterinario;

    /**
     * Nombre de la vacuna
     */
    @NotBlank(message = "El nombre de la vacuna es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    /**
     * Tipo de vacuna
     * Valores: OBLIGATORIA, RECOMENDADA, OPCIONAL
     * Categorías: RABIA, PARVOVIRUS, MOQUILLO, POLIVALENTE, etc.
     */
    @NotBlank(message = "El tipo de vacuna es obligatorio")
    @Size(max = 100, message = "El tipo no puede exceder 100 caracteres")
    @Column(name = "tipo_vacuna", nullable = false, length = 100)
    private String tipoVacuna;

    /**
     * Fecha de aplicación de la vacuna
     */
    @NotNull(message = "La fecha de aplicación es obligatoria")
    @Column(name = "fecha_aplicacion", nullable = false)
    private LocalDate fechaAplicacion;

    /**
     * Fecha programada para la próxima dosis o refuerzo
     */
    @Column(name = "fecha_proxima_dosis")
    private LocalDate fechaProximaDosis;

    /**
     * Número de lote de la vacuna
     */
    @Size(max = 50, message = "El lote no puede exceder 50 caracteres")
    @Column(name = "lote", length = 50)
    private String lote;

    /**
     * Laboratorio fabricante de la vacuna
     */
    @Size(max = 100, message = "El laboratorio no puede exceder 100 caracteres")
    @Column(name = "laboratorio", length = 100)
    private String laboratorio;

    /**
     * Dosis aplicada (ej: 1ml, 2ml)
     */
    @Size(max = 50, message = "La dosis no puede exceder 50 caracteres")
    @Column(name = "dosis", length = 50)
    private String dosis;

    /**
     * Vía de administración
     * Valores: SUBCUTÁNEA, INTRAMUSCULAR, INTRANASAL, ORAL
     */
    @Size(max = 50, message = "La vía de administración no puede exceder 50 caracteres")
    @Column(name = "via_administracion", length = 50)
    private String viaAdministracion;

    /**
     * Número de dosis en la serie de vacunación (1ra, 2da, 3ra, refuerzo)
     */
    @Min(value = 1, message = "El número de dosis debe ser al menos 1")
    @Column(name = "numero_dosis")
    private Integer numeroDosis;

    /**
     * Intervalo en días hasta la próxima dosis (si aplica)
     */
    @Column(name = "intervalo_dias")
    private Integer intervaloDias;

    /**
     * Indica si es un refuerzo anual
     */
    @Column(name = "es_refuerzo")
    @Builder.Default
    private Boolean esRefuerzo = false;

    /**
     * Observaciones sobre la aplicación
     */
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    /**
     * Reacciones adversas presentadas
     */
    @Column(name = "reacciones_adversas", columnDefinition = "TEXT")
    private String reaccionesAdversas;

    /**
     * Peso del paciente al momento de la vacunación (para referencia)
     */
    @Column(name = "peso_aplicacion")
    private Double pesoAplicacion;

    /**
     * Indica si la vacuna está completada (serie completa)
     */
    @Column(name = "serie_completa")
    @Builder.Default
    private Boolean serieCompleta = false;

    // ==================== MÉTODOS DE NEGOCIO ====================

    /**
     * Calcula y establece la fecha de próxima dosis basada en el intervalo
     */
    public void calcularProximaDosis() {
        if (fechaAplicacion != null && intervaloDias != null && intervaloDias > 0) {
            this.fechaProximaDosis = fechaAplicacion.plusDays(intervaloDias);
        }
    }

    /**
     * Verifica si la vacuna es obligatoria
     * @return true si es obligatoria
     */
    public boolean esObligatoria() {
        return tipoVacuna != null && tipoVacuna.toUpperCase().contains("OBLIGATORIA");
    }

    /**
     * Verifica si requiere refuerzo próximamente
     * @param diasAnticipacion días de anticipación para considerar próximo
     * @return true si el refuerzo está próximo
     */
    public boolean requiereRefuerzoProximo(int diasAnticipacion) {
        if (fechaProximaDosis == null) {
            return false;
        }

        LocalDate fechaLimite = LocalDate.now().plusDays(diasAnticipacion);
        return !fechaProximaDosis.isAfter(fechaLimite);
    }

    /**
     * Verifica si el refuerzo está vencido
     * @return true si la fecha de próxima dosis ya pasó
     */
    public boolean refuerzoVencido() {
        return fechaProximaDosis != null && fechaProximaDosis.isBefore(LocalDate.now());
    }

    /**
     * Verifica si tuvo reacciones adversas
     * @return true si tuvo reacciones
     */
    public boolean tuvoReaccionesAdversas() {
        return reaccionesAdversas != null && !reaccionesAdversas.trim().isEmpty();
    }

    /**
     * Verifica si es una vacuna de rabia
     * @return true si es vacuna antirrábica
     */
    public boolean esAntirrábica() {
        return nombre != null && nombre.toUpperCase().contains("RABIA") ||
               tipoVacuna != null && tipoVacuna.toUpperCase().contains("RABIA");
    }

    /**
     * Obtiene el estado de la vacunación
     * @return descripción del estado
     */
    public String obtenerEstado() {
        if (serieCompleta) {
            if (fechaProximaDosis == null) {
                return "Completada - Sin refuerzo programado";
            } else if (refuerzoVencido()) {
                return "Refuerzo vencido";
            } else if (requiereRefuerzoProximo(30)) {
                return "Refuerzo próximo";
            } else {
                return "Vigente - Próximo refuerzo: " + fechaProximaDosis;
            }
        } else {
            return "Serie incompleta - Faltan dosis";
        }
    }

    /**
     * Obtiene un resumen de la vacuna
     * @return resumen
     */
    public String obtenerResumen() {
        StringBuilder resumen = new StringBuilder(nombre);
        if (numeroDosis != null) {
            resumen.append(" (Dosis ").append(numeroDosis).append(")");
        }
        resumen.append(" - ").append(fechaAplicacion);
        if (fechaProximaDosis != null) {
            resumen.append(" - Próxima: ").append(fechaProximaDosis);
        }
        return resumen.toString();
    }

    /**
     * Marca la serie de vacunación como completa
     */
    public void completarSerie() {
        this.serieCompleta = true;
    }

    @Override
    public String toString() {
        return "Vacuna{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipoVacuna + '\'' +
                ", fechaAplicacion=" + fechaAplicacion +
                ", serieCompleta=" + serieCompleta +
                '}';
    }
}
