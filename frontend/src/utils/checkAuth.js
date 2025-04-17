import { parseJwt } from './jwtUtils'

export function checkAuth(router) {
    const token = localStorage.getItem('token') // or sessionStorage
    if (!token) {
        alert('로그인이 필요합니다.')
        router.push('/signin')
        return false
    }

    try {
        const payload = parseJwt(token)
        if (!payload || !payload.id) {
            alert('유효하지 않은 토큰입니다.')
            router.push('/signin')
            return false
        }
        return true
    } catch (e) {
        console.error('JWT 파싱 에러:', e)
        router.push('/signin')
        return false
    }
}