<script setup>
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'
import { useUiStore } from '@/stores/uiStore'

const router = useRouter()
const authStore = useAuthStore()
const uiStore = useUiStore()

function logout() {
  authStore.logout()
  router.push('/login')
}
</script>

<template>
  <header class="navbar">
    <div class="navbar-left">
      <button class="icon-button" @click="uiStore.toggleSidebar">
        ☰
      </button>

      <h1 class="app-title">MyBlog</h1>
    </div>

    <div class="navbar-right">
      <button class="icon-button">

      </button>

      <div class="user-info">
        <span>{{ authStore.user?.username || 'User' }}</span>
      </div>

      <button class="logout-button" @click="logout">
        Logout
      </button>
    </div>
  </header>
</template>

<style scoped>
.navbar {
  height: 64px;
  background: #ffffff;
  border-bottom: 1px solid #e5e7eb;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.navbar-left,
.navbar-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.app-title {
  font-size: 20px;
  font-weight: 700;
  color: #111827;
}

.icon-button {
  border: none;
  background: transparent;
  font-size: 22px;
  cursor: pointer;
}

.user-info {
  font-size: 14px;
  color: #374151;
}

.logout-button {
  border: none;
  background: #ef4444;
  color: white;
  padding: 8px 12px;
  border-radius: 8px;
  cursor: pointer;
}
</style>