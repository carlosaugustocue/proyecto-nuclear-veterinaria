package com.veterinaria.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veterinaria.application.dto.paciente.CreatePacienteRequest;
import com.veterinaria.application.dto.paciente.PacienteDTO;
import com.veterinaria.application.dto.paciente.UpdatePacienteRequest;
import com.veterinaria.application.service.PacienteService;
import com.veterinaria.domain.enums.EstadoPaciente;
import com.veterinaria.domain.enums.Sexo;
import com.veterinaria.domain.enums.TipoEspecie;
import com.veterinaria.infrastructure.exception.ResourceNotFoundException;
import com.veterinaria.infrastructure.security.filter.JwtAuthenticationFilter;
import com.veterinaria.infrastructure.security.jwt.JwtTokenProvider;
import com.veterinaria.presentation.controller.PacienteController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests unitarios para PacienteController.
 * Usa MockMvc para probar endpoints REST.
 *
 * @author Sistema Veterinaria
 */
@WebMvcTest(controllers = PacienteController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("PacienteController - Tests REST")
class PacienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PacienteService pacienteService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private PacienteDTO pacienteDTO;
    private CreatePacienteRequest createRequest;
    private UpdatePacienteRequest updateRequest;

    @BeforeEach
    void setUp() {
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

        createRequest = new CreatePacienteRequest(
            "Max",
            TipoEspecie.PERRO,
            "Labrador",
            LocalDate.now().minusYears(3),
            Sexo.MACHO,
            "Dorado",
            30.5,
            "123456789",
            "Paciente sano",
            null,
            1L
        );

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
    @DisplayName("POST /api/v1/pacientes - Debe crear paciente exitosamente")
    void debeCrearPacienteExitosamente() throws Exception {
        // Given
        when(pacienteService.crearPaciente(any(CreatePacienteRequest.class)))
            .thenReturn(pacienteDTO);

        // When/Then
        mockMvc.perform(post("/api/v1/pacientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.nombre", is("Max")))
            .andExpect(jsonPath("$.especie", is("PERRO")))
            .andExpect(jsonPath("$.raza", is("Labrador")))
            .andExpect(jsonPath("$.microchip", is("123456789")));
    }

    @Test
    @DisplayName("POST /api/v1/pacientes - Debe retornar 400 cuando datos inválidos")
    void debeRetornar400CuandoDatosInvalidos() throws Exception {
        // Given - Request sin nombre (campo requerido)
        CreatePacienteRequest requestInvalido = new CreatePacienteRequest(
            null, // nombre es @NotBlank
            TipoEspecie.PERRO,
            "Labrador",
            LocalDate.now().minusYears(3),
            Sexo.MACHO,
            "Dorado",
            30.5,
            "123456789",
            null,
            null,
            1L
        );

        // When/Then
        mockMvc.perform(post("/api/v1/pacientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestInvalido)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/v1/pacientes/{id} - Debe obtener paciente por ID")
    void debeObtenerPacientePorId() throws Exception {
        // Given
        when(pacienteService.obtenerPacientePorId(1L)).thenReturn(pacienteDTO);

        // When/Then
        mockMvc.perform(get("/api/v1/pacientes/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.nombre", is("Max")))
            .andExpect(jsonPath("$.especie", is("PERRO")));
    }

    @Test
    @DisplayName("GET /api/v1/pacientes/{id} - Debe retornar 404 cuando no existe")
    void debeRetornar404CuandoPacienteNoExiste() throws Exception {
        // Given
        when(pacienteService.obtenerPacientePorId(999L))
            .thenThrow(new ResourceNotFoundException("Paciente no encontrado con ID: 999"));

        // When/Then
        mockMvc.perform(get("/api/v1/pacientes/999"))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/v1/pacientes - Debe obtener todos los pacientes")
    void debeObtenerTodosLosPacientes() throws Exception {
        // Given
        List<PacienteDTO> pacientes = List.of(pacienteDTO, pacienteDTO);
        when(pacienteService.obtenerTodosLosPacientes()).thenReturn(pacientes);

        // When/Then
        mockMvc.perform(get("/api/v1/pacientes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].nombre", is("Max")))
            .andExpect(jsonPath("$[1].nombre", is("Max")));
    }

    @Test
    @DisplayName("PUT /api/v1/pacientes/{id} - Debe actualizar paciente exitosamente")
    void debeActualizarPacienteExitosamente() throws Exception {
        // Given
        PacienteDTO pacienteActualizado = new PacienteDTO(
            1L, "Max", TipoEspecie.PERRO, "Golden Retriever",
            LocalDate.now().minusYears(3), 3, Sexo.MACHO, "Dorado claro",
            32.0, EstadoPaciente.ACTIVO, null, "Observaciones actualizadas",
            "123456789", new PacienteDTO.ClienteResumenDTO(1L, "Juan Pérez", "+573001234567", "juan@example.com"),
            "Ejercicio diario, socialización", "Alimento para adultos", LocalDateTime.now(), LocalDateTime.now()
        );

        when(pacienteService.actualizarPaciente(eq(1L), any(UpdatePacienteRequest.class)))
            .thenReturn(pacienteActualizado);

        // When/Then
        mockMvc.perform(put("/api/v1/pacientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.raza", is("Golden Retriever")))
            .andExpect(jsonPath("$.color", is("Dorado claro")))
            .andExpect(jsonPath("$.pesoKg", is(32.0)));
    }

    @Test
    @DisplayName("DELETE /api/v1/pacientes/{id} - Debe eliminar paciente exitosamente")
    void debeEliminarPacienteExitosamente() throws Exception {
        // Given
        doNothing().when(pacienteService).eliminarPaciente(1L);

        // When/Then
        mockMvc.perform(delete("/api/v1/pacientes/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/v1/pacientes/buscar - Debe buscar por nombre")
    void debeBuscarPorNombre() throws Exception {
        // Given
        List<PacienteDTO> pacientes = List.of(pacienteDTO);
        when(pacienteService.buscarPorNombre("Max")).thenReturn(pacientes);

        // When/Then
        mockMvc.perform(get("/api/v1/pacientes/buscar")
                .param("nombre", "Max"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].nombre", is("Max")));
    }

    @Test
    @DisplayName("GET /api/v1/pacientes/especie/{especie} - Debe buscar por especie")
    void debeBuscarPorEspecie() throws Exception {
        // Given
        List<PacienteDTO> pacientes = List.of(pacienteDTO);
        when(pacienteService.buscarPorEspecie(TipoEspecie.PERRO)).thenReturn(pacientes);

        // When/Then
        mockMvc.perform(get("/api/v1/pacientes/especie/PERRO"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].especie", is("PERRO")));
    }

    @Test
    @DisplayName("GET /api/v1/pacientes/estado/{estado} - Debe buscar por estado")
    void debeBuscarPorEstado() throws Exception {
        // Given
        List<PacienteDTO> pacientes = List.of(pacienteDTO);
        when(pacienteService.buscarPorEstado(EstadoPaciente.ACTIVO)).thenReturn(pacientes);

        // When/Then
        mockMvc.perform(get("/api/v1/pacientes/estado/ACTIVO"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].estado", is("ACTIVO")));
    }

    @Test
    @DisplayName("GET /api/v1/pacientes/cliente/{clienteId} - Debe buscar por cliente")
    void debeBuscarPorCliente() throws Exception {
        // Given
        List<PacienteDTO> pacientes = List.of(pacienteDTO);
        when(pacienteService.buscarPorCliente(1L)).thenReturn(pacientes);

        // When/Then
        mockMvc.perform(get("/api/v1/pacientes/cliente/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("GET /api/v1/pacientes/cliente/{clienteId}/activos - Debe buscar activos por cliente")
    void debeBuscarActivosPorCliente() throws Exception {
        // Given
        List<PacienteDTO> pacientes = List.of(pacienteDTO);
        when(pacienteService.buscarPacientesActivosPorCliente(1L)).thenReturn(pacientes);

        // When/Then
        mockMvc.perform(get("/api/v1/pacientes/cliente/1/activos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].estado", is("ACTIVO")));
    }

    @Test
    @DisplayName("GET /api/v1/pacientes/microchip/{microchip} - Debe buscar por microchip")
    void debeBuscarPorMicrochip() throws Exception {
        // Given
        when(pacienteService.buscarPorMicrochip("123456789")).thenReturn(pacienteDTO);

        // When/Then
        mockMvc.perform(get("/api/v1/pacientes/microchip/123456789"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.microchip", is("123456789")))
            .andExpect(jsonPath("$.nombre", is("Max")));
    }

    @Test
    @DisplayName("PATCH /api/v1/pacientes/{id}/marcar-fallecido - Debe marcar como fallecido")
    void debeMarcarComoFallecido() throws Exception {
        // Given
        PacienteDTO pacienteFallecido = new PacienteDTO(
            1L, "Max", TipoEspecie.PERRO, "Labrador",
            LocalDate.now().minusYears(3), 3, Sexo.MACHO, "Dorado",
            30.5, EstadoPaciente.FALLECIDO, null, null, "123456789",
            new PacienteDTO.ClienteResumenDTO(1L, "Juan Pérez", "+573001234567", "juan@example.com"),
            "Ejercicio diario, socialización", "Alimento para adultos", LocalDateTime.now(), LocalDateTime.now()
        );

        when(pacienteService.marcarComoFallecido(1L)).thenReturn(pacienteFallecido);

        // When/Then
        mockMvc.perform(patch("/api/v1/pacientes/1/marcar-fallecido"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.estado", is("FALLECIDO")));
    }

    @Test
    @DisplayName("PATCH /api/v1/pacientes/{id}/reactivar - Debe reactivar paciente")
    void debeReactivarPaciente() throws Exception {
        // Given
        when(pacienteService.reactivarPaciente(1L)).thenReturn(pacienteDTO);

        // When/Then
        mockMvc.perform(patch("/api/v1/pacientes/1/reactivar"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.estado", is("ACTIVO")));
    }

    @Test
    @DisplayName("GET /api/v1/pacientes/contar/estado/{estado} - Debe contar por estado")
    void debeContarPorEstado() throws Exception {
        // Given
        when(pacienteService.contarPorEstado(EstadoPaciente.ACTIVO)).thenReturn(5L);

        // When/Then
        mockMvc.perform(get("/api/v1/pacientes/contar/estado/ACTIVO"))
            .andExpect(status().isOk())
            .andExpect(content().string("5"));
    }

    @Test
    @DisplayName("GET /api/v1/pacientes/filtrar - Debe filtrar por especie y estado")
    void debeFiltrarPorEspecieYEstado() throws Exception {
        // Given
        List<PacienteDTO> pacientes = List.of(pacienteDTO);
        when(pacienteService.buscarPorEspecieYEstado(TipoEspecie.PERRO, EstadoPaciente.ACTIVO))
            .thenReturn(pacientes);

        // When/Then
        mockMvc.perform(get("/api/v1/pacientes/filtrar")
                .param("especie", "PERRO")
                .param("estado", "ACTIVO"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].especie", is("PERRO")))
            .andExpect(jsonPath("$[0].estado", is("ACTIVO")));
    }
}
