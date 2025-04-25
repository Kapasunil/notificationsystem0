package com.example.com.dto;

import com.example.com.enums.RideStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class RideBookingDTO {
    private UUID id;
    private UUID userId;
    private UUID driverId;
    private String pickupLocation;
    private String dropLocation;
    private boolean isParcel;
    private RideStatus status;
    private LocalDateTime bookingTime;
}
