import { ref } from 'vue'
import { useApi } from './useApi'

const { get } = useApi()

// Cached data
const razas = ref([])
const tiposServicio = ref([])
const usuarios = ref([])
const dataLoaded = ref({
  razas: false,
  tiposServicio: false,
  usuarios: false,
})

export function useReferenceData() {
  const fetchRazas = async () => {
    if (dataLoaded.value.razas && razas.value.length > 0) {
      return razas.value
    }
    try {
      const response = await get('/v1/razas')
      razas.value = response.data || response || []
      dataLoaded.value.razas = true
      return razas.value
    } catch (error) {
      console.error('Error fetching razas:', error)
      return []
    }
  }

  const fetchTiposServicio = async () => {
    if (dataLoaded.value.tiposServicio && tiposServicio.value.length > 0) {
      return tiposServicio.value
    }
    try {
      const response = await get('/v1/tipos-servicio')
      tiposServicio.value = response.data || response || []
      dataLoaded.value.tiposServicio = true
      return tiposServicio.value
    } catch (error) {
      console.error('Error fetching tipos-servicio:', error)
      return []
    }
  }

  const fetchUsuarios = async (tipo = 'VETERINARIO') => {
    if (dataLoaded.value.usuarios && usuarios.value.length > 0) {
      return usuarios.value
    }
    try {
      // useApi ya tiene baseURL='/api', solo usar /usuarios
      const response = await get('/usuarios/tipo/' + tipo)
      // El backend devuelve una Page, extraer el contenido
      usuarios.value = response.data?.content || response.data || []
      dataLoaded.value.usuarios = true
      return usuarios.value
    } catch (error) {
      console.error('Error fetching usuarios:', error)
      return []
    }
  }

  const fetchPacientes = async () => {
    try {
      const response = await get('/v1/pacientes')
      return response.data || response || []
    } catch (error) {
      console.error('Error fetching pacientes:', error)
      return []
    }
  }

  const fetchClientes = async () => {
    try {
      const response = await get('/v1/clientes')
      return response.data || response || []
    } catch (error) {
      console.error('Error fetching clientes:', error)
      return []
    }
  }

  const fetchCitas = async () => {
    try {
      const response = await get('/v1/citas')
      return response.data || response || []
    } catch (error) {
      console.error('Error fetching citas:', error)
      return []
    }
  }

  return {
    razas,
    tiposServicio,
    usuarios,
    fetchRazas,
    fetchTiposServicio,
    fetchUsuarios,
    fetchPacientes,
    fetchClientes,
    fetchCitas,
  }
}
