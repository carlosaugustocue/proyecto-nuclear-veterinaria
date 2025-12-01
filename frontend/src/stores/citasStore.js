import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { useApi } from '@/composables/useApi'

const { get, post, put, patch, delete: deleteRequest } = useApi()

export const useCitasStore = defineStore('citas', () => {
  const citas = ref([])
  const currentCita = ref(null)
  const loading = ref(false)
  const error = ref(null)

  // Helper para mapear DTOs del backend a formato amigable para vistas
  const mapCitaDTO = (dto) => {
    if (!dto) return null
    return {
      ...dto,
      // Mantener objetos completos para edición (si existen)
      pacienteObj: dto.paciente,
      clienteObj: dto.cliente,
      veterinarioObj: dto.veterinario,
      tipoServicioObj: dto.tipoServicio,
      // Usar campos directos del DTO (el backend ya los mapea)
      // Si no vienen, intentar extraer de objetos anidados como fallback
      pacienteNombre: dto.pacienteNombre || dto.paciente?.nombre || '',
      clienteNombre: dto.clienteNombre || dto.cliente?.nombreCompleto || dto.cliente?.nombre || '',
      veterinarioNombre: dto.veterinarioNombre || dto.veterinario?.nombreCompleto || dto.veterinario?.nombre || '',
      tipoServicioNombre: dto.tipoServicioNombre || dto.tipoServicio?.nombre || '',
      // El backend retorna el estado como string en el campo 'estado'
      estadoNombre: dto.estado || dto.estadoNombre || '',
      // Mantener también el campo estado original para compatibilidad
      estado: dto.estado || dto.estadoNombre || '',
    }
  }

  const citasCount = computed(() => citas.value.length)
  const citasProximas = computed(() => citas.value.filter(c => {
    const estado = (c.estadoNombre || c.estado || '').toLowerCase()
    return estado === 'confirmada' || estado === 'programada' || estado.includes('atenci')
  }).length)

  const fetchCitas = async (filters = {}) => {
    loading.value = true
    error.value = null
    try {
      const response = await get('/v1/citas', { params: filters })
      const rawData = response.data || []
      citas.value = rawData.map(mapCitaDTO)
      return citas.value
    } catch (err) {
      error.value = err.message || 'Error al cargar citas'
      console.error('Error fetching citas:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const fetchCitaById = async (id) => {
    loading.value = true
    error.value = null
    try {
      const response = await get(`/v1/citas/${id}`)
      currentCita.value = mapCitaDTO(response.data)
      return currentCita.value
    } catch (err) {
      error.value = err.message || 'Error al cargar cita'
      console.error('Error fetching cita:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const createCita = async (formData) => {
    loading.value = true
    error.value = null
    try {
      // Log de datos que se envían para debugging
      console.log('[CitasStore] Creando cita con datos:', JSON.stringify(formData, null, 2))
      
      const response = await post('/v1/citas', formData)
      const mappedCita = mapCitaDTO(response.data)
      citas.value.push(mappedCita)
      return mappedCita
    } catch (err) {
      error.value = err.message || 'Error al crear cita'
      console.error('Error creating cita:', err)
      console.error('Error response data:', err.response?.data)
      throw err
    } finally {
      loading.value = false
    }
  }

  const updateCita = async (id, formData) => {
    loading.value = true
    error.value = null
    try {
      const response = await put(`/v1/citas/${id}`, formData)
      const mappedCita = mapCitaDTO(response.data)
      const index = citas.value.findIndex(c => c.id === id)
      if (index > -1) {
        citas.value[index] = mappedCita
      }
      currentCita.value = mappedCita
      return mappedCita
    } catch (err) {
      error.value = err.message || 'Error al actualizar cita'
      console.error('Error updating cita:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const deleteCita = async (id) => {
    loading.value = true
    error.value = null
    try {
      await deleteRequest(`/v1/citas/${id}`)
      citas.value = citas.value.filter(c => c.id !== id)
      return true
    } catch (err) {
      error.value = err.message || 'Error al eliminar cita'
      console.error('Error deleting cita:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  // Operaciones avanzadas de citas
  const confirmarCita = async (id) => {
    loading.value = true
    error.value = null
    try {
      const response = await patch(`/v1/citas/${id}/confirmar`)
      const mappedCita = mapCitaDTO(response.data)
      const index = citas.value.findIndex(c => c.id === id)
      if (index > -1) {
        citas.value[index] = mappedCita
      }
      currentCita.value = mappedCita
      return mappedCita
    } catch (err) {
      error.value = err.message || 'Error al confirmar cita'
      console.error('Error confirming cita:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const cancelarCita = async (id, motivoCancelacion) => {
    loading.value = true
    error.value = null
    try {
      const response = await patch(`/v1/citas/${id}/cancelar`, { motivoCancelacion })
      const mappedCita = mapCitaDTO(response.data)
      const index = citas.value.findIndex(c => c.id === id)
      if (index > -1) {
        citas.value[index] = mappedCita
      }
      currentCita.value = mappedCita
      return mappedCita
    } catch (err) {
      error.value = err.message || 'Error al cancelar cita'
      console.error('Error canceling cita:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const iniciarCita = async (id) => {
    loading.value = true
    error.value = null
    try {
      const response = await patch(`/v1/citas/${id}/iniciar`)
      const mappedCita = mapCitaDTO(response.data)
      const index = citas.value.findIndex(c => c.id === id)
      if (index > -1) {
        citas.value[index] = mappedCita
      }
      currentCita.value = mappedCita
      return mappedCita
    } catch (err) {
      error.value = err.message || 'Error al iniciar cita'
      console.error('Error starting cita:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const completarCita = async (id) => {
    loading.value = true
    error.value = null
    try {
      const response = await patch(`/v1/citas/${id}/completar`)
      const mappedCita = mapCitaDTO(response.data)
      const index = citas.value.findIndex(c => c.id === id)
      if (index > -1) {
        citas.value[index] = mappedCita
      }
      currentCita.value = mappedCita
      return mappedCita
    } catch (err) {
      error.value = err.message || 'Error al completar cita'
      console.error('Error completing cita:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const reagendarCita = async (id, nuevaFecha, nuevaHora, motivoReagendamiento) => {
    loading.value = true
    error.value = null
    try {
      const response = await patch(`/v1/citas/${id}/reagendar`, {
        nuevaFecha,
        nuevaHora,
        motivoReagendamiento
      })
      const mappedCita = mapCitaDTO(response.data)
      const index = citas.value.findIndex(c => c.id === id)
      if (index > -1) {
        citas.value[index] = mappedCita
      }
      currentCita.value = mappedCita
      return mappedCita
    } catch (err) {
      error.value = err.message || 'Error al reagendar cita'
      console.error('Error rescheduling cita:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const obtenerHorariosDisponibles = async (veterinarioId, fecha, duracionMinutos = 30) => {
    loading.value = true
    error.value = null
    try {
      const response = await get(`/v1/citas/disponibilidad/veterinario/${veterinarioId}`, {
        params: { fecha, duracion: duracionMinutos }
      })
      return response.data || []
    } catch (err) {
      error.value = err.message || 'Error al obtener horarios disponibles'
      console.error('Error fetching available times:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  return {
    citas,
    currentCita,
    loading,
    error,
    citasCount,
    citasProximas,
    fetchCitas,
    fetchCitaById,
    createCita,
    updateCita,
    deleteCita,
    confirmarCita,
    cancelarCita,
    iniciarCita,
    completarCita,
    reagendarCita,
    obtenerHorariosDisponibles,
  }
})
