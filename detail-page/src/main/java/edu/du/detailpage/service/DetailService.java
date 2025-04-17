package edu.du.detailpage.service;

import edu.du.detailpage.dto.Detail;
import edu.du.detailpage.entity.DetailEntity;
import edu.du.detailpage.repository.DetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class DetailService {

    private final RestTemplate restTemplate;
    private final DetailRepository detailRepository;

    public Detail getMergedDetail(Long id) {
        // 외부 서비스(title, price)
        Detail basic = restTemplate.getForObject(
                "http://travel-product-service/packages/" + id,
                Detail.class
        );

        // 내부 DB(description, date, image_url)
        DetailEntity extra = detailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("없음"));

        // 조합
        Detail result = new Detail();
        result.setId(id);
        result.setTitle(basic.getTitle());       // 외부
        result.setPrice(basic.getPrice());       // 외부
        result.setDescription(extra.getDescription()); // 내부
        result.setImageUrl(extra.getImageUrl());      // 내부
        result.setDate(extra.getDate());               // 내부

        return result;
    }

    public void saveDetail(Detail dto) {
        DetailEntity entity = new DetailEntity();

        entity.setId(dto.getId()); // 패키지 ID와 동일하게 설정
        entity.setDescription(dto.getDescription());
        entity.setImageUrl(dto.getImageUrl());
        entity.setDate(dto.getDate());

        detailRepository.save(entity);
    }
}
