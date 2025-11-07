# INFORME: PATRONES DE DISEÑO Y FLUJOS DE EJECUCIÓN

**Proyecto**: Sistema de Gestión Veterinaria  
**Fecha**: 2025-11-07

---

## 1. PATRONES DE DISEÑO IMPLEMENTADOS

### 1.1 Factory Method Pattern
**Ubicación**: `src/main/java/com/veterinaria/domain/factory/PacienteFactory.java`

**Propósito**: Crear instancias polimórficas de Paciente según el tipo de especie

**Implementación**:
```java
public class PacienteFactory {
    public static Paciente crearPaciente(TipoEspecie tipo) {
        return switch (tipo) {
            case PERRO -> new Perro();
            case GATO -> new Gato();
            case AVE -> new Ave();
            case REPTIL -> new Reptil();
            case ROEDOR -> new Roedor();
        };
    }
}
```

**Uso en**:
- `src/main/java/com/veterinaria/application/service/impl/PacienteServiceImpl.java:65`
  ```java
  Paciente paciente = PacienteFactory.crearPaciente(request.getEspecie());
  ```

---

### 1.2 Template Method Pattern
**Ubicación**: `src/main/java/com/veterinaria/domain/entity/patients/Paciente.java`

**Propósito**: Definir el esqueleto de comportamiento común en la clase abstracta y delegar detalles específicos a subclases

**Implementación**:
```java
@MappedSuperclass
public abstract class Paciente extends BaseAuditableEntity {
    // Campos comunes a todas las especies
    
    // Método abstracto que cada especie debe implementar
    public abstract TipoEspecie getTipo();
    
    // Método template con comportamiento común
    public String getInformacionCompleta() {
        return String.format("%s - %s (%s)", nombre, getTipo(), raza);
    }
}
```

**Subclases**:
- `src/main/java/com/veterinaria/domain/entity/patients/Perro.java`
- `src/main/java/com/veterinaria/domain/entity/patients/Gato.java`
- `src/main/java/com/veterinaria/domain/entity/patients/Ave.java`
- `src/main/java/com/veterinaria/domain/entity/patients/Reptil.java`
- `src/main/java/com/veterinaria/domain/entity/patients/Roedor.java`

---

### 1.3 Repository Pattern
**Ubicación**: `src/main/java/com/veterinaria/application/repository/*.java`

**Propósito**: Abstraer el acceso a datos y desacoplar la lógica de negocio de la persistencia

**Implementación** (ejemplos):
```java
// ClienteRepository
src/main/java/com/veterinaria/application/repository/ClienteRepository.java
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByDni(String dni);
    Optional<Cliente> findByEmail(String email);
}

// PacienteRepository
src/main/java/com/veterinaria/application/repository/PacienteRepository.java
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByMicrochip(String microchip);
    List<Paciente> findByEspecie(TipoEspecie especie);
}
```

**Repositorios implementados**:
1. `UsuarioRepository.java`
2. `RolRepository.java`
3. `PermisoRepository.java`
4. `SesionActivaRepository.java`
5. `AuditLogRepository.java`
6. `ClienteRepository.java`
7. `PacienteRepository.java`
8. `RazaRepository.java`
9. `HistorialEstadoPacienteRepository.java`

---

### 1.4 Builder Pattern
**Ubicación**: Todas las entidades y DTOs

**Propósito**: Construir objetos complejos de manera fluida y legible

**Implementación** (usando Lombok):
```java
// En entidades de dominio
@Entity
@SuperBuilder  // Para clases con herencia
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends BaseAuditableEntity {
    // campos...
}

// Uso:
Cliente cliente = Cliente.builder()
    .nombre("Juan")
    .apellido("Pérez")
    .dni("12345678")
    .email("juan@email.com")
    .build();
```

**Ubicaciones**:
- Entidades: `src/main/java/com/veterinaria/domain/entity/**/*.java`
- DTOs: `src/main/java/com/veterinaria/application/dto/**/*.java`

---

### 1.5 Value Object Pattern
**Ubicación**: `src/main/java/com/veterinaria/domain/valueobject/`

