<template>
  <v-container fluid class="pa-4 pa-md-6">
    <!-- Header con saludo -->
    <v-row class="mb-4">
      <v-col cols="12">
        <div class="d-flex align-center justify-space-between flex-wrap">
          <div>
            <h1 class="text-h4 font-weight-bold mb-1">Dashboard</h1>
            <p class="text-subtitle-1 text-grey">{{ saludo }}</p>
          </div>
          <v-chip color="primary" variant="flat" size="large" class="mt-2">
            <v-icon start>mdi-calendar-today</v-icon>
            {{ fechaActual }}
          </v-chip>
        </div>
      </v-col>
    </v-row>

    <!-- Cards de estadísticas principales -->
    <v-row class="mb-4">
      <v-col cols="12" sm="6" md="3">
        <v-card 
          class="stat-card elevation-3" 
          :class="{'stat-card-primary': true}"
          @click="$router.push('/citas')"
          style="cursor: pointer;"
        >
          <v-card-text class="pa-4">
            <div class="d-flex align-center justify-space-between">
              <div>
                <div class="text-h4 font-weight-bold mb-1">{{ citasProximas }}</div>
                <div class="text-subtitle-2 text-grey-darken-1">Citas Próximas</div>
                <div class="text-caption text-grey mt-1">
                  <v-icon size="small" class="mr-1">mdi-trending-up</v-icon>
                  {{ citasHoy }} hoy
                </div>
              </div>
              <v-avatar size="64" color="primary" variant="flat">
                <v-icon size="32" color="white">mdi-calendar-clock</v-icon>
              </v-avatar>
            </div>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-card 
          class="stat-card elevation-3" 
          :class="{'stat-card-success': true}"
          @click="$router.push('/pacientes')"
          style="cursor: pointer;"
        >
          <v-card-text class="pa-4">
            <div class="d-flex align-center justify-space-between">
              <div>
                <div class="text-h4 font-weight-bold mb-1">{{ pacientesActivos }}</div>
                <div class="text-subtitle-2 text-grey-darken-1">Mascotas Activas</div>
                <div class="text-caption text-grey mt-1">
                  <v-icon size="small" class="mr-1">mdi-paw</v-icon>
                  {{ pacientesTotal }} total
                </div>
              </div>
              <v-avatar size="64" color="success" variant="flat">
                <v-icon size="32" color="white">mdi-paw</v-icon>
              </v-avatar>
            </div>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-card 
          class="stat-card elevation-3" 
          :class="{'stat-card-warning': true}"
          @click="$router.push('/facturas')"
          style="cursor: pointer;"
        >
          <v-card-text class="pa-4">
            <div class="d-flex align-center justify-space-between">
              <div>
                <div class="text-h4 font-weight-bold mb-1">{{ facturenPendientes }}</div>
                <div class="text-subtitle-2 text-grey-darken-1">Facturas Pendientes</div>
                <div class="text-caption text-grey mt-1">
                  <v-icon size="small" class="mr-1">mdi-currency-usd</v-icon>
                  ${{ formatCurrency(facturasTotal) }}
                </div>
              </div>
              <v-avatar size="64" color="warning" variant="flat">
                <v-icon size="32" color="white">mdi-receipt</v-icon>
              </v-avatar>
            </div>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-card 
          class="stat-card elevation-3" 
          :class="{'stat-card-info': true}"
          @click="$router.push('/clientes')"
          style="cursor: pointer;"
        >
          <v-card-text class="pa-4">
            <div class="d-flex align-center justify-space-between">
              <div>
                <div class="text-h4 font-weight-bold mb-1">{{ clientesTotal }}</div>
                <div class="text-subtitle-2 text-grey-darken-1">Clientes</div>
                <div class="text-caption text-grey mt-1">
                  <v-icon size="small" class="mr-1">mdi-account-group</v-icon>
                  Registrados
                </div>
              </div>
              <v-avatar size="64" color="info" variant="flat">
                <v-icon size="32" color="white">mdi-account-group</v-icon>
              </v-avatar>
            </div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <!-- Segunda fila de estadísticas -->
    <v-row class="mb-4">
      <v-col cols="12" sm="6" md="3">
        <v-card class="elevation-2">
          <v-card-text class="pa-4">
            <div class="d-flex align-center">
              <v-icon color="success" size="32" class="mr-3">mdi-check-circle</v-icon>
              <div>
                <div class="text-h6 font-weight-bold">{{ citasCompletadas }}</div>
                <div class="text-caption text-grey">Completadas</div>
              </div>
            </div>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-card class="elevation-2">
          <v-card-text class="pa-4">
            <div class="d-flex align-center">
              <v-icon color="info" size="32" class="mr-3">mdi-calendar-check</v-icon>
              <div>
                <div class="text-h6 font-weight-bold">{{ citasConfirmadas }}</div>
                <div class="text-caption text-grey">Confirmadas</div>
              </div>
            </div>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-card class="elevation-2">
          <v-card-text class="pa-4">
            <div class="d-flex align-center">
              <v-icon color="warning" size="32" class="mr-3">mdi-progress-clock</v-icon>
              <div>
                <div class="text-h6 font-weight-bold">{{ citasEnProgreso }}</div>
                <div class="text-caption text-grey">En Progreso</div>
              </div>
            </div>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-card class="elevation-2">
          <v-card-text class="pa-4">
            <div class="d-flex align-center">
              <v-icon color="error" size="32" class="mr-3">mdi-cancel</v-icon>
              <div>
                <div class="text-h6 font-weight-bold">{{ citasCanceladas }}</div>
                <div class="text-caption text-grey">Canceladas</div>
              </div>
            </div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <!-- Citas de hoy y próximas citas -->
    <v-row class="mb-4">
      <!-- Citas de Hoy -->
      <v-col cols="12" md="6">
        <v-card class="elevation-3" :class="{'border-primary': citasDeHoy.length > 0}">
          <v-card-title class="bg-primary text-white d-flex align-center">
            <v-icon class="mr-2">mdi-calendar-today</v-icon>
            Citas de Hoy
            <v-spacer></v-spacer>
            <v-chip color="white" text-color="primary" size="small">
              {{ citasDeHoy.length }}
            </v-chip>
          </v-card-title>
          <v-card-text class="pa-0">
            <v-list v-if="citasDeHoy.length > 0" lines="two">
              <v-list-item
                v-for="cita in citasDeHoy.slice(0, 5)"
                :key="cita.id"
                :to="`/citas/${cita.id}`"
                class="cursor-pointer"
              >
                <template v-slot:prepend>
                  <v-avatar :color="getEstadoColor(obtenerEstado(cita))" size="40">
                    <v-icon color="white" size="20">mdi-paw</v-icon>
                  </v-avatar>
                </template>
                <v-list-item-title class="font-weight-medium">
                  {{ cita.pacienteNombre || 'Sin nombre' }}
                </v-list-item-title>
                <v-list-item-subtitle>
                  <div class="d-flex align-center mt-1">
                    <v-icon size="small" class="mr-1">mdi-clock-outline</v-icon>
                    <span>{{ formatTime(cita.hora) }}</span>
                    <v-chip
                      :color="getEstadoColor(obtenerEstado(cita))"
                      size="x-small"
                      class="ml-2"
                      text-color="white"
                    >
                      {{ formatearEstado(obtenerEstado(cita)) }}
                    </v-chip>
                  </div>
                </v-list-item-subtitle>
              </v-list-item>
            </v-list>
            <v-alert v-else type="info" variant="tonal" class="ma-4">
              <v-icon class="mr-2">mdi-information</v-icon>
              No hay citas programadas para hoy
            </v-alert>
          </v-card-text>
          <v-card-actions v-if="citasDeHoy.length > 5">
            <v-spacer></v-spacer>
            <v-btn to="/citas" variant="text" color="primary" size="small">
              Ver todas
              <v-icon end>mdi-arrow-right</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>

      <!-- Próximas Citas -->
      <v-col cols="12" md="6">
        <v-card class="elevation-3">
          <v-card-title class="d-flex align-center">
            <v-icon class="mr-2" color="primary">mdi-calendar-clock</v-icon>
            Próximas Citas
            <v-spacer></v-spacer>
            <v-btn to="/citas" variant="text" color="primary" size="small" prepend-icon="mdi-arrow-right">
              Ver todas
            </v-btn>
          </v-card-title>
          <v-divider></v-divider>
          <v-card-text class="pa-0">
            <v-list v-if="ultimasCitas.length > 0" lines="two">
              <v-list-item
                v-for="cita in ultimasCitas"
                :key="cita.id"
                :to="`/citas/${cita.id}`"
                class="cursor-pointer"
              >
                <template v-slot:prepend>
                  <v-avatar color="primary" size="48">
                    <v-icon color="white">mdi-paw</v-icon>
                  </v-avatar>
                </template>

                <v-list-item-title class="font-weight-bold mb-1">
                  {{ cita.pacienteNombre || 'Sin nombre' }}
                </v-list-item-title>

                <v-list-item-subtitle class="text-wrap">
                  <div class="d-flex flex-column gap-1 mt-1">
                    <div class="d-flex align-center">
                      <v-icon size="small" class="mr-1">mdi-account</v-icon>
                      <span class="text-body-2">{{ cita.clienteNombre || 'Cliente no especificado' }}</span>
                    </div>
                    <div class="d-flex align-center">
                      <v-icon size="small" class="mr-1">mdi-calendar</v-icon>
                      <span class="text-body-2">{{ formatDate(cita.fecha) }} a las {{ formatTime(cita.hora) }}</span>
                    </div>
                    <div v-if="cita.motivo" class="d-flex align-center">
                      <v-icon size="small" class="mr-1">mdi-text</v-icon>
                      <span class="text-body-2 text-truncate">{{ cita.motivo }}</span>
                    </div>
                  </div>
                </v-list-item-subtitle>

                <template v-slot:append>
                  <v-chip
                    :color="getEstadoColor(obtenerEstado(cita))"
                    size="small"
                    text-color="white"
                    class="font-weight-bold"
                  >
                    <v-icon size="x-small" class="mr-1">{{ getEstadoIcon(obtenerEstado(cita)) }}</v-icon>
                    {{ formatearEstado(obtenerEstado(cita)) }}
                  </v-chip>
                </template>
              </v-list-item>
            </v-list>
            <v-alert v-else type="info" variant="tonal" class="ma-4">
              <v-icon class="mr-2">mdi-information</v-icon>
              No hay citas próximas programadas
            </v-alert>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <!-- Acciones rápidas y estadísticas adicionales -->
    <v-row>
      <!-- Acciones Rápidas -->
      <v-col cols="12" md="4">
        <v-card class="elevation-3">
          <v-card-title class="bg-secondary text-white">
            <v-icon class="mr-2">mdi-lightning-bolt</v-icon>
            Acciones Rápidas
          </v-card-title>
          <v-card-text class="pa-3">
            <v-list lines="one" density="compact">
              <v-list-item
                v-for="accion in accionesRapidas"
                :key="accion.to"
                :to="accion.to"
                class="mb-2 rounded"
                style="cursor: pointer;"
              >
                <template v-slot:prepend>
                  <v-avatar :color="accion.color" size="40" variant="flat">
                    <v-icon color="white">{{ accion.icon }}</v-icon>
                  </v-avatar>
                </template>
                <v-list-item-title class="font-weight-medium">{{ accion.title }}</v-list-item-title>
                <template v-slot:append>
                  <v-icon>mdi-chevron-right</v-icon>
                </template>
              </v-list-item>
            </v-list>
          </v-card-text>
        </v-card>
      </v-col>

      <!-- Distribución de Citas por Estado -->
      <v-col cols="12" md="4">
        <v-card class="elevation-3">
          <v-card-title class="d-flex align-center">
            <v-icon class="mr-2" color="primary">mdi-chart-pie</v-icon>
            Distribución de Citas
          </v-card-title>
          <v-divider></v-divider>
          <v-card-text class="pa-4">
            <div v-if="citasStore.citas.length > 0">
              <div
                v-for="(item, index) in distribucionCitas"
                :key="index"
                class="mb-3"
              >
                <div class="d-flex justify-space-between align-center mb-1">
                  <div class="d-flex align-center">
                    <v-icon :color="item.color" size="small" class="mr-2">{{ item.icon }}</v-icon>
                    <span class="text-body-2 font-weight-medium">{{ item.label }}</span>
                  </div>
                  <div class="d-flex align-center">
                    <span class="text-body-2 font-weight-bold mr-2">{{ item.count }}</span>
                    <span class="text-caption text-grey">({{ item.percentage }}%)</span>
                  </div>
                </div>
                <v-progress-linear
                  :model-value="item.percentage"
                  :color="item.color"
                  height="8"
                  rounded
                ></v-progress-linear>
              </div>
            </div>
            <v-alert v-else type="info" variant="tonal">
              No hay datos de citas disponibles
            </v-alert>
          </v-card-text>
        </v-card>
      </v-col>

      <!-- Facturas Recientes -->
      <v-col cols="12" md="4">
        <v-card class="elevation-3">
          <v-card-title class="d-flex align-center">
            <v-icon class="mr-2" color="warning">mdi-receipt</v-icon>
            Facturas Recientes
            <v-spacer></v-spacer>
            <v-btn to="/facturas" variant="text" color="primary" size="small">
              Ver todas
            </v-btn>
          </v-card-title>
          <v-divider></v-divider>
          <v-card-text class="pa-0">
            <v-list v-if="facturasRecientes.length > 0" lines="two" density="compact">
              <v-list-item
                v-for="factura in facturasRecientes"
                :key="factura.id"
                :to="`/facturas/${factura.id}`"
                class="cursor-pointer"
              >
                <template v-slot:prepend>
                  <v-avatar :color="getFacturaColor(factura)" size="36" variant="flat">
                    <v-icon color="white" size="18">mdi-receipt</v-icon>
                  </v-avatar>
                </template>
                <v-list-item-title class="text-body-2">
                  #{{ factura.numero || factura.id }}
                </v-list-item-title>
                <v-list-item-subtitle>
                  <div class="d-flex align-center justify-space-between">
                    <span>{{ factura.clienteNombre || 'Cliente' }}</span>
                    <span class="font-weight-bold">${{ formatCurrency(factura.total || 0) }}</span>
                  </div>
                </v-list-item-subtitle>
              </v-list-item>
            </v-list>
            <v-alert v-else type="info" variant="tonal" class="ma-4">
              No hay facturas recientes
            </v-alert>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useCitasStore } from '@/stores/citasStore'
