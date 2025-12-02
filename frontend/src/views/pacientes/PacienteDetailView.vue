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
        <p class="mt-4">Cargando información del paciente...</p>
      </v-col>
    </v-row>

    <!-- Contenido Principal -->
    <template v-else-if="paciente">
      <!-- Header con Foto y Nombre -->
      <v-row>
        <v-col cols="12">
          <v-card class="mb-6" elevation="3">
            <!-- Barra superior con fondo azul -->
            <div class="header-gradient pa-6">
              <v-row align="center" no-gutters>
                <v-col cols="12" class="d-flex justify-space-between align-center">
                  <div class="d-flex align-center">
                    <v-avatar size="100" class="elevation-6 mr-4" style="border: 4px solid white;">
                      <v-img
                        v-if="paciente.fotoUrl"
                        :src="paciente.fotoUrl"
                        :alt="paciente.nombre"
                        cover
                      ></v-img>
                      <div v-else class="d-flex align-center justify-center" style="width: 100%; height: 100%; background: white;">
                        <v-icon size="48" color="primary">mdi-paw</v-icon>
                      </div>
                    </v-avatar>
                    <div>
                      <h1 class="text-h4 font-weight-bold text-white mb-1" style="text-shadow: 0 2px 4px rgba(0,0,0,0.2);">
                        {{ paciente.nombre }}
                      </h1>
                      <div class="d-flex align-center">
                        <v-icon size="small" color="white" class="mr-1">mdi-calendar</v-icon>
                        <span class="text-body-1 text-white font-weight-medium">
                          <span v-if="paciente.edadAnios !== undefined">
                            {{ paciente.edadAnios }} {{ paciente.edadAnios === 1 ? 'año' : 'años' }}
                          </span>
                          <span v-else-if="paciente.fechaNacimiento">
                            Nacido el {{ formatDate(paciente.fechaNacimiento) }}
                          </span>
                        </span>
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
                      <v-list-item @click="editarPaciente">
                        <template v-slot:prepend>
                          <v-icon>mdi-pencil</v-icon>
                        </template>
                        <v-list-item-title>Editar</v-list-item-title>
                      </v-list-item>
                      <v-list-item @click="verHistorial">
                        <template v-slot:prepend>
                          <v-icon>mdi-file-document</v-icon>
                        </template>
                        <v-list-item-title>Ver Historial</v-list-item-title>
                      </v-list-item>
                      <v-list-item @click="nuevaCita">
                        <template v-slot:prepend>
                          <v-icon>mdi-calendar-plus</v-icon>
                        </template>
                        <v-list-item-title>Nueva Cita</v-list-item-title>
                      </v-list-item>
                      <v-divider></v-divider>
                      <v-list-item @click="deletePaciente" class="text-error">
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
                  color="secondary" 
                  text-color="white" 
                  size="large"
                  class="chip-elevated"
                  prepend-icon="mdi-paw"
                >
                  <strong>{{ formatEspecie(paciente.especie) }}</strong>
                </v-chip>
                <v-chip
                  :color="getEstadoColor(paciente.estado)"
                  text-color="white"
                  size="large"
                  class="chip-elevated"
                  :prepend-icon="getEstadoIcon(paciente.estado)"
                >
                  <strong>{{ formatEstado(paciente.estado) }}</strong>
                </v-chip>
                <v-chip
                  v-if="paciente.raza"
                  color="accent"
                  text-color="white"
                  size="large"
                  class="chip-elevated"
                  prepend-icon="mdi-dog"
                >
                  <strong>{{ paciente.raza }}</strong>
                </v-chip>
                <v-chip
                  v-if="paciente.pesoKg"
                  color="info"
                  variant="outlined"
                  size="large"
                  class="chip-outlined"
                  prepend-icon="mdi-weight-kilogram"
                >
                  <strong>{{ paciente.pesoKg }} kg</strong>
                </v-chip>
                <v-chip
                  v-if="paciente.microchip"
                  color="grey-darken-1"
                  variant="outlined"
                  size="large"
                  class="chip-outlined font-mono"
                  prepend-icon="mdi-chip"
                >
                  <strong>{{ paciente.microchip }}</strong>
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
                  prepend-icon="mdi-file-document"
                  @click="verHistorial"
                  variant="elevated"
                >
                  Historial Clínico
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
                  prepend-icon="mdi-pencil"
                  @click="editarPaciente"
                  variant="outlined"
                >
                  Editar
                </v-btn>
                <v-btn
                  color="warning"
                  prepend-icon="mdi-receipt"
                  @click="verFacturas"
                  variant="outlined"
                >
                  Facturas
                </v-btn>
              </div>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>

      <v-row>
        <!-- Columna Izquierda - Información Principal -->
        <v-col cols="12" md="8">
          <!-- Información Básica -->
          <v-card class="mb-4" elevation="1">
            <v-card-title class="bg-grey-lighten-4 d-flex align-center">
              <v-icon class="mr-2" color="primary">mdi-information</v-icon>
              <span>Información Básica</span>
            </v-card-title>
            <v-card-text class="pa-4">
              <v-row>
                <v-col cols="12" md="6">
                  <div class="info-item mb-4">
                    <div class="text-caption text-grey mb-1">
                      <v-icon size="small" class="mr-1">mdi-calendar</v-icon>
                      Fecha de Nacimiento
                    </div>
                    <div class="text-body-1 font-weight-medium">
                      {{ formatDate(paciente.fechaNacimiento) }}
                    </div>
                  </div>
                </v-col>
                <v-col cols="12" md="6">
                  <div class="info-item mb-4">
                    <div class="text-caption text-grey mb-1">
                      <v-icon size="small" class="mr-1">mdi-gender-male-female</v-icon>
                      Sexo
                    </div>
                    <div class="text-body-1 font-weight-medium">
                      {{ formatSexo(paciente.sexo) }}
                    </div>
                  </div>
                </v-col>
                <v-col cols="12" md="6" v-if="paciente.pesoKg">
                  <div class="info-item mb-4">
                    <div class="text-caption text-grey mb-1">
                      <v-icon size="small" class="mr-1">mdi-weight-kilogram</v-icon>
                      Peso
                    </div>
                    <div class="text-body-1 font-weight-medium">
                      {{ paciente.pesoKg }} kg
                    </div>
                  </div>
                </v-col>
                <v-col cols="12" md="6" v-if="paciente.color">
                  <div class="info-item mb-4">
                    <div class="text-caption text-grey mb-1">
                      <v-icon size="small" class="mr-1">mdi-palette</v-icon>
                      Color
                    </div>
                    <div class="text-body-1 font-weight-medium">
                      {{ paciente.color }}
                    </div>
                  </div>
                </v-col>
                <v-col cols="12" md="6" v-if="paciente.microchip">
                  <div class="info-item mb-4">
                    <div class="text-caption text-grey mb-1">
                      <v-icon size="small" class="mr-1">mdi-chip</v-icon>
                      Microchip
                    </div>
                    <div class="text-body-1 font-weight-medium font-mono">
                      {{ paciente.microchip }}
                    </div>
                  </div>
                </v-col>
                <v-col cols="12" md="6" v-if="paciente.edadAnios !== undefined">
                  <div class="info-item mb-4">
                    <div class="text-caption text-grey mb-1">
                      <v-icon size="small" class="mr-1">mdi-cake</v-icon>
                      Edad
                    </div>
                    <div class="text-body-1 font-weight-medium">
                      {{ paciente.edadAnios }} {{ paciente.edadAnios === 1 ? 'año' : 'años' }}
                    </div>
                  </div>
                </v-col>
              </v-row>
            </v-card-text>
          </v-card>

          <!-- Información del Propietario -->
          <v-card class="mb-4" elevation="1" v-if="paciente.cliente">
            <v-card-title class="bg-grey-lighten-4 d-flex align-center">
              <v-icon class="mr-2" color="primary">mdi-account</v-icon>
              <span>Propietario</span>
            </v-card-title>
            <v-card-text class="pa-4">
              <v-row>
                <v-col cols="12" md="6">
                  <div class="info-item mb-3">
                    <div class="text-caption text-grey mb-1">Nombre</div>
                    <div class="text-body-1 font-weight-medium">
                      {{ paciente.cliente.nombreCompleto }}
                    </div>
                  </div>
                </v-col>
                <v-col cols="12" md="6" v-if="paciente.cliente.telefono">
                  <div class="info-item mb-3">
                    <div class="text-caption text-grey mb-1">
                      <v-icon size="small" class="mr-1">mdi-phone</v-icon>
                      Teléfono
                    </div>
                    <div class="text-body-1 font-weight-medium">
                      <a :href="`tel:${paciente.cliente.telefono}`" class="text-decoration-none">
                        {{ paciente.cliente.telefono }}
                      </a>
                    </div>
                  </div>
                </v-col>
                <v-col cols="12" md="6" v-if="paciente.cliente.email">
                  <div class="info-item mb-3">
                    <div class="text-caption text-grey mb-1">
                      <v-icon size="small" class="mr-1">mdi-email</v-icon>
                      Email
                    </div>
                    <div class="text-body-1 font-weight-medium">
                      <a :href="`mailto:${paciente.cliente.email}`" class="text-decoration-none">
                        {{ paciente.cliente.email }}
                      </a>
                    </div>
                  </div>
                </v-col>
                <v-col cols="12">
                  <v-btn
                    :to="`/clientes/${paciente.cliente.id}`"
                    color="primary"
                    variant="outlined"
                    prepend-icon="mdi-eye"
                    size="small"
                  >
                    Ver Perfil del Cliente
                  </v-btn>
                </v-col>
              </v-row>
            </v-card-text>
          </v-card>

          <!-- Observaciones -->
          <v-card class="mb-4" elevation="1" v-if="paciente.observaciones">
            <v-card-title class="bg-grey-lighten-4 d-flex align-center">
              <v-icon class="mr-2" color="primary">mdi-note-text</v-icon>
              <span>Observaciones</span>
            </v-card-title>
            <v-card-text class="pa-4">
              <p class="text-body-1">{{ paciente.observaciones }}</p>
            </v-card-text>
          </v-card>

          <!-- Cuidados Específicos -->
          <v-card class="mb-4" elevation="1" v-if="paciente.cuidadosEspecificos">
            <v-card-title class="bg-grey-lighten-4 d-flex align-center">
              <v-icon class="mr-2" color="primary">mdi-heart-pulse</v-icon>
              <span>Cuidados Específicos</span>
            </v-card-title>
            <v-card-text class="pa-4">
              <p class="text-body-1">{{ paciente.cuidadosEspecificos }}</p>
            </v-card-text>
          </v-card>

          <!-- Dieta Recomendada -->
          <v-card class="mb-4" elevation="1" v-if="paciente.dietaRecomendada">
            <v-card-title class="bg-grey-lighten-4 d-flex align-center">
              <v-icon class="mr-2" color="primary">mdi-food</v-icon>
              <span>Dieta Recomendada</span>
            </v-card-title>
            <v-card-text class="pa-4">
              <p class="text-body-1">{{ paciente.dietaRecomendada }}</p>
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
                <div class="text-caption text-grey mb-1">ID del Paciente</div>
                <div class="text-body-1 font-weight-medium font-mono">
                  #{{ paciente.id }}
                </div>
              </div>
              <div class="info-item mb-3" v-if="paciente.createdAt">
                <div class="text-caption text-grey mb-1">
                  <v-icon size="small" class="mr-1">mdi-calendar-plus</v-icon>
                  Fecha de Registro
                </div>
                <div class="text-body-2">
                  {{ formatDateTime(paciente.createdAt) }}
                </div>
              </div>
              <div class="info-item mb-3" v-if="paciente.updatedAt">
                <div class="text-caption text-grey mb-1">
                  <v-icon size="small" class="mr-1">mdi-calendar-edit</v-icon>
                  Última Actualización
                </div>
                <div class="text-body-2">
                  {{ formatDateTime(paciente.updatedAt) }}
                </div>
              </div>
            </v-card-text>
          </v-card>

          <!-- Estadísticas Rápidas (si están disponibles) -->
          <v-card class="mb-4" elevation="1">
            <v-card-title class="bg-grey-lighten-4 d-flex align-center">
              <v-icon class="mr-2" color="primary">mdi-chart-line</v-icon>
              <span>Accesos Rápidos</span>
            </v-card-title>
            <v-card-text class="pa-4">
              <v-list density="compact">
                <v-list-item
                  prepend-icon="mdi-file-document"
                  title="Historial Clínico"
                  @click="verHistorial"
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
            <p class="text-h6">No se pudo cargar la información del paciente</p>
            <v-btn color="primary" @click="getPaciente" class="mt-4">
              Reintentar
            </v-btn>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { usePacientesStore } from '@/stores/pacientesStore'
