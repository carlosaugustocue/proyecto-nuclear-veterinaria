package com.veterinaria.domain.entity.security;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa un rol en el sistema.
 * Los roles agrupan permisos y se asignan a usuarios.
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Rol extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del rol (ej: ADMINISTRADOR, VETERINARIO, RECEPCIONISTA)
     */
    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    /**
     * Descripción del rol
     */
    @Column(length = 255)
    private String descripcion;

    /**
     * Permisos asociados al rol (Many-to-Many)
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "roles_permisos",
        joinColumns = @JoinColumn(name = "rol_id"),
        inverseJoinColumns = @JoinColumn(name = "permiso_id")
    )
    @Builder.Default
    private List<Permiso> permisos = new ArrayList<>();

    /**
     * Usuarios que tienen este rol (Many-to-Many inversa)
     */
    @ManyToMany(mappedBy = "roles")
    @Builder.Default
    private List<Usuario> usuarios = new ArrayList<>();

    /**
     * Agrega un permiso al rol
     *
     * @param permiso permiso a agregar
     */
    public void agregarPermiso(Permiso permiso) {
        if (permiso != null && !permisos.contains(permiso)) {
            permisos.add(permiso);
        }
    }

    /**
     * Remueve un permiso del rol
     *
     * @param permiso permiso a remover
     */
    public void removerPermiso(Permiso permiso) {
        permisos.remove(permiso);
    }

    /**
     * Verifica si el rol tiene un permiso específico
     *
     * @param codigoPermiso código del permiso a verificar
     * @return true si el rol tiene el permiso
     */
    public boolean tienePermiso(String codigoPermiso) {
        return permisos.stream()
            .anyMatch(p -> p.getCodigo().equals(codigoPermiso));
    }

    @Override
    public String toString() {
        return "Rol{" +
                "id=" + getId() +
                ", nombre='" + nombre + '\'' +
                ", activo=" + getIsActive() +
                ", permisos=" + permisos.size() +
                '}';
    }
}
