package com.veterinaria.unit.entity;

import com.veterinaria.domain.entity.patients.Cliente;
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
 * Tests unitarios para la entidad Cliente.
 * Verifica la gestión de propietarios y su relación con pacientes.
 *
 * @author Sistema Veterinaria
 */
@DisplayName("Cliente Entity Tests")
class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = Cliente.builder()
                .nombre("Juan")
                .apellido("Pérez")
                .dni("12345678")
                .email("juan.perez@example.com")
                .telefono("+573001234567")
                .direccion("Calle 123 #45-67")
                .ciudad("Bogotá")
                .departamento("Cundinamarca")
                .build();
    }

    @Test
    @DisplayName("Debe crear cliente correctamente con Builder")
    void debeCrearClienteCorrectamente() {
        // Assert
        assertNotNull(cliente);
        assertEquals("Juan", cliente.getNombre());
        assertEquals("Pérez", cliente.getApellido());
        assertEquals("12345678", cliente.getDni());
        assertEquals("juan.perez@example.com", cliente.getEmail());
        assertEquals("+573001234567", cliente.getTelefono());
        assertEquals("Calle 123 #45-67", cliente.getDireccion());
        assertEquals("Bogotá", cliente.getCiudad());
        assertEquals("Cundinamarca", cliente.getDepartamento());
    }

    @Test
    @DisplayName("Debe obtener nombre completo")
    void debeObtenerNombreCompleto() {
        // Act
        String nombreCompleto = cliente.getNombreCompleto();

        // Assert
        assertEquals("Juan Pérez", nombreCompleto);
    }

    @Test
    @DisplayName("Debe agregar paciente correctamente")
    void debeAgregarPacienteCorrectamente() {
        // Arrange
        Perro perro = Perro.builder()
                .nombre("Max")
                .especie(TipoEspecie.PERRO)
                .fechaNacimiento(LocalDate.now().minusYears(3))
                .sexo(Sexo.MACHO)
                .estado(EstadoPaciente.ACTIVO)
                .build();

        // Act
        cliente.agregarPaciente(perro);

        // Assert
        assertTrue(cliente.tienePacientes());
        assertEquals(1, cliente.getPacientes().size());
        assertEquals(perro, cliente.getPacientes().get(0));
        assertEquals(cliente, perro.getCliente()); // Verificar bidireccionalidad
    }

    @Test
    @DisplayName("Debe remover paciente correctamente")
    void debeRemoverPacienteCorrectamente() {
        // Arrange
        Perro perro = Perro.builder()
                .nombre("Max")
                .especie(TipoEspecie.PERRO)
                .fechaNacimiento(LocalDate.now().minusYears(3))
                .sexo(Sexo.MACHO)
                .estado(EstadoPaciente.ACTIVO)
                .build();

        cliente.agregarPaciente(perro);
        assertEquals(1, cliente.getPacientes().size());

        // Act
        cliente.removerPaciente(perro);

        // Assert
        assertFalse(cliente.tienePacientes());
        assertEquals(0, cliente.getPacientes().size());
        assertNull(perro.getCliente()); // Verificar bidireccionalidad
    }

    @Test
    @DisplayName("Debe contar pacientes activos correctamente")
    void debeContarPacientesActivos() {
        // Arrange
        Perro perroActivo1 = Perro.builder()
                .nombre("Max")
                .especie(TipoEspecie.PERRO)
                .fechaNacimiento(LocalDate.now().minusYears(3))
                .sexo(Sexo.MACHO)
                .estado(EstadoPaciente.ACTIVO)
                .build();

        Perro perroActivo2 = Perro.builder()
                .nombre("Rocky")
                .especie(TipoEspecie.PERRO)
                .fechaNacimiento(LocalDate.now().minusYears(5))
                .sexo(Sexo.MACHO)
                .estado(EstadoPaciente.ACTIVO)
                .build();

        Perro perroInactivo = Perro.builder()
                .nombre("Rex")
                .especie(TipoEspecie.PERRO)
                .fechaNacimiento(LocalDate.now().minusYears(10))
                .sexo(Sexo.MACHO)
                .estado(EstadoPaciente.INACTIVO)
                .build();

        cliente.agregarPaciente(perroActivo1);
        cliente.agregarPaciente(perroActivo2);
        cliente.agregarPaciente(perroInactivo);

        // Act
        long cantidadActivos = cliente.contarPacientesActivos();

        // Assert
        assertEquals(3, cliente.getPacientes().size());
        assertEquals(2, cantidadActivos);
    }

    @Test
    @DisplayName("Cliente sin pacientes debe retornar false en tienePacientes")
    void clienteSinPacientesDebeRetornarFalse() {
        // Assert
        assertFalse(cliente.tienePacientes());
        assertEquals(0, cliente.contarPacientesActivos());
    }

    @Test
    @DisplayName("No debe agregar paciente nulo")
    void noDebeAgregarPacienteNulo() {
        // Act
        cliente.agregarPaciente(null);

        // Assert
        assertFalse(cliente.tienePacientes());
        assertEquals(0, cliente.getPacientes().size());
    }

    @Test
    @DisplayName("No debe remover paciente nulo")
    void noDebeRemoverPacienteNulo() {
        // Arrange
        Perro perro = Perro.builder()
                .nombre("Max")
                .especie(TipoEspecie.PERRO)
                .fechaNacimiento(LocalDate.now().minusYears(3))
                .sexo(Sexo.MACHO)
                .build();
        cliente.agregarPaciente(perro);

        // Act
        cliente.removerPaciente(null);

        // Assert
        assertTrue(cliente.tienePacientes());
        assertEquals(1, cliente.getPacientes().size());
    }

    @Test
    @DisplayName("Debe mantener consistencia bidireccional al agregar múltiples pacientes")
    void debeManterConsistenciaBidireccional() {
        // Arrange
        Perro perro1 = Perro.builder()
                .nombre("Max")
                .especie(TipoEspecie.PERRO)
                .fechaNacimiento(LocalDate.now().minusYears(3))
                .sexo(Sexo.MACHO)
                .build();

        Perro perro2 = Perro.builder()
                .nombre("Luna")
                .especie(TipoEspecie.PERRO)
                .fechaNacimiento(LocalDate.now().minusYears(2))
                .sexo(Sexo.HEMBRA)
                .build();

        // Act
        cliente.agregarPaciente(perro1);
        cliente.agregarPaciente(perro2);

        // Assert
        assertEquals(2, cliente.getPacientes().size());
        assertEquals(cliente, perro1.getCliente());
        assertEquals(cliente, perro2.getCliente());
    }

    @Test
    @DisplayName("Debe inicializar lista de pacientes vacía por defecto")
    void debeInicializarListaPacientesVacia() {
        // Arrange
        Cliente nuevoCliente = Cliente.builder()
                .nombre("María")
                .apellido("García")
                .dni("87654321")
                .email("maria@example.com")
                .telefono("+573009876543")
                .direccion("Calle 456")
                .build();

        // Assert
        assertNotNull(nuevoCliente.getPacientes());
        assertEquals(0, nuevoCliente.getPacientes().size());
        assertFalse(nuevoCliente.tienePacientes());
    }
}
