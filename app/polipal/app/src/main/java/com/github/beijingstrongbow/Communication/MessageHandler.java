package com.github.beijingstrongbow.Communication;

import android.app.Activity;

import com.example.nolan.polipal.Conversation;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Semaphore;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by ericd on 11/4/2017.
 */

public class MessageHandler {

    private DatabaseReference conversationRef;

    private String thisUser;

    private String otherUser;

    private String messageLocation;

    private Conversation c;

    public static MessageHandler mh;

    private boolean empty;

    public MessageHandler(String messageLocation, String thisUser, String otherUser, boolean empty) {
        conversationRef = FirebaseDatabase.getInstance().getReference("/Conversations/").child(messageLocation);
        this.thisUser = thisUser;
        this.otherUser = otherUser;
        this.messageLocation = messageLocation;
        this.empty = empty;
    }

    public void setConversation(Conversation c) {
        this.c = c;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void sendMessage(String message){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/Conversations/" + messageLocation + "/");
        ref.child("sender").setValue(thisUser);
        ref.child("message").setValue(message);
        /*if(lastMessage > 0){
            ref.getParent().child(Long.toString(lastMessage)).child("message").removeValue();
            ref.getParent().child(Long.toString(lastMessage)).child("sender").removeValue();
            ref.getParent().child(Long.toString(lastMessage)).removeValue();
        }*/

        //lastMessage = timestamp;
    }

    private String lastMessage = "";

    private String lastSender = "";

    private String sender = "";

    private int counter = 0;
    private String msg = "";
    private String msg1 = "";

    public void registerMessageListener(){
        conversationRef.child("message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                if(c != null){
                    c.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            String message = "";
                            message = (String) dataSnapshot.getValue();
                            System.out.println(sender + " " + thisUser);
                            if(sender != null && message != null){
                                if(sender.equals(thisUser)) {
                                    msg += message + " ";
                                    counter++;
                                }
                                if(counter >= 1) {
                                    MessageSentiment.getInstance().manageScore(msg);
                                    counter = 0;
                                    msg = "";
                                }
                                if(!sender.equals(thisUser) && (!message.equals(lastMessage) || !sender.equals(lastSender))){
                                    lastMessage = message;
                                    lastSender = sender;
                                    c.showTheirMessage(message);
                                }
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        conversationRef.child("sender").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                if(c != null){
                    c.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            sender = (String) dataSnapshot.getValue();
                        }
                    });
                }
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
