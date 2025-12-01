import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { useApi } from '@/composables/useApi'

const { get, post, put, delete: deleteRequest } = useApi()

export const useClientesStore = defineStore('clientes', () => {
  const clientes = ref([])
  const currentCliente = ref(null)
  const loading = ref(false)
  const error = ref(null)

  // Helper para mapear DTOs del backend
  const mapClienteDTO = (dto) => {
    if (!dto) return null
    return {
      ...dto,
      tipoDocumentoNombre: dto.tipoDocumento?.nombre || dto.tipoDocumento || '',
      nombreCompleto: dto.nombreCompleto || `${dto.nombre || ''} ${dto.apellido || ''}`.trim(),
      // Incluir pacientes/mascotas del DTO
      pacientes: dto.pacientes || [],
      cantidadPacientesActivos: dto.cantidadPacientesActivos || 0,
      // Alias para compatibilidad
      mascotas: dto.pacientes || [],
    }
  }

  const clientesCount = computed(() => clientes.value.length)

  const fetchClientes = async (filters = {}) => {
    loading.value = true
    error.value = null
    try {
      const response = await get('/v1/clientes', { params: filters })
      const rawData = response.data || []
      clientes.value = rawData.map(mapClienteDTO)
      return clientes.value
    } catch (err) {
      error.value = err.message || 'Error al cargar clientes'
      console.error('Error fetching clientes:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const fetchClienteById = async (id) => {
    loading.value = true
    error.value = null
    try {
      const response = await get(`/v1/clientes/${id}`)
      currentCliente.value = mapClienteDTO(response.data)
      return currentCliente.value
    } catch (err) {
      error.value = err.message || 'Error al cargar cliente'
      console.error('Error fetching cliente:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const createCliente = async (formData) => {
    loading.value = true
    error.value = null
    try {
      const response = await post('/v1/clientes', formData)
      const mappedCliente = mapClienteDTO(response.data)
      clientes.value.push(mappedCliente)
      return mappedCliente
    } catch (err) {
      error.value = err.message || 'Error al crear cliente'
      console.error('Error creating cliente:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const updateCliente = async (id, formData) => {
    loading.value = true
    error.value = null
    try {
      const response = await put(`/v1/clientes/${id}`, formData)
      const mappedCliente = mapClienteDTO(response.data)
      const index = clientes.value.findIndex(c => c.id === id)
      if (index > -1) {
        clientes.value[index] = mappedCliente
      }
      currentCliente.value = mappedCliente
      return mappedCliente
    } catch (err) {
      error.value = err.message || 'Error al actualizar cliente'
      console.error('Error updating cliente:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const deleteCliente = async (id) => {
    loading.value = true
    error.value = null
    try {
      await deleteRequest(`/v1/clientes/${id}`)
      clientes.value = clientes.value.filter(c => c.id !== id)
      return true
    } catch (err) {
      error.value = err.message || 'Error al eliminar cliente'
      console.error('Error deleting cliente:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  return {
    clientes,
    currentCliente,
    loading,
    error,
    clientesCount,
    fetchClientes,
    fetchClienteById,
    createCliente,
    updateCliente,
    deleteCliente,
  }
})
