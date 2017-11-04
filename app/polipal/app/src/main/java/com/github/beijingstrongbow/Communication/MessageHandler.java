package com.github.beijingstrongbow.Communication;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ericd on 11/4/2017.
 */

public class MessageHandler {

    private DatabaseReference conversationRef;

    private String thisUser;

    private String otherUser;

    private String messageLocation;

    private long lastMessage = -1;

    public MessageHandler(String messageLocation, String thisUser, String otherUser){
        conversationRef = FirebaseDatabase.getInstance().getReference("/Conversations/").child(messageLocation);
        this.thisUser = thisUser;
        this.otherUser = otherUser;
        this.messageLocation = messageLocation;
    }

    public void sendMessage(String message){
        long timestamp = System.currentTimeMillis();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/Conversations/" + messageLocation + "/" + timestamp + "/");
        ref.child("message").setValue(message);
        ref.child("sender").setValue(thisUser);
        if(lastMessage > 0){
            ref.getParent().child(Long.toString(lastMessage)).child("message").removeValue();
            ref.getParent().child(Long.toString(lastMessage)).child("sender").removeValue();
            ref.getParent().child(Long.toString(lastMessage)).removeValue();
        }

        lastMessage = timestamp;
    }

    public void registerMessageListener(){
        conversationRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static String getNewMessageLocation(){
        return FirebaseDatabase.getInstance().getReference("/Messages/").push().getKey();
    }
}
