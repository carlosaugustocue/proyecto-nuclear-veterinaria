package com.veterinaria.unit.factory;

import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.enums.TipoUsuario;
import com.veterinaria.domain.factory.UsuarioFactory;
import com.veterinaria.domain.factory.UsuarioFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para UsuarioFactory.
 * Patrón testeado: Factory Method
 */
@DisplayName("UsuarioFactory Tests - Factory Method Pattern")
class UsuarioFactoryTest {

    private UsuarioFactory usuarioFactory;
    private Map<String, Object> datosBase;

    @BeforeEach
    void setUp() {
        usuarioFactory = new UsuarioFactoryImpl();

        // Datos base para crear usuarios
        datosBase = new HashMap<>();
        datosBase.put("username", "testuser");
        datosBase.put("email", "test@veterinaria.com");
        datosBase.put("password", "encodedPassword123");
        datosBase.put("nombre", "Juan");
        datosBase.put("apellido", "Pérez");
        datosBase.put("telefono", "3001234567");
        datosBase.put("dni", "12345678");
    }

    @Test
    @DisplayName("Debe crear veterinario correctamente")
    void debeCrearVeterinarioCorrectamente() {
        // Arrange
        datosBase.put("fotoPerfilUrl", "https://ejemplo.com/foto.jpg");

        // Act
        Usuario veterinario = usuarioFactory.crearVeterinario(datosBase);

        // Assert
        assertNotNull(veterinario);
        assertEquals(TipoUsuario.VETERINARIO, veterinario.getTipoUsuario());
        assertEquals("testuser", veterinario.getUsername());
        assertEquals("test@veterinaria.com", veterinario.getEmail());
        assertEquals("Juan", veterinario.getNombre());
        assertEquals("Pérez", veterinario.getApellido());
        assertEquals("https://ejemplo.com/foto.jpg", veterinario.getFotoperfilUrl());
        assertEquals(0, veterinario.getIntentosFallidos());
        assertFalse(veterinario.getCuentaBloqueada());
    }

    @Test
    @DisplayName("Debe crear administrador correctamente")
    void debeCrearAdministradorCorrectamente() {
        // Act
        Usuario admin = usuarioFactory.crearAdministrador(datosBase);

        // Assert
        assertNotNull(admin);
        assertEquals(TipoUsuario.ADMINISTRADOR, admin.getTipoUsuario());
        assertEquals("testuser", admin.getUsername());
        assertFalse(admin.getRequiereCambioPassword());
        assertFalse(admin.getCuentaBloqueada());
    }

    @Test
    @DisplayName("Debe crear recepcionista correctamente")
    void debeCrearRecepcionistaCorrectamente() {
        // Act
        Usuario recepcionista = usuarioFactory.crearRecepcionista(datosBase);

        // Assert
        assertNotNull(recepcionista);
        assertEquals(TipoUsuario.RECEPCIONISTA, recepcionista.getTipoUsuario());
        assertTrue(recepcionista.getRequiereCambioPassword());
    }

    @Test
    @DisplayName("Debe crear asistente correctamente")
    void debeCrearAsistenteCorrectamente() {
        // Act
        Usuario asistente = usuarioFactory.crearAsistente(datosBase);

        // Assert
        assertNotNull(asistente);
        assertEquals(TipoUsuario.ASISTENTE, asistente.getTipoUsuario());
        assertTrue(asistente.getRequiereCambioPassword());
    }

    @Test
    @DisplayName("Debe crear propietario correctamente")
    void debeCrearPropietarioCorrectamente() {
        // Arrange
        datosBase.put("direccion", "Calle 123 #45-67");

        // Act
        Usuario propietario = usuarioFactory.crearPropietario(datosBase);

        // Assert
        assertNotNull(propietario);
        assertEquals(TipoUsuario.PROPIETARIO, propietario.getTipoUsuario());
        assertEquals("Calle 123 #45-67", propietario.getDireccion());
        assertFalse(propietario.getRequiereCambioPassword());
    }

    @Test
    @DisplayName("Debe crear usuario según tipo con Factory Method principal")
    void debeCrearUsuarioSegunTipoConFactoryMethod() {
        // Act
        Usuario veterinario = usuarioFactory.crearUsuario(TipoUsuario.VETERINARIO, datosBase);
        Usuario admin = usuarioFactory.crearUsuario(TipoUsuario.ADMINISTRADOR, datosBase);
        Usuario recepcionista = usuarioFactory.crearUsuario(TipoUsuario.RECEPCIONISTA, datosBase);

        // Assert
        assertEquals(TipoUsuario.VETERINARIO, veterinario.getTipoUsuario());
        assertEquals(TipoUsuario.ADMINISTRADOR, admin.getTipoUsuario());
        assertEquals(TipoUsuario.RECEPCIONISTA, recepcionista.getTipoUsuario());
    }

    @Test
    @DisplayName("Debe lanzar excepción con datos nulos")
    void debeLanzarExcepcionConDatosNulos() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            usuarioFactory.crearVeterinario(null)
        );
    }

    @Test
    @DisplayName("Debe lanzar excepción con datos vacíos")
    void debeLanzarExcepcionConDatosVacios() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            usuarioFactory.crearVeterinario(new HashMap<>())
        );
    }

    @Test
    @DisplayName("Debe lanzar excepción con campo obligatorio faltante")
    void debeLanzarExcepcionConCampoObligatorioFaltante() {
        // Arrange
        datosBase.remove("email");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            usuarioFactory.crearVeterinario(datosBase)
        );

        assertTrue(exception.getMessage().contains("email"));
    }

    @Test
    @DisplayName("Factory debe aplicar configuración específica por tipo")
    void factoryDebeAplicarConfiguracionEspecificaPorTipo() {
        // Act
        Usuario admin = usuarioFactory.crearAdministrador(datosBase);
        Usuario recepcionista = usuarioFactory.crearRecepcionista(datosBase);

        // Assert - Cada tipo tiene configuración diferente
        assertFalse(admin.getRequiereCambioPassword(), "Admin no requiere cambio de password");
        assertTrue(recepcionista.getRequiereCambioPassword(), "Recepcionista sí requiere cambio");
    }
}
