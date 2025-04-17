package com.example.gatewayservice.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class RegisterRequest {
    private long no;
    @Size(min = 5, max = 15, message = "{error.signup.id}")
    private String id;
    @Size(min = 7, message = "{error.signup.password}")
    private String password;
    private String confirmPassword;
    @Size(min = 2, message = "{error.signup.name}")
    private String name;
    @Email(message = "{error.signup.email}")
    private String email;
    @Size(min = 10, max = 11, message = "{error.signup.phone}")
    @Pattern(regexp = "^[0-9]*$", message = "{error.signup.phone}")
    private String phone;
    private String address;
    private String detailAddress;
    private String postcode;
    private Timestamp createdAt;
    private Timestamp updatedAt;
} 