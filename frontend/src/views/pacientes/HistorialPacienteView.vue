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
            <v-card-text class="pa-4" style="max-height: 70vh; overflow-y: auto;">
              <v-alert v-if="consultas.length === 0" type="info" variant="tonal">
                <v-icon class="mr-2">mdi-information</v-icon>
                No hay consultas registradas para este paciente.
              </v-alert>

              <v-timeline v-else side="end" align="start" density="compact">
                <v-timeline-item
                  v-for="item in consultas"
                  :key="item.id"
                  dot-color="primary"
                  size="small"
                >
                  <template v-slot:opposite>
                    <div class="d-flex flex-column align-end">
                      <v-chip size="small" color="primary" variant="outlined" class="mb-1 font-weight-bold">
                        #{{ item.id }}
                      </v-chip>
                      <div class="text-subtitle-2 font-weight-medium">{{ formatDate(item.fechaConsulta || item.fecha) }}</div>
                    </div>
                  </template>
                  <v-card class="mb-3" style="max-width: 100%;">
                    <v-card-title class="bg-grey-lighten-4 d-flex justify-space-between align-center py-2">
                      <div class="d-flex align-center gap-2" style="max-width: 70%;">
                        <v-chip size="x-small" color="primary" variant="outlined" class="font-weight-bold">
                          #{{ item.id }}
                        </v-chip>
                        <span class="text-subtitle-2 text-truncate">
                          {{ truncateText(item.motivo || item.motivoConsulta, 50) }}
                        </span>
                      </div>
                      <v-btn
                        v-if="!item.estaFinalizada"
                        icon="mdi-pencil"
                        size="x-small"
                        variant="text"
                        @click="editarConsulta(item)"
                        color="primary"
                      ></v-btn>
                    </v-card-title>
                    <v-card-text class="pa-3" style="max-height: 400px; overflow-y: auto;">
                      <div class="text-caption text-grey mb-2 d-flex align-center">
                        <v-icon size="x-small" class="mr-1">mdi-calendar</v-icon>
                        <span><strong>Fecha:</strong> {{ formatDate(item.fechaConsulta || item.fecha) }}</span>
                      </div>
                      <v-row dense>
                        <v-col cols="12" md="6" v-if="item.signosVitales?.pesoKg">
                          <div class="text-caption">
                            <v-icon size="x-small" class="mr-1">mdi-weight-kilogram</v-icon>
                            <strong>Peso:</strong> {{ item.signosVitales.pesoKg }} kg
                          </div>
                        </v-col>
                        <v-col cols="12" md="6" v-if="item.signosVitales?.temperatura">
                          <div class="text-caption">
                            <v-icon size="x-small" class="mr-1">mdi-thermometer</v-icon>
                            <strong>Temperatura:</strong> {{ item.signosVitales.temperatura }} °C
                          </div>
                        </v-col>
                        <v-col cols="12" md="6" v-if="item.signosVitales?.frecuenciaCardiaca">
                          <div class="text-caption">
                            <v-icon size="x-small" class="mr-1">mdi-heart-pulse</v-icon>
                            <strong>Frecuencia Cardíaca:</strong> {{ item.signosVitales.frecuenciaCardiaca }} lpm
                          </div>
                        </v-col>
                      </v-row>
                      
                      <v-expansion-panels variant="accordion" class="mt-2">
                        <v-expansion-panel v-if="item.anamnesis || item.sintomas">
                          <v-expansion-panel-title>
                            <v-icon size="small" class="mr-2">mdi-clipboard-text</v-icon>
                            <span class="text-caption">Anamnesis / Síntomas</span>
                          </v-expansion-panel-title>
                          <v-expansion-panel-text>
                            <div class="text-body-2">{{ item.anamnesis || item.sintomas }}</div>
                          </v-expansion-panel-text>
                        </v-expansion-panel>
                        
                        <v-expansion-panel v-if="item.examenFisico || item.diagnostico">
                          <v-expansion-panel-title>
                            <v-icon size="small" class="mr-2">mdi-stethoscope</v-icon>
                            <span class="text-caption">Diagnóstico</span>
                          </v-expansion-panel-title>
                          <v-expansion-panel-text>
                            <div class="text-body-2">{{ item.examenFisico || item.diagnostico || 'No especificado' }}</div>
                          </v-expansion-panel-text>
                        </v-expansion-panel>
                        
                        <v-expansion-panel v-if="item.planTratamiento || item.tratamiento">
                          <v-expansion-panel-title>
                            <v-icon size="small" class="mr-2">mdi-pill</v-icon>
                            <span class="text-caption">Tratamiento</span>
                          </v-expansion-panel-title>
                          <v-expansion-panel-text>
                            <div class="text-body-2">{{ item.planTratamiento || item.tratamiento || 'No especificado' }}</div>
                          </v-expansion-panel-text>
                        </v-expansion-panel>
                        
                        <v-expansion-panel v-if="item.observaciones">
                          <v-expansion-panel-title>
                            <v-icon size="small" class="mr-2">mdi-note-text</v-icon>
                            <span class="text-caption">Observaciones</span>
                          </v-expansion-panel-title>
                          <v-expansion-panel-text>
                            <div class="text-body-2">{{ item.observaciones }}</div>
                          </v-expansion-panel-text>
                        </v-expansion-panel>
                      </v-expansion-panels>
                      
                      <div v-if="item.diagnosticos && item.diagnosticos.length > 0" class="mt-2">
                        <div class="text-caption mb-1">
                          <v-icon size="x-small" class="mr-1">mdi-clipboard-list</v-icon>
                          <strong>Diagnósticos adicionales:</strong>
                        </div>
                        <div class="d-flex flex-wrap gap-1">
                          <v-chip
                            v-for="diag in item.diagnosticos"
                            :key="diag.id"
                            size="x-small"
                            color="info"
                            variant="outlined"
                          >
                            {{ diag.descripcion }}
                          </v-chip>
                        </div>
                      </div>
                      
                      <div v-if="item.tratamientos && item.tratamientos.length > 0" class="mt-2">
                        <div class="text-caption mb-1">
                          <v-icon size="x-small" class="mr-1">mdi-medical-bag</v-icon>
                          <strong>Tratamientos prescritos:</strong>
                        </div>
                        <div class="d-flex flex-wrap gap-1">
                          <v-chip
                            v-for="trat in item.tratamientos"
                            :key="trat.id"
                            size="x-small"
                            color="success"
                            variant="outlined"
                          >
                            {{ trat.nombre }}
                          </v-chip>
                        </div>
                      </div>
                      
                      <v-divider class="my-2"></v-divider>
                      <div class="text-caption text-grey d-flex align-center">
                        <v-icon size="x-small" class="mr-1">mdi-account</v-icon>
                        <span>Atendido por: {{ item.veterinarioNombre || 'Dr. Desconocido' }}</span>
                      </div>
                    </v-card-text>
                  </v-card>
                </v-timeline-item>
              </v-timeline>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>

      <!-- Diálogo para editar consulta -->
      <v-dialog
        v-model="dialogEditar"
        max-width="900px"
        scrollable
        persistent
      >
        <v-card>
          <v-card-title class="bg-primary text-white d-flex align-center">
            <v-icon class="mr-2">mdi-file-document-edit</v-icon>
            <span>Editar Consulta Médica</span>
            <v-spacer></v-spacer>
            <v-btn
              icon="mdi-close"
              variant="text"
              @click="cancelarEdicion"
              color="white"
            ></v-btn>
          </v-card-title>
          <v-card-text class="pa-6">
            <v-form ref="formEditarRef" @submit.prevent="guardarConsultaEditar">
              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="consultaEditar.fecha"
                    label="Fecha de Consulta *"
                    type="date"
                    :rules="[rules.required]"
                    variant="outlined"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="consultaEditar.peso"
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
                    v-model="consultaEditar.motivoConsulta"
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
                    v-model="consultaEditar.sintomas"
                    label="Síntomas Observados"
                    rows="3"
                    variant="outlined"
                  ></v-textarea>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="consultaEditar.temperatura"
                    label="Temperatura (°C)"
                    type="number"
                    step="0.1"
                    variant="outlined"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="consultaEditar.frecuenciaCardiaca"
                    label="Frecuencia Cardíaca (lpm)"
                    type="number"
                    variant="outlined"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12">
                  <v-textarea
                    v-model="consultaEditar.diagnostico"
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
                    v-model="consultaEditar.tratamiento"
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
                    v-model="consultaEditar.observaciones"
                    label="Observaciones Adicionales"
                    rows="3"
                    variant="outlined"
                  ></v-textarea>
                </v-col>
              </v-row>
            </v-form>
          </v-card-text>
          <v-card-actions class="pa-4">
            <v-spacer></v-spacer>
            <v-btn
              color="secondary"
              @click="cancelarEdicion"
              variant="outlined"
            >
              Cancelar
            </v-btn>
            <v-btn
              color="primary"
              @click="guardarConsultaEditar"
              :loading="guardando"
            >
              Actualizar Consulta
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </template>
  </v-container>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useNotification } from '@/composables/useNotification'
