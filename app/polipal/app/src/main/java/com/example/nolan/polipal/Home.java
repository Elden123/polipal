package com.example.nolan.polipal;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.github.beijingstrongbow.Communication.UserHandler;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button signInB = (Button) findViewById(R.id.signInB);
        Button registerB = (Button) findViewById(R.id.registerB);
        RelativeLayout r = (RelativeLayout) findViewById(R.id.activity_home);
        r.setBackgroundColor(Color.parseColor("#303030"));

        UserHandler h = new UserHandler();
        UserData d = h.retrieveUser("ericdhiggins@gmail.com", "password");
        d.addPoints(30, h);
        System.out.println(d.getPoints());

        /**
         * TODO: Change "Decide.class" to "Login.class"
         * "Decide.class" is just for testing
         */
        signInB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSignIn = new Intent(Home.this, Decide.class);
                Home.this.startActivity(toSignIn);
            }
        });

        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRegister = new Intent(Home.this, Register.class);
                Home.this.startActivity(toRegister);
            }
        });

    }
}
