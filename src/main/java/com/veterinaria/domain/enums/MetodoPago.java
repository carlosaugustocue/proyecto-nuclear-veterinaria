package com.veterinaria.domain.enums;

import lombok.Getter;

/**
 * Métodos de pago disponibles en el sistema.
 * Se utiliza con el patrón Template Method para procesar pagos.
 */
@Getter
public enum MetodoPago {
    EFECTIVO("Efectivo", "Pago en efectivo", 0.0),
    TARJETA_CREDITO("Tarjeta de Crédito", "Pago con tarjeta de crédito", 0.03),
    TARJETA_DEBITO("Tarjeta de Débito", "Pago con tarjeta de débito", 0.01),
    TRANSFERENCIA("Transferencia Bancaria", "Transferencia bancaria", 0.0),
    YAPE("Yape", "Pago con Yape", 0.0),
    PLIN("Plin", "Pago con Plin", 0.0);

    private final String displayName;
    private final String descripcion;
    private final double comision;

    MetodoPago(String displayName, String descripcion, double comision) {
        this.displayName = displayName;
        this.descripcion = descripcion;
        this.comision = comision;
    }

    /**
     * Calcula el monto total incluyendo la comisión.
     *
     * @param montoBase Monto base antes de comisión
     * @return Monto total con comisión aplicada
     */
    public double calcularMontoConComision(double montoBase) {
        return montoBase * (1 + comision);
    }
}
