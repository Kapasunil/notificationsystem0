package com.example.com.service;

import com.example.com.domain.entity.Driver;
import com.example.com.domain.entity.User;
import com.example.com.dto.DriverRequest;
import com.example.com.enums.VehicleType;
import com.example.com.repository.DriverRepository;
import com.example.com.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final UserRepository userRepository;
    private final DriverRepository driverRepository;

    public String createDriver(DriverRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().name().equals("DRIVER")) {
            throw new RuntimeException("User is not assigned DRIVER role");
        }

        Driver driver = Driver.builder()
                .user(user)
                .licenseNumber(request.getLicenseNumber())
                .vehicleNumber(request.getVehicleNumber())
                .vehicleCapacity(request.getVehicleCapacity())
                .vehicleType(request.getVehicleType())
                .build();

        driverRepository.save(driver);
        return "Driver profile created successfully!";
    }
}
