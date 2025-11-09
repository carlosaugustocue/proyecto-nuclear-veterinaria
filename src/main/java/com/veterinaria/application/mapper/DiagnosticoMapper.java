package com.veterinaria.application.mapper;

import com.veterinaria.application.dto.medical.CreateDiagnosticoRequest;
import com.veterinaria.application.dto.medical.DiagnosticoDTO;
import com.veterinaria.domain.entity.medical.Diagnostico;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para convertir entre Diagnostico (entidad) y DTOs.
 * Utiliza MapStruct para generación automática de código.
 *
 * @author Sistema Veterinaria
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DiagnosticoMapper {

    /**
     * Convierte una entidad Diagnostico a DiagnosticoDTO
     * @param diagnostico la entidad
     * @return el DTO
     */
    @Mapping(source = "consulta.id", target = "consultaId")
    @Mapping(target = "esPresuntivo", expression = "java(diagnostico.esPresuntivo())")
    @Mapping(target = "esDefinitivo", expression = "java(diagnostico.esDefinitivo())")
    @Mapping(target = "esGraveOCritico", expression = "java(diagnostico.esGraveOCritico())")
    @Mapping(target = "tieneCie10", expression = "java(diagnostico.tieneCie10())")
    DiagnosticoDTO toDTO(Diagnostico diagnostico);

    /**
     * Convierte una lista de entidades a lista de DTOs
     * @param diagnosticos lista de entidades
     * @return lista de DTOs
     */
    List<DiagnosticoDTO> toDTOList(List<Diagnostico> diagnosticos);

    /**
     * Convierte CreateDiagnosticoRequest a entidad Diagnostico
     * @param request el DTO de creación
     * @return la entidad
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "consulta", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    Diagnostico toEntity(CreateDiagnosticoRequest request);
}
