package com.veterinaria.application.mapper;

import com.veterinaria.application.dto.billing.CreateDescuentoRequest;
import com.veterinaria.application.dto.billing.DescuentoDTO;
import com.veterinaria.domain.entity.billing.Descuento;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para convertir entre Descuento (entidad) y DTOs.
 * Utiliza MapStruct para generación automática de código.
 *
 * @author Sistema Veterinaria
 */
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DescuentoMapper {

    /**
     * Convierte una entidad Descuento a DescuentoDTO
     * @param descuento la entidad
     * @return el DTO
     */
    @Mapping(source = "factura.id", target = "facturaId")
    DescuentoDTO toDTO(Descuento descuento);

    /**
     * Convierte una lista de entidades a lista de DTOs
     * @param descuentos lista de entidades
     * @return lista de DTOs
     */
    List<DescuentoDTO> toDTOList(List<Descuento> descuentos);

    /**
     * Convierte CreateDescuentoRequest a entidad Descuento
     * @param request el DTO de creación
     * @return la entidad
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "factura", ignore = true)
    @Mapping(target = "monto", ignore = true) // Se calcula en el servicio
    @Mapping(target = "fechaAplicacion", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "esValido", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    Descuento toEntity(CreateDescuentoRequest request);
}
