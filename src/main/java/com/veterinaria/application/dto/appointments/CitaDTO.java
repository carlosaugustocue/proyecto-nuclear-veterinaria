package com.veterinaria.application.dto.appointments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * DTO para representar una Cita en las respuestas de la API.
 * Patrón DTO para desacoplar la capa de presentación del dominio.
 *
 * @author Sistema Veterinaria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitaDTO {

    private Long id;
    private LocalDate fecha;
    private LocalTime hora;
    private String motivo;
    private String estado; // Nombre del estado
    private String notas;
    private Integer duracionEstimada;
    private String motivoCancelacion;

    // Información del veterinario
    private Long veterinarioId;
    private String veterinarioNombre;
    private String veterinarioEmail;

    // Información del paciente
    private Long pacienteId;
    private String pacienteNombre;
    private String pacienteEspecie;

    // Información del cliente
    private Long clienteId;
    private String clienteNombre;
    private String clienteEmail;
    private String clienteTelefono;

    // Información del tipo de servicio
    private Long tipoServicioId;
    private String tipoServicioNombre;
    private String tipoServicioCategoria;
    private Double tipoServicioPrecio;

    // Fechas de control
    private LocalDateTime fechaConfirmacion;
    private LocalDateTime fechaInicioAtencion;
    private LocalDateTime fechaFinAtencion;

    // Metadata
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

    // Campos calculados
    private LocalTime horaFinEstimada;
    private String resumen;
    private Boolean esModificable;
    private Boolean estaActiva;
}
