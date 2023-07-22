package com.jovanovic.stefan.sqlitetutorial;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class WaitingActivity extends AppCompatActivity {

    private TextView appname;
    private LottieAnimationView lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        appname = (TextView) findViewById(R.id.appname);
        lottie = (LottieAnimationView) findViewById(R.id.lottie);
        appname.animate().translationY(-2400).setDuration(2700).setStartDelay(0);
        appname.animate().translationX(2000).setDuration(2000).setStartDelay(2900);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(),Login_Activity.class);
                startActivity(i);
            }
        },3000);
    }
}