**Propósito**: Encapsular conceptos de negocio con validación y comportamiento propio

**Implementación**:

**1. Email.java**
```java
src/main/java/com/veterinaria/domain/valueobject/Email.java

@Embeddable
public class Email {
    private String direccion;
    
    public Email(String direccion) {
        validar(direccion);
        this.direccion = direccion.toLowerCase();
    }
    
    private void validar(String email) {
        // Validación de formato
    }
}
```

**2. Telefono.java**
```java
src/main/java/com/veterinaria/domain/valueobject/Telefono.java

@Embeddable
public class Telefono {
    private String codigoPais;
    private String numero;
    
    public String getNumeroCompleto() {
        return codigoPais + " " + numero;
    }
}
```

**3. Direccion.java**
```java
src/main/java/com/veterinaria/domain/valueobject/Direccion.java

@Embeddable
public class Direccion {
    private String calle;
    private String ciudad;
    private String codigoPostal;
}
```

---

### 1.6 DTO Pattern (Data Transfer Object)
**Ubicación**: `src/main/java/com/veterinaria/application/dto/**/*.java`

**Propósito**: Transferir datos entre capas sin exponer entidades de dominio

**Implementación**:
```java
// DTOs de respuesta
src/main/java/com/veterinaria/application/dto/cliente/ClienteDTO.java
src/main/java/com/veterinaria/application/dto/paciente/PacienteDTO.java

// DTOs de request
src/main/java/com/veterinaria/application/dto/cliente/CreateClienteRequest.java
src/main/java/com/veterinaria/application/dto/paciente/CreatePacienteRequest.java
```

**Mappers** (usando MapStruct):
```java
src/main/java/com/veterinaria/application/mapper/ClienteMapper.java
src/main/java/com/veterinaria/application/mapper/PacienteMapper.java
```

---

### 1.7 Strategy Pattern
**Ubicación**: Validaciones específicas por tipo de paciente

**Propósito**: Encapsular algoritmos de validación específicos para cada especie

**Implementación implícita**:
```java
// En cada subclase de Paciente
src/main/java/com/veterinaria/domain/entity/patients/Perro.java
public class Perro extends Paciente {
    @Override
    public void validarEspecifico() {
        // Validaciones específicas de perro
    }
}
```

---

### 1.8 Dependency Injection Pattern
**Ubicación**: Todos los servicios y controladores

**Propósito**: Inyectar dependencias y favorecer el bajo acoplamiento

**Implementación** (usando Spring):
```java
@RestController
@RequiredArgsConstructor  // Lombok genera constructor con dependencias final
public class ClienteController {
    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;
}
```

---

### 1.9 Singleton Pattern
**Ubicación**: Beans de Spring (implícito)

**Propósito**: Una única instancia de servicios, repositorios y configuraciones

**Implementación** (Spring Container):
```java
@Service  // Spring crea un singleton por defecto
public class ClienteServiceImpl implements ClienteService {
    // ...
}
```

---

## 2. FLUJOS DE EJECUCIÓN COMPLETOS

### 2.1 FLUJO: LOGIN DE USUARIO

**Request**: `POST http://localhost:8080/api/auth/login`

**Recorrido completo**:

