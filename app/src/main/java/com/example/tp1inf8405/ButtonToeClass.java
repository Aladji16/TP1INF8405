package com.example.tp1inf8405;

import android.content.Context;
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

public class ButtonToeClass extends ImageButton {
    private int player; //si le joueur 1 ou 2 a joué (débute à 0)
    private int position; //position du bouton

    public ButtonToeClass(Context context, int position)
    {
        super(context);
        this.position = position;
        player = 0;

    }


    public int getPosition()
    {
        return this.position;
    }

    public void setPlayer(int player)
    {
        this.player = player;
    }

    public int getPlayer()
    {
        return this.player;
    }

}
