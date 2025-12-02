<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12">
        <h1>Historia Clínica</h1>
        <p class="text-subtitle-1 mt-2">Seleccione una mascota para ver su historial médico</p>
      </v-col>
    </v-row>

    <v-row class="mb-6">
      <v-col cols="12" md="6">
        <v-autocomplete
          v-model="selectedPacienteId"
          :items="pacientes"
          item-title="nombre"
          item-value="id"
          label="Buscar Mascota"
          prepend-icon="mdi-magnify"
          @update:model-value="loadHistorial"
        ></v-autocomplete>
      </v-col>
    </v-row>

    <v-row v-if="selectedPacienteId && currentHistorial">
      <v-col cols="12">
        <!-- Información del Paciente -->
        <v-card class="mb-4" elevation="2">
          <v-card-title class="bg-primary text-white d-flex align-center">
            <v-icon left class="mr-2">mdi-clipboard-text</v-icon>
            Historial Clínico
          </v-card-title>
          <v-card-text class="pa-6">
            <v-row>
              <v-col cols="12" md="6">
                <div class="d-flex align-center mb-3">
                  <v-icon color="primary" class="mr-2">mdi-paw</v-icon>
                  <div>
                    <div class="text-overline">Paciente</div>
                    <div class="text-h6">{{ pacienteActual?.nombre || currentHistorial.pacienteNombre || 'N/A' }}</div>
                  </div>
                </div>
              </v-col>
              <v-col cols="12" md="6">
                <div class="d-flex align-center mb-3">
                  <v-icon color="primary" class="mr-2">mdi-account</v-icon>
                  <div>
                    <div class="text-overline">Propietario</div>
                    <div class="text-h6">{{ pacienteActual?.clienteNombre || 'N/A' }}</div>
                  </div>
                </div>
              </v-col>
              <v-col cols="12" md="4">
                <div class="text-overline">Especie</div>
                <v-chip color="secondary" class="mt-1">
                  {{ pacienteActual?.especieNombre || currentHistorial.pacienteEspecie || 'N/A' }}
                </v-chip>
              </v-col>
              <v-col cols="12" md="4">
                <div class="text-overline">Raza</div>
                <div class="text-body-1">{{ pacienteActual?.razaNombre || currentHistorial.pacienteRaza || 'N/A' }}</div>
              </v-col>
              <v-col cols="12" md="4">
                <div class="text-overline">Edad</div>
                <div class="text-body-1">{{ pacienteActual ? calcularEdad(pacienteActual.fechaNacimiento) : 'N/A' }}</div>
              </v-col>
            </v-row>

            <v-divider class="my-4"></v-divider>

            <v-btn
              color="primary"
              @click="showNuevaConsulta = true"
              prepend-icon="mdi-plus"
              size="large"
            >
              Nueva Consulta
            </v-btn>
          </v-card-text>
        </v-card>

        <!-- Timeline de Consultas -->
        <v-card elevation="2">
          <v-card-title class="bg-secondary text-white">
            <v-icon left class="mr-2">mdi-history</v-icon>
            Historial de Atenciones ({{ consultas.length }})
          </v-card-title>
          <v-card-text class="pa-4" style="max-height: 70vh; overflow-y: auto;">
            <v-alert v-if="consultas.length === 0" type="info" variant="tonal" class="my-4">
              <v-icon left>mdi-information</v-icon>
              No hay consultas registradas para este paciente
            </v-alert>

            <v-timeline side="end" v-else density="compact">
              <v-timeline-item
                v-for="(consulta, index) in consultas"
                :key="consulta.id"
                :dot-color="index === 0 ? 'success' : 'primary'"
                size="small"
              >
                <template v-slot:opposite>
                  <div class="text-caption font-weight-bold">
                    {{ formatDate(consulta.fechaConsulta) }}
                  </div>
                  <v-chip size="x-small" :color="index === 0 ? 'success' : 'grey'" class="mt-1">
                    {{ index === 0 ? 'Más reciente' : `Consulta #${consultas.length - index}` }}
                  </v-chip>
                </template>

                <v-card elevation="3" class="mb-2" style="max-width: 100%; word-wrap: break-word;">
                  <v-card-title class="d-flex align-center bg-grey-lighten-4 py-2">
                    <v-icon color="primary" class="mr-2" size="small">mdi-stethoscope</v-icon>
                    <span class="text-subtitle-2 text-truncate" style="max-width: 80%;">
                      {{ truncateText(consulta.motivo || 'Sin motivo especificado', 60) }}
                    </span>
                  </v-card-title>

                  <v-card-subtitle class="pt-2 pb-2">
                    <v-icon size="x-small" class="mr-1">mdi-doctor</v-icon>
                    <span class="text-caption">Dr. {{ consulta.veterinarioNombre || 'No especificado' }}</span>
                  </v-card-subtitle>

                  <v-divider></v-divider>

                  <v-card-text class="pa-3" style="max-height: 500px; overflow-y: auto;">
                    <v-expansion-panels variant="accordion" class="mt-2">
                      <v-expansion-panel v-if="consulta.anamnesis">
                        <v-expansion-panel-title>
                            <v-icon color="info" size="small" class="mr-2">mdi-clipboard-text</v-icon>
                          <span class="text-caption font-weight-bold">Anamnesis</span>
                        </v-expansion-panel-title>
                        <v-expansion-panel-text>
                          <div class="text-body-2" style="word-wrap: break-word; white-space: pre-wrap;">
                            {{ consulta.anamnesis }}
                          </div>
                        </v-expansion-panel-text>
                      </v-expansion-panel>

                      <v-expansion-panel v-if="consulta.examenFisico">
                        <v-expansion-panel-title>
                            <v-icon color="primary" size="small" class="mr-2">mdi-stethoscope</v-icon>
                          <span class="text-caption font-weight-bold">Examen Físico</span>
                        </v-expansion-panel-title>
                        <v-expansion-panel-text>
                          <div class="text-body-2" style="word-wrap: break-word; white-space: pre-wrap;">
                            {{ consulta.examenFisico }}
                          </div>
                        </v-expansion-panel-text>
                      </v-expansion-panel>

                      <v-expansion-panel v-if="consulta.planTratamiento">
                        <v-expansion-panel-title>
                          <v-icon color="success" size="small" class="mr-2">mdi-pill</v-icon>
                          <span class="text-caption font-weight-bold">Plan de Tratamiento</span>
                        </v-expansion-panel-title>
                        <v-expansion-panel-text>
                          <div class="text-body-2" style="word-wrap: break-word; white-space: pre-wrap;">
                            {{ consulta.planTratamiento }}
                          </div>
                        </v-expansion-panel-text>
                      </v-expansion-panel>

                      <v-expansion-panel v-if="consulta.observaciones">
                        <v-expansion-panel-title>
                          <v-icon color="warning" size="small" class="mr-2">mdi-note-text</v-icon>
                          <span class="text-caption font-weight-bold">Observaciones</span>
                        </v-expansion-panel-title>
                        <v-expansion-panel-text>
                          <v-alert variant="tonal" color="warning" density="compact">
                            <div class="text-body-2" style="word-wrap: break-word; white-space: pre-wrap;">
                              {{ consulta.observaciones }}
                        </div>
                          </v-alert>
                        </v-expansion-panel-text>
                      </v-expansion-panel>
                    </v-expansion-panels>

                    <v-row dense class="mt-2">
                      <v-col cols="12" v-if="consulta.diagnosticos && consulta.diagnosticos.length > 0">
                        <div class="mb-2">
                          <div class="d-flex align-center mb-1">
                            <v-icon color="error" size="x-small" class="mr-1">mdi-medical-bag</v-icon>
                            <span class="text-caption font-weight-bold">Diagnósticos</span>
                          </div>
                          <div class="d-flex flex-wrap gap-1">
                            <v-chip
                              v-for="diag in consulta.diagnosticos"
                              :key="diag.id"
                              size="x-small"
                              color="error"
                              variant="outlined"
                            >
                              {{ diag.descripcion }}
                            </v-chip>
                          </div>
                        </div>
                      </v-col>

                      <v-col cols="12" v-if="consulta.tratamientos && consulta.tratamientos.length > 0">
                        <div class="mb-2">
                          <div class="d-flex align-center mb-1">
                            <v-icon color="success" size="x-small" class="mr-1">mdi-medical-bag</v-icon>
                            <span class="text-caption font-weight-bold">Tratamientos Prescritos</span>
                          </div>
                          <div class="d-flex flex-wrap gap-1">
                            <v-chip
                              v-for="trat in consulta.tratamientos"
                              :key="trat.id"
                              size="x-small"
                              color="success"
                              variant="outlined"
                            >
                              {{ trat.descripcion || trat.nombre }}
                            </v-chip>
                          </div>
                        </div>
                      </v-col>

                      <v-col cols="12" v-if="consulta.pronostico">
                        <div class="mb-2">
                          <div class="d-flex align-center mb-1">
                            <v-icon color="purple" size="x-small" class="mr-1">mdi-trending-up</v-icon>
                            <span class="text-caption font-weight-bold">Pronóstico</span>
                          </div>
                          <v-chip size="small" color="purple" variant="outlined">
                            {{ consulta.pronostico }}
                          </v-chip>
                        </div>
                      </v-col>

                      <v-col cols="12" v-if="consulta.requiereSeguimiento">
                        <v-alert variant="tonal" color="info" density="compact" class="mt-2">
                          <v-icon size="x-small" class="mr-2">mdi-calendar-clock</v-icon>
                          <span class="text-caption">Requiere seguimiento</span>
                          <span v-if="consulta.fechaSeguimiento" class="text-caption"> el {{ formatDate(consulta.fechaSeguimiento) }}</span>
                          </v-alert>
                      </v-col>
                    </v-row>

                    <v-divider class="my-2"></v-divider>
                    <div class="d-flex justify-end">
                      <v-chip size="x-small" variant="outlined">
                        <v-icon size="x-small" class="mr-1">mdi-calendar</v-icon>
                        ID: {{ consulta.id }}
                      </v-chip>
                    </div>
                  </v-card-text>
                </v-card>
              </v-timeline-item>
            </v-timeline>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <!-- Dialog Nueva Consulta -->
    <v-dialog v-model="showNuevaConsulta" max-width="900px">
      <v-card>
        <v-card-title class="bg-primary text-white d-flex align-center">
          <v-icon class="mr-2">mdi-stethoscope</v-icon>
          Nueva Consulta Médica
        </v-card-title>
        <v-card-text class="pa-6">
          <v-form @submit.prevent="saveConsulta">
            <v-row>
              <v-col cols="12">
                <v-autocomplete
                  v-model="consultaForm.veterinarioId"
                  :items="veterinarios"
                  item-title="nombre"
                  item-value="id"
                  label="Veterinario *"
                  :rules="[v => !!v || 'Requerido']"
                  prepend-icon="mdi-doctor"
                  variant="outlined"
                ></v-autocomplete>
              </v-col>

              <v-col cols="12">
                <v-text-field
                  v-model="consultaForm.motivo"
                  label="Motivo de Consulta *"
                  :rules="[v => !!v || 'Requerido']"
                  prepend-icon="mdi-text"
                  variant="outlined"
                ></v-text-field>
              </v-col>

              <v-col cols="12">
                <v-textarea
                  v-model="consultaForm.anamnesis"
                  label="Anamnesis (Historia clínica)"
                  hint="Descripción de la historia clínica del paciente y síntomas"
                  rows="3"
                  prepend-icon="mdi-clipboard-text"
                  variant="outlined"
                ></v-textarea>
              </v-col>

              <v-col cols="12">
                <v-textarea
                  v-model="consultaForm.examenFisico"
                  label="Examen Físico"
                  hint="Hallazgos del examen físico del paciente"
                  rows="3"
                  prepend-icon="mdi-stethoscope"
                  variant="outlined"
                ></v-textarea>
              </v-col>

              <v-col cols="12">
                <v-textarea
                  v-model="consultaForm.planTratamiento"
                  label="Plan de Tratamiento"
                  hint="Tratamiento prescrito para el paciente"
                  rows="3"
                  prepend-icon="mdi-pill"
                  variant="outlined"
                ></v-textarea>
              </v-col>

              <v-col cols="12" md="6">
                <v-text-field
                  v-model="consultaForm.pronostico"
                  label="Pronóstico"
                  prepend-icon="mdi-trending-up"
                  variant="outlined"
                ></v-text-field>
              </v-col>

              <v-col cols="12" md="6">
                <v-switch
                  v-model="consultaForm.requiereSeguimiento"
                  label="Requiere seguimiento"
                  color="primary"
                  inset
                ></v-switch>
              </v-col>

              <v-col cols="12" v-if="consultaForm.requiereSeguimiento">
                <v-text-field
                  v-model="consultaForm.fechaSeguimiento"
                  label="Fecha de Seguimiento"
                  type="date"
                  prepend-icon="mdi-calendar"
                  variant="outlined"
                ></v-text-field>
              </v-col>

              <v-col cols="12">
                <v-textarea
                  v-model="consultaForm.observaciones"
                  label="Observaciones"
                  hint="Observaciones adicionales sobre la consulta"
                  rows="2"
                  prepend-icon="mdi-note-text"
                  variant="outlined"
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
                  size="large"
                  prepend-icon="mdi-content-save"
                >
                  Guardar Consulta
                </v-btn>
              </v-col>
              <v-col cols="6">
                <v-btn
                  color="secondary"
                  block
                  @click="cerrarDialogoConsulta"
                  size="large"
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
import { ref, reactive, onMounted, computed } from 'vue'
import { useHistorialesStore } from '@/stores/historialesStore'
import { usePacientesStore } from '@/stores/pacientesStore'
import { useReferenceData } from '@/composables/useReferenceData'
import { useNotification } from '@/composables/useNotification'

