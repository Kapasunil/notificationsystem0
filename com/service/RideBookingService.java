package com.example.com.service;

import com.example.com.domain.entity.Driver;
import com.example.com.domain.entity.RideBooking;
import com.example.com.domain.entity.User;
import com.example.com.enums.NotificationType;
import com.example.com.enums.RideStatus;
import com.example.com.repository.DriverRepository;
import com.example.com.repository.RideBookingRepository;
import com.example.com.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RideBookingService {

    private final RideBookingRepository rideBookingRepository;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final NotificationService notificationService;

    public RideBooking bookRide(UUID userId, UUID driverId, String pickup, String drop, boolean isParcel,RideStatus status) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("User not found with ID: " +userId));
        Driver driver=driverRepository.findById(driverId)
                .orElseThrow(()->new EntityNotFoundException("Driver not found with ID: "+driverId));

        RideBooking booking = RideBooking.builder()
                .user(user)
                .driver(driver)
                .pickupLocation(pickup)
                .dropLocation(drop)
                .isParcel(isParcel)
                .status(status)
                .bookingTime(LocalDateTime.now())
                .build();

        RideBooking saved = rideBookingRepository.save(booking);


        notificationService.sendNotification(user, NotificationType.SMS, "Your ride/parcel has been booked.");
        notificationService.sendNotification(driver.getUser(), NotificationType.SMS, "You have a new ride/parcel request.");

        return saved;
    }

    @Transactional
    public RideBooking updateRideStatus(UUID bookingId, RideStatus status) {
        RideBooking booking = rideBookingRepository.findById(bookingId).orElseThrow(()->new EntityNotFoundException("RIDE booking not found with ID: " +bookingId));
        booking.setStatus(status);
        RideBooking updated = rideBookingRepository.save(booking);

        String userMessage = "";
        String driverMessage = "";

        switch (status) {
            case ARRIVED -> {
                userMessage = "Delivery boy has arrived at your location.";
                driverMessage = "You have arrived at the pickup location.";
            }
            case STARTED -> {
                userMessage = "Your parcel ride has been started.";
                driverMessage = "Parcel ride started.";
            }
            case DELIVERED -> {
                userMessage = "Package has been delivered.";
                driverMessage = "Package delivered to user.";
            }
            case COMPLETED -> {
                userMessage = "Your ride is completed.";
                driverMessage = "Your ride is over.";
            }
        }

        if (!userMessage.isEmpty()) {
            notificationService.sendNotification(booking.getUser(), NotificationType.SMS, userMessage);
            notificationService.sendNotification(booking.getDriver().getUser(), NotificationType.SMS, driverMessage);
        }

        return updated;
    }

    public List<RideBooking> getUserBookings(UUID userId) {
        return rideBookingRepository.findByUserId(userId);
    }

    public List<RideBooking> getDriverBookings(UUID driverId) {
        return rideBookingRepository.findByDriver_Id(driverId);
    }

    public RideBooking getBooking(UUID id) {
        return rideBookingRepository.findById(id).orElseThrow();
    }
}
