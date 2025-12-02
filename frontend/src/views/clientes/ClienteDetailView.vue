<template>
  <v-container fluid class="pa-6">
    <!-- Botón Volver -->
    <v-row>
      <v-col cols="12">
        <v-btn
          @click="$router.back()"
          variant="text"
          prepend-icon="mdi-arrow-left"
          class="mb-4"
        >
          Volver
        </v-btn>
      </v-col>
    </v-row>

    <!-- Loading State -->
    <v-row v-if="loading">
      <v-col cols="12" class="text-center">
        <v-progress-circular indeterminate color="primary" size="64"></v-progress-circular>
        <p class="mt-4">Cargando información del cliente...</p>
      </v-col>
    </v-row>

    <!-- Contenido Principal -->
    <template v-else-if="cliente">
      <!-- Header con Avatar y Nombre -->
      <v-row>
        <v-col cols="12">
          <v-card class="mb-6" elevation="3">
            <!-- Barra superior con fondo azul -->
            <div class="header-gradient pa-6">
              <v-row align="center" no-gutters>
                <v-col cols="12" class="d-flex justify-space-between align-center">
                  <div class="d-flex align-center">
                    <v-avatar size="100" class="elevation-6 mr-4" style="border: 4px solid white;">
                      <div class="d-flex align-center justify-center" style="width: 100%; height: 100%; background: white;">
                        <v-icon size="48" color="primary">mdi-account</v-icon>
                      </div>
                    </v-avatar>
                    <div>
                      <h1 class="text-h4 font-weight-bold text-white mb-1" style="text-shadow: 0 2px 4px rgba(0,0,0,0.2);">
                        {{ cliente.nombreCompleto || `${cliente.nombre || ''} ${cliente.apellido || ''}`.trim() }}
                      </h1>
                      <div class="d-flex align-center gap-2 mt-2">
                        <v-chip size="small" color="white" variant="flat" class="text-primary">
                          <v-icon start size="small">mdi-identifier</v-icon>
                          ID: #{{ cliente.id }}
                        </v-chip>
                        <v-chip 
                          v-if="cliente.cantidadPacientesActivos !== undefined"
                          size="small" 
                          color="white" 
                          variant="flat" 
                          class="text-primary"
                        >
                          <v-icon start size="small">mdi-paw</v-icon>
                          {{ cliente.cantidadPacientesActivos }} {{ cliente.cantidadPacientesActivos === 1 ? 'mascota' : 'mascotas' }}
                        </v-chip>
                      </div>
                    </div>
                  </div>
                  <v-menu>
                    <template v-slot:activator="{ props }">
                      <v-btn
                        icon="mdi-dots-vertical"
                        color="white"
                        variant="text"
                        v-bind="props"
                        size="large"
                      ></v-btn>
                    </template>
                    <v-list>
                      <v-list-item @click="editarCliente">
                        <template v-slot:prepend>
                          <v-icon>mdi-pencil</v-icon>
                        </template>
                        <v-list-item-title>Editar</v-list-item-title>
                      </v-list-item>
                      <v-list-item @click="nuevaCita">
                        <template v-slot:prepend>
                          <v-icon>mdi-calendar-plus</v-icon>
                        </template>
                        <v-list-item-title>Nueva Cita</v-list-item-title>
                      </v-list-item>
                      <v-list-item @click="verCitas">
                        <template v-slot:prepend>
                          <v-icon>mdi-calendar</v-icon>
                        </template>
                        <v-list-item-title>Ver Citas</v-list-item-title>
                      </v-list-item>
                      <v-divider></v-divider>
                      <v-list-item @click="deleteCliente" class="text-error">
                        <template v-slot:prepend>
                          <v-icon color="error">mdi-delete</v-icon>
                        </template>
                        <v-list-item-title>Eliminar</v-list-item-title>
                      </v-list-item>
                    </v-list>
                  </v-menu>
                </v-col>
              </v-row>
            </div>
            
            <!-- Sección de información con fondo blanco -->
            <v-card-text class="pa-6 bg-white">
              <div class="d-flex flex-wrap gap-3 align-center">
                <v-chip 
                  v-if="cliente.email"
                  color="accent"
                  size="large"
                  class="chip-elevated email-chip"
                >
                  <v-icon start size="20" color="white">mdi-email</v-icon>
                  <a :href="`mailto:${cliente.email}`" class="email-link">
                    {{ cliente.email }}
                  </a>
                </v-chip>
                <v-chip
                  v-if="cliente.telefono"
                  color="secondary"
                  size="large"
                  class="chip-elevated phone-chip"
                >
                  <v-icon start size="20" color="white">mdi-phone</v-icon>
                  <a :href="`tel:${cliente.telefono}`" class="phone-link">
                    {{ cliente.telefono }}
                  </a>
                </v-chip>
                <v-chip
                  v-if="cliente.dni || cliente.documento"
                  color="info"
                  variant="outlined"
                  size="large"
                  class="chip-outlined"
                >
                  <v-icon start size="20" color="info">mdi-card-account-details</v-icon>
                  <strong>{{ cliente.dni || cliente.documento }}</strong>
                </v-chip>
              </div>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>

      <!-- Acciones Rápidas -->
      <v-row class="mb-6">
        <v-col cols="12">
          <v-card variant="outlined">
            <v-card-text class="pa-4">
              <div class="d-flex flex-wrap gap-2">
                <v-btn
                  color="primary"
                  prepend-icon="mdi-pencil"
                  @click="editarCliente"
                  variant="elevated"
                >
                  Editar Cliente
                </v-btn>
                <v-btn
                  color="accent"
                  prepend-icon="mdi-calendar-plus"
                  @click="nuevaCita"
                  variant="elevated"
                >
                  Nueva Cita
                </v-btn>
                <v-btn
                  color="secondary"
                  prepend-icon="mdi-paw"
                  @click="mostrarFormularioMascota = true"
                  variant="outlined"
                >
                  Agregar Mascota
                </v-btn>
                <v-btn
                  color="info"
                  prepend-icon="mdi-calendar"
                  @click="verCitas"
                  variant="outlined"
                >
                  Ver Citas
                </v-btn>
                <v-btn
                  color="warning"
                  prepend-icon="mdi-receipt"
                  @click="verFacturas"
                  variant="outlined"
                >
                  Ver Facturas
                </v-btn>
              </div>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>

      <v-row>
        <!-- Columna Izquierda - Información Principal -->
        <v-col cols="12" md="8">
          <!-- Información Personal -->
          <v-card class="mb-4" elevation="1">
            <v-card-title class="bg-grey-lighten-4 d-flex align-center">
              <v-icon class="mr-2" color="primary">mdi-account</v-icon>
              <span>Información Personal</span>
            </v-card-title>
            <v-card-text class="pa-4">
              <v-row>
                <v-col cols="12" md="6">
                  <div class="info-item mb-4">
                    <div class="text-caption text-grey mb-1">
                      <v-icon size="small" class="mr-1">mdi-account</v-icon>
                      Nombre
                    </div>
                    <div class="text-body-1 font-weight-medium">
                      {{ cliente.nombre || 'N/A' }}
                    </div>
                  </div>
                </v-col>
                <v-col cols="12" md="6">
                  <div class="info-item mb-4">
                    <div class="text-caption text-grey mb-1">
                      <v-icon size="small" class="mr-1">mdi-account</v-icon>
                      Apellido
                    </div>
                    <div class="text-body-1 font-weight-medium">
                      {{ cliente.apellido || 'N/A' }}
                    </div>
                  </div>
                </v-col>
                <v-col cols="12" md="6" v-if="cliente.dni || cliente.documento">
                  <div class="info-item mb-4">
                    <div class="text-caption text-grey mb-1">
                      <v-icon size="small" class="mr-1">mdi-card-account-details</v-icon>
                      Documento de Identidad
                    </div>
                    <div class="text-body-1 font-weight-medium font-mono">
                      {{ cliente.dni || cliente.documento }}
                    </div>
                  </div>
                </v-col>
                <v-col cols="12" md="6" v-if="cliente.email">
                  <div class="info-item mb-4">
                    <div class="text-caption text-grey mb-1">
                      <v-icon size="small" class="mr-1">mdi-email</v-icon>
                      Email
                    </div>
                    <div class="text-body-1 font-weight-medium">
                      <a :href="`mailto:${cliente.email}`" class="text-primary text-decoration-none">
                        {{ cliente.email }}
                      </a>
                    </div>
                  </div>
                </v-col>
                <v-col cols="12" md="6" v-if="cliente.telefono">
                  <div class="info-item mb-4">
                    <div class="text-caption text-grey mb-1">
                      <v-icon size="small" class="mr-1">mdi-phone</v-icon>
                      Teléfono
                    </div>
                    <div class="text-body-1 font-weight-medium">
                      <a :href="`tel:${cliente.telefono}`" class="text-primary text-decoration-none">
                        {{ cliente.telefono }}
                      </a>
                    </div>
                  </div>
                </v-col>
              </v-row>
            </v-card-text>
          </v-card>

          <!-- Información de Contacto -->
          <v-card class="mb-4" elevation="1" v-if="cliente.direccion || cliente.ciudad || cliente.departamento || cliente.codigoPostal">
            <v-card-title class="bg-grey-lighten-4 d-flex align-center">
              <v-icon class="mr-2" color="primary">mdi-map-marker</v-icon>
              <span>Dirección</span>
            </v-card-title>
            <v-card-text class="pa-4">
              <v-row>
                <v-col cols="12" v-if="cliente.direccion">
                  <div class="info-item mb-3">
                    <div class="text-caption text-grey mb-1">Dirección</div>
                    <div class="text-body-1 font-weight-medium">
                      {{ cliente.direccion }}
                    </div>
                  </div>
                </v-col>
                <v-col cols="12" md="6" v-if="cliente.ciudad">
                  <div class="info-item mb-3">
                    <div class="text-caption text-grey mb-1">Ciudad</div>
                    <div class="text-body-1 font-weight-medium">
                      {{ cliente.ciudad }}
                    </div>
                  </div>
                </v-col>
                <v-col cols="12" md="6" v-if="cliente.departamento">
                  <div class="info-item mb-3">
                    <div class="text-caption text-grey mb-1">Departamento</div>
                    <div class="text-body-1 font-weight-medium">
                      {{ cliente.departamento }}
                    </div>
                  </div>
                </v-col>
                <v-col cols="12" md="6" v-if="cliente.codigoPostal">
                  <div class="info-item mb-3">
                    <div class="text-caption text-grey mb-1">Código Postal</div>
                    <div class="text-body-1 font-weight-medium font-mono">
                      {{ cliente.codigoPostal }}
                    </div>
                  </div>
                </v-col>
              </v-row>
            </v-card-text>
          </v-card>

          <!-- Observaciones -->
          <v-card class="mb-4" elevation="1" v-if="cliente.observaciones">
            <v-card-title class="bg-grey-lighten-4 d-flex align-center">
              <v-icon class="mr-2" color="primary">mdi-note-text</v-icon>
              <span>Observaciones</span>
            </v-card-title>
            <v-card-text class="pa-4">
              <p class="text-body-1">{{ cliente.observaciones }}</p>
            </v-card-text>
          </v-card>

          <!-- Sección de Mascotas -->
          <v-card class="mb-4" elevation="1">
            <v-card-title class="bg-grey-lighten-4 d-flex justify-space-between align-center">
              <div class="d-flex align-center">
                <v-icon class="mr-2" color="primary">mdi-paw</v-icon>
                <span>Mascotas ({{ cliente.mascotas?.length || cliente.pacientes?.length || 0 }})</span>
              </div>
              <v-btn
                color="primary"
                prepend-icon="mdi-plus"
                size="small"
                @click="mostrarFormularioMascota = true"
              >
                Agregar
              </v-btn>
            </v-card-title>
            <v-card-text class="pa-4">
              <v-row v-if="(cliente.mascotas && cliente.mascotas.length > 0) || (cliente.pacientes && cliente.pacientes.length > 0)">
                <v-col
                  v-for="mascota in (cliente.mascotas || cliente.pacientes)"
                  :key="mascota.id"
                  cols="12"
                  md="6"
                  lg="4"
                >
                  <v-card
                    variant="outlined"
                    class="h-100 mascota-card"
                    :to="`/pacientes/${mascota.id}`"
                    style="cursor: pointer; transition: all 0.3s ease;"
                    @mouseenter="$event.currentTarget.style.transform = 'translateY(-4px)'"
                    @mouseleave="$event.currentTarget.style.transform = 'translateY(0)'"
                  >
                    <v-card-title class="d-flex justify-space-between align-center bg-grey-lighten-5">
                      <div class="d-flex align-center">
                        <v-avatar color="primary" size="48" class="mr-3">
                          <v-icon color="white">mdi-paw</v-icon>
                        </v-avatar>
                        <div>
                          <div class="text-h6 font-weight-bold">{{ mascota.nombre }}</div>
                          <v-chip size="x-small" color="secondary" text-color="white" class="mt-1">
                            {{ formatEspecie(mascota.especie) }}
                          </v-chip>
                        </div>
                      </div>
                    </v-card-title>
                    <v-card-text>
                      <div class="mb-2" v-if="mascota.raza">
                        <v-icon size="small" class="mr-1 text-grey">mdi-dog</v-icon>
                        <span class="text-body-2">{{ mascota.raza }}</span>
                      </div>
                      <div class="mb-2" v-if="mascota.sexo">
                        <v-icon size="small" class="mr-1 text-grey">mdi-gender-male-female</v-icon>
                        <span class="text-body-2">{{ formatSexo(mascota.sexo) }}</span>
                      </div>
                      <div class="mb-2" v-if="mascota.fechaNacimiento">
                        <v-icon size="small" class="mr-1 text-grey">mdi-calendar</v-icon>
                        <span class="text-body-2">{{ formatDate(mascota.fechaNacimiento) }}</span>
                      </div>
                      <div v-if="mascota.pesoKg">
                        <v-icon size="small" class="mr-1 text-grey">mdi-weight-kilogram</v-icon>
                        <span class="text-body-2">{{ mascota.pesoKg }} kg</span>
                      </div>
                    </v-card-text>
                    <v-card-actions>
                      <v-btn
                        variant="text"
                        color="primary"
                        prepend-icon="mdi-eye"
                        size="small"
                        @click.stop="$router.push(`/pacientes/${mascota.id}`)"
                      >
                        Ver Detalles
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-col>
              </v-row>
              <v-alert
                v-else
                type="info"
                variant="tonal"
                class="mt-2"
              >
                <v-icon class="mr-2">mdi-information</v-icon>
                No hay mascotas registradas para este cliente.
              </v-alert>
            </v-card-text>
          </v-card>
        </v-col>

        <!-- Columna Derecha - Información Adicional -->
        <v-col cols="12" md="4">
          <!-- Información del Sistema -->
          <v-card class="mb-4" elevation="1">
            <v-card-title class="bg-grey-lighten-4 d-flex align-center">
              <v-icon class="mr-2" color="primary">mdi-database</v-icon>
              <span>Información del Sistema</span>
            </v-card-title>
            <v-card-text class="pa-4">
              <div class="info-item mb-3">
                <div class="text-caption text-grey mb-1">ID del Cliente</div>
                <div class="text-body-1 font-weight-medium font-mono">
                  #{{ cliente.id }}
                </div>
              </div>
              <div class="info-item mb-3" v-if="cliente.createdAt">
                <div class="text-caption text-grey mb-1">
                  <v-icon size="small" class="mr-1">mdi-calendar-plus</v-icon>
                  Fecha de Registro
                </div>
                <div class="text-body-2">
                  {{ formatDateTime(cliente.createdAt) }}
                </div>
              </div>
              <div class="info-item mb-3" v-if="cliente.updatedAt">
                <div class="text-caption text-grey mb-1">
                  <v-icon size="small" class="mr-1">mdi-calendar-edit</v-icon>
                  Última Actualización
                </div>
                <div class="text-body-2">
                  {{ formatDateTime(cliente.updatedAt) }}
                </div>
              </div>
            </v-card-text>
          </v-card>

          <!-- Estadísticas -->
          <v-card class="mb-4" elevation="1">
            <v-card-title class="bg-grey-lighten-4 d-flex align-center">
              <v-icon class="mr-2" color="primary">mdi-chart-line</v-icon>
              <span>Estadísticas</span>
            </v-card-title>
            <v-card-text class="pa-4">
              <div class="stat-item mb-3">
                <div class="d-flex justify-space-between align-center">
                  <div class="d-flex align-center">
                    <v-icon size="small" color="primary" class="mr-2">mdi-paw</v-icon>
                    <span class="text-body-2">Total Mascotas</span>
                  </div>
                  <v-chip size="small" color="primary" variant="flat">
                    {{ cliente.mascotas?.length || cliente.pacientes?.length || 0 }}
                  </v-chip>
                </div>
              </div>
              <div class="stat-item mb-3" v-if="cliente.cantidadPacientesActivos !== undefined">
                <div class="d-flex justify-space-between align-center">
                  <div class="d-flex align-center">
                    <v-icon size="small" color="success" class="mr-2">mdi-check-circle</v-icon>
                    <span class="text-body-2">Mascotas Activas</span>
                  </div>
                  <v-chip size="small" color="success" variant="flat">
                    {{ cliente.cantidadPacientesActivos }}
                  </v-chip>
                </div>
              </div>
            </v-card-text>
          </v-card>

          <!-- Accesos Rápidos -->
          <v-card class="mb-4" elevation="1">
            <v-card-title class="bg-grey-lighten-4 d-flex align-center">
              <v-icon class="mr-2" color="primary">mdi-lightning-bolt</v-icon>
              <span>Accesos Rápidos</span>
            </v-card-title>
            <v-card-text class="pa-4">
              <v-list density="compact">
                <v-list-item
                  prepend-icon="mdi-calendar-plus"
                  title="Nueva Cita"
                  @click="nuevaCita"
                  class="cursor-pointer"
                ></v-list-item>
                <v-list-item
                  prepend-icon="mdi-calendar"
                  title="Ver Citas"
                  @click="verCitas"
                  class="cursor-pointer"
                ></v-list-item>
                <v-list-item
                  prepend-icon="mdi-receipt"
                  title="Ver Facturas"
                  @click="verFacturas"
                  class="cursor-pointer"
                ></v-list-item>
                <v-list-item
                  prepend-icon="mdi-paw"
                  title="Agregar Mascota"
                  @click="mostrarFormularioMascota = true"
                  class="cursor-pointer"
                ></v-list-item>
              </v-list>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </template>

    <!-- Error State -->
    <v-row v-else>
      <v-col cols="12">
        <v-card>
          <v-card-text class="pa-6 text-center">
            <v-icon size="64" color="error" class="mb-4">mdi-alert-circle</v-icon>
            <p class="text-h6">No se pudo cargar la información del cliente</p>
            <v-btn color="primary" @click="getCliente" class="mt-4">
              Reintentar
            </v-btn>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <!-- Formulario para agregar mascota (Dialog) -->
    <v-dialog v-model="mostrarFormularioMascota" max-width="800" persistent>
      <v-card>
        <v-card-title class="bg-primary text-white">
          <span>Agregar Nueva Mascota</span>
          <v-spacer></v-spacer>
          <v-btn icon color="white" variant="text" @click="cerrarFormularioMascota">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-card-title>

        <v-card-text class="pa-6">
          <v-form ref="formMascotaRef" @submit.prevent="guardarMascota">
            <v-row>
              <v-col cols="12" md="6">
                <v-text-field
                  v-model="nuevaMascota.nombre"
                  label="Nombre de la Mascota *"
                  :rules="[rules.required]"
                  variant="outlined"
                ></v-text-field>
              </v-col>
              <v-col cols="12" md="6">
                <v-select
                  v-model="nuevaMascota.especie"
                  label="Especie *"
                  :items="especieOptions"
                  item-title="title"
                  item-value="value"
                  :rules="[rules.required]"
                  variant="outlined"
                ></v-select>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="12" md="6">
                <v-select
                  v-model="nuevaMascota.razaSeleccionada"
                  label="Raza"
                  :items="getRazasPorEspecie(nuevaMascota.especie)"
                  item-title="nombre"
                  item-value="id"
                  clearable
                  variant="outlined"
                ></v-select>
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field
                  v-model="nuevaMascota.fechaNacimiento"
                  label="Fecha de Nacimiento *"
                  type="date"
                  :rules="[rules.required]"
                  variant="outlined"
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="12" md="6">
                <v-select
                  v-model="nuevaMascota.sexo"
                  label="Sexo *"
                  :items="sexoOptions"
                  item-title="title"
                  item-value="value"
                  :rules="[rules.required]"
                  variant="outlined"
                ></v-select>
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field
                  v-model="nuevaMascota.pesoKg"
                  label="Peso (kg)"
                  type="number"
                  step="0.1"
                  variant="outlined"
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="12" md="6">
                <v-text-field
                  v-model="nuevaMascota.color"
                  label="Color"
                  variant="outlined"
                ></v-text-field>
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field
                  v-model="nuevaMascota.microchip"
                  label="Microchip"
                  variant="outlined"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-form>
        </v-card-text>

        <v-card-actions class="pa-4">
          <v-spacer></v-spacer>
          <v-btn
            color="secondary"
            @click="cerrarFormularioMascota"
            variant="outlined"
          >
            Cancelar
          </v-btn>
          <v-btn
            color="primary"
            :loading="guardandoMascota"
            @click="guardarMascota"
            variant="elevated"
          >
            Guardar Mascota
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useClientesStore } from '@/stores/clientesStore'
import { usePacientesStore } from '@/stores/pacientesStore'
import { useReferenceData } from '@/composables/useReferenceData'
import { useNotification } from '@/composables/useNotification'

