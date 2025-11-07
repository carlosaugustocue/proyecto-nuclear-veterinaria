# PLAN DE IMPLEMENTACIÓN - MÓDULO DE SEGURIDAD

**Fecha inicio:** 2025-01-06
**Prioridad:** 🔴 CRÍTICA
**Estrategia:** Implementación rápida, tests después

---

## 🎯 OBJETIVO

Implementar el módulo completo de seguridad del sistema veterinaria incluyendo:
- ✅ Autenticación con JWT
- ✅ Gestión de roles y permisos
- ✅ Auditoría de accesos
- ✅ Bloqueo por intentos fallidos
- ✅ Gestión de sesiones activas

---

## 📋 PLAN DE EJECUCIÓN (15 TAREAS)

### **FASE 1: Entidades y Repositorios Base** (Tareas 1-2)

#### Tarea 1: Crear Entidades de Seguridad
**Ubicación:** `src/main/java/com/veterinaria/domain/entity/security/`

**Archivos a crear:**

1. **`Rol.java`**
```java
@Entity
@Table(name = "roles")
public class Rol extends BaseAuditableEntity {
    private String nombre;
    private String descripcion;
    private boolean activo;

    @ManyToMany
    @JoinTable(name = "roles_permisos",
        joinColumns = @JoinColumn(name = "rol_id"),
        inverseJoinColumns = @JoinColumn(name = "permiso_id"))
    private List<Permiso> permisos;

    // Relación con usuarios
    @ManyToMany(mappedBy = "roles")
    private List<Usuario> usuarios;
}
```

2. **`Permiso.java`**
```java
@Entity
@Table(name = "permisos")
public class Permiso extends BaseAuditableEntity {
    private String codigo;
    private String nombre;
    private String modulo;
    private String descripcion;
}
```

3. **`RegistroAuditoria.java`**
```java
@Entity
@Table(name = "auditoria_accesos")
public class RegistroAuditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private LocalDateTime fechaHora;
    private String accion;
    private String modulo;
    private String recurso;

    @Enumerated(EnumType.STRING)
    private ResultadoAccion resultado;

    private String direccionIP;
    private String detalles;
    private long duracionMs;
}
```

4. **`SesionActiva.java`**
```java
@Entity
@Table(name = "sesiones_activas")
public class SesionActiva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String direccionIP;
    private LocalDateTime inicioSesion;
    private LocalDateTime ultimaActividad;
    private boolean activa;
}
```

5. **`IntentosLogin.java`**
```java
@Entity
@Table(name = "intentos_login")
public class IntentosLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private int intentosFallidos;
    private LocalDateTime ultimoIntento;
    private LocalDateTime bloqueadoHasta;
}
```

6. **`DispositivoConfiable.java`**
```java
@Entity
@Table(name = "dispositivos_confiables")
public class DispositivoConfiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String huella;
    private String nombreDispositivo;
    private LocalDateTime fechaRegistro;
}
```

**Enums a crear:**

7. **`ResultadoAccion.java`**
```java
public enum ResultadoAccion {
    EXITOSO,
    DENEGADO_SIN_PERMISO,
    DENEGADO_SIN_AUTENTICACION,
    DENEGADO_BLOQUEADO,
    ERROR
}
```

**Modificar:** `Usuario.java` - Agregar relación ManyToMany con roles:
```java
@ManyToMany
@JoinTable(name = "usuarios_roles",
    joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id"))
private List<Rol> roles = new ArrayList<>();
```

---

#### Tarea 2: Crear Repositorios
**Ubicación:** `src/main/java/com/veterinaria/application/repository/`

**Archivos a crear:**

1. **`RolRepository.java`**
```java
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
    List<Rol> findByActivoTrue();
}
```

2. **`PermisoRepository.java`**
```java
public interface PermisoRepository extends JpaRepository<Permiso, Long> {
    Optional<Permiso> findByCodigo(String codigo);
    List<Permiso> findByModulo(String modulo);
}
```

3. **`AuditoriaRepository.java`**
```java
public interface AuditoriaRepository extends JpaRepository<RegistroAuditoria, Long> {
    List<RegistroAuditoria> findByUsuarioAndFechaHoraBetween(
        Usuario usuario, LocalDateTime inicio, LocalDateTime fin);
    List<RegistroAuditoria> findByResultado(ResultadoAccion resultado);
}
```

