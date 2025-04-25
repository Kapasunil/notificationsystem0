package com.example.com.dto;

import com.example.com.enums.RideStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Request to book a ride")
public class RideBookingRequest {

    @Schema(description = "UUID of the user booking the ride", example = "d9f1c7c2-15ec-42a4-8a7f-b6d23f0130e6", required = true)
    private UUID userId;

    @Schema(description = "UUID of the assigned driver", example = "a1b2c3d4-5678-90ab-cdef-1234567890ab", required = true)
    private UUID driverId;

    @Schema(description = "Pickup location", example = "KBHP, Hyderabad", required = true)
    private String pickup;

    @Schema(description = "Drop-off location", example = "KBHP, Hyderabad", required = true)
    private String drop;

    @Schema(description = "Indicates whether the ride includes a parcel", example = "True")
    private boolean isParcel;

    @Schema(description = "Current status of the ride", example = "ARRIVED")
    private RideStatus status;
}
