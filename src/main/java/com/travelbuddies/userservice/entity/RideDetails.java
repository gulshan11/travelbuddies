package com.travelbuddies.userservice.entity;

import java.util.ArrayList;

public class RideDetails {
	private String destination;
	private String emailAddress;
	private String seatsAvailable;
	private String source;
	private String ridetime;
	private String totalSeats;
	private int popularity;
	private String createtime;
	private String keyid;  
	public String getKeyid() {
		return keyid;
	}
	public void setKeyid(String keyid) {
		this.keyid = keyid;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public int getPopularity() {
		return popularity;
	}
	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
	public String getTotalSeats() {
		return totalSeats;
	}
	public void setTotalSeats(String totalSeats) {
		this.totalSeats = totalSeats;
	}
	public String getRidetime() {
		return ridetime;
	}
	public void setRidetime(String ridetime) {
		this.ridetime = ridetime;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getSeatsAvailable() {
		return seatsAvailable;
	}
	public void setSeatsAvailable(String seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

}
