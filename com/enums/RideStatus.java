package com.example.com.enums;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status of a ride")
public enum RideStatus {
    BOOKED,
    ARRIVED,
    STARTED,
    DELIVERED,
    COMPLETED
}
