package com.veterinaria.domain.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Servicio para encriptar y verificar contraseñas usando BCrypt.
 * Utiliza BCryptPasswordEncoder de Spring Security.
 *
 * @author Sistema Veterinaria
 */
@Component
public class EncriptadorContrasena {

    private final BCryptPasswordEncoder encoder;

    /**
     * Constructor que inicializa el encoder con nivel de seguridad 12
     */
    public EncriptadorContrasena() {
        this.encoder = new BCryptPasswordEncoder(12);
    }

    /**
     * Encripta una contraseña en texto plano
     *
     * @param contrasenaPlana contraseña en texto plano
     * @return contraseña encriptada
     * @throws IllegalArgumentException si la contraseña es nula o vacía
     */
    public String encriptar(String contrasenaPlana) {
        if (contrasenaPlana == null || contrasenaPlana.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía");
        }
        return encoder.encode(contrasenaPlana);
    }

    /**
     * Verifica si una contraseña en texto plano coincide con una encriptada
     *
     * @param contrasenaPlana contraseña en texto plano
     * @param contrasenaEncriptada contraseña encriptada
     * @return true si coinciden, false en caso contrario
     */
    public boolean verificar(String contrasenaPlana, String contrasenaEncriptada) {
        if (contrasenaPlana == null || contrasenaEncriptada == null) {
            return false;
        }
        try {
            return encoder.matches(contrasenaPlana, contrasenaEncriptada);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica si una contraseña encriptada necesita ser re-encriptada
     * (útil cuando se cambia el nivel de seguridad del encoder)
     *
     * @param contrasenaEncriptada contraseña encriptada a verificar
     * @return true si necesita actualización
     */
    public boolean necesitaActualizacion(String contrasenaEncriptada) {
        if (contrasenaEncriptada == null || contrasenaEncriptada.isEmpty()) {
            return true;
        }
        return encoder.upgradeEncoding(contrasenaEncriptada);
    }

    /**
     * Valida que una contraseña cumpla con los requisitos mínimos
     *
     * @param contrasena contraseña a validar
     * @return true si cumple los requisitos
     */
    public boolean esContrasenaSegura(String contrasena) {
        if (contrasena == null || contrasena.length() < 8) {
            return false;
        }

        boolean tieneMayuscula = contrasena.chars().anyMatch(Character::isUpperCase);
        boolean tieneMinuscula = contrasena.chars().anyMatch(Character::isLowerCase);
        boolean tieneDigito = contrasena.chars().anyMatch(Character::isDigit);
        boolean tieneEspecial = contrasena.chars().anyMatch(c -> "!@#$%^&*()_+-=[]{}|;:',.<>?".indexOf(c) >= 0);

        return tieneMayuscula && tieneMinuscula && tieneDigito && tieneEspecial;
    }

    /**
     * Obtiene un mensaje descriptivo de los requisitos de contraseña
     *
     * @return mensaje con los requisitos
     */
    public String obtenerRequisitosContrasena() {
        return "La contraseña debe tener al menos 8 caracteres, " +
               "incluir mayúsculas, minúsculas, números y caracteres especiales";
    }
}
