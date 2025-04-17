import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import PackageListView from '../views/PackageListView.vue'
import ProductDetail from '../views/ProductDetail.vue'
import SignUpView from "@/views/SignUpView.vue"
import SignInView from "@/views/SignInView.vue"
import Myinfo from "@/views/mypage/myinfo.vue"
import Mypage from "@/views/mypage/mypage.vue";
import Update from "@/views/mypage/update.vue";
import MyReservation from '@/views/mypage/reservation.vue'

import MemberList from '@/views/MemberList.vue'
import MemberEdit from '@/views/MemberEdit.vue'
import DestinationList from '@/views/DestinationList.vue'
import DestinationForm from "@/views/DestinationForm.vue"
import DestinationDetailForm from "@/views/DestinationDetailForm.vue";
const routes = [
    { path: '/', name: 'Home', component: HomeView },
    { path: '/package', name: 'PackageList', component: PackageListView },
    { path: '/package/detail/:id', name: 'ProductDetail', component: ProductDetail }, {
        path: '/signup',
        name: 'signup',
        component: SignUpView
    },
    {
        path: '/signin',
        name: 'signin',
        component: SignInView
    },
    {
        path: '/mypage',
        name: 'mypage',
        component: Mypage
    },
    {
        path: '/mypage/myinfo',
        name: 'myinfo',
        component: Myinfo
    },
    {
        path: '/mypage/myinfo/update',
        name: 'update',
        component: Update
    },
    {
        path: '/mypage/reservations',
        name: 'myreservation',
        component: MyReservation
    },

    {
        path: '/members',              // URL 경로: /members
        name: 'MaemberList',            // 라우트 이름 (내부 라우팅에 사용)
        component: MemberList          // 이 경로에서 보여줄 컴포넌트
    },
    {
        path: '/members/edit/:no',     // URL 경로: /members/edit/1
        name: 'MemberEdit',            // 라우트 이름
        component: MemberEdit          // 회원 수정 컴포넌트
    },
    {
        path: '/destination',
        name: 'DestinationList',
        component: DestinationList
    },
    {
        path: '/destination/new',
        name: 'DestinationForm',
        component: DestinationForm
    },
    {
        path: '/destination/edit/:id',
        name: 'DestinationEdit',
        component: () => import('@/views/DestinationEdit.vue')
    }
    ,
    {
        path: '/board',
        component: () => import('@/views/BoardView.vue')
    },
    {
        path: '/board/:id',
        component: () => import('@/views/BoardDetailView.vue')
    },
    {
        path: '/board/write',
        component: () => import('@/views/BoardWriteView.vue')
    },
    {
        path: '/board/edit/:id',
        component: () => import('@/views/BoardWriteView.vue') // 재활용
    },
    {
        path: '/admin/destination/:id/detail',
        name: 'DestinationDetailForm',
        component: DestinationDetailForm
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})
// 네비게이션 가드
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token')
    const role = localStorage.getItem('role')

    if (to.meta.requiresAuth && !token) {
        next('/signin')
    } else if (to.meta.requiresAdmin && role !== 'ADMIN') {
        next('/')
    } else {
        next()
    }
})
export default router
