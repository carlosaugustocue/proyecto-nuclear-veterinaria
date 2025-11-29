<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12">
        <div class="d-flex justify-space-between align-center mb-6">
          <h1>Gestión de Citas</h1>
          <v-btn
            to="/citas/nueva"
            color="primary"
            prepend-icon="mdi-plus"
            size="large"
          >
            Nueva Cita
          </v-btn>
        </div>
      </v-col>
    </v-row>

    <!-- Tabs para diferentes vistas -->
    <v-row>
      <v-col cols="12">
        <v-tabs v-model="tab" bg-color="primary">
          <v-tab value="lista">
            <v-icon class="mr-2">mdi-format-list-bulleted</v-icon>
            Lista
          </v-tab>
          <v-tab value="calendario">
            <v-icon class="mr-2">mdi-calendar-month</v-icon>
            Calendario
          </v-tab>
          <v-tab value="hoy">
            <v-icon class="mr-2">mdi-calendar-today</v-icon>
            Hoy
          </v-tab>
        </v-tabs>
      </v-col>
    </v-row>

    <!-- Filtros -->
    <v-row class="mb-6">
      <v-col cols="12" sm="6" md="3">
        <v-text-field
          v-model="filters.searchText"
          label="Buscar paciente"
          prepend-icon="mdi-magnify"
          clearable
          @update:model-value="fetchCitas"
        ></v-text-field>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-select
          v-model="filters.estado"
          label="Estado"
          :items="estadoOptions"
          clearable
          @update:model-value="fetchCitas"
        ></v-select>
      </v-col>

      <v-col cols="12" sm="6" md="3">
        <v-text-field
          v-model="filters.fecha"
          label="Fecha"
          type="date"
          @update:model-value="fetchCitas"
        ></v-text-field>
      </v-col>
    </v-row>

    <!-- Contenido de las pestañas -->
    <v-window v-model="tab" class="mt-4">
      <!-- Vista de Lista -->
      <v-window-item value="lista">
        <v-row>
          <v-col cols="12">
            <v-card>
              <v-data-table
                :headers="headers"
                :items="citas"
                :loading="loading"
                :sort-by="[{ key: 'fecha', order: 'asc' }]"
                class="elevation-1"
                :items-per-page="15"
              >
                <!-- Fecha y Hora -->
                <template v-slot:item.fecha="{ item }">
                  <div>
                    <div class="font-weight-medium">{{ formatDate(item.fecha) }}</div>
                    <div class="text-caption text-grey">{{ item.hora }}</div>
                  </div>
                </template>

                <!-- Estado -->
                <template v-slot:item.estado="{ item }">
                  <v-chip :color="getEstadoColor(item.estado)" text-color="white" size="small">
                    <v-icon size="x-small" class="mr-1">{{ getEstadoIcon(item.estado) }}</v-icon>
                    {{ item.estado }}
                  </v-chip>
                </template>

                <!-- Acciones con controles de estado -->
                <template v-slot:item.actions="{ item }">
                  <div class="d-flex gap-1">
                    <!-- Botón Ver -->
                    <v-btn
                      size="small"
                      color="primary"
                      variant="text"
                      icon="mdi-eye"
                      :to="`/citas/${item.id}`"
                    ></v-btn>

                    <!-- Botones de cambio de estado -->
                    <v-menu v-if="item.estado?.toLowerCase() !== 'cancelada' && item.estado?.toLowerCase() !== 'completada'">
                      <template v-slot:activator="{ props }">
                        <v-btn
                          size="small"
                          color="info"
                          variant="text"
                          icon="mdi-state-machine"
                          v-bind="props"
                        ></v-btn>
                      </template>
                      <v-list>
                        <v-list-item
                          v-if="item.estado?.toLowerCase() === 'programada'"
                          @click="confirmarCita(item.id)"
                        >
                          <template v-slot:prepend>
                            <v-icon color="success">mdi-check-circle</v-icon>
                          </template>
                          <v-list-item-title>Confirmar</v-list-item-title>
                        </v-list-item>

                        <v-list-item
                          v-if="item.estado?.toLowerCase() === 'confirmada'"
                          @click="iniciarCita(item.id)"
                        >
                          <template v-slot:prepend>
                            <v-icon color="warning">mdi-play-circle</v-icon>
                          </template>
                          <v-list-item-title>Iniciar Atención</v-list-item-title>
                        </v-list-item>

                        <v-list-item
                          v-if="item.estado?.toLowerCase().includes('atenci')"
                          @click="continuarConsulta(item)"
                        >
                          <template v-slot:prepend>
                            <v-icon color="primary">mdi-clipboard-text</v-icon>
                          </template>
                          <v-list-item-title>Continuar Consulta</v-list-item-title>
                        </v-list-item>

                        <v-list-item
                          v-if="item.estado?.toLowerCase().includes('atenci')"
                          @click="completarCita(item.id)"
                        >
                          <template v-slot:prepend>
                            <v-icon color="success">mdi-check-all</v-icon>
                          </template>
                          <v-list-item-title>Completar sin Consulta</v-list-item-title>
                        </v-list-item>

                        <v-divider></v-divider>

                        <v-list-item @click="mostrarDialogoCancelar(item)">
                          <template v-slot:prepend>
                            <v-icon color="error">mdi-cancel</v-icon>
                          </template>
                          <v-list-item-title>Cancelar</v-list-item-title>
                        </v-list-item>
                      </v-list>
                    </v-menu>

                    <!-- Botón Eliminar -->
                    <v-btn
                      size="small"
                      color="error"
                      variant="text"
                      icon="mdi-delete"
                      @click="deleteCita(item.id)"
                    ></v-btn>
                  </div>
                </template>

                <template v-slot:no-data>
                  <v-alert type="info" variant="tonal" class="my-6">
                    <v-icon class="mr-2">mdi-information</v-icon>
                    No hay citas registradas
                  </v-alert>
                </template>
              </v-data-table>
            </v-card>
          </v-col>
        </v-row>
      </v-window-item>

      <!-- Vista de Calendario -->
      <v-window-item value="calendario">
        <v-card>
          <v-card-title class="d-flex justify-space-between align-center">
            <v-btn icon="mdi-chevron-left" @click="previousMonth" variant="text"></v-btn>
            <span class="text-h5">{{ currentMonthYear }}</span>
            <v-btn icon="mdi-chevron-right" @click="nextMonth" variant="text"></v-btn>
          </v-card-title>
          <v-card-text>
            <!-- Días de la semana -->
            <v-row class="text-center font-weight-bold mb-2">
              <v-col v-for="dia in diasSemana" :key="dia" class="pa-2">
                {{ dia }}
              </v-col>
            </v-row>

            <!-- Calendario -->
            <v-row v-for="(semana, index) in calendarioDias" :key="index" class="mb-1">
              <v-col v-for="dia in semana" :key="dia.fecha" class="pa-1">
                <v-card
                  :class="{
                    'bg-grey-lighten-3': !dia.esDelMes,
                    'bg-primary-lighten-5': dia.esHoy,
                    'elevation-2': dia.esDelMes
                  }"
                  :variant="dia.esDelMes ? 'outlined' : 'flat'"
                  min-height="100"
                  @click="dia.esDelMes && verCitasDia(dia.fecha)"
                  style="cursor: pointer;"
                >
                  <v-card-text class="pa-2">
                    <div class="text-caption font-weight-bold mb-1" :class="{ 'text-primary': dia.esHoy }">
                      {{ dia.numero }}
                    </div>
                    <div v-if="dia.citas.length > 0" class="text-caption">
                      <v-chip
                        v-for="cita in dia.citas.slice(0, 2)"
                        :key="cita.id"
                        size="x-small"
                        :color="getEstadoColor(cita.estado)"
                        class="mb-1 d-block text-truncate"
                        style="max-width: 100%;"
                      >
                        {{ cita.hora }} - {{ cita.pacienteNombre }}
                      </v-chip>
                      <div v-if="dia.citas.length > 2" class="text-caption text-grey">
                        +{{ dia.citas.length - 2 }} más
                      </div>
                    </div>
                  </v-card-text>
                </v-card>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-window-item>

      <!-- Vista de Hoy -->
      <v-window-item value="hoy">
        <v-card>
          <v-card-title>
            <v-icon class="mr-2">mdi-calendar-today</v-icon>
            Citas de Hoy - {{ new Date().toLocaleDateString('es-ES', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' }) }}
          </v-card-title>
          <v-card-text>
            <v-timeline side="end" align="start" v-if="citasDeHoy.length > 0">
              <v-timeline-item
                v-for="cita in citasDeHoy"
                :key="cita.id"
                :dot-color="getEstadoColor(cita.estado)"
                size="small"
              >
                <template v-slot:opposite>
                  <div class="text-h6 font-weight-bold">{{ cita.hora }}</div>
                </template>
                <v-card :color="getEstadoColor(cita.estado)" variant="outlined">
                  <v-card-title class="d-flex justify-space-between align-center">
                    <span>{{ cita.pacienteNombre }}</span>
                    <v-chip :color="getEstadoColor(cita.estado)" size="small" text-color="white">
                      {{ cita.estado }}
                    </v-chip>
                  </v-card-title>
                  <v-card-text>
                    <div><strong>Cliente:</strong> {{ cita.clienteNombre }}</div>
                    <div><strong>Veterinario:</strong> {{ cita.veterinarioNombre }}</div>
                    <div v-if="cita.motivo"><strong>Motivo:</strong> {{ cita.motivo }}</div>
                  </v-card-text>
                  <v-card-actions>
                    <v-btn
                      size="small"
                      color="primary"
                      :to="`/citas/${cita.id}`"
                      prepend-icon="mdi-eye"
                    >
                      Ver Detalles
                    </v-btn>
                    <v-spacer></v-spacer>
                    <v-btn
                      v-if="cita.estado?.toLowerCase() === 'programada'"
                      size="small"
                      color="success"
                      @click="confirmarCita(cita.id)"
                      prepend-icon="mdi-check"
                    >
                      Confirmar
                    </v-btn>
                    <v-btn
                      v-if="cita.estado?.toLowerCase() === 'confirmada'"
                      size="small"
                      color="warning"
                      @click="iniciarCita(cita.id)"
                      prepend-icon="mdi-play"
                    >
                      Iniciar
                    </v-btn>
                    <v-btn
                      v-if="cita.estado?.toLowerCase().includes('atenci')"
                      size="small"
                      color="primary"
                      @click="continuarConsulta(cita)"
                      prepend-icon="mdi-clipboard-text"
                    >
                      Continuar Consulta
                    </v-btn>
                    <v-btn
                      v-if="cita.estado?.toLowerCase().includes('atenci')"
                      size="small"
                      color="success"
                      @click="completarCita(cita.id)"
                      prepend-icon="mdi-check-all"
                    >
                      Completar
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-timeline-item>
            </v-timeline>
            <v-alert v-else type="info" variant="tonal">
              <v-icon class="mr-2">mdi-information</v-icon>
              No hay citas programadas para hoy
            </v-alert>
          </v-card-text>
        </v-card>
      </v-window-item>
    </v-window>

    <!-- Dialog para cancelar cita -->
    <v-dialog v-model="dialogoCancelar" max-width="500px">
      <v-card>
        <v-card-title class="bg-error text-white">
          <v-icon class="mr-2">mdi-cancel</v-icon>
          Cancelar Cita
        </v-card-title>
        <v-card-text class="pa-6">
          <v-textarea
            v-model="motivoCancelacion"
            label="Motivo de Cancelación *"
            rows="3"
            variant="outlined"
            :rules="[v => !!v || 'El motivo es obligatorio']"
          ></v-textarea>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="grey" @click="dialogoCancelar = false">Cerrar</v-btn>
          <v-btn color="error" @click="cancelarCita" :loading="loading">Cancelar Cita</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useCitasStore } from '@/stores/citasStore'
import { useNotification } from '@/composables/useNotification'

const router = useRouter()
const citasStore = useCitasStore()
const { showSuccess, showError } = useNotification()

// Estado de la vista
const tab = ref('lista')
const dialogoCancelar = ref(false)
const citaSeleccionada = ref(null)
const motivoCancelacion = ref('')

const filters = reactive({
  searchText: '',
  estado: null,
  fecha: null,
})

// Estado del calendario
const currentMonth = ref(new Date().getMonth())
const currentYear = ref(new Date().getFullYear())
const diasSemana = ['Dom', 'Lun', 'Mar', 'Mié', 'Jue', 'Vie', 'Sáb']

const estadoOptions = [
  'Programada',
  'Confirmada',
  'En Atención',
  'Completada',
  'Cancelada',
  'No Asistió',
]

const headers = [
  { title: 'Paciente', value: 'pacienteNombre' },
  { title: 'Cliente', value: 'clienteNombre' },
  { title: 'Fecha', value: 'fecha', width: '140' },
  { title: 'Veterinario', value: 'veterinarioNombre' },
  { title: 'Motivo', value: 'motivo' },
  { title: 'Estado', value: 'estado', width: '140' },
  { title: 'Acciones', value: 'actions', sortable: false, width: '150' },
]

const loading = computed(() => citasStore.loading)
const citas = computed(() => citasStore.citas)

const fetchCitas = async () => {
  try {
    await citasStore.fetchCitas(filters)
  } catch (error) {
    console.error('Error al cargar citas:', error)
    showError(error.userMessage || 'Error al cargar las citas')
  }
}

const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleDateString('es-ES', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
  })
}

