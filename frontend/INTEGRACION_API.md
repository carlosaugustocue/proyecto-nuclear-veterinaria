# 🔗 Guía de Integración con el Backend API

Este documento explica cómo conectar las vistas del frontend con los endpoints reales del backend Spring Boot.

---

## Paso 1: Verificar que el Backend esté Corriendo

```bash
# En otra terminal, asegúrate de que Spring Boot esté corriendo
cd /home/newton/IdeaProjects/proyecto-nuclear-veterinaria
mvn spring-boot:run

# O si usas IDE, ejecuta la clase main
com.veterinaria.VeterinariaApplication

# Verifica que esté escuchando en http://localhost:8080
```

---

## Paso 2: Verificar Endpoints en Swagger

Una vez que Spring Boot esté corriendo, accede a:

```
http://localhost:8080/swagger-ui.html
```

Aquí puedes ver todos los endpoints disponibles y probarlos.

---

## Paso 3: Conectar Cada Módulo

### 🔐 MÓDULO DE AUTENTICACIÓN

**Archivo:** `src/stores/authStore.js`

**Endpoint:** `POST /api/auth/login`

**Request:**
```json
{
  "email": "usuario@example.com",
  "password": "contraseña123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "usuario": {
    "id": 1,
    "email": "usuario@example.com",
    "nombre": "Juan Pérez",
    "rol": "VETERINARIO"
  }
}
```

**Código Actual (Dummy):**
```javascript
const login = async (email, password) => {
  try {
    const response = await post('/auth/login', { email, password })
    const { token: newToken, usuario } = response.data
    token.value = newToken
    user.value = usuario
    localStorage.setItem('token', newToken)
    return { success: true }
  } catch (error) {
    return { success: false, error: error.response?.data?.message }
  }
}
```

✅ **Este código ya está correctamente implementado y conectado al API real.**

---

### 📅 MÓDULO DE CITAS

**Archivo:** `src/views/CitasView.vue`

#### Obtener todas las citas

```javascript
// Reemplaza en fetchCitas()
const fetchCitas = async () => {
  loading.value = true
  try {
    // ANTES (dummy)
    // citas.value = [{ id: 1, paciente: 'Max', ... }]

    // DESPUÉS (API real)
    const response = await get('/v1/citas', {
      params: {
        searchText: filters.searchText,
        estado: filters.estado,
        fecha: filters.fecha
      }
    })
    citas.value = response.data
  } catch (error) {
    console.error('Error al cargar citas:', error)
  } finally {
    loading.value = false
  }
}
```

#### Crear una cita

**Archivo:** `src/views/citas/CitaFormView.vue`

```javascript
const saveCita = async () => {
  loading.value = true
  try {
    if (isEditing.value) {
      await put(`/v1/citas/${route.params.id}`, form)
    } else {
      await post('/v1/citas', form)
    }
    router.push('/citas')
  } catch (error) {
    console.error('Error:', error)
  } finally {
    loading.value = false
  }
}
```

#### Obtener detalles de una cita

**Archivo:** `src/views/citas/CitaDetailView.vue`

```javascript
const getCita = async () => {
  try {
    const response = await get(`/v1/citas/${route.params.id}`)
    cita.value = response.data
  } catch (error) {
    console.error('Error:', error)
  }
}
```

#### Eliminar una cita

```javascript
const deleteCita = async () => {
  if (confirm('¿Estás seguro?')) {
    try {
      await deleteRequest(`/v1/citas/${route.params.id}`)
      router.push('/citas')
    } catch (error) {
      console.error('Error:', error)
    }
  }
}
```

---

### 🐾 MÓDULO DE PACIENTES

**Archivo:** `src/views/PacientesView.vue`

#### Obtener todos los pacientes

```javascript
const fetchPacientes = async () => {
  loading.value = true
  try {
    const response = await get('/v1/pacientes', {
      params: {
        searchText: filters.searchText,
        especie: filters.especie,
        estado: filters.estado
      }
    })
    pacientes.value = response.data
  } catch (error) {
    console.error('Error:', error)
  } finally {
    loading.value = false
  }
}
```

#### Crear un paciente

**Archivo:** `src/views/pacientes/PacienteFormView.vue`

```javascript
const savePaciente = async () => {
  loading.value = true
  try {
    if (isEditing.value) {
      await put(`/v1/pacientes/${route.params.id}`, form)
    } else {
      await post('/v1/pacientes', form)
    }
    router.push('/pacientes')
  } catch (error) {
    console.error('Error:', error)
  } finally {
    loading.value = false
  }
}
```

