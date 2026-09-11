package com.rideshare.com.rideshare.ride_service.dto;

import com.rideshare.com.rideshare.ride_service.Model.Address;
import com.rideshare.com.rideshare.ride_service.Model.Enum.Ride_Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiderRequest {
    @NotBlank(message = "Rider ID is required and cannot be blank")
    private String riderId;

    @NotNull(message = "Pickup longitude is required")
    private Double pickupLongitude; // Alterado de double para Double para permitir validação @NotNull

    @NotNull(message = "Pickup latitude is required")
    private Double pickupLatitude;

    @NotNull(message = "Pickup address details are required")
    private Address pickupAddress;

    @NotNull(message = "Drop-off longitude is required")
    private Double dropLongitude;

    @NotNull(message = "Drop-off latitude is required")
    private Double dropLatitude;

    @NotNull(message = "Drop-off address details are required")
    private Address dropAddress;

    @NotNull(message = "Ride status is required")
    private Ride_Status rideStatus;

}
