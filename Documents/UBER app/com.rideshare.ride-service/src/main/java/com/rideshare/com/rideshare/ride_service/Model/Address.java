package com.rideshare.com.rideshare.ride_service.Model;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String number;
    private String cep;
    private String road;
    private String city;
    private String neighborhood;
}
