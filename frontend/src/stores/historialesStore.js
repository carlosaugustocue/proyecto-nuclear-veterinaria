import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { useApi } from '@/composables/useApi'

const { get, post, put } = useApi()

export const useHistorialesStore = defineStore('historiales', () => {
  const historiales = ref([])
  const currentHistorial = ref(null)
  const consultas = ref([])
  const loading = ref(false)
  const error = ref(null)

  const fetchHistorialByPaciente = async (pacienteId) => {
    loading.value = true
    error.value = null
    try {
      const response = await get(`/v1/historiales-clinicos/paciente/${pacienteId}`)
      currentHistorial.value = response.data
      return currentHistorial.value
    } catch (err) {
      error.value = err.message || 'Error al cargar historial'
      console.error('Error fetching historial:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const createHistorial = async (pacienteId) => {
    loading.value = true
    error.value = null
    try {
      const response = await post('/v1/historiales-clinicos', { pacienteId })
      currentHistorial.value = response.data
      return currentHistorial.value
    } catch (err) {
      error.value = err.message || 'Error al crear historial'
      console.error('Error creating historial:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const fetchConsultasByHistorial = async (historialId) => {
    loading.value = true
    error.value = null
    try {
      const response = await get(`/v1/consultas/historial/${historialId}`)
      consultas.value = response.data?.content || response.data || []
      return consultas.value
    } catch (err) {
      error.value = err.message || 'Error al cargar consultas'
      console.error('Error fetching consultas:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const createConsulta = async (historialId, formData) => {
    loading.value = true
    error.value = null
    try {
      const response = await post(`/v1/consultas`, {
        ...formData,
        historialId
      })
      consultas.value.unshift(response.data)
      return response.data
    } catch (err) {
      error.value = err.message || 'Error al crear consulta'
      console.error('Error creating consulta:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  return {
    historiales,
    currentHistorial,
    consultas,
    loading,
    error,
    fetchHistorialByPaciente,
    createHistorial,
    fetchConsultasByHistorial,
    createConsulta,
  }
})
