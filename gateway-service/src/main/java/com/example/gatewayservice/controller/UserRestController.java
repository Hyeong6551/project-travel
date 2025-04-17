package com.example.gatewayservice.controller;

import com.example.gatewayservice.dto.UpdateRequest;
import com.example.gatewayservice.entity.Member;
import com.example.gatewayservice.service.MemberService;
import jakarta.validation.Valid;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final MemberService memberService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public List<Member> getAllUsers() {
        return memberService.findAll();
    }

    @GetMapping("/{no}")
    public ResponseEntity<Member> getUserByNo(@PathVariable Long no) {
        Member member = memberService.getUserByNo(no);
        return member != null ? ResponseEntity.ok(member) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{no}")
    public ResponseEntity<?> updateUser(@PathVariable Long no, @Valid @RequestBody UpdateRequest updateMember) {
        updateMember.setNo(no);
        Map<String, String> errors = new HashMap<>();
        if (memberService.existsByEmailAndNoNot(updateMember.getEmail(), updateMember.getNo())) {
            errors.put("email", "이미 사용 중인 이메일입니다.");
        }
        if (memberService.existsByPhoneAndNoNot(updateMember.getPhone(), updateMember.getNo())) {
            errors.put("phone", "이미 사용 중인 번호입니다.");
        }
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        Member member = memberService.getUserByNo(updateMember.getNo());
        member.setName(updateMember.getName());
        member.setEmail(updateMember.getEmail());
        member.setPhone(updateMember.getPhone());
        member.setAddress(updateMember.getAddress());
        member.setPostcode(updateMember.getPostcode());
        member.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        if (!updateMember.getPassword().startsWith("$2a$")) {
            member.setPassword(passwordEncoder.encode(updateMember.getPassword()));
        }
        System.out.println(member);
        memberService.updateMember(member);
        return ResponseEntity.ok("회원 정보가 수정되었습니다.");
    }

    // 회원 탈퇴
    @DeleteMapping("/delete/{no}")
    public ResponseEntity<String> deleteUser(@PathVariable Long no) {
        memberService.deleteByNo(no);
        return ResponseEntity.ok("탈퇴되었습니다.");
    }

    @PutMapping("{no}")
    public ResponseEntity<Member> updateMember(@PathVariable Long no, @RequestBody UpdateRequest dto) {
        Member updated = memberService.updateMember(no, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("{no}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long no) {
        memberService.deleteByNo(no);
        return ResponseEntity.noContent().build();
    }
}
