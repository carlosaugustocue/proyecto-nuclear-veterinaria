package com.veterinaria.integration.repository;

import com.veterinaria.application.repository.ClienteRepository;
import com.veterinaria.application.repository.FacturaRepository;
import com.veterinaria.application.repository.UsuarioRepository;
import com.veterinaria.domain.entity.billing.Factura;
import com.veterinaria.domain.entity.patients.Cliente;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.valueobject.Direccion;
import com.veterinaria.domain.valueobject.Email;
import com.veterinaria.domain.valueobject.Telefono;
import com.veterinaria.domain.enums.EstadoFactura;
import com.veterinaria.domain.enums.TipoUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests de integración para FacturaRepository.
 * Verifica las queries personalizadas del repositorio.
 *
 * @author Sistema Veterinaria
 */
@DataJpaTest
@ActiveProfiles("test")
@DisplayName("FacturaRepository Integration Tests")
class FacturaRepositoryIntegrationTest {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Cliente cliente;
    private Usuario usuarioEmisor;
    private Factura facturaPendiente;
    private Factura facturaPagada;
    private Factura facturaVencida;

    @BeforeEach
    void setUp() {
        // Crear usuario emisor
        usuarioEmisor = Usuario.builder()
                .username("admin_test")
                .email("admin@test.com")
                .password("password")
                .nombre("Admin")
                .apellido("Test")
                .tipoUsuario(TipoUsuario.ADMINISTRADOR)
                .cuentaBloqueada(false)
                .cuentaExpirada(false)
                .credencialesExpiradas(false)
                .intentosFallidos(0)
                .requiereCambioPassword(false)
                .build();
        usuarioEmisor.setIsActive(true);
        usuarioEmisor = usuarioRepository.save(usuarioEmisor);

        // Crear cliente
        cliente = Cliente.builder()
                .nombre("Juan")
                .apellido("Pérez")
                .dni("12345678")
                .email("juan@test.com")
                .telefono("987654321")
                .direccion("Av. Test 123")
                .ciudad("Lima")
                .departamento("Lima")
                .codigoPostal("15001")
                .build();
        cliente.setIsActive(true);
        cliente = clienteRepository.save(cliente);

        // Crear factura pendiente
        facturaPendiente = Factura.builder()
                .numeroFactura("FAC-2025-000001")
                .cliente(cliente)
                .usuarioEmisor(usuarioEmisor)
                .fechaEmision(LocalDate.now())
                .fechaVencimiento(LocalDate.now().plusDays(30))
                .estado(EstadoFactura.PENDIENTE)
                .subtotal(100.0)
                .totalDescuentos(0.0)
                .totalImpuestos(0.0)
                .total(100.0)
                .totalPagado(0.0)
                .saldoPendiente(100.0)
                .build();
        facturaPendiente.setIsActive(true);
        facturaPendiente = facturaRepository.save(facturaPendiente);

        // Crear factura pagada
        facturaPagada = Factura.builder()
                .numeroFactura("FAC-2025-000002")
                .cliente(cliente)
                .usuarioEmisor(usuarioEmisor)
                .fechaEmision(LocalDate.now().minusDays(7))
                .fechaVencimiento(LocalDate.now().plusDays(23))
                .estado(EstadoFactura.PAGADA)
                .subtotal(200.0)
                .totalDescuentos(20.0)
                .totalImpuestos(0.0)
                .total(180.0)
                .totalPagado(180.0)
                .saldoPendiente(0.0)
                .build();
        facturaPagada.setIsActive(true);
        facturaPagada = facturaRepository.save(facturaPagada);

        // Crear factura vencida
        facturaVencida = Factura.builder()
                .numeroFactura("FAC-2025-000003")
                .cliente(cliente)
                .usuarioEmisor(usuarioEmisor)
                .fechaEmision(LocalDate.now().minusDays(60))
                .fechaVencimiento(LocalDate.now().minusDays(30))
                .estado(EstadoFactura.PENDIENTE)
                .subtotal(150.0)
                .totalDescuentos(0.0)
                .totalImpuestos(0.0)
                .total(150.0)
                .totalPagado(0.0)
                .saldoPendiente(150.0)
                .build();
        facturaVencida.setIsActive(true);
        facturaVencida = facturaRepository.save(facturaVencida);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("Debe encontrar factura por número")
    void debeEncontrarFacturaPorNumero() {
        // When
        Optional<Factura> factura = facturaRepository.findByNumeroFactura("FAC-2025-000001");

        // Then
        assertThat(factura).isPresent();
        assertThat(factura.get().getCliente().getNombreCompleto()).isEqualTo("Juan Pérez");
        assertThat(factura.get().getTotal()).isEqualTo(100.0);
    }

    @Test
    @DisplayName("Debe encontrar facturas por cliente")
    void debeEncontrarFacturasPorCliente() {
        // When
        List<Factura> facturas = facturaRepository.findByClienteId(cliente.getId());

        // Then
        assertThat(facturas).hasSize(3);
        assertThat(facturas).extracting("numeroFactura")
                .containsExactlyInAnyOrder("FAC-2025-000001", "FAC-2025-000002", "FAC-2025-000003");
    }

    @Test
    @DisplayName("Debe encontrar facturas por estado")
    void debeEncontrarFacturasPorEstado() {
        // When
        List<Factura> facturasPendientes = facturaRepository.findByEstado(EstadoFactura.PENDIENTE);
        List<Factura> facturasPagadas = facturaRepository.findByEstado(EstadoFactura.PAGADA);

        // Then
        assertThat(facturasPendientes).hasSize(2);
        assertThat(facturasPagadas).hasSize(1);
        assertThat(facturasPagadas.get(0).getNumeroFactura()).isEqualTo("FAC-2025-000002");
    }

    @Test
    @DisplayName("Debe encontrar facturas por rango de fechas")
    void debeEncontrarFacturasPorRangoFechas() {
        // When
        List<Factura> facturas = facturaRepository.findByFechaEmisionBetween(
                LocalDate.now().minusDays(10), LocalDate.now());

        // Then
        assertThat(facturas).hasSize(2);
        assertThat(facturas).extracting("numeroFactura")
                .containsExactlyInAnyOrder("FAC-2025-000001", "FAC-2025-000002");
    }

    @Test
    @DisplayName("Debe encontrar facturas vencidas")
    void debeEncontrarFacturasVencidas() {
        // When
        List<Factura> facturasVencidas = facturaRepository.findFacturasVencidas(LocalDate.now());

        // Then
        assertThat(facturasVencidas).hasSize(1);
        assertThat(facturasVencidas.get(0).getNumeroFactura()).isEqualTo("FAC-2025-000003");
        assertThat(facturasVencidas.get(0).getFechaVencimiento()).isBefore(LocalDate.now());
    }

    @Test
    @DisplayName("Debe encontrar facturas pendientes")
    void debeEncontrarFacturasPendientes() {
        // When
        List<Factura> pendientes = facturaRepository.findFacturasPendientes();

        // Then
        assertThat(pendientes).hasSize(2);
        assertThat(pendientes).allMatch(f -> f.getEstado() == EstadoFactura.PENDIENTE);
    }

    @Test
    @DisplayName("Debe encontrar facturas con saldo pendiente")
    void debeEncontrarFacturasConSaldoPendiente() {
        // When
        List<Factura> conSaldo = facturaRepository.findFacturasConSaldoPendiente();

        // Then
        assertThat(conSaldo).hasSize(2);
        assertThat(conSaldo).allMatch(f -> f.getSaldoPendiente() > 0);
    }

    @Test
    @DisplayName("Debe calcular total de ventas por fecha")
    void debeCalcularTotalVentasPorFecha() {
        // When
        Double totalVentas = facturaRepository.calcularTotalVentasByFecha(
                LocalDate.now().minusDays(10), LocalDate.now());

        // Then
        assertThat(totalVentas).isEqualTo(280.0); // 100 + 180
    }

    @Test
    @DisplayName("Debe calcular total pagado por fecha")
    void debeCalcularTotalPagadoPorFecha() {
        // When
        Double totalPagado = facturaRepository.calcularTotalPagadoByFecha(
                LocalDate.now().minusDays(10), LocalDate.now());

        // Then
        assertThat(totalPagado).isEqualTo(180.0);
    }

    @Test
    @DisplayName("Debe calcular saldo pendiente total")
    void debeCalcularSaldoPendienteTotal() {
        // When
        Double saldoTotal = facturaRepository.calcularSaldoPendienteTotal();

        // Then
        assertThat(saldoTotal).isEqualTo(250.0); // 100 + 150
    }

    @Test
    @DisplayName("Debe contar facturas por estado")
    void debeContarFacturasPorEstado() {
        // When
        Long countPendientes = facturaRepository.countByEstado(EstadoFactura.PENDIENTE);
        Long countPagadas = facturaRepository.countByEstado(EstadoFactura.PAGADA);

        // Then
        assertThat(countPendientes).isEqualTo(2L);
        assertThat(countPagadas).isEqualTo(1L);
    }

    @Test
    @DisplayName("Debe verificar existencia de factura por número")
    void debeVerificarExistenciaPorNumero() {
        // When
        boolean existe = facturaRepository.existsByNumeroFactura("FAC-2025-000001");
        boolean noExiste = facturaRepository.existsByNumeroFactura("FAC-2025-999999");

        // Then
        assertThat(existe).isTrue();
        assertThat(noExiste).isFalse();
    }

    @Test
    @DisplayName("Debe encontrar último número de factura del año")
    void debeEncontrarUltimoNumeroFactura() {
        // When
        Optional<String> ultimoNumero = facturaRepository.findUltimoNumeroFacturaDelAnio(2025);

        // Then
        assertThat(ultimoNumero).isPresent();
        assertThat(ultimoNumero.get()).isEqualTo("FAC-2025-000003");
    }

    @Test
    @DisplayName("Debe verificar métodos helper de factura")
    void debeVerificarMetodosHelper() {
        // When & Then
        assertThat(facturaPendiente.estaPendiente()).isTrue();
        assertThat(facturaPendiente.estaPagada()).isFalse();
        assertThat(facturaPendiente.tieneSaldoPendiente()).isTrue();

        assertThat(facturaPagada.estaPagada()).isTrue();
        assertThat(facturaPagada.tieneSaldoPendiente()).isFalse();

        assertThat(facturaPendiente.puedeRecibirPagos()).isTrue();
        assertThat(facturaPagada.puedeRecibirPagos()).isFalse();
    }
}
