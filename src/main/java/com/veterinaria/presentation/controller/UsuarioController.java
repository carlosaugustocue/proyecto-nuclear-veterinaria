package com.veterinaria.presentation.controller;

import com.veterinaria.application.dto.usuario.ChangePasswordRequest;
import com.veterinaria.application.dto.usuario.CreateUsuarioRequest;
import com.veterinaria.application.dto.usuario.UpdateUsuarioRequest;
import com.veterinaria.application.dto.usuario.UsuarioDTO;
import com.veterinaria.application.service.SesionService;
import com.veterinaria.application.service.UsuarioService;
import com.veterinaria.domain.entity.security.Sesion;
import com.veterinaria.domain.enums.TipoUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gestión de usuarios.
 * Endpoints para CRUD de usuarios, gestión de sesiones y administración de cuentas.
 */
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Usuarios", description = "Endpoints para gestión de usuarios")
@SecurityRequirement(name = "Bearer Authentication")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final SesionService sesionService;

    /**
     * Obtiene todos los usuarios (paginado).
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    @Operation(summary = "Listar usuarios", description = "Obtiene todos los usuarios activos con paginación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios obtenidos exitosamente"),
            @ApiResponse(responseCode = "403", description = "No tiene permisos para acceder")
    })
    public ResponseEntity<Page<UsuarioDTO>> obtenerTodos(
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("GET /usuarios - Page: {}, Size: {}", pageable.getPageNumber(), pageable.getPageSize());

        Page<UsuarioDTO> usuarios = usuarioService.obtenerTodos(pageable);

        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtiene un usuario por ID.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO') or #id == authentication.principal.id")
    @Operation(summary = "Obtener usuario por ID", description = "Obtiene los detalles de un usuario específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "403", description = "No tiene permisos")
    })
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Long id) {
        log.info("GET /usuarios/{}", id);

        UsuarioDTO usuario = usuarioService.obtenerPorId(id);

        return ResponseEntity.ok(usuario);
    }

    /**
     * Busca usuarios por nombre o apellido.
     */
    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO', 'RECEPCIONISTA')")
    @Operation(summary = "Buscar usuarios", description = "Busca usuarios por nombre o apellido")
    public ResponseEntity<Page<UsuarioDTO>> buscar(
            @RequestParam String termino,
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("GET /usuarios/buscar?termino={}", termino);

        Page<UsuarioDTO> usuarios = usuarioService.buscarPorNombre(termino, pageable);

        return ResponseEntity.ok(usuarios);
    }

    /**
     * Obtiene usuarios por tipo.
     */
    @GetMapping("/tipo/{tipo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VETERINARIO')")
    @Operation(summary = "Obtener usuarios por tipo", description = "Filtra usuarios por tipo (VETERINARIO, RECEPCIONISTA, etc.)")
    public ResponseEntity<Page<UsuarioDTO>> obtenerPorTipo(
            @PathVariable TipoUsuario tipo,
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("GET /usuarios/tipo/{}", tipo);

        Page<UsuarioDTO> usuarios = usuarioService.obtenerPorTipo(tipo, pageable);

        return ResponseEntity.ok(usuarios);
    }

    /**
     * Crea un nuevo usuario.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear usuario", description = "Crea un nuevo usuario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "409", description = "El usuario ya existe"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<UsuarioDTO> crear(@Valid @RequestBody CreateUsuarioRequest request) {
        log.info("POST /usuarios - Username: {}", request.getUsername());

        UsuarioDTO usuario = usuarioService.crear(request);

        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    /**
     * Actualiza un usuario existente.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "409", description = "Email o DNI duplicado")
    })
    public ResponseEntity<UsuarioDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUsuarioRequest request) {
        log.info("PUT /usuarios/{}", id);

        UsuarioDTO usuario = usuarioService.actualizar(id, request);

        return ResponseEntity.ok(usuario);
    }

    /**
     * Elimina un usuario (soft delete).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema (soft delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE /usuarios/{}", id);

        usuarioService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Desbloquea la cuenta de un usuario.
     */
    @PostMapping("/{id}/desbloquear")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Desbloquear cuenta", description = "Desbloquea una cuenta bloqueada por intentos fallidos")
    public ResponseEntity<MessageResponse> desbloquear(@PathVariable Long id) {
        log.info("POST /usuarios/{}/desbloquear", id);

        usuarioService.desbloquearCuenta(id);

        return ResponseEntity.ok(new MessageResponse("Cuenta desbloqueada exitosamente"));
    }

    /**
     * Cambia la contraseña de un usuario.
     */
    @PostMapping("/{id}/cambiar-password")
    @PreAuthorize("#id == authentication.principal.id")
    @Operation(summary = "Cambiar contraseña", description = "Cambia la contraseña de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contraseña cambiada"),
            @ApiResponse(responseCode = "400", description = "Contraseña actual incorrecta o las nuevas no coinciden")
    })
    public ResponseEntity<MessageResponse> cambiarPassword(
            @PathVariable Long id,
            @Valid @RequestBody ChangePasswordRequest request) {
        log.info("POST /usuarios/{}/cambiar-password", id);

        usuarioService.cambiarPassword(id, request);

        return ResponseEntity.ok(new MessageResponse("Contraseña cambiada exitosamente"));
    }

    /**
     * Obtiene las sesiones activas de un usuario.
     */
    @GetMapping("/{id}/sesiones")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Operation(summary = "Obtener sesiones activas", description = "Lista las sesiones activas de un usuario")
    public ResponseEntity<List<Sesion>> obtenerSesiones(@PathVariable Long id) {
        log.info("GET /usuarios/{}/sesiones", id);

        List<Sesion> sesiones = sesionService.obtenerSesionesActivas(id);

        return ResponseEntity.ok(sesiones);
    }

    /**
     * Cierra una sesión específica de un usuario.
     */
    @DeleteMapping("/{id}/sesiones/{sesionId}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Operation(summary = "Cerrar sesión", description = "Cierra una sesión específica de un usuario")
    public ResponseEntity<MessageResponse> cerrarSesion(
            @PathVariable Long id,
            @PathVariable Long sesionId) {
        log.info("DELETE /usuarios/{}/sesiones/{}", id, sesionId);

        sesionService.cerrarSesion(sesionId);

        return ResponseEntity.ok(new MessageResponse("Sesión cerrada exitosamente"));
    }

    /**
     * Cierra todas las sesiones de un usuario.
     */
    @DeleteMapping("/{id}/sesiones")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Operation(summary = "Cerrar todas las sesiones", description = "Cierra todas las sesiones activas de un usuario")
    public ResponseEntity<MessageResponse> cerrarTodasLasSesiones(@PathVariable Long id) {
        log.info("DELETE /usuarios/{}/sesiones", id);

        sesionService.cerrarTodasLasSesiones(id);

        return ResponseEntity.ok(new MessageResponse("Todas las sesiones cerradas exitosamente"));
    }

    /**
     * Clase interna para respuestas de mensajes simples.
     */
    @lombok.Data
    @lombok.AllArgsConstructor
    public static class MessageResponse {
        private String message;
    }
}
