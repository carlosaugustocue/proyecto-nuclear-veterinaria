package com.veterinaria.domain.entity.medical;

import com.veterinaria.domain.constants.ReferenciasClinicas;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Value Object que representa los signos vitales de un paciente.
 * Se embebe en la entidad Consulta.
 *
 * @author Sistema Veterinaria
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignosVitales implements Serializable {

    /**
     * Temperatura corporal en grados Celsius
     */
    @DecimalMin(value = "30.0", message = "La temperatura no puede ser menor a 30°C")
    @DecimalMax(value = "45.0", message = "La temperatura no puede ser mayor a 45°C")
    @Column(name = "temperatura")
    private Double temperatura;

    /**
     * Peso del paciente en kilogramos
     */
    @DecimalMin(value = "0.1", message = "El peso debe ser mayor a 0.1 kg")
    @DecimalMax(value = "200.0", message = "El peso no puede exceder 200 kg")
    @Column(name = "peso_kg")
    private Double pesoKg;

    /**
     * Frecuencia cardíaca en latidos por minuto
     */
    @Min(value = 30, message = "La frecuencia cardíaca no puede ser menor a 30 lpm")
    @Max(value = 300, message = "La frecuencia cardíaca no puede exceder 300 lpm")
    @Column(name = "frecuencia_cardiaca")
    private Integer frecuenciaCardiaca;

    /**
     * Frecuencia respiratoria en respiraciones por minuto
     */
    @Min(value = 5, message = "La frecuencia respiratoria no puede ser menor a 5 rpm")
    @Max(value = 100, message = "La frecuencia respiratoria no puede exceder 100 rpm")
    @Column(name = "frecuencia_respiratoria")
    private Integer frecuenciaRespiratoria;

    /**
     * Temperatura rectal (más precisa que la axilar)
     */
    @Column(name = "temperatura_rectal")
    private Double temperaturaRectal;

    /**
     * Condición corporal (escala 1-9)
     * 1-3: Bajo peso
     * 4-5: Ideal
     * 6-9: Sobrepeso/Obesidad
     */
    @Min(value = 1, message = "La condición corporal debe estar entre 1 y 9")
    @Max(value = 9, message = "La condición corporal debe estar entre 1 y 9")
    @Column(name = "condicion_corporal")
    private Integer condicionCorporal;

    /**
     * Verifica si los signos vitales están en rango normal para perros
     * @return true si están en rango normal
     */
    public boolean sonNormalesParaPerro() {
        if (temperatura == null || frecuenciaCardiaca == null) {
            return false;
        }

        boolean tempNormal = temperatura >= ReferenciasClinicas.Temperatura.PERRO_MIN_NORMAL
                          && temperatura <= ReferenciasClinicas.Temperatura.PERRO_MAX_NORMAL;
        boolean fcNormal = frecuenciaCardiaca >= ReferenciasClinicas.FrecuenciaCardiaca.PERRO_MIN_NORMAL
                        && frecuenciaCardiaca <= ReferenciasClinicas.FrecuenciaCardiaca.PERRO_MAX_NORMAL;

        return tempNormal && fcNormal;
    }

    /**
     * Verifica si los signos vitales están en rango normal para gatos
     * @return true si están en rango normal
     */
    public boolean sonNormalesParaGato() {
        if (temperatura == null || frecuenciaCardiaca == null) {
            return false;
        }

        boolean tempNormal = temperatura >= ReferenciasClinicas.Temperatura.GATO_MIN_NORMAL
                          && temperatura <= ReferenciasClinicas.Temperatura.GATO_MAX_NORMAL;
        boolean fcNormal = frecuenciaCardiaca >= ReferenciasClinicas.FrecuenciaCardiaca.GATO_MIN_NORMAL
                        && frecuenciaCardiaca <= ReferenciasClinicas.FrecuenciaCardiaca.GATO_MAX_NORMAL;

        return tempNormal && fcNormal;
    }

    /**
     * Verifica si hay fiebre
     * @return true si la temperatura indica fiebre
     */
    public boolean tieneFiebre() {
        return temperatura != null && temperatura > ReferenciasClinicas.Temperatura.UMBRAL_FIEBRE;
    }

    /**
     * Verifica si hay hipotermia
     * @return true si la temperatura indica hipotermia
     */
    public boolean tieneHipotermia() {
        return temperatura != null && temperatura < ReferenciasClinicas.Temperatura.UMBRAL_HIPOTERMIA;
    }

    /**
     * Evalúa la condición corporal
     * @return descripción de la condición corporal
     */
    public String evaluarCondicionCorporal() {
        if (condicionCorporal == null) {
            return "No evaluada";
        }

        if (condicionCorporal <= ReferenciasClinicas.CondicionCorporal.BAJO_PESO_MAX) {
            return "Bajo peso";
        } else if (condicionCorporal >= ReferenciasClinicas.CondicionCorporal.IDEAL_MIN
                && condicionCorporal <= ReferenciasClinicas.CondicionCorporal.IDEAL_MAX) {
            return "Peso ideal";
        } else if (condicionCorporal >= ReferenciasClinicas.CondicionCorporal.SOBREPESO_MIN) {
            return "Sobrepeso/Obesidad";
        }

        return "No determinada";
    }

    @Override
    public String toString() {
        return String.format("SignosVitales{temp=%.1f°C, peso=%.2fkg, FC=%d lpm, FR=%d rpm}",
                temperatura, pesoKg, frecuenciaCardiaca, frecuenciaRespiratoria);
    }
}
