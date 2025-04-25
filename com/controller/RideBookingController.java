package com.example.com.controller;

import com.example.com.domain.entity.RideBooking;
import com.example.com.dto.RideBookingRequest;
import com.example.com.dto.RideStatusUpdateRequest;
import com.example.com.service.RideBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
@Tag(name = "Ride Booking", description = "Endpoints for managing ride bookings and statuses")
public class RideBookingController {

    private final RideBookingService rideBookingService;

    @PostMapping("/book")
    @Operation(summary = "Book a ride", description = "Allows a user to book a ride with driver, pickup and drop location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ride booked successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<RideBooking> bookRide(@RequestBody RideBookingRequest request) {
        RideBooking ride = rideBookingService.bookRide(
                request.getUserId(),
                request.getDriverId(),
                request.getPickup(),
                request.getDrop(),
                request.isParcel(),
                request.getStatus()
        );
        return ResponseEntity.ok(ride);
    }

    @PutMapping("/{rideId}/status")
    @Operation(summary = "Update ride status", description = "Allows updating the current status of a ride")
    public ResponseEntity<?> updateRideStatus(@PathVariable UUID rideId, @RequestBody RideStatusUpdateRequest request) {
        rideBookingService.updateRideStatus(rideId, request.getStatus());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get bookings for user", description = "Returns all ride bookings made by a specific user")
    public ResponseEntity<List<RideBooking>> getUserBookings(@PathVariable UUID userId) {
        return ResponseEntity.ok(rideBookingService.getUserBookings(userId));
    }

    @GetMapping("/driver/{driverId}")
    @Operation(summary = "Get bookings for driver", description = "Returns all rides assigned to a specific driver")
    public ResponseEntity<List<RideBooking>> getDriverBookings(@PathVariable UUID driverId) {
        return ResponseEntity.ok(rideBookingService.getDriverBookings(driverId));
    }

    @GetMapping("/{bookingId}")
    @Operation(summary = "Get ride booking by ID", description = "Returns the details of a specific ride booking")
    public ResponseEntity<RideBooking> getBooking(@PathVariable UUID bookingId) {
        return ResponseEntity.ok(rideBookingService.getBooking(bookingId));
    }
}
