package com.veterinaria.application.mapper;

import com.veterinaria.application.dto.medical.CreateVacunaRequest;
import com.veterinaria.application.dto.medical.VacunaDTO;
import com.veterinaria.domain.entity.medical.Vacuna;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para convertir entre Vacuna (entidad) y DTOs.
 * Utiliza MapStruct para generación automática de código.
 *
 * @author Sistema Veterinaria
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VacunaMapper {

    /**
     * Convierte una entidad Vacuna a VacunaDTO
     * @param vacuna la entidad
     * @return el DTO
     */
    @Mapping(source = "historialClinico.id", target = "historialClinicoId")
    @Mapping(source = "historialClinico.paciente.id", target = "pacienteId")
    @Mapping(source = "historialClinico.paciente.nombre", target = "pacienteNombre")
    @Mapping(source = "veterinario.id", target = "veterinarioId")
    @Mapping(source = "veterinario.nombreCompleto", target = "veterinarioNombre")
    @Mapping(target = "esObligatoria", expression = "java(vacuna.esObligatoria())")
    @Mapping(target = "esAntirrábica", expression = "java(vacuna.esAntirrábica())")
    @Mapping(target = "refuerzoVencido", expression = "java(vacuna.refuerzoVencido())")
    @Mapping(target = "refuerzoProximo", expression = "java(vacuna.requiereRefuerzoProximo(30))")
    @Mapping(target = "tuvoReacciones", expression = "java(vacuna.tuvoReaccionesAdversas())")
    @Mapping(target = "estado", expression = "java(vacuna.obtenerEstado())")
    @Mapping(target = "resumen", expression = "java(vacuna.obtenerResumen())")
    VacunaDTO toDTO(Vacuna vacuna);

    /**
     * Convierte una lista de entidades a lista de DTOs
     * @param vacunas lista de entidades
     * @return lista de DTOs
     */
    List<VacunaDTO> toDTOList(List<Vacuna> vacunas);

    /**
     * Convierte CreateVacunaRequest a entidad Vacuna
     * @param request el DTO de creación
     * @return la entidad
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "historialClinico", ignore = true)
    @Mapping(target = "veterinario", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    Vacuna toEntity(CreateVacunaRequest request);
}
