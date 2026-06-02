import api from '@/api/axios'

const userService = {
    createPost(payload) {
        return api.post('/v1/api/post', payload)
    }
}

export default userService