---

### 👥 MÓDULO DE CLIENTES

**Archivo:** `src/views/ClientesView.vue`

#### Obtener todos los clientes

```javascript
const fetchClientes = async () => {
  loading.value = true
  try {
    const response = await get('/v1/clientes', {
      params: {
        searchText: filters.searchText,
        ciudad: filters.ciudad
      }
    })
    clientes.value = response.data
  } catch (error) {
    console.error('Error:', error)
  } finally {
    loading.value = false
  }
}
```

---

### 💰 MÓDULO DE FACTURAS

**Archivo:** `src/views/FacturasView.vue`

#### Obtener todas las facturas

```javascript
const fetchFacturas = async () => {
  loading.value = true
  try {
    const response = await get('/v1/facturas', {
      params: {
        searchText: filters.searchText,
        estado: filters.estado,
        fechaInicio: filters.fechaInicio,
        fechaFin: filters.fechaFin
      }
    })
    facturas.value = response.data
  } catch (error) {
    console.error('Error:', error)
  } finally {
    loading.value = false
  }
}
```

#### Crear una factura

**Archivo:** `src/views/facturas/FacturaFormView.vue`

```javascript
const saveFactura = async () => {
  loading.value = true
  try {
    if (isEditing.value) {
      await put(`/v1/facturas/${route.params.id}`, form)
    } else {
      // El backend calcula el número automático
      await post('/v1/facturas', form)
    }
    router.push('/facturas')
  } catch (error) {
    console.error('Error:', error)
  } finally {
    loading.value = false
  }
}
```

---

## Paso 4: Cargar Datos de Referencia

Algunos campos dependen de datos del backend (clientes, veterinarios, etc).

### Crear un Composable para Datos de Referencia

**Archivo:** `src/composables/useReferenceData.js`

```javascript
import { ref } from 'vue'
import { useApi } from './useApi'

const { get } = useApi()

export function useReferenceData() {
  const clientes = ref([])
  const pacientes = ref([])
  const veterinarios = ref([])
  const tiposServicio = ref([])
  const razas = ref([])

  const loadClientes = async () => {
    try {
      const response = await get('/v1/clientes')
      clientes.value = response.data
    } catch (error) {
      console.error('Error cargando clientes:', error)
    }
  }

  const loadPacientes = async () => {
    try {
      const response = await get('/v1/pacientes')
      pacientes.value = response.data
    } catch (error) {
      console.error('Error cargando pacientes:', error)
    }
  }

  const loadVeterinarios = async () => {
    try {
      // Ajusta el endpoint según tu backend
      const response = await get('/v1/usuarios?rol=VETERINARIO')
      veterinarios.value = response.data
    } catch (error) {
      console.error('Error cargando veterinarios:', error)
    }
  }

  const loadTiposServicio = async () => {
    try {
      const response = await get('/v1/tipos-servicio')
      tiposServicio.value = response.data
    } catch (error) {
      console.error('Error cargando tipos servicio:', error)
    }
  }

  const loadRazas = async () => {
    try {
      const response = await get('/v1/razas')
      razas.value = response.data
    } catch (error) {
      console.error('Error cargando razas:', error)
    }
  }

  return {
    clientes,
    pacientes,
    veterinarios,
    tiposServicio,
    razas,
    loadClientes,
    loadPacientes,
    loadVeterinarios,
    loadTiposServicio,
    loadRazas
  }
}
```

### Usar en un Componente

**Archivo:** `src/views/citas/CitaFormView.vue`

```javascript
import { useReferenceData } from '@/composables/useReferenceData'

const { clientes, pacientes, veterinarios, tiposServicio } = useReferenceData()

onMounted(async () => {
  await Promise.all([
    useReferenceData().loadClientes(),
    useReferenceData().loadPacientes(),
    useReferenceData().loadVeterinarios(),
    useReferenceData().loadTiposServicio()
  ])
})
```

---

## Paso 5: Manejo de Errores Mejorado

Crea un composable para manejo de errores:

**Archivo:** `src/composables/useNotification.js`

```javascript
import { ref } from 'vue'

export function useNotification() {
  const notification = ref({
    show: false,
    message: '',
    type: 'info' // 'success', 'error', 'warning', 'info'
  })

  const showNotification = (message, type = 'info', duration = 3000) => {
    notification.value = { show: true, message, type }
    setTimeout(() => {
      notification.value.show = false
    }, duration)
  }

  const showSuccess = (message) => showNotification(message, 'success')
  const showError = (message) => showNotification(message, 'error')
  const showWarning = (message) => showNotification(message, 'warning')
  const showInfo = (message) => showNotification(message, 'info')

  return {
    notification,
    showNotification,
    showSuccess,
    showError,
    showWarning,
    showInfo
  }
}
```

