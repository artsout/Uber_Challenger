package com.rideshare.com.rideshare.ride_service.Service;


import com.rideshare.com.rideshare.ride_service.Model.Enum.Ride_Status;
import com.rideshare.com.rideshare.ride_service.Model.Ride;
import com.rideshare.com.rideshare.ride_service.Repository.RideRepository;
import com.rideshare.com.rideshare.ride_service.dto.RideResponse;
import com.rideshare.com.rideshare.ride_service.dto.RiderRequest;
import com.rideshare.com.rideshare.ride_service.dto.RideMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;




@Service
@RequiredArgsConstructor
@Slf4j
public class RideService {
    private final RideRepository rideRepository;

    @Autowired
    private final RideMapper rideMapper;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public RideResponse requestRide(RiderRequest riderRequest){
        log.info("Receiving request ride from ID: {}", riderRequest.getRiderId());

        Ride ride = rideMapper.toEntity(riderRequest);

        ride.setRideStatus(Ride_Status.REQUESTED);

        ride.setEstimateFare(calculateEstimateFare(riderRequest));

        RideResponse response =  rideMapper.toDtoResponse(ride);

        kafkaTemplate.send("RIDE_REQUEST_TOPIC",ride.getRideId(),response);
        log.info("Publish to kafka for ride: {}", ride.getRiderId());

        ride.setRideStatus(Ride_Status.MATCHING);

        response.setRideStatus(Ride_Status.MATCHING);

        Ride savedRide = rideRepository.save(ride);

        return  response;
    }

    public void updatedRideWithDriver(String rideId,String driverId){
        log.info("Driver {} accepted the ride {}", driverId, rideId);

        UUID driverUUID = UUID.fromString(driverId);
        Ride ride =rideRepository.findById(rideId)
                                .orElseThrow(() ->
                                new RuntimeException("Not found"));

        ride.setDriverId(driverUUID);
        ride.setRideStatus(Ride_Status.ACCEPTED);
        rideRepository.save(ride);
        log.info("Ride {} successfully updated with driver {}", rideId, driverId);
    }

    public RideResponse findById(String rideId){
     Ride ride = rideRepository.findById(rideId).orElseThrow(() ->
             new RuntimeException("Not found"));

      return rideMapper.toDtoResponse(ride);
    }
    public RideResponse startRide(String rideId){
        Ride ride = rideRepository.findById(rideId).orElseThrow(() ->
                new RuntimeException("Not found"));

        if(ride.getRideStatus()!=Ride_Status.ACCEPTED){
            throw  new RuntimeException("Ride cannot be started . Current Stats = "+ ride.getRideStatus());
        }
        ride.setRideStatus(Ride_Status.IN_PROGRESS);
        ride.setStartedAt(LocalDateTime.now());
        rideRepository.save(ride);

        return rideMapper.toDtoResponse(ride);
    }

    public  RideResponse completeRide(String rideId){
        Ride ride = rideRepository.findById(rideId).orElseThrow(() ->
                new RuntimeException("Not found"));
        if(ride.getRideStatus()!=Ride_Status.IN_PROGRESS){
            throw  new RuntimeException("Ride cannot be complete , just if status equals In_Progress. Current Stats = "+ ride.getRideStatus());
        }

        ride.setRideStatus(Ride_Status.COMPLETED);
        ride.setCompletedAt(LocalDateTime.now());

        rideRepository.save(ride);
        ride.setEstimateFare(ride.getActualFare());
        return rideMapper.toDtoResponse(ride);
    }

    public RideResponse cancelRide(String rideId){
        Ride ride = rideRepository.findById(rideId).orElseThrow(() ->
                new RuntimeException("Not found"));

        if(ride.getRideStatus() != Ride_Status.ACCEPTED){
            throw  new RuntimeException("Ride cannot be cancel. Current Stats = "+ ride.getRideStatus());
        }
        ride.setRideStatus(Ride_Status.CANCELLED);
        ride.setCompletedAt(LocalDateTime.now());
        rideRepository.save(ride);
        return rideMapper.toDtoResponse(ride);
    }

    public  RideResponse findByRiderId(String riderId){
        Ride ride= rideRepository.findById(riderId).orElseThrow(() ->
                new RuntimeException("Not found"));
        UUID riderUUID = UUID.fromString(riderId);

        ride.setRiderId(riderUUID);

        return rideMapper.toDtoResponse(ride);
    }

    public List<RideResponse> getRidesByRider(String riderId){

        return rideRepository.findByRiderIdOrderByCreatedAtDesc(UUID.fromString(riderId))
                .stream().map(rideMapper::toDtoResponse)
                .collect(Collectors.toList()
                );
    }

    public double calculateEstimateFare(RiderRequest riderRequest){
        double pickLatitude = Math.toRadians(riderRequest.getPickupLatitude());
        double dropLatitude = Math.toRadians(riderRequest.getDropLatitude());

        double pickLongitude =  Math.toRadians(riderRequest.getPickupLongitude());
        double dropLongitude = Math.toRadians(riderRequest.getDropLongitude());

        double diferenceLatitude = dropLatitude - pickLatitude;

        double diferenceLongitude = dropLongitude - pickLongitude;

        double formula = Math.pow(Math.sin(diferenceLatitude/2 ),2)
                + Math.cos(pickLatitude) * Math.cos(dropLatitude)
                * Math.pow(Math.sin(diferenceLongitude/2),2);

        double formula2 =2 * Math.asin(Math.sqrt(formula));

        double distanceInKm = (6371 * formula2) * 1.25;

        double fare = 5.0 + (distanceInKm * 1.20);

        return Math.round(fare * 100.0) /100.0;
    }


}
