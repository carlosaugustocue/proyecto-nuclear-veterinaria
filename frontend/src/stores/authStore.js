import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { useApi } from '@/composables/useApi'

export const useAuthStore = defineStore('auth', () => {
  const user = ref(null)
  const token = ref(localStorage.getItem('token'))
  const isAuthenticated = computed(() => !!token.value && !!user.value)

  const { post, get } = useApi()

  const login = async (email, password) => {
    try {
      const response = await post('/auth/login', { email, password })
      const { token: newToken, usuario } = response.data

      token.value = newToken
      user.value = usuario
      localStorage.setItem('token', newToken)

      return { success: true }
    } catch (error) {
      return { success: false, error: error.response?.data?.message || 'Error al iniciar sesión' }
    }
  }

  const logout = async () => {
    try {
      await post('/auth/logout')
    } catch (error) {
      console.error('Error al cerrar sesión:', error)
    } finally {
      token.value = null
      user.value = null
      localStorage.removeItem('token')
    }
  }

  const validateToken = async () => {
    if (!token.value) return false

    try {
      const response = await get('/auth/validar')
      return response.status === 200
    } catch (error) {
      token.value = null
      user.value = null
      localStorage.removeItem('token')
      return false
    }
  }

  const getCurrentUser = async () => {
    try {
      const response = await get('/auth/me')
      user.value = response.data
      return response.data
    } catch (error) {
      return null
    }
  }

  return {
    user,
    token,
    isAuthenticated,
    login,
    logout,
    validateToken,
    getCurrentUser,
  }
})
