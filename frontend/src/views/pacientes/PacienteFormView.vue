<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12" md="8" offset-md="2">
        <v-card>
          <v-card-title class="bg-primary text-white py-4">
            {{ isEditing ? 'Editar Mascota' : 'Nueva Mascota' }}
          </v-card-title>

          <v-card-text class="pa-6">
            <v-form @submit.prevent="savePaciente" ref="formRef">
              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="nombre"
                    label="Nombre de la Mascota *"
                    :rules="[rules.required]"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-select
                    v-model="clienteId"
                    label="Propietario *"
                    :items="clientes"
                    item-title="nombre"
                    item-value="id"
                    :rules="[rules.required]"
                  ></v-select>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" md="6">
                  <v-select
                    v-model="especie"
                    label="Especie *"
                    :items="especieOptions"
                    item-title="title"
                    item-value="value"
                    :rules="[rules.required]"
                  ></v-select>
                </v-col>

                <v-col cols="12" md="6">
                  <v-select
                    v-model="razaSeleccionada"
                    label="Raza"
                    :items="razas"
                    item-title="nombre"
                    item-value="id"
                    clearable
                  ></v-select>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="fechaNacimiento"
                    label="Fecha de Nacimiento *"
                    type="date"
                    :rules="[rules.required]"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-select
                    v-model="sexo"
                    label="Sexo *"
                    :items="sexoOptions"
                    item-title="title"
                    item-value="value"
                    :rules="[rules.required]"
                  ></v-select>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="pesoKg"
                    label="Peso (kg)"
                    type="number"
                    step="0.1"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="color"
                    label="Color"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12">
                  <v-text-field
                    v-model="microchip"
                    label="Microchip"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12">
                  <v-textarea
                    v-model="observaciones"
                    label="Observaciones"
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
                    {{ isEditing ? 'Actualizar' : 'Crear' }} Mascota
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
import { usePacientesStore } from '@/stores/pacientesStore'
import { useReferenceData } from '@/composables/useReferenceData'

const router = useRouter()
const route = useRoute()
const pacientesStore = usePacientesStore()
const { fetchClientes, fetchRazas } = useReferenceData()

const formRef = ref(null)
const loading = computed(() => pacientesStore.loading)
const isEditing = computed(() => !!route.params.id)

const nombre = ref('')
const clienteId = ref(null)
const especie = ref(null)
const fechaNacimiento = ref(null)
const sexo = ref(null)
const pesoKg = ref(null)
const color = ref('')
const microchip = ref('')
const observaciones = ref('')

const clientes = ref([])
const razas = ref([])
const razaSeleccionada = ref(null)
const especieOptions = [
  { title: 'Perro', value: 'PERRO' },
  { title: 'Gato', value: 'GATO' },
  { title: 'Ave', value: 'AVE' },
  { title: 'Reptil', value: 'REPTIL' },
  { title: 'Roedor', value: 'ROEDOR' },
  { title: 'Otro', value: 'OTRO' }
]
const sexoOptions = [
  { title: 'Macho', value: 'MACHO' },
  { title: 'Hembra', value: 'HEMBRA' }
]

const rules = {
  required: (v) => !!v || 'Este campo es requerido',
}

const loadData = async () => {
  try {
    const [clientesData, razasData] = await Promise.all([
      fetchClientes(),
      fetchRazas(),
    ])

    clientes.value = clientesData
    razas.value = razasData

    // Si estamos editando, cargar los datos del paciente
    if (isEditing.value) {
      await pacientesStore.fetchPacienteById(route.params.id)
      const paciente = pacientesStore.currentPaciente
      if (paciente) {
        // Mapear correctamente el paciente a las refs
        nombre.value = paciente.nombre || ''
        clienteId.value = paciente.cliente?.id || paciente.clienteId || null
        especie.value = paciente.especie || null

        // Buscar la raza por nombre o ID
        const razaNombre = paciente.raza?.nombre || paciente.raza || ''
        const razaEncontrada = razas.value.find(r => r.nombre === razaNombre || r.id === paciente.raza?.id)
        razaSeleccionada.value = razaEncontrada?.id || null

        fechaNacimiento.value = paciente.fechaNacimiento || null
        sexo.value = paciente.sexo || null
        pesoKg.value = paciente.pesoKg || paciente.peso || null
        color.value = paciente.color || ''
        microchip.value = paciente.microchip || ''
        observaciones.value = paciente.observaciones || ''
      }
    }
  } catch (error) {
    console.error('Error al cargar datos:', error)
  }
}

const savePaciente = async () => {
  try {
    // Extraer el nombre de la raza seleccionada
    const razaNombre = razaSeleccionada.value
      ? razas.value.find(r => r.id === razaSeleccionada.value)?.nombre
      : null

    const formData = {
      nombre: nombre.value,
      clienteId: clienteId.value,
      especie: especie.value,
      raza: razaNombre,
      fechaNacimiento: fechaNacimiento.value,
      sexo: sexo.value,
      pesoKg: pesoKg.value ? parseFloat(pesoKg.value) : null,
      color: color.value || null,
      microchip: microchip.value || null,
      observaciones: observaciones.value || null,
    }

    if (isEditing.value) {
      await pacientesStore.updatePaciente(route.params.id, formData)
    } else {
      await pacientesStore.createPaciente(formData)
    }
    router.push('/pacientes')
  } catch (error) {
    console.error('Error al guardar paciente:', error)
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
</style>