import { useNotification } from '@/composables/useNotification'

const router = useRouter()
const route = useRoute()
const pacientesStore = usePacientesStore()
const { showError, showSuccess } = useNotification()

const loading = computed(() => pacientesStore.loading)
const paciente = computed(() => pacientesStore.currentPaciente)

const getPaciente = async () => {
  try {
    await pacientesStore.fetchPacienteById(route.params.id)
  } catch (error) {
    console.error('Error al cargar paciente:', error)
    showError('Error al cargar la información del paciente')
  }
}

const formatDate = (dateStr) => {
  if (!dateStr) return 'No especificada'
  try {
    const date = new Date(dateStr)
    return date.toLocaleDateString('es-ES', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    })
  } catch (error) {
    return dateStr
  }
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

const formatEstado = (estado) => {
  if (!estado) return 'No especificado'
  const estados = {
    'ACTIVO': 'Activo',
    'INACTIVO': 'Inactivo',
    'FALLECIDO': 'Fallecido'
  }
  return estados[estado] || estado
}

const getEstadoColor = (estado) => {
  if (!estado) return 'grey'
  const estadoUpper = estado.toUpperCase()
  const colores = {
    'ACTIVO': 'success',
    'INACTIVO': 'warning',
    'FALLECIDO': 'error',
  }
  return colores[estadoUpper] || 'grey'
}

