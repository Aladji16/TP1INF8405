package com.example.tp1inf8405;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class GameActivity extends AppCompatActivity {

    public int compteur = 0;


    public int isGameOver(HashMap<Integer,ButtonToeClass> positionMap, ButtonToeClass lastPlay, int gridSize)
            //retourne 0 si personne n'a gagné, 1 si c'est le joueur 1, 2 si c'est le joueur 2
    {

        int lastPlayer = lastPlay.getPlayer();
        int lastPlayPosition = lastPlay.getPosition();


        int row = lastPlayPosition / gridSize;
        int col = lastPlayPosition % gridSize;




        //vérification ligne
        int i = row * gridSize;
        ButtonToeClass button = positionMap.get(i);

        //pour tests
        for (int j = row * gridSize; j < row * gridSize + gridSize; j++)
        {
            ButtonToeClass buttonTest = positionMap.get(j);
            Log.d("STATE",String.valueOf(buttonTest.getPlayer()));

        }

        while ((i < row * gridSize + gridSize) && button.getPlayer() == lastPlayer)
        {
            i += 1;

            button = positionMap.get(i);

        }
        if (i == row + gridSize)
        {
            return lastPlayer;
        }




        //vérification colonne
        i = col;
        button = positionMap.get(i);
        while ( (i < col + gridSize * gridSize) && button.getPlayer() == lastPlayer)
        {
            i += gridSize;
            button = positionMap.get(i);

        }

        if (i == col + gridSize * gridSize)
        {
            return lastPlayer;
        }



        //vérification diagonale "gauche"
        if (row == col)
        {
            i = 0;
            button = positionMap.get(i);

            while (i < gridSize*(gridSize + 1) &&  button.getPlayer() == lastPlayer)
            {
                i += gridSize+1;
                button = positionMap.get(i);

            }
            if (i == gridSize*(gridSize + 1))
            {
                return lastPlayer;
            }

        }


        //vérification diagonale "droite"
        if (row + col == gridSize - 1)
        {

            i = gridSize - 1;
            button = positionMap.get(i);

            while (i < gridSize*gridSize - 1 &&  button.getPlayer() == lastPlayer)
            {
                i += gridSize-1;
                button = positionMap.get(i);

            }
            if (i == gridSize*gridSize - 1)
            {
                return lastPlayer;
            }

        }

        return 0;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //https://stackoverflow.com/questions/51181747/passing-arguments-to-intent-android
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView textViewTurn = (TextView) findViewById(R.id.textViewTurn);

        HashMap<Integer,ButtonToeClass> positionMap = new HashMap<Integer,ButtonToeClass>(); //hashmap pour lier les positions aux boutons

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        int gridSize = 3;
        if (extras.containsKey("size")) {
            gridSize = Integer.parseInt(i.getCharSequenceExtra("size").toString());
        }


        androidx.gridlayout.widget.GridLayout gameGrid = findViewById(R.id.gameGrid);
        gameGrid.setColumnCount(gridSize);
        gameGrid.setRowCount(gridSize);

        //        code pour créer et afficher la grille
        for (int j = 0; j < gridSize * gridSize; j++) {
            ButtonToeClass imageButton = new ButtonToeClass(getApplicationContext(),j);

            positionMap.put(j, imageButton);

//                    https://www.android-examples.com/change-imagebutton-image-width-height-in-android-programmatically/
            ViewGroup.LayoutParams layoutParams =  new ViewGroup.LayoutParams(120, 120);

            imageButton.setLayoutParams(layoutParams);

            int finalGridSize = gridSize;

            imageButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {



                    compteur++;
                    imageButton.setAdjustViewBounds(true); //pour pouvoir modifier la taille de l'image
                    imageButton.setBackgroundColor(Color.WHITE);

                    CharSequence text = "Tour du joueur 1";
                    if (compteur % 2 == 1)
                    { //o
                        imageButton.setPlayer(1);
                        imageButton.setImageResource(R.drawable.post_97990_1260678636);


                        text = "Tour du joueur 2";

                    }

                    else
                    { //x
                        imageButton.setPlayer(2);
                        imageButton.setImageResource(R.drawable.post_97990_1260678617);

                    }


                    textViewTurn.setText(text);
                    imageButton.setClickable(false);

                    int winner = isGameOver(positionMap, imageButton, finalGridSize);
                    if (winner > 0)
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "Le joueur "+ String.valueOf(winner) + " a gagné", Toast.LENGTH_SHORT);
                        toast.show();
                    }


                }
            });
            gameGrid.addView(imageButton);
            imageButton.setImageResource(R.drawable.simple_black_frame_md); //ne fonctionne pas???

        }

        Button resetButton = (Button) findViewById(R.id.resetButton);


        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //https://www.thetopsites.net/article/58948853.shtml
                for (int i = 0; i < gameGrid.getChildCount(); i++)
                {
//                   Log.d("STATE","hahahaha");
                   ButtonToeClass imageButton = (ButtonToeClass) gameGrid.getChildAt(i);
                   imageButton.setClickable(true);
                   imageButton.setPlayer(0);
                   imageButton.setImageResource(R.drawable.simple_black_frame_md); //ne fonctionne pas???
                   compteur = 0;
                   textViewTurn.setText("Tour du joueur 1");


                }

            }
        });


    }
}
