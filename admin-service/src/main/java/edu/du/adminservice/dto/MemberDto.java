package edu.du.adminservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDto {
    private Long id;
    private String name;
    private String email;
    private String role;
    private Timestamp createdAt;
}
