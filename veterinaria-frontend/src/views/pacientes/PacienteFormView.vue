<template>
  <div>
    <div class="mb-6">
      <v-btn
        variant="text"
        prepend-icon="mdi-arrow-left"
        @click="volver"
      >
        Volver a Pacientes
      </v-btn>
    </div>

    <v-card>
      <v-card-title class="text-h5 pa-6">
        <v-icon class="mr-2">{{ modoEdicion ? 'mdi-pencil' : 'mdi-plus' }}</v-icon>
        {{ modoEdicion ? 'Editar Paciente' : 'Nuevo Paciente' }}
      </v-card-title>

      <v-divider></v-divider>

      <v-card-text class="pa-6">
        <v-form ref="formRef" @submit.prevent="guardar">
          <v-row>
            <!-- Información Básica -->
            <v-col cols="12">
              <h3 class="text-h6 mb-4">Información Básica</h3>
            </v-col>

            <v-col cols="12" md="6">
              <v-text-field
                v-model="form.nombre"
                label="Nombre *"
                prepend-inner-icon="mdi-paw"
                :rules="[rules.required, rules.minLength(2), rules.maxLength(100)]"
                required
              ></v-text-field>
            </v-col>

            <v-col cols="12" md="6">
              <v-select
                v-model="form.especie"
                :items="especiesOptions"
                label="Especie *"
                prepend-inner-icon="mdi-dog"
                :rules="[rules.required]"
                required
              ></v-select>
            </v-col>

            <v-col cols="12" md="6">
              <v-text-field
                v-model="form.raza"
                label="Raza"
                prepend-inner-icon="mdi-tag"
                :rules="[rules.maxLength(100)]"
              ></v-text-field>
            </v-col>

            <v-col cols="12" md="6">
              <v-select
                v-model="form.sexo"
                :items="sexoOptions"
                label="Sexo *"
                prepend-inner-icon="mdi-gender-male-female"
                :rules="[rules.required]"
                required
              ></v-select>
            </v-col>

            <v-col cols="12" md="6">
              <v-text-field
                v-model="form.fechaNacimiento"
                label="Fecha de Nacimiento *"
                type="date"
                prepend-inner-icon="mdi-calendar"
                :rules="[rules.required, rules.pastDate]"
                :max="hoy"
                required
              ></v-text-field>
            </v-col>

            <v-col cols="12" md="6">
              <v-text-field
                v-model.number="form.pesoKg"
                label="Peso (kg)"
                type="number"
                step="0.1"
                prepend-inner-icon="mdi-weight-kilogram"
                :rules="[rules.peso]"
              ></v-text-field>
            </v-col>

            <v-col cols="12" md="6">
              <v-text-field
                v-model="form.color"
                label="Color"
                prepend-inner-icon="mdi-palette"
                :rules="[rules.maxLength(50)]"
              ></v-text-field>
            </v-col>

            <v-col cols="12" md="6">
              <v-select
                v-model="form.estado"
                :items="estadosOptions"
                label="Estado *"
                prepend-inner-icon="mdi-circle"
                :rules="[rules.required]"
                required
              ></v-select>
            </v-col>

            <!-- Información del Propietario -->
            <v-col cols="12" class="mt-4">
              <h3 class="text-h6 mb-4">Información del Propietario</h3>
            </v-col>

            <v-col cols="12" md="6">
              <v-autocomplete
                v-model="form.clienteId"
                :items="clientesOptions"
                label="Propietario"
                prepend-inner-icon="mdi-account"
                clearable
                :loading="loadingClientes"
                hint="Busca y selecciona el propietario"
                persistent-hint
              ></v-autocomplete>
            </v-col>

            <v-col cols="12" md="6">
              <v-text-field
                v-model="form.microchip"
                label="Microchip"
                prepend-inner-icon="mdi-chip"
                :rules="[rules.maxLength(50)]"
                hint="Número único de identificación"
                persistent-hint
              ></v-text-field>
            </v-col>

            <!-- Información Adicional -->
            <v-col cols="12" class="mt-4">
              <h3 class="text-h6 mb-4">Información Adicional</h3>
            </v-col>

            <v-col cols="12">
              <v-text-field
                v-model="form.fotoUrl"
                label="URL de Foto"
                prepend-inner-icon="mdi-image"
                :rules="[rules.maxLength(500), rules.url]"
                hint="URL de la imagen del paciente"
                persistent-hint
              ></v-text-field>
            </v-col>

            <v-col cols="12">
              <v-textarea
                v-model="form.observaciones"
                label="Observaciones"
                prepend-inner-icon="mdi-note-text"
                :rules="[rules.maxLength(1000)]"
                rows="4"
                counter="1000"
                hint="Información adicional sobre el paciente"
                persistent-hint
              ></v-textarea>
            </v-col>

            <!-- Preview de foto si existe URL -->
            <v-col v-if="form.fotoUrl" cols="12" class="text-center">
              <v-avatar size="120">
                <v-img
                  :src="form.fotoUrl"
                  :alt="form.nombre"
                  @error="errorCargandoImagen"
                ></v-img>
              </v-avatar>
              <p class="text-caption text-grey-darken-1 mt-2">Vista previa de la foto</p>
            </v-col>
          </v-row>

          <v-divider class="my-6"></v-divider>

          <!-- Botones de acción -->
          <div class="d-flex justify-end gap-2">
            <v-btn
              variant="text"
              @click="volver"
            >
              Cancelar
            </v-btn>
            <v-btn
              type="submit"
              color="primary"
              :loading="loading"
            >
              {{ modoEdicion ? 'Actualizar' : 'Crear' }} Paciente
            </v-btn>
          </div>
        </v-form>
      </v-card-text>
    </v-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { usePacientesStore } from '@/store/pacientes'
