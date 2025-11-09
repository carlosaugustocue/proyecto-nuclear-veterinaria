package com.veterinaria.application.mapper;

import com.veterinaria.application.dto.medical.ConsultaDTO;
import com.veterinaria.application.dto.medical.CreateConsultaRequest;
import com.veterinaria.application.dto.medical.UpdateConsultaRequest;
import com.veterinaria.domain.entity.medical.Consulta;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para convertir entre Consulta (entidad) y DTOs.
 * Utiliza MapStruct para generación automática de código.
 *
 * @author Sistema Veterinaria
 */
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {DiagnosticoMapper.class, TratamientoMapper.class, SignosVitalesMapper.class, MapperUtils.class})
public interface ConsultaMapper {

    /**
     * Convierte una entidad Consulta a ConsultaDTO
     * @param consulta la entidad
     * @return el DTO
     */
    @Mapping(source = "historialClinico.id", target = "historialClinicoId")
    @Mapping(source = "historialClinico.paciente.id", target = "pacienteId")
    @Mapping(source = "historialClinico.paciente.nombre", target = "pacienteNombre")
    @Mapping(source = "historialClinico.paciente.especie", target = "pacienteEspecie", qualifiedByName = "especieToString")
    @Mapping(source = "veterinario.id", target = "veterinarioId")
    @Mapping(source = "veterinario.nombreCompleto", target = "veterinarioNombre")
    @Mapping(source = "cita.id", target = "citaId")
    @Mapping(target = "duracionMinutos", expression = "java(consulta.calcularDuracionMinutos())")
    @Mapping(target = "estaEnCurso", expression = "java(consulta.estaEnCurso())")
    @Mapping(target = "estaFinalizada", expression = "java(consulta.estaFinalizada())")
    @Mapping(target = "resumen", expression = "java(consulta.obtenerResumen())")
    ConsultaDTO toDTO(Consulta consulta);

    /**
     * Convierte una lista de entidades a lista de DTOs
     * @param consultas lista de entidades
     * @return lista de DTOs
     */
    List<ConsultaDTO> toDTOList(List<Consulta> consultas);

    /**
     * Convierte CreateConsultaRequest a entidad Consulta
     * @param request el DTO de creación
     * @return la entidad
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "historialClinico", ignore = true)
    @Mapping(target = "cita", ignore = true)
    @Mapping(target = "veterinario", ignore = true)
    @Mapping(target = "diagnosticos", ignore = true)
    @Mapping(target = "tratamientos", ignore = true)
    @Mapping(target = "horaInicio", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "horaFin", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    Consulta toEntity(CreateConsultaRequest request);

    /**
     * Actualiza una entidad Consulta con datos de UpdateConsultaRequest
     * @param request el DTO con los datos a actualizar
     * @param consulta la entidad a actualizar
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "historialClinico", ignore = true)
    @Mapping(target = "cita", ignore = true)
    @Mapping(target = "veterinario", ignore = true)
    @Mapping(target = "fechaConsulta", ignore = true)
    @Mapping(target = "horaInicio", ignore = true)
    @Mapping(target = "horaFin", ignore = true)
    @Mapping(target = "diagnosticos", ignore = true)
    @Mapping(target = "tratamientos", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    void updateEntityFromDTO(UpdateConsultaRequest request, @MappingTarget Consulta consulta);
}
