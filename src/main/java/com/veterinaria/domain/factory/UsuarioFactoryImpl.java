package com.veterinaria.domain.factory;

import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.enums.TipoUsuario;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Implementación concreta del patrón Factory Method para crear usuarios.
 * Encapsula la lógica de creación y configuración de diferentes tipos de usuarios.
 *
 * Patrón: Factory Method
 * Propósito: Proporcionar una interfaz para crear objetos Usuario,
 * permitiendo que las subclases (métodos específicos) decidan qué configuración aplicar.
 *
 * @author Sistema Veterinaria
 */
@Component
public class UsuarioFactoryImpl implements UsuarioFactory {

    /**
     * Factory Method principal: delega la creación al método específico según el tipo.
     */
    @Override
    public Usuario crearUsuario(TipoUsuario tipoUsuario, Map<String, Object> datos) {
        validarDatosBasicos(datos);

        return switch (tipoUsuario) {
            case VETERINARIO -> crearVeterinario(datos);
            case ADMINISTRADOR -> crearAdministrador(datos);
            case RECEPCIONISTA -> crearRecepcionista(datos);
            case ASISTENTE -> crearAsistente(datos);
            case PROPIETARIO -> crearPropietario(datos);
            default -> throw new IllegalArgumentException("Tipo de usuario no soportado: " + tipoUsuario);
        };
    }

    @Override
    public Usuario crearVeterinario(Map<String, Object> datos) {
        validarDatosBasicos(datos);

        Usuario veterinario = crearUsuarioBase(datos, TipoUsuario.VETERINARIO);

        // Configuración específica para veterinarios
        veterinario.setFotoperfilUrl((String) datos.get("fotoPerfilUrl"));

        return veterinario;
    }

    @Override
    public Usuario crearAdministrador(Map<String, Object> datos) {
        validarDatosBasicos(datos);

        Usuario admin = crearUsuarioBase(datos, TipoUsuario.ADMINISTRADOR);

        // Los administradores no requieren cambio de contraseña inicial
        admin.setRequiereCambioPassword(false);

        return admin;
    }

    @Override
    public Usuario crearRecepcionista(Map<String, Object> datos) {
        validarDatosBasicos(datos);

        Usuario recepcionista = crearUsuarioBase(datos, TipoUsuario.RECEPCIONISTA);

        // Configuración específica para recepcionistas
        // Requieren cambio de contraseña en primer login
        recepcionista.setRequiereCambioPassword(true);

        return recepcionista;
    }

    @Override
    public Usuario crearAsistente(Map<String, Object> datos) {
        validarDatosBasicos(datos);

        Usuario asistente = crearUsuarioBase(datos, TipoUsuario.ASISTENTE);

        // Configuración específica para asistentes
        asistente.setRequiereCambioPassword(true);

        return asistente;
    }

    @Override
    public Usuario crearPropietario(Map<String, Object> datos) {
        validarDatosBasicos(datos);

        Usuario propietario = crearUsuarioBase(datos, TipoUsuario.PROPIETARIO);

        // Los propietarios tienen configuración más flexible
        propietario.setRequiereCambioPassword(false);
        propietario.setDireccion((String) datos.get("direccion"));

        return propietario;
    }

    /**
     * Método auxiliar para crear la estructura base de un usuario.
     * Template Method interno que centraliza la lógica común.
     */
    private Usuario crearUsuarioBase(Map<String, Object> datos, TipoUsuario tipo) {
        return Usuario.builder()
                .username((String) datos.get("username"))
                .email((String) datos.get("email"))
                .password((String) datos.get("password")) // Ya debe venir encriptada
                .nombre((String) datos.get("nombre"))
                .apellido((String) datos.get("apellido"))
                .telefono((String) datos.get("telefono"))
                .dni((String) datos.get("dni"))
                .tipoUsuario(tipo)
                .cuentaBloqueada(false)
                .cuentaExpirada(false)
                .credencialesExpiradas(false)
                .intentosFallidos(0)
                .requiereCambioPassword(false) // Se sobrescribe en métodos específicos
                .build();
    }

    /**
     * Valida que los datos mínimos requeridos estén presentes.
     */
    private void validarDatosBasicos(Map<String, Object> datos) {
        if (datos == null || datos.isEmpty()) {
            throw new IllegalArgumentException("Los datos del usuario no pueden estar vacíos");
        }

        String[] camposRequeridos = {"username", "email", "password", "nombre", "apellido"};

        for (String campo : camposRequeridos) {
            if (!datos.containsKey(campo) || datos.get(campo) == null) {
                throw new IllegalArgumentException("El campo '" + campo + "' es obligatorio");
            }
        }
    }
}
