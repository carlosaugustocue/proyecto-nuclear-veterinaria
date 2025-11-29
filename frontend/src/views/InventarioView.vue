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
            <!-- Filtros de movimientos -->
            <v-card class="mb-4">
              <v-card-text>
                <v-row>
                  <v-col cols="12" md="4">
                    <v-autocomplete
                      v-model="filtroProductoMovimiento"
                      :items="productos"
                      item-title="nombre"
                      item-value="id"
                      label="Filtrar por Producto"
                      clearable
                      prepend-icon="mdi-filter"
                      variant="outlined"
                      density="compact"
                    ></v-autocomplete>
                  </v-col>
                  <v-col cols="12" md="3">
                    <v-select
                      v-model="filtroTipoMovimiento"
                      :items="['ENTRADA', 'SALIDA']"
                      label="Tipo de Movimiento"
                      clearable
                      prepend-icon="mdi-swap-horizontal"
                      variant="outlined"
                      density="compact"
                    ></v-select>
                  </v-col>
                  <v-col cols="12" md="3">
                    <v-text-field
                      v-model="filtroFechaMovimiento"
                      label="Fecha"
                      type="date"
                      clearable
                      prepend-icon="mdi-calendar"
                      variant="outlined"
                      density="compact"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" md="2" class="d-flex align-center">
                    <v-btn
                      color="secondary"
                      variant="outlined"
                      @click="limpiarFiltrosMovimientos"
                      block
                    >
                      <v-icon class="mr-1">mdi-filter-off</v-icon>
                      Limpiar
                    </v-btn>
                  </v-col>
                </v-row>
              </v-card-text>
            </v-card>

            <v-card>
              <v-card-title class="d-flex align-center justify-space-between bg-grey-lighten-4">
                <div>
                  <v-icon class="mr-2">mdi-swap-horizontal</v-icon>
                  Historial de Movimientos
                </div>
                <v-chip color="primary" variant="outlined">
                  {{ movimientosFiltrados.length }} movimientos
                </v-chip>
              </v-card-title>

              <v-data-table
                :headers="movimientosHeaders"
                :items="movimientosFiltrados"
                :loading="loading"
                :sort-by="[{ key: 'fechaMovimiento', order: 'desc' }]"
                class="elevation-1"
                :items-per-page="15"
              >
                <!-- Tipo de movimiento -->
                <template v-slot:item.tipo="{ item }">
                  <v-chip
                    :color="item.tipo === 'ENTRADA' ? 'success' : 'error'"
                    text-color="white"
                    size="small"
                  >
                    <v-icon size="x-small" class="mr-1">
                      {{ item.tipo === 'ENTRADA' ? 'mdi-arrow-down-bold' : 'mdi-arrow-up-bold' }}
                    </v-icon>
                    {{ item.tipo }}
                  </v-chip>
                </template>

                <!-- Producto -->
                <template v-slot:item.productoNombre="{ item }">
                  <div>
                    <div class="font-weight-medium">{{ item.productoNombre }}</div>
                    <div class="text-caption text-grey">{{ item.productoCodigo }}</div>
                  </div>
                </template>

                <!-- Cantidad -->
                <template v-slot:item.cantidad="{ item }">
                  <v-chip
                    :color="item.tipo === 'ENTRADA' ? 'success' : 'error'"
                    variant="outlined"
                    size="small"
                  >
                    {{ item.tipo === 'ENTRADA' ? '+' : '-' }}{{ item.cantidad }}
                  </v-chip>
                </template>

                <!-- Stock Anterior -->
                <template v-slot:item.stockAnterior="{ item }">
                  <span class="text-body-2">{{ item.stockAnterior }}</span>
                </template>

                <!-- Stock Posterior -->
                <template v-slot:item.stockPosterior="{ item }">
                  <v-chip
                    :color="getStockChangeColor(item.stockAnterior, item.stockPosterior)"
                    size="small"
                    variant="outlined"
                  >
                    {{ item.stockPosterior }}
                  </v-chip>
                </template>

                <!-- Motivo -->
                <template v-slot:item.motivo="{ item }">
                  <div class="text-truncate" style="max-width: 200px;">
                    {{ item.motivo }}
                  </div>
                  <div v-if="item.observaciones" class="text-caption text-grey text-truncate" style="max-width: 200px;">
                    {{ item.observaciones }}
                  </div>
                </template>

                <!-- Usuario -->
                <template v-slot:item.usuarioNombre="{ item }">
                  <v-chip size="small" variant="text" prepend-icon="mdi-account">
                    {{ item.usuarioNombre || 'Sistema' }}
                  </v-chip>
                </template>

                <!-- Costo Total -->
                <template v-slot:item.costoTotal="{ item }">
                  <span v-if="item.costoTotal" class="font-weight-medium">
                    ${{ item.costoTotal.toLocaleString('es-ES', { minimumFractionDigits: 2, maximumFractionDigits: 2 }) }}
                  </span>
                  <span v-else class="text-grey">-</span>
                </template>

                <!-- Fecha -->
                <template v-slot:item.fechaMovimiento="{ item }">
                  <div class="text-body-2">{{ formatDateTime(item.fechaMovimiento) }}</div>
                </template>

                <!-- Fila expandible con más detalles -->
                <template v-slot:expanded-row="{ item }">
                  <tr>
                    <td :colspan="movimientosHeaders.length" class="pa-4 bg-grey-lighten-5">
                      <v-row>
                        <v-col cols="12" md="6">
                          <div class="mb-2">
                            <strong>Motivo Completo:</strong>
                            <p class="mt-1">{{ item.motivo }}</p>
                          </div>
                          <div v-if="item.observaciones">
                            <strong>Observaciones:</strong>
                            <p class="mt-1">{{ item.observaciones }}</p>
                          </div>
                        </v-col>
                        <v-col cols="12" md="6">
                          <div class="mb-2" v-if="item.costoUnitario">
                            <strong>Costo Unitario:</strong> ${{ item.costoUnitario.toFixed(2) }}
                          </div>
                          <div class="mb-2" v-if="item.numeroDocumento">
                            <strong>Documento:</strong> {{ item.numeroDocumento }}
                          </div>
                          <div class="mb-2">
                            <strong>Usuario:</strong> {{ item.usuarioNombre || 'Sistema' }}
                          </div>
                          <div class="mb-2">
                            <strong>Fecha Registro:</strong> {{ formatDateTime(item.createdAt) }}
                          </div>
                        </v-col>
                      </v-row>
                    </td>
                  </tr>
                </template>

                <!-- Sin datos -->
                <template v-slot:no-data>
                  <v-alert type="info" variant="tonal" class="my-6">
                    <v-icon class="mr-2">mdi-information</v-icon>
                    No hay movimientos registrados. Los movimientos se registran automáticamente al agregar o retirar productos del inventario.
                  </v-alert>
                </template>
              </v-data-table>
            </v-card>
          </v-window-item>
        </v-window>
      </v-col>
    </v-row>

    <!-- Dialog Nuevo Producto -->
    <v-dialog v-model="showNuevoProducto" max-width="800px">
      <v-card>
        <v-card-title class="bg-primary text-white">
          {{ isEdit ? 'Editar Producto' : 'Nuevo Producto' }}
        </v-card-title>
        <v-card-text class="pa-6">
          <v-form @submit.prevent="isEdit ? updateProductoForm() : saveProducto()">
            <v-row>
              <v-col cols="12" md="6">
                <v-text-field v-model="productoForm.nombre" label="Nombre *" :rules="[v => !!v || 'Requerido']"></v-text-field>
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field v-model="productoForm.codigo" label="Código *" :rules="[v => !!v || 'Requerido']"></v-text-field>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="12">
                <v-textarea v-model="productoForm.descripcion" label="Descripción" rows="2"></v-textarea>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="12" md="6">
                <v-select v-model="productoForm.categoria" label="Categoría *" :items="categorias" :rules="[v => !!v || 'Requerido']"></v-select>
              </v-col>
              <v-col cols="12" md="6">
                <v-select v-model="productoForm.unidadMedida" label="Unidad de Medida *" :items="unidadesMedida" :rules="[v => !!v || 'Requerido']"></v-select>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="12" md="6">
                <v-text-field v-model.number="productoForm.stockInicial" label="Stock Inicial *" type="number" :rules="[v => v >= 0 || 'Debe ser mayor o igual a 0']"></v-text-field>
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field v-model.number="productoForm.stockMinimo" label="Stock Mínimo *" type="number" :rules="[v => v >= 0 || 'Debe ser mayor o igual a 0']"></v-text-field>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="12" md="6">
                <v-text-field v-model.number="productoForm.precioCompra" label="Precio de Compra *" type="number" step="0.01" :rules="[v => v >= 0 || 'Debe ser mayor o igual a 0']"></v-text-field>
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field v-model.number="productoForm.precioVenta" label="Precio de Venta *" type="number" step="0.01" :rules="[v => v >= 0 || 'Debe ser mayor o igual a 0']"></v-text-field>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="12" md="6">
                <v-text-field v-model="productoForm.proveedor" label="Proveedor"></v-text-field>
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field v-model="productoForm.lote" label="Lote"></v-text-field>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="12" md="6">
                <v-text-field v-model="productoForm.fechaVencimiento" label="Fecha de Vencimiento" type="date"></v-text-field>
              </v-col>
            </v-row>

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
    <v-dialog v-model="showNuevoMovimiento" max-width="700px">
      <v-card>
        <v-card-title class="bg-primary text-white d-flex align-center">
          <v-icon class="mr-2">mdi-swap-horizontal</v-icon>
          Registrar Movimiento de Inventario
        </v-card-title>
        <v-card-text class="pa-6">
          <v-form @submit.prevent="saveMovimiento">
            <v-row>
              <v-col cols="12">
                <v-autocomplete
                  v-model="movimientoForm.productoId"
                  :items="productos"
                  item-title="nombre"
                  item-value="id"
                  label="Producto *"
                  :rules="[v => !!v || 'Debe seleccionar un producto']"
                  prepend-icon="mdi-package-variant"
                  variant="outlined"
                ></v-autocomplete>
              </v-col>

              <v-col cols="12" md="6">
                <v-select
                  v-model="movimientoForm.tipo"
                  label="Tipo de Movimiento *"
                  :items="['ENTRADA', 'SALIDA']"
                  :rules="[v => !!v || 'Requerido']"
                  prepend-icon="mdi-swap-horizontal"
                  variant="outlined"
                ></v-select>
              </v-col>

              <v-col cols="12" md="6">
                <v-text-field
                  v-model.number="movimientoForm.cantidad"
                  label="Cantidad *"
                  type="number"
                  :rules="[v => v > 0 || 'La cantidad debe ser mayor a 0']"
                  prepend-icon="mdi-counter"
                  variant="outlined"
                  min="1"
                ></v-text-field>
              </v-col>

              <v-col cols="12">
                <v-text-field
                  v-model.number="movimientoForm.costoUnitario"
                  label="Costo Unitario"
                  type="number"
                  step="0.01"
                  prepend-icon="mdi-currency-usd"
                  variant="outlined"
                  hint="Opcional - Costo por unidad del producto"
                ></v-text-field>
              </v-col>

              <v-col cols="12">
                <v-textarea
                  v-model="movimientoForm.motivo"
                  label="Motivo *"
                  rows="2"
                  :rules="[v => !!v || 'El motivo es obligatorio']"
                  prepend-icon="mdi-text"
                  variant="outlined"
                  hint="Descripción del motivo del movimiento"
                ></v-textarea>
              </v-col>

              <v-col cols="12">
                <v-textarea
                  v-model="movimientoForm.observaciones"
                  label="Observaciones"
                  rows="2"
                  prepend-icon="mdi-note-text"
                  variant="outlined"
                  hint="Información adicional sobre el movimiento"
                ></v-textarea>
              </v-col>
            </v-row>

            <v-divider class="my-4"></v-divider>

            <v-row>
              <v-col cols="6">
                <v-btn
                  color="primary"
                  block
                  type="submit"
                  :loading="loading"
                  prepend-icon="mdi-content-save"
                >
                  Guardar
                </v-btn>
              </v-col>
              <v-col cols="6">
                <v-btn
                  color="secondary"
                  block
                  @click="cerrarDialogoMovimiento"
                  variant="outlined"
                >
                  Cancelar
                </v-btn>
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

