package com.veterinaria.domain.enums;

import lombok.Getter;

/**
 * Tipos de productos en el inventario.
 */
@Getter
public enum TipoProducto {
    MEDICAMENTO("Medicamento", "Productos farmacéuticos"),
    VACUNA("Vacuna", "Vacunas y biológicos"),
    ALIMENTO("Alimento", "Alimentos para mascotas"),
    ACCESORIO("Accesorio", "Accesorios y complementos"),
    MATERIAL_CLINICO("Material Clínico", "Material para procedimientos"),
    HIGIENE("Higiene", "Productos de higiene y limpieza"),
    OTRO("Otro", "Otros productos");

    private final String displayName;
    private final String descripcion;

    TipoProducto(String displayName, String descripcion) {
        this.displayName = displayName;
        this.descripcion = descripcion;
    }
}
