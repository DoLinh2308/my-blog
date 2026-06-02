import axios from 'axios'

const api = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 15000,
    headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json'
    }
})

api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('accessToken')

        if (token) {
            config.headers.Authorization = `Bearer ${token}`
        }

        return config
    },
    (error) => Promise.reject(error)
)

api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (!error.response) {
            console.error('Không thể kết nối server')
            return Promise.reject(error)
        }

        const status = error.response.status

        if (status === 401) {
            localStorage.removeItem('accessToken')
            localStorage.removeItem('refreshToken')
            localStorage.removeItem('authUser')
            window.location.href = '/login'
        }

        if (status === 403) {
            console.error('Forbidden - không đủ quyền')
        }

        if (status >= 500) {
            console.error('Internal Server Error')
        }

        return Promise.reject(error)
    }
)

export default api