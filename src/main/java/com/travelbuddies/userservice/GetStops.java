package com.travelbuddies.userservice;

import java.io.IOException;
import java.util.ArrayList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.travelbuddies.userservice.entity.RideDetails;
import com.travelbuddies.userservice.entity.StopDetails;

public class GetStops {
	private String message = null; 
	public final static Object obj = new Object();
public ArrayList<StopDetails> getStops(String stopid) {
		
		ArrayList<StopDetails> stop_list = new ArrayList<StopDetails>();
		 
		FireBaseService fbs = null; 
        try {
        	fbs = new FireBaseService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DatabaseReference ref = fbs.getDb().getReference("/");
        
        ref.getRoot().child("stops").child(stopid).addListenerForSingleValueEvent(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {  
				System.out.println("in event");
				   message = "hjj";
	                  
	                 for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
	                	 StopDetails rd=snapshot.getValue(StopDetails.class);
	     				 System.out.println(rd.toString());
	     					rd.setKeyid(snapshot.getKey());
	     					stop_list.add(rd);
	     				 
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
		return stop_list;
	}
}
