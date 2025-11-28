<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12" md="8" offset-md="2">
        <v-card>
          <v-card-title class="bg-primary text-white py-4">
            {{ isEditing ? 'Editar Cita' : 'Nueva Cita' }}
          </v-card-title>

          <v-card-text class="pa-6">
            <v-form @submit.prevent="saveCita" ref="formRef">
              <v-row>
                <v-col cols="12" md="6">
                  <v-select
                    v-model="form.pacienteId"
                    label="Paciente *"
                    :items="pacientes"
                    item-title="nombre"
                    item-value="id"
                    :rules="[rules.required]"
                  ></v-select>
                </v-col>

                <v-col cols="12" md="6">
                  <v-select
                    v-model="form.clienteId"
                    label="Cliente *"
                    :items="clientes"
                    item-title="nombre"
                    item-value="id"
                    :rules="[rules.required]"
                  ></v-select>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="form.fecha"
                    label="Fecha *"
                    type="date"
                    :rules="[rules.required]"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="form.hora"
                    label="Hora *"
                    type="time"
                    :rules="[rules.required]"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" md="6">
                  <v-select
                    v-model="form.veterinarioId"
                    label="Veterinario *"
                    :items="veterinarios"
                    item-title="nombre"
                    item-value="id"
                    :rules="[rules.required]"
                  ></v-select>
                </v-col>

                <v-col cols="12" md="6">
                  <v-select
                    v-model="form.tipoServicioId"
                    label="Tipo de Servicio *"
                    :items="tiposServicio"
                    item-title="nombre"
                    item-value="id"
                    :rules="[rules.required]"
                  ></v-select>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12">
                  <v-textarea
                    v-model="form.motivo"
                    label="Motivo de la Cita"
                    counter
                    maxlength="500"
                  ></v-textarea>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12">
                  <v-textarea
                    v-model="form.notas"
                    label="Notas"
                    counter
                    maxlength="500"
                  ></v-textarea>
                </v-col>
              </v-row>

              <v-row class="mt-6">
                <v-col cols="12" md="6">
                  <v-btn
                    color="primary"
                    block
                    type="submit"
                    :loading="loading"
                  >
                    {{ isEditing ? 'Actualizar' : 'Crear' }} Cita
                  </v-btn>
                </v-col>

                <v-col cols="12" md="6">
                  <v-btn
                    color="secondary"
                    block
                    @click="$router.back()"
                  >
                    Cancelar
                  </v-btn>
                </v-col>
              </v-row>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useCitasStore } from '@/stores/citasStore'
import { useReferenceData } from '@/composables/useReferenceData'
import { useNotification } from '@/composables/useNotification'

const router = useRouter()
const route = useRoute()
const citasStore = useCitasStore()
const { fetchPacientes, fetchClientes, fetchUsuarios, fetchTiposServicio } = useReferenceData()
const { showSuccess, showError } = useNotification()

const loading = computed(() => citasStore.loading)
const isEditing = computed(() => !!route.params.id)

const formRef = ref(null)  // Template ref para el formulario

const form = reactive({
  pacienteId: null,
  clienteId: null,
  veterinarioId: null,
  tipoServicioId: null,
  fecha: null,
  hora: null,
  motivo: '',
  notas: '',
})

const pacientes = ref([])
const clientes = ref([])
const veterinarios = ref([])
const tiposServicio = ref([])

const rules = {
  required: (v) => !!v || 'Este campo es requerido',
}

const loadData = async () => {
  try {
    const [pacientesData, clientesData, veterinariosData, tiposServicioData] = await Promise.all([
      fetchPacientes(),
      fetchClientes(),
      fetchUsuarios('VETERINARIO'),
      fetchTiposServicio(),
    ])

    pacientes.value = pacientesData
    clientes.value = clientesData
    veterinarios.value = veterinariosData
    tiposServicio.value = tiposServicioData

    // Si estamos editando, cargar los datos de la cita
    if (isEditing.value) {
      await citasStore.fetchCitaById(route.params.id)
      const cita = citasStore.currentCita
      if (cita) {
        // Mapear IDs de objetos anidados
        form.pacienteId = cita.pacienteObj?.id || cita.pacienteId
        form.clienteId = cita.clienteObj?.id || cita.clienteId
        form.veterinarioId = cita.veterinarioObj?.id || cita.veterinarioId
        form.tipoServicioId = cita.tipoServicioObj?.id || cita.tipoServicioId
        form.fecha = cita.fecha
        form.hora = cita.hora
        form.motivo = cita.motivo
        form.notas = cita.notas
      }
    }
  } catch (error) {
    console.error('Error al cargar datos:', error)
    showError(error.userMessage || 'Error al cargar los datos del formulario')
  }
}

const saveCita = async () => {
  try {
    if (isEditing.value) {
      await citasStore.updateCita(route.params.id, form)
      showSuccess('Cita actualizada exitosamente')
    } else {
      await citasStore.createCita(form)
      showSuccess('Cita creada exitosamente')
    }
    router.push('/citas')
  } catch (error) {
    console.error('Error al guardar cita:', error)
    showError(error.userMessage || 'Error al guardar la cita')
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
</style>
