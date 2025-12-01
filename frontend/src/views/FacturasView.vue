<template>
  <v-container fluid class="pa-4 pa-md-6">
    <!-- Header -->
    <v-row class="mb-4">
      <v-col cols="12">
        <div class="d-flex justify-space-between align-center flex-wrap">
          <div class="d-flex align-center mb-2">
            <v-icon color="primary" size="40" class="mr-3">mdi-receipt</v-icon>
            <div>
              <h1 class="text-h4 font-weight-bold mb-1">Gestión de Facturas</h1>
              <p class="text-body-2 text-grey mb-0">Administra y consulta todas las facturas del sistema</p>
            </div>
          </div>
          <v-btn
            to="/facturas/nueva"
            color="primary"
            prepend-icon="mdi-plus"
            size="large"
            class="mb-2"
          >
            Nueva Factura
          </v-btn>
        </div>
      </v-col>
    </v-row>

    <!-- Estadísticas -->
    <v-row class="mb-4">
      <v-col cols="12" sm="6" md="3">
        <v-card class="elevation-2" :class="{ 'bg-primary-lighten-5': true }">
          <v-card-text class="d-flex align-center">
            <v-avatar color="primary" size="56" class="mr-4">
              <v-icon color="white" size="28">mdi-receipt</v-icon>
            </v-avatar>
            <div>
              <div class="text-caption text-grey">Total Facturas</div>
              <div class="text-h5 font-weight-bold">{{ facturas.length }}</div>
            </div>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col cols="12" sm="6" md="3">
        <v-card class="elevation-2" :class="{ 'bg-success-lighten-5': true }">
          <v-card-text class="d-flex align-center">
            <v-avatar color="success" size="56" class="mr-4">
              <v-icon color="white" size="28">mdi-cash-check</v-icon>
            </v-avatar>
            <div>
              <div class="text-caption text-grey">Facturas Pagadas</div>
              <div class="text-h5 font-weight-bold">
                {{ facturas.filter(f => (f.estadoNombre || f.estado || '').toLowerCase() === 'pagada' || (f.estadoNombre || f.estado || '').toLowerCase() === 'pagado').length }}
              </div>
            </div>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col cols="12" sm="6" md="3">
        <v-card class="elevation-2" :class="{ 'bg-warning-lighten-5': true }">
          <v-card-text class="d-flex align-center">
            <v-avatar color="warning" size="56" class="mr-4">
              <v-icon color="white" size="28">mdi-clock-outline</v-icon>
            </v-avatar>
            <div>
              <div class="text-caption text-grey">Facturas Pendientes</div>
              <div class="text-h5 font-weight-bold">
                {{ facturas.filter(f => (f.estadoNombre || f.estado || '').toLowerCase() === 'pendiente').length }}
              </div>
            </div>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col cols="12" sm="6" md="3">
        <v-card class="elevation-2" :class="{ 'bg-info-lighten-5': true }">
          <v-card-text class="d-flex align-center">
            <v-avatar color="info" size="56" class="mr-4">
              <v-icon color="white" size="28">mdi-currency-usd</v-icon>
            </v-avatar>
            <div>
              <div class="text-caption text-grey">Total Facturado</div>
              <div class="text-h5 font-weight-bold">
                ${{ formatCurrency(facturas.reduce((sum, f) => sum + (f.total || 0), 0)) }}
              </div>
            </div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <!-- Filtros -->
    <v-card class="mb-4 elevation-2">
      <v-card-title class="bg-grey-lighten-4 d-flex align-center">
        <v-icon class="mr-2">mdi-filter</v-icon>
        <span class="font-weight-bold">Filtros de Búsqueda</span>
        <v-spacer></v-spacer>
        <v-btn
          v-if="hasActiveFilters"
          size="small"
          color="error"
          variant="text"
          prepend-icon="mdi-close"
          @click="clearFilters"
        >
          Limpiar Filtros
        </v-btn>
      </v-card-title>
      <v-card-text class="pa-4">
        <v-row>
          <v-col cols="12" sm="6" md="3">
            <v-text-field
              v-model="filters.searchText"
              label="Buscar por número o cliente"
              prepend-icon="mdi-magnify"
              clearable
              variant="outlined"
              density="comfortable"
              @update:model-value="fetchFacturas"
            ></v-text-field>
          </v-col>

          <v-col cols="12" sm="6" md="3">
            <v-select
              v-model="filters.estado"
              label="Estado"
              :items="estadoOptions"
              clearable
              variant="outlined"
              density="comfortable"
              prepend-icon="mdi-tag"
              @update:model-value="fetchFacturas"
            >
              <template v-slot:item="{ props, item }">
                <v-list-item v-bind="props">
                  <template v-slot:prepend>
                    <v-chip
                      :color="getEstadoColor(item.value)"
                      size="small"
                      text-color="white"
                    >
                      {{ item.title }}
                    </v-chip>
                  </template>
                </v-list-item>
              </template>
            </v-select>
          </v-col>

          <v-col cols="12" sm="6" md="3">
            <v-text-field
              v-model="filters.fechaInicio"
              label="Fecha Inicio"
              type="date"
              variant="outlined"
              density="comfortable"
              prepend-icon="mdi-calendar-start"
              @update:model-value="fetchFacturas"
            ></v-text-field>
          </v-col>

          <v-col cols="12" sm="6" md="3">
            <v-text-field
              v-model="filters.fechaFin"
              label="Fecha Fin"
              type="date"
              variant="outlined"
              density="comfortable"
              prepend-icon="mdi-calendar-end"
              @update:model-value="fetchFacturas"
            ></v-text-field>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>

    <!-- Tabla de facturas -->
    <v-row>
      <v-col cols="12">
        <v-card class="elevation-3">
          <v-card-title class="bg-primary text-white d-flex align-center">
            <v-icon class="mr-2">mdi-receipt</v-icon>
            <span>Lista de Facturas</span>
            <v-spacer></v-spacer>
            <v-chip color="white" text-color="primary" class="ml-2">
              {{ facturas.length }} factura{{ facturas.length !== 1 ? 's' : '' }}
            </v-chip>
          </v-card-title>
          <v-data-table
            :headers="headers"
            :items="facturas"
            :loading="loading"
            :sort-by="[{ key: 'fechaEmision', order: 'desc' }]"
            :items-per-page="15"
            class="elevation-0"
            show-expand
            item-value="id"
          >
            <!-- Número de Factura -->
            <template v-slot:item.numeroFactura="{ item }">
              <div class="d-flex align-center">
                <v-icon color="primary" size="small" class="mr-2">mdi-receipt</v-icon>
                <span class="font-weight-bold text-primary">
                  {{ item.numeroFactura || item.numero || `#${item.id}` }}
                </span>
              </div>
            </template>

            <!-- Cliente -->
            <template v-slot:item.clienteNombre="{ item }">
              <div class="d-flex align-center">
                <v-avatar size="32" color="blue" class="mr-2">
                  <v-icon color="white" size="small">mdi-account</v-icon>
                </v-avatar>
                <div>
                  <div class="font-weight-medium">{{ item.clienteNombre || item.cliente || 'No especificado' }}</div>
                  <div v-if="item.citaId" class="text-caption text-grey">
                    <v-chip size="x-small" color="info" variant="outlined">
                      Cita #{{ item.citaId }}
                    </v-chip>
                  </div>
                </div>
              </div>
            </template>

            <!-- Fecha -->
            <template v-slot:item.fechaEmision="{ item }">
              <div>
                <div class="font-weight-medium">{{ formatDate(item.fechaEmision || item.fecha) }}</div>
                <div class="text-caption text-grey">{{ formatTime(item.fechaEmision || item.fecha) }}</div>
              </div>
            </template>

            <!-- Items/Detalles -->
            <template v-slot:item.detalles="{ item }">
              <div v-if="item.detalles && item.detalles.length > 0">
                <v-chip size="small" color="info" variant="outlined" class="mr-1">
                  {{ item.detalles.length }} item{{ item.detalles.length !== 1 ? 's' : '' }}
                </v-chip>
                <div class="text-caption text-grey mt-1">
                  {{ item.detalles[0]?.descripcion || 'Sin descripción' }}
                  <span v-if="item.detalles.length > 1"> +{{ item.detalles.length - 1 }} más</span>
                </div>
              </div>
              <span v-else class="text-grey">Sin items</span>
            </template>

            <!-- Total -->
            <template v-slot:item.total="{ item }">
              <div class="text-right">
                <div class="font-weight-bold text-h6 text-primary">
                  ${{ formatCurrency(item.total || 0) }}
                </div>
                <div v-if="(item.saldoPendiente || 0) > 0" class="text-caption text-warning">
                  Pendiente: ${{ formatCurrency(item.saldoPendiente) }}
                </div>
                <div v-else-if="(item.totalPagado || 0) > 0" class="text-caption text-success">
                  Pagado: ${{ formatCurrency(item.totalPagado) }}
                </div>
              </div>
            </template>

            <!-- Estado -->
            <template v-slot:item.estadoNombre="{ item }">
              <v-chip
                :color="getEstadoColor(item.estadoNombre || item.estado)"
                :prepend-icon="getEstadoIcon(item.estadoNombre || item.estado)"
                size="small"
                text-color="white"
                class="font-weight-bold"
              >
                {{ formatearEstado(item.estadoNombre || item.estado) }}
              </v-chip>
            </template>

            <!-- Pagos -->
            <template v-slot:item.pagos="{ item }">
              <div v-if="item.pagos && item.pagos.length > 0">
                <v-chip size="small" color="success" variant="outlined" class="mr-1">
                  {{ item.pagos.length }} pago{{ item.pagos.length !== 1 ? 's' : '' }}
                </v-chip>
                <div class="text-caption text-grey mt-1">
                  Total: ${{ formatCurrency(item.totalPagado || item.pagos.reduce((sum, p) => sum + (p.monto || 0), 0)) }}
                </div>
              </div>
              <span v-else class="text-grey">Sin pagos</span>
            </template>

            <!-- Acciones -->
            <template v-slot:item.actions="{ item }">
              <div class="d-flex align-center">
                <v-tooltip text="Ver Detalles">
                  <template v-slot:activator="{ props }">
                    <v-btn
                      v-bind="props"
                      size="small"
                      color="primary"
                      variant="text"
                      icon="mdi-eye"
                      :to="`/facturas/${item.id}`"
                    ></v-btn>
                  </template>
                </v-tooltip>
                <v-tooltip text="Editar">
                  <template v-slot:activator="{ props }">
                    <v-btn
                      v-bind="props"
                      size="small"
                      color="info"
                      variant="text"
                      icon="mdi-pencil"
                      :to="`/facturas/${item.id}/editar`"
                    ></v-btn>
                  </template>
                </v-tooltip>
                <v-tooltip text="Eliminar">
                  <template v-slot:activator="{ props }">
                    <v-btn
                      v-bind="props"
                      size="small"
                      color="error"
                      variant="text"
                      icon="mdi-delete"
                      @click="deleteFactura(item.id)"
                    ></v-btn>
                  </template>
                </v-tooltip>
              </div>
            </template>

            <!-- Fila expandible con detalles -->
            <template v-slot:expanded-row="{ item }">
              <tr>
                <td :colspan="headers.length" class="pa-4 bg-grey-lighten-5">
                  <v-row>
                    <v-col cols="12" md="6">
                      <v-card variant="outlined" class="mb-3">
                        <v-card-title class="bg-blue-lighten-5 text-h6">
                          <v-icon class="mr-2">mdi-format-list-bulleted</v-icon>
                          Items / Servicios
                        </v-card-title>
                        <v-card-text>
                          <v-list v-if="item.detalles && item.detalles.length > 0" density="compact">
                            <v-list-item
                              v-for="(detalle, index) in item.detalles"
                              :key="index"
                            >
                              <template v-slot:prepend>
                                <v-icon color="primary">mdi-medical-bag</v-icon>
                              </template>
                              <v-list-item-title>{{ detalle.descripcion }}</v-list-item-title>
                              <v-list-item-subtitle>
                                Cantidad: {{ detalle.cantidad }} × ${{ formatCurrency(detalle.precioUnitario) }}
                              </v-list-item-subtitle>
                              <template v-slot:append>
                                <span class="font-weight-bold">
                                  ${{ formatCurrency(detalle.subtotal || (detalle.cantidad * detalle.precioUnitario)) }}
                                </span>
                              </template>
                            </v-list-item>
                          </v-list>
                          <v-alert v-else type="info" variant="tonal">
                            No hay items registrados
                          </v-alert>
                        </v-card-text>
                      </v-card>
                    </v-col>
                    <v-col cols="12" md="6">
                      <v-card variant="outlined" class="mb-3">
                        <v-card-title class="bg-amber-lighten-5 text-h6">
                          <v-icon class="mr-2">mdi-cash-multiple</v-icon>
                          Pagos Registrados
                        </v-card-title>
                        <v-card-text>
                          <v-list v-if="item.pagos && item.pagos.length > 0" density="compact">
                            <v-list-item
                              v-for="(pago, index) in item.pagos"
                              :key="index"
                            >
                              <template v-slot:prepend>
                                <v-avatar color="amber" size="32">
                                  <v-icon color="white" size="small">mdi-cash</v-icon>
                                </v-avatar>
                              </template>
                              <v-list-item-title>
                                {{ pago.metodoPagoNombre || pago.metodo || 'Pago' }}
                              </v-list-item-title>
                              <v-list-item-subtitle>
                                {{ formatDate(pago.fecha || pago.fechaPago) }}
                                <span v-if="pago.numeroReferencia"> - Ref: {{ pago.numeroReferencia }}</span>
                              </v-list-item-subtitle>
                              <template v-slot:append>
                                <span class="font-weight-bold text-success">
                                  ${{ formatCurrency(pago.monto) }}
                                </span>
                              </template>
                            </v-list-item>
                          </v-list>
                          <v-alert v-else type="info" variant="tonal">
                            No hay pagos registrados
                          </v-alert>
                        </v-card-text>
                      </v-card>
                      <v-card variant="outlined">
                        <v-card-title class="bg-purple-lighten-5 text-h6">
                          <v-icon class="mr-2">mdi-calculator</v-icon>
                          Resumen Financiero
                        </v-card-title>
                        <v-card-text>
                          <div class="d-flex justify-space-between mb-2">
                            <span>Subtotal:</span>
                            <strong>${{ formatCurrency(item.subtotal || 0) }}</strong>
                          </div>
                          <div v-if="(item.totalDescuentos || 0) > 0" class="d-flex justify-space-between mb-2 text-error">
                            <span>Descuentos:</span>
                            <strong>-${{ formatCurrency(item.totalDescuentos) }}</strong>
                          </div>
                          <div v-if="(item.totalImpuestos || 0) > 0" class="d-flex justify-space-between mb-2">
                            <span>Impuestos:</span>
                            <strong>${{ formatCurrency(item.totalImpuestos) }}</strong>
                          </div>
                          <v-divider class="my-2"></v-divider>
                          <div class="d-flex justify-space-between text-h6 font-weight-bold text-primary">
                            <span>Total:</span>
                            <strong>${{ formatCurrency(item.total || 0) }}</strong>
                          </div>
                          <div v-if="(item.totalPagado || 0) > 0" class="d-flex justify-space-between mt-2">
                            <span>Total Pagado:</span>
                            <strong class="text-success">${{ formatCurrency(item.totalPagado) }}</strong>
                          </div>
                          <div v-if="(item.saldoPendiente || 0) > 0" class="d-flex justify-space-between mt-2">
                            <span>Saldo Pendiente:</span>
                            <strong class="text-warning">${{ formatCurrency(item.saldoPendiente) }}</strong>
                          </div>
                        </v-card-text>
                      </v-card>
                    </v-col>
                  </v-row>
                </td>
              </tr>
            </template>

            <template v-slot:no-data>
              <v-alert type="info" variant="tonal" class="my-6">
                <v-icon class="mr-2">mdi-information</v-icon>
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
import { useNotification } from '@/composables/useNotification'

