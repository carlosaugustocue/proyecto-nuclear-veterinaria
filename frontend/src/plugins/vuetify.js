import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import { aliases, mdi } from 'vuetify/lib/iconsets/mdi'

export default createVuetify({
  components,
  directives,

  theme: {
    defaultTheme: 'humboldt',
    themes: {
      humboldt: {
        dark: false,
        colors: {
          primary: '#083E6B',      // Azul institucional oscuro
          secondary: '#0C5A96',    // Azul medio corporativo
          accent: '#1C78BF',       // Azul claro (acciones suaves)
          
          background: '#F2F6FA',   // Fondo limpio y moderno
          surface: '#FFFFFF',      // Formularios, cards

          // Colores corporativos alternos (NO verdes ni amarillos)
          success: '#0C5A96',      // Azul medio (feedback positivo)
          info: '#1C78BF',         // Azul claro
          warning: '#0C5A96',      // Aviso elegante sin estridencias
          error: '#B71C1C',        // Rojo oscuro profesional
        },
      },
    },
  },

  icons: {
    defaultSet: 'mdi',
    aliases,
    sets: { mdi },
  },
})
