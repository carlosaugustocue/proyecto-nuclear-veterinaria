package com.veterinaria.unit.valueobject;

import com.veterinaria.domain.valueobject.Direccion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para el Value Object Direccion.
 * Patrón testeado: Value Object
 */
@DisplayName("Direccion Value Object Tests")
class DireccionTest {

    @Test
    @DisplayName("Debe crear dirección completa")
    void debeCrearDireccionCompleta() {
        // Arrange & Act
        Direccion direccion = new Direccion(
            "Calle 123 #45-67",
            "Bogotá",
            "Cundinamarca",
            "110111"
        );

        // Assert
        assertNotNull(direccion);
        assertEquals("Calle 123 #45-67", direccion.getCalle());
        assertEquals("Bogotá", direccion.getCiudad());
        assertEquals("Cundinamarca", direccion.getDepartamento());
        assertEquals("110111", direccion.getCodigoPostal());
    }

    @Test
    @DisplayName("Debe crear dirección sin código postal")
    void debeCrearDireccionSinCodigoPostal() {
        // Arrange & Act
        Direccion direccion = new Direccion(
            "Avenida 10 #20-30",
            "Cali",
            "Valle del Cauca"
        );

        // Assert
        assertNotNull(direccion);
        assertEquals("", direccion.getCodigoPostal());
    }

    @Test
    @DisplayName("Debe obtener dirección completa formateada")
    void debeObtenerDireccionCompletaFormateada() {
        // Arrange
        Direccion direccion = new Direccion(
            "Carrera 7 #10-20",
            "Medellín",
            "Antioquia",
            "050001"
        );

        // Act
        String completa = direccion.obtenerDireccionCompleta();

        // Assert
        assertEquals("Carrera 7 #10-20, Medellín, Antioquia (050001)", completa);
    }

    @Test
    @DisplayName("Debe formatear dirección sin código postal")
    void debeFormatearDireccionSinCodigoPostal() {
        // Arrange
        Direccion direccion = new Direccion(
            "Calle 50 #30-40",
            "Barranquilla",
            "Atlántico"
        );

        // Act
        String completa = direccion.obtenerDireccionCompleta();

        // Assert
        assertEquals("Calle 50 #30-40, Barranquilla, Atlántico", completa);
    }

    @Test
    @DisplayName("Debe lanzar excepción con calle nula")
    void debeLanzarExcepcionConCalleNula() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            new Direccion(null, "Bogotá", "Cundinamarca")
        );
    }

    @Test
    @DisplayName("Debe lanzar excepción con ciudad nula")
    void debeLanzarExcepcionConCiudadNula() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            new Direccion("Calle 1", null, "Cundinamarca")
        );
    }

    @Test
    @DisplayName("Debe lanzar excepción con departamento nulo")
    void debeLanzarExcepcionConDepartamentoNulo() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            new Direccion("Calle 1", "Bogotá", null)
        );
    }

    @Test
    @DisplayName("Dos direcciones iguales deben ser equals")
    void dosDireccionesIgualesDebenSerEquals() {
        // Arrange
        Direccion dir1 = new Direccion("Calle 1", "Bogotá", "Cundinamarca", "110111");
        Direccion dir2 = new Direccion("Calle 1", "Bogotá", "Cundinamarca", "110111");

        // Act & Assert
        assertEquals(dir1, dir2);
        assertEquals(dir1.hashCode(), dir2.hashCode());
    }
}
