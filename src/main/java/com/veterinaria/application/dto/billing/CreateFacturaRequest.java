package com.veterinaria.application.dto.billing;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para crear una nueva Factura.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateFacturaRequest {

    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;

    private Long citaId; // Opcional

    @NotNull(message = "El usuario emisor es obligatorio")
    private Long usuarioEmisorId;

    @NotNull(message = "La fecha de emisión es obligatoria")
    private LocalDate fechaEmision;

    private LocalDate fechaVencimiento;

    private String observaciones;
}
