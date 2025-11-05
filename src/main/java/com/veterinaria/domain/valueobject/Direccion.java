package com.veterinaria.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value Object que representa una dirección física.
 * Patrón: Value Object - Inmutable y con validación encapsulada.
 */
@Getter
@EqualsAndHashCode
public final class Direccion {

    private final String calle;
    private final String ciudad;
    private final String departamento;
    private final String codigoPostal;

    /**
     * Crea una dirección completa.
     * @param calle dirección de la calle
     * @param ciudad ciudad
     * @param departamento departamento o estado
     * @param codigoPostal código postal
     */
    public Direccion(String calle, String ciudad, String departamento, String codigoPostal) {
        if (calle == null || calle.isBlank()) {
            throw new IllegalArgumentException("La calle no puede estar vacía");
        }
        if (ciudad == null || ciudad.isBlank()) {
            throw new IllegalArgumentException("La ciudad no puede estar vacía");
        }
        if (departamento == null || departamento.isBlank()) {
            throw new IllegalArgumentException("El departamento no puede estar vacío");
        }

        this.calle = calle.trim();
        this.ciudad = ciudad.trim();
        this.departamento = departamento.trim();
        this.codigoPostal = (codigoPostal != null) ? codigoPostal.trim() : "";
    }

    /**
     * Crea una dirección sin código postal.
     */
    public Direccion(String calle, String ciudad, String departamento) {
        this(calle, ciudad, departamento, null);
    }

    /**
     * Obtiene la dirección completa formateada.
     * @return dirección formateada
     */
    public String obtenerDireccionCompleta() {
        StringBuilder sb = new StringBuilder();
        sb.append(calle);
        sb.append(", ").append(ciudad);
        sb.append(", ").append(departamento);

        if (codigoPostal != null && !codigoPostal.isBlank()) {
            sb.append(" (").append(codigoPostal).append(")");
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return obtenerDireccionCompleta();
    }
}
