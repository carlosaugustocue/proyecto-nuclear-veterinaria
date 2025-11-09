package com.veterinaria.application.mapper;

import com.veterinaria.application.dto.billing.CreatePagoRequest;
import com.veterinaria.application.dto.billing.PagoDTO;
import com.veterinaria.domain.entity.billing.Pago;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para convertir entre Pago (entidad) y DTOs.
 * Utiliza MapStruct para generación automática de código.
 *
 * @author Sistema Veterinaria
 */
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PagoMapper {

    /**
     * Convierte una entidad Pago a PagoDTO
     * @param pago la entidad
     * @return el DTO
     */
    @Mapping(source = "factura.id", target = "facturaId")
    @Mapping(source = "usuarioRegistro.id", target = "usuarioRegistroId")
    @Mapping(source = "usuarioRegistro.nombreCompleto", target = "usuarioRegistroNombre")
    @Mapping(source = "usuarioVerificacion.id", target = "usuarioVerificacionId")
    @Mapping(source = "usuarioVerificacion.nombreCompleto", target = "usuarioVerificacionNombre")
    PagoDTO toDTO(Pago pago);

    /**
     * Convierte una lista de entidades a lista de DTOs
     * @param pagos lista de entidades
     * @return lista de DTOs
     */
    List<PagoDTO> toDTOList(List<Pago> pagos);

    /**
     * Convierte CreatePagoRequest a entidad Pago
     * @param request el DTO de creación
     * @return la entidad
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "factura", ignore = true)
    @Mapping(target = "horaPago", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "comision", ignore = true) // Se calcula en el servicio
    @Mapping(target = "montoNeto", ignore = true) // Se calcula en el servicio
    @Mapping(target = "usuarioRegistro", ignore = true)
    @Mapping(target = "verificado", constant = "false")
    @Mapping(target = "fechaVerificacion", ignore = true)
    @Mapping(target = "usuarioVerificacion", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    Pago toEntity(CreatePagoRequest request);
}
