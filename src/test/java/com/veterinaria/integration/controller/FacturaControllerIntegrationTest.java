package com.veterinaria.integration.controller;

import com.veterinaria.application.dto.billing.*;
import com.veterinaria.application.repository.ClienteRepository;
import com.veterinaria.application.repository.TipoServicioRepository;
import com.veterinaria.application.repository.UsuarioRepository;
import com.veterinaria.domain.entity.appointments.TipoServicio;
import com.veterinaria.domain.entity.patients.Cliente;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.valueobject.Direccion;
import com.veterinaria.domain.valueobject.Email;
import com.veterinaria.domain.valueobject.Telefono;
import com.veterinaria.domain.enums.MetodoPago;
import com.veterinaria.domain.enums.TipoDescuento;
import com.veterinaria.domain.enums.TipoUsuario;
import com.veterinaria.integration.AbstractIntegrationTest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de integración para FacturaController.
 * Verifica los endpoints REST completos incluyendo seguridad.
 *
 * @author Sistema Veterinaria
 */
@DisplayName("FacturaController Integration Tests")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FacturaControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TipoServicioRepository tipoServicioRepository;

    @Autowired
    private EntityManager entityManager;

    private Usuario usuarioEmisor;
    private Cliente cliente;
    private TipoServicio tipoServicio;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();

        // Crear usuario emisor
        usuarioEmisor = Usuario.builder()
                .username("admin_factura_test")
                .email("admin_factura@test.com")
                .password("$2a$12$encrypted.password.here")
                .nombre("Admin")
                .apellido("Factura")
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
                .nombre("María")
                .apellido("García")
                .dni("45678912")
                .email("maria@test.com")
                .telefono("998877665")
                .direccion("Calle Test 789")
                .ciudad("Lima")
                .departamento("Lima")
                .codigoPostal("15003")
                .build();
        cliente.setIsActive(true);
        cliente = clienteRepository.save(cliente);

        // Crear tipo de servicio
        tipoServicio = TipoServicio.builder()
                .nombre("Servicio Test Factura Controller")
                .descripcion("Servicio de prueba para tests de facturación")
                .precioBase(150.0)
                .duracionEstimada(60)
                .categoria("Cirugía")
                .build();
        tipoServicio.setIsActive(true);
        tipoServicio = tipoServicioRepository.save(tipoServicio);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @WithMockUser(authorities = {"FACTURAS_CREAR", "FACTURAS_VER"})
    @DisplayName("POST /api/v1/facturas - Debe crear factura con autenticación")
    void debeCrearFacturaConAutenticacion() throws Exception {
        // Given
        CreateFacturaRequest request = CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .fechaVencimiento(LocalDate.now().plusDays(30))
                .observaciones("Factura de prueba")
                .build();

        // When & Then
        mockMvc.perform(post("/api/v1/facturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.numeroFactura").exists())
                .andExpect(jsonPath("$.numeroFactura").value(startsWith("FAC-")))
                .andExpect(jsonPath("$.clienteNombre").value("María García"))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"))
                .andExpect(jsonPath("$.total").value(0.0))
                .andExpect(jsonPath("$.saldoPendiente").value(0.0));
    }

    @Test
    @DisplayName("POST /api/v1/facturas - Debe rechazar sin autenticación")
    void debeRechazarCrearFacturaSinAutenticacion() throws Exception {
        // Given
        CreateFacturaRequest request = CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .build();

        // When & Then
        mockMvc.perform(post("/api/v1/facturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"FACTURAS_CREAR", "FACTURAS_VER", "FACTURAS_EDITAR"})
    @DisplayName("POST /api/v1/facturas/{id}/detalles - Debe agregar detalle")
    void debeAgregarDetalle() throws Exception {
        // Given - Crear factura primero
        CreateFacturaRequest facturaRequest = CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .build();

        String responseCreate = mockMvc.perform(post("/api/v1/facturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(facturaRequest)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long facturaId = objectMapper.readTree(responseCreate).get("id").asLong();

        // Agregar detalle
        CreateDetalleFacturaRequest detalleRequest = CreateDetalleFacturaRequest.builder()
                .tipoServicioId(tipoServicio.getId())
                .descripcion("Cirugía menor")
                .cantidad(1)
                .precioUnitario(150.0)
                .build();

        // When & Then
        mockMvc.perform(post("/api/v1/facturas/" + facturaId + "/detalles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(detalleRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descripcion").value("Cirugía menor"))
                .andExpect(jsonPath("$.cantidad").value(1))
                .andExpect(jsonPath("$.precioUnitario").value(150.0))
                .andExpect(jsonPath("$.subtotal").value(150.0))
                .andExpect(jsonPath("$.total").value(150.0));

        // Verificar que la factura se actualizó
        mockMvc.perform(get("/api/v1/facturas/" + facturaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(150.0))
                .andExpect(jsonPath("$.saldoPendiente").value(150.0));
    }

    @Test
    @WithMockUser(authorities = {"FACTURAS_CREAR", "FACTURAS_VER", "FACTURAS_EDITAR", "FACTURAS_APLICAR_DESCUENTO"})
    @DisplayName("POST /api/v1/facturas/{id}/descuentos - Debe aplicar descuento")
    void debeAplicarDescuento() throws Exception {
        // Given - Crear factura con detalle
        CreateFacturaRequest facturaRequest = CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .build();

        String responseCreate = mockMvc.perform(post("/api/v1/facturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(facturaRequest)))
                .andReturn().getResponse().getContentAsString();

        Long facturaId = objectMapper.readTree(responseCreate).get("id").asLong();

        // Agregar detalle
        mockMvc.perform(post("/api/v1/facturas/" + facturaId + "/detalles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(CreateDetalleFacturaRequest.builder()
                        .descripcion("Servicio")
                        .cantidad(1)
                        .precioUnitario(100.0)
                        .build())));

        // Aplicar descuento
        CreateDescuentoRequest descuentoRequest = CreateDescuentoRequest.builder()
                .descripcion("Descuento 10%")
                .tipo(TipoDescuento.PORCENTAJE)
                .valor(10.0)
                .motivo("Cliente frecuente")
                .build();

        // When & Then
        mockMvc.perform(post("/api/v1/facturas/" + facturaId + "/descuentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(descuentoRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipo").value("PORCENTAJE"))
                .andExpect(jsonPath("$.valor").value(10.0))
                .andExpect(jsonPath("$.monto").value(10.0));

        // Verificar que el total se actualizó
        mockMvc.perform(get("/api/v1/facturas/" + facturaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subtotal").value(100.0))
                .andExpect(jsonPath("$.totalDescuentos").value(10.0))
                .andExpect(jsonPath("$.total").value(90.0));
    }

    @Test
    @WithMockUser(authorities = {"FACTURAS_CREAR", "FACTURAS_VER", "FACTURAS_EDITAR", "PAGOS_REGISTRAR"})
    @DisplayName("POST /api/v1/facturas/{id}/pagos - Debe registrar pago")
    void debeRegistrarPago() throws Exception {
        // Given - Crear factura con detalle
        CreateFacturaRequest facturaRequest = CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .build();

        String responseCreate = mockMvc.perform(post("/api/v1/facturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(facturaRequest)))
                .andReturn().getResponse().getContentAsString();

        Long facturaId = objectMapper.readTree(responseCreate).get("id").asLong();

        // Agregar detalle
        mockMvc.perform(post("/api/v1/facturas/" + facturaId + "/detalles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(CreateDetalleFacturaRequest.builder()
                        .descripcion("Servicio")
                        .cantidad(1)
                        .precioUnitario(100.0)
                        .build())));

        // Registrar pago
        CreatePagoRequest pagoRequest = CreatePagoRequest.builder()
                .fechaPago(LocalDate.now())
                .metodoPago(MetodoPago.EFECTIVO)
                .monto(100.0)
                .usuarioRegistroId(usuarioEmisor.getId())
                .numeroReferencia("REF-TEST-001")
                .build();

        // When & Then
        mockMvc.perform(post("/api/v1/facturas/" + facturaId + "/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(pagoRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.monto").value(100.0))
                .andExpect(jsonPath("$.metodoPago").value("EFECTIVO"))
                .andExpect(jsonPath("$.numeroReferencia").value("REF-TEST-001"));

        // Verificar que el estado cambió a PAGADA
        mockMvc.perform(get("/api/v1/facturas/" + facturaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("PAGADA"))
                .andExpect(jsonPath("$.totalPagado").value(100.0))
                .andExpect(jsonPath("$.saldoPendiente").value(0.0));
    }

    @Test
    @WithMockUser(authorities = {"FACTURAS_CREAR", "FACTURAS_VER"})
    @DisplayName("GET /api/v1/facturas/{id} - Debe obtener factura por ID")
    void debeObtenerFacturaPorId() throws Exception {
        // Given - Crear factura
        CreateFacturaRequest request = CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .observaciones("Factura de prueba")
                .build();

        String responseCreate = mockMvc.perform(post("/api/v1/facturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request)))
                .andReturn().getResponse().getContentAsString();

        Long facturaId = objectMapper.readTree(responseCreate).get("id").asLong();

        // When & Then
        mockMvc.perform(get("/api/v1/facturas/" + facturaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facturaId))
                .andExpect(jsonPath("$.observaciones").value("Factura de prueba"));
    }

    @Test
    @WithMockUser(authorities = {"FACTURAS_CREAR", "FACTURAS_VER"})
    @DisplayName("GET /api/v1/facturas/cliente/{clienteId} - Debe listar facturas por cliente")
    void debeListarFacturasPorCliente() throws Exception {
        // Given - Crear dos facturas
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

        mockMvc.perform(post("/api/v1/facturas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request1)));

        mockMvc.perform(post("/api/v1/facturas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request2)));

        // When & Then
        mockMvc.perform(get("/api/v1/facturas/cliente/" + cliente.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].clienteNombre", everyItem(is("María García"))));
    }

    @Test
    @WithMockUser(authorities = {"FACTURAS_CREAR", "FACTURAS_VER"})
    @DisplayName("GET /api/v1/facturas/pendientes - Debe listar facturas pendientes")
    void debeListarFacturasPendientes() throws Exception {
        // Given
        CreateFacturaRequest request = CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .build();

        mockMvc.perform(post("/api/v1/facturas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        // When & Then
        mockMvc.perform(get("/api/v1/facturas/pendientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[*].estado", everyItem(is("PENDIENTE"))));
    }

    @Test
    @WithMockUser(authorities = {"FACTURAS_CREAR", "FACTURAS_VER", "FACTURAS_ANULAR"})
    @DisplayName("PATCH /api/v1/facturas/{id}/anular - Debe anular factura")
    void debeAnularFactura() throws Exception {
        // Given - Crear factura
        CreateFacturaRequest facturaRequest = CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .build();

        String responseCreate = mockMvc.perform(post("/api/v1/facturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(facturaRequest)))
                .andReturn().getResponse().getContentAsString();

        Long facturaId = objectMapper.readTree(responseCreate).get("id").asLong();

        // Anular factura
        AnularFacturaRequest anularRequest = AnularFacturaRequest.builder()
                .motivoAnulacion("Factura emitida por error")
                .build();

        // When & Then
        mockMvc.perform(patch("/api/v1/facturas/" + facturaId + "/anular")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(anularRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("ANULADA"))
                .andExpect(jsonPath("$.motivoAnulacion").value("Factura emitida por error"))
                .andExpect(jsonPath("$.fechaAnulacion").exists());
    }

    @Test
    @WithMockUser(authorities = {"FACTURAS_CREAR", "FACTURAS_VER", "FACTURAS_ELIMINAR"})
    @DisplayName("DELETE /api/v1/facturas/{id} - Debe eliminar factura")
    void debeEliminarFactura() throws Exception {
        // Given - Crear factura
        CreateFacturaRequest request = CreateFacturaRequest.builder()
                .clienteId(cliente.getId())
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                .build();

        String responseCreate = mockMvc.perform(post("/api/v1/facturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request)))
                .andReturn().getResponse().getContentAsString();

        Long facturaId = objectMapper.readTree(responseCreate).get("id").asLong();

        // When & Then - Eliminar
        mockMvc.perform(delete("/api/v1/facturas/" + facturaId))
                .andExpect(status().isNoContent());

        // Verificar que no se puede obtener
        mockMvc.perform(get("/api/v1/facturas/" + facturaId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = {"FACTURAS_CREAR"})
    @DisplayName("POST /api/v1/facturas - Debe validar request inválido")
    void debeValidarRequestInvalido() throws Exception {
        // Given - Request sin cliente (campo requerido)
        CreateFacturaRequest request = CreateFacturaRequest.builder()
                .usuarioEmisorId(usuarioEmisor.getId())
                .fechaEmision(LocalDate.now())
                // clienteId es obligatorio y no se proporciona
                .build();

        // When & Then
        mockMvc.perform(post("/api/v1/facturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request)))
                .andExpect(status().isBadRequest());
    }
}
