package com.rideshare.com.rideshare.location_service.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.aot.generate.Generated;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverLocationRequest {

    private String driverId;

    private double longitude;

    private double latitude;
}
