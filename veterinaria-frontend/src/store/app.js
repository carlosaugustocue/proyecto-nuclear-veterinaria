import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAppStore = defineStore('app', () => {
  // State
  const snackbar = ref({
    show: false,
    message: '',
    color: 'success',
    timeout: 3000,
  })

  const loading = ref(false)
  const drawer = ref(true) // Sidebar abierto/cerrado

  // Actions
  function showSnackbar(message, color = 'success', timeout = 3000) {
    snackbar.value = {
      show: true,
      message,
      color,
      timeout,
    }
  }

  function hideSnackbar() {
    snackbar.value.show = false
  }

  function showSuccess(message) {
    showSnackbar(message, 'success')
  }

  function showError(message) {
    showSnackbar(message, 'error', 5000)
  }

  function showWarning(message) {
    showSnackbar(message, 'warning')
  }

  function showInfo(message) {
    showSnackbar(message, 'info')
  }

  function toggleDrawer() {
    drawer.value = !drawer.value
  }

  function setLoading(value) {
    loading.value = value
  }

  return {
    // State
    snackbar,
    loading,
    drawer,
    // Actions
    showSnackbar,
    hideSnackbar,
    showSuccess,
    showError,
    showWarning,
    showInfo,
    toggleDrawer,
    setLoading,
  }
})
