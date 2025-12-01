# 🚀 Guía Rápida: Variables de Entorno

## Configuración Inicial

### 1. Crear archivo `.env`

```bash
cp env.example .env
```

### 2. Editar `.env` con tus valores

```bash
nano .env
# o
vim .env
```

### 3. Cargar variables (Linux/Mac)

```bash
source load-env.sh
# o manualmente:
export $(cat .env | grep -v '^#' | xargs)
```

### 4. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

## Variables Mínimas Requeridas

Para desarrollo local, estas son las variables más importantes:

```env
DB_PASSWORD=tu-password-mysql
JWT_SECRET=tu-secret-key-de-al-menos-64-caracteres
EMAIL_USERNAME=tu-email@gmail.com
EMAIL_PASSWORD=tu-contraseña-de-aplicacion
```

## Generar JWT Secret

```bash
# Linux/Mac
openssl rand -hex 32

# O con Python
python3 -c "import secrets; print(secrets.token_hex(32))"
```

## Ver Documentación Completa

Consulta `doc/CONFIGURACION_VARIABLES_ENTORNO.md` para más detalles.

