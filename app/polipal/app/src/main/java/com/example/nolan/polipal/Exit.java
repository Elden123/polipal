package com.example.nolan.polipal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.widget.Button;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.Gravity;

public class Exit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);
        /*RelativeLayout r = (RelativeLayout) findViewById(R.id.activity_exit);
        r.setBackgroundColor(Color.parseColor("#303030"));

        int points = 82; //need to fill in with stephanie's score


        TextView disp = (TextView) findViewById(R.id.disp);
        String[] congr = {"Congratulations!", "Less congratulations.", "You kind of suck."};

        if (points>=75) {
            printScore("#7a9fc6", points);
            disp.setText(congr[0]);

        } else if (points<=25) {
            printScore("#ca7373", points);
            disp.setText(congr[2]);
        } else {
            printScore("#f4dbdb", points);
            disp.setText(congr[1]);
        }

        //are we putting social media stuff here

        Button returnHomeB = (Button) findViewById(R.id.returnHomeB);

        returnHomeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toReturnHome = new Intent(Exit.this, com.example.nolan.polipal.Decide.class);
                Exit.this.startActivity(toReturnHome);
            }
        });

        public void printScore(String color, int scre) {
            String s = scre + "";
            SpannableString ss1=  new SpannableString(s);
            ss1.setSpan(new RelativeSizeSpan(4f), 0, 5, 0); // set size
            TextView sco = (TextView) findViewById(R.id.sco);
            sco.setTextColor(Color.parseColor(color));
            sco.setText(ss1);
        }*/
    }
}
