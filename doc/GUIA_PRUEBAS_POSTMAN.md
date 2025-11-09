# 🧪 Guía de Pruebas con Postman - Sistema Veterinaria

## 📋 Módulos Implementados

### ✅ MÓDULO 1: AUTENTICACIÓN Y SEGURIDAD (100% Completo)
**Entidades:** Usuario, Rol, Permiso, SesionActiva, IntentosLogin, DispositivoConfiable, RegistroAuditoria

**Funcionalidades:**
- Login con JWT (duración 24 horas)
- Logout
- Cambio de contraseña
- Validación de token
- Información del usuario autenticado
- Control de intentos fallidos (5 máximo, bloqueo 15 min)
- Gestión de sesiones activas (máximo 5 por usuario)
- Auditoría completa de accesos
- 3 roles: ADMIN, VETERINARIO, RECEPCIONISTA
- 38 permisos granulares

**Estado:** ✅ 100% Funcional y Probado

---

### ✅ MÓDULO 2: GESTIÓN DE CLIENTES (100% Completo)
**Entidades:** Cliente

**Funcionalidades:**
- CRUD completo de clientes
- Búsqueda por nombre, DNI, email
- Relación con pacientes (mascotas)
- Validaciones de datos

**Estado:** ✅ 100% Funcional

---

### ✅ MÓDULO 3: GESTIÓN DE PACIENTES (95% Completo - Mejorado Hoy)
**Entidades:** Paciente (abstracto), Perro, Gato, Raza, HistorialEstadoPaciente

**Funcionalidades Implementadas:**
- ✅ CRUD completo de pacientes
- ✅ Factory Method Pattern para crear Perro/Gato
- ✅ Template Method Pattern para cuidados específicos
- ✅ **NUEVO: Gestión completa de Razas (HU-09)**
  - CRUD de razas
  - Catálogo predefinido de 60+ razas
  - Razas por especie (PERRO, GATO, AVE, REPTIL, ROEDOR)
  - Mestizos y razas personalizadas
  - Estadísticas por especie
- ✅ **NUEVO: Historial de cambios de estado (HU-23)**
  - Cambio de estado con motivo y fecha
  - Trazabilidad completa
  - No permite resucitar pacientes fallecidos
- ✅ Búsquedas avanzadas (nombre, especie, estado, cliente, microchip)
- ✅ Control de estados (ACTIVO, INACTIVO, FALLECIDO)

**Funcionalidades Pendientes:**
- ❌ Exportación a PDF (HU-24)
- ❌ Paginación de listados
- ❌ Asociación con historial clínico (depende de módulo 4)

**Estado:** ✅ 95% Funcional - Listo para pruebas

---

### ✅ MÓDULO 4: GESTIÓN DE USUARIOS (100% Completo)
**Entidades:** Usuario (con roles y permisos)

**Funcionalidades:**
- CRUD completo de usuarios
- Asignación de roles
- Control de permisos
- Gestión de cuenta (activar/desactivar)

**Estado:** ✅ 100% Funcional

---

### ❌ MÓDULO 5: GESTIÓN DE CITAS (0% - Pendiente)
**Estado:** 🔴 No Implementado

---

### ❌ MÓDULO 6: HISTORIAL CLÍNICO (0% - Pendiente)
**Estado:** 🔴 No Implementado

---

### ❌ MÓDULO 7: INVENTARIO (0% - Pendiente)
**Estado:** 🔴 No Implementado

---

### ❌ MÓDULO 8: FACTURACIÓN (0% - Pendiente)
**Estado:** 🔴 No Implementado

---

## 🚀 Endpoints Disponibles para Probar

### 🔐 1. AUTENTICACIÓN (`/api/auth`)

#### 1.1 Login
```http
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "email": "admin@veterinaria.com",
  "password": "Admin123!",
  "huellaBrowser": "PostmanTest-Chrome-123"
}
```

