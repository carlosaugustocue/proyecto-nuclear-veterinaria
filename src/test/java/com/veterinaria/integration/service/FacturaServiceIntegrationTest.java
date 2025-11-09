package com.veterinaria.integration.service;

import com.veterinaria.application.dto.billing.*;
import com.veterinaria.application.exception.BusinessRuleException;
import com.veterinaria.application.exception.ResourceNotFoundException;
import com.veterinaria.application.repository.ClienteRepository;
import com.veterinaria.application.repository.TipoServicioRepository;
import com.veterinaria.application.repository.UsuarioRepository;
import com.veterinaria.application.service.FacturaService;
import com.veterinaria.domain.entity.appointments.TipoServicio;
import com.veterinaria.domain.entity.patients.Cliente;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.valueobject.Direccion;
import com.veterinaria.domain.valueobject.Email;
import com.veterinaria.domain.valueobject.Telefono;
import com.veterinaria.domain.enums.EstadoFactura;
import com.veterinaria.domain.enums.MetodoPago;
import com.veterinaria.domain.enums.TipoDescuento;
import com.veterinaria.domain.enums.TipoUsuario;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests de integración para FacturaService.
 * Verifica la lógica de negocio completa con base de datos real.
 *
 * @author Sistema Veterinaria
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("FacturaService Integration Tests")
class FacturaServiceIntegrationTest {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoServicioRepository tipoServicioRepository;

    @Autowired
    private EntityManager entityManager;

    private Cliente cliente;
    private Usuario usuarioEmisor;
    private TipoServicio tipoServicio;

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
                .nombre("Carlos")
                .apellido("Rodríguez")
                .dni("87654321")
                .email("carlos@test.com")
                .telefono("912345678")
                .direccion("Jr. Test 456")
                .ciudad("Lima")
                .departamento("Lima")
                .codigoPostal("15002")
                .build();
        cliente.setIsActive(true);
        cliente = clienteRepository.save(cliente);

        // Crear tipo de servicio
        tipoServicio = TipoServicio.builder()
                .nombre("Servicio Test Factura Service")
                .descripcion("Servicio de prueba para tests de facturación")
                .precioBase(50.0)
                .duracionEstimada(30)
                .categoria("Consulta")
                .build();
        tipoServicio.setIsActive(true);
        tipoServicio = tipoServicioRepository.save(tipoServicio);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("Debe crear factura exitosamente")
    void debeCrearFacturaExitosamente() {
        // Given
        CreateFacturaRequest request = CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .fechaVencimiento(LocalDate.now().plusDays(30))
                .observaciones("Primera factura de prueba")
                .build();

        // When
        FacturaDTO creada = facturaService.crear(request);

        // Then
        assertThat(creada).isNotNull();
        assertThat(creada.getId()).isNotNull();
        assertThat(creada.getNumeroFactura()).isNotNull();
        assertThat(creada.getNumeroFactura()).startsWith("FAC-");
        assertThat(creada.getClienteNombre()).isEqualTo("Carlos Rodríguez");
        assertThat(creada.getEstado()).isEqualTo(EstadoFactura.PENDIENTE);
        assertThat(creada.getTotal()).isEqualTo(0.0);
        assertThat(creada.getSaldoPendiente()).isEqualTo(0.0);
    }

    @Test
    @DisplayName("Debe generar números de factura consecutivos")
    void debeGenerarNumerosConsecutivos() {
        // Given
        CreateFacturaRequest request1 = CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .build();

        CreateFacturaRequest request2 = CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .build();

        // When
        FacturaDTO factura1 = facturaService.crear(request1);
        FacturaDTO factura2 = facturaService.crear(request2);

        // Then
        assertThat(factura1.getNumeroFactura()).matches("FAC-\\d{4}-\\d{6}");
        assertThat(factura2.getNumeroFactura()).matches("FAC-\\d{4}-\\d{6}");
        assertThat(factura1.getNumeroFactura()).isNotEqualTo(factura2.getNumeroFactura());
    }

    @Test
    @DisplayName("Debe agregar detalle y recalcular totales")
    void debeAgregarDetalleYRecalcularTotales() {
        // Given
        FacturaDTO factura = facturaService.crear(CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .build());

        CreateDetalleFacturaRequest detalleRequest = CreateDetalleFacturaRequest.builder()
                .tipoServicioId(tipoServicio.getId())
                .descripcion("Consulta veterinaria general")
                .cantidad(1)
                .precioUnitario(50.0)
                .build();

        // When
        DetalleFacturaDTO detalle = facturaService.agregarDetalle(factura.getId(), detalleRequest);
        FacturaDTO actualizada = facturaService.obtenerPorId(factura.getId());

        // Then
        assertThat(detalle).isNotNull();
        assertThat(detalle.getDescripcion()).isEqualTo("Consulta veterinaria general");
        assertThat(detalle.getSubtotal()).isEqualTo(50.0);
        assertThat(detalle.getTotal()).isEqualTo(50.0);

        assertThat(actualizada.getSubtotal()).isEqualTo(50.0);
        assertThat(actualizada.getTotal()).isEqualTo(50.0);
        assertThat(actualizada.getSaldoPendiente()).isEqualTo(50.0);
    }

