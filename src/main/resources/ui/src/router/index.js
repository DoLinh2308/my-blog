import { createRouter, createWebHistory } from 'vue-router'

import LoginView from '@/views/LoginView.vue'
import CreatePostView from '@/views/CreatePostView.vue'

const routes = [
    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/login',
        component: LoginView
    },
    {
        path: '/dashboard',
        component: CreatePostView
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router