<template>
  <div>
    <div class="mb-6">
      <v-btn
        variant="text"
        prepend-icon="mdi-arrow-left"
        @click="volver"
      >
        Volver a Pacientes
      </v-btn>
    </div>

    <v-row v-if="loading" class="justify-center">
      <v-col cols="12" class="text-center">
        <v-progress-circular indeterminate color="primary" size="64"></v-progress-circular>
        <p class="mt-4">Cargando información del paciente...</p>
      </v-col>
    </v-row>

    <div v-else-if="paciente">
      <!-- Cabecera del Paciente -->
      <v-card class="mb-4">
        <v-card-text class="pa-6">
          <v-row>
            <v-col cols="12" md="3" class="text-center">
              <v-avatar size="150">
                <v-img
                  v-if="paciente.fotoUrl"
                  :src="paciente.fotoUrl"
                  :alt="paciente.nombre"
                ></v-img>
                <v-icon v-else size="80" color="grey-lighten-1">mdi-paw</v-icon>
              </v-avatar>
            </v-col>

            <v-col cols="12" md="9">
              <div class="d-flex justify-space-between align-start mb-4">
                <div>
                  <h1 class="text-h4 font-weight-bold mb-2">{{ paciente.nombre }}</h1>
                  <v-chip :color="getEspecieColor(paciente.especie)" variant="tonal" class="mr-2">
                    <v-icon start>{{ getEspecieIcon(paciente.especie) }}</v-icon>
                    {{ getEspecieLabel(paciente.especie) }}
                  </v-chip>
                  <v-chip :color="getEstadoColor(paciente.estado)" variant="tonal">
                    {{ getEstadoLabel(paciente.estado) }}
                  </v-chip>
                </div>
                <div>
                  <v-btn
                    color="primary"
                    prepend-icon="mdi-pencil"
                    :to="`/pacientes/${paciente.id}/editar`"
                  >
                    Editar
                  </v-btn>
                </div>
              </div>

              <v-row class="mt-4">
                <v-col cols="12" sm="6" md="3">
                  <div class="text-caption text-grey-darken-1">Edad</div>
                  <div class="text-h6 font-weight-medium">
                    {{ calcularEdad(paciente.fechaNacimiento) }} años
                  </div>
                </v-col>
                <v-col cols="12" sm="6" md="3">
                  <div class="text-caption text-grey-darken-1">Sexo</div>
                  <div class="text-h6 font-weight-medium">
                    {{ paciente.sexo === 'MACHO' ? 'Macho' : 'Hembra' }}
                  </div>
                </v-col>
                <v-col cols="12" sm="6" md="3">
                  <div class="text-caption text-grey-darken-1">Peso</div>
                  <div class="text-h6 font-weight-medium">
                    {{ paciente.pesoKg ? `${paciente.pesoKg} kg` : 'No registrado' }}
                  </div>
                </v-col>
                <v-col cols="12" sm="6" md="3">
                  <div class="text-caption text-grey-darken-1">Raza</div>
                  <div class="text-h6 font-weight-medium">
                    {{ paciente.raza || 'No especificada' }}
                  </div>
                </v-col>
              </v-row>
            </v-col>
          </v-row>
        </v-card-text>
      </v-card>

      <!-- Información Detallada -->
      <v-row>
        <!-- Información General -->
        <v-col cols="12" md="6">
          <v-card>
            <v-card-title class="bg-primary">
              <v-icon class="mr-2">mdi-information</v-icon>
              Información General
            </v-card-title>
            <v-card-text class="pa-4">
              <v-list>
                <v-list-item>
                  <template #prepend>
                    <v-icon>mdi-calendar</v-icon>
                  </template>
                  <v-list-item-title>Fecha de Nacimiento</v-list-item-title>
                  <v-list-item-subtitle>{{ formatearFecha(paciente.fechaNacimiento) }}</v-list-item-subtitle>
                </v-list-item>

                <v-divider></v-divider>

                <v-list-item>
                  <template #prepend>
                    <v-icon>mdi-palette</v-icon>
                  </template>
                  <v-list-item-title>Color</v-list-item-title>
                  <v-list-item-subtitle>{{ paciente.color || 'No especificado' }}</v-list-item-subtitle>
                </v-list-item>

                <v-divider></v-divider>

                <v-list-item>
                  <template #prepend>
                    <v-icon>mdi-chip</v-icon>
                  </template>
                  <v-list-item-title>Microchip</v-list-item-title>
                  <v-list-item-subtitle>{{ paciente.microchip || 'No registrado' }}</v-list-item-subtitle>
                </v-list-item>

                <v-divider></v-divider>

                <v-list-item>
                  <template #prepend>
                    <v-icon>mdi-circle</v-icon>
                  </template>
                  <v-list-item-title>Estado</v-list-item-title>
                  <v-list-item-subtitle>
                    <v-chip :color="getEstadoColor(paciente.estado)" variant="tonal" size="small">
                      {{ getEstadoLabel(paciente.estado) }}
                    </v-chip>
                  </v-list-item-subtitle>
                </v-list-item>
              </v-list>
            </v-card-text>
          </v-card>
        </v-col>

        <!-- Información del Propietario -->
        <v-col cols="12" md="6">
          <v-card>
            <v-card-title class="bg-secondary">
              <v-icon class="mr-2">mdi-account</v-icon>
              Información del Propietario
            </v-card-title>
            <v-card-text class="pa-4">
              <div v-if="paciente.cliente">
                <v-list>
                  <v-list-item>
                    <template #prepend>
                      <v-icon>mdi-account-circle</v-icon>
                    </template>
                    <v-list-item-title>Nombre</v-list-item-title>
                    <v-list-item-subtitle>{{ paciente.cliente.nombre }}</v-list-item-subtitle>
                  </v-list-item>

                  <v-divider></v-divider>

                  <v-list-item>
                    <template #prepend>
                      <v-icon>mdi-email</v-icon>
                    </template>
                    <v-list-item-title>Email</v-list-item-title>
                    <v-list-item-subtitle>{{ paciente.cliente.email }}</v-list-item-subtitle>
                  </v-list-item>

                  <v-divider></v-divider>

                  <v-list-item>
                    <template #prepend>
                      <v-icon>mdi-phone</v-icon>
                    </template>
                    <v-list-item-title>Teléfono</v-list-item-title>
                    <v-list-item-subtitle>{{ paciente.cliente.telefono || 'No registrado' }}</v-list-item-subtitle>
                  </v-list-item>
                </v-list>

                <div class="mt-4">
                  <v-btn
                    block
                    color="secondary"
                    variant="outlined"
                    :to="`/clientes/${paciente.cliente.id}`"
                  >
                    Ver Perfil del Cliente
                  </v-btn>
                </div>
              </div>
              <div v-else class="text-center pa-8">
                <v-icon size="64" color="grey-lighten-1">mdi-account-off</v-icon>
                <p class="text-grey-darken-1 mt-2">Sin propietario asignado</p>
              </div>
            </v-card-text>
          </v-card>
        </v-col>

        <!-- Observaciones -->
        <v-col v-if="paciente.observaciones" cols="12">
          <v-card>
            <v-card-title class="bg-accent">
              <v-icon class="mr-2">mdi-note-text</v-icon>
              Observaciones
            </v-card-title>
            <v-card-text class="pa-4">
              <p class="text-body-1">{{ paciente.observaciones }}</p>
            </v-card-text>
          </v-card>
        </v-col>

        <!-- Historial Médico (Placeholder) -->
        <v-col cols="12">
          <v-card>
            <v-card-title class="bg-success">
              <v-icon class="mr-2">mdi-medical-bag</v-icon>
              Historial Médico
            </v-card-title>
            <v-card-text class="pa-4 text-center">
              <v-icon size="64" color="grey-lighten-1">mdi-clipboard-text</v-icon>
              <p class="text-grey-darken-1 mt-2">
                Historial médico próximamente disponible
              </p>
            </v-card-text>
          </v-card>
        </v-col>

        <!-- Citas Programadas (Placeholder) -->
        <v-col cols="12" md="6">
          <v-card>
            <v-card-title class="bg-primary">
              <v-icon class="mr-2">mdi-calendar-clock</v-icon>
              Citas Programadas
            </v-card-title>
            <v-card-text class="pa-4 text-center">
              <v-icon size="48" color="grey-lighten-1">mdi-calendar</v-icon>
              <p class="text-grey-darken-1 mt-2">
                No hay citas programadas
              </p>
            </v-card-text>
          </v-card>
        </v-col>

        <!-- Vacunas (Placeholder) -->
        <v-col cols="12" md="6">
          <v-card>
            <v-card-title class="bg-warning">
              <v-icon class="mr-2">mdi-needle</v-icon>
              Vacunas
            </v-card-title>
            <v-card-text class="pa-4 text-center">
              <v-icon size="48" color="grey-lighten-1">mdi-needle</v-icon>
              <p class="text-grey-darken-1 mt-2">
                Registro de vacunas próximamente disponible
              </p>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>

      <!-- Información de Auditoría -->
      <v-card class="mt-4">
        <v-card-title>
          <v-icon class="mr-2">mdi-clock-outline</v-icon>
          Información de Registro
        </v-card-title>
        <v-card-text>
          <v-row>
            <v-col cols="12" md="6">
              <div class="text-caption text-grey-darken-1">Fecha de Registro</div>
              <div class="text-body-1">
                {{ formatearFechaHora(paciente.createdAt) }}
              </div>
            </v-col>
            <v-col cols="12" md="6">
              <div class="text-caption text-grey-darken-1">Última Actualización</div>
              <div class="text-body-1">
                {{ formatearFechaHora(paciente.updatedAt) }}
              </div>
            </v-col>
          </v-row>
        </v-card-text>
      </v-card>
    </div>

    <v-row v-else>
      <v-col cols="12" class="text-center pa-8">
        <v-icon size="64" color="grey-lighten-1">mdi-alert-circle</v-icon>
        <p class="text-h6 text-grey-darken-1 mt-2">No se encontró el paciente</p>
      </v-col>
    </v-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { usePacientesStore } from '@/store/pacientes'
