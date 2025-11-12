import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import { aliases, mdi } from 'vuetify/iconsets/mdi'
import '@mdi/font/css/materialdesignicons.css'

// Tema personalizado de la veterinaria
const veterinariaTheme = {
  dark: false,
  colors: {
    primary: '#003f70',       // Azul principal veterinaria
    secondary: '#4a90e2',     // Azul claro
    accent: '#00bfa5',        // Verde accent
    error: '#e53935',         // Rojo alerta
    warning: '#FFC107',       // Amarillo warning
    info: '#2196F3',          // Azul info
    success: '#00bfa5',       // Verde éxito
    background: '#f5f5f5',    // Gris claro fondo
    surface: '#ffffff',       // Blanco superficie
    'on-primary': '#ffffff',
    'on-secondary': '#ffffff',
    'grey-dark': '#424242',   // Gris oscuro
  },
}

export default createVuetify({
  components,
  directives,
  icons: {
    defaultSet: 'mdi',
    aliases,
    sets: {
      mdi,
    },
  },
  theme: {
    defaultTheme: 'veterinariaTheme',
    themes: {
      veterinariaTheme,
    },
  },
  defaults: {
    VBtn: {
      elevation: 2,
      rounded: 'md',
    },
    VCard: {
      elevation: 2,
      rounded: 'lg',
    },
    VTextField: {
      variant: 'outlined',
      density: 'comfortable',
      color: 'primary',
    },
    VSelect: {
      variant: 'outlined',
      density: 'comfortable',
      color: 'primary',
    },
    VTextarea: {
      variant: 'outlined',
      density: 'comfortable',
      color: 'primary',
    },
    VDataTable: {
      hover: true,
    },
  },
})
