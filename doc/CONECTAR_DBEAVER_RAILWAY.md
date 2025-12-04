# 🔌 Conectar DBeaver a MySQL en Railway

Esta guía explica cómo conectarte a la base de datos MySQL de producción en Railway usando DBeaver.

## ⚠️ Importante: Limitaciones de Railway

**Railway usa hostnames internos** (como `mysql.railway.internal`) que **NO son accesibles desde fuera de Railway**. 

Para conectarte desde DBeaver, tienes **3 opciones**:

1. ✅ **Usar Railway CLI con túnel SSH** (Recomendado)
2. ✅ **Usar MYSQL_PUBLIC_URL** (si está disponible)
3. ✅ **Crear un servicio proxy** (más complejo)

---

## 🚀 Opción 1: Usar Railway CLI con Túnel SSH (Recomendado)

Esta es la forma más segura y recomendada.

### Paso 1: Instalar Railway CLI

#### En Linux/macOS:
```bash
curl -fsSL https://railway.app/install.sh | sh
```

#### En Windows:
Descarga desde: https://railway.app/cli

### Paso 2: Iniciar sesión en Railway

```bash
railway login
```

### Paso 3: Conectarte a tu proyecto

```bash
# Listar proyectos
railway projects

# Conectarte a tu proyecto
railway link
# O especificar el proyecto directamente
railway link --project <project-id>
```

### Paso 4: Crear un túnel SSH a MySQL

```bash
# Obtener el nombre de tu servicio MySQL
railway service

# Crear túnel (reemplaza 'MySQL' con el nombre de tu servicio)
railway connect MySQL
```

Esto creará un túnel SSH y mostrará algo como:
```
Tunneling MySQL on port 3306
Local port: 3306
```

### Paso 5: Configurar DBeaver

1. Abre DBeaver
2. Click derecho en "Databases" → "New Database Connection"
3. Selecciona **MySQL**
4. Configura:
   - **Host**: `localhost` (o `127.0.0.1`)
   - **Port**: `3306` (el puerto local del túnel)
   - **Database**: El nombre de tu base de datos (ej: `railway`)
   - **Username**: `root` (o el usuario de MySQL)
   - **Password**: La contraseña de MySQL

5. Click en "Test Connection"
6. Si funciona, click en "Finish"

**Nota:** Mantén el túnel SSH abierto mientras uses DBeaver.

---

## 🌐 Opción 2: Usar MYSQL_PUBLIC_URL (Si está disponible)

Algunos servicios MySQL en Railway pueden tener una URL pública.

### Paso 1: Verificar si existe MYSQL_PUBLIC_URL

1. En Railway, ve a tu servicio **MySQL**
2. Ve a la pestaña **Variables**
3. Busca `MYSQL_PUBLIC_URL`

Si existe, copia el valor. Debería verse algo como:
```
mysql://root:password@public.railway.app:3306/railway
```

### Paso 2: Extraer información de la URL

La URL tiene el formato:
```
mysql://usuario:contraseña@host:puerto/base_de_datos
```

Ejemplo:
- **Host**: `public.railway.app`
- **Port**: `3306`
- **Database**: `railway`
- **Username**: `root`
- **Password**: (la contraseña de la URL)

### Paso 3: Configurar DBeaver

1. Abre DBeaver
2. Click derecho en "Databases" → "New Database Connection"
3. Selecciona **MySQL**
4. Configura:
   - **Host**: El hostname de `MYSQL_PUBLIC_URL`
   - **Port**: El puerto de `MYSQL_PUBLIC_URL` (generalmente `3306`)
   - **Database**: El nombre de la base de datos
   - **Username**: El usuario de la URL
   - **Password**: La contraseña de la URL

5. Click en "Test Connection"
6. Si funciona, click en "Finish"

**⚠️ Nota:** Si `MYSQL_PUBLIC_URL` no existe, esta opción no funcionará.

---

## 🔧 Opción 3: Obtener Credenciales Manualmente

Si ninguna de las opciones anteriores funciona, puedes obtener las credenciales manualmente.

### Paso 1: Obtener Credenciales en Railway

1. En Railway, ve a tu servicio **MySQL**
2. Ve a la pestaña **Variables**
3. Busca y copia estos valores (haz click en el ícono del ojo para verlos):
   - `MYSQLHOST` o `MYSQL_HOST`
   - `MYSQLPORT` o `MYSQL_PORT` (generalmente `3306`)
   - `MYSQLDATABASE` o `MYSQL_DATABASE`
   - `MYSQLUSER` o `MYSQL_USER` (generalmente `root`)
   - `MYSQLPASSWORD` o `MYSQL_PASSWORD` o `MYSQL_ROOT_PASSWORD`

