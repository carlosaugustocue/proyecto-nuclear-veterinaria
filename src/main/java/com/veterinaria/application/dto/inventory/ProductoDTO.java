package com.veterinaria.application.dto.inventory;

import com.veterinaria.domain.enums.CategoriaProducto;
import com.veterinaria.domain.enums.UnidadMedida;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO para transferir información de Producto.
 *
 * @author Sistema Veterinaria
 */
@Builder
public record ProductoDTO(
        Long id,
        String nombre,
        String codigo,
        String descripcion,
        CategoriaProducto categoria,
        UnidadMedida unidadMedida,
        Integer stockActual,
        Integer stockMinimo,
        Double precioCompra,
        Double precioVenta,
        String proveedor,
        LocalDate fechaVencimiento,
        String lote,
        Boolean necesitaReposicion,
        Boolean estaPorVencer,
        Boolean estaVencido,
        Double margen,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
