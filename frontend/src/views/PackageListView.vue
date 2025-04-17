<template>
  <Header/>
  <NavBar/>
  <div class="hero-section text-center">
    <div class="container-fluid px-5">
      <h1 class="display-4 mb-4">특별한 여행을 시작하세요</h1>
      <p class="lead mb-4">전세계 아름다운 목적지로의 특별한 여행</p>

      <div class="row justify-content-center mb-4">
        <!-- 검색창 -->
        <!--        <div class="col-md-6">-->
        <!--          <div class="search-section">-->
        <!--            <div class="input-group">-->
        <!--              <input-->
        <!--                  type="text"-->
        <!--                  v-model="searchKeyword"-->
        <!--                  class="form-control"-->
        <!--                  placeholder="여행지를 입력하세요 (예: 일본)"-->
        <!--                  @keypress.enter="search"-->
        <!--              />-->
        <!--              <button class="btn btn-primary" @click="search">-->
        <!--                <i class="fas fa-search"></i> 검색-->
        <!--              </button>-->
        <!--            </div>-->
        <!--          </div>-->
        <!--        </div>-->
      </div>

      <!-- 필터 -->
      <div class="row justify-content-center">
        <div class="col-md-8">
          <div class="filter-section">
            <div class="d-flex justify-content-between align-items-center">
              <h5 class="filter-title mb-3">가격 범위</h5>
              <a href="#" class="filter-reset" @click.prevent="resetFilter">초기화</a>
            </div>
            <div class="price-inputs mb-3">
              <input
                  type="number"
                  class="form-control price-input"
                  v-model="minPrice"
                  placeholder="최소 가격"
              />
              <span>~</span>
              <input
                  type="number"
                  class="form-control price-input"
                  v-model="maxPrice"
                  placeholder="최대 가격"
              />
            </div>
            <div class="price-range-text text-center">{{ priceRangeText }}</div>

            <div class="filter-section mt-4">
              <h5 class="filter-title mb-3">여행 기간</h5>
              <div class="btn-group flex-wrap" role="group">
                <button
                    v-for="d in durations"
                    :key="d"
                    type="button"
                    class="btn btn-outline-primary duration-btn m-1"
                    :class="{ active: selectedDuration === d }"
                    @click="selectDuration(d)"
                >
                  {{ d }}
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div>
    <div class="container mb-5">
      <div class="row">
        <!-- 목록 -->
        <div class="container">
          <div id="sortButtons" class="my-4">
            <button
                v-for="s in sortOptions"
                :key="s.label"
                class="sort-btn"
                :class="{ active: currentSort === s.value }"
                @click="changeSort(s.value)"
            >
              {{ s.label }}
            </button>
          </div>

          <div class="row g-4 justify-content-center">
            <div class="col-md-3" v-for="pkg in packages" :key="pkg.id">
              <a :href="`/package/detail/${pkg.id}`" style="text-decoration: none; color: inherit;">
                <div class="package-card card h-100">
                  <div class="position-relative">
                    <img :src="pkg.imageUrl" class="card-img-top" :alt="pkg.title" />
                    <div class="price-tag">{{ pkg.price.toLocaleString() }}원</div>
                  </div>
                  <div class="card-body">
                    <h5 class="package-title">{{ pkg.title }}</h5>
                    <div class="price-highlight">{{ pkg.price.toLocaleString() }}원</div>
                    <p class="card-text text-muted">{{ pkg.description }}</p>
                    <div class="package-features">
                      <div class="mb-2">
                        <i class="fas fa-plane feature-icon"></i> {{ pkg.date }}
                      </div>
                    </div>
                  </div>
                </div>
              </a>
            </div>

            <div v-if="packages.length === 0" class="col-12 text-center">검색 결과가 없습니다.</div>
          </div>
        </div>

      </div>
    </div>
  </div>
  <Footer/>
</template>

<script setup>
import {ref, watch, onMounted, computed} from "vue"
import {useRoute} from "vue-router"
import axios from "axios"
import Header from "../components/Header.vue";
import NavBar from "../components/NavBar.vue";
import Footer from "../components/Footer.vue";

const packages = ref([])
const searchKeyword = ref("")
const minPrice = ref("")
const maxPrice = ref("")
const selectedDuration = ref("")
const currentSort = ref("id,desc")

const route = useRoute()

const durations = ["1박 2일", "2박 3일", "3박 4일", "7박 9일", "8박 10일", "11박 12일"]
const sortOptions = [
  {label: "최신순", value: "id,desc"},
  {label: "오래된순", value: "id,asc"},
  {label: "낮은 가격순", value: "price,asc"},
  {label: "높은 가격순", value: "price,desc"},
  {label: "가나다순", value: "title,asc"},
  {label: "reverse 가나다순", value: "title,desc"}
]

const fetchPackages = async () => {
  try {
    const params = {
      sort: currentSort.value
    }
    if (minPrice.value) params.minPrice = minPrice.value
    if (maxPrice.value) params.maxPrice = maxPrice.value
    if (searchKeyword.value) params.keyword = searchKeyword.value

    if (selectedDuration.value) params.duration = selectedDuration.value

    const res = await axios.get("http://localhost:8000/api/packages/search", {params})
    packages.value = res.data
  } catch (e) {
    console.error(e)
    alert("패키지 목록 불러오기 실패")
  }
}

const search = () => {
  fetchPackages()
}

const resetFilter = () => {
  minPrice.value = ""
  maxPrice.value = ""
  selectedDuration.value = ""
  fetchPackages()
}

const changeSort = (sort) => {
  currentSort.value = sort
  fetchPackages()
}

const selectDuration = (duration) => {
  selectedDuration.value = duration
  fetchPackages()
}

watch([minPrice, maxPrice], () => {
  fetchPackages()
})

onMounted(() => {
  // 검색어가 있으면 자동 검색
  if (route.query.keyword) {
    searchKeyword.value = route.query.keyword
  }
  fetchPackages()
})

const priceRangeText = computed(() => {
  if (minPrice.value && maxPrice.value) {
    return `${parseInt(minPrice.value).toLocaleString()}원 ~ ${parseInt(maxPrice.value).toLocaleString()}원`
  } else if (minPrice.value) {
    return `${parseInt(minPrice.value).toLocaleString()}원 이상`
  } else if (maxPrice.value) {
    return `${parseInt(maxPrice.value).toLocaleString()}원 이하`
  } else {
    return ""
  }
})
</script>

<style scoped src="../assets/packagelist.css"></style>
