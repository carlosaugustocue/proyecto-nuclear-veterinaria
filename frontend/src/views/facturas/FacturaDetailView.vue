<template>
  <v-container fluid class="pa-4 pa-md-6">
    <!-- Botón volver -->
    <v-row class="mb-4">
      <v-col cols="12">
        <v-btn
          @click="$router.back()"
          variant="text"
          prepend-icon="mdi-arrow-left"
          class="mb-2"
        >
          Volver
            </v-btn>
      </v-col>
    </v-row>

    <!-- Loading -->
    <v-row v-if="loading && !factura">
      <v-col cols="12" class="text-center">
        <v-progress-circular indeterminate color="primary" size="64"></v-progress-circular>
        <p class="mt-4 text-grey">Cargando información de la factura...</p>
              </v-col>
    </v-row>

    <!-- Contenido principal -->
    <template v-else-if="factura">
      <!-- Header con número y estado -->
      <v-row class="mb-4">
        <v-col cols="12">
          <v-card class="elevation-3">
            <v-card-title class="bg-primary text-white d-flex align-center">
              <v-icon class="mr-2" size="28">mdi-receipt</v-icon>
              <span class="text-h5">Factura {{ factura.numeroFactura || factura.numero || `#${factura.id}` }}</span>
              <v-spacer></v-spacer>
              <v-chip
                :color="getEstadoColor(factura.estadoNombre || factura.estado)"
                size="large"
                class="font-weight-bold"
                text-color="white"
              >
                {{ factura.estadoNombre || factura.estado || 'N/A' }}
                  </v-chip>
            </v-card-title>
          </v-card>
              </v-col>
            </v-row>

      <v-row>
        <!-- Columna izquierda - Información principal -->
        <v-col cols="12" md="8">
          <!-- Información del Cliente -->
          <v-card class="mb-4 elevation-2">
            <v-card-title class="bg-blue-lighten-5 d-flex align-center">
              <v-icon color="blue" class="mr-2">mdi-account</v-icon>
              <span class="font-weight-bold">Información del Cliente</span>
            </v-card-title>
            <v-card-text class="pa-4">
              <div class="text-h6">{{ factura.clienteNombre || factura.cliente || 'No especificado' }}</div>
              <div v-if="factura.citaId" class="mt-2">
                <v-chip size="small" color="info" variant="outlined">
                  <v-icon size="small" class="mr-1">mdi-calendar-clock</v-icon>
                  Cita #{{ factura.citaId }}
                </v-chip>
              </div>
            </v-card-text>
          </v-card>

          <!-- Items de la Factura -->
          <v-card class="mb-4 elevation-2">
            <v-card-title class="bg-green-lighten-5 d-flex align-center justify-space-between">
              <div class="d-flex align-center">
                <v-icon color="green" class="mr-2">mdi-format-list-bulleted</v-icon>
                <span class="font-weight-bold">Items / Servicios</span>
              </div>
              <v-btn
                v-if="puedeAgregarItems"
                color="green"
                size="small"
                prepend-icon="mdi-plus"
                @click="abrirDialogoAgregarItem"
              >
                Agregar Item
              </v-btn>
            </v-card-title>
            <v-card-text class="pa-0">
              <v-table v-if="factura.detalles && factura.detalles.length > 0">
              <thead>
                <tr>
                  <th>Descripción</th>
                  <th>Cantidad</th>
                    <th>Precio Unit.</th>
                  <th>Subtotal</th>
                    <th v-if="puedeAgregarItems">Acciones</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="item in factura.detalles" :key="item.id">
                    <td>
                      <div class="font-weight-medium">{{ item.descripcion }}</div>
                      <div class="text-caption text-grey mt-1">
                        <v-chip 
                          v-if="item.tipoServicioId || item.tipoServicioNombre" 
                          size="x-small" 
                          color="primary" 
                          variant="outlined"
                          class="mr-1"
                        >
                          <v-icon size="x-small" class="mr-1">mdi-medical-bag</v-icon>
                          {{ item.tipoServicioNombre || 'Servicio' }}
                        </v-chip>
                        <v-chip 
                          v-if="item.productoId || item.productoNombre" 
                          size="x-small" 
                          color="success" 
                          variant="outlined"
                        >
                          <v-icon size="x-small" class="mr-1">mdi-package-variant</v-icon>
                          {{ item.productoNombre || 'Producto' }}
                        </v-chip>
                      </div>
                    </td>
                  <td>{{ item.cantidad }}</td>
                  <td>${{ formatCurrency(item.precioUnitario) }}</td>
                    <td class="font-weight-bold">${{ formatCurrency(item.subtotal || (item.cantidad * item.precioUnitario)) }}</td>
                    <td v-if="puedeAgregarItems">
                      <v-btn
                        icon
                        size="small"
                        color="error"
                        variant="text"
                        @click="eliminarItem(item.id)"
                        :loading="eliminandoItem === item.id"
                      >
                        <v-icon size="small">mdi-delete</v-icon>
                      </v-btn>
                    </td>
                </tr>
              </tbody>
            </v-table>
              <v-alert v-else type="info" variant="tonal" class="ma-4">
                <v-icon class="mr-2">mdi-information</v-icon>
                No hay items registrados en esta factura
              </v-alert>
            </v-card-text>
          </v-card>

          <!-- Resumen Financiero -->
          <v-card class="mb-4 elevation-2">
            <v-card-title class="bg-purple-lighten-5 d-flex align-center">
              <v-icon color="purple" class="mr-2">mdi-calculator</v-icon>
              <span class="font-weight-bold">Resumen Financiero</span>
            </v-card-title>
            <v-card-text class="pa-4">
              <v-row>
              <v-col cols="12" md="6" offset-md="6">
                  <div class="text-right mb-3">
                    <div class="d-flex justify-space-between mb-2">
                      <span>Subtotal:</span>
                      <strong>${{ formatCurrency(factura.subtotal || 0) }}</strong>
                    </div>
                    <div v-if="(factura.totalDescuentos || 0) > 0" class="d-flex justify-space-between mb-2 text-error">
                      <span>Descuentos:</span>
                      <strong>-${{ formatCurrency(factura.totalDescuentos) }}</strong>
                    </div>
                    <div v-if="(factura.totalImpuestos || 0) > 0" class="d-flex justify-space-between mb-2">
                      <span>Impuestos:</span>
                      <strong>${{ formatCurrency(factura.totalImpuestos) }}</strong>
                    </div>
                    <v-divider class="my-3"></v-divider>
                    <div class="d-flex justify-space-between text-h6 font-weight-bold text-primary">
                      <span>Total:</span>
                      <strong>${{ formatCurrency(factura.total || 0) }}</strong>
                    </div>
                    <div v-if="(factura.totalPagado || 0) > 0" class="d-flex justify-space-between mt-3">
                      <span>Total Pagado:</span>
                      <strong class="text-success">${{ formatCurrency(factura.totalPagado) }}</strong>
                </div>
                    <div v-if="(factura.saldoPendiente || 0) > 0" class="d-flex justify-space-between mt-2">
                      <span>Saldo Pendiente:</span>
                      <strong class="text-warning">${{ formatCurrency(factura.saldoPendiente) }}</strong>
                </div>
                </div>
              </v-col>
            </v-row>
            </v-card-text>
          </v-card>

            <!-- Pagos -->
          <v-card class="mb-4 elevation-2">
            <v-card-title class="bg-amber-lighten-5 d-flex align-center justify-space-between">
              <div class="d-flex align-center">
                <v-icon color="amber-darken-2" class="mr-2">mdi-cash-multiple</v-icon>
                <span class="font-weight-bold">Pagos Registrados</span>
              </div>
              <v-btn
                v-if="puedeRecibirPagos"
                color="success"
                size="small"
                prepend-icon="mdi-cash-plus"
                @click="abrirDialogoRegistrarPago"
              >
                Registrar Pago
              </v-btn>
            </v-card-title>
            <v-card-text class="pa-0">
              <v-list v-if="factura.pagos && factura.pagos.length > 0" lines="two">
                <v-list-item
                  v-for="pago in factura.pagos"
                  :key="pago.id"
                >
                  <template v-slot:prepend>
                    <v-avatar color="amber" size="40">
                      <v-icon color="white">mdi-cash</v-icon>
                    </v-avatar>
                  </template>
                  <v-list-item-title>{{ pago.metodoPagoNombre || pago.metodo || 'Pago' }}</v-list-item-title>
                  <v-list-item-subtitle>{{ formatDate(pago.fecha || pago.fechaPago) }}</v-list-item-subtitle>
                <template v-slot:append>
                    <span class="font-weight-bold text-h6">${{ formatCurrency(pago.monto) }}</span>
                </template>
              </v-list-item>
            </v-list>
              <v-alert v-else type="info" variant="tonal" class="ma-4">
                <div class="d-flex align-center">
                  <v-icon class="mr-2">mdi-information</v-icon>
                  <div>
                    <div>No hay pagos registrados</div>
                    <v-btn
                      v-if="puedeRecibirPagos"
                      color="success"
                      size="small"
                      prepend-icon="mdi-cash-plus"
                      @click="abrirDialogoRegistrarPago"
                      class="mt-2"
                    >
                      Registrar Primer Pago
                    </v-btn>
                  </div>
                </div>
              </v-alert>
            </v-card-text>
          </v-card>
        </v-col>

        <!-- Columna derecha - Información secundaria y acciones -->
        <v-col cols="12" md="4">
          <!-- Información de la Factura -->
          <v-card class="mb-4 elevation-2">
            <v-card-title class="bg-grey-lighten-4 d-flex align-center">
              <v-icon class="mr-2">mdi-information</v-icon>
              <span class="font-weight-bold">Información</span>
            </v-card-title>
            <v-card-text class="pa-4">
              <div class="mb-3">
                <div class="text-caption text-grey mb-1">Fecha de Emisión</div>
                <div class="text-body-1 font-weight-medium">{{ formatDate(factura.fechaEmision || factura.fecha) }}</div>
              </div>
              <div v-if="factura.fechaVencimiento" class="mb-3">
                <div class="text-caption text-grey mb-1">Fecha de Vencimiento</div>
                <div class="text-body-1 font-weight-medium">{{ formatDate(factura.fechaVencimiento) }}</div>
              </div>
              <div v-if="factura.observaciones" class="mb-3">
                <div class="text-caption text-grey mb-1">Observaciones</div>
                <div class="text-body-2">{{ factura.observaciones }}</div>
              </div>
            </v-card-text>
          </v-card>

            <!-- Acciones -->
          <v-card class="mb-4 elevation-2">
            <v-card-title class="bg-grey-lighten-4 d-flex align-center">
              <v-icon class="mr-2">mdi-cog</v-icon>
              <span class="font-weight-bold">Acciones</span>
            </v-card-title>
            <v-card-text class="pa-3">
              <v-list density="compact">
                <v-list-item
                  @click="abrirDialogoRegistrarPago"
                  class="mb-2 rounded"
                  style="cursor: pointer;"
                  :disabled="!puedeRecibirPagos"
                >
                  <template v-slot:prepend>
                    <v-icon color="success">mdi-cash-plus</v-icon>
                  </template>
                  <v-list-item-title>Registrar Pago</v-list-item-title>
                  <v-list-item-subtitle v-if="!puedeRecibirPagos">
                    Factura no puede recibir pagos
                  </v-list-item-subtitle>
                </v-list-item>

                <v-list-item
                  :to="`/facturas/${factura.id}/editar`"
                  class="mb-2 rounded"
                  style="cursor: pointer;"
                >
                  <template v-slot:prepend>
                    <v-icon color="info">mdi-pencil</v-icon>
                  </template>
                  <v-list-item-title>Editar Factura</v-list-item-title>
                </v-list-item>

                <v-list-item
                  @click="descargarPDF"
                  class="mb-2 rounded"
                  style="cursor: pointer;"
                >
                  <template v-slot:prepend>
                    <v-icon color="primary">mdi-file-pdf-box</v-icon>
                  </template>
                  <v-list-item-title>Descargar PDF</v-list-item-title>
                </v-list-item>

                <v-divider class="my-2"></v-divider>

                <v-list-item
                  @click="deleteFactura"
                  class="mb-2 rounded text-error"
                  style="cursor: pointer;"
                >
                  <template v-slot:prepend>
                    <v-icon color="error">mdi-delete</v-icon>
                  </template>
                  <v-list-item-title>Eliminar Factura</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-card-text>
          </v-card>
              </v-col>
            </v-row>
    </template>

    <!-- Error o no encontrada -->
    <v-row v-else>
      <v-col cols="12">
        <v-card>
          <v-card-text class="pa-6 text-center">
            <v-icon color="error" size="64" class="mb-4">mdi-alert-circle</v-icon>
            <div class="text-h6 mb-2">Factura no encontrada</div>
            <div class="text-body-2 text-grey mb-4">
              La factura que buscas no existe o ha sido eliminada.
            </div>
            <v-btn
              to="/facturas"
              color="primary"
              prepend-icon="mdi-arrow-left"
            >
              Volver a Facturas
            </v-btn>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <!-- Dialog para registrar pago -->
    <v-dialog v-model="dialogRegistrarPago" width="600" persistent>
      <v-card>
        <v-card-title class="bg-success text-white">
          Registrar Pago
        </v-card-title>
        <v-card-text class="pa-4">
          <v-alert type="info" variant="tonal" class="mb-4">
            <div class="text-body-2">
              <strong>Saldo Pendiente:</strong> ${{ formatCurrency(factura?.saldoPendiente || factura?.total || 0) }}
            </div>
          </v-alert>

          <v-select
            v-model="nuevoPago.metodoPago"
            label="Método de Pago *"
            :items="metodosPago"
            item-title="label"
            item-value="value"
            prepend-icon="mdi-cash-multiple"
            :rules="[v => !!v || 'El método de pago es obligatorio']"
            class="mb-4"
          >
            <template v-slot:item="{ props, item }">
              <v-list-item v-bind="props">
                <template v-slot:prepend>
                  <v-icon>{{ item.raw.icon }}</v-icon>
                </template>
                <v-list-item-title>{{ item.raw.label }}</v-list-item-title>
                <v-list-item-subtitle v-if="item.raw.comision > 0">
                  Comisión: {{ (item.raw.comision * 100).toFixed(1) }}%
                </v-list-item-subtitle>
              </v-list-item>
            </template>
          </v-select>

          <v-text-field
            v-model.number="nuevoPago.monto"
            label="Monto *"
            type="number"
            step="0.01"
            min="0"
            :max="factura?.saldoPendiente || factura?.total || 0"
            prepend-icon="mdi-currency-usd"
            :rules="[
              v => !!v || 'El monto es obligatorio',
              v => (v && v > 0) || 'El monto debe ser mayor a 0',
              v => (!factura || !factura.saldoPendiente || v <= factura.saldoPendiente) || `El monto no puede exceder el saldo pendiente ($${formatCurrency(factura?.saldoPendiente || 0)})`
            ]"
            class="mb-4"
          ></v-text-field>

          <v-text-field
            v-model="nuevoPago.fechaPago"
            label="Fecha de Pago *"
            type="date"
            prepend-icon="mdi-calendar"
            :rules="[v => !!v || 'La fecha de pago es obligatoria']"
            class="mb-4"
          ></v-text-field>

          <v-text-field
            v-model="nuevoPago.numeroReferencia"
            label="Número de Referencia"
            prepend-icon="mdi-identifier"
            class="mb-4"
            hint="Número de transacción, referencia bancaria, etc."
            persistent-hint
          ></v-text-field>

          <v-text-field
            v-model="nuevoPago.numeroVoucher"
            label="Número de Voucher"
            prepend-icon="mdi-receipt"
            class="mb-4"
            hint="Número de voucher o comprobante (opcional)"
            persistent-hint
          ></v-text-field>

          <v-textarea
            v-model="nuevoPago.observaciones"
            label="Observaciones"
            rows="3"
            prepend-icon="mdi-text"
            class="mb-4"
          ></v-textarea>

          <!-- Mostrar cálculo de comisión si aplica -->
          <v-alert
            v-if="nuevoPago.metodoPago && nuevoPago.monto > 0 && calcularComision(nuevoPago.metodoPago, nuevoPago.monto) > 0"
            type="warning"
            variant="tonal"
            class="mt-4"
          >
            <div class="d-flex justify-space-between align-center">
              <span>Comisión:</span>
              <strong>-${{ formatCurrency(calcularComision(nuevoPago.metodoPago, nuevoPago.monto)) }}</strong>
            </div>
            <div class="d-flex justify-space-between align-center mt-2">
              <span>Monto Neto a Recibir:</span>
              <strong class="text-success">${{ formatCurrency(nuevoPago.monto - calcularComision(nuevoPago.metodoPago, nuevoPago.monto)) }}</strong>
            </div>
          </v-alert>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="secondary" @click="cancelarRegistrarPago">Cancelar</v-btn>
          <v-btn
            color="success"
            @click="confirmarRegistrarPago"
            :loading="registrandoPago"
            :disabled="!nuevoPago.metodoPago || !nuevoPago.monto || !nuevoPago.fechaPago"
          >
            Registrar Pago
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Dialog para agregar item -->
    <v-dialog v-model="dialogAgregarItem" width="600" persistent>
      <v-card>
        <v-card-title class="bg-primary text-white">
          Agregar Item / Servicio
        </v-card-title>
        <v-card-text class="pa-4">
          <!-- Tipo de Item -->
          <v-radio-group v-model="tipoItemSeleccionado" inline class="mb-4">
            <v-radio label="Servicio" value="servicio" color="primary"></v-radio>
            <v-radio label="Producto del Inventario" value="producto" color="success"></v-radio>
            <v-radio label="Item Manual" value="manual" color="info"></v-radio>
          </v-radio-group>

          <v-divider class="my-4"></v-divider>

          <!-- Selección de Servicio -->
          <v-select
            v-if="tipoItemSeleccionado === 'servicio'"
            v-model="nuevoItem.tipoServicioId"
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
                  ${{ formatCurrency(item.raw.precioBase || item.raw.precio || 0) }}
                </v-list-item-subtitle>
              </v-list-item>
            </template>
          </v-select>

          <!-- Selección de Producto del Inventario -->
          <div v-if="tipoItemSeleccionado === 'producto'">
            <v-select
              v-model="nuevoItem.productoId"
              label="Seleccionar Producto"
              :items="productos"
              item-title="nombre"
              item-value="id"
              clearable
              class="mb-4"
              prepend-icon="mdi-package-variant"
              :loading="inventarioStore.loading"
              :disabled="inventarioStore.loading"
              :no-data-text="productos.length === 0 && !inventarioStore.loading ? 'No hay productos con stock disponible' : 'Cargando productos...'"
              @update:model-value="onProductoSeleccionado"
            >
              <template v-slot:item="{ props, item }">
                <v-list-item v-bind="props">
                  <template v-slot:prepend>
                    <v-icon>mdi-package-variant</v-icon>
                  </template>
                  <v-list-item-title>{{ item.raw.nombre }}</v-list-item-title>
                  <v-list-item-subtitle>
                    Stock: {{ item.raw.stockActual || 0 }} | Precio: ${{ formatCurrency(item.raw.precioVenta || 0) }}
                  </v-list-item-subtitle>
                </v-list-item>
              </template>
            </v-select>
            
            <!-- Mensaje informativo si no hay productos -->
            <v-alert
              v-if="productos.length === 0 && !inventarioStore.loading"
              type="warning"
              variant="tonal"
              class="mb-4"
            >
              <v-icon class="mr-2">mdi-alert</v-icon>
              No hay productos con stock disponible en el inventario. 
              Por favor, ingrese productos al inventario primero.
            </v-alert>
          </div>

          <!-- Alerta de stock disponible -->
          <v-alert
            v-if="tipoItemSeleccionado === 'producto' && nuevoItem.stockDisponible !== null"
            type="info"
            variant="tonal"
            class="mb-4"
          >
            Stock disponible: <strong>{{ nuevoItem.stockDisponible }}</strong>
            <span v-if="nuevoItem.cantidad > nuevoItem.stockDisponible" class="text-error ml-2">
              (Cantidad excede el stock disponible)
            </span>
          </v-alert>

          <v-divider v-if="tipoItemSeleccionado !== 'manual'" class="my-4"></v-divider>

          <!-- Campos manuales -->
          <v-text-field
            v-model="nuevoItem.descripcion"
            label="Descripción *"
            class="mb-4"
            :rules="[v => !!v || 'Este campo es requerido']"
            prepend-icon="mdi-text"
          ></v-text-field>

          <v-row>
            <v-col cols="6">
              <v-text-field
                v-model.number="nuevoItem.cantidad"
                label="Cantidad *"
                type="number"
                min="1"
                :max="tipoItemSeleccionado === 'producto' && nuevoItem.stockDisponible ? nuevoItem.stockDisponible : undefined"
                :rules="[
                  v => !!v || 'Este campo es requerido',
                  v => (v && v > 0) || 'Debe ser mayor a 0',
                  v => {
                    if (tipoItemSeleccionado === 'producto' && nuevoItem.stockDisponible !== null) {
                      return v <= nuevoItem.stockDisponible || 'La cantidad no puede exceder el stock disponible'
                    }
                    return true
                  }
                ]"
                prepend-icon="mdi-numeric"
              ></v-text-field>
            </v-col>
            <v-col cols="6">
              <v-text-field
                v-model.number="nuevoItem.precioUnitario"
                label="Precio Unitario *"
                type="number"
                step="0.01"
                min="0"
                :rules="[
                  v => !!v || 'Este campo es requerido',
                  v => (v && v > 0) || 'Debe ser mayor a 0'
                ]"
                prepend-icon="mdi-currency-usd"
              ></v-text-field>
            </v-col>
          </v-row>

          <!-- Mostrar subtotal calculado -->
          <v-alert
            type="info"
            variant="tonal"
            class="mt-4"
            v-if="nuevoItem.cantidad > 0 && nuevoItem.precioUnitario > 0"
          >
            <div class="d-flex justify-space-between align-center">
              <span>Subtotal:</span>
              <strong class="text-h6">
                ${{ formatCurrency(nuevoItem.cantidad * nuevoItem.precioUnitario) }}
              </strong>
            </div>
          </v-alert>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="secondary" @click="cancelarAgregarItem">Cancelar</v-btn>
          <v-btn
            color="primary"
            @click="confirmarAgregarItem"
            :loading="agregandoItem"
            :disabled="!nuevoItem.descripcion || !nuevoItem.cantidad || !nuevoItem.precioUnitario"
          >
            Agregar
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script setup>
import { computed, onMounted, ref, reactive } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useFacturasStore } from '@/stores/facturasStore'
import { useReferenceData } from '@/composables/useReferenceData'
import { useNotification } from '@/composables/useNotification'
import { useAuthStore } from '@/stores/authStore'
import { useInventarioStore } from '@/stores/inventarioStore'

