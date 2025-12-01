package com.veterinaria.application.dto.billing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para representar un Detalle de Factura en las respuestas.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleFacturaDTO {

    private Long id;
    private Long facturaId;
    private Long tipoServicioId;
    private String tipoServicioNombre;
    private Long productoId;
    private String productoNombre;
    private String descripcion;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
    private Double descuentoPorcentaje;
    private Double descuentoMonto;
    private Double total;
    private String observaciones;
}
