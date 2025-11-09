package com.veterinaria.application.mapper;

import com.veterinaria.application.dto.billing.CreateFacturaRequest;
import com.veterinaria.application.dto.billing.FacturaDTO;
import com.veterinaria.application.dto.billing.UpdateFacturaRequest;
import com.veterinaria.domain.entity.billing.Factura;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper para convertir entre Factura (entidad) y DTOs.
 * Utiliza MapStruct para generación automática de código.
 *
 * @author Sistema Veterinaria
 */
@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {DetalleFacturaMapper.class, PagoMapper.class, DescuentoMapper.class})
public interface FacturaMapper {

    /**
     * Convierte una entidad Factura a FacturaDTO
     * @param factura la entidad
     * @return el DTO
     */
    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nombreCompleto", target = "clienteNombre")
    @Mapping(source = "cita.id", target = "citaId")
    @Mapping(source = "usuarioEmisor.id", target = "usuarioEmisorId")
    @Mapping(source = "usuarioEmisor.nombreCompleto", target = "usuarioEmisorNombre")
    FacturaDTO toDTO(Factura factura);

    /**
     * Convierte una lista de entidades a lista de DTOs
     * @param facturas lista de entidades
     * @return lista de DTOs
     */
    List<FacturaDTO> toDTOList(List<Factura> facturas);

    /**
     * Convierte CreateFacturaRequest a entidad Factura
     * @param request el DTO de creación
     * @return la entidad
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "numeroFactura", ignore = true) // Se genera en el servicio
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "cita", ignore = true)
    @Mapping(target = "usuarioEmisor", ignore = true)
    @Mapping(target = "estado", constant = "PENDIENTE")
    @Mapping(target = "subtotal", constant = "0.0")
    @Mapping(target = "totalDescuentos", constant = "0.0")
    @Mapping(target = "totalImpuestos", constant = "0.0")
    @Mapping(target = "total", constant = "0.0")
    @Mapping(target = "totalPagado", constant = "0.0")
    @Mapping(target = "saldoPendiente", constant = "0.0")
    @Mapping(target = "motivoAnulacion", ignore = true)
    @Mapping(target = "fechaAnulacion", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    @Mapping(target = "pagos", ignore = true)
    @Mapping(target = "descuentos", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    Factura toEntity(CreateFacturaRequest request);

    /**
     * Actualiza una entidad Factura con datos de UpdateFacturaRequest
     * @param request el DTO con los datos a actualizar
     * @param factura la entidad a actualizar
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "numeroFactura", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "cita", ignore = true)
    @Mapping(target = "usuarioEmisor", ignore = true)
    @Mapping(target = "fechaEmision", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    @Mapping(target = "totalDescuentos", ignore = true)
    @Mapping(target = "totalImpuestos", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "totalPagado", ignore = true)
    @Mapping(target = "saldoPendiente", ignore = true)
    @Mapping(target = "motivoAnulacion", ignore = true)
    @Mapping(target = "fechaAnulacion", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    @Mapping(target = "pagos", ignore = true)
    @Mapping(target = "descuentos", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    void updateEntityFromDTO(UpdateFacturaRequest request, @MappingTarget Factura factura);
}
