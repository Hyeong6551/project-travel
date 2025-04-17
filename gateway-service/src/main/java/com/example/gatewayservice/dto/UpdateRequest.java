package com.example.gatewayservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UpdateRequest {
    private long no;
    @Size(min = 7, message = "{error.update.password}")
    private String password;
    @Size(min = 2, message = "{error.update.name}")
    private String name;
    @Email(message = "{error.update.email}")
    private String email;
    @Size(min = 10, max = 11, message = "{error.update.phone}")
    @Pattern(regexp = "^[0-9]*$", message = "{error.update.phone}")
    private String phone;
    private String address;
    private String detailAddress;
    private String postcode;
    private Timestamp updatedAt;
    private String role;
}