4. **`SesionActivaRepository.java`**
```java
public interface SesionActivaRepository extends JpaRepository<SesionActiva, Long> {
    List<SesionActiva> findByUsuarioAndActivaTrue(Usuario usuario);
    Optional<SesionActiva> findByTokenAndActivaTrue(String token);
    void deleteByUsuarioAndActivaTrue(Usuario usuario);
}
```

5. **`IntentosLoginRepository.java`**
```java
public interface IntentosLoginRepository extends JpaRepository<IntentosLogin, Long> {
    Optional<IntentosLogin> findByEmail(String email);
    void deleteByEmail(String email);
}
```

6. **`DispositivoConfiableRepository.java`**
```java
public interface DispositivoConfiableRepository extends JpaRepository<DispositivoConfiable, Long> {
    List<DispositivoConfiable> findByUsuario(Usuario usuario);
    boolean existsByUsuarioAndHuella(Usuario usuario, String huella);
}
```

---

### **FASE 2: Componentes de Seguridad Core** (Tareas 3-7)

#### Tarea 3: Implementar EncriptadorContraseña
**Ubicación:** `src/main/java/com/veterinaria/infrastructure/security/`

**Archivo:** `EncriptadorContrasena.java`
```java
@Component
public class EncriptadorContrasena {
    private final PasswordEncoder passwordEncoder;

    public EncriptadorContrasena() {
        this.passwordEncoder = new BCryptPasswordEncoder(12);
    }

    public String encriptar(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean verificar(String password, String hash) {
        return passwordEncoder.matches(password, hash);
    }
}
```

---

#### Tarea 4: Crear DTOs de Autenticación
**Ubicación:** `src/main/java/com/veterinaria/application/dto/auth/`

**Archivos a crear:**

1. **`LoginRequest.java`**
```java
public record LoginRequest(
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email inválido")
    String email,

    @NotBlank(message = "La contraseña es obligatoria")
    String password,

    String huella // Para identificación de dispositivo
) {}
```

2. **`LoginResponse.java`**
```java
public record LoginResponse(
    String token,
    String tipoToken,
    Long expiresIn,
    UsuarioDTO usuario
) {}
```

3. **`CambiarPasswordRequest.java`**
```java
public record CambiarPasswordRequest(
    @NotBlank String passwordActual,
    @NotBlank @Size(min = 8) String passwordNueva
) {}
```

4. **`RecuperarPasswordRequest.java`**
```java
public record RecuperarPasswordRequest(
    @NotBlank @Email String email
) {}
```

---

#### Tarea 5: Implementar JwtTokenProvider
**Ubicación:** `src/main/java/com/veterinaria/infrastructure/security/jwt/`

**Archivo:** `JwtTokenProvider.java`
```java
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public String generarToken(Usuario usuario) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
            .setSubject(usuario.getEmail().getValor())
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .claim("userId", usuario.getId())
            .claim("roles", usuario.getRoles().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toList()))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    public String obtenerEmailDeToken(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
```

**Agregar a `application.properties`:**
```properties
jwt.secret=clave-secreta-super-segura-cambiar-en-produccion-minimo-256-bits
jwt.expiration=86400000
```

---

#### Tarea 6: Implementar GestorSesiones (Singleton)
**Ubicación:** `src/main/java/com/veterinaria/infrastructure/security/session/`

**Archivo:** `GestorSesiones.java`
```java
@Component
public class GestorSesiones {

    private final SesionActivaRepository sesionRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public SesionActiva crearSesion(Usuario usuario, String ip, String token) {
        // Cerrar otras sesiones del mismo usuario (opcional)
        // cerrarOtrasSesiones(usuario, token);

        SesionActiva sesion = SesionActiva.builder()
            .token(token)
            .usuario(usuario)
            .direccionIP(ip)
            .inicioSesion(LocalDateTime.now())
            .ultimaActividad(LocalDateTime.now())
            .activa(true)
            .build();

        return sesionRepository.save(sesion);
    }

    public void cerrarSesion(String token) {
        sesionRepository.findByTokenAndActivaTrue(token)
            .ifPresent(sesion -> {
                sesion.setActiva(false);
                sesionRepository.save(sesion);
            });
    }

    public boolean validarSesion(String token) {
        return sesionRepository.findByTokenAndActivaTrue(token).isPresent();
    }

    public List<SesionActiva> obtenerSesionesUsuario(Usuario usuario) {
        return sesionRepository.findByUsuarioAndActivaTrue(usuario);
    }

    public void renovarSesion(String token) {
        sesionRepository.findByTokenAndActivaTrue(token)
            .ifPresent(sesion -> {
                sesion.setUltimaActividad(LocalDateTime.now());
                sesionRepository.save(sesion);
            });
    }
}
```