const facturasStore = useFacturasStore()
const { showSuccess, showError } = useNotification()

const loading = computed(() => facturasStore.loading)
const facturas = computed(() => facturasStore.facturas)

const filters = reactive({
  searchText: '',
  estado: null,
  fechaInicio: null,
  fechaFin: null,
})

const estadoOptions = [
  { title: 'Pendiente', value: 'PENDIENTE' },
  { title: 'Pagada', value: 'PAGADA' },
  { title: 'Anulada', value: 'ANULADA' },
  { title: 'Vencida', value: 'VENCIDA' },
  { title: 'Parcial', value: 'PARCIAL' },
]

const hasActiveFilters = computed(() => {
  return !!(filters.searchText || filters.estado || filters.fechaInicio || filters.fechaFin)
})

const clearFilters = () => {
  filters.searchText = ''
  filters.estado = null
  filters.fechaInicio = null
  filters.fechaFin = null
  fetchFacturas()
}

const headers = [
  { title: 'Número', value: 'numeroFactura', width: '150' },
  { title: 'Cliente', value: 'clienteNombre', width: '200' },
  { title: 'Fecha', value: 'fechaEmision', width: '130' },
  { title: 'Items', value: 'detalles', width: '150', sortable: false },
  { title: 'Total', value: 'total', width: '150' },
  { title: 'Pagos', value: 'pagos', width: '120', sortable: false },
  { title: 'Estado', value: 'estadoNombre', width: '130' },
  { title: 'Acciones', value: 'actions', sortable: false, width: '120' },
]