const router = useRouter()
const route = useRoute()
const facturasStore = useFacturasStore()
const authStore = useAuthStore()
const inventarioStore = useInventarioStore()
const { fetchTiposServicio } = useReferenceData()
const { showSuccess, showError } = useNotification()

const loading = computed(() => facturasStore.loading)
const factura = computed(() => facturasStore.currentFactura)

// Verificar que las funciones necesarias existan al montar el componente
onMounted(() => {
  getFactura()
  
  // Verificar funciones críticas y mostrar advertencia si faltan
  const funcionesFaltantes = []
  if (typeof facturasStore.agregarDetalle !== 'function') {
    funcionesFaltantes.push('agregarDetalle')
    console.warn('⚠️ facturasStore.agregarDetalle no está disponible. Se usará API directa como fallback.')
  }
  if (typeof facturasStore.eliminarDetalle !== 'function') {
    funcionesFaltantes.push('eliminarDetalle')
    console.warn('⚠️ facturasStore.eliminarDetalle no está disponible. Se usará API directa como fallback.')
  }
  
  if (funcionesFaltantes.length > 0) {
    console.warn('⚠️ Funciones faltantes en el store (probablemente necesita recarga):', funcionesFaltantes)
    console.log('✅ Funciones disponibles en el store:', Object.keys(facturasStore).filter(k => typeof facturasStore[k] === 'function'))
  }
})

