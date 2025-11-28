package com.veterinaria.domain.enums;

/**
 * Unidades de medida para productos
 */
public enum UnidadMedida {
    UNIDAD("Unidad"),
    CAJA("Caja"),
    FRASCO("Frasco"),
    AMPOLLA("Ampolla"),
    TABLETA("Tableta"),
    CAPSULA("Cápsula"),
    ML("Mililitro"),
    LITRO("Litro"),
    GRAMO("Gramo"),
    KILOGRAMO("Kilogramo"),
    SOBRE("Sobre"),
    PAQUETE("Paquete");

    private final String descripcion;

    UnidadMedida(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
