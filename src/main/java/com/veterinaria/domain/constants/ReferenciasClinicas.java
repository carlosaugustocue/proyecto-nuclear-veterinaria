package com.veterinaria.domain.constants;

/**
 * Constantes con valores de referencia clínica para diagnóstico veterinario.
 * Centraliza los rangos normales de signos vitales y parámetros médicos.
 *
 * @author Sistema Veterinaria
 */
public final class ReferenciasClinicas {

    private ReferenciasClinicas() {
        throw new UnsupportedOperationException("Clase de constantes no instanciable");
    }

    /**
     * Rangos de temperatura corporal en grados Celsius
     */
    public static final class Temperatura {
        private Temperatura() {}

        // Rangos normales para perros
        public static final double PERRO_MIN_NORMAL = 37.5;
        public static final double PERRO_MAX_NORMAL = 39.2;

        // Rangos normales para gatos
        public static final double GATO_MIN_NORMAL = 38.1;
        public static final double GATO_MAX_NORMAL = 39.2;

        // Límites generales
        public static final double MIN_ACEPTABLE = 30.0;
        public static final double MAX_ACEPTABLE = 45.0;

        // Umbrales de alerta
        public static final double UMBRAL_FIEBRE = 39.5;
        public static final double UMBRAL_HIPOTERMIA = 37.0;
    }

    /**
     * Rangos de frecuencia cardíaca en latidos por minuto (lpm)
     */
    public static final class FrecuenciaCardiaca {
        private FrecuenciaCardiaca() {}

        // Rangos normales para perros
        public static final int PERRO_MIN_NORMAL = 60;
        public static final int PERRO_MAX_NORMAL = 140;

        // Rangos normales para gatos
        public static final int GATO_MIN_NORMAL = 140;
        public static final int GATO_MAX_NORMAL = 220;

        // Límites generales
        public static final int MIN_ACEPTABLE = 30;
        public static final int MAX_ACEPTABLE = 300;
    }

    /**
     * Rangos de frecuencia respiratoria en respiraciones por minuto (rpm)
     */
    public static final class FrecuenciaRespiratoria {
        private FrecuenciaRespiratoria() {}

        // Rangos normales generales
        public static final int MIN_NORMAL = 10;
        public static final int MAX_NORMAL = 30;

        // Límites de validación
        public static final int MIN_ACEPTABLE = 5;
        public static final int MAX_ACEPTABLE = 100;
    }

    /**
     * Rangos de peso en kilogramos
     */
    public static final class Peso {
        private Peso() {}

        public static final double MIN_ACEPTABLE = 0.1;
        public static final double MAX_ACEPTABLE = 200.0;
    }

    /**
     * Escala de condición corporal (1-9)
     */
    public static final class CondicionCorporal {
        private CondicionCorporal() {}

        public static final int MIN_ESCALA = 1;
        public static final int MAX_ESCALA = 9;

        public static final int BAJO_PESO_MAX = 3;
        public static final int IDEAL_MIN = 4;
        public static final int IDEAL_MAX = 5;
        public static final int SOBREPESO_MIN = 6;
    }

    /**
     * Tiempos de ayuno en horas
     */
    public static final class TiempoAyuno {
        private TiempoAyuno() {}

        public static final int CIRUGIA_GENERAL_HORAS = 12;
        public static final int EXAMEN_SANGRE_HORAS = 8;
        public static final int ECOGRAFIA_ABDOMINAL_HORAS = 6;
    }

    /**
     * Valores de validación para campos numéricos
     */
    public static final class Validacion {
        private Validacion() {}

        public static final int PRECISION_TEMPERATURA = 4;
        public static final int SCALE_TEMPERATURA = 1;

        public static final int PRECISION_PESO = 5;
        public static final int SCALE_PESO = 2;
    }
}
