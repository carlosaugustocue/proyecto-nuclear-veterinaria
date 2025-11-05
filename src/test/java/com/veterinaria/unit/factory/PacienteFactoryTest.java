package com.veterinaria.unit.factory;

import com.veterinaria.domain.entity.patients.Gato;
import com.veterinaria.domain.entity.patients.Paciente;
import com.veterinaria.domain.entity.patients.Perro;
import com.veterinaria.domain.enums.EstadoPaciente;
import com.veterinaria.domain.enums.Sexo;
import com.veterinaria.domain.enums.TipoEspecie;
import com.veterinaria.domain.factory.PacienteFactory;
import com.veterinaria.domain.factory.PacienteFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para PacienteFactory.
 * Patrón testeado: Factory Method
 *
 * @author Sistema Veterinaria
 */
@DisplayName("PacienteFactory Tests - Factory Method Pattern")
class PacienteFactoryTest {

    private PacienteFactory pacienteFactory;
    private Map<String, Object> datosBase;

    @BeforeEach
    void setUp() {
        pacienteFactory = new PacienteFactoryImpl();

        // Datos base para crear pacientes
        datosBase = new HashMap<>();
        datosBase.put("nombre", "Max");
        datosBase.put("fechaNacimiento", LocalDate.now().minusYears(3));
        datosBase.put("sexo", Sexo.MACHO);
        datosBase.put("raza", "Labrador");
        datosBase.put("color", "Dorado");
        datosBase.put("pesoKg", 30.5);
    }

    @Test
    @DisplayName("Debe crear perro correctamente")
    void debeCrearPerroCorrectamente() {
        // Act
        Paciente paciente = pacienteFactory.crearPerro(datosBase);

        // Assert
        assertNotNull(paciente);
        assertInstanceOf(Perro.class, paciente);
        assertEquals("Max", paciente.getNombre());
        assertEquals(TipoEspecie.PERRO, paciente.getEspecie());
        assertEquals("Labrador", paciente.getRaza());
        assertEquals(Sexo.MACHO, paciente.getSexo());
        assertEquals(30.5, paciente.getPesoKg());
        assertEquals(EstadoPaciente.ACTIVO, paciente.getEstado());
        assertEquals(3, paciente.calcularEdadAnios());
    }

    @Test
    @DisplayName("Debe crear gato correctamente")
    void debeCrearGatoCorrectamente() {
        // Arrange
        datosBase.put("nombre", "Luna");
        datosBase.put("raza", "Siamés");
        datosBase.put("sexo", Sexo.HEMBRA);
        datosBase.put("pesoKg", 4.5);
        datosBase.put("fechaNacimiento", LocalDate.now().minusYears(2));

        // Act
        Paciente paciente = pacienteFactory.crearGato(datosBase);

        // Assert
        assertNotNull(paciente);
        assertInstanceOf(Gato.class, paciente);
        assertEquals("Luna", paciente.getNombre());
        assertEquals(TipoEspecie.GATO, paciente.getEspecie());
        assertEquals("Siamés", paciente.getRaza());
        assertEquals(Sexo.HEMBRA, paciente.getSexo());
        assertEquals(4.5, paciente.getPesoKg());
        assertEquals(EstadoPaciente.ACTIVO, paciente.getEstado());
    }

    @Test
    @DisplayName("Debe crear paciente según tipo con Factory Method principal - Perro")
    void debeCrearPacienteSegunTipoPerro() {
        // Act
        Paciente perro = pacienteFactory.crearPaciente(TipoEspecie.PERRO, datosBase);

        // Assert
        assertNotNull(perro);
        assertInstanceOf(Perro.class, perro);
        assertEquals(TipoEspecie.PERRO, perro.getEspecie());
    }

    @Test
    @DisplayName("Debe crear paciente según tipo con Factory Method principal - Gato")
    void debeCrearPacienteSegunTipoGato() {
        // Act
        Paciente gato = pacienteFactory.crearPaciente(TipoEspecie.GATO, datosBase);

        // Assert
        assertNotNull(gato);
        assertInstanceOf(Gato.class, gato);
        assertEquals(TipoEspecie.GATO, gato.getEspecie());
    }

