package com.example.cyhunt;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import java.util.ArrayList;

public class HuntsLocationsActivity extends AppCompatActivity implements LocationObjectAdapter.OnLocationListener{

    RecyclerView recyclerHome;
    private ArrayList<LocationObject> locationArrayList;
    private String huntID;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hunts_locations);


        recyclerHome = findViewById(R.id.recycler_home);




        locationArrayList = new ArrayList<>();
        // Get the current user ID
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db = FirebaseFirestore.getInstance();


        if(getIntent().hasExtra("selected-hunt")){
            huntID = getIntent().getStringExtra("selected-hunt");
            fetchLocationsForHunt(huntID);
        }

        LocationObjectAdapter adapter = new LocationObjectAdapter(locationArrayList, this, this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);

        recyclerHome.setLayoutManager(layoutManager);
        recyclerHome.setAdapter(adapter);
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
            getSupportActionBar().setTitle("Your Title"); // Optional: set a custom title
        }
    }

    private void fetchLocationsForHunt(String huntId) {
        CollectionReference locationsRef = db.collection("scavengerHunts")
                .document(huntId)
                .collection("locations");

        locationsRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Fetch locations from the subcollection and create LocationObject
                        for (QueryDocumentSnapshot locationDoc : task.getResult()) {
                            String locationId = locationDoc.getId();
                            String locationName = locationDoc.getString("name");
                            String locationDescription = locationDoc.getString("description");
                            double latitude = locationDoc.getDouble("latitude");
                            double longitude = locationDoc.getDouble("longitude");
                            String hint1 = locationDoc.getString("hint1");
                            String hint2 = locationDoc.getString("hint2");
                            String hint3 = locationDoc.getString("hint3");
                            String image = locationDoc.getString("imageUrl");
                            String question = locationDoc.getString("quizQuestion");
                            String answer = locationDoc.getString("quizAnswer");

                            // Add each location to the huntLocations array
                            locationArrayList.add(new LocationObject(locationId, locationName, locationDescription, hint1, hint2, hint3, latitude, longitude, image, question, answer));
                        }
                        if (!locationArrayList.isEmpty()) {
                            recyclerHome.getAdapter().notifyDataSetChanged();
                        } else {
                            Log.w("Firestore", "No locations found for hunt " + huntId);
                        }

                    } else {
                        Log.e("Firestore", "Error getting locations: ", task.getException());
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // or onBackPressed()
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onLocationClick(int position) {
        LocationObject selectedLocation = locationArrayList.get(position);
        if (selectedLocation == null) {
            Log.e("Location Click", "Selected location is null at position: " + position);
            return;
        }

        Intent intent = new Intent(this, LocationHintsActivity.class);
        intent.putExtra("selected_location", selectedLocation);
        intent.putExtra("selected_hunt", huntID);
        startActivity(intent);
    }
}