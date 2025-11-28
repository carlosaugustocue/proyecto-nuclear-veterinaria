package com.veterinaria.application.dto.inventory;

import com.veterinaria.domain.enums.CategoriaProducto;
import com.veterinaria.domain.enums.UnidadMedida;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

/**
 * DTO para actualizar un producto existente.
 *
 * @author Sistema Veterinaria
 */
@Builder
public record UpdateProductoRequest(
        @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
        String nombre,

        @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
        String descripcion,

        CategoriaProducto categoria,

        UnidadMedida unidadMedida,

        @Min(value = 0, message = "El stock mínimo no puede ser negativo")
        Integer stockMinimo,

        @DecimalMin(value = "0.0", message = "El precio de compra no puede ser negativo")
        Double precioCompra,

        @DecimalMin(value = "0.0", message = "El precio de venta no puede ser negativo")
        Double precioVenta,

        @Size(max = 200, message = "El proveedor no puede exceder 200 caracteres")
        String proveedor,

        LocalDate fechaVencimiento,

        @Size(max = 100, message = "El lote no puede exceder 100 caracteres")
        String lote
) {}
