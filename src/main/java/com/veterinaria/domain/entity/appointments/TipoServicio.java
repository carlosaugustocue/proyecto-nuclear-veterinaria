package com.veterinaria.domain.entity.appointments;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entidad que representa un tipo de servicio veterinario.
 * Define los servicios que ofrece la clínica con su precio base y duración estimada.
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "tipos_servicio", indexes = {
        @Index(name = "idx_tipo_servicio_categoria", columnList = "categoria")
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TipoServicio extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del servicio es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(nullable = false, length = 100, unique = true)
    private String nombre;

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    @Column(length = 500)
    private String descripcion;

    /**
     * Duración estimada del servicio en minutos
     */
    @NotNull(message = "La duración estimada es obligatoria")
    @Min(value = 5, message = "La duración mínima es 5 minutos")
    @Max(value = 2880, message = "La duración máxima es 2880 minutos (48 horas)")
    @Column(name = "duracion_estimada", nullable = false)
    private Integer duracionEstimada;

    /**
     * Precio base del servicio
     */
    @NotNull(message = "El precio base es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Column(name = "precio_base", nullable = false)
    private Double precioBase;

    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 50, message = "La categoría no puede exceder 50 caracteres")
    @Column(nullable = false, length = 50)
    private String categoria;

    /**
     * Indica si el servicio requiere confirmación especial
     */
    @Column(name = "requiere_confirmacion")
    @Builder.Default
    private Boolean requiereConfirmacion = false;

    /**
     * Verifica si el servicio está disponible (activo)
     * @return true si el servicio está activo
     */
    public boolean estaDisponible() {
        return Boolean.TRUE.equals(getIsActive());
    }

    /**
     * Calcula el precio con un recargo (por ejemplo, urgencia)
     * @param porcentajeRecargo porcentaje de recargo a aplicar
     * @return precio con recargo aplicado
     */
    public Double calcularPrecioConRecargo(double porcentajeRecargo) {
        if (porcentajeRecargo < 0) {
            throw new IllegalArgumentException("El recargo no puede ser negativo");
        }
        return precioBase + (precioBase * porcentajeRecargo / 100);
    }

    @Override
    public String toString() {
        return "TipoServicio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", precioBase=" + precioBase +
                ", duracionEstimada=" + duracionEstimada +
                '}';
    }
}
