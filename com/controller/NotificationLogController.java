package com.example.com.controller;

import com.example.com.domain.entity.Notification;
import com.example.com.domain.entity.NotificationLog;
import com.example.com.enums.NotificationStatus;
import com.example.com.service.NotificationLogService;
import com.example.com.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications/logs")
@RequiredArgsConstructor
public class NotificationLogController {

    private final NotificationLogService logService;
    private final NotificationService notificationService;

    @GetMapping("/by-notification/{notificationId}")
    public ResponseEntity<List<NotificationLog>> getLogsByNotification(@PathVariable UUID notificationId) {
        Notification notification = notificationService.getNotificationById(notificationId);
        return ResponseEntity.ok(logService.getLogsByNotification(notification));
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<NotificationLog>> getLogsByStatus(@RequestParam NotificationStatus status) {
        return ResponseEntity.ok(logService.getLogsByStatus(status));
    }
}
