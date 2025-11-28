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
          prepend-icon="search"
          @update:model-value="loadHistorial"
        ></v-autocomplete>
      </v-col>
    </v-row>

    <v-row v-if="selectedPacienteId && currentHistorial">
      <v-col cols="12">
        <v-card>
          <v-card-title class="bg-primary text-white">
            Historial Clínico - {{ currentHistorial.paciente?.nombre }}
          </v-card-title>
          <v-card-text class="pa-4">
            <v-btn
              color="primary"
              @click="showNuevaConsulta = true"
              class="mb-4"
            >
              Nueva Consulta
            </v-btn>

            <v-timeline side="end">
              <v-timeline-item
                v-for="consulta in consultas"
                :key="consulta.id"
                dot-color="primary"
                size="small"
              >
                <template v-slot:opposite>
                  <div class="text-caption">{{ formatDate(consulta.fecha) }}</div>
                </template>
                <v-card>
                  <v-card-title>{{ consulta.motivo }}</v-card-title>
                  <v-card-subtitle>Dr. {{ consulta.veterinario?.nombre }}</v-card-subtitle>
                  <v-card-text>
                    <p><strong>Diagnóstico:</strong> {{ consulta.diagnostico }}</p>
                    <p><strong>Tratamiento:</strong> {{ consulta.tratamiento }}</p>
                    <p v-if="consulta.observaciones"><strong>Observaciones:</strong> {{ consulta.observaciones }}</p>
                  </v-card-text>
                </v-card>
              </v-timeline-item>
            </v-timeline>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

    <!-- Dialog Nueva Consulta -->
    <v-dialog v-model="showNuevaConsulta" max-width="800px">
      <v-card>
        <v-card-title class="bg-primary text-white">Nueva Consulta</v-card-title>
        <v-card-text class="pa-6">
          <v-form @submit.prevent="saveConsulta">
            <v-autocomplete
              v-model="consultaForm.veterinarioId"
              :items="veterinarios"
              item-title="nombre"
              item-value="id"
              label="Veterinario *"
              :rules="[v => !!v || 'Requerido']"
              prepend-icon="person"
            ></v-autocomplete>

            <v-text-field
              v-model="consultaForm.motivo"
              label="Motivo de Consulta *"
              :rules="[v => !!v || 'Requerido']"
            ></v-text-field>

            <v-textarea
              v-model="consultaForm.diagnostico"
              label="Diagnóstico"
              rows="3"
            ></v-textarea>

            <v-textarea
              v-model="consultaForm.tratamiento"
              label="Tratamiento"
              rows="3"
            ></v-textarea>

            <v-textarea
              v-model="consultaForm.observaciones"
              label="Observaciones"
              rows="2"
            ></v-textarea>

            <v-row class="mt-4">
              <v-col cols="6">
                <v-btn color="primary" block type="submit" :loading="loading">Guardar</v-btn>
              </v-col>
              <v-col cols="6">
                <v-btn color="secondary" block @click="showNuevaConsulta = false">Cancelar</v-btn>
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
import { useReferenceData } from '@/composables/useReferenceData'

const historialesStore = useHistorialesStore()
const { fetchPacientes, fetchUsuarios } = useReferenceData()

const selectedPacienteId = ref(null)
const pacientes = ref([])
const veterinarios = ref([])
const showNuevaConsulta = ref(false)

const loading = computed(() => historialesStore.loading)
const currentHistorial = computed(() => historialesStore.currentHistorial)
const consultas = computed(() => historialesStore.consultas)

const consultaForm = reactive({
  motivo: '',
  diagnostico: '',
  tratamiento: '',
  observaciones: '',
  veterinarioId: null,
})

const loadHistorial = async () => {
  if (!selectedPacienteId.value) return
  try {
    await historialesStore.fetchHistorialByPaciente(selectedPacienteId.value)
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
    showNuevaConsulta.value = false
    Object.assign(consultaForm, {
      motivo: '',
      diagnostico: '',
      tratamiento: '',
      observaciones: '',
      veterinarioId: null,
    })
  } catch (error) {
    console.error('Error saving consulta:', error)
  }
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

onMounted(async () => {
  pacientes.value = await fetchPacientes()
  veterinarios.value = await fetchUsuarios('VETERINARIO')
})
</script>
