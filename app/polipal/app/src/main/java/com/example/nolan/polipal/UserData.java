package com.example.nolan.polipal;

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

    public UserData(){}

    public UserData(String name){
        this.name = name;
    }

    public void setName(String name){ this.name = name; }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){ this.email = email; }

    public String getPassword(){
        return email;
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

    public void setPoliticalParty(String politicalParty){
        this.politicalParty = politicalParty;
    }

    public ArrayList<String> getPolicyInterests(){
        return policyInterests;
    }

    public void setPolicyInterests(ArrayList<String> policyInterests){ this.policyInterests = policyInterests; }
}
