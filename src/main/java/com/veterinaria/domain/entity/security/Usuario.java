package com.veterinaria.domain.entity.security;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import com.veterinaria.domain.enums.TipoUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad que representa un usuario del sistema.
 * Implementa el patrón Builder para construcción flexible de objetos complejos.
 */
@Entity
@Table(name = "usuarios", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
}, indexes = {
        @Index(name = "idx_username", columnList = "username"),
        @Index(name = "idx_email", columnList = "email"),
        @Index(name = "idx_tipo_usuario", columnList = "tipo_usuario")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede exceder 100 caracteres")
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Size(max = 8, message = "El DNI debe tener 8 dígitos")
    @Pattern(regexp = "^[0-9]{8}$", message = "El DNI debe contener solo dígitos")
    @Column(name = "dni", length = 8, unique = true)
    private String dni;

    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    @Column(name = "telefono", length = 20)
    private String telefono;

    @Size(max = 255, message = "La dirección no puede exceder 255 caracteres")
    @Column(name = "direccion")
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false, length = 20)
    private TipoUsuario tipoUsuario;

    /**
     * Roles asignados al usuario.
     * Relación Many-to-Many con la entidad Rol.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"),
            indexes = {
                    @Index(name = "idx_usuario_roles_usuario", columnList = "usuario_id"),
                    @Index(name = "idx_usuario_roles_rol", columnList = "rol_id")
            }
    )
    @Builder.Default
    private Set<Rol> roles = new HashSet<>();

    @Column(name = "cuenta_bloqueada", nullable = false)
    @Builder.Default
    private Boolean cuentaBloqueada = false;

    @Column(name = "cuenta_expirada", nullable = false)
    @Builder.Default
    private Boolean cuentaExpirada = false;

    @Column(name = "credenciales_expiradas", nullable = false)
    @Builder.Default
    private Boolean credencialesExpiradas = false;

    @Column(name = "intentos_fallidos", nullable = false)
    @Builder.Default
    private Integer intentosFallidos = 0;

    @Column(name = "ultimo_acceso")
    private LocalDateTime ultimoAcceso;

    @Column(name = "fecha_cambio_password")
    private LocalDateTime fechaCambioPassword;

    /**
     * Indica si el usuario debe cambiar su contraseña en el próximo login.
     */
    @Column(name = "requiere_cambio_password", nullable = false)
    @Builder.Default
    private Boolean requiereCambioPassword = false;

    /**
     * URL de la foto de perfil del usuario.
     */
    @Column(name = "foto_perfil_url")
    private String fotoperfilUrl;

    /**
     * Agrega un rol al usuario.
     *
     * @param rol Rol a agregar
     */
    public void agregarRol(Rol rol) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles.add(rol);
    }

    /**
     * Remueve un rol del usuario.
     *
     * @param rol Rol a remover
     */
    public void removerRol(Rol rol) {
        if (this.roles != null) {
            this.roles.remove(rol);
        }
    }

    /**
     * Verifica si el usuario tiene un rol específico.
     *
     * @param nombreRol Nombre del rol a verificar
     * @return true si el usuario tiene el rol
     */
    public boolean tieneRol(String nombreRol) {
        return this.roles != null &&
                this.roles.stream().anyMatch(r -> r.getNombre().equals(nombreRol));
    }

    /**
     * Bloquea la cuenta del usuario.
     */
    public void bloquearCuenta() {
        this.cuentaBloqueada = true;
    }

    /**
     * Desbloquea la cuenta del usuario.
     */
    public void desbloquearCuenta() {
        this.cuentaBloqueada = false;
        this.intentosFallidos = 0;
    }

    /**
     * Incrementa el contador de intentos fallidos de login.
     *
     * @param maxIntentos Máximo de intentos permitidos antes de bloquear
     */
    public void incrementarIntentosFallidos(int maxIntentos) {
        this.intentosFallidos++;
        if (this.intentosFallidos >= maxIntentos) {
            this.bloquearCuenta();
        }
    }

    /**
     * Resetea el contador de intentos fallidos.
     */
    public void resetearIntentosFallidos() {
        this.intentosFallidos = 0;
    }

    /**
     * Actualiza la fecha del último acceso.
     */
    public void actualizarUltimoAcceso() {
        this.ultimoAcceso = LocalDateTime.now();
    }

    /**
     * Obtiene el nombre completo del usuario.
     *
     * @return Nombre completo
     */
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    /**
     * Verifica si la cuenta está habilitada.
     *
     * @return true si la cuenta está habilitada
     */
    public boolean isCuentaHabilitada() {
        return Boolean.TRUE.equals(getIsActive()) &&
                !Boolean.TRUE.equals(cuentaBloqueada) &&
                !Boolean.TRUE.equals(cuentaExpirada);
    }

    /**
     * Verifica si las credenciales no han expirado.
     *
     * @return true si las credenciales son válidas
     */
    public boolean areCredencialesValidas() {
        return !Boolean.TRUE.equals(credencialesExpiradas);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", tipoUsuario=" + tipoUsuario +
                '}';
    }
}