const getEstadoColor = (estado) => {
  const estadoLower = estado?.toLowerCase() || ''
  if (estadoLower.includes('atenci')) return 'warning'
  const colores = {
    'programada': 'info',
    'confirmada': 'success',
    'completada': 'success',
    'cancelada': 'error',
    'no asistió': 'grey',
    'no asistio': 'grey',
  }
  return colores[estadoLower] || 'grey'
}

const getEstadoIcon = (estado) => {
  const estadoLower = estado?.toLowerCase() || ''
  if (estadoLower.includes('atenci')) return 'mdi-progress-clock'
  const iconos = {
    'programada': 'mdi-calendar-clock',
    'confirmada': 'mdi-check-circle',
    'completada': 'mdi-check-all',
    'cancelada': 'mdi-cancel',
    'no asistió': 'mdi-account-off',
    'no asistio': 'mdi-account-off',
  }
  return iconos[estadoLower] || 'mdi-circle'
}

// Calendario - Computed properties
const currentMonthYear = computed(() => {
  const meses = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
                 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre']
  return `${meses[currentMonth.value]} ${currentYear.value}`
})

const calendarioDias = computed(() => {
  const primerDia = new Date(currentYear.value, currentMonth.value, 1)
  const ultimoDia = new Date(currentYear.value, currentMonth.value + 1, 0)
  const diasDelMes = ultimoDia.getDate()
  const primerDiaSemana = primerDia.getDay()

  const semanas = []
  let semanaActual = []

  // Días del mes anterior
  for (let i = 0; i < primerDiaSemana; i++) {
    const fecha = new Date(currentYear.value, currentMonth.value, -(primerDiaSemana - i - 1))
    semanaActual.push({
      numero: fecha.getDate(),
      fecha: fecha.toISOString().split('T')[0],
      esDelMes: false,
      esHoy: false,
      citas: []
    })
  }

  // Días del mes actual
  for (let dia = 1; dia <= diasDelMes; dia++) {
    const fecha = new Date(currentYear.value, currentMonth.value, dia)
    const fechaStr = fecha.toISOString().split('T')[0]
    const hoy = new Date()
    const esHoy = fecha.toDateString() === hoy.toDateString()

    const citasDelDia = citas.value.filter(cita => cita.fecha === fechaStr)

    semanaActual.push({
      numero: dia,
      fecha: fechaStr,
      esDelMes: true,
      esHoy,
      citas: citasDelDia
    })

    if (semanaActual.length === 7) {
      semanas.push(semanaActual)
      semanaActual = []
    }
  }

  // Días del mes siguiente para completar la última semana
  if (semanaActual.length > 0) {
    let dia = 1
    while (semanaActual.length < 7) {
      const fecha = new Date(currentYear.value, currentMonth.value + 1, dia)
      semanaActual.push({
        numero: dia,
        fecha: fecha.toISOString().split('T')[0],
        esDelMes: false,
        esHoy: false,
        citas: []
      })
      dia++
    }
    semanas.push(semanaActual)
  }

  return semanas
})

