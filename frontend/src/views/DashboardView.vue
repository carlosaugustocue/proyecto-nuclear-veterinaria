<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12">
        <h1 class="mb-6">Dashboard</h1>
      </v-col>
    </v-row>

    <!-- Cards de estadísticas -->
    <v-row>
      <v-col cols="12" sm="6" md="3">
        <v-card class="bg-blue-lighten-5">
          <v-card-text>
            <div class="text-h5 font-weight-bold text-blue">{{ citasProximas }}</div>
            <div class="text-subtitle2 text-grey">Citas Próximas</div>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-card class="bg-green-lighten-5">
          <v-card-text>
            <div class="text-h5 font-weight-bold text-green">{{ pacientesActivos }}</div>
            <div class="text-subtitle2 text-grey">Pacientes Activos</div>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-card class="bg-orange-lighten-5">
          <v-card-text>
            <div class="text-h5 font-weight-bold text-orange">{{ facturenPendientes }}</div>
            <div class="text-subtitle2 text-grey">Facturas Pendientes</div>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-card class="bg-purple-lighten-5">
          <v-card-text>
            <div class="text-h5 font-weight-bold text-purple">{{ clientesTotal }}</div>
            <div class="text-subtitle2 text-grey">Clientes</div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <!-- Acciones rápidas -->
    <v-row class="mt-6">
      <v-col cols="12">
        <h2 class="mb-4">Acciones Rápidas</h2>
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="12" sm="6" md="3">
        <v-btn
          to="/citas/nueva"
          color="primary"
          block
          size="large"
          class="text-none"
        >
          <v-icon class="mr-2">mdi-plus</v-icon>
          Nueva Cita
        </v-btn>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-btn
          to="/pacientes/nuevo"
          color="success"
          block
          size="large"
          class="text-none"
        >
          <v-icon class="mr-2">mdi-plus</v-icon>
          Nuevo Paciente
        </v-btn>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-btn
          to="/clientes/nuevo"
          color="info"
          block
          size="large"
          class="text-none"
        >
          <v-icon class="mr-2">mdi-plus</v-icon>
          Nuevo Cliente
        </v-btn>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-btn
          to="/facturas/nueva"
          color="warning"
          block
          size="large"
          class="text-none"
        >
          <v-icon class="mr-2">mdi-plus</v-icon>
          Nueva Factura
        </v-btn>
      </v-col>
    </v-row>

    <!-- Últimas citas -->
    <v-row class="mt-6">
      <v-col cols="12">
        <v-card>
          <v-card-title>
            Próximas Citas
            <v-spacer></v-spacer>
            <v-btn to="/citas" text color="primary">Ver todas</v-btn>
          </v-card-title>
          <v-divider></v-divider>
          <v-card-text>
            <v-list v-if="ultimasCitas.length > 0">
              <v-list-item v-for="cita in ultimasCitas" :key="cita.id">
                <template v-slot:prepend>
                  <v-icon color="primary">mdi-calendar</v-icon>
                </template>
                <v-list-item-title>{{ cita.pacienteNombre }}</v-list-item-title>
                <v-list-item-subtitle>{{ cita.fecha }} - {{ cita.hora }}</v-list-item-subtitle>
                <template v-slot:append>
                  <v-chip :color="getEstadoColor(cita.estadoNombre)" text-color="white">
                    {{ cita.estadoNombre }}
                  </v-chip>
                </template>
              </v-list-item>
            </v-list>
            <v-alert v-else type="info" text>
              No hay citas próximas
            </v-alert>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useCitasStore } from '@/stores/citasStore'
import { usePacientesStore } from '@/stores/pacientesStore'
import { useClientesStore } from '@/stores/clientesStore'
import { useFacturasStore } from '@/stores/facturasStore'

const citasStore = useCitasStore()
const pacientesStore = usePacientesStore()
const clientesStore = useClientesStore()
const facturasStore = useFacturasStore()

const citasProximas = computed(() => citasStore.citasProximas)
const pacientesActivos = computed(() => pacientesStore.pacientesActivos)
const facturenPendientes = computed(() => facturasStore.facturasPendientes)
const clientesTotal = computed(() => clientesStore.clientesCount)
const ultimasCitas = computed(() => citasStore.citas.slice(0, 3))

onMounted(async () => {
  try {
    await Promise.all([
      citasStore.fetchCitas(),
      pacientesStore.fetchPacientes(),
      clientesStore.fetchClientes(),
      facturasStore.fetchFacturas(),
    ])
  } catch (error) {
    console.error('Error al cargar datos del dashboard:', error)
  }
})

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
</script>

<style scoped>
</style>
