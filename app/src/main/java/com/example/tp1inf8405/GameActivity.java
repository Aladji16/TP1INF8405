package com.example.tp1inf8405;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
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

import com.viralypatel.sharedpreferenceshelper.lib.SharedPreferencesHelper;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    public int compteur = 0;

    SharedPreferencesHelper sph;


    public int isGameOver(HashMap<Integer,ButtonToeClass> positionMap, ButtonToeClass lastPlay, int gridSize)
            //retourne 0 si personne n'a gagné, 1 si c'est le joueur 1, 2 si c'est le joueur 2, 3 s'il n'y a plus de place
    {

        int lastPlayer = lastPlay.getPlayer();
        int lastPlayPosition = lastPlay.getPosition();


        int row = lastPlayPosition / gridSize;
        int col = lastPlayPosition % gridSize;




        //vérification ligne
        int i = row * gridSize;
        ButtonToeClass button = positionMap.get(i);


        while ((i < row * gridSize + gridSize) && button.getPlayer() == lastPlayer)
        {

            i += 1;

            button = positionMap.get(i);

        }
        if (i == row * gridSize + gridSize)
        {
            return lastPlayer;
        }




        //vérification colonne
        i = col;
        button = positionMap.get(i);



        //vrai code
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

        //si toutes les cases sont remplies sans vainqueur
        i = 0;
        button = positionMap.get(i);
        while (i < gridSize * gridSize && button.getPlayer() > 0)
        {
            i += 1;
            button = positionMap.get(i);

        }

        if (i == gridSize * gridSize )
        {
            return 3;
        }
        return 0;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //https://stackoverflow.com/questions/51181747/passing-arguments-to-intent-android
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView textViewTurn = (TextView) findViewById(R.id.textViewTurn);

        HashMap<Integer,ButtonToeClass> positionMap = new HashMap<>(); //hashmap pour lier les positions aux boutons

        sph = new SharedPreferencesHelper(this);

        int player1_wins = sph.getInt("score_p1");
        int player2_wins = sph.getInt("score_p2");


        Intent i = getIntent();
        Bundle extras = i.getExtras();




        int gridSize = 3;
        if (extras.containsKey("size")) {
            gridSize = Integer.parseInt(i.getCharSequenceExtra("size").toString());
        }

        TextView scorePlayer1 = (TextView) findViewById(R.id.scorePlayer1);
        TextView scorePlayer2 = (TextView) findViewById(R.id.scorePlayer2);


        scorePlayer1.setText(getString(R.string.score, 1, player1_wins));
        scorePlayer2.setText(getString(R.string.score, 2, player2_wins));

        androidx.gridlayout.widget.GridLayout gameGrid = findViewById(R.id.gameGrid);
        gameGrid.setColumnCount(gridSize);
        gameGrid.setRowCount(gridSize);

        //        code pour créer et afficher la grille
        for (int j = 0; j < gridSize * gridSize; j++) {
            ButtonToeClass imageButton = new ButtonToeClass(getApplicationContext(),j);

            positionMap.put(j, imageButton);

//                    https://www.android-examples.com/change-imagebutton-image-width-height-in-android-programmatically/

            ViewGroup.LayoutParams layoutParams =  new ViewGroup.LayoutParams(225, 225);

            if(gridSize == 4) {
                layoutParams =  new ViewGroup.LayoutParams(175, 175);
            } else if(gridSize == 5) {
                layoutParams =  new ViewGroup.LayoutParams(125, 125);
            }


            imageButton.setLayoutParams(layoutParams);

            int finalGridSize = gridSize;

            MediaPlayer mp_player1 = MediaPlayer.create(getBaseContext(), R.raw.player1);
            MediaPlayer mp_player2 = MediaPlayer.create(getBaseContext(), R.raw.player2);
            imageButton.setOnClickListener((View view) -> {



                    compteur++;
//                    imageButton.setAdjustViewBounds(true); //pour pouvoir modifier la taille de l'image
                    imageButton.setBackgroundColor(Color.WHITE);

                    CharSequence text = getString(R.string.tour, 1);
                    if (compteur % 2 == 1)
                    { //o
                        imageButton.setPlayer(1);
                        // Rajout du son pour le joueur 1
                        mp_player1.start();
                        imageButton.setImageResource(R.drawable.post_97990_1260678636);


                        text = getString(R.string.tour, 2);

                    }

                    else
                    { //x
                        imageButton.setPlayer(2);
                        // Rajout du son pour le joueur 2
                        mp_player2.start();
                        imageButton.setImageResource(R.drawable.post_97990_1260678617);

                    }


                    textViewTurn.setText(text);
                    imageButton.setClickable(false);

                    int winner = isGameOver(positionMap, imageButton, finalGridSize);
                    if (winner > 0)
                    {

                        //les cases ne sont plus clickables quand la partie est terminée
                        for (int k = 0; k < gameGrid.getChildCount(); k++)
                        {
                            ButtonToeClass imgButton = (ButtonToeClass) gameGrid.getChildAt(k);
                            imgButton.setClickable(false);

                        }

                        //les boutons "reset" et "changeSize" ne sont plus clickables quand la partie est terminée
                        Button resetButton = (Button) findViewById(R.id.resetButton);
                        resetButton.setClickable(false);

                        Button changeGridSizeButton = (Button) findViewById(R.id.changeGridSizeButton);
                        changeGridSizeButton.setClickable(false);


                        String toastText = "";
                        if (winner == 1) {
                            CharSequence text_score = scorePlayer1.getText();
                            String[] splitScore = text_score.toString().split("\\s+");
                            int number = Integer.parseInt(splitScore[splitScore.length - 1]);
                            scorePlayer1.setText(getString(R.string.score, 1, number + 1));
                            sph.putInt("score_p1", number+1);
                            toastText = getString(R.string.gagne, 1);
                        }
                        else if (winner == 2)
                        {
                            CharSequence text_score = scorePlayer2.getText();
                            String[] splitScore = text_score.toString().split("\\s+");
                            int number = Integer.parseInt(splitScore[splitScore.length - 1]);
                            scorePlayer2.setText(getString(R.string.score, 2, number + 1));
                            sph.putInt("score_p2", number+1);
                            toastText = getString(R.string.gagne, 2);
                        }

                        else if (winner == 3)
                        {
                            toastText = getString(R.string.nul);
                        }
                        Toast toast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG);
                        toast.show();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GameActivity.this);
                        alertDialogBuilder.setCancelable(false); //force à faire un choix

                        alertDialogBuilder.setTitle("Partie terminée.\nVoulez vous recommencer?");


//                        https://exceptionshub.com/how-to-add-multiple-buttons-on-a-single-alertdialog.html ajouter plusieurs boutons

                        alertDialogBuilder.setItems(new CharSequence[]
                                        {"Rejouer la partie", "Choisir la taille de grille", "Revenir au menu principal", "Quitter le jeu"},
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        //les boutons reset et changement de taille de grille redeviennent clickables
                                        Button resetButton = (Button) findViewById(R.id.resetButton);
                                        resetButton.setClickable(true);

                                        Button changeGridSizeButton = (Button) findViewById(R.id.changeGridSizeButton);
                                        changeGridSizeButton.setClickable(true);

                                        switch (which) {
                                            case 0:
                                                for (int i = 0; i < gameGrid.getChildCount(); i++)
                                                {
                                                    ButtonToeClass imageButton = (ButtonToeClass) gameGrid.getChildAt(i);
                                                    imageButton.setClickable(true);
                                                    imageButton.setPlayer(0);
                                                    imageButton.setImageResource(R.drawable.simple_black_frame_md);
                                                    compteur = 0;
                                                    textViewTurn.setText("Tour du joueur 1");


                                                }

                                                break;
                                            case 1:
                                                Intent chooseSizeIntent = new Intent(GameActivity.this, ChooseGridSizeActivity.class);
                                                //transférer l'historique de victoires


                                                int player1_wins = sph.getInt("score_p1");
                                                int player2_wins = sph.getInt("score_p2");

                                                chooseSizeIntent.putExtra("player1_wins",player1_wins);
                                                chooseSizeIntent.putExtra("player2_wins",player2_wins);


                                                startActivity(chooseSizeIntent);

                                                break;
                                            case 2:
                                                Intent mainMenuIntent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(mainMenuIntent);
                                                break;
                                            case 3:
                                                finishAffinity();
                                                break;
                                        }
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        new Handler().postDelayed(alertDialog::show, 3600);
                    }


                });
            gameGrid.addView(imageButton);

        }

        Button resetButton = (Button) findViewById(R.id.resetButton);


        resetButton.setOnClickListener((View view) -> {
                //https://www.thetopsites.net/article/58948853.shtml
                for (int j = 0; j < gameGrid.getChildCount(); j++)
                {
                   ButtonToeClass imageButton = (ButtonToeClass) gameGrid.getChildAt(j);
                   imageButton.setClickable(true);
                   imageButton.setPlayer(0);
                   imageButton.setImageResource(R.drawable.simple_black_frame_md);
                   compteur = 0;
                   textViewTurn.setText(getString(R.string.tour, 1));
                }
                sph.putInt("score_p1", 0); //on considère que "reset" réinitialise aussi les scores des joueurs
                sph.putInt("score_p2", 0);

                scorePlayer1.setText(getString(R.string.score, 1, 0));
                scorePlayer2.setText(getString(R.string.score, 2, 0));

            });

        Button changeGridSizeButton = (Button) findViewById(R.id.changeGridSizeButton);


        changeGridSizeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent chooseSizeIntent = new Intent(GameActivity.this, ChooseGridSizeActivity.class);

                int player1_wins = sph.getInt("score_p1");
                int player2_wins = sph.getInt("score_p2");

                chooseSizeIntent.putExtra("player1_wins",player1_wins);
                chooseSizeIntent.putExtra("player2_wins",player2_wins);


                startActivity(chooseSizeIntent);

            }
        });

    }
}
