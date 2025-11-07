package com.veterinaria.domain.entity.patients;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import com.veterinaria.domain.enums.TipoEspecie;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entidad que representa una raza de mascota en el sistema.
 * Permite gestionar un catálogo de razas por especie, incluyendo
 * razas predefinidas, mestizos y razas personalizadas.
 *
 * Implementa HU-09: Tipos de Mascotas
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "razas",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_raza_nombre_especie",
                         columnNames = {"nombre", "especie"})
    },
    indexes = {
        @Index(name = "idx_raza_especie", columnList = "especie"),
        @Index(name = "idx_raza_nombre", columnList = "nombre"),
        @Index(name = "idx_raza_activa", columnList = "is_active")
    }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Raza extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de la raza (ej: Labrador, Siamés, Mestizo)
     */
    @NotBlank(message = "El nombre de la raza es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    /**
     * Especie a la que pertenece esta raza
     */
    @NotNull(message = "La especie es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoEspecie especie;

    /**
     * Descripción opcional de la raza (características, temperamento, etc.)
     */
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    @Column(length = 500)
    private String descripcion;

    /**
     * Indica si es una raza predefinida del sistema o personalizada
     */
    @Column(name = "es_predefinida", nullable = false)
    @Builder.Default
    private Boolean esPredefinida = false;

    /**
     * Indica si es mestizo (raza mixta)
     */
    @Column(name = "es_mestizo", nullable = false)
    @Builder.Default
    private Boolean esMestizo = false;

    /**
     * Tamaño típico de la raza (pequeño, mediano, grande, gigante)
     * Solo aplicable a algunas especies como PERRO
     */
    @Column(length = 20)
    private String tamanioTipico;

    /**
     * Peso promedio en kg (opcional)
     */
    @Column(name = "peso_promedio_kg")
    private Double pesoPromedioKg;

    /**
     * Verifica si la raza está activa
     * @return true si está activa
     */
    public boolean estaActiva() {
        return Boolean.TRUE.equals(getIsActive());
    }

    /**
     * Activa la raza
     */
    public void activar() {
        setIsActive(true);
    }

    /**
     * Desactiva la raza (no elimina, solo oculta de búsquedas)
     */
    public void desactivar() {
        setIsActive(false);
    }

    /**
     * Verifica si es una raza genérica (Mestizo u Otra)
     * @return true si es genérica
     */
    public boolean esRazaGenerica() {
        return Boolean.TRUE.equals(esMestizo) ||
               "Mestizo".equalsIgnoreCase(nombre) ||
               "Otro".equalsIgnoreCase(nombre) ||
               "Otra".equalsIgnoreCase(nombre);
    }

    @Override
    public String toString() {
        return "Raza{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", especie=" + especie +
                ", activa=" + getIsActive() +
                ", predefinida=" + esPredefinida +
                ", mestizo=" + esMestizo +
                '}';
    }
}
