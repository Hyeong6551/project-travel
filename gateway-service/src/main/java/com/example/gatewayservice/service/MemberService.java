package com.example.gatewayservice.service;

import com.example.gatewayservice.dto.RegisterRequest;
import com.example.gatewayservice.dto.UpdateRequest;
import com.example.gatewayservice.entity.Member;
import com.example.gatewayservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public Optional<Member> findById(String id) {
        return memberRepository.findById(id);
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> findByPhone(String phone) {
        return memberRepository.findByPhone(phone);
    }

    public void save(Member member) {
        memberRepository.save(member);
    }

    public Member getUserByNo(Long no) {
        return memberRepository.findByNo(no).get();
    }

    public boolean existsByEmailAndNoNot(String email, Long no) {
        return memberRepository.existsByEmailAndNoNot(email, no);
    }

    public boolean existsByPhoneAndNoNot(String phone, Long no) {
        return memberRepository.existsByPhoneAndNoNot(phone, no);
    }

    @Transactional
    public void updateMember(Member member) {
        memberRepository.updateMember(member);
    }

    @Transactional
    public void deleteByNo(Long no) {
        memberRepository.deleteByNo(no);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member updateMember(Long no, UpdateRequest dto) {
        Member member = memberRepository.findByNo(no)
                .orElseThrow(() -> new RuntimeException("해당 회원을 찾을 수 없습니다."));
        if (dto.getName() != null) {
            member.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            member.setEmail(dto.getEmail());
        }
        if (dto.getRole() != null) {
            member.setRole(dto.getRole());
        }
        return memberRepository.save(member);
    }
}
