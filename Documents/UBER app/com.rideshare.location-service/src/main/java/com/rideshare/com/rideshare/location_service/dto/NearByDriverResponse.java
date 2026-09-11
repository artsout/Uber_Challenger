package com.rideshare.com.rideshare.location_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NearByDriverResponse {

        private String driverId;

        private double longitude;

        private double latitude;

        private double distanceInKm;
}