// Filtros de movimientos
const filtroProductoMovimiento = ref(null)
const filtroTipoMovimiento = ref(null)
const filtroFechaMovimiento = ref(null)

const loading = computed(() => inventarioStore.loading)
const productos = computed(() => inventarioStore.productos)
const movimientos = computed(() => inventarioStore.movimientos)

// Movimientos filtrados
const movimientosFiltrados = computed(() => {
  let filtered = movimientos.value

  if (filtroProductoMovimiento.value) {
    filtered = filtered.filter(m => m.productoId === filtroProductoMovimiento.value)
  }

  if (filtroTipoMovimiento.value) {
    filtered = filtered.filter(m => m.tipo === filtroTipoMovimiento.value)
  }

  if (filtroFechaMovimiento.value) {
    filtered = filtered.filter(m => {
      if (!m.fechaMovimiento) return false
      const movDate = new Date(m.fechaMovimiento).toISOString().split('T')[0]
      return movDate === filtroFechaMovimiento.value
    })
  }

  return filtered
})

const categorias = ['MEDICAMENTO', 'ALIMENTO', 'ACCESORIO', 'VACUNA', 'MATERIAL', 'INSUMO']
const unidadesMedida = ['UNIDAD', 'KILOGRAMO', 'GRAMO', 'LITRO', 'ML', 'CAJA', 'PAQUETE', 'FRASCO', 'AMPOLLA', 'TABLETA', 'CAPSULA', 'SOBRE']

