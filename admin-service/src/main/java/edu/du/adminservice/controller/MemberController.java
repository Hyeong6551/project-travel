package edu.du.adminservice.controller;

import edu.du.adminservice.dto.MemberDto;
import edu.du.adminservice.entity.Member;
import edu.du.adminservice.repository.MemberRepository;
import edu.du.adminservice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admins")
public class MemberController {

    private final MemberRepository service;

    @Autowired
    private MemberService memberService;

    public MemberController(MemberRepository service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Member> dto = memberService.findAll();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public Member save(@RequestBody Member member) {
        return service.save(member);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPackageDetail(@PathVariable Long id) {
        ResponseEntity<Member> dto = memberService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> update(@PathVariable Long id, @RequestBody MemberDto member) {
        Member updatedMember = memberService.save(id, member);
        member.setId(id);
        return ResponseEntity.ok(updatedMember);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        memberService.deleteUser(id);  // admin의 서비스 호출
        return ResponseEntity.noContent().build();
    }
}