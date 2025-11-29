<template>
  <v-container fluid class="pa-6">
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

    <v-row v-if="loading">
      <v-col cols="12" class="text-center">
        <v-progress-circular indeterminate color="primary" size="64"></v-progress-circular>
        <p class="mt-4">Cargando historial clínico...</p>
      </v-col>
    </v-row>

    <template v-else>
      <!-- Información del Paciente -->
      <v-row>
        <v-col cols="12">
          <v-card class="mb-6">
            <v-card-title class="bg-primary text-white d-flex align-center">
              <v-icon class="mr-2">mdi-paw</v-icon>
              <span>Historial Clínico - {{ paciente?.nombre }}</span>
            </v-card-title>
            <v-card-text class="pa-4">
              <v-row>
                <v-col cols="12" md="3">
                  <strong>Especie:</strong> {{ paciente?.especie }}
                </v-col>
                <v-col cols="12" md="3">
                  <strong>Raza:</strong> {{ paciente?.raza }}
                </v-col>
                <v-col cols="12" md="3">
                  <strong>Edad:</strong> {{ paciente?.edad }} años
                </v-col>
                <v-col cols="12" md="3">
                  <strong>Propietario:</strong> {{ paciente?.clienteNombre }}
                </v-col>
              </v-row>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>

      <!-- Alert si viene desde cita -->
      <v-row v-if="fromCita">
        <v-col cols="12">
          <v-alert type="info" variant="tonal" prominent>
            <v-row align="center">
              <v-col>
                <div class="text-h6">{{ enProgreso ? 'Consulta en Progreso' : 'Registrar Consulta de Cita Completada' }}</div>
                <div class="text-body-2 mt-2">
                  {{ enProgreso ?
                    'Continúe registrando la consulta de la cita en progreso. Cuando termine, haga clic en "Completar Cita".' :
                    `Complete el formulario a continuación para registrar los hallazgos de la cita del ${formatDate(citaData.fecha)} a las ${citaData.hora}.`
                  }}
                </div>
              </v-col>
              <v-col cols="auto" v-if="enProgreso">
                <v-btn
                  color="success"
                  size="large"
                  @click="completarCitaYRegresar"
                  prepend-icon="mdi-check-circle"
                >
                  Completar Cita
                </v-btn>
              </v-col>
            </v-row>
          </v-alert>
        </v-col>
      </v-row>

      <!-- Formulario de Nueva Consulta -->
      <v-row>
        <v-col cols="12">
          <v-card>
            <v-card-title class="bg-secondary">
              <v-icon class="mr-2">mdi-file-document-plus</v-icon>
              Nueva Consulta Médica
            </v-card-title>
            <v-card-text class="pa-6">
              <v-form ref="formRef" @submit.prevent="guardarConsulta">
                <v-row>
                  <v-col cols="12" md="6">
                    <v-text-field
                      v-model="consulta.fecha"
                      label="Fecha de Consulta *"
                      type="date"
                      :rules="[rules.required]"
                      variant="outlined"
                    ></v-text-field>
                  </v-col>

                  <v-col cols="12" md="6">
                    <v-text-field
                      v-model="consulta.peso"
                      label="Peso (kg)"
                      type="number"
                      step="0.1"
                      variant="outlined"
                    ></v-text-field>
                  </v-col>
                </v-row>

                <v-row>
                  <v-col cols="12">
                    <v-textarea
                      v-model="consulta.motivoConsulta"
                      label="Motivo de Consulta *"
                      :rules="[rules.required]"
                      rows="3"
                      variant="outlined"
                    ></v-textarea>
                  </v-col>
                </v-row>

                <v-row>
                  <v-col cols="12">
                    <v-textarea
                      v-model="consulta.sintomas"
                      label="Síntomas Observados"
                      rows="3"
                      variant="outlined"
                    ></v-textarea>
                  </v-col>
                </v-row>

                <v-row>
                  <v-col cols="12" md="6">
                    <v-text-field
                      v-model="consulta.temperatura"
                      label="Temperatura (°C)"
                      type="number"
                      step="0.1"
                      variant="outlined"
                    ></v-text-field>
                  </v-col>

                  <v-col cols="12" md="6">
                    <v-text-field
                      v-model="consulta.frecuenciaCardiaca"
                      label="Frecuencia Cardíaca (lpm)"
                      type="number"
                      variant="outlined"
                    ></v-text-field>
                  </v-col>
                </v-row>

                <v-row>
                  <v-col cols="12">
                    <v-textarea
                      v-model="consulta.diagnostico"
                      label="Diagnóstico *"
                      :rules="[rules.required]"
                      rows="4"
                      variant="outlined"
                    ></v-textarea>
                  </v-col>
                </v-row>

                <v-row>
                  <v-col cols="12">
                    <v-textarea
                      v-model="consulta.tratamiento"
                      label="Tratamiento Prescrito *"
                      :rules="[rules.required]"
                      rows="4"
                      variant="outlined"
                    ></v-textarea>
                  </v-col>
                </v-row>

                <v-row>
                  <v-col cols="12">
                    <v-textarea
                      v-model="consulta.observaciones"
                      label="Observaciones Adicionales"
                      rows="3"
                      variant="outlined"
                    ></v-textarea>
                  </v-col>
                </v-row>

                <v-row>
                  <v-col cols="12" class="d-flex justify-end gap-2">
                    <v-btn
                      color="secondary"
                      @click="limpiarFormulario"
                      variant="outlined"
                    >
                      Limpiar
                    </v-btn>
                    <v-btn
                      color="primary"
                      type="submit"
                      :loading="guardando"
                    >
                      Guardar Consulta
                    </v-btn>
                  </v-col>
                </v-row>
              </v-form>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>

      <!-- Historial de Consultas -->
      <v-row class="mt-6">
        <v-col cols="12">
          <v-card>
            <v-card-title class="bg-info text-white">
              <v-icon class="mr-2">mdi-history</v-icon>
              Historial de Consultas
            </v-card-title>
            <v-card-text class="pa-4">
              <v-alert v-if="consultas.length === 0" type="info" variant="tonal">
                <v-icon class="mr-2">mdi-information</v-icon>
                No hay consultas registradas para este paciente.
              </v-alert>

              <v-timeline v-else side="end" align="start">
                <v-timeline-item
                  v-for="item in consultas"
                  :key="item.id"
                  dot-color="primary"
                  size="small"
                >
                  <template v-slot:opposite>
                    <div class="text-h6">{{ formatDate(item.fecha) }}</div>
                  </template>
                  <v-card>
                    <v-card-title class="bg-grey-lighten-4">
                      {{ item.motivoConsulta }}
                    </v-card-title>
                    <v-card-text class="pa-4">
                      <div class="mb-2" v-if="item.peso">
                        <strong>Peso:</strong> {{ item.peso }} kg
                      </div>
                      <div class="mb-2" v-if="item.temperatura">
                        <strong>Temperatura:</strong> {{ item.temperatura }} °C
                      </div>
                      <div class="mb-2" v-if="item.sintomas">
                        <strong>Síntomas:</strong> {{ item.sintomas }}
                      </div>
                      <div class="mb-2">
                        <strong>Diagnóstico:</strong> {{ item.diagnostico }}
                      </div>
                      <div class="mb-2">
                        <strong>Tratamiento:</strong> {{ item.tratamiento }}
                      </div>
                      <div class="mb-2" v-if="item.observaciones">
                        <strong>Observaciones:</strong> {{ item.observaciones }}
                      </div>
                      <div class="text-caption text-grey mt-2">
                        Atendido por: {{ item.veterinarioNombre || 'Dr. Desconocido' }}
                      </div>
                    </v-card-text>
                  </v-card>
                </v-timeline-item>
              </v-timeline>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </template>
  </v-container>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useNotification } from '@/composables/useNotification'
