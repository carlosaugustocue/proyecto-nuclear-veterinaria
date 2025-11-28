package com.veterinaria.application.dto.patients;

import com.veterinaria.domain.enums.TipoEspecie;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * Request DTO para crear una nueva Raza.
 *
 * @author Sistema Veterinaria
 */
@Builder
public record CreateRazaRequest(

    @NotBlank(message = "El nombre de la raza es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    String nombre,

    @NotNull(message = "La especie es obligatoria")
    TipoEspecie especie,

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    String descripcion,

    Boolean esMestizo,

    @Size(max = 20, message = "El tamaño típico no puede exceder 20 caracteres")
    String tamanioTipico,

    Double pesoPromedioKg
) {
}