const fetchFacturas = async () => {
  try {
    await facturasStore.fetchFacturas(filters)
  } catch (error) {
    console.error('Error al cargar facturas:', error)
    showError(error.userMessage || 'Error al cargar las facturas')
  }
}

const getEstadoColor = (estado) => {
  if (!estado) return 'grey'
  const estadoLower = estado.toLowerCase()
  if (estadoLower === 'pagada' || estadoLower === 'pagado') return 'success'
  if (estadoLower === 'pendiente') return 'warning'
  if (estadoLower === 'anulada' || estadoLower === 'anulado') return 'error'
  if (estadoLower === 'vencida') return 'error'
  if (estadoLower === 'parcial') return 'info'
  return 'grey'
}

const getEstadoIcon = (estado) => {
  if (!estado) return 'mdi-help-circle'
  const estadoLower = estado.toLowerCase()
  if (estadoLower === 'pagada' || estadoLower === 'pagado') return 'mdi-check-circle'
  if (estadoLower === 'pendiente') return 'mdi-clock-outline'
  if (estadoLower === 'anulada' || estadoLower === 'anulado') return 'mdi-cancel'
  if (estadoLower === 'vencida') return 'mdi-alert-circle'
  if (estadoLower === 'parcial') return 'mdi-cash-multiple'
  return 'mdi-help-circle'
}