const tiposServicio = ref([])
const productos = ref([])
const dialogAgregarItem = ref(false)
const agregandoItem = ref(false)
const eliminandoItem = ref(null)
const tipoItemSeleccionado = ref('servicio') // 'servicio', 'producto', 'manual'

const dialogRegistrarPago = ref(false)
const registrandoPago = ref(false)

const nuevoItem = reactive({
  tipoServicioId: null,
  productoId: null,
  descripcion: '',
  cantidad: 1,
  precioUnitario: 0,
  stockDisponible: null,
})

const nuevoPago = reactive({
  metodoPago: null,
  monto: null,
  fechaPago: new Date().toISOString().split('T')[0],
  numeroReferencia: '',
  numeroVoucher: '',
  observaciones: '',
})

// Métodos de pago disponibles
const metodosPago = [
  { value: 'EFECTIVO', label: 'Efectivo', icon: 'mdi-cash', comision: 0.0 },
  { value: 'TARJETA_CREDITO', label: 'Tarjeta de Crédito', icon: 'mdi-credit-card', comision: 0.03 },
  { value: 'TARJETA_DEBITO', label: 'Tarjeta de Débito', icon: 'mdi-card', comision: 0.01 },
  { value: 'TRANSFERENCIA', label: 'Transferencia Bancaria', icon: 'mdi-bank-transfer', comision: 0.0 },
  { value: 'YAPE', label: 'Yape', icon: 'mdi-cellphone', comision: 0.0 },
  { value: 'PLIN', label: 'Plin', icon: 'mdi-cellphone', comision: 0.0 },
]

