<script setup>
import { ref, onMounted } from 'vue'
import Quill from 'quill'
import 'quill/dist/quill.snow.css'
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
    htmlContent.value =
        quill.root.innerHTML
  })
})

const savePost = async () => {
  const token =
      localStorage.getItem('accessToken')

  await fetch('http://localhost:8080/v1/api/post', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify({
      authorId: "0853befa-528c-40de-a323-d42b015f9f44",
      categoryId: "62fa556a-03ea-4112-b62d-172cf3a2b4d6",
      title: title.value,
      content: htmlContent.value,
      slug: "slug",
      excerpt: "excerpt",
      status: "PUBLISHED",
      visibility: "PUBLIC"
    })
  })
}
</script>

<template>
  <input
      v-model="title"
      placeholder="Title"
  />

  <div id="editor"></div>

  <button @click="savePost">
    Save
  </button>
</template>