import { useAuthStore } from '@/stores/authStore'
import { useCitasStore } from '@/stores/citasStore'
import { useApi } from '@/composables/useApi'
import { useHistorialesStore } from '@/stores/historialesStore'
import { usePacientesStore } from '@/stores/pacientesStore'

const route = useRoute()
const router = useRouter()
const { showSuccess, showError } = useNotification()
const authStore = useAuthStore()
const citasStore = useCitasStore()
const historialesStore = useHistorialesStore()
const pacientesStore = usePacientesStore()

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
  try {
    // Si ya es una fecha ISO completa, usarla directamente
    const date = dateStr.includes('T') ? new Date(dateStr) : new Date(dateStr + 'T00:00:00')
    return date.toLocaleDateString('es-ES', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    })
  } catch (error) {
    console.error('Error al formatear fecha:', dateStr, error)
    return dateStr
  }
}

const truncateText = (text, maxLength) => {
  if (!text) return ''
  if (text.length <= maxLength) return text
  return text.substring(0, maxLength) + '...'
}

// Función para editar una consulta existente
const consultaEditando = ref(null)

const editarConsulta = (consultaItem) => {
  consultaEditando.value = consultaItem
  dialogEditar.value = true
  
  // Pre-llenar el formulario con los datos de la consulta
  consultaEditar.fecha = consultaItem.fechaConsulta || consultaItem.fecha || new Date().toISOString().split('T')[0]
  consultaEditar.peso = consultaItem.signosVitales?.pesoKg || null
  consultaEditar.motivoConsulta = consultaItem.motivo || consultaItem.motivoConsulta || ''
  consultaEditar.sintomas = consultaItem.anamnesis || consultaItem.sintomas || ''
  consultaEditar.temperatura = consultaItem.signosVitales?.temperatura || null
  consultaEditar.frecuenciaCardiaca = consultaItem.signosVitales?.frecuenciaCardiaca || null
  consultaEditar.diagnostico = consultaItem.examenFisico || consultaItem.diagnostico || ''
  consultaEditar.tratamiento = consultaItem.planTratamiento || consultaItem.tratamiento || ''
  consultaEditar.observaciones = consultaItem.observaciones || ''
}

