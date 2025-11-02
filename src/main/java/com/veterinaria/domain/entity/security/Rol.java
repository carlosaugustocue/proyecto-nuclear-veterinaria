package com.veterinaria.domain.entity.security;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Entidad que representa un rol en el sistema de seguridad.
 * Los roles definen conjuntos de permisos que se asignan a los usuarios.
 */
@Entity
@Table(name = "roles", uniqueConstraints = {
        @UniqueConstraint(columnNames = "nombre")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rol extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(max = 50, message = "El nombre del rol no puede exceder 50 caracteres")
    @Column(name = "nombre", nullable = false, unique = true, length = 50)
    private String nombre;

    @Size(max = 255, message = "La descripción no puede exceder 255 caracteres")
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Permisos asociados a este rol.
     * Relación Many-to-Many con la entidad Permiso.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "rol_permisos",
            joinColumns = @JoinColumn(name = "rol_id")
    )
    @Column(name = "permiso")
    @Builder.Default
    private Set<String> permisos = new HashSet<>();

    /**
     * Nivel jerárquico del rol (1 = más alto, 10 = más bajo).
     * Se usa para validar que un usuario solo pueda gestionar roles de nivel inferior.
     */
    @Column(name = "nivel_jerarquico")
    private Integer nivelJerarquico;

    /**
     * Agrega un permiso al rol.
     *
     * @param permiso Permiso a agregar
     */
    public void agregarPermiso(String permiso) {
        if (this.permisos == null) {
            this.permisos = new HashSet<>();
        }
        this.permisos.add(permiso);
    }

    /**
     * Remueve un permiso del rol.
     *
     * @param permiso Permiso a remover
     */
    public void removerPermiso(String permiso) {
        if (this.permisos != null) {
            this.permisos.remove(permiso);
        }
    }

    /**
     * Verifica si el rol tiene un permiso específico.
     *
     * @param permiso Permiso a verificar
     * @return true si el rol tiene el permiso
     */
    public boolean tienePermiso(String permiso) {
        return this.permisos != null && this.permisos.contains(permiso);
    }

    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", nivelJerarquico=" + nivelJerarquico +
                '}';
    }
}
