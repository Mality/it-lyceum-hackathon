package com.example.hackathon;

import com.google.firebase.database.FirebaseDatabase;

public class Firebase {
    public  static void autentificate(User user,String id){
        FirebaseDatabase.getInstance().getReference(id).setValue(user);
    }
}
