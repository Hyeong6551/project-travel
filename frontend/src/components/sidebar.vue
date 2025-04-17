<!-- src/components/Sidebar.vue -->
<template>
  <div class="sidebar">
    <h2>마이페이지</h2>
    <router-link to="/mypage">마이페이지 홈</router-link>
    <router-link to="/mypage/myinfo">개인정보 관리</router-link>
    <router-link to="/mypage/reservations">예약 관리</router-link>
  </div>
</template>

<style scoped>
.sidebar {
  width: 250px;
  border-right: 2px solid #ddd;
  padding: 20px;
}
.sidebar h2 {
  margin-bottom: 20px;
  color: #333;
}
.sidebar a {
  display: block;
  padding: 10px 0;
  color: #333;
  text-decoration: none;
}
.sidebar a:hover {
  color: #008000;
  font-weight: bold;
}
</style>
