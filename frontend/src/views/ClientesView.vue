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

            <template v-slot:no-data>
              <v-alert type="info" text class="my-6">
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
  { title: 'Nombre', value: 'nombre' },
  { title: 'Email', value: 'email' },
  { title: 'Teléfono', value: 'telefono' },
  { title: 'Ciudad', value: 'ciudad' },
  { title: 'Mascotas', value: 'mascotas' },
  { title: 'Acciones', value: 'actions', sortable: false },
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
