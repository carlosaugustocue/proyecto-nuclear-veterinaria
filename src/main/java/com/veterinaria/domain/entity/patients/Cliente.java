package com.veterinaria.domain.entity.patients;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa al propietario de mascotas (Cliente).
 * Un cliente puede tener múltiples pacientes (mascotas).
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "clientes", indexes = {
    @Index(name = "idx_cliente_dni", columnList = "dni"),
    @Index(name = "idx_cliente_email", columnList = "email"),
    @Index(name = "idx_cliente_apellido", columnList = "apellido")
})
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String apellido;

    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 7, max = 20, message = "El DNI debe tener entre 7 y 20 caracteres")
    @Column(unique = true, nullable = false, length = 20)
    private String dni;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[+]?[0-9]{7,15}$", message = "El teléfono debe tener entre 7 y 15 dígitos")
    @Column(nullable = false, length = 20)
    private String telefono;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 300, message = "La dirección no puede exceder 300 caracteres")
    @Column(nullable = false, length = 300)
    private String direccion;

    @Size(max = 100, message = "La ciudad no puede exceder 100 caracteres")
    @Column(length = 100)
    private String ciudad;

    @Size(max = 100, message = "El departamento no puede exceder 100 caracteres")
    @Column(length = 100)
    private String departamento;

    @Column(name = "codigo_postal", length = 20)
    private String codigoPostal;

    @Size(max = 500, message = "Las observaciones no pueden exceder 500 caracteres")
    @Column(length = 500)
    private String observaciones;

    /**
     * Relación OneToMany bidireccional con Paciente.
     * Un cliente puede tener múltiples mascotas.
     */
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Paciente> pacientes = new ArrayList<>();

    /**
     * Obtiene el nombre completo del cliente.
     * @return nombre completo
     */
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    /**
     * Agrega un paciente a la lista del cliente.
     * Mantiene la consistencia bidireccional.
     *
     * @param paciente paciente a agregar
     */
    public void agregarPaciente(Paciente paciente) {
        if (paciente != null) {
            pacientes.add(paciente);
            paciente.setCliente(this);
        }
    }

    /**
     * Remueve un paciente de la lista del cliente.
     * Mantiene la consistencia bidireccional.
     *
     * @param paciente paciente a remover
     */
    public void removerPaciente(Paciente paciente) {
        if (paciente != null) {
            pacientes.remove(paciente);
            paciente.setCliente(null);
        }
    }

    /**
     * Obtiene la cantidad de pacientes activos del cliente.
     * @return cantidad de pacientes activos
     */
    public long contarPacientesActivos() {
        return pacientes.stream()
                .filter(Paciente::estaActivo)
                .count();
    }

    /**
     * Verifica si el cliente tiene pacientes registrados.
     * @return true si tiene al menos un paciente
     */
    public boolean tienePacientes() {
        return !pacientes.isEmpty();
    }
}
