<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12" md="8" offset-md="2">
        <v-card>
          <v-card-title class="bg-primary text-white py-4">
            {{ isEditing ? 'Editar Paciente' : 'Nuevo Paciente' }}
          </v-card-title>

          <v-card-text class="pa-6">
            <v-form @submit.prevent="savePaciente" ref="form">
              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="form.nombre"
                    label="Nombre del Paciente *"
                    :rules="[rules.required]"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-select
                    v-model="form.clienteId"
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
                    v-model="form.especie"
                    label="Especie *"
                    :items="especieOptions"
                    item-title="title"
                    item-value="value"
                    :rules="[rules.required]"
                  ></v-select>
                </v-col>

                <v-col cols="12" md="6">
                  <v-select
                    v-model="form.razaId"
                    label="Raza"
                    :items="razas"
                    item-title="nombre"
                    item-value="id"
                  ></v-select>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="form.fechaNacimiento"
                    label="Fecha de Nacimiento *"
                    type="date"
                    :rules="[rules.required]"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-select
                    v-model="form.sexo"
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
                    v-model="form.peso"
                    label="Peso (kg)"
                    type="number"
                    step="0.1"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="form.color"
                    label="Color"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12">
                  <v-text-field
                    v-model="form.microchip"
                    label="Microchip"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12">
                  <v-textarea
                    v-model="form.observaciones"
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
                    {{ isEditing ? 'Actualizar' : 'Crear' }} Paciente
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

const loading = computed(() => pacientesStore.loading)
const isEditing = computed(() => !!route.params.id)

const form = reactive({
  nombre: '',
  clienteId: null,
  especie: null,
  razaId: null,
  fechaNacimiento: null,
  sexo: null,
  peso: null,
  color: '',
  microchip: '',
  observaciones: '',
})

const clientes = ref([])
const razas = ref([])
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
        Object.assign(form, paciente)
      }
    }
  } catch (error) {
    console.error('Error al cargar datos:', error)
  }
}

const savePaciente = async () => {
  try {
    if (isEditing.value) {
      await pacientesStore.updatePaciente(route.params.id, form)
    } else {
      await pacientesStore.createPaciente(form)
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
