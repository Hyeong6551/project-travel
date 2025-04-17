<!-- Vue.js 단일 파일 컴포넌트(.vue) -->
<template>
  <Header/>
  <NavBar/>
  <!-- 회원 정보 수정 폼을 감싸는 컨테이너 -->
  <div class="edit-form">
    <!-- 폼 제목 -->
    <h2>회원 정보 수정</h2>

    <!-- 회원 정보를 수정하는 폼, 제출 시 updateMember() 메서드 호출 -->
    <form @submit.prevent="updateMember">
      <div>
        <label>이름:</label>
        <!-- 회원 이름 입력 필드 (v-model로 양방향 바인딩) -->
        <input type="text" v-model="member.name" required />
      </div>

      <div>
        <label>이메일:</label>
        <!-- 회원 이메일 입력 필드 (유효한 이메일 형식 필수) -->
        <input type="email" v-model="member.email" required />
      </div>

      <div>
        <label>권한:</label>
        <!-- 회원 권한 선택 드롭다운 -->
        <select v-model="member.role">
          <option value="USER">USER</option>
          <option value="ADMIN">ADMIN</option>
        </select>
      </div>

      <!-- 수정 완료 버튼 -->
      <button type="submit" class="back-button">수정</button>
      <a @click="$router.back()" class="btn btn-primary mx-3">뒤로가기</a>
    </form>

  </div>
  <Footer/>
</template>

<script>
// Axios 라이브러리 사용하여 API 호출 수행
import axios from 'axios'
import NavBar from "@/components/NavBar.vue";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";

export default {
  // 컴포넌트 이름 설정
  name: 'MemberEdit',
  components: {Footer, Header, NavBar},

  // 컴포넌트 상태 데이터
  data() {
    return {
      member: {}  // API로부터 가져올 회원 정보를 담을 객체
    }
  },

  mounted() {
    const role = localStorage.getItem('role');
    if (role !== 'ADMIN') {
      alert('접근 권한이 없습니다.');
      this.$router.push('/');
      return;
    }
    const no = this.$route.params.no
    axios.get(`/api/user/${no}`)
        .then(response => {
          this.member = response.data
        })
        .catch(error => {
          console.error('회원 정보 로딩 실패:', error)
        })
  },

  // 컴포넌트 메서드 정의
  methods: {
    // 회원 정보 수정 요청 메서드
    updateMember() {
      axios.put(`/api/user/${this.member.no}`, this.member)
          .then(() => {
            alert('수정 완료!')
            this.$router.push('/members')
          })
          .catch(error => {
            console.error('수정 실패:', error)
          })
    }
  }
}
</script>

<style scoped>
/* 현재 컴포넌트에만 적용되는 스타일 */

/* 수정 폼 스타일 */
.edit-form {
  width: 400px;                 /* 폼 너비 설정 */
  margin: 50px auto;            /* 폼을 중앙으로 정렬 */
  padding: 20px;                /* 폼 내부 여백 설정 */
  border: 1px solid #ddd;       /* 폼 테두리 스타일 설정 */
  border-radius: 8px;           /* 폼 모서리 둥글게 처리 */
  background: #fafafa;          /* 폼 배경색 설정 */
}

/* 라벨 스타일 설정 */
.edit-form label {
  display: block;               /* 라벨을 블록 요소로 표시 (줄바꿈) */
  margin-top: 10px;             /* 라벨 위 여백 설정 */
}

/* 입력 필드 및 선택상자 스타일 설정 */
.edit-form input,
.edit-form select {
  width: 100%;                  /* 입력 필드 전체 너비 사용 */
  padding: 8px;                 /* 입력 필드 내부 여백 설정 */
  margin-top: 5px;              /* 입력 필드 위쪽 여백 설정 */
}

/* 버튼 스타일 설정 */
.edit-form button {
  margin-top: 20px;             /* 버튼 위쪽 여백 설정 */
  padding: 10px 20px;           /* 버튼 내부 여백 설정 */
  background-color: #42b983;    /* 버튼 배경색 설정 (Vue.js 기본 컬러) */
  color: white;                 /* 버튼 글자색 설정 */
  border: none;                 /* 버튼 테두리 없애기 */
  border-radius: 4px;           /* 버튼 모서리 둥글게 처리 */
  cursor: pointer;              /* 마우스 포인터를 손가락 모양으로 변경 */
}

.back-button {
  margin-top: 24px;
  padding: 10px 20px;
  background-color: #bdc3c7;
  color: #2c3e50;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.back-button:hover {
  background-color: #1f8034;
}
</style>