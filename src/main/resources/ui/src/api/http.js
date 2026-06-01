import axios from 'axios'

const http = axios.create({
    baseURL: 'http://localhost:8080/v1/api'
})

http.interceptors.request.use(config => {
    const token =
        localStorage.getItem('accessToken')

    if (token) {
        config.headers.Authorization =
            `Bearer ${token}`
    }

    return config
})

export default http