```
1. PUNTO DE ENTRADA - Controller
   📁 src/main/java/com/veterinaria/presentation/controller/AuthController.java:37
   ├─ Método: login(@RequestBody LoginRequest request, HttpServletRequest httpRequest)
   └─ Recibe: { "email": "admin@veterinaria.com", "password": "Admin123!" }

2. FILTRO DE SEGURIDAD (bypassed para /api/auth/login)
   📁 src/main/java/com/veterinaria/infrastructure/security/config/SecurityConfig.java:65
   └─ .requestMatchers("/api/auth/login").permitAll()

3. VALIDACIÓN DE REQUEST
   📁 src/main/java/com/veterinaria/presentation/dto/auth/LoginRequest.java
   ├─ @NotBlank en email
   ├─ @Email en email
   └─ @NotBlank en password

4. OBTENCIÓN DE IP
   📁 src/main/java/com/veterinaria/application/service/impl/ServicioAutenticacionImpl.java:144
   └─ Método: obtenerDireccionIP(HttpServletRequest request)

5. SERVICIO DE AUTENTICACIÓN
   📁 src/main/java/com/veterinaria/application/service/impl/ServicioAutenticacionImpl.java:48
   ├─ Método: login(LoginRequest request, String direccionIP)
   │
   ├─ 5.1 BUSCAR USUARIO POR EMAIL
   │   📁 src/main/java/com/veterinaria/application/repository/UsuarioRepository.java:22
   │   └─ findByEmail(String email)
   │       └─ Query: SELECT * FROM usuarios WHERE email = ?
   │
   ├─ 5.2 VALIDAR USUARIO ACTIVO
   │   📁 src/main/java/com/veterinaria/domain/entity/security/Usuario.java:124
   │   └─ Método: isCuentaHabilitada()
   │       └─ Verifica: isActive == true
   │
   ├─ 5.3 VALIDAR PASSWORD
   │   📁 src/main/java/com/veterinaria/infrastructure/security/config/SecurityConfig.java:42
   │   └─ PasswordEncoder (BCrypt strength 12)
   │       └─ passwordEncoder.matches(passwordPlano, passwordHash)
   │
   ├─ 5.4 CARGAR ROLES Y PERMISOS
   │   📁 src/main/java/com/veterinaria/domain/entity/security/Usuario.java:134
   │   ├─ Método: obtenerPermisos()
   │   └─ Recorre roles → permisos (relación ManyToMany)
   │       └─ Query: SELECT p.* FROM permisos p 
   │                 JOIN roles_permisos rp ON p.id = rp.permiso_id
   │                 JOIN usuarios_roles ur ON rp.rol_id = ur.rol_id
   │                 WHERE ur.usuario_id = ?
   │
   ├─ 5.5 GENERAR TOKEN JWT
   │   📁 src/main/java/com/veterinaria/infrastructure/security/jwt/JwtTokenProvider.java:52
   │   ├─ Método: generarToken(String email, Collection<String> roles, permisos)
   │   ├─ Claims: email, roles, permisos, fechaCreacion, expiración
   │   ├─ Algoritmo: HS256
   │   ├─ Secret: jwt.secret (application.properties:44)
   │   └─ Expiración: 24 horas (86400000 ms)
   │
   └─ 5.6 CREAR SESIÓN ACTIVA
       📁 src/main/java/com/veterinaria/application/repository/SesionActivaRepository.java
       └─ save(SesionActiva)
           └─ INSERT INTO sesiones_activas (token, usuario_id, direccion_ip, 
                                           inicio_sesion, ultima_actividad, activa)
              VALUES (?, ?, ?, NOW(), NOW(), true)

6. CONSTRUCCIÓN DE RESPONSE
   📁 src/main/java/com/veterinaria/presentation/dto/auth/LoginResponse.java
   └─ Response: {
         "token": "eyJhbGciOiJIUzI1NiJ9...",
         "email": "admin@veterinaria.com",
         "nombreCompleto": "Administrador Sistema",
         "roles": ["ADMIN"],
         "permisos": ["USUARIOS_CREAR", "USUARIOS_VER", ...]
      }

7. HTTP RESPONSE
   └─ ResponseEntity.ok(response)
      └─ HTTP 200 OK
```

**Archivos involucrados (orden de ejecución)**:
1. `presentation/controller/AuthController.java`
2. `presentation/dto/auth/LoginRequest.java`
3. `application/service/impl/ServicioAutenticacionImpl.java`
4. `application/repository/UsuarioRepository.java`
5. `domain/entity/security/Usuario.java`
6. `infrastructure/security/config/SecurityConfig.java`
7. `infrastructure/security/jwt/JwtTokenProvider.java`
8. `application/repository/SesionActivaRepository.java`
9. `domain/entity/security/SesionActiva.java`
10. `presentation/dto/auth/LoginResponse.java`

---

### 2.2 FLUJO: CREAR CLIENTE

**Request**: `POST http://localhost:8080/api/v1/clientes`  
**Headers**: `Authorization: Bearer <token>`

