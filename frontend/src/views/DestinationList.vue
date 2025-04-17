<template>
  <Header/>
  <NavBar/>
  <div class="container">
    <h2 class="admin-title">여행 관리 페이지</h2>
    <!-- 여행지 카드 전체 컨테이너 -->
    <div class="card-container">
      <!-- 여행 패키지마다 카드 생성 (v-for 반복) -->
      <div class="card" v-for="pkg in travelPackages" :key="pkg.id">

        <!-- 이미지 영역 -->
        <div class="image-wrapper">
          <img class="card-image" :src="pkg.imageUrl" alt="여행지 이미지" />
          <div class="price-badge">{{ formatPrice(pkg.price) }}</div>
        </div>

        <!-- 텍스트 및 버튼 컨텐츠 영역 -->
        <div class="card-content">
          <h3 class="card-title">{{ pkg.title }}</h3>
          <p class="description">{{ pkg.description }}</p>
          <p class="duration"><span class="icon">✈</span> {{ pkg.date }}</p>

          <div class="button-group">
            <button class="edit-button" @click="editDestination(pkg.id)">수정</button>
            <button class="delete-button" @click="deletePackage(pkg.id)">삭제</button>
          </div>
        </div>
      </div>
    </div>

    <!-- 여행지 등록 버튼 (페이지 하단에 고정) -->
    <div class="action-buttons">
      <button class="add-button" @click="goToRegister">
        <span class="icon">+</span> 여행지 등록
      </button>
      <button class="back-button" @click="$router.push('/members')">회원 관리</button>
    </div>
  </div>

  <Footer/>
</template>


<script setup>
import {ref, onMounted, computed} from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import NavBar from "@/components/NavBar.vue";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import {useAuthStore} from "@/stores/auth.js";

// ✅ 여행 패키지 목록을 담을 변수
const travelPackages = ref([])
const router = useRouter()
const authStore = useAuthStore()
const role = computed(() => authStore.role)

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
    const res = await axios.get('/api/admins/destination/external-packages')
    travelPackages.value = res.data
  } catch (err) {
    console.error('패키지 불러오기 실패:', err)
  }
})

// ✅ 가격 포맷 함수
function formatPrice(price) {
  return price.toLocaleString() + '원'
}

const deletePackage = async (id) => {
  if (confirm("정말 삭제하시겠습니까?")) {
    try {
      await axios.delete(`/api/admins/destination/external-packages/${id}`);
      travelPackages.value = travelPackages.value.filter(pkg => pkg.id !== id);
      alert("삭제되었습니다.");
    } catch (err) {
      console.error("삭제 실패:", err);
      alert("삭제 중 오류가 발생했습니다.");
    }
  }
}
const editDestination = (id) => {
  router.push(`/destination/edit/${id}`)  // 경로 수정
}

const goToRegister = () => {
  router.push('/destination/new')  // 경로를 /admin이 아닌 /destination으로
}
</script>

<!-- CSS 영역 -->
<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.admin-title {
  text-align: center;
  font-size: 28px;
  margin: 24px 0;
  color: #2c3e50;
  font-weight: bold;
  position: relative;
}

.admin-title::after {
  content: '';
  display: block;
  width: 50px;
  height: 3px;
  background-color: #4CAF50;
  margin: 12px auto;
}

/* 카드 전체 컨테이너: flex로 카드 정렬 */
.card-container {
  display: flex;
  flex-wrap: wrap;
  gap: 24px;
  padding: 24px;
  justify-content: center;
}

/* 카드 하나의 스타일 */
.card {
  width: 300px;
  border-radius: 12px;
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.1);
  background: white;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15);
}

/* 이미지 wrapper: 가격 뱃지와 겹치는 구조 대비 */
.image-wrapper {
  position: relative;
  overflow: hidden;
}

/* 카드 내부 이미지 스타일 */
.card-image {
  width: 100%;
  height: 200px;
  object-fit: cover; /* 이미지 비율 유지하며 채움 */
  transition: transform 0.5s ease;
}

.card:hover .card-image {
  transform: scale(1.05);
}

/* 가격 뱃지 (이미지 우상단 고정) */
.price-badge {
  position: absolute;
  top: 12px;
  right: 12px;
  background: rgba(0, 0, 0, 0.7);
  color: #fff;
  font-weight: bold;
  border-radius: 16px;
  padding: 6px 12px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.2);
}

/* 카드 텍스트 및 버튼 영역 */
.card-content {
  padding: 20px;
  position: relative;
  flex-grow: 1;
  display: flex;
  flex-direction: column;
}

/* 제목 (여행지 이름) */
.card-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
  color: #2c3e50;
}

/* 설명 텍스트 */
.description {
  color: #666;
  font-size: 14px;
  margin-bottom: 10px;
  line-height: 1.4;
  flex-grow: 1;
}

/* 여행 기간 표시 */
.duration {
  font-size: 15px;
  color: #444;
  margin-bottom: 15px;
  display: flex;
  align-items: center;
}

.icon {
  margin-right: 5px;
}

/* 버튼 그룹 */
.button-group {
  display: flex;
  justify-content: space-between;
}

/* 삭제 버튼 스타일 */
.delete-button {
  padding: 8px 16px;
  background-color: #e74c3c;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
  font-weight: 600;
  transition: background-color 0.2s ease;
  flex: 1;
  margin: 0 5px;
}

.delete-button:hover {
  background-color: #c0392b;
}

/* 수정 버튼 스타일 */
.edit-button {
  padding: 8px 16px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
  font-weight: 600;
  transition: background-color 0.2s ease;
  flex: 1;
  margin: 0 5px;
}

.edit-button:hover {
  background-color: #2980b9;
}

/* 액션 버튼 컨테이너 */
.action-buttons {
  display: flex;
  justify-content: space-between;
  margin: 30px auto;
  max-width: 500px;
}

/* 등록 버튼 스타일 */
.add-button {
  padding: 12px 24px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
  font-weight: 600;
  transition: all 0.2s ease;
  display: flex;
  align-items: center;
}

.add-button:hover {
  background-color: #3d8b40;
  transform: scale(1.05);
}

.back-button {
  padding: 12px 24px;
  background-color: #bdc3c7;
  color: #2c3e50;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.back-button:hover {
  background-color: #95a5a6;
}
</style>