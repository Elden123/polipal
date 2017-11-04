package com.example.nolan.polipal;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Conversation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        ArrayList<String> matchedOn = getMatchedOn();
        String buffer = "";

        TextView matchedText = (TextView) findViewById(R.id.matchedText);

        for(int i = 0; i < matchedOn.size(); i++) {
            buffer = matchedText.getText().toString();
            matchedText.setText(buffer + ", " + matchedOn.get(i));
        }

    }

    public ArrayList<String> getMatchedOn() {
        ArrayList<String> matchedOn = new ArrayList<String>();
        matchedOn.add("Gun Control");
        matchedOn.add("Abortion");
        matchedOn.add("Immigration");
        matchedOn.add("Shopping");
        matchedOn.add("Cooking");
        matchedOn.add("Playing Sports");

        return(matchedOn);
    }
}
