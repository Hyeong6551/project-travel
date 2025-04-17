<script setup lang="ts">
import { reactive, ref, onMounted } from "vue";
import { useRouter } from 'vue-router'
import axios from 'axios'
import Header from '@/components/Header.vue'
import NavBar from '@/components/NavBar.vue'
import Footer from "@/components/Footer.vue"

// 회원가입
const router = useRouter()
const errors = reactive<Record<string, string>>({});
const error = ref("");
const form = reactive({
  id: "",
  password: "",
  confirmPassword: "",
  name: "",
  email: "",
  phone: "",
  postcode: "",
  address: "",
  detailAddress: ""
});

const handlerSignUp = async () => {
  error.value = "";
  Object.keys(errors).forEach((key) => delete errors[key]);
  try {
    await axios.post("/api/auth/signup", {
      ...form
    });
    alert("회원가입이 완료되었습니다.")
    router.push("/signin");
  } catch (err: any) {
    if (err.response && err.response.data) {
      const data = err.response.data;
      Object.keys(errors).forEach((key) => delete errors[key]);
      for (const key in data) {
        errors[key] = data[key];
      }
    } else {
      error.value = "회원가입 중 오류가 발생했습니다.";
    }
  }
};

// 전화번호
const phone1 = ref("");
const phone2 = ref("");
const phone3 = ref("");

const updatePhone = () => {
  form.phone = `${phone1.value}${phone2.value}${phone3.value}`;
};

const phone1Ref = ref<HTMLInputElement | null>(null);
const phone2Ref = ref<HTMLInputElement | null>(null);
const phone3Ref = ref<HTMLInputElement | null>(null);

const cphone1 = () => {
  if (phone1.value.length === 3) {
    phone2Ref.value?.focus();
  }
  updatePhone();
};

const cphone2 = () => {
  if (phone2.value.length === 4) {
    phone3Ref.value?.focus();
  }
  updatePhone();
};

const cphone3 = () => {
  updatePhone();
};

// 주소 API
onMounted(() => {
  const script = document.createElement("script");
  script.src = "//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js";
  script.async = true;
  document.head.appendChild(script);
});

declare global {
  interface Window {
    daum: any;
  }
}

const findPostcode = () => {
  if (window.daum && window.daum.Postcode) {
    new window.daum.Postcode({
      oncomplete: (data: any) => {
        form.postcode = data.zonecode;
        form.address = data.userSelectedType === "R" ? data.roadAddress : data.jibunAddress;
      }
    }).open();
  } else {
    alert("우편번호 서비스를 불러오는 중입니다. 잠시 후 다시 시도해주세요.");
  }
};
</script>

<template>
  <Header/>
  <NavBar/>
  <div class="signup-img">
    <div class="container">
      <div class="register-container">
        <h2 class="register-title">회원 가입</h2>
        <form @submit.prevent="handlerSignUp">
          <div class="mb-3">
            <label for="id" class="form-label">아이디</label>
            <input type="text" class="form-control" v-model="form.id" id="id" name="id" />
            <span v-if="errors.id" class="text-danger text-center mt-2 error-message">{{ errors.id }}</span>
          </div>

          <div class="mb-3">
            <label for="password" class="form-label">비밀번호</label>
            <input type="password" class="form-control" v-model="form.password" id="password" name="password" />
            <span v-if="errors.password" class="text-danger text-center mt-2 error-message">{{ errors.password }}</span>
          </div>

          <div class="mb-3">
            <label for="password" class="form-label">비밀번호 확인</label>
            <input type="password" class="form-control" v-model="form.confirmPassword"/>
            <span v-if="errors.confirmPassword " class="text-danger text-center mt-2 error-message">{{ errors.confirmPassword }}</span>
          </div>

          <div class="mb-3">
            <label for="name" class="form-label">이름</label>
            <input type="text" class="form-control" v-model="form.name" id="name" name="name" />
            <span v-if="errors.name" class="text-danger text-center mt-2 error-message">{{ errors.name }}</span>
          </div>

          <div class="mb-3">
            <label for="email" class="form-label">이메일</label>
            <input type="text" class="form-control" v-model="form.email" id="email" name="email" />
            <span v-if="errors.email" class="text-danger text-center mt-2 error-message">{{ errors.email }}</span>
          </div>

          <div class="mb-3">
            <label for="phone" class="form-label">전화번호</label>
            <div class="d-flex">
              <input type="text" v-model="phone1" maxlength="3" @input="cphone1" ref="phone1Ref" class="form-control" />&nbsp;_&nbsp;
              <input type="text" v-model="phone2" maxlength="4" @input="cphone2" ref="phone2Ref" class="form-control" />&nbsp;_&nbsp;
              <input type="text" v-model="phone3" maxlength="4" @input="cphone3" ref="phone3Ref" class="form-control" />
            </div>
            <span v-if="errors.phone" class="text-danger text-center mt-2 error-message">{{ errors.phone }}</span>
          </div>
          <div class="mb-3">
            <label class="form-label" for="postcode">주소</label>
            <div class="d-flex">
              <input type="text" v-model="form.postcode" placeholder="우편번호" class="form-control me-3" readonly/>
              <button type="button" @click="findPostcode" class="form-control btn-success">우편번호 찾기</button>
            </div>
            <input type="text" v-model="form.address" placeholder="주소" class="form-control mt-4" readonly/>
            <input type="text" v-if="form.address" v-model="form.detailAddress" placeholder="상세주소" class="form-control mt-2" />
          </div>
          <div class="d-grid">
            <button type="submit" class="btn btn-success btn-register">회원 가입</button>
          </div>

          <div class="login-link">
            <span>이미 계정이 있으신가요? </span><router-link to="/signin" style="text-decoration-line: none; color: green">로그인</router-link>
          </div>
        </form>
      </div>
    </div>
  </div>
  <Footer/>
</template>

<style>
body {
  background-color: #f8f9fa;
}
.register-container {
  max-width: 400px;
  margin: 100px auto;
  padding: 20px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
}
.register-title {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
}
.form-control:focus {
  box-shadow: none;
  border-color: #0d6efd;
}
.btn-register {
  width: 100%;
  padding: 10px;
}
.login-link {
  text-align: center;
  margin-top: 20px;
}
.error-message{
  font-size: 14px;
  line-height: 0.6;
  white-space: normal;
}
.signup-img{
  background-image: url('../assets/image/signup-bg.jpg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  display: flex;
  width: 100%;
  height: 100vh;
  position: relative
}
</style>