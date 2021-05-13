package com.example.hackathon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Transition;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class Auth extends AppCompatActivity {
    EditText login;
    EditText password;
    SharedPreferences sh;
    static  String id = "";

    @Override
    protected void onStart() {
        super.onStart();
        if(load_id().equals("")){
            id = UUID.randomUUID().toString();
            save_id(id);
        }else{
            id = load_id();
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                     if(snapshot.getValue(User.class) != null){
                         Intent i = new Intent(Auth.this, MainActivity.class);
                         startActivity(i);
                     }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            FirebaseDatabase.getInstance().getReference(id).addValueEventListener(valueEventListener);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth);
        if(load_id().equals("")){
            id = UUID.randomUUID().toString();
            save_id(id);
        }else{
            id = load_id();
        }
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
    }
    public void sign_in(View v){
        if(login.getText().toString().isEmpty() == false && password.getText().toString().isEmpty() == false){
            Firebase.autentificate(new User(login.getText().toString(),password.getText().toString()),id);
            Intent i = new Intent(Auth.this,MainActivity.class);
            startActivity(i);
        }else{
            Toast.makeText(getApplicationContext(),"Please enter full data",Toast.LENGTH_SHORT).show();
        }
    }
    public String load_id(){
        sh = getPreferences(MODE_PRIVATE);
        String id = sh.getString("id","");
        return id;
    }
    public void save_id(String id){
        sh = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.putString("id",id);
        editor.commit();
    }
}
