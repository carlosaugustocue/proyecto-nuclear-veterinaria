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
                    v-model="form.clienteId"
                    label="Cliente *"
                    :items="clientes"
                    :item-title="(item) => item.nombreCompleto || `${item.nombre || ''} ${item.apellido || ''}`.trim() || 'Sin nombre'"
                    item-value="id"
                    :rules="[rules.required]"
                    prepend-icon="mdi-account"
                    @update:model-value="onClienteSeleccionado"
                    clearable
                  >
                    <template v-slot:item="{ props, item }">
                      <v-list-item v-bind="props">
                        <template v-slot:prepend>
                          <v-avatar color="blue" size="32">
                            <v-icon color="white" size="small">mdi-account</v-icon>
                          </v-avatar>
                        </template>
                        <v-list-item-title>{{ item.raw.nombreCompleto || `${item.raw.nombre || ''} ${item.raw.apellido || ''}`.trim() }}</v-list-item-title>
                        <v-list-item-subtitle v-if="item.raw.pacientes && item.raw.pacientes.length > 0">
                          {{ item.raw.pacientes.length }} mascota{{ item.raw.pacientes.length !== 1 ? 's' : '' }}
                        </v-list-item-subtitle>
                      </v-list-item>
                    </template>
                  </v-select>
                </v-col>

                <v-col cols="12" md="6">
                  <v-select
                    v-model="form.pacienteId"
                    label="Mascota *"
                    :items="pacientesFiltrados"
                    :item-title="(item) => `${item.nombre}${item.especieNombre ? ' (' + item.especieNombre + ')' : ''}`"
                    item-value="id"
                    :rules="[rules.required]"
                    prepend-icon="mdi-paw"
                    :disabled="!form.clienteId"
                    :hint="form.clienteId ? 'Seleccione una mascota del cliente' : 'Primero seleccione un cliente'"
                    persistent-hint
                  >
                    <template v-slot:item="{ props, item }">
                      <v-list-item v-bind="props">
                        <template v-slot:prepend>
                          <v-avatar color="primary" size="32">
                            <v-icon color="white" size="small">mdi-paw</v-icon>
                          </v-avatar>
                        </template>
                        <v-list-item-title>{{ item.raw.nombre }}</v-list-item-title>
                        <v-list-item-subtitle>
                          <span v-if="item.raw.especieNombre">{{ item.raw.especieNombre }}</span>
                          <span v-if="item.raw.razaNombre"> - {{ item.raw.razaNombre }}</span>
                          <span v-if="item.raw.clienteNombre"> - Dueño: {{ item.raw.clienteNombre }}</span>
                        </v-list-item-subtitle>
                      </v-list-item>
                    </template>
                    <template v-slot:no-data>
                      <v-list-item>
                        <v-list-item-title class="text-grey">
                          {{ form.clienteId ? 'No hay mascotas registradas para este cliente' : 'Seleccione un cliente primero' }}
                        </v-list-item-title>
                      </v-list-item>
                    </template>
                  </v-select>
                </v-col>
              </v-row>

              <!-- Alerta informativa cuando se selecciona cliente sin mascotas -->
              <v-row v-if="form.clienteId && pacientesFiltrados.length === 0 && !cargandoMascotas">
                <v-col cols="12">
                  <v-alert type="info" variant="tonal" class="mb-4">
                    <div class="d-flex align-center">
                      <v-icon class="mr-2">mdi-information</v-icon>
                      <div class="flex-grow-1">
                        <strong>Sin Mascotas:</strong> Este cliente no tiene mascotas registradas.
                      </div>
                      <v-btn
                        size="small"
                        color="primary"
                        prepend-icon="mdi-plus"
                        :to="`/pacientes/nuevo?clienteId=${form.clienteId}`"
                      >
                        Registrar Mascota
                      </v-btn>
                    </div>
                  </v-alert>
                </v-col>
              </v-row>

              <!-- Indicador de carga de mascotas -->
              <v-row v-if="cargandoMascotas">
                <v-col cols="12">
                  <v-alert type="info" variant="tonal" class="mb-4">
                    <div class="d-flex align-center">
                      <v-progress-circular indeterminate color="primary" size="20" class="mr-2"></v-progress-circular>
                      <span>Cargando mascotas del cliente...</span>
                    </div>
                  </v-alert>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="form.fecha"
                    label="Fecha *"
                    type="date"
                    :rules="[rules.required, rules.fechaFutura]"
                    :min="new Date().toISOString().split('T')[0]"
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
                    @update:model-value="checkScheduleConflict"
                  ></v-select>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12">
                  <v-textarea
                    v-model="form.motivo"
                    label="Motivo de la Cita *"
                    counter
                    maxlength="500"
                    :rules="[rules.motivoMinLength]"
                    hint="Mínimo 5 caracteres"
                    persistent-hint
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
import { ref, reactive, onMounted, computed, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useCitasStore } from '@/stores/citasStore'
import { usePacientesStore } from '@/stores/pacientesStore'
import { useReferenceData } from '@/composables/useReferenceData'
import { useNotification } from '@/composables/useNotification'

