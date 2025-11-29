<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12">
        <h1>Historia Clínica</h1>
        <p class="text-subtitle-1 mt-2">Seleccione un paciente para ver su historial médico</p>
      </v-col>
    </v-row>

    <v-row class="mb-6">
      <v-col cols="12" md="6">
        <v-autocomplete
          v-model="selectedPacienteId"
          :items="pacientes"
          item-title="nombre"
          item-value="id"
          label="Buscar Paciente"
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
          <v-card-text class="pa-4">
            <v-alert v-if="consultas.length === 0" type="info" variant="tonal" class="my-4">
              <v-icon left>mdi-information</v-icon>
              No hay consultas registradas para este paciente
            </v-alert>

            <v-timeline side="end" v-else>
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

                <v-card elevation="3" class="mb-2">
                  <v-card-title class="d-flex align-center bg-grey-lighten-4">
                    <v-icon color="primary" class="mr-2">mdi-stethoscope</v-icon>
                    {{ consulta.motivo || 'Sin motivo especificado' }}
                  </v-card-title>

                  <v-card-subtitle class="pt-2">
                    <v-icon size="small" class="mr-1">mdi-doctor</v-icon>
                    Dr. {{ consulta.veterinarioNombre || 'No especificado' }}
                  </v-card-subtitle>

                  <v-divider></v-divider>

                  <v-card-text class="pa-4">
                    <v-row>
                      <v-col cols="12" v-if="consulta.anamnesis">
                        <div class="mb-3">
                          <div class="d-flex align-center mb-2">
                            <v-icon color="info" size="small" class="mr-2">mdi-clipboard-text</v-icon>
                            <span class="text-subtitle-2 font-weight-bold">Anamnesis</span>
                          </div>
                          <div class="text-body-1 pl-7">
                            {{ consulta.anamnesis }}
                          </div>
                        </div>
                      </v-col>

                      <v-col cols="12" v-if="consulta.examenFisico">
                        <div class="mb-3">
                          <div class="d-flex align-center mb-2">
                            <v-icon color="primary" size="small" class="mr-2">mdi-stethoscope</v-icon>
                            <span class="text-subtitle-2 font-weight-bold">Examen Físico</span>
                          </div>
                          <div class="text-body-1 pl-7">
                            {{ consulta.examenFisico }}
                          </div>
                        </div>
                      </v-col>

                      <v-col cols="12" v-if="consulta.diagnosticos && consulta.diagnosticos.length > 0">
                        <div class="mb-3">
                          <div class="d-flex align-center mb-2">
                            <v-icon color="error" size="small" class="mr-2">mdi-medical-bag</v-icon>
                            <span class="text-subtitle-2 font-weight-bold">Diagnósticos</span>
                          </div>
                          <div class="text-body-1 pl-7">
                            <v-chip
                              v-for="diag in consulta.diagnosticos"
                              :key="diag.id"
                              class="ma-1"
                              color="error"
                              variant="outlined"
                            >
                              {{ diag.descripcion }}
                            </v-chip>
                          </div>
                        </div>
                      </v-col>

                      <v-col cols="12" v-if="consulta.planTratamiento">
                        <div class="mb-3">
                          <div class="d-flex align-center mb-2">
                            <v-icon color="success" size="small" class="mr-2">mdi-pill</v-icon>
                            <span class="text-subtitle-2 font-weight-bold">Plan de Tratamiento</span>
                          </div>
                          <div class="text-body-1 pl-7">
                            {{ consulta.planTratamiento }}
                          </div>
                        </div>
                      </v-col>

                      <v-col cols="12" v-if="consulta.tratamientos && consulta.tratamientos.length > 0">
                        <div class="mb-3">
                          <div class="d-flex align-center mb-2">
                            <v-icon color="success" size="small" class="mr-2">mdi-medical-bag</v-icon>
                            <span class="text-subtitle-2 font-weight-bold">Tratamientos Prescritos</span>
                          </div>
                          <div class="text-body-1 pl-7">
                            <v-chip
                              v-for="trat in consulta.tratamientos"
                              :key="trat.id"
                              class="ma-1"
                              color="success"
                              variant="outlined"
                            >
                              {{ trat.descripcion }}
                            </v-chip>
                          </div>
                        </div>
                      </v-col>

                      <v-col cols="12" v-if="consulta.pronostico">
                        <div class="mb-3">
                          <div class="d-flex align-center mb-2">
                            <v-icon color="purple" size="small" class="mr-2">mdi-trending-up</v-icon>
                            <span class="text-subtitle-2 font-weight-bold">Pronóstico</span>
                          </div>
                          <div class="text-body-1 pl-7">
                            <v-chip color="purple" variant="outlined">
                              {{ consulta.pronostico }}
                            </v-chip>
                          </div>
                        </div>
                      </v-col>

                      <v-col cols="12" v-if="consulta.observaciones">
                        <div class="mb-2">
                          <div class="d-flex align-center mb-2">
                            <v-icon color="warning" size="small" class="mr-2">mdi-note-text</v-icon>
                            <span class="text-subtitle-2 font-weight-bold">Observaciones</span>
                          </div>
                          <v-alert variant="tonal" color="warning" density="compact" class="pl-7">
                            {{ consulta.observaciones }}
                          </v-alert>
                        </div>
                      </v-col>

                      <v-col cols="12" v-if="consulta.requiereSeguimiento">
                        <div class="mb-2">
                          <v-alert variant="tonal" color="info" density="compact">
                            <v-icon size="small" class="mr-2">mdi-calendar-clock</v-icon>
                            Requiere seguimiento
                            <span v-if="consulta.fechaSeguimiento"> el {{ formatDate(consulta.fechaSeguimiento) }}</span>
                          </v-alert>
                        </div>
                      </v-col>
                    </v-row>
                  </v-card-text>

                  <v-card-actions class="bg-grey-lighten-5">
                    <v-spacer></v-spacer>
                    <v-chip size="small" variant="outlined">
                      <v-icon size="small" class="mr-1">mdi-calendar</v-icon>
                      ID: {{ consulta.id }}
                    </v-chip>
                  </v-card-actions>
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

