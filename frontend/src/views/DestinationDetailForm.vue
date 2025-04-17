<template>
  <Header/>
  <NavBar/>
  <div class="destination-form-container">
    <h2 class="form-title">상세 내용 입력</h2>
    <div class="form-group">
      <label>상세 설명</label>
      <textarea v-model="form.description" style="height: 150px; resize: none;" placeholder="여행지에 대한 상세 설명을 입력하세요

예시)
바르셀로나의 예술, 마드리드의 왕궁, 세비야의 정열, 그리고 대서양의 품에 안긴 리스본까지! 스페인과 포르투갈을 한 번에 즐기는 유럽 시그니처 루트, 감성 가득한 7박 9일의 여행!
" class="form-textarea" />
    </div>

    <div class="form-group">
      <label>이미지 URL</label>
      <textarea v-model="form.imageUrl" rows="4"  style="resize: none;" placeholder="이미지 URL을 입력하세요" class="form-textarea"></textarea>
      <p class="input-preview">실시간 입력값: {{ form.imageUrl }}</p>
    </div>

    <div class="form-group">
      <label>일정 요약</label>
      <textarea v-model="form.date"  style="height: 200px; resize: none;" placeholder="여행 일정에 대한 요약을 입력하세요

예시)
1일차 인천공항 출발 ✈️ → 마드리드 도착 → 시내 관광(스페인 광장, 솔광장) → 호텔 체크인
2일차 마드리드 왕궁 → 프라도 미술관 관람 → 톨레도 이동 → 구시가지 탐방
3일차 톨레도 → 세비야 이동 🚅 → 스페인 광장, 세비야 대성당, 히랄다탑 관람
4일차 세비야 → 그라나다 이동 🚍 → 알람브라 궁전 & 헤네랄리페 정원 투어)" class="form-textarea" />
    </div>

    <button @click="submitDetail" class="submit-button">저장</button>
  </div>
  <Footer/>
</template>

<script setup>
import { reactive } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import Header from "@/components/Header.vue";
import NavBar from "@/components/NavBar.vue";
import Footer from "@/components/Footer.vue";

const route = useRoute()
const router = useRouter()

// ✅ 이렇게 선언하면:
const form = reactive({
  description: '',
  imageUrl: '',
  date: ''
})
console.log("전송될 이미지 URLs:", form.imageUrl);
const submitDetail = async () => {
  await axios.post(`http://localhost:8000/api/admins/destination/${route.params.id}/detail`, {
    id: route.params.id,
    description: form.description,
    imageUrl: form.imageUrl,
    date: form.date
  })
  console.log("보내는 데이터 확인:", {
    id: route.params.id,
    description: form.description,
    imageUrl: form.imageUrl,
    date: form.date
  })
  alert('상세 저장 완료!')
  router.push('/destination')
}
</script>

<style scoped>
.destination-form-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
  background-color: #f8f9fa;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.form-title {
  color: #343a40;
  margin-bottom: 2rem;
  text-align: center;
  font-size: 1.8rem;
  border-bottom: 2px solid #4dabf7;
  padding-bottom: 0.5rem;
}

.form-group {
  margin-bottom: 1.5rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
  color: #495057;
}

.form-textarea {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ced4da;
  border-radius: 5px;
  font-size: 1rem;
  min-height: 100px;
  resize: vertical;
  transition: border-color 0.2s;
}

.form-textarea:focus {
  border-color: #4dabf7;
  outline: none;
  box-shadow: 0 0 0 2px rgba(77, 171, 247, 0.25);
}

.input-preview {
  margin-top: 0.5rem;
  font-size: 0.875rem;
  color: #6c757d;
}

.submit-button {
  background-color: #4dabf7;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  font-size: 1rem;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.2s;
  display: block;
  margin: 2rem auto 0;
  min-width: 150px;
}

.submit-button:hover {
  background-color: #3793dd;
}
</style>
