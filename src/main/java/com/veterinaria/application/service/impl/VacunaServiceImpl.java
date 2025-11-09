package com.veterinaria.application.service.impl;

import com.veterinaria.application.dto.medical.CreateVacunaRequest;
import com.veterinaria.application.dto.medical.VacunaDTO;
import com.veterinaria.application.exception.BusinessRuleException;
import com.veterinaria.application.exception.ResourceNotFoundException;
import com.veterinaria.application.mapper.VacunaMapper;
import com.veterinaria.application.repository.HistorialClinicoRepository;
import com.veterinaria.application.repository.UsuarioRepository;
import com.veterinaria.application.repository.VacunaRepository;
import com.veterinaria.application.service.VacunaService;
import com.veterinaria.domain.entity.medical.HistorialClinico;
import com.veterinaria.domain.entity.medical.Vacuna;
import com.veterinaria.domain.entity.security.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementación del servicio de Vacunas.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VacunaServiceImpl implements VacunaService {

    private final VacunaRepository vacunaRepository;
    private final HistorialClinicoRepository historialClinicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final VacunaMapper vacunaMapper;

    @Override
    @Transactional
    public VacunaDTO registrar(CreateVacunaRequest request) {
        log.info("Registrando nueva vacuna para historial clínico ID: {}", request.getHistorialClinicoId());

        // Validar historial clínico
        HistorialClinico historialClinico = historialClinicoRepository.findById(request.getHistorialClinicoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Historial clínico no encontrado con ID: " + request.getHistorialClinicoId()
                ));

        // Validar veterinario
        Usuario veterinario = usuarioRepository.findById(request.getVeterinarioId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Veterinario no encontrado con ID: " + request.getVeterinarioId()
                ));

        // Crear vacuna usando mapper
        Vacuna vacuna = vacunaMapper.toEntity(request);
        vacuna.setHistorialClinico(historialClinico);
        vacuna.setVeterinario(veterinario);

        // Calcular próxima dosis si se especificó intervalo
        if (request.getIntervaloDias() != null && request.getIntervaloDias() > 0) {
            vacuna.calcularProximaDosis();
        }

        // Guardar
        Vacuna guardada = vacunaRepository.save(vacuna);

        log.info("Vacuna registrada exitosamente con ID: {}", guardada.getId());
        return vacunaMapper.toDTO(guardada);
    }

    @Override
    public VacunaDTO obtenerPorId(Long id) {
        log.debug("Buscando vacuna con ID: {}", id);

        Vacuna vacuna = vacunaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vacuna no encontrada con ID: " + id
                ));

        return vacunaMapper.toDTO(vacuna);
    }

    @Override
    public List<VacunaDTO> listarPorPaciente(Long pacienteId) {
        log.debug("Listando vacunas del paciente ID: {}", pacienteId);

        List<Vacuna> vacunas = vacunaRepository.findByPacienteId(pacienteId);
        return vacunaMapper.toDTOList(vacunas);
    }

    @Override
    public List<VacunaDTO> listarPorTipo(String tipoVacuna) {
        log.debug("Listando vacunas por tipo: {}", tipoVacuna);

        List<Vacuna> vacunas = vacunaRepository.findByTipoVacuna(tipoVacuna);
        return vacunaMapper.toDTOList(vacunas);
    }

    @Override
    public List<VacunaDTO> listarAntirrábicas() {
        log.debug("Listando vacunas antirrábicas");

        List<Vacuna> vacunas = vacunaRepository.findVacunasAntirrábicas();
        return vacunaMapper.toDTOList(vacunas);
    }

    @Override
    public List<VacunaDTO> listarConRefuerzoProximo(int dias) {
        log.debug("Listando vacunas con refuerzo próximo (dentro de {} días)", dias);

        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = fechaInicio.plusDays(dias);

        List<Vacuna> vacunas = vacunaRepository.findVacunasConRefuerzoProximo(fechaInicio, fechaFin);
        return vacunaMapper.toDTOList(vacunas);
    }

    @Override
    public List<VacunaDTO> listarConRefuerzoVencido() {
        log.debug("Listando vacunas con refuerzo vencido");

        List<Vacuna> vacunas = vacunaRepository.findVacunasConRefuerzoVencido(LocalDate.now());
        return vacunaMapper.toDTOList(vacunas);
    }

    @Override
    public List<VacunaDTO> listarConSerieIncompleta() {
        log.debug("Listando vacunas con serie incompleta");

        List<Vacuna> vacunas = vacunaRepository.findVacunasConSerieIncompleta();
        return vacunaMapper.toDTOList(vacunas);
    }

    @Override
    public List<VacunaDTO> listarConSerieIncompletaPorPaciente(Long pacienteId) {
        log.debug("Listando vacunas con serie incompleta del paciente ID: {}", pacienteId);

        List<Vacuna> vacunas = vacunaRepository.findVacunasConSerieIncompletaByPaciente(pacienteId);
        return vacunaMapper.toDTOList(vacunas);
    }

    @Override
    public List<VacunaDTO> listarConReaccionesAdversas() {
        log.debug("Listando vacunas con reacciones adversas");

        List<Vacuna> vacunas = vacunaRepository.findVacunasConReaccionesAdversas();
        return vacunaMapper.toDTOList(vacunas);
    }

    @Override
    @Transactional
    public VacunaDTO calcularProximaDosis(Long id) {
        log.info("Calculando próxima dosis para vacuna ID: {}", id);

        Vacuna vacuna = vacunaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vacuna no encontrada con ID: " + id
                ));

        if (vacuna.getIntervaloDias() == null || vacuna.getIntervaloDias() <= 0) {
            throw new BusinessRuleException(
                    "La vacuna no tiene intervalo de días configurado"
            );
        }

        // Calcular usando método de dominio
        vacuna.calcularProximaDosis();

        Vacuna actualizada = vacunaRepository.save(vacuna);

        log.info("Próxima dosis calculada para vacuna ID: {}", id);
        return vacunaMapper.toDTO(actualizada);
    }

    @Override
    @Transactional
    public VacunaDTO completarSerie(Long id) {
        log.info("Completando serie de vacunación para vacuna ID: {}", id);

        Vacuna vacuna = vacunaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vacuna no encontrada con ID: " + id
                ));

        if (vacuna.getSerieCompleta()) {
            throw new BusinessRuleException(
                    "La serie de vacunación ya está completa"
            );
        }

        // Marcar usando método de dominio
        vacuna.completarSerie();

        Vacuna actualizada = vacunaRepository.save(vacuna);

        log.info("Serie de vacunación completada para vacuna ID: {}", id);
        return vacunaMapper.toDTO(actualizada);
    }

    @Override
    public List<VacunaDTO> listarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        log.debug("Listando vacunas entre {} y {}", fechaInicio, fechaFin);

        List<Vacuna> vacunas = vacunaRepository.findByFechaAplicacionBetween(fechaInicio, fechaFin);
        return vacunaMapper.toDTOList(vacunas);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando vacuna ID: {}", id);

        Vacuna vacuna = vacunaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vacuna no encontrada con ID: " + id
                ));

        // Soft delete
        vacuna.setIsActive(false);
        vacunaRepository.save(vacuna);

        log.info("Vacuna eliminada (soft delete) exitosamente ID: {}", id);
    }
}
