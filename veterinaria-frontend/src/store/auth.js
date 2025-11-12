import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { authService } from '@/services/auth.service'

export const useAuthStore = defineStore('auth', () => {
  // State
  const token = ref(localStorage.getItem('authToken') || null)
  const user = ref(JSON.parse(localStorage.getItem('user') || 'null'))
  const loading = ref(false)

  // Getters
  const isAuthenticated = computed(() => !!token.value)
  const userName = computed(() => user.value?.nombre || user.value?.username || user.value?.email || '')
  const userEmail = computed(() => user.value?.email || '')
  const userRoles = computed(() => user.value?.roles || [])

  // Actions
  async function login(email, password) {
    loading.value = true
    try {
      const data = await authService.login(email, password)

      token.value = data.token
      user.value = {
        username: data.username,
        email: data.email,
        nombre: data.nombre,
        roles: data.roles,
        authorities: data.authorities,
      }

      localStorage.setItem('authToken', data.token)
      localStorage.setItem('user', JSON.stringify(user.value))

      return true
    } catch (error) {
      console.error('Error en login:', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  function logout() {
    authService.logout()
    token.value = null
    user.value = null
  }

  function hasPermission(permission) {
    return user.value?.authorities?.includes(permission) || false
  }

  return {
    // State
    token,
    user,
    loading,
    // Getters
    isAuthenticated,
    userName,
    userEmail,
    userRoles,
    // Actions
    login,
    logout,
    hasPermission,
  }
})
