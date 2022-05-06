package com.travelbuddies.userservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseService {


    FirebaseDatabase db;

    public FireBaseService() throws IOException {
        File file = new File(
                getClass().getClassLoader().getResource("key.json").getFile()
        );

        FileInputStream fis = new FileInputStream(file);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(fis))
                .setDatabaseUrl("https://needaride-c52b6-default-rtdb.firebaseio.com")
                .build(); 
        if(FirebaseApp.getApps().isEmpty())
        	FirebaseApp.initializeApp(options);

        db = FirebaseDatabase.getInstance();
    }

    public FirebaseDatabase getDb() {
        return db;
    }

}
