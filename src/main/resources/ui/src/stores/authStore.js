import { defineStore } from 'pinia'
import authService from '@/services/authService'

export const useAuthStore = defineStore('auth', {
    state: () => ({
        user: JSON.parse(localStorage.getItem('authUser') || 'null'),
        accessToken: localStorage.getItem('accessToken'),
        refreshToken: localStorage.getItem('refreshToken'),
        permissions: JSON.parse(localStorage.getItem('permissions') || '[]'),
        roles: JSON.parse(localStorage.getItem('roles') || '[]'),
        loading: false
    }),

    getters: {
        isAuthenticated: (state) => !!state.accessToken,

        hasRole: (state) => {
            return (role) => state.roles.includes(role)
        },

        hasPermission: (state) => {
            return (permission) => state.permissions.includes(permission)
        }
    },

    actions: {
        async login(payload) {
            this.loading = true

            try {
                const response = await authService.login(payload)
                const data = response.data

                this.user = data.user
                this.accessToken = data.accessToken
                this.refreshToken = data.refreshToken
                this.roles = data.roles || []
                // this.permissions = data.permissions || []

                localStorage.setItem('authUser', JSON.stringify(this.user))
                localStorage.setItem('accessToken', this.accessToken)
                localStorage.setItem('refreshToken', this.refreshToken)
                localStorage.setItem('roles', JSON.stringify(this.roles))
                localStorage.setItem('permissions', JSON.stringify(this.permissions))
            } finally {
                this.loading = false
            }
        },

        logout() {
            this.user = null
            this.accessToken = null
            this.refreshToken = null
            this.roles = []
            this.permissions = []

            localStorage.removeItem('authUser')
            localStorage.removeItem('accessToken')
            localStorage.removeItem('refreshToken')
            localStorage.removeItem('roles')
            localStorage.removeItem('permissions')
        }
    }
})