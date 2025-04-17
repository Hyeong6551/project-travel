package edu.du.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title; //제목
    private String description; //내용
    private int price; //가격
//    private String flightInfo; //항공
//    private String hotelInfo; // 숙소 이름
//    private String tourInfo; // 투어 정보
    private String imageUrl;
    private String date;
}