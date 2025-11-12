import axios from 'axios'
import router from '@/router'

// URL base del backend Spring Boot
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api'

// Crear instancia de Axios
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 10000,
})

// Interceptor de Request - Agregar token JWT
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('authToken')

    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }

    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// Interceptor de Response - Manejar errores
api.interceptors.response.use(
  (response) => response,
  (error) => {
    // Token expirado o no autorizado
    if (error.response?.status === 401) {
      localStorage.removeItem('authToken')
      localStorage.removeItem('user')
      router.push('/login')
    }

    // Forbidden
    if (error.response?.status === 403) {
      console.error('Acceso denegado - Permisos insuficientes')
    }

    return Promise.reject(error)
  }
)

export default api
