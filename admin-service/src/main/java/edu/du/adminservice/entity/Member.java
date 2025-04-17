package edu.du.adminservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;


@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String role;

    @CreationTimestamp
    private Timestamp createdAt;
}