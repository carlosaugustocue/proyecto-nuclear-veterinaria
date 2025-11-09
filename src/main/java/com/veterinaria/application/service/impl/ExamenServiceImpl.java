package com.veterinaria.application.service.impl;

import com.veterinaria.application.dto.medical.CreateExamenRequest;
import com.veterinaria.application.dto.medical.ExamenDTO;
import com.veterinaria.application.dto.medical.RegistrarResultadosExamenRequest;
import com.veterinaria.application.exception.BusinessRuleException;
import com.veterinaria.application.exception.ResourceNotFoundException;
import com.veterinaria.application.mapper.ExamenMapper;
import com.veterinaria.application.repository.ConsultaRepository;
import com.veterinaria.application.repository.ExamenRepository;
import com.veterinaria.application.repository.HistorialClinicoRepository;
import com.veterinaria.application.repository.UsuarioRepository;
import com.veterinaria.application.service.ExamenService;
import com.veterinaria.domain.entity.medical.Consulta;
import com.veterinaria.domain.entity.medical.Examen;
import com.veterinaria.domain.entity.medical.HistorialClinico;
import com.veterinaria.domain.entity.security.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementación del servicio de Exámenes médicos.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExamenServiceImpl implements ExamenService {

    private final ExamenRepository examenRepository;
    private final HistorialClinicoRepository historialClinicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ConsultaRepository consultaRepository;
    private final ExamenMapper examenMapper;

    @Override
    @Transactional
    public ExamenDTO solicitar(CreateExamenRequest request) {
        log.info("Solicitando nuevo examen para historial clínico ID: {}", request.getHistorialClinicoId());

        // Validar historial clínico
        HistorialClinico historialClinico = historialClinicoRepository.findById(request.getHistorialClinicoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Historial clínico no encontrado con ID: " + request.getHistorialClinicoId()
                ));

        // Validar veterinario solicitante
        Usuario veterinario = usuarioRepository.findById(request.getVeterinarioSolicitanteId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Veterinario no encontrado con ID: " + request.getVeterinarioSolicitanteId()
                ));

        // Validar consulta si se proporciona
        Consulta consulta = null;
        if (request.getConsultaId() != null) {
            consulta = consultaRepository.findById(request.getConsultaId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Consulta no encontrada con ID: " + request.getConsultaId()
                    ));
        }

        // Crear examen usando mapper
        Examen examen = examenMapper.toEntity(request);
        examen.setHistorialClinico(historialClinico);
        examen.setVeterinarioSolicitante(veterinario);
        examen.setConsulta(consulta);

        // Guardar
        Examen guardado = examenRepository.save(examen);

        log.info("Examen solicitado exitosamente con ID: {}", guardado.getId());
        return examenMapper.toDTO(guardado);
    }

    @Override
    public ExamenDTO obtenerPorId(Long id) {
        log.debug("Buscando examen con ID: {}", id);

        Examen examen = examenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Examen no encontrado con ID: " + id
                ));

        return examenMapper.toDTO(examen);
    }

    @Override
    public List<ExamenDTO> listarPorPaciente(Long pacienteId) {
        log.debug("Listando exámenes del paciente ID: {}", pacienteId);

        List<Examen> examenes = examenRepository.findByPacienteId(pacienteId);
        return examenMapper.toDTOList(examenes);
    }

    @Override
    public List<ExamenDTO> listarPorConsulta(Long consultaId) {
        log.debug("Listando exámenes de la consulta ID: {}", consultaId);

        List<Examen> examenes = examenRepository.findByConsultaId(consultaId);
        return examenMapper.toDTOList(examenes);
    }

    @Override
    public List<ExamenDTO> listarPorTipo(String tipoExamen) {
        log.debug("Listando exámenes por tipo: {}", tipoExamen);

        List<Examen> examenes = examenRepository.findByTipoExamen(tipoExamen);
        return examenMapper.toDTOList(examenes);
    }

    @Override
    public List<ExamenDTO> listarPorEstado(String estado) {
        log.debug("Listando exámenes por estado: {}", estado);

        List<Examen> examenes = examenRepository.findByEstado(estado);
        return examenMapper.toDTOList(examenes);
    }

    @Override
    public List<ExamenDTO> listarPendientes() {
        log.debug("Listando exámenes pendientes");

        List<Examen> examenes = examenRepository.findExamenesPendientes();
        return examenMapper.toDTOList(examenes);
    }

    @Override
    public List<ExamenDTO> listarCompletados() {
        log.debug("Listando exámenes completados");

        List<Examen> examenes = examenRepository.findExamenesCompletados();
        return examenMapper.toDTOList(examenes);
    }

    @Override
    public List<ExamenDTO> listarConResultadosAnormales() {
        log.debug("Listando exámenes con resultados anormales");

        List<Examen> examenes = examenRepository.findExamenesConResultadosAnormales();
        return examenMapper.toDTOList(examenes);
    }

    @Override
    public List<ExamenDTO> listarUrgentes() {
        log.debug("Listando exámenes urgentes");

        List<Examen> examenes = examenRepository.findExamenesUrgentes();
        return examenMapper.toDTOList(examenes);
    }

    @Override
    public List<ExamenDTO> listarLaboratorio() {
        log.debug("Listando exámenes de laboratorio");

        List<Examen> examenes = examenRepository.findExamenesLaboratorio();
        return examenMapper.toDTOList(examenes);
    }

    @Override
    public List<ExamenDTO> listarImagenologia() {
        log.debug("Listando exámenes de imagenología");

        List<Examen> examenes = examenRepository.findExamenesImagenologia();
        return examenMapper.toDTOList(examenes);
    }

    @Override
    public List<ExamenDTO> listarDemorados(int diasLimite) {
        log.debug("Listando exámenes demorados (más de {} días)", diasLimite);

        LocalDate fechaLimite = LocalDate.now().minusDays(diasLimite);
        List<Examen> examenes = examenRepository.findExamenesDemorados(fechaLimite);
        return examenMapper.toDTOList(examenes);
    }

    @Override
    @Transactional
    public ExamenDTO registrarResultados(Long id, RegistrarResultadosExamenRequest request) {
        log.info("Registrando resultados para examen ID: {}", id);

        Examen examen = examenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Examen no encontrado con ID: " + id
                ));

        if (examen.estaCompletado()) {
            throw new BusinessRuleException(
                    "El examen ya tiene resultados registrados"
            );
        }

        // Actualizar datos de resultados
        if (request.getFechaRealizacion() != null) {
            examen.setFechaRealizacion(request.getFechaRealizacion());
        }

        // Registrar usando método de dominio
        examen.registrarResultados(request.getResultados());

        // Actualizar campos adicionales
        examen.setValoresReferencia(request.getValoresReferencia());
        examen.setInterpretacion(request.getInterpretacion());
        examen.setHallazgos(request.getHallazgos());
        examen.setArchivoRuta(request.getArchivoRuta());
        examen.setArchivoTipo(request.getArchivoTipo());

        if (request.getResultadoAnormal() != null) {
            examen.setResultadoAnormal(request.getResultadoAnormal());
        }

        Examen actualizado = examenRepository.save(examen);

        log.info("Resultados registrados exitosamente para examen ID: {}", id);
        return examenMapper.toDTO(actualizado);
    }

    @Override
    @Transactional
    public ExamenDTO marcarRealizado(Long id) {
        log.info("Marcando examen como realizado ID: {}", id);

        Examen examen = examenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Examen no encontrado con ID: " + id
                ));

        // Marcar usando método de dominio
        examen.marcarRealizado();

        Examen actualizado = examenRepository.save(examen);

        log.info("Examen marcado como realizado ID: {}", id);
        return examenMapper.toDTO(actualizado);
    }

    @Override
    @Transactional
    public ExamenDTO cancelar(Long id, String motivo) {
        log.info("Cancelando examen ID: {} con motivo: {}", id, motivo);

        Examen examen = examenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Examen no encontrado con ID: " + id
                ));

        if (examen.estaCompletado()) {
            throw new BusinessRuleException(
                    "No se puede cancelar un examen que ya está completado"
            );
        }

        // Cancelar usando método de dominio
        examen.cancelar(motivo);

        Examen cancelado = examenRepository.save(examen);

        log.info("Examen cancelado exitosamente ID: {}", id);
        return examenMapper.toDTO(cancelado);
    }

    @Override
    public List<ExamenDTO> listarPorRangoFechasSolicitud(LocalDate fechaInicio, LocalDate fechaFin) {
        log.debug("Listando exámenes solicitados entre {} y {}", fechaInicio, fechaFin);

        List<Examen> examenes = examenRepository.findByFechaSolicitudBetween(fechaInicio, fechaFin);
        return examenMapper.toDTOList(examenes);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando examen ID: {}", id);

        Examen examen = examenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Examen no encontrado con ID: " + id
                ));

        // Soft delete
        examen.setIsActive(false);
        examenRepository.save(examen);

        log.info("Examen eliminado (soft delete) exitosamente ID: {}", id);
    }
}
