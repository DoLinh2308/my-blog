import { createRouter, createWebHistory } from 'vue-router'

import LoginView from '@/views/LoginView.vue'
import CreatePostView from '@/views/CreatePostView.vue'

const routes = [
    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/home',
        component: () => import('@/layouts/DashboardLayout.vue')
    },
    {
        path: '/login',
        component: LoginView
    },
    {
        path: '/create-post',
        component: CreatePostView
    },
    {
        path: '/post/:id',
        name: 'PostDetail',
        component: () => import('@/views/PostDetailView.vue')
    }
]

export default createRouter11({
    history: createWebHistory(),
    routes
})