const formatearEstado = (estado) => {
  if (!estado) return 'N/A'
  const estadoLower = estado.toLowerCase()
  const estados = {
    'pendiente': 'Pendiente',
    'pagada': 'Pagada',
    'pagado': 'Pagada',
    'anulada': 'Anulada',
    'anulado': 'Anulada',
    'vencida': 'Vencida',
    'parcial': 'Parcial',
  }
  return estados[estadoLower] || estado
}

const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleDateString('es-ES', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
  })
}

const formatTime = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleTimeString('es-ES', {
    hour: '2-digit',
    minute: '2-digit'
  })
}

const formatCurrency = (value) => {
  return new Intl.NumberFormat('es-CO', {
    minimumFractionDigits: 0,
  }).format(value)
}

const deleteFactura = async (id) => {
  if (confirm('¿Estás seguro de que quieres eliminar esta factura? Esta acción no se puede deshacer.')) {
    try {
      await facturasStore.deleteFactura(id)
      showSuccess('Factura eliminada exitosamente')
      await fetchFacturas() // Recargar la lista
    } catch (error) {
      console.error('Error al eliminar factura:', error)
      const mensaje = error.response?.data?.userMessage || 
                     error.response?.data?.message || 
                     'Error al eliminar la factura'
      showError(mensaje)
    }
  }
}

onMounted(() => {
  fetchFacturas()
})
</script>

<style scoped>
</style>
