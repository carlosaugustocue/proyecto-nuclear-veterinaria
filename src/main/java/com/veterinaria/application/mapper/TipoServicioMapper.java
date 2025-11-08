package com.veterinaria.application.mapper;

import com.veterinaria.application.dto.tiposervicio.CreateTipoServicioRequest;
import com.veterinaria.application.dto.tiposervicio.TipoServicioDTO;
import com.veterinaria.application.dto.tiposervicio.UpdateTipoServicioRequest;
import com.veterinaria.domain.entity.appointments.TipoServicio;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para convertir entre TipoServicio (entidad) y TipoServicioDTO.
 * Utiliza MapStruct para generación automática de código.
 *
 * @author Sistema Veterinaria
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TipoServicioMapper {

    /**
     * Convierte una entidad TipoServicio a TipoServicioDTO
     * @param tipoServicio la entidad
     * @return el DTO
     */
    @Mapping(target = "estaDisponible", expression = "java(tipoServicio.estaDisponible())")
    TipoServicioDTO toDTO(TipoServicio tipoServicio);

    /**
     * Convierte una lista de entidades a lista de DTOs
     * @param tiposServicio lista de entidades
     * @return lista de DTOs
     */
    List<TipoServicioDTO> toDTOList(List<TipoServicio> tiposServicio);

    /**
     * Convierte un CreateTipoServicioRequest a entidad TipoServicio
     * @param request el DTO de creación
     * @return la entidad
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    TipoServicio toEntity(CreateTipoServicioRequest request);

    /**
     * Actualiza una entidad TipoServicio con datos de UpdateTipoServicioRequest
     * @param request el DTO con los datos a actualizar
     * @param tipoServicio la entidad a actualizar
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    void updateEntityFromDTO(UpdateTipoServicioRequest request, @MappingTarget TipoServicio tipoServicio);
}
