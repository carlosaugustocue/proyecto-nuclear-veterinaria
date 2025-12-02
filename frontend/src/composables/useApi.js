import axios from 'axios'

const apiClient = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 10000, // 10 segundos timeout
})

// Agregar token a cada request
apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    console.log(`[API] ${config.method.toUpperCase()} ${config.baseURL}${config.url}`)
    return config
  },
  (error) => {
    console.error('[API Error en Request]', error)
    return Promise.reject(error)
  }
)

// Manejo de errores globales
apiClient.interceptors.response.use(
  (response) => {
    console.log(`[API Response] ${response.status} ${response.statusText}`, response.data)
    return response
  },
  (error) => {
    // Extraer mensaje de error del backend
    let errorMessage = 'Error desconocido'

    if (error.response?.data) {
      const errorData = error.response.data
      
      // Manejar errores de validación de Bean Validation
      if (errorData.errors) {
        if (Array.isArray(errorData.errors)) {
          // Formato: [{ field: "...", message: "..." }]
          errorMessage = errorData.errors
            .map(err => err.message || `${err.field}: ${err.defaultMessage || 'Error de validación'}`)
            .join(', ')
        } else if (typeof errorData.errors === 'object') {
          // Formato: { "campo": ["mensaje1", "mensaje2"] }
          const erroresArray = Object.entries(errorData.errors)
            .map(([campo, mensajes]) => {
              const mensajesArray = Array.isArray(mensajes) ? mensajes : [mensajes]
              return mensajesArray.map(msg => `${campo}: ${msg}`).join(', ')
            })
            .flat()
          errorMessage = erroresArray.join(', ')
        }
      }
      
      // Si no hay errores de validación específicos, usar mensaje general
      if (!errorMessage || errorMessage === 'Error desconocido') {
        errorMessage = errorData.message ||
                       errorData.error ||
                       errorData.mensaje ||
                     errorMessage
      }
    } else if (error.message) {
      errorMessage = error.message
    }

    console.error('[API Error en Response]', {
      status: error.response?.status,
      message: errorMessage,
      data: error.response?.data,
    })

    // Asignar mensaje mejorado al error
    error.userMessage = errorMessage

    if (error.response?.status === 401) {
      // Token expirado o inválido
      console.warn('[AUTH] Token inválido o expirado, redirigiendo a login')
      localStorage.removeItem('token')
      window.location.href = '/login'
    }

    // Manejo de errores de conexión
    if (error.message === 'Network Error' || !error.response) {
      console.error('[NETWORK ERROR] No se puede conectar con el servidor')
      error.userMessage = 'No se puede conectar con el servidor. Verifique que el backend esté ejecutándose en http://localhost:8080'
    }

    return Promise.reject(error)
  }
)

export function useApi() {
  return {
    get: (url, config = {}) => apiClient.get(url, config),
    post: (url, data, config = {}) => apiClient.post(url, data, config),
    put: (url, data, config = {}) => apiClient.put(url, data, config),
    patch: (url, data, config = {}) => apiClient.patch(url, data, config),
    delete: (url, config = {}) => apiClient.delete(url, config),
  }
}