const cancelarEdicion = () => {
  consultaEditando.value = null
  dialogEditar.value = false
  limpiarFormularioEditar()
}

// Formulario separado para edición en el diálogo
const dialogEditar = ref(false)
const formEditarRef = ref(null)
const consultaEditar = reactive({
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

const limpiarFormularioEditar = () => {
  consultaEditar.fecha = new Date().toISOString().split('T')[0]
  consultaEditar.peso = null
  consultaEditar.motivoConsulta = ''
  consultaEditar.sintomas = ''
  consultaEditar.temperatura = null
  consultaEditar.frecuenciaCardiaca = null
  consultaEditar.diagnostico = ''
  consultaEditar.tratamiento = ''
  consultaEditar.observaciones = ''
  formEditarRef.value?.resetValidation()
}

const cargarPaciente = async () => {
  try {
    paciente.value = await pacientesStore.fetchPacienteById(route.params.id)
  } catch (error) {
    console.error('Error al cargar paciente:', error)
    showError('Error al cargar la información del paciente')
  }
}

const cargarConsultas = async () => {
  try {
    // Intentar cargar el historial del paciente
    try {
      await historialesStore.fetchHistorialByPaciente(route.params.id)
      
      // Si existe historial, cargar las consultas
      if (historialesStore.currentHistorial?.id) {
        await historialesStore.fetchConsultasByHistorial(historialesStore.currentHistorial.id)
        consultas.value = historialesStore.consultas.sort((a, b) => {
          const fechaA = a.fechaConsulta || a.fecha || ''
          const fechaB = b.fechaConsulta || b.fecha || ''
          return new Date(fechaB) - new Date(fechaA)
        })
      } else {
        consultas.value = []
      }
    } catch (error) {
      // Si no existe historial (404), crearlo automáticamente
      if (error.response?.status === 404) {
        try {
          const nuevoHistorial = await historialesStore.createHistorial(route.params.id)
          if (nuevoHistorial?.id) {
            // Inicializar consultas vacías para el nuevo historial
            consultas.value = []
            // No mostrar mensaje de éxito para no interrumpir el flujo
          }
        } catch (createError) {
          console.error('Error creating historial:', createError)
          const mensaje = createError.response?.data?.userMessage || 
                         createError.response?.data?.message || 
                         'Error al crear el historial clínico'
          showError(mensaje)
          consultas.value = []
        }
      } else {
        // Otro tipo de error
        console.error('Error loading historial:', error)
        consultas.value = []
      }
    }
  } catch (error) {
    console.error('Error al cargar consultas:', error)
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
    
    if (!token) {
      showError('No hay sesión activa. Por favor, inicie sesión nuevamente.')
      guardando.value = false
      return
    }
    
    // Obtener el usuario del store o del endpoint
    let user = authStore.user
    
    // Si no hay usuario en el store, intentar obtenerlo del endpoint
    if (!user || !user.id) {
      console.log('Usuario no encontrado en store, obteniendo del endpoint...')
      user = await authStore.getCurrentUser()
      
      // Si el usuario no tiene ID, buscarlo por username para obtener el ID completo
      if (user && !user.id && user.username) {
        console.log('Buscando usuario completo por username:', user.username)
        try {
          const { get } = useApi()
          // Buscar en la lista de usuarios
          const usuariosResponse = await get('/usuarios', {
            params: { page: 0, size: 100 }
          })
          const usuarios = usuariosResponse.data?.content || usuariosResponse.data || []
          const usuarioCompleto = usuarios.find(u => u.username === user.username || u.email === user.email)
          if (usuarioCompleto) {
            user = { ...user, id: usuarioCompleto.id }
            console.log('Usuario completo encontrado:', user)
          }
        } catch (error) {
          console.error('Error al buscar usuario completo:', error)
        }
      }
    }
    
    // Si aún no hay usuario o no tiene ID, mostrar error
    if (!user || !user.id) {
      console.error('No se pudo obtener el usuario o su ID:', user)
      showError('No se pudo obtener la información del veterinario. Por favor, inicie sesión nuevamente.')
      guardando.value = false
      return
    }
    
    console.log('Usuario obtenido con ID:', user)

    // Obtener o crear historial clínico
    let historialId = null
    try {
      await historialesStore.fetchHistorialByPaciente(route.params.id)
      historialId = historialesStore.currentHistorial?.id
    } catch (error) {
      // Si no existe (404), crearlo automáticamente
      if (error.response?.status === 404) {
        try {
          const nuevoHistorial = await historialesStore.createHistorial(route.params.id)
          historialId = nuevoHistorial?.id
        } catch (createError) {
          console.error('Error al crear historial:', createError)
          showError('Error al crear el historial clínico')
          guardando.value = false
          return
        }
      } else {
        console.error('Error al obtener historial:', error)
        showError('Error al obtener el historial clínico')
        guardando.value = false
        return
      }
    }

    // Preparar signos vitales
    const signosVitales = {}
    if (consulta.peso) signosVitales.pesoKg = parseFloat(consulta.peso)
    if (consulta.temperatura) signosVitales.temperatura = parseFloat(consulta.temperatura)
    if (consulta.frecuenciaCardiaca) signosVitales.frecuenciaCardiaca = parseInt(consulta.frecuenciaCardiaca)

    // Obtener post de useApi
    const { post } = useApi()

    // Crear nueva consulta (siempre POST desde el formulario principal)
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
    
    // Crear consulta con signos vitales
    await post('/v1/consultas', consultaData)
    
    // Recargar consultas
    await historialesStore.fetchConsultasByHistorial(historialId)
    consultas.value = historialesStore.consultas.sort((a, b) => {
      const fechaA = a.fechaConsulta || a.fecha || ''
      const fechaB = b.fechaConsulta || b.fecha || ''
      return new Date(fechaB) - new Date(fechaA)
    })

    showSuccess('Consulta guardada exitosamente')
    
    // Si vino desde una cita en progreso, completar la cita automáticamente
    if (fromCita.value && enProgreso.value && citaData.id) {
      try {
        await citasStore.completarCita(parseInt(citaData.id))
        showSuccess('Consulta guardada y cita completada exitosamente')
      } catch (error) {
        console.error('Error al completar la cita:', error)
        showError('Consulta guardada, pero no se pudo completar la cita automáticamente')
      }
    }
    
    limpiarFormulario()

    // Si vino desde una cita, redirigir a la lista de citas
    if (fromCita.value) {
      router.push('/citas')
      return
    }
    
    await cargarConsultas()
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

// Función para guardar desde el diálogo de edición
const guardarConsultaEditar = async () => {
  const { valid } = await formEditarRef.value.validate()

  if (!valid) {
    showError('Por favor, complete todos los campos requeridos')
    return
  }

  if (!consultaEditando.value) {
    showError('No se encontró la consulta a editar')
    return
  }

  guardando.value = true

  try {
    const token = localStorage.getItem('token')
    
    if (!token) {
      showError('No hay sesión activa. Por favor, inicie sesión nuevamente.')
      guardando.value = false
      return
    }

    // Preparar signos vitales
    const signosVitales = {}
    if (consultaEditar.peso) signosVitales.pesoKg = parseFloat(consultaEditar.peso)
    if (consultaEditar.temperatura) signosVitales.temperatura = parseFloat(consultaEditar.temperatura)
    if (consultaEditar.frecuenciaCardiaca) signosVitales.frecuenciaCardiaca = parseInt(consultaEditar.frecuenciaCardiaca)

    // Obtener put de useApi
    const { put } = useApi()

    // Actualizar consulta existente
    const consultaData = {
      motivo: consultaEditar.motivoConsulta,
      anamnesis: consultaEditar.sintomas || null,
      examenFisico: consultaEditar.diagnostico || null,
      planTratamiento: consultaEditar.tratamiento || null,
      observaciones: consultaEditar.observaciones || null,
      signosVitales: Object.keys(signosVitales).length > 0 ? signosVitales : null
    }

    await put(`/v1/consultas/${consultaEditando.value.id}`, consultaData)

    showSuccess('Consulta actualizada exitosamente')
    cancelarEdicion()
    await cargarConsultas()
  } catch (error) {
    console.error('Error al actualizar consulta:', error)
    const mensaje = error.response?.data?.userMessage ||
                    error.response?.data?.message ||
                    'Error al actualizar la consulta'
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
