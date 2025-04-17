package com.example.gatewayservice.controller;

import com.example.gatewayservice.dto.LoginRequest;
import com.example.gatewayservice.dto.LoginResponse;
import com.example.gatewayservice.dto.RegisterRequest;
import com.example.gatewayservice.entity.Member;
import com.example.gatewayservice.service.AuthService;
import com.example.gatewayservice.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberService memberService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signin")
    public Mono<ResponseEntity<?>> signin(@Valid @RequestBody LoginRequest loginRequest) {
        return Mono.defer(() -> {
            Map<String, String> errors = new HashMap<>();

            try {
                if (!"".equals(loginRequest.getId()) && !memberService.findById(loginRequest.getId()).isPresent()) {
                    errors.put("id", "아이디가 존재하지 않습니다.");
                } else {
                    Member member = memberService.findById(loginRequest.getId()).get();
                    System.out.println("DB 저장 비번: " + member.getPassword());
                    System.out.println("입력된 비번: " + loginRequest.getPassword());
                    if (!"".equals(loginRequest.getPassword()) && !passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
                        errors.put("password", "비밀번호가 일치하지 않습니다.");
                    }
                }

                if (!errors.isEmpty()) {
                    return Mono.just(ResponseEntity.badRequest().body(errors));
                }

                return Mono.fromCallable(() -> {
                    LoginResponse response = authService.login(loginRequest);
                    return ResponseEntity.ok(response);
                });

            } catch (Exception e) {
                return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "로그인 중 오류가 발생했습니다.")));
            }
        });
    }


    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@Valid @RequestBody RegisterRequest registerRequest) {
        Map<String, String> errors = new HashMap<>();

        if (memberService.findById(registerRequest.getId()).isPresent()) {
            errors.put("id", "이미 사용중인 아이디 입니다.");
        }
        if (memberService.findByEmail(registerRequest.getEmail()).isPresent()) {
            errors.put("email", "이미 사용중인 이메일 입니다.");
        }
        if (memberService.findByPhone(registerRequest.getPhone()).isPresent()) {
            errors.put("phone", "이미 사용중인 전화번호 입니다.");
        }
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            errors.put("confirmPassword", "비밀번호가 일치하지 않습니다.");
        }

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Member member = new Member();
        member.setId(registerRequest.getId());
        member.setName(registerRequest.getName());
        member.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        member.setEmail(registerRequest.getEmail());
        member.setPhone(registerRequest.getPhone());
        member.setAddress(registerRequest.getAddress());
        member.setDetailAddress(registerRequest.getDetailAddress());
        member.setPostcode(registerRequest.getPostcode());
        member.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        member.setRole("USER");
        memberService.save(member);
        return ResponseEntity.ok(Map.of("message", "회원가입 성공"));
    }
} 