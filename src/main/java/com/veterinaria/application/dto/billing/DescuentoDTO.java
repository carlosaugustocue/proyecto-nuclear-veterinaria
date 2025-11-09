package com.veterinaria.application.dto.billing;

import com.veterinaria.domain.enums.TipoDescuento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para representar un Descuento en las respuestas.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DescuentoDTO {

    private Long id;
    private Long facturaId;
    private String codigo;
    private String descripcion;
    private TipoDescuento tipo;
    private Double valor;
    private Double monto;
    private String motivo;
    private LocalDate fechaAplicacion;
    private Boolean esPromocional;
    private Boolean esValido;
}
