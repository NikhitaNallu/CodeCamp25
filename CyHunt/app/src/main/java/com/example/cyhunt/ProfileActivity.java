package com.example.cyhunt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProfileActivity extends AppCompatActivity {

    ImageView star_img;
    EditText name_edit_txt;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();


        star_img = findViewById(R.id.star_img);
        name_edit_txt = findViewById(R.id.name_edit_txt);


        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        // Set Pantry selected
        bottomNavigationView.setSelectedItemId(R.id.profile_home);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.hunts_home) {
                    startActivity(new Intent(getApplicationContext(), HuntsActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                } else if (itemId == R.id.map_home) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                } else if (itemId == R.id.profile_home) {
                    return true;
                }
                return false;
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

// Enable the Up button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Profile"); // Optional: set a custom title
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if the user is already logged in
        // Ensure that mAuth is initialized before using it
        if (mAuth != null) {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                String username = currentUser.getEmail();
                int indexOfAt = username.indexOf('@');
                username = username.substring(0, indexOfAt);
                name_edit_txt.setText(username);
                Log.d("ProfileActivity", "Logged in as: " + username);
            } else {
                Log.d("ProfileActivity", "No user is logged in");
            }
        } else {
            Log.e("ProfileActivity", "FirebaseAuth is not initialized");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Go to a specific activity instead of going back
            Intent intent = new Intent(this, MainActivity.class); // or whatever activity you want
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}