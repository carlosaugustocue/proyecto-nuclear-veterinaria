package com.veterinaria.domain.enums;

/**
 * Enumeración para los tipos de descuento aplicables.
 *
 * @author Sistema Veterinaria
 */
public enum TipoDescuento {

    /**
     * Descuento como porcentaje del total (ej: 10%, 20%)
     */
    PORCENTAJE("Porcentaje"),

    /**
     * Descuento como monto fijo (ej: $10, $50)
     */
    MONTO_FIJO("Monto fijo");

    private final String descripcion;

    TipoDescuento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Calcula el monto de descuento basado en el subtotal
     *
     * @param valor valor del descuento (porcentaje o monto fijo)
     * @param subtotal subtotal sobre el cual aplicar el descuento
     * @return monto de descuento calculado
     */
    public Double calcularDescuento(Double valor, Double subtotal) {
        if (valor == null || subtotal == null || valor <= 0 || subtotal <= 0) {
            return 0.0;
        }

        return switch (this) {
            case PORCENTAJE -> {
                // Validar que el porcentaje no exceda 100%
                double porcentaje = Math.min(valor, 100.0);
                yield subtotal * (porcentaje / 100.0);
            }
            case MONTO_FIJO -> {
                // El descuento no puede ser mayor que el subtotal
                yield Math.min(valor, subtotal);
            }
        };
    }
}
