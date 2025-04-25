package com.example.com.dto;

import com.example.com.enums.VehicleType;
import lombok.Data;

@Data
public class DriverRequest {
    private String email; // or use phone
    private String licenseNumber;
    private String vehicleNumber;
    private Double vehicleCapacity;
    private VehicleType vehicleType;
}
