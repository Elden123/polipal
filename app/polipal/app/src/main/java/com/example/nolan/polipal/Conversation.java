package com.example.nolan.polipal;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Conversation extends AppCompatActivity {

    LinearLayout holderLayout;

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

        addMessage("asdf", true);
        addMessage("asdf", false);
        addMessage("asdf", false);
        addMessage("asdf", false);
        addMessage("asdf", true);
        addMessage("asdf", false);

    }


    public void addMessage(String message, boolean isUser) {

        LinearLayout LL = new LinearLayout(this);
        TextView tv1 = new TextView(this);
        TextView tv2 = new TextView(this);
        LL.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, Gravity.CENTER);

        //other person
        LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.LEFT);

        //user
        LinearLayout.LayoutParams tv2Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.LEFT);

        LLParams.weight = 1;
        tv1Params.weight = 1;
        tv2Params.weight = 1;
        tv1.setLayoutParams(tv1Params);
        tv2.setLayoutParams(tv2Params);

        if(isUser) {
            tv1.setText(" ");
            tv2.setText(message);
            tv2.setBackgroundColor(Color.parseColor("#48C5FC"));
        } else {
            tv2.setText(" ");
            tv1.setText(message);
            tv1.setBackgroundColor(Color.parseColor("#E8336F"));
        }

        tv1.setTextColor(Color.parseColor("#000000"));
        tv1.setTextSize(18);
        tv2.setTextColor(Color.parseColor("#000000"));
        tv2.setTextSize(18);
        LL.setLayoutParams(LLParams);

        LL.addView(tv1);
        LL.addView(tv2);

        holderLayout = (LinearLayout) findViewById(R.id.lLayout);
        holderLayout.addView(LL);




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
