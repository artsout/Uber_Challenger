package com.rideshare.com.rideshare.ride_service.Repository;


import com.rideshare.com.rideshare.ride_service.Model.Ride;
import com.rideshare.com.rideshare.ride_service.dto.RideResponse;
import com.rideshare.com.rideshare.ride_service.dto.RiderRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RideRepository extends JpaRepository<Ride, String> {

    List<Ride> findByRiderIdOrderByCreatedAtDesc(UUID riderId);

}
