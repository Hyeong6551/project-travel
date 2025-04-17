<template>
  <Header/>
  <NavBar/>
  <div class="container">

  <!-- 사이드바 -->
    <Sidebar/>
    <!-- 예약 내역 -->
    <div class="mypage-content">
      <h2 class="page-title">예약 내역</h2>
      <div v-if="loading">로딩 중...</div>
      <div v-if="reservations.length === 0" class="no-data">
        <p>예약 내역이 없습니다.</p>
      </div>

      <div v-else class="reservation-cards">
        <div class="card" v-for="reservation in reservations" :key="reservation.id">
          <div class="card-img">
              <img :src="getFirstImage(reservation.imageUrl)" alt="여행 이미지" />
          </div>
          <div class="card-info">
            <div class="info-content">
              <h3 class="title">{{ getPackageName(reservation.packageId) }}</h3>
              <p><strong>인원수:</strong> {{ reservation.numberOfPeople }}명</p>
              <p class="price">{{ formatPrice(reservation.totalPrice) }}</p>
              <button class="cancel-btn" @click="cancelReservation(reservation.id)">예약 취소</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <Footer/>
</template>

<script>
import axios from 'axios';
import Header from '@/components/Header.vue'
import NavBar from '@/components/NavBar.vue'
import Footer from "@/components/Footer.vue"
import { useAuthStore } from '@/stores/auth'
import Sidebar from "@/components/sidebar.vue";

export default {
  components: {Footer, NavBar, Header, Sidebar},
  data() {
    return {

      reservations: [],
      packages: {},
      loading: false
    };
  },
  mounted() {
    const authStore = useAuthStore();
    console.log('authStore:', authStore);
    console.log('isAuthenticated:', authStore.isAuthenticated);
    console.log('userId:', authStore.id);
    
    if (!authStore.isAuthenticated) {
      alert('로그인이 필요한 서비스입니다.');
      this.$router.push("/signin");
      return;
    }
    
    const userId = authStore.id;
    
    if (!userId) {
      alert('로그인 정보를 찾을 수 없습니다. 다시 로그인해주세요.');
      this.$router.push("/signin");
      return;
    }
    
    this.fetchReservations(userId);
  },
  methods: {
    // 이미지 URL 처리 함수 추가
    getFirstImage(imageUrl) {
      if (!imageUrl) return '';
      
      try {
        // 이미지 URL이 배열인 경우 (문자열로 저장된 배열)
        if (typeof imageUrl === 'string' && (imageUrl.startsWith('[') || imageUrl.includes(','))) {
          // 문자열을 배열로 파싱 시도
          let images;
          try {
            images = JSON.parse(imageUrl);
          } catch (e) {
            // 콤마로 구분된 문자열인 경우
            images = imageUrl.split(',').map(url => url.trim());
          }
          
          // 배열의 첫 번째 이미지를 반환
          return Array.isArray(images) && images.length > 0 ? images[0] : '';
        }
        
        // 단일 URL 문자열인 경우 그대로 반환
        return imageUrl;
      } catch (e) {
        console.error('이미지 URL 처리 중 오류:', e);
        return '';
      }
    },
    formatPrice(price) {
      if (price === null || price === undefined) return '가격 정보 없음';
      try {
        return Number(price).toLocaleString() + '원';
      } catch (e) {
        return price + '원';
      }
    },
    getPackageName(packageId) {
      if (this.packages[packageId]) {
        return this.packages[packageId].title;
      } else {
        // 패키지 정보가 없으면 ID 표시
        return '패키지 ID: ' + packageId;
      }
    },
    async fetchPackageInfo(packageId) {
      try {
        // 패키지 정보가 이미 있으면 다시 불러오지 않음
        if (this.packages[packageId]) return;
        
        const response = await axios.get(`http://localhost:8000/api/packages/detail/${packageId}`);
        this.packages[packageId] = response.data;
        console.log(`패키지 ${packageId} 정보 로드:`, response.data);
      } catch (err) {
        console.error(`패키지 ${packageId} 정보 로드 실패:`, err);
      }
    },
    async fetchReservations(userId) {
      this.loading = true;
      try {
        console.log('조회할 userId:', userId);
        const response = await axios.get(`http://localhost:8000/api/reservations/user/${userId}`);
        console.log('예약 목록 응답:', response.data);
        this.reservations = response.data;
        
        // 각 예약의 패키지 정보 로드
        for (const reservation of this.reservations) {
          await this.fetchPackageInfo(reservation.packageId);
        }
      } catch (err) {
        console.error('예약 내역 불러오기 실패:', err);
        alert('예약 내역을 불러오는데 실패했습니다.');
      } finally {
        this.loading = false;
      }
    },
    
    async cancelReservation(id) {
      if (!confirm('정말 예약을 취소하시겠습니까?')) return;
      try {
        const authStore = useAuthStore();
        const userId = authStore.id;
        
        await axios.delete(`http://localhost:8000/api/reservations/${id}`);
        this.fetchReservations(userId);
        alert('예약이 취소되었습니다.');
      } catch (err) {
        console.error('예약 취소 실패:', err);
        alert('예약 취소에 실패했습니다.');
      }
    },
  },
};
</script>

<style scoped>
.container {
  display: flex;
  max-width: 1200px;
  margin: 40px auto;
}


.mypage-content {
  flex-grow: 1;
  padding: 40px;
}

.page-title {
  font-size: 28px;
  color: #2e7d32;
  margin-bottom: 30px;
  font-weight: bold;
}

.no-data {
  text-align: center;
  color: #888;
  font-size: 16px;
  padding: 50px 0;
}

.reservation-cards {
  display: flex;
  flex-direction: column;
  gap: 25px;
}

.card {
  display: flex;
  flex-direction: row;
  background-color: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  transition: transform 0.2s ease;
  height: auto;
  min-height: 180px;
}

.card:hover {
  transform: translateY(-3px);
}

.card-img {
  flex: 0 0 auto;
  width: 200px;
}

.card-img img {
  width: 200px;
  height: 100%;
  object-fit: cover;
}

.card-info {
  flex: 1;
  padding: 20px;
  display: flex;
  flex-direction: column;
}

.info-content {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.title {
  font-size: 20px;
  font-weight: bold;
  color: #006400;
  margin-bottom: 10px;
  word-break: break-word;
}

.desc {
  font-size: 14px;
  color: #555;
  margin-bottom: 10px;
}

.info-bottom p {
  margin: 4px 0;
  font-size: 14px;
}

.price {
  color: #d32f2f;
  font-weight: bold;
  font-size: 20px;
  margin-top: 10px;
  margin-bottom: 15px;
}

.cancel-btn {
  align-self: flex-start;
  margin-top: 15px;
  padding: 8px 16px;
  background-color: #e53935;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background 0.2s ease;
}
.sidebar {
  width: 250px;
  border-right: 2px solid #ddd;
  padding: 20px;
}
.sidebar a {
  display: block;
  padding: 10px;
  text-decoration: none;
  color: black;
}
.sidebar a:hover {
  background: #f0f0f0;
}
.cancel-btn:hover {
  background-color: #b71c1c;
}
</style>

