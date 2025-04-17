package edu.du.adminservice.service;

import edu.du.adminservice.dto.MemberDto;
import edu.du.adminservice.entity.Member;
import edu.du.adminservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final RestTemplate restTemplate;
    private final MemberRepository memberRepository;

    public List<Member> findAll() {
        ResponseEntity<List<Member>> response = restTemplate.exchange(
                "http://user-service/api/user",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Member>>() {
                }
        );
        return response.getBody();
    }

    public Member save(long no, MemberDto dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MemberDto> request = new HttpEntity<>(dto, headers);

        ResponseEntity<Member> response = restTemplate.exchange(
                "http://user-service/api/user/" + no,
                HttpMethod.PUT,
                request,
                Member.class
        );
        return response.getBody();
    }

    public ResponseEntity<Member> findById(Long no) {
        Member response = restTemplate.getForObject("http://user-service/api/user/" + no, Member.class);

        Member member = new Member();
        member.setId(response.getId());
        member.setName(response.getName());
        member.setCreatedAt(response.getCreatedAt());
        member.setRole(response.getRole());
        member.setEmail(response.getEmail());

        return ResponseEntity.ok(member);
    }

    public void deleteUser(Long id) {
        restTemplate.exchange(
                "http://user-service/api/user/" + id,
                HttpMethod.DELETE,
                null,
                Void.class
        );
    }
}