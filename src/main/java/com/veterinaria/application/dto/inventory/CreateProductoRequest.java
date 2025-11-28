package com.veterinaria.application.dto.inventory;

import com.veterinaria.domain.enums.CategoriaProducto;
import com.veterinaria.domain.enums.UnidadMedida;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

/**
 * DTO para crear un nuevo producto.
 *
 * @author Sistema Veterinaria
 */
@Builder
public record CreateProductoRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
        String nombre,

        @NotBlank(message = "El código es obligatorio")
        @Size(max = 50, message = "El código no puede exceder 50 caracteres")
        String codigo,

        @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
        String descripcion,

        @NotNull(message = "La categoría es obligatoria")
        CategoriaProducto categoria,

        @NotNull(message = "La unidad de medida es obligatoria")
        UnidadMedida unidadMedida,

        @NotNull(message = "El stock inicial es obligatorio")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stockInicial,

        @NotNull(message = "El stock mínimo es obligatorio")
        @Min(value = 0, message = "El stock mínimo no puede ser negativo")
        Integer stockMinimo,

        @NotNull(message = "El precio de compra es obligatorio")
        @DecimalMin(value = "0.0", message = "El precio de compra no puede ser negativo")
        Double precioCompra,

        @NotNull(message = "El precio de venta es obligatorio")
        @DecimalMin(value = "0.0", message = "El precio de venta no puede ser negativo")
        Double precioVenta,

        @Size(max = 200, message = "El proveedor no puede exceder 200 caracteres")
        String proveedor,

        LocalDate fechaVencimiento,

        @Size(max = 100, message = "El lote no puede exceder 100 caracteres")
        String lote
) {}
