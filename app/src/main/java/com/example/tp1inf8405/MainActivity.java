package com.example.tp1inf8405;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playB = findViewById(R.id.playButton);
        Button aboutB = findViewById(R.id.aboutButton);
        Button exitB = findViewById(R.id.exitButton);

        playB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "You chose PLAY!", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        aboutB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "You chose ABOUT!", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        exitB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Exit the application
                finish();
                System.exit(0);
            }
        });
    }
}