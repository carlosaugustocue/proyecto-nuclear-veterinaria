package com.veterinaria.domain.entity.medical;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entidad que representa un diagnóstico médico asociado a una consulta.
 * Un diagnóstico describe la condición o enfermedad identificada en el paciente.
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "diagnosticos", indexes = {
        @Index(name = "idx_diagnostico_consulta", columnList = "consulta_id"),
        @Index(name = "idx_diagnostico_tipo", columnList = "tipo_diagnostico")
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Diagnostico extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Consulta a la que pertenece este diagnóstico
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id", nullable = false)
    private Consulta consulta;

    /**
     * Tipo de diagnóstico
     * Valores: PRESUNTIVO, DEFINITIVO, DIFERENCIAL
     */
    @NotBlank(message = "El tipo de diagnóstico es obligatorio")
    @Column(name = "tipo_diagnostico", nullable = false, length = 50)
    private String tipoDiagnostico;

    /**
     * Descripción del diagnóstico
     */
    @NotBlank(message = "La descripción del diagnóstico es obligatoria")
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    /**
     * Código CIE-10 veterinario (opcional)
     */
    @Column(name = "codigo_cie10", length = 10)
    private String codigoCie10;

    /**
     * Gravedad del diagnóstico
     * Valores: LEVE, MODERADO, GRAVE, CRÍTICO
     */
    @Column(name = "gravedad", length = 20)
    private String gravedad;

    /**
     * Notas adicionales sobre el diagnóstico
     */
    @Column(name = "notas", columnDefinition = "TEXT")
    private String notas;

    /**
     * Indica si es el diagnóstico principal
     */
    @Column(name = "es_principal")
    @Builder.Default
    private Boolean esPrincipal = false;

    // ==================== MÉTODOS DE NEGOCIO ====================

    /**
     * Verifica si el diagnóstico es presuntivo
     * @return true si es presuntivo
     */
    public boolean esPresuntivo() {
        return "PRESUNTIVO".equalsIgnoreCase(tipoDiagnostico);
    }

    /**
     * Verifica si el diagnóstico es definitivo
     * @return true si es definitivo
     */
    public boolean esDefinitivo() {
        return "DEFINITIVO".equalsIgnoreCase(tipoDiagnostico);
    }

    /**
     * Verifica si el diagnóstico es grave o crítico
     * @return true si es grave o crítico
     */
    public boolean esGraveOCritico() {
        return "GRAVE".equalsIgnoreCase(gravedad) || "CRÍTICO".equalsIgnoreCase(gravedad);
    }

    /**
     * Verifica si tiene código CIE-10
     * @return true si tiene código CIE-10
     */
    public boolean tieneCie10() {
        return codigoCie10 != null && !codigoCie10.trim().isEmpty();
    }

    @Override
    public String toString() {
        return "Diagnostico{" +
                "id=" + id +
                ", tipo='" + tipoDiagnostico + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", gravedad='" + gravedad + '\'' +
                '}';
    }
}
