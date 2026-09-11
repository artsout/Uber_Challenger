package com.rideshare.com.rideshare.ride_service.dto;

import com.rideshare.com.rideshare.ride_service.Model.Address;
import com.rideshare.com.rideshare.ride_service.Model.Enum.Ride_Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideResponse {


    private String rideId;

    private String riderId;

    private String driverId;

    private double pickupLongitude;

    private double pickupLatitude;

    private Address pickupAddress;

    private double dropLongitude;

    private double dropLatitude;

    private Address dropAddress;

    private Ride_Status rideStatus;

    private double estimateFare;

    private double actualFare;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;
}
