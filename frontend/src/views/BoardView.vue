<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import axios from 'axios'
import { useRouter, useRoute } from 'vue-router'
import NavBar from "@/components/NavBar.vue";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import { useAuthStore } from '@/stores/auth'

const posts = ref([])
const router = useRouter()
const route = useRoute()
const authStore = useAuthStore()
const isAdmin = computed(() => authStore.role === 'ADMIN')

// 페이징 관련 상태
const currentPage = ref(1)
const itemsPerPage = 10
const totalItems = ref(0)
const totalPages = computed(() => Math.ceil(totalItems.value / itemsPerPage))

// URL에서 페이지 파라미터 가져오기
onMounted(() => {
  const pageParam = route.query.page
  if (pageParam) {
    currentPage.value = parseInt(pageParam as string) || 1
  }
  loadPosts()
})

const loadPosts = async () => {
  try {
    // 페이지 정보를 포함한 API 요청
    const res = await axios.get('/api/posts', {
      params: {
        page: currentPage.value,
        size: itemsPerPage,
        sort: 'id,desc' // ID 내림차순 정렬
      }
    })
    
    // 페이징 서버 응답이 있는 경우
    if (res.data.content) {
      posts.value = res.data.content
      totalItems.value = res.data.totalElements
    } else {
      // 서버에서 단순 배열로 응답하는 경우
      posts.value = res.data.sort((a, b) => b.id - a.id) // 클라이언트 측에서 정렬
      totalItems.value = res.data.length
    }
  } catch (err) {
    console.error('목록 불러오기 실패:', err)
  }
}

const changePage = (page: number) => {
  currentPage.value = page
  router.push({ query: { ...route.query, page: page.toString() } })
  loadPosts()
}

const goDetail = (id: number) => {
  router.push(`/board/${id}`)
}
</script>

<template>
  <!-- ✅ 헤더 -->
  <Header/>
  <NavBar/>
  <div class="container mt-5">
    <h2>📋 공지사항</h2>
    <router-link v-if="isAdmin" to="/board/write" class="btn btn-success mb-3">글쓰기</router-link>
    <table class="table">
      <thead>
      <tr>
        <th>번호</th>
        <th>제목</th>
        <th>작성자</th>
        <th>작성일</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="post in posts" :key="post.id" @click="goDetail(post.id)" style="cursor: pointer">
        <td>{{ post.id }}</td>
        <td>{{ post.title }}</td>
        <td>{{ post.writer }}</td>
        <td>{{ new Date(post.createdAt).toLocaleString() }}</td>
      </tr>
      </tbody>
    </table>
    
    <!-- 페이징 컴포넌트 -->
    <div class="pagination-container" v-if="totalPages > 1">
      <ul class="pagination">
        <li class="page-item" :class="{ disabled: currentPage === 1 }">
          <a class="page-link" href="#" @click.prevent="changePage(currentPage - 1)">이전</a>
        </li>
        <li class="page-item" v-for="page in totalPages" :key="page" :class="{ active: page === currentPage }">
          <a class="page-link" href="#" @click.prevent="changePage(page)">{{ page }}</a>
        </li>
        <li class="page-item" :class="{ disabled: currentPage === totalPages }">
          <a class="page-link" href="#" @click.prevent="changePage(currentPage + 1)">다음</a>
        </li>
      </ul>
    </div>
  </div>
  <Footer/>
</template>
<style scoped>
.container {
  max-width: 1000px;
  margin: 0 auto;
  padding: 40px 20px;
  background-color: #ffffff;
  border-radius: 16px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.05);
}

h2 {
  font-size: 28px;
  font-weight: 700;
  color: #2c3e50;
  margin-bottom: 30px;
}

.btn-success {
  background-color: #28a745;
  border: none;
  font-weight: 500;
}

.btn-success:hover {
  background-color: #218838;
}

.table {
  width: 100%;
  border-collapse: collapse;
  background-color: #fafafa;
}

.table thead {
  background-color: #e9ecef;
  text-align: center;
}

.table th, .table td {
  padding: 14px;
  text-align: center;
  border-bottom: 1px solid #dee2e6;
  font-size: 15px;
}

.table tbody tr:hover {
  background-color: #f1f3f5;
}

@media screen and (max-width: 768px) {
  .table th:nth-child(1),
  .table td:nth-child(1),
  .table th:nth-child(4),
  .table td:nth-child(4) {
    display: none;
  }

  h2 {
    font-size: 22px;
  }
}
.table thead th {
  background-color: #f8f9fa; /* 연한 회색 배경 */
  color: #495057; /* 다소 진한 회색 텍스트 */
  font-weight: bold;
  font-size: 15px;
}

.pagination-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.pagination {
  display: flex;
  list-style: none;
  padding: 0;
  margin: 0;
}

.page-item {
  margin: 0 5px;
}

.page-link {
  display: block;
  padding: 8px 12px;
  border-radius: 4px;
  border: 1px solid #dee2e6;
  color: #028267;
  text-decoration: none;
  transition: all 0.2s;
}

.page-item.active .page-link {
  background-color: #028267;
  color: white;
  border-color: #028267;
}

.page-item.disabled .page-link {
  color: #aaa;
  pointer-events: none;
  cursor: default;
}

.page-link:hover {
  background-color: #e9ecef;
}
</style>

