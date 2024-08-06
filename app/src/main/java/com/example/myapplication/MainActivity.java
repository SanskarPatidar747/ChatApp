package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    TextView name, own1,own2;
    ImageView logo;
    FirebaseAuth auth;
    Animation topAni,bottomAni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        FirebaseAuth auth= FirebaseAuth.getInstance();
        TextView name= (TextView) findViewById(R.id.textView);
        TextView own1= (TextView) findViewById(R.id.textView2);
        TextView own2= (TextView) findViewById(R.id.textView3);
        ImageView logo= (ImageView) findViewById(R.id.imageView);
        topAni= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAni=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        logo.setAnimation(topAni);
        name.setAnimation(bottomAni);
        own1.setAnimation(bottomAni);
        own2.setAnimation(bottomAni);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (auth.getCurrentUser()==null) {
                    Intent intent = new Intent(MainActivity.this, login.class);
                    startActivities(new Intent[]{intent});
                    finish();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, splash.class);
                    startActivities(new Intent[]{intent});
                    finish();

                }

            }
        },3000);
    }
}