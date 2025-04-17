package com.example.gatewayservice.controller;

import com.example.gatewayservice.entity.Member;
import com.example.gatewayservice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/users")
    public ResponseEntity<List<Member>> getAllUsers() {
        return ResponseEntity.ok(memberRepository.findAll());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody Member member) {
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        existingMember.setId(member.getId());
        existingMember.setEmail(member.getEmail());
        existingMember.setRole(member.getRole());

        memberRepository.save(existingMember);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        memberRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 