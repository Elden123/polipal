package com.example.nolan.polipal;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.github.beijingstrongbow.Communication.MessageHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Conversation extends AppCompatActivity {

    LinearLayout holderLayout;
    ScrollView sView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        MessageHandler.mh.setConversation(this);
        sView = (ScrollView) findViewById(R.id.sLayout);

        ArrayList<String> matchedOn = getMatchedOn();
        String buffer = "";

        TextView matchedText = (TextView) findViewById(R.id.matchedText);
        Button sendButton = (Button) findViewById(R.id.sendButton);
        final EditText messageToSend = (EditText) findViewById(R.id.messageToSend);

        noMatchError();


        for (int i = 0; i < matchedOn.size(); i++) {
            buffer = matchedText.getText().toString();
            if (i > 0) {
                matchedText.setText(buffer + ", " + matchedOn.get(i));
            } else {
                matchedText.setText(buffer + matchedOn.get(i));
            }

        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageHandler.mh.sendMessage(messageToSend.getText().toString());
                showMyMessage(messageToSend.getText().toString());
                messageToSend.setText("");
            }
        });

    }

    public void showMyMessage(String message) {
        addMessage(message, true);
    }

    public void showTheirMessage(String message) {
        addMessage(message, false);
    }

    public void addMessage(String message, boolean isUser) {

        LinearLayout LL = new LinearLayout(this);
        TextView tv1 = new TextView(this);
        LL.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.LEFT);

        LLParams.weight = 1;
        tv1Params.weight = 1;
        tv1Params.setMargins(0, 30, 0, 30);
        tv1.setLayoutParams(tv1Params);

        if (isUser) {
            tv1.setTextColor(Color.parseColor("#48C5FC"));
        } else {
            tv1.setTextColor(Color.parseColor("#E8336F"));
        }
        tv1.setText(message);
        tv1.setTextSize(18);
        LL.setLayoutParams(LLParams);

        LL.addView(tv1);

        holderLayout = (LinearLayout) findViewById(R.id.lLayout);
        holderLayout.addView(LL);


    }

    public void noMatchError() {
        if (MessageHandler.mh.isEmpty()) {
            Toast.makeText(Conversation.this, "No match was found",
                    Toast.LENGTH_LONG).show();
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

        return (matchedOn);
    }
<<<<<<
    <HEAD

    /*
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
        tv1Params.setMargins(0,30,0,30);
        tv2Params.setMargins(0,30,0,30);
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
     */

//this stuff could go wrong, feel free to delete
        Button exitB = (Button) findViewById(R.id.exitB);

        exitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Intent toExit = new Intent(Conversation.this, com.example.nolan.polipal.Exit.class);
                Conversation.this.startActivity(toExit);
            }
        });

}