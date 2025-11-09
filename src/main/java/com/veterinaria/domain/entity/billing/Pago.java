package com.veterinaria.domain.entity.billing;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import com.veterinaria.domain.enums.MetodoPago;
import com.veterinaria.domain.entity.security.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad que representa un pago realizado a una factura.
 * Una factura puede tener múltiples pagos (pago parcial o completo).
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "pagos", indexes = {
        @Index(name = "idx_pago_factura", columnList = "factura_id"),
        @Index(name = "idx_pago_fecha", columnList = "fecha_pago"),
        @Index(name = "idx_pago_referencia", columnList = "numero_referencia")
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Pago extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Factura a la que pertenece este pago
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id", nullable = false)
    @NotNull(message = "La factura es obligatoria")
    private Factura factura;

    /**
     * Fecha del pago
     */
    @NotNull(message = "La fecha de pago es obligatoria")
    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    /**
     * Hora del pago
     */
    @Column(name = "hora_pago")
    private LocalDateTime horaPago;

    /**
     * Método de pago utilizado
     */
    @Enumerated(EnumType.STRING)
    @NotNull(message = "El método de pago es obligatorio")
    @Column(name = "metodo_pago", nullable = false, length = 30)
    private MetodoPago metodoPago;

    /**
     * Monto pagado
     */
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    @Column(name = "monto", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Double monto;

    /**
     * Comisión del método de pago (si aplica)
     */
    @Column(name = "comision", columnDefinition = "DECIMAL(10,2)")
    @Builder.Default
    private Double comision = 0.0;

    /**
     * Monto neto recibido (monto - comisión)
     */
    @Column(name = "monto_neto", columnDefinition = "DECIMAL(10,2)")
    private Double montoNeto;

    /**
     * Número de referencia/transacción
     */
    @Size(max = 100, message = "El número de referencia no puede exceder 100 caracteres")
    @Column(name = "numero_referencia", length = 100)
    private String numeroReferencia;

    /**
     * Número de voucher o comprobante
     */
    @Size(max = 50, message = "El número de voucher no puede exceder 50 caracteres")
    @Column(name = "numero_voucher", length = 50)
    private String numeroVoucher;

    /**
     * Usuario que registró el pago
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_registro_id", nullable = false)
    @NotNull(message = "El usuario de registro es obligatorio")
    private Usuario usuarioRegistro;

    /**
     * Observaciones del pago
     */
    @Column(name = "observaciones", length = 500)
    private String observaciones;

    /**
     * Indica si el pago fue verificado
     */
    @Column(name = "verificado")
    @Builder.Default
    private Boolean verificado = false;

    /**
     * Fecha de verificación
     */
    @Column(name = "fecha_verificacion")
    private LocalDateTime fechaVerificacion;

    /**
     * Usuario que verificó el pago
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_verificacion_id")
    private Usuario usuarioVerificacion;

    // ==================== MÉTODOS HELPER SIMPLES ====================

    /**
     * Verifica si el pago fue verificado
     * @return true si fue verificado
     */
    public boolean estaVerificado() {
        return Boolean.TRUE.equals(verificado);
    }

    /**
     * Verifica si tiene comisión
     * @return true si tiene comisión
     */
    public boolean tieneComision() {
        return comision != null && comision > 0;
    }

    /**
     * Verifica si tiene número de referencia
     * @return true si tiene referencia
     */
    public boolean tieneReferencia() {
        return numeroReferencia != null && !numeroReferencia.trim().isEmpty();
    }

    /**
     * Verifica si el pago es en efectivo
     * @return true si es efectivo
     */
    public boolean esEfectivo() {
        return metodoPago == MetodoPago.EFECTIVO;
    }

    /**
     * Verifica si el pago es con tarjeta
     * @return true si es con tarjeta
     */
    public boolean esConTarjeta() {
        return metodoPago == MetodoPago.TARJETA_CREDITO ||
               metodoPago == MetodoPago.TARJETA_DEBITO;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "id=" + id +
                ", fechaPago=" + fechaPago +
                ", metodoPago=" + metodoPago +
                ", monto=" + monto +
                ", verificado=" + verificado +
                '}';
    }
}
