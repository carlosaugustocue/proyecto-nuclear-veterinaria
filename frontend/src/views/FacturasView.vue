<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12">
        <div class="d-flex justify-space-between align-center mb-6">
          <h1>Gestión de Facturas</h1>
          <v-btn
            to="/facturas/nueva"
            color="primary"
            prepend-icon="mdi-plus"
          >
            Nueva Factura
          </v-btn>
        </div>
      </v-col>
    </v-row>

    <!-- Filtros -->
    <v-row class="mb-6">
      <v-col cols="12" sm="6" md="3">
        <v-text-field
          v-model="filters.searchText"
          label="Buscar por número o cliente"
          prepend-icon="mdi-magnify"
          clearable
          @update:model-value="fetchFacturas"
        ></v-text-field>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-select
          v-model="filters.estado"
          label="Estado"
          :items="estadoOptions"
          clearable
          @update:model-value="fetchFacturas"
        ></v-select>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-text-field
          v-model="filters.fechaInicio"
          label="Fecha Inicio"
          type="date"
          @update:model-value="fetchFacturas"
        ></v-text-field>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-text-field
          v-model="filters.fechaFin"
          label="Fecha Fin"
          type="date"
          @update:model-value="fetchFacturas"
        ></v-text-field>
      </v-col>
    </v-row>

    <!-- Tabla de facturas -->
    <v-row>
      <v-col cols="12">
        <v-card>
          <v-data-table
            :headers="headers"
            :items="facturas"
            :loading="loading"
            :sort-by="[{ key: 'numero', order: 'asc' }]"
            class="elevation-1"
          >
            <template v-slot:item.total="{ item }">
              <span class="font-weight-bold">$ {{ formatCurrency(item.total) }}</span>
            </template>

            <template v-slot:item.estado="{ item }">
              <v-chip :color="getEstadoColor(item.estado)" text-color="white">
                {{ item.estado }}
              </v-chip>
            </template>

            <template v-slot:item.actions="{ item }">
              <v-btn
                size="small"
                color="primary"
                variant="text"
                icon="mdi-eye"
                :to="`/facturas/${item.id}`"
              ></v-btn>
              <v-btn
                size="small"
                color="info"
                variant="text"
                icon="mdi-pencil"
                :to="`/facturas/${item.id}/editar`"
              ></v-btn>
              <v-btn
                size="small"
                color="error"
                variant="text"
                icon="mdi-delete"
                @click="deleteFactura(item.id)"
              ></v-btn>
            </template>

            <template v-slot:no-data>
              <v-alert type="info" text class="my-6">
                No hay facturas registradas
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
import { useFacturasStore } from '@/stores/facturasStore'

const facturasStore = useFacturasStore()

const loading = computed(() => facturasStore.loading)
const facturas = computed(() => facturasStore.facturas)

const filters = reactive({
  searchText: '',
  estado: null,
  fechaInicio: null,
  fechaFin: null,
})

const estadoOptions = ['Pendiente', 'Pagada', 'Anulada', 'Vencida']

const headers = [
  { title: 'Número', value: 'numero' },
  { title: 'Cliente', value: 'cliente' },
  { title: 'Fecha', value: 'fecha' },
  { title: 'Total', value: 'total' },
  { title: 'Estado', value: 'estado' },
  { title: 'Acciones', value: 'actions', sortable: false },
]

const fetchFacturas = async () => {
  try {
    await facturasStore.fetchFacturas(filters)
  } catch (error) {
    console.error('Error al cargar facturas:', error)
  }
}

const getEstadoColor = (estado) => {
  const colores = {
    'Pendiente': 'warning',
    'Pagada': 'success',
    'Anulada': 'error',
    'Vencida': 'error',
  }
  return colores[estado] || 'grey'
}

const formatCurrency = (value) => {
  return new Intl.NumberFormat('es-CO', {
    minimumFractionDigits: 0,
  }).format(value)
}

const deleteFactura = async (id) => {
  if (confirm('¿Estás seguro de que quieres eliminar esta factura?')) {
    try {
      await facturasStore.deleteFactura(id)
    } catch (error) {
      console.error('Error al eliminar factura:', error)
    }
  }
}

onMounted(() => {
  fetchFacturas()
})
</script>

<style scoped>
</style>
