package com.example.cyhunt;

import android.content.Intent;
import android.content.SharedPreferences;
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

    private void updateCompletedLocation() {
        // 1. Update hunt location to mark as completed (for shared use like the map)
        DocumentReference locationRef = db.collection("scavengerHunts")
                .document(huntId)
                .collection("locations")
                .document(locationId);

        locationRef.update("isQuizCompleted", true)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Marked location completed in hunt"))
                .addOnFailureListener(e -> Log.e("Firestore", "Error updating hunt location", e));

        // 2. Store completed locationId in SharedPreferences for MainActivity
        SharedPreferences prefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("locationId", locationId);  // Store the locationId
        editor.putString("huntId", huntId);  // Store the huntId
        editor.apply();  // Apply changes
    }


    private void markLocationAsCompletedLocally(String locationId) {
        // Get the SharedPreferences
        SharedPreferences prefs = getSharedPreferences("completedLocations", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Store the location as completed
        editor.putBoolean(locationId, true);  // Store with the location ID as the key and true as the value
        editor.apply();  // Commit the changes to SharedPreferences
        Log.d("SharedPreferences", "Location " + locationId + " completed: " + true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_hints);

        // Try to get huntId from the Intent first
        huntId = getIntent().getStringExtra("huntId");
        if (huntId != null) {
            Log.d("MainActivity", "Hunt ID from Intent: " + huntId);
        } else {
            // If huntId is not passed, use fallback
            huntId = getSharedPreferences("userPrefs", MODE_PRIVATE).getString("huntId", null);
            if (huntId != null) {
                Log.d("MainActivity", "Hunt ID from SharedPreferences: " + huntId);
            } else {
                Log.e("MainActivity", "Hunt ID not found in Intent or SharedPreferences.");
            }
        }

        db = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();  // Get current user ID

        hint1 = findViewById(R.id.location_hint_1);
        hint2 = findViewById(R.id.location_hint_2);
        hint3 = findViewById(R.id.location_hint_3);
        question = findViewById(R.id.location_quiz_question);
        guess = findViewById(R.id.location_quiz_guess_edit_txt);
        submit_btn = findViewById(R.id.location_submit_guess_btn);

        if (getIntent().hasExtra("selected_location")) {
            location = getIntent().getParcelableExtra("selected_location");
            hint1.setText(location.getHint1());
            hint2.setText(location.getHint2());
            hint3.setText(location.getHint3());
            question.setText(location.getQuizQuestion());
            locationId = location.getLocationID();
        }

        // Use finalHuntId when the user clicks the submit button
        String finalHuntId = huntId;
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Quiz:", "guess" + guess.getText().toString() + "answer" + location.getQuizAnswer());
                if (guess.getText().toString().equals(location.getQuizAnswer().toLowerCase())) {
                    // Correct guess
                    Toast.makeText(LocationHintsActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    updateCompletedLocation();  // Mark the location as completed

                    // Navigate to the main activity
                    Intent intent = new Intent(LocationHintsActivity.this, MainActivity.class);
                    intent.putExtra("huntId", finalHuntId);  // Pass the huntId
                    intent.putExtra("location", locationId);
                    startActivity(intent);
                } else {
                    // Display try again
                    Toast.makeText(LocationHintsActivity.this, "Incorrect answer - please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_location_hints);
//
//        String huntId = getIntent().getStringExtra("huntId");
//        if (huntId != null) {
//            Log.d("MainActivity", "Hunt ID: " + huntId);
//        } else {
//            db = FirebaseFirestore.getInstance();
//            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();  // Get current user ID
//
//            hint1 = findViewById(R.id.location_hint_1);
//            hint2 = findViewById(R.id.location_hint_2);
//            hint3 = findViewById(R.id.location_hint_3);
//            question = findViewById(R.id.location_quiz_question);
//            guess = findViewById(R.id.location_quiz_guess_edit_txt);
//            submit_btn = findViewById(R.id.location_submit_guess_btn);
//
//
//            if (getIntent().hasExtra("selected_location")) {
//                location = getIntent().getParcelableExtra("selected_location");
//                huntId = getIntent().getParcelableExtra("selected_hunt");
//                hint1.setText(location.getHint1());
//                hint2.setText(location.getHint2());
//                hint3.setText(location.getHint3());
//                question.setText(location.getQuizQuestion());
//                locationId = location.getLocationID();
//
//            }
//
//
//            String finalHuntId = huntId;
//            submit_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    final String huntIdFinal = finalHuntId;
//                    Log.d("Quiz:", "guess" + guess.getText().toString() + "answer" + location.getQuizAnswer());
//                    if (guess.getText().toString().equals(location.getQuizAnswer().toLowerCase())) {
//                        // Correct guess
//                        Toast.makeText(LocationHintsActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
//                         updateCompletedLocation();  // Mark the location as completed
////                        SharedPreferences prefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
////                        String huntId = prefs.getString("huntId", null);  // Retrieve huntId
////                        if (huntId != null) {
////                            Log.d("MainActivity", "Hunt ID from SharedPreferences: " + huntId);
////                        } else {
////                            Log.d("MainActivity", "Hunt ID not found in SharedPreferences.");
////                        }
////                        markLocationAsCompletedLocally(locationId);
//
//                        // Navigate to the main activity (or wherever the player should go next)
//                        Intent intent = new Intent(LocationHintsActivity.this, MainActivity.class);
//                        intent.putExtra("huntId", finalHuntId);  // Pass the huntId
//                        startActivity(intent);
//                    } else {
//                        // Display try again
//                        Toast.makeText(LocationHintsActivity.this, "Incorrect answer - please try again", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//
//
//        }
//    }
}