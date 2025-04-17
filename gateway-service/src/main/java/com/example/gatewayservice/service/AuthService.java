package com.example.gatewayservice.service;

import com.example.gatewayservice.dto.LoginRequest;
import com.example.gatewayservice.dto.LoginResponse;
import com.example.gatewayservice.entity.Member;
import com.example.gatewayservice.repository.MemberRepository;
import com.example.gatewayservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findById(loginRequest.getId())
                .orElseThrow(() -> new RuntimeException("아이디가 존재하지 않습니다."));

        if (!passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.generateToken(member.getId());
        return new LoginResponse(token, member.getId(), member.getRole());
    }
} 