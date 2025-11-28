<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12">
        <h1>Gestión de Inventario</h1>
      </v-col>
    </v-row>

    <!-- Botones principales -->
    <v-row>
      <v-col cols="12" md="6">
        <v-btn color="primary" @click="abrirNuevoProducto" class="mr-2">Nuevo Producto</v-btn>
        <v-btn color="secondary" @click="abrirNuevoMovimiento">Registrar Movimiento</v-btn>
      </v-col>
    </v-row>

    <v-row class="mt-4">
      <v-col cols="12">
        <!-- Sección de alertas -->
        <v-alert v-if="lowStockCount > 0" type="warning" class="mb-4">
          Existen {{ lowStockCount }} productos con stock bajo.
        </v-alert>
        <v-alert v-if="expiringCount > 0" type="warning" class="mb-4">
          Existen {{ expiringCount }} productos próximos a vencer.
        </v-alert>

        <v-tabs v-model="tab">
          <v-tab value="productos">Productos</v-tab>
          <v-tab value="movimientos">Movimientos</v-tab>
        </v-tabs>

        <v-window v-model="tab">
          <!-- Pestaña de productos -->
          <v-window-item value="productos">
            <v-card>
              <v-data-table
                :headers="productosHeaders"
                :items="productos"
                :loading="loading"
                class="elevation-1"
              >
                <!-- Chip para stock -->
                <template v-slot:item.stockActual="{ item }">
                  <v-chip :color="getStockColor(item.stockActual, item.stockMinimo)">
                    {{ item.stockActual }}
                  </v-chip>
                </template>
                <!-- Acciones -->
                <template v-slot:item.acciones="{ item }">
                  <v-icon small class="mr-2" color="info" @click="editarProducto(item)">mdi-pencil</v-icon>
                  <v-icon small color="error" @click="confirmarEliminarProducto(item)">mdi-delete</v-icon>
                </template>
              </v-data-table>
            </v-card>
          </v-window-item>

          <!-- Pestaña de movimientos -->
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
        <v-card-title class="bg-primary text-white">
          {{ isEdit ? 'Editar Producto' : 'Nuevo Producto' }}
        </v-card-title>
        <v-card-text class="pa-6">
          <v-form @submit.prevent="isEdit ? updateProductoForm() : saveProducto()">
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
                <v-btn color="secondary" block @click="cerrarDialogoProducto">Cancelar</v-btn>
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
            <v-autocomplete
              v-model="movimientoForm.productoId"
              :items="productos"
              item-title="nombre"
              item-value="id"
              label="Producto *"
            ></v-autocomplete>
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
const isEdit = ref(false)

const loading = computed(() => inventarioStore.loading)
const productos = computed(() => inventarioStore.productos)
const movimientos = computed(() => inventarioStore.movimientos)

const categorias = ['MEDICAMENTO', 'ALIMENTO', 'ACCESORIO', 'HIGIENE', 'OTRO']

// Formularios reactivos
const productoForm = reactive({
  id: null,
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

// Cabeceras de las tablas
const productosHeaders = [
  { title: 'Código', value: 'codigo' },
  { title: 'Nombre', value: 'nombre' },
  { title: 'Categoría', value: 'categoria' },
  { title: 'Stock', value: 'stockActual' },
  { title: 'Precio', value: 'precio' },
  { title: 'Acciones', value: 'acciones', sortable: false },
]

const movimientosHeaders = [
  { title: 'Fecha', value: 'fecha' },
  { title: 'Producto', value: 'producto.nombre' },
  { title: 'Tipo', value: 'tipoMovimiento' },
  { title: 'Cantidad', value: 'cantidad' },
  { title: 'Motivo', value: 'motivo' },
]

// Cálculos para alertas
const lowStockCount = ref(0)
const expiringCount = ref(0)

const actualizarAlertas = async () => {
  const bajos = await inventarioStore.fetchProductosStockBajo()
  lowStockCount.value = bajos.length
  const porVencer = await inventarioStore.fetchProductosPorVencer()
  expiringCount.value = porVencer.length
}

const getStockColor = (actual, minimo) => {
  if (actual <= minimo) return 'error'
  if (actual <= minimo * 1.5) return 'warning'
  return 'success'
}

// Abrir y cerrar diálogos
const abrirNuevoProducto = () => {
  isEdit.value = false
  Object.assign(productoForm, { id: null, nombre: '', codigo: '', categoria: '', precio: 0, stockMinimo: 0 })
  showNuevoProducto.value = true
}

const cerrarDialogoProducto = () => {
  showNuevoProducto.value = false
}

const abrirNuevoMovimiento = () => {
  Object.assign(movimientoForm, { productoId: null, tipoMovimiento: 'ENTRADA', cantidad: 0, motivo: '' })
  showNuevoMovimiento.value = true
}

// Guardar/actualizar productos
const saveProducto = async () => {
  try {
    await inventarioStore.createProducto(productoForm)
    cerrarDialogoProducto()
    await inventarioStore.fetchProductos()
    await actualizarAlertas()
  } catch (error) {
    console.error('Error saving producto:', error)
  }
}

const updateProductoForm = async () => {
  try {
    await inventarioStore.updateProducto(productoForm.id, productoForm)
    cerrarDialogoProducto()
    await inventarioStore.fetchProductos()
    await actualizarAlertas()
  } catch (error) {
    console.error('Error updating producto:', error)
  }
}

// Guardar movimiento
const saveMovimiento = async () => {
  try {
    await inventarioStore.createMovimiento(movimientoForm)
    showNuevoMovimiento.value = false
    await inventarioStore.fetchProductos()
    await actualizarAlertas()
  } catch (error) {
    console.error('Error saving movimiento:', error)
  }
}

// Editar y eliminar producto
const editarProducto = (item) => {
  isEdit.value = true
  Object.assign(productoForm, { ...item })
  showNuevoProducto.value = true
}

const confirmarEliminarProducto = async (item) => {
  if (confirm(`¿Estás seguro de eliminar el producto "${item.nombre}"?`)) {
    try {
      await inventarioStore.deleteProducto(item.id)
      await inventarioStore.fetchProductos()
      await actualizarAlertas()
    } catch (error) {
      console.error('Error deleting producto:', error)
    }
  }
}

watch(tab, async (newTab) => {
  if (newTab === 'movimientos') {
    await inventarioStore.fetchMovimientos()
  }
})

onMounted(async () => {
  await inventarioStore.fetchProductos()
  await actualizarAlertas()
})
</script>