### Paso 2: Verificar el Hostname

**Importante:** El hostname puede ser:
- `mysql.railway.internal` (solo accesible desde dentro de Railway)
- `shinkansen.proxy.rlwy.net` (puede ser accesible públicamente)
- Otro hostname específico de Railway

### Paso 3: Intentar Conexión Directa

1. Abre DBeaver
2. Click derecho en "Databases" → "New Database Connection"
3. Selecciona **MySQL**
4. Configura con los valores obtenidos:
   - **Host**: El hostname de Railway
   - **Port**: El puerto (generalmente `3306`)
   - **Database**: El nombre de la base de datos
   - **Username**: El usuario
   - **Password**: La contraseña

5. Click en "Test Connection"

**Si falla con "Connection refused" o timeout:**
- El hostname es interno y no es accesible desde fuera
- Usa la **Opción 1** (Railway CLI con túnel SSH)

---

## 📋 Configuración Detallada en DBeaver

### Configuración Básica

1. **Driver**: MySQL 8+
2. **Host**: `localhost` (si usas túnel) o el hostname público
3. **Port**: `3306`
4. **Database**: `railway` (o el nombre de tu base de datos)
5. **Username**: `root`
6. **Password**: (tu contraseña)

### Configuración Avanzada (Driver Properties)

En la pestaña "Driver properties", puedes agregar:

```
useSSL=false
allowPublicKeyRetrieval=true
serverTimezone=UTC
```

### Configuración de Conexión (Connection Settings)

- **Connection timeout**: `30` segundos
- **Keep-alive interval**: `30` segundos

---

## 🐛 Troubleshooting

### Error: "Connection refused"

**Problema:** El hostname es interno y no es accesible desde fuera.

**Solución:**
- Usa la **Opción 1** (Railway CLI con túnel SSH)
- O verifica si existe `MYSQL_PUBLIC_URL`

### Error: "Access denied"

**Problema:** Credenciales incorrectas.

**Solución:**
1. Verifica que estés usando las credenciales correctas de Railway
2. Asegúrate de copiar la contraseña completa (puede tener caracteres especiales)
3. Verifica que el usuario tenga permisos de acceso remoto

### Error: "Unknown database"

**Problema:** El nombre de la base de datos es incorrecto.

**Solución:**
1. Verifica el nombre de la base de datos en Railway
2. Generalmente es `railway` o el valor de `MYSQLDATABASE`

### Error: "Public Key Retrieval is not allowed"

**Problema:** MySQL requiere configuración adicional.

**Solución:**
En DBeaver, en "Driver properties", agrega:
```
allowPublicKeyRetrieval=true
```

### El túnel SSH se desconecta

**Problema:** El túnel se cierra después de un tiempo.

**Solución:**
- Mantén la terminal abierta mientras uses DBeaver
- O usa un gestor de túneles como `autossh` para mantenerlo activo

---

## 🔐 Seguridad

### Buenas Prácticas

1. ✅ **Usa túnel SSH** en lugar de conexión directa cuando sea posible
2. ✅ **No compartas** las credenciales de producción
3. ✅ **Usa conexiones SSL** si están disponibles
4. ✅ **Cierra** las conexiones cuando no las uses
5. ✅ **No commitees** credenciales al repositorio

### Alternativas Seguras

- Usa **Railway CLI** para crear túneles seguros
- Considera usar un **servicio proxy** si necesitas acceso permanente
- Usa **variables de entorno** para las credenciales

---

## 📚 Recursos Adicionales

- [Railway CLI Documentation](https://docs.railway.app/develop/cli)
- [DBeaver Documentation](https://dbeaver.com/docs/)
- [MySQL Connection Guide](https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-configuration-properties.html)

---

## ✅ Checklist

- [ ] Railway CLI instalado
- [ ] Iniciado sesión en Railway CLI
- [ ] Proyecto vinculado en Railway CLI
- [ ] Túnel SSH creado (si usas Opción 1)
- [ ] Credenciales obtenidas de Railway
- [ ] DBeaver configurado con las credenciales
- [ ] Conexión de prueba exitosa
- [ ] Propiedades del driver configuradas (SSL, etc.)

---

## 🎯 Resumen Rápido

**Método más fácil:**
1. Instala Railway CLI
2. Ejecuta: `railway connect MySQL`
3. En DBeaver, conecta a `localhost:3306`
4. Usa las credenciales de Railway

**Si no funciona:**
- Verifica si existe `MYSQL_PUBLIC_URL` en Railway
- O usa las credenciales manualmente con el hostname público