    @Test
    @DisplayName("Debe aplicar descuento por porcentaje y recalcular")
    void debeAplicarDescuentoPorPorcentaje() {
        // Given
        FacturaDTO factura = facturaService.crear(CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .build());

        facturaService.agregarDetalle(factura.getId(), CreateDetalleFacturaRequest.builder()
                .descripcion("Servicio veterinario")
                .cantidad(1)
                .precioUnitario(100.0)
                .build());

        CreateDescuentoRequest descuentoRequest = CreateDescuentoRequest.builder()
                .descripcion("Descuento cliente frecuente")
                .tipo(TipoDescuento.PORCENTAJE)
                .valor(10.0) // 10%
                .motivo("Cliente frecuente")
                .build();

        // When
        DescuentoDTO descuento = facturaService.aplicarDescuento(factura.getId(), descuentoRequest);
        FacturaDTO actualizada = facturaService.obtenerPorId(factura.getId());

        // Then
        assertThat(descuento.getMonto()).isEqualTo(10.0); // 10% de 100
        assertThat(actualizada.getSubtotal()).isEqualTo(100.0);
        assertThat(actualizada.getTotalDescuentos()).isEqualTo(10.0);
        assertThat(actualizada.getTotal()).isEqualTo(90.0); // 100 - 10
        assertThat(actualizada.getSaldoPendiente()).isEqualTo(90.0);
    }

    @Test
    @DisplayName("Debe aplicar descuento de monto fijo")
    void debeAplicarDescuentoMontoFijo() {
        // Given
        FacturaDTO factura = facturaService.crear(CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .build());

        facturaService.agregarDetalle(factura.getId(), CreateDetalleFacturaRequest.builder()
                .descripcion("Servicio veterinario")
                .cantidad(1)
                .precioUnitario(100.0)
                .build());

        CreateDescuentoRequest descuentoRequest = CreateDescuentoRequest.builder()
                .codigo("DESC20")
                .descripcion("Descuento S/20 fijo")
                .tipo(TipoDescuento.MONTO_FIJO)
                .valor(20.0)
                .build();

        // When
        DescuentoDTO descuento = facturaService.aplicarDescuento(factura.getId(), descuentoRequest);
        FacturaDTO actualizada = facturaService.obtenerPorId(factura.getId());

        // Then
        assertThat(descuento.getMonto()).isEqualTo(20.0);
        assertThat(actualizada.getTotal()).isEqualTo(80.0); // 100 - 20
    }

    @Test
    @DisplayName("Debe registrar pago y actualizar estado a PAGADA")
    void debeRegistrarPagoYActualizarEstado() {
        // Given
        FacturaDTO factura = facturaService.crear(CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .build());

        facturaService.agregarDetalle(factura.getId(), CreateDetalleFacturaRequest.builder()
                .descripcion("Servicio")
                .cantidad(1)
                .precioUnitario(100.0)
                .build());

        CreatePagoRequest pagoRequest = CreatePagoRequest.builder()
                .fechaPago(LocalDate.now())
                .metodoPago(MetodoPago.EFECTIVO)
                .monto(100.0)
                .usuarioRegistroId(usuarioEmisor.getId())
                .numeroReferencia("REF-001")
                .build();

        // When
        PagoDTO pago = facturaService.registrarPago(factura.getId(), pagoRequest);
        FacturaDTO actualizada = facturaService.obtenerPorId(factura.getId());

        // Then
        assertThat(pago).isNotNull();
        assertThat(pago.getMonto()).isEqualTo(100.0);
        assertThat(pago.getMetodoPago()).isEqualTo(MetodoPago.EFECTIVO);
        assertThat(pago.getComision()).isEqualTo(0.0); // Efectivo no tiene comisión

        assertThat(actualizada.getEstado()).isEqualTo(EstadoFactura.PAGADA);
        assertThat(actualizada.getTotalPagado()).isEqualTo(100.0);
        assertThat(actualizada.getSaldoPendiente()).isEqualTo(0.0);
    }

    @Test
    @DisplayName("Debe registrar pago parcial y actualizar estado a PARCIAL")
    void debeRegistrarPagoParcial() {
        // Given
        FacturaDTO factura = facturaService.crear(CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .build());

        facturaService.agregarDetalle(factura.getId(), CreateDetalleFacturaRequest.builder()
                .descripcion("Servicio")
                .cantidad(1)
                .precioUnitario(200.0)
                .build());

        CreatePagoRequest pagoRequest = CreatePagoRequest.builder()
                .fechaPago(LocalDate.now())
                .metodoPago(MetodoPago.EFECTIVO)
                .monto(100.0) // Pago parcial
                .usuarioRegistroId(usuarioEmisor.getId())
                .build();

        // When
        facturaService.registrarPago(factura.getId(), pagoRequest);
        FacturaDTO actualizada = facturaService.obtenerPorId(factura.getId());

        // Then
        assertThat(actualizada.getEstado()).isEqualTo(EstadoFactura.PARCIAL);
        assertThat(actualizada.getTotalPagado()).isEqualTo(100.0);
        assertThat(actualizada.getSaldoPendiente()).isEqualTo(100.0);
    }

