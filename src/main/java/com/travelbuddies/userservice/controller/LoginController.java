package com.travelbuddies.userservice.controller;
 
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.servlet.ModelAndView;

import com.google.firebase.database.DatabaseReference;
import com.travelbuddies.userservice.FireBaseService;
import com.travelbuddies.userservice.GetUserAuthentication;
import com.travelbuddies.userservice.Rides;
import com.travelbuddies.userservice.entity.LoginDetails;
import com.travelbuddies.userservice.entity.RideDetails;

@Controller 
public class LoginController {
	
	@PostMapping(value = "/home")
    public ModelAndView verifyUser(@RequestBody MultiValueMap<String, String> formData, HttpServletRequest request, HttpServletResponse response) {
		String userName= formData.getFirst("uname");
		String passWord= formData.getFirst("psw");
		LoginDetails ld=new LoginDetails();
		ld.setUsername(userName);
		ld.setPassword(passWord);
		GetUserAuthentication oa=new GetUserAuthentication();
		ld.setLogin_status(0);
		oa.run(ld);
		if(ld.getLogin_status()==1) {
			HttpSession session = request.getSession(true);
			session.setMaxInactiveInterval(1000);
			session.setAttribute("user", userName);
			Cookie cookie2 = new Cookie("user", userName);
	        cookie2.setMaxAge(1000);
	        cookie2.setSecure(true);
	        cookie2.setPath("/");
	        response.addCookie(cookie2);
			ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("main.html");
	        return modelAndView;
		}
		else
		{
			Cookie cookie = new Cookie("status", "logonFailed");
	        cookie.setMaxAge(3);
	        cookie.setSecure(true); 
	        cookie.setPath("/");
	        response.addCookie(cookie);  
			ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("../public/index.html");
	        return modelAndView;
		}
    }
	
	@GetMapping(value = "/manage")
    public ModelAndView manageRide(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		
		ModelAndView modelAndView = new ModelAndView();
			if(session==null) { 
				modelAndView.setViewName("../public/index.html");
			}
			else { 
	        modelAndView.setViewName("ManageRide.html");
	        }
	        return modelAndView; 
    }
	
	@GetMapping(value = "/home")
    public ModelAndView getHome(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
			ModelAndView modelAndView = new ModelAndView();
			if(session==null) { 
				modelAndView.setViewName("../public/index.html");
			}
			else {
	        modelAndView.setViewName("main.html");
	        } 
	        return modelAndView; 
    }
	
	@GetMapping(value = "/logout")
    public ModelAndView goLogout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
			ModelAndView modelAndView = new ModelAndView();
			if(session==null) { 
				modelAndView.setViewName("../public/index.html");
			}
			else {
				session.invalidate();
	        modelAndView.setViewName("../public/index.html");
	        } 
	        return modelAndView; 
    }
	@PostMapping(value = "/create")
    public ModelAndView createUser(@RequestBody MultiValueMap<String, String> formData, HttpServletResponse response) {
		String userName= formData.getFirst("uname");
		String passWord= formData.getFirst("psw");
		FireBaseService fbs = null;  
        try { 
            fbs = new FireBaseService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DatabaseReference ref = fbs.getDb().getReference("/");
        
        String key = ref.getRoot().child("users").push().getKey();
        Map<String, Object> userCreate = new HashMap<>();
        userCreate.put("email_address", userName);
        userCreate.put("password", passWord); 
        ref.getRoot().child("users").child(key).updateChildrenAsync(userCreate); 
        Cookie cookie = new Cookie("status", "createSuccess");
        cookie.setMaxAge(3);
        cookie.setSecure(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("../public/index.html");
        return modelAndView;
    }
	@PostMapping(value = "/addRide")
    public ModelAndView createRide(@RequestBody MultiValueMap<String, String> formData, HttpServletResponse response,HttpServletRequest request) {
		String source= formData.getFirst("source");
		String destination= formData.getFirst("destination");
		String noOfSeatsAvailable= formData.getFirst("seats_available");
		LocalDateTime rdt = LocalDateTime.parse(formData.getFirst("ride-time")); 
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		System.out.println(formatter.format(date));
	    String formattedDate = rdt.format(myFormatObj);
		FireBaseService fbs = null;  
        try { 
            fbs = new FireBaseService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DatabaseReference ref = fbs.getDb().getReference("/");
        HttpSession session = request.getSession(false); 
		String username=(String) session.getAttribute("user");
		 
        String key = ref.getRoot().child("rides").push().getKey();
        Map<String, Object> rideDetails = new HashMap<>();
        rideDetails.put("emailAddress", username);
        rideDetails.put("source", source);
        rideDetails.put("destination", destination); 
        rideDetails.put("seatsAvailable", noOfSeatsAvailable);
        rideDetails.put("totalSeats", noOfSeatsAvailable);
        rideDetails.put("ridetime", formattedDate);
        rideDetails.put("popularity", Math.round( Math.random() ));
        rideDetails.put("createtime", formatter.format(date));
        ref.getRoot().child("rides").child(key).updateChildrenAsync(rideDetails);  
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ManageRide.html");
        return modelAndView;
    }

}
