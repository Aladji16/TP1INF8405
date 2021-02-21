package com.example.tp1inf8405;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class ChooseGridSizeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        int player1 = 0;
        int player2 = 0;
        if (extras != null ) {
            if (extras.containsKey("player1_wins")) {
                player1 = i.getIntExtra("player1_wins",0);
            }

            if (extras.containsKey("player2_wins")) {
                player2 = i.getIntExtra("player2_wins",0);
            }
        }
        final int player1_wins = player1;
        final int player2_wins = player2;

        Button confirmButton = findViewById(R.id.buttonConfirm);
        RadioGroup radioGroup = findViewById(R.id.radioSizeGroup);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RadioButton checkedButton = findViewById(radioGroup.getCheckedRadioButtonId());
                CharSequence gridSize = checkedButton.getText();

                Intent gameIntent = new Intent(getApplicationContext(), GameActivity.class);
                gameIntent.putExtra("size",gridSize);
                gameIntent.putExtra("player1_wins",player1_wins);
                gameIntent.putExtra("player2_wins",player2_wins);
                startActivity(gameIntent);
//                RadioButton checkedButton = findViewById(radioGroup.getCheckedRadioButtonId());
//
//                Toast toast = Toast.makeText(getApplicationContext(), "You chose "+ checkedButton.getText(), Toast.LENGTH_LONG);
//                toast.show();
            }
        });
    }
}