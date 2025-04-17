<template>
  <Header/>
  <NavBar/>
  <!-- 폼 전체 감싸는 컨테이너 -->
  <div class="form-wrapper">
    <h2 class="title">여행지 수정</h2>

    <!-- 수정 폼. 기본 제출 이벤트 막고 submitForm() 메서드 실행 -->
    <form @submit.prevent="submitForm" class="form-container">

      <!-- 각 입력 필드들은 form 객체와 양방향 바인딩 (v-model) -->
      <textarea v-model="form.title" placeholder="여행 패키지 명" required></textarea>
      <input v-model="form.description" placeholder="내용" />
      <input v-model="form.price" type="number" placeholder="가격 (예: 799000)" />
      <input v-model="form.date" placeholder="기간 (예: 3박 4일)" />

      <input v-model="form.imageUrl" placeholder="이미지 url주소" />

      <!-- 수정 버튼 -->
      <button type="submit" class="submit-button">수정 완료</button>
    </form>
  </div>
  <Footer/>
</template>

<script setup>
import {ref, onMounted, computed} from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import Footer from "@/components/Footer.vue";
import NavBar from "@/components/NavBar.vue";
import Header from "@/components/Header.vue";
import {useAuthStore} from "@/stores/auth.js";

const route = useRoute()
const router = useRouter()
const id = route.params.id
const authStore = useAuthStore()
const role = computed(() => authStore.role)

const form = ref({
  title: '',
  description: '',
  price: '',
  date: '',
  imageUrl: ''
})
const imagePreview = ref(null)

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
  try {
    const res = await axios.get(`/api/admins/destination/external-packages/${id}`)
    form.value = res.data

    if (form.value.imageUrl?.startsWith('data:image')) {
      imagePreview.value = form.value.imageUrl
    }
  } catch (err) {
    console.error('수정 대상 조회 실패:', err)
    alert('해당 데이터를 불러올 수 없습니다.')
    router.push('/destination')
  }
})


const submitForm = async () => {
  try {
    await axios.put(`/api/admins/destination/external-packages/${id}`, form.value)
    alert('수정 완료!')
    router.push('/destination')
  } catch (err) {
    console.error('수정 실패:', err)
    alert('수정 중 오류가 발생했습니다.')
  }
}
</script>



<style scoped>
/* 전체 폼 외관 스타일 */
.form-wrapper {
  max-width: 600px;
  margin: 40px auto;
  padding: 30px;
  background: #f9f9f9;
  border-radius: 16px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.05);
}

/* 제목 스타일 */
.title {
  text-align: center;
  margin-bottom: 20px;
  font-size: 24px;
  color: #2c3e50;
  font-weight: bold;
}

/* 입력 필드 공통 스타일 */
.form-container input,
.form-container textarea {
  width: 100%;
  padding: 12px;
  margin-bottom: 16px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 15px;
}

/* 파일 업로드 라벨 */
.file-label {
  font-weight: bold;
  margin-bottom: 6px;
  display: block;
}

/* 파일 입력 필드 스타일 */
.file-input {
  margin-bottom: 16px;
}

/* 이미지 미리보기 박스 */
.preview-box {
  margin-bottom: 16px;
  text-align: center;
}

/* 이미지 미리보기 스타일 */
.preview-image {
  max-width: 100%;
  border-radius: 10px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.1);
}

/* 제출 버튼 스타일 */
.submit-button {
  width: 100%;
  padding: 12px;
  background: #2ecc71;
  border: none;
  border-radius: 8px;
  color: white;
  font-size: 16px;
  cursor: pointer;
  transition: 0.2s;
}
.submit-button:hover {
  background: #27ae60;
}
</style>