import { usePacientesStore } from '@/stores/pacientesStore'
import { useClientesStore } from '@/stores/clientesStore'
import { useFacturasStore } from '@/stores/facturasStore'

const citasStore = useCitasStore()
const pacientesStore = usePacientesStore()
const clientesStore = useClientesStore()
const facturasStore = useFacturasStore()

// Estadísticas principales
const citasProximas = computed(() => citasStore.citasProximas)
const pacientesActivos = computed(() => pacientesStore.pacientesActivos)
const pacientesTotal = computed(() => pacientesStore.pacientesCount)
const facturenPendientes = computed(() => facturasStore.facturasPendientes)
const facturasTotal = computed(() => facturasStore.facturasTotal)
const clientesTotal = computed(() => clientesStore.clientesCount)

// Estadísticas de citas
const citasCompletadas = computed(() => 
  citasStore.citas.filter(c => {
    const estado = obtenerEstado(c).toLowerCase()
    return estado === 'completada'
  }).length
)

const citasConfirmadas = computed(() => 
  citasStore.citas.filter(c => {
    const estado = obtenerEstado(c).toLowerCase()
    return estado === 'confirmada'
  }).length
)

const citasEnProgreso = computed(() => 
  citasStore.citas.filter(c => {
    const estado = obtenerEstado(c).toLowerCase()
    return estado.includes('progreso') || estado.includes('atenci') || estado === 'en_progreso'
  }).length
)