const puedeAgregarItems = computed(() => {
  if (!factura.value) return false
  const estado = (factura.value.estadoNombre || factura.value.estado || '').toLowerCase()
  return estado === 'pendiente' || estado === 'PENDIENTE'
})

const puedeRecibirPagos = computed(() => {
  if (!factura.value) return false
  const estado = (factura.value.estadoNombre || factura.value.estado || '').toLowerCase()
  const saldoPendiente = factura.value.saldoPendiente || factura.value.total || 0
  return (estado === 'pendiente' || estado === 'PENDIENTE' || estado === 'parcial' || estado === 'PARCIAL') && saldoPendiente > 0
})

const getFactura = async () => {
  try {
    await facturasStore.fetchFacturaById(route.params.id)
    await cargarTiposServicio()
    await cargarProductos()
  } catch (error) {
    console.error('Error al cargar factura:', error)
    showError('Error al cargar la información de la factura')
  }
}

const cargarTiposServicio = async () => {
  try {
    tiposServicio.value = await fetchTiposServicio()
  } catch (error) {
    console.error('Error al cargar tipos de servicio:', error)
  }
}

const cargarProductos = async () => {
  try {
    console.log('Cargando productos del inventario...')
    const productosCargados = await inventarioStore.fetchProductos()
    console.log('Productos obtenidos del store:', productosCargados?.length || inventarioStore.productos?.length || 0)
    console.log('Productos raw:', inventarioStore.productos)
    
    // Usar los productos retornados o los del store
    const productosDisponibles = productosCargados || inventarioStore.productos || []
    
    // Filtrar solo productos con stock disponible
    // El backend ya filtra productos activos, así que solo verificamos stock
    productos.value = productosDisponibles.filter(p => {
      const stock = p.stockActual || 0
      const tieneStock = stock > 0
      if (!tieneStock) {
        console.log(`Producto ${p.nombre} (ID: ${p.id}) no tiene stock disponible: ${stock}`)
      }
      return tieneStock
    })
    console.log('Productos con stock disponible:', productos.value.length)
    console.log('Lista de productos filtrados:', productos.value.map(p => ({ id: p.id, nombre: p.nombre, stock: p.stockActual })))
    
    if (productos.value.length === 0) {
      console.warn('No hay productos con stock disponible en el inventario')
      if (productosDisponibles.length > 0) {
        console.warn('Hay productos en el inventario pero ninguno tiene stock:', productosDisponibles.map(p => ({ nombre: p.nombre, stock: p.stockActual })))
      }
    }
  } catch (error) {
    console.error('Error al cargar productos:', error)
    const errorMsg = error?.response?.data?.message || error?.message || 'Error desconocido'
    showError('Error al cargar productos del inventario: ' + errorMsg)
    productos.value = [] // Asegurar que esté vacío en caso de error
  }
}