**Recorrido completo**:

```
1. PUNTO DE ENTRADA - Controller
   📁 src/main/java/com/veterinaria/presentation/controller/ClienteController.java:52
   ├─ Método: crearCliente(@RequestBody CreateClienteRequest request)
   └─ Recibe: {
         "nombre": "Juan",
         "apellido": "Pérez",
         "dni": "12345678",
         "email": "juan@email.com",
         "telefono": "3001234567",
         "direccion": "Calle 123"
      }

2. FILTRO DE SEGURIDAD JWT
   📁 src/main/java/com/veterinaria/infrastructure/security/filter/JwtAuthenticationFilter.java:41
   ├─ Método: doFilterInternal(request, response, filterChain)
   │
   ├─ 2.1 EXTRAER TOKEN
   │   └─ Header: "Authorization: Bearer eyJhbGci..."
   │       └─ Token extraído: "eyJhbGci..."
   │
   ├─ 2.2 VALIDAR TOKEN
   │   📁 src/main/java/com/veterinaria/infrastructure/security/jwt/JwtTokenProvider.java:93
   │   ├─ Método: validarToken(String token)
   │   ├─ Verifica firma
   │   ├─ Verifica expiración
   │   └─ Resultado: true
   │
   ├─ 2.3 VERIFICAR SESIÓN ACTIVA
   │   📁 src/main/java/com/veterinaria/application/repository/SesionActivaRepository.java:17
   │   └─ findByTokenAndActiva(token, true)
   │       └─ Query: SELECT * FROM sesiones_activas 
   │                 WHERE token = ? AND activa = true
   │
   ├─ 2.4 EXTRAER EMAIL DEL TOKEN
   │   📁 src/main/java/com/veterinaria/infrastructure/security/jwt/JwtTokenProvider.java:113
   │   └─ Método: obtenerEmailDelToken(token)
   │       └─ Claims.getSubject() → "admin@veterinaria.com"
   │
   ├─ 2.5 CARGAR USER DETAILS
   │   📁 src/main/java/com/veterinaria/infrastructure/security/CustomUserDetailsService.java:28
   │   ├─ Método: loadUserByUsername(email)
   │   ├─ Busca usuario en BD
   │   └─ Crea UserDetails con roles y permisos
   │
   └─ 2.6 ESTABLECER AUTHENTICATION
       📁 src/main/java/com/veterinaria/infrastructure/security/filter/JwtAuthenticationFilter.java:59
       └─ SecurityContextHolder.getContext().setAuthentication(authentication)

3. AUTORIZACIÓN - Security Config
   📁 src/main/java/com/veterinaria/infrastructure/security/config/SecurityConfig.java:87
   └─ .requestMatchers(HttpMethod.POST, "/api/clientes")
         .hasAuthority("CLIENTES_CREAR")
      ├─ Usuario tiene permiso: ✓ (admin tiene todos los permisos)
      └─ Continúa ejecución

4. VALIDACIÓN DE REQUEST
   📁 src/main/java/com/veterinaria/application/dto/cliente/CreateClienteRequest.java
   ├─ @NotBlank en nombre
   ├─ @Size(min=2, max=100) en nombre
   ├─ @Email en email
   ├─ @Pattern para teléfono
   └─ Bean Validation ejecuta validaciones

5. SERVICIO DE CLIENTE
   📁 src/main/java/com/veterinaria/application/service/impl/ClienteServiceImpl.java:39
   ├─ Método: crear(CreateClienteRequest request)
   │
   ├─ 5.1 VALIDAR DNI ÚNICO
   │   📁 src/main/java/com/veterinaria/application/repository/ClienteRepository.java:19
   │   └─ existsByDni(String dni)
   │       └─ Query: SELECT COUNT(*) FROM clientes WHERE dni = ?
   │       └─ Si existe → lanza DuplicateResourceException
   │
   ├─ 5.2 VALIDAR EMAIL ÚNICO
   │   📁 src/main/java/com/veterinaria/application/repository/ClienteRepository.java:20
   │   └─ existsByEmail(String email)
   │       └─ Query: SELECT COUNT(*) FROM clientes WHERE email = ?
   │
   ├─ 5.3 MAPEAR DTO A ENTIDAD
   │   📁 src/main/java/com/veterinaria/application/mapper/ClienteMapper.java:20
   │   └─ Método: toEntity(CreateClienteRequest dto)
   │       └─ MapStruct genera código de mapeo automático
   │           └─ Cliente entity = Cliente.builder()
   │                 .nombre(request.getNombre())
   │                 .apellido(request.getApellido())
   │                 ...
   │                 .build();
   │
   ├─ 5.4 ESTABLECER AUDITORÍA
   │   📁 src/main/java/com/veterinaria/domain/entity/BaseAuditableEntity.java
   │   └─ @CreatedDate → createdAt = now()
   │   └─ @CreatedBy → createdBy = "admin@veterinaria.com"
   │   └─ @LastModifiedDate → updatedAt = now()
   │   └─ isActive = true
   │
   ├─ 5.5 GUARDAR EN BD
   │   📁 src/main/java/com/veterinaria/application/repository/ClienteRepository.java
   │   └─ save(Cliente entity)
   │       └─ INSERT INTO clientes (
   │             nombre, apellido, dni, email, telefono, direccion,
   │             ciudad, departamento, codigo_postal, observaciones,
   │             created_at, created_by, updated_at, updated_by, is_active
   │          ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, true)
   │       └─ Hibernate genera ID auto-increment
   │
   └─ 5.6 MAPEAR ENTIDAD A DTO
       📁 src/main/java/com/veterinaria/application/mapper/ClienteMapper.java:17
       └─ Método: toDTO(Cliente entity)
           └─ ClienteDTO response = ClienteDTO.builder()
                 .id(entity.getId())
                 .nombre(entity.getNombre())
                 ...
                 .build();

6. CONSTRUCCIÓN DE RESPONSE
   📁 src/main/java/com/veterinaria/presentation/dto/cliente/ClienteDTO.java
   └─ Response: {
         "id": 1,
         "nombre": "Juan",
         "apellido": "Pérez",
         "nombreCompleto": "Juan Pérez",
         "dni": "12345678",
         "email": "juan@email.com",
         "telefono": "+57 3001234567",
         "direccion": "Calle 123",
         "createdAt": "2025-11-07T10:30:00",
         "cantidadPacientes": 0
      }

7. HTTP RESPONSE
   └─ ResponseEntity.status(HttpStatus.CREATED).body(clienteDTO)
      └─ HTTP 201 Created
         └─ Location: /api/v1/clientes/1
```

