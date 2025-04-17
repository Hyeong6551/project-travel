<template>
<Header/>
<NavBar/>

    <div v-if="error" class="alert alert-warning mt-4">
      <p>해당 패키지를 찾을 수 없습니다.</p>
    </div>

    <div v-if="packageData" class="row">
      <div class="col-md-8">
        <div class="card mb-4">
          <div class="card-body">
            <h2 class="card-title">{{ packageData.title }}</h2>

            <div id="packageCarousel" class="carousel slide mb-3" data-bs-ride="carousel">
              <div class="carousel-inner">
                <div
                    v-for="(img, idx) in images"
                    :key="idx"
                    class="carousel-item"
                    :class="{ active: idx === 0 }"
                >
                  <img :src="img" class="d-block w-100" alt="슬라이드 이미지" style="height: 400px; object-fit: cover;" />
                </div>
              </div>
              <button class="carousel-control-prev" type="button" data-bs-target="#packageCarousel" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
              </button>
              <button class="carousel-control-next" type="button" data-bs-target="#packageCarousel" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
              </button>
            </div>

            <p class="card-text mb-3">{{ packageData.description }}</p>
            <p class="h4">가격: {{ packageData.price?.toLocaleString() }} 원</p>
          </div>
        </div>
        <!--여행 주요일정 -->
        <div v-if="packageData" class="card mb-4">
          <div class="card-header">
            <strong>여행 주요일정</strong>
          </div>
          <div class="card-body">
            <dl class="row">
              <dt class="col-sm-3">여행기간</dt>
              <dd class="col-sm-9">2025.04.29 (토) ~ 2025.05.10 (월) 기간 내</dd>

              <br />

              <dt class="col-sm-3">항공일정</dt>
              <dd class="col-sm-9">
                <img src="data:image/jpeg;base64,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"
                     alt="아시아나 로고" style="width:50px; margin-bottom:5px;"/>
                (아시아나 항공)<br />
                항공 일정은 미팅 후 협의하여 최종 확정됩니다.

              </dd>

              <dt class="col-sm-3">예약인원</dt>
              <dd class="col-sm-9">현재 0명 예약 (잔여석: <span class="red-text">8명</span> / 최소인원: 10명)
              </dd>
            </dl>
          </div>
        </div>

        <div v-if="parsedDates.length" class="card mb-4">
          <div class="card-header">
            <strong>일정 요약</strong>
          </div>
          <div class="card-body schedule-summary">
            <div v-for="(line, index) in parsedDates" :key="index" class="date-line">
              <span class="bold-day">{{ line.split(" ")[0] }}</span>
              {{ line.substring(line.indexOf(" ") + 1) }}
            </div>
          </div>
        </div>


        <div v-if="packageData" class="card mb-4">
          <div class="card-header">
            <strong>상품 핵심 포인트</strong>
          </div>
          <div class="card-body">
            <div class="tab-content p-3" id="myTabContent">
              <div class="tab-pane fade show active" id="special" role="tabpanel" aria-labelledby="special-tab">
                <h5 class="mb-3"><strong>스페셜 혜택</strong></h5>
                <div style="background-color:#f8f9fa; padding:1rem; border-radius:.25rem;">
                  <p>[특전] ■ 전 일정 엄선된 4성급 (★★★★급) 호텔 숙박</p>
                  <p>[특전] ■ 인천공항 출발부터 전립형 전문인솔자 동반, 개인용 수신기 제공</p>
                  <p>[특전] ■ 대표 인기 4대 특전 ALL 포함</p>
                  <p>[특전] 1. 전망대 / 2. 유람선 / 3. 벤츠 차량 투어</p>
                  <p>[특전] ■ 고니치샤 특식 & 디저트</p>
                  <p>[특전] ■ 든든한 한식 제공</p>
                  <p>[특전] ■ 디저트: 티라미수, 에스프레소, 젤라또</p>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="card mb-4">
          <div class="card-header">
            <strong>미팅정보</strong>
          </div>
          <div class="card-body" style="background-color: #f8f9fa;">
            인천국제공항 1터미널 3층 출국장 1번 출구 앞(A카운터 옆 창측)<br />
            여행사카운터 1~4번 테이블
          </div>
        </div>

        <!-- 호텔 정보 -->
        <div v-if="packageData" class="card mb-4">
          <div class="card-header">
            <strong>호텔</strong>
          </div>
          <div class="card-body p-0">
            <table class="table table-bordered mb-0 text-center">
              <thead class="table-light">
              <tr>
                <th scope="col">일정/지역</th>
                <th scope="col">호텔명 & 등급</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td>1일차</td>
                <td>
                  - 도심 중심부 4성급 호텔<br />
                  <small class="text-muted">
                    예) Novotel, Mercure 등 <br />
                    조식 뷔페 제공
                  </small>
                </td>
              </tr>
              <tr>
                <td>2일차</td>
                <td>
                  - 시내 4성급 호텔 <br />
                  <small class="text-muted">
                    예) Belstay Aurelia<br />
                    넓은 객실, 수영장 보유
                  </small>
                </td>
              </tr>
              <tr>
                <td>3일차</td>
                <td>
                  - 강변 4성급 호텔<br />
                  <small class="text-muted">
                    예) NH Venezia Rio Novo<br />
                    강변 다리 근처
                  </small>
                </td>
              </tr>
              <tr>
                <td>4일차</td>
                <td>
                  - 4성급 호텔<br />
                  <small class="text-muted">
                    예) Hilton Garden <br />
                    피트니스 센터
                  </small>
                </td>
              </tr>
              <tr>
                <td>5~6일차</td>
                <td>
                  - 인터라켄 4성급<br />
                  <small class="text-muted">
                    예) Hotel Interlaken<br />
                    산림 뷰 스위트룸, 조식 제공
                  </small>
                </td>
              </tr>
              <tr>
                <td>7일차</td>
                <td>
                  - 4성급 호텔<br />
                  <small class="text-muted">
                    예) Novotel Centre<br />
                    도보 15분 / 와인 시음 가능
                  </small>
                </td>
              </tr>
              <tr>
                <td>8일차</td>
                <td>
                  - 4성급 호텔<br />
                  <small class="text-muted">
                    예) Copthorne Tara Hotel<br />
                    하이드파크 인접, 공항 셔틀 이용 가능
                  </small>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>

        <div class="mt-4 text-center">
          <router-link to="/" class="btn btn-primary">메인 페이지로 돌아가기</router-link>
        </div>
    </div>
      <!-- 오른쪽 사이드바 -->
      <div class="right-sidebar">
        <div class="booking-box">
          <!-- 출발일 정보 -->
          <div class="departure-info mb-3">
            <div class="d-flex justify-content-between">
              <span>· 인천 출발</span>
              <strong>2025.04.29(월) 23:55</strong>
            </div>
            <div class="d-flex justify-content-between">
              <span>· 인천 도착</span>
              <strong>2025.05.08(토) 18:05</strong>
            </div>
            <!--          <button class="btn btn-dark w-100 mt-2">출발일 변경</button>-->
          </div>
          <h5><strong>예약인원 선택</strong></h5>

          <div class="mb-3">
            <label>성인</label>
            <div class="input-group">
              <button class="btn btn-outline-secondary" @click="decrease('adult')">-</button>
              <input type="text" class="form-control text-center" :value="counts.adult" readonly>
              <button class="btn btn-outline-secondary" @click="increase('adult')">+</button>
            </div>
          </div>

          <div class="mb-3">
            <label>아동</label>
            <div class="input-group">
              <button class="btn btn-outline-secondary" @click="decrease('childExtra')">-</button>
              <input type="text" class="form-control text-center" :value="counts.childExtra" readonly>
              <button class="btn btn-outline-secondary" @click="increase('childExtra')">+</button>
            </div>
          </div>

          <!-- 현지 필수 경비 -->
          <div class="local-cost mt-4">
            <div class="d-flex justify-content-between">
              <span>현지 필수 경비 <span class="tooltip-icon"></span></span>
              <div>
                <div>성인 <strong>$180</strong></div>
                <div>아동 <strong>$180</strong></div>
              </div>
            </div>
          </div>


          <hr />
          <div class="d-flex justify-content-between">
            <strong>총 금액</strong>
            <strong style="color: green">{{ totalPrice.toLocaleString() }}원</strong>
          </div>
          <button class="btn btn-dark w-100 mt-2" @click="handlePayment">결제하기</button>
        </div>
      </div>
    </div>

  <Footer/>
