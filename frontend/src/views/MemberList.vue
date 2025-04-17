<template>
  <Header/>
  <NavBar/>
  <div class="container">
    <h2 class="title">회원 목록</h2>
    <transition-group name="fade" tag="table" class="member-table">
      <thead>
      <tr>
        <th>번호</th>
        <th>ID</th>
        <th>이름</th>
        <th>이메일</th>
        <th>주소</th>
        <th>권한</th>
        <th>생성 날짜</th>
        <th>수정</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="member in members" :key="member.no">
        <td>{{ member.no }}</td>
        <td>{{ member.id }}</td>
        <td>{{ member.name }}</td>
        <td>{{ member.email }}</td>
        <td>{{ member.address }}</td>
        <td>{{ member.role }}</td>
        <td>{{ member.createdAt }}</td>
        <td>
          <button @click="editMember(member)">수정</button>
          <button @click="deleteMember(member)" v-if="member.role !== 'ADMIN'">삭제</button>
        </td>
      </tr>
      </tbody>
    </transition-group>
    <div class="nav-button-box">
      <button @click="$router.push('/destination')" class="package-button">여행패키지 이동</button>
      <button @click="$router.back()" class="back-button mx-3">← 돌아가기</button>
    </div>
  </div>
  <!-- 버튼 박스를 따로 묶어서 정렬 -->
  <Footer/>
</template>

<script>
import axios from 'axios'
import Footer from "@/components/Footer.vue";
import NavBar from "@/components/NavBar.vue";
import Header from "@/components/Header.vue";

export default {
  name: 'MemberList',
  components: {Header, NavBar, Footer},
  data() {
    return {
      members: []
    }
  },

  mounted() {
    const role = localStorage.getItem('role');
    if (role !== 'ADMIN') {
      alert('접근 권한이 없습니다.');
      this.$router.push('/');
      return; // 아래 코드 실행 막기
    }

    axios.get('/api/user') // 백엔드 API와 연결
        .then(response => {
          this.members = response.data
        })
        .catch(error => {
          console.error('회원 목록 조회 실패:', error)
        })
  },

  methods: {
    editMember(member) {
      this.$router.push({name: 'MemberEdit', params: {no: member.no}});
    },
    deleteMember(member) {
      if (confirm('정말 삭제하시겠습니까?')) {
        axios.delete(`/api/user/${member.no}`)
            .then(() => {
              this.members = this.members.filter(m => m.no !== member.no);
            })
            .catch(error => {
              console.error('삭제 실패:', error);
            });
      }
    }
  }
}
</script>

<style scoped>
/* 전체 배경 */
.container {
  max-width: 960px;
  margin: 40px auto;
  padding: 20px;
  background: #ffffff;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  animation: slideIn 0.8s ease;
}

/* 제목 스타일 */
.title {
  text-align: center;
  font-size: 28px;
  margin-bottom: 24px;
  color: #2c3e50;
  font-weight: bold;
}

/* 테이블 스타일 */
.member-table {
  width: 100%;
  border-collapse: collapse;
}

.member-table th,
.member-table td {
  padding: 12px 16px;
  border-bottom: 1px solid #ddd;
  text-align: center;
}

.member-table th {
  background-color: #4CAF50;
  color: white;
  font-weight: bold;
}

.member-table tr:nth-child(even) {
  background-color: #f9f9f9;
}

.member-table tr:hover {
  background-color: #f1f1f1;
  transition: background-color 0.3s ease;
}

/* 애니메이션 */
.fade-enter-active,
.fade-leave-active {
  transition: all 0.5s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: translateY(10px);
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/*     */

/* 버튼 스타일 */
.member-table button {
  padding: 6px 12px;
  margin: 2px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 600;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.member-table button:first-of-type {
  background-color: #3498db;
  color: white;
}

.member-table button:first-of-type:hover {
  background-color: #2980b9;
  transform: scale(1.05);
}

.member-table button:last-of-type {
  background-color: #e74c3c;
  color: white;
}

.member-table button:last-of-type:hover {
  background-color: #c0392b;
  transform: scale(1.05);
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
  background-color: #95a5a6;
}
.package-button {
  margin-top: 24px;
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.package-button:hover {
  background-color: #3d8b40;
  transform: scale(1.05);
}

.nav-button-box {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}
</style>