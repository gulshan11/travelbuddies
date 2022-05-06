package com.travelbuddies.userservice.controller;

import java.util.ArrayList;

import javax.servlet.http.Cookie;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travelbuddies.userservice.RecentRides;
import com.travelbuddies.userservice.Rides;
import com.travelbuddies.userservice.TopRides;
import com.travelbuddies.userservice.entity.RideDetails;

@RestController
public class RestApiController {
	@GetMapping("/getrides")
	public String rideList(@RequestParam(value = "username", defaultValue = "") String username) {
		Rides rides=new Rides();
		ArrayList<RideDetails> my_rides; 
		my_rides=rides.getRides(username);
		String rides_code="<table id='rideslist'>";
		for(int i=0;i<my_rides.size();i++) {
			RideDetails rd=my_rides.get(i);
			rides_code+="<tr id='"+rd.getKeyid()+"' onclick='popup(this.id)'><td>Source</td> <td>"+ rd.getSource()+"</td>";
			rides_code+="<td>Destination</td> <td>"+ rd.getDestination()+"</td>";
			rides_code+="<td>Seats Available</td> <td>"+ rd.getSeatsAvailable()+"</td>";
			rides_code+="<td>Total Seats</td> <td>"+ rd.getTotalSeats()+"</td></tr>";
			rides_code+="<td>Ride Time</td> <td>"+ rd.getRidetime()+"</td></tr>";
		}
		rides_code+="</table>";
		System.out.println(rides_code); 
		return rides_code;
	}
	@GetMapping("/toprides")
	public String topRides() {
		TopRides rides=new TopRides();
		ArrayList<RideDetails> my_rides; 
		my_rides=rides.getRides();
		String rides_code="<table id='rideslist'>";
		for(int i=0;i<my_rides.size();i++) {
			RideDetails rd=my_rides.get(i);
			rides_code+="<tr id='"+rd.getKeyid()+"' onclick='popup(this.id)'><td>"+ rd.getSource()+"</td><td> to</td>";
			rides_code+="<td>"+ rd.getDestination()+"</td>";
			rides_code+="<td> with</td><td> "+ rd.getSeatsAvailable()+"</td><td> seats</td></tr>";
		}
		rides_code+="</table>";
		System.out.println(rides_code); 
		return rides_code;
	}
	@GetMapping("/recentrides")
	public String recentRides() {
		RecentRides rides=new RecentRides();
		ArrayList<RideDetails> my_rides; 
		my_rides=rides.getRides();
		String rides_code="<table id='rideslist'>";
		for(int i=0;i<my_rides.size();i++) {
			RideDetails rd=my_rides.get(i);
			rides_code+="<tr id='"+rd.getKeyid()+"' onclick='popup(this.id)'><td>"+ rd.getSource()+"</td><td> to</td>";
			rides_code+="<td>"+ rd.getDestination()+"</td>";
			rides_code+="<td> with </td><td>"+ rd.getSeatsAvailable()+"</td><td> seats</td></tr>";
		}
		rides_code+="</table>";
		System.out.println(rides_code); 
		return rides_code;
	}
}
