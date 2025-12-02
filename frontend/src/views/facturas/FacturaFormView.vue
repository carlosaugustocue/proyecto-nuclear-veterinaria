<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12" md="10" offset-md="1">
        <v-card>
          <v-card-title class="bg-primary text-white py-4">
            {{ isEditing ? 'Editar Factura' : 'Nueva Factura' }}
          </v-card-title>

          <v-card-text class="pa-6">
            <v-form @submit.prevent="saveFactura" ref="form">
              <!-- Datos de la factura -->
              <h3 class="mb-4">Datos Generales</h3>
              <v-row>
                <v-col cols="12" md="6">
                  <v-select
                    v-model="form.clienteId"
                    label="Cliente *"
                    :items="clientes"
                    :item-title="(item) => item.nombreCompleto || item.nombre || `${item.nombre || ''} ${item.apellido || ''}`.trim() || 'Sin nombre'"
                    item-value="id"
                    :rules="[rules.required]"
                    prepend-icon="mdi-account"
                  ></v-select>
                </v-col>

                <v-col cols="12" md="6">
                  <v-select
                    v-model="form.citaId"
                    label="Cita Asociada"
                    :items="citas"
                    :item-title="(item) => `${item.pacienteNombre || 'Sin paciente'} - ${formatDate(item.fecha)} ${formatTime(item.hora)}`"
                    item-value="id"
                    prepend-icon="mdi-calendar-clock"
                    clearable
                  ></v-select>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="form.fechaEmision"
                    label="Fecha de Emisión *"
                    type="date"
                    :rules="[rules.required]"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="form.fechaVencimiento"
                    label="Fecha de Vencimiento"
                    type="date"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-divider class="my-6"></v-divider>

              <!-- Items de la factura -->
              <h3 class="mb-4">Items</h3>
              <v-table v-if="form.detalles.length > 0" class="mb-4">
                <thead>
                  <tr>
                    <th>Descripción</th>
                    <th>Cantidad</th>
                    <th>Precio Unitario</th>
                    <th>Subtotal</th>
                    <th>Acciones</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(item, index) in form.detalles" :key="index">
                    <td>{{ item.descripcion }}</td>
                    <td>{{ item.cantidad }}</td>
                    <td>${{ formatCurrency(item.precioUnitario) }}</td>
                    <td>${{ formatCurrency(item.cantidad * item.precioUnitario) }}</td>
                    <td>
                      <v-btn
                        size="small"
                        icon="delete"
                        color="error"
                        @click="form.detalles.splice(index, 1)"
                      ></v-btn>
                    </td>
                  </tr>
                </tbody>
              </v-table>

              <v-btn
                color="secondary"
                class="mb-4"
                @click="addDetalle"
              >
                Agregar Item
              </v-btn>

              <v-divider class="my-6"></v-divider>

              <!-- Resumen -->
              <v-row class="mb-4">
                <v-col cols="12" md="6" offset-md="6">
                  <div class="text-right mb-2">
                    <strong>Subtotal:</strong> $ {{ formatCurrency(subtotal) }}
                  </div>
                  <div class="text-right mb-2">
                    <strong>Descuento:</strong> $ {{ formatCurrency(form.descuento) }}
                  </div>
                  <div class="text-right text-h6 font-weight-bold">
                    <strong>Total:</strong> $ {{ formatCurrency(total) }}
                  </div>
                </v-col>
              </v-row>

              <v-row class="mt-6">
                <v-col cols="12" md="6">
                  <v-btn
                    color="primary"
                    block
                    type="submit"
                    :loading="loading"
                  >
                    {{ isEditing ? 'Actualizar' : 'Crear' }} Factura
                  </v-btn>
                </v-col>

                <v-col cols="12" md="6">
                  <v-btn
                    color="secondary"
                    block
                    @click="$router.back()"
                  >
                    Cancelar
                  </v-btn>
                </v-col>
              </v-row>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <!-- Dialog para agregar items -->
    <v-dialog v-model="dialogDetalle" width="600" persistent>
      <v-card>
        <v-card-title class="bg-primary text-white">
          Agregar Item / Servicio
        </v-card-title>
        <v-card-text class="pa-4">
          <!-- Selección de Servicio -->
          <v-select
            v-model="nuevoDetalle.tipoServicioId"
            label="Seleccionar Servicio"
            :items="tiposServicio"
            item-title="nombre"
            item-value="id"
            clearable
            class="mb-4"
            prepend-icon="mdi-medical-bag"
            @update:model-value="onServicioSeleccionado"
          >
            <template v-slot:item="{ props, item }">
              <v-list-item v-bind="props">
                <template v-slot:prepend>
                  <v-icon>mdi-medical-bag</v-icon>
                </template>
                <v-list-item-title>{{ item.raw.nombre }}</v-list-item-title>
                <v-list-item-subtitle>
                  ${{ formatCurrency(item.raw.precioBase || 0) }}
                </v-list-item-subtitle>
              </v-list-item>
            </template>
          </v-select>

          <v-divider class="my-4"></v-divider>

          <!-- Campos manuales -->
          <v-text-field
            v-model="nuevoDetalle.descripcion"
            label="Descripción *"
            class="mb-4"
            :rules="[rules.required]"
            prepend-icon="mdi-text"
          ></v-text-field>

          <v-row>
            <v-col cols="6">
          <v-text-field
            v-model.number="nuevoDetalle.cantidad"
            label="Cantidad *"
            type="number"
                min="1"
                :rules="[rules.required, rules.positive]"
                prepend-icon="mdi-numeric"
          ></v-text-field>
            </v-col>
            <v-col cols="6">
          <v-text-field
            v-model.number="nuevoDetalle.precioUnitario"
            label="Precio Unitario *"
            type="number"
            step="0.01"
                min="0"
                :rules="[rules.required, rules.positive]"
                prepend-icon="mdi-currency-usd"
          ></v-text-field>
            </v-col>
          </v-row>

          <!-- Mostrar subtotal calculado -->
          <v-alert
            type="info"
            variant="tonal"
            class="mt-4"
            v-if="nuevoDetalle.cantidad > 0 && nuevoDetalle.precioUnitario > 0"
          >
            <div class="d-flex justify-space-between align-center">
              <span>Subtotal:</span>
              <strong class="text-h6">
                ${{ formatCurrency(nuevoDetalle.cantidad * nuevoDetalle.precioUnitario) }}
              </strong>
            </div>
          </v-alert>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="secondary" @click="cancelarDetalle">Cancelar</v-btn>
          <v-btn
            color="primary"
            @click="confirmDetalle"
            :disabled="!nuevoDetalle.descripcion || !nuevoDetalle.cantidad || !nuevoDetalle.precioUnitario"
          >
            Agregar
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useFacturasStore } from '@/stores/facturasStore'
import { useReferenceData } from '@/composables/useReferenceData'

