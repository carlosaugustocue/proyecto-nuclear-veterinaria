<template>
  <v-app>
    <v-main class="auth-background">
      <v-container class="fill-height" fluid>
        <v-row align="center" justify="center">
          <v-col cols="12" sm="8" md="6" lg="4">
            <router-view></router-view>
          </v-col>
        </v-row>
      </v-container>
    </v-main>

    <!-- Snackbar -->
    <v-snackbar
      v-model="appStore.snackbar.show"
      :color="appStore.snackbar.color"
      :timeout="appStore.snackbar.timeout"
      location="top"
    >
      {{ appStore.snackbar.message }}
      <template v-slot:actions>
        <v-btn icon="mdi-close" @click="appStore.hideSnackbar"></v-btn>
      </template>
    </v-snackbar>
  </v-app>
</template>

<script setup>
import { useAppStore } from '@/store/app'

const appStore = useAppStore()
</script>

<style scoped>
.auth-background {
  background: linear-gradient(135deg, #003f70 0%, #4a90e2 100%);
}
</style>
