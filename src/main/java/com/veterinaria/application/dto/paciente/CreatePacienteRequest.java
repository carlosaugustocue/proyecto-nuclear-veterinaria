package com.veterinaria.application.dto.paciente;

import com.veterinaria.domain.enums.Sexo;
import com.veterinaria.domain.enums.TipoEspecie;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * DTO para crear un nuevo paciente (mascota).
 * Utiliza Java Record para inmutabilidad.
 *
 * @author Sistema Veterinaria
 */
public record CreatePacienteRequest(
    @NotBlank(message = "El nombre del paciente es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    String nombre,

    @NotNull(message = "La especie es obligatoria")
    TipoEspecie especie,

    @Size(max = 100, message = "La raza no puede exceder 100 caracteres")
    String raza,

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    LocalDate fechaNacimiento,

    @NotNull(message = "El sexo es obligatorio")
    Sexo sexo,

    @Size(max = 50, message = "El color no puede exceder 50 caracteres")
    String color,

    @DecimalMin(value = "0.1", message = "El peso debe ser mayor a 0.1 kg")
    @DecimalMax(value = "500.0", message = "El peso no puede exceder 500 kg")
    Double pesoKg,

    @Size(max = 500, message = "La URL de la foto no puede exceder 500 caracteres")
    String fotoUrl,

    @Size(max = 1000, message = "Las observaciones no pueden exceder 1000 caracteres")
    String observaciones,

    @Pattern(regexp = "^[A-Z0-9]{15}$", message = "El microchip debe tener 15 caracteres alfanuméricos")
    String microchip,

    @NotNull(message = "El ID del cliente es obligatorio")
    @Positive(message = "El ID del cliente debe ser positivo")
    Long clienteId
) {
}