const router = useRouter()
const route = useRoute()
const facturasStore = useFacturasStore()
const { fetchClientes, fetchCitas, fetchTiposServicio } = useReferenceData()

const loading = computed(() => facturasStore.loading)
const isEditing = computed(() => !!route.params.id)
const dialogDetalle = ref(false)

const form = reactive({
  clienteId: null,
  citaId: null,
  fechaEmision: new Date().toISOString().split('T')[0],
  fechaVencimiento: null,
  detalles: [],
  descuento: 0,
})

const nuevoDetalle = reactive({
  tipoServicioId: null,
  descripcion: '',
  cantidad: 1,
  precioUnitario: 0,
})

const clientes = ref([])
const citas = ref([])
const tiposServicio = ref([])

const subtotal = computed(() => {
  return form.detalles.reduce((sum, item) => sum + item.cantidad * item.precioUnitario, 0)
})

const total = computed(() => {
  return subtotal.value - form.descuento
})

const rules = {
  required: (v) => !!v || 'Este campo es requerido',
  positive: (v) => (v && v > 0) || 'El valor debe ser mayor a 0',
}

const formatCurrency = (value) => {
  return new Intl.NumberFormat('es-CO', {
    minimumFractionDigits: 0,
  }).format(value || 0)
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

const formatTime = (timeString) => {
  if (!timeString) return ''
  return timeString.substring(0, 5) // HH:MM
}

const addDetalle = () => {
  // Resetear formulario
  nuevoDetalle.tipoServicioId = null
  nuevoDetalle.descripcion = ''
  nuevoDetalle.cantidad = 1
  nuevoDetalle.precioUnitario = 0
  dialogDetalle.value = true
}

const onServicioSeleccionado = (servicioId) => {
  if (servicioId) {
    const servicio = tiposServicio.value.find(s => s.id === servicioId)
    if (servicio) {
      nuevoDetalle.descripcion = servicio.nombre
      nuevoDetalle.precioUnitario = servicio.precioBase || servicio.precio || 0
      nuevoDetalle.tipoServicioId = servicio.id
    }
  }
}

const cancelarDetalle = () => {
  nuevoDetalle.tipoServicioId = null
    nuevoDetalle.descripcion = ''
    nuevoDetalle.cantidad = 1
    nuevoDetalle.precioUnitario = 0
    dialogDetalle.value = false
}

const confirmDetalle = () => {
  if (nuevoDetalle.descripcion && nuevoDetalle.cantidad && nuevoDetalle.precioUnitario) {
    const detalle = {
      tipoServicioId: nuevoDetalle.tipoServicioId,
      descripcion: nuevoDetalle.descripcion,
      cantidad: nuevoDetalle.cantidad,
      precioUnitario: nuevoDetalle.precioUnitario,
      subtotal: nuevoDetalle.cantidad * nuevoDetalle.precioUnitario,
    }
    form.detalles.push(detalle)
    cancelarDetalle()
  }
}

const loadData = async () => {
  try {
    const [clientesData, citasData, tiposServicioData] = await Promise.all([
      fetchClientes(),
      fetchCitas(),
      fetchTiposServicio(),
    ])

    clientes.value = clientesData
    citas.value = citasData
    tiposServicio.value = tiposServicioData

    // Si estamos editando, cargar los datos de la factura
    if (isEditing.value) {
      await facturasStore.fetchFacturaById(route.params.id)
      const factura = facturasStore.currentFactura
      if (factura) {
        form.clienteId = factura.clienteId
        form.citaId = factura.citaId
        form.fechaEmision = factura.fechaEmision
        form.fechaVencimiento = factura.fechaVencimiento
        form.detalles = factura.detalles || []
        form.descuento = factura.totalDescuentos || 0
      }
    }
  } catch (error) {
    console.error('Error al cargar datos:', error)
  }
}

const saveFactura = async () => {
  try {
    if (isEditing.value) {
      await facturasStore.updateFactura(route.params.id, form)
    } else {
      await facturasStore.createFactura(form)
    }
    router.push('/facturas')
  } catch (error) {
    console.error('Error al guardar factura:', error)
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
</style>
