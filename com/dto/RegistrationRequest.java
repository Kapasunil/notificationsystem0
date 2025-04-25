package com.example.com.dto;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String phone;
    private String email;
    private String fullName;
    private String password;
    private String otp;
}
