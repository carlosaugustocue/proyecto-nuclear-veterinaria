package com.veterinaria.application.service.validator;

import com.veterinaria.application.repository.CitaRepository;
import com.veterinaria.domain.entity.appointments.Cita;
import com.veterinaria.domain.entity.security.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Validador de disponibilidad para citas.
 * Verifica conflictos de horarios, disponibilidad de veterinarios y horarios hábiles.
 *
 * Patrón de Diseño: Validator Pattern
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ValidadorDisponibilidad {

    private final CitaRepository citaRepository;

    // Configuración de horarios hábiles (puede venir de properties o base de datos)
    @Value("${app.horario.apertura:08:00}")
    private String horarioApertura;

    @Value("${app.horario.cierre:18:00}")
    private String horarioCierre;

    @Value("${app.horario.duracion-minima-cita:15}")
    private Integer duracionMinimaCita;

    /**
     * Verifica la disponibilidad completa del veterinario en una fecha y hora
     * @param veterinario el veterinario
     * @param fecha fecha de la cita
     * @param horaInicio hora de inicio
     * @param duracionMinutos duración en minutos
     * @param citaIdExcluir ID de cita a excluir (para reagendamiento), null si es nueva
     * @return true si está disponible
     */
    public boolean verificarDisponibilidadVeterinario(Usuario veterinario, LocalDate fecha,
                                                       LocalTime horaInicio, Integer duracionMinutos,
                                                       Long citaIdExcluir) {
        log.info("Verificando disponibilidad - Veterinario: {}, Fecha: {}, Hora: {}, Duración: {} min", 
                veterinario.getId(), fecha, horaInicio, duracionMinutos);
        
        // 1. Verificar que la fecha no sea pasada
        LocalDate hoy = LocalDate.now();
        log.info("Validando fecha - Fecha solicitada: {}, Fecha actual: {}", fecha, hoy);
        
        if (fecha.isBefore(hoy)) {
            log.error("❌ VALIDACIÓN FALLIDA: Fecha pasada: {} (hoy es: {})", fecha, hoy);
            return false;
        }
        
        // Permitir citas para hoy si la hora es futura
        if (fecha.equals(hoy)) {
            LocalTime ahora = LocalTime.now();
            log.info("Fecha es hoy - Hora solicitada: {}, Hora actual: {}", horaInicio, ahora);
            if (horaInicio.isBefore(ahora)) {
                log.error("❌ VALIDACIÓN FALLIDA: Hora pasada para hoy: {} (hora actual: {})", horaInicio, ahora);
                return false;
            }
        }
        
        log.info("✓ Fecha válida");

        // 2. Verificar horario hábil
        if (!verificarHorarioHabil(fecha, horaInicio, duracionMinutos)) {
            log.warn("Fuera de horario hábil: {} {} (duración: {} min)", fecha, horaInicio, duracionMinutos);
            return false;
        }

        // 3. Verificar conflictos con otras citas
        log.info("Buscando conflictos - Veterinario ID: {}, Fecha: {}, Hora inicio: {}, Duración: {} min", 
                veterinario.getId(), fecha, horaInicio, duracionMinutos);
        
        List<Cita> conflictos = verificarConflictos(veterinario, fecha, horaInicio,
                duracionMinutos, citaIdExcluir);

        if (!conflictos.isEmpty()) {
            log.error("❌ CONFLICTO ENCONTRADO: {} cita(s) en conflicto - IDs: {}", 
                    conflictos.size(), 
                    conflictos.stream().map(c -> String.format("ID:%d (Fecha:%s Hora:%s Duración:%d)", 
                            c.getId(), c.getFecha(), c.getHora(), c.getDuracionEstimada())).toList());
            return false;
        }

        log.info("✓ Sin conflictos de horario");

        log.info("Disponibilidad confirmada para veterinario {} en {} a las {}", 
                veterinario.getId(), fecha, horaInicio);
        return true;
    }

    /**
     * Verifica si la fecha y hora están dentro del horario hábil
     * @param fecha fecha a verificar
     * @param horaInicio hora de inicio
     * @param duracionMinutos duración en minutos
     * @return true si está en horario hábil
     */
    public boolean verificarHorarioHabil(LocalDate fecha, LocalTime horaInicio, Integer duracionMinutos) {
        // Verificar si es día laborable (lunes a sábado)
        DayOfWeek diaSemana = fecha.getDayOfWeek();
        if (diaSemana == DayOfWeek.SUNDAY) {
            log.warn("No se atiende los domingos - Fecha: {}", fecha);
            return false;
        }

        // Parsear horarios de apertura y cierre
        LocalTime apertura = LocalTime.parse(horarioApertura);
        LocalTime cierre = LocalTime.parse(horarioCierre);

        log.info("Verificando horario hábil - Apertura: {}, Cierre: {}, Hora inicio: {}, Duración: {} min", 
                apertura, cierre, horaInicio, duracionMinutos);

        // Verificar que la cita inicie dentro del horario
        if (horaInicio.isBefore(apertura)) {
            log.warn("Hora {} antes de la apertura {} - Fecha: {}", horaInicio, apertura, fecha);
            return false;
        }

        // Verificar que la cita termine antes del cierre
        LocalTime horaFin = horaInicio.plusMinutes(duracionMinutos);
        log.info("Calculando hora fin - Hora inicio: {}, Duración: {} min, Hora fin calculada: {}, Cierre: {}", 
                horaInicio, duracionMinutos, horaFin, cierre);
        
        if (horaFin.isAfter(cierre)) {
            log.error("❌ VALIDACIÓN FALLIDA: Hora fin {} después del cierre {} - Hora inicio: {}, Duración: {} min, Fecha: {}", 
                    horaFin, cierre, horaInicio, duracionMinutos, fecha);
            return false;
        }

        log.info("✓ Horario hábil válido - Hora inicio: {}, Hora fin: {}", horaInicio, horaFin);
        return true;
    }

    /**
     * Verifica conflictos de horario con otras citas del veterinario
     * @param veterinario el veterinario
     * @param fecha fecha de la cita
     * @param horaInicio hora de inicio
     * @param duracionMinutos duración en minutos
     * @param citaIdExcluir ID de cita a excluir (null si es nueva)
     * @return lista de citas en conflicto
     */
    public List<Cita> verificarConflictos(Usuario veterinario, LocalDate fecha,
                                            LocalTime horaInicio, Integer duracionMinutos,
                                            Long citaIdExcluir) {
        Long idExcluir = citaIdExcluir != null ? citaIdExcluir : -1L;
        LocalTime horaFin = horaInicio.plusMinutes(duracionMinutos);

        return citaRepository.findConflictosHorario(veterinario, fecha, horaInicio, horaFin, idExcluir);
    }

    /**
     * Obtiene los horarios disponibles del veterinario en una fecha
     * @param veterinario el veterinario
     * @param fecha fecha a consultar
     * @param duracionMinutos duración de la cita a agendar
     * @return lista de horarios disponibles (strings HH:mm)
     */
    public List<String> obtenerHorariosDisponibles(Usuario veterinario, LocalDate fecha, Integer duracionMinutos) {
        List<String> horariosDisponibles = new ArrayList<>();

        // Validar que sea día hábil
        if (!verificarHorarioHabil(fecha, LocalTime.parse(horarioApertura), duracionMinutos)) {
            return horariosDisponibles;
        }

        // Obtener todas las citas del veterinario en esa fecha
        List<Cita> citasDelDia = citaRepository.findCitasActivasByVeterinarioAndFecha(veterinario, fecha);

        // Generar slots de tiempo cada 15 minutos (configurable)
        LocalTime apertura = LocalTime.parse(horarioApertura);
        LocalTime cierre = LocalTime.parse(horarioCierre);
        LocalTime horaActual = apertura;

        while (horaActual.plusMinutes(duracionMinutos).isBefore(cierre) ||
               horaActual.plusMinutes(duracionMinutos).equals(cierre)) {

            // Verificar si este horario tiene conflicto
            LocalTime finalHoraActual = horaActual;
            boolean tieneConflicto = citasDelDia.stream()
                    .anyMatch(cita -> tieneConflictoHorario(cita, finalHoraActual, duracionMinutos));

            if (!tieneConflicto) {
                horariosDisponibles.add(horaActual.toString());
            }

            horaActual = horaActual.plusMinutes(duracionMinimaCita);
        }

        return horariosDisponibles;
    }

    /**
     * Verifica si una hora propuesta tiene conflicto con una cita existente
     * @param citaExistente cita existente
     * @param horaPropuesta hora propuesta
     * @param duracionPropuesta duración propuesta en minutos
     * @return true si hay conflicto
     */
    private boolean tieneConflictoHorario(Cita citaExistente, LocalTime horaPropuesta, Integer duracionPropuesta) {
        LocalTime inicioPropuesta = horaPropuesta;
        LocalTime finPropuesta = horaPropuesta.plusMinutes(duracionPropuesta);

        LocalTime inicioExistente = citaExistente.getHora();
        LocalTime finExistente = citaExistente.getHoraFinEstimada();

        // Hay conflicto si los rangos se solapan
        return !(finPropuesta.isBefore(inicioExistente) ||
                 finPropuesta.equals(inicioExistente) ||
                 inicioPropuesta.isAfter(finExistente) ||
                 inicioPropuesta.equals(finExistente));
    }

    /**
     * Valida que la duración de la cita sea razonable
     * @param duracionMinutos duración en minutos
     * @return true si es válida
     */
    public boolean validarDuracionCita(Integer duracionMinutos) {
        if (duracionMinutos == null || duracionMinutos < duracionMinimaCita) {
            log.warn("Duración {} menor a la mínima permitida {}", duracionMinutos, duracionMinimaCita);
            return false;
        }

        if (duracionMinutos > 480) { // Máximo 8 horas
            log.warn("Duración {} excede el máximo permitido (480 minutos)", duracionMinutos);
            return false;
        }

        return true;
    }

    /**
     * Verifica si es posible reagendar una cita
     * @param cita la cita a reagendar
     * @param nuevaFecha nueva fecha
     * @param nuevaHora nueva hora
     * @return true si es posible reagendar
     */
    public boolean puedeReagendar(Cita cita, LocalDate nuevaFecha, LocalTime nuevaHora) {
        // Verificar que la cita sea modificable
        if (!cita.esModificable()) {
            log.warn("Cita {} no es modificable en estado {}", cita.getId(), cita.getEstado().getNombre());
            return false;
        }

        // Verificar disponibilidad en el nuevo horario
        return verificarDisponibilidadVeterinario(
                cita.getVeterinario(),
                nuevaFecha,
                nuevaHora,
                cita.getDuracionEstimada(),
                cita.getId() // Excluir la cita actual
        );
    }

    /**
     * Cuenta cuántas citas tiene el veterinario en una fecha
     * @param veterinario el veterinario
     * @param fecha la fecha
     * @return cantidad de citas
     */
    public Long contarCitasDelVeterinario(Usuario veterinario, LocalDate fecha) {
        return citaRepository.countByVeterinarioAndFecha(veterinario, fecha);
    }
}
