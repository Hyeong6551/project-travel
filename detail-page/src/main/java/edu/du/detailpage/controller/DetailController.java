package edu.du.detailpage.controller;

import edu.du.detailpage.dto.Detail;
import edu.du.detailpage.entity.DetailEntity;
import edu.du.detailpage.service.DetailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/packages/detail") // 👈 경로를 Gateway와 일치시키기
@RequiredArgsConstructor
@Slf4j
public class DetailController {

    private final DetailService detailService;


    @GetMapping("/{id}")
    public ResponseEntity<?> getPackageDetail(@PathVariable Long id) {
        Detail dto = detailService.getMergedDetail(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createDetail(@RequestBody Detail dto) {
        log.info("받은 Detail 데이터: {}", dto);
        log.info("이미지 URL: {}", dto.getImageUrl());
        detailService.saveDetail(dto);  // Service에 위임
        return ResponseEntity.ok().build();
    }

}
