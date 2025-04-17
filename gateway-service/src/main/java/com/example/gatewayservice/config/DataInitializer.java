package com.example.gatewayservice.config;

import com.example.gatewayservice.entity.Member;
import com.example.gatewayservice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private MemberRepository memberRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) {
        // 테스트 사용자 생성
        if (memberRepository.count() == 0) {
            Member admin = new Member();
            admin.setId("admin");
            admin.setName("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@example.com");
            admin.setRole("ADMIN");
            admin.setPhone("01012345678");
            memberRepository.save(admin);

            Member member1 = new Member();
            member1.setId("user1");
            member1.setName("user1");
            member1.setPassword(passwordEncoder.encode("user123"));
            member1.setEmail("user1@example.com");
            member1.setRole("USER");
            member1.setPhone("01011112222");
            memberRepository.save(member1);

            Member member2 = new Member();
            member2.setId("user2");
            member2.setName("user2");
            member2.setPassword(passwordEncoder.encode("user123"));
            member2.setEmail("user2@example.com");
            member2.setRole("USER");
            member2.setPhone("01033334444");
            memberRepository.save(member2);
        }
    }
} 