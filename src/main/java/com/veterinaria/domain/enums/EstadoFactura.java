package com.veterinaria.domain.enums;

/**
 * Enumeración para los estados de una factura.
 *
 * @author Sistema Veterinaria
 */
public enum EstadoFactura {

    /**
     * Factura emitida pero no pagada
     */
    PENDIENTE("Pendiente de pago"),

    /**
     * Factura con pago parcial
     */
    PARCIAL("Pago parcial"),

    /**
     * Factura completamente pagada
     */
    PAGADA("Pagada"),

    /**
     * Factura anulada/cancelada
     */
    ANULADA("Anulada"),

    /**
     * Factura vencida (no pagada después de fecha límite)
     */
    VENCIDA("Vencida");

    private final String descripcion;

    EstadoFactura(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Verifica si la factura está pagada completamente
     */
    public boolean estaPagada() {
        return this == PAGADA;
    }

    /**
     * Verifica si la factura puede recibir pagos
     */
    public boolean puedeRecibirPagos() {
        return this == PENDIENTE || this == PARCIAL || this == VENCIDA;
    }

    /**
     * Verifica si la factura puede ser modificada
     */
    public boolean puedeSerModificada() {
        return this == PENDIENTE;
    }
}
