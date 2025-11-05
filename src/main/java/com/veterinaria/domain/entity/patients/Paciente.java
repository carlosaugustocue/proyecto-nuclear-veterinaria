package com.veterinaria.domain.entity.patients;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import com.veterinaria.domain.enums.EstadoPaciente;
import com.veterinaria.domain.enums.Sexo;
import com.veterinaria.domain.enums.TipoEspecie;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * Entidad abstracta base para todos los pacientes (mascotas).
 * Implementa el patrón Template Method definiendo la estructura común
 * y dejando que las subclases especifiquen comportamientos específicos.
 *
 * Estrategia de herencia: SINGLE_TABLE para mejor rendimiento.
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "pacientes", indexes = {
    @Index(name = "idx_paciente_nombre", columnList = "nombre"),
    @Index(name = "idx_paciente_estado", columnList = "estado"),
    @Index(name = "idx_paciente_especie", columnList = "especie")
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_especie", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Paciente extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del paciente es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotNull(message = "La especie es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoEspecie especie;

    @Size(max = 100, message = "La raza no puede exceder 100 caracteres")
    @Column(length = 100)
    private String raza;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @NotNull(message = "El sexo es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Sexo sexo;

    @Size(max = 50, message = "El color no puede exceder 50 caracteres")
    @Column(length = 50)
    private String color;

    @DecimalMin(value = "0.1", message = "El peso debe ser mayor a 0.1 kg")
    @DecimalMax(value = "500.0", message = "El peso no puede exceder 500 kg")
    @Column(precision = 5, scale = 2)
    private Double pesoKg;

    @NotNull(message = "El estado del paciente es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EstadoPaciente estado = EstadoPaciente.ACTIVO;

    @Column(name = "foto_url", length = 500)
    private String fotoUrl;

    @Size(max = 1000, message = "Las observaciones no pueden exceder 1000 caracteres")
    @Column(length = 1000)
    private String observaciones;

    @Column(name = "microchip", unique = true, length = 50)
    private String microchip;

    /**
     * Relación ManyToOne con Cliente (propietario).
     * Un paciente pertenece a un cliente.
     * Nullable = true para permitir registro de pacientes sin cliente (casos de emergencia).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;

    /**
     * Calcula la edad del paciente en años.
     * @return edad en años
     */
    public int calcularEdadAnios() {
        if (fechaNacimiento == null) {
            return 0;
        }
        return LocalDate.now().getYear() - fechaNacimiento.getYear();
    }

    /**
     * Verifica si el paciente está activo y puede recibir atención.
     * @return true si está activo
     */
    public boolean estaActivo() {
        return estado == EstadoPaciente.ACTIVO;
    }

    /**
     * Marca el paciente como fallecido.
     */
    public void marcarComoFallecido() {
        this.estado = EstadoPaciente.FALLECIDO;
    }

    /**
     * Marca el paciente como inactivo.
     */
    public void marcarComoInactivo() {
        this.estado = EstadoPaciente.INACTIVO;
    }

    /**
     * Reactiva un paciente inactivo.
     * Solo puede reactivarse si no está fallecido.
     */
    public void reactivar() {
        if (this.estado != EstadoPaciente.FALLECIDO) {
            this.estado = EstadoPaciente.ACTIVO;
        } else {
            throw new IllegalStateException("No se puede reactivar un paciente fallecido");
        }
    }

    /**
     * Método abstracto que debe implementar cada especie para obtener
     * información específica de cuidados.
     * Template Method Pattern.
     */
    public abstract String obtenerCuidadosEspecificos();

    /**
     * Método abstracto para obtener la dieta recomendada según la especie.
     * Template Method Pattern.
     */
    public abstract String obtenerDietaRecomendada();
}