---

#### Tarea 7: Implementar GestorIntentosFallidos
**Ubicación:** `src/main/java/com/veterinaria/infrastructure/security/login/`

**Archivo:** `GestorIntentosFallidos.java`
```java
@Service
public class GestorIntentosFallidos {

    private static final int MAX_INTENTOS = 5;
    private static final int TIEMPO_BLOQUEO_MINUTOS = 15;

    private final IntentosLoginRepository intentosRepository;

    public void registrarIntentoFallido(String email) {
        IntentosLogin intentos = intentosRepository.findByEmail(email)
            .orElse(IntentosLogin.builder()
                .email(email)
                .intentosFallidos(0)
                .build());

        intentos.setIntentosFallidos(intentos.getIntentosFallidos() + 1);
        intentos.setUltimoIntento(LocalDateTime.now());

        if (intentos.getIntentosFallidos() >= MAX_INTENTOS) {
            intentos.setBloqueadoHasta(
                LocalDateTime.now().plusMinutes(TIEMPO_BLOQUEO_MINUTOS)
            );
        }

        intentosRepository.save(intentos);
    }

    public void registrarIntentoExitoso(String email) {
        intentosRepository.findByEmail(email)
            .ifPresent(intentosRepository::delete);
    }

    public boolean estaBloqueado(String email) {
        return intentosRepository.findByEmail(email)
            .map(intentos -> {
                if (intentos.getBloqueadoHasta() == null) {
                    return false;
                }

                if (LocalDateTime.now().isBefore(intentos.getBloqueadoHasta())) {
                    return true;
                }

                // Bloqueo expirado, eliminar registro
                intentosRepository.delete(intentos);
                return false;
            })
            .orElse(false);
    }

    public void desbloquear(String email) {
        intentosRepository.findByEmail(email)
            .ifPresent(intentosRepository::delete);
    }
}
```

---

### **FASE 3: Servicios de Negocio** (Tareas 8-10)

#### Tarea 8: Implementar ServicioAutenticacion
**Ubicación:** `src/main/java/com/veterinaria/application/service/impl/`

**Archivo:** `ServicioAutenticacionImpl.java`
```java
@Service
@Transactional
public class ServicioAutenticacionImpl implements ServicioAutenticacion {

    private final UsuarioRepository usuarioRepository;
    private final EncriptadorContrasena encriptador;
    private final JwtTokenProvider jwtTokenProvider;
    private final GestorSesiones gestorSesiones;
    private final GestorIntentosFallidos gestorIntentosFallidos;
    private final AuditoriaAcceso auditoriaAcceso;

    @Override
    public LoginResponse login(LoginRequest request, String ip) {
        // Validar si está bloqueado
        if (gestorIntentosFallidos.estaBloqueado(request.email())) {
            auditoriaAcceso.registrarIntentoFallido(request.email(),
                ResultadoAccion.DENEGADO_BLOQUEADO, ip);
            throw new SecurityException("Cuenta bloqueada temporalmente");
        }

        // Buscar usuario
        Usuario usuario = usuarioRepository.findByEmail(request.email())
            .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));

        // Validar contraseña
        if (!encriptador.verificar(request.password(), usuario.getPasswordHash())) {
            gestorIntentosFallidos.registrarIntentoFallido(request.email());
            auditoriaAcceso.registrarIntentoFallido(request.email(),
                ResultadoAccion.DENEGADO_SIN_AUTENTICACION, ip);
            throw new BadCredentialsException("Credenciales inválidas");
        }

        // Validar que esté activo
        if (!usuario.isActivo()) {
            throw new DisabledException("Usuario inactivo");
        }

        // Generar token
        String token = jwtTokenProvider.generarToken(usuario);

        // Crear sesión
        gestorSesiones.crearSesion(usuario, ip, token);

        // Registrar intento exitoso
        gestorIntentosFallidos.registrarIntentoExitoso(request.email());
        auditoriaAcceso.registrarAccesoExitoso(usuario, "LOGIN", ip);

        return new LoginResponse(
            token,
            "Bearer",
            jwtExpiration,
            usuarioMapper.toDTO(usuario)
        );
    }

    @Override
    public void logout(String token) {
        gestorSesiones.cerrarSesion(token);
        // Auditoría
    }

    @Override
    public void cambiarPassword(Usuario usuario, CambiarPasswordRequest request) {
        // Validar password actual
        if (!encriptador.verificar(request.passwordActual(), usuario.getPasswordHash())) {
            throw new BadCredentialsException("Contraseña actual incorrecta");
        }

        // Actualizar password
        usuario.setPasswordHash(encriptador.encriptar(request.passwordNueva()));
        usuarioRepository.save(usuario);

        // Auditoría
    }

    @Override
    public void recuperarPassword(String email) {
        // TODO: Implementar envío de email con token temporal
        // Por ahora solo registramos el intento
    }
}
```