const citasCanceladas = computed(() => 
  citasStore.citas.filter(c => {
    const estado = obtenerEstado(c).toLowerCase()
    return estado === 'cancelada'
  }).length
)

// Citas de hoy
const citasDeHoy = computed(() => {
  const hoy = new Date().toISOString().split('T')[0]
  return citasStore.citas
    .filter(cita => cita.fecha === hoy)
    .sort((a, b) => a.hora.localeCompare(b.hora))
})

const citasHoy = computed(() => citasDeHoy.value.length)

// Próximas citas
const ultimasCitas = computed(() => {
  const hoy = new Date()
  hoy.setHours(0, 0, 0, 0)
  
  return citasStore.citas
    .filter(cita => {
      const estado = obtenerEstado(cita).toLowerCase()
      const fechaCita = new Date(cita.fecha)
      fechaCita.setHours(0, 0, 0, 0)
      
      return estado !== 'completada' && 
             estado !== 'cancelada' && 
             estado !== 'no asistió' &&
             estado !== 'no asistio' &&
             fechaCita >= hoy
    })
    .sort((a, b) => {
      const fechaA = new Date(`${a.fecha}T${a.hora}`)
      const fechaB = new Date(`${b.fecha}T${b.hora}`)
      return fechaA - fechaB
    })
    .slice(0, 5)
})

