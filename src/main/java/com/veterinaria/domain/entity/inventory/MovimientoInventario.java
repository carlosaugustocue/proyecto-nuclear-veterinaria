package com.veterinaria.domain.entity.inventory;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import com.veterinaria.domain.entity.medical.Tratamiento;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.enums.TipoMovimiento;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Entidad que representa un movimiento de inventario.
 * Registra entradas, salidas y ajustes de productos.
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "movimientos_inventario", indexes = {
        @Index(name = "idx_movimiento_producto", columnList = "producto_id"),
        @Index(name = "idx_movimiento_tipo", columnList = "tipo"),
        @Index(name = "idx_movimiento_fecha", columnList = "fecha_movimiento"),
        @Index(name = "idx_movimiento_tratamiento", columnList = "tratamiento_id")
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoInventario extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El producto es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @NotNull(message = "El tipo de movimiento es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoMovimiento tipo;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Column(nullable = false)
    private Integer cantidad;

    @NotNull(message = "La fecha del movimiento es obligatoria")
    @Column(name = "fecha_movimiento", nullable = false)
    private LocalDateTime fechaMovimiento;

    @Column(name = "costo_unitario")
    private Double costoUnitario;

    @NotBlank(message = "El motivo es obligatorio")
    @Size(max = 500, message = "El motivo no puede exceder 500 caracteres")
    @Column(nullable = false, length = 500)
    private String motivo;

    @Size(max = 1000, message = "Las observaciones no pueden exceder 1000 caracteres")
    @Column(length = 1000)
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    /**
     * Relación opcional con Tratamiento
     * Se usa cuando el movimiento es una salida por aplicación de insumo en un tratamiento
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tratamiento_id")
    private Tratamiento tratamiento;

    @Column(name = "stock_anterior")
    private Integer stockAnterior;

    @Column(name = "stock_posterior")
    private Integer stockPosterior;

    @Size(max = 100, message = "El número de documento no puede exceder 100 caracteres")
    @Column(name = "numero_documento", length = 100)
    private String numeroDocumento;

    /**
     * Calcula el costo total del movimiento
     */
    public Double calcularCostoTotal() {
        if (costoUnitario == null) {
            return 0.0;
        }
        return costoUnitario * cantidad;
    }

    /**
     * Verifica si el movimiento está asociado a un tratamiento
     */
    public boolean esMovimientoDeTratamiento() {
        return tratamiento != null;
    }

    /**
     * Verifica si es una entrada de inventario
     */
    public boolean esEntrada() {
        return tipo == TipoMovimiento.ENTRADA;
    }

    /**
     * Verifica si es una salida de inventario
     */
    public boolean esSalida() {
        return tipo == TipoMovimiento.SALIDA;
    }

    /**
     * Verifica si es un ajuste de inventario
     */
    public boolean esAjuste() {
        return tipo == TipoMovimiento.AJUSTE;
    }

    @Override
    public String toString() {
        return "MovimientoInventario{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", cantidad=" + cantidad +
                ", producto=" + (producto != null ? producto.getNombre() : "null") +
                ", fechaMovimiento=" + fechaMovimiento +
                '}';
    }
}
