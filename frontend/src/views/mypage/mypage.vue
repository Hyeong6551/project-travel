<script>
  import axios from "axios";
  import {useAuthStore} from "@/stores/auth.js";
  import Sidebar from "@/components/sidebar.vue";
  import {jwtDecode} from "jwt-decode";
  import Header from '@/components/Header.vue'
  import NavBar from '@/components/NavBar.vue'
  import Footer from "@/components/Footer.vue"

  export default{
    components: {
      Footer,
      NavBar,
      Header,
      Sidebar,
    },
    data() {
      return {
        myInfo: {
          no: "",
          id: "",
          name: "",
        },
        reservations: [],
        packages: {},
        loading: false
      };
    },
    computed: {
      reservationCount() {
        return this.reservations.length;
      },
    },
    mounted() {
      const authStore = useAuthStore();
      if (!authStore.isAuthenticated) {
        alert('잘못된 접근입니다')
        authStore.logout()
        this.$router.push("/");
        return;
      }

      this.loadUserInfo();
      this.loadReservations(authStore.id);
    },
    methods: {
      async loadUserInfo() {
        const token = localStorage.getItem("token");
        if (!token) {
          console.error("토큰이 없습니다.");
          return;
        }
        const decoded = jwtDecode(token);
        const no = decoded.no;

        try {
          const response = await axios.get(`http://localhost:8000/api/user/${no}`);
          this.myInfo = response.data;
        } catch (err) {
          console.error("회원 정보 에러:", err);
        }
      },
      async loadReservations(userId) {
        this.loading = true;
        try {
          // 로그인된 사용자의 예약 정보를 새로운 API로 가져옵니다
          const response = await axios.get(`http://localhost:8000/api/reservations/user/${userId}`);
          console.log('예약 목록 응답:', response.data);
          this.reservations = response.data;
          
          // 각 예약의 패키지 정보 로드
          for (const reservation of this.reservations) {
            await this.fetchPackageInfo(reservation.packageId);
          }
        } catch (err) {
          console.error("예약 정보 에러:", err);
        } finally {
          this.loading = false;
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
      
      getPackageName(packageId) {
        if (this.packages[packageId]) {
          return this.packages[packageId].title;
        } else {
          return '패키지 ID: ' + packageId;
        }
      },
      
      formatPrice(price) {
        if (price === null || price === undefined) return '가격 정보 없음';
        try {
          return Number(price).toLocaleString() + '원';
        } catch (e) {
          return price + '원';
        }
      }
    },
  }
</script>

<template>
  <Header/>
  <NavBar/>
  <div class="mypage-container">
    <!-- 좌측 사이드바 -->
    <Sidebar />

    <!-- 메인 콘텐츠 -->
    <div class="main-content">
      <!-- 회원정보 카드 -->
      <div class="user-card">
        <div class="user-profile">

          <div>
            <p class="username">{{ myInfo.name }}님</p>
            <p class="userid">아이디: {{ myInfo.id }}</p>
            <router-link to="/mypage/myinfo" class="small-link">개인정보 관리</router-link>
          </div>
        </div>
        <div class="user-stats">
          <div>
            <strong>{{ reservationCount }}</strong>
            <span>예약</span>
          </div>
        </div>
      </div>

      <!-- 예약 정보 카드 -->
      <div class="reservation-section">
        <h3>예약 내역</h3>

        <div v-if="loading" class="loading">로딩 중...</div>
        <div v-else-if="reservations.length > 0" class="reservation-list">
          <div v-for="r in reservations.slice(0, 2)" :key="r.id" class="reservation-card">
            <p><strong>패키지:</strong> {{ getPackageName(r.packageId) }}</p>
            <p><strong>인원수:</strong> {{ r.numberOfPeople }}명</p>
            <p><strong>가격:</strong> {{ formatPrice(r.totalPrice) }}</p>
          </div>
          <router-link to="/mypage/reservations" class="small-link">→ 전체 예약 보기</router-link>
        </div>

        <div v-else class="no-reservation">
          <p>현재 예약하신 내역이 없습니다.</p>
        </div>
      </div>
    </div>
  </div>

<Footer/>
</template>

<style scoped>
.mypage-container {
  display: flex;
  max-width: 1200px;
  margin: 40px auto;
}

.main-content {
  flex-grow: 1;
  padding: 30px;
}

/* 회원 정보 카드 */
.user-card {
  background: #f4fef4;
  border: 2px solid #cce5cc;
  border-radius: 10px;
  padding: 20px;
  margin-bottom: 30px;
}

.user-profile {
  display: flex;
  align-items: center;
  gap: 20px;
}

.user-profile img {
  width: 70px;
  height: 70px;
  border-radius: 50%;
  background: #ddd;
}

.username {
  font-size: 18px;
  font-weight: bold;
}

.userid {
  color: #666;
}

.user-stats {
  display: flex;
  gap: 40px;
  margin-top: 20px;
}

.user-stats div {
  text-align: center;
}

.user-stats strong {
  display: block;
  font-size: 20px;
  color: #008000;
}

.small-link {
  color: #008000;
  text-decoration: none;
  margin-top: 5px;
  display: inline-block;
}

.small-link:hover {
  text-decoration: underline;
}

/* 예약 카드 */
.reservation-section h3 {
  font-size: 20px;
  margin-bottom: 15px;
  color: #006400;
}

.reservation-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.reservation-card {
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 15px;
  background: #f9f9f9;
}

.no-reservation {
  text-align: center;
  color: #888;
  margin-top: 30px;
}

.no-reservation img {
  width: 80px;
  margin-bottom: 10px;
}

.loading {
  text-align: center;
  color: #888;
  margin: 20px 0;
}
</style>