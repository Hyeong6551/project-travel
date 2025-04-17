package edu.du.adminservice.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    @LoadBalanced // 💡 Eureka 서비스명으로 호출하려면 반드시 필요!
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
