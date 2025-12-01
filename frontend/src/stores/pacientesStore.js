import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { useApi } from '@/composables/useApi'

const { get, post, put, delete: deleteRequest } = useApi()

export const usePacientesStore = defineStore('pacientes', () => {
  const pacientes = ref([])
  const currentPaciente = ref(null)
  const loading = ref(false)
  const error = ref(null)

  // Helper para mapear DTOs del backend
  const mapPacienteDTO = (dto) => {
    if (!dto) return null
    return {
      ...dto,
      // Mantener objetos completos
      clienteObj: dto.cliente,
      razaObj: dto.raza,
      // Campos planos para tablas
      clienteNombre: dto.cliente?.nombreCompleto || dto.cliente?.nombre || '',
      razaNombre: dto.raza?.nombre || '',
      estadoNombre: dto.estado?.nombre || dto.estado || '',
      especieNombre: dto.raza?.especie || dto.especie || '',
    }
  }

  const pacientesCount = computed(() => pacientes.value.length)
  const pacientesActivos = computed(() => pacientes.value.filter(p => {
    const estado = p.estadoNombre || p.estado
    return estado === 'Activo' || estado === 'ACTIVO'
  }).length)

  const fetchPacientes = async (filters = {}) => {
    loading.value = true
    error.value = null
    try {
      const response = await get('/v1/pacientes', { params: filters })
      const rawData = response.data || []
      pacientes.value = rawData.map(mapPacienteDTO)
      return pacientes.value
    } catch (err) {
      error.value = err.message || 'Error al cargar pacientes'
      console.error('Error fetching pacientes:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const fetchPacienteById = async (id) => {
    loading.value = true
    error.value = null
    try {
      const response = await get(`/v1/pacientes/${id}`)
      currentPaciente.value = mapPacienteDTO(response.data)
      return currentPaciente.value
    } catch (err) {
      error.value = err.message || 'Error al cargar paciente'
      console.error('Error fetching paciente:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const createPaciente = async (formData) => {
    loading.value = true
    error.value = null
    try {
      const response = await post('/v1/pacientes', formData)
      const mappedPaciente = mapPacienteDTO(response.data)
      pacientes.value.push(mappedPaciente)
      return mappedPaciente
    } catch (err) {
      error.value = err.message || 'Error al crear paciente'
      console.error('Error creating paciente:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const updatePaciente = async (id, formData) => {
    loading.value = true
    error.value = null
    try {
      const response = await put(`/v1/pacientes/${id}`, formData)
      const mappedPaciente = mapPacienteDTO(response.data)
      const index = pacientes.value.findIndex(p => p.id === id)
      if (index > -1) {
        pacientes.value[index] = mappedPaciente
      }
      currentPaciente.value = mappedPaciente
      return mappedPaciente
    } catch (err) {
      error.value = err.message || 'Error al actualizar paciente'
      console.error('Error updating paciente:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const deletePaciente = async (id) => {
    loading.value = true
    error.value = null
    try {
      await deleteRequest(`/v1/pacientes/${id}`)
      pacientes.value = pacientes.value.filter(p => p.id !== id)
      return true
    } catch (err) {
      error.value = err.message || 'Error al eliminar paciente'
      console.error('Error deleting paciente:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  // Buscar pacientes por cliente
  const fetchPacientesByCliente = async (clienteId) => {
    loading.value = true
    error.value = null
    try {
      const response = await get(`/v1/pacientes/cliente/${clienteId}`)
      const rawData = response.data || []
      return rawData.map(mapPacienteDTO)
    } catch (err) {
      error.value = err.message || 'Error al cargar pacientes del cliente'
      console.error('Error fetching pacientes by cliente:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  // Buscar pacientes activos por cliente
  const fetchPacientesActivosByCliente = async (clienteId) => {
    loading.value = true
    error.value = null
    try {
      const response = await get(`/v1/pacientes/cliente/${clienteId}/activos`)
      const rawData = response.data || []
      return rawData.map(mapPacienteDTO)
    } catch (err) {
      error.value = err.message || 'Error al cargar pacientes activos del cliente'
      console.error('Error fetching pacientes activos by cliente:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  return {
    pacientes,
    currentPaciente,
    loading,
    error,
    pacientesCount,
    pacientesActivos,
    fetchPacientes,
    fetchPacienteById,
    fetchPacientesByCliente,
    fetchPacientesActivosByCliente,
    createPaciente,
    updatePaciente,
    deletePaciente,
  }
})
