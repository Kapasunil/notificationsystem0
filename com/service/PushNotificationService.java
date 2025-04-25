package com.example.com.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PushNotificationService {

    public static void sendPush(String deviceToken, String message) {
        // Simulate push notification logic (e.g., Firebase, OneSignal, etc.)
        log.info("Sending PUSH notification to deviceToken: {} with message: {}", deviceToken, message);

    }
}