**Archivos involucrados (orden de ejecución)**:
1. `infrastructure/security/filter/JwtAuthenticationFilter.java`
2. `infrastructure/security/jwt/JwtTokenProvider.java`
3. `application/repository/SesionActivaRepository.java`
4. `infrastructure/security/CustomUserDetailsService.java`
5. `infrastructure/security/config/SecurityConfig.java`
6. `presentation/controller/ClienteController.java`
7. `application/dto/cliente/CreateClienteRequest.java`
8. `application/service/impl/ClienteServiceImpl.java`
9. `application/repository/ClienteRepository.java`
10. `application/mapper/ClienteMapper.java`
11. `domain/entity/patients/Cliente.java`
12. `domain/entity/BaseAuditableEntity.java`
13. `application/dto/cliente/ClienteDTO.java`

---

### 2.3 FLUJO: CREAR PACIENTE (con Factory Pattern)

**Request**: `POST http://localhost:8080/api/v1/pacientes`  
**Headers**: `Authorization: Bearer <token>`

**Recorrido completo**:

```
1. PUNTO DE ENTRADA
   📁 src/main/java/com/veterinaria/presentation/controller/PacienteController.java:58
   └─ Método: crearPaciente(@RequestBody CreatePacienteRequest request)

2. AUTENTICACIÓN Y AUTORIZACIÓN (igual que flujo anterior)
   📁 JwtAuthenticationFilter → SecurityConfig
   └─ Verifica permiso: PACIENTES_CREAR

3. VALIDACIÓN DE REQUEST
   📁 src/main/java/com/veterinaria/application/dto/paciente/CreatePacienteRequest.java
   ├─ @NotNull TipoEspecie especie
   ├─ @NotBlank String nombre
   ├─ @NotNull Long clienteId
   └─ Bean Validation OK

4. SERVICIO DE PACIENTE
   📁 src/main/java/com/veterinaria/application/service/impl/PacienteServiceImpl.java:63
   ├─ Método: crear(CreatePacienteRequest request)
   │
   ├─ 4.1 VALIDAR CLIENTE EXISTE
   │   📁 src/main/java/com/veterinaria/application/repository/ClienteRepository.java
   │   └─ findById(request.getClienteId())
   │       └─ Query: SELECT * FROM clientes WHERE id = ?
   │       └─ Si no existe → ResourceNotFoundException
   │
   ├─ 4.2 BUSCAR O CREAR RAZA
   │   📁 src/main/java/com/veterinaria/application/service/impl/RazaServiceImpl.java:111
   │   └─ buscarOCrearRaza(nombreRaza, especie)
   │       └─ Query: SELECT * FROM razas 
   │                 WHERE nombre = ? AND especie = ?
   │
   ├─ 4.3 FACTORY METHOD - CREAR PACIENTE SEGÚN ESPECIE
   │   📁 src/main/java/com/veterinaria/domain/factory/PacienteFactory.java:22
   │   ├─ Método: crearPaciente(TipoEspecie tipo)
   │   ├─ Switch sobre tipo:
   │   │   └─ case PERRO → return new Perro();
   │   │       📁 src/main/java/com/veterinaria/domain/entity/patients/Perro.java
   │   │       └─ Crea instancia con campos específicos de Perro
   │   │           (nivelEnergia, entrenamiento, socializacion)
   │   │
   │   │   └─ case GATO → return new Gato();
   │   │       📁 src/main/java/com/veterinaria/domain/entity/patients/Gato.java
   │   │       └─ Crea instancia con campos específicos de Gato
   │   │           (caracter, nivelIndependencia, viveConOtrosGatos)
   │   │
   │   └─ Retorna: Paciente (polimórfico)
   │
   ├─ 4.4 MAPEAR DATOS DEL REQUEST A LA ENTIDAD
   │   📁 src/main/java/com/veterinaria/application/mapper/PacienteMapper.java
   │   └─ mapRequestToEntity(request, paciente)
   │       └─ paciente.setNombre(request.getNombre())
   │       └─ paciente.setFechaNacimiento(request.getFechaNacimiento())
   │       └─ paciente.setSexo(request.getSexo())
   │       └─ paciente.setCliente(cliente)
   │       └─ paciente.setRaza(raza)
   │
   ├─ 4.5 ESTABLECER RELACIÓN BIDIRECCIONAL
   │   📁 src/main/java/com/veterinaria/domain/entity/patients/Cliente.java:103
   │   └─ cliente.agregarPaciente(paciente)
   │       └─ pacientes.add(paciente);
   │       └─ paciente.setCliente(this);
   │
   ├─ 4.6 GUARDAR PACIENTE
   │   📁 src/main/java/com/veterinaria/application/repository/PacienteRepository.java
   │   └─ save(Paciente)
   │       └─ INSERT INTO perros (  // Tabla específica según tipo
   │             nombre, fecha_nacimiento, sexo, microchip, color, peso,
   │             estado, observaciones, cliente_id, raza_id,
   │             nivel_energia, entrenamiento, socializacion,  // Campos específicos de Perro
   │             created_at, created_by, updated_at, is_active
   │          ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), true)
   │
   └─ 4.7 MAPEAR A DTO
       📁 src/main/java/com/veterinaria/application/mapper/PacienteMapper.java:25
       └─ toDTO(Paciente entity)
           └─ PacienteDTO con todos los campos

5. HTTP RESPONSE
   └─ HTTP 201 Created
      └─ Body: PacienteDTO
```