**Respuesta esperada:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tipo": "Bearer",
  "email": "admin@veterinaria.com",
  "nombre": "Administrador Sistema",
  "roles": ["ROLE_ADMIN"],
  "permisos": ["USUARIOS_CREAR", "USUARIOS_VER", ...]
}
```

#### 1.2 Validar Token
```http
POST http://localhost:8080/api/auth/validar
Authorization: Bearer {TOKEN}
```

#### 1.3 Información del Usuario Autenticado
```http
GET http://localhost:8080/api/auth/me
Authorization: Bearer {TOKEN}
```

#### 1.4 Logout
```http
POST http://localhost:8080/api/auth/logout
Authorization: Bearer {TOKEN}
Content-Type: application/json

{
  "token": "{TOKEN}"
}
```

#### 1.5 Cambiar Contraseña
```http
POST http://localhost:8080/api/auth/cambiar-password
Authorization: Bearer {TOKEN}
Content-Type: application/json

{
  "passwordActual": "Admin123!",
  "passwordNuevo": "NuevoPass123!"
}
```

---

### 👥 2. GESTIÓN DE CLIENTES (`/api/v1/clientes`)

#### 2.1 Crear Cliente
```http
POST http://localhost:8080/api/v1/clientes
Authorization: Bearer {TOKEN}
Content-Type: application/json

{
  "nombre": "Juan",
  "apellido": "Pérez",
  "dni": "12345678",
  "email": "juan.perez@email.com",
  "telefono": "3001234567",
  "direccion": "Calle 123 #45-67",
  "ciudad": "Bogotá",
  "departamento": "Cundinamarca",
  "codigoPostal": "110111",
  "observaciones": "Cliente preferencial"
}
```

#### 2.2 Listar Todos los Clientes
```http
GET http://localhost:8080/api/v1/clientes
Authorization: Bearer {TOKEN}
```

#### 2.3 Obtener Cliente por ID
```http
GET http://localhost:8080/api/v1/clientes/{id}
Authorization: Bearer {TOKEN}
```

#### 2.4 Buscar Cliente por DNI
```http
GET http://localhost:8080/api/v1/clientes/buscar/dni/{dni}
Authorization: Bearer {TOKEN}
```

#### 2.5 Buscar Cliente por Email
```http
GET http://localhost:8080/api/v1/clientes/buscar/email/{email}
Authorization: Bearer {TOKEN}
```

#### 2.6 Actualizar Cliente
```http
PUT http://localhost:8080/api/v1/clientes/{id}
Authorization: Bearer {TOKEN}
Content-Type: application/json

{
  "telefono": "3009876543",
  "direccion": "Nueva dirección",
  "observaciones": "Actualizado"
}
```

#### 2.7 Eliminar Cliente
```http
DELETE http://localhost:8080/api/v1/clientes/{id}
Authorization: Bearer {TOKEN}
```

---

### 🐾 3. GESTIÓN DE RAZAS (`/api/v1/razas`) - **NUEVO**

#### 3.1 Listar Todas las Razas
```http
GET http://localhost:8080/api/v1/razas
Authorization: Bearer {TOKEN}
```

#### 3.2 Listar Razas Activas
```http
GET http://localhost:8080/api/v1/razas/activas
Authorization: Bearer {TOKEN}
```

#### 3.3 Obtener Razas por Especie
```http
GET http://localhost:8080/api/v1/razas/especie/PERRO
Authorization: Bearer {TOKEN}

# Opciones: PERRO, GATO, AVE, REPTIL, ROEDOR, OTRO
```

#### 3.4 Obtener Razas Activas por Especie
```http
GET http://localhost:8080/api/v1/razas/especie/GATO/activas
Authorization: Bearer {TOKEN}
```

#### 3.5 Obtener Razas Específicas (sin mestizos)
```http
GET http://localhost:8080/api/v1/razas/especie/PERRO/especificas
Authorization: Bearer {TOKEN}
```

#### 3.6 Buscar Razas por Nombre
```http
GET http://localhost:8080/api/v1/razas/buscar?nombre=Labrador
Authorization: Bearer {TOKEN}
```

#### 3.7 Obtener Razas Predefinidas
```http
GET http://localhost:8080/api/v1/razas/predefinidas
Authorization: Bearer {TOKEN}
```

#### 3.8 Obtener Estadísticas de Razas
```http
GET http://localhost:8080/api/v1/razas/estadisticas
Authorization: Bearer {TOKEN}
```

#### 3.9 Crear Raza Personalizada
```http
POST http://localhost:8080/api/v1/razas
Authorization: Bearer {TOKEN}
Content-Type: application/json

