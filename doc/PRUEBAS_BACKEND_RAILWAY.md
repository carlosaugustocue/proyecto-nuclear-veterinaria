# 🧪 Guía para Probar el Backend en Railway

Esta guía te muestra cómo probar el backend desplegado en Railway.

## 🔗 URL del Backend

```
https://proyecto-nuclear-veterinaria-production.up.railway.app
```

---

## 🚀 Método 1: Usar curl (Terminal)

### 1. Verificar que el backend esté funcionando

```bash
# Ping (endpoint público, no requiere autenticación)
curl https://proyecto-nuclear-veterinaria-production.up.railway.app/api/auth/ping
```

**Respuesta esperada:**
```json
{
  "message": "pong",
  "timestamp": "2024-12-04T01:00:00Z"
}
```

### 2. Hacer Login

```bash
curl -X POST https://proyecto-nuclear-veterinaria-production.up.railway.app/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@veterinaria.com",
    "password": "admin123"
  }'
```

**Respuesta esperada:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "userId": 1,
  "email": "admin@veterinaria.com",
  "username": "admin",
  "nombre": "Administrador",
  "roles": ["ROLE_ADMIN"]
}
```

**Guarda el token** de la respuesta para usarlo en los siguientes requests.

### 3. Probar un endpoint protegido (con token)

```bash
# Reemplaza YOUR_TOKEN con el token que obtuviste del login
curl https://proyecto-nuclear-veterinaria-production.up.railway.app/api/auth/me \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 4. Listar clientes

```bash
curl https://proyecto-nuclear-veterinaria-production.up.railway.app/api/v1/clientes \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 5. Crear un cliente

```bash
curl -X POST https://proyecto-nuclear-veterinaria-production.up.railway.app/api/v1/clientes \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan",
    "apellido": "Pérez",
    "dni": "12345678",
    "email": "juan@example.com",
    "telefono": "+573001234567",
    "direccion": "Calle 123",
    "ciudad": "Bogotá"
  }'
```

---

## 🌐 Método 2: Usar el Navegador

Algunos endpoints GET se pueden probar directamente en el navegador (si no requieren autenticación o si usas una extensión para agregar headers).

### Endpoints públicos (sin autenticación):

1. **Ping:**
   ```
   https://proyecto-nuclear-veterinaria-production.up.railway.app/api/auth/ping
   ```

2. **Health Check (si está habilitado):**
   ```
   https://proyecto-nuclear-veterinaria-production.up.railway.app/actuator/health
   ```

### Para endpoints protegidos:

Usa una extensión del navegador como:
- **ModHeader** (Chrome/Edge)
- **Requestly** (Chrome/Firefox)

Configura el header:
```
Authorization: Bearer YOUR_TOKEN
```

---

## 📬 Método 3: Usar Postman (Recomendado)

### Paso 1: Importar la colección

1. Abre Postman
2. Click en **Import**
3. Importa estos archivos del proyecto:
   - `postman/Veterinaria_API.postman_collection.json`
   - `postman/Veterinaria_Environment.postman_environment.json`

### Paso 2: Configurar el Environment para Railway

1. En Postman, click en el icono de **ojo** (👁️) arriba a la derecha
2. Selecciona **Veterinaria - Local** (o crea uno nuevo)
3. Edita las variables:
   - `baseUrl`: `https://proyecto-nuclear-veterinaria-production.up.railway.app/api`
   - `authToken`: (se llenará automáticamente después del login)

### Paso 3: Probar el backend

1. Ve a la carpeta **01 - Authentication**
2. Ejecuta el request **Login** con:
   ```json
   {
     "email": "admin@veterinaria.com",
     "password": "admin123"
   }
   ```
3. El token se guardará automáticamente
4. Ahora puedes probar cualquier otro endpoint

---

## 🔍 Método 4: Usar HTTPie (Terminal mejorada)

Si tienes HTTPie instalado:

```bash
# Instalar HTTPie (si no lo tienes)
# macOS: brew install httpie
# Linux: sudo apt install httpie
# Windows: pip install httpie

# Login
http POST https://proyecto-nuclear-veterinaria-production.up.railway.app/api/auth/login \
  email=admin@veterinaria.com \
  password=admin123

# Usar el token (guarda el token de la respuesta anterior)
http GET https://proyecto-nuclear-veterinaria-production.up.railway.app/api/auth/me \
  Authorization:"Bearer YOUR_TOKEN"
```

---

## 📋 Endpoints Principales para Probar

### 🔐 Autenticación (Públicos)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/auth/ping` | Verificar que el backend esté funcionando |
| POST | `/api/auth/login` | Iniciar sesión |

