<script setup lang="ts">
import {useRouter} from "vue-router";
import {useAuthStore} from "@/stores/auth.ts";
import {onMounted, reactive, ref} from "vue";
import Header from '@/components/Header.vue'
import NavBar from '@/components/NavBar.vue'
import Footer from "@/components/Footer.vue"

const router = useRouter()
const authStore = useAuthStore()
const error = ref('')
const errors = reactive<Record<string, string>>({})

const loginForm = reactive({
  id : ref(''),
  password : ref('')
})

const handleSignIn = async () => {
  try {
    error.value = ''
    errors.id = ''
    errors.password = ''

    const success = await authStore.loginWithCredentials(loginForm.id, loginForm.password)
    if (success) {
      router.push('/')
    } else {
      error.value = '로그인에 실패했습니다. 사용자명과 비밀번호를 확인해주세요.'
    }
  } catch (err: any) {
    const resData = err.response?.data

    errors.id = ''
    errors.password = ''

    if (!loginForm.id.trim()) {
      errors.id = '아이디를 입력해주세요.'
    }

    if (resData) {
      if (!loginForm.password.trim() && loginForm.id.trim() && !resData.id) {
        errors.password = '비밀번호를 입력해주세요.'
      }
      if (resData.id) {
        errors.id = resData.id
      }
      if (resData.password) {
        errors.password = resData.password
      }
      if (resData.error) {
        error.value = resData.error
      } else if (resData.message) {
        error.value = resData.message
      }
    } else {
      error.value = '알 수 없는 오류가 발생했습니다.'
    }
  }
}

onMounted(() => {
  if (authStore.isAuthenticated) {
    alert('세션이 만료되었습니다. 다시 로그인해주세요.')
    authStore.logout()
    router.push('/')
  }
})

</script>

<template>
  <Header/>
  <NavBar/>
  <div class="signin-img">
    <div class="container">
      <div class="login-container">
        <h2 class="login-title">로그인</h2>
        <span id="error-msg"></span>
        <form @submit.prevent="handleSignIn">
          <div class="mb-3">
            <label for="id" class="form-label">아이디</label>
            <input type="text" class="form-control" v-model="loginForm.id" id="id" name="id" />
            <span v-if="errors.id" class="text-danger text-center mt-2 error-message">{{ errors.id }}</span>
          </div>
          <div class="mb-3">
            <label for="password" class="form-label">비밀번호</label>
            <input type="password" class="form-control" v-model="loginForm.password" id="password" name="password" />
            <span v-if="errors.password" class="text-danger text-center mt-2 error-message">{{ errors.password }}</span>
          </div>
          <div class="d-grid">
            <button type="submit" class="btn btn-success btn-login">로그인</button>
          </div>
          <div class="register-link">
            <span>계정이 없으신가요? </span><router-link to="/signup" style="text-decoration-line: none; color: green">회원 가입</router-link>
          </div>
        </form>
      </div>
    </div>
  </div>
  <Footer/>
</template>

<style scoped>
body {
  background-color: #f8f9fa;
}
.login-container {
  max-width: 400px;
  margin: 100px auto;
  padding: 20px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
}
.login-title {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
}
.form-control:focus {
  box-shadow: none;
  border-color: #0d6efd;
}
.btn-login {
  width: 100%;
  padding: 10px;
}
.register-link {
  text-align: center;
  margin-top: 20px;
}
.signin-img{
  background-image: url('../assets/image/signup-bg.jpg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  display: flex;
  width: 100%;
  height: 100vh;
  position: relative
}
.error-message{
  font-size: 14px;
  line-height: 0.6;
  white-space: normal;
}
</style>