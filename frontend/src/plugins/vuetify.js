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
          // Colores principales según paleta
          primary: '#0A74B7',      // Azul Institucional
          secondary: '#0C5A96',    // Azul Medio
          accent: '#1C78BF',       // Azul Claro
          
          // Colores de fondo y superficie
          background: '#F4F6F8',   // Gris Claro
          surface: '#FFFFFF',      // Blanco
          
          // Colores de estado y feedback
          success: '#0C5A96',      // Azul Medio (feedback positivo)
          info: '#1C78BF',         // Azul Claro
          warning: '#F57C4F',      // Naranja (Acción Destacada)
          error: '#B71C1C',        // Rojo oscuro profesional
          
          // Colores adicionales de la paleta
          'primary-dark': '#083E6B',  // Azul Profundo (variante oscura)
          'grey-light': '#F4F6F8',    // Gris Claro
          'grey-medium': '#E0E3E6',   // Gris Medio
          'action-highlight': '#F57C4F', // Naranja (Acción Destacada)
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
