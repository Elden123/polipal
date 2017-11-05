package com.example.nolan.polipal;

import com.github.beijingstrongbow.Communication.UserHandler;

import java.util.ArrayList;

/**
 * Created by ericd on 11/4/2017.
 */

public class UserData {

    private String name;

    private String password;

    private String email;

    private ArrayList<String> hobbies;

    private String politicalParty;

    private ArrayList<String> policyInterests;

    private String UID;

    //represented as <total points>/<total conversations> i.e. 1012/5 for 1012 points over 5 conversations
    private String pointsString;

    public UserData(){
    }

    public UserData(String name){
        this.name = name;
    }

    public void setName(String name){ this.name = name; }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){ this.email = email; }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public ArrayList<String> getHobbies(){
        return hobbies;
    }

    public void setHobbies(ArrayList<String> hobbies){
        this.hobbies = hobbies;
    }

    public String getName(){
        return name;
    }

    public String getPoliticalParty(){
        return politicalParty;
    }

    public void setPoliticalParty(String value){
        this.politicalParty = value;
    }

    public ArrayList<String> getPolicyInterests(){
        return policyInterests;
    }

    public void setPolicyInterests(ArrayList<String> policyInterests){ this.policyInterests = policyInterests; }

    public String getUID() { return UID; }

    public void setUID(String UID) { this.UID = UID; }

    public void setPoints(String points){
        this.pointsString = points;
    }

    public static UserData ud;

    public void addPoints(int add, UserHandler handler){
        int points = Integer.parseInt(pointsString.substring(0, pointsString.indexOf("/")));
        int conversations = Integer.parseInt(pointsString.substring(pointsString.indexOf("/")+1));
        pointsString = (points+add) + "/" + (conversations+1);
        handler.updatePoints(this);
    }

    public int getPoints(){
        int points = Integer.parseInt(pointsString.substring(0, pointsString.indexOf("/")));
        int conversations = Integer.parseInt(pointsString.substring(pointsString.indexOf("/")+1));
        return points/conversations;
    }

    public String getPointsString(){
        return pointsString;
    }
}