const historialesStore = useHistorialesStore()
const pacientesStore = usePacientesStore()
const { fetchPacientes, fetchUsuarios } = useReferenceData()
const { showSuccess, showError } = useNotification()

const selectedPacienteId = ref(null)
const pacientes = ref([])
const veterinarios = ref([])
const showNuevaConsulta = ref(false)
const pacienteActual = ref(null)

const loading = computed(() => historialesStore.loading)
const currentHistorial = computed(() => historialesStore.currentHistorial)
const consultas = computed(() => historialesStore.consultas)

const consultaForm = reactive({
  veterinarioId: null,
  motivo: '',
  anamnesis: '',
  examenFisico: '',
  planTratamiento: '',
  pronostico: '',
  observaciones: '',
  requiereSeguimiento: false,
  fechaSeguimiento: null,
})

const loadHistorial = async () => {
  if (!selectedPacienteId.value) return
  
  try {
    // Cargar los datos completos del paciente primero
    pacienteActual.value = await pacientesStore.fetchPacienteById(selectedPacienteId.value)
    
    // Intentar cargar el historial
    try {
    await historialesStore.fetchHistorialByPaciente(selectedPacienteId.value)

      // Si existe, cargar las consultas
      if (currentHistorial.value?.id) {
      await historialesStore.fetchConsultasByHistorial(currentHistorial.value.id)
    }
  } catch (error) {
      // Si no existe historial (404), crearlo automáticamente
    if (error.response?.status === 404) {
      try {
        const nuevoHistorial = await historialesStore.createHistorial(selectedPacienteId.value)
          if (nuevoHistorial?.id) {
            // Inicializar consultas vacías para el nuevo historial
          consultas.value = []
            showSuccess('Historial clínico creado exitosamente')
        }
      } catch (createError) {
        console.error('Error creating historial:', createError)
          const mensaje = createError.response?.data?.userMessage || 
                         createError.response?.data?.message || 
                         'Error al crear el historial clínico'
          showError(mensaje)
      }
    } else {
        // Otro tipo de error
      console.error('Error loading historial:', error)
        const mensaje = error.response?.data?.userMessage || 
                       error.response?.data?.message || 
                       'Error al cargar el historial clínico'
        showError(mensaje)
      }
    }
  } catch (error) {
    console.error('Error loading paciente:', error)
    showError('Error al cargar la información del paciente')
  }
}

