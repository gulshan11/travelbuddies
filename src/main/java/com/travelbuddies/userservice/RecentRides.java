package com.travelbuddies.userservice;

import java.io.IOException;
import java.util.ArrayList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.travelbuddies.userservice.entity.RideDetails;

public class RecentRides {
	private String message = null; 
	public final static Object obj = new Object();
	public ArrayList<RideDetails> getRides() {
		
		ArrayList<RideDetails> ride_list = new ArrayList<RideDetails>();
		 
		FireBaseService fbs = null; 
        try {
        	fbs = new FireBaseService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DatabaseReference ref = fbs.getDb().getReference("/"); 
        ref.getRoot().child("rides").orderByChild("createtime").limitToLast(5).addListenerForSingleValueEvent(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {  
				    
				   message = "hjj";
	                  
	                 for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
	                	 RideDetails rd=snapshot.getValue(RideDetails.class); 
	                	 	rd.setKeyid(snapshot.getKey());
	     					ride_list.add(rd);  
	                 } 
	                 synchronized (obj) {
	                	 obj.notify();
					} 
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
				
			}
		});
        synchronized (obj) {
       	 while(message == null) {
          	  try {
          		  obj.wait();
  			} catch (InterruptedException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
            } 
		}
		return ride_list;
	}
}
