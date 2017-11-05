package com.example.nolan.polipal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.widget.Button;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.Gravity;

public class Exit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);
        RelativeLayout r = (RelativeLayout) findViewById(R.id.activity_exit);
        r.setBackgroundColor(Color.parseColor("#303030"));

        int points = Conversation.ms.getFinalScore(); //need to fill in with stephanie's score

        System.out.println("-----------------------------------"+points);
        TextView disp = (TextView) findViewById(R.id.disp);
        String[] congr = {"Good job!", "Solid work.", "Try again?"};

        ImageView twitterImage = (ImageView) findViewById(R.id.twitterImage);
        ImageView instagramImage = (ImageView) findViewById(R.id.instagramImage);
        ImageView facebookImage = (ImageView) findViewById(R.id.faceBookImage);
        ImageView snapchatImage = (ImageView) findViewById(R.id.snapchatImage);

        TextView twitterLabel = (TextView) findViewById(R.id.twitterLabel);
        TextView instagramLabel = (TextView) findViewById(R.id.instagramLabel);
        TextView facebookLabel = (TextView) findViewById(R.id.facebookLabel);
        TextView snapchatLabel = (TextView) findViewById(R.id.facebookLabel);

        twitterImage.setVisibility(View.INVISIBLE);
        instagramImage.setVisibility(View.INVISIBLE);
        facebookImage.setVisibility(View.INVISIBLE);
        snapchatImage.setVisibility(View.INVISIBLE);

        twitterLabel.setVisibility(View.INVISIBLE);
        instagramLabel.setVisibility(View.INVISIBLE);
        facebookLabel.setVisibility(View.INVISIBLE);
        snapchatLabel.setVisibility(View.INVISIBLE);


        String twitter = UserData.ud.getTwitter();
        String instagram = UserData.ud.getInstagram();
        String facebook = UserData.ud.getFacebook();
        String snapchat = UserData.ud.getSnapchat();

        if (points>=75) {
            printScore(points);
            disp.setText(congr[0]);

            if(twitter.length() > 0) {
                twitterImage.setVisibility(View.VISIBLE);
                twitterLabel.setVisibility(View.VISIBLE);
                twitterLabel.setText(twitter);
            }
            if(instagram.length() > 0) {
                instagramImage.setVisibility(View.VISIBLE);
                instagramLabel.setVisibility(View.VISIBLE);
                instagramLabel.setText(instagram);
            }
            if(facebook.length() > 0) {
                facebookImage.setVisibility(View.VISIBLE);
                facebookLabel.setVisibility(View.VISIBLE);
                facebookLabel.setText(facebook);
            }
            if(snapchat.length() > 0) {
                snapchatImage.setVisibility(View.VISIBLE);
                snapchatLabel.setVisibility(View.VISIBLE);
                snapchatLabel.setText(snapchat);
            }

        } else if (points<=25) {
            printScore(points);
            disp.setText(congr[2]);
        } else {
            printScore(points);
            disp.setText(congr[1]);
        }


        Button returnHomeB = (Button) findViewById(R.id.returnHomeB);

        returnHomeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toReturnHome = new Intent(Exit.this, com.example.nolan.polipal.Decide.class);
                Exit.this.startActivity(toReturnHome);
            }
        });
    }

    public void printScore(int scre) {
        String s = scre + "";
        TextView sco = (TextView) findViewById(R.id.sco);
        sco.setText(s);
    }
}
