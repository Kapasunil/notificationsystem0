package com.example.com.controller;

import com.example.com.dto.DriverRequest;
import com.example.com.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @PostMapping("/register")
    public ResponseEntity<String> registerDriver(@RequestBody DriverRequest request) {
        String message = driverService.createDriver(request);
        return ResponseEntity.ok(message);
    }
}

