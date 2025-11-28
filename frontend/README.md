# Sistema de Gestión Veterinaria - Frontend

Frontend del Sistema de Gestión Veterinaria desarrollado con Vue 3, Vite y Vuetify.

## 🚀 Características

- **Vue 3** - Framework progresivo de JavaScript
- **Vite** - Build tool y dev server ultra rápido
- **Vuetify 3** - Librería de componentes Material Design
- **Pinia** - Gestión de estado moderno
- **Vue Router** - Enrutamiento
- **Axios** - Cliente HTTP
- **Autenticación JWT** - Seguridad con tokens

## 📋 Módulos Implementados

1. **Autenticación** - Login con JWT, validación de sesiones
2. **Dashboard** - Panel de control con estadísticas
3. **Citas** - CRUD completo de citas médicas
4. **Pacientes** - Gestión de mascotas
5. **Clientes** - Gestión de propietarios
6. **Facturas** - Facturación y pagos

## 🛠️ Instalación

```bash
# Instalar dependencias
npm install

# Ejecutar servidor de desarrollo
npm run dev

# Build para producción
npm run build

# Preview de build
npm run preview

# Linting y formateo
npm run lint
```

## 📁 Estructura del Proyecto

```
frontend/
├── src/
│   ├── main.js                 # Punto de entrada
│   ├── App.vue                 # Componente raíz
│   ├── plugins/
│   │   └── vuetify.js          # Configuración de Vuetify
│   ├── router/
│   │   └── index.js            # Rutas y guards
│   ├── stores/
│   │   └── authStore.js        # Store de autenticación (Pinia)
│   ├── composables/
│   │   └── useApi.js           # Composable para API HTTP
│   └── views/
│       ├── LoginView.vue       # Vista de login
│       ├── DashboardView.vue   # Dashboard principal
│       ├── CitasView.vue       # Listado de citas
│       ├── PacientesView.vue   # Listado de pacientes
│       ├── ClientesView.vue    # Listado de clientes
│       ├── FacturasView.vue    # Listado de facturas
│       ├── citas/              # Vistas de citas
│       ├── pacientes/          # Vistas de pacientes
│       ├── clientes/           # Vistas de clientes
│       └── facturas/           # Vistas de facturas
├── index.html                  # HTML principal
├── vite.config.js              # Configuración de Vite
├── package.json                # Dependencias
└── README.md                   # Este archivo
```

## 🔐 Configuración de Autenticación

El frontend utiliza JWT (JSON Web Tokens) para autenticación:

1. El usuario se autentica en `/login`
2. El backend retorna un token JWT
3. El token se almacena en `localStorage`
4. Se envía en cada request en el header `Authorization: Bearer <token>`
5. Si el token expira, el usuario es redirigido a login

## 🌐 Conexión al Backend

El proxy de Vite redirije las llamadas a `/api` hacia `http://localhost:8080`:

```javascript
proxy: {
  '/api': {
    target: 'http://localhost:8080',
    changeOrigin: true,
  },
}
```

Para cambiar la URL del backend, modifica `vite.config.js`.

## 📱 Componentes principales

### Composable useApi
Proporciona métodos para hacer requests HTTP con autenticación automática:

```javascript
import { useApi } from '@/composables/useApi'

const { get, post, put, patch, delete: deleteRequest } = useApi()

const response = await get('/v1/citas')
```

### Store de Autenticación (Pinia)
Gestiona el estado de autenticación:

```javascript
import { useAuthStore } from '@/stores/authStore'

const authStore = useAuthStore()
await authStore.login(email, password)
authStore.logout()
```

## 🎨 Tema y Estilos

Vuetify está configurado con un tema de color personalizado. Modifica el archivo `src/plugins/vuetify.js` para cambiar colores:

```javascript
const customTheme = {
  colors: {
    primary: '#1976D2',
    secondary: '#424242',
    // ... más colores
  }
}
```

## 🚀 Despliegue en Producción

1. Build:
   ```bash
   npm run build
   ```

2. Los archivos compilados se generan en `dist/`

3. Despliega el contenido de `dist/` en tu servidor web

4. Configura el servidor web para servir `index.html` en rutas no encontradas (para que Vue Router funcione correctamente)

Ejemplo con Nginx:
```nginx
location / {
  try_files $uri $uri/ /index.html;
}
```

## 📝 Guía de Desarrollo

### Crear una nueva vista

1. Crea el archivo en `src/views/MiVistaView.vue`
2. Agrega la ruta en `src/router/index.js`
3. Importa y usa la vista en el router

### Agregar un endpoint de API

1. Usa el composable `useApi` en tu componente:
   ```javascript
   const { get, post } = useApi()
   const response = await get('/v1/mi-endpoint')
   ```

2. El token JWT se añade automáticamente en el header

### Estado global con Pinia

1. Crea un nuevo store en `src/stores/`
2. Define estado, acciones y getters
3. Usa en componentes con `defineStore`

## 🐛 Troubleshooting

### El frontend no se conecta al backend
- Verifica que el servidor Spring Boot esté corriendo en `http://localhost:8080`
- Comprueba la configuración del proxy en `vite.config.js`
- Revisa la consola del navegador para errores CORS

### Token expirado
- El token JWT se valida automáticamente
- Si está expirado, se redirige a login
- Los datos se guardan en `localStorage` bajo la clave `token`

### Errores de CORS
- Asegúrate de que el backend tenga CORS habilitado
- Verifica `app.cors.allowed-origins` en `application.properties` del backend

## 📚 Recursos

- [Vue 3 Docs](https://vuejs.org/)
- [Vite Docs](https://vitejs.dev/)
- [Vuetify Documentation](https://vuetifyjs.com/)
- [Pinia Documentation](https://pinia.vuejs.org/)
- [Vue Router Documentation](https://router.vuejs.org/)

## 📄 Licencia

Este proyecto es parte del Sistema de Gestión Veterinaria.
