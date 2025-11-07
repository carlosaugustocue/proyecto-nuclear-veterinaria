package com.veterinaria.infrastructure.security;

import com.veterinaria.application.repository.UsuarioRepository;
import com.veterinaria.domain.entity.security.Rol;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Servicio personalizado para cargar detalles de usuario.
 * Implementa UserDetailsService de Spring Security.
 * Patrón Adapter - Adapta la entidad Usuario al UserDetails de Spring Security.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Cargando usuario: {}", username);

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado: {}", username);
                    return new UsernameNotFoundException("Usuario no encontrado: " + username);
                });

        // Verificar si la cuenta está habilitada
        if (!Boolean.TRUE.equals(usuario.getIsActive())) {
            log.warn("Intento de login con cuenta inactiva: {}", username);
            throw new UsernameNotFoundException("Cuenta inactiva: " + username);
        }

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(getAuthorities(usuario))
                .accountExpired(Boolean.TRUE.equals(usuario.getCuentaExpirada()))
                .accountLocked(Boolean.TRUE.equals(usuario.getCuentaBloqueada()))
                .credentialsExpired(Boolean.TRUE.equals(usuario.getCredencialesExpiradas()))
                .disabled(!Boolean.TRUE.equals(usuario.getIsActive()))
                .build();
    }

    /**
     * Carga un usuario por ID.
     *
     * @param id ID del usuario
     * @return UserDetails
     */
    @Transactional(readOnly = true)
    public UserDetails loadUserById(Long id) {
        log.debug("Cargando usuario por ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(getAuthorities(usuario))
                .accountExpired(Boolean.TRUE.equals(usuario.getCuentaExpirada()))
                .accountLocked(Boolean.TRUE.equals(usuario.getCuentaBloqueada()))
                .credentialsExpired(Boolean.TRUE.equals(usuario.getCredencialesExpiradas()))
                .disabled(!Boolean.TRUE.equals(usuario.getIsActive()))
                .build();
    }

    /**
     * Obtiene las authorities (roles y permisos) de un usuario.
     *
     * @param usuario Usuario
     * @return Colección de authorities
     */
    private Collection<? extends GrantedAuthority> getAuthorities(Usuario usuario) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        // Agregar roles
        if (usuario.getRoles() != null) {
            for (Rol rol : usuario.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(rol.getNombre()));

                // Agregar permisos del rol
                if (rol.getPermisos() != null) {
                    authorities.addAll(
                            rol.getPermisos().stream()
                                    .map(permiso -> new SimpleGrantedAuthority(permiso.getCodigo()))
                                    .collect(Collectors.toSet())
                    );
                }
            }
        }

        log.debug("Authorities para usuario {}: {}", usuario.getUsername(), authorities);
        return authorities;
    }

    /**
     * Obtiene la entidad Usuario completa por username.
     * Útil cuando se necesita más información que la proporcionada por UserDetails.
     *
     * @param username Username
     * @return Usuario
     */
    @Transactional(readOnly = true)
    public Usuario getUsuarioByUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "username", username));
    }
}