const router = useRouter()
const route = useRoute()
const clientesStore = useClientesStore()
const pacientesStore = usePacientesStore()
const { fetchRazas } = useReferenceData()
const { showSuccess, showError } = useNotification()

const loading = computed(() => clientesStore.loading)
const cliente = computed(() => clientesStore.currentCliente)
const mostrarFormularioMascota = ref(false)
const guardandoMascota = ref(false)
const formMascotaRef = ref(null)
const razas = ref([])

const nuevaMascota = ref({
  nombre: '',
  especie: null,
  razaSeleccionada: null,
  fechaNacimiento: null,
  sexo: null,
  pesoKg: null,
  color: '',
  microchip: ''
})

const especieOptions = [
  { title: 'Perro', value: 'PERRO' },
  { title: 'Gato', value: 'GATO' },
  { title: 'Ave', value: 'AVE' },
  { title: 'Reptil', value: 'REPTIL' },
  { title: 'Roedor', value: 'ROEDOR' },
  { title: 'Otro', value: 'OTRO' }
]

const sexoOptions = [
  { title: 'Macho', value: 'MACHO' },
  { title: 'Hembra', value: 'HEMBRA' }
]

const rules = {
  required: (v) => !!v || 'Este campo es requerido',
}

const formatDate = (dateString) => {
  if (!dateString) return ''
  if (typeof dateString === 'string' && /^\d{4}-\d{2}-\d{2}/.test(dateString)) {
    const [year, month, day] = dateString.split('T')[0].split('-')
    const date = new Date(parseInt(year), parseInt(month) - 1, parseInt(day))
    return date.toLocaleDateString('es-ES', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit'
    })
  }
  const date = new Date(dateString)
  return date.toLocaleDateString('es-ES', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
  })
}

