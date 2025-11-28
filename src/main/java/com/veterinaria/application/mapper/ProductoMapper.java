package com.veterinaria.application.mapper;

import com.veterinaria.application.dto.inventory.CreateProductoRequest;
import com.veterinaria.application.dto.inventory.ProductoDTO;
import com.veterinaria.application.dto.inventory.UpdateProductoRequest;
import com.veterinaria.domain.entity.inventory.Producto;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para convertir entre Producto y sus DTOs.
 *
 * @author Sistema Veterinaria
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductoMapper {

    /**
     * Convierte Producto a ProductoDTO
     */
    @Mapping(target = "necesitaReposicion", expression = "java(producto.necesitaReposicion())")
    @Mapping(target = "estaPorVencer", expression = "java(producto.estaPorVencer())")
    @Mapping(target = "estaVencido", expression = "java(producto.estaVencido())")
    @Mapping(target = "margen", expression = "java(producto.calcularMargen())")
    ProductoDTO toDTO(Producto producto);

    /**
     * Convierte lista de Productos a lista de DTOs
     */
    List<ProductoDTO> toDTOList(List<Producto> productos);

    /**
     * Convierte CreateProductoRequest a Producto
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "stockActual", source = "stockInicial")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    Producto toEntity(CreateProductoRequest request);

    /**
     * Actualiza un Producto existente con datos de UpdateProductoRequest
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "codigo", ignore = true)
    @Mapping(target = "stockActual", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    void updateEntityFromDTO(UpdateProductoRequest request, @MappingTarget Producto producto);
}
