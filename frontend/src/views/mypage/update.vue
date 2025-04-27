<template>
  <Header/>
  <NavBar/>
  <div class="container">
    <Sidebar />



      <div class="content">
        <h2>회원정보 수정</h2>

        <div class="edit-form w-50">
          <label>아이디</label>
          <input v-model="myInfo.id" type="text" disabled />

          <label>비밀번호</label>
          <input v-model="myInfo.plainPassword" type="password" placeholder="비밀번호 변경 시에만 입력" />
          <span v-if="errors.plainPassword" class="text-danger error-message">{{ errors.plainPassword }}</span>

          <label>이름</label>
          <input v-model="myInfo.name" type="text" />
          <span v-if="errors.name" class="text-danger error-message">{{ errors.name }}</span>

          <label>이메일</label>
          <input v-model="myInfo.email" type="email" />
          <span v-if="errors.email" class="text-danger error-message">{{ errors.email }}</span>

          <label>전화번호</label>
          <div class="d-flex">
            <input type="text" v-model="phone1" maxlength="3" @input="cphone1" ref="phone1Ref" class="form-control" />&nbsp;_&nbsp;
            <input type="text" v-model="phone2" maxlength="4" @input="cphone2" ref="phone2Ref" class="form-control" />&nbsp;_&nbsp;
            <input type="text" v-model="phone3" maxlength="4" @input="cphone3" ref="phone3Ref" class="form-control" />
            <input v-model="myInfo.phone" type="hidden" />
          </div>
          <span v-if="errors.phone" class="text-danger error-message">{{ errors.phone }}</span>


          <div class="mb-3">
            <label class="form-label" for="postcode">주소</label>
            <div class="d-flex">
              <input type="text" v-model="myInfo.postcode" placeholder="우편번호" class="form-control me-3" />
              <button type="button" @click="findPostcode" class="form-control btn-success">우편번호 찾기</button>
            </div>
            <input type="text" v-model="myInfo.address" placeholder="주소" class="form-control mt-4" />
            <input type="text" v-model="myInfo.detailAddress" placeholder="상세주소" class="form-control" />
          </div>

          <div class="button-group">
            <button class="btn cancel" @click="$router.go(-1)">취소</button>
            <button class="btn save" @click="updateUserInfo">저장</button>
          </div>
        </div>
      </div>
    </div>

  <Footer/>
</template>

<script>
import axios from "axios";
import Sidebar from "@/components/sidebar.vue";
import {jwtDecode} from "jwt-decode";
import Header from '@/components/Header.vue'
import NavBar from '@/components/NavBar.vue'
import Footer from "@/components/Footer.vue"

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
        plainPassword:"",
        name: "",
        email: "",
        phone: "",
        address: "",
        detailAddress:"",
        postcode: "",
      },
      errors: {
        email: "",
        plainPassword: "",
        phone: "",
        names: ""
      },
      phone1: "",
      phone2: "",
      phone3: ""
    };
  },
  mounted() {
    this.loadUserInfo();
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

        if (this.myInfo.phone) {
          this.phone1 = this.myInfo.phone.slice(0, 3);
          this.phone2 = this.myInfo.phone.slice(3, 7);
          this.phone3 = this.myInfo.phone.slice(7);
        }
      } catch (err) {
        console.error("회원 정보 불러오기 오류:", err);
      }
    },

    updatePhone() {
      this.myInfo.phone = `${this.phone1}${this.phone2}${this.phone3}`;
    },
    cphone1() {
      if (this.phone1.length === 3) {
        this.$refs.phone2Ref.focus();
      }
      this.updatePhone();
    },
    cphone2() {
      if (this.phone2.length === 4) {
        this.$refs.phone3Ref.focus();
      }
      this.updatePhone();
    },
    cphone3() {
      this.updatePhone();
    },

    async updateUserInfo() {
      this.errors = { email: "", phone: "", plainPassword: "", name:""};  // 에러 초기화
      try {
        await axios.put(`http://localhost:8000/api/user/update/${this.myInfo.no}`, this.myInfo);
        alert("회원정보가 성공적으로 수정되었습니다.");
        this.$router.push("/mypage/myinfo");
      } catch (err) {
        if (err.response && err.response.data) {
          const errors = err.response.data;
          console.log(errors)
          Object.keys(errors).forEach(key => {
            if (this.errors.hasOwnProperty(key)) {
              this.errors[key] = errors[key];
            }
          });
        } else {
          alert("수정 중 오류가 발생했습니다.");
        }
      }
    },
    async findPostcode(){
      new window.daum.Postcode({
        oncomplete: (data)=> {
          this.myInfo.postcode = data.myInfo.postcode;
          this.myInfo.address = data.myInfo.address;
        }
      }).open()
    }
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
  padding: 40px;
  background: #f9f9f9;
  border-left: 2px solid #e0e0e0;
}

h2 {
  margin-bottom: 30px;
  color: #008000;
  text-align: center;
}
.edit-form{
    margin: 0 auto;
}
.edit-form label {
  display: block;
  margin-top: 15px;
  font-weight: bold;
}

.edit-form input {
  width: 100%;
  padding: 10px;
  margin-top: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.button-group {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 30px;
}

.btn {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
}

.cancel {
  background-color: #ccc;
  color: #333;
}

.save {
  background-color: #008000;
  color: white;
}

.save:hover {
  background-color: #006400;
}

.cancel:hover {
  background-color: #999;
}

.error-message {
  font-size: 0.9em;
  margin-top: 4px;
}
</style>

