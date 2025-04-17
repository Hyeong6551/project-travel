package edu.du.detailpage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "edu.du") // ⬅️ 추가
public class DetailPageApplication {

    public static void main(String[] args) {
        SpringApplication.run(DetailPageApplication.class, args);
    }

}
