package com.veterinaria.application.service.impl;

import com.veterinaria.application.dto.medical.CreateDiagnosticoRequest;
import com.veterinaria.application.dto.medical.DiagnosticoDTO;
import com.veterinaria.application.exception.ResourceNotFoundException;
import com.veterinaria.application.mapper.DiagnosticoMapper;
import com.veterinaria.application.repository.ConsultaRepository;
import com.veterinaria.application.repository.DiagnosticoRepository;
import com.veterinaria.application.service.DiagnosticoService;
import com.veterinaria.domain.entity.medical.Consulta;
import com.veterinaria.domain.entity.medical.Diagnostico;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementación del servicio de Diagnósticos médicos.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiagnosticoServiceImpl implements DiagnosticoService {

    private final DiagnosticoRepository diagnosticoRepository;
    private final ConsultaRepository consultaRepository;
    private final DiagnosticoMapper diagnosticoMapper;

    @Override
    @Transactional
    public DiagnosticoDTO crear(CreateDiagnosticoRequest request) {
        log.info("Creando nuevo diagnóstico para consulta ID: {}", request.getConsultaId());

        // Validar consulta
        Consulta consulta = consultaRepository.findById(request.getConsultaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Consulta no encontrada con ID: " + request.getConsultaId()
                ));

        // Crear diagnóstico usando mapper
        Diagnostico diagnostico = diagnosticoMapper.toEntity(request);
        diagnostico.setConsulta(consulta);

        // Guardar
        Diagnostico guardado = diagnosticoRepository.save(diagnostico);

        log.info("Diagnóstico creado exitosamente con ID: {}", guardado.getId());
        return diagnosticoMapper.toDTO(guardado);
    }

    @Override
    public DiagnosticoDTO obtenerPorId(Long id) {
        log.debug("Buscando diagnóstico con ID: {}", id);

        Diagnostico diagnostico = diagnosticoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Diagnóstico no encontrado con ID: " + id
                ));

        return diagnosticoMapper.toDTO(diagnostico);
    }

    @Override
    public List<DiagnosticoDTO> listarPorPaciente(Long pacienteId) {
        log.debug("Listando diagnósticos del paciente ID: {}", pacienteId);

        List<Diagnostico> diagnosticos = diagnosticoRepository.findByPacienteId(pacienteId);
        return diagnosticoMapper.toDTOList(diagnosticos);
    }

    @Override
    public List<DiagnosticoDTO> listarPorTipo(String tipoDiagnostico) {
        log.debug("Listando diagnósticos por tipo: {}", tipoDiagnostico);

        List<Diagnostico> diagnosticos = diagnosticoRepository.findByTipoDiagnostico(tipoDiagnostico);
        return diagnosticoMapper.toDTOList(diagnosticos);
    }

    @Override
    public List<DiagnosticoDTO> listarPorGravedad(String gravedad) {
        log.debug("Listando diagnósticos por gravedad: {}", gravedad);

        List<Diagnostico> diagnosticos = diagnosticoRepository.findByGravedad(gravedad);
        return diagnosticoMapper.toDTOList(diagnosticos);
    }

    @Override
    public List<DiagnosticoDTO> listarGravesOCriticos() {
        log.debug("Listando diagnósticos graves o críticos");

        List<Diagnostico> diagnosticos = diagnosticoRepository.findDiagnosticosGravesOCriticos();
        return diagnosticoMapper.toDTOList(diagnosticos);
    }

    @Override
    public List<DiagnosticoDTO> listarPrincipales() {
        log.debug("Listando diagnósticos principales");

        List<Diagnostico> diagnosticos = diagnosticoRepository.findDiagnosticosPrincipales();
        return diagnosticoMapper.toDTOList(diagnosticos);
    }

    @Override
    public List<DiagnosticoDTO> listarPorCodigoCie10(String codigoCie10) {
        log.debug("Listando diagnósticos por código CIE-10: {}", codigoCie10);

        List<Diagnostico> diagnosticos = diagnosticoRepository.findByCodigoCie10(codigoCie10);
        return diagnosticoMapper.toDTOList(diagnosticos);
    }

    @Override
    public List<DiagnosticoDTO> buscarPorDescripcion(String descripcion) {
        log.debug("Buscando diagnósticos por descripción: {}", descripcion);

        List<Diagnostico> diagnosticos = diagnosticoRepository.findByDescripcionContaining(descripcion);
        return diagnosticoMapper.toDTOList(diagnosticos);
    }

    @Override
    public List<DiagnosticoDTO> listarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        log.debug("Listando diagnósticos entre {} y {}", fechaInicio, fechaFin);

        List<Diagnostico> diagnosticos = diagnosticoRepository.findByFechaBetween(fechaInicio, fechaFin);
        return diagnosticoMapper.toDTOList(diagnosticos);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando diagnóstico ID: {}", id);

        Diagnostico diagnostico = diagnosticoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Diagnóstico no encontrado con ID: " + id
                ));

        // Soft delete
        diagnostico.setIsActive(false);
        diagnosticoRepository.save(diagnostico);

        log.info("Diagnóstico eliminado (soft delete) exitosamente ID: {}", id);
    }
}
