import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { useApi } from '@/composables/useApi'

const { get, post, put, delete: deleteRequest } = useApi()

export const useUsuariosStore = defineStore('usuarios', () => {
  const usuarios = ref([])
  const currentUsuario = ref(null)
  const loading = ref(false)
  const error = ref(null)

  const mapUsuarioDTO = (dto) => {
    if (!dto) return null
    return {
      ...dto,
      nombreCompleto: `${dto.nombre} ${dto.apellido}`,
      rolesStr: dto.roles?.map(r => r.nombre).join(', ') || '',
      tipoUsuarioStr: dto.tipoUsuario || '',
      estadoStr: dto.isActive ? 'Activo' : 'Inactivo'
    }
  }

  const usuariosCount = computed(() => usuarios.value.length)
  const usuariosActivos = computed(() => usuarios.value.filter(u => u.isActive).length)

  const fetchUsuarios = async (filters = {}) => {
    loading.value = true
    error.value = null
    try {
      const response = await get('/usuarios', { params: filters })
      const rawData = response.data?.content || response.data || []
      usuarios.value = rawData.map(mapUsuarioDTO)
      return usuarios.value
    } catch (err) {
      error.value = err.message || 'Error al cargar usuarios'
      console.error('Error fetching usuarios:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const fetchUsuarioById = async (id) => {
    loading.value = true
    error.value = null
    try {
      const response = await get(`/usuarios/${id}`)
      currentUsuario.value = mapUsuarioDTO(response.data)
      return currentUsuario.value
    } catch (err) {
      error.value = err.message || 'Error al cargar usuario'
      console.error('Error fetching usuario:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const createUsuario = async (formData) => {
    loading.value = true
    error.value = null
    try {
      const response = await post('/usuarios', formData)
      const mappedUsuario = mapUsuarioDTO(response.data)
      usuarios.value.push(mappedUsuario)
      return mappedUsuario
    } catch (err) {
      error.value = err.message || 'Error al crear usuario'
      console.error('Error creating usuario:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const updateUsuario = async (id, formData) => {
    loading.value = true
    error.value = null
    try {
      const response = await put(`/usuarios/${id}`, formData)
      const mappedUsuario = mapUsuarioDTO(response.data)
      const index = usuarios.value.findIndex(u => u.id === id)
      if (index > -1) {
        usuarios.value[index] = mappedUsuario
      }
      currentUsuario.value = mappedUsuario
      return mappedUsuario
    } catch (err) {
      error.value = err.message || 'Error al actualizar usuario'
      console.error('Error updating usuario:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const deleteUsuario = async (id) => {
    loading.value = true
    error.value = null
    try {
      await deleteRequest(`/usuarios/${id}`)
      usuarios.value = usuarios.value.filter(u => u.id !== id)
      return true
    } catch (err) {
      error.value = err.message || 'Error al eliminar usuario'
      console.error('Error deleting usuario:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  return {
    usuarios,
    currentUsuario,
    loading,
    error,
    usuariosCount,
    usuariosActivos,
    fetchUsuarios,
    fetchUsuarioById,
    createUsuario,
    updateUsuario,
    deleteUsuario,
  }
})
