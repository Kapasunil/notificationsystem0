package com.example.com.controller;

import com.example.com.dto.OtpRequest;
import com.example.com.dto.OtpResponse;
import com.example.com.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/send")
    public ResponseEntity<OtpResponse> sendOtp(@RequestBody OtpRequest request) {
        if (request.getPhone() == null || request.getPhone().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        OtpResponse response = otpService.generateAndSendOtp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateOtp(@RequestBody OtpRequest request) {
        try {
            boolean isValid = otpService.validateOtp(request.getPhone(), request.getOtp());
            if (isValid) {
                return ResponseEntity.ok("OTP is valid.");
            } else {
                return ResponseEntity.status(400).body("Invalid OTP.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred during OTP validation.");
        }
    }
}
