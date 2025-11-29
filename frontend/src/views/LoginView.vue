<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center" class="fill-height">
      <v-col cols="12" sm="8" md="6" lg="4">
        <v-card class="elevation-12">
          <v-card-title class="bg-primary text-white py-4 text-center">
            <h1>Sistema Veterinaria</h1>
          </v-card-title>

          <v-card-text class="pa-6">
            <v-form @submit.prevent="handleLogin" ref="form">
              <v-text-field
                v-model="email"
                label="Correo Electrónico"
                type="email"
                prepend-icon="mdi-account"
                :rules="emailRules"
                required
                class="mb-4"
              ></v-text-field>

              <v-text-field
                v-model="password"
                label="Contraseña"
                type="password"
                prepend-icon="mdi-lock"
                :rules="passwordRules"
                required
                class="mb-2"
              ></v-text-field>

              <v-alert
                v-if="errorMessage"
                type="error"
                class="mb-4"
                closable
              >
                {{ errorMessage }}
              </v-alert>

              <v-btn
                block
                color="primary"
                class="my-2"
                :loading="loading"
                type="submit"
              >
                Iniciar Sesión
              </v-btn>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'

const router = useRouter()
const authStore = useAuthStore()

const email = ref('')
const password = ref('')
const loading = ref(false)
const errorMessage = ref('')

const emailRules = [
  (v) => !!v || 'El correo es requerido',
  (v) => /.+@.+\..+/.test(v) || 'El correo debe ser válido',
]

const passwordRules = [
  (v) => !!v || 'La contraseña es requerida',
  (v) => v.length >= 6 || 'La contraseña debe tener al menos 6 caracteres',
]

const handleLogin = async () => {
  loading.value = true
  errorMessage.value = ''

  try {
    const result = await authStore.login(email.value, password.value)

    if (result.success) {
      console.log('Login exitoso, redirigiendo a dashboard')
      router.push('/')
    } else {
      errorMessage.value = result.error || 'Error en la autenticación'
    }
  } catch (error) {
    console.error('Error al hacer login:', error)
    errorMessage.value = error.message || 'Error al conectar con el servidor'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
</style>