import axios from 'axios'

const route = useRoute()
const router = useRouter()
const { showSuccess, showError } = useNotification()

const loading = ref(true)
const guardando = ref(false)
const paciente = ref(null)
const consultas = ref([])
const formRef = ref(null)

// Datos de la cita (si viene desde completar cita)
const fromCita = computed(() => !!route.query.fromCita)
const enProgreso = computed(() => route.query.enProgreso === 'true')
const citaData = reactive({
  id: route.query.fromCita || null,
  fecha: route.query.citaFecha || null,
  hora: route.query.citaHora || null,
  motivo: route.query.citaMotivo || null
})

const consulta = reactive({
  fecha: new Date().toISOString().split('T')[0],
  peso: null,
  motivoConsulta: '',
  sintomas: '',
  temperatura: null,
  frecuenciaCardiaca: null,
  diagnostico: '',
  tratamiento: '',
  observaciones: ''
})

const rules = {
  required: v => !!v || 'Este campo es requerido'
}

// Pre-llenar motivo si viene desde cita
if (fromCita.value && citaData.motivo) {
  consulta.motivoConsulta = citaData.motivo
  consulta.observaciones = `Consulta correspondiente a cita del ${citaData.fecha} a las ${citaData.hora}`
}

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr + 'T00:00:00')
  return date.toLocaleDateString('es-ES', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

const cargarPaciente = async () => {
  try {
    const token = localStorage.getItem('token')
    const response = await axios.get(
      `http://localhost:8080/api/v1/pacientes/${route.params.id}`,
      {
        headers: { Authorization: `Bearer ${token}` }
      }
    )
    paciente.value = response.data
  } catch (error) {
    console.error('Error al cargar paciente:', error)
    showError('Error al cargar la información del paciente')
  }
}

const cargarConsultas = async () => {
  try {
    const token = localStorage.getItem('token')
    // Intentar cargar consultas del historial
    const response = await axios.get(
      `http://localhost:8080/api/v1/historiales-clinicos/paciente/${route.params.id}`,
      {
        headers: { Authorization: `Bearer ${token}` }
      }
    )

    if (response.data && response.data.consultas) {
      consultas.value = response.data.consultas.sort((a, b) =>
        new Date(b.fecha) - new Date(a.fecha)
      )
    }
  } catch (error) {
    console.error('Error al cargar consultas:', error)
    // No mostrar error si simplemente no existen consultas
    consultas.value = []
  }
}

const guardarConsulta = async () => {
  const { valid } = await formRef.value.validate()

  if (!valid) {
    showError('Por favor, complete todos los campos requeridos')
    return
  }

  guardando.value = true

  try {
    const token = localStorage.getItem('token')
    const userStr = localStorage.getItem('user')
    const user = userStr ? JSON.parse(userStr) : null

    if (!user) {
      showError('No se pudo obtener la información del veterinario')
      return
    }

    // Obtener o crear historial clínico
    let historialId = null
    try {
      const historialResponse = await axios.get(
        `http://localhost:8080/api/v1/historiales-clinicos/paciente/${route.params.id}`,
        { headers: { Authorization: `Bearer ${token}` } }
      )
      historialId = historialResponse.data?.id
    } catch (error) {
      // Si no existe, el backend lo creará automáticamente
      console.log('No existe historial clínico, se creará automáticamente')
    }

    // Preparar signos vitales
    const signosVitales = {}
    if (consulta.peso) signosVitales.pesoKg = parseFloat(consulta.peso)
    if (consulta.temperatura) signosVitales.temperatura = parseFloat(consulta.temperatura)
    if (consulta.frecuenciaCardiaca) signosVitales.frecuenciaCardiaca = parseInt(consulta.frecuenciaCardiaca)

    // Preparar datos de la consulta según el modelo del backend
    const consultaData = {
      historialClinicoId: historialId,
      veterinarioId: user.id,
      fechaConsulta: consulta.fecha,
      motivo: consulta.motivoConsulta,
      anamnesis: consulta.sintomas || null,
      examenFisico: consulta.diagnostico || null,
      planTratamiento: consulta.tratamiento || null,
      observaciones: consulta.observaciones || null,
      citaId: citaData.id ? parseInt(citaData.id) : null,
      signosVitales: Object.keys(signosVitales).length > 0 ? signosVitales : null
    }

    await axios.post(
      'http://localhost:8080/api/v1/consultas',
      consultaData,
      {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      }
    )

    showSuccess('Consulta guardada exitosamente')
    limpiarFormulario()
    await cargarConsultas()

    // Si vino desde una cita, limpiar los parámetros de query
    if (fromCita.value) {
      router.replace({ path: `/pacientes/${route.params.id}/historial` })
    }
  } catch (error) {
    console.error('Error al guardar consulta:', error)
    const mensaje = error.response?.data?.userMessage ||
                    error.response?.data?.message ||
                    'Error al guardar la consulta'
    showError(mensaje)
  } finally {
    guardando.value = false
  }
}

const limpiarFormulario = () => {
  consulta.fecha = new Date().toISOString().split('T')[0]
  consulta.peso = null
  consulta.motivoConsulta = ''
  consulta.sintomas = ''
  consulta.temperatura = null
  consulta.frecuenciaCardiaca = null
  consulta.diagnostico = ''
  consulta.tratamiento = ''
  consulta.observaciones = ''
  formRef.value?.resetValidation()
}

const completarCitaYRegresar = async () => {
  if (!citaData.id) {
    showError('No se pudo obtener el ID de la cita')
    return
  }

  try {
    const token = localStorage.getItem('token')
    await axios.patch(
      `http://localhost:8080/api/v1/citas/${citaData.id}/completar`,
      {},
      {
        headers: {
          Authorization: `Bearer ${token}`
        }
      }
    )

    showSuccess('Cita completada exitosamente')
    // Redirigir de vuelta a la lista de citas
    router.push('/citas')
  } catch (error) {
    console.error('Error al completar cita:', error)
    const mensaje = error.response?.data?.userMessage ||
                    error.response?.data?.message ||
                    'Error al completar la cita'
    showError(mensaje)
  }
}

onMounted(async () => {
  loading.value = true
  await Promise.all([
    cargarPaciente(),
    cargarConsultas()
  ])
  loading.value = false
})
</script>

<style scoped>
.gap-2 {
  gap: 8px;
}
</style>
