package com.veterinaria.application.service.impl;

import com.veterinaria.application.dto.appointments.*;
import com.veterinaria.application.exception.BusinessRuleException;
import com.veterinaria.application.exception.ResourceNotFoundException;
import com.veterinaria.application.mapper.CitaMapper;
import com.veterinaria.application.repository.CitaRepository;
import com.veterinaria.application.repository.ClienteRepository;
import com.veterinaria.application.repository.PacienteRepository;
import com.veterinaria.application.repository.TipoServicioRepository;
import com.veterinaria.application.repository.UsuarioRepository;
import com.veterinaria.application.service.CitaService;
import com.veterinaria.application.service.notification.EmailService;
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
    private final EmailService emailService;

    @Override
    @Transactional
    public CitaDTO crear(CreateCitaRequest request) {
        log.info("Creando nueva cita - Fecha recibida: {} (tipo: {}), Hora recibida: {} (tipo: {})",
                request.getFecha(), request.getFecha() != null ? request.getFecha().getClass().getSimpleName() : "null",
                request.getHora(), request.getHora() != null ? request.getHora().getClass().getSimpleName() : "null");
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
        log.info("Validando disponibilidad - Veterinario ID: {}, Fecha: {}, Hora: {}, Tipo Servicio: {} (duración: {} min)",
                veterinario.getId(), request.getFecha(), request.getHora(), 
                tipoServicio.getNombre(), tipoServicio.getDuracionEstimada());
        
        boolean disponible = validadorDisponibilidad.verificarDisponibilidadVeterinario(
                veterinario,
                request.getFecha(),
                request.getHora(),
                tipoServicio.getDuracionEstimada(),
                null // Es nueva cita, no excluir ninguna
        );

        if (!disponible) {
            log.error("Disponibilidad rechazada - Veterinario ID: {}, Fecha: {}, Hora: {}, Duración: {} min",
                    veterinario.getId(), request.getFecha(), request.getHora(), tipoServicio.getDuracionEstimada());
            throw new BusinessRuleException(
                    "El veterinario no está disponible en la fecha y hora solicitadas. " +
                    "Verifique que la fecha sea futura, el horario esté dentro del horario hábil " +
                    "(08:00-18:00) y no haya conflictos con otras citas."
            );
        }

        // 6. Generar token único de confirmación
        String tokenConfirmacion = generarTokenConfirmacion();

        // 7. Crear la cita
        log.info("Construyendo entidad Cita - Fecha: {}, Hora: {}", request.getFecha(), request.getHora());
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
                .tokenConfirmacion(tokenConfirmacion)
                .build();

        log.info("Entidad Cita creada - Fecha: {}, Hora: {}", cita.getFecha(), cita.getHora());

        // 8. Guardar en base de datos
        Cita guardada = citaRepository.save(cita);
        log.info("Cita guardada en BD - ID: {}, Fecha guardada: {}, Hora guardada: {}", 
                guardada.getId(), guardada.getFecha(), guardada.getHora());

        // 9. Enviar notificación por email al cliente con link de confirmación
        try {
            emailService.notificarCitaCreada(
                    cliente.getEmail(),
                    cliente.getNombreCompleto(),
                    paciente.getNombre(),
                    guardada.getFecha(),
                    guardada.getHora(),
                    tipoServicio.getNombre(),
                    tokenConfirmacion
            );
            log.info("✓ Solicitud de envío de email de confirmación procesada para: {}", cliente.getEmail());
        } catch (Exception e) {
            log.error("❌ Error al enviar email de confirmación de cita a {}: {}", cliente.getEmail(), e.getMessage());
            log.error("   Tipo de error: {}", e.getClass().getSimpleName());
            if (e.getCause() != null) {
                log.error("   Causa: {}", e.getCause().getMessage());
            }
            // No interrumpir el flujo si falla el email, pero registrar el error completo
            log.debug("Stack trace completo del error de email:", e);
        }

        log.info("Cita creada exitosamente con ID: {}", guardada.getId());
        return citaMapper.toDTO(guardada);
    }

    /**
     * Genera un token único para confirmación de cita
     * @return token generado
     */
    private String generarTokenConfirmacion() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
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

        // Notificar al veterinario que la cita fue confirmada
        try {
            emailService.notificarVeterinarioCitaConfirmada(
                    cita.getVeterinario().getEmail(),
                    cita.getVeterinario().getNombreCompleto(),
                    cita.getCliente().getNombreCompleto(),
                    cita.getPaciente().getNombre(),
                    cita.getFecha(),
                    cita.getHora()
            );
            log.info("Email de notificación enviado al veterinario: {}", cita.getVeterinario().getEmail());
        } catch (Exception e) {
            log.error("Error al enviar email de notificación al veterinario: {}", e.getMessage());
        }

        log.info("Cita confirmada exitosamente ID: {}", id);
        return citaMapper.toDTO(confirmada);
    }

    @Override
    @Transactional
    public CitaDTO confirmarPorToken(String token) {
        log.info("Confirmando cita por token: {}", token);

        Cita cita = citaRepository.findByTokenConfirmacion(token)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró una cita con el token de confirmación proporcionado"
                ));

        // Validar que la cita no esté vencida (opcional: solo permitir confirmación hasta X horas antes)
        if (cita.getFecha().isBefore(LocalDate.now())) {
            throw new BusinessRuleException(
                    "No se puede confirmar una cita que ya pasó"
            );
        }

        // Usar método de la entidad (lógica de dominio)
        try {
            cita.confirmar();
        } catch (IllegalStateException e) {
            throw new BusinessRuleException(e.getMessage());
        }

        Cita confirmada = citaRepository.save(cita);

        // Notificar al veterinario que la cita fue confirmada
        try {
            emailService.notificarVeterinarioCitaConfirmada(
                    cita.getVeterinario().getEmail(),
                    cita.getVeterinario().getNombreCompleto(),
                    cita.getCliente().getNombreCompleto(),
                    cita.getPaciente().getNombre(),
                    cita.getFecha(),
                    cita.getHora()
            );
            log.info("Email de notificación enviado al veterinario: {}", cita.getVeterinario().getEmail());
        } catch (Exception e) {
            log.error("Error al enviar email de notificación al veterinario: {}", e.getMessage());
        }

        log.info("Cita confirmada exitosamente por token");
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

        // Enviar notificación por email al cliente
        try {
            emailService.notificarCitaCancelada(
                    cita.getCliente().getEmail(),
                    cita.getCliente().getNombreCompleto(),
                    cita.getPaciente().getNombre(),
                    cita.getFecha(),
                    request.getMotivoCancelacion()
            );
            log.info("Email de cancelación enviado a: {}", cita.getCliente().getEmail());
        } catch (Exception e) {
            log.error("Error al enviar email de cancelación: {}", e.getMessage());
        }

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
