package edu.du.product.controller;

import edu.du.product.dto.TravelPackageListDTO;
import edu.du.product.entity.TravelPackage;
import edu.du.product.service.TravelPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/packages")
@RequiredArgsConstructor
public class TravelPackageController {
    private final TravelPackageService service;

    @GetMapping
    public ResponseEntity<Page<TravelPackageListDTO>> getPackages(
            @RequestParam(defaultValue = "id,desc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(service.getPackages(sort, page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<List<TravelPackageListDTO>> searchPackages(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String duration,
            @RequestParam(defaultValue = "id,desc") String sort
    ) {
        return ResponseEntity.ok(service.searchPackages(keyword, minPrice, maxPrice, duration,sort));
    }
    @GetMapping("/{id}")
    public ResponseEntity<TravelPackageListDTO> getPackageById(@PathVariable Long id) {
        return service.getPackageDetail(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePackage(
            @PathVariable Long id,
            @RequestBody TravelPackageListDTO dto) {
        service.updatePackage(id, dto);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
        service.deletePackage(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping
    public ResponseEntity<Long> createPackage(@RequestBody TravelPackageListDTO dto) {
        Long id = service.save(dto); // ID 반환 받음
        return ResponseEntity.ok(id); // ✅ 프론트로 ID 전달
    }

}