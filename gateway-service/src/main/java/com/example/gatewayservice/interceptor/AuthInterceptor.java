package com.example.gatewayservice.interceptor;

import com.example.gatewayservice.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();

        // 로그인, 회원가입, 정적 리소스는 인증 제외
        if (path.startsWith("/signin") || path.startsWith("/signup") ||
                path.startsWith("/css") || path.startsWith("/js") ||
                path.startsWith("/images") || path.startsWith("/h2-console")) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (!jwtUtil.isTokenExpired(token)) {
                return true;
            }
        }

        response.sendRedirect("/signin");
        return false;
    }
} 