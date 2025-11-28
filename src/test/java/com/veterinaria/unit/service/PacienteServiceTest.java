package com.veterinaria.unit.service;

import com.veterinaria.application.dto.patients.CreatePacienteRequest;
import com.veterinaria.application.dto.patients.PacienteDTO;
import com.veterinaria.application.dto.patients.UpdatePacienteRequest;
import com.veterinaria.application.mapper.PacienteMapper;
import com.veterinaria.application.repository.ClienteRepository;
import com.veterinaria.application.repository.PacienteRepository;
import com.veterinaria.application.service.impl.PacienteServiceImpl;
import com.veterinaria.domain.entity.patients.Cliente;
import com.veterinaria.domain.entity.patients.Gato;
import com.veterinaria.domain.entity.patients.Paciente;
import com.veterinaria.domain.entity.patients.Perro;
import com.veterinaria.domain.enums.EstadoPaciente;
import com.veterinaria.domain.enums.Sexo;
import com.veterinaria.domain.enums.TipoEspecie;
import com.veterinaria.domain.factory.PacienteFactory;
import com.veterinaria.infrastructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios para PacienteServiceImpl.
 * Usa Mockito para aislar la lógica de negocio.
 *
 * @author Sistema Veterinaria
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("PacienteService - Tests Unitarios")
class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PacienteMapper pacienteMapper;

    @Mock
    private PacienteFactory pacienteFactory;

    @InjectMocks
    private PacienteServiceImpl pacienteService;

    private Cliente cliente;
    private Paciente perro;
    private Paciente gato;
    private PacienteDTO pacienteDTO;
    private CreatePacienteRequest createRequest;
    private UpdatePacienteRequest updateRequest;

    @BeforeEach
    void setUp() {
        // Crear cliente de prueba
        cliente = Cliente.builder()
            .id(1L)
            .nombre("Juan")
            .apellido("Pérez")
            .dni("12345678")
            .email("juan@example.com")
            .build();

        // Crear perro de prueba
        perro = Perro.builder()
            .id(1L)
            .nombre("Max")
            .especie(TipoEspecie.PERRO)
            .raza("Labrador")
            .fechaNacimiento(LocalDate.now().minusYears(3))
            .sexo(Sexo.MACHO)
            .color("Dorado")
            .pesoKg(30.5)
            .estado(EstadoPaciente.ACTIVO)
            .microchip("123456789")
            .cliente(cliente)
            .build();

        // Crear gato de prueba
        gato = Gato.builder()
            .id(2L)
            .nombre("Luna")
            .especie(TipoEspecie.GATO)
            .raza("Persa")
            .fechaNacimiento(LocalDate.now().minusYears(2))
            .sexo(Sexo.HEMBRA)
            .color("Blanco")
            .pesoKg(4.5)
            .estado(EstadoPaciente.ACTIVO)
            .microchip("987654321")
            .cliente(cliente)
            .build();

        // Crear DTO de prueba
        pacienteDTO = new PacienteDTO(
            1L,
            "Max",
            TipoEspecie.PERRO,
            "Labrador",
            LocalDate.now().minusYears(3),
            3,
            Sexo.MACHO,
            "Dorado",
            30.5,
            EstadoPaciente.ACTIVO,
            null,
            null,
            "123456789",
            new PacienteDTO.ClienteResumenDTO(1L, "Juan Pérez", "+573001234567", "juan@example.com"),
            "Ejercicio diario, socialización, paseos regulares",
            "Alimento para adultos",
            LocalDateTime.now(),
            LocalDateTime.now()
        );

        // Crear request de creación
        createRequest = new CreatePacienteRequest(
            "Max",
            TipoEspecie.PERRO,
            "Labrador",
            LocalDate.now().minusYears(3),
            Sexo.MACHO,
            "Dorado",
            30.5,
            null,
            "Paciente sano",
            "123456789",
            1L
        );

        // Crear request de actualización
        updateRequest = new UpdatePacienteRequest(
            "Golden Retriever",
            "Dorado claro",
            32.0,
            EstadoPaciente.ACTIVO,
            null,
            "Observaciones actualizadas",
            null
        );
    }

    @Test
    @DisplayName("Debe crear paciente exitosamente cuando los datos son válidos")
    void debeCrearPacienteExitosamente() {
        // Given
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(pacienteRepository.existsByMicrochip(anyString())).thenReturn(false);
        when(pacienteFactory.crearPaciente(any(TipoEspecie.class), anyMap())).thenReturn(perro);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        when(pacienteMapper.toDTO(any(Paciente.class))).thenReturn(pacienteDTO);

        // When
        PacienteDTO resultado = pacienteService.crearPaciente(createRequest);

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.nombre()).isEqualTo("Max");
        assertThat(resultado.especie()).isEqualTo(TipoEspecie.PERRO);

        verify(clienteRepository).findById(1L);
        verify(pacienteRepository).existsByMicrochip("123456789");
        verify(pacienteFactory).crearPaciente(eq(TipoEspecie.PERRO), anyMap());
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el cliente no existe")
    void debeLanzarExcepcionCuandoClienteNoExiste() {
        // Given
        when(clienteRepository.findById(999L)).thenReturn(Optional.empty());

        CreatePacienteRequest requestConClienteInvalido = new CreatePacienteRequest(
            "Max", TipoEspecie.PERRO, "Labrador", LocalDate.now().minusYears(3),
            Sexo.MACHO, "Dorado", 30.5, "123456789", null, null, 999L
        );

        // When/Then
        assertThatThrownBy(() -> pacienteService.crearPaciente(requestConClienteInvalido))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Cliente no encontrado con ID: 999");

        verify(clienteRepository).findById(999L);
        verify(pacienteFactory, never()).crearPaciente(any(), anyMap());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el microchip ya existe")
    void debeLanzarExcepcionCuandoMicrochipYaExiste() {
        // Given
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(pacienteRepository.existsByMicrochip("123456789")).thenReturn(true);

        // When/Then
        assertThatThrownBy(() -> pacienteService.crearPaciente(createRequest))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Ya existe un paciente con el microchip");

        verify(pacienteRepository).existsByMicrochip("123456789");
        verify(pacienteFactory, never()).crearPaciente(any(), anyMap());
    }

    @Test
    @DisplayName("Debe obtener paciente por ID exitosamente")
    void debeObtenerPacientePorIdExitosamente() {
        // Given
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(perro));
        when(pacienteMapper.toDTO(any(Paciente.class))).thenReturn(pacienteDTO);

        // When
        PacienteDTO resultado = pacienteService.obtenerPacientePorId(1L);

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.id()).isEqualTo(1L);
        assertThat(resultado.nombre()).isEqualTo("Max");

        verify(pacienteRepository).findById(1L);
        verify(pacienteMapper).toDTO(perro);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando paciente no existe por ID")
    void debeLanzarExcepcionCuandoPacienteNoExistePorId() {
        // Given
        when(pacienteRepository.findById(999L)).thenReturn(Optional.empty());

        // When/Then
        assertThatThrownBy(() -> pacienteService.obtenerPacientePorId(999L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Paciente no encontrado con ID: 999");

        verify(pacienteRepository).findById(999L);
        verify(pacienteMapper, never()).toDTO(any(Paciente.class));
    }

    @Test
    @DisplayName("Debe obtener todos los pacientes")
    void debeObtenerTodosLosPacientes() {
        // Given
        List<Paciente> pacientes = List.of(perro, gato);
        when(pacienteRepository.findAll()).thenReturn(pacientes);
        when(pacienteMapper.toDTO(any(Paciente.class))).thenReturn(pacienteDTO);

        // When
        List<PacienteDTO> resultado = pacienteService.obtenerTodosLosPacientes();

        // Then
        assertThat(resultado).hasSize(2);
        verify(pacienteRepository).findAll();
        verify(pacienteMapper, times(2)).toDTO(any(Paciente.class));
    }

    @Test
    @DisplayName("Debe actualizar paciente exitosamente")
    void debeActualizarPacienteExitosamente() {
        // Given
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(perro));
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(perro);
        when(pacienteMapper.toDTO(any(Paciente.class))).thenReturn(pacienteDTO);

        // When
        PacienteDTO resultado = pacienteService.actualizarPaciente(1L, updateRequest);

        // Then
        assertThat(resultado).isNotNull();
        verify(pacienteRepository).findById(1L);
        verify(pacienteRepository).save(any(Paciente.class));
        verify(pacienteMapper).toDTO(any(Paciente.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción al actualizar con cliente inexistente")
    void debeLanzarExcepcionAlActualizarConClienteInexistente() {
        // Given
        UpdatePacienteRequest requestConClienteInvalido = new UpdatePacienteRequest(
            "Golden Retriever", "Dorado", 32.0, EstadoPaciente.ACTIVO, null, "Observaciones", 999L
        );

        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(perro));
        when(clienteRepository.findById(999L)).thenReturn(Optional.empty());

        // When/Then
        assertThatThrownBy(() -> pacienteService.actualizarPaciente(1L, requestConClienteInvalido))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Cliente no encontrado con ID: 999");

        verify(pacienteRepository, never()).save(any(Paciente.class));
    }

    @Test
    @DisplayName("Debe eliminar paciente exitosamente")
    void debeEliminarPacienteExitosamente() {
        // Given
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(perro));

        // When
        pacienteService.eliminarPaciente(1L);

        // Then
        verify(pacienteRepository).findById(1L);
        verify(pacienteRepository).delete(perro);
    }

    @Test
    @DisplayName("Debe buscar pacientes por nombre")
    void debeBuscarPacientesPorNombre() {
        // Given
        List<Paciente> pacientes = List.of(perro);
        when(pacienteRepository.findByNombreContainingIgnoreCase("Max")).thenReturn(pacientes);
        when(pacienteMapper.toDTO(any(Paciente.class))).thenReturn(pacienteDTO);

        // When
        List<PacienteDTO> resultado = pacienteService.buscarPorNombre("Max");

        // Then
        assertThat(resultado).hasSize(1);
        verify(pacienteRepository).findByNombreContainingIgnoreCase("Max");
    }

    @Test
    @DisplayName("Debe buscar pacientes por especie")
    void debeBuscarPacientesPorEspecie() {
        // Given
        List<Paciente> pacientes = List.of(perro);
        when(pacienteRepository.findByEspecie(TipoEspecie.PERRO)).thenReturn(pacientes);
        when(pacienteMapper.toDTO(any(Paciente.class))).thenReturn(pacienteDTO);

        // When
        List<PacienteDTO> resultado = pacienteService.buscarPorEspecie(TipoEspecie.PERRO);

        // Then
        assertThat(resultado).hasSize(1);
        verify(pacienteRepository).findByEspecie(TipoEspecie.PERRO);
    }

    @Test
    @DisplayName("Debe buscar pacientes por estado")
    void debeBuscarPacientesPorEstado() {
        // Given
        List<Paciente> pacientes = List.of(perro, gato);
        when(pacienteRepository.findByEstado(EstadoPaciente.ACTIVO)).thenReturn(pacientes);
        when(pacienteMapper.toDTO(any(Paciente.class))).thenReturn(pacienteDTO);

        // When
        List<PacienteDTO> resultado = pacienteService.buscarPorEstado(EstadoPaciente.ACTIVO);

        // Then
        assertThat(resultado).hasSize(2);
        verify(pacienteRepository).findByEstado(EstadoPaciente.ACTIVO);
    }

    @Test
    @DisplayName("Debe buscar pacientes por cliente")
    void debeBuscarPacientesPorCliente() {
        // Given
        when(clienteRepository.existsById(1L)).thenReturn(true);
        List<Paciente> pacientes = List.of(perro, gato);
        when(pacienteRepository.findByClienteId(1L)).thenReturn(pacientes);
        when(pacienteMapper.toDTO(any(Paciente.class))).thenReturn(pacienteDTO);

        // When
        List<PacienteDTO> resultado = pacienteService.buscarPorCliente(1L);

        // Then
        assertThat(resultado).hasSize(2);
        verify(clienteRepository).existsById(1L);
        verify(pacienteRepository).findByClienteId(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción al buscar pacientes de cliente inexistente")
    void debeLanzarExcepcionAlBuscarPacientesDeClienteInexistente() {
        // Given
        when(clienteRepository.existsById(999L)).thenReturn(false);

        // When/Then
        assertThatThrownBy(() -> pacienteService.buscarPorCliente(999L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Cliente no encontrado con ID: 999");

        verify(pacienteRepository, never()).findByClienteId(any());
    }

    @Test
    @DisplayName("Debe buscar pacientes activos por cliente")
    void debeBuscarPacientesActivosPorCliente() {
        // Given
        when(clienteRepository.existsById(1L)).thenReturn(true);
        List<Paciente> pacientes = List.of(perro);
        when(pacienteRepository.findPacientesActivosByClienteId(1L)).thenReturn(pacientes);
        when(pacienteMapper.toDTO(any(Paciente.class))).thenReturn(pacienteDTO);

        // When
        List<PacienteDTO> resultado = pacienteService.buscarPacientesActivosPorCliente(1L);

        // Then
        assertThat(resultado).hasSize(1);
        verify(pacienteRepository).findPacientesActivosByClienteId(1L);
    }

    @Test
    @DisplayName("Debe buscar paciente por microchip")
    void debeBuscarPacientePorMicrochip() {
        // Given
        when(pacienteRepository.findByMicrochip("123456789")).thenReturn(Optional.of(perro));
        when(pacienteMapper.toDTO(any(Paciente.class))).thenReturn(pacienteDTO);

        // When
        PacienteDTO resultado = pacienteService.buscarPorMicrochip("123456789");

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.microchip()).isEqualTo("123456789");
        verify(pacienteRepository).findByMicrochip("123456789");
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando microchip no existe")
    void debeLanzarExcepcionCuandoMicrochipNoExiste() {
        // Given
        when(pacienteRepository.findByMicrochip("999999999")).thenReturn(Optional.empty());

        // When/Then
        assertThatThrownBy(() -> pacienteService.buscarPorMicrochip("999999999"))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Paciente no encontrado con microchip");
    }

    @Test
    @DisplayName("Debe marcar paciente como fallecido")
    void debeMarcarPacienteComoFallecido() {
        // Given
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(perro));
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(perro);
        when(pacienteMapper.toDTO(any(Paciente.class))).thenReturn(pacienteDTO);

        // When
        PacienteDTO resultado = pacienteService.marcarComoFallecido(1L);

        // Then
        assertThat(resultado).isNotNull();
        verify(pacienteRepository).findById(1L);
        verify(pacienteRepository).save(perro);
    }

    @Test
    @DisplayName("Debe reactivar paciente")
    void debeReactivarPaciente() {
        // Given
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(perro));
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(perro);
        when(pacienteMapper.toDTO(any(Paciente.class))).thenReturn(pacienteDTO);

        // When
        PacienteDTO resultado = pacienteService.reactivarPaciente(1L);

        // Then
        assertThat(resultado).isNotNull();
        verify(pacienteRepository).findById(1L);
        verify(pacienteRepository).save(perro);
    }

    @Test
    @DisplayName("Debe contar pacientes por estado")
    void debeContarPacientesPorEstado() {
        // Given
        when(pacienteRepository.countByEstado(EstadoPaciente.ACTIVO)).thenReturn(5L);

        // When
        long resultado = pacienteService.contarPorEstado(EstadoPaciente.ACTIVO);

        // Then
        assertThat(resultado).isEqualTo(5L);
        verify(pacienteRepository).countByEstado(EstadoPaciente.ACTIVO);
    }

    @Test
    @DisplayName("Debe buscar pacientes por especie y estado")
    void debeBuscarPacientesPorEspecieYEstado() {
        // Given
        List<Paciente> pacientes = List.of(perro);
        when(pacienteRepository.findByEspecieAndEstado(TipoEspecie.PERRO, EstadoPaciente.ACTIVO))
            .thenReturn(pacientes);
        when(pacienteMapper.toDTO(any(Paciente.class))).thenReturn(pacienteDTO);

        // When
        List<PacienteDTO> resultado = pacienteService.buscarPorEspecieYEstado(
            TipoEspecie.PERRO, EstadoPaciente.ACTIVO
        );

        // Then
        assertThat(resultado).hasSize(1);
        verify(pacienteRepository).findByEspecieAndEstado(TipoEspecie.PERRO, EstadoPaciente.ACTIVO);
    }
}
