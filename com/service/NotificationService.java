package com.example.com.service;

import com.example.com.domain.entity.Notification;
import com.example.com.domain.entity.User;
import com.example.com.enums.NotificationStatus;
import com.example.com.enums.NotificationType;
import com.example.com.repository.NotificationRepository;
import com.example.com.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepo;
    private final UserRepository userRepo;
    private final TwilioService twilioService;
    private final EmailService emailService;
    private final PushNotificationService pushNotificationService;

    public Notification getNotificationById(UUID notificationId) {
        return notificationRepo.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + notificationId));
    }

    public void sendNotification(User recipient, NotificationType type, String message) {
        Notification notification = Notification.builder()
                .user(recipient)
                .type(type)
                .message(message)
                .status(NotificationStatus.UNREAD)
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepo.save(notification);

        switch (type) {
            case SMS -> {
                if (recipient.getPhone() != null) {
                    twilioService.sendSms(recipient.getPhone(), message);
                }
            }
            case EMAIL -> {
                if (recipient.getEmail() != null) {
                    emailService.sendEmail(recipient.getEmail(), "New Notification", message);
                }
            }
            case PUSH -> {
                if (recipient.getDeviceToken() != null) {
                    PushNotificationService.sendPush(recipient.getDeviceToken(), message);
                }
            }
        }
    }


    public List<Notification> getAllNotifications(UUID userId) {
        User user = userRepo.findById(userId).orElseThrow();
        return notificationRepo.findByUser(user);
    }

    public List<Notification> getUnreadNotifications(UUID userId) {
        User user = userRepo.findById(userId).orElseThrow();
        return notificationRepo.findByUserAndStatus(user, NotificationStatus.UNREAD);
    }

    public Notification markAsRead(UUID notificationId) {
        Notification notification = notificationRepo.findById(notificationId).orElseThrow();
        notification.setStatus(NotificationStatus.READ);
        return notificationRepo.save(notification);
    }

    public List<Notification> getByStatus(NotificationStatus status) {
        return notificationRepo.findByStatus(status);
    }

    public List<Notification> getByType(UUID userId, NotificationType type) {
        return notificationRepo.findByUser(userRepo.findById(userId).orElseThrow()).stream()
                .filter(n -> n.getType() == type)
                .toList();
    }
}
