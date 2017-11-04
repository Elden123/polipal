package com.github.beijingstrongbow.Communication;

import android.util.JsonReader;

import com.example.nolan.polipal.UserData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by ericd on 11/4/2017.
 */

public class UserHandler {

    private DatabaseReference usersRef;

    public UserHandler() {
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

    private final UserData user = new UserData();

    public UserData retrieveUser(final String email, final String password){

        final Semaphore lock = new Semaphore(0);

        Thread t = new Thread(new Runnable(){

            @Override
            public void run() {
                boolean found = false;

                try{
                    URL url = new URL("https://polipal-d2e24.firebaseio.com/Users.json");

                    HttpsURLConnection c = (HttpsURLConnection) url.openConnection();

                    c.setRequestMethod("GET");
                    c.setDoOutput(false);
                    c.setDoInput(true);

                    BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));

                    String data = in.readLine();
                    String[] json = data.split(":|\",|\\},");

                    for(int i = 1; i < json.length; i+=2){
                        String key = json[i].replaceAll("\"", "");
                        String value = json[i+1].replaceAll("\"", "");
                        value = value.replaceAll("\\{", "");
                        value = value.replaceAll("\\}", "");
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
                            default:
                                i--;
                        }
                        if(user.getEmail() != null && user.getPassword() != null){
                            if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
                                found = true;
                            }
                        }
                    }
                }
                catch(IOException ex){}

                if(!found){
                    user.setName("null");
                    user.setEmail("null");
                    user.setPassword("null");
                    user.setPoliticalParty("null");
                }
                lock.release();
            }
        });
        t.start();

        lock.acquireUninterruptibly();
        return user;
    }

    private void parseJsonObject(JsonReader json, UserData current) throws IOException{
        json.beginObject();

        while(json.hasNext()){
            switch(json.nextName()){
                case "NAME":
                    current.setName(json.nextString());
                    break;
                case "EMAIL":
                    current.setEmail(json.nextString());
                    break;
                case "PASSWORD":
                    current.setPassword(json.nextString());
                    break;
                case "HOBBIES":
                    current.setHobbies(csvToStringArray(json.nextString()));
                    break;
                case "POLITICALINTERESTS":
                    current.setPolicyInterests(csvToStringArray(json.nextString()));
                    break;
                case "POLITICALPARTY":
                    current.setEmail(json.nextString());
                    break;
            }
        }

        json.endObject();
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
}
