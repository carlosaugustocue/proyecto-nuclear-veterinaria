package com.veterinaria.domain.entity.security;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entidad que representa un permiso en el sistema.
 * Los permisos definen acciones específicas que pueden realizar los usuarios.
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "permisos")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Permiso extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Código único del permiso (ej: CITAS_CREAR, HISTORIAL_VER)
     */
    @Column(nullable = false, unique = true, length = 50)
    private String codigo;

    /**
     * Nombre descriptivo del permiso
     */
    @Column(nullable = false, length = 100)
    private String nombre;

    /**
     * Módulo al que pertenece el permiso (ej: CITAS, HISTORIAL, INVENTARIO)
     */
    @Column(nullable = false, length = 50)
    private String modulo;

    /**
     * Descripción detallada del permiso
     */
    @Column(length = 255)
    private String descripcion;

    /**
     * Verifica si el permiso es igual a otro código
     *
     * @param codigoPermiso código a comparar
     * @return true si los códigos coinciden
     */
    public boolean esIgualA(String codigoPermiso) {
        return this.codigo != null && this.codigo.equals(codigoPermiso);
    }

    @Override
    public String toString() {
        return "Permiso{" +
                "id=" + getId() +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", modulo='" + modulo + '\'' +
                '}';
    }
}
