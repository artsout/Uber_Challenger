package com.rideshare.com.rideshare.ride_service.Model.Enum;

import jakarta.persistence.Embeddable;

@Embeddable
public enum Ride_Status {
    REQUESTED,
    MATCHING,
    ACCEPTED,
    ARRIVED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}
