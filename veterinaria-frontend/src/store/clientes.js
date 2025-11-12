import { defineStore } from 'pinia'
import { ref } from 'vue'
import { clientesService } from '@/services/clientes.service'

export const useClientesStore = defineStore('clientes', () => {
  // State
  const clientes = ref([])
  const currentCliente = ref(null)
  const loading = ref(false)

  // Actions
  async function fetchAll() {
    loading.value = true
    try {
      clientes.value = await clientesService.getAll()
    } catch (error) {
      console.error('Error al cargar clientes:', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  async function fetchById(id) {
    loading.value = true
    try {
      currentCliente.value = await clientesService.getById(id)
      return currentCliente.value
    } catch (error) {
      console.error('Error al cargar cliente:', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  async function create(cliente) {
    loading.value = true
    try {
      const newCliente = await clientesService.create(cliente)
      clientes.value.push(newCliente)
      return newCliente
    } catch (error) {
      console.error('Error al crear cliente:', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  async function update(id, cliente) {
    loading.value = true
    try {
      const updated = await clientesService.update(id, cliente)
      const index = clientes.value.findIndex((c) => c.id === id)
      if (index !== -1) {
        clientes.value[index] = updated
      }
      return updated
    } catch (error) {
      console.error('Error al actualizar cliente:', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  async function remove(id) {
    loading.value = true
    try {
      await clientesService.delete(id)
      clientes.value = clientes.value.filter((c) => c.id !== id)
    } catch (error) {
      console.error('Error al eliminar cliente:', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  async function search(nombre) {
    loading.value = true
    try {
      clientes.value = await clientesService.searchByName(nombre)
    } catch (error) {
      console.error('Error al buscar clientes:', error)
      throw error
    } finally {
      loading.value = false
    }
  }

  return {
    // State
    clientes,
    currentCliente,
    loading,
    // Actions
    fetchAll,
    fetchById,
    create,
    update,
    remove,
    search,
  }
})
