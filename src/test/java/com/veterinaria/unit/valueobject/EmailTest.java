package com.veterinaria.unit.valueobject;

import com.veterinaria.domain.valueobject.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para el Value Object Email.
 * Patrón testeado: Value Object
 */
@DisplayName("Email Value Object Tests")
class EmailTest {

    @Test
    @DisplayName("Debe crear email válido")
    void debeCrearEmailValido() {
        // Arrange & Act
        Email email = new Email("usuario@ejemplo.com");

        // Assert
        assertNotNull(email);
        assertEquals("usuario@ejemplo.com", email.getValor());
    }

    @Test
    @DisplayName("Debe normalizar email a minúsculas")
    void debeNormalizarEmailAMinusculas() {
        // Arrange & Act
        Email email = new Email("Usuario@EJEMPLO.COM");

        // Assert
        assertEquals("usuario@ejemplo.com", email.getValor());
    }

    @Test
    @DisplayName("Debe obtener dominio correctamente")
    void debeObtenerDominioCorrectamente() {
        // Arrange
        Email email = new Email("test@gmail.com");

        // Act
        String dominio = email.obtenerDominio();

        // Assert
        assertEquals("gmail.com", dominio);
    }

    @Test
    @DisplayName("Debe obtener nombre de usuario correctamente")
    void debeObtenerNombreUsuarioCorrectamente() {
        // Arrange
        Email email = new Email("carlos@veterinaria.com");

        // Act
        String nombreUsuario = email.obtenerNombreUsuario();

        // Assert
        assertEquals("carlos", nombreUsuario);
    }

    @Test
    @DisplayName("Debe lanzar excepción con email nulo")
    void debeLanzarExcepcionConEmailNulo() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Email(null));
    }

    @Test
    @DisplayName("Debe lanzar excepción con email vacío")
    void debeLanzarExcepcionConEmailVacio() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Email("   "));
    }

    @Test
    @DisplayName("Debe lanzar excepción con formato inválido")
    void debeLanzarExcepcionConFormatoInvalido() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Email("email-invalido"));
        assertThrows(IllegalArgumentException.class, () -> new Email("@ejemplo.com"));
        assertThrows(IllegalArgumentException.class, () -> new Email("usuario@"));
    }

    @Test
    @DisplayName("Dos emails iguales deben ser equals")
    void dosEmailsIgualesDebenSerEquals() {
        // Arrange
        Email email1 = new Email("test@example.com");
        Email email2 = new Email("TEST@EXAMPLE.COM"); // Se normaliza a minúsculas

        // Act & Assert
        assertEquals(email1, email2);
        assertEquals(email1.hashCode(), email2.hashCode());
    }
}
