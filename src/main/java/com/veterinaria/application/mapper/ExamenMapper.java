package com.veterinaria.application.mapper;

import com.veterinaria.application.dto.medical.CreateExamenRequest;
import com.veterinaria.application.dto.medical.ExamenDTO;
import com.veterinaria.domain.entity.medical.Examen;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para convertir entre Examen (entidad) y DTOs.
 * Utiliza MapStruct para generación automática de código.
 *
 * @author Sistema Veterinaria
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ExamenMapper {

    /**
     * Convierte una entidad Examen a ExamenDTO
     * @param examen la entidad
     * @return el DTO
     */
    @Mapping(source = "historialClinico.id", target = "historialClinicoId")
    @Mapping(source = "historialClinico.paciente.id", target = "pacienteId")
    @Mapping(source = "historialClinico.paciente.nombre", target = "pacienteNombre")
    @Mapping(source = "veterinarioSolicitante.id", target = "veterinarioSolicitanteId")
    @Mapping(source = "veterinarioSolicitante.nombreCompleto", target = "veterinarioSolicitanteNombre")
    @Mapping(source = "consulta.id", target = "consultaId")
    @Mapping(target = "estaPendiente", expression = "java(examen.estaPendiente())")
    @Mapping(target = "estaCompletado", expression = "java(examen.estaCompletado())")
    @Mapping(target = "esLaboratorio", expression = "java(examen.esLaboratorio())")
    @Mapping(target = "esImagenologia", expression = "java(examen.esImagenologia())")
    @Mapping(target = "tieneArchivo", expression = "java(examen.tieneArchivo())")
    @Mapping(target = "esExterno", expression = "java(examen.esExterno())")
    @Mapping(target = "diasDesdeSolicitud", expression = "java(examen.diasDesdeSolicitud())")
    @Mapping(target = "estadoDescriptivo", expression = "java(examen.obtenerEstadoDescriptivo())")
    @Mapping(target = "resumen", expression = "java(examen.obtenerResumen())")
    ExamenDTO toDTO(Examen examen);

    /**
     * Convierte una lista de entidades a lista de DTOs
     * @param examenes lista de entidades
     * @return lista de DTOs
     */
    List<ExamenDTO> toDTOList(List<Examen> examenes);

    /**
     * Convierte CreateExamenRequest a entidad Examen
     * @param request el DTO de creación
     * @return la entidad
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "historialClinico", ignore = true)
    @Mapping(target = "veterinarioSolicitante", ignore = true)
    @Mapping(target = "consulta", ignore = true)
    @Mapping(target = "fechaResultados", ignore = true)
    @Mapping(target = "estado", constant = "SOLICITADO")
    @Mapping(target = "resultados", ignore = true)
    @Mapping(target = "valoresReferencia", ignore = true)
    @Mapping(target = "interpretacion", ignore = true)
    @Mapping(target = "hallazgos", ignore = true)
    @Mapping(target = "resultadoAnormal", constant = "false")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    Examen toEntity(CreateExamenRequest request);
}