const formatDateTime = (dateStr) => {
  if (!dateStr) return 'No disponible'
  try {
    const date = new Date(dateStr)
    return date.toLocaleString('es-ES', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    })
  } catch (error) {
    return dateStr
  }
}

const formatEspecie = (especie) => {
  if (!especie) return 'No especificada'
  const especies = {
    'PERRO': 'Perro',
    'GATO': 'Gato',
    'AVE': 'Ave',
    'ROEDOR': 'Roedor',
    'REPTIL': 'Reptil',
    'OTRO': 'Otro'
  }
  return especies[especie] || especie
}

const formatSexo = (sexo) => {
  if (!sexo) return 'No especificado'
  const sexos = {
    'MACHO': 'Macho',
    'HEMBRA': 'Hembra'
  }
  return sexos[sexo] || sexo
}

const getRazasPorEspecie = (especie) => {
  if (!especie) return []
  return razas.value.filter(r => r.especie === especie)
}

const cerrarFormularioMascota = () => {
  mostrarFormularioMascota.value = false
  nuevaMascota.value = {
    nombre: '',
    especie: null,
    razaSeleccionada: null,
    fechaNacimiento: null,
    sexo: null,
    pesoKg: null,
    color: '',
    microchip: ''
  }
  if (formMascotaRef.value) {
    formMascotaRef.value.resetValidation()
  }
}

