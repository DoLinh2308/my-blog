<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'
import { useUiStore } from '@/stores/uiStore'

const route = useRoute()
const authStore = useAuthStore()
const uiStore = useUiStore()

const menus = [
  {
    label: 'New Post',
    path: '/home/create-post',
    icon: ''
  },
  {
    label: 'Users',
    path: '/users',
    icon: '',
    permission: 'USER_VIEW'
  },
  {
    label: 'Roles',
    path: '/roles',
    icon: '',
    permission: 'ROLE_VIEW'
  },
  {
    label: 'Warehouse',
    path: '/warehouses',
    icon: '',
    permission: 'WAREHOUSE_VIEW'
  },
  {
    label: 'Inventory',
    path: '/inventory',
    icon: '',
    permission: 'INVENTORY_VIEW'
  }
]

const visibleMenus = computed(() => {
  return menus.filter((menu) => {
    if (!menu.permission) {
      return true
    }

    return authStore.hasPermission(menu.permission)
  })
})

function isActive(path) {
  return route.path === path
}
</script>

<template>
  <aside class="sidebar" :class="{ collapsed: !uiStore.sidebarOpen }">
    <div class="sidebar-logo">
      <span v-if="uiStore.sidebarOpen">MyBlog</span>
      <span v-else>W</span>
    </div>

    <nav class="sidebar-menu">
      <RouterLink
          v-for="menu in visibleMenus"
          :key="menu.path"
          :to="menu.path"
          class="menu-item"
          :class="{ active: isActive(menu.path) }"
      >
        <span class="menu-icon">{{ menu.icon }}</span>
        <span v-if="uiStore.sidebarOpen" class="menu-label">
          {{ menu.label }}
        </span>
      </RouterLink>
    </nav>
  </aside>
</template>

<style scoped>
.sidebar {
  width: 260px;
  min-height: 100vh;
  background: #111827;
  color: white;
  transition: width 0.2s ease;
}

.sidebar.collapsed {
  width: 80px;
}

.sidebar-logo {
  height: 64px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
  font-weight: 700;
  border-bottom: 1px solid #374151;
}

.sidebar-menu {
  padding: 16px 12px;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.menu-item {
  display: flex;
  align-items: center;
  gap: 12px;
  color: #d1d5db;
  text-decoration: none;
  padding: 12px 14px;
  border-radius: 10px;
  transition: background 0.2s ease;
}

.menu-item:hover {
  background: #1f2937;
}

.menu-item.active {
  background: #2563eb;
  color: white;
}

.menu-icon {
  font-size: 20px;
}

.menu-label {
  font-size: 15px;
  font-weight: 500;
}
</style>