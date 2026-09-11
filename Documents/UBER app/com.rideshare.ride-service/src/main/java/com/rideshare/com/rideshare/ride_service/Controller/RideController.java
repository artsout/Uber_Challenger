package com.rideshare.com.rideshare.ride_service.Controller;


import com.rideshare.com.rideshare.ride_service.Service.RideService;
import com.rideshare.com.rideshare.ride_service.dto.RideResponse;
import com.rideshare.com.rideshare.ride_service.dto.RiderRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rides")
@Slf4j
@RequiredArgsConstructor
public class RideController {
    private final RideService rideService;


    @PostMapping("/request")
    public ResponseEntity<RideResponse> requestRide(
            @Valid @RequestBody RiderRequest riderRequest
            ){
        log.info("Ride request from : {}", riderRequest.getRiderId());

        return  ResponseEntity.status(HttpStatus.CREATED).body(rideService.requestRide(riderRequest));
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<RideResponse> getRideById(
     @PathVariable  String rideId
    ){
       return ResponseEntity.ok(rideService.findById(rideId));
    }

    @GetMapping("/rider/{riderId}")
    public ResponseEntity<List<RideResponse>> getRidesByRiderId(
            @PathVariable  String riderId
    ){
        return ResponseEntity.ok(rideService.getRidesByRider(riderId));
    }

    @PutMapping("/{rideId}/start")
    public ResponseEntity<RideResponse> startRide(
            @PathVariable String rideId
    ){
        return  ResponseEntity.ok(rideService.startRide(rideId));
    }

    @PutMapping("/{rideId}/cancel")
    public ResponseEntity<RideResponse> cancelRide(
            @PathVariable String rideId
    ){
        return  ResponseEntity.ok(rideService.cancelRide(rideId));
    }
    @PutMapping("/{rideId}/complete")
    public ResponseEntity<RideResponse> completeRide(
            @PathVariable String rideId
    ){
        return  ResponseEntity.ok(rideService.completeRide(rideId));
    }
}
