<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12" md="8" offset-md="2">
        <v-card v-if="paciente">
          <v-card-title class="bg-primary text-white py-4 d-flex justify-space-between">
            <span>{{ paciente.nombre }}</span>
            <v-btn icon color="white" @click="$router.back()">
              <v-icon>mdi-close</v-icon>
            </v-btn>
          </v-card-title>

          <v-card-text class="pa-6">
            <v-row>
              <v-col cols="12" md="6">
                <div class="mb-4">
                  <strong>Propietario:</strong>
                  <p>{{ paciente.propietario }}</p>
                </div>
                <div class="mb-4">
                  <strong>Especie:</strong>
                  <v-chip color="secondary" text-color="white">{{ paciente.especie }}</v-chip>
                </div>
              </v-col>

              <v-col cols="12" md="6">
                <div class="mb-4">
                  <strong>Raza:</strong>
                  <p>{{ paciente.raza }}</p>
                </div>
                <div class="mb-4">
                  <strong>Estado:</strong>
                  <v-chip :color="getEstadoColor(paciente.estado)" text-color="white">
                    {{ paciente.estado }}
                  </v-chip>
                </div>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="12" md="6">
                <div class="mb-4">
                  <strong>Fecha de Nacimiento:</strong>
                  <p>{{ paciente.fechaNacimiento }}</p>
                </div>
                <div class="mb-4">
                  <strong>Sexo:</strong>
                  <p>{{ paciente.sexo }}</p>
                </div>
              </v-col>

              <v-col cols="12" md="6">
                <div class="mb-4">
                  <strong>Peso:</strong>
                  <p>{{ paciente.peso }} kg</p>
                </div>
                <div class="mb-4">
                  <strong>Color:</strong>
                  <p>{{ paciente.color }}</p>
                </div>
              </v-col>
            </v-row>

            <v-divider class="my-4"></v-divider>

            <v-row v-if="paciente.observaciones">
              <v-col cols="12">
                <strong>Observaciones:</strong>
                <p>{{ paciente.observaciones }}</p>
              </v-col>
            </v-row>

            <v-row class="mt-6">
              <v-col cols="12" md="6">
                <v-btn color="info" block class="mb-2">Editar</v-btn>
              </v-col>

              <v-col cols="12" md="6">
                <v-btn color="error" block @click="deletePaciente">Eliminar</v-btn>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>

        <v-card v-else>
          <v-card-text class="pa-6 text-center">
            <v-progress-circular indeterminate color="primary"></v-progress-circular>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { usePacientesStore } from '@/stores/pacientesStore'

const router = useRouter()
const route = useRoute()
const pacientesStore = usePacientesStore()

const paciente = computed(() => pacientesStore.currentPaciente)

const getPaciente = async () => {
  try {
    await pacientesStore.fetchPacienteById(route.params.id)
  } catch (error) {
    console.error('Error al cargar paciente:', error)
  }
}

const getEstadoColor = (estado) => {
  const colores = {
    'Activo': 'success',
    'Inactivo': 'warning',
    'Fallecido': 'error',
  }
  return colores[estado] || 'grey'
}

const deletePaciente = async () => {
  if (confirm('¿Estás seguro de que quieres eliminar este paciente?')) {
    try {
      await pacientesStore.deletePaciente(route.params.id)
      router.push('/pacientes')
    } catch (error) {
      console.error('Error al eliminar paciente:', error)
    }
  }
}

onMounted(() => {
  getPaciente()
})
</script>

<style scoped>
</style>
