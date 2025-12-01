# Configuración de Variables de Entorno

Este documento explica cómo configurar y usar variables de entorno en el Sistema de Gestión Veterinaria.

## 📋 Tabla de Contenidos

- [Introducción](#introducción)
- [Configuración Inicial](#configuración-inicial)
- [Variables Disponibles](#variables-disponibles)
- [Uso por Perfil](#uso-por-perfil)
- [Despliegue en Producción](#despliegue-en-producción)
- [Troubleshooting](#troubleshooting)

## Introducción

El proyecto utiliza variables de entorno para configurar valores sensibles y específicos del entorno (desarrollo, testing, producción). Esto permite:

- ✅ Mantener credenciales fuera del código
- ✅ Configurar diferentes entornos sin cambiar código
- ✅ Mejorar la seguridad del proyecto
- ✅ Facilitar el despliegue

## Configuración Inicial

### 1. Crear archivo `.env`

Copia el archivo de ejemplo y completa con tus valores:

```bash
cp .env.example .env
```

### 2. Editar `.env`

Abre el archivo `.env` y completa los valores necesarios:

```bash
# Base de datos
DB_HOST=localhost
DB_PORT=3306
DB_NAME=veterinaria_db
DB_USERNAME=root
DB_PASSWORD=tu-password-segura

# JWT Secret (genera uno seguro)
JWT_SECRET=tu-secret-key-de-al-menos-64-caracteres

# Email
EMAIL_USERNAME=tu-email@gmail.com
EMAIL_PASSWORD=tu-contraseña-de-aplicacion
```

### 3. Cargar variables de entorno

#### Opción A: Usando un plugin de Maven (Recomendado)

Agrega el plugin `dotenv-maven-plugin` al `pom.xml`:

```xml
<plugin>
    <groupId>io.github.git-commit-id</groupId>
    <artifactId>dotenv-maven-plugin</artifactId>
    <version>1.0.0</version>
    <executions>
        <execution>
            <phase>initialize</phase>
            <goals>
                <goal>dotenv</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

#### Opción B: Cargar manualmente antes de ejecutar

**Linux/Mac:**
```bash
export $(cat .env | xargs)
mvn spring-boot:run
```

**Windows (PowerShell):**
```powershell
Get-Content .env | ForEach-Object {
    if ($_ -match '^([^#][^=]+)=(.*)$') {
        [Environment]::SetEnvironmentVariable($matches[1], $matches[2], 'Process')
    }
}
mvn spring-boot:run
```

#### Opción C: Usando IntelliJ IDEA

1. Ve a `Run` → `Edit Configurations`
2. Selecciona tu configuración de Spring Boot
3. En `Environment variables`, haz clic en el icono de carpeta
4. Haz clic en `+` y agrega variables desde el archivo `.env`

## Variables Disponibles

### Configuración General

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `SPRING_PROFILES_ACTIVE` | Perfil activo (dev, test, prod) | `dev` |
| `SERVER_PORT` | Puerto del servidor | `8080` |
| `APP_NAME` | Nombre de la aplicación | `Sistema Gestion Veterinaria` |

### Base de Datos

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `DB_HOST` | Host de la base de datos | `localhost` |
| `DB_PORT` | Puerto de la base de datos | `3306` |
| `DB_NAME` | Nombre de la base de datos | `veterinaria_db` |
| `DB_USERNAME` | Usuario de la base de datos | `root` |
| `DB_PASSWORD` | Contraseña de la base de datos | `123456` |
| `DB_URL` | URL completa (opcional, se construye automáticamente) | - |
| `DB_MAX_POOL_SIZE` | Tamaño máximo del pool de conexiones | `10` |
| `DB_MIN_IDLE` | Conexiones mínimas inactivas | `5` |
| `DB_CONNECTION_TIMEOUT` | Timeout de conexión (ms) | `20000` |
| `DB_IDLE_TIMEOUT` | Timeout de inactividad (ms) | `300000` |
| `DB_MAX_LIFETIME` | Tiempo máximo de vida de conexión (ms) | `1200000` |

### Seguridad JWT

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `JWT_SECRET` | Secret key para JWT (mínimo 64 caracteres) | - |
| `JWT_EXPIRATION` | Tiempo de expiración del token (ms) | `86400000` (24h) |
| `JWT_REFRESH_EXPIRATION` | Tiempo de expiración del refresh token (ms) | `604800000` (7d) |
| `SECURITY_LOGIN_MAX_INTENTOS` | Intentos máximos de login | `5` |
| `SECURITY_LOGIN_BLOQUEO_DURACION_MINUTOS` | Duración del bloqueo (minutos) | `15` |

### Email

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `EMAIL_HOST` | Servidor SMTP | `smtp.gmail.com` |
| `EMAIL_PORT` | Puerto SMTP | `587` |
| `EMAIL_USERNAME` | Usuario del email | - |
| `EMAIL_PASSWORD` | Contraseña del email | - |
| `EMAIL_FROM` | Email remitente (si no se especifica, usa `EMAIL_USERNAME`) | `${EMAIL_USERNAME}` |

### Frontend / URLs

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `FRONTEND_URL` | URL del frontend (para links en emails de confirmación) | `http://localhost:3000` |

### Información de Contacto

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `CONTACTO_TELEFONO` | Teléfono de contacto de la clínica | - |
| `CONTACTO_DIRECCION` | Dirección de la clínica | - |
| `CONTACTO_EMAIL` | Email de contacto (si no se especifica, usa `EMAIL_USERNAME`) | `${EMAIL_USERNAME}` |

### CORS

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `CORS_ALLOWED_ORIGINS` | Orígenes permitidos (separados por coma) | `http://localhost:3000,http://localhost:4200` |

### Logging

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `LOG_LEVEL_ROOT` | Nivel de log raíz | `INFO` |
| `LOG_LEVEL_APP` | Nivel de log de la aplicación | `DEBUG` |
| `LOG_FILE_NAME` | Nombre del archivo de log | `logs/veterinaria.log` |
| `LOG_FILE_MAX_SIZE` | Tamaño máximo del archivo | `10MB` |
| `LOG_FILE_MAX_HISTORY` | Días de historial | `30` |

### JPA/Hibernate

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `JPA_DDL_AUTO` | Modo DDL (update, validate, none) | `update` |
| `JPA_SHOW_SQL` | Mostrar SQL en consola | `true` |
| `JPA_FORMAT_SQL` | Formatear SQL | `true` |
| `JPA_BATCH_SIZE` | Tamaño del batch | `20` |

### Configuración Personalizada

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `APP_UPLOAD_DIR` | Directorio de uploads | `uploads` |
| `APP_MAX_FILE_SIZE` | Tamaño máximo de archivo | `5MB` |
| `APP_NOMBRE` | Nombre de la clínica | `Clínica Veterinaria VetCare` |

## Uso por Perfil

### Desarrollo (`dev`)

El perfil `dev` usa valores por defecto y permite desarrollo local sin configuración adicional.

```bash
SPRING_PROFILES_ACTIVE=dev
```

### Testing (`test`)

El perfil `test` usa H2 en memoria y no requiere configuración de base de datos.

```bash
SPRING_PROFILES_ACTIVE=test
```

### Producción (`prod`)

El perfil `prod` **requiere** que todas las variables sensibles estén configuradas:

```bash
SPRING_PROFILES_ACTIVE=prod
DB_HOST=tu-servidor-db
DB_USERNAME=usuario-prod
DB_PASSWORD=password-segura
JWT_SECRET=secret-super-seguro-de-al-menos-64-caracteres
EMAIL_USERNAME=email@produccion.com
EMAIL_PASSWORD=password-email
```

## Despliegue en Producción

### 1. Generar JWT Secret seguro

```bash
# Linux/Mac
openssl rand -hex 32

# O usando Python
python3 -c "import secrets; print(secrets.token_hex(32))"
```

### 2. Configurar variables en el servidor

#### Opción A: Archivo `.env` en el servidor

```bash
# En el servidor
nano .env
# Pega tus variables de entorno
```

#### Opción B: Variables de entorno del sistema

```bash
# Linux
export DB_PASSWORD=tu-password
export JWT_SECRET=tu-secret
# etc.
```

#### Opción C: Docker

```yaml
# docker-compose.yml
services:
  backend:
    environment:
      - DB_HOST=db
      - DB_PASSWORD=${DB_PASSWORD}
      - JWT_SECRET=${JWT_SECRET}
    env_file:
      - .env
```

#### Opción D: Servicios en la nube

- **Heroku**: `heroku config:set KEY=value`
- **AWS**: Usa Systems Manager Parameter Store o Secrets Manager
- **Azure**: Azure Key Vault
- **Google Cloud**: Secret Manager

### 3. Verificar configuración

```bash
# Verificar que las variables estén cargadas
echo $JWT_SECRET
echo $DB_PASSWORD
```

## Troubleshooting

### Las variables no se cargan

**Problema**: Spring Boot no lee las variables de entorno.

**Solución**:
1. Verifica que el archivo `.env` esté en la raíz del proyecto
2. Asegúrate de que las variables estén exportadas antes de ejecutar
3. Verifica que no haya espacios alrededor del `=` en `.env`

### Error de conexión a base de datos

**Problema**: No puede conectarse a la base de datos.

**Solución**:
1. Verifica que `DB_HOST`, `DB_PORT`, `DB_NAME` sean correctos
2. Verifica que `DB_USERNAME` y `DB_PASSWORD` sean correctos
3. Asegúrate de que MySQL esté corriendo
4. Verifica permisos del usuario de la base de datos

### JWT Secret inválido

**Problema**: Error al generar tokens JWT.

**Solución**:
1. Asegúrate de que `JWT_SECRET` tenga al menos 64 caracteres
2. No uses caracteres especiales problemáticos
3. Genera un nuevo secret seguro

### Email no funciona

**Problema**: No se envían emails.

**Solución**:
1. Para Gmail, usa una "Contraseña de aplicación" en lugar de tu contraseña normal
2. Ve a: https://myaccount.google.com/apppasswords
3. Verifica que `EMAIL_HOST` y `EMAIL_PORT` sean correctos
4. Revisa los logs para ver errores específicos

## Ejemplos de Configuración

### Desarrollo Local

```env
SPRING_PROFILES_ACTIVE=dev
DB_PASSWORD=123456
JWT_SECRET=4f8a6d2e9b1c3f7a5d8e2b9c4f6a1d3e7b5c8f2a6d9e1c4b7f3a5d8e2b9c6f1a
EMAIL_USERNAME=dev@example.com
EMAIL_PASSWORD=dev-password
```

### Producción

```env
SPRING_PROFILES_ACTIVE=prod
DB_HOST=prod-db.example.com
DB_NAME=veterinaria_prod
DB_USERNAME=veterinaria_user
DB_PASSWORD=super-secure-password-here
JWT_SECRET=generated-secure-secret-minimum-64-characters-long
EMAIL_USERNAME=noreply@veterinaria.com
EMAIL_PASSWORD=secure-email-password
CORS_ALLOWED_ORIGINS=https://veterinaria.com,https://www.veterinaria.com
LOG_LEVEL_ROOT=WARN
LOG_LEVEL_APP=INFO
```

## Seguridad

⚠️ **IMPORTANTE**:

1. **NUNCA** commitees el archivo `.env` al repositorio
2. **SIEMPRE** usa secrets seguros en producción
3. **ROTA** los secrets periódicamente
4. **LIMITA** el acceso al archivo `.env` (permisos 600)
5. **USA** servicios de gestión de secrets en producción (AWS Secrets Manager, Azure Key Vault, etc.)

## Referencias

- [Spring Boot Externalized Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config)
- [12-Factor App: Config](https://12factor.net/config)
- [OWASP: Secrets Management](https://owasp.org/www-community/vulnerabilities/Use_of_hard-coded_cryptographic_key)

