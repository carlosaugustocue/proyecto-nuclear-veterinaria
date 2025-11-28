<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12">
        <div class="d-flex justify-space-between align-center mb-6">
          <h1>Gestión de Pacientes</h1>
          <v-btn
            to="/pacientes/nuevo"
            color="primary"
            prepend-icon="plus"
          >
            Nuevo Paciente
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
          @update:model-value="fetchPacientes"
        ></v-text-field>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-select
          v-model="filters.especie"
          label="Especie"
          :items="especieOptions"
          clearable
          @update:model-value="fetchPacientes"
        ></v-select>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-select
          v-model="filters.estado"
          label="Estado"
          :items="estadoOptions"
          clearable
          @update:model-value="fetchPacientes"
        ></v-select>
      </v-col>
    </v-row>

    <!-- Tabla de pacientes -->
    <v-row>
      <v-col cols="12">
        <v-card>
          <v-data-table
            :headers="headers"
            :items="pacientes"
            :loading="loading"
            :sort-by="[{ key: 'nombre', order: 'asc' }]"
            class="elevation-1"
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
                icon="eye"
                :to="`/pacientes/${item.id}`"
              ></v-btn>
              <v-btn
                size="small"
                color="info"
                variant="text"
                icon="edit"
                :to="`/pacientes/${item.id}/editar`"
              ></v-btn>
              <v-btn
                size="small"
                color="error"
                variant="text"
                icon="delete"
                @click="deletePaciente(item.id)"
              ></v-btn>
            </template>

            <template v-slot:no-data>
              <v-alert type="info" text class="my-6">
                No hay pacientes registrados
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

const especieOptions = ['Perro', 'Gato', 'Ave', 'Roedor', 'Otro']
const estadoOptions = ['Activo', 'Inactivo', 'Fallecido']

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
const pacientes = computed(() => pacientesStore.pacientes)

const fetchPacientes = async () => {
  try {
    await pacientesStore.fetchPacientes(filters)
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
    } catch (error) {
      console.error('Error al eliminar paciente:', error)
    }
  }
}

onMounted(() => {
  fetchPacientes()
})
</script>

<style scoped>
</style>
