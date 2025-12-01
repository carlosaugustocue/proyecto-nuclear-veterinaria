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
      clienteNombre: dto.clienteNombre || dto.cliente?.nombreCompleto || dto.cliente?.nombre || '',
      estadoNombre: dto.estado || dto.estadoNombre || '',
      metodoPagoNombre: dto.metodoPago?.nombre || dto.metodoPago || '',
      // Asegurar que detalles y pagos estén disponibles
      detalles: dto.detalles || [],
      pagos: dto.pagos || [],
      // Mapear campos de fecha
      fechaEmision: dto.fechaEmision || dto.fecha,
      fecha: dto.fechaEmision || dto.fecha,
      // Mapear número de factura
      numeroFactura: dto.numeroFactura || dto.numero,
      numero: dto.numeroFactura || dto.numero,
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

  // Buscar factura por citaId
  const fetchFacturaByCitaId = async (citaId) => {
    if (!citaId) return null
    
    error.value = null
    try {
      // Buscar en la lista de facturas cargadas primero (sin marcar loading para no bloquear UI)
      const facturaExistente = facturas.value.find(f => f.citaId === citaId)
      if (facturaExistente) {
        return facturaExistente
      }
      
      // Si no está en la lista, buscar en el backend
      // Buscar en todas las facturas del cliente o directamente en todas
      const response = await get('/v1/facturas')
      const rawData = response.data || []
      const facturasMapeadas = rawData.map(mapFacturaDTO)
      
      // Actualizar el store con todas las facturas
      facturas.value = facturasMapeadas
      
      // Buscar la factura por citaId
      const factura = facturasMapeadas.find(f => {
        // Comparar tanto por número como por string
        const facturaCitaId = f.citaId
        return facturaCitaId !== null && 
               facturaCitaId !== undefined && 
               (facturaCitaId === citaId || facturaCitaId.toString() === citaId.toString())
      })
      
      return factura || null
    } catch (err) {
      // No establecer error para que no se muestre al usuario
      // Solo loggear para debugging
      console.error('Error fetching factura by citaId:', err)
      return null
    }
  }

  // Crear factura desde una cita
  const createFacturaFromCita = async (citaId, clienteId, usuarioEmisorId, detalles = []) => {
    loading.value = true
    error.value = null
    try {
      const facturaData = {
        clienteId: clienteId,
        citaId: citaId,
        usuarioEmisorId: usuarioEmisorId,
        fechaEmision: new Date().toISOString().split('T')[0],
        detalles: detalles
      }
      
      const response = await post('/v1/facturas', facturaData)
      const mappedFactura = mapFacturaDTO(response.data)
      facturas.value.push(mappedFactura)
      return mappedFactura
    } catch (err) {
      error.value = err.message || 'Error al crear factura desde cita'
      console.error('Error creating factura from cita:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  // Agregar detalle a factura existente
  const agregarDetalle = async (facturaId, detalleData) => {
    // No usar loading general para no bloquear la UI
    error.value = null
    try {
      const response = await post(`/v1/facturas/${facturaId}/detalles`, detalleData)
      // Recargar la factura para obtener los detalles actualizados
      // No usar loading aquí para no bloquear
      try {
        const facturaResponse = await get(`/v1/facturas/${facturaId}`)
        currentFactura.value = mapFacturaDTO(facturaResponse.data)
        // Actualizar también en la lista si existe
        const index = facturas.value.findIndex(f => f.id === facturaId)
        if (index > -1) {
          facturas.value[index] = currentFactura.value
        }
      } catch (reloadError) {
        console.error('Error al recargar factura después de agregar detalle:', reloadError)
      }
      return response.data
    } catch (err) {
      const errorMessage = err.response?.data?.userMessage || 
                          err.response?.data?.message || 
                          err.message || 
                          'Error al agregar detalle a la factura'
      error.value = errorMessage
      console.error('Error adding detalle to factura:', err)
      throw { ...err, userMessage: errorMessage }
    }
  }

  // Eliminar detalle de factura
  const eliminarDetalle = async (facturaId, detalleId) => {
    // No usar loading general para no bloquear la UI
    error.value = null
    try {
      await deleteRequest(`/v1/facturas/${facturaId}/detalles/${detalleId}`)
      // Recargar la factura para obtener los detalles actualizados
      // No usar loading aquí para no bloquear
      try {
        const facturaResponse = await get(`/v1/facturas/${facturaId}`)
        currentFactura.value = mapFacturaDTO(facturaResponse.data)
        // Actualizar también en la lista si existe
        const index = facturas.value.findIndex(f => f.id === facturaId)
        if (index > -1) {
          facturas.value[index] = currentFactura.value
        }
      } catch (reloadError) {
        console.error('Error al recargar factura después de eliminar detalle:', reloadError)
      }
      return true
    } catch (err) {
      const errorMessage = err.response?.data?.userMessage || 
                          err.response?.data?.message || 
                          err.message || 
                          'Error al eliminar detalle de la factura'
      error.value = errorMessage
      console.error('Error deleting detalle from factura:', err)
      throw { ...err, userMessage: errorMessage }
    }
  }

  // Registrar pago a factura
  const registrarPago = async (facturaId, pagoData) => {
    // No usar loading general para no bloquear la UI
    error.value = null
    try {
      const response = await post(`/v1/facturas/${facturaId}/pagos`, pagoData)
      // Recargar la factura para obtener los datos actualizados
      try {
        const facturaResponse = await get(`/v1/facturas/${facturaId}`)
        currentFactura.value = mapFacturaDTO(facturaResponse.data)
        // Actualizar también en la lista si existe
        const index = facturas.value.findIndex(f => f.id === facturaId)
        if (index > -1) {
          facturas.value[index] = currentFactura.value
        }
      } catch (reloadError) {
        console.error('Error al recargar factura después de registrar pago:', reloadError)
      }
      return response.data
    } catch (err) {
      const errorMessage = err.response?.data?.userMessage || 
                          err.response?.data?.message || 
                          err.message || 
                          'Error al registrar el pago'
      error.value = errorMessage
      console.error('Error registering pago:', err)
      throw { ...err, userMessage: errorMessage }
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
    fetchFacturaByCitaId,
    createFactura,
    createFacturaFromCita,
    updateFactura,
    deleteFactura,
    agregarDetalle,
    eliminarDetalle,
    registrarPago,
    marcarComoPagada,
    anularFactura,
  }
})
