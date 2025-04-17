// 📦 현재 클래스가 속한 패키지를 명시
package edu.du.adminservice.controller;

// ✅ 필요한 클래스들을 import
import edu.du.adminservice.dto.DetailRequestDTO;
import edu.du.adminservice.dto.TravelPackageListDTO;
import edu.du.adminservice.entity.Destination;                // 여행지 정보를 담는 엔티티 클래스
import edu.du.adminservice.repository.DestinationRepository;  // DB 접근을 위한 Repository (JPA)

import edu.du.adminservice.service.TravelProductProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



// ✅ 이 클래스는 REST API 컨트롤러 역할을 함 (JSON 반환)
@RestController

// ✅ 공통 URL 경로 설정: 모든 요청은 /api/destination 으로 시작
@RequestMapping("/admins/destination")
@RequiredArgsConstructor
public class DestinationController {

    // 🔧 DB에 접근하기 위한 JPA Repository 선언 (의존성 주입)
    private final DestinationRepository destinationRepository;
    private final TravelProductProxyService proxyService;
    // 🔧 생성자 주입 방식으로 repository 연결


    @GetMapping("/external-packages")
    public ResponseEntity<List<TravelPackageListDTO>> getExternalPackages() {
        List<TravelPackageListDTO> packages = proxyService.getAllPackages();
        return ResponseEntity.ok(packages);
    }

    @PostMapping
    public Destination create(@RequestBody Destination destination) {
        return destinationRepository.save(destination); // DB에 저장
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        destinationRepository.deleteById(id); // 해당 id 삭제
        return ResponseEntity.noContent().build(); // 응답: 204 No Content
    }

    @GetMapping("/external-packages/{id}")
    public ResponseEntity<TravelPackageListDTO> getExternalPackage(@PathVariable Long id) {
        TravelPackageListDTO dto = proxyService.getPackageById(id);
        return ResponseEntity.ok(dto);
    }
//    @GetMapping("/{id}")
//    public ResponseEntity<Destination> findById(@PathVariable Long id) {
//        return destinationRepository.findById(id)
//                .map(ResponseEntity::ok) // 데이터 있으면 200 OK와 함께 반환
//                .orElse(ResponseEntity.notFound().build()); // 없으면 404 Not Found
//    }

    @PutMapping("/external-packages/{id}")
    public ResponseEntity<Void> updateExternalPackage(
            @PathVariable Long id,
            @RequestBody TravelPackageListDTO dto
    ) {
        proxyService.updatePackage(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Destination> updateDestination(@PathVariable Long id, @RequestBody Destination updated) {
        return destinationRepository.findById(id)
                .map(existing -> {
                    // 기존 값에 새로운 데이터 덮어쓰기
                    existing.setName(updated.getName());
                    existing.setDescription(updated.getDescription());
                    existing.setLocation(updated.getLocation());
                    existing.setPrice(updated.getPrice());
                    existing.setDuration(updated.getDuration());
                    existing.setIncluded(updated.getIncluded());
                    existing.setImageUrl(updated.getImageUrl());

                    // 변경된 데이터를 DB에 저장 후 응답
                    Destination saved = destinationRepository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build()); // 해당 ID 없으면 404 응답
    }


    @DeleteMapping("/external-packages/{id}")
    public ResponseEntity<Void> deleteExternalPackage(@PathVariable Long id) {
        // travel-product-service 데이터도 삭제
        proxyService.deletePackage(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/external-packages")
    public ResponseEntity<Long> createExternalPackage(@RequestBody TravelPackageListDTO dto) {
        Long savedId = proxyService.createPackage(dto);  // ID 받아옴
        return ResponseEntity.ok(savedId);               // 프론트로 응답
    }


    @PostMapping("/{id}/detail")
    public ResponseEntity<Void> createDetail(@PathVariable Long id, @RequestBody DetailRequestDTO dto) {
        // ID를 명확히 설정 (URL 파라미터를 DTO에 반영)
        dto.setId(id);
        proxyService.createDetail(dto);  // detail-page 서비스로 전송
        return ResponseEntity.ok().build();
    }
}