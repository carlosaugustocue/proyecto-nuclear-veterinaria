import api from './api'

export const authService = {
  /**
   * Login - Autenticar usuario
   */
  async login(email, password) {
    const response = await api.post('/auth/login', {
      email,
      password,
    })
    return response.data
  },

  /**
   * Logout - Cerrar sesión
   */
  logout() {
    localStorage.removeItem('authToken')
    localStorage.removeItem('user')
  },

  /**
   * Recuperar contraseña
   */
  async recuperarPassword(email) {
    const response = await api.post('/auth/recuperar-password', { email })
    return response.data
  },

  /**
   * Verificar si el usuario está autenticado
   */
  isAuthenticated() {
    return !!localStorage.getItem('authToken')
  },

  /**
   * Obtener usuario actual del localStorage
   */
  getCurrentUser() {
    const userStr = localStorage.getItem('user')
    return userStr ? JSON.parse(userStr) : null
  },

  /**
   * Verificar si el usuario tiene un permiso específico
   */
  hasPermission(permission) {
    const user = this.getCurrentUser()
    return user?.authorities?.includes(permission) || false
  },
}
