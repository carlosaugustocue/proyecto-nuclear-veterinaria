<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12" md="8" offset-md="2">
        <v-card v-if="factura">
          <v-card-title class="bg-primary text-white py-4 d-flex justify-space-between">
            <span>{{ factura.numero }}</span>
            <v-btn icon color="white" @click="$router.back()">
              <v-icon>mdi-close</v-icon>
            </v-btn>
          </v-card-title>

          <v-card-text class="pa-6">
            <!-- Encabezado -->
            <v-row class="mb-6 pb-4 border-bottom">
              <v-col cols="12" md="6">
                <div class="mb-4">
                  <strong>Cliente:</strong>
                  <p>{{ factura.cliente }}</p>
                </div>
                <div class="mb-4">
                  <strong>Fecha de Emisión:</strong>
                  <p>{{ factura.fecha }}</p>
                </div>
              </v-col>

              <v-col cols="12" md="6">
                <div class="mb-4">
                  <strong>Estado:</strong>
                  <v-chip :color="getEstadoColor(factura.estado)" text-color="white">
                    {{ factura.estado }}
                  </v-chip>
                </div>
              </v-col>
            </v-row>

            <!-- Items -->
            <h3 class="mb-4">Items</h3>
            <v-table v-if="factura.detalles && factura.detalles.length > 0" class="mb-6">
              <thead>
                <tr>
                  <th>Descripción</th>
                  <th>Cantidad</th>
                  <th>Precio Unitario</th>
                  <th>Subtotal</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="item in factura.detalles" :key="item.id">
                  <td>{{ item.descripcion }}</td>
                  <td>{{ item.cantidad }}</td>
                  <td>${{ formatCurrency(item.precioUnitario) }}</td>
                  <td>${{ formatCurrency(item.cantidad * item.precioUnitario) }}</td>
                </tr>
              </tbody>
            </v-table>

            <!-- Resumen -->
            <v-divider class="my-4"></v-divider>
            <v-row class="mt-6">
              <v-col cols="12" md="6" offset-md="6">
                <div class="text-right mb-2">
                  <strong>Subtotal:</strong> $ {{ formatCurrency(factura.subtotal) }}
                </div>
                <div class="text-right mb-2" v-if="factura.descuento > 0">
                  <strong>Descuento:</strong> -$ {{ formatCurrency(factura.descuento) }}
                </div>
                <div class="text-right text-h6 font-weight-bold bg-light pa-2 rounded">
                  <strong>Total:</strong> $ {{ formatCurrency(factura.total) }}
                </div>
              </v-col>
            </v-row>

            <!-- Pagos -->
            <v-divider class="my-6"></v-divider>
            <h3 class="mb-4">Pagos</h3>
            <v-list v-if="factura.pagos && factura.pagos.length > 0">
              <v-list-item v-for="pago in factura.pagos" :key="pago.id">
                <v-list-item-title>{{ pago.metodo }}</v-list-item-title>
                <v-list-item-subtitle>{{ pago.fecha }}</v-list-item-subtitle>
                <template v-slot:append>
                  <span class="font-weight-bold">${{ formatCurrency(pago.monto) }}</span>
                </template>
              </v-list-item>
            </v-list>
            <v-alert v-else type="info" text>No hay pagos registrados</v-alert>

            <!-- Acciones -->
            <v-row class="mt-6">
              <v-col cols="12" md="4">
                <v-btn
                  color="info"
                  block
                  class="mb-2"
                >
                  Registrar Pago
                </v-btn>
              </v-col>

              <v-col cols="12" md="4">
                <v-btn
                  color="primary"
                  block
                  class="mb-2"
                >
                  Descargar PDF
                </v-btn>
              </v-col>

              <v-col cols="12" md="4">
                <v-btn
                  color="error"
                  block
                  @click="deleteFactura"
                >
                  Eliminar
                </v-btn>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>

        <v-card v-else>
          <v-card-text class="pa-6 text-center">
            <v-progress-circular indeterminate color="primary"></v-progress-circular>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useFacturasStore } from '@/stores/facturasStore'

const router = useRouter()
const route = useRoute()
const facturasStore = useFacturasStore()

const factura = computed(() => facturasStore.currentFactura)

const getFactura = async () => {
  try {
    await facturasStore.fetchFacturaById(route.params.id)
  } catch (error) {
    console.error('Error al cargar factura:', error)
  }
}

const formatCurrency = (value) => {
  return new Intl.NumberFormat('es-CO', {
    minimumFractionDigits: 0,
  }).format(value || 0)
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

const deleteFactura = async () => {
  if (confirm('¿Estás seguro de que quieres eliminar esta factura?')) {
    try {
      await facturasStore.deleteFactura(route.params.id)
      router.push('/facturas')
    } catch (error) {
      console.error('Error al eliminar factura:', error)
    }
  }
}

onMounted(() => {
  getFactura()
})
</script>

<style scoped>
</style>
