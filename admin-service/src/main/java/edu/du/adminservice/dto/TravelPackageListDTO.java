package edu.du.adminservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelPackageListDTO {
    private Long id;
    private String title;
    private String description;
    private int price;
    private String imageUrl;
    private String date;
}
