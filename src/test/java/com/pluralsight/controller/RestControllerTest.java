package com.pluralsight.controller;

import java.io.ObjectInputStream;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.pluralsight.model.Ride;

import org.junit.Test;

public class RestControllerTest {

	@Test(timeout = 5000)
	public void testGetRides() {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List<Ride>> ridesResponse = restTemplate.exchange(
				"http://localhost:8080/ride_tracker/rides", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Ride>>() {
				});
		List<Ride> rides = ridesResponse.getBody();

		for (Ride ride : rides) {
			System.out.println("Ride name: " + ride.getName());
		}
	}

	@Test(timeout = 5000)
	public void testCreateRidePost() {
		RestTemplate restTemplate = new RestTemplate();

		Ride ride = new Ride();
		ride.setName("Bob Sled");
		ride.setDuration(35);

		Ride postedObject = restTemplate.postForObject("http://localhost:8080/ride_tracker/ride", ride, Ride.class);

		System.out.println(postedObject.getId());
	}

	@Test(timeout = 5000)
	public void testGetRide() {
		RestTemplate restTemplate = new RestTemplate();

		Ride ride = restTemplate.getForObject("http://localhost:8080/ride_tracker/ride/8", Ride.class);

		System.out.println(ride.getId());
		System.out.println(ride.getName());
	}

	@Test(timeout = 5000)
	public void testBatchUpdate() {
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.getForObject("http://localhost:8080/ride_tracker/batch", Object.class);
	}

    @Test(timeout = 5000)
    public void testUpdateRide() {
        RestTemplate restTemplate = new RestTemplate();

        Ride ride = restTemplate.getForObject("http://localhost:8080/ride_tracker/ride/8", Ride.class);

        System.out.println("before update " + ride.getDuration());

        ride.setDuration(ride.getDuration() + 1);

        restTemplate.put("http://localhost:8080/ride_tracker/ride", ride);

		Ride newRide = restTemplate.getForObject("http://localhost:8080/ride_tracker/ride/8", Ride.class);

		System.out.println("after update " + newRide.getDuration());

    }
}
