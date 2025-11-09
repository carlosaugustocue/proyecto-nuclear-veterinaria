package com.veterinaria.integration.repository;

import com.veterinaria.application.repository.ConsultaRepository;
import com.veterinaria.application.repository.HistorialClinicoRepository;
import com.veterinaria.application.repository.UsuarioRepository;
import com.veterinaria.domain.entity.medical.Consulta;
import com.veterinaria.domain.entity.medical.HistorialClinico;
import com.veterinaria.domain.entity.medical.SignosVitales;
import com.veterinaria.domain.entity.patients.Perro;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.enums.Sexo;
import com.veterinaria.domain.enums.TipoEspecie;
import com.veterinaria.domain.enums.TipoUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests de integración para ConsultaRepository.
 * Verifica las queries personalizadas del repositorio.
 *
 * @author Sistema Veterinaria
 */
@DataJpaTest
@ActiveProfiles("test")
@DisplayName("ConsultaRepository Integration Tests")
class ConsultaRepositoryIntegrationTest {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private HistorialClinicoRepository historialClinicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TestEntityManager entityManager;

    private HistorialClinico historialClinico;
    private Usuario veterinario;
    private Consulta consultaActiva;
    private Consulta consultaFinalizada;

    @BeforeEach
    void setUp() {
        // Crear veterinario
        veterinario = Usuario.builder()
                .username("vet_test")
                .email("vet@test.com")
                .password("password")
                .nombre("Dr. Test")
                .apellido("Veterinario")
                .tipoUsuario(TipoUsuario.VETERINARIO)
                .cuentaBloqueada(false)
                .cuentaExpirada(false)
                .credencialesExpiradas(false)
                .intentosFallidos(0)
                .requiereCambioPassword(false)
                .build();
        veterinario.setIsActive(true);
        veterinario = usuarioRepository.save(veterinario);

        // Crear paciente (Perro)
        Perro paciente = Perro.builder()
                .nombre("Rex")
                .especie(TipoEspecie.PERRO)
                .raza("Labrador")
                .fechaNacimiento(LocalDate.now().minusYears(3))
                .sexo(Sexo.MACHO)
                .pesoKg(25.0)
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

        // Crear consulta activa (en curso)
        consultaActiva = Consulta.builder()
                .historialClinico(historialClinico)
                .veterinario(veterinario)
                .fechaConsulta(LocalDate.now())
                .horaInicio(LocalDateTime.now().minusHours(1))
                .motivo("Chequeo general")
                .signosVitales(SignosVitales.builder()
                        .temperatura(38.5)
                        .pesoKg(25.0)
                        .frecuenciaCardiaca(80)
                        .frecuenciaRespiratoria(20)
                        .build())
                .build();
        consultaActiva.setIsActive(true);
        consultaActiva = consultaRepository.save(consultaActiva);

        // Crear consulta finalizada
        consultaFinalizada = Consulta.builder()
                .historialClinico(historialClinico)
                .veterinario(veterinario)
                .fechaConsulta(LocalDate.now().minusDays(7))
                .horaInicio(LocalDateTime.now().minusDays(7).minusHours(2))
                .horaFin(LocalDateTime.now().minusDays(7).minusHours(1))
                .motivo("Vacunación")
                .observaciones("Paciente sano")
                .planTratamiento("Vacuna antirrábica aplicada")
                .signosVitales(SignosVitales.builder()
                        .temperatura(38.3)
                        .pesoKg(24.5)
                        .frecuenciaCardiaca(75)
                        .build())
                .build();
        consultaFinalizada.setIsActive(true);
        consultaFinalizada = consultaRepository.save(consultaFinalizada);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("Debe encontrar consultas por paciente")
    void debeEncontrarConsultasPorPaciente() {
        // When
        List<Consulta> consultas = consultaRepository.findByPacienteId(
                historialClinico.getPaciente().getId());

        // Then
        assertThat(consultas).hasSize(2);
        assertThat(consultas).extracting("motivo")
                .containsExactlyInAnyOrder("Chequeo general", "Vacunación");
    }

    @Test
    @DisplayName("Debe encontrar consultas en curso")
    void debeEncontrarConsultasEnCurso() {
        // When
        List<Consulta> consultasEnCurso = consultaRepository.findConsultasEnCurso();

        // Then
        assertThat(consultasEnCurso).hasSize(1);
        assertThat(consultasEnCurso.get(0).getMotivo()).isEqualTo("Chequeo general");
        assertThat(consultasEnCurso.get(0).getHoraFin()).isNull();
    }

    @Test
    @DisplayName("Debe encontrar consultas finalizadas en rango")
    void debeEncontrarConsultasFinalizadas() {
        // When
        List<Consulta> consultasFinalizadas = consultaRepository.findConsultasFinalizadasEnRango(
                LocalDate.now().minusDays(30), LocalDate.now());

        // Then
        assertThat(consultasFinalizadas).hasSize(1);
        assertThat(consultasFinalizadas.get(0).getMotivo()).isEqualTo("Vacunación");
        assertThat(consultasFinalizadas.get(0).getHoraFin()).isNotNull();
    }

    @Test
    @DisplayName("Debe encontrar consultas por veterinario en rango de fechas")
    void debeEncontrarConsultasPorVeterinarioYFecha() {
        // When
        List<Consulta> consultas = consultaRepository.findByVeterinarioAndFechaBetween(
                veterinario, LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));

        // Then
        assertThat(consultas).hasSize(1);
        assertThat(consultas.get(0).getMotivo()).isEqualTo("Chequeo general");
    }