{
  "nombre": "Pitbull",
  "especie": "PERRO",
  "descripcion": "Raza fuerte y leal",
  "esMestizo": false,
  "tamanioTipico": "Grande",
  "pesoPromedioKg": 28.0
}
```

#### 3.10 Actualizar Raza
```http
PUT http://localhost:8080/api/v1/razas/{id}
Authorization: Bearer {TOKEN}
Content-Type: application/json

{
  "descripcion": "Descripción actualizada",
  "pesoPromedioKg": 30.0
}
```

#### 3.11 Desactivar Raza
```http
PATCH http://localhost:8080/api/v1/razas/{id}/desactivar
Authorization: Bearer {TOKEN}
```

#### 3.12 Activar Raza
```http
PATCH http://localhost:8080/api/v1/razas/{id}/activar
Authorization: Bearer {TOKEN}
```

---

### 🐶 4. GESTIÓN DE PACIENTES (`/api/v1/pacientes`)

#### 4.1 Crear Paciente (Perro)
```http
POST http://localhost:8080/api/v1/pacientes
Authorization: Bearer {TOKEN}
Content-Type: application/json

{
  "nombre": "Max",
  "especie": "PERRO",
  "raza": "Labrador Retriever",
  "fechaNacimiento": "2020-05-15",
  "sexo": "MACHO",
  "color": "Dorado",
  "pesoKg": 32.5,
  "microchip": "123456789012345",
  "observaciones": "Muy amigable",
  "fotoUrl": "https://ejemplo.com/foto-max.jpg",
  "clienteId": 1
}
```

#### 4.2 Crear Paciente (Gato)
```http
POST http://localhost:8080/api/v1/pacientes
Authorization: Bearer {TOKEN}
Content-Type: application/json

{
  "nombre": "Luna",
  "especie": "GATO",
  "raza": "Siamés",
  "fechaNacimiento": "2021-03-20",
  "sexo": "HEMBRA",
  "color": "Crema con puntos oscuros",
  "pesoKg": 4.2,
  "clienteId": 1
}
```

#### 4.3 Listar Todos los Pacientes
```http
GET http://localhost:8080/api/v1/pacientes
Authorization: Bearer {TOKEN}
```

#### 4.4 Obtener Paciente por ID
```http
GET http://localhost:8080/api/v1/pacientes/{id}
Authorization: Bearer {TOKEN}
```

#### 4.5 Buscar Pacientes por Nombre
```http
GET http://localhost:8080/api/v1/pacientes/buscar?nombre=Max
Authorization: Bearer {TOKEN}
```

#### 4.6 Buscar Pacientes por Especie
```http
GET http://localhost:8080/api/v1/pacientes/especie/PERRO
Authorization: Bearer {TOKEN}
```

#### 4.7 Buscar Pacientes por Estado
```http
GET http://localhost:8080/api/v1/pacientes/estado/ACTIVO
Authorization: Bearer {TOKEN}

# Estados: ACTIVO, INACTIVO, FALLECIDO
```

#### 4.8 Buscar Pacientes de un Cliente
```http
GET http://localhost:8080/api/v1/pacientes/cliente/{clienteId}
Authorization: Bearer {TOKEN}
```

#### 4.9 Buscar Pacientes Activos de un Cliente
```http
GET http://localhost:8080/api/v1/pacientes/cliente/{clienteId}/activos
Authorization: Bearer {TOKEN}
```

#### 4.10 Buscar Paciente por Microchip
```http
GET http://localhost:8080/api/v1/pacientes/microchip/{microchip}
Authorization: Bearer {TOKEN}
```

#### 4.11 Actualizar Paciente
```http
PUT http://localhost:8080/api/v1/pacientes/{id}
Authorization: Bearer {TOKEN}
Content-Type: application/json

