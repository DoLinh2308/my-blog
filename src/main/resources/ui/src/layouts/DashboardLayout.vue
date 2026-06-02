<script setup>
import Navbar from '@/components/layout/Navbar.vue'
import Sidebar from '@/components/layout/Sidebar.vue'
import {useRoute} from "vue-router";
const route = useRoute()

</script>
<template>
  <div class="dashboard-layout">
    <Sidebar />

    <div class="layout-right">
      <Navbar />

      <main class="layout-content">
        <RouterView />
      </main>
    </div>
  </div>
</template>

<style scoped>
.dashboard-layout {
  min-height: 90vh;
  display: flex;
  background: #f3f4f6;
}

.layout-right {
  flex: 1;
  min-width: 0;
  display: flex;
  flex-direction: column;
}

.layout-content {
  flex: 1;
  padding: 24px;
  overflow: auto;
}
</style>