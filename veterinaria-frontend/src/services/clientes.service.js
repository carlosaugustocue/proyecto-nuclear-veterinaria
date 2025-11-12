import api from './api'

export const clientesService = {
  /**
   * Obtener todos los clientes
   */
  async getAll() {
    const response = await api.get('/clientes')
    return response.data
  },

  /**
   * Obtener cliente por ID
   */
  async getById(id) {
    const response = await api.get(`/clientes/${id}`)
    return response.data
  },

  /**
   * Crear nuevo cliente
   */
  async create(cliente) {
    const response = await api.post('/clientes', cliente)
    return response.data
  },

  /**
   * Actualizar cliente
   */
  async update(id, cliente) {
    const response = await api.put(`/clientes/${id}`, cliente)
    return response.data
  },

  /**
   * Eliminar cliente
   */
  async delete(id) {
    await api.delete(`/clientes/${id}`)
  },

  /**
   * Buscar clientes por nombre
   */
  async searchByName(nombre) {
    const response = await api.get('/clientes/buscar', {
      params: { nombre },
    })
    return response.data
  },

  /**
   * Obtener pacientes de un cliente
   */
  async getPacientes(clienteId) {
    const response = await api.get(`/clientes/${clienteId}/pacientes`)
    return response.data
  },

  /**
   * Activar/desactivar cliente
   */
  async toggleActive(id) {
    const response = await api.patch(`/clientes/${id}/toggle-active`)
    return response.data
  },
}
