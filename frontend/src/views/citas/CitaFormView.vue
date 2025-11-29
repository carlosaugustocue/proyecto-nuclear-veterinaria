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
                    @update:model-value="checkScheduleConflict"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="form.hora"
                    label="Hora *"
                    type="time"
                    :rules="[rules.required]"
                    @blur="checkScheduleConflict"
                    hint="Seleccione de los horarios disponibles o escriba manualmente"
                  ></v-text-field>
                </v-col>
              </v-row>

              <!-- Alerta de conflicto de horario -->
              <v-row v-if="scheduleConflict">
                <v-col cols="12">
                  <v-alert type="error" variant="tonal" class="mb-4">
                    <v-icon class="mr-2">mdi-alert-circle</v-icon>
                    <strong>Horario No Disponible:</strong>
                    {{ horariosDisponibles.length === 0
                      ? 'No hay horarios disponibles para esta fecha. Puede ser un día no hábil (domingo) o el veterinario no tiene disponibilidad.'
                      : 'El horario seleccionado no está disponible. Por favor, seleccione uno de los horarios sugeridos.'
                    }}
                  </v-alert>
                </v-col>
              </v-row>

              <!-- Horarios disponibles -->
              <v-row v-if="showAvailableTimes && horariosDisponibles.length > 0">
                <v-col cols="12">
                  <v-card variant="outlined" class="mb-4">
                    <v-card-title class="text-subtitle-1">
                      <v-icon class="mr-2" color="info">mdi-clock-outline</v-icon>
                      Horarios Disponibles para {{ form.fecha }}
                    </v-card-title>
                    <v-card-text>
                      <v-chip-group>
                        <v-chip
                          v-for="horario in horariosDisponibles"
                          :key="horario"
                          @click="seleccionarHorario(horario)"
                          :color="form.hora === horario ? 'primary' : 'default'"
                          class="ma-1"
                        >
                          {{ horario }}
                        </v-chip>
                      </v-chip-group>
                    </v-card-text>
                  </v-card>
                </v-col>
              </v-row>

              <!-- Alerta cuando no hay horarios disponibles pero no hay conflicto (sin hora seleccionada) -->
              <v-row v-if="form.veterinarioId && form.fecha && horariosDisponibles.length === 0 && !form.hora">
                <v-col cols="12">
                  <v-alert type="warning" variant="tonal" class="mb-4">
                    <v-icon class="mr-2">mdi-information</v-icon>
                    <strong>Sin Disponibilidad:</strong> No hay horarios disponibles para la fecha seleccionada.
                    Puede ser un día no hábil (domingo) o el veterinario tiene todas las citas ocupadas.
                    Por favor, seleccione otra fecha.
                  </v-alert>
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
                    @update:model-value="checkScheduleConflict"
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

// Estado para detección de conflictos
const scheduleConflict = ref(false)
const showAvailableTimes = ref(false)
const horariosDisponibles = ref([])

const rules = {
  required: (v) => !!v || 'Este campo es requerido',
}

// Función auxiliar para normalizar formato de hora
const normalizeTimeFormat = (timeStr) => {
  if (!timeStr) return ''
  // Si ya está en formato HH:mm, devolverlo
  if (/^\d{2}:\d{2}$/.test(timeStr)) {
    return timeStr
  }
  // Si tiene segundos (HH:mm:ss), eliminarlos
  if (/^\d{2}:\d{2}:\d{2}$/.test(timeStr)) {
    return timeStr.substring(0, 5)
  }
  // Si es solo hora (ej: "8:00"), agregar cero
  if (/^\d{1}:\d{2}$/.test(timeStr)) {
    return '0' + timeStr
  }
  return timeStr
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

const seleccionarHorario = (horario) => {
  form.hora = normalizeTimeFormat(horario)
  scheduleConflict.value = false
}

const checkScheduleConflict = async () => {
  // Reiniciar estados
  scheduleConflict.value = false
  showAvailableTimes.value = false
  horariosDisponibles.value = []

  // Validar que tengamos los datos necesarios
  if (!form.veterinarioId || !form.fecha) {
    return
  }

  try {
    // Obtener horarios disponibles para el veterinario en la fecha seleccionada
    // Asumiendo duración estándar de 30 minutos
    const horarios = await citasStore.obtenerHorariosDisponibles(
      form.veterinarioId,
      form.fecha,
      30
    )

    horariosDisponibles.value = horarios

    // Solo mostrar horarios disponibles si hay alguno
    if (horarios && horarios.length > 0) {
      showAvailableTimes.value = true
    }

    // Si ya seleccionó una hora, verificar si está disponible
    if (form.hora && horarios && horarios.length > 0) {
      // Normalizar formato de hora para comparación (asegurar HH:mm)
      const horaSeleccionada = normalizeTimeFormat(form.hora)
      const horariosNormalizados = horarios.map(h => normalizeTimeFormat(h))

      console.log('Hora seleccionada:', horaSeleccionada)
      console.log('Horarios disponibles:', horariosNormalizados)

      const isAvailable = horariosNormalizados.includes(horaSeleccionada)

      if (!isAvailable) {
        scheduleConflict.value = true
      } else {
        scheduleConflict.value = false
      }
    } else if (form.hora && (!horarios || horarios.length === 0)) {
      // Si no hay horarios disponibles en todo el día, mostrar conflicto
      scheduleConflict.value = true
    }
  } catch (error) {
    console.error('Error al verificar disponibilidad:', error)
    // No mostrar error al usuario, simplemente no validar conflictos
  }
}

const saveCita = async () => {
  // Verificar conflicto de horario antes de guardar
  if (scheduleConflict.value && horariosDisponibles.value.length > 0) {
    // Si hay horarios disponibles pero seleccionó uno que no está disponible
    showError('Por favor, seleccione uno de los horarios disponibles')
    return
  }

  if (scheduleConflict.value && horariosDisponibles.value.length === 0) {
    // Si no hay horarios disponibles en absoluto, preguntar si quiere continuar
    const continuar = confirm(
      'No hay horarios disponibles para esta fecha. ¿Desea crear la cita de todas formas? ' +
      'Nota: El backend validará si hay conflictos reales.'
    )
    if (!continuar) {
      return
    }
  }

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
