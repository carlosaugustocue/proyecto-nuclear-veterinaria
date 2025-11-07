package com.veterinaria.application.mapper;

import com.veterinaria.application.dto.paciente.PacienteDTO;
import com.veterinaria.domain.entity.patients.Cliente;
import com.veterinaria.domain.entity.patients.Paciente;
import org.springframework.stereotype.Component;

/**
 * Mapper manual para convertir entre Paciente y PacienteDTO.
 * Evita antipatrón de exponer entidades directamente.
 *
 * @author Sistema Veterinaria
 */
@Component
public class PacienteMapper {

    /**
     * Convierte Paciente entity a PacienteDTO.
     */
    public PacienteDTO toDTO(Paciente paciente) {
        if (paciente == null) {
            return null;
        }

        return new PacienteDTO(
            paciente.getId(),
            paciente.getNombre(),
            paciente.getEspecie(),
            paciente.getRaza(),
            paciente.getFechaNacimiento(),
            paciente.calcularEdadAnios(),
            paciente.getSexo(),
            paciente.getColor(),
            paciente.getPesoKg(),
            paciente.getEstado(),
            paciente.getFotoUrl(),
            paciente.getObservaciones(),
            paciente.getMicrochip(),
            toClienteResumen(paciente.getCliente()),
            paciente.obtenerCuidadosEspecificos(),
            paciente.obtenerDietaRecomendada(),
            paciente.getCreatedAt(),
            paciente.getUpdatedAt()
        );
    }

    /**
     * Convierte Cliente a ClienteResumenDTO anidado.
     */
    private PacienteDTO.ClienteResumenDTO toClienteResumen(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        return new PacienteDTO.ClienteResumenDTO(
            cliente.getId(),
            cliente.getNombreCompleto(),
            cliente.getTelefono(),
            cliente.getEmail()
        );
    }
}
