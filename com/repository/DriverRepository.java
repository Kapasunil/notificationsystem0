package com.example.com.repository;

import com.example.com.domain.entity.Driver;
import com.example.com.domain.entity.RideBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface DriverRepository extends JpaRepository<Driver, UUID> {
    Optional<Driver> findById(UUID driverId);
}