const abrirDialogoAgregarItem = async () => {
  tipoItemSeleccionado.value = 'servicio'
  nuevoItem.tipoServicioId = null
  nuevoItem.productoId = null
  nuevoItem.descripcion = ''
  nuevoItem.cantidad = 1
  nuevoItem.precioUnitario = 0
  nuevoItem.stockDisponible = null
  
  // Cargar productos si no están cargados o si la lista está vacía
  if (productos.value.length === 0) {
    await cargarProductos()
  }
  
  dialogAgregarItem.value = true
}

const onServicioSeleccionado = (servicioId) => {
  if (servicioId) {
    const servicio = tiposServicio.value.find(s => s.id === servicioId)
    if (servicio) {
      nuevoItem.descripcion = servicio.nombre
      nuevoItem.precioUnitario = servicio.precioBase || servicio.precio || 0
      nuevoItem.tipoServicioId = servicio.id
      nuevoItem.productoId = null // Limpiar producto si se selecciona servicio
    }
  }
}

const onProductoSeleccionado = (productoId) => {
  if (productoId) {
    const producto = productos.value.find(p => p.id === productoId)
    if (producto) {
      nuevoItem.descripcion = producto.nombre
      nuevoItem.precioUnitario = producto.precioVenta || producto.precio || 0
      nuevoItem.productoId = producto.id
      nuevoItem.stockDisponible = producto.stockActual
      nuevoItem.cantidad = 1
      nuevoItem.tipoServicioId = null // Limpiar servicio si se selecciona producto
    }
  }
}

