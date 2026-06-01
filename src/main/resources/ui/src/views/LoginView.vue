<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const username = ref('')
const password = ref('')
const loading = ref(false)
const error = ref('')

const login = async () => {
  try {
    loading.value = true
    error.value = ''

    const response = await fetch(
        'http://localhost:8080/v1/api/auth/login',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            username: username.value,
            password: password.value
          })
        }
    )

    if (!response.ok) {
      throw new Error('Login failed')
    }

    const data = await response.json()

    localStorage.setItem(
        'accessToken',
        data.accessToken
    )

    localStorage.setItem(
        'refreshToken',
        data.refreshToken
    )

    await router.push('/post/3c132107-e8aa-4e64-bded-7516f336ef2a')
  } catch (err) {
    error.value = err.message
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="login-container">
    <div class="card">
      <h2>MBlog Login</h2>

      <input
          v-model="username"
          placeholder="Username"
      />

      <input
          v-model="password"
          type="password"
          placeholder="Password"
      />

      <button
          @click="login"
          :disabled="loading"
      >
        {{ loading ? 'Loading...' : 'Login' }}
      </button>

      <p v-if="error" class="error">
        {{ error }}
      </p>
    </div>
  </div>
</template>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #f5f5f5;
}

.card {
  width: 350px;
  background: white;
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0,0,0,.1);
}

h2 {
  margin-bottom: 20px;
}

input {
  width: 100%;
  padding: 10px;
  margin-bottom: 12px;
  box-sizing: border-box;
}

button {
  width: 100%;
  padding: 10px;
  cursor: pointer;
}

.error {
  color: red;
  margin-top: 10px;
}
</style>