---

#### Tarea 9: Implementar ServicioAutorizacion
**Ubicación:** `src/main/java/com/veterinaria/application/service/impl/`

**Archivo:** `ServicioAutorizacionImpl.java`
```java
@Service
public class ServicioAutorizacionImpl implements ServicioAutorizacion {

    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;

    @Override
    public boolean tienePermiso(Usuario usuario, String codigoPermiso) {
        return usuario.getRoles().stream()
            .flatMap(rol -> rol.getPermisos().stream())
            .anyMatch(permiso -> permiso.getCodigo().equals(codigoPermiso));
    }

    @Override
    public boolean tieneRol(Usuario usuario, String nombreRol) {
        return usuario.getRoles().stream()
            .anyMatch(rol -> rol.getNombre().equals(nombreRol));
    }

    @Override
    public boolean tieneAlgunoDeEstosPermisos(Usuario usuario, List<String> permisos) {
        Set<String> permisosUsuario = usuario.getRoles().stream()
            .flatMap(rol -> rol.getPermisos().stream())
            .map(Permiso::getCodigo)
            .collect(Collectors.toSet());

        return permisos.stream().anyMatch(permisosUsuario::contains);
    }

    @Override
    public List<Permiso> obtenerPermisosUsuario(Usuario usuario) {
        return usuario.getRoles().stream()
            .flatMap(rol -> rol.getPermisos().stream())
            .distinct()
            .collect(Collectors.toList());
    }
}
```

---

#### Tarea 10: Implementar AuditoriaAcceso
**Ubicación:** `src/main/java/com/veterinaria/application/service/impl/`

**Archivo:** `AuditoriaAccesoImpl.java`
```java
@Service
public class AuditoriaAccesoImpl implements AuditoriaAcceso {

    private final AuditoriaRepository auditoriaRepository;

    @Override
    public void registrarAccesoExitoso(Usuario usuario, String accion, String ip) {
        RegistroAuditoria registro = RegistroAuditoria.builder()
            .usuario(usuario)
            .fechaHora(LocalDateTime.now())
            .accion(accion)
            .resultado(ResultadoAccion.EXITOSO)
            .direccionIP(ip)
            .build();

        auditoriaRepository.save(registro);
    }

    @Override
    public void registrarIntentoFallido(String email, ResultadoAccion resultado, String ip) {
        RegistroAuditoria registro = RegistroAuditoria.builder()
            .fechaHora(LocalDateTime.now())
            .accion("LOGIN_FALLIDO")
            .resultado(resultado)
            .direccionIP(ip)
            .detalles("Email: " + email)
            .build();

        auditoriaRepository.save(registro);
    }

    // Métodos de consulta...
}
```

---

### **FASE 4: Configuración Spring Security** (Tareas 11-12)

#### Tarea 11: Configurar Spring Security
**Ubicación:** `src/main/java/com/veterinaria/infrastructure/config/`

**Archivo:** `SecurityConfig.java`
```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/publico/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
```

---

#### Tarea 12: Crear Filtro JwtAuthenticationFilter
**Ubicación:** `src/main/java/com/veterinaria/infrastructure/security/filters/`

**Archivo:** `JwtAuthenticationFilter.java`
```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;
    private final GestorSesiones gestorSesiones;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain)
                                   throws ServletException, IOException {
        try {
            String token = obtenerTokenDeRequest(request);

            if (token != null && jwtTokenProvider.validarToken(token)) {
                // Validar que la sesión esté activa
                if (!gestorSesiones.validarSesion(token)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Sesión inválida o expirada");
                    return;
                }

                String email = jwtTokenProvider.obtenerEmailDeToken(token);
                Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow();

                // Crear autenticación
                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                        usuario,
                        null,
                        obtenerAuthorities(usuario)
                    );

                SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

                // Renovar actividad de la sesión
                gestorSesiones.renovarSesion(token);
            }
        } catch (Exception e) {
            logger.error("Error en filtro JWT", e);
        }

        filterChain.doFilter(request, response);
    }

    private String obtenerTokenDeRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> obtenerAuthorities(Usuario usuario) {
        return usuario.getRoles().stream()
            .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getNombre()))
            .collect(Collectors.toList());
    }
}
```

