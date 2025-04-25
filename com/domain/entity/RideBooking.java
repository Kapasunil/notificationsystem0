package com.example.com.domain.entity;

import com.example.com.enums.RideStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ride_bookings", schema = "gdc-db")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private RideStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

//    @ManyToOne
//    @JoinColumn(name = "driver_id", nullable = false)
//    private User driver;

    @Column(nullable = false)
    private String pickupLocation;

    @Column(nullable = false)
    private String dropLocation;

    @Column(nullable = false)
    private boolean isParcel;

//    @Column(nullable = false)
//    private String status;

    @Column(nullable = false)
    private LocalDateTime bookingTime;
}
