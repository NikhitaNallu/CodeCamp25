package com.example.cyhunt;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationHintsActivity extends AppCompatActivity {

    private LocationObject location;

    TextView hint1;
    TextView hint2;
    TextView hint3;
    TextView question;
    EditText guess;
    Button submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_hints);


        hint1 = findViewById(R.id.location_hint_1);
        hint2 = findViewById(R.id.location_hint_2);
        hint3 = findViewById(R.id.location_hint_3);
        question = findViewById(R.id.location_quiz_question);
        guess = findViewById(R.id.location_quiz_guess_edit_txt);
        submit_btn = findViewById(R.id.location_submit_guess_btn);


        if(getIntent().hasExtra("selected_location")){
            location = getIntent().getParcelableExtra("selected_location");
            hint1.setText(location.getHint1());
            hint2.setText(location.getHint2());
            hint3.setText(location.getHint3());
            question.setText(location.getQuizQuestion());

        } else {
            location = new LocationObject("null", "null", "null", "null", "null", 0, 0, "null", "null", "null");
        }


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Quiz:", "guess" + guess.getText().toString() + "answer" + location.getQuizAnswer());
                if(guess.getText().toString().equals(location.getQuizAnswer().toLowerCase())){
                    //Correct guess
                    Intent intent = new Intent(LocationHintsActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    //Display try again
                }
            }
        });
    }

}