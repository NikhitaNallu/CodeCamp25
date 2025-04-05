package com.example.cyhunt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HuntsActivity extends AppCompatActivity implements HuntsObjectAdapter.OnHuntsListener{

    RecyclerView recyclerHome;
    private ArrayList<HuntsObject> huntsArrayList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hunts);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        // Set Pantry selected
        bottomNavigationView.setSelectedItemId(R.id.hunts_home);


        recyclerHome = findViewById(R.id.recycler_home);

        huntsArrayList = new ArrayList<>();
        // Get the current user ID
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db = FirebaseFirestore.getInstance();

        // Query Firestore for all hunts
        fetchHuntsFromFirestore();

        HuntsObjectAdapter adapter = new HuntsObjectAdapter(huntsArrayList, this, this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);

        recyclerHome.setLayoutManager(layoutManager);
        recyclerHome.setAdapter(adapter);



        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.hunts_home) {
                    return true;
                } else if (itemId == R.id.map_home) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                } else if (itemId == R.id.profile_home) {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                }
                return false;
            }
        });

    }

    // Method to fetch hunts from Firestore
    private void fetchHuntsFromFirestore() {
        CollectionReference huntsRef = db.collection("scavengerHunts");

        huntsRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Extract data from each document
                            String huntName = document.getString("huntName");
                            //Long locationsCount = document.getLong("totalLocations");
                            String huntDescription = document.getString("huntDescription");

                            if (huntName != null && huntDescription != null) {
                                ArrayList<LocationObject> huntLocations = new ArrayList<>();

                                // Fetch the locations for this hunt
                                String huntId = document.getId(); // Get hunt document ID
                                fetchLocationsForHunt(huntId, huntLocations, document, huntName, huntDescription);
                            } else {
                                Log.w("Firestore", "Hunt data is missing for document: " + document.getId());
                            }
                        }
                    } else {
                        Log.e("Firestore", "Error getting hunts: ", task.getException());
                    }
                });
    }


    // Method to fetch locations for a specific hunt
    private void fetchLocationsForHunt(String huntId, ArrayList<LocationObject> huntLocations, QueryDocumentSnapshot document, String huntName, String huntDescription) {
        CollectionReference locationsRef = db.collection("scavengerHunts")
                .document(huntId)
                .collection("locations");

        locationsRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot locationDoc : task.getResult()) {
                            // Extract data from each location document
                            if(locationDoc.getString("name").isEmpty()){
                                break;
                            }
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

                            String locationId = locationDoc.getId();

                            // Make sure that required location fields are not null
                            if (locationName != null) {
                                // Create and add the LocationObject to the huntLocations list
                                huntLocations.add(new LocationObject(locationId, locationName, locationDescription, hint1, hint2, hint3, latitude, longitude, image, question, answer));
                            } else {
                                Log.w("Firestore", "Missing required location data for hunt " + huntId);
                            }
                        }

                        if (!huntLocations.isEmpty()) {
                            huntsArrayList.add(new HuntsObject(huntId, huntName, huntDescription, huntLocations.size(), "test", huntLocations));
                            recyclerHome.getAdapter().notifyDataSetChanged();
                        } else {
                            Log.w("Firestore", "No locations found for hunt " + huntId);
                        }
                    } else {
                        Log.e("Firestore", "Error getting locations: ", task.getException());
                    }
                });
    }



    //    @Override
    //    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    //        if (item.getItemId() == android.R.id.home) {
    //            // Navigate back when the back arrow is pressed
    //            finish(); // or use NavUtils.navigateUpFromSameTask(this); if you're doing more complex navigation
    //            return true;
    //        }
    //        return super.onOptionsItemSelected(item);
    //    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // or onBackPressed()
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onHuntsClick(int position) {
        HuntsObject selectedHunt = huntsArrayList.get(position);
        if (selectedHunt == null) {
            Log.e("Hunt Click", "Selected recipe is null at position: " + position);
            return;
        }

        // Get the hunt ID from the selected hunt
        String huntId = selectedHunt.gethuntId(); // Assuming you have a getter for hunt ID in HuntsObject

        Intent intent = new Intent(HuntsActivity.this, HuntsLocationsActivity.class);
        intent.putExtra("selected-hunt", huntId);  // Passing the full hunt object with its locations
        startActivity(intent);

    }
}
