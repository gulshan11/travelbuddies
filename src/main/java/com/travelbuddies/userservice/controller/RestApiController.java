package com.travelbuddies.userservice.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.database.DatabaseReference;
import com.travelbuddies.userservice.FireBaseService;
import com.travelbuddies.userservice.GetStops;
import com.travelbuddies.userservice.RecentRides;
import com.travelbuddies.userservice.Rides;
import com.travelbuddies.userservice.TopRides;
import com.travelbuddies.userservice.entity.RideDetails;
import com.travelbuddies.userservice.entity.StopDetails;

@RestController
public class RestApiController {
	@PostMapping("/getstops")
	public String getStops(@RequestParam(value = "stopid", defaultValue = "") String stopid) {
		GetStops stops=new GetStops();
		ArrayList<StopDetails> my_stops; 
		System.out.println(stopid);
		my_stops=stops.getStops(stopid);
		String rides_code="<table id='rideslist'>";
		for(int i=0;i<my_stops.size();i++) {
			StopDetails rd=my_stops.get(i);
			rides_code+="<tr id='"+rd.getKeyid()+"' onclick='popup(this.id)'>";
			rides_code+="<td>Requested Stop:</td> <td>"+ rd.getRequested_stop()+"</td>";
			rides_code+="<td>Seats Available:</td> <td>"+ rd.getNo_of_seats()+"</td>";
			rides_code+="<td>Is Aprroved:</td> <td>"+ rd.getIsapproved()+"</td></tr>"; 
		}
		rides_code+="</table>";
		System.out.println(rides_code); 
		return rides_code;
	}
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
	@PostMapping("/requestride")
	public String requestRide(@RequestParam(value = "ride_id", defaultValue = "") String ride_id,@RequestParam(value = "ride_stop", defaultValue = "") String ride_stop,@RequestParam(value = "nseats", defaultValue = "") String nseats,@RequestParam(value = "aseats", defaultValue = "") String aseats) {
		 
		FireBaseService fbs = null;  
        try { 
            fbs = new FireBaseService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DatabaseReference ref = fbs.getDb().getReference("/");
         
        Map<String, Object> userCreate = new HashMap<>();
        userCreate.put("requested_stop", ride_stop);
        userCreate.put("no_of_seats", nseats);
        Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		int i=Integer.parseInt(aseats);
		int j=Integer.parseInt(nseats); 
		int updated_seats=i-j; 
        userCreate.put("created_at", formatter.format(date));
        userCreate.put("isapproved", "false"); 
        String key = ref.getRoot().child("stops").child(ride_id).push().getKey();
        ref.getRoot().child("stops").child(ride_id).child(key).updateChildrenAsync(userCreate);
        ref.getRoot().child("rides").child(ride_id).child("seatsAvailable").setValueAsync(String.valueOf(updated_seats));
        return "success";
	}
}
