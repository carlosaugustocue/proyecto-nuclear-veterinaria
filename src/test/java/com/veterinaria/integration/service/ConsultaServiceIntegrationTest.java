package com.veterinaria.integration.service;

import com.veterinaria.application.dto.medical.ConsultaDTO;
import com.veterinaria.application.dto.medical.CreateConsultaRequest;
import com.veterinaria.application.dto.medical.SignosVitalesRequest;
import com.veterinaria.application.dto.medical.UpdateConsultaRequest;
import com.veterinaria.application.repository.HistorialClinicoRepository;
import com.veterinaria.application.repository.UsuarioRepository;
import com.veterinaria.application.service.ConsultaService;
import com.veterinaria.domain.entity.medical.HistorialClinico;
import com.veterinaria.domain.entity.patients.Perro;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.enums.Sexo;
import com.veterinaria.domain.enums.TipoEspecie;
import com.veterinaria.domain.enums.TipoUsuario;
import com.veterinaria.application.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests de integración para ConsultaService.
 * Verifica la lógica de negocio completa con base de datos real.
 *
 * @author Sistema Veterinaria
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("ConsultaService Integration Tests")
class ConsultaServiceIntegrationTest {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private HistorialClinicoRepository historialClinicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EntityManager entityManager;

    private HistorialClinico historialClinico;
    private Usuario veterinario;

