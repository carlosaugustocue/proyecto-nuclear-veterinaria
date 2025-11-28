package com.veterinaria.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veterinaria.application.dto.patients.ClienteDTO;
import com.veterinaria.application.dto.patients.CreateClienteRequest;
import com.veterinaria.application.dto.patients.UpdateClienteRequest;
import com.veterinaria.application.service.ClienteService;
import com.veterinaria.infrastructure.exception.ResourceNotFoundException;
import com.veterinaria.infrastructure.security.filter.JwtAuthenticationFilter;
import com.veterinaria.infrastructure.security.jwt.JwtTokenProvider;
import com.veterinaria.presentation.controller.ClienteController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests unitarios para ClienteController.
 * Usa MockMvc para probar endpoints REST.
 *
 * @author Sistema Veterinaria
 */
@WebMvcTest(controllers = ClienteController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("ClienteController - Tests REST")
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService clienteService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private ClienteDTO clienteDTO;
    private CreateClienteRequest createRequest;
    private UpdateClienteRequest updateRequest;

    @BeforeEach
    void setUp() {
        clienteDTO = new ClienteDTO(
            1L,
            "Juan",
            "Pérez",
            "Juan Pérez",
            "12345678",
            "juan@example.com",
            "+573001234567",
            "Calle 123",
            "Bogotá",
            "Cundinamarca",
            "110111",
            "Cliente VIP",
            new ArrayList<>(),
            0L,
            LocalDateTime.now(),
            LocalDateTime.now()
        );

        createRequest = new CreateClienteRequest(
            "Juan",
            "Pérez",
            "12345678",
            "juan@example.com",
            "3001234567",
            "Calle 123",
            "Bogotá",
            "Cundinamarca",
            "110111",
            "Cliente VIP"
        );

        updateRequest = new UpdateClienteRequest(
            "juan.nuevo@example.com",
            "3009876543",
            "Calle 456",
            "Medellín",
            "Antioquia",
            "050001",
            "Observaciones actualizadas"
        );
    }

    @Test
    @DisplayName("POST /api/v1/clientes - Debe crear cliente exitosamente")
    void debeCrearClienteExitosamente() throws Exception {
        // Given
        when(clienteService.crearCliente(any(CreateClienteRequest.class)))
            .thenReturn(clienteDTO);

        // When/Then
        mockMvc.perform(post("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.nombre", is("Juan")))
            .andExpect(jsonPath("$.apellido", is("Pérez")))
            .andExpect(jsonPath("$.dni", is("12345678")))
            .andExpect(jsonPath("$.email", is("juan@example.com")));
    }

    @Test
    @DisplayName("POST /api/v1/clientes - Debe retornar 400 cuando datos inválidos")
    void debeRetornar400CuandoDatosInvalidos() throws Exception {
        // Given - Request sin nombre (campo requerido)
        CreateClienteRequest requestInvalido = new CreateClienteRequest(
            null, // nombre es @NotBlank
            "Pérez",
            "12345678",
            "juan@example.com",
            "3001234567",
            "Calle 123",
            "Bogotá",
            "Cundinamarca",
            "110111",
            null
        );

        // When/Then
        mockMvc.perform(post("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestInvalido)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/v1/clientes/{id} - Debe obtener cliente por ID")
    void debeObtenerClientePorId() throws Exception {
        // Given
        when(clienteService.obtenerClientePorId(1L)).thenReturn(clienteDTO);

        // When/Then
        mockMvc.perform(get("/api/v1/clientes/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.nombre", is("Juan")))
            .andExpect(jsonPath("$.nombreCompleto", is("Juan Pérez")));
    }

    @Test
    @DisplayName("GET /api/v1/clientes/{id} - Debe retornar 404 cuando no existe")
    void debeRetornar404CuandoClienteNoExiste() throws Exception {
        // Given
        when(clienteService.obtenerClientePorId(999L))
            .thenThrow(new ResourceNotFoundException("Cliente no encontrado con ID: 999"));

        // When/Then
        mockMvc.perform(get("/api/v1/clientes/999"))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/v1/clientes - Debe obtener todos los clientes")
    void debeObtenerTodosLosClientes() throws Exception {
        // Given
        List<ClienteDTO> clientes = List.of(clienteDTO, clienteDTO);
        when(clienteService.obtenerTodosLosClientes()).thenReturn(clientes);

        // When/Then
        mockMvc.perform(get("/api/v1/clientes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].nombre", is("Juan")))
            .andExpect(jsonPath("$[1].nombre", is("Juan")));
    }

    @Test
    @DisplayName("PUT /api/v1/clientes/{id} - Debe actualizar cliente exitosamente")
    void debeActualizarClienteExitosamente() throws Exception {
        // Given
        ClienteDTO clienteActualizado = new ClienteDTO(
            1L, "Juan", "Pérez", "Juan Pérez",
            "12345678", "juan.nuevo@example.com", "+573009876543",
            "Calle 456", "Medellín", "Antioquia", "050001",
            "Observaciones actualizadas", new ArrayList<>(), 0L,
            LocalDateTime.now(), LocalDateTime.now()
        );

        when(clienteService.actualizarCliente(eq(1L), any(UpdateClienteRequest.class)))
            .thenReturn(clienteActualizado);

        // When/Then
        mockMvc.perform(put("/api/v1/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email", is("juan.nuevo@example.com")))
            .andExpect(jsonPath("$.ciudad", is("Medellín")));
    }

    @Test
    @DisplayName("DELETE /api/v1/clientes/{id} - Debe eliminar cliente exitosamente")
    void debeEliminarClienteExitosamente() throws Exception {
        // Given
        doNothing().when(clienteService).eliminarCliente(1L);

        // When/Then
        mockMvc.perform(delete("/api/v1/clientes/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/v1/clientes/{id} - Debe retornar 400 cuando tiene pacientes activos")
    void debeRetornar400CuandoTienePacientesActivos() throws Exception {
        // Given
        doThrow(new IllegalStateException("No se puede eliminar el cliente porque tiene 2 pacientes activos"))
            .when(clienteService).eliminarCliente(1L);

        // When/Then
        mockMvc.perform(delete("/api/v1/clientes/1"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/v1/clientes/buscar - Debe buscar por nombre o apellido")
    void debeBuscarPorNombreOApellido() throws Exception {
        // Given
        List<ClienteDTO> clientes = List.of(clienteDTO);
        when(clienteService.buscarPorNombreOApellido("Juan")).thenReturn(clientes);

        // When/Then
        mockMvc.perform(get("/api/v1/clientes/buscar")
                .param("termino", "Juan"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].nombre", is("Juan")));
    }

    @Test
    @DisplayName("GET /api/v1/clientes/dni/{dni} - Debe buscar por DNI")
    void debeBuscarPorDni() throws Exception {
        // Given
        when(clienteService.buscarPorDni("12345678")).thenReturn(clienteDTO);

        // When/Then
        mockMvc.perform(get("/api/v1/clientes/dni/12345678"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.dni", is("12345678")))
            .andExpect(jsonPath("$.nombre", is("Juan")));
    }

    @Test
    @DisplayName("GET /api/v1/clientes/email - Debe buscar por email")
    void debeBuscarPorEmail() throws Exception {
        // Given
        when(clienteService.buscarPorEmail("juan@example.com")).thenReturn(clienteDTO);

        // When/Then
        mockMvc.perform(get("/api/v1/clientes/email")
                .param("email", "juan@example.com"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email", is("juan@example.com")));
    }

    @Test
    @DisplayName("GET /api/v1/clientes/con-pacientes-activos - Debe obtener clientes con pacientes activos")
    void debeObtenerClientesConPacientesActivos() throws Exception {
        // Given
        List<ClienteDTO> clientes = List.of(clienteDTO);
        when(clienteService.obtenerClientesConPacientesActivos()).thenReturn(clientes);

        // When/Then
        mockMvc.perform(get("/api/v1/clientes/con-pacientes-activos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("GET /api/v1/clientes/ciudad/{ciudad} - Debe buscar por ciudad")
    void debeBuscarPorCiudad() throws Exception {
        // Given
        List<ClienteDTO> clientes = List.of(clienteDTO);
        when(clienteService.buscarPorCiudad("Bogotá")).thenReturn(clientes);

        // When/Then
        mockMvc.perform(get("/api/v1/clientes/ciudad/Bogotá"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].ciudad", is("Bogotá")));
    }
}
