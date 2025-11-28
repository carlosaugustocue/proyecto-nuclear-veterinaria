package com.veterinaria.application.mapper;

import com.veterinaria.application.dto.appointments.CitaDTO;
import com.veterinaria.application.dto.appointments.UpdateCitaRequest;
import com.veterinaria.domain.entity.appointments.Cita;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para convertir entre Cita (entidad) y CitaDTO.
 * Utiliza MapStruct para generación automática de código.
 *
 * @author Sistema Veterinaria
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CitaMapper {

    /**
     * Convierte una entidad Cita a CitaDTO
     * @param cita la entidad
     * @return el DTO
     */
    @Mapping(source = "estado", target = "estado", qualifiedByName = "estadoToString")
    @Mapping(source = "veterinario.id", target = "veterinarioId")
    @Mapping(source = "veterinario.nombreCompleto", target = "veterinarioNombre")
    @Mapping(source = "veterinario.email", target = "veterinarioEmail")
    @Mapping(source = "paciente.id", target = "pacienteId")
    @Mapping(source = "paciente.nombre", target = "pacienteNombre")
    @Mapping(source = "paciente.especie", target = "pacienteEspecie", qualifiedByName = "especieToString")
    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nombreCompleto", target = "clienteNombre")
    @Mapping(source = "cliente.email", target = "clienteEmail")
    @Mapping(source = "cliente.telefono", target = "clienteTelefono")
    @Mapping(source = "tipoServicio.id", target = "tipoServicioId")
    @Mapping(source = "tipoServicio.nombre", target = "tipoServicioNombre")
    @Mapping(source = "tipoServicio.categoria", target = "tipoServicioCategoria")
    @Mapping(source = "tipoServicio.precioBase", target = "tipoServicioPrecio")
    @Mapping(target = "horaFinEstimada", expression = "java(cita.getHoraFinEstimada())")
    @Mapping(target = "resumen", expression = "java(cita.obtenerResumen())")
    @Mapping(target = "esModificable", expression = "java(cita.esModificable())")
    @Mapping(target = "estaActiva", expression = "java(cita.estaActiva())")
    CitaDTO toDTO(Cita cita);

    /**
     * Convierte una lista de entidades a lista de DTOs
     * @param citas lista de entidades
     * @return lista de DTOs
     */
    List<CitaDTO> toDTOList(List<Cita> citas);

    /**
     * Named mapping para convertir EstadoCita a String
     */
    @Named("estadoToString")
    default String estadoToString(com.veterinaria.domain.entity.appointments.EstadoCita estado) {
        return estado != null ? estado.getNombre() : null;
    }

    /**
     * Named mapping para convertir TipoEspecie enum a String
     */
    @Named("especieToString")
    default String especieToString(com.veterinaria.domain.enums.TipoEspecie especie) {
        return especie != null ? especie.name() : null;
    }

    /**
     * Actualiza una entidad Cita con datos de UpdateCitaRequest
     * Solo actualiza campos permitidos (motivo y notas)
     * @param request el DTO con los datos a actualizar
     * @param cita la entidad a actualizar
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    @Mapping(target = "hora", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "veterinario", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "tipoServicio", ignore = true)
    @Mapping(target = "duracionEstimada", ignore = true)
    @Mapping(target = "motivoCancelacion", ignore = true)
    @Mapping(target = "fechaConfirmacion", ignore = true)
    @Mapping(target = "fechaInicioAtencion", ignore = true)
    @Mapping(target = "fechaFinAtencion", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    void updateEntityFromDTO(UpdateCitaRequest request,
                             @MappingTarget Cita cita);
}
