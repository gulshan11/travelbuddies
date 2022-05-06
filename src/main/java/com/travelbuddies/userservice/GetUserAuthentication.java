package com.travelbuddies.userservice;

import java.io.IOException;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference; 
import com.google.firebase.database.ValueEventListener; 
import com.travelbuddies.userservice.entity.LoginDetails; 
import com.travelbuddies.userservice.entity.Users;

public class GetUserAuthentication extends Thread{
	private String message = null; 
	public final static Object obj = new Object();
	public void run (LoginDetails ld) {
        FireBaseService fbs = null;
        ld.setLogin_status(0);
        
        try {
         
            fbs = new FireBaseService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DatabaseReference ref = fbs.getDb().getReference("/");
         ref.getRoot().child("users")
         .addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
            	 
                     message = "hjj";
                  
                 for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                     Users user = snapshot.getValue(Users.class);
                     if(ld.getUsername().equalsIgnoreCase(user.getEmail_address()) && ld.getPassword().equals(user.getPassword())) {
                    	  
                    		 ld.setLogin_status(1); 
                    	  
                     }  
                 }
                 if(ld.getLogin_status()==1) {
                	 System.out.println("Login Successfull: "+ld.getLogin_status());
                 }
                 else {
                	 System.out.println("login Failed: "+ld.getLogin_status());
                 }
                 synchronized (obj) {
                	 obj.notify();
				} 
                  
             }
             @Override
             public void onCancelled(DatabaseError databaseError) {
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
          
          
         System.out.println("checking authentication");
	}
}