const guardarMascota = async () => {
  if (!formMascotaRef.value) return
  
  const { valid } = await formMascotaRef.value.validate()
  if (!valid) return

  if (!nuevaMascota.value.nombre || !nuevaMascota.value.especie || 
      !nuevaMascota.value.fechaNacimiento || !nuevaMascota.value.sexo) {
    showError('Por favor completa todos los campos requeridos')
    return
  }

  guardandoMascota.value = true
  try {
    const razaNombre = nuevaMascota.value.razaSeleccionada
      ? razas.value.find(r => r.id === nuevaMascota.value.razaSeleccionada)?.nombre
      : null

    const mascotaData = {
      nombre: nuevaMascota.value.nombre,
      clienteId: cliente.value.id,
      especie: nuevaMascota.value.especie,
      raza: razaNombre,
      fechaNacimiento: nuevaMascota.value.fechaNacimiento,
      sexo: nuevaMascota.value.sexo,
      pesoKg: nuevaMascota.value.pesoKg ? parseFloat(nuevaMascota.value.pesoKg) : null,
      color: nuevaMascota.value.color || null,
      microchip: nuevaMascota.value.microchip || null,
      observaciones: null,
    }

    await pacientesStore.createPaciente(mascotaData)
    await clientesStore.fetchClienteById(route.params.id)
    
    showSuccess('Mascota agregada exitosamente')
    cerrarFormularioMascota()
  } catch (error) {
    console.error('Error al guardar mascota:', error)
    showError(error.response?.data?.message || 'Error al guardar la mascota')
  } finally {
    guardandoMascota.value = false
  }
}