import { storeToRefs } from 'pinia'

const router = useRouter()
const route = useRoute()
const pacientesStore = usePacientesStore()
const { loading } = storeToRefs(pacientesStore)

const paciente = ref(null)

onMounted(async () => {
  try {
    paciente.value = await pacientesStore.cargarPaciente(route.params.id)
  } catch (error) {
    console.error('Error cargando paciente:', error)
  }
})

function volver() {
  pacientesStore.clearPacienteActual()
  router.push('/pacientes')
}

function calcularEdad(fechaNacimiento) {
  if (!fechaNacimiento) return 0
  const hoy = new Date()
  const nacimiento = new Date(fechaNacimiento)
  let edad = hoy.getFullYear() - nacimiento.getFullYear()
  const mes = hoy.getMonth() - nacimiento.getMonth()
  if (mes < 0 || (mes === 0 && hoy.getDate() < nacimiento.getDate())) {
    edad--
  }
  return edad
}

function formatearFecha(fecha) {
  if (!fecha) return 'No disponible'
  const date = new Date(fecha)
  return date.toLocaleDateString('es-ES', {
    day: '2-digit',
    month: 'long',
    year: 'numeric',
  })
}

function formatearFechaHora(fecha) {
  if (!fecha) return 'No disponible'
  const date = new Date(fecha)
  return date.toLocaleString('es-ES', {
    day: '2-digit',
    month: 'long',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
  })
}

