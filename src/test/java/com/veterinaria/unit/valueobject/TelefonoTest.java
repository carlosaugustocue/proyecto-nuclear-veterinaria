package com.veterinaria.unit.valueobject;

import com.veterinaria.domain.valueobject.Telefono;
import com.veterinaria.infrastructure.constants.TelefonoConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para el Value Object Telefono.
 * Patrón testeado: Value Object
 */
@DisplayName("Telefono Value Object Tests")
class TelefonoTest {

    @Test
    @DisplayName("Debe crear teléfono con código de país por defecto")
    void debeCrearTelefonoConCodigoPaisPorDefecto() {
        // Arrange & Act
        Telefono telefono = new Telefono("3001234567");

        // Assert
        assertNotNull(telefono);
        assertEquals("3001234567", telefono.getNumero());
        assertEquals(TelefonoConstants.CODIGO_PAIS_DEFAULT, telefono.getCodigoPais());
    }

    @Test
    @DisplayName("Debe crear teléfono con código de país específico")
    void debeCrearTelefonoConCodigoPaisEspecifico() {
        // Arrange & Act
        Telefono telefono = new Telefono("5551234567", "+1");

        // Assert
        assertEquals("5551234567", telefono.getNumero());
        assertEquals("+1", telefono.getCodigoPais());
    }

    @Test
    @DisplayName("Debe limpiar caracteres no numéricos")
    void debeLimpiarCaracteresNoNumericos() {
        // Arrange & Act
        Telefono telefono = new Telefono("(300) 123-4567");

        // Assert
        assertEquals("3001234567", telefono.getNumero());
    }

    @Test
    @DisplayName("Debe formatear número de 10 dígitos")
    void debeFormatearNumeroDe10Digitos() {
        // Arrange
        Telefono telefono = new Telefono("3001234567");

        // Act
        String formateado = telefono.formatear();

        // Assert
        assertEquals("300-123-4567", formateado);
    }

    @Test
    @DisplayName("Debe obtener número completo con código de país")
    void debeObtenerNumeroCompletoConCodigoPais() {
        // Arrange
        Telefono telefono = new Telefono("3001234567", "+57");

        // Act
        String completo = telefono.obtenerNumeroCompleto();

        // Assert
        assertEquals("+57 3001234567", completo);
    }

    @Test
    @DisplayName("Debe lanzar excepción con número nulo")
    void debeLanzarExcepcionConNumeroNulo() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Telefono(null));
    }

    @Test
    @DisplayName("Debe lanzar excepción con número muy corto")
    void debeLanzarExcepcionConNumeroMuyCorto() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Telefono("12345"));
    }

    @Test
    @DisplayName("Dos teléfonos iguales deben ser equals")
    void dosTelefonosIgualesDebenSerEquals() {
        // Arrange
        Telefono tel1 = new Telefono("3001234567", "+57");
        Telefono tel2 = new Telefono("(300) 123-4567", "+57");

        // Act & Assert
        assertEquals(tel1, tel2);
        assertEquals(tel1.hashCode(), tel2.hashCode());
    }
}
