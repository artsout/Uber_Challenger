package com.rideshare.com.rideshare.ride_service.dto;

import com.rideshare.com.rideshare.ride_service.Model.Ride;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface RideMapper {

    RiderRequest toDtoRequest(Ride ride);

    Ride toEntity(RiderRequest riderRequest);

    Ride FromResponseToEntity(RideResponse rideResponse);

    RideResponse toDtoResponse(Ride ride);
}