const citasDeHoy = computed(() => {
  const hoy = new Date().toISOString().split('T')[0]
  return citas.value
    .filter(cita => cita.fecha === hoy)
    .sort((a, b) => a.hora.localeCompare(b.hora))
})

// Calendario - Métodos
const previousMonth = () => {
  if (currentMonth.value === 0) {
    currentMonth.value = 11
    currentYear.value--
  } else {
    currentMonth.value--
  }
}

const nextMonth = () => {
  if (currentMonth.value === 11) {
    currentMonth.value = 0
    currentYear.value++
  } else {
    currentMonth.value++
  }
}

const verCitasDia = (fecha) => {
  filters.fecha = fecha
  tab.value = 'lista'
  fetchCitas()
}

// Funciones de cambio de estado
const confirmarCita = async (id) => {
  try {
    await citasStore.confirmarCita(id)
    showSuccess('Cita confirmada exitosamente')
    await fetchCitas()
  } catch (error) {
    console.error('Error al confirmar cita:', error)
    showError(error.userMessage || 'Error al confirmar la cita')
  }
}

const iniciarCita = async (id) => {
  try {
    const citaIniciada = await citasStore.iniciarCita(id)
    showSuccess('Atención iniciada exitosamente')

    // Redirigir al formulario de historial clínico para registrar la consulta
    if (citaIniciada) {
      const pacienteId = citaIniciada.pacienteObj?.id || citaIniciada.pacienteId
      if (pacienteId) {
        router.push({
          path: `/pacientes/${pacienteId}/historial`,
          query: {
            fromCita: id,
            citaFecha: citaIniciada.fecha,
            citaHora: citaIniciada.hora,
            citaMotivo: citaIniciada.motivo
          }
        })
      }
    }
  } catch (error) {
    console.error('Error al iniciar cita:', error)
    showError(error.userMessage || 'Error al iniciar la atención')
  }
}

