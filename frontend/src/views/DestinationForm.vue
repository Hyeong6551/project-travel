<template>
  <Header/>
  <NavBar/>
  <div class="container">
    <h2 class="form-title">여행 패키지 등록</h2>
    <div class="form-container">
      <div class="form-group">
        <label>여행지 이름</label>
        <input v-model="form.title" placeholder="예: 제주도 올레길 탐방" />
      </div>
      <div class="form-group">
        <label>설명</label>
        <textarea v-model="form.description" placeholder="여행에 대한 간략한 설명을 입력하세요" rows="3"></textarea>
      </div>
      <div class="form-group">
        <label>가격</label>
        <input v-model="form.price" type="number" placeholder="숫자만 입력 (예: 799000)" />
      </div>
      <div class="form-group">
        <label>기간</label>
        <input v-model="form.date" placeholder="예: 3박 4일" />
      </div>
      <div class="form-group">
        <label>이미지 URL</label>
        <input v-model="form.imageUrl" placeholder="이미지 URL을 입력하세요" />
      </div>
      <div class="preview-image" v-if="form.imageUrl">
        <p>이미지 미리보기:</p>
        <img :src="form.imageUrl" alt="미리보기" />
      </div>
      <div class="button-group">
        <button class="cancel-button" @click="$router.push('/destination')">취소</button>
        <button class="submit-button" @click="submitForm">저장하기</button>
      </div>
    </div>
  </div>
  <Footer/>
</template>


<script setup>
import {computed, onMounted, ref} from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import NavBar from "@/components/NavBar.vue";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
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
const submitForm = async () => {
  try {
    const response = await axios.post('/api/admins/destination/external-packages', form.value);
    const newId = response.data; // 응답받은 패키지 ID
    alert('패키지가 등록되었습니다.');
    router.push(`/admin/destination/${newId}/detail`);
  } catch (error) {
    console.error('패키지 등록 실패:', error);
    alert('등록 중 오류가 발생했습니다.');
  }
}

</script>


<style scoped>
.container {
  max-width: 800px;
  margin: 40px auto;
  padding: 30px;
  background: #ffffff;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.form-title {
  text-align: center;
  font-size: 28px;
  margin-bottom: 30px;
  color: #2c3e50;
  font-weight: bold;
  position: relative;
}

.form-title::after {
  content: '';
  display: block;
  width: 50px;
  height: 3px;
  background-color: #4CAF50;
  margin: 12px auto;
}

.form-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 5px;
}

.form-group label {
  font-weight: 600;
  margin-bottom: 8px;
  color: #333;
  font-size: 16px;
}

/* input과 textarea 공통 스타일 */
.form-container input,
.form-container textarea {
  width: 100%;
  padding: 14px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 16px;
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

.form-container input:focus,
.form-container textarea:focus {
  outline: none;
  border-color: #4CAF50;
  box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.2);
}

.preview-image {
  margin-top: 15px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.preview-image p {
  font-weight: 600;
  margin-bottom: 10px;
  color: #555;
}

.preview-image img {
  max-width: 100%;
  max-height: 300px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  object-fit: cover;
}

.button-group {
  display: flex;
  justify-content: space-between;
  margin-top: 30px;
}

.submit-button {
  padding: 12px 30px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
}

.submit-button:hover {
  background-color: #3d8b40;
  transform: scale(1.05);
}

.cancel-button {
  padding: 12px 30px;
  background-color: #e0e0e0;
  color: #333;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.cancel-button:hover {
  background-color: #c0c0c0;
}
</style>