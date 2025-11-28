package com.veterinaria.domain.entity.inventory;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import com.veterinaria.domain.enums.CategoriaProducto;
import com.veterinaria.domain.enums.UnidadMedida;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * Entidad que representa un producto en el inventario.
 * Puede ser medicamento, vacuna, material médico o insumo.
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "productos", indexes = {
        @Index(name = "idx_producto_codigo", columnList = "codigo"),
        @Index(name = "idx_producto_categoria", columnList = "categoria"),
        @Index(name = "idx_producto_stock", columnList = "stock_actual")
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Producto extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    @Column(nullable = false, length = 200)
    private String nombre;

    @NotBlank(message = "El código del producto es obligatorio")
    @Size(max = 50, message = "El código no puede exceder 50 caracteres")
    @Column(nullable = false, length = 50, unique = true)
    private String codigo;

    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    @Column(length = 1000)
    private String descripcion;

    @NotNull(message = "La categoría es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CategoriaProducto categoria;

    @NotNull(message = "La unidad de medida es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_medida", nullable = false, length = 30)
    private UnidadMedida unidadMedida;

    @NotNull(message = "El stock actual es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(name = "stock_actual", nullable = false)
    private Integer stockActual;

    @NotNull(message = "El stock mínimo es obligatorio")
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    @Column(name = "stock_minimo", nullable = false)
    private Integer stockMinimo;

    @NotNull(message = "El precio de compra es obligatorio")
    @DecimalMin(value = "0.0", message = "El precio de compra no puede ser negativo")
    @Column(name = "precio_compra", nullable = false)
    private Double precioCompra;

    @NotNull(message = "El precio de venta es obligatorio")
    @DecimalMin(value = "0.0", message = "El precio de venta no puede ser negativo")
    @Column(name = "precio_venta", nullable = false)
    private Double precioVenta;

    @Size(max = 200, message = "El proveedor no puede exceder 200 caracteres")
    @Column(length = 200)
    private String proveedor;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Size(max = 100, message = "El lote no puede exceder 100 caracteres")
    @Column(length = 100)
    private String lote;

    /**
     * Verifica si el producto está por debajo del stock mínimo
     */
    public boolean necesitaReposicion() {
        return stockActual <= stockMinimo;
    }

    /**
     * Verifica si el producto está próximo a vencer (30 días)
     */
    public boolean estaPorVencer() {
        if (fechaVencimiento == null) {
            return false;
        }
        return fechaVencimiento.isBefore(LocalDate.now().plusDays(30));
    }

    /**
     * Verifica si el producto está vencido
     */
    public boolean estaVencido() {
        if (fechaVencimiento == null) {
            return false;
        }
        return fechaVencimiento.isBefore(LocalDate.now());
    }

    /**
     * Incrementa el stock del producto
     */
    public void incrementarStock(Integer cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser positiva");
        }
        this.stockActual += cantidad;
    }

    /**
     * Decrementa el stock del producto
     */
    public void decrementarStock(Integer cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser positiva");
        }
        if (this.stockActual < cantidad) {
            throw new IllegalStateException("Stock insuficiente. Disponible: " + this.stockActual);
        }
        this.stockActual -= cantidad;
    }

    /**
     * Calcula el margen de ganancia
     */
    public Double calcularMargen() {
        if (precioCompra == 0) {
            return 0.0;
        }
        return ((precioVenta - precioCompra) / precioCompra) * 100;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", codigo='" + codigo + '\'' +
                ", categoria=" + categoria +
                ", stockActual=" + stockActual +
                '}';
    }
}
