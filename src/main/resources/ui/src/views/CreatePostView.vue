<script setup>
import { ref, onMounted } from 'vue'
import Quill from 'quill'
import 'quill/dist/quill.snow.css'
import postService from "@/services/postService.js";

const title = ref('')
const htmlContent = ref('')

let quill

onMounted(() => {
  quill = new Quill('#editor', {
    theme: 'snow',
    modules: {
      toolbar: [
        [{ header: [1, 2, 3, false] }],
        ['bold', 'italic', 'underline'],
        ['link', 'image'],
        [{ list: 'ordered' }, { list: 'bullet' }],
        ['code-block'],
        ['clean']
      ]
    }
  })

  quill.on('text-change', () => {
    htmlContent.value = quill.root.innerHTML
  })
})
const savePost = async () => {
  try {
    const response = await postService.createPost({
      authorId: "0853befa-528c-40de-a323-d42b015f9f44",
      categoryId: "62fa556a-03ea-4112-b62d-172cf3a2b4d6",
      title: title.value,
      content: htmlContent.value,
      slug: "slugggs",
      excerpt: "excerpttts",
      status: "PUBLISHED",
      visibility: "PUBLIC"
    })

    if (response.status === 200 || response.status === 201) {

      // hiện popup
      showSuccess.value = true

      // clear dữ liệu
      title.value = ''
      htmlContent.value = ''
      quill.setContents([])

      // tự ẩn sau 3 giây
      setTimeout(() => {
        showSuccess.value = false
      }, 3000)
    }

  } catch (error) {
    console.error(error)
    alert('Đăng bài thất bại!')
  }
}
</script>

<template>
  <div class="editor-page">
    <div
        v-if="showSuccess"
        class="success-popup"
    >
      ✅ Post published successfully!
    </div>
    <div class="editor-header">
      <h1>Create New Post</h1>

      <button
          class="save-btn"
          @click="savePost"
      >
        Publish
      </button>
    </div>

    <div class="editor-card">

      <input
          v-model="title"
          class="title-input"
          placeholder="Enter your title..."
      />

      <div id="editor"></div>

    </div>

  </div>
</template>

<style scoped>
.editor-page {
  max-width: 1100px;
  margin: 0 auto;
  padding: 24px;
}

.editor-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.editor-header h1 {
  font-size: 28px;
  font-weight: 700;
  color: #111827;
}

.save-btn {
  border: none;
  background: #2563eb;
  color: white;
  padding: 12px 22px;
  border-radius: 10px;
  font-weight: 600;
  cursor: pointer;
}

.save-btn:hover {
  opacity: 0.9;
}

.editor-card {
  background: white;
  border-radius: 16px;
  padding: 24px;
  box-shadow:
      0 4px 12px rgba(0,0,0,0.06);
}

.title-input {
  width: 100%;
  border: none;
  outline: none;
  font-size: 40px;
  font-weight: 700;
  margin-bottom: 24px;
  color: #111827;
}

.title-input::placeholder {
  color: #9ca3af;
}

:deep(.ql-toolbar) {
  border-radius: 10px 10px 0 0;
}

:deep(.ql-container) {
  min-height: 500px;
  font-size: 16px;
}

:deep(.ql-editor) {
  min-height: 500px;
  line-height: 1.8;
}
.success-popup {
  position: fixed;
  top: 20px;
  right: 20px;
  background: #22c55e;
  color: white;
  padding: 14px 20px;
  border-radius: 10px;
  font-weight: 600;
  box-shadow: 0 4px 12px rgba(0,0,0,.15);
  z-index: 9999;
}
</style>