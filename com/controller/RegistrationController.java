package com.example.com.controller;

import com.example.com.domain.entity.User;
import com.example.com.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestParam String phone) {
        registrationService.sendOtp(phone);
        return ResponseEntity.ok("OTP sent successfully");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        registrationService.registerUser(user);

        return ResponseEntity.ok("User registered successfully");
    }

}
