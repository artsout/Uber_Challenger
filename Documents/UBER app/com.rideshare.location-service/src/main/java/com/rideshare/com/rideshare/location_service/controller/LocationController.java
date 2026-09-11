package com.rideshare.com.rideshare.location_service.controller;

import com.rideshare.com.rideshare.location_service.dto.DriverLocationRequest;
import com.rideshare.com.rideshare.location_service.dto.NearByDriverResponse;
import com.rideshare.com.rideshare.location_service.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/locations")
@Slf4j
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;


    //driver phone calls this method every 3 second
    @PostMapping("/drivers/update")
    public ResponseEntity<String> updateDriverCurrentLocation(
            @RequestBody DriverLocationRequest driverLocationRequest
    ){

        locationService.updateDriverLocation(driverLocationRequest);

        return ResponseEntity.ok("Driver location update");
    }

    //Matching Services call this when rides is requested
    @GetMapping("/drivers/nearby")
    public  ResponseEntity<List<NearByDriverResponse>> getNearByDrivers(
            @RequestParam double latitude,@RequestParam double longitude,
            @RequestParam (defaultValue = "5.0") double radius
    ){

        return ResponseEntity.ok(locationService.findNearByDrivers(latitude,longitude,radius));
    }


    @DeleteMapping("/drivers/{driverId}")
    public ResponseEntity<String> removeDriver(
            @PathVariable String driverId

    ){
        locationService.removeDriver(driverId);
        return ResponseEntity.ok("Driver removed");


    }
}
