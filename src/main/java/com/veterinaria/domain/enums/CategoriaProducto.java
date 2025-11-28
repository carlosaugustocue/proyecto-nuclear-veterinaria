package com.veterinaria.domain.enums;

/**
 * Categorías de productos en inventario
 */
public enum CategoriaProducto {
    MEDICAMENTO("Medicamento"),
    VACUNA("Vacuna"),
    MATERIAL("Material médico"),
    INSUMO("Insumo"),
    ALIMENTO("Alimento"),
    ACCESORIO("Accesorio");

    private final String descripcion;

    CategoriaProducto(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
