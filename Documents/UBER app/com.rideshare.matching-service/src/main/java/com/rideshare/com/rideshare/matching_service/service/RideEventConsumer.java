package com.rideshare.com.rideshare.matching_service.service;


import com.rideshare.com.rideshare.matching_service.event.RideRequestedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;

import static com.rideshare.com.rideshare.matching_service.service.MatchingService.log;

@Service
@RequiredArgsConstructor
public class RideEventConsumer {
    private final MatchingService matchingService;


    @KafkaListener(
            topics = "ride.request",
            groupId = "matching-service-group"
    )
    public  void consumeRequestedEvent(RideRequestedEvent rideRequestedEvent){
        try{
            matchingService.matchDriverForRide(rideRequestedEvent);
        } catch (Exception e) {
            log.error("Error processing ride request : {} - {}",rideRequestedEvent.getRideId(),e.getMessage());
        }
    }
}
