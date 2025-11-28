import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { useApi } from '@/composables/useApi'

const { get, post, put, delete: deleteRequest } = useApi()

export const useFacturasStore = defineStore('facturas', () => {
  const facturas = ref([])
  const currentFactura = ref(null)
  const loading = ref(false)
  const error = ref(null)

  // Helper para mapear DTOs del backend
  const mapFacturaDTO = (dto) => {
    if (!dto) return null
    return {
      ...dto,
      clienteObj: dto.cliente,
      clienteNombre: dto.cliente?.nombreCompleto || dto.cliente?.nombre || '',
      estadoNombre: dto.estado?.nombre || dto.estado || '',
      metodoPagoNombre: dto.metodoPago?.nombre || dto.metodoPago || '',
    }
  }

  const facturasCount = computed(() => facturas.value.length)
  const facturasTotal = computed(() =>
    facturas.value.reduce((sum, f) => sum + (f.total || 0), 0)
  )
  const facturasPendientes = computed(() =>
    facturas.value.filter(f => {
      const estado = f.estadoNombre || f.estado
      return estado === 'Pendiente' || estado === 'PENDIENTE'
    }).length
  )

  const fetchFacturas = async (filters = {}) => {
    loading.value = true
    error.value = null
    try {
      const response = await get('/v1/facturas', { params: filters })
      const rawData = response.data || []
      facturas.value = rawData.map(mapFacturaDTO)
      return facturas.value
    } catch (err) {
      error.value = err.message || 'Error al cargar facturas'
      console.error('Error fetching facturas:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const fetchFacturaById = async (id) => {
    loading.value = true
    error.value = null
    try {
      const response = await get(`/v1/facturas/${id}`)
      currentFactura.value = mapFacturaDTO(response.data)
      return currentFactura.value
    } catch (err) {
      error.value = err.message || 'Error al cargar factura'
      console.error('Error fetching factura:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const createFactura = async (formData) => {
    loading.value = true
    error.value = null
    try {
      const response = await post('/v1/facturas', formData)
      const mappedFactura = mapFacturaDTO(response.data)
      facturas.value.push(mappedFactura)
      return mappedFactura
    } catch (err) {
      error.value = err.message || 'Error al crear factura'
      console.error('Error creating factura:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const updateFactura = async (id, formData) => {
    loading.value = true
    error.value = null
    try {
      const response = await put(`/v1/facturas/${id}`, formData)
      const mappedFactura = mapFacturaDTO(response.data)
      const index = facturas.value.findIndex(f => f.id === id)
      if (index > -1) {
        facturas.value[index] = mappedFactura
      }
      currentFactura.value = mappedFactura
      return mappedFactura
    } catch (err) {
      error.value = err.message || 'Error al actualizar factura'
      console.error('Error updating factura:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const deleteFactura = async (id) => {
    loading.value = true
    error.value = null
    try {
      await deleteRequest(`/v1/facturas/${id}`)
      facturas.value = facturas.value.filter(f => f.id !== id)
      return true
    } catch (err) {
      error.value = err.message || 'Error al eliminar factura'
      console.error('Error deleting factura:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  // Operaciones avanzadas de facturas
  const marcarComoPagada = async (id) => {
    loading.value = true
    error.value = null
    try {
      const response = await post(`/v1/facturas/${id}/marcar-pagada`)
      const mappedFactura = mapFacturaDTO(response.data)
      const index = facturas.value.findIndex(f => f.id === id)
      if (index > -1) {
        facturas.value[index] = mappedFactura
      }
      currentFactura.value = mappedFactura
      return mappedFactura
    } catch (err) {
      error.value = err.message || 'Error al marcar factura como pagada'
      console.error('Error marking factura as paid:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const anularFactura = async (id, motivo) => {
    loading.value = true
    error.value = null
    try {
      const response = await post(`/v1/facturas/${id}/anular`, { motivo })
      const mappedFactura = mapFacturaDTO(response.data)
      const index = facturas.value.findIndex(f => f.id === id)
      if (index > -1) {
        facturas.value[index] = mappedFactura
      }
      currentFactura.value = mappedFactura
      return mappedFactura
    } catch (err) {
      error.value = err.message || 'Error al anular factura'
      console.error('Error canceling factura:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  return {
    facturas,
    currentFactura,
    loading,
    error,
    facturasCount,
    facturasTotal,
    facturasPendientes,
    fetchFacturas,
    fetchFacturaById,
    createFactura,
    updateFactura,
    deleteFactura,
    marcarComoPagada,
    anularFactura,
  }
})
