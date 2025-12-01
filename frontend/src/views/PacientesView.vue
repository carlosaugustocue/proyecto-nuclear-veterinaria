<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12">
        <div class="d-flex justify-space-between align-center mb-6">
          <h1>Gestión de Mascotas</h1>
          <v-btn
            to="/pacientes/nuevo"
            color="primary"
            prepend-icon="mdi-plus"
          >
            Nueva Mascota
          </v-btn>
        </div>
      </v-col>
    </v-row>

    <!-- Filtros -->
    <v-row class="mb-6">
      <v-col cols="12" sm="6" md="3">
        <v-text-field
          v-model="filters.searchText"
          label="Buscar mascota"
          prepend-icon="mdi-magnify"
          clearable
          hint="Buscar por nombre, propietario o raza"
          persistent-hint
        ></v-text-field>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-select
          v-model="filters.especie"
          label="Especie"
          :items="especieOptions"
          item-title="title"
          item-value="value"
          clearable
        ></v-select>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-select
          v-model="filters.estado"
          label="Estado"
          :items="estadoOptions"
          item-title="title"
          item-value="value"
          clearable
        ></v-select>
      </v-col>

      <v-col cols="12" sm="6" md="3" v-if="filters.searchText || filters.especie || filters.estado">
        <v-btn
          color="secondary"
          variant="outlined"
          prepend-icon="mdi-filter-off"
          @click="limpiarFiltros"
          block
        >
          Limpiar Filtros
        </v-btn>
      </v-col>
    </v-row>

    <!-- Tabla de pacientes -->
    <v-row>
      <v-col cols="12">
        <v-card>
          <v-card-title class="d-flex justify-space-between align-center">
            <span>Lista de Mascotas</span>
            <div class="d-flex align-center gap-2">
              <v-chip 
                v-if="pacientes.length !== pacientesRaw.length" 
                color="info" 
                size="small"
                prepend-icon="mdi-filter"
              >
                {{ pacientes.length }} de {{ pacientesRaw.length }}
              </v-chip>
              <v-chip v-else color="success" size="small" prepend-icon="mdi-check">
                {{ pacientes.length }} total
              </v-chip>
            </div>
          </v-card-title>
          <v-data-table
            :headers="headers"
            :items="pacientes"
            :loading="loading"
            :sort-by="[{ key: 'nombre', order: 'asc' }]"
            class="elevation-1"
            :items-per-page="15"
          >
            <template v-slot:item.especieNombre="{ item }">
              <v-chip color="secondary" text-color="white">
                {{ item.especieNombre }}
              </v-chip>
            </template>

            <template v-slot:item.estadoNombre="{ item }">
              <v-chip :color="getEstadoColor(item.estadoNombre)" text-color="white">
                {{ item.estadoNombre }}
              </v-chip>
            </template>

            <template v-slot:item.actions="{ item }">
              <v-btn
                size="small"
                color="primary"
                variant="text"
                icon="mdi-eye"
                :to="`/pacientes/${item.id}`"
              ></v-btn>
              <v-btn
                size="small"
                color="info"
                variant="text"
                icon="mdi-pencil"
                :to="`/pacientes/${item.id}/editar`"
              ></v-btn>
              <v-btn
                size="small"
                color="error"
                variant="text"
                icon="mdi-delete"
                @click="deletePaciente(item.id)"
              ></v-btn>
            </template>

            <template v-slot:no-data>
              <v-alert 
                v-if="pacientesRaw.length === 0"
                type="info" 
                variant="tonal" 
                class="my-6"
              >
                <v-icon class="mr-2">mdi-information</v-icon>
                No hay mascotas registradas
              </v-alert>
              <v-alert 
                v-else
                type="warning" 
                variant="tonal" 
                class="my-6"
              >
                <v-icon class="mr-2">mdi-filter-remove</v-icon>
                No se encontraron mascotas con los filtros aplicados.
                <v-btn
                  color="primary"
                  variant="text"
                  size="small"
                  @click="limpiarFiltros"
                  class="mt-2"
                >
                  Limpiar Filtros
                </v-btn>
              </v-alert>
            </template>
          </v-data-table>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { reactive, onMounted, computed } from 'vue'