const router = useRouter()
const route = useRoute()
const citasStore = useCitasStore()
const pacientesStore = usePacientesStore()
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
const pacientesFiltrados = ref([])
const clientes = ref([])
const veterinarios = ref([])
const tiposServicio = ref([])
const cargandoMascotas = ref(false)

// Estado para detección de conflictos
const scheduleConflict = ref(false)
const showAvailableTimes = ref(false)
const horariosDisponibles = ref([])

const rules = {
  required: (v) => !!v || 'Este campo es requerido',
  motivoMinLength: (v) => {
    if (!v || v.trim().length === 0) return 'El motivo es obligatorio'
    if (v.trim().length < 5) return 'El motivo debe tener al menos 5 caracteres'
    if (v.trim().length > 500) return 'El motivo no puede exceder 500 caracteres'
    return true
  },
  fechaFutura: (v) => {
    if (!v) return 'La fecha es obligatoria'
    const fechaSeleccionada = new Date(v)
    const hoy = new Date()
    hoy.setHours(0, 0, 0, 0)
    if (fechaSeleccionada <= hoy) {
      return 'La fecha debe ser futura'
    }
    return true
  },
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

const onClienteSeleccionado = async (clienteId) => {
  // Limpiar la selección de paciente cuando cambia el cliente
  form.pacienteId = null
  pacientesFiltrados.value = []

  if (!clienteId) {
    return
  }

  cargandoMascotas.value = true
  try {
    let mascotasDelCliente = []

    // Intentar usar la función del store si existe, si no, usar API directa
    if (typeof pacientesStore.fetchPacientesActivosByCliente === 'function') {
      mascotasDelCliente = await pacientesStore.fetchPacientesActivosByCliente(clienteId)
    } else {
      // Fallback: usar API directa
      console.warn('fetchPacientesActivosByCliente no está disponible en el store, usando API directa')
      const { useApi } = await import('@/composables/useApi')
      const { get } = useApi()
      
      const response = await get(`/v1/pacientes/cliente/${clienteId}/activos`)
      const rawData = response.data || []
      mascotasDelCliente = rawData.map(p => ({
        ...p,
        clienteObj: p.cliente,
        razaObj: p.raza,
        clienteNombre: p.cliente?.nombreCompleto || p.cliente?.nombre || '',
        razaNombre: p.raza?.nombre || '',
        estadoNombre: p.estado?.nombre || p.estado || '',
        especieNombre: p.raza?.especie || p.especie || '',
      }))
    }

    pacientesFiltrados.value = mascotasDelCliente

    if (mascotasDelCliente.length === 0) {
      // No mostrar error, solo información
      console.log('Este cliente no tiene mascotas registradas')
    }
  } catch (error) {
    console.error('Error al cargar mascotas del cliente:', error)
    const mensaje = error.response?.data?.userMessage || 
                   error.response?.data?.message || 
                   'Error al cargar las mascotas del cliente seleccionado'
    showError(mensaje)
    pacientesFiltrados.value = []
  } finally {
    cargandoMascotas.value = false
  }
}

const loadData = async () => {
  try {
    // No cargar todos los pacientes inicialmente, solo cuando se seleccione un cliente
    const [clientesData, veterinariosData, tiposServicioData] = await Promise.all([
      fetchClientes(),
      fetchUsuarios('VETERINARIO'),
      fetchTiposServicio(),
    ])

    clientes.value = clientesData
    veterinarios.value = veterinariosData
    tiposServicio.value = tiposServicioData
    
    // Log para debugging
    console.log('[CitaFormView] Tipos de servicio cargados:', tiposServicioData.length)
    if (tiposServicioData.length > 0) {
      console.log('[CitaFormView] Primer tipo de servicio:', tiposServicioData[0])
      console.log('[CitaFormView] Campos disponibles:', Object.keys(tiposServicioData[0]))
    }

    // Si estamos editando, cargar los datos de la cita
    if (isEditing.value) {
      await citasStore.fetchCitaById(route.params.id)
      const cita = citasStore.currentCita
      if (cita) {
        // Mapear IDs de objetos anidados
        const clienteId = cita.clienteObj?.id || cita.clienteId
        const pacienteId = cita.pacienteObj?.id || cita.pacienteId
        
        form.clienteId = clienteId
        form.veterinarioId = cita.veterinarioObj?.id || cita.veterinarioId
        form.tipoServicioId = cita.tipoServicioObj?.id || cita.tipoServicioId
        form.fecha = cita.fecha
        form.hora = cita.hora
        form.motivo = cita.motivo
        form.notas = cita.notas

        // Si hay cliente, cargar sus mascotas
        if (clienteId) {
          await onClienteSeleccionado(clienteId)
          // Después de cargar las mascotas, seleccionar la mascota de la cita
          if (pacienteId) {
            form.pacienteId = pacienteId
          }
        }
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

  // Obtener la duración del tipo de servicio seleccionado
  let duracionMinutos = 30 // Valor por defecto
  if (form.tipoServicioId) {
    const tipoServicioSeleccionado = tiposServicio.value.find(ts => ts.id === form.tipoServicioId)
    console.log(`[CitaFormView] Buscando tipo de servicio ID: ${form.tipoServicioId}`)
    console.log(`[CitaFormView] Tipo de servicio encontrado:`, tipoServicioSeleccionado)
    
    if (tipoServicioSeleccionado) {
      // Intentar obtener la duración de diferentes posibles nombres de campo
      const duracion = tipoServicioSeleccionado.duracionEstimada || 
                      tipoServicioSeleccionado.duracion_estimada ||
                      tipoServicioSeleccionado.duracion ||
                      tipoServicioSeleccionado.duration
      
      if (duracion) {
        duracionMinutos = parseInt(duracion, 10)
        console.log(`[CitaFormView] ✓ Usando duración del tipo de servicio: ${duracionMinutos} minutos para "${tipoServicioSeleccionado.nombre}"`)
      } else {
        console.warn(`[CitaFormView] ⚠ Tipo de servicio "${tipoServicioSeleccionado.nombre}" (ID: ${form.tipoServicioId}) no tiene duración, usando 30 minutos por defecto`)
        console.warn(`[CitaFormView] Campos disponibles:`, Object.keys(tipoServicioSeleccionado))
      }
    } else {
      console.error(`[CitaFormView] ✗ Tipo de servicio ${form.tipoServicioId} no encontrado en la lista`)
      console.error(`[CitaFormView] Tipos de servicio disponibles:`, tiposServicio.value.map(ts => ({ id: ts.id, nombre: ts.nombre })))
    }
  } else {
    console.log('[CitaFormView] No hay tipo de servicio seleccionado, usando 30 minutos por defecto')
  }

  try {
    // Obtener horarios disponibles para el veterinario en la fecha seleccionada
    // Usando la duración del tipo de servicio seleccionado
    console.log(`[CitaFormView] Obteniendo horarios disponibles para veterinario ${form.veterinarioId}, fecha ${form.fecha}, duración ${duracionMinutos} minutos`)
    const horarios = await citasStore.obtenerHorariosDisponibles(
      form.veterinarioId,
      form.fecha,
      duracionMinutos
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
  // Validar formulario antes de enviar
  const { valid } = await formRef.value.validate()
  if (!valid) {
    showError('Por favor, complete todos los campos requeridos correctamente')
    return
  }

  // Validaciones adicionales
  if (!form.motivo || form.motivo.trim().length < 5) {
    showError('El motivo debe tener al menos 5 caracteres')
    return
  }

  if (form.motivo.trim().length > 500) {
    showError('El motivo no puede exceder 500 caracteres')
    return
  }

  // Validar que la fecha sea futura
  if (form.fecha) {
    const fechaSeleccionada = new Date(form.fecha)
    const hoy = new Date()
    hoy.setHours(0, 0, 0, 0)
    if (fechaSeleccionada <= hoy) {
      showError('La fecha debe ser futura')
      return
    }
  }

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

  // Obtener la duración del tipo de servicio para logging
  let duracionServicio = 'desconocida'
  if (form.tipoServicioId) {
    const tipoServicioSeleccionado = tiposServicio.value.find(ts => ts.id === form.tipoServicioId)
    if (tipoServicioSeleccionado) {
      duracionServicio = tipoServicioSeleccionado.duracionEstimada || 
                        tipoServicioSeleccionado.duracion_estimada ||
                        tipoServicioSeleccionado.duracion ||
                        'no especificada'
    }
  }
  
  console.log(`[CitaFormView] Enviando cita - Fecha: ${form.fecha}, Hora: ${form.hora}, Tipo Servicio ID: ${form.tipoServicioId}, Duración: ${duracionServicio} min`)

  // Preparar datos para enviar
  // Asegurar que la fecha se envíe como string en formato ISO (YYYY-MM-DD)
  let fechaParaEnviar = form.fecha
  if (fechaParaEnviar instanceof Date) {
    // Si es un objeto Date, convertir a string ISO
    fechaParaEnviar = fechaParaEnviar.toISOString().split('T')[0]
  } else if (typeof fechaParaEnviar === 'string') {
    // Si ya es string, asegurar que esté en formato correcto
    // El input type="date" ya devuelve YYYY-MM-DD, pero verificamos
    if (!/^\d{4}-\d{2}-\d{2}$/.test(fechaParaEnviar)) {
      // Si no está en formato correcto, intentar parsearlo
      const fechaObj = new Date(fechaParaEnviar)
      if (!isNaN(fechaObj.getTime())) {
        fechaParaEnviar = fechaObj.toISOString().split('T')[0]
      }
    }
  }
  
  console.log(`[CitaFormView] Fecha original: ${form.fecha} (tipo: ${typeof form.fecha}), Fecha para enviar: ${fechaParaEnviar}`)
  
  const datosParaEnviar = {
    pacienteId: form.pacienteId,
    clienteId: form.clienteId,
    veterinarioId: form.veterinarioId,
    tipoServicioId: form.tipoServicioId,
    fecha: fechaParaEnviar, // Asegurar formato YYYY-MM-DD
    hora: normalizeTimeFormat(form.hora), // Asegurar formato HH:mm
    motivo: form.motivo.trim(),
    notas: form.notas ? form.notas.trim() : null,
  }

  // Validar que todos los campos requeridos estén presentes
  const camposRequeridos = ['pacienteId', 'clienteId', 'veterinarioId', 'tipoServicioId', 'fecha', 'hora', 'motivo']
  const camposFaltantes = camposRequeridos.filter(campo => !datosParaEnviar[campo])
  
  if (camposFaltantes.length > 0) {
    showError(`Faltan campos requeridos: ${camposFaltantes.join(', ')}`)
    return
  }

  try {
    if (isEditing.value) {
      await citasStore.updateCita(route.params.id, datosParaEnviar)
      showSuccess('Cita actualizada exitosamente')
    } else {
      await citasStore.createCita(datosParaEnviar)
      showSuccess('Cita creada exitosamente')
    }
    router.push('/citas')
  } catch (error) {
    console.error('Error al guardar cita:', error)
    
    // Extraer mensaje de error más detallado
    let mensajeError = 'Error al guardar la cita'
    
    if (error.response?.data) {
      const errorData = error.response.data
      
      // Si hay mensaje directo
      if (errorData.message) {
        mensajeError = errorData.message
      } else if (errorData.error) {
        mensajeError = errorData.error
      } else if (errorData.mensaje) {
        mensajeError = errorData.mensaje
      }
      
      // Si hay errores de validación
      if (errorData.errors && Array.isArray(errorData.errors)) {
        mensajeError = errorData.errors.join(', ')
      } else if (errorData.errors && typeof errorData.errors === 'object') {
        // Errores de validación de Bean Validation
        const erroresArray = Object.values(errorData.errors).flat()
        mensajeError = erroresArray.join(', ')
      }
    } else if (error.userMessage) {
      mensajeError = error.userMessage
    } else if (error.message) {
      mensajeError = error.message
    }
    
    showError(mensajeError)
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
</style>