const cancelarAgregarItem = () => {
  tipoItemSeleccionado.value = 'servicio'
  nuevoItem.tipoServicioId = null
  nuevoItem.productoId = null
  nuevoItem.descripcion = ''
  nuevoItem.cantidad = 1
  nuevoItem.precioUnitario = 0
  nuevoItem.stockDisponible = null
  dialogAgregarItem.value = false
}

const confirmarAgregarItem = async () => {
  if (!factura.value) return

  agregandoItem.value = true
  try {
    // Validar stock si es un producto
    if (nuevoItem.productoId && nuevoItem.stockDisponible !== null) {
      if (nuevoItem.cantidad > nuevoItem.stockDisponible) {
        showError(`Stock insuficiente. Disponible: ${nuevoItem.stockDisponible}`)
        agregandoItem.value = false
        return
      }
    }

    const detalleData = {
      tipoServicioId: nuevoItem.tipoServicioId || null,
      productoId: nuevoItem.productoId || null,
      descripcion: nuevoItem.descripcion,
      cantidad: nuevoItem.cantidad,
      precioUnitario: nuevoItem.precioUnitario,
    }

    // Intentar usar la función del store si existe, si no, usar API directa
    if (typeof facturasStore.agregarDetalle === 'function') {
      await facturasStore.agregarDetalle(factura.value.id, detalleData)
    } else {
      // Fallback: llamar directamente a la API
      console.warn('agregarDetalle no está disponible en el store, usando API directa')
      const { useApi } = await import('@/composables/useApi')
      const { post, get } = useApi()
      
      await post(`/v1/facturas/${factura.value.id}/detalles`, detalleData)
      
      // Recargar la factura
      const facturaResponse = await get(`/v1/facturas/${factura.value.id}`)
      if (facturasStore.fetchFacturaById) {
        await facturasStore.fetchFacturaById(factura.value.id)
      }
    }
    
    showSuccess('Item agregado exitosamente')
    cancelarAgregarItem()
    
    // Recargar la factura para asegurar que los datos estén actualizados
    if (typeof facturasStore.fetchFacturaById === 'function') {
      await facturasStore.fetchFacturaById(factura.value.id)
    }
  } catch (error) {
    console.error('Error al agregar item:', error)
    const mensaje = error?.userMessage || 
                   error?.response?.data?.userMessage || 
                   error?.response?.data?.message || 
                   error?.message ||
                   'Error al agregar el item'
    showError(mensaje)
  } finally {
    agregandoItem.value = false
  }
}

const eliminarItem = async (detalleId) => {
  if (!factura.value) return
  if (!confirm('¿Estás seguro de que quieres eliminar este item?')) return

  eliminandoItem.value = detalleId
  try {
    // Intentar usar la función del store si existe, si no, usar API directa
    if (typeof facturasStore.eliminarDetalle === 'function') {
      await facturasStore.eliminarDetalle(factura.value.id, detalleId)
    } else {
      // Fallback: llamar directamente a la API
      console.warn('eliminarDetalle no está disponible en el store, usando API directa')
      const { useApi } = await import('@/composables/useApi')
      const { delete: deleteRequest, get } = useApi()
      
      await deleteRequest(`/v1/facturas/${factura.value.id}/detalles/${detalleId}`)
      
      // Recargar la factura
      if (typeof facturasStore.fetchFacturaById === 'function') {
        await facturasStore.fetchFacturaById(factura.value.id)
      } else {
        const facturaResponse = await get(`/v1/facturas/${factura.value.id}`)
        // Actualizar manualmente el currentFactura si es posible
        if (facturasStore.currentFactura) {
          facturasStore.currentFactura = facturaResponse.data
        }
      }
    }
    
    showSuccess('Item eliminado exitosamente')
    
    // Recargar la factura para asegurar que los datos estén actualizados
    if (typeof facturasStore.fetchFacturaById === 'function') {
      await facturasStore.fetchFacturaById(factura.value.id)
    }
  } catch (error) {
    console.error('Error al eliminar item:', error)
    const mensaje = error?.userMessage || 
                   error?.response?.data?.userMessage || 
                   error?.response?.data?.message || 
                   error?.message ||
                   'Error al eliminar el item'
    showError(mensaje)
  } finally {
    eliminandoItem.value = null
  }
}

