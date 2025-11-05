package com.veterinaria.domain.factory;

import com.veterinaria.domain.entity.patients.Gato;
import com.veterinaria.domain.entity.patients.Paciente;
import com.veterinaria.domain.entity.patients.Perro;
import com.veterinaria.domain.enums.EstadoPaciente;
import com.veterinaria.domain.enums.Sexo;
import com.veterinaria.domain.enums.TipoEspecie;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

/**
 * Implementación concreta del patrón Factory Method para crear pacientes.
 * Encapsula la lógica de creación y configuración de diferentes tipos de especies.
 *
 * Patrón: Factory Method
 * Propósito: Proporcionar una interfaz para crear objetos Paciente,
 * permitiendo que las subclases (métodos específicos) decidan qué configuración aplicar.
 *
 * @author Sistema Veterinaria
 */
@Component
public class PacienteFactoryImpl implements PacienteFactory {

    /**
     * Factory Method principal: delega la creación al método específico según la especie.
     */
    @Override
    public Paciente crearPaciente(TipoEspecie tipoEspecie, Map<String, Object> datos) {
        validarDatosBasicos(datos);

        return switch (tipoEspecie) {
            case PERRO -> crearPerro(datos);
            case GATO -> crearGato(datos);
            case AVE, REPTIL, ROEDOR, OTRO ->
                throw new UnsupportedOperationException(
                    "La especie " + tipoEspecie + " no está disponible actualmente. " +
                    "Solo se soportan PERRO y GATO por el momento."
                );
            default -> throw new IllegalArgumentException("Tipo de especie no soportado: " + tipoEspecie);
        };
    }

    @Override
    public Paciente crearPerro(Map<String, Object> datos) {
        validarDatosBasicos(datos);

        Perro perro = Perro.builder()
                .nombre((String) datos.get("nombre"))
                .especie(TipoEspecie.PERRO)
                .raza((String) datos.get("raza"))
                .fechaNacimiento((LocalDate) datos.get("fechaNacimiento"))
                .sexo((Sexo) datos.get("sexo"))
                .color((String) datos.get("color"))
                .pesoKg((Double) datos.get("pesoKg"))
                .estado(EstadoPaciente.ACTIVO)
                .microchip((String) datos.get("microchip"))
                .observaciones((String) datos.get("observaciones"))
                .fotoUrl((String) datos.get("fotoUrl"))
                .build();

        // Configuración específica para perros si es necesario
        // Por ejemplo: validar peso según raza, etc.

        return perro;
    }

    @Override
    public Paciente crearGato(Map<String, Object> datos) {
        validarDatosBasicos(datos);

        Gato gato = Gato.builder()
                .nombre((String) datos.get("nombre"))
                .especie(TipoEspecie.GATO)
                .raza((String) datos.get("raza"))
                .fechaNacimiento((LocalDate) datos.get("fechaNacimiento"))
                .sexo((Sexo) datos.get("sexo"))
                .color((String) datos.get("color"))
                .pesoKg((Double) datos.get("pesoKg"))
                .estado(EstadoPaciente.ACTIVO)
                .microchip((String) datos.get("microchip"))
                .observaciones((String) datos.get("observaciones"))
                .fotoUrl((String) datos.get("fotoUrl"))
                .build();

        // Configuración específica para gatos si es necesario
        // Por ejemplo: sugerir control de peso, etc.

        return gato;
    }

    /**
     * Valida que los datos mínimos requeridos estén presentes.
     */
    private void validarDatosBasicos(Map<String, Object> datos) {
        if (datos == null || datos.isEmpty()) {
            throw new IllegalArgumentException("Los datos del paciente no pueden estar vacíos");
        }

        String[] camposRequeridos = {"nombre", "fechaNacimiento", "sexo"};

        for (String campo : camposRequeridos) {
            if (!datos.containsKey(campo) || datos.get(campo) == null) {
                throw new IllegalArgumentException("El campo '" + campo + "' es obligatorio");
            }
        }

        // Validaciones adicionales
        validarFechaNacimiento((LocalDate) datos.get("fechaNacimiento"));
    }

    /**
     * Valida que la fecha de nacimiento sea válida.
     */
    private void validarFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura");
        }
    }
}
