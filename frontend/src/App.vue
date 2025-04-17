<script setup>
import { onMounted } from 'vue'
import { useAuthStore } from './stores/auth'

const auth = useAuthStore()


onMounted(async () => {
  if (auth.token && !auth.username) {
    const res = await fetch('/api/user/me', {
      headers: { Authorization: `Bearer ${auth.token}` }
    })
    const user = await res.json()
    auth.login(user.username, auth.token)
  }
})
</script>

<template>
  <div id="app">
    <Header /> <!-- 모든 페이지 상단에 공통 헤더 -->
    <NavBar /> <!-- 메인 네비게이션 바 -->

    <main>
      <RouterView /> <!-- 페이지별 뷰 -->
    </main>

    <Footer /> <!-- 모든 페이지 하단에 공통 푸터 -->
  </div>
</template>



<style>
body {
  margin: 0;
  font-family: 'Pretendard', 'Noto Sans KR', sans-serif;
  background-color: #fafafa;
  color: #333;
}
</style>