**Archivos involucrados (orden)**:
1. `presentation/controller/PacienteController.java`
2. `application/dto/paciente/CreatePacienteRequest.java`
3. `application/service/impl/PacienteServiceImpl.java`
4. `application/repository/ClienteRepository.java`
5. `application/service/impl/RazaServiceImpl.java`
6. **`domain/factory/PacienteFactory.java`** ⭐ Factory Method Pattern
7. **`domain/entity/patients/Perro.java`** ⭐ Template Method Pattern
8. `application/mapper/PacienteMapper.java`
9. `domain/entity/patients/Cliente.java`
10. `application/repository/PacienteRepository.java`
11. `domain/entity/BaseAuditableEntity.java`

---

### 2.4 FLUJO: CAMBIAR ESTADO DE PACIENTE (con Auditoría)

**Request**: `PATCH http://localhost:8080/api/v1/pacientes/1/cambiar-estado`

**Recorrido completo**:

```
1. CONTROLLER
   📁 src/main/java/com/veterinaria/presentation/controller/PacienteController.java:116
   └─ cambiarEstado(@PathVariable Long id, @RequestBody CambiarEstadoPacienteRequest)

2. SERVICIO
   📁 src/main/java/com/veterinaria/application/service/impl/PacienteServiceImpl.java:152
   ├─ Método: cambiarEstado(Long id, CambiarEstadoPacienteRequest request)
   │
   ├─ 2.1 BUSCAR PACIENTE
   │   📁 application/repository/PacienteRepository.java
   │   └─ findById(id)
   │
   ├─ 2.2 VALIDAR CAMBIO DE ESTADO
   │   └─ validarCambioEstado(estadoAnterior, nuevoEstado)
   │       └─ No permitir: FALLECIDO → ACTIVO
   │
   ├─ 2.3 ACTUALIZAR ESTADO
   │   📁 domain/entity/patients/Paciente.java
   │   └─ paciente.setEstado(nuevoEstado)
   │
   ├─ 2.4 CREAR REGISTRO DE AUDITORÍA
   │   📁 domain/entity/patients/HistorialEstadoPaciente.java
   │   └─ HistorialEstadoPaciente.builder()
   │         .paciente(paciente)
   │         .estadoAnterior(ACTIVO)
   │         .nuevoEstado(INACTIVO)
   │         .motivo("Mudanza del propietario")
   │         .fechaCambio(LocalDate.now())
   │         .build()
   │
   ├─ 2.5 GUARDAR HISTORIAL
   │   📁 application/repository/HistorialEstadoPacienteRepository.java
   │   └─ save(historial)
   │       └─ INSERT INTO historial_estado_pacientes
   │          (paciente_id, estado_anterior, nuevo_estado, 
   │           motivo, fecha_cambio, created_at, created_by)
   │
   └─ 2.6 GUARDAR PACIENTE ACTUALIZADO
       └─ pacienteRepository.save(paciente)

3. RESPONSE
   └─ HTTP 200 OK
      └─ PacienteDTO actualizado
```

