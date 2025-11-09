package com.veterinaria.domain.entity.medical;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * Entidad que representa un tratamiento prescrito en una consulta.
 * Puede ser un medicamento, procedimiento, terapia, etc.
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "tratamientos", indexes = {
        @Index(name = "idx_tratamiento_consulta", columnList = "consulta_id"),
        @Index(name = "idx_tratamiento_tipo", columnList = "tipo_tratamiento")
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Tratamiento extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Consulta a la que pertenece este tratamiento
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id", nullable = false)
    private Consulta consulta;

    /**
     * Tipo de tratamiento
     * Valores: MEDICAMENTO, PROCEDIMIENTO, TERAPIA, DIETA, CIRUGÍA
     */
    @NotBlank(message = "El tipo de tratamiento es obligatorio")
    @Column(name = "tipo_tratamiento", nullable = false, length = 50)
    private String tipoTratamiento;

    /**
     * Nombre del tratamiento o medicamento
     */
    @NotBlank(message = "El nombre del tratamiento es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    /**
     * Descripción detallada del tratamiento
     */
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    /**
     * Dosis prescrita (para medicamentos)
     */
    @Size(max = 100, message = "La dosis no puede exceder 100 caracteres")
    @Column(name = "dosis", length = 100)
    private String dosis;

    /**
     * Frecuencia de administración (ej: cada 8 horas, 2 veces al día)
     */
    @Size(max = 100, message = "La frecuencia no puede exceder 100 caracteres")
    @Column(name = "frecuencia", length = 100)
    private String frecuencia;

    /**
     * Vía de administración
     * Valores: ORAL, INTRAVENOSA, INTRAMUSCULAR, SUBCUTÁNEA, TÓPICA, etc.
     */
    @Size(max = 50, message = "La vía de administración no puede exceder 50 caracteres")
    @Column(name = "via_administracion", length = 50)
    private String viaAdministracion;

    /**
     * Duración del tratamiento en días
     */
    @Min(value = 1, message = "La duración debe ser al menos 1 día")
    @Column(name = "duracion_dias")
    private Integer duracionDias;

    /**
     * Fecha de inicio del tratamiento
     */
    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    /**
     * Fecha de fin del tratamiento (calculada o manual)
     */
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    /**
     * Indicaciones especiales para el tratamiento
     */
    @Column(name = "indicaciones", columnDefinition = "TEXT")
    private String indicaciones;

    /**
     * Efectos secundarios posibles
     */
    @Column(name = "efectos_secundarios", columnDefinition = "TEXT")
    private String efectosSecundarios;

    /**
     * Cantidad total prescrita
     */
    @DecimalMin(value = "0.0", message = "La cantidad debe ser positiva")
    @Column(name = "cantidad")
    private Double cantidad;

    /**
     * Unidad de medida (tabletas, ml, mg, etc.)
     */
    @Size(max = 20, message = "La unidad no puede exceder 20 caracteres")
    @Column(name = "unidad", length = 20)
    private String unidad;

    /**
     * Indica si el tratamiento está activo
     */
    @Column(name = "activo")
    @Builder.Default
    private Boolean activo = true;

    /**
     * Indica si requiere supervisión especial
     */
    @Column(name = "requiere_supervision")
    @Builder.Default
    private Boolean requiereSupervision = false;

    // ==================== MÉTODOS DE NEGOCIO ====================

    /**
     * Calcula y establece la fecha de fin basada en la duración
     */
    public void calcularFechaFin() {
        if (fechaInicio != null && duracionDias != null) {
            this.fechaFin = fechaInicio.plusDays(duracionDias);
        }
    }

    /**
     * Verifica si el tratamiento es un medicamento
     * @return true si es medicamento
     */
    public boolean esMedicamento() {
        return "MEDICAMENTO".equalsIgnoreCase(tipoTratamiento);
    }

    /**
     * Verifica si el tratamiento ha finalizado
     * @return true si ha finalizado
     */
    public boolean haFinalizado() {
        return fechaFin != null && LocalDate.now().isAfter(fechaFin);
    }

    /**
     * Verifica si el tratamiento está vigente
     * @return true si está vigente
     */
    public boolean estaVigente() {
        LocalDate hoy = LocalDate.now();
        return activo &&
               (fechaInicio.isBefore(hoy) || fechaInicio.isEqual(hoy)) &&
               (fechaFin == null || fechaFin.isAfter(hoy) || fechaFin.isEqual(hoy));
    }

    /**
     * Suspende el tratamiento
     */
    public void suspender() {
        this.activo = false;
        if (this.fechaFin == null || this.fechaFin.isAfter(LocalDate.now())) {
            this.fechaFin = LocalDate.now();
        }
    }

    /**
     * Obtiene un resumen del tratamiento
     * @return resumen del tratamiento
     */
    public String obtenerResumen() {
        StringBuilder resumen = new StringBuilder(nombre);
        if (dosis != null && !dosis.isEmpty()) {
            resumen.append(" - ").append(dosis);
        }
        if (frecuencia != null && !frecuencia.isEmpty()) {
            resumen.append(" - ").append(frecuencia);
        }
        if (duracionDias != null) {
            resumen.append(" - ").append(duracionDias).append(" días");
        }
        return resumen.toString();
    }

    @Override
    public String toString() {
        return "Tratamiento{" +
                "id=" + id +
                ", tipo='" + tipoTratamiento + '\'' +
                ", nombre='" + nombre + '\'' +
                ", dosis='" + dosis + '\'' +
                ", activo=" + activo +
                '}';
    }
}
