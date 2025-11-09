package com.veterinaria.domain.entity.billing;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import com.veterinaria.domain.entity.appointments.Cita;
import com.veterinaria.domain.entity.patients.Cliente;
import com.veterinaria.domain.enums.EstadoFactura;
import com.veterinaria.domain.entity.security.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa una factura de servicios veterinarios.
 * Una factura puede generarse desde una cita completada o de manera manual.
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "facturas", indexes = {
        @Index(name = "idx_factura_numero", columnList = "numero_factura", unique = true),
        @Index(name = "idx_factura_cliente", columnList = "cliente_id"),
        @Index(name = "idx_factura_estado", columnList = "estado"),
        @Index(name = "idx_factura_fecha", columnList = "fecha_emision")
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Factura extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Número único de factura (formato: FAC-YYYY-NNNNNN)
     */
    @NotBlank(message = "El número de factura es obligatorio")
    @Size(max = 20, message = "El número de factura no puede exceder 20 caracteres")
    @Column(name = "numero_factura", nullable = false, unique = true, length = 20)
    private String numeroFactura;

    /**
     * Cliente al que se emite la factura
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "El cliente es obligatorio")
    private Cliente cliente;

    /**
     * Cita asociada (opcional)
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cita_id")
    private Cita cita;

    /**
     * Usuario que emitió la factura
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_emisor_id", nullable = false)
    @NotNull(message = "El usuario emisor es obligatorio")
    private Usuario usuarioEmisor;

    /**
     * Fecha de emisión de la factura
     */
    @NotNull(message = "La fecha de emisión es obligatoria")
    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    /**
     * Fecha de vencimiento de pago
     */
    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    /**
     * Estado de la factura
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    @Builder.Default
    private EstadoFactura estado = EstadoFactura.PENDIENTE;

    /**
     * Subtotal (suma de detalles antes de descuentos)
     */
    @NotNull(message = "El subtotal es obligatorio")
    @PositiveOrZero(message = "El subtotal debe ser mayor o igual a cero")
    @Column(name = "subtotal", nullable = false, columnDefinition = "DECIMAL(10,2)")
    @Builder.Default
    private Double subtotal = 0.0;

    /**
     * Total de descuentos aplicados
     */
    @Column(name = "total_descuentos", columnDefinition = "DECIMAL(10,2)")
    @Builder.Default
    private Double totalDescuentos = 0.0;

    /**
     * Total de impuestos (IGV u otros)
     */
    @Column(name = "total_impuestos", columnDefinition = "DECIMAL(10,2)")
    @Builder.Default
    private Double totalImpuestos = 0.0;

    /**
     * Total final a pagar
     */
    @NotNull(message = "El total es obligatorio")
    @PositiveOrZero(message = "El total debe ser mayor o igual a cero")
    @Column(name = "total", nullable = false, columnDefinition = "DECIMAL(10,2)")
    @Builder.Default
    private Double total = 0.0;

    /**
     * Total pagado hasta el momento
     */
    @Column(name = "total_pagado", columnDefinition = "DECIMAL(10,2)")
    @Builder.Default
    private Double totalPagado = 0.0;

    /**
     * Saldo pendiente
     */
    @Column(name = "saldo_pendiente", columnDefinition = "DECIMAL(10,2)")
    @Builder.Default
    private Double saldoPendiente = 0.0;

    /**
     * Observaciones de la factura
     */
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    /**
     * Motivo de anulación (si aplica)
     */
    @Column(name = "motivo_anulacion", length = 500)
    private String motivoAnulacion;

    /**
     * Fecha de anulación
     */
    @Column(name = "fecha_anulacion")
    private LocalDate fechaAnulacion;

    /**
     * Detalles de la factura (items)
     */
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DetalleFactura> detalles = new ArrayList<>();

    /**
     * Pagos realizados
     */
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Pago> pagos = new ArrayList<>();

    /**
     * Descuentos aplicados
     */
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Descuento> descuentos = new ArrayList<>();

    // ==================== MÉTODOS HELPER SIMPLES ====================

    /**
     * Agrega un detalle a la factura
     * @param detalle el detalle a agregar
     */
    public void agregarDetalle(DetalleFactura detalle) {
        detalles.add(detalle);
        detalle.setFactura(this);
    }

    /**
     * Agrega un pago a la factura
     * @param pago el pago a agregar
     */
    public void agregarPago(Pago pago) {
        pagos.add(pago);
        pago.setFactura(this);
    }

    /**
     * Agrega un descuento a la factura
     * @param descuento el descuento a agregar
     */
    public void agregarDescuento(Descuento descuento) {
        descuentos.add(descuento);
        descuento.setFactura(this);
    }

    /**
     * Verifica si la factura está pagada completamente
     * @return true si está pagada
     */
    public boolean estaPagada() {
        return estado == EstadoFactura.PAGADA;
    }

    /**
     * Verifica si la factura está pendiente
     * @return true si está pendiente
     */
    public boolean estaPendiente() {
        return estado == EstadoFactura.PENDIENTE;
    }

    /**
     * Verifica si la factura está anulada
     * @return true si está anulada
     */
    public boolean estaAnulada() {
        return estado == EstadoFactura.ANULADA;
    }

    /**
     * Verifica si la factura está vencida
     * @return true si está vencida
     */
    public boolean estaVencida() {
        return estado == EstadoFactura.VENCIDA;
    }

    /**
     * Verifica si tiene pagos registrados
     * @return true si tiene pagos
     */
    public boolean tienePagos() {
        return !pagos.isEmpty();
    }

    /**
     * Verifica si tiene descuentos aplicados
     * @return true si tiene descuentos
     */
    public boolean tieneDescuentos() {
        return !descuentos.isEmpty();
    }

    /**
     * Verifica si la factura puede ser modificada
     * @return true si puede ser modificada
     */
    public boolean puedeSerModificada() {
        return estado.puedeSerModificada();
    }

    /**
     * Verifica si la factura puede recibir pagos
     * @return true si puede recibir pagos
     */
    public boolean puedeRecibirPagos() {
        return estado.puedeRecibirPagos();
    }

    /**
     * Verifica si tiene saldo pendiente
     * @return true si tiene saldo pendiente
     */
    public boolean tieneSaldoPendiente() {
        return saldoPendiente != null && saldoPendiente > 0;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "id=" + id +
                ", numeroFactura='" + numeroFactura + '\'' +
                ", estado=" + estado +
                ", total=" + total +
                ", totalPagado=" + totalPagado +
                ", saldoPendiente=" + saldoPendiente +
                '}';
    }
}
