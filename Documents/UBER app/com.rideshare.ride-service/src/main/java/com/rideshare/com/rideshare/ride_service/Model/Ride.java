package com.rideshare.com.rideshare.ride_service.Model;

import com.rideshare.com.rideshare.ride_service.Model.Enum.Ride_Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "rides")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String rideId;

    @Column(nullable = false)
    private UUID riderId;

    //Null until MAtch-Service
    @Column
    private UUID driverId;

    @Column(nullable = false)
    private double pickupLongitude;

    @Column(nullable = false)
    private double pickupLatitude;


    @org.hibernate.annotations.JdbcTypeCode(org.hibernate.type.SqlTypes.JSON)
    @Column(name = "pickup_address", nullable = false)
    private Address pickupAddress;

    @Column(nullable = false)
    private double dropLongitude;

    @Column(nullable = false)
    private double dropLatitude;


    @org.hibernate.annotations.JdbcTypeCode(org.hibernate.type.SqlTypes.JSON)
    @Column(name = "drop_address", updatable = false)
    private Address dropAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Ride_Status rideStatus;


    private double estimateFare;
    private double actualFare;

    @CurrentTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;
}
