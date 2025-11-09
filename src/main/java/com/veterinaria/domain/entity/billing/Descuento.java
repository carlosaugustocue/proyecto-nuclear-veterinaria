package com.veterinaria.domain.entity.billing;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import com.veterinaria.domain.enums.TipoDescuento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * Entidad que representa un descuento aplicado a una factura.
 * Puede ser porcentaje o monto fijo.
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "descuentos", indexes = {
        @Index(name = "idx_descuento_factura", columnList = "factura_id"),
        @Index(name = "idx_descuento_codigo", columnList = "codigo")
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Descuento extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Factura a la que se aplica este descuento
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id", nullable = false)
    @NotNull(message = "La factura es obligatoria")
    private Factura factura;

    /**
     * Código del descuento/cupón (opcional)
     */
    @Size(max = 20, message = "El código no puede exceder 20 caracteres")
    @Column(name = "codigo", length = 20)
    private String codigo;

    /**
     * Descripción del descuento
     */
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 200, message = "La descripción no puede exceder 200 caracteres")
    @Column(name = "descripcion", nullable = false, length = 200)
    private String descripcion;

    /**
     * Tipo de descuento (PORCENTAJE o MONTO_FIJO)
     */
    @Enumerated(EnumType.STRING)
    @NotNull(message = "El tipo de descuento es obligatorio")
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoDescuento tipo;

    /**
     * Valor del descuento (porcentaje o monto según el tipo)
     */
    @NotNull(message = "El valor del descuento es obligatorio")
    @Positive(message = "El valor debe ser positivo")
    @Column(name = "valor", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Double valor;

    /**
     * Monto calculado del descuento
     */
    @NotNull(message = "El monto es obligatorio")
    @PositiveOrZero(message = "El monto debe ser mayor o igual a cero")
    @Column(name = "monto", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Double monto;

    /**
     * Motivo o razón del descuento
     */
    @Column(name = "motivo", length = 300)
    private String motivo;

    /**
     * Fecha de aplicación del descuento
     */
    @Column(name = "fecha_aplicacion")
    private LocalDate fechaAplicacion;

    /**
     * Indica si es un descuento promocional
     */
    @Column(name = "es_promocional")
    @Builder.Default
    private Boolean esPromocional = false;

    /**
     * Indica si es válido (puede ser revocado)
     */
    @Column(name = "es_valido")
    @Builder.Default
    private Boolean esValido = true;

    // ==================== MÉTODOS HELPER SIMPLES ====================

    /**
     * Verifica si es un descuento por porcentaje
     * @return true si es porcentaje
     */
    public boolean esPorcentaje() {
        return tipo == TipoDescuento.PORCENTAJE;
    }

    /**
     * Verifica si es un descuento de monto fijo
     * @return true si es monto fijo
     */
    public boolean esMontoFijo() {
        return tipo == TipoDescuento.MONTO_FIJO;
    }

    /**
     * Verifica si tiene código de cupón
     * @return true si tiene código
     */
    public boolean tieneCodigo() {
        return codigo != null && !codigo.trim().isEmpty();
    }

    /**
     * Verifica si es promocional
     * @return true si es promocional
     */
    public boolean esPromocional() {
        return Boolean.TRUE.equals(esPromocional);
    }

    /**
     * Verifica si está activo/válido
     * @return true si es válido
     */
    public boolean estaActivo() {
        return Boolean.TRUE.equals(esValido);
    }

    @Override
    public String toString() {
        return "Descuento{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", tipo=" + tipo +
                ", valor=" + valor +
                ", monto=" + monto +
                ", esValido=" + esValido +
                '}';
    }
}
