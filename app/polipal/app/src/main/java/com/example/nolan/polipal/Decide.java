package com.example.nolan.polipal;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Decide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decide);
        RelativeLayout r = (RelativeLayout) findViewById(R.id.activity_decide);
        r.setBackgroundColor(Color.parseColor("#303030"));

        Button startConversation = (Button) findViewById(R.id.startConversationB);

        final Bundle extras = getIntent().getExtras();

        startConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Quiz.class);

                i.putExtra("userName",extras.getString("userName"));
                i.putExtra("userEmail",extras.getString("userEmail"));
                i.putExtra("userPassword",extras.getString("userPassword"));
                i.putExtra("userParty",extras.getString("userParty"));
                i.putExtra("userTopics",extras.getStringArrayList("userTopics"));
                i.putExtra("userHobbies",extras.getStringArrayList("userHobbies"));
                i.putExtra("userId",extras.getString("userId"));
                i.putExtra("userPoints",extras.getInt("userPoints"));
                startActivity(i);
            }
        });
    }
}
