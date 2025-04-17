package edu.du.detailpage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Detail {
    private Long id;
    private String title;
    private String description;

    private String imageUrl;
    private int price;
    private String date;
}