    @BeforeEach
    void setUp() {
        // Crear veterinario
        veterinario = Usuario.builder()
                .username("vet_service_test")
                .email("vet_service@test.com")
                .password("password")
                .nombre("Dr. Service")
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
                .nombre("Max")
                .especie(TipoEspecie.PERRO)
                .raza("Golden Retriever")
                .fechaNacimiento(LocalDate.now().minusYears(2))
                .sexo(Sexo.MACHO)
                .pesoKg(30.0)
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
    @DisplayName("Debe crear consulta exitosamente")
    void debeCrearConsultaExitosamente() {
        // Given
        CreateConsultaRequest request = CreateConsultaRequest.builder()
                .historialClinicoId(historialClinico.getId())
                .veterinarioId(veterinario.getId())
                .fechaConsulta(LocalDate.now())
                .motivo("Chequeo de rutina")
                .anamnesis("Paciente activo, comiendo bien")
                .signosVitales(SignosVitalesRequest.builder()
                        .temperatura(38.5)
                        .pesoKg(30.0)
                        .frecuenciaCardiaca(80)
                        .frecuenciaRespiratoria(20)
                        .build())
                .build();

        // When
        ConsultaDTO creada = consultaService.crear(request);

        // Then
        assertThat(creada).isNotNull();
        assertThat(creada.getId()).isNotNull();
        assertThat(creada.getMotivo()).isEqualTo("Chequeo de rutina");
        assertThat(creada.getVeterinarioNombre()).contains("Dr. Service");
        assertThat(creada.getPacienteNombre()).isEqualTo("Max");
        assertThat(creada.getEstaEnCurso()).isTrue();
        assertThat(creada.getEstaFinalizada()).isFalse();
        assertThat(creada.getSignosVitales()).isNotNull();
        assertThat(creada.getSignosVitales().getTemperatura()).isEqualTo(38.5);
    }

    @Test
    @DisplayName("Debe obtener consulta por ID")
    void debeObtenerConsultaPorId() {
        // Given
        CreateConsultaRequest request = CreateConsultaRequest.builder()
                .historialClinicoId(historialClinico.getId())
                .veterinarioId(veterinario.getId())
                .fechaConsulta(LocalDate.now())
                .motivo("Control post-operatorio")
                .build();
        ConsultaDTO creada = consultaService.crear(request);

        // When
        ConsultaDTO obtenida = consultaService.obtenerPorId(creada.getId());

        // Then
        assertThat(obtenida).isNotNull();
        assertThat(obtenida.getId()).isEqualTo(creada.getId());
        assertThat(obtenida.getMotivo()).isEqualTo("Control post-operatorio");
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando consulta no existe")
    void debeLanzarExcepcionCuandoConsultaNoExiste() {
        // When & Then
        assertThatThrownBy(() -> consultaService.obtenerPorId(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Consulta no encontrada");
    }

    @Test
    @DisplayName("Debe actualizar consulta")
    void debeActualizarConsulta() {
        // Given
        CreateConsultaRequest createRequest = CreateConsultaRequest.builder()
                .historialClinicoId(historialClinico.getId())
                .veterinarioId(veterinario.getId())
                .fechaConsulta(LocalDate.now())
                .motivo("Consulta inicial")
                .build();
        ConsultaDTO creada = consultaService.crear(createRequest);

        UpdateConsultaRequest updateRequest = UpdateConsultaRequest.builder()
                .motivo("Consulta inicial - Actualizada")
                .anamnesis("Paciente presenta tos leve")
                .examenFisico("Auscultación: sonidos pulmonares normales")
                .observaciones("Se recomienda reposo")
                .planTratamiento("Reposo y antihistamínico")
                .build();

        // When
        ConsultaDTO actualizada = consultaService.actualizar(creada.getId(), updateRequest);

        // Then
        assertThat(actualizada.getId()).isEqualTo(creada.getId());
        assertThat(actualizada.getMotivo()).isEqualTo("Consulta inicial - Actualizada");
        assertThat(actualizada.getAnamnesis()).isEqualTo("Paciente presenta tos leve");
        assertThat(actualizada.getExamenFisico()).isEqualTo("Auscultación: sonidos pulmonares normales");
    }

    @Test
    @DisplayName("Debe listar consultas por paciente")
    void debeListarConsultasPorPaciente() {
        // Given
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

        consultaService.crear(request1);
        consultaService.crear(request2);

        // When
        List<ConsultaDTO> consultas = consultaService.listarPorPaciente(
                historialClinico.getPaciente().getId());

        // Then
        assertThat(consultas).hasSize(2);
        assertThat(consultas).extracting("motivo")
                .containsExactlyInAnyOrder("Primera consulta", "Segunda consulta");
    }

    @Test
    @DisplayName("Debe listar consultas en curso")
    void debeListarConsultasEnCurso() {
        // Given
        CreateConsultaRequest request = CreateConsultaRequest.builder()
                .historialClinicoId(historialClinico.getId())
                .veterinarioId(veterinario.getId())
                .fechaConsulta(LocalDate.now())
                .motivo("Consulta en curso")
                .build();
        consultaService.crear(request);

        // When
        List<ConsultaDTO> consultasEnCurso = consultaService.listarEnCurso();

        // Then
        assertThat(consultasEnCurso).hasSize(1);
        assertThat(consultasEnCurso.get(0).getMotivo()).isEqualTo("Consulta en curso");
        assertThat(consultasEnCurso.get(0).getEstaEnCurso()).isTrue();
    }

    @Test
    @DisplayName("Debe eliminar consulta (soft delete)")
    void debeEliminarConsulta() {
        // Given
        CreateConsultaRequest request = CreateConsultaRequest.builder()
                .historialClinicoId(historialClinico.getId())
                .veterinarioId(veterinario.getId())
                .fechaConsulta(LocalDate.now())
                .motivo("Consulta a eliminar")
                .build();
        ConsultaDTO creada = consultaService.crear(request);

        // When
        consultaService.eliminar(creada.getId());

        // Then
        assertThatThrownBy(() -> consultaService.obtenerPorId(creada.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("Debe validar signos vitales al crear consulta")
    void debeValidarSignosVitales() {
        // Given
        CreateConsultaRequest request = CreateConsultaRequest.builder()
                .historialClinicoId(historialClinico.getId())
                .veterinarioId(veterinario.getId())
                .fechaConsulta(LocalDate.now())
                .motivo("Chequeo con signos vitales completos")
                .signosVitales(SignosVitalesRequest.builder()
                        .temperatura(38.7)
                        .pesoKg(30.5)
                        .frecuenciaCardiaca(85)
                        .frecuenciaRespiratoria(22)
                        .condicionCorporal(5)
                        .build())
                .build();

        // When
        ConsultaDTO creada = consultaService.crear(request);

        // Then
        assertThat(creada.getSignosVitales()).isNotNull();
        assertThat(creada.getSignosVitales().getTemperatura()).isEqualTo(38.7);
        assertThat(creada.getSignosVitales().getPesoKg()).isEqualTo(30.5);
        assertThat(creada.getSignosVitales().getFrecuenciaCardiaca()).isEqualTo(85);
        assertThat(creada.getSignosVitales().getFrecuenciaRespiratoria()).isEqualTo(22);
        assertThat(creada.getSignosVitales().getCondicionCorporal()).isEqualTo(5);
    }
}
