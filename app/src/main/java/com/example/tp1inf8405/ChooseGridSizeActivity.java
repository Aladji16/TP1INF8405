package com.example.tp1inf8405;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

        Button confirmButton = findViewById(R.id.buttonConfirm);
        RadioGroup radioGroup = findViewById(R.id.radioSizeGroup);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RadioButton checkedButton = findViewById(radioGroup.getCheckedRadioButtonId());
                CharSequence gridSize = checkedButton.getText();

                Intent gameIntent = new Intent(getApplicationContext(), GameActivity.class);
                gameIntent.putExtra("size",gridSize);
                startActivity(gameIntent);
//                RadioButton checkedButton = findViewById(radioGroup.getCheckedRadioButtonId());
//
//                Toast toast = Toast.makeText(getApplicationContext(), "You chose "+ checkedButton.getText(), Toast.LENGTH_LONG);
//                toast.show();
            }
        });
    }
}