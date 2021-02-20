package com.example.tp1inf8405;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    public int compteur = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //https://stackoverflow.com/questions/51181747/passing-arguments-to-intent-android
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView textViewTurn = (TextView) findViewById(R.id.textViewTurn);



        Intent i = getIntent();
        Bundle extras = i.getExtras();
        int gridSize = 3;
        if (extras.containsKey("size")) {
            gridSize = Integer.parseInt(i.getCharSequenceExtra("size").toString());
        }

        androidx.gridlayout.widget.GridLayout gameGrid = findViewById(R.id.gameGrid);
        gameGrid.setColumnCount(gridSize);
        gameGrid.setRowCount(gridSize);

        //        code pour afficher une croix
        for (int j = 0; j < gridSize * gridSize; j++) {
            ImageButton imageButton = new ImageButton(getApplicationContext());
            imageButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
//                Toast toast = Toast.makeText(getApplicationContext(), "You chose PLAY!", Toast.LENGTH_LONG);
//                toast.show();
                    compteur++;
                    imageButton.setAdjustViewBounds(true);
                    imageButton.setBackgroundColor(Color.WHITE);
                    imageButton.setImageResource(R.drawable.post_97990_1260678617);
                    CharSequence text = "Tour du joueur 1";
                    if (compteur % 2 == 1)
                    {
                        text = "Tour du joueur 2";
                    }
                    textViewTurn.setText(text);
                    imageButton.setClickable(false);


                }
            });
            gameGrid.addView(imageButton);
        }




//        code pour ajouter tous les boutons
//        for (int j = 0; j < gridSize * gridSize; j++) {
//            Button button = new Button(getApplicationContext());
//
//            gameGrid.addView(button);
//        }




//        Toast toast = Toast.makeText(getApplicationContext(), "You chose "+ String.valueOf(gridSize), Toast.LENGTH_LONG);
//        toast.show();



    }
}
