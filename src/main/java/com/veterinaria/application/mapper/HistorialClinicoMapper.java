package com.veterinaria.application.mapper;

import com.veterinaria.application.dto.medical.HistorialClinicoDTO;
import com.veterinaria.application.dto.medical.UpdateHistorialClinicoRequest;
import com.veterinaria.domain.entity.medical.HistorialClinico;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para convertir entre HistorialClinico (entidad) y DTOs.
 * Utiliza MapStruct para generación automática de código.
 *
 * @author Sistema Veterinaria
 */
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ConsultaMapper.class, VacunaMapper.class, ExamenMapper.class, MapperUtils.class})
public interface HistorialClinicoMapper {

    /**
     * Convierte una entidad HistorialClinico a HistorialClinicoDTO
     * @param historialClinico la entidad
     * @return el DTO
     */
    @Mapping(source = "paciente.id", target = "pacienteId")
    @Mapping(source = "paciente.nombre", target = "pacienteNombre")
    @Mapping(source = "paciente.especie", target = "pacienteEspecie", qualifiedByName = "especieToString")
    @Mapping(source = "paciente.raza", target = "pacienteRaza")
    @Mapping(target = "ultimaConsulta", expression = "java(historialClinico.obtenerUltimaConsulta() != null ? consultaMapper.toDTO(historialClinico.obtenerUltimaConsulta()) : null)")
    @Mapping(target = "totalConsultas", expression = "java(historialClinico.getTotalConsultas())")
    @Mapping(target = "totalVacunas", expression = "java(historialClinico.getTotalVacunas())")
    @Mapping(target = "totalExamenes", expression = "java(historialClinico.getTotalExamenes())")
    @Mapping(target = "tieneAlergias", expression = "java(historialClinico.tieneAlergias())")
    @Mapping(target = "tieneCondicionesCronicas", expression = "java(historialClinico.tieneCondicionesCronicas())")
    HistorialClinicoDTO toDTO(HistorialClinico historialClinico);

    /**
     * Convierte una lista de entidades a lista de DTOs
     * @param historialesClinico lista de entidades
     * @return lista de DTOs
     */
    List<HistorialClinicoDTO> toDTOList(List<HistorialClinico> historialesClinico);

    /**
     * Actualiza una entidad HistorialClinico con datos de UpdateHistorialClinicoRequest
     * @param request el DTO con los datos a actualizar
     * @param historialClinico la entidad a actualizar
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "fechaApertura", ignore = true)
    @Mapping(target = "consultas", ignore = true)
    @Mapping(target = "vacunas", ignore = true)
    @Mapping(target = "examenes", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    void updateEntityFromDTO(UpdateHistorialClinicoRequest request, @MappingTarget HistorialClinico historialClinico);
}