const continuarConsulta = (cita) => {
  // Redirigir al historial clínico del paciente para continuar con la consulta
  const pacienteId = cita.pacienteObj?.id || cita.pacienteId
  if (pacienteId) {
    router.push({
      path: `/pacientes/${pacienteId}/historial`,
      query: {
        fromCita: cita.id,
        citaFecha: cita.fecha,
        citaHora: cita.hora,
        citaMotivo: cita.motivo,
        enProgreso: 'true'
      }
    })
  } else {
    showError('No se pudo obtener el ID del paciente')
  }
}

const completarCita = async (id) => {
  try {
    const citaCompletada = await citasStore.completarCita(id)
    showSuccess('Cita completada exitosamente')
    await fetchCitas()

    // Preguntar si desea registrar consulta médica
    const registrarConsulta = confirm(
      '¿Desea registrar los hallazgos de esta cita en el historial clínico del paciente?'
    )

    if (registrarConsulta && citaCompletada) {
      const pacienteId = citaCompletada.pacienteObj?.id || citaCompletada.pacienteId
      if (pacienteId) {
        // Redirigir con datos de la cita para pre-llenar consulta
        router.push({
          path: `/pacientes/${pacienteId}/historial`,
          query: {
            fromCita: id,
            citaFecha: citaCompletada.fecha,
            citaHora: citaCompletada.hora,
            citaMotivo: citaCompletada.motivo
          }
        })
      }
    }
  } catch (error) {
    console.error('Error al completar cita:', error)
    showError(error.userMessage || 'Error al completar la cita')
  }
}

const mostrarDialogoCancelar = (item) => {
  citaSeleccionada.value = item
  motivoCancelacion.value = ''
  dialogoCancelar.value = true
}

const cancelarCita = async () => {
  if (!motivoCancelacion.value || motivoCancelacion.value.trim() === '') {
    showError('Debe proporcionar un motivo de cancelación')
    return
  }

  try {
    await citasStore.cancelarCita(citaSeleccionada.value.id, motivoCancelacion.value)
    showSuccess('Cita cancelada exitosamente')
    dialogoCancelar.value = false
    citaSeleccionada.value = null
    motivoCancelacion.value = ''
    await fetchCitas()
  } catch (error) {
    console.error('Error al cancelar cita:', error)
    showError(error.userMessage || 'Error al cancelar la cita')
  }
}

const deleteCita = async (id) => {
  if (confirm('¿Estás seguro de que quieres eliminar esta cita?')) {
    try {
      await citasStore.deleteCita(id)
      showSuccess('Cita eliminada exitosamente')
      await fetchCitas()
    } catch (error) {
      console.error('Error al eliminar cita:', error)
      showError(error.userMessage || 'Error al eliminar la cita')
    }
  }
}

onMounted(() => {
  fetchCitas()
})
</script>

<style scoped>
</style>
