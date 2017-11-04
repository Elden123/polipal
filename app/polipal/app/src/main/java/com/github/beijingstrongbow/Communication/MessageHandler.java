package com.github.beijingstrongbow.Communication;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ericd on 11/4/2017.
 */

public class MessageHandler {

    DatabaseReference messageRef;

    public MessageHandler(){
        FirebaseDatabase.getInstance().getReference("/Messages/");
    }

    public void sendMessage(String message){
        
    }
}
