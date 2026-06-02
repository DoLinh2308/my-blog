import api from '@/api/axios'

const authService = {
    register(payload) {
        return api.post('/v1/api/auth/register', payload)
    },

    login(payload) {
        return api.post('/v1/api/auth/login', payload)
    },

    refreshToken(payload) {
        return api.post('/v1/api/auth/refresh-token', payload)
    },

    logout() {
        return api.post('/v1/api/auth/logout')
    },

    getProfile() {
        return api.get('/v1/api/auth/me')
    }
}

export default authService