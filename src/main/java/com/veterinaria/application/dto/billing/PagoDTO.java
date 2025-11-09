package com.veterinaria.application.dto.billing;

import com.veterinaria.domain.enums.MetodoPago;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO para representar un Pago en las respuestas.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {

    private Long id;
    private Long facturaId;
    private LocalDate fechaPago;
    private LocalDateTime horaPago;
    private MetodoPago metodoPago;
    private Double monto;
    private Double comision;
    private Double montoNeto;
    private String numeroReferencia;
    private String numeroVoucher;
    private Long usuarioRegistroId;
    private String usuarioRegistroNombre;
    private String observaciones;
    private Boolean verificado;
    private LocalDateTime fechaVerificacion;
    private Long usuarioVerificacionId;
    private String usuarioVerificacionNombre;
}
