package com.example.com.controller;

import com.example.com.domain.entity.SystemAlerts;
import com.example.com.enums.NotificationStatus;
import com.example.com.enums.Severity;
import com.example.com.service.SystemAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class SystemAlertController {

    private final SystemAlertService alertService;

    @GetMapping
    public ResponseEntity<List<SystemAlerts>> getAllAlerts() {
        return ResponseEntity.ok(alertService.getAllAlerts());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<SystemAlerts>> getByStatus(@PathVariable NotificationStatus status) {
        return ResponseEntity.ok(alertService.getAlertsByStatus(status));
    }

    @GetMapping("/severity/{severity}")
    public ResponseEntity<List<SystemAlerts>> getBySeverity(@PathVariable Severity severity) {
        return ResponseEntity.ok(alertService.getAlertsBySeverity(severity));
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<SystemAlerts>> getByAdmin(@PathVariable Long adminId) {
        return ResponseEntity.ok(alertService.getAlertsByAdmin(adminId));
    }

    @PutMapping("/{alertId}/read")
    public ResponseEntity<SystemAlerts> markAsRead(@PathVariable Long alertId) {
        return ResponseEntity.ok(alertService.markAlertAsRead(alertId));
    }

    @PutMapping("/{alertId}/resolve")
    public ResponseEntity<SystemAlerts> markAsResolved(@PathVariable Long alertId) {
        return ResponseEntity.ok(alertService.markAlertAsResolved(alertId));
    }

    @PostMapping
    public ResponseEntity<SystemAlerts> createAlert(@RequestBody SystemAlerts alert) {
        return ResponseEntity.ok(alertService.createAlert(alert));
    }
}
