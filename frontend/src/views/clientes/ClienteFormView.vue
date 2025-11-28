<template>
  <v-container fluid class="pa-6">
    <v-row>
      <v-col cols="12" md="8" offset-md="2">
        <v-card>
          <v-card-title class="bg-primary text-white py-4">
            {{ isEditing ? 'Editar Cliente' : 'Nuevo Cliente' }}
          </v-card-title>

          <v-card-text class="pa-6">
            <v-form @submit.prevent="saveCliente" ref="form">
              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="form.nombre"
                    label="Nombre Completo *"
                    :rules="[rules.required]"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="form.email"
                    label="Email *"
                    type="email"
                    :rules="[rules.required, rules.email]"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="form.telefono"
                    label="Teléfono *"
                    :rules="[rules.required]"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="form.documento"
                    label="Documento"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12">
                  <v-text-field
                    v-model="form.direccion"
                    label="Dirección"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="form.ciudad"
                    label="Ciudad"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="form.departamento"
                    label="Departamento"
                  ></v-text-field>
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
                    {{ isEditing ? 'Actualizar' : 'Crear' }} Cliente
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
import { onMounted, reactive, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useClientesStore } from '@/stores/clientesStore'

const router = useRouter()
const route = useRoute()
const clientesStore = useClientesStore()

const loading = computed(() => clientesStore.loading)
const isEditing = computed(() => !!route.params.id)

const form = reactive({
  nombre: '',
  email: '',
  telefono: '',
  documento: '',
  direccion: '',
  ciudad: '',
  departamento: '',
})

const rules = {
  required: (v) => !!v || 'Este campo es requerido',
  email: (v) => /.+@.+\..+/.test(v) || 'El email debe ser válido',
}

const saveCliente = async () => {
  try {
    if (isEditing.value) {
      await clientesStore.updateCliente(route.params.id, form)
    } else {
      await clientesStore.createCliente(form)
    }
    router.push('/clientes')
  } catch (error) {
    console.error('Error al guardar cliente:', error)
  }
}

onMounted(async () => {
  if (isEditing.value) {
    try {
      await clientesStore.fetchClienteById(route.params.id)
      const cliente = clientesStore.currentCliente
      if (cliente) {
        Object.assign(form, cliente)
      }
    } catch (error) {
      console.error('Error al cargar cliente:', error)
    }
  }
})
</script>

<style scoped>
</style>
