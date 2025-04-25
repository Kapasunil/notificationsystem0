package com.example.com.service;

import com.example.com.domain.entity.OtpVerification;
import com.example.com.dto.OtpRequest;
import com.example.com.dto.OtpResponse;
import com.example.com.repository.OtpVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpVerificationRepository otpVerificationRepository;

    public OtpResponse generateAndSendOtp(OtpRequest request) {
        String phone = normalizePhone(request.getPhone());
        String otp = generateOtp();

        OtpVerification otpVerification = OtpVerification.builder()
                .phone(phone)
                .otp(otp)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(5)) // expires in 5 minutes
                .build();

        otpVerificationRepository.save(otpVerification);

        System.out.println("Sending OTP " + otp + " to phone number: " + phone);

        return OtpResponse.builder()
                .phone(phone)
                .otp(otp)
                .build();
    }

    public boolean validateOtp(String phone, String otp) {
        phone = normalizePhone(phone);
        List<OtpVerification> otpRecords = otpVerificationRepository.findAllByPhone(phone);

        if (otpRecords.isEmpty()) {
            System.out.println("No OTP records found for phone: " + phone);
            return false;
        }

//        Optional<OtpVerification> otpRecords=otpVerificationRepository.findAllByPhone(phone);
//
//        if (otpRecords.isEmpty()) {
//            System.out.println("No OTP records found for phone: " + phone);
//            return false;
//        }

        Optional<OtpVerification>latestValidOtpRecord=otpRecords.stream()
                .filter(record->record.getExpiresAt().isAfter(LocalDateTime.now()))
                .max(Comparator.comparing(OtpVerification::getCreatedAt));

        if (latestValidOtpRecord.isEmpty()) {
            System.out.println("No valid (not expired) OTP found for phone: "+phone);
            return false;
        }

        OtpVerification record = latestValidOtpRecord.get();

        boolean isOtpValid = record.getOtp().equals(otp);

        System.out.println("Validating OTP for phone: " + phone);
        System.out.println("Stored OTP: " + record.getOtp() + ", Expires At: " + record.getExpiresAt());

        return isOtpValid;
    }

    private String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000)); // 6-digit OTP
    }

    private String normalizePhone(String phone) {
        return phone.replaceAll("[^0-9]", "");
    }
}
