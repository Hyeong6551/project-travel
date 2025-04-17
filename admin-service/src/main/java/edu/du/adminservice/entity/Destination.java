// ✅ 이 클래스는 JPA가 관리하는 엔티티로 DB의 "Destination" 테이블에 매핑됨
package edu.du.adminservice.entity;

import jakarta.persistence.*;                    // JPA 관련 어노테이션
import lombok.*;                                 // Lombok으로 코드 간결화 (Getter, Setter 등)
import org.hibernate.annotations.CreationTimestamp; // 자동 생성 시간 어노테이션

import java.sql.Timestamp;                      // 생성일자를 위한 클래스

// ✅ 이 클래스는 JPA 엔티티임을 선언
@Entity

// ✅ Lombok 어노테이션: 생성자, Getter, Setter, 빌더 등을 자동 생성
@Getter @Setter              // 모든 필드에 대한 Getter / Setter 생성
@NoArgsConstructor           // 기본 생성자 생성
@AllArgsConstructor          // 전체 필드를 매개변수로 받는 생성자 생성
@Builder                     // 빌더 패턴 생성 (객체를 유연하게 생성 가능)
public class Destination {

    // ✅ 기본 키(PK): ID (자동 증가)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에서 자동으로 ID 증가
    private Long id;

    // ✅ 여행지 이름 (예: 제주도 효도 여행)
    private String name;

    // ✅ 여행지 설명 (예: 부모님과 여유롭게 제주도 풀빌라에서 힐링!)
    private String description;

    // ✅ 여행지 위치 (예: 제주도, 강릉, 해외 등)
    private String location;

    // ✅ 등록 시간 (자동 생성됨)
    @CreationTimestamp // INSERT 시 자동으로 현재 시간 입력됨
    private Timestamp createdAt;

    // ✅ 이미지 URL 또는 Base64 문자열 (길이가 길 수 있으므로 TEXT 타입으로 지정)
    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    // ✅ 포함 사항 (예: 숙박, 항공, 조식 포함 등)
    private String included;

    // ✅ 여행 기간 (예: 3박 4일)
    private String duration;

    // ✅ 가격 (정수형, 단위: 원)
    private Integer price;
}