const getCliente = async () => {
  try {
    await clientesStore.fetchClienteById(route.params.id)
  } catch (error) {
    console.error('Error al cargar cliente:', error)
    showError('Error al cargar la información del cliente')
  }
}

const editarCliente = () => {
  router.push(`/clientes/${route.params.id}/editar`)
}

const nuevaCita = () => {
  router.push({
    path: '/citas/nueva',
    query: { clienteId: route.params.id }
  })
}

const verCitas = () => {
  router.push({
    path: '/citas',
    query: { clienteId: route.params.id }
  })
}

const verFacturas = () => {
  router.push({
    path: '/facturas',
    query: { clienteId: route.params.id }
  })
}

const deleteCliente = async () => {
  if (confirm('¿Estás seguro de que quieres eliminar este cliente? Esta acción no se puede deshacer.')) {
    try {
      await clientesStore.deleteCliente(route.params.id)
      showSuccess('Cliente eliminado exitosamente')
      router.push('/clientes')
    } catch (error) {
      console.error('Error al eliminar cliente:', error)
      showError(error.userMessage || 'Error al eliminar el cliente')
    }
  }
}

onMounted(async () => {
  try {
    razas.value = await fetchRazas()
  } catch (error) {
    console.error('Error al cargar razas:', error)
  }
  
  getCliente()
})
</script>

<style scoped>
.info-item {
  min-height: 48px;
}

.stat-item {
  padding: 8px 0;
}

.font-mono {
  font-family: 'Courier New', monospace;
}

.cursor-pointer {
  cursor: pointer;
}

.gap-2 {
  gap: 8px;
}

.gap-3 {
  gap: 12px;
}

.header-gradient {
  background: linear-gradient(135deg, #0A74B7 0%, #083E6B 100%);
  position: relative;
}

.header-gradient::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 1px;
  background: rgba(255, 255, 255, 0.2);
}

.chip-elevated {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15) !important;
  font-weight: 500;
}

.chip-outlined {
  border-width: 2px !important;
  font-weight: 500;
}

.mascota-card {
  transition: all 0.3s ease;
}

.mascota-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15) !important;
}

.email-chip {
  background-color: #1C78BF !important;
}

.email-link {
  color: #FFFFFF !important;
  text-decoration: none;
  font-weight: 500;
}

.email-link:hover {
  text-decoration: underline;
}

.phone-chip {
  background-color: #0C5A96 !important;
}

.phone-link {
  color: #FFFFFF !important;
  text-decoration: none;
  font-weight: 500;
}

.phone-link:hover {
  text-decoration: underline;
}
</style>
