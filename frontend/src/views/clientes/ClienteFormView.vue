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
                    v-model="nombre"
                    label="Nombre *"
                    :rules="[rules.required]"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="apellido"
                    label="Apellido *"
                    :rules="[rules.required]"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="dni"
                    label="DNI *"
                    :rules="[rules.required]"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="email"
                    label="Email *"
                    type="email"
                    :rules="[rules.required, rules.email]"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="telefono"
                    label="Teléfono *"
                    :rules="[rules.required]"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="direccion"
                    label="Dirección *"
                    :rules="[rules.required]"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="ciudad"
                    label="Ciudad"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="departamento"
                    label="Departamento"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-row>
                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="codigoPostal"
                    label="Código Postal"
                  ></v-text-field>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field
                    v-model="observaciones"
                    label="Observaciones"
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
import { onMounted, ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useClientesStore } from '@/stores/clientesStore'

const router = useRouter()
const route = useRoute()
const clientesStore = useClientesStore()

const loading = computed(() => clientesStore.loading)
const isEditing = computed(() => !!route.params.id)

const nombre = ref('')
const apellido = ref('')
const dni = ref('')
const email = ref('')
const telefono = ref('')
const direccion = ref('')
const ciudad = ref('')
const departamento = ref('')
const codigoPostal = ref('')
const observaciones = ref('')

const rules = {
  required: (v) => !!v || 'Este campo es requerido',
  email: (v) => /.+@.+\..+/.test(v) || 'El email debe ser válido',
}

const saveCliente = async () => {
  try {
    const formData = {
      nombre: nombre.value,
      apellido: apellido.value,
      dni: dni.value,
      email: email.value,
      telefono: telefono.value,
      direccion: direccion.value,
      ciudad: ciudad.value || null,
      departamento: departamento.value || null,
      codigoPostal: codigoPostal.value || null,
      observaciones: observaciones.value || null,
    }

    if (isEditing.value) {
      await clientesStore.updateCliente(route.params.id, formData)
    } else {
      await clientesStore.createCliente(formData)
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
        nombre.value = cliente.nombre || ''
        apellido.value = cliente.apellido || ''
        dni.value = cliente.dni || ''
        email.value = cliente.email || ''
        telefono.value = cliente.telefono || ''
        direccion.value = cliente.direccion || ''
        ciudad.value = cliente.ciudad || ''
        departamento.value = cliente.departamento || ''
        codigoPostal.value = cliente.codigoPostal || ''
        observaciones.value = cliente.observaciones || ''
      }
    } catch (error) {
      console.error('Error al cargar cliente:', error)
    }
  }
})
</script>

<style scoped>
</style>
