package com.veterinaria.application.dto.medical;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para crear un nuevo Tratamiento.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTratamientoRequest {

    @NotNull(message = "El ID de la consulta es obligatorio")
    private Long consultaId;

    @NotBlank(message = "El tipo de tratamiento es obligatorio")
    @Size(max = 50, message = "El tipo no puede exceder 50 caracteres")
    private String tipoTratamiento;

    @NotBlank(message = "El nombre del tratamiento es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String nombre;

    @Size(max = 5000, message = "La descripción no puede exceder 5000 caracteres")
    private String descripcion;

    @Size(max = 100, message = "La dosis no puede exceder 100 caracteres")
    private String dosis;

    @Size(max = 100, message = "La frecuencia no puede exceder 100 caracteres")
    private String frecuencia;

    @Size(max = 50, message = "La vía de administración no puede exceder 50 caracteres")
    private String viaAdministracion;

    @Min(value = 1, message = "La duración debe ser al menos 1 día")
    private Integer duracionDias;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @Size(max = 5000, message = "Las indicaciones no pueden exceder 5000 caracteres")
    private String indicaciones;

    @Size(max = 5000, message = "Los efectos secundarios no pueden exceder 5000 caracteres")
    private String efectosSecundarios;

    @DecimalMin(value = "0.0", message = "La cantidad debe ser positiva")
    private Double cantidad;

    @Size(max = 20, message = "La unidad no puede exceder 20 caracteres")
    private String unidad;

    private Boolean requiereSupervision;
}
