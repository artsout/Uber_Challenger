package com.rideshare.com.rideshare.matching_service.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Event published from kafka topic : ride.matched
//Consumed by ride service t0o update ride with assigned driver
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideMatchedEvent{
    private String rideId;
    private String riderId;
    private String driverId;
    private  double driverLatitude;
    private  double driverLongitude;
    private  double distanceToPickupKm;
}

