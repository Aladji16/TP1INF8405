package com.example.tp1inf8405;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playB = findViewById(R.id.playButton);
        Button aboutB = findViewById(R.id.aboutButton);
        Button exitB = findViewById(R.id.exitButton);

        playB.setOnClickListener((View view) -> {
                Intent chooseSizeIntent = new Intent(getApplicationContext(), ChooseGridSizeActivity.class);
                startActivity(chooseSizeIntent);
            });

        aboutB.setOnClickListener((View view) -> {
                Intent aboutIntent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(aboutIntent);
            });

        exitB.setOnClickListener((View view) -> {
                // Exit the application
                finishAffinity();
                System.exit(0);
            });
    }
}