// Distribución de citas
const distribucionCitas = computed(() => {
  const total = citasStore.citas.length
  if (total === 0) return []
  
  const distribucion = [
    { label: 'Completadas', count: citasCompletadas.value, color: 'success', icon: 'mdi-check-circle' },
    { label: 'Confirmadas', count: citasConfirmadas.value, color: 'info', icon: 'mdi-calendar-check' },
    { label: 'En Progreso', count: citasEnProgreso.value, color: 'warning', icon: 'mdi-progress-clock' },
    { label: 'Programadas', count: citasStore.citas.filter(c => obtenerEstado(c).toLowerCase() === 'programada').length, color: 'primary', icon: 'mdi-calendar-clock' },
    { label: 'Canceladas', count: citasCanceladas.value, color: 'error', icon: 'mdi-cancel' },
  ].filter(item => item.count > 0)
  
  return distribucion.map(item => ({
    ...item,
    percentage: Math.round((item.count / total) * 100)
  }))
})

// Facturas recientes
const facturasRecientes = computed(() => {
  return facturasStore.facturas
    .sort((a, b) => {
      const fechaA = new Date(a.createdAt || a.fechaEmision || 0)
      const fechaB = new Date(b.createdAt || b.fechaEmision || 0)
      return fechaB - fechaA
    })
    .slice(0, 5)
})

