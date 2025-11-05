package com.veterinaria.unit.entity;

import com.veterinaria.domain.entity.patients.Perro;
import com.veterinaria.domain.enums.EstadoPaciente;
import com.veterinaria.domain.enums.Sexo;
import com.veterinaria.domain.enums.TipoEspecie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para la entidad Perro.
 * Patrón testeado: Template Method Pattern
 *
 * @author Sistema Veterinaria
 */
@DisplayName("Perro Entity Tests - Template Method Pattern")
class PerroTest {

    private Perro perro;

    @BeforeEach
    void setUp() {
        perro = Perro.builder()
                .nombre("Max")
                .especie(TipoEspecie.PERRO)
                .raza("Labrador")
                .fechaNacimiento(LocalDate.now().minusYears(3))
                .sexo(Sexo.MACHO)
                .color("Dorado")
                .pesoKg(30.5)
                .estado(EstadoPaciente.ACTIVO)
                .build();
    }

    @Test
    @DisplayName("Debe crear perro correctamente con Builder")
    void debeCrearPerroCorrectamente() {
        // Assert
        assertNotNull(perro);
        assertEquals("Max", perro.getNombre());
        assertEquals(TipoEspecie.PERRO, perro.getEspecie());
        assertEquals("Labrador", perro.getRaza());
        assertEquals(Sexo.MACHO, perro.getSexo());
        assertEquals(30.5, perro.getPesoKg());
        assertEquals(EstadoPaciente.ACTIVO, perro.getEstado());
        assertTrue(perro.estaActivo());
    }

    @Test
    @DisplayName("Debe calcular edad correctamente")
    void debeCalcularEdadCorrectamente() {
        // Arrange
        Perro cachorro = Perro.builder()
                .nombre("Puppy")
                .especie(TipoEspecie.PERRO)
                .fechaNacimiento(LocalDate.now().minusMonths(6))
                .sexo(Sexo.HEMBRA)
                .build();

        Perro adulto = Perro.builder()
                .nombre("Rex")
                .especie(TipoEspecie.PERRO)
                .fechaNacimiento(LocalDate.now().minusYears(5))
                .sexo(Sexo.MACHO)
                .build();

        Perro senior = Perro.builder()
                .nombre("Old")
                .especie(TipoEspecie.PERRO)
                .fechaNacimiento(LocalDate.now().minusYears(10))
                .sexo(Sexo.MACHO)
                .build();

        // Assert
        assertEquals(0, cachorro.calcularEdadAnios());
        assertEquals(5, adulto.calcularEdadAnios());
        assertEquals(10, senior.calcularEdadAnios());
    }

    @Test
    @DisplayName("Debe obtener cuidados específicos para perros")
    void debeObtenerCuidadosEspecificos() {
        // Act
        String cuidados = perro.obtenerCuidadosEspecificos();

        // Assert
        assertNotNull(cuidados);
        assertTrue(cuidados.contains("Vacunación anual"));
        assertTrue(cuidados.contains("parvovirus"));
        assertTrue(cuidados.contains("Desparasitación trimestral"));
        assertTrue(cuidados.contains("Ejercicio diario"));
    }

    @Test
    @DisplayName("Debe obtener dieta recomendada según edad - cachorro")
    void debeObtenerDietaCachorro() {
        // Arrange
        Perro cachorro = Perro.builder()
                .nombre("Puppy")
                .especie(TipoEspecie.PERRO)
                .fechaNacimiento(LocalDate.now().minusMonths(6))
                .sexo(Sexo.HEMBRA)
                .build();

        // Act
        String dieta = cachorro.obtenerDietaRecomendada();

        // Assert
        assertNotNull(dieta);
        assertTrue(dieta.contains("cachorro"));
        assertTrue(dieta.contains("proteínas"));
    }

    @Test
    @DisplayName("Debe obtener dieta recomendada según edad - adulto")
    void debeObtenerDietaAdulto() {
        // Arrange
        Perro adulto = Perro.builder()
                .nombre("Max")
                .especie(TipoEspecie.PERRO)
                .fechaNacimiento(LocalDate.now().minusYears(3))
                .sexo(Sexo.MACHO)
                .build();

        // Act
        String dieta = adulto.obtenerDietaRecomendada();

        // Assert
        assertNotNull(dieta);
        assertTrue(dieta.contains("adulto"));
        assertTrue(dieta.contains("2 veces al día"));
    }

    @Test
    @DisplayName("Debe obtener dieta recomendada según edad - senior")
    void debeObtenerDietaSenior() {
        // Arrange
        Perro senior = Perro.builder()
                .nombre("Old")
                .especie(TipoEspecie.PERRO)
                .fechaNacimiento(LocalDate.now().minusYears(10))
                .sexo(Sexo.MACHO)
                .build();

        // Act
        String dieta = senior.obtenerDietaRecomendada();

        // Assert
        assertNotNull(dieta);
        assertTrue(dieta.contains("senior"));
        assertTrue(dieta.contains("bajo en grasas"));
    }

    @Test
    @DisplayName("Debe marcar paciente como fallecido")
    void debeMarcarComoFallecido() {
        // Act
        perro.marcarComoFallecido();

        // Assert
        assertEquals(EstadoPaciente.FALLECIDO, perro.getEstado());
        assertFalse(perro.estaActivo());
    }

    @Test
    @DisplayName("Debe marcar paciente como inactivo")
    void debeMarcarComoInactivo() {
        // Act
        perro.marcarComoInactivo();

        // Assert
        assertEquals(EstadoPaciente.INACTIVO, perro.getEstado());
        assertFalse(perro.estaActivo());
    }

    @Test
    @DisplayName("Debe reactivar paciente inactivo")
    void debeReactivarPacienteInactivo() {
        // Arrange
        perro.marcarComoInactivo();
        assertEquals(EstadoPaciente.INACTIVO, perro.getEstado());

        // Act
        perro.reactivar();

        // Assert
        assertEquals(EstadoPaciente.ACTIVO, perro.getEstado());
        assertTrue(perro.estaActivo());
    }

    @Test
    @DisplayName("No debe reactivar paciente fallecido")
    void noDebeReactivarPacienteFallecido() {
        // Arrange
        perro.marcarComoFallecido();

        // Act & Assert
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> perro.reactivar()
        );

        assertTrue(exception.getMessage().contains("fallecido"));
        assertEquals(EstadoPaciente.FALLECIDO, perro.getEstado());
    }

    @Test
    @DisplayName("Estado activo debe poder agendar citas")
    void estadoActivoPuedeAgendarCitas() {
        // Assert
        assertTrue(perro.getEstado().isPuedeAgendarCitas());
        assertTrue(perro.getEstado().puedeRecibirAtencion());
    }

    @Test
    @DisplayName("Estado inactivo no debe poder agendar citas")
    void estadoInactivoNoPuedeAgendarCitas() {
        // Arrange
        perro.marcarComoInactivo();

        // Assert
        assertFalse(perro.getEstado().isPuedeAgendarCitas());
        assertFalse(perro.getEstado().puedeRecibirAtencion());
    }

    @Test
    @DisplayName("Debe calcular edad cero con fecha nula")
    void debeCalcularEdadCeroConFechaNula() {
        // Arrange
        Perro perroSinFecha = Perro.builder()
                .nombre("Test")
                .especie(TipoEspecie.PERRO)
                .sexo(Sexo.MACHO)
                .build();

        // Act
        int edad = perroSinFecha.calcularEdadAnios();

        // Assert
        assertEquals(0, edad);
    }
}
