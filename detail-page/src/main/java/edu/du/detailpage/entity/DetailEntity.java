package edu.du.detailpage.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
//@Table(name = "PackageDetail")
public class DetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(length = 8000)
    private String description;

    @Lob
    @Column(name = "image_url", columnDefinition = "CLOB")
    private String imageUrl;

    @Lob
    @Column(columnDefinition = "CLOB")
    private String date;

}
