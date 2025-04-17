package com.example.gatewayservice.repository;

import com.example.gatewayservice.dto.RegisterRequest;
import com.example.gatewayservice.dto.UpdateRequest;
import com.example.gatewayservice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(String id);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByPhone(String Phone);

    Optional<Member> findByNo(Long no);

    void deleteByNo(Long no);

    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.no = :#{#member.no}, " +
            "m.password = :#{#member.password}," +
            "m.name = :#{#member.name}, " +
            "m.email = :#{#member.email}, " +
            "m.phone = :#{#member.phone}, " +
            "m.address = :#{#member.address}, " +
            "m.postcode = :#{#member.postcode} " +
            "WHERE m.no = :#{#member.no}")
    void updateMember(Member member);

    boolean existsByEmailAndNoNot(String email, Long no);
    boolean existsByPhoneAndNoNot(String phone, Long no);
}