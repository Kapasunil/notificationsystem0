package com.example.com.controller;

import com.example.com.domain.entity.UserPreference;
import com.example.com.service.UserPreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/preferences")
@RequiredArgsConstructor
public class UserPreferenceController {

    private final UserPreferenceService preferenceService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserPreference> getPreferences(@PathVariable UUID userId) {
        return ResponseEntity.ok(preferenceService.getPreferences(userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<UserPreference> setPreferences(
            @PathVariable UUID userId,
            @RequestParam(required = false) Boolean sms,
            @RequestParam(required = false) Boolean email,
            @RequestParam(required = false) Boolean push
    ) {
        return ResponseEntity.ok(preferenceService.createOrUpdatePreferences(userId, sms, email, push));
    }
}
