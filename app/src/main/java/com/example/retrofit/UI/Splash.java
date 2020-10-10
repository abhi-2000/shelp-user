package com.example.retrofit.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.retrofit.R;
import com.example.retrofit.Sharedprefs;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                checkuser();
            }
        }, 2500);
    }

    public void checkuser() {
        Boolean check = Boolean.valueOf(Sharedprefs.readShared(Splash.this, "Clip", "true"));

        Intent intro = new Intent(Splash.this, SignupActivity.class);
        intro.putExtra("Clip", check);

        if (check) {
            startActivity(intro);
        } else {
            Intent mainintent = new Intent(Splash.this, student.class);
            startActivity(mainintent);
        }
    }
}