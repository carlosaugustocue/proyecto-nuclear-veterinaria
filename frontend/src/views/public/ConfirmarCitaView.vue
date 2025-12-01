<template>
  <v-container class="fill-height d-flex align-center justify-center" fluid>
    <v-row justify="center">
      <v-col cols="12" sm="10" md="8" lg="6" xl="5">
        <v-card elevation="10" class="rounded-lg">
          <!-- Header -->
          <v-card-title class="bg-primary text-white py-6 text-center">
            <v-icon size="48" class="mb-2">mdi-calendar-check</v-icon>
            <h2 class="text-h4">Confirmación de Cita</h2>
            <p class="text-subtitle-1 mt-2">Clínica Veterinaria</p>
          </v-card-title>

          <v-card-text class="pa-8">
            <!-- Loading State -->
            <div v-if="loading" class="text-center py-8">
              <v-progress-circular
                indeterminate
                color="primary"
                size="64"
              ></v-progress-circular>
              <p class="text-h6 mt-4">Confirmando su cita...</p>
            </div>

            <!-- Success State -->
            <div v-else-if="success" class="text-center">
              <v-icon size="80" color="success" class="mb-4">mdi-check-circle</v-icon>
              <h3 class="text-h5 mb-4">¡Cita Confirmada Exitosamente!</h3>

              <v-alert type="success" variant="tonal" class="text-left mb-4">
                <div class="text-body-1">
                  <strong>Gracias por confirmar su asistencia</strong><br><br>
                  Su cita ha sido confirmada correctamente. Le hemos enviado un email de confirmación
                  al veterinario asignado.
                </div>
              </v-alert>

              <v-divider class="my-4"></v-divider>

              <!-- Detalles de la cita -->
              <div v-if="citaData" class="text-left">
                <h4 class="text-h6 mb-3">Detalles de su Cita:</h4>

                <v-list density="compact" class="bg-grey-lighten-5 rounded">
                  <v-list-item>
                    <template v-slot:prepend>
                      <v-icon>mdi-account</v-icon>
                    </template>
                    <v-list-item-title><strong>Cliente:</strong> {{ citaData.clienteObj?.nombreCompleto || citaData.clienteNombre }}</v-list-item-title>
                  </v-list-item>

                  <v-divider></v-divider>

                  <v-list-item>
                    <template v-slot:prepend>
                      <v-icon>mdi-paw</v-icon>
                    </template>
                    <v-list-item-title><strong>Paciente:</strong> {{ citaData.pacienteObj?.nombre || citaData.pacienteNombre }}</v-list-item-title>
                  </v-list-item>

                  <v-divider></v-divider>

                  <v-list-item>
                    <template v-slot:prepend>
                      <v-icon>mdi-calendar</v-icon>
                    </template>
                    <v-list-item-title><strong>Fecha:</strong> {{ formatDate(citaData.fecha) }}</v-list-item-title>
                  </v-list-item>

                  <v-divider></v-divider>

                  <v-list-item>
                    <template v-slot:prepend>
                      <v-icon>mdi-clock</v-icon>
                    </template>
                    <v-list-item-title><strong>Hora:</strong> {{ formatTime(citaData.hora) }}</v-list-item-title>
                  </v-list-item>

                  <v-divider></v-divider>

                  <v-list-item>
                    <template v-slot:prepend>
                      <v-icon>mdi-medical-bag</v-icon>
                    </template>
                    <v-list-item-title><strong>Servicio:</strong> {{ citaData.tipoServicioNombre || 'Consulta General' }}</v-list-item-title>
                  </v-list-item>

                  <v-divider></v-divider>

                  <v-list-item>
                    <template v-slot:prepend>
                      <v-icon>mdi-doctor</v-icon>
                    </template>
                    <v-list-item-title><strong>Veterinario:</strong> {{ citaData.veterinarioObj?.nombreCompleto || citaData.veterinarioNombre }}</v-list-item-title>
                  </v-list-item>
                </v-list>
              </div>

              <v-alert type="info" variant="tonal" class="mt-4 text-left">
                <v-icon class="mr-2">mdi-information</v-icon>
                <strong>Recordatorio:</strong> Por favor, llegue 10 minutos antes de su cita.
              </v-alert>
            </div>

            <!-- Error State -->
            <div v-else-if="error" class="text-center">
              <v-icon size="80" color="error" class="mb-4">mdi-close-circle</v-icon>
              <h3 class="text-h5 mb-4">No se pudo confirmar la cita</h3>

              <v-alert type="error" variant="tonal" class="text-left">
                <div class="text-body-1">
                  <strong>{{ errorMessage }}</strong>
                </div>
              </v-alert>

              <v-alert type="info" variant="tonal" class="mt-4 text-left">
                <v-icon class="mr-2">mdi-help-circle</v-icon>
                <strong>Posibles razones:</strong>
                <ul class="mt-2">
                  <li>El enlace de confirmación ya fue utilizado</li>
                  <li>La cita ya estaba confirmada previamente</li>
                  <li>El enlace ha expirado</li>
                  <li>La cita fue cancelada</li>
                </ul>
              </v-alert>

              <p class="mt-4 text-body-1">
                Si necesita ayuda, por favor contáctenos directamente a la clínica.
              </p>
            </div>
          </v-card-text>

          <v-divider></v-divider>

          <v-card-actions class="pa-4 justify-center">
            <v-btn
              v-if="!loading"
              color="primary"
              variant="flat"
              size="large"
              href="http://localhost:3000"
            >
              Volver al Inicio
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'

const route = useRoute()

const loading = ref(true)
const success = ref(false)
const error = ref(false)
const errorMessage = ref('')
const citaData = ref(null)

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr + 'T00:00:00')
  const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' }
  return date.toLocaleDateString('es-ES', options)
}

const formatTime = (timeStr) => {
  if (!timeStr) return ''
  // timeStr puede venir como "14:30:00" o "14:30"
  const parts = timeStr.split(':')
  return `${parts[0]}:${parts[1]}`
}

const confirmarCita = async () => {
  const token = route.query.token

  if (!token) {
    error.value = true
    errorMessage.value = 'No se proporcionó un token de confirmación válido'
    loading.value = false
    return
  }

  try {
    // Llamar al endpoint público de confirmación
    const response = await axios.post(
      `http://localhost:8080/api/v1/citas/confirmar-por-token?token=${token}`,
      null,
      {
        headers: {
          'Content-Type': 'application/json'
        }
      }
    )

    // Si la confirmación fue exitosa
    citaData.value = response.data
    success.value = true
    error.value = false
  } catch (err) {
    console.error('Error al confirmar cita:', err)
    error.value = true
    success.value = false

    if (err.response) {
      // El servidor respondió con un código de error
      if (err.response.status === 404) {
        errorMessage.value = 'No se encontró una cita con este token de confirmación'
      } else if (err.response.status === 400) {
        errorMessage.value = err.response.data.message || err.response.data.userMessage || 'La cita no puede ser confirmada en este momento'
      } else {
        errorMessage.value = err.response.data.userMessage || err.response.data.message || 'Ocurrió un error al confirmar la cita'
      }
    } else if (err.request) {
      // La petición fue hecha pero no hubo respuesta
      errorMessage.value = 'No se pudo conectar con el servidor. Por favor, intente más tarde.'
    } else {
      // Algo pasó al configurar la petición
      errorMessage.value = 'Ocurrió un error inesperado. Por favor, intente más tarde.'
    }
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  confirmarCita()
})
</script>

<style scoped>
.fill-height {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.v-card {
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
}
</style>
