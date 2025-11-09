package com.veterinaria.application.dto.medical;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para actualizar un Historial Clínico.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateHistorialClinicoRequest {

    @Size(max = 10, message = "El grupo sanguíneo no puede exceder 10 caracteres")
    private String grupoSanguineo;

    @Size(max = 5000, message = "Las alergias no pueden exceder 5000 caracteres")
    private String alergias;

    @Size(max = 5000, message = "Las condiciones crónicas no pueden exceder 5000 caracteres")
    private String condicionesCronicas;

    @Size(max = 5000, message = "Las notas importantes no pueden exceder 5000 caracteres")
    private String notasImportantes;
}
