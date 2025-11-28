<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12" md="8" offset-md="2">
        <v-card v-if="cliente">
          <v-card-title class="bg-primary text-white py-4 d-flex justify-space-between">
            <span>{{ cliente.nombre }}</span>
            <v-btn icon color="white" @click="$router.back()">
              <v-icon>mdi-close</v-icon>
            </v-btn>
          </v-card-title>

          <v-card-text class="pa-6">
            <v-row>
              <v-col cols="12" md="6">
                <div class="mb-4">
                  <strong>Email:</strong>
                  <p>{{ cliente.email }}</p>
                </div>
                <div class="mb-4">
                  <strong>Teléfono:</strong>
                  <p>{{ cliente.telefono }}</p>
                </div>
              </v-col>

              <v-col cols="12" md="6">
                <div class="mb-4">
                  <strong>Documento:</strong>
                  <p>{{ cliente.documento }}</p>
                </div>
                <div class="mb-4">
                  <strong>Ciudad:</strong>
                  <p>{{ cliente.ciudad }}</p>
                </div>
              </v-col>
            </v-row>

            <v-divider class="my-4"></v-divider>

            <h3 class="mb-4">Mascotas</h3>
            <v-list v-if="cliente.mascotas && cliente.mascotas.length > 0">
              <v-list-item v-for="mascota in cliente.mascotas" :key="mascota.id">
                <v-list-item-title>{{ mascota.nombre }}</v-list-item-title>
                <v-list-item-subtitle>{{ mascota.especie }} - {{ mascota.raza }}</v-list-item-subtitle>
              </v-list-item>
            </v-list>
            <v-alert v-else type="info" text>No hay mascotas registradas</v-alert>

            <v-row class="mt-6">
              <v-col cols="12" md="6">
                <v-btn color="info" block class="mb-2">Editar</v-btn>
              </v-col>

              <v-col cols="12" md="6">
                <v-btn color="error" block @click="deleteCliente">Eliminar</v-btn>
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
import { useClientesStore } from '@/stores/clientesStore'

const router = useRouter()
const route = useRoute()

const clientesStore = useClientesStore()
const cliente = computed(() => clientesStore.currentCliente)

const getCliente = async () => {
  try {
    await clientesStore.fetchClienteById(route.params.id)
  } catch (error) {
    console.error('Error al cargar cliente:', error)
  }
}

const deleteCliente = async () => {
  if (confirm('¿Estás seguro de que quieres eliminar este cliente?')) {
    try {
      await clientesStore.deleteCliente(route.params.id)
      router.push('/clientes')
    } catch (error) {
      console.error('Error al eliminar cliente:', error)
    }
  }
}

onMounted(() => {
  getCliente()
})
</script>

<style scoped>
</style>
