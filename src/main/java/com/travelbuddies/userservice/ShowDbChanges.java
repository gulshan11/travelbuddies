package com.travelbuddies.userservice;

import java.io.IOException;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ShowDbChanges implements Runnable {
    public void run() {
        FireBaseService fbs = null;
        try {
            fbs = new FireBaseService();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DatabaseReference ref = fbs.getDb().getReference("/");
        
        ref.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                Object document = dataSnapshot.getValue();
                System.out.println(document);
            }


            public void onCancelled(DatabaseError error) {
                System.out.print("Error: " + error.getMessage());
            }
        });


    }
    public static void main(String[] args) throws IOException {

        Thread t=new Thread(new ShowDbChanges());

        t.run();
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

 