package com.veterinaria.application.mapper;

import com.veterinaria.application.dto.inventory.MovimientoInventarioDTO;
import com.veterinaria.domain.entity.inventory.MovimientoInventario;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para convertir entre MovimientoInventario y sus DTOs.
 *
 * @author Sistema Veterinaria
 */
@Mapper(componentModel = "spring")
public interface MovimientoInventarioMapper {

    /**
     * Convierte MovimientoInventario a MovimientoInventarioDTO
     */
    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "producto.nombre", target = "productoNombre")
    @Mapping(source = "producto.codigo", target = "productoCodigo")
    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.nombreCompleto", target = "usuarioNombre")
    @Mapping(source = "tratamiento.id", target = "tratamientoId")
    @Mapping(target = "costoTotal", expression = "java(movimiento.calcularCostoTotal())")
    MovimientoInventarioDTO toDTO(MovimientoInventario movimiento);

    /**
     * Convierte lista de MovimientoInventario a lista de DTOs
     */
    List<MovimientoInventarioDTO> toDTOList(List<MovimientoInventario> movimientos);
}
