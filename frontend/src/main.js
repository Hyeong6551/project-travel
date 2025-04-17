import { createApp } from 'vue'
import './assets/main.css' // 기존 style.css 경로
import App from './App.vue'
import router from './router'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import { createPinia } from 'pinia' // ✅ Pinia import
import axios from 'axios'

// axios 기본 설정
axios.defaults.baseURL = 'http://localhost:8000'
axios.defaults.headers.common['Content-Type'] = 'application/json'

axios.interceptors.request.use(config => {
    const token = localStorage.getItem('token')
    if (token) {
        config.headers.Authorization = `Bearer ${token}`
    }
    return config
})

// 요청 인터셉터
axios.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token')
        if (token) {
            config.headers.Authorization = `Bearer ${token}`
        }
        return config
    },
    (error) => {
        return Promise.reject(error)
    }
)


// 응답 인터셉터
axios.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401) {
            localStorage.removeItem('token')
            router.push('/signin')
        }
        return Promise.reject(error)
    }
)

const app = createApp(App)


const pinia = createPinia()        // ✅ Pinia 인스턴스 생성
app.use(pinia)                     // ✅ 앱에 등록
app.use(router)
app.mount('#app')

createApp(App).use(router).mount('#app')