// Acciones rápidas
const accionesRapidas = [
  { title: 'Nueva Cita', to: '/citas/nueva', icon: 'mdi-calendar-plus', color: 'primary' },
  { title: 'Nueva Mascota', to: '/pacientes/nuevo', icon: 'mdi-paw', color: 'success' },
  { title: 'Nuevo Cliente', to: '/clientes/nuevo', icon: 'mdi-account-plus', color: 'info' },
  { title: 'Nueva Factura', to: '/facturas/nueva', icon: 'mdi-receipt', color: 'warning' },
  { title: 'Historia Clínica', to: '/historiales', icon: 'mdi-file-document', color: 'purple' },
]

// Saludo dinámico
const saludo = computed(() => {
  const hora = new Date().getHours()
  if (hora < 12) return '¡Buenos días!'
  if (hora < 18) return '¡Buenas tardes!'
  return '¡Buenas noches!'
})

// Fecha actual formateada
const fechaActual = computed(() => {
  return new Date().toLocaleDateString('es-ES', {
    weekday: 'long',
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
})

// Helpers
const obtenerEstado = (cita) => {
  return cita?.estadoNombre || cita?.estado || ''
}

const formatearEstado = (estado) => {
  if (!estado) return ''
  const estadoLower = estado.toLowerCase()
  if (estadoLower === 'en_progreso' || estadoLower.includes('progreso')) {
    return 'En Progreso'
  }
  if (estadoLower.includes('atenci')) {
    return 'En Atención'
  }
  return estado.charAt(0).toUpperCase() + estado.slice(1).toLowerCase()
}

const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleDateString('es-ES', {
    weekday: 'short',
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  })
}

const formatTime = (timeString) => {
  if (!timeString) return ''
  return timeString.substring(0, 5)
}

const formatCurrency = (amount) => {
  if (!amount) return '0'
  return new Intl.NumberFormat('es-CO', {
    minimumFractionDigits: 0,
    maximumFractionDigits: 0
  }).format(amount)
}

const getEstadoColor = (estado) => {
  const estadoLower = estado?.toLowerCase() || ''
  if (estadoLower.includes('atenci') || estadoLower.includes('progreso') || estadoLower === 'en_progreso') {
    return 'warning'
  }
  const colores = {
    'programada': 'info',
    'confirmada': 'success',
    'completada': 'success',
    'cancelada': 'error',
    'no asistió': 'grey',
    'no asistio': 'grey',
  }
  return colores[estadoLower] || 'grey'
}

const getEstadoIcon = (estado) => {
  const estadoLower = estado?.toLowerCase() || ''
  if (estadoLower.includes('atenci') || estadoLower.includes('progreso') || estadoLower === 'en_progreso') {
    return 'mdi-progress-clock'
  }
  const iconos = {
    'programada': 'mdi-calendar-clock',
    'confirmada': 'mdi-check-circle',
    'completada': 'mdi-check-all',
    'cancelada': 'mdi-cancel',
    'no asistió': 'mdi-account-off',
    'no asistio': 'mdi-account-off',
  }
  return iconos[estadoLower] || 'mdi-circle'
}

const getFacturaColor = (factura) => {
  const estado = (factura.estadoNombre || factura.estado || '').toLowerCase()
  if (estado === 'pagada' || estado === 'pagado') return 'success'
  if (estado === 'pendiente') return 'warning'
  return 'info'
}

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
</script>

<style scoped>
.stat-card {
  transition: all 0.3s ease;
  border-left: 4px solid transparent;
}

.stat-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15) !important;
}

.stat-card-primary {
  border-left-color: #0A74B7; /* Azul Institucional */
}

.stat-card-success {
  border-left-color: #0C5A96; /* Azul Medio */
}

.stat-card-warning {
  border-left-color: #F57C4F; /* Naranja - Acción Destacada */
}

.stat-card-info {
  border-left-color: #1C78BF; /* Azul Claro */
}

.border-primary {
  border: 2px solid #0A74B7 !important; /* Azul Institucional */
}

.cursor-pointer {
  cursor: pointer;
  transition: background-color 0.2s;
}

.cursor-pointer:hover {
  background-color: rgba(0, 0, 0, 0.04);
}

.gap-1 {
  gap: 4px;
}
</style>