const formatCurrency = (value) => {
  return new Intl.NumberFormat('es-CO', {
    minimumFractionDigits: 0,
    maximumFractionDigits: 0
  }).format(value || 0)
}

const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleDateString('es-ES', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

const getEstadoColor = (estado) => {
  const estadoLower = (estado || '').toLowerCase()
  if (estadoLower === 'pagada' || estadoLower === 'pagado') return 'success'
  if (estadoLower === 'pendiente') return 'warning'
  if (estadoLower === 'anulada') return 'error'
  if (estadoLower === 'vencida') return 'error'
  return 'info'
}

const abrirDialogoRegistrarPago = () => {
  nuevoPago.metodoPago = null
  nuevoPago.monto = factura.value?.saldoPendiente || factura.value?.total || null
  nuevoPago.fechaPago = new Date().toISOString().split('T')[0]
  nuevoPago.numeroReferencia = ''
  nuevoPago.numeroVoucher = ''
  nuevoPago.observaciones = ''
  dialogRegistrarPago.value = true
}

const cancelarRegistrarPago = () => {
  nuevoPago.metodoPago = null
  nuevoPago.monto = null
  nuevoPago.fechaPago = new Date().toISOString().split('T')[0]
  nuevoPago.numeroReferencia = ''
  nuevoPago.numeroVoucher = ''
  nuevoPago.observaciones = ''
  dialogRegistrarPago.value = false
}

const calcularComision = (metodoPago, monto) => {
  if (!metodoPago || !monto) return 0
  const metodo = metodosPago.find(m => m.value === metodoPago)
  if (!metodo) return 0
  return monto * metodo.comision
}

const confirmarRegistrarPago = async () => {
  if (!factura.value) return

  registrandoPago.value = true
  try {
    // Obtener usuario actual
    let usuario = authStore.user
    if (!usuario || !usuario.id) {
      usuario = await authStore.getCurrentUser()
      if (!usuario || !usuario.id) {
        // Buscar usuario por username si no tiene ID
        if (usuario && usuario.username) {
          try {
            const { useApi } = await import('@/composables/useApi')
            const { get } = useApi()
            const usuariosResponse = await get('/usuarios', {
              params: { page: 0, size: 100 }
            })
            const usuarios = usuariosResponse.data?.content || usuariosResponse.data || []
            const usuarioCompleto = usuarios.find(u => u.username === usuario.username || u.email === usuario.email)
            if (usuarioCompleto) {
              usuario = { ...usuario, id: usuarioCompleto.id }
            }
          } catch (error) {
            console.error('Error al buscar usuario completo:', error)
          }
        }
      }
    }

    if (!usuario || !usuario.id) {
      showError('No se pudo obtener la información del usuario para registrar el pago')
      return
    }

    const pagoData = {
      fechaPago: nuevoPago.fechaPago,
      metodoPago: nuevoPago.metodoPago,
      monto: nuevoPago.monto,
      numeroReferencia: nuevoPago.numeroReferencia || null,
      numeroVoucher: nuevoPago.numeroVoucher || null,
      usuarioRegistroId: usuario.id,
      observaciones: nuevoPago.observaciones || null,
    }

    // Intentar usar la función del store si existe, si no, usar API directa
    if (typeof facturasStore.registrarPago === 'function') {
      await facturasStore.registrarPago(factura.value.id, pagoData)
    } else {
      // Fallback: usar API directa
      console.warn('registrarPago no está disponible en el store, usando API directa')
      const { useApi } = await import('@/composables/useApi')
      const { post, get } = useApi()
      
      await post(`/v1/facturas/${factura.value.id}/pagos`, pagoData)
      
      // Recargar la factura
      if (typeof facturasStore.fetchFacturaById === 'function') {
        await facturasStore.fetchFacturaById(factura.value.id)
      } else {
        const facturaResponse = await get(`/v1/facturas/${factura.value.id}`)
        // Actualizar manualmente el currentFactura si es posible
        if (facturasStore.currentFactura) {
          facturasStore.currentFactura = facturaResponse.data
        }
      }
    }

    showSuccess('Pago registrado exitosamente')
    cancelarRegistrarPago()

    // Recargar la factura para asegurar que los datos estén actualizados
    if (typeof facturasStore.fetchFacturaById === 'function') {
      await facturasStore.fetchFacturaById(factura.value.id)
    }
  } catch (error) {
    console.error('Error al registrar pago:', error)
    const mensaje = error?.userMessage || 
                   error?.response?.data?.userMessage || 
                   error?.response?.data?.message || 
                   error?.message ||
                   'Error al registrar el pago'
    showError(mensaje)
  } finally {
    registrandoPago.value = false
  }
}

const descargarPDF = () => {
  if (!factura.value) return

  try {
    // Crear contenido HTML del PDF
    const contenidoHTML = generarHTMLFactura(factura.value)
    
    // Crear ventana nueva para imprimir/guardar como PDF
    const ventanaImpresion = window.open('', '_blank')
    ventanaImpresion.document.write(contenidoHTML)
    ventanaImpresion.document.close()
    
    // Esperar a que cargue el contenido y luego imprimir
    ventanaImpresion.onload = () => {
      setTimeout(() => {
        ventanaImpresion.print()
        // Cerrar después de un delay para dar tiempo a imprimir
        setTimeout(() => {
          ventanaImpresion.close()
        }, 250)
      }, 250)
    }
    
    showSuccess('Abriendo vista de impresión. Use Ctrl+P para guardar como PDF.')
  } catch (error) {
    console.error('Error al generar PDF:', error)
    showError('Error al generar el PDF. Por favor, intente nuevamente.')
  }
}

const generarHTMLFactura = (factura) => {
  const fechaEmision = formatDate(factura.fechaEmision || factura.fecha)
  const itemsHTML = factura.detalles && factura.detalles.length > 0
    ? factura.detalles.map(item => `
      <tr>
        <td>${item.descripcion}</td>
        <td style="text-align: center;">${item.cantidad}</td>
        <td style="text-align: right;">$${formatCurrency(item.precioUnitario)}</td>
        <td style="text-align: right;">$${formatCurrency(item.subtotal || (item.cantidad * item.precioUnitario))}</td>
      </tr>
    `).join('')
    : '<tr><td colspan="4" style="text-align: center;">No hay items registrados</td></tr>'

  const pagosHTML = factura.pagos && factura.pagos.length > 0
    ? factura.pagos.map(pago => `
      <tr>
        <td>${pago.metodoPagoNombre || pago.metodo || 'N/A'}</td>
        <td>${formatDate(pago.fecha || pago.fechaPago)}</td>
        <td style="text-align: right;">$${formatCurrency(pago.monto)}</td>
      </tr>
    `).join('')
    : '<tr><td colspan="3" style="text-align: center;">No hay pagos registrados</td></tr>'

  return `
    <!DOCTYPE html>
    <html>
    <head>
      <meta charset="UTF-8">
      <title>Factura ${factura.numeroFactura || factura.numero || factura.id}</title>
      <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .header { text-align: center; margin-bottom: 30px; }
        .info { margin-bottom: 20px; }
        table { width: 100%; border-collapse: collapse; margin: 20px 0; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #E0E3E6; font-weight: bold; } /* Gris Medio */
        .total { text-align: right; font-weight: bold; margin-top: 20px; }
        .footer { margin-top: 40px; text-align: center; font-size: 12px; color: #666; }
      </style>
    </head>
    <body>
      <div class="header">
        <h1>Sistema Veterinaria</h1>
        <h2>FACTURA ${factura.numeroFactura || factura.numero || `#${factura.id}`}</h2>
      </div>
      
      <div class="info">
        <p><strong>Cliente:</strong> ${factura.clienteNombre || factura.cliente || 'No especificado'}</p>
        <p><strong>Fecha de Emisión:</strong> ${fechaEmision}</p>
        <p><strong>Estado:</strong> ${factura.estadoNombre || factura.estado || 'N/A'}</p>
        ${factura.citaId ? `<p><strong>Cita Asociada:</strong> #${factura.citaId}</p>` : ''}
      </div>

      <h3>Items / Servicios</h3>
      <table>
        <thead>
          <tr>
            <th>Descripción</th>
            <th>Cantidad</th>
            <th>Precio Unit.</th>
            <th>Subtotal</th>
          </tr>
        </thead>
        <tbody>
          ${itemsHTML}
        </tbody>
      </table>

      <div class="total">
        <p>Subtotal: $${formatCurrency(factura.subtotal || 0)}</p>
        ${(factura.totalDescuentos || 0) > 0 ? `<p>Descuentos: -$${formatCurrency(factura.totalDescuentos)}</p>` : ''}
        ${(factura.totalImpuestos || 0) > 0 ? `<p>Impuestos: $${formatCurrency(factura.totalImpuestos)}</p>` : ''}
        <h3>Total: $${formatCurrency(factura.total || 0)}</h3>
        ${(factura.totalPagado || 0) > 0 ? `<p>Total Pagado: $${formatCurrency(factura.totalPagado)}</p>` : ''}
        ${(factura.saldoPendiente || 0) > 0 ? `<p>Saldo Pendiente: $${formatCurrency(factura.saldoPendiente)}</p>` : ''}
      </div>

      ${factura.pagos && factura.pagos.length > 0 ? `
      <h3>Pagos Registrados</h3>
      <table>
        <thead>
          <tr>
            <th>Método</th>
            <th>Fecha</th>
            <th>Monto</th>
          </tr>
        </thead>
        <tbody>
          ${pagosHTML}
        </tbody>
      </table>
      ` : ''}

      <div class="footer">
        <p>Este documento fue generado el ${new Date().toLocaleString('es-ES')}</p>
      </div>
    </body>
    </html>
  `
}

const deleteFactura = async () => {
  if (!factura.value) return
  if (!confirm('¿Estás seguro de que quieres eliminar esta factura? Esta acción no se puede deshacer.')) {
    return
  }

    try {
    await facturasStore.deleteFactura(factura.value.id)
    showSuccess('Factura eliminada exitosamente')
      router.push('/facturas')
    } catch (error) {
      console.error('Error al eliminar factura:', error)
    const mensaje = error.response?.data?.userMessage || 
                   error.response?.data?.message || 
                   'Error al eliminar la factura'
    showError(mensaje)
  }
}

onMounted(() => {
  getFactura()
})
</script>

<style scoped>
.cursor-pointer {
  cursor: pointer;
  transition: background-color 0.2s;
}

.cursor-pointer:hover {
  background-color: rgba(0, 0, 0, 0.04);
}
</style>
