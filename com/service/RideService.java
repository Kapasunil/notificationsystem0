package com.example.com.service;

import com.example.com.domain.entity.*;
import com.example.com.dto.RideBookingDTO;
import com.example.com.enums.*;
import com.example.com.exception.EntityNotFoundException;
import com.example.com.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideBookingRepository rideRepo;
    private final DriverRepository driverRepo;
    private final NotificationRepository notificationRepo;
    private final UserRepository userRepo;
    private final TwilioService twilioService;

    @Transactional
    public RideBookingDTO bookRide(UUID userId, UUID driverId, String pickup, String drop, boolean isParcel) {
        if (pickup == null || drop == null || pickup.isBlank() || drop.isBlank()) {
            throw new IllegalArgumentException("Pickup and drop locations must be provided.");
        }

        if (userId.equals(driverId)) {
            throw new IllegalArgumentException("User and driver cannot be the same.");
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        Driver driver = driverRepo.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found with ID: " + driverId));


        RideBooking ride = RideBooking.builder()
                .user(user)
                .driver(driver)
                .pickupLocation(pickup)
                .dropLocation(drop)
                .isParcel(isParcel)
                .status(RideStatus.BOOKED)
                .bookingTime(LocalDateTime.now())
                .build();

        RideBooking savedRide = rideRepo.save(ride);

        sendNotification(user, NotificationType.SMS, "Your ride has been booked!");
        sendNotification(driver.getUser(), NotificationType.SMS, "A new ride has been assigned to you.");

        return mapToDTO(savedRide);
    }

    public void updateRideStatus(UUID rideId, RideStatus newStatus) {
        RideBooking ride = rideRepo.findById(rideId)
                .orElseThrow(() -> new EntityNotFoundException("Ride not found with ID: " + rideId));

        ride.setStatus(newStatus);
        rideRepo.save(ride);

        String userMsg = "", driverMsg = "";

        switch (newStatus) {
            case ARRIVED -> {
                userMsg = "Delivery boy has arrived at your location.";
                driverMsg = "You have arrived at the user’s location.";
            }
            case STARTED -> {
                userMsg = "Your parcel ride has been started.";
                driverMsg = "You have started the ride.";
            }
            case DELIVERED -> {
                userMsg = "Package has been delivered.";
                driverMsg = "Your ride is over.";
            }
        }

        if (!userMsg.isEmpty()) sendNotification(ride.getUser(), NotificationType.SMS, userMsg);
        if (!driverMsg.isEmpty()) sendNotification(ride.getDriver().getUser(), NotificationType.SMS, driverMsg);
    }

    private void sendNotification(User user, NotificationType type, String message) {
        Notification notification = Notification.builder()
                .user(user)
                .type(type)
                .message(message)
                .status(NotificationStatus.SENT)
                .build();

        notificationRepo.save(notification);

        if (type == NotificationType.SMS) {
            twilioService.sendSms(user.getPhone(), message);
        }
    }

    private RideBookingDTO mapToDTO(RideBooking ride) {
        return RideBookingDTO.builder()
                .id(ride.getId())
                .userId(ride.getUser().getId())
                .driverId(ride.getDriver().getId())
                .pickupLocation(ride.getPickupLocation())
                .dropLocation(ride.getDropLocation())
                .isParcel(ride.isParcel())
                .status(ride.getStatus())
                .bookingTime(ride.getBookingTime())
                .build();
    }
}
