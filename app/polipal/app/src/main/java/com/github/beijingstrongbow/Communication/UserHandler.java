package com.github.beijingstrongbow.Communication;

import com.example.nolan.polipal.UserData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by ericd on 11/4/2017.
 */

public class UserHandler {

    private DatabaseReference usersRef;

    private DatabaseReference onlineUsersRef;
    public static UserHandler instance;

    public UserHandler() {
        usersRef = FirebaseDatabase.getInstance().getReference("/Users/");
        onlineUsersRef = FirebaseDatabase.getInstance().getReference("/OnlineUsers/");
        instance = this;
    }


    public void addNewUser(UserData data){
        DatabaseReference user = usersRef.push();
        data.setUID(user.getKey());
        user.child("name").setValue(data.getName());
        user.child("email").setValue(data.getEmail());
        user.child("password").setValue(data.getPassword());
        user.child("politicalParty").setValue(data.getPoliticalParty());
        user.child("hobbies").setValue(arrayListToCSV(data.getHobbies()));
        user.child("policyInterests").setValue(arrayListToCSV(data.getPolicyInterests()));
        user.child("points").setValue("0/0");
        user.child("twitter").setValue(data.getTwitter());
        user.child("facebook").setValue(data.getFacebook());
        user.child("instagram").setValue(data.getInstagram());
        user.child("snapchat").setValue(data.getSnapchat());
    }

    private final UserData user = new UserData();

    private boolean runOnce = false;

    public UserData retrieveUser(final String email, final String password){
        System.out.println(email + " " + password);
        final Semaphore lock = new Semaphore(0);

        Thread t = new Thread(new Runnable(){
            boolean found = false;

            @Override
            public void run() {
                if(!runOnce){

                    try{
                        URL url = new URL("https://polipal-d2e24.firebaseio.com/Users.json");

                        HttpsURLConnection c = (HttpsURLConnection) url.openConnection();

                        c.setRequestMethod("GET");
                        c.setDoOutput(false);
                        c.setDoInput(true);

                        BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));

                        String data = in.readLine();
                        System.out.println(data);
                        String[] json = data.split(":|\",|\\},");

                        for(int i = 0; i < json.length && !found; i+=2){
                            String key = json[i].replaceAll("\"", "");
                            String value = json[i+1].replaceAll("\"", "");
                            value = value.replaceAll("\\{", "");
                            value = value.replaceAll("\\}", "");

                            if(value.equals("email")){
                                key = key.replaceAll("\\{", "");
                                user.setUID(key);
                            }

                            switch(key){
                                case "name":
                                    user.setName(value);
                                    break;
                                case "{email":
                                    user.setEmail(value);
                                    break;
                                case "password":
                                    user.setPassword(value);
                                    break;
                                case "policyInterests":
                                    user.setPolicyInterests(csvToStringArray(value));
                                    break;
                                case "hobbies":
                                    user.setHobbies(csvToStringArray(value));
                                    break;
                                case "politicalParty":
                                    user.setPoliticalParty(value);
                                    break;
                                case "twitter":
                                    user.setTwitter(value);
                                    break;
                                case "facebook":
                                    user.setFacebook(value);
                                    break;
                                case "snapchat":
                                    user.setSnapchat(value);
                                    break;
                                case "instagram":
                                    user.setInstagram(value);
                                    break;
                                case "points":
                                    user.setPoints(value);
                                default:
                                    i--;
                            }
                            System.out.println(user.getEmail() + " " + user.getPassword() + " " + user.getName() + " " + user.getPoliticalParty() + " " + user.getPolicyInterests() + " " + user.getHobbies());
                            if(user.getEmail() != null && user.getPassword() != null && user.getName() != null && user.getPoliticalParty() != null && user.getPolicyInterests() != null && user.getHobbies() != null){


                                if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
                                    found = true;
                                    lock.release();
                                    runOnce = true;
                                    return;
                                }
                                else{
                                    user.setEmail(null);
                                    user.setPassword(null);
                                    user.setName(null);
                                    user.setPolicyInterests(null);
                                    user.setHobbies(null);
                                    user.setPoliticalParty(null);
                                    user.setSnapchat(null);
                                    user.setInstagram(null);
                                    user.setFacebook(null);
                                    user.setTwitter(null);
                                    user.setUID(null);
                                }
                            }
                        }
                    }
                    catch(IOException ex){}
                    if(!found){
                        user.setName("null");
                        user.setEmail("null");
                        user.setPoliticalParty("null");
                    }
                    runOnce = true;
                    lock.release();
                }
            }
        });
        t.start();
        lock.acquireUninterruptibly();

        return user;
    }

    private String arrayListToCSV(ArrayList<String> array){
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < array.size(); i++){
            output.append(array.get(i) + ",");
        }

        output.deleteCharAt(output.length() - 1);

        return output.toString();
    }

    private ArrayList<String> csvToStringArray(String csv){
        String[] array = csv.split(",");

        ArrayList<String> list = new ArrayList<String>();

        for(int i = 0; i < array.length; i++){
            list.add(array[i]);
        }

        return list;
    }

    public void goOnline(UserData data, ArrayList<String> topChoices, String time){
        DatabaseReference ref = onlineUsersRef.child(data.getUID());
        ref.child("politicalParty").setValue(data.getPoliticalParty());
        ref.child("topChoices").setValue(arrayListToCSV(topChoices));
        ref.child("time").setValue(time);
    }

    public void removeFromOnline(UserData data){
        onlineUsersRef.child(data.getUID()).child("politicalParty").removeValue();
        onlineUsersRef.child(data.getUID()).child("topChoices").removeValue();
        onlineUsersRef.child(data.getUID()).child("time").removeValue();
        onlineUsersRef.child(data.getUID()).removeValue();
    }

    public void updatePoints(UserData data){
        usersRef.child(data.getUID()).child("points").setValue(data.getPointsString());
    }
}
