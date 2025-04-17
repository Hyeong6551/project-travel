import { defineStore } from 'pinia'
import axios from 'axios'

interface AuthState {
  token: string | null
  id: string | null
  role: string | null
}

export const useAuthStore = defineStore('auth', {
  state: (): AuthState => ({
    token: localStorage.getItem('token'),
    id: localStorage.getItem('id'),
    role: localStorage.getItem('role')
  }),

  getters: {
    isAuthenticated: (state) => !!state.token,
    isAdmin: (state) => state.role === 'ADMIN'
  },

  actions: {
    setAuth(auth: { isAuthenticated: boolean; id: string; role: string }) {
      this.token = auth.isAuthenticated ? localStorage.getItem('token') : null
      this.id = auth.id
      this.role = auth.role
    },

    login(token: string, id: string, role: string) {
      this.token = token
      this.id = id
      this.role = role
      localStorage.setItem('token', token)
      localStorage.setItem('id', id)
      localStorage.setItem('role', role)
    },

    async loginWithCredentials(id: string, password: string) {
      try {
        const response = await axios.post('/api/auth/signin', { id, password })
        const { token, role } = response.data
        this.login(token, id, role)
        return true
      } catch (error) {
        console.error('Login failed:', error)
        throw error
      }
    },

    logout() {
      this.token = null
      this.id = null
      this.role = null
      localStorage.removeItem('token')
      localStorage.removeItem('id')
      localStorage.removeItem('role')
    }
  }
}) 