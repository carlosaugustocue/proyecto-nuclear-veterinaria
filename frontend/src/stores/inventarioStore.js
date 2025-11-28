import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { useApi } from '@/composables/useApi'

const { get, post, put } = useApi()

export const useInventarioStore = defineStore('inventario', () => {
  const productos = ref([])
  const movimientos = ref([])
  const currentProducto = ref(null)
  const loading = ref(false)
  const error = ref(null)

  const fetchProductos = async (filters = {}) => {
    loading.value = true
    error.value = null
    try {
      const response = await get('/v1/productos', { params: filters })
      productos.value = response.data?.content || response.data || []
      return productos.value
    } catch (err) {
      error.value = err.message || 'Error al cargar productos'
      console.error('Error fetching productos:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const fetchProductoById = async (id) => {
    loading.value = true
    error.value = null
    try {
      const response = await get(`/v1/productos/${id}`)
      currentProducto.value = response.data
      return currentProducto.value
    } catch (err) {
      error.value = err.message || 'Error al cargar producto'
      console.error('Error fetching producto:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const createProducto = async (formData) => {
    loading.value = true
    error.value = null
    try {
      const response = await post('/v1/productos', formData)
      productos.value.push(response.data)
      return response.data
    } catch (err) {
      error.value = err.message || 'Error al crear producto'
      console.error('Error creating producto:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const createMovimiento = async (formData) => {
    loading.value = true
    error.value = null
    try {
      const response = await post('/v1/movimientos-inventario', formData)
      movimientos.value.unshift(response.data)
      return response.data
    } catch (err) {
      error.value = err.message || 'Error al crear movimiento'
      console.error('Error creating movimiento:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const fetchMovimientos = async (filters = {}) => {
    loading.value = true
    error.value = null
    try {
      const response = await get('/v1/movimientos-inventario', { params: filters })
      movimientos.value = response.data?.content || response.data || []
      return movimientos.value
    } catch (err) {
      error.value = err.message || 'Error al cargar movimientos'
      console.error('Error fetching movimientos:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  return {
    productos,
    movimientos,
    currentProducto,
    loading,
    error,
    fetchProductos,
    fetchProductoById,
    createProducto,
    createMovimiento,
    fetchMovimientos,
  }
})
