package edu.du.adminservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * ✅ AdminServiceApplication: 관리자 서비스의 메인 클래스
 * 이 클래스는 스프링 부트를 실행하고, 해당 애플리케이션을 시작하는 진입점입니다.
 */

@SpringBootApplication
// 🧩 이 애노테이션은 @Configuration, @EnableAutoConfiguration, @ComponentScan을 합친 것
// 즉, 설정 + 빈 자동 등록 + 컴포넌트 스캔 기능을 모두 포함

@EnableDiscoveryClient
// ✅ Eureka 또는 Consul 등 서비스 디스커버리 서버에 이 서비스를 등록하겠다는 의미
// 🔍 MSA 환경에서 마이크로서비스가 Eureka 서버에 등록되어 다른 서비스들과 통신할 수 있도록 함

public class AdminServiceApplication {

    /**
     * ✅ main(): 애플리케이션 실행을 위한 진입 메서드
     * SpringApplication.run(...)을 통해 Spring Boot 애플리케이션을 시작
     */
    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApplication.class, args);
    }

}