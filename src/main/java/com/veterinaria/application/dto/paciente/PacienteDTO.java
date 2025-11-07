package com.veterinaria.application.dto.paciente;

import com.veterinaria.domain.enums.EstadoPaciente;
import com.veterinaria.domain.enums.Sexo;
import com.veterinaria.domain.enums.TipoEspecie;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO de respuesta para Paciente.
 * Incluye información del cliente propietario.
 *
 * @author Sistema Veterinaria
 */
public record PacienteDTO(
    Long id,
    String nombre,
    TipoEspecie especie,
    String raza,
    LocalDate fechaNacimiento,
    int edadAnios,
    Sexo sexo,
    String color,
    Double pesoKg,
    EstadoPaciente estado,
    String fotoUrl,
    String observaciones,
    String microchip,
    ClienteResumenDTO cliente,
    String cuidadosEspecificos,
    String dietaRecomendada,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    /**
     * DTO anidado con resumen del cliente propietario.
     */
    public record ClienteResumenDTO(
        Long id,
        String nombreCompleto,
        String telefono,
        String email
    ) {
    }
}
