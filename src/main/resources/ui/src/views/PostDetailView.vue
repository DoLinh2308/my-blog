<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const post = ref(null)
const loading = ref(true)
const error = ref(null)

const fetchPost = async () => {
  try {
    const postId = route.params.id
    const res = await fetch(`http://localhost:8080/v1/api/post/post-detail/${postId}`, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('accessToken')}`
      }
    })
    if (!res.ok) throw new Error('Không thể tải bài viết')
    post.value = await res.json()
  } catch (err) {
    error.value = err.message
  } finally {
    loading.value = false
  }
}

onMounted(fetchPost)
</script>

<template>
  <div class="post-detail">
    <div v-if="loading" class="status">Đang tải...</div>
    <div v-else-if="error" class="status error">{{ error }}</div>
    <article v-else>
      <h1>{{ post.title }}</h1>
      <div class="meta">
        <span v-if="post.categoryName">Danh mục: {{ post.categoryName }}</span>
        <span>Tác giả: {{ post.authorName }}</span>
        <span>Ngày đăng: {{ new Date(post.createdAt).toLocaleDateString() }}</span>
      </div>
      <div class="content" v-html="post.content"></div>
    </article>
  </div>
</template>

<style scoped>
.post-detail {
  max-width: 800px;
  margin: 2rem auto;
  padding: 1rem;
}

.status {
  text-align: center;
  padding: 2rem;
  color: #666;
}

.error {
  color: red;
}

h1 {
  font-size: 2rem;
  margin-bottom: 0.5rem;
}

.meta {
  display: flex;
  gap: 1rem;
  color: #888;
  font-size: 0.9rem;
  margin-bottom: 2rem;
  border-bottom: 1px solid #eee;
  padding-bottom: 1rem;
}

.content {
  line-height: 1.8;
  /* Giữ style cho Quill HTML */
}
</style>