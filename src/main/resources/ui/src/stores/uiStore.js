import { defineStore } from 'pinia'

export const useUiStore = defineStore('ui', {
    state: () => ({
        sidebarOpen: true,
        mobileSidebarOpen: false,
        theme: localStorage.getItem('theme') || 'light'
    }),

    actions: {
        toggleSidebar() {
            this.sidebarOpen = !this.sidebarOpen
        },

        openMobileSidebar() {
            this.mobileSidebarOpen = true
        },

        closeMobileSidebar() {
            this.mobileSidebarOpen = false
        },

        toggleTheme() {
            this.theme = this.theme === 'light' ? 'dark' : 'light'
            localStorage.setItem('theme', this.theme)
        }
    }
})