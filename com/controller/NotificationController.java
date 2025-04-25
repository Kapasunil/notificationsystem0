package com.example.com.controller;

import com.example.com.domain.entity.Notification;
import com.example.com.enums.NotificationStatus;
import com.example.com.enums.NotificationType;
import com.example.com.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getAllNotifications(@PathVariable UUID userId) {
        return ResponseEntity.ok(notificationService.getAllNotifications(userId));
    }

    @GetMapping("/{userId}/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable UUID userId) {
        return ResponseEntity.ok(notificationService.getUnreadNotifications(userId));
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Notification> markNotificationAsRead(@PathVariable UUID notificationId) {
        return ResponseEntity.ok(notificationService.markAsRead(notificationId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Notification>> getByStatus(@PathVariable NotificationStatus status) {
        return ResponseEntity.ok(notificationService.getByStatus(status));
    }

    @GetMapping("/{userId}/type/{type}")
    public ResponseEntity<List<Notification>> getByType(
            @PathVariable UUID userId,
            @PathVariable NotificationType type
    ) {
        return ResponseEntity.ok(notificationService.getByType(userId, type));
    }
}
