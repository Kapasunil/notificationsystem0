package com.example.com.service;

import com.example.com.domain.entity.Notification;
import com.example.com.domain.entity.NotificationLog;
import com.example.com.enums.NotificationStatus;
import com.example.com.enums.NotificationType;
import com.example.com.repository.NotificationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationLogService {

    private final NotificationLogRepository notificationLogRepository;

    public void logNotification(Notification notification, NotificationType channel, NotificationStatus status, String response) {
        NotificationLog log = NotificationLog.builder()
                .notification(notification)
                .channel(channel)
                .status(status)
                .response(response)
                .sentAt(LocalDateTime.now())
                .build();
        notificationLogRepository.save(log);
    }

    public List<NotificationLog> getLogsByNotification(Notification notification) {
        return notificationLogRepository.findByNotification(notification);
    }

    public List<NotificationLog> getLogsByStatus(NotificationStatus status) {
        return notificationLogRepository.findByStatus(status);
    }

    public List<NotificationLog> getLogsByNotificationAndStatus(Notification notification, NotificationStatus status) {
        return notificationLogRepository.findByNotificationAndStatus(notification, status);
    }
}
