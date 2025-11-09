package com.veterinaria.application.mapper;

import com.veterinaria.application.dto.medical.CreateTratamientoRequest;
import com.veterinaria.application.dto.medical.TratamientoDTO;
import com.veterinaria.domain.entity.medical.Tratamiento;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para convertir entre Tratamiento (entidad) y DTOs.
 * Utiliza MapStruct para generación automática de código.
 *
 * @author Sistema Veterinaria
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TratamientoMapper {

    /**
     * Convierte una entidad Tratamiento a TratamientoDTO
     * @param tratamiento la entidad
     * @return el DTO
     */
    @Mapping(source = "consulta.id", target = "consultaId")
    @Mapping(target = "esMedicamento", expression = "java(tratamiento.esMedicamento())")
    @Mapping(target = "haFinalizado", expression = "java(tratamiento.haFinalizado())")
    @Mapping(target = "estaVigente", expression = "java(tratamiento.estaVigente())")
    @Mapping(target = "resumen", expression = "java(tratamiento.obtenerResumen())")
    TratamientoDTO toDTO(Tratamiento tratamiento);

    /**
     * Convierte una lista de entidades a lista de DTOs
     * @param tratamientos lista de entidades
     * @return lista de DTOs
     */
    List<TratamientoDTO> toDTOList(List<Tratamiento> tratamientos);

    /**
     * Convierte CreateTratamientoRequest a entidad Tratamiento
     * @param request el DTO de creación
     * @return la entidad
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "consulta", ignore = true)
    @Mapping(target = "activo", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    Tratamiento toEntity(CreateTratamientoRequest request);
}