---

### **FASE 5: Controllers y Endpoints** (Tarea 13)

#### Tarea 13: Implementar AuthController
**Ubicación:** `src/main/java/com/veterinaria/presentation/controller/`

**Archivo:** `AuthController.java`
```java
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticación", description = "Endpoints de autenticación y seguridad")
public class AuthController {

    private final ServicioAutenticacion servicioAutenticacion;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {

        String ip = obtenerIP(httpRequest);
        LoginResponse response = servicioAutenticacion.login(request, ip);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String token = obtenerToken(request);
        servicioAutenticacion.logout(token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cambiar-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> cambiarPassword(
            @Valid @RequestBody CambiarPasswordRequest request,
            @AuthenticationPrincipal Usuario usuario) {

        servicioAutenticacion.cambiarPassword(usuario, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/recuperar-password")
    public ResponseEntity<Void> recuperarPassword(
            @Valid @RequestBody RecuperarPasswordRequest request) {

        servicioAutenticacion.recuperarPassword(request.email());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioActual(
            @AuthenticationPrincipal Usuario usuario) {

        return ResponseEntity.ok(usuarioMapper.toDTO(usuario));
    }

    private String obtenerIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private String obtenerToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        return bearer != null ? bearer.substring(7) : null;
    }
}
```

---

### **FASE 6: Interceptores y Datos Iniciales** (Tareas 14-15)

#### Tarea 14: Crear Interceptores de Seguridad
**Ubicación:** `src/main/java/com/veterinaria/infrastructure/security/interceptor/`

**Archivo:** `SecurityInterceptor.java`
```java
@Component
public class SecurityInterceptor implements HandlerInterceptor {

    private final AuditoriaAcceso auditoriaAcceso;

    @Override
    public boolean preHandle(HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler) {
        // Log de request entrante
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                               HttpServletResponse response,
                               Object handler,
                               Exception ex) {
        // Registrar auditoría de todas las operaciones
    }
}
```

---

#### Tarea 15: Agregar Datos Iniciales (DataLoader)
**Ubicación:** `src/main/java/com/veterinaria/infrastructure/config/`

**Archivo:** `DataLoader.java`
```java
@Component
public class DataLoader implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EncriptadorContrasena encriptador;

    @Override
    @Transactional
    public void run(String... args) {
        if (rolRepository.count() > 0) {
            return; // Ya hay datos
        }

        // Crear permisos
        Permiso permisoCrearCita = crearPermiso("CITAS_CREAR", "Crear citas", "CITAS");
        Permiso permisoVerCita = crearPermiso("CITAS_VER", "Ver citas", "CITAS");
        // ... más permisos

        // Crear roles
        Rol rolAdmin = crearRol("ADMINISTRADOR", "Administrador del sistema",
            List.of(permisoCrearCita, permisoVerCita /* todos */));
        Rol rolVet = crearRol("VETERINARIO", "Veterinario",
            List.of(permisoVerCita /* permisos médicos */));
        Rol rolRecep = crearRol("RECEPCIONISTA", "Recepcionista",
            List.of(permisoCrearCita, permisoVerCita));

        // Crear usuario admin por defecto
        crearUsuarioAdmin(rolAdmin);
    }

    private Permiso crearPermiso(String codigo, String nombre, String modulo) {
        return permisoRepository.save(Permiso.builder()
            .codigo(codigo)
            .nombre(nombre)
            .modulo(modulo)
            .build());
    }

    private Rol crearRol(String nombre, String descripcion, List<Permiso> permisos) {
        return rolRepository.save(Rol.builder()
            .nombre(nombre)
            .descripcion(descripcion)
            .activo(true)
            .permisos(permisos)
            .build());
    }

    private void crearUsuarioAdmin(Rol rolAdmin) {
        Administrador admin = Administrador.builder()
            .nombre("Admin")
            .apellido("Sistema")
            .email(new Email("admin@veterinaria.com"))
            .telefono(new Telefono("3001234567", "+57"))
            .passwordHash(encriptador.encriptar("Admin123!"))
            .activo(true)
            .roles(List.of(rolAdmin))
            .nivelAcceso(10)
            .build();

        usuarioRepository.save(admin);
    }
}
```