{
  "pesoKg": 35.0,
  "observaciones": "Peso actualizado en control",
  "color": "Dorado oscuro"
}
```

#### 4.12 **NUEVO: Cambiar Estado con Motivo** (HU-23)
```http
PATCH http://localhost:8080/api/v1/pacientes/{id}/cambiar-estado
Authorization: Bearer {TOKEN}
Content-Type: application/json

{
  "nuevoEstado": "FALLECIDO",
  "motivo": "Falleció debido a complicaciones de salud avanzadas",
  "fechaCambio": "2025-01-06"
}
```

**Ejemplos de cambios de estado:**

Marcar como inactivo:
```json
{
  "nuevoEstado": "INACTIVO",
  "motivo": "Cliente se mudó de ciudad",
  "fechaCambio": "2025-01-06"
}
```

Reactivar paciente:
```json
{
  "nuevoEstado": "ACTIVO",
  "motivo": "Cliente regresó y solicita reactivación",
  "fechaCambio": "2025-01-06"
}
```

#### 4.13 Filtrar por Especie y Estado
```http
GET http://localhost:8080/api/v1/pacientes/filtrar?especie=PERRO&estado=ACTIVO
Authorization: Bearer {TOKEN}
```

#### 4.14 Contar Pacientes por Estado
```http
GET http://localhost:8080/api/v1/pacientes/contar/estado/ACTIVO
Authorization: Bearer {TOKEN}
```

#### 4.15 Eliminar Paciente
```http
DELETE http://localhost:8080/api/v1/pacientes/{id}
Authorization: Bearer {TOKEN}
```

---

### 👤 5. GESTIÓN DE USUARIOS (`/api/usuarios`)

#### 5.1 Crear Usuario
```http
POST http://localhost:8080/api/usuarios
Authorization: Bearer {TOKEN}
Content-Type: application/json

{
  "username": "veterinario1",
  "email": "vet1@veterinaria.com",
  "password": "Vet123!",
  "nombre": "Carlos",
  "apellido": "Rodríguez",
  "tipoUsuario": "VETERINARIO",
  "roles": ["ROLE_VETERINARIO"]
}
```

#### 5.2 Listar Todos los Usuarios
```http
GET http://localhost:8080/api/usuarios
Authorization: Bearer {TOKEN}
```

#### 5.3 Obtener Usuario por ID
```http
GET http://localhost:8080/api/usuarios/{id}
Authorization: Bearer {TOKEN}
```

#### 5.4 Actualizar Usuario
```http
PUT http://localhost:8080/api/usuarios/{id}
Authorization: Bearer {TOKEN}
Content-Type: application/json

