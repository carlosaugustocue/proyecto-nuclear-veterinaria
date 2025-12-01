<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12">
        <div class="d-flex justify-space-between align-center mb-6">
          <h1>Gestión de Clientes</h1>
          <v-btn
            to="/clientes/nuevo"
            color="primary"
            prepend-icon="mdi-plus"
          >
            Nuevo Cliente
          </v-btn>
        </div>
      </v-col>
    </v-row>

    <!-- Filtros -->
    <v-row class="mb-6">
      <v-col cols="12" sm="6" md="3">
        <v-text-field
          v-model="filters.searchText"
          label="Buscar cliente"
          prepend-icon="mdi-magnify"
          clearable
          @update:model-value="fetchClientes"
        ></v-text-field>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-text-field
          v-model="filters.ciudad"
          label="Ciudad"
          clearable
          @update:model-value="fetchClientes"
        ></v-text-field>
      </v-col>
    </v-row>

    <!-- Tabla de clientes -->
    <v-row>
      <v-col cols="12">
        <v-card>
          <v-data-table
            :headers="headers"
            :items="clientes"
            :loading="loading"
            :sort-by="[{ key: 'nombre', order: 'asc' }]"
            class="elevation-1"
          >
            <!-- Mascotas -->
            <template v-slot:item.mascotas="{ item }">
              <div v-if="item.pacientes && item.pacientes.length > 0">
                <v-chip
                  v-for="(paciente, index) in item.pacientes.slice(0, 3)"
                  :key="paciente.id || index"
                  size="small"
                  color="primary"
                  variant="outlined"
                  class="mr-1 mb-1"
                >
                  <v-icon size="x-small" class="mr-1">mdi-paw</v-icon>
                  {{ paciente.nombre }}
                </v-chip>
                <v-chip
                  v-if="item.pacientes.length > 3"
                  size="small"
                  color="grey"
                  variant="outlined"
                >
                  +{{ item.pacientes.length - 3 }} más
                </v-chip>
                <div v-if="item.cantidadPacientesActivos !== undefined" class="text-caption text-grey mt-1">
                  {{ item.cantidadPacientesActivos }} activa{{ item.cantidadPacientesActivos !== 1 ? 's' : '' }}
                </div>
              </div>
              <span v-else class="text-grey">Sin mascotas</span>
            </template>

            <template v-slot:item.actions="{ item }">
              <v-btn
                size="small"
                color="primary"
                variant="text"
                icon="mdi-eye"
                :to="`/clientes/${item.id}`"
              ></v-btn>
              <v-btn
                size="small"
                color="info"
                variant="text"
                icon="mdi-pencil"
                :to="`/clientes/${item.id}/editar`"
              ></v-btn>
              <v-btn
                size="small"
                color="error"
                variant="text"
                icon="mdi-delete"
                @click="deleteCliente(item.id)"
              ></v-btn>
            </template>

            <!-- Nombre -->
            <template v-slot:item.nombreCompleto="{ item }">
              <div class="d-flex align-center">
                <v-avatar color="blue" size="32" class="mr-3">
                  <v-icon color="white" size="small">mdi-account</v-icon>
                </v-avatar>
                <div>
                  <div class="font-weight-medium">{{ item.nombreCompleto || `${item.nombre || ''} ${item.apellido || ''}`.trim() }}</div>
                  <div v-if="item.dni" class="text-caption text-grey">Documento de Identidad: {{ item.dni }}</div>
                </div>
              </div>
            </template>

            <template v-slot:no-data>
              <v-alert type="info" variant="tonal" class="my-6">
                <v-icon class="mr-2">mdi-information</v-icon>
                No hay clientes registrados
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
import { useClientesStore } from '@/stores/clientesStore'

const clientesStore = useClientesStore()

const filters = reactive({
  searchText: '',
  ciudad: null,
})

const headers = [
  { title: 'Nombre', value: 'nombreCompleto', width: '200' },
  { title: 'Email', value: 'email', width: '200' },
  { title: 'Teléfono', value: 'telefono', width: '150' },
  { title: 'Ciudad', value: 'ciudad', width: '120' },
  { title: 'Mascotas', value: 'mascotas', width: '200', sortable: false },
  { title: 'Acciones', value: 'actions', sortable: false, width: '150' },
]

const loading = computed(() => clientesStore.loading)
const clientes = computed(() => clientesStore.clientes)

const fetchClientes = async () => {
  try {
    await clientesStore.fetchClientes(filters)
  } catch (error) {
    console.error('Error al cargar clientes:', error)
  }
}

const deleteCliente = async (id) => {
  if (confirm('¿Estás seguro de que quieres eliminar este cliente?')) {
    try {
      await clientesStore.deleteCliente(id)
    } catch (error) {
      console.error('Error al eliminar cliente:', error)
    }
  }
}

onMounted(() => {
  fetchClientes()
})
</script>

<style scoped>
</style>