import { useClientesStore } from '@/store/clientes'
import { useAppStore } from '@/store/app'
import { storeToRefs } from 'pinia'

const router = useRouter()
const route = useRoute()
const pacientesStore = usePacientesStore()
const clientesStore = useClientesStore()
const appStore = useAppStore()

const { loading } = storeToRefs(pacientesStore)
const { clientes } = storeToRefs(clientesStore)

const formRef = ref(null)
const loadingClientes = ref(false)
const modoEdicion = computed(() => !!route.params.id)
const hoy = new Date().toISOString().split('T')[0]

const form = reactive({
  nombre: '',
  especie: null,
  raza: '',
  fechaNacimiento: '',
  sexo: null,
  color: '',
  pesoKg: null,
  estado: 'ACTIVO',
  fotoUrl: '',
  observaciones: '',
  microchip: '',
  clienteId: null,
})

const especiesOptions = [
  { title: 'Perro', value: 'PERRO' },
  { title: 'Gato', value: 'GATO' },
  { title: 'Ave', value: 'AVE' },
  { title: 'Reptil', value: 'REPTIL' },
  { title: 'Roedor', value: 'ROEDOR' },
  { title: 'Otro', value: 'OTRO' },
]

const sexoOptions = [
  { title: 'Macho', value: 'MACHO' },
  { title: 'Hembra', value: 'HEMBRA' },
]

const estadosOptions = [
  { title: 'Activo', value: 'ACTIVO' },
  { title: 'Inactivo', value: 'INACTIVO' },
  { title: 'Fallecido', value: 'FALLECIDO' },
]

const clientesOptions = computed(() => {
  return clientes.value.map((cliente) => ({
    title: `${cliente.nombre} - ${cliente.email}`,
    value: cliente.id,
  }))
})

const rules = {
  required: (v) => !!v || 'Este campo es requerido',
  minLength: (min) => (v) => !v || v.length >= min || `Mínimo ${min} caracteres`,
  maxLength: (max) => (v) => !v || v.length <= max || `Máximo ${max} caracteres`,
  peso: (v) => {
    if (!v) return true
    if (v < 0.1) return 'El peso debe ser mayor a 0.1 kg'
    if (v > 500) return 'El peso no puede exceder 500 kg'
    return true
  },
  pastDate: (v) => {
    if (!v) return true
    const fecha = new Date(v)
    const hoy = new Date()
    return fecha < hoy || 'La fecha debe ser en el pasado'
  },
  url: (v) => {
    if (!v) return true
    try {
      new URL(v)
      return true
    } catch {
      return 'URL inválida'
    }
  },
}

onMounted(async () => {
  // Cargar lista de clientes para el selector
  loadingClientes.value = true
  try {
    await clientesStore.cargarClientes()
  } catch (error) {
    console.error('Error cargando clientes:', error)
  } finally {
    loadingClientes.value = false
  }

  // Si es edición, cargar datos del paciente
  if (modoEdicion.value) {
    try {
      const paciente = await pacientesStore.cargarPaciente(route.params.id)
      Object.assign(form, {
        nombre: paciente.nombre,
        especie: paciente.especie,
        raza: paciente.raza || '',
        fechaNacimiento: paciente.fechaNacimiento,
        sexo: paciente.sexo,
        color: paciente.color || '',
        pesoKg: paciente.pesoKg || null,
        estado: paciente.estado,
        fotoUrl: paciente.fotoUrl || '',
        observaciones: paciente.observaciones || '',
        microchip: paciente.microchip || '',
        clienteId: paciente.cliente?.id || null,
      })
    } catch (error) {
      appStore.showError('Error al cargar el paciente')
      volver()
    }
  }
})

async function guardar() {
  const { valid } = await formRef.value.validate()
  if (!valid) return

  const pacienteData = {
    nombre: form.nombre,
    especie: form.especie,
    raza: form.raza || null,
    fechaNacimiento: form.fechaNacimiento,
    sexo: form.sexo,
    color: form.color || null,
    pesoKg: form.pesoKg || null,
    estado: form.estado,
    fotoUrl: form.fotoUrl || null,
    observaciones: form.observaciones || null,
    microchip: form.microchip || null,
    clienteId: form.clienteId || null,
  }

  try {
    if (modoEdicion.value) {
      await pacientesStore.actualizarPaciente(route.params.id, pacienteData)
    } else {
      await pacientesStore.crearPaciente(pacienteData)
    }
    volver()
  } catch (error) {
    console.error('Error al guardar paciente:', error)
  }
}

function volver() {
  pacientesStore.clearPacienteActual()
  router.push('/pacientes')
}

function errorCargandoImagen() {
  appStore.showWarning('No se pudo cargar la imagen desde la URL proporcionada')
}
</script>
