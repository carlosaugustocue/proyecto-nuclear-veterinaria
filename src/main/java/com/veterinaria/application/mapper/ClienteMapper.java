package com.veterinaria.application.mapper;

import com.veterinaria.application.dto.patients.ClienteDTO;
import com.veterinaria.domain.entity.patients.Cliente;
import com.veterinaria.domain.entity.patients.Paciente;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper manual para convertir entre Cliente y ClienteDTO.
 * Evita antipatrón de exponer entidades directamente.
 *
 * @author Sistema Veterinaria
 */
@Component
public class ClienteMapper {

    /**
     * Convierte Cliente entity a ClienteDTO.
     */
    public ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        return new ClienteDTO(
            cliente.getId(),
            cliente.getNombre(),
            cliente.getApellido(),
            cliente.getNombreCompleto(),
            cliente.getDni(),
            cliente.getEmail(),
            cliente.getTelefono(),
            cliente.getDireccion(),
            cliente.getCiudad(),
            cliente.getDepartamento(),
            cliente.getCodigoPostal(),
            cliente.getObservaciones(),
            toPacientesResumen(cliente.getPacientes()),
            cliente.contarPacientesActivos(),
            cliente.getCreatedAt(),
            cliente.getUpdatedAt()
        );
    }

    /**
     * Convierte lista de Pacientes a lista de PacienteResumenDTO anidados.
     */
    private List<ClienteDTO.PacienteResumenDTO> toPacientesResumen(List<Paciente> pacientes) {
        if (pacientes == null) {
            return List.of();
        }

        return pacientes.stream()
            .map(this::toPacienteResumen)
            .collect(Collectors.toList());
    }

    /**
     * Convierte Paciente a PacienteResumenDTO anidado.
     */
    private ClienteDTO.PacienteResumenDTO toPacienteResumen(Paciente paciente) {
        return new ClienteDTO.PacienteResumenDTO(
            paciente.getId(),
            paciente.getNombre(),
            paciente.getEspecie().getNombreComun(),
            paciente.calcularEdadAnios(),
            paciente.getEstado().getDisplayName()
        );
    }
}
