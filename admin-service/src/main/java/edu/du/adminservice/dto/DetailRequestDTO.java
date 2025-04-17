package edu.du.adminservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailRequestDTO {
    private Long id;
    private String description;
    private String imageUrl;
    private String date;
}