    @Test
    @DisplayName("Debe lanzar excepción con datos nulos")
    void debeLanzarExcepcionConDatosNulos() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            pacienteFactory.crearPerro(null)
        );
    }

    @Test
    @DisplayName("Debe lanzar excepción con datos vacíos")
    void debeLanzarExcepcionConDatosVacios() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            pacienteFactory.crearPerro(new HashMap<>())
        );
    }

    @Test
    @DisplayName("Debe lanzar excepción con campo obligatorio faltante")
    void debeLanzarExcepcionConCampoObligatorioFaltante() {
        // Arrange
        datosBase.remove("nombre");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> pacienteFactory.crearPerro(datosBase)
        );

        assertTrue(exception.getMessage().contains("nombre"));
    }

    @Test
    @DisplayName("Debe lanzar excepción con fecha de nacimiento futura")
    void debeLanzarExcepcionConFechaNacimientoFutura() {
        // Arrange
        datosBase.put("fechaNacimiento", LocalDate.now().plusDays(1));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> pacienteFactory.crearPerro(datosBase)
        );

        assertTrue(exception.getMessage().contains("futura"));
    }

    @Test
    @DisplayName("Debe lanzar UnsupportedOperationException para especies no soportadas")
    void debeLanzarExcepcionParaEspeciesNoSoportadas() {
        // Act & Assert - AVE
        UnsupportedOperationException exceptionAve = assertThrows(
            UnsupportedOperationException.class,
            () -> pacienteFactory.crearPaciente(TipoEspecie.AVE, datosBase)
        );
        assertTrue(exceptionAve.getMessage().contains("AVE"));
        assertTrue(exceptionAve.getMessage().contains("no está disponible"));

        // Act & Assert - REPTIL
        UnsupportedOperationException exceptionReptil = assertThrows(
            UnsupportedOperationException.class,
            () -> pacienteFactory.crearPaciente(TipoEspecie.REPTIL, datosBase)
        );
        assertTrue(exceptionReptil.getMessage().contains("REPTIL"));
    }

    @Test
    @DisplayName("Factory debe crear pacientes con estado ACTIVO por defecto")
    void factoryDebeCrearPacientesConEstadoActivo() {
        // Act
        Paciente perro = pacienteFactory.crearPerro(datosBase);
        Paciente gato = pacienteFactory.crearGato(datosBase);

        // Assert
        assertEquals(EstadoPaciente.ACTIVO, perro.getEstado());
        assertEquals(EstadoPaciente.ACTIVO, gato.getEstado());
        assertTrue(perro.estaActivo());
        assertTrue(gato.estaActivo());
    }

    @Test
    @DisplayName("Factory debe permitir campos opcionales vacíos")
    void factoryDebePermitirCamposOpcionalesVacios() {
        // Arrange - Solo campos obligatorios
        Map<String, Object> datosMinimos = new HashMap<>();
        datosMinimos.put("nombre", "Buddy");
        datosMinimos.put("fechaNacimiento", LocalDate.now().minusYears(1));
        datosMinimos.put("sexo", Sexo.MACHO);

        // Act
        Paciente perro = pacienteFactory.crearPerro(datosMinimos);

        // Assert
        assertNotNull(perro);
        assertEquals("Buddy", perro.getNombre());
        assertNull(perro.getRaza()); // Campo opcional
        assertNull(perro.getColor()); // Campo opcional
    }

    @Test
    @DisplayName("Factory debe procesar microchip opcional")
    void factoryDebeProcesarMicrochipOpcional() {
        // Arrange
        datosBase.put("microchip", "123456789");

        // Act
        Paciente perro = pacienteFactory.crearPerro(datosBase);

        // Assert
        assertEquals("123456789", perro.getMicrochip());
    }
}
