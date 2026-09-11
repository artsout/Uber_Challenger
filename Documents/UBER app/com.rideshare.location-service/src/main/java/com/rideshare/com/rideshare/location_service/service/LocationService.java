package com.rideshare.com.rideshare.location_service.service;

import com.rideshare.com.rideshare.location_service.dto.DriverLocationRequest;
import com.rideshare.com.rideshare.location_service.dto.NearByDriverResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisCommand;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.Metrics;
import org.springframework.stereotype.Service;

import javax.management.StringValueExp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationService {


    private final RedisTemplate<String, String> redisTemplate;

    private static final String DRIVER_GEO_LOCATION = "drivers:locations";


    public  void updateDriverLocation(DriverLocationRequest driverLocationRequest){
        log.info("Updating Location for driver : {} ", driverLocationRequest.getDriverId());

        //Important:: longitude first(GEO space standard)
        Point driverPoint = new Point(
                driverLocationRequest.getLongitude(),
                driverLocationRequest.getLatitude()
        );

        //opsForGeo get access to all redis geo spacial command in java
        redisTemplate.opsForGeo().add(
                DRIVER_GEO_LOCATION,
                driverPoint,
                driverLocationRequest.getDriverId()
        );

        log.info("Location updated for driver: {} ", driverLocationRequest.getDriverId());

    }

    public List<NearByDriverResponse> findNearByDrivers(double longitude,double latitude,double radius){
        log.info("Finding drivers near lat: {} long: {} withing: {}km",latitude,longitude,radius);

        Circle searchArea= new Circle(
                new Point(longitude,latitude),
                new Distance(radius, Metrics.KILOMETERS)
        );

        GeoResults<RedisGeoCommands.GeoLocation<String>> result = redisTemplate.opsForGeo().radius(
                DRIVER_GEO_LOCATION,
                searchArea,
                RedisGeoCommands.GeoRadiusCommandArgs
                        .newGeoRadiusArgs()
                        .includeCoordinates()
                        .includeDistance()
                        .sortAscending()
                        .limit(10)
        );

        List<NearByDriverResponse> nearByDrivers = new ArrayList<>();

        if(result != null){
            result.getContent().forEach(resultado ->{RedisGeoCommands.GeoLocation<String> location = resultado.getContent();
            nearByDrivers.add(new NearByDriverResponse(
                    location.getName(),
                    location.getPoint().getX(),
                    location.getPoint().getY(),
                    resultado.getDistance().getValue()
            ));
            });
        }

        log.info("Quantity of nearby drivers : {} ",nearByDrivers.size());
        return nearByDrivers;
    }


    public  void removeDriver(String DriverId){
        log.info("Removing driver : {} ",DriverId);

        redisTemplate.opsForGeo().remove(DRIVER_GEO_LOCATION,DriverId);
    }
}