import { usePacientesStore } from '@/stores/pacientesStore'

const pacientesStore = usePacientesStore()

const filters = reactive({
  searchText: '',
  especie: null,
  estado: null,
})

// Opciones de especie (deben coincidir con el enum del backend)
const especieOptions = [
  { title: 'Perro', value: 'PERRO' },
  { title: 'Gato', value: 'GATO' },
  { title: 'Ave', value: 'AVE' },
  { title: 'Roedor', value: 'ROEDOR' },
  { title: 'Reptil', value: 'REPTIL' },
  { title: 'Otro', value: 'OTRO' }
]

// Opciones de estado (deben coincidir con el enum del backend)
const estadoOptions = [
  { title: 'Activo', value: 'ACTIVO' },
  { title: 'Inactivo', value: 'INACTIVO' },
  { title: 'Fallecido', value: 'FALLECIDO' }
]

const headers = [
  { title: 'Nombre', value: 'nombre' },
  { title: 'Propietario', value: 'clienteNombre' },
  { title: 'Especie', value: 'especieNombre' },
  { title: 'Raza', value: 'razaNombre' },
  { title: 'Fecha Nacimiento', value: 'fechaNacimiento' },
  { title: 'Estado', value: 'estadoNombre' },
  { title: 'Acciones', value: 'actions', sortable: false },
]

const loading = computed(() => pacientesStore.loading)
const pacientesRaw = computed(() => pacientesStore.pacientes)

// Aplicar filtros localmente
const pacientes = computed(() => {
  let filtered = pacientesRaw.value

  // Filtro por texto de búsqueda (nombre o propietario)
  if (filters.searchText && filters.searchText.trim()) {
    const searchLower = filters.searchText.toLowerCase().trim()
    filtered = filtered.filter(p => {
      const nombre = (p.nombre || '').toLowerCase()
      const clienteNombre = (p.clienteNombre || '').toLowerCase()
      const razaNombre = (p.razaNombre || '').toLowerCase()
      return nombre.includes(searchLower) || 
             clienteNombre.includes(searchLower) ||
             razaNombre.includes(searchLower)
    })
  }

  // Filtro por especie
  if (filters.especie) {
    filtered = filtered.filter(p => {
      // El backend devuelve el enum como string (PERRO, GATO, etc.)
      const especieEnum = String(p.especie || p.especieNombre || '').toUpperCase()
      const especieFilter = String(filters.especie).toUpperCase()
      
      // Comparar el enum directamente
      return especieEnum === especieFilter
    })
  }

  // Filtro por estado
  if (filters.estado) {
    filtered = filtered.filter(p => {
      // El backend devuelve el enum como string (ACTIVO, INACTIVO, FALLECIDO)
      // o el displayName (Activo, Inactivo, Fallecido) en estadoNombre
      const estadoEnum = String(p.estado || '').toUpperCase()
      const estadoNombre = String(p.estadoNombre || '').toUpperCase()
      const estadoFilter = String(filters.estado).toUpperCase()
      
      // Comparar tanto el enum como el displayName
      return estadoEnum === estadoFilter || estadoNombre === estadoFilter
    })
  }

  return filtered
})

const fetchPacientes = async () => {
  try {
    // Cargar todos los pacientes (sin filtros, el filtrado se hace en el frontend)
    await pacientesStore.fetchPacientes()
  } catch (error) {
    console.error('Error al cargar pacientes:', error)
  }
}

const getEstadoColor = (estado) => {
  const colores = {
    'Activo': 'success',
    'Inactivo': 'warning',
    'Fallecido': 'error',
  }
  return colores[estado] || 'grey'
}

const deletePaciente = async (id) => {
  if (confirm('¿Estás seguro de que quieres eliminar este paciente?')) {
    try {
      await pacientesStore.deletePaciente(id)
      // Recargar pacientes después de eliminar
      await fetchPacientes()
    } catch (error) {
      console.error('Error al eliminar paciente:', error)
    }
  }
}

const limpiarFiltros = () => {
  filters.searchText = ''
  filters.especie = null
  filters.estado = null
}

onMounted(() => {
  fetchPacientes()
})
</script>

<style scoped>
</style>