function getEspecieIcon(especie) {
  const icons = {
    PERRO: 'mdi-dog',
    GATO: 'mdi-cat',
    AVE: 'mdi-bird',
    REPTIL: 'mdi-snake',
    ROEDOR: 'mdi-rodent',
    OTRO: 'mdi-paw',
  }
  return icons[especie] || 'mdi-paw'
}

function getEspecieColor(especie) {
  const colors = {
    PERRO: 'brown',
    GATO: 'orange',
    AVE: 'blue',
    REPTIL: 'green',
    ROEDOR: 'grey',
    OTRO: 'purple',
  }
  return colors[especie] || 'grey'
}

function getEspecieLabel(especie) {
  const labels = {
    PERRO: 'Perro',
    GATO: 'Gato',
    AVE: 'Ave',
    REPTIL: 'Reptil',
    ROEDOR: 'Roedor',
    OTRO: 'Otro',
  }
  return labels[especie] || especie
}

function getEstadoColor(estado) {
  const colors = {
    ACTIVO: 'success',
    INACTIVO: 'warning',
    FALLECIDO: 'error',
  }
  return colors[estado] || 'grey'
}

function getEstadoLabel(estado) {
  const labels = {
    ACTIVO: 'Activo',
    INACTIVO: 'Inactivo',
    FALLECIDO: 'Fallecido',
  }
  return labels[estado] || estado
}
</script>
