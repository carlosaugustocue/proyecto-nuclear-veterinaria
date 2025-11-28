package com.veterinaria.application.dto.patients;

import com.veterinaria.domain.enums.TipoEspecie;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * DTO para transferir información de Raza.
 *
 * @author Sistema Veterinaria
 */
@Builder
public record RazaDTO(
    Long id,
    String nombre,
    TipoEspecie especie,
    String descripcion,
    Boolean esPredefinida,
    Boolean esMestizo,
    String tamanioTipico,
    Double pesoPromedioKg,
    Boolean isActive,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    String createdBy,
    String updatedBy
) {
}
