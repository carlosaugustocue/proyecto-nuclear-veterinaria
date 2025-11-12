<template>
  <v-card elevation="8" rounded="lg">
    <v-card-text class="pa-8">
      <!-- Logo y Título -->
      <div class="text-center mb-6">
        <v-icon size="64" color="primary">mdi-paw</v-icon>
        <h2 class="text-h4 font-weight-bold primary--text mt-2">Sistema Veterinaria</h2>
        <p class="text-subtitle-1 text-grey-darken-1 mt-2">Inicia sesión para continuar</p>
      </div>

      <!-- Formulario -->
      <v-form ref="formRef" @submit.prevent="handleLogin">
        <v-text-field
          v-model="form.email"
          label="Email"
          prepend-inner-icon="mdi-email"
          type="email"
          :rules="[rules.required, rules.email]"
          required
        ></v-text-field>

        <v-text-field
          v-model="form.password"
          label="Contraseña"
          prepend-inner-icon="mdi-lock"
          :append-inner-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
          :type="showPassword ? 'text' : 'password'"
          :rules="[rules.required]"
          @click:append-inner="showPassword = !showPassword"
          required
        ></v-text-field>

        <div class="d-flex justify-space-between align-center mb-4">
          <v-checkbox v-model="rememberMe" label="Recordarme" hide-details></v-checkbox>
          <v-btn text color="primary" size="small" @click="handleRecoverPassword">
            ¿Olvidaste tu contraseña?
          </v-btn>
        </div>

        <v-btn
          type="submit"
          color="primary"
          size="large"
          block
          :loading="loading"
          class="mb-4"
        >
          Iniciar Sesión
        </v-btn>

        <v-divider class="my-4"></v-divider>

        <div class="text-center">
          <p class="text-caption text-grey-darken-1">
            Sistema de Gestión Veterinaria v1.0
          </p>
        </div>
      </v-form>
    </v-card-text>
  </v-card>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/store/auth'
import { useAppStore } from '@/store/app'

const router = useRouter()
const authStore = useAuthStore()
const appStore = useAppStore()

const formRef = ref(null)
const loading = ref(false)
const showPassword = ref(false)
const rememberMe = ref(false)

const form = reactive({
  email: '',
  password: '',
})

const rules = {
  required: (v) => !!v || 'Este campo es requerido',
  email: (v) => /.+@.+\..+/.test(v) || 'Email inválido',
}

const handleLogin = async () => {
  const { valid } = await formRef.value.validate()

  if (!valid) return

  loading.value = true

  try {
    await authStore.login(form.email, form.password)
    appStore.showSuccess('¡Bienvenido!')
    router.push('/')
  } catch (error) {
    const message = error.response?.data?.message || 'Email o contraseña incorrectos'
    appStore.showError(message)
  } finally {
    loading.value = false
  }
}

const handleRecoverPassword = () => {
  appStore.showInfo('Funcionalidad próximamente disponible')
}
</script>

<style scoped>
.v-card {
  max-width: 100%;
}
</style>
