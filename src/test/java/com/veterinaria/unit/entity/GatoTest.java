package com.veterinaria.unit.entity;

import com.veterinaria.domain.entity.patients.Gato;
import com.veterinaria.domain.enums.EstadoPaciente;
import com.veterinaria.domain.enums.Sexo;
import com.veterinaria.domain.enums.TipoEspecie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para la entidad Gato.
 * Patrón testeado: Template Method Pattern
 *
 * @author Sistema Veterinaria
 */
@DisplayName("Gato Entity Tests - Template Method Pattern")
class GatoTest {

    private Gato gato;

    @BeforeEach
    void setUp() {
        gato = Gato.builder()
                .nombre("Luna")
                .especie(TipoEspecie.GATO)
                .raza("Siamés")
                .fechaNacimiento(LocalDate.now().minusYears(2))
                .sexo(Sexo.HEMBRA)
                .color("Gris")
                .pesoKg(4.5)
                .estado(EstadoPaciente.ACTIVO)
                .build();
    }

    @Test
    @DisplayName("Debe crear gato correctamente con Builder")
    void debeCrearGatoCorrectamente() {
        // Assert
        assertNotNull(gato);
        assertEquals("Luna", gato.getNombre());
        assertEquals(TipoEspecie.GATO, gato.getEspecie());
        assertEquals("Siamés", gato.getRaza());
        assertEquals(Sexo.HEMBRA, gato.getSexo());
        assertEquals(4.5, gato.getPesoKg());
        assertEquals(EstadoPaciente.ACTIVO, gato.getEstado());
        assertTrue(gato.estaActivo());
    }

    @Test
    @DisplayName("Debe calcular edad correctamente")
    void debeCalcularEdadCorrectamente() {
        // Arrange
        Gato gatito = Gato.builder()
                .nombre("Kitten")
                .especie(TipoEspecie.GATO)
                .fechaNacimiento(LocalDate.now().minusMonths(4))
                .sexo(Sexo.MACHO)
                .build();

        Gato adulto = Gato.builder()
                .nombre("Felix")
                .especie(TipoEspecie.GATO)
                .fechaNacimiento(LocalDate.now().minusYears(4))
                .sexo(Sexo.MACHO)
                .build();

        Gato senior = Gato.builder()
                .nombre("OldCat")
                .especie(TipoEspecie.GATO)
                .fechaNacimiento(LocalDate.now().minusYears(12))
                .sexo(Sexo.HEMBRA)
                .build();

        // Assert
        assertEquals(0, gatito.calcularEdadAnios());
        assertEquals(4, adulto.calcularEdadAnios());
        assertEquals(12, senior.calcularEdadAnios());
    }

    @Test
    @DisplayName("Debe obtener cuidados específicos para gatos")
    void debeObtenerCuidadosEspecificos() {
        // Act
        String cuidados = gato.obtenerCuidadosEspecificos();

        // Assert
        assertNotNull(cuidados);
        assertTrue(cuidados.contains("leucemia felina"));
        assertTrue(cuidados.contains("Desparasitación trimestral"));
        assertTrue(cuidados.contains("bolas de pelo"));
        assertTrue(cuidados.contains("arenero"));
    }

    @Test
    @DisplayName("Debe obtener dieta recomendada según edad - gatito")
    void debeObtenerDietaGatito() {
        // Arrange
        Gato gatito = Gato.builder()
                .nombre("Kitten")
                .especie(TipoEspecie.GATO)
                .fechaNacimiento(LocalDate.now().minusMonths(5))
                .sexo(Sexo.MACHO)
                .build();

        // Act
        String dieta = gatito.obtenerDietaRecomendada();

        // Assert
        assertNotNull(dieta);
        assertTrue(dieta.contains("gatito"));
        assertTrue(dieta.contains("proteína animal"));
    }

    @Test
    @DisplayName("Debe obtener dieta recomendada según edad - adulto")
    void debeObtenerDietaAdulto() {
        // Act
        String dieta = gato.obtenerDietaRecomendada();

        // Assert
        assertNotNull(dieta);
        assertTrue(dieta.contains("adulto"));
        assertTrue(dieta.contains("taurina"));
    }

    @Test
    @DisplayName("Debe obtener dieta recomendada según edad - senior")
    void debeObtenerDietaSenior() {
        // Arrange
        Gato senior = Gato.builder()
                .nombre("OldCat")
                .especie(TipoEspecie.GATO)
                .fechaNacimiento(LocalDate.now().minusYears(10))
                .sexo(Sexo.HEMBRA)
                .build();

        // Act
        String dieta = senior.obtenerDietaRecomendada();

        // Assert
        assertNotNull(dieta);
        assertTrue(dieta.contains("senior"));
        assertTrue(dieta.contains("fósforo"));
    }

    @Test
    @DisplayName("Debe marcar paciente como fallecido")
    void debeMarcarComoFallecido() {
        // Act
        gato.marcarComoFallecido();

        // Assert
        assertEquals(EstadoPaciente.FALLECIDO, gato.getEstado());
        assertFalse(gato.estaActivo());
    }

    @Test
    @DisplayName("Debe marcar paciente como inactivo")
    void debeMarcarComoInactivo() {
        // Act
        gato.marcarComoInactivo();

        // Assert
        assertEquals(EstadoPaciente.INACTIVO, gato.getEstado());
        assertFalse(gato.estaActivo());
    }

    @Test
    @DisplayName("Debe reactivar paciente inactivo")
    void debeReactivarPacienteInactivo() {
        // Arrange
        gato.marcarComoInactivo();
        assertEquals(EstadoPaciente.INACTIVO, gato.getEstado());

        // Act
        gato.reactivar();

        // Assert
        assertEquals(EstadoPaciente.ACTIVO, gato.getEstado());
        assertTrue(gato.estaActivo());
    }

    @Test
    @DisplayName("No debe reactivar paciente fallecido")
    void noDebeReactivarPacienteFallecido() {
        // Arrange
        gato.marcarComoFallecido();

        // Act & Assert
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> gato.reactivar()
        );

        assertTrue(exception.getMessage().contains("fallecido"));
        assertEquals(EstadoPaciente.FALLECIDO, gato.getEstado());
    }

    @Test
    @DisplayName("Template Method Pattern - debe implementar métodos abstractos")
    void templateMethodPatternDebeImplementarMetodosAbstractos() {
        // Assert - Verifica que los métodos del Template Method están implementados
        assertNotNull(gato.obtenerCuidadosEspecificos());
        assertNotNull(gato.obtenerDietaRecomendada());
        assertFalse(gato.obtenerCuidadosEspecificos().isEmpty());
        assertFalse(gato.obtenerDietaRecomendada().isEmpty());
    }

    @Test
    @DisplayName("Debe mantener estado activo por defecto")
    void debeManterEstadoActivoPorDefecto() {
        // Arrange
        Gato nuevoGato = Gato.builder()
                .nombre("Nuevo")
                .especie(TipoEspecie.GATO)
                .fechaNacimiento(LocalDate.now().minusYears(1))
                .sexo(Sexo.MACHO)
                .build();

        // Assert - El estado por defecto debe ser ACTIVO
        assertEquals(EstadoPaciente.ACTIVO, nuevoGato.getEstado());
        assertTrue(nuevoGato.estaActivo());
    }
}