### 👤 Usuario Autenticado (Requieren Token)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/auth/me` | Obtener información del usuario actual |
| GET | `/api/auth/validar` | Validar token |

### 👥 Clientes (Requieren Token)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/clientes` | Listar todos los clientes |
| POST | `/api/v1/clientes` | Crear un cliente |
| GET | `/api/v1/clientes/{id}` | Obtener un cliente por ID |

### 🐾 Pacientes (Requieren Token)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/pacientes` | Listar todos los pacientes |
| POST | `/api/v1/pacientes` | Crear un paciente |
| GET | `/api/v1/pacientes/{id}` | Obtener un paciente por ID |

### 📅 Citas (Requieren Token)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/citas` | Listar todas las citas |
| POST | `/api/v1/citas` | Crear una cita |
| GET | `/api/v1/citas/{id}` | Obtener una cita por ID |

---

## 🧪 Script de Prueba Rápida (Bash)

Crea un archivo `test-backend.sh`:

```bash
#!/bin/bash

BASE_URL="https://proyecto-nuclear-veterinaria-production.up.railway.app/api"

echo "🔍 1. Probando Ping..."
curl -s "$BASE_URL/auth/ping" | jq '.'

echo -e "\n🔐 2. Haciendo Login..."
LOGIN_RESPONSE=$(curl -s -X POST "$BASE_URL/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@veterinaria.com",
    "password": "admin123"
  }')

echo "$LOGIN_RESPONSE" | jq '.'

# Extraer el token
TOKEN=$(echo "$LOGIN_RESPONSE" | jq -r '.token')

if [ "$TOKEN" != "null" ] && [ -n "$TOKEN" ]; then
  echo -e "\n✅ Token obtenido: ${TOKEN:0:50}..."
  
  echo -e "\n👤 3. Obteniendo información del usuario..."
  curl -s "$BASE_URL/auth/me" \
    -H "Authorization: Bearer $TOKEN" | jq '.'
  
  echo -e "\n👥 4. Listando clientes..."
  curl -s "$BASE_URL/v1/clientes" \
    -H "Authorization: Bearer $TOKEN" | jq '.'
  
  echo -e "\n🐾 5. Listando pacientes..."
  curl -s "$BASE_URL/v1/pacientes" \
    -H "Authorization: Bearer $TOKEN" | jq '.'
else
  echo -e "\n❌ Error: No se pudo obtener el token"
fi
```

**Ejecutar:**
```bash
chmod +x test-backend.sh
./test-backend.sh
```

---

## 🐛 Troubleshooting

### Error: "Connection refused" o timeout

**Problema:** El backend no está accesible.

**Solución:**
1. Verifica que el servicio esté activo en Railway
2. Verifica que la URL sea correcta
3. Verifica que no haya problemas de red/firewall

### Error: "401 Unauthorized"

**Problema:** Token inválido o expirado.

**Solución:**
1. Vuelve a hacer login para obtener un nuevo token
2. Verifica que estés enviando el header correctamente: `Authorization: Bearer TOKEN`

### Error: "403 Forbidden"

**Problema:** El usuario no tiene permisos para ese endpoint.

**Solución:**
1. Usa una cuenta con permisos de administrador
2. Verifica que el usuario tenga los roles necesarios

### Error: "404 Not Found"

**Problema:** El endpoint no existe o la URL es incorrecta.

**Solución:**
1. Verifica que la URL sea correcta
2. Verifica que el endpoint exista en la documentación
3. Asegúrate de incluir `/api` en la ruta

### Error: "500 Internal Server Error"

**Problema:** Error en el servidor.

**Solución:**
1. Revisa los logs en Railway
2. Verifica que la base de datos esté conectada
3. Verifica que todas las variables de entorno estén configuradas

---

## 📚 Recursos Adicionales

- **Documentación completa de endpoints**: Ver `postman/API_ENDPOINTS_GUIDE.md`
- **Colección de Postman**: `postman/Veterinaria_API.postman_collection.json`
- **Guía de Postman**: `postman/README.md`

---

## ✅ Checklist de Pruebas

- [ ] Ping funciona (`/api/auth/ping`)
- [ ] Login funciona (`/api/auth/login`)
- [ ] Obtener usuario actual funciona (`/api/auth/me`)
- [ ] Listar clientes funciona (`/api/v1/clientes`)
- [ ] Crear cliente funciona (`POST /api/v1/clientes`)
- [ ] Listar pacientes funciona (`/api/v1/pacientes`)
- [ ] Crear paciente funciona (`POST /api/v1/pacientes`)
- [ ] Listar citas funciona (`/api/v1/citas`)

---

## 🎉 ¡Listo!

Ya puedes probar el backend. Empieza con el **ping** para verificar que esté funcionando, luego haz **login** y prueba los demás endpoints.