const getEstadoIcon = (estado) => {
  if (!estado) return 'mdi-help-circle'
  const estadoUpper = estado.toUpperCase()
  const iconos = {
    'ACTIVO': 'mdi-check-circle',
    'INACTIVO': 'mdi-pause-circle',
    'FALLECIDO': 'mdi-close-circle',
  }
  return iconos[estadoUpper] || 'mdi-help-circle'
}

const editarPaciente = () => {
  router.push(`/pacientes/${route.params.id}/editar`)
}

const verHistorial = () => {
  router.push(`/pacientes/${route.params.id}/historial`)
}

const nuevaCita = () => {
  router.push({
    path: '/citas/nueva',
    query: { pacienteId: route.params.id }
  })
}

const verCitas = () => {
  router.push({
    path: '/citas',
    query: { pacienteId: route.params.id }
  })
}

const verFacturas = () => {
  router.push({
    path: '/facturas',
    query: { pacienteId: route.params.id }
  })
}

const deletePaciente = async () => {
  if (confirm('¿Estás seguro de que quieres eliminar este paciente? Esta acción no se puede deshacer.')) {
    try {
      await pacientesStore.deletePaciente(route.params.id)
      showSuccess('Paciente eliminado exitosamente')
      router.push('/pacientes')
    } catch (error) {
      console.error('Error al eliminar paciente:', error)
      showError(error.userMessage || 'Error al eliminar el paciente')
    }
  }
}

onMounted(() => {
  getPaciente()
})
</script>

<style scoped>
.info-item {
  min-height: 48px;
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
</style>
