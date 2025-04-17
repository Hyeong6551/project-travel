<!-- src/components/NavBar.vue -->
<template>
  <nav class="main-nav">
    <div class="nav-container">
      <div class="menu-icon">
        <svg xmlns="http://www.w3.org/2000/svg" class="icon" width="18" height="18" viewBox="0 0 24 24" stroke-width="2" stroke="#028267" fill="none" stroke-linecap="round" stroke-linejoin="round">
          <path stroke="none" d="M0 0h24v24H0z" fill="none" />
          <path d="M4 6l16 0" />
          <path d="M4 12l16 0" />
          <path d="M4 18l16 0" />
        </svg>
        <span>전체메뉴</span>
      </div>

      <a href="/package">패키지 여행</a>

    </div>
  </nav>
</template>

<style scoped>
.main-nav {
  background-color: white;
  border-top: 1px solid #eee;
  border-bottom: 1px solid #ddd;
  padding: 10px 0;
  margin-top: 0;
}
.nav-container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  gap: 30px;
  font-size: 14px;
  font-weight: 500;
  color: #333;
  justify-content: flex-start;
}
.menu-icon {
  display: flex;
  align-items: center;
  color: #028267;
  font-weight: 700;
}
.menu-icon .icon {
  margin-right: 6px;
}
a {
  text-decoration: none;
}
</style>
<script setup lang="ts">
</script>