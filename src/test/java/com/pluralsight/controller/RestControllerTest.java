package com.pluralsight.controller;

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
	public void testCreateRidePut() {
		System.out.println("PUT");
		RestTemplate restTemplate = new RestTemplate();

		Ride ride = new Ride();
		ride.setName("Bob Sled");
		ride.setDuration(35);

		restTemplate.put("http://localhost:8080/ride_tracker/ride", ride);
	}

	@Test(timeout = 5000)
	public void testCreateRidePost() {
		System.out.println("POST");
		RestTemplate restTemplate = new RestTemplate();

		Ride ride = new Ride();
		ride.setName("Bob Sled");
		ride.setDuration(35);

		Ride postedObject = restTemplate.postForObject("http://localhost:8080/ride_tracker/ride", ride, Ride.class);

		System.out.println(postedObject.getId());
	}
}
