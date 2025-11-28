package com.veterinaria.application.dto.security;

import com.veterinaria.domain.enums.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO para transferencia de información de usuario.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String username;
    private String email;
    private String nombre;
    private String apellido;
    private String nombreCompleto;
    private String dni;
    private String telefono;
    private String direccion;
    private TipoUsuario tipoUsuario;
    private Set<String> roles;
    private Boolean cuentaBloqueada;
    private Boolean cuentaExpirada;
    private Integer intentosFallidos;
    private LocalDateTime ultimoAcceso;
    private LocalDateTime fechaCambioPassword;
    private Boolean requiereCambioPassword;
    private String fotoperfilUrl;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
