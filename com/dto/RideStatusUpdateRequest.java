package com.example.com.dto;

import com.example.com.enums.RideStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request to update the status of a ride ")
public class RideStatusUpdateRequest {
    @NotNull
    @Schema(description = "New status of the ride",example = "ARRIVED", required = true)
    private RideStatus status;
}
