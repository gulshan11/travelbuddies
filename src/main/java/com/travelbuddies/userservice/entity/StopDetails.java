package com.travelbuddies.userservice.entity;

public class StopDetails {
	private String created_at;
	private String no_of_seats;
	private String requested_stop;
	private String isapproved;
	private String keyid;
	public String getKeyid() {
		return keyid;
	}
	public void setKeyid(String keyid) {
		this.keyid = keyid;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getNo_of_seats() {
		return no_of_seats;
	}
	@Override
	public String toString() {
		return "StopDetails [created_at=" + created_at + ", no_of_seats=" + no_of_seats + ", requested_stop="
				+ requested_stop + ", isapproved=" + isapproved + ", keyid=" + keyid + "]";
	}
	public void setNo_of_seats(String no_of_seats) {
		this.no_of_seats = no_of_seats;
	}
	public String getRequested_stop() {
		return requested_stop;
	}
	public void setRequested_stop(String requested_stop) {
		this.requested_stop = requested_stop;
	}
	public String getIsapproved() {
		return isapproved;
	}
	public void setIsapproved(String isapproved) {
		this.isapproved = isapproved;
	}
	
}
