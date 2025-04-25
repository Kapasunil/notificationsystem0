package com.example.com.service;

import com.example.com.domain.entity.OtpVerification;
import com.example.com.domain.entity.User;
import com.example.com.repository.OtpVerificationRepository;
import com.example.com.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final OtpVerificationRepository otpRepo;
    private final TwilioService twilioService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public User registerUser(User user) {
        User user1 = userRepository.save(user);
        return user1;
    }

    public void sendOtp(String phone) {
        String otp = String.valueOf(new Random().nextInt(899999) + 100000);

        OtpVerification otpVerification = OtpVerification.builder()
                .phone(phone)
                .otp(otp)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .build();

        List<OtpVerification> existingOtps = otpRepo.findAllByPhone(phone);
        if (!existingOtps.isEmpty()) {
            otpRepo.deleteAll(existingOtps);
        }

        otpRepo.save(otpVerification);
        twilioService.sendSms(phone, "Your OTP is: " + otp);
    }
    public boolean verifyOtp(String phone, String otp) {
        List<OtpVerification> records = otpRepo.findAllByPhone(phone);

        return records.stream()
                .filter(record -> record.getOtp().equals(otp))
                .filter(record -> record.getExpiresAt().isAfter(LocalDateTime.now()))
                .findFirst()
                .isPresent();
    }
}
