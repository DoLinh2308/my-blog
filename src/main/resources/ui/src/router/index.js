import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'

const routes = [
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/LoginView.vue'),
        meta: {
            public: true
        }
    },
    {
        path: '/home',
        component: () => import('@/layouts/DashboardLayout.vue'),
        meta: {
            requiresAuth: true
        },
        children: [
            // {
            //     path: 'dss',
            //     name: 'Dashboard',
            //     component: () => import('@/views/DashboardView.vue')
            // },
            // {
            //     path: 'users',
            //     name: 'Users',
            //     component: () => import('@/views/UserListView.vue'),
            //     meta: {
            //         permission: 'USER_VIEW'
            //     }
            // },
            // {
            //     path: 'roles',
            //     name: 'Roles',
            //     component: () => import('@/views/RoleListView.vue'),
            //     meta: {
            //         permission: 'ROLE_VIEW'
            //     }
            // },
            {
                path: 'create-post',
                name: 'CreatePost',
                component: () => import('@/views/CreatePostView.vue')
            },
            {
                path: '/post/:id',
                name: 'PostDetail',
                component: () => import('@/views/PostDetailView.vue')
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    const authStore = useAuthStore()

    if (to.meta.public) {
        return next()
    }

    if (to.meta.requiresAuth && !authStore.isAuthenticated) {
        return next('/login')
    }

    // if (to.meta.permission && !authStore.hasPermission(to.meta.permission)) {
    //     return next('/')
    // }

    next()
})

export default router