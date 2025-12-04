# Configuración de Railway para el Sistema Veterinaria

Esta guía explica cómo configurar las variables de entorno en Railway para conectar el backend con MySQL.

## Problema Común

Railway no siempre inyecta automáticamente las variables de MySQL en el servicio de backend. Necesitas configurarlas manualmente.

## Solución: Configurar Variables de Entorno en Railway

### Paso 1: Obtener las Variables del Servicio MySQL

1. En Railway, ve al servicio **MySQL**
2. Ve a la pestaña **Variables**
3. Busca las siguientes variables (pueden estar ocultas, haz clic en el ícono del ojo para verlas):
   - `MYSQL_HOST` o `MYSQLHOST`
   - `MYSQL_PORT` o `MYSQLPORT` (generalmente `3306`)
   - `MYSQL_DATABASE` o `MYSQLDATABASE`
   - `MYSQL_USER` o `MYSQLUSER` (generalmente `root`)
   - `MYSQL_PASSWORD` o `MYSQLPASSWORD` o `MYSQL_ROOT_PASSWORD`

### Paso 2: Agregar Variables al Servicio Backend

1. En Railway, ve al servicio **proyecto-nuclear-veterinaria**
2. Ve a la pestaña **Variables**
3. Haz clic en **"New Variable"** o **"Add Variable"**
4. Agrega las siguientes variables:

#### Opción A: Usando Referencias de Railway (Recomendado)

Si Railway permite referencias entre servicios, puedes usar:

```
MYSQLHOST=${{MySQL.MYSQLHOST}}
MYSQLPORT=${{MySQL.MYSQLPORT}}
MYSQLDATABASE=${{MySQL.MYSQLDATABASE}}
MYSQLUSER=${{MySQL.MYSQLUSER}}
MYSQLPASSWORD=${{MySQL.MYSQLPASSWORD}}
```

#### Opción B: Valores Directos

Si las referencias no funcionan, copia los valores directamente:

```
MYSQLHOST=<valor-del-servicio-mysql>
MYSQLPORT=3306
MYSQLDATABASE=railway
MYSQLUSER=root
MYSQLPASSWORD=<password-del-servicio-mysql>
```

### Paso 3: Configurar Variables Adicionales

Agrega también estas variables necesarias para la aplicación:

```
SPRING_PROFILES_ACTIVE=prod
JWT_SECRET=<genera-un-secret-seguro-de-al-menos-64-caracteres>
```

Para generar un JWT_SECRET seguro:
```bash
openssl rand -hex 32
```

### Paso 4: Variables Opcionales (si las necesitas)

```
EMAIL_HOST=smtp.gmail.com
EMAIL_PORT=587
EMAIL_USERNAME=tu-email@gmail.com
EMAIL_PASSWORD=tu-contraseña-de-aplicación
EMAIL_FROM=noreply@veterinaria.com
FRONTEND_URL=https://tu-frontend.railway.app
```

## Verificación

Después de agregar las variables:

1. **Redespliega el servicio** (Railway debería hacerlo automáticamente)
2. Ve a la pestaña **Logs** del servicio backend
3. Verifica que no aparezcan errores de conexión a la base de datos
4. Busca en los logs: `HikariPool-1 - Starting...` seguido de `HikariPool-1 - Start completed`

## Troubleshooting

### Error: "Connection refused"

- Verifica que las variables `MYSQLHOST`, `MYSQLPORT`, etc. estén configuradas
- Asegúrate de que el servicio MySQL esté corriendo
- Verifica que ambos servicios estén en el mismo proyecto de Railway

### Error: "Access denied"

- Verifica que `MYSQLUSER` y `MYSQLPASSWORD` sean correctos
- Asegúrate de usar las credenciales del servicio MySQL

### Las variables no aparecen

- Railway a veces tarda unos minutos en propagar las variables
- Intenta redesplegar el servicio manualmente
- Verifica que estés en el entorno correcto (production/staging)

## Estructura Final de Variables

Tu servicio backend debería tener estas variables configuradas:

```
# Base de datos (desde servicio MySQL)
MYSQLHOST=<host-del-mysql>
MYSQLPORT=3306
MYSQLDATABASE=railway
MYSQLUSER=root
MYSQLPASSWORD=<password>

# Spring Boot
SPRING_PROFILES_ACTIVE=prod

# JWT (obligatorio)
JWT_SECRET=<tu-secret-key>

# Email (opcional)
EMAIL_HOST=smtp.gmail.com
EMAIL_PORT=587
EMAIL_USERNAME=...
EMAIL_PASSWORD=...
```

## Notas Importantes

1. **Nunca commitees** las contraseñas o secrets al repositorio
2. Railway encripta las variables automáticamente
3. Las variables son específicas por servicio y entorno
4. Si cambias las variables, Railway redesplegará automáticamente