</template>

<script>
import axios from "axios";
import { ref, onMounted, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useAuthStore } from '@/stores/auth'

import Header from '@/components/Header.vue'
import NavBar from '@/components/NavBar.vue'
import Footer from "@/components/Footer.vue"

export default {
  components: {Footer, NavBar, Header},
  setup() {
    const route = useRoute();
    const router = useRouter();
    const authStore = useAuthStore();
    const id = route.params.id  // ✅ id를 여기서 꺼내야 함
    const packageData = ref(null);
    const error = ref(false);

    // 디버깅: authStore 및 사용자 정보 확인
    console.log('authStore:', authStore);
    console.log('isAuthenticated:', authStore.isAuthenticated);
    if (authStore.isAuthenticated) {
      console.log('user:', authStore.user);
    }

    const counts = ref({ adult: 1, childExtra: 0 });

    const images = computed(() => {
      if (!packageData.value || !packageData.value.imageUrl) return [];
      return packageData.value.imageUrl.split(",").map(str => str.trim());
    });

    const parsedDates = computed(() => {
      if (!packageData.value || !packageData.value.date) return [];

      const regex = /(\d+일차\s[^\n]*)/g; // "1일차 ~ 줄 끝" 형식 매칭
      return [...packageData.value.date.matchAll(regex)].map(match => match[0]);
    });

    const totalPrice = computed(() => {
      const price = packageData.value?.price || 0;
      const totalCount =
          counts.value.adult + counts.value.childExtra;
      return price * totalCount;
    });

    const increase = (type) => {
      counts.value[type]++;
    };

    const decrease = (type) => {
      if (counts.value[type] > 0) counts.value[type]--;
    };
    
    const handlePayment = async () => {
      if (!authStore.isAuthenticated) {
        alert('로그인이 필요한 서비스입니다.');
        router.push('/signin');
        return;
      }
      
      console.log('사용자 ID:', authStore.id); // 사용자 ID 로깅
      
      try {
        // 예약 정보 생성
        const userId = authStore.id;
        
        if (!userId) {
          alert('로그인 정보를 찾을 수 없습니다. 다시 로그인해주세요.');
          router.push('/signin');
          return;
        }
        
        const reservationData = {
          userId: userId,
          imageUrl: images.value.length > 0 ? images.value[0] : '',  // 첫 번째 이미지만 전송
          packageId: Number(id), // Number로 변환하여 전송
          numberOfPeople: counts.value.adult + counts.value.childExtra,
          totalPrice: totalPrice.value
        };
        
        console.log('예약 데이터:', reservationData); // 예약 데이터 로깅
        
        // gateway-service로 예약 정보 전송
        const response = await axios.post('http://localhost:8000/api/reservations', reservationData);
        console.log('예약 응답:', response.data); // 응답 데이터 로깅
        
        alert('예약이 완료되었습니다.');
        router.push('/mypage/reservations'); // 예약 내역 페이지로 이동
      } catch (error) {
        console.error('예약 중 오류가 발생했습니다:', error);
        alert('예약 중 오류가 발생했습니다. 다시 시도해주세요.');
      }
    };
    
    onMounted(async () => {
      try {
        const response = await axios.get(`http://localhost:8000/api/packages/detail/${id}`);
        packageData.value = response.data;
      } catch (err) {
        console.error("데이터 로드 실패:", err);
        error.value = true;
      }
    });

    return {
      packageData,
      error,
      images,
      parsedDates,
      counts,
      increase,
      decrease,
      totalPrice,
      handlePayment
    };
  },
};
</script>

<style scoped src="../assets/productDetail.css"></style>