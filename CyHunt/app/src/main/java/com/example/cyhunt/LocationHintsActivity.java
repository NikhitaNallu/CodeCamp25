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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class LocationHintsActivity extends AppCompatActivity {

    private LocationObject location;
    private FirebaseFirestore db;
    private String userId;

    private String huntId;
    TextView hint1;
    TextView hint2;
    TextView hint3;
    TextView question;
    EditText guess;
    Button submit_btn;
    String locationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_hints);

        db = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();  // Get current user ID

        hint1 = findViewById(R.id.location_hint_1);
        hint2 = findViewById(R.id.location_hint_2);
        hint3 = findViewById(R.id.location_hint_3);
        question = findViewById(R.id.location_quiz_question);
        guess = findViewById(R.id.location_quiz_guess_edit_txt);
        submit_btn = findViewById(R.id.location_submit_guess_btn);


        if(getIntent().hasExtra("selected_location")){
            location = getIntent().getParcelableExtra("selected_location");
            huntId = getIntent().getParcelableExtra("selected_hunt");
            hint1.setText(location.getHint1());
            hint2.setText(location.getHint2());
            hint3.setText(location.getHint3());
            question.setText(location.getQuizQuestion());
            locationId = location.getLocationID();

        }


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Quiz:", "guess" + guess.getText().toString() + "answer" + location.getQuizAnswer());
                if(guess.getText().toString().equals(location.getQuizAnswer().toLowerCase())){
                    //Correct guess
                    Toast.makeText(LocationHintsActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    updateCompletedLocation();

                    // Navigate to the main activity (or wherever the player should go next)
                    Intent intent = new Intent(LocationHintsActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    //Display try again
                    Toast.makeText(LocationHintsActivity.this, "Incorrect answer - please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void updateCompletedLocation() {
        // Reference to the user's document in Firestore
        DocumentReference userRef = db.collection("users").document(userId)
                .collection("hunts").document(huntId)  // Reference to the specific hunt
                .collection("completedLocations").document(locationId); // Reference to the specific location

        // Update the location document to mark it as completed
        userRef.update("completed", true)  // Assuming you have a "completed" field in the location document
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Location marked as completed"))
                .addOnFailureListener(e -> Log.e("Firestore", "Error updating location", e));
    }

}