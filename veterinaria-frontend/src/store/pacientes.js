import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { pacientesService } from '@/services/pacientes.service'
import { useAppStore } from './app'

export const usePacientesStore = defineStore('pacientes', () => {
  const appStore = useAppStore()

  // State
  const pacientes = ref([])
  const pacienteActual = ref(null)
  const loading = ref(false)
  const filters = ref({
    search: '',
    especie: null,
    estado: null,
    clienteId: null,
  })

  // Getters
  const pacientesFiltrados = computed(() => {
    let resultado = [...pacientes.value]

    // Filtrar por búsqueda (nombre o microchip)
    if (filters.value.search) {
      const search = filters.value.search.toLowerCase()
      resultado = resultado.filter(
        (p) =>
          p.nombre.toLowerCase().includes(search) ||
          p.microchip?.toLowerCase().includes(search)
      )
    }

    // Filtrar por especie
    if (filters.value.especie) {
      resultado = resultado.filter((p) => p.especie === filters.value.especie)
    }

    // Filtrar por estado
    if (filters.value.estado) {
      resultado = resultado.filter((p) => p.estado === filters.value.estado)
    }

    // Filtrar por cliente
    if (filters.value.clienteId) {
      resultado = resultado.filter((p) => p.cliente?.id === filters.value.clienteId)
    }

    return resultado
  })

  const totalPacientes = computed(() => pacientes.value.length)
  const pacientesActivos = computed(
    () => pacientes.value.filter((p) => p.estado === 'ACTIVO').length
  )

  // Actions
  async function cargarPacientes() {
    loading.value = true
    try {
      pacientes.value = await pacientesService.getAll()
    } catch (error) {
      appStore.showError('Error al cargar pacientes')
      console.error('Error cargando pacientes:', error)
    } finally {
      loading.value = false
    }
  }

  async function cargarPaciente(id) {
    loading.value = true
    try {
      pacienteActual.value = await pacientesService.getById(id)
      return pacienteActual.value
    } catch (error) {
      appStore.showError('Error al cargar paciente')
      console.error('Error cargando paciente:', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  async function crearPaciente(pacienteData) {
    loading.value = true
    try {
      const nuevoPaciente = await pacientesService.create(pacienteData)
      pacientes.value.push(nuevoPaciente)
      appStore.showSuccess('Paciente creado exitosamente')
      return nuevoPaciente
    } catch (error) {
      const message = error.response?.data?.message || 'Error al crear paciente'
      appStore.showError(message)
      console.error('Error creando paciente:', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  async function actualizarPaciente(id, pacienteData) {
    loading.value = true
    try {
      const pacienteActualizado = await pacientesService.update(id, pacienteData)
      const index = pacientes.value.findIndex((p) => p.id === id)
      if (index !== -1) {
        pacientes.value[index] = pacienteActualizado
      }
      appStore.showSuccess('Paciente actualizado exitosamente')
      return pacienteActualizado
    } catch (error) {
      const message = error.response?.data?.message || 'Error al actualizar paciente'
      appStore.showError(message)
      console.error('Error actualizando paciente:', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  async function eliminarPaciente(id) {
    loading.value = true
    try {
      await pacientesService.delete(id)
      pacientes.value = pacientes.value.filter((p) => p.id !== id)
      appStore.showSuccess('Paciente eliminado exitosamente')
    } catch (error) {
      const message = error.response?.data?.message || 'Error al eliminar paciente'
      appStore.showError(message)
      console.error('Error eliminando paciente:', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  async function buscarPorNombre(nombre) {
    loading.value = true
    try {
      pacientes.value = await pacientesService.searchByName(nombre)
    } catch (error) {
      appStore.showError('Error al buscar pacientes')
      console.error('Error buscando pacientes:', error)
    } finally {
      loading.value = false
    }
  }

  async function buscarPorEspecie(especie) {
    loading.value = true
    try {
      pacientes.value = await pacientesService.searchByEspecie(especie)
    } catch (error) {
      appStore.showError('Error al buscar pacientes')
      console.error('Error buscando pacientes:', error)
    } finally {
      loading.value = false
    }
  }

  async function buscarPorEstado(estado) {
    loading.value = true
    try {
      pacientes.value = await pacientesService.searchByEstado(estado)
    } catch (error) {
      appStore.showError('Error al buscar pacientes')
      console.error('Error buscando pacientes:', error)
    } finally {
      loading.value = false
    }
  }

  async function buscarPorCliente(clienteId) {
    loading.value = true
    try {
      pacientes.value = await pacientesService.getByCliente(clienteId)
    } catch (error) {
      appStore.showError('Error al cargar pacientes del cliente')
      console.error('Error cargando pacientes del cliente:', error)
    } finally {
      loading.value = false
    }
  }

  function setFilters(newFilters) {
    filters.value = { ...filters.value, ...newFilters }
  }

  function clearFilters() {
    filters.value = {
      search: '',
      especie: null,
      estado: null,
      clienteId: null,
    }
  }

  function clearPacienteActual() {
    pacienteActual.value = null
  }

  return {
    // State
    pacientes,
    pacienteActual,
    loading,
    filters,
    // Getters
    pacientesFiltrados,
    totalPacientes,
    pacientesActivos,
    // Actions
    cargarPacientes,
    cargarPaciente,
    crearPaciente,
    actualizarPaciente,
    eliminarPaciente,
    buscarPorNombre,
    buscarPorEspecie,
    buscarPorEstado,
    buscarPorCliente,
    setFilters,
    clearFilters,
    clearPacienteActual,
  }
})