const saveConsulta = async () => {
  if (!currentHistorial.value) {
    showError('No hay historial clínico disponible')
    return
  }
  if (!consultaForm.veterinarioId) {
    showError('Debe seleccionar un veterinario')
    return
  }
  if (!consultaForm.motivo || consultaForm.motivo.trim() === '') {
    showError('El motivo de consulta es requerido')
    return
  }
  
  try {
    await historialesStore.createConsulta(
      currentHistorial.value.id,
      consultaForm,
      consultaForm.veterinarioId
    )
    showSuccess('Consulta guardada exitosamente')
    cerrarDialogoConsulta()
  } catch (error) {
    console.error('Error saving consulta:', error)
    const mensaje = error.response?.data?.userMessage || 
                   error.response?.data?.message || 
                   'Error al guardar la consulta'
    showError(mensaje)
  }
}

const cerrarDialogoConsulta = () => {
  showNuevaConsulta.value = false
  Object.assign(consultaForm, {
    veterinarioId: null,
    motivo: '',
    anamnesis: '',
    examenFisico: '',
    planTratamiento: '',
    pronostico: '',
    observaciones: '',
    requiereSeguimiento: false,
    fechaSeguimiento: null,
  })
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

const calcularEdad = (fechaNacimiento) => {
  if (!fechaNacimiento) return 'N/A'
  const hoy = new Date()
  const nacimiento = new Date(fechaNacimiento)

  let años = hoy.getFullYear() - nacimiento.getFullYear()
  let meses = hoy.getMonth() - nacimiento.getMonth()

  if (meses < 0) {
    años--
    meses += 12
  }

  if (años > 0) {
    return años === 1 ? '1 año' : `${años} años`
  } else if (meses > 0) {
    return meses === 1 ? '1 mes' : `${meses} meses`
  } else {
    const dias = Math.floor((hoy - nacimiento) / (1000 * 60 * 60 * 24))
    return dias === 1 ? '1 día' : `${dias} días`
  }
}

const truncateText = (text, maxLength) => {
  if (!text) return ''
  if (text.length <= maxLength) return text
  return text.substring(0, maxLength) + '...'
}

onMounted(async () => {
  pacientes.value = await fetchPacientes()
  veterinarios.value = await fetchUsuarios('VETERINARIO')
})
</script>
