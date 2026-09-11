package com.rideshare.com.rideshare.matching_service.service;

import com.rideshare.com.rideshare.matching_service.client.LocationServiceClient;
import com.rideshare.com.rideshare.matching_service.dto.NearByDriverResponse;
import com.rideshare.com.rideshare.matching_service.event.RideMatchedEvent;
import com.rideshare.com.rideshare.matching_service.event.RideRequestedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchingService {
    static final Logger log = LoggerFactory.getLogger(MatchingService.class);
    private final LocationServiceClient locationServiceClient;
    private final KafkaTemplate<String, RideMatchedEvent> kafkaTemplate;

    private static final String RIDE_MATCHED_TOPIC = "ride.matched";

    private  static final double DEFAULT_SEARCH_RADIUS_KM =5.0;



    public   void matchDriverForRide(RideRequestedEvent rideRequestedEvent){

        List<NearByDriverResponse> nearByDrivers = locationServiceClient.getNearByDrivers(
                rideRequestedEvent.getPickupLatitude(),
                rideRequestedEvent.getDropLatitude(),
                DEFAULT_SEARCH_RADIUS_KM
        );
        if(nearByDrivers.isEmpty()){
            log.warn("No drivers found near ride : {} ");
            return;
        }
        Optional<NearByDriverResponse> bestDriver = findBestDriver(nearByDrivers);


        if(bestDriver.isEmpty()){
            log.warn("Could not find a suitable driver for ride : {} ");
        }

        NearByDriverResponse assignedDriver= bestDriver.get();

        RideMatchedEvent matchedEvent = new RideMatchedEvent(
                rideRequestedEvent.getRideId(),
                rideRequestedEvent.getRiderId(),
                assignedDriver.getDriverId(),
                assignedDriver.getLatitude(),
                assignedDriver.getLongitude(),
                assignedDriver.getDistanceInKm()

        );

        kafkaTemplate.send(RIDE_MATCHED_TOPIC,rideRequestedEvent.getRideId(),matchedEvent);
        log.info("RideMatchedEvent published");


    }

    private  Optional<NearByDriverResponse> findBestDriver(
            List<NearByDriverResponse> drivers
    ){
        double distanceWeight = 0.7;
        double ratingWeight = 0.3;

        return  drivers.stream().max(Comparator.comparingDouble(driver ->{
            double distanceScore = 1.0/(driver.getDistanceInKm() +0.1);

            double simulatedRating = 4.0 + Math.random();

            return (distanceScore * distanceWeight)+(simulatedRating*ratingWeight);
        }));
    }

}
