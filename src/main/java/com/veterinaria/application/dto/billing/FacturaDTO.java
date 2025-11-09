package com.veterinaria.application.dto.billing;

import com.veterinaria.domain.enums.EstadoFactura;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO para representar una Factura en las respuestas.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacturaDTO {

    private Long id;
    private String numeroFactura;
    private Long clienteId;
    private String clienteNombre;
    private Long citaId;
    private Long usuarioEmisorId;
    private String usuarioEmisorNombre;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private EstadoFactura estado;
    private Double subtotal;
    private Double totalDescuentos;
    private Double totalImpuestos;
    private Double total;
    private Double totalPagado;
    private Double saldoPendiente;
    private String observaciones;
    private String motivoAnulacion;
    private LocalDate fechaAnulacion;
    private List<DetalleFacturaDTO> detalles;
    private List<PagoDTO> pagos;
    private List<DescuentoDTO> descuentos;
}
