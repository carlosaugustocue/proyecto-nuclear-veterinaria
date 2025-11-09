package com.veterinaria.application.service.impl;

import com.veterinaria.application.dto.medical.HistorialClinicoDTO;
import com.veterinaria.application.dto.medical.UpdateHistorialClinicoRequest;
import com.veterinaria.application.exception.BusinessRuleException;
import com.veterinaria.application.exception.ResourceNotFoundException;
import com.veterinaria.application.mapper.HistorialClinicoMapper;
import com.veterinaria.application.repository.HistorialClinicoRepository;
import com.veterinaria.application.repository.PacienteRepository;
import com.veterinaria.application.service.HistorialClinicoService;
import com.veterinaria.domain.entity.medical.HistorialClinico;
import com.veterinaria.domain.entity.patients.Paciente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementación del servicio de Historiales Clínicos.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistorialClinicoServiceImpl implements HistorialClinicoService {

    private final HistorialClinicoRepository historialClinicoRepository;
    private final PacienteRepository pacienteRepository;
    private final HistorialClinicoMapper historialClinicoMapper;

    @Override
    @Transactional
    public HistorialClinicoDTO crear(Long pacienteId) {
        log.info("Creando historial clínico para paciente ID: {}", pacienteId);

        // Validar que el paciente existe
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Paciente no encontrado con ID: " + pacienteId
                ));

        // Validar que no tenga historial previo
        if (historialClinicoRepository.existsByPacienteId(pacienteId)) {
            throw new BusinessRuleException(
                    "El paciente ya tiene un historial clínico registrado"
            );
        }

        // Crear historial
        HistorialClinico historialClinico = HistorialClinico.builder()
                .paciente(paciente)
                .fechaApertura(LocalDate.now())
                .build();

        HistorialClinico guardado = historialClinicoRepository.save(historialClinico);

        log.info("Historial clínico creado exitosamente con ID: {}", guardado.getId());
        return historialClinicoMapper.toDTO(guardado);
    }

    @Override
    @Transactional
    public HistorialClinicoDTO actualizar(Long id, UpdateHistorialClinicoRequest request) {
        log.info("Actualizando historial clínico ID: {}", id);

        HistorialClinico historialClinico = historialClinicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Historial clínico no encontrado con ID: " + id
                ));

        // Actualizar usando mapper
        historialClinicoMapper.updateEntityFromDTO(request, historialClinico);

        HistorialClinico actualizado = historialClinicoRepository.save(historialClinico);

        log.info("Historial clínico actualizado exitosamente ID: {}", id);
        return historialClinicoMapper.toDTO(actualizado);
    }

    @Override
    public HistorialClinicoDTO obtenerPorId(Long id) {
        log.debug("Buscando historial clínico con ID: {}", id);

        HistorialClinico historialClinico = historialClinicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Historial clínico no encontrado con ID: " + id
                ));

        return historialClinicoMapper.toDTO(historialClinico);
    }

    @Override
    public HistorialClinicoDTO obtenerPorPaciente(Long pacienteId) {
        log.debug("Buscando historial clínico del paciente ID: {}", pacienteId);

        HistorialClinico historialClinico = historialClinicoRepository.findByPacienteId(pacienteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró historial clínico para el paciente ID: " + pacienteId
                ));

        return historialClinicoMapper.toDTO(historialClinico);
    }

    @Override
    public List<HistorialClinicoDTO> listarTodos() {
        log.debug("Listando todos los historiales clínicos");

        List<HistorialClinico> historiales = historialClinicoRepository.findAll();
        return historialClinicoMapper.toDTOList(historiales);
    }

    @Override
    public List<HistorialClinicoDTO> listarConAlergias() {
        log.debug("Listando historiales con alergias registradas");

        List<HistorialClinico> historiales = historialClinicoRepository.findHistorialesConAlergias();
        return historialClinicoMapper.toDTOList(historiales);
    }

    @Override
    public List<HistorialClinicoDTO> listarConCondicionesCronicas() {
        log.debug("Listando historiales con condiciones crónicas");

        List<HistorialClinico> historiales = historialClinicoRepository.findHistorialesConCondicionesCronicas();
        return historialClinicoMapper.toDTOList(historiales);
    }

    @Override
    public boolean existeHistorialPaciente(Long pacienteId) {
        log.debug("Verificando si existe historial para paciente ID: {}", pacienteId);

        return historialClinicoRepository.existsByPacienteId(pacienteId);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando historial clínico ID: {}", id);

        HistorialClinico historialClinico = historialClinicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Historial clínico no encontrado con ID: " + id
                ));

        // Soft delete
        historialClinico.setIsActive(false);
        historialClinicoRepository.save(historialClinico);

        log.info("Historial clínico eliminado (soft delete) exitosamente ID: {}", id);
    }
}
