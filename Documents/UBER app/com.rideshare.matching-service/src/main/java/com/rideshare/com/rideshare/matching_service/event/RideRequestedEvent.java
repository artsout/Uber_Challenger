package com.rideshare.com.rideshare.matching_service.event;


import com.rideshare.com.rideshare.ride_service.Model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Event consumed from kafka topic : ride.request
//Published by ride service when a rider request ride

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestedEvent {

    private String rideId;
    private String riderId;
    private Double pickupLongitude; // Alterado de double para Double para permitir validação @NotNull
    private Double pickupLatitude;
    private Address pickupAddress;
    private Double dropLongitude;
    private Double dropLatitude;
    private Address dropAddress;



}