### Usar en un Componente

```javascript
import { useNotification } from '@/composables/useNotification'

const { showSuccess, showError } = useNotification()

const saveCita = async () => {
  try {
    await post('/v1/citas', form)
    showSuccess('Cita creada exitosamente')
    router.push('/citas')
  } catch (error) {
    showError(error.response?.data?.message || 'Error al crear cita')
  }
}
```

---

## Paso 6: Formato de Datos

### Convertir datos del API

El API retorna fechas en ISO format (string). Considera convertirlas:

**Archivo:** `src/utils/dateUtils.js`

```javascript
import { format, parse } from 'date-fns'
import { es } from 'date-fns/locale'

export function formatDate(date) {
  return format(new Date(date), 'dd/MM/yyyy', { locale: es })
}

export function formatDateTime(date) {
  return format(new Date(date), 'dd/MM/yyyy HH:mm', { locale: es })
}

export function parseDate(dateString) {
  return parse(dateString, 'yyyy-MM-dd', new Date())
}
```

### Usar en DataTable

```javascript
import { formatDate } from '@/utils/dateUtils'

const headers = [
  { title: 'Fecha', value: 'fecha' },
]

const items = [
  {
    fecha: '2025-11-11', // API retorna ISO
    fechaFormato: formatDate('2025-11-11') // "11/11/2025"
  }
]
```

---

## Paso 7: Validaciones en Formularios

El backend también valida, pero el frontend debe validar primero:

```javascript
// CitaFormView.vue
const rules = {
  required: (v) => !!v || 'Este campo es requerido',
  date: (v) => {
    const date = new Date(v)
    return date > new Date() || 'La fecha debe ser en el futuro'
  },
  phone: (v) => /^\d{10}$/.test(v) || 'Debe tener 10 dígitos',
  email: (v) => /.+@.+\..+/.test(v) || 'Email inválido'
}
```

---

## Checklist de Integración

- [ ] Backend ejecutándose en http://localhost:8080
- [ ] Acceso a Swagger en http://localhost:8080/swagger-ui.html
- [ ] Frontend ejecutándose en http://localhost:3000
- [ ] Login funciona con credenciales reales
- [ ] Token JWT se obtiene y almacena
- [ ] Token se envía en requests (revisar DevTools)
- [ ] GET /v1/citas funciona
- [ ] POST /v1/citas funciona
- [ ] Datos se muestran en tabla
- [ ] Filtros funcionan
- [ ] CRUD completo de citas
- [ ] Mismo para pacientes, clientes, facturas
- [ ] Errores se muestran en notifications
- [ ] Token expirado redirige a login
- [ ] Logout funciona

---

## Debugging

### Inspeccionar Requests en DevTools

1. Abre DevTools (F12)
2. Ir a "Network" tab
3. Filtra por "XHR" o "Fetch"
4. Haz una acción (ej: crear cita)
5. Inspecciona request y response

**Verifica:**
- Header Authorization contiene token
- Response Status 200 (éxito)
- JSON response tiene datos esperados

### Ver Token en localStorage

1. DevTools → Application
2. localStorage → http://localhost:3000
3. Clave "token" debe existir

### Logs en Consola

```javascript
// Agregar al composable useApi
console.log('Request:', config.url, config.data)
console.log('Response:', response.status, response.data)
```

---

## Troubleshooting Común

### Error: "Cannot find module"
Verifica imports y rutas relativas vs alias (@/)

### Error 401 Unauthorized
- Token no se está enviando
- Token expirado
- Credenciales incorrectas

### CORS Error
- Backend no permite origen frontend
- Verifica `app.cors.allowed-origins` en backend

### Datos vacíos en tabla
- Endpoint retorna []
- No hay datos en BD
- Request falla silenciosamente (revisar Network)

---

## Próximas Mejoras

- [ ] Implementar paginación en DataTables
- [ ] Agregar buscador en tiempo real
- [ ] Cachear datos de referencia
- [ ] Offline mode con Service Workers
- [ ] Sincronización automática
- [ ] WebSockets para updates en tiempo real

---

¡El frontend está listo para integración! 🚀

Cualquier pregunta sobre endpoints, revisar Swagger del backend o la documentación del repositorio.
