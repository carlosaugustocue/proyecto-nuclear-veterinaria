package com.veterinaria.application.mapper;

import com.veterinaria.application.dto.billing.CreateDetalleFacturaRequest;
import com.veterinaria.application.dto.billing.DetalleFacturaDTO;
import com.veterinaria.domain.entity.billing.DetalleFactura;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para convertir entre DetalleFactura (entidad) y DTOs.
 * Utiliza MapStruct para generación automática de código.
 *
 * @author Sistema Veterinaria
 */
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DetalleFacturaMapper {

    /**
     * Convierte una entidad DetalleFactura a DetalleFacturaDTO
     * @param detalle la entidad
     * @return el DTO
     */
    @Mapping(source = "factura.id", target = "facturaId")
    @Mapping(source = "tipoServicio.id", target = "tipoServicioId")
    @Mapping(source = "tipoServicio.nombre", target = "tipoServicioNombre")
    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "producto.nombre", target = "productoNombre")
    DetalleFacturaDTO toDTO(DetalleFactura detalle);

    /**
     * Convierte una lista de entidades a lista de DTOs
     * @param detalles lista de entidades
     * @return lista de DTOs
     */
    List<DetalleFacturaDTO> toDTOList(List<DetalleFactura> detalles);

    /**
     * Convierte CreateDetalleFacturaRequest a entidad DetalleFactura
     * @param request el DTO de creación
     * @return la entidad
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "factura", ignore = true)
    @Mapping(target = "tipoServicio", ignore = true)
    @Mapping(target = "producto", ignore = true) // Se establece manualmente en el servicio
    @Mapping(target = "subtotal", ignore = true) // Se calcula en el servicio
    @Mapping(target = "descuentoMonto", constant = "0.0")
    @Mapping(target = "total", ignore = true) // Se calcula en el servicio
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    DetalleFactura toEntity(CreateDetalleFacturaRequest request);
}
