import api from './api'

export const pacientesService = {
  /**
   * Obtener todos los pacientes
   */
  async getAll() {
    const response = await api.get('/pacientes')
    return response.data
  },

  /**
   * Obtener paciente por ID
   */
  async getById(id) {
    const response = await api.get(`/pacientes/${id}`)
    return response.data
  },

  /**
   * Crear nuevo paciente
   */
  async create(paciente) {
    const response = await api.post('/pacientes', paciente)
    return response.data
  },

  /**
   * Actualizar paciente
   */
  async update(id, paciente) {
    const response = await api.put(`/pacientes/${id}`, paciente)
    return response.data
  },

  /**
   * Eliminar paciente
   */
  async delete(id) {
    await api.delete(`/pacientes/${id}`)
  },

  /**
   * Buscar pacientes por nombre
   */
  async searchByName(nombre) {
    const response = await api.get('/pacientes/buscar/nombre', {
      params: { nombre },
    })
    return response.data
  },

  /**
   * Buscar por especie (PERRO/GATO)
   */
  async searchByEspecie(especie) {
    const response = await api.get('/pacientes/buscar/especie', {
      params: { especie },
    })
    return response.data
  },

  /**
   * Buscar por estado
   */
  async searchByEstado(estado) {
    const response = await api.get('/pacientes/buscar/estado', {
      params: { estado },
    })
    return response.data
  },

  /**
   * Obtener pacientes de un cliente
   */
  async getByCliente(clienteId) {
    const response = await api.get('/pacientes/buscar/cliente', {
      params: { clienteId },
    })
    return response.data
  },

  /**
   * Buscar por microchip
   */
  async searchByMicrochip(microchip) {
    const response = await api.get('/pacientes/buscar/microchip', {
      params: { microchip },
    })
    return response.data
  },
}
