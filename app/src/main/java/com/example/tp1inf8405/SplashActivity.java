package com.example.tp1inf8405;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    ImageView ghostImg;
    Animation testAnimation;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Splash);
        setContentView(R.layout.activity_splash);

        // Rajout d'un effet d'animation (translation du bas vers le centre) de l'image
        ghostImg = findViewById(R.id.splash_ghost);
        testAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.test_animation);
        ghostImg.setAnimation(testAnimation);

        // Rajout du son
        MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.splash);
        mp.start();

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mp.stop();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        },4000);
    }
}