package com.veterinaria.domain.enums;

/**
 * Tipos de movimientos de inventario
 */
public enum TipoMovimiento {
    ENTRADA("Entrada al inventario"),
    SALIDA("Salida del inventario"),
    AJUSTE("Ajuste de inventario");

    private final String descripcion;

    TipoMovimiento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