---

## 📊 CONSTANTES DE PERMISOS

**Archivo:** `PermisosDelSistema.java`
```java
public class PermisosDelSistema {
    // Citas
    public static final String CITAS_CREAR = "CITAS_CREAR";
    public static final String CITAS_VER = "CITAS_VER";
    public static final String CITAS_EDITAR = "CITAS_EDITAR";
    public static final String CITAS_CANCELAR = "CITAS_CANCELAR";

    // Historial
    public static final String HISTORIAL_VER = "HISTORIAL_VER";
    public static final String HISTORIAL_CREAR = "HISTORIAL_CREAR";
    public static final String HISTORIAL_EDITAR = "HISTORIAL_EDITAR";

    // Facturación
    public static final String FACTURA_CREAR = "FACTURA_CREAR";
    public static final String FACTURA_ANULAR = "FACTURA_ANULAR";

    // Inventario
    public static final String INVENTARIO_VER = "INVENTARIO_VER";
    public static final String INVENTARIO_INGRESAR = "INVENTARIO_INGRESAR";
    public static final String INVENTARIO_DAR_BAJA = "INVENTARIO_DAR_BAJA";

    // Usuarios
    public static final String USUARIOS_CREAR = "USUARIOS_CREAR";
    public static final String USUARIOS_EDITAR = "USUARIOS_EDITAR";
    public static final String USUARIOS_VER = "USUARIOS_VER";
}
```

---

## 🎯 ORDEN DE EJECUCIÓN RECOMENDADO

1. ✅ Crear todas las entidades (Tarea 1)
2. ✅ Crear todos los repositorios (Tarea 2)
3. ✅ Implementar EncriptadorContraseña (Tarea 3)
4. ✅ Crear DTOs (Tarea 4)
5. ✅ Implementar JwtTokenProvider (Tarea 5)
6. ✅ Implementar GestorSesiones (Tarea 6)
7. ✅ Implementar GestorIntentosFallidos (Tarea 7)
8. ✅ Implementar ServicioAutenticacion (Tarea 8)
9. ✅ Implementar ServicioAutorizacion (Tarea 9)
10. ✅ Implementar AuditoriaAcceso (Tarea 10)
11. ✅ Configurar Spring Security (Tarea 11)
12. ✅ Crear JwtAuthenticationFilter (Tarea 12)
13. ✅ Implementar AuthController (Tarea 13)
14. ✅ Crear interceptores (Tarea 14)
15. ✅ Agregar datos iniciales (Tarea 15)

---

## ✅ VERIFICACIÓN FINAL

Después de completar todas las tareas, verificar:

1. **Compilación:** `mvn clean compile` sin errores
2. **Inicio de aplicación:** `mvn spring-boot:run` exitoso
3. **Login funcional:** Probar con `admin@veterinaria.com` / `Admin123!`
4. **Token generado:** Verificar que se genera JWT
5. **Endpoints protegidos:** Verificar que sin token retorna 401
6. **Roles funcionando:** Verificar permisos por rol

---

## 📝 DEPENDENCIAS A AGREGAR EN POM.XML

```xml
<!-- JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>

<!-- Spring Security ya está incluido -->
```

---

## 🚀 ESTIMACIÓN DE TIEMPO

- **Fase 1 (Entidades y Repos):** 2-3 horas
- **Fase 2 (Componentes Core):** 2-3 horas
- **Fase 3 (Servicios):** 3-4 horas
- **Fase 4 (Spring Security):** 2-3 horas
- **Fase 5 (Controllers):** 1-2 horas
- **Fase 6 (Datos iniciales):** 1 hora

**Total estimado:** 11-16 horas de trabajo

---

## 📌 NOTAS IMPORTANTES

1. **Orden:** Seguir el orden de las tareas para evitar dependencias faltantes
2. **Tests:** Los dejamos para después según acuerdo
3. **Passwords:** Cambiar la clave JWT en producción
4. **Email:** La recuperación de contraseña por email la implementamos después
5. **Dispositivos:** La gestión de dispositivos confiables es opcional por ahora

---

**Última actualización:** 2025-01-06
**Estado:** ✅ Plan listo para ejecución
