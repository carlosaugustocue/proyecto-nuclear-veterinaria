package com.veterinaria.application.dto.medical;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para crear un nuevo Examen médico.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateExamenRequest {

    @NotNull(message = "El ID del historial clínico es obligatorio")
    private Long historialClinicoId;

    @NotNull(message = "El ID del veterinario solicitante es obligatorio")
    private Long veterinarioSolicitanteId;

    private Long consultaId; // Opcional

    @NotBlank(message = "El tipo de examen es obligatorio")
    @Size(max = 100, message = "El tipo no puede exceder 100 caracteres")
    private String tipoExamen;

    @NotBlank(message = "El nombre del examen es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String nombre;

    @Size(max = 5000, message = "La descripción no puede exceder 5000 caracteres")
    private String descripcion;

    @NotNull(message = "La fecha de solicitud es obligatoria")
    private LocalDate fechaSolicitud;

    private LocalDate fechaRealizacion;

    @Size(max = 500, message = "La ruta del archivo no puede exceder 500 caracteres")
    private String archivoRuta;

    @Size(max = 100, message = "El tipo MIME no puede exceder 100 caracteres")
    private String archivoTipo;

    @Size(max = 200, message = "El laboratorio externo no puede exceder 200 caracteres")
    private String laboratorioExterno;

    @Size(max = 100, message = "La referencia externa no puede exceder 100 caracteres")
    private String referenciaExterna;

    @DecimalMin(value = "0.0", message = "El costo debe ser positivo")
    private Double costo;

    @Size(max = 5000, message = "Las notas no pueden exceder 5000 caracteres")
    private String notas;

    private Boolean requiereAyuno;

    private Boolean esUrgente;
}
