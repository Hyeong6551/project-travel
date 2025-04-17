<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { storeToRefs } from 'pinia'
import router from "../router";
const authStore = useAuthStore()
const { isAuthenticated, id, role } = storeToRefs(authStore)
const searchKeyword = ref("");
//검색 기능
const search = () => {
  if (searchKeyword.value.trim() === "") {
    alert("검색어를 입력하세요")
    return
  }
  router.push({ path: "/package", query: { keyword: searchKeyword.value } })
}
// 로그인 상태 변경 감지
watch(() => authStore.isAuthenticated, (newValue) => {
  if (newValue) {
    id.value = localStorage.getItem('id') || ''
    role.value = localStorage.getItem('role') || ''
  } else {
    id.value = ''
    role.value = ''
  }
})

const handleLogout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('id')
  localStorage.removeItem('role')
  authStore.logout()
  router.push("/")
}

onMounted(() => {
  const token = localStorage.getItem('token')
  if (token) {
    authStore.setAuth({
      isAuthenticated: true,
      id: localStorage.getItem('id') || '',
      role: localStorage.getItem('role') || ''
    })
  }
})
</script>

<template>
  <header class="header">
    <div class="header-container">
      <!-- 로고 + 검색창 -->
      <div class="header-left">
        <h1 class="logo">
          <a href="/">🌍 <span>여행 플랫폼</span></a>
        </h1>
        <form   class="search-form">
          <input type="text" v-model="searchKeyword" class="form-control" placeholder="여행지를 검색하세요" @keypress.enter="search">
          <button class="btn btn-primary" @click="search">검색</button>
        </form>
      </div>

      <div class="top-nav">
        <template v-if="isAuthenticated">
           <span class="user-info">
            👤 <strong>{{ id }}</strong> <span class="role" v-if="role === 'ADMIN'">({{ role }})</span>
          </span>
          <router-link to="/" @click.prevent="handleLogout">로그아웃</router-link>
          <a href="/mypage">마이페이지</a>

        </template>
        <template v-else>
          <router-link class="nav-link" to="/signin">로그인</router-link>
          <router-link class="nav-link" to="/signup">회원가입</router-link>
        </template>
        <a class="nav-link"  href="/board">공지사항</a>
      </div>
    </div>
  </header>
</template>

<style scoped>
.navbar {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  z-index: 10;
}
</style>


<style scoped>
.header {
  background-color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  padding: 12px 0;
}
.header-container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.header-left {
  display: flex;
  align-items: center;
  gap: 20px;
}
.logo {
  font-size: 24px;
  white-space: nowrap;
  display: flex;
  align-items: center;
}

.logo span {
  margin-left: 6px;
  display: inline-block;
}
.search-form {
  display: flex;
  align-items: center;
  gap: 6px;
}
.search-form input {
  width: 360px;
  height: 36px;
  padding: 0 10px;
  border: 1px solid #ccc;
  border-radius: 6px;
}
.search-form button {
  height: 36px;
  min-width: 60px; /* ✅ 버튼 너비 확보 */
  padding: 0 12px;
  border: none;
  background-color: #028267;
  color: white;
  border-radius: 6px;
  display: flex;
  align-items: center;
  justify-content: center;
  line-height: 1;
  font-size: 14px;
  white-space: nowrap; /* ✅ 줄바꿈 방지 */
}

.top-nav {
  display: flex;
  gap: 15px;
  font-weight: 500;
  white-space: nowrap; /* 줄바꿈 방지 */
  align-items: center;
  flex-wrap: nowrap;   /* 한 줄에 유지 */
}
.nav-link {
  display: flex;
  gap: 15px;
  font-weight: 500;
  white-space: nowrap; /* 줄바꿈 방지 */
  align-items: center;
  flex-wrap: nowrap;   /* 한 줄에 유지 */
  color: #646cff;
  text-decoration: inherit;

}
.nav-link:hover {
  color: #535bf2;
}

a {
  text-decoration: none;
}
</style>
