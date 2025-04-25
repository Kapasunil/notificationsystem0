package com.example.com.controller;

import com.example.com.domain.entity.RideBooking;
import com.example.com.dto.RideBookingDTO;
import com.example.com.enums.RideStatus;
import com.example.com.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping("/book")
    public ResponseEntity<RideBookingDTO> bookRide(
            @RequestParam UUID userId,
            @RequestParam UUID driverId,
            @RequestParam String pickup,
            @RequestParam String drop,
            @RequestParam boolean isParcel
    ) {
        RideBookingDTO ride = rideService.bookRide(userId, driverId, pickup, drop, isParcel);
        return ResponseEntity.ok(ride);
    }

    @PutMapping("/update-status")
    public ResponseEntity<String> updateRideStatus(
            @RequestParam UUID rideId,
            @RequestParam RideStatus status
    ) {
        rideService.updateRideStatus(rideId, status);
        return ResponseEntity.ok("Ride status updated and notifications sent.");
    }
}
