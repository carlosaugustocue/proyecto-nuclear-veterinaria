package com.veterinaria.application.service.impl;

import com.veterinaria.application.dto.medical.CreateTratamientoRequest;
import com.veterinaria.application.dto.medical.TratamientoDTO;
import com.veterinaria.application.exception.BusinessRuleException;
import com.veterinaria.application.exception.ResourceNotFoundException;
import com.veterinaria.application.mapper.TratamientoMapper;
import com.veterinaria.application.repository.ConsultaRepository;
import com.veterinaria.application.repository.TratamientoRepository;
import com.veterinaria.application.service.TratamientoService;
import com.veterinaria.domain.entity.medical.Consulta;
import com.veterinaria.domain.entity.medical.Tratamiento;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementación del servicio de Tratamientos médicos.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TratamientoServiceImpl implements TratamientoService {

    private final TratamientoRepository tratamientoRepository;
    private final ConsultaRepository consultaRepository;
    private final TratamientoMapper tratamientoMapper;

    @Override
    @Transactional
    public TratamientoDTO crear(CreateTratamientoRequest request) {
        log.info("Creando nuevo tratamiento para consulta ID: {}", request.getConsultaId());

        // Validar consulta
        Consulta consulta = consultaRepository.findById(request.getConsultaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Consulta no encontrada con ID: " + request.getConsultaId()
                ));

        // Crear tratamiento usando mapper
        Tratamiento tratamiento = tratamientoMapper.toEntity(request);
        tratamiento.setConsulta(consulta);

        // Calcular fecha fin si es necesario
        if (tratamiento.getFechaFin() == null && tratamiento.getDuracionDias() != null) {
            tratamiento.calcularFechaFin();
        }

        // Guardar
        Tratamiento guardado = tratamientoRepository.save(tratamiento);

        log.info("Tratamiento creado exitosamente con ID: {}", guardado.getId());
        return tratamientoMapper.toDTO(guardado);
    }

    @Override
    public TratamientoDTO obtenerPorId(Long id) {
        log.debug("Buscando tratamiento con ID: {}", id);

        Tratamiento tratamiento = tratamientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tratamiento no encontrado con ID: " + id
                ));

        return tratamientoMapper.toDTO(tratamiento);
    }

    @Override
    public List<TratamientoDTO> listarPorPaciente(Long pacienteId) {
        log.debug("Listando tratamientos del paciente ID: {}", pacienteId);

        List<Tratamiento> tratamientos = tratamientoRepository.findByPacienteId(pacienteId);
        return tratamientoMapper.toDTOList(tratamientos);
    }

    @Override
    public List<TratamientoDTO> listarActivosPorPaciente(Long pacienteId) {
        log.debug("Listando tratamientos activos del paciente ID: {}", pacienteId);

        List<Tratamiento> tratamientos = tratamientoRepository.findTratamientosActivosByPaciente(pacienteId);
        return tratamientoMapper.toDTOList(tratamientos);
    }

    @Override
    public List<TratamientoDTO> listarVigentesPorPaciente(Long pacienteId) {
        log.debug("Listando tratamientos vigentes del paciente ID: {}", pacienteId);

        List<Tratamiento> tratamientos = tratamientoRepository.findTratamientosVigentesByPaciente(
                pacienteId,
                LocalDate.now()
        );
        return tratamientoMapper.toDTOList(tratamientos);
    }

    @Override
    public List<TratamientoDTO> listarPorTipo(String tipoTratamiento) {
        log.debug("Listando tratamientos por tipo: {}", tipoTratamiento);

        List<Tratamiento> tratamientos = tratamientoRepository.findByTipoTratamiento(tipoTratamiento);
        return tratamientoMapper.toDTOList(tratamientos);
    }

    @Override
    public List<TratamientoDTO> listarMedicamentosActivos() {
        log.debug("Listando medicamentos activos");

        List<Tratamiento> tratamientos = tratamientoRepository.findMedicamentosActivos();
        return tratamientoMapper.toDTOList(tratamientos);
    }

    @Override
    public List<TratamientoDTO> listarConSupervision() {
        log.debug("Listando tratamientos que requieren supervisión");

        List<Tratamiento> tratamientos = tratamientoRepository.findTratamientosConSupervision();
        return tratamientoMapper.toDTOList(tratamientos);
    }

    @Override
    public List<TratamientoDTO> listarProximosAFinalizar(int dias) {
        log.debug("Listando tratamientos próximos a finalizar (dentro de {} días)", dias);

        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = fechaInicio.plusDays(dias);

        List<Tratamiento> tratamientos = tratamientoRepository.findTratamientosProximosAFinalizar(
                fechaInicio,
                fechaFin
        );
        return tratamientoMapper.toDTOList(tratamientos);
    }

    @Override
    public List<TratamientoDTO> listarSuspendidos() {
        log.debug("Listando tratamientos suspendidos");

        List<Tratamiento> tratamientos = tratamientoRepository.findTratamientosSuspendidos();
        return tratamientoMapper.toDTOList(tratamientos);
    }

    @Override
    @Transactional
    public TratamientoDTO suspender(Long id) {
        log.info("Suspendiendo tratamiento ID: {}", id);

        Tratamiento tratamiento = tratamientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tratamiento no encontrado con ID: " + id
                ));

        if (!tratamiento.getActivo()) {
            throw new BusinessRuleException(
                    "El tratamiento ya está suspendido"
            );
        }

        // Suspender usando método de dominio
        tratamiento.suspender();

        Tratamiento suspendido = tratamientoRepository.save(tratamiento);

        log.info("Tratamiento suspendido exitosamente ID: {}", id);
        return tratamientoMapper.toDTO(suspendido);
    }

    @Override
    @Transactional
    public TratamientoDTO calcularFechaFin(Long id) {
        log.info("Calculando fecha fin para tratamiento ID: {}", id);

        Tratamiento tratamiento = tratamientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tratamiento no encontrado con ID: " + id
                ));

        if (tratamiento.getDuracionDias() == null) {
            throw new BusinessRuleException(
                    "El tratamiento no tiene duración en días configurada"
            );
        }

        // Calcular usando método de dominio
        tratamiento.calcularFechaFin();

        Tratamiento actualizado = tratamientoRepository.save(tratamiento);

        log.info("Fecha fin calculada para tratamiento ID: {}", id);
        return tratamientoMapper.toDTO(actualizado);
    }

    @Override
    public List<TratamientoDTO> listarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        log.debug("Listando tratamientos entre {} y {}", fechaInicio, fechaFin);

        List<Tratamiento> tratamientos = tratamientoRepository.findByFechaInicioBetween(
                fechaInicio,
                fechaFin
        );
        return tratamientoMapper.toDTOList(tratamientos);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando tratamiento ID: {}", id);

        Tratamiento tratamiento = tratamientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tratamiento no encontrado con ID: " + id
                ));

        // Soft delete
        tratamiento.setIsActive(false);
        tratamientoRepository.save(tratamiento);

        log.info("Tratamiento eliminado (soft delete) exitosamente ID: {}", id);
    }
}
