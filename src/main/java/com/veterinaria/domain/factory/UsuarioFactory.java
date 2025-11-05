package com.veterinaria.domain.factory;

import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.enums.TipoUsuario;

import java.util.Map;

/**
 * Patrón Factory Method: Interfaz para la creación de diferentes tipos de usuarios.
 * Permite crear usuarios con configuraciones específicas según su tipo sin exponer
 * la lógica de creación al cliente.
 *
 * @author Sistema Veterinaria
 */
public interface UsuarioFactory {

    /**
     * Crea un usuario según el tipo especificado con los datos proporcionados.
     *
     * @param tipoUsuario tipo de usuario a crear (VETERINARIO, ADMINISTRADOR, etc.)
     * @param datos mapa con los datos del usuario (username, email, nombre, etc.)
     * @return usuario creado con configuración específica del tipo
     * @throws IllegalArgumentException si el tipo no es soportado o faltan datos
     */
    Usuario crearUsuario(TipoUsuario tipoUsuario, Map<String, Object> datos);

    /**
     * Crea un veterinario con configuración específica.
     */
    Usuario crearVeterinario(Map<String, Object> datos);

    /**
     * Crea un administrador con configuración específica.
     */
    Usuario crearAdministrador(Map<String, Object> datos);

    /**
     * Crea un recepcionista con configuración específica.
     */
    Usuario crearRecepcionista(Map<String, Object> datos);

    /**
     * Crea un asistente con configuración específica.
     */
    Usuario crearAsistente(Map<String, Object> datos);

    /**
     * Crea un propietario de mascota con configuración específica.
     */
    Usuario crearPropietario(Map<String, Object> datos);
}
