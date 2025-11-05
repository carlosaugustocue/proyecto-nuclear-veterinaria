package com.veterinaria.domain.factory;

import com.veterinaria.domain.entity.patients.Paciente;
import com.veterinaria.domain.enums.TipoEspecie;

import java.util.Map;

/**
 * Patrón Factory Method: Interfaz para la creación de diferentes tipos de pacientes.
 * Permite crear pacientes (Perro, Gato, etc.) con configuraciones específicas
 * según su especie sin exponer la lógica de creación al cliente.
 *
 * @author Sistema Veterinaria
 */
public interface PacienteFactory {

    /**
     * Crea un paciente según la especie especificada con los datos proporcionados.
     *
     * @param tipoEspecie tipo de especie (PERRO, GATO, etc.)
     * @param datos mapa con los datos del paciente (nombre, fechaNacimiento, sexo, etc.)
     * @return paciente creado con configuración específica de la especie
     * @throws IllegalArgumentException si la especie no es soportada o faltan datos
     */
    Paciente crearPaciente(TipoEspecie tipoEspecie, Map<String, Object> datos);

    /**
     * Crea un paciente de especie canina.
     * @param datos datos del perro
     * @return instancia de Perro
     */
    Paciente crearPerro(Map<String, Object> datos);

    /**
     * Crea un paciente de especie felina.
     * @param datos datos del gato
     * @return instancia de Gato
     */
    Paciente crearGato(Map<String, Object> datos);
}
