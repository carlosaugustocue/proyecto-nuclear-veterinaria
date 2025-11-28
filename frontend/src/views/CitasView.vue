<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12">
        <div class="d-flex justify-space-between align-center mb-6">
          <h1>Gestión de Citas</h1>
          <v-btn
            to="/citas/nueva"
            color="primary"
            prepend-icon="plus"
          >
            Nueva Cita
          </v-btn>
        </div>
      </v-col>
    </v-row>

    <!-- Filtros -->
    <v-row class="mb-6">
      <v-col cols="12" sm="6" md="3">
        <v-text-field
          v-model="filters.searchText"
          label="Buscar paciente"
          prepend-icon="search"
          clearable
          @update:model-value="fetchCitas"
        ></v-text-field>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-select
          v-model="filters.estado"
          label="Estado"
          :items="estadoOptions"
          clearable
          @update:model-value="fetchCitas"
        ></v-select>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-text-field
          v-model="filters.fecha"
          label="Fecha"
          type="date"
          @update:model-value="fetchCitas"
        ></v-text-field>
      </v-col>
    </v-row>

    <!-- Tabla de citas -->
    <v-row>
      <v-col cols="12">
        <v-card>
          <v-data-table
            :headers="headers"
            :items="citas"
            :loading="loading"
            :sort-by="[{ key: 'fecha', order: 'asc' }]"
            class="elevation-1"
          >
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
                icon="eyeDropper"
                :to="`/citas/${item.id}`"
              ></v-btn>
              <v-btn
                size="small"
                color="info"
                variant="text"
                icon="edit"
              ></v-btn>
              <v-btn
                size="small"
                color="error"
                variant="text"
                icon="delete"
                @click="deleteCita(item.id)"
              ></v-btn>
            </template>

            <template v-slot:no-data>
              <v-alert type="info" text class="my-6">
                No hay citas registradas
              </v-alert>
            </template>
          </v-data-table>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useCitasStore } from '@/stores/citasStore'
import { useNotification } from '@/composables/useNotification'

const citasStore = useCitasStore()
const { showSuccess, showError } = useNotification()

const filters = reactive({
  searchText: '',
  estado: null,
  fecha: null,
})

const estadoOptions = [
  'Programada',
  'Confirmada',
  'En Progreso',
  'Completada',
  'Cancelada',
]

const headers = [
  { title: 'Paciente', value: 'pacienteNombre' },
  { title: 'Cliente', value: 'clienteNombre' },
  { title: 'Fecha', value: 'fecha' },
  { title: 'Hora', value: 'hora' },
  { title: 'Veterinario', value: 'veterinarioNombre' },
  { title: 'Estado', value: 'estadoNombre' },
  { title: 'Acciones', value: 'actions', sortable: false },
]

const loading = computed(() => citasStore.loading)
const citas = computed(() => citasStore.citas)

const fetchCitas = async () => {
  try {
    await citasStore.fetchCitas(filters)
  } catch (error) {
    console.error('Error al cargar citas:', error)
    showError(error.userMessage || 'Error al cargar las citas')
  }
}

const getEstadoColor = (estado) => {
  const colores = {
    'Programada': 'info',
    'Confirmada': 'success',
    'En Progreso': 'warning',
    'Completada': 'success',
    'Cancelada': 'error',
  }
  return colores[estado] || 'grey'
}

const deleteCita = async (id) => {
  if (confirm('¿Estás seguro de que quieres eliminar esta cita?')) {
    try {
      await citasStore.deleteCita(id)
      showSuccess('Cita eliminada exitosamente')
    } catch (error) {
      console.error('Error al eliminar cita:', error)
      showError(error.userMessage || 'Error al eliminar la cita')
    }
  }
}

onMounted(() => {
  fetchCitas()
})
</script>

<style scoped>
</style>
