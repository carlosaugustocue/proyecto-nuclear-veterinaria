package com.veterinaria.application.service;

import com.veterinaria.application.dto.security.ChangePasswordRequest;
import com.veterinaria.application.dto.security.CreateUsuarioRequest;
import com.veterinaria.application.dto.security.UpdateUsuarioRequest;
import com.veterinaria.application.dto.security.UsuarioDTO;
import com.veterinaria.application.repository.RolRepository;
import com.veterinaria.application.repository.UsuarioRepository;
import com.veterinaria.domain.entity.security.Rol;
import com.veterinaria.domain.entity.security.Usuario;
import com.veterinaria.domain.enums.TipoUsuario;
import com.veterinaria.infrastructure.exception.BusinessException;
import com.veterinaria.infrastructure.exception.DuplicateResourceException;
import com.veterinaria.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Servicio de gestión de usuarios.
 * Maneja operaciones CRUD y gestión de roles.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final SesionService sesionService;

    /**
     * Obtiene todos los usuarios activos.
     *
     * @param pageable Configuración de paginación
     * @return Página de usuarios
     */
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> obtenerTodos(Pageable pageable) {
        return usuarioRepository.findByIsActiveTrue(pageable)
                .map(this::convertirADTO);
    }

    /**
     * Obtiene un usuario por ID.
     *
     * @param id ID del usuario
     * @return UsuarioDTO
     */
    @Transactional(readOnly = true)
    public UsuarioDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

        return convertirADTO(usuario);
    }

    /**
     * Obtiene un usuario por username.
     *
     * @param username Username
     * @return UsuarioDTO
     */
    @Transactional(readOnly = true)
    public UsuarioDTO obtenerPorUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "username", username));

        return convertirADTO(usuario);
    }

    /**
     * Busca usuarios por nombre o apellido.
     *
     * @param termino  Término de búsqueda
     * @param pageable Configuración de paginación
     * @return Página de usuarios que coinciden
     */
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> buscarPorNombre(String termino, Pageable pageable) {
        return usuarioRepository.buscarPorNombreOApellido(termino, termino, pageable)
                .map(this::convertirADTO);
    }

    /**
     * Obtiene usuarios por tipo.
     *
     * @param tipoUsuario Tipo de usuario
     * @param pageable    Configuración de paginación
     * @return Página de usuarios
     */
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> obtenerPorTipo(TipoUsuario tipoUsuario, Pageable pageable) {
        return usuarioRepository.findByTipoUsuario(tipoUsuario, pageable)
                .map(this::convertirADTO);
    }

    /**
     * Crea un nuevo usuario.
     *
     * @param request Datos del usuario
     * @return Usuario creado
     */
    @Transactional
    public UsuarioDTO crear(CreateUsuarioRequest request) {
        log.info("Creando nuevo usuario: {}", request.getUsername());

        // Validar que no exista el username
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Usuario", "username", request.getUsername());
        }

        // Validar que no exista el email
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Usuario", "email", request.getEmail());
        }

        // Validar DNI si está presente
        if (request.getDni() != null && usuarioRepository.existsByDni(request.getDni())) {
            throw new DuplicateResourceException("Usuario", "dni", request.getDni());
        }

        // Crear usuario
        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .dni(request.getDni())
                .telefono(request.getTelefono())
                .direccion(request.getDireccion())
                .tipoUsuario(request.getTipoUsuario())
                .intentosFallidos(0)
                .cuentaBloqueada(false)
                .cuentaExpirada(false)
                .credencialesExpiradas(false)
                .requiereCambioPassword(false)
                .roles(new HashSet<>())
                .build();

        // Asignar roles
        if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
            Set<Rol> roles = request.getRoleIds().stream()
                    .map(roleId -> rolRepository.findById(roleId)
                            .orElseThrow(() -> new ResourceNotFoundException("Rol", "id", roleId)))
                    .collect(Collectors.toSet());
            usuario.setRoles(roles);
        }

        Usuario savedUsuario = usuarioRepository.save(usuario);

        log.info("Usuario creado exitosamente: {}", savedUsuario.getUsername());

        return convertirADTO(savedUsuario);
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param id      ID del usuario
     * @param request Datos a actualizar
     * @return Usuario actualizado
     */
    @Transactional
    public UsuarioDTO actualizar(Long id, UpdateUsuarioRequest request) {
        log.info("Actualizando usuario ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

        // Actualizar email si cambió
        if (request.getEmail() != null && !request.getEmail().equals(usuario.getEmail())) {
            if (usuarioRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateResourceException("Usuario", "email", request.getEmail());
            }
            usuario.setEmail(request.getEmail());
        }

        // Actualizar DNI si cambió
        if (request.getDni() != null && !request.getDni().equals(usuario.getDni())) {
            if (usuarioRepository.existsByDni(request.getDni())) {
                throw new DuplicateResourceException("Usuario", "dni", request.getDni());
            }
            usuario.setDni(request.getDni());
        }

        // Actualizar otros campos
        if (request.getNombre() != null) {
            usuario.setNombre(request.getNombre());
        }
        if (request.getApellido() != null) {
            usuario.setApellido(request.getApellido());
        }
        if (request.getTelefono() != null) {
            usuario.setTelefono(request.getTelefono());
        }
        if (request.getDireccion() != null) {
            usuario.setDireccion(request.getDireccion());
        }
        if (request.getFotoperfilUrl() != null) {
            usuario.setFotoperfilUrl(request.getFotoperfilUrl());
        }

        // Actualizar roles si se proporcionaron
        if (request.getRoleIds() != null) {
            Set<Rol> roles = request.getRoleIds().stream()
                    .map(roleId -> rolRepository.findById(roleId)
                            .orElseThrow(() -> new ResourceNotFoundException("Rol", "id", roleId)))
                    .collect(Collectors.toSet());
            usuario.setRoles(roles);
        }

        Usuario updatedUsuario = usuarioRepository.save(usuario);

        log.info("Usuario actualizado exitosamente: {}", updatedUsuario.getUsername());

        return convertirADTO(updatedUsuario);
    }

    /**
     * Elimina un usuario (soft delete).
     *
     * @param id ID del usuario
     */
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando usuario ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

        // Soft delete
        usuario.softDelete();
        usuarioRepository.save(usuario);

        // Cerrar todas las sesiones activas
        sesionService.cerrarTodasLasSesiones(id);

        log.info("Usuario eliminado (soft delete) exitosamente: {}", usuario.getUsername());
    }

    /**
     * Desbloquea la cuenta de un usuario.
     *
     * @param id ID del usuario
     */
    @Transactional
    public void desbloquearCuenta(Long id) {
        log.info("Desbloqueando cuenta del usuario ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

        usuario.desbloquearCuenta();
        usuarioRepository.save(usuario);

        log.info("Cuenta desbloqueada: {}", usuario.getUsername());
    }

    /**
     * Cambia la contraseña de un usuario.
     *
     * @param id      ID del usuario
     * @param request Datos del cambio de contraseña
     */
    @Transactional
    public void cambiarPassword(Long id, ChangePasswordRequest request) {
        log.info("Cambiando contraseña del usuario ID: {}", id);

        // Validar que las contraseñas coincidan
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BusinessException("Las contraseñas no coinciden");
        }

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

        // Verificar contraseña actual
        if (!passwordEncoder.matches(request.getCurrentPassword(), usuario.getPassword())) {
            throw new BusinessException("La contraseña actual es incorrecta");
        }

        // Actualizar contraseña
        usuario.setPassword(passwordEncoder.encode(request.getNewPassword()));
        usuario.setFechaCambioPassword(LocalDateTime.now());
        usuario.setRequiereCambioPassword(false);
        usuarioRepository.save(usuario);

        // Cerrar otras sesiones por seguridad
        sesionService.cerrarTodasLasSesiones(id);

        log.info("Contraseña cambiada exitosamente para usuario: {}", usuario.getUsername());
    }

    /**
     * Obtiene la entidad Usuario por ID.
     *
     * @param id ID del usuario
     * @return Usuario
     */
    @Transactional(readOnly = true)
    public Usuario obtenerEntidadPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
    }

    /**
     * Convierte una entidad Usuario a DTO.
     */
    private UsuarioDTO convertirADTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .nombreCompleto(usuario.getNombreCompleto())
                .dni(usuario.getDni())
                .telefono(usuario.getTelefono())
                .direccion(usuario.getDireccion())
                .tipoUsuario(usuario.getTipoUsuario())
                .roles(usuario.getRoles().stream()
                        .map(Rol::getNombre)
                        .collect(Collectors.toSet()))
                .cuentaBloqueada(usuario.getCuentaBloqueada())
                .cuentaExpirada(usuario.getCuentaExpirada())
                .intentosFallidos(usuario.getIntentosFallidos())
                .ultimoAcceso(usuario.getUltimoAcceso())
                .fechaCambioPassword(usuario.getFechaCambioPassword())
                .requiereCambioPassword(usuario.getRequiereCambioPassword())
                .fotoperfilUrl(usuario.getFotoperfilUrl())
                .isActive(usuario.getIsActive())
                .createdAt(usuario.getCreatedAt())
                .updatedAt(usuario.getUpdatedAt())
                .build();
    }
}
