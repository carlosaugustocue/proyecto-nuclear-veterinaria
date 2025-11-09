package com.veterinaria.application.dto.medical;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para registrar los resultados de un Examen médico.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarResultadosExamenRequest {

    private LocalDate fechaRealizacion;

    @NotBlank(message = "Los resultados son obligatorios")
    @Size(max = 10000, message = "Los resultados no pueden exceder 10000 caracteres")
    private String resultados;

    @Size(max = 5000, message = "Los valores de referencia no pueden exceder 5000 caracteres")
    private String valoresReferencia;

    @Size(max = 5000, message = "La interpretación no puede exceder 5000 caracteres")
    private String interpretacion;

    @Size(max = 5000, message = "Los hallazgos no pueden exceder 5000 caracteres")
    private String hallazgos;

    @Size(max = 500, message = "La ruta del archivo no puede exceder 500 caracteres")
    private String archivoRuta;

    @Size(max = 100, message = "El tipo MIME no puede exceder 100 caracteres")
    private String archivoTipo;

    private Boolean resultadoAnormal;
}
