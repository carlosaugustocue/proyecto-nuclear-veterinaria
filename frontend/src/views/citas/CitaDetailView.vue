<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12" md="8" offset-md="2">
        <v-card v-if="cita">
          <v-card-title class="bg-primary text-white py-4 d-flex justify-space-between">
            <span>Detalle de Cita</span>
            <v-btn
              icon
              color="white"
              @click="$router.back()"
            >
              <v-icon>mdi-close</v-icon>
            </v-btn>
          </v-card-title>

          <v-card-text class="pa-6">
            <v-row>
              <v-col cols="12" md="6">
                <div class="mb-6">
                  <strong>Paciente:</strong>
                  <p>{{ cita.paciente }}</p>
                </div>
                <div class="mb-6">
                  <strong>Cliente:</strong>
                  <p>{{ cita.cliente }}</p>
                </div>
              </v-col>

              <v-col cols="12" md="6">
                <div class="mb-6">
                  <strong>Fecha:</strong>
                  <p>{{ cita.fecha }}</p>
                </div>
                <div class="mb-6">
                  <strong>Hora:</strong>
                  <p>{{ cita.hora }}</p>
                </div>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="12" md="6">
                <div class="mb-6">
                  <strong>Veterinario:</strong>
                  <p>{{ cita.veterinario }}</p>
                </div>
              </v-col>

              <v-col cols="12" md="6">
                <div class="mb-6">
                  <strong>Estado:</strong>
                  <v-chip :color="getEstadoColor(cita.estado)" text-color="white">
                    {{ cita.estado }}
                  </v-chip>
                </div>
              </v-col>
            </v-row>

            <v-divider class="my-4"></v-divider>

            <v-row v-if="cita.motivo || cita.notas">
              <v-col cols="12">
                <div v-if="cita.motivo" class="mb-6">
                  <strong>Motivo:</strong>
                  <p>{{ cita.motivo }}</p>
                </div>
                <div v-if="cita.notas" class="mb-6">
                  <strong>Notas:</strong>
                  <p>{{ cita.notas }}</p>
                </div>
              </v-col>
            </v-row>

            <v-row class="mt-6">
              <v-col cols="12" md="6">
                <v-btn
                  color="info"
                  block
                  class="mb-2"
                >
                  Editar
                </v-btn>
              </v-col>

              <v-col cols="12" md="6">
                <v-btn
                  color="error"
                  block
                  @click="deleteCita"
                >
                  Eliminar
                </v-btn>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>

        <v-card v-else>
          <v-card-text class="pa-6 text-center">
            <v-progress-circular
              indeterminate
              color="primary"
            ></v-progress-circular>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useCitasStore } from '@/stores/citasStore'

const router = useRouter()
const route = useRoute()
const citasStore = useCitasStore()

const cita = computed(() => citasStore.currentCita)

const getCita = async () => {
  try {
    await citasStore.fetchCitaById(route.params.id)
  } catch (error) {
    console.error('Error al cargar cita:', error)
  }
}

const getEstadoColor = (estado) => {
  const colores = {
    'Programada': 'info',
    'Confirmada': 'success',
    'En Progreso': 'warning',
    'Completada': 'success',
    'Cancelada': 'error',
  }
  return colores[estado] || 'grey'
}

const deleteCita = async () => {
  if (confirm('¿Estás seguro de que quieres eliminar esta cita?')) {
    try {
      await citasStore.deleteCita(route.params.id)
      router.push('/citas')
    } catch (error) {
      console.error('Error al eliminar cita:', error)
    }
  }
}

onMounted(() => {
  getCita()
})
</script>

<style scoped>
</style>
