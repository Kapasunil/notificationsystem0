package com.example.com.service;

import com.example.com.domain.entity.UserPreference;
import com.example.com.repository.UserPreferenceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserPreferenceService {

    private final UserPreferenceRepository preferenceRepository;

    public UserPreference getPreferences(UUID userId) {
        return preferenceRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Preferences not found for user"));
    }

    @Transactional
    public UserPreference createOrUpdatePreferences(UUID userId, Boolean sms, Boolean email, Boolean push) {
        UserPreference preference = preferenceRepository.findByUserId(userId)
                .orElse(UserPreference.builder().userId(userId).build());

        if (sms != null) preference.setSmsEnabled(sms);
        if (email != null) preference.setEmailEnabled(email);
        if (push != null) preference.setPushEnabled(push);

        preference.setUpdatedAt(LocalDateTime.now());
        return preferenceRepository.save(preference);
    }
}