{
  "nombre": "Carlos Alberto",
  "telefono": "3001234567"
}
```

#### 5.5 Eliminar Usuario
```http
DELETE http://localhost:8080/api/usuarios/{id}
Authorization: Bearer {TOKEN}
```

---

## 🔄 Flujo de Prueba Recomendado

### Fase 1: Autenticación ✅
1. Login con usuario admin
2. Validar token recibido
3. Obtener información del usuario autenticado

### Fase 2: Crear Datos Base ✅
1. Crear 2-3 clientes
2. Ver catálogo de razas predefinidas
3. Filtrar razas por especie

### Fase 3: Gestión de Pacientes ✅
1. Crear paciente perro con raza del catálogo
2. Crear paciente gato con raza del catálogo
3. Buscar pacientes por nombre
4. Buscar pacientes de un cliente específico
5. **NUEVO:** Cambiar estado de paciente a INACTIVO con motivo
6. **NUEVO:** Cambiar estado de paciente a FALLECIDO con motivo
7. Listar pacientes por estado ACTIVO
8. Ver estadísticas de razas

### Fase 4: Gestión de Razas ✅
1. Ver catálogo completo de razas
2. Crear raza personalizada
3. Buscar razas por nombre
4. Desactivar una raza
5. Ver estadísticas por especie

### Fase 5: Gestión de Usuarios ✅
1. Crear nuevo usuario veterinario
2. Crear nuevo usuario recepcionista
3. Listar usuarios

---

## 📊 Datos Precargados al Iniciar

### Usuarios:
- **Admin:** admin@veterinaria.com / Admin123!
  - Rol: ROLE_ADMIN
  - Permisos: Todos (38)

### Razas Predefinidas: 60+
- **Perros:** 15 razas + Mestizo
- **Gatos:** 15 razas + Mestizo/Criollo
- **Aves:** 7 razas + Otra
- **Reptiles:** 5 especies + Otro
- **Roedores:** 6 especies + Otro
- **Otro:** Categoría general

### Roles:
- ROLE_ADMIN (38 permisos)
- ROLE_VETERINARIO (10 permisos)
- ROLE_RECEPCIONISTA (10 permisos)

### Permisos: 38 en total
- 5 de Citas
- 4 de Historial Clínico
- 4 de Facturación
- 4 de Inventario
- 4 de Usuarios
- 4 de Pacientes
- 4 de Clientes
- 4 de Reportes
- 2 de Configuración

---

## 🎯 Escenarios de Prueba Importantes

### Escenario 1: Registro Completo de Mascota
1. Crear cliente nuevo
2. Ver razas disponibles para PERRO
3. Crear paciente perro con raza específica
4. Verificar que el paciente aparece en la lista del cliente

### Escenario 2: Control de Estado con Trazabilidad (HU-23)
1. Crear paciente
2. Cambiar estado a INACTIVO con motivo "Cliente viajó"
3. Intentar cambiar nuevamente a INACTIVO (debe fallar)
4. Reactivar con motivo "Cliente regresó"
5. Cambiar a FALLECIDO con motivo
6. Intentar reactivar (debe fallar - no se puede resucitar)

### Escenario 3: Gestión de Razas
1. Ver catálogo de razas de GATO
2. Crear raza personalizada "Angora Turco"
3. Buscar razas que contengan "Angora"
4. Crear paciente con la nueva raza
5. Desactivar la raza
6. Ver que ya no aparece en razas activas pero sí en el histórico

### Escenario 4: Búsquedas Avanzadas
1. Crear varios pacientes de diferentes especies
2. Buscar todos los PERRO activos
3. Buscar todos los GATO
4. Buscar por microchip específico
5. Contar pacientes por estado

### Escenario 5: Seguridad y Permisos
1. Login como ADMIN
2. Crear usuario VETERINARIO
3. Login con VETERINARIO
4. Intentar crear usuario (debe fallar - no tiene permiso)
5. Crear paciente (debe funcionar - tiene permiso)

---

## ⚠️ Notas Importantes

1. **Todos los endpoints requieren autenticación** excepto `/api/auth/login` y `/api/auth/recuperar-password`

2. **El token JWT expira en 24 horas** - guarda el token y reutilízalo

3. **Los IDs son autogenerados** - usa los IDs que te devuelve el sistema

4. **Validaciones activas:**
   - Email único en clientes y usuarios
   - DNI único en clientes
   - Microchip único en pacientes (opcional)
   - Nombre + Especie único en razas
   - No se puede cambiar estado de paciente fallecido
   - Fecha de nacimiento no puede ser futura

5. **Las razas predefinidas se cargan automáticamente** al iniciar por primera vez

6. **Cuenta de bloqueo:** 5 intentos fallidos = 15 minutos bloqueado

7. **Máximo 5 sesiones activas** por usuario

---

## 📝 Variables de Entorno Recomendadas en Postman

```javascript
{
  "base_url": "http://localhost:8080",
  "token": "", // Se actualiza después del login
  "clienteId": "", // ID del cliente creado
  "pacienteId": "", // ID del paciente creado
  "razaId": "" // ID de raza creada
}
```

---

## 🚀 Próximos Módulos a Implementar

1. **Citas** (ÉPICA 3) - 13 Story Points
2. **Historial Clínico** (ÉPICA 4) - 13 Story Points
3. **Inventario** (ÉPICA 5) - 8 Story Points
4. **Facturación** (ÉPICA 5) - 13 Story Points

---

**Fecha de actualización:** 2025-01-06
**Versión:** 1.2.0
**Módulos completados:** 4 de 8 (50%)
