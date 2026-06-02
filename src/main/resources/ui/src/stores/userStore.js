import { defineStore } from 'pinia'
import userService from '@/services/userService'

export const useUserStore = defineStore('user', {
    state: () => ({
        users: [],
        loading: false,
        error: null,
        page: 0,
        size: 10,
        totalPages: 0,
        totalElements: 0,
        keyword: ''
    }),

    actions: {
        async fetchUsers() {
            this.loading = true
            this.error = null

            try {
                const response = await userService.getUsers({
                    page: this.page,
                    size: this.size,
                    keyword: this.keyword,
                    sort: 'createdAt,desc'
                })

                this.users = response.data.content
                this.totalPages = response.data.totalPages
                this.totalElements = response.data.totalElements
            } catch (error) {
                this.error = error.response?.data?.message || 'Load users failed'
            } finally {
                this.loading = false
            }
        },

        setPage(page) {
            this.page = page
            this.fetchUsers()
        },

        setKeyword(keyword) {
            this.keyword = keyword
            this.page = 0
            this.fetchUsers()
        }
    }
})