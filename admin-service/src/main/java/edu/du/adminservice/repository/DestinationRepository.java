package edu.du.adminservice.repository;

import edu.du.adminservice.entity.Destination;        // 여행지 엔티티 import
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ✅ DestinationRepository는 여행지(Destination) 엔티티를 위한 데이터베이스 접근 인터페이스입니다.
 * ✅ JpaRepository를 상속받아 CRUD(생성, 조회, 수정, 삭제) 기능을 자동으로 사용할 수 있습니다.
 *
 * JpaRepository<Destination, Long>
 *  - Destination: 어떤 엔티티를 다룰지 명시
 *  - Long: 엔티티의 기본 키 타입 (id의 타입)
 *
 * 이 인터페이스는 구현체를 따로 만들지 않아도,
 * Spring Data JPA가 런타임에 자동으로 구현체를 생성해줍니다.
 */
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    // 특별한 메서드를 추가하지 않아도 기본적인 CRUD 기능이 모두 제공됩니다.

    // 예) findById(), findAll(), save(), deleteById() 등
}