package edu.du.adminservice.service;


import edu.du.adminservice.dto.DetailRequestDTO;
import edu.du.adminservice.dto.TravelPackageListDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TravelProductProxyService {
    private final RestTemplate restTemplate;

    public List<TravelPackageListDTO> getAllPackages() {
        String url = "http://travel-product-service/packages/search";
        // keyword, minPrice, maxPrice, duration 없이 전체 조회
        ResponseEntity<List<TravelPackageListDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TravelPackageListDTO>>() {}
        );
        return response.getBody();
    }
    public void updatePackage(Long id, TravelPackageListDTO dto) {
        restTemplate.put(
                "http://travel-product-service/packages/" + id,
                dto
        );
    }

    public TravelPackageListDTO getPackageById(Long id) {
        return restTemplate.getForObject(
                "http://travel-product-service/packages/" + id,
                TravelPackageListDTO.class
        );
    }


    public void deletePackage(Long id) {
        restTemplate.delete("http://travel-product-service/packages/" + id);
    }
    public Long createPackage(TravelPackageListDTO dto) {
        return restTemplate.postForObject(
                "http://travel-product-service/packages",
                dto,
                Long.class // travel-product-service에서 ID를 응답해야 함
        );
    }
    public void createDetail(DetailRequestDTO dto) {
        log.info("Detail-page 서비스로 전송하는 데이터: {}", dto);
        log.info("이미지 URL: {}", dto.getImageUrl());
        restTemplate.postForObject("http://detail-page/packages/detail/new", dto, Void.class);
    }
}

