package com.veterinaria.domain.service;

import com.veterinaria.application.repository.IntentosLoginRepository;
import com.veterinaria.domain.entity.security.IntentosLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Gestor de intentos de login fallidos.
 * Implementa lógica de bloqueo de cuentas por intentos fallidos.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GestorIntentosFallidos {

    private final IntentosLoginRepository intentosLoginRepository;

    @Value("${security.login.max-intentos:5}")
    private int maxIntentosFallidos;

    @Value("${security.login.bloqueo-duracion-minutos:15}")
    private int bloqueoDuracionMinutos;

    /**
     * Registra un intento de login fallido
     *
     * @param email email del usuario
     */
    @Transactional
    public void registrarIntentoFallido(String email) {
        Optional<IntentosLogin> intentosOpt = intentosLoginRepository.findByEmail(email);

        IntentosLogin intentos;
        if (intentosOpt.isPresent()) {
            intentos = intentosOpt.get();
            intentos.incrementarFallos();
        } else {
            intentos = IntentosLogin.builder()
                    .email(email)
                    .intentosFallidos(1)
                    .ultimoIntento(LocalDateTime.now())
                    .build();
        }

        // Si alcanzó el máximo de intentos, bloquear
        if (intentos.getIntentosFallidos() >= maxIntentosFallidos) {
            intentos.bloquear(bloqueoDuracionMinutos);
            log.warn("Cuenta bloqueada por {} minutos: {}", bloqueoDuracionMinutos, email);
        }

        intentosLoginRepository.save(intentos);
        log.debug("Intento fallido registrado para: {}. Total: {}", 
                  email, intentos.getIntentosFallidos());
    }

    /**
     * Verifica si una cuenta está bloqueada
     *
     * @param email email a verificar
     * @return true si está bloqueada
     */
    public boolean estaBloqueado(String email) {
        Optional<IntentosLogin> intentosOpt = intentosLoginRepository.findByEmail(email);
        
        if (intentosOpt.isEmpty()) {
            return false;
        }

        IntentosLogin intentos = intentosOpt.get();
        boolean bloqueado = intentos.estaBloqueado();

        // Si el bloqueo expiró, reiniciar automáticamente
        if (!bloqueado && intentos.getBloqueadoHasta() != null) {
            reiniciarIntentos(email);
        }

        return bloqueado;
    }

    /**
     * Obtiene el tiempo restante de bloqueo en minutos
     *
     * @param email email a verificar
     * @return minutos restantes de bloqueo, 0 si no está bloqueado
     */
    public long obtenerMinutosRestantesBloqueo(String email) {
        Optional<IntentosLogin> intentosOpt = intentosLoginRepository.findByEmail(email);
        
        if (intentosOpt.isEmpty()) {
            return 0;
        }

        IntentosLogin intentos = intentosOpt.get();
        if (!intentos.estaBloqueado()) {
            return 0;
        }

        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime bloqueadoHasta = intentos.getBloqueadoHasta();
        
        return java.time.Duration.between(ahora, bloqueadoHasta).toMinutes();
    }

    /**
     * Reinicia los intentos fallidos después de un login exitoso
     *
     * @param email email del usuario
     */
    @Transactional
    public void reiniciarIntentos(String email) {
        Optional<IntentosLogin> intentosOpt = intentosLoginRepository.findByEmail(email);
        
        if (intentosOpt.isPresent()) {
            IntentosLogin intentos = intentosOpt.get();
            intentos.reiniciar();
            intentosLoginRepository.save(intentos);
            log.debug("Intentos reiniciados para: {}", email);
        }
    }

    /**
     * Obtiene el número de intentos fallidos de una cuenta
     *
     * @param email email a consultar
     * @return número de intentos fallidos
     */
    public int obtenerIntentosFallidos(String email) {
        Optional<IntentosLogin> intentosOpt = intentosLoginRepository.findByEmail(email);
        return intentosOpt.map(IntentosLogin::getIntentosFallidos).orElse(0);
    }

    /**
     * Desbloquea manualmente una cuenta
     *
     * @param email email a desbloquear
     */
    @Transactional
    public void desbloquearCuenta(String email) {
        Optional<IntentosLogin> intentosOpt = intentosLoginRepository.findByEmail(email);
        
        if (intentosOpt.isPresent()) {
            IntentosLogin intentos = intentosOpt.get();
            intentos.reiniciar();
            intentosLoginRepository.save(intentos);
            log.info("Cuenta desbloqueada manualmente: {}", email);
        }
    }

    /**
     * Limpia registros de intentos antiguos (más de 30 días)
     */
    @Transactional
    public void limpiarIntentosAntiguos() {
        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(30);
        intentosLoginRepository.deleteByUltimoIntentoBefore(fechaLimite);
        log.info("Registros de intentos antiguos eliminados");
    }

    /**
     * Obtiene el máximo de intentos permitidos
     *
     * @return número máximo de intentos
     */
    public int getMaxIntentosFallidos() {
        return maxIntentosFallidos;
    }

    /**
     * Obtiene la duración del bloqueo en minutos
     *
     * @return duración en minutos
     */
    public int getBloqueoDuracionMinutos() {
        return bloqueoDuracionMinutos;
    }
}