// Formularios reactivos
const productoForm = reactive({
  id: null,
  nombre: '',
  codigo: '',
  descripcion: '',
  categoria: null,
  unidadMedida: null,
  stockInicial: 0,
  stockMinimo: 0,
  precioCompra: 0,
  precioVenta: 0,
  proveedor: '',
  fechaVencimiento: null,
  lote: '',
})

const movimientoForm = reactive({
  productoId: null,
  tipo: 'ENTRADA',
  cantidad: 1,
  motivo: '',
  costoUnitario: null,
  observaciones: '',
})

// Cabeceras de las tablas
const productosHeaders = [
  { title: 'Código', value: 'codigo' },
  { title: 'Nombre', value: 'nombre' },
  { title: 'Categoría', value: 'categoria' },
  { title: 'Stock', value: 'stockActual' },
  { title: 'Precio Venta', value: 'precioVenta' },
  { title: 'Unidad', value: 'unidadMedida' },
  { title: 'Acciones', value: 'acciones', sortable: false },
]

const movimientosHeaders = [
  { title: 'Fecha', value: 'fechaMovimiento', width: '140' },
  { title: 'Producto', value: 'productoNombre' },
  { title: 'Tipo', value: 'tipo', width: '120' },
  { title: 'Cantidad', value: 'cantidad', align: 'center', width: '100' },
  { title: 'Stock Anterior', value: 'stockAnterior', align: 'center', width: '120' },
  { title: 'Stock Posterior', value: 'stockPosterior', align: 'center', width: '120' },
  { title: 'Motivo', value: 'motivo' },
  { title: 'Usuario', value: 'usuarioNombre', width: '150' },
  { title: 'Costo Total', value: 'costoTotal', align: 'end', width: '120' },
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

const getStockChangeColor = (anterior, posterior) => {
  if (posterior > anterior) return 'success' // Incrementó
  if (posterior < anterior) return 'error'   // Decrementó
  return 'grey'                               // Sin cambio
}

const limpiarFiltrosMovimientos = () => {
  filtroProductoMovimiento.value = null
  filtroTipoMovimiento.value = null
  filtroFechaMovimiento.value = null
}

// Abrir y cerrar diálogos
const abrirNuevoProducto = () => {
  isEdit.value = false
  Object.assign(productoForm, {
    id: null,
    nombre: '',
    codigo: '',
    descripcion: '',
    categoria: null,
    unidadMedida: null,
    stockInicial: 0,
    stockMinimo: 0,
    precioCompra: 0,
    precioVenta: 0,
    proveedor: '',
    fechaVencimiento: null,
    lote: '',
  })
  showNuevoProducto.value = true
}

const cerrarDialogoProducto = () => {
  showNuevoProducto.value = false
}

const abrirNuevoMovimiento = () => {
  Object.assign(movimientoForm, {
    productoId: null,
    tipo: 'ENTRADA',
    cantidad: 1,
    motivo: '',
    costoUnitario: null,
    observaciones: ''
  })
  showNuevoMovimiento.value = true
}

const cerrarDialogoMovimiento = () => {
  showNuevoMovimiento.value = false
  Object.assign(movimientoForm, {
    productoId: null,
    tipo: 'ENTRADA',
    cantidad: 1,
    motivo: '',
    costoUnitario: null,
    observaciones: ''
  })
}

// Guardar/actualizar productos
const saveProducto = async () => {
  try {
    const formData = {
      nombre: productoForm.nombre,
      codigo: productoForm.codigo,
      descripcion: productoForm.descripcion || null,
      categoria: productoForm.categoria,
      unidadMedida: productoForm.unidadMedida,
      stockInicial: parseInt(productoForm.stockInicial) || 0,
      stockMinimo: parseInt(productoForm.stockMinimo) || 0,
      precioCompra: parseFloat(productoForm.precioCompra) || 0,
      precioVenta: parseFloat(productoForm.precioVenta) || 0,
      proveedor: productoForm.proveedor || null,
      fechaVencimiento: productoForm.fechaVencimiento || null,
      lote: productoForm.lote || null,
    }
    await inventarioStore.createProducto(formData)
    cerrarDialogoProducto()
    await inventarioStore.fetchProductos()
    await actualizarAlertas()
  } catch (error) {
    console.error('Error saving producto:', error)
  }
}

const updateProductoForm = async () => {
  try {
    const formData = {
      nombre: productoForm.nombre,
      codigo: productoForm.codigo,
      descripcion: productoForm.descripcion || null,
      categoria: productoForm.categoria,
      unidadMedida: productoForm.unidadMedida,
      stockInicial: parseInt(productoForm.stockInicial) || 0,
      stockMinimo: parseInt(productoForm.stockMinimo) || 0,
      precioCompra: parseFloat(productoForm.precioCompra) || 0,
      precioVenta: parseFloat(productoForm.precioVenta) || 0,
      proveedor: productoForm.proveedor || null,
      fechaVencimiento: productoForm.fechaVencimiento || null,
      lote: productoForm.lote || null,
    }
    await inventarioStore.updateProducto(productoForm.id, formData)
    cerrarDialogoProducto()
    await inventarioStore.fetchProductos()
    await actualizarAlertas()
  } catch (error) {
    console.error('Error updating producto:', error)
  }
}

// Guardar movimiento
const saveMovimiento = async () => {
  // Validación básica
  if (!movimientoForm.productoId) {
    console.error('Debe seleccionar un producto')
    return
  }
  if (!movimientoForm.motivo || movimientoForm.motivo.trim() === '') {
    console.error('El motivo es obligatorio')
    return
  }
  if (!movimientoForm.cantidad || movimientoForm.cantidad <= 0) {
    console.error('La cantidad debe ser mayor a 0')
    return
  }

  try {
    const formData = {
      productoId: movimientoForm.productoId,
      tipo: movimientoForm.tipo,
      cantidad: parseInt(movimientoForm.cantidad),
      motivo: movimientoForm.motivo.trim(),
      costoUnitario: movimientoForm.costoUnitario ? parseFloat(movimientoForm.costoUnitario) : null,
      observaciones: movimientoForm.observaciones ? movimientoForm.observaciones.trim() : null
    }

    await inventarioStore.createMovimiento(formData)
    cerrarDialogoMovimiento()
    await inventarioStore.fetchProductos()
    await inventarioStore.fetchMovimientos()
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

const formatDateTime = (dateTimeString) => {
  if (!dateTimeString) return '-'
  const date = new Date(dateTimeString)
  return date.toLocaleString('es-ES', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
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