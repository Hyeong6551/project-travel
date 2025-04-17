package com.example.gatewayservice.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();

        // 관리자 관련 경로가 아닌 경우 필터링하지 않음
        if (!path.startsWith("/admin") && !path.startsWith("/api/admin")) {
            return true;
        }

        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

        if (role == null || !"ADMIN".equals(role)) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
} 