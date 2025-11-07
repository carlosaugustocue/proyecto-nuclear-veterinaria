package com.veterinaria.application.dto.cliente;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO de respuesta para Cliente.
 * Incluye resumen de pacientes.
 *
 * @author Sistema Veterinaria
 */
public record ClienteDTO(
    Long id,
    String nombre,
    String apellido,
    String nombreCompleto,
    String dni,
    String email,
    String telefono,
    String direccion,
    String ciudad,
    String departamento,
    String codigoPostal,
    String observaciones,
    List<PacienteResumenDTO> pacientes,
    long cantidadPacientesActivos,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    /**
     * DTO anidado con resumen de paciente.
     */
    public record PacienteResumenDTO(
        Long id,
        String nombre,
        String especie,
        int edadAnios,
        String estado
    ) {
    }
}
