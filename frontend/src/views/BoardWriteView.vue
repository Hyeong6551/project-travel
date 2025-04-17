<script setup lang="ts">
import {ref, onMounted, computed} from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import Footer from "@/components/Footer.vue";
import NavBar from "@/components/NavBar.vue";
import Header from "@/components/Header.vue";
import {useAuthStore} from "@/stores/auth";

const form = ref({ title: '', content: '',writer: localStorage.getItem('id') || 'anonymous' })
const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()
const role = computed(() => authStore.role)
const isEdit = route.path.includes('edit')
onMounted(async () => {
  const token = localStorage.getItem('token')
  if (token) {
    authStore.setAuth({
      isAuthenticated: true,
      id: localStorage.getItem('id') || '',
      role: localStorage.getItem('role') || ''
    })
  }
  if (role.value !== 'ADMIN') {
    alert('접근 권한이 없습니다.')
    router.push('/')
  }
})
const loadPost = async () => {
  if (isEdit) {
    const res = await axios.get(`/api/posts/${route.params.id}`)
    form.value = res.data
  }
}

const save = async () => {
  try {
    if (isEdit) {
      await axios.put(`/api/posts/${route.params.id}`, form.value)
    } else {
      await axios.post('/api/posts', form.value)
    }
    alert('저장되었습니다')
    await router.push('/board')
  } catch (err) {
    console.error(err)
  }
}

onMounted(() => loadPost())
</script>

<template>
  <!-- ✅ 헤더 -->
  <Header/>
  <NavBar/>
  <div class="container mt-5">
    <h2>{{ isEdit ? '게시글 수정' : '게시글 작성' }}</h2>
    <input v-model="form.title" class="form-control mb-3" placeholder="제목" />
    <textarea v-model="form.content" class="form-control mb-3" rows="10" placeholder="내용"></textarea>
    <button @click="save" class="btn btn-success">저장</button>
  </div>
  <Footer/>
</template>
