package com.github.beijingstrongbow.Communication;

import com.example.nolan.polipal.UserData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        user.child("hobbies").setValue(stringArrayToCSV(data.getHobbies()));
        user.child("policyInterests").setValue(stringArrayToCSV(data.getPolicyInterests()));
    }

    private String stringArrayToCSV(String[] array){
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < array.length; i++){
            output.append(array[i] + ",");
        }

        output.deleteCharAt(output.length() - 1);

        return output.toString();
    }
}
