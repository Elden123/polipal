package com.github.beijingstrongbow.Communication;

import com.example.nolan.polipal.UserData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by ericd on 11/4/2017.
 */

public class FirebaseHandler {

    private DatabaseReference usersRef;

    public FirebaseHandler() {
        usersRef = FirebaseDatabase.getInstance().getReference("/Users/");
    }

    public void addNewUser(UserData data){
        DatabaseReference user = usersRef.push();
        user.child("name").setValue(data.getName());
        user.child("email").setValue(data.getEmail());
        user.child("password").setValue(data.getPassword());
        user.child("politicalParty").setValue(data.getPoliticalParty());
        user.child("hobbies").setValue(arrayListToCSV(data.getHobbies()));
        user.child("policyInterests").setValue(arrayListToCSV(data.getPolicyInterests()));
    }

    private final Semaphore lock = new Semaphore(0);

    private final UserData data = new UserData();

    public UserData retrieveUser(final String email, final String password){

        usersRef.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("here");
                System.out.println("here");
                System.out.println("here");
                System.out.println("here");
                System.out.println("here");
                System.out.println("here");

                lock.release();
                /*for(DataSnapshot d : dataSnapshot.getChildren()){
                    System.out.println(((String) d.child("email").getValue()) + " " + ((String) d.child("password").getValue()));
                    if(((String) d.child("email").getValue()).equals(email) && ((String) d.child("password").getValue()).equals(password)){
                        data.setName((String) d.child("name").getValue());
                        data.setEmail(email);
                        data.setPassword(password);
                        data.setPolicyInterests(csvToStringArray((String) d.child("policyInterests").getValue()));
                        data.setHobbies(csvToStringArray((String) d.child("hobbies").getValue()));
                        data.setPoliticalParty((String) d.child("politicalParty").getValue());
                    }
                }*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
            }
        });
        try{
            lock.acquire();
            System.out.println("in main thread");
        }
        catch(InterruptedException ex){}

        return data;
    }

    private String arrayListToCSV(ArrayList<String> array){
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < array.size(); i++){
            output.append(array.get(i) + ",");
        }

        output.deleteCharAt(output.length() - 1);

        return output.toString();
    }

    private String[] csvToStringArray(String csv){
        return csv.split(",");
    }
}
