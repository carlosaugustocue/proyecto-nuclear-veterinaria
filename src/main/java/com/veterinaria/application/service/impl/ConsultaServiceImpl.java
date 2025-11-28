package com.veterinaria.application.service.impl;

import com.veterinaria.application.dto.medical.*;
import com.veterinaria.application.exception.BusinessRuleException;
import com.veterinaria.application.exception.ResourceNotFoundException;
import com.veterinaria.application.mapper.ConsultaMapper;
import com.veterinaria.application.mapper.DiagnosticoMapper;
import com.veterinaria.application.mapper.TratamientoMapper;
import com.veterinaria.application.repository.*;
import com.veterinaria.application.service.ConsultaService;
import com.veterinaria.domain.entity.appointments.Cita;
import com.veterinaria.domain.entity.medical.*;
import com.veterinaria.domain.entity.security.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementación del servicio de Consultas médicas.
 * Contiene la lógica de negocio para gestión de consultas.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsultaServiceImpl implements ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final HistorialClinicoRepository historialClinicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CitaRepository citaRepository;
    private final DiagnosticoRepository diagnosticoRepository;
    private final TratamientoRepository tratamientoRepository;
    private final ConsultaMapper consultaMapper;
    private final DiagnosticoMapper diagnosticoMapper;
    private final TratamientoMapper tratamientoMapper;

    @Override
    @Transactional
    public ConsultaDTO crear(CreateConsultaRequest request) {
        log.info("Creando nueva consulta para historial clínico ID: {}", request.getHistorialClinicoId());

        // 1. Validar historial clínico
        HistorialClinico historialClinico = historialClinicoRepository.findById(request.getHistorialClinicoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Historial clínico no encontrado con ID: " + request.getHistorialClinicoId()
                ));

        // 2. Validar veterinario
        Usuario veterinario = usuarioRepository.findById(request.getVeterinarioId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Veterinario no encontrado con ID: " + request.getVeterinarioId()
                ));

        // 3. Validar cita si se proporciona
        Cita cita = null;
        if (request.getCitaId() != null) {
            cita = citaRepository.findById(request.getCitaId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Cita no encontrada con ID: " + request.getCitaId()
                    ));
        }

        // 4. Crear la consulta usando mapper
        Consulta consulta = consultaMapper.toEntity(request);
        consulta.setHistorialClinico(historialClinico);
        consulta.setVeterinario(veterinario);
        consulta.setCita(cita);

        // 5. Guardar
        Consulta guardada = consultaRepository.save(consulta);

        log.info("Consulta creada exitosamente con ID: {}", guardada.getId());
        return consultaMapper.toDTO(guardada);
    }

    @Override
    @Transactional
    public ConsultaDTO actualizar(Long id, UpdateConsultaRequest request) {
        log.info("Actualizando consulta ID: {}", id);

        Consulta consulta = consultaRepository.findByIdAndActive(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Consulta no encontrada con ID: " + id
                ));

        // No permitir actualización si está finalizada
        if (consulta.estaFinalizada()) {
            throw new BusinessRuleException(
                    "No se puede modificar una consulta finalizada"
            );
        }

        // Actualizar usando mapper
        consultaMapper.updateEntityFromDTO(request, consulta);

        Consulta actualizada = consultaRepository.save(consulta);

        log.info("Consulta actualizada exitosamente ID: {}", id);
        return consultaMapper.toDTO(actualizada);
    }

    @Override
    @Transactional
    public ConsultaDTO finalizar(Long id, FinalizarConsultaRequest request) {
        log.info("Finalizando consulta ID: {}", id);

        Consulta consulta = consultaRepository.findByIdAndActive(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Consulta no encontrada con ID: " + id
                ));

        if (consulta.estaFinalizada()) {
            throw new BusinessRuleException("La consulta ya está finalizada");
        }

        // Actualizar datos finales
        if (request.getObservacionesFinales() != null) {
            String observaciones = consulta.getObservaciones() != null
                    ? consulta.getObservaciones() + "\n\n" + request.getObservacionesFinales()
                    : request.getObservacionesFinales();
            consulta.setObservaciones(observaciones);
        }

        if (request.getPlanTratamiento() != null) {
            consulta.setPlanTratamiento(request.getPlanTratamiento());
        }

        if (request.getPronostico() != null) {
            consulta.setPronostico(request.getPronostico());
        }

        if (request.getRequiereSeguimiento() != null) {
            consulta.setRequiereSeguimiento(request.getRequiereSeguimiento());
            consulta.setFechaSeguimiento(request.getFechaSeguimiento());
        }

        // Finalizar usando método de dominio
        consulta.finalizar();

        Consulta finalizada = consultaRepository.save(consulta);

        log.info("Consulta finalizada exitosamente ID: {}", id);
        return consultaMapper.toDTO(finalizada);
    }

    @Override
    public ConsultaDTO obtenerPorId(Long id) {
        log.debug("Buscando consulta con ID: {}", id);

        Consulta consulta = consultaRepository.findByIdAndActive(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Consulta no encontrada con ID: " + id
                ));

        return consultaMapper.toDTO(consulta);
    }

    @Override
    public List<ConsultaDTO> listarPorPaciente(Long pacienteId) {
        log.debug("Listando consultas del paciente ID: {}", pacienteId);

        List<Consulta> consultas = consultaRepository.findByPacienteId(pacienteId);
        return consultaMapper.toDTOList(consultas);
    }

    @Override
    public List<ConsultaDTO> listarPorHistorialClinico(Long historialClinicoId) {
        log.debug("Listando consultas del historial clínico ID: {}", historialClinicoId);

        List<Consulta> consultas = consultaRepository.findByHistorialClinicoId(historialClinicoId);
        return consultaMapper.toDTOList(consultas);
    }

    @Override
    public List<ConsultaDTO> listarPorVeterinario(Long veterinarioId) {
        log.debug("Listando consultas del veterinario ID: {}", veterinarioId);

        Usuario veterinario = usuarioRepository.findById(veterinarioId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Veterinario no encontrado con ID: " + veterinarioId
                ));

        List<Consulta> consultas = consultaRepository.findByVeterinario(veterinario);
        return consultaMapper.toDTOList(consultas);
    }

    @Override
    public List<ConsultaDTO> listarPorFecha(LocalDate fecha) {
        log.debug("Listando consultas de la fecha: {}", fecha);

        List<Consulta> consultas = consultaRepository.findByFecha(fecha);
        return consultaMapper.toDTOList(consultas);
    }

    @Override
    public List<ConsultaDTO> listarEnCurso() {
        log.debug("Listando consultas en curso");

        List<Consulta> consultas = consultaRepository.findConsultasEnCurso();
        return consultaMapper.toDTOList(consultas);
    }

    @Override
    public List<ConsultaDTO> listarConSeguimiento() {
        log.debug("Listando consultas que requieren seguimiento");

        List<Consulta> consultas = consultaRepository.findConsultasConSeguimiento();
        return consultaMapper.toDTOList(consultas);
    }

    @Override
    public ConsultaDTO obtenerUltimaConsultaPaciente(Long pacienteId) {
        log.debug("Obteniendo última consulta del paciente ID: {}", pacienteId);

        Consulta consulta = consultaRepository.findUltimaConsultaByPaciente(pacienteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontraron consultas para el paciente ID: " + pacienteId
                ));

        return consultaMapper.toDTO(consulta);
    }

    @Override
    @Transactional
    public DiagnosticoDTO agregarDiagnostico(Long consultaId, CreateDiagnosticoRequest request) {
        log.info("Agregando diagnóstico a consulta ID: {}", consultaId);

        Consulta consulta = consultaRepository.findByIdAndActive(consultaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Consulta no encontrada con ID: " + consultaId
                ));

        // Crear diagnóstico
        Diagnostico diagnostico = diagnosticoMapper.toEntity(request);
        diagnostico.setConsulta(consulta);

        // Agregar usando método de dominio
        consulta.agregarDiagnostico(diagnostico);

        // Guardar
        Diagnostico guardado = diagnosticoRepository.save(diagnostico);

        log.info("Diagnóstico agregado exitosamente con ID: {}", guardado.getId());
        return diagnosticoMapper.toDTO(guardado);
    }

    @Override
    @Transactional
    public TratamientoDTO agregarTratamiento(Long consultaId, CreateTratamientoRequest request) {
        log.info("Agregando tratamiento a consulta ID: {}", consultaId);

        Consulta consulta = consultaRepository.findByIdAndActive(consultaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Consulta no encontrada con ID: " + consultaId
                ));

        // Crear tratamiento
        Tratamiento tratamiento = tratamientoMapper.toEntity(request);
        tratamiento.setConsulta(consulta);

        // Calcular fecha fin si es necesario
        if (tratamiento.getFechaFin() == null && tratamiento.getDuracionDias() != null) {
            tratamiento.calcularFechaFin();
        }

        // Agregar usando método de dominio
        consulta.agregarTratamiento(tratamiento);

        // Guardar
        Tratamiento guardado = tratamientoRepository.save(tratamiento);

        log.info("Tratamiento agregado exitosamente con ID: {}", guardado.getId());
        return tratamientoMapper.toDTO(guardado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando consulta ID: {}", id);

        Consulta consulta = consultaRepository.findByIdAndActive(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Consulta no encontrada con ID: " + id
                ));

        // Soft delete
        consulta.setIsActive(false);
        consultaRepository.save(consulta);

        log.info("Consulta eliminada (soft delete) exitosamente ID: {}", id);
    }
}
