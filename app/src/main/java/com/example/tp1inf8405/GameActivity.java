package com.example.tp1inf8405;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //https://stackoverflow.com/questions/51181747/passing-arguments-to-intent-android
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        int gridSize = 3;
        if (extras.containsKey("size")) {
            gridSize = Integer.parseInt(i.getCharSequenceExtra("size").toString());
        }

        androidx.gridlayout.widget.GridLayout gameGrid = findViewById(R.id.gameGrid);
        gameGrid.setColumnCount(gridSize);
        gameGrid.setRowCount(gridSize);
        for (int j = 0; j < gridSize * gridSize; j++) {
            Button button = new Button(getApplicationContext());

            gameGrid.addView(button);
        }




//        Toast toast = Toast.makeText(getApplicationContext(), "You chose "+ String.valueOf(gridSize), Toast.LENGTH_LONG);
//        toast.show();
    }
}
