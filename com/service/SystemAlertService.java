package com.example.com.service;

import com.example.com.domain.entity.SystemAlerts;
import com.example.com.enums.NotificationStatus;
import com.example.com.enums.Severity;
import com.example.com.repository.SystemAlertsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SystemAlertService {

    private final SystemAlertsRepository alertsRepo;

    public List<SystemAlerts> getAllAlerts() {
        return alertsRepo.findAll();
    }

    public List<SystemAlerts> getAlertsByStatus(NotificationStatus status) {
        return alertsRepo.findByStatus(status);
    }

    public List<SystemAlerts> getAlertsBySeverity(Severity severity) {
        return alertsRepo.findBySeverity(severity);
    }

    public List<SystemAlerts> getAlertsByAdmin(Long adminId) {
        return alertsRepo.findByAdminId(adminId);
    }

    public SystemAlerts markAlertAsRead(Long alertId) {
        SystemAlerts alert = alertsRepo.findById(alertId).orElseThrow();
        alert.setStatus(NotificationStatus.READ);
        return alertsRepo.save(alert);
    }

    public SystemAlerts markAlertAsResolved(Long alertId) {
        SystemAlerts alert = alertsRepo.findById(alertId).orElseThrow();
        alert.setStatus(NotificationStatus.RESOLVED);
        return alertsRepo.save(alert);
    }

    public SystemAlerts createAlert(SystemAlerts alert) {
        return alertsRepo.save(alert);
    }
}