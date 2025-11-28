import { ref } from 'vue'

// Global notification state
const snackbar = ref({
  show: false,
  message: '',
  color: 'success',
  timeout: 3000,
})

export function useNotification() {
  const showNotification = (message, type = 'success', timeout = 3000) => {
    const colors = {
      success: 'success',
      error: 'error',
      warning: 'warning',
      info: 'info',
    }

    snackbar.value = {
      show: true,
      message,
      color: colors[type] || 'info',
      timeout,
    }
  }

  const showSuccess = (message, timeout = 3000) => {
    showNotification(message, 'success', timeout)
  }

  const showError = (message, timeout = 5000) => {
    showNotification(message, 'error', timeout)
  }

  const showWarning = (message, timeout = 4000) => {
    showNotification(message, 'warning', timeout)
  }

  const showInfo = (message, timeout = 3000) => {
    showNotification(message, 'info', timeout)
  }

  const hideNotification = () => {
    snackbar.value.show = false
  }

  return {
    snackbar,
    showNotification,
    showSuccess,
    showError,
    showWarning,
    showInfo,
    hideNotification,
  }
}
