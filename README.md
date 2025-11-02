# Sistema de Gestión Veterinaria

Sistema completo de gestión para clínica veterinaria con 12 patrones de diseño implementados.

## Tecnologías

- **Framework**: Spring Boot 3.5
- **Lenguaje**: Java 21
- **Base de datos**: MySQL
- **ORM**: Spring Data JPA / Hibernate
- **Seguridad**: Spring Security con JWT
- **Documentación API**: OpenAPI/Swagger
- **Build**: Maven
- **Testing**: JUnit 5
- **Mapping**: MapStruct
- **Logging**: SLF4J con Logback

## Arquitectura

El proyecto sigue una **arquitectura por capas** bien definida:

```
com.veterinaria/
├── config/                    # Configuraciones (Security, JPA, Auditing)
├── domain/                    # Capa de dominio
│   ├── entity/               # Entidades JPA
│   │   ├── security/        # Usuario, Rol, Sesión, AuditoriaAcceso
│   │   ├── users/           # Propietarios, Veterinarios, etc.
│   │   ├── patients/        # Pacientes (mascotas)
│   │   ├── appointments/    # Citas
│   │   ├── clinicalhistory/ # Historia clínica
│   │   ├── inventory/       # Inventario
│   │   ├── billing/         # Facturación
│   │   ├── tracking/        # Seguimiento
│   │   └── notifications/   # Notificaciones
│   └── enums/               # Enumeraciones del dominio
├── application/              # Capa de aplicación
│   ├── repository/          # Repositorios JPA
│   ├── service/             # Servicios de negocio
│   ├── dto/                 # Data Transfer Objects
│   ├── mapper/              # Mappers (MapStruct)
│   └── facade/              # Fachadas para operaciones complejas
├── infrastructure/          # Capa de infraestructura
│   ├── security/            # JWT, filtros, UserDetailsService
│   ├── exception/           # Excepciones personalizadas
│   ├── constants/           # Constantes de la aplicación
│   └── util/                # Utilidades
└── presentation/            # Capa de presentación
    └── controller/          # Controllers REST
```

## Módulos Implementados

### ✅ Módulo de Seguridad (Completo)

#### Entidades
- **Usuario**: Gestión completa de usuarios con control de intentos fallidos y bloqueo de cuenta
- **Rol**: Sistema de roles con permisos jerárquicos
- **Sesión**: Control de sesiones activas y gestión de tokens
- **AuditoriaAcceso**: Registro completo de accesos y detección de actividad sospechosa

#### Características
- Autenticación JWT con refresh tokens
- Control de sesiones múltiples
- Bloqueo automático por intentos fallidos
- Auditoría completa de accesos
- Detección de actividad sospechosa
- Contraseñas encriptadas con BCrypt
- Soft delete en entidades

#### Repositorios
- `UsuarioRepository`: 15+ métodos de consulta optimizados
- `RolRepository`: Búsqueda por permisos y nivel jerárquico
- `SesionRepository`: Gestión de sesiones y limpieza automática
- `AuditoriaAccesoRepository`: Análisis de patrones de acceso

## Patrones de Diseño Implementados

1. ✅ **Singleton** - JwtTokenProvider, configuraciones centralizadas
2. ✅ **Builder** - Construcción flexible de entidades (Usuario, Rol, Sesión, AuditoriaAcceso)
3. ✅ **State** - Control de estados con transiciones validadas (EstadoCita, EstadoTratamiento, EstadoSesion)
4. ✅ **Adapter** - CustomUserDetailsService adapta Usuario a UserDetails de Spring Security
5. ✅ **Template Method** - BaseAuditableEntity con hooks @PrePersist y @PreUpdate
6. ⏳ **Factory Method** - Creación de notificaciones (pendiente)
7. ⏳ **Composite** - Estructura de tratamientos compuestos (pendiente)
8. ⏳ **Strategy** - Estrategias de descuentos y fidelización (pendiente)
9. ⏳ **Decorator** - Extensión dinámica de servicios (pendiente)
10. ⏳ **Observer** - Sistema de notificaciones (pendiente)
11. ⏳ **Facade** - Simplificación de operaciones complejas (pendiente)
12. ⏳ **Proxy** - Control de acceso y caché (pendiente)

## Mejores Prácticas Implementadas

### ✅ Seguridad
- Contraseñas nunca en texto plano (BCrypt)
- JWT con expiración configurable
- CORS configurado
- CSRF deshabilitado (arquitectura stateless)
- Endpoints públicos y protegidos bien definidos
- Auditoría completa de accesos

### ✅ Clean Code
- Sin God Objects
- Sin Magic Numbers (constantes descriptivas)
- Sin Hard Coding (archivos de configuración)
- Separación clara de responsabilidades
- Nombres descriptivos
- Principios SOLID

### ✅ Validaciones
- Bean Validation en todas las entidades
- DTOs para transferencia de datos (evita exponer entidades)
- Excepciones personalizadas con @ControllerAdvice

### ✅ Performance
- Índices en campos de búsqueda frecuente
- Lazy loading apropiado
- Paginación configurada (20 items por defecto, máximo 100)
- Connection pooling con HikariCP
- Caché para consultas frecuentes

### ✅ Mantenibilidad
- Código bien documentado con JavaDoc
- Logging estructurado (app, error, audit)
- Configuración por perfiles (dev, test, prod)
- Testing preparado (JUnit 5)

## Configuración

### Base de Datos

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/veterinaria_db
spring.datasource.username=root
spring.datasource.password=root
```

### JWT

```properties
jwt.secret=<tu-secret-key>
jwt.expiration=86400000          # 24 horas
jwt.refresh-expiration=604800000 # 7 días
```

### CORS

```properties
app.cors.allowed-origins=http://localhost:3000,http://localhost:4200
```

## Instalación

1. Clonar el repositorio:
```bash
git clone git@github.com:carlosaugustocue/proyecto-nuclear-veterinaria.git
cd proyecto-nuclear-veterinaria
```

2. Configurar la base de datos MySQL:
```sql
CREATE DATABASE veterinaria_db;
```

3. Configurar variables de entorno (opcional):
```bash
export EMAIL_USERNAME=tu-email@gmail.com
export EMAIL_PASSWORD=tu-password
```

4. Compilar y ejecutar:
```bash
mvn clean install
mvn spring-boot:run
```

5. Acceder a la documentación de la API:
- Swagger UI: http://localhost:8080/api/v1/swagger-ui.html
- API Docs: http://localhost:8080/api/v1/api-docs

## Testing

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar tests con cobertura
mvn clean test jacoco:report
```

## Estructura de Commits

El proyecto sigue [Conventional Commits](https://www.conventionalcommits.org/):

- `feat:` - Nueva funcionalidad
- `fix:` - Corrección de bugs
- `docs:` - Cambios en documentación
- `refactor:` - Refactorización de código
- `test:` - Agregar o modificar tests
- `chore:` - Cambios en build o herramientas

## Roadmap

- [x] Configuración base del proyecto
- [x] Módulo de seguridad completo
- [ ] Módulo de usuarios y propietarios
- [ ] Módulo de pacientes (mascotas)
- [ ] Módulo de citas y agenda
- [ ] Módulo de historia clínica
- [ ] Módulo de inventario
- [ ] Módulo de facturación
- [ ] Sistema de notificaciones
- [ ] Dashboard y reportes
- [ ] Tests unitarios y de integración
- [ ] Documentación completa

## Autor

Carlos Augusto Cué

## Licencia

Este proyecto es privado y está destinado exclusivamente para fines educativos.
