package com.veterinaria.domain.entity.patients;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Entidad concreta para pacientes de especie felina.
 * Implementa Template Method Pattern con cuidados específicos de gatos.
 *
 * @author Sistema Veterinaria
 */
@Entity
@DiscriminatorValue("GATO")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Gato extends Paciente {

    @Override
    public String obtenerCuidadosEspecificos() {
        return """
            Cuidados específicos para felinos:
            - Vacunación anual contra leucemia felina, calicivirus y rabia
            - Desparasitación trimestral
            - Cepillado semanal para evitar bolas de pelo
            - Limpieza de arenero diaria
            - Revisión dental anual
            - Control de parásitos externos mensual
            - Estimulación mental con juguetes
            """;
    }

    @Override
    public String obtenerDietaRecomendada() {
        int edad = calcularEdadAnios();

        if (edad < 1) {
            return "Alimento para gatito, 3-4 veces al día, alto en proteína animal";
        } else if (edad < 7) {
            return "Alimento para adulto, 2 veces al día, rico en taurina y proteínas";
        } else {
            return "Alimento para senior, 2-3 veces al día, bajo en fósforo, fácil digestión";
        }
    }
}
