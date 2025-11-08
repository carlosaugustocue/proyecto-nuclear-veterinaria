package com.veterinaria.application.service.impl;

import com.veterinaria.application.dto.cita.*;
import com.veterinaria.application.exception.BusinessRuleException;
import com.veterinaria.application.exception.ResourceNotFoundException;
import com.veterinaria.application.mapper.CitaMapper;
import com.veterinaria.application.repository.CitaRepository;
import com.veterinaria.application.repository.ClienteRepository;
import com.veterinaria.application.repository.PacienteRepository;
import com.veterinaria.application.repository.TipoServicioRepository;
import com.veterinaria.application.repository.UsuarioRepository;
import com.veterinaria.application.service.CitaService;
import com.veterinaria.application.service.validator.ValidadorDisponibilidad;
import com.veterinaria.domain.entity.appointments.Cita;
import com.veterinaria.domain.entity.appointments.TipoServicio;
import com.veterinaria.domain.entity.patients.Cliente;
import com.veterinaria.domain.entity.patients.Paciente;
import com.veterinaria.domain.entity.security.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementación del servicio de Citas.
 * Contiene la lógica de negocio para gestión de citas médicas.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PacienteRepository pacienteRepository;
    private final ClienteRepository clienteRepository;
    private final TipoServicioRepository tipoServicioRepository;
    private final ValidadorDisponibilidad validadorDisponibilidad;
    private final CitaMapper citaMapper;

    @Override
    @Transactional
    public CitaDTO crear(CreateCitaRequest request) {
        log.info("Creando nueva cita para paciente ID: {} en fecha: {}",
                request.getPacienteId(), request.getFecha());

        // 1. Buscar y validar veterinario
        Usuario veterinario = usuarioRepository.findById(request.getVeterinarioId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Veterinario no encontrado con ID: " + request.getVeterinarioId()
                ));

        // 2. Buscar y validar paciente
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Paciente no encontrado con ID: " + request.getPacienteId()
                ));

        // 3. Buscar y validar cliente
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente no encontrado con ID: " + request.getClienteId()
                ));

        // 4. Buscar y validar tipo de servicio
        TipoServicio tipoServicio = tipoServicioRepository.findById(request.getTipoServicioId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tipo de servicio no encontrado con ID: " + request.getTipoServicioId()
                ));

        if (!tipoServicio.estaDisponible()) {
            throw new BusinessRuleException(
                    "El tipo de servicio '" + tipoServicio.getNombre() + "' no está disponible"
            );
        }

        // 5. Validar disponibilidad del veterinario
        boolean disponible = validadorDisponibilidad.verificarDisponibilidadVeterinario(
                veterinario,
                request.getFecha(),
                request.getHora(),
                tipoServicio.getDuracionEstimada(),
                null // Es nueva cita, no excluir ninguna
        );

        if (!disponible) {
            throw new BusinessRuleException(
                    "El veterinario no está disponible en la fecha y hora solicitadas"
            );
        }

        // 6. Crear la cita
        Cita cita = Cita.builder()
                .fecha(request.getFecha())
                .hora(request.getHora())
                .motivo(request.getMotivo())
                .veterinario(veterinario)
                .paciente(paciente)
                .cliente(cliente)
                .tipoServicio(tipoServicio)
                .notas(request.getNotas())
                .duracionEstimada(tipoServicio.getDuracionEstimada())
                .build();

        // 7. Guardar en base de datos
        Cita guardada = citaRepository.save(cita);

        log.info("Cita creada exitosamente con ID: {}", guardada.getId());
        return citaMapper.toDTO(guardada);
    }

    @Override
    @Transactional
    public CitaDTO actualizar(Long id, UpdateCitaRequest request) {
        log.info("Actualizando cita ID: {}", id);

        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cita no encontrada con ID: " + id
                ));

        // Solo permitir actualización si la cita es modificable
        if (!cita.esModificable()) {
            throw new BusinessRuleException(
                    "No se puede modificar una cita en estado " + cita.getEstado().getNombre()
            );
        }

        // Actualizar campos permitidos
        citaMapper.updateEntityFromDTO(request, cita);

        Cita actualizada = citaRepository.save(cita);

        log.info("Cita actualizada exitosamente ID: {}", id);
        return citaMapper.toDTO(actualizada);
    }

    @Override
    public CitaDTO obtenerPorId(Long id) {
        log.debug("Buscando cita con ID: {}", id);

        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cita no encontrada con ID: " + id
                ));

        return citaMapper.toDTO(cita);
    }

    @Override
    public List<CitaDTO> listarTodas() {
        log.debug("Listando todas las citas activas");

        List<Cita> citas = citaRepository.findAll();
        return citaMapper.toDTOList(citas);
    }

    @Override
    public List<CitaDTO> listarPorFecha(LocalDate fecha) {
        log.debug("Listando citas por fecha: {}", fecha);

        List<Cita> citas = citaRepository.findByFecha(fecha);
        return citaMapper.toDTOList(citas);
    }

    @Override
    public List<CitaDTO> listarPorVeterinarioYFecha(Long veterinarioId, LocalDate fecha) {
        log.debug("Listando citas del veterinario ID: {} en fecha: {}", veterinarioId, fecha);

        Usuario veterinario = usuarioRepository.findById(veterinarioId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Veterinario no encontrado con ID: " + veterinarioId
                ));

        List<Cita> citas = citaRepository.findByVeterinarioAndFecha(veterinario, fecha);
        return citaMapper.toDTOList(citas);
    }

    @Override
    public List<CitaDTO> listarPorPaciente(Long pacienteId) {
        log.debug("Listando citas del paciente ID: {}", pacienteId);

        List<Cita> citas = citaRepository.findByPacienteId(pacienteId);
        return citaMapper.toDTOList(citas);
    }

    @Override
    public List<CitaDTO> listarPorCliente(Long clienteId) {
        log.debug("Listando citas del cliente ID: {}", clienteId);

        List<Cita> citas = citaRepository.findByClienteId(clienteId);
        return citaMapper.toDTOList(citas);
    }

    @Override
    public List<CitaDTO> listarProximasCitasPaciente(Long pacienteId) {
        log.debug("Listando próximas citas del paciente ID: {}", pacienteId);

        List<Cita> citas = citaRepository.findProximasCitasByPaciente(pacienteId, LocalDate.now());
        return citaMapper.toDTOList(citas);
    }

    @Override
    @Transactional
    public CitaDTO confirmar(Long id) {
        log.info("Confirmando cita ID: {}", id);

        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cita no encontrada con ID: " + id
                ));

        // Usar método de la entidad (lógica de dominio)
        try {
            cita.confirmar();
        } catch (IllegalStateException e) {
            throw new BusinessRuleException(e.getMessage());
        }

        Cita confirmada = citaRepository.save(cita);

        log.info("Cita confirmada exitosamente ID: {}", id);
        return citaMapper.toDTO(confirmada);
    }

    @Override
    @Transactional
    public CitaDTO cancelar(Long id, CancelarCitaRequest request) {
        log.info("Cancelando cita ID: {} con motivo: {}", id, request.getMotivoCancelacion());

        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cita no encontrada con ID: " + id
                ));

        // Usar método de la entidad (lógica de dominio)
        try {
            cita.cancelar(request.getMotivoCancelacion());
        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new BusinessRuleException(e.getMessage());
        }

        Cita cancelada = citaRepository.save(cita);

        log.info("Cita cancelada exitosamente ID: {}", id);
        return citaMapper.toDTO(cancelada);
    }

    @Override
    @Transactional
    public CitaDTO iniciar(Long id) {
        log.info("Iniciando atención de cita ID: {}", id);

        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cita no encontrada con ID: " + id
                ));

        // Usar método de la entidad
        try {
            cita.iniciar();
        } catch (IllegalStateException e) {
            throw new BusinessRuleException(e.getMessage());
        }

        Cita iniciada = citaRepository.save(cita);

        log.info("Cita iniciada exitosamente ID: {}", id);
        return citaMapper.toDTO(iniciada);
    }

    @Override
    @Transactional
    public CitaDTO completar(Long id) {
        log.info("Completando cita ID: {}", id);

        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cita no encontrada con ID: " + id
                ));

        // Usar método de la entidad
        try {
            cita.completar();
        } catch (IllegalStateException e) {
            throw new BusinessRuleException(e.getMessage());
        }

        Cita completada = citaRepository.save(cita);

        log.info("Cita completada exitosamente ID: {}", id);
        return citaMapper.toDTO(completada);
    }

    @Override
    @Transactional
    public CitaDTO reagendar(Long id, ReagendarCitaRequest request) {
        log.info("Reagendando cita ID: {} a fecha: {} hora: {}",
                id, request.getNuevaFecha(), request.getNuevaHora());

        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cita no encontrada con ID: " + id
                ));

        // Validar disponibilidad en el nuevo horario
        boolean disponible = validadorDisponibilidad.verificarDisponibilidadVeterinario(
                cita.getVeterinario(),
                request.getNuevaFecha(),
                request.getNuevaHora(),
                cita.getDuracionEstimada(),
                id // Excluir la cita actual de la validación
        );

        if (!disponible) {
            throw new BusinessRuleException(
                    "El veterinario no está disponible en la nueva fecha y hora"
            );
        }

        // Usar método de la entidad
        try {
            cita.reagendar(request.getNuevaFecha(), request.getNuevaHora());
        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new BusinessRuleException(e.getMessage());
        }

        // Agregar nota del reagendamiento
        String notaReagendamiento = String.format("\n[REAGENDADA] %s", request.getMotivoReagendamiento());
        cita.setNotas(cita.getNotas() != null ? cita.getNotas() + notaReagendamiento : notaReagendamiento);

        Cita reagendada = citaRepository.save(cita);

        log.info("Cita reagendada exitosamente ID: {}", id);
        return citaMapper.toDTO(reagendada);
    }

    @Override
    public List<String> obtenerHorariosDisponibles(Long veterinarioId, LocalDate fecha, Integer duracionMinutos) {
        log.debug("Obteniendo horarios disponibles del veterinario ID: {} para fecha: {}",
                veterinarioId, fecha);

        Usuario veterinario = usuarioRepository.findById(veterinarioId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Veterinario no encontrado con ID: " + veterinarioId
                ));

        return validadorDisponibilidad.obtenerHorariosDisponibles(veterinario, fecha, duracionMinutos);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando cita ID: {}", id);

        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cita no encontrada con ID: " + id
                ));

        // Soft delete
        cita.setIsActive(false);
        citaRepository.save(cita);

        log.info("Cita eliminada (soft delete) exitosamente ID: {}", id);
    }
}