    @Test
    @DisplayName("Debe encontrar consultas por fecha")
    void debeEncontrarConsultasPorFecha() {
        // When
        List<Consulta> consultas = consultaRepository.findByFecha(LocalDate.now());

        // Then
        assertThat(consultas).hasSize(1);
        assertThat(consultas.get(0).getMotivo()).isEqualTo("Chequeo general");
    }

    @Test
    @DisplayName("Debe contar consultas del veterinario en rango")
    void debeContarConsultasDelVeterinario() {
        // When
        Long count = consultaRepository.countByVeterinarioAndFechaBetween(
                veterinario.getId(), LocalDate.now(), LocalDate.now());

        // Then
        assertThat(count).isEqualTo(1L);
    }

    @Test
    @DisplayName("Debe encontrar última consulta de un paciente")
    void debeEncontrarUltimaConsultaDePaciente() {
        // When
        Optional<Consulta> ultimaConsulta = consultaRepository.findUltimaConsultaByPaciente(
                historialClinico.getPaciente().getId());

        // Then
        assertThat(ultimaConsulta).isPresent();
        assertThat(ultimaConsulta.get().getMotivo()).isEqualTo("Chequeo general");
        assertThat(ultimaConsulta.get().getFechaConsulta()).isEqualTo(LocalDate.now());
    }

    @Test
    @DisplayName("Debe buscar consultas por motivo")
    void debeBuscarConsultasPorMotivo() {
        // When
        List<Consulta> consultas = consultaRepository.findByMotivoContaining("Chequeo");

        // Then
        assertThat(consultas).hasSize(1);
        assertThat(consultas.get(0).getMotivo()).contains("Chequeo");
    }

    @Test
    @DisplayName("Debe verificar que consulta está en curso")
    void debeVerificarConsultaEnCurso() {
        // When
        boolean enCurso = consultaActiva.estaEnCurso();

        // Then
        assertThat(enCurso).isTrue();
    }

    @Test
    @DisplayName("Debe verificar que consulta está finalizada")
    void debeVerificarConsultaFinalizada() {
        // When
        boolean finalizada = consultaFinalizada.estaFinalizada();

        // Then
        assertThat(finalizada).isTrue();
    }

    @Test
    @DisplayName("Debe calcular duración de consulta finalizada")
    void debeCalcularDuracionConsulta() {
        // When
        Long duracion = consultaFinalizada.calcularDuracionMinutos();

        // Then
        assertThat(duracion).isNotNull();
        assertThat(duracion).isGreaterThan(0);
    }

    @Test
    @DisplayName("Debe obtener resumen de consulta")
    void debeObtenerResumenConsulta() {
        // When
        String resumen = consultaFinalizada.obtenerResumen();

        // Then
        assertThat(resumen).contains("Vacunación");
        assertThat(resumen).contains("Paciente sano");
    }
}