    @Test
    @DisplayName("Debe calcular comisión con tarjeta de crédito")
    void debeCalcularComisionConTarjeta() {
        // Given
        FacturaDTO factura = facturaService.crear(CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .build());

        facturaService.agregarDetalle(factura.getId(), CreateDetalleFacturaRequest.builder()
                .descripcion("Servicio")
                .cantidad(1)
                .precioUnitario(100.0)
                .build());

        CreatePagoRequest pagoRequest = CreatePagoRequest.builder()
                .fechaPago(LocalDate.now())
                .metodoPago(MetodoPago.TARJETA_CREDITO) // 3% de comisión
                .monto(100.0)
                .usuarioRegistroId(usuarioEmisor.getId())
                .build();

        // When
        PagoDTO pago = facturaService.registrarPago(factura.getId(), pagoRequest);

        // Then
        assertThat(pago.getComision()).isEqualTo(3.0); // 3% de 100
        assertThat(pago.getMontoNeto()).isEqualTo(97.0); // 100 - 3
    }

    @Test
    @DisplayName("Debe anular factura sin pagos")
    void debeAnularFacturaSinPagos() {
        // Given
        FacturaDTO factura = facturaService.crear(CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .build());

        AnularFacturaRequest request = AnularFacturaRequest.builder()
                .motivoAnulacion("Factura emitida por error")
                .build();

        // When
        FacturaDTO anulada = facturaService.anular(factura.getId(), request);

        // Then
        assertThat(anulada.getEstado()).isEqualTo(EstadoFactura.ANULADA);
        assertThat(anulada.getMotivoAnulacion()).isEqualTo("Factura emitida por error");
        assertThat(anulada.getFechaAnulacion()).isNotNull();
    }

    @Test
    @DisplayName("No debe permitir anular factura con pagos")
    void noPuedeAnularFacturaConPagos() {
        // Given
        FacturaDTO factura = facturaService.crear(CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .build());

        facturaService.agregarDetalle(factura.getId(), CreateDetalleFacturaRequest.builder()
                .descripcion("Servicio")
                .cantidad(1)
                .precioUnitario(100.0)
                .build());

        facturaService.registrarPago(factura.getId(), CreatePagoRequest.builder()
                .fechaPago(LocalDate.now())
                .metodoPago(MetodoPago.EFECTIVO)
                .monto(100.0)
                .usuarioRegistroId(usuarioEmisor.getId())
                .build());

        // When & Then
        assertThatThrownBy(() -> facturaService.anular(factura.getId(), AnularFacturaRequest.builder()
                .motivoAnulacion("Intento de anulación")
                .build()))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("pagos registrados");
    }

    @Test
    @DisplayName("Debe listar facturas por cliente")
    void debeListarFacturasPorCliente() {
        // Given
        facturaService.crear(CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .build());

        facturaService.crear(CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .build());

        // When
        List<FacturaDTO> facturas = facturaService.listarPorCliente(cliente.getId());

        // Then
        assertThat(facturas).hasSize(2);
    }

    @Test
    @DisplayName("Debe eliminar detalle y recalcular totales")
    void debeEliminarDetalleYRecalcular() {
        // Given
        FacturaDTO factura = facturaService.crear(CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .build());

        DetalleFacturaDTO detalle1 = facturaService.agregarDetalle(factura.getId(),
                CreateDetalleFacturaRequest.builder()
                .descripcion("Servicio 1")
                .cantidad(1)
                .precioUnitario(50.0)
                .build());

        facturaService.agregarDetalle(factura.getId(), CreateDetalleFacturaRequest.builder()
                .descripcion("Servicio 2")
                .cantidad(1)
                .precioUnitario(30.0)
                .build());

        // When
        facturaService.eliminarDetalle(factura.getId(), detalle1.getId());
        FacturaDTO actualizada = facturaService.obtenerPorId(factura.getId());

        // Then
        assertThat(actualizada.getTotal()).isEqualTo(30.0); // Solo queda servicio 2
    }

    @Test
    @DisplayName("No debe permitir pago mayor al saldo pendiente")
    void noPuedeRegistrarPagoMayorAlSaldo() {
        // Given
        FacturaDTO factura = facturaService.crear(CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .build());

        facturaService.agregarDetalle(factura.getId(), CreateDetalleFacturaRequest.builder()
                .descripcion("Servicio")
                .cantidad(1)
                .precioUnitario(100.0)
                .build());

        // When & Then
        assertThatThrownBy(() -> facturaService.registrarPago(factura.getId(),
                CreatePagoRequest.builder()
                .fechaPago(LocalDate.now())
                .metodoPago(MetodoPago.EFECTIVO)
                .monto(150.0) // Mayor al total
                .usuarioRegistroId(usuarioEmisor.getId())
                .build()))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("excede el saldo pendiente");
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando factura no existe")
    void debeLanzarExcepcionCuandoFacturaNoExiste() {
        // When & Then
        assertThatThrownBy(() -> facturaService.obtenerPorId(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Factura no encontrada");
    }
}
