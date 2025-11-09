package com.veterinaria.integration.controller;

import com.veterinaria.application.dto.medical.CreateConsultaRequest;
import com.veterinaria.application.dto.medical.SignosVitalesRequest;
import com.veterinaria.application.repository.HistorialClinicoRepository;
import com.veterinaria.application.repository.UsuarioRepository;
import com.veterinaria.domain.entity.medical.HistorialClinico;
import com.veterinaria.domain.entity.patients.Perro;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.enums.Sexo;
import com.veterinaria.domain.enums.TipoEspecie;
import com.veterinaria.domain.enums.TipoUsuario;
import com.veterinaria.integration.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de integración para ConsultaController.
 * Verifica los endpoints REST completos incluyendo seguridad.
 *
 * @author Sistema Veterinaria
 */
@DisplayName("ConsultaController Integration Tests")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ConsultaControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HistorialClinicoRepository historialClinicoRepository;

    @Autowired
    private EntityManager entityManager;

    private Usuario veterinario;
    private HistorialClinico historialClinico;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();

        // Crear usuario veterinario
        veterinario = Usuario.builder()
                .username("vet_controller_test")
                .email("vet_controller@test.com")
                .password("$2a$12$encrypted.password.here")
                .nombre("Dr. Controller")
                .apellido("Test")
                .tipoUsuario(TipoUsuario.VETERINARIO)
                .cuentaBloqueada(false)
                .cuentaExpirada(false)
                .credencialesExpiradas(false)
                .intentosFallidos(0)
                .requiereCambioPassword(false)
                .build();
        veterinario.setIsActive(true);
        veterinario = usuarioRepository.save(veterinario);

        // Crear paciente
        Perro paciente = Perro.builder()
                .nombre("Toby")
                .especie(TipoEspecie.PERRO)
                .raza("Beagle")
                .fechaNacimiento(LocalDate.now().minusYears(4))
                .sexo(Sexo.MACHO)
                .pesoKg(12.0)
                .build();
        paciente.setIsActive(true);
        entityManager.persist(paciente);

        // Crear historial clínico
        historialClinico = HistorialClinico.builder()
                .paciente(paciente)
                .fechaApertura(LocalDate.now())
                .build();
        historialClinico.setIsActive(true);
        historialClinico = historialClinicoRepository.save(historialClinico);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @WithMockUser(authorities = {"CONSULTAS_CREAR", "CONSULTAS_VER"})
    @DisplayName("POST /api/v1/consultas - Debe crear consulta con autenticación")
    void debeCrearConsultaConAutenticacion() throws Exception {
        // Given
        CreateConsultaRequest request = CreateConsultaRequest.builder()
                .historialClinicoId(historialClinico.getId())
                .veterinarioId(veterinario.getId())
                .fechaConsulta(LocalDate.now())
                .motivo("Chequeo general")
                .anamnesis("Paciente activo, sin síntomas")
                .signosVitales(SignosVitalesRequest.builder()
                        .temperatura(38.2)
                        .pesoKg(12.5)
                        .frecuenciaCardiaca(90)
                        .frecuenciaRespiratoria(24)
                        .build())
                .build();

        // When & Then
        mockMvc.perform(post("/api/v1/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.motivo").value("Chequeo general"))
                .andExpect(jsonPath("$.anamnesis").value("Paciente activo, sin síntomas"))
                .andExpect(jsonPath("$.veterinarioNombre").value(containsString("Dr. Controller")))
                .andExpect(jsonPath("$.pacienteNombre").value("Toby"))
                .andExpect(jsonPath("$.estaEnCurso").value(true))
                .andExpect(jsonPath("$.estaFinalizada").value(false))
                .andExpect(jsonPath("$.signosVitales.temperatura").value(38.2))
                .andExpect(jsonPath("$.signosVitales.pesoKg").value(12.5));
    }

    @Test
    @DisplayName("POST /api/v1/consultas - Debe rechazar sin autenticación")
    void debeRechazarCrearConsultaSinAutenticacion() throws Exception {
        // Given
        CreateConsultaRequest request = CreateConsultaRequest.builder()
                .historialClinicoId(historialClinico.getId())
                .veterinarioId(veterinario.getId())
                .fechaConsulta(LocalDate.now())
                .motivo("Chequeo sin auth")
                .build();

        // When & Then
        mockMvc.perform(post("/api/v1/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"CONSULTAS_CREAR", "CONSULTAS_VER"})
    @DisplayName("GET /api/v1/consultas/{id} - Debe obtener consulta por ID")
    void debeObtenerConsultaPorId() throws Exception {
        // Given - Crear consulta primero
        CreateConsultaRequest createRequest = CreateConsultaRequest.builder()
                .historialClinicoId(historialClinico.getId())
                .veterinarioId(veterinario.getId())
                .fechaConsulta(LocalDate.now())
                .motivo("Consulta de prueba")
                .build();

        String responseCreate = mockMvc.perform(post("/api/v1/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(createRequest)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long consultaId = objectMapper.readTree(responseCreate).get("id").asLong();

        // When & Then
        mockMvc.perform(get("/api/v1/consultas/" + consultaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(consultaId))
                .andExpect(jsonPath("$.motivo").value("Consulta de prueba"));
    }

    @Test
    @WithMockUser(authorities = {"CONSULTAS_CREAR", "CONSULTAS_VER"})
    @DisplayName("GET /api/v1/consultas/paciente/{pacienteId} - Debe listar consultas por paciente")
    void debeListarConsultasPorPaciente() throws Exception {
        // Given - Crear dos consultas
        CreateConsultaRequest request1 = CreateConsultaRequest.builder()
                .historialClinicoId(historialClinico.getId())
                .veterinarioId(veterinario.getId())
                .fechaConsulta(LocalDate.now())
                .motivo("Primera consulta")
                .build();

        CreateConsultaRequest request2 = CreateConsultaRequest.builder()
                .historialClinicoId(historialClinico.getId())
                .veterinarioId(veterinario.getId())
                .fechaConsulta(LocalDate.now())
                .motivo("Segunda consulta")
                .build();

        mockMvc.perform(post("/api/v1/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request1)));

        mockMvc.perform(post("/api/v1/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request2)));

        // When & Then
        mockMvc.perform(get("/api/v1/consultas/paciente/" + historialClinico.getPaciente().getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].motivo", containsInAnyOrder("Primera consulta", "Segunda consulta")));
    }

    @Test
    @WithMockUser(authorities = {"CONSULTAS_CREAR", "CONSULTAS_VER"})
    @DisplayName("GET /api/v1/consultas/en-curso - Debe listar consultas en curso")
    void debeListarConsultasEnCurso() throws Exception {
        // Given
        CreateConsultaRequest request = CreateConsultaRequest.builder()
                .historialClinicoId(historialClinico.getId())
                .veterinarioId(veterinario.getId())
                .fechaConsulta(LocalDate.now())
                .motivo("Consulta en curso")
                .build();

        mockMvc.perform(post("/api/v1/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        // When & Then
        mockMvc.perform(get("/api/v1/consultas/en-curso"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].estaEnCurso").value(true));
    }

    @Test
    @WithMockUser(authorities = {"CONSULTAS_CREAR", "CONSULTAS_VER", "CONSULTAS_ELIMINAR"})
    @DisplayName("DELETE /api/v1/consultas/{id} - Debe eliminar consulta")
    void debeEliminarConsulta() throws Exception {
        // Given - Crear consulta primero
        CreateConsultaRequest createRequest = CreateConsultaRequest.builder()
                .historialClinicoId(historialClinico.getId())
                .veterinarioId(veterinario.getId())
                .fechaConsulta(LocalDate.now())
                .motivo("Consulta a eliminar")
                .build();

        String responseCreate = mockMvc.perform(post("/api/v1/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(createRequest)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long consultaId = objectMapper.readTree(responseCreate).get("id").asLong();

        // When & Then - Eliminar
        mockMvc.perform(delete("/api/v1/consultas/" + consultaId))
                .andExpect(status().isNoContent());

        // Verificar que no se puede obtener
        mockMvc.perform(get("/api/v1/consultas/" + consultaId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = {"CONSULTAS_CREAR"})
    @DisplayName("POST /api/v1/consultas - Debe validar request inválido")
    void debeValidarRequestInvalido() throws Exception {
        // Given - Request sin motivo (campo requerido)
        CreateConsultaRequest request = CreateConsultaRequest.builder()
                .historialClinicoId(historialClinico.getId())
                .veterinarioId(veterinario.getId())
                .fechaConsulta(LocalDate.now())
                // motivo es obligatorio y no se proporciona
                .build();

        // When & Then
        mockMvc.perform(post("/api/v1/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request)))
                .andExpect(status().isBadRequest());
    }
}
