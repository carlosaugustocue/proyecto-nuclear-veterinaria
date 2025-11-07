package com.veterinaria.domain.entity.security;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad que registra intentos de login fallidos.
 * Utilizado para bloqueo temporal de cuentas.
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "intentos_login", indexes = {
    @Index(name = "idx_email", columnList = "email")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntentosLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Email del usuario que intenta hacer login
     */
    @Column(nullable = false, unique = true, length = 255)
    private String email;

    /**
     * Número de intentos fallidos consecutivos
     */
    @Column(nullable = false)
    @Builder.Default
    private int intentosFallidos = 0;

    /**
     * Fecha y hora del último intento fallido
     */
    private LocalDateTime ultimoIntento;

    /**
     * Fecha y hora hasta cuando está bloqueado
     */
    private LocalDateTime bloqueadoHasta;

    /**
     * Incrementa el contador de intentos fallidos
     */
    public void incrementarFallos() {
        this.intentosFallidos++;
        this.ultimoIntento = LocalDateTime.now();
    }

    /**
     * Bloquea la cuenta por un número de minutos
     *
     * @param duracionMinutos duración del bloqueo
     */
    public void bloquear(int duracionMinutos) {
        this.bloqueadoHasta = LocalDateTime.now().plusMinutes(duracionMinutos);
    }

    /**
     * Verifica si la cuenta está bloqueada
     */
    public boolean estaBloqueado() {
        return bloqueadoHasta != null &&
               LocalDateTime.now().isBefore(bloqueadoHasta);
    }

    /**
     * Reinicia los intentos fallidos
     */
    public void reiniciar() {
        this.intentosFallidos = 0;
        this.ultimoIntento = null;
        this.bloqueadoHasta = null;
    }

    @Override
    public String toString() {
        return "IntentosLogin{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", intentosFallidos=" + intentosFallidos +
                ", bloqueado=" + estaBloqueado() +
                '}';
    }
}
