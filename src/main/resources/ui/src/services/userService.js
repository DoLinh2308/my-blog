import api from '@/api/axios'

const userService = {
    getUsers(params) {
        return api.get('/v1/api/users', { params })
    },

    getUserById(id) {
        return api.get(`/api/users/${id}`)
    },

    createUser(payload) {
        return api.post('/api/users', payload)
    },

    updateUser(id, payload) {
        return api.put(`/api/users/${id}`, payload)
    },

    deleteUser(id) {
        return api.delete(`/api/users/${id}`)
    }
}

export default userService