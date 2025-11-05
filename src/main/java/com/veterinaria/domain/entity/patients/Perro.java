package com.veterinaria.domain.entity.patients;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Entidad concreta para pacientes de especie canina.
 * Implementa Template Method Pattern con cuidados específicos de perros.
 *
 * @author Sistema Veterinaria
 */
@Entity
@DiscriminatorValue("PERRO")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Perro extends Paciente {

    @Override
    public String obtenerCuidadosEspecificos() {
        return """
            Cuidados específicos para caninos:
            - Vacunación anual contra parvovirus, moquillo y rabia
            - Desparasitación trimestral
            - Ejercicio diario mínimo 30-60 minutos
            - Baño cada 2-4 semanas
            - Revisión dental semestral
            - Control de pulgas y garrapatas mensual
            """;
    }

    @Override
    public String obtenerDietaRecomendada() {
        int edad = calcularEdadAnios();

        if (edad < 1) {
            return "Alimento para cachorro, 3-4 veces al día, rico en proteínas y calcio";
        } else if (edad < 7) {
            return "Alimento para adulto, 2 veces al día, balanceado en proteínas y grasas";
        } else {
            return "Alimento para senior, 2 veces al día, bajo en grasas, alto en fibra";
        }
    }
}
