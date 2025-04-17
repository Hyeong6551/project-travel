

<template>
  <!-- ✅ 헤더 -->
  <Header/>
  <NavBar/>

  <div>
    <!-- 비주얼 배너 -->
    <section id="main-banner">
      <div class="swiper bannerSwiper">
        <div class="swiper-wrapper">
          <div class="swiper-slide" v-for="(slide, index) in bannerSlides" :key="index">
            <video autoplay loop muted playsinline>
              <source :src="slide.video" type="video/mp4" />
            </video>
            <div class="overlay">
              <div class="content top-left">
                <h2 class="text-4xl font-bold mb-2">{{ slide.title }}</h2>
                <p class="text-xl">{{ slide.description }}</p>
              </div>
            </div>
          </div>
        </div>
        <div class="swiper-button-next text-white"></div>
        <div class="swiper-button-prev text-white"></div>
        <div class="swiper-pagination"></div>
      </div>
    </section>


    <!-- 추천 여행 상품 슬라이더 -->
    <section id="recommended" style="margin: 60px auto; max-width: 1200px;">
      <h2 style="font-size: 30px;">👉 여행 고수의 스마트한 추천</h2>
      <p style="margin: 12px 0;">비수기 시즌, 덜 붐비고 더 합리적인 상품만 선별했어요.</p>

      <div class="swiper mySwiper" style="position: relative;">
        <div class="swiper-wrapper">
          <div class="swiper-slide" v-for="(product, index) in products" :key="index">
            <div class="product-card">
              <img :src="product.image" />
              <div class="product-info">
                <h3>{{ product.title }}</h3>
                <p class="price">✅ {{ product.price }}</p>
              </div>
            </div>
          </div>
        </div>
        <div class="swiper-button-prev"></div>
        <div class="swiper-button-next"></div>
      </div>
    </section>
    <Footer/>

  </div>
</template>

<script setup>

import {onMounted} from 'vue'
import Swiper from 'swiper/bundle'
import 'swiper/css/bundle'
import Header from '@/components/Header.vue'
import NavBar from '@/components/NavBar.vue'
import Footer from "@/components/Footer.vue"


const bannerSlides = [
  {video: '/videos/japan.mp4', title: '벚꽃과 함께하는 일본 여행', description: '지금이 딱 좋은 계절'},
  {video: '/videos/jeju.mp4', title: '푸른 바다와 초록의 섬, 제주도', description: '국내 최고의 자연여행지'},
  {video: '/videos/china.mp4', title: '위대한 유산과 함께하는 중국 여행', description: '만리장성과 황궁의 장엄함을 만나는 순간'},
]

const products = [
  {image: '/images/product1.png', title: '[일본] 도쿄 3박 4일', price: '499,000원~'},
  {image: '/images/product2.png', title: '[제주도] 올인클루시브', price: '299,000원~'},
  {image: '/images/product3.png', title: '[중국] 일주 7일', price: '1,499,000원~'},
  {image: '/images/product4.png', title: '[스위스] 일주 14일', price: '3,499,000원~'},
  {image: '/images/product5.png', title: '[미국] 일주 14일', price: '5,899,000원~'},
  {image: '/images/product6.png', title: '[영국] 일주 7일', price: '3,499,000원~'},
]

onMounted(() => {
  new Swiper('.bannerSwiper', {
    loop: true,
    effect: 'fade', // 페이드 효과
    fadeEffect: {
      crossFade: true
    },
    autoplay: {
      delay: 7000, // 전환 간격 (ms)
      disableOnInteraction: false,
    },
    pagination: {
      el: '.swiper-pagination',
      clickable: true,
    },
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    }
  })

  new Swiper('.mySwiper', {
    slidesPerView: 3,
    spaceBetween: 24,
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },
    loop: true,
  })
})
</script>

<style scoped>
#main-banner {
  width: 100%;
  height: 540px;
  position: relative;
  overflow: hidden;
}

.bannerSwiper {
  width: 100%;
  height: 100%;
}

.bannerSwiper .swiper-slide {
  position: relative;
  width: 100%;
  height: 100%;
}

.bannerSwiper video {
  width: 100%;
  height: 100%;
  object-fit: cover;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 0;
}

.overlay {
  position: absolute;
  inset: 0;
  background-color: rgba(0, 0, 0, 0.4);
  z-index: 1;
  display: flex;
  align-items: center;
}

.content.top-left {
  position: absolute;
  top: 40px;
  left: 40px;
  color: white;
  text-align: left;
  z-index: 2;
}

#main-banner {
  width: 100%;
  height: 540px;
  position: relative;
  overflow: hidden;
}

.bannerSwiper video {
  width: 100%;
  height: 100%;
  object-fit: cover;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 0;
}

.overlay {
  position: absolute;
  inset: 0;
  background-color: rgba(0, 0, 0, 0.4);
  z-index: 1;
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
}

.content.top-left {
  padding: 40px;
  color: white;
  z-index: 2;
}

.product-card {
  width: 380px;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
  transition: transform 0.3s ease;
}
.product-card:hover {
  transform: scale(1.03); /* 약간 확대 */
}

.product-card img {
  width: 100%;
  height: 220px;
  object-fit: cover;
  transition: transform 0.3s ease;
}
.product-card:hover img {
  transform: scale(1.05); /* 이미지도 확대 */
}

.product-info {
  padding: 16px;
}

.footer {
  background-color: #f9f9f9;
  border-top: 1px solid #ddd;
  padding: 40px 0;
  font-size: 14px;
  color: #666;
}

.footer-container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: flex-start;
}

.footer-container h3 {
  font-size: 20px;
  color: #028267;
}

.footer-container h4 {
  font-weight: bold;
  margin-bottom: 10px;
}

.footer-container ul {
  list-style: none;
  padding: 0;
  line-height: 2;
}

.footer-bottom {
  text-align: center;
  margin-top: 30px;
  color: #aaa;
}
.price {
  color: #028267;
  font-weight: bold;
  font-size: 16px;
  margin-top: 8px;
}
</style>
