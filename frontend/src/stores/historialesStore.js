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
      // Si es un 404, es esperado (el historial no existe aún)
      // No establecer error para 404, solo lanzar para que el componente lo maneje
      if (err.response?.status === 404) {
        currentHistorial.value = null
        error.value = null // No es un error, es un estado esperado
        loading.value = false
        throw err // Lanzar para que el componente pueda crear el historial
      }
      // Para otros errores, establecer el mensaje de error
      error.value = err.response?.data?.userMessage || 
                   err.response?.data?.message || 
                   err.message || 
                   'Error al cargar historial'
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
    const response = await post(`/v1/historiales-clinicos/paciente/${pacienteId}`)
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

  const createConsulta = async (historialId, formData, veterinarioId) => {
    loading.value = true
    error.value = null
    try {
      const response = await post(`/v1/consultas`, {
        historialClinicoId: historialId,
        veterinarioId: veterinarioId,
        fechaConsulta: new Date().toISOString().split('T')[0], // Fecha actual en formato YYYY-MM-DD
        motivo: formData.motivo,
        anamnesis: formData.anamnesis || null,
        examenFisico: formData.examenFisico || null,
        planTratamiento: formData.planTratamiento || null,
        pronostico: formData.pronostico || null,
        observaciones: formData.observaciones || null,
        requiereSeguimiento: formData.requiereSeguimiento || false,
        fechaSeguimiento: formData.fechaSeguimiento || null
      })

      // Recargar todas las consultas para obtener datos completos con información del veterinario
      await fetchConsultasByHistorial(historialId)

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
