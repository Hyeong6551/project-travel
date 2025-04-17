package com.example.gatewayservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private Long packageId;


    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Integer numberOfPeople;

    @Column(nullable = false)
    private Double totalPrice;
} 