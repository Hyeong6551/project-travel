<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import NavBar from "@/components/NavBar.vue";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import { useAuthStore } from '@/stores/auth'

const route = useRoute()
const router = useRouter()
const post = ref({ title: '', content: '', id: 0, createdAt: new Date().toISOString() })
const authStore = useAuthStore()
const isAdmin = computed(() => authStore.role === 'ADMIN')

const loadPost = async () => {
  const res = await axios.get(`/api/posts/${route.params.id}`)
  post.value = res.data
}

const remove = async () => {
  if (confirm('정말 삭제하시겠습니까?')) {
    await axios.delete(`/api/posts/${route.params.id}`)
    alert('삭제되었습니다')
    router.push('/board')
  }
}

onMounted(() => loadPost())
</script>

<template>
  <!-- ✅ 헤더 -->
  <Header/>
  <NavBar/>
  <div class="container my-5">
    <div class="card shadow-sm">
      <div class="card-header bg-light py-3">
        <h2 class="mb-0 fw-bold">{{ post.title }}</h2>
        <div class="d-flex justify-content-between align-items-center mt-2">
          <span class="text-muted small">
            <i class="bi bi-calendar-date me-1"></i>
            {{ new Date(post.createdAt).toLocaleString() }}
          </span>
        </div>
      </div>
      <div class="card-body p-4">
        <div class="content-area" v-html="post.content"></div>
      </div>
      <div class="card-footer bg-white border-top-0 pb-3 px-4" v-if="isAdmin">
        <div class="d-flex justify-content-end">
          <router-link :to="`/board/edit/${post.id}`" class="btn btn-outline-primary">
            <i class="bi bi-pencil-square me-1"></i>수정
          </router-link>
          <button @click="remove" class="btn btn-outline-danger ms-2">
            <i class="bi bi-trash me-1"></i>삭제
          </button>
        </div>
      </div>
    </div>
    <div class="d-flex justify-content-center mt-4">
      <router-link to="/board" class="btn btn-outline-secondary px-4">
        <i class="bi bi-list me-1"></i>목록으로
      </router-link>
    </div>
  </div>
  <Footer/>
</template>

<style scoped>
.content-area {
  min-height: 300px;
  line-height: 1.8;
}

.card {
  border-radius: 8px;
  border: 1px solid rgba(0, 0, 0, 0.1);
}

.card-header {
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
}

h2 {
  color: #333;
  font-size: 1.5rem;
}

.btn {
  border-radius: 5px;
  font-weight: 500;
  transition: all 0.3s ease;
}

.btn:hover {
  transform: translateY(-2px);
}
</style>