const historialesStore = useHistorialesStore()
const pacientesStore = usePacientesStore()
const { fetchPacientes, fetchUsuarios } = useReferenceData()

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
    // Cargar el historial
    await historialesStore.fetchHistorialByPaciente(selectedPacienteId.value)

    // Cargar los datos completos del paciente
    if (selectedPacienteId.value) {
      pacienteActual.value = await pacientesStore.fetchPacienteById(selectedPacienteId.value)
    }

    // Cargar las consultas del historial
    if (currentHistorial.value) {
      await historialesStore.fetchConsultasByHistorial(currentHistorial.value.id)
    }
  } catch (error) {
    // Si no existe historial (404), intentar crearlo
    if (error.response?.status === 404) {
      console.log('Historial no existe, creando uno nuevo...')
      try {
        const nuevoHistorial = await historialesStore.createHistorial(selectedPacienteId.value)
        if (nuevoHistorial) {
          consultas.value = []
        }
        // Cargar los datos del paciente de todas formas
        if (selectedPacienteId.value) {
          pacienteActual.value = await pacientesStore.fetchPacienteById(selectedPacienteId.value)
        }
      } catch (createError) {
        console.error('Error creating historial:', createError)
      }
    } else {
      console.error('Error loading historial:', error)
    }
  }
}

const saveConsulta = async () => {
  if (!currentHistorial.value) return
  if (!consultaForm.veterinarioId) {
    console.error('Debe seleccionar un veterinario')
    return
  }
  try {
    await historialesStore.createConsulta(
      currentHistorial.value.id,
      consultaForm,
      consultaForm.veterinarioId
    )
    cerrarDialogoConsulta()
  } catch (error) {
    console.error('Error saving consulta:', error)
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

onMounted(async () => {
  pacientes.value = await fetchPacientes()
  veterinarios.value = await fetchUsuarios('VETERINARIO')
})
</script>
