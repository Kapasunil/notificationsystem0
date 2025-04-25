package com.example.com.repository;

import com.example.com.domain.entity.RideBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface RideBookingRepository extends JpaRepository<RideBooking, UUID> {
    List<RideBooking> findByUserId(UUID userId);
    List<RideBooking> findByDriver_Id(UUID driverId);

}
