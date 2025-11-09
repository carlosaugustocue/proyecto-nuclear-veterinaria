package com.veterinaria.application.dto.medical;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para crear una nueva Vacuna.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateVacunaRequest {

    @NotNull(message = "El ID del historial clínico es obligatorio")
    private Long historialClinicoId;

    @NotNull(message = "El ID del veterinario es obligatorio")
    private Long veterinarioId;

    @NotBlank(message = "El nombre de la vacuna es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String nombre;

    @NotBlank(message = "El tipo de vacuna es obligatorio")
    @Size(max = 100, message = "El tipo no puede exceder 100 caracteres")
    private String tipoVacuna;

    @NotNull(message = "La fecha de aplicación es obligatoria")
    private LocalDate fechaAplicacion;

    private LocalDate fechaProximaDosis;

    @Size(max = 50, message = "El lote no puede exceder 50 caracteres")
    private String lote;

    @Size(max = 100, message = "El laboratorio no puede exceder 100 caracteres")
    private String laboratorio;

    @Size(max = 50, message = "La dosis no puede exceder 50 caracteres")
    private String dosis;

    @Size(max = 50, message = "La vía de administración no puede exceder 50 caracteres")
    private String viaAdministracion;

    @Min(value = 1, message = "El número de dosis debe ser al menos 1")
    private Integer numeroDosis;

    private Integer intervaloDias;

    private Boolean esRefuerzo;

    @Size(max = 5000, message = "Las observaciones no pueden exceder 5000 caracteres")
    private String observaciones;

    @Size(max = 5000, message = "Las reacciones adversas no pueden exceder 5000 caracteres")
    private String reaccionesAdversas;

    @DecimalMin(value = "0.1", message = "El peso debe ser mayor a 0.1 kg")
    @DecimalMax(value = "200.0", message = "El peso no puede exceder 200 kg")
    private Double pesoAplicacion;

    private Boolean serieCompleta;
}