---

## 3. ARQUITECTURA EN CAPAS - FLUJO VERTICAL

```
┌─────────────────────────────────────────────────────────────┐
│  PRESENTATION LAYER (Capa de Presentación)                  │
│  📁 src/main/java/com/veterinaria/presentation/             │
│                                                              │
│  ┌────────────────────┐         ┌─────────────────────┐    │
│  │   Controllers      │         │       DTOs          │    │
│  │  AuthController    │ ──────→ │  LoginRequest       │    │
│  │  ClienteController │         │  ClienteDTO         │    │
│  │  PacienteController│         │  PacienteDTO        │    │
│  └────────────────────┘         └─────────────────────┘    │
│         ↓ llama                                             │
└─────────────────────────────────────────────────────────────┘
         ↓
┌─────────────────────────────────────────────────────────────┐
│  APPLICATION LAYER (Capa de Aplicación)                     │
│  📁 src/main/java/com/veterinaria/application/              │
│                                                              │
│  ┌──────────────┐    ┌──────────────┐    ┌──────────────┐ │
│  │  Services    │    │  Repositories│    │   Mappers    │ │
│  │ ServiceImpl  │ →  │  JpaRepo     │    │  MapStruct   │ │
│  └──────────────┘    └──────────────┘    └──────────────┘ │
│         ↓ usa                ↓ accede            ↑ mapea   │
└─────────────────────────────────────────────────────────────┘
         ↓                     ↓                     ↓
┌─────────────────────────────────────────────────────────────┐
│  DOMAIN LAYER (Capa de Dominio)                             │
│  📁 src/main/java/com/veterinaria/domain/                   │
│                                                              │
│  ┌────────────┐  ┌──────────────┐  ┌──────────────────┐   │
│  │ Entities   │  │   Factory    │  │  Value Objects   │   │
│  │  Usuario   │  │PacienteFactory│  │   Email, Tel     │   │
│  │  Cliente   │  │              │  │   Direccion      │   │
│  │  Paciente  │  │  (Pattern)   │  │   (Pattern)      │   │
│  └────────────┘  └──────────────┘  └──────────────────┘   │
│         ↓ hereda                                            │
│  ┌────────────────────────────────────────────────┐        │
│  │  Template Method Pattern                       │        │
│  │  Perro, Gato, Ave, Reptil, Roedor             │        │
│  └────────────────────────────────────────────────┘        │
└─────────────────────────────────────────────────────────────┘
         ↓
┌─────────────────────────────────────────────────────────────┐
│  INFRASTRUCTURE LAYER (Capa de Infraestructura)             │
│  📁 src/main/java/com/veterinaria/infrastructure/           │
│                                                              │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐ │
│  │  Security    │  │     JWT      │  │   Config         │ │
│  │  Filters     │  │TokenProvider │  │  SecurityConfig  │ │
│  │  JwtAuthFilter│  │              │  │  DataLoader      │ │
│  └──────────────┘  └──────────────┘  └──────────────────┘ │
└─────────────────────────────────────────────────────────────┘
         ↓
┌─────────────────────────────────────────────────────────────┐
│  DATABASE LAYER (Base de Datos)                             │
│  📁 MySQL Database: veterinaria_db                          │
│                                                              │
│  usuarios, roles, permisos, sesiones_activas               │
│  clientes, razas, perros, gatos, aves, reptiles, roedores  │
│  historial_estado_pacientes                                 │
└─────────────────────────────────────────────────────────────┘
```

