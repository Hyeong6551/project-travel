package com.example.gatewayservice.config;

import com.example.gatewayservice.interceptor.AdminInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**", "/api/admin/**")
                .excludePathPatterns("/admin/login");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // Vue 개발 서버 포트
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP method
                .allowCredentials(true) // 쿠키 인증 요청 허용
                .allowedHeaders("*") // 모든 헤더 허용
                .maxAge(3600); // pre-flight 요청을 1시간 동안 캐싱
    }
} 