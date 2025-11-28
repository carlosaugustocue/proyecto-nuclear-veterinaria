<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12">
        <h1>Gestión de Inventario</h1>
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="12" md="6">
        <v-btn color="primary" @click="showNuevoProducto = true" class="mr-2">Nuevo Producto</v-btn>
        <v-btn color="secondary" @click="showNuevoMovimiento = true">Registrar Movimiento</v-btn>
      </v-col>
    </v-row>

    <v-row class="mt-4">
      <v-col cols="12">
        <v-tabs v-model="tab">
          <v-tab value="productos">Productos</v-tab>
          <v-tab value="movimientos">Movimientos</v-tab>
        </v-tabs>

        <v-window v-model="tab">
          <v-window-item value="productos">
            <v-card>
              <v-data-table
                :headers="productosHeaders"
                :items="productos"
                :loading="loading"
                class="elevation-1"
              >
                <template v-slot:item.stockActual="{ item }">
                  <v-chip :color="getStockColor(item.stockActual, item.stockMinimo)">
                    {{ item.stockActual }}
                  </v-chip>
                </template>
              </v-data-table>
            </v-card>
          </v-window-item>

          <v-window-item value="movimientos">
            <v-card>
              <v-data-table
                :headers="movimientosHeaders"
                :items="movimientos"
                :loading="loading"
                class="elevation-1"
              >
                <template v-slot:item.tipoMovimiento="{ item }">
                  <v-chip :color="item.tipoMovimiento === 'ENTRADA' ? 'success' : 'error'">
                    {{ item.tipoMovimiento }}
                  </v-chip>
                </template>
              </v-data-table>
            </v-card>
          </v-window-item>
        </v-window>
      </v-col>
    </v-row>

    <!-- Dialog Nuevo Producto -->
    <v-dialog v-model="showNuevoProducto" max-width="600px">
      <v-card>
        <v-card-title class="bg-primary text-white">Nuevo Producto</v-card-title>
        <v-card-text class="pa-6">
          <v-form @submit.prevent="saveProducto">
            <v-text-field v-model="productoForm.nombre" label="Nombre *" :rules="[v => !!v || 'Requerido']"></v-text-field>
            <v-text-field v-model="productoForm.codigo" label="Código"></v-text-field>
            <v-select v-model="productoForm.categoria" label="Categoría" :items="categorias"></v-select>
            <v-text-field v-model.number="productoForm.precio" label="Precio" type="number" step="0.01"></v-text-field>
            <v-text-field v-model.number="productoForm.stockMinimo" label="Stock Mínimo" type="number"></v-text-field>
            <v-row class="mt-4">
              <v-col cols="6">
                <v-btn color="primary" block type="submit" :loading="loading">Guardar</v-btn>
              </v-col>
              <v-col cols="6">
                <v-btn color="secondary" block @click="showNuevoProducto = false">Cancelar</v-btn>
              </v-col>
            </v-row>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>

    <!-- Dialog Nuevo Movimiento -->
    <v-dialog v-model="showNuevoMovimiento" max-width="600px">
      <v-card>
        <v-card-title class="bg-primary text-white">Registrar Movimiento</v-card-title>
        <v-card-text class="pa-6">
          <v-form @submit.prevent="saveMovimiento">
            <v-autocomplete v-model="movimientoForm.productoId" :items="productos" item-title="nombre" item-value="id" label="Producto *"></v-autocomplete>
            <v-select v-model="movimientoForm.tipoMovimiento" label="Tipo *" :items="['ENTRADA', 'SALIDA']"></v-select>
            <v-text-field v-model.number="movimientoForm.cantidad" label="Cantidad *" type="number"></v-text-field>
            <v-textarea v-model="movimientoForm.motivo" label="Motivo" rows="2"></v-textarea>
            <v-row class="mt-4">
              <v-col cols="6">
                <v-btn color="primary" block type="submit" :loading="loading">Guardar</v-btn>
              </v-col>
              <v-col cols="6">
                <v-btn color="secondary" block @click="showNuevoMovimiento = false">Cancelar</v-btn>
              </v-col>
            </v-row>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script setup>
import { ref, reactive, onMounted, computed, watch } from 'vue'
import { useInventarioStore } from '@/stores/inventarioStore'

const inventarioStore = useInventarioStore()

const tab = ref('productos')
const showNuevoProducto = ref(false)
const showNuevoMovimiento = ref(false)

const loading = computed(() => inventarioStore.loading)
const productos = computed(() => inventarioStore.productos)
const movimientos = computed(() => inventarioStore.movimientos)

const categorias = ['MEDICAMENTO', 'ALIMENTO', 'ACCESORIO', 'HIGIENE', 'OTRO']

const productoForm = reactive({
  nombre: '',
  codigo: '',
  categoria: '',
  precio: 0,
  stockMinimo: 0,
})

const movimientoForm = reactive({
  productoId: null,
  tipoMovimiento: 'ENTRADA',
  cantidad: 0,
  motivo: '',
})

const productosHeaders = [
  { title: 'Código', value: 'codigo' },
  { title: 'Nombre', value: 'nombre' },
  { title: 'Categoría', value: 'categoria' },
  { title: 'Stock', value: 'stockActual' },
  { title: 'Precio', value: 'precio' },
]

const movimientosHeaders = [
  { title: 'Fecha', value: 'fecha' },
  { title: 'Producto', value: 'producto.nombre' },
  { title: 'Tipo', value: 'tipoMovimiento' },
  { title: 'Cantidad', value: 'cantidad' },
  { title: 'Motivo', value: 'motivo' },
]

const getStockColor = (actual, minimo) => {
  if (actual <= minimo) return 'error'
  if (actual <= minimo * 1.5) return 'warning'
  return 'success'
}

const saveProducto = async () => {
  try {
    await inventarioStore.createProducto(productoForm)
    showNuevoProducto.value = false
    Object.assign(productoForm, { nombre: '', codigo: '', categoria: '', precio: 0, stockMinimo: 0 })
  } catch (error) {
    console.error('Error saving producto:', error)
  }
}

const saveMovimiento = async () => {
  try {
    await inventarioStore.createMovimiento(movimientoForm)
    showNuevoMovimiento.value = false
    Object.assign(movimientoForm, { productoId: null, tipoMovimiento: 'ENTRADA', cantidad: 0, motivo: '' })
    await inventarioStore.fetchProductos()
  } catch (error) {
    console.error('Error saving movimiento:', error)
  }
}

watch(tab, (newTab) => {
  if (newTab === 'movimientos') {
    inventarioStore.fetchMovimientos()
  }
})

onMounted(() => {
  inventarioStore.fetchProductos()
})
</script>