---

## 4. RESUMEN DE PATRONES POR MÓDULO

| Patrón | Ubicación | Propósito |
|--------|-----------|-----------|
| **Factory Method** | `domain/factory/PacienteFactory.java` | Crear instancias polimórficas de Paciente |
| **Template Method** | `domain/entity/patients/Paciente.java` | Definir comportamiento común y delegar detalles |
| **Repository** | `application/repository/*.java` | Abstraer acceso a datos |
| **Builder** | Todas las entidades (Lombok) | Construir objetos complejos |
| **Value Object** | `domain/valueobject/*.java` | Encapsular conceptos con validación |
| **DTO** | `application/dto/**/*.java` | Transferir datos entre capas |
| **Strategy** | Validaciones en subclases de Paciente | Algoritmos intercambiables |
| **Dependency Injection** | Todos los @Service, @Controller | Inversión de control |
| **Singleton** | Spring Beans | Única instancia de servicios |

---

## 5. CONVENCIONES DE NOMBRES

### Paquetes
- `domain.*` → Lógica de negocio pura
- `application.*` → Casos de uso y orquestación
- `infrastructure.*` → Detalles técnicos (BD, seguridad)
- `presentation.*` → API REST y DTOs de presentación

### Clases
- `*Controller` → Controladores REST
- `*Service` / `*ServiceImpl` → Servicios de aplicación
- `*Repository` → Repositorios de datos
- `*Mapper` → Conversores DTO ↔ Entity
- `*Request` → DTOs de entrada
- `*Response` / `*DTO` → DTOs de salida
- `*Config` → Clases de configuración

### Métodos
- `crear()` → Crear entidad
- `actualizar()` → Actualizar entidad
- `eliminar()` → Soft delete
- `obtenerPorId()` → Buscar por ID
- `listarTodos()` → Listar todos

---

**Fin del Informe**
