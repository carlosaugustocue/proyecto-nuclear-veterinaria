package com.veterinaria.domain.entity.medical;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import com.veterinaria.domain.entity.security.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad que representa un examen o estudio médico realizado a un paciente.
 * Incluye exámenes de laboratorio, imagenología, y otros estudios diagnósticos.
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "examenes", indexes = {
        @Index(name = "idx_examen_historial", columnList = "historial_clinico_id"),
        @Index(name = "idx_examen_tipo", columnList = "tipo_examen"),
        @Index(name = "idx_examen_estado", columnList = "estado"),
        @Index(name = "idx_examen_fecha", columnList = "fecha_realizacion")
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Examen extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Historial clínico al que pertenece este examen
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "historial_clinico_id", nullable = false)
    private HistorialClinico historialClinico;

    /**
     * Veterinario que solicitó el examen
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veterinario_solicitante_id", nullable = false)
    private Usuario veterinarioSolicitante;

    /**
     * Consulta que originó la solicitud del examen (opcional)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id")
    private Consulta consulta;

    /**
     * Tipo de examen
     * Valores: SANGRE, ORINA, HECES, RAYOS_X, ULTRASONIDO, ENDOSCOPIA,
     *          BIOPSIA, CITOLOGÍA, ELECTROCARDIOGRAMA, etc.
     */
    @NotBlank(message = "El tipo de examen es obligatorio")
    @Size(max = 100, message = "El tipo no puede exceder 100 caracteres")
    @Column(name = "tipo_examen", nullable = false, length = 100)
    private String tipoExamen;

    /**
     * Nombre específico del examen
     */
    @NotBlank(message = "El nombre del examen es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    /**
     * Descripción o motivo del examen
     */
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    /**
     * Fecha de solicitud del examen
     */
    @NotNull(message = "La fecha de solicitud es obligatoria")
    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDate fechaSolicitud;

    /**
     * Fecha de realización del examen
     */
    @Column(name = "fecha_realizacion")
    private LocalDate fechaRealizacion;

    /**
     * Fecha de recepción de resultados
     */
    @Column(name = "fecha_resultados")
    private LocalDateTime fechaResultados;

    /**
     * Estado del examen
     * Valores: SOLICITADO, PROGRAMADO, EN_PROCESO, COMPLETADO, CANCELADO
     */
    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 50, message = "El estado no puede exceder 50 caracteres")
    @Column(name = "estado", nullable = false, length = 50)
    @Builder.Default
    private String estado = "SOLICITADO";

    /**
     * Resultados del examen (texto libre o valores)
     */
    @Column(name = "resultados", columnDefinition = "TEXT")
    private String resultados;

    /**
     * Valores de referencia para el examen
     */
    @Column(name = "valores_referencia", columnDefinition = "TEXT")
    private String valoresReferencia;

    /**
     * Interpretación médica de los resultados
     */
    @Column(name = "interpretacion", columnDefinition = "TEXT")
    private String interpretacion;

    /**
     * Hallazgos importantes o anormales
     */
    @Column(name = "hallazgos", columnDefinition = "TEXT")
    private String hallazgos;

    /**
     * Ruta del archivo del examen (PDF, imagen, etc.)
     */
    @Size(max = 500, message = "La ruta del archivo no puede exceder 500 caracteres")
    @Column(name = "archivo_ruta", length = 500)
    private String archivoRuta;

    /**
     * Tipo MIME del archivo
     */
    @Size(max = 100, message = "El tipo MIME no puede exceder 100 caracteres")
    @Column(name = "archivo_tipo", length = 100)
    private String archivoTipo;

    /**
     * Laboratorio externo (si aplica)
     */
    @Size(max = 200, message = "El laboratorio no puede exceder 200 caracteres")
    @Column(name = "laboratorio_externo", length = 200)
    private String laboratorioExterno;

    /**
     * Número de referencia del laboratorio externo
     */
    @Size(max = 100, message = "La referencia no puede exceder 100 caracteres")
    @Column(name = "referencia_externa", length = 100)
    private String referenciaExterna;

    /**
     * Costo del examen
     */
    @Column(name = "costo")
    private Double costo;

    /**
     * Notas adicionales
     */
    @Column(name = "notas", columnDefinition = "TEXT")
    private String notas;

    /**
     * Indica si requiere ayuno
     */
    @Column(name = "requiere_ayuno")
    @Builder.Default
    private Boolean requiereAyuno = false;

    /**
     * Indica si los resultados son urgentes
     */
    @Column(name = "es_urgente")
    @Builder.Default
    private Boolean esUrgente = false;

    /**
     * Indica si los resultados son anormales
     */
    @Column(name = "resultado_anormal")
    @Builder.Default
    private Boolean resultadoAnormal = false;

    // ==================== MÉTODOS DE NEGOCIO ====================

    /**
     * Marca el examen como realizado
     */
    public void marcarRealizado() {
        this.estado = "COMPLETADO";
        this.fechaRealizacion = LocalDate.now();
    }

    /**
     * Registra los resultados del examen
     * @param resultados los resultados a registrar
     */
    public void registrarResultados(String resultados) {
        this.resultados = resultados;
        this.fechaResultados = LocalDateTime.now();
        if (!"COMPLETADO".equals(this.estado)) {
            this.estado = "COMPLETADO";
        }
    }

    /**
     * Cancela el examen
     * @param motivo motivo de la cancelación
     */
    public void cancelar(String motivo) {
        this.estado = "CANCELADO";
        if (this.notas == null) {
            this.notas = "Cancelado: " + motivo;
        } else {
            this.notas += "\nCancelado: " + motivo;
        }
    }

    /**
     * Verifica si el examen está pendiente
     * @return true si está pendiente
     */
    public boolean estaPendiente() {
        return "SOLICITADO".equals(estado) || "PROGRAMADO".equals(estado) || "EN_PROCESO".equals(estado);
    }

    /**
     * Verifica si el examen está completado
     * @return true si está completado
     */
    public boolean estaCompletado() {
        return "COMPLETADO".equals(estado) && resultados != null;
    }

    /**
     * Verifica si es un examen de laboratorio
     * @return true si es de laboratorio
     */
    public boolean esLaboratorio() {
        return tipoExamen != null &&
               (tipoExamen.contains("SANGRE") ||
                tipoExamen.contains("ORINA") ||
                tipoExamen.contains("HECES") ||
                tipoExamen.contains("BIOPSIA") ||
                tipoExamen.contains("CITOLOGÍA"));
    }

    /**
     * Verifica si es un examen de imagenología
     * @return true si es de imagenología
     */
    public boolean esImagenologia() {
        return tipoExamen != null &&
               (tipoExamen.contains("RAYOS_X") ||
                tipoExamen.contains("ULTRASONIDO") ||
                tipoExamen.contains("TOMOGRAFÍA") ||
                tipoExamen.contains("RESONANCIA") ||
                tipoExamen.contains("ENDOSCOPIA"));
    }

    /**
     * Verifica si tiene archivo adjunto
     * @return true si tiene archivo
     */
    public boolean tieneArchivo() {
        return archivoRuta != null && !archivoRuta.trim().isEmpty();
    }

    /**
     * Verifica si es externo (laboratorio externo)
     * @return true si es externo
     */
    public boolean esExterno() {
        return laboratorioExterno != null && !laboratorioExterno.trim().isEmpty();
    }

    /**
     * Calcula los días transcurridos desde la solicitud
     * @return días transcurridos
     */
    public long diasDesdeSolicitud() {
        return java.time.temporal.ChronoUnit.DAYS.between(fechaSolicitud, LocalDate.now());
    }

    /**
     * Verifica si el examen está demorado (más de X días sin resultados)
     * @param diasLimite días límite considerados normales
     * @return true si está demorado
     */
    public boolean estaDemorado(int diasLimite) {
        return estaPendiente() && diasDesdeSolicitud() > diasLimite;
    }

    /**
     * Obtiene el estado descriptivo del examen
     * @return descripción del estado
     */
    public String obtenerEstadoDescriptivo() {
        switch (estado) {
            case "SOLICITADO":
                return "Solicitado - Pendiente de programación";
            case "PROGRAMADO":
                return "Programado para " + (fechaRealizacion != null ? fechaRealizacion : "definir fecha");
            case "EN_PROCESO":
                return "En proceso de análisis";
            case "COMPLETADO":
                return estaCompletado() ? "Completado con resultados" : "Completado sin resultados";
            case "CANCELADO":
                return "Cancelado";
            default:
                return estado;
        }
    }

    /**
     * Obtiene un resumen del examen
     * @return resumen
     */
    public String obtenerResumen() {
        StringBuilder resumen = new StringBuilder(nombre);
        resumen.append(" (").append(tipoExamen).append(")");
        resumen.append(" - Estado: ").append(estado);
        if (fechaRealizacion != null) {
            resumen.append(" - Realizado: ").append(fechaRealizacion);
        }
        if (resultadoAnormal) {
            resumen.append(" - ANORMAL");
        }
        return resumen.toString();
    }

    @Override
    public String toString() {
        return "Examen{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipoExamen + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaSolicitud=" + fechaSolicitud +
                '}';
    }
}
