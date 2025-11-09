package com.veterinaria.domain.entity.billing;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import com.veterinaria.domain.entity.appointments.TipoServicio;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entidad que representa un detalle (item) de una factura.
 * Cada detalle corresponde a un servicio o producto facturado.
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "detalles_factura", indexes = {
        @Index(name = "idx_detalle_factura", columnList = "factura_id")
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleFactura extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Factura a la que pertenece este detalle
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id", nullable = false)
    @NotNull(message = "La factura es obligatoria")
    private Factura factura;

    /**
     * Tipo de servicio (opcional, para servicios de la clínica)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_servicio_id")
    private TipoServicio tipoServicio;

    /**
     * Descripción del item
     */
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 300, message = "La descripción no puede exceder 300 caracteres")
    @Column(name = "descripcion", nullable = false, length = 300)
    private String descripcion;

    /**
     * Cantidad de items
     */
    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser positiva")
    @Column(name = "cantidad", nullable = false)
    @Builder.Default
    private Integer cantidad = 1;

    /**
     * Precio unitario
     */
    @NotNull(message = "El precio unitario es obligatorio")
    @Positive(message = "El precio unitario debe ser positivo")
    @Column(name = "precio_unitario", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Double precioUnitario;

    /**
     * Subtotal (cantidad * precio unitario)
     */
    @NotNull(message = "El subtotal es obligatorio")
    @Positive(message = "El subtotal debe ser positivo")
    @Column(name = "subtotal", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Double subtotal;

    /**
     * Porcentaje de descuento aplicado a este item
     */
    @Column(name = "descuento_porcentaje", columnDefinition = "DECIMAL(5,2)")
    @Builder.Default
    private Double descuentoPorcentaje = 0.0;

    /**
     * Monto de descuento aplicado
     */
    @Column(name = "descuento_monto", columnDefinition = "DECIMAL(10,2)")
    @Builder.Default
    private Double descuentoMonto = 0.0;

    /**
     * Total después de descuentos
     */
    @Column(name = "total", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Double total;

    /**
     * Observaciones del detalle
     */
    @Column(name = "observaciones", length = 500)
    private String observaciones;

    // ==================== MÉTODOS HELPER SIMPLES ====================

    /**
     * Verifica si tiene descuento aplicado
     * @return true si tiene descuento
     */
    public boolean tieneDescuento() {
        return descuentoMonto != null && descuentoMonto > 0;
    }

    /**
     * Verifica si está asociado a un servicio
     * @return true si tiene tipo de servicio
     */
    public boolean esServicio() {
        return tipoServicio != null;
    }

    /**
     * Verifica si es un producto (no tiene tipo de servicio)
     * @return true si no tiene tipo de servicio
     */
    public boolean esProducto() {
        return tipoServicio == null;
    }

    @Override
    public String toString() {
        return "DetalleFactura{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", total=" + total +
                '}';
    }
}
