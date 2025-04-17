<template>
  <Header/>
  <NavBar/>
  <div class="container">
    <!-- 사이드바 -->
    <Sidebar />

    <!-- 개인정보 관리 -->
    <div class="content">
      <h2>개인정보 관리</h2>
      <div class="info-box">
        <label>아이디</label>
        <p>{{ myInfo.id }}</p>

        <label>이름</label>
        <p>{{ myInfo.name }}</p>

        <label>이메일</label>
        <p>{{ myInfo.email }}</p>

        <label>전화번호</label>
        <p>{{ myInfo.phone }}</p>

        <label>주소</label>
        <p>{{ myInfo.address }}</p>

        <label>우편번호</label>
        <p>{{ myInfo.postcode }}</p>
      </div>

      <router-link to="/mypage/myinfo/update" class="btn">수정하기</router-link>
      <button class="btn danger" @click="deleteUser">회원 탈퇴</button>
    </div>
  </div>
  <Footer/>
</template>

<script>
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import { useAuthStore } from "@/stores/auth";
import Header from '@/components/Header.vue'
import NavBar from '@/components/NavBar.vue'
import Footer from "@/components/Footer.vue"
import Sidebar from "@/components/sidebar.vue";

export default {
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
        email: "",
        phone: "",
        address: "",
        postcode: "",
      },
    };
  },
  mounted() {
    this.loadUserInfo();
    const authStore = useAuthStore();
    if (!authStore.isAuthenticated) {
      alert('잘못된 접근입니다')
      authStore.logout()
      this.$router.push("/");
    }
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
      } catch (error) {
        console.error("회원 정보를 불러오는 중 오류 발생:", error);
      }
    },

    async deleteUser() {
      if (!confirm("정말 탈퇴하시겠습니까?")) return;
        try {
          await axios.delete(`http://localhost:8000/api/user/delete/${this.myInfo.no}`);
          alert("회원 탈퇴가 완료되었습니다.");
          sessionStorage.clear();
          const authStore = useAuthStore();
          authStore.logout();
          this.$router.push("/");
        } catch (error) {
          console.error("회원 탈퇴 오류:", error);
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

.content {
  flex-grow: 1;
  padding: 40px 50px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 128, 0, 0.1);
}

h2 {
  color: #008000;
  font-size: 26px;
  margin-bottom: 30px;
}

.info-box {
  display: grid;
  grid-template-columns: 1fr;
  row-gap: 15px;
  margin-bottom: 30px;
}

label {
  font-weight: 600;
  margin-bottom: 5px;
  color: #333;
}

p {
  margin: 0;
  padding: 10px 12px;
  border: 1px solid #ccc;
  border-radius: 6px;
  background: #f9f9f9;
  font-size: 15px;
  color: #555;
}

.btn {
  background: #008000;
  color: white;
  padding: 12px 5px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  margin-top: 10px;
  font-size: 15px;
  transition: all 0.3s ease;
  display: inline-block;
  min-width: 120px;
  text-align: center;
  text-decoration: none;
}

.btn:hover {
  background: #006400;
  box-shadow: 0 4px 12px rgba(0, 128, 0, 0.3);
}

.danger {
  background: #ff4444;
  margin-left: 12px;
}

.danger:hover {
  background: #cc0000;
  box-shadow: 0 4px 12px rgba(255, 68, 68, 0.3);
}
</style>

