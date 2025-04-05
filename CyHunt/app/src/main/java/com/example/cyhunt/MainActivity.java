//package com.example.cyhunt;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//public class MainActivity extends AppCompatActivity OnMapReadyCallBack {
//    private GoogleMap mMap;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
//        // Set Pantry selected
//        bottomNavigationView.setSelectedItemId(R.id.map_home);
//
//        // Perform item selected listener
//        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int itemId = item.getItemId();
//
//                if (itemId == R.id.hunts_home) {
//                    startActivity(new Intent(getApplicationContext(), HuntsActivity.class));
//                    overridePendingTransition(0,0);
//                    return true;
//                } else if (itemId == R.id.map_home) {
//                    return true;
//                } else if (itemId == R.id.profile_home) {
//                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
//                    overridePendingTransition(0,0);
//                    return true;
//                }
//                return false;
//            }
//        });
//        // Set up WebView to display StoryMap
////        WebView webView = findViewById(R.id.webview);
////        WebSettings webSettings = webView.getSettings();
////        webSettings.setJavaScriptEnabled(true); // Enable JavaScript if needed
////
////        // Load the StoryMap URL
////        webView.loadUrl("https://storymaps.arcgis.com/stories/54d860a8a2284111a379f0a0c893fffc");
////
////        // Make sure links inside the WebView open within the WebView
////        webView.setWebViewClient(new WebViewClient());
//
//    }
//}
package com.example.cyhunt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FirebaseFirestore db;
    private ArrayList<HuntsObject> huntsArrayList;
    RecyclerView recyclerHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);  //
        setContentView(R.layout.activity_main);

        // Initialize map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);

        }

        // Set up bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.map_home);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.hunts_home) {
                    startActivity(new Intent(getApplicationContext(), HuntsActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.map_home) {
                    return true;
                } else if (itemId == R.id.profile_home) {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });
    }
    // Method to fetch hunts from Firestore
//    private void fetchHuntsFromFirestore() {
//        CollectionReference huntsRef = db.collection("scavengerHunts");
//
//        huntsRef.get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            // Extract data from each document
//                            String huntName = document.getString("huntName");
//                            //Long locationsCount = document.getLong("totalLocations");
//                            String huntDescription = document.getString("huntDescription");
//
//                            if (huntName != null && huntDescription != null) {
//                                ArrayList<LocationObject> huntLocations = new ArrayList<>();
//
//                                // Fetch the locations for this hunt
//                                String huntId = document.getId(); // Get hunt document ID
//                                fetchLocationsForHunt(huntId, huntLocations, document, huntName, huntDescription);
//                            } else {
//                                Log.w("Firestore", "Hunt data is missing for document: " + document.getId());
//                            }
//                        }
//                    } else {
//                        Log.e("Firestore", "Error getting hunts: ", task.getException());
//                    }
//                });
//    }
//    private void fetchStatuesForMap() {
//        SharedPreferences prefs = getSharedPreferences("completedLocations", MODE_PRIVATE);
//
//        // Get all the completed locations from SharedPreferences
//        Map<String, ?> allCompletedLocations = prefs.getAll();  // This fetches all key-value pairs
//
//        for (Map.Entry<String, ?> entry : allCompletedLocations.entrySet()) {
//            String locationId = entry.getKey();
//            boolean isCompleted = (boolean) entry.getValue();
//
//            if (isCompleted) {
//                // Now, fetch the actual location details from Firestore using locationId
//                fetchLocationFromFirestore(locationId);
//            }
//        }
//    }

//    private void fetchLocationFromFirestore(String locationId) {
//        // Fetch location details from Firestore
//        db.collection("scavengerHunts")
//                .document(huntId)  // Make sure you have the correct huntId
//                .collection("locations")
//                .document(locationId)
//                .get()
//                .addOnSuccessListener(documentSnapshot -> {
//                    if (documentSnapshot.exists()) {
//                        Double latitude = documentSnapshot.getDouble("latitude");
//                        Double longitude = documentSnapshot.getDouble("longitude");
//                        String name = documentSnapshot.getString("name");
//                        String description = documentSnapshot.getString("description");
//
//                        if (latitude != null && longitude != null) {
//                            // Add the marker on the map
//                            LatLng location = new LatLng(latitude, longitude);
//                            mMap.addMarker(new MarkerOptions()
//                                    .position(location)
//                                    .title(name)
//                                    .snippet(description));
//                        }
//                    }
//                })
//                .addOnFailureListener(e -> Log.e("Firestore", "Error fetching location details", e));
//    }
//    // Method to fetch locations for a specific hunt
//    private void fetchLocationsForHunt(String huntId, ArrayList<LocationObject> huntLocations, QueryDocumentSnapshot document, String huntName, String huntDescription) {
//        CollectionReference locationsRef = db.collection("scavengerHunts")
//                .document(huntId)
//                .collection("locations");
//
//        locationsRef.get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        for (QueryDocumentSnapshot locationDoc : task.getResult()) {
//                            // Extract data from each location document
//                            if(locationDoc.getString("name").isEmpty()){
//                                break;
//                            }
//                            String locationName = locationDoc.getString("name");
//                            String locationDescription = locationDoc.getString("description");
//                            double latitude = locationDoc.getDouble("latitude");
//                            double longitude = locationDoc.getDouble("longitude");
//                            String hint1 = locationDoc.getString("hint1");
//                            String hint2 = locationDoc.getString("hint2");
//                            String hint3 = locationDoc.getString("hint3");
//                            String image = locationDoc.getString("imageUrl");
//                            String question = locationDoc.getString("quizQuestion");
//                            String answer = locationDoc.getString("quizAnswer");
//
//                            String locationId = locationDoc.getId();
//
//                            // Make sure that required location fields are not null
//                            if (locationName != null) {
//                                // Create and add the LocationObject to the huntLocations list
//                                huntLocations.add(new LocationObject(locationId, locationName, locationDescription, hint1, hint2, hint3, latitude, longitude, image, question, answer));
//                            } else {
//                                Log.w("Firestore", "Missing required location data for hunt " + huntId);
//                            }
//                        }
//
//                        if (!huntLocations.isEmpty()) {
//                            huntsArrayList.add(new HuntsObject(huntId, huntName, huntDescription, huntLocations.size(), "test", huntLocations));
//                            recyclerHome.getAdapter().notifyDataSetChanged();
//                        } else {
//                            Log.w("Firestore", "No locations found for hunt " + huntId);
//                        }
//                    } else {
//                        Log.e("Firestore", "Error getting locations: ", task.getException());
//                    }
//                });
//    }
//
//    private void fetchStatuesForMap() {
//        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//        // Fetch hunts for the user
//        db.collection("users")
//                .document(userId)
//                .collection("hunts")
//                .get()
//                .addOnCompleteListener(huntTask -> {
//                    if (huntTask.isSuccessful()) {
//                        for (QueryDocumentSnapshot huntDoc : huntTask.getResult()) {
//                            String huntId = huntDoc.getId();
//
//                            // Fetch completed locations for this hunt
//                            db.collection("users")
//                                    .document(userId)
//                                    .collection("hunts")
//                                    .document(huntId)
//                                    .collection("completedLocations")
//                                    .get()
//                                    .addOnCompleteListener(locationTask -> {
//                                        if (locationTask.isSuccessful()) {
//                                            for (QueryDocumentSnapshot locDoc : locationTask.getResult()) {
//                                                boolean isCompleted = locDoc.getBoolean("completed") != null &&
//                                                        locDoc.getBoolean("completed");
//
//                                                if (isCompleted) {
//                                                    String locationId = locDoc.getId();
//
//                                                    // Fetch location info from the original hunt collection
//                                                    db.collection("scavengerHunts")
//                                                            .document(huntId)
//                                                            .collection("locations")
//                                                            .document(locationId)
//                                                            .get()
//                                                            .addOnSuccessListener(locationDoc -> {
//                                                                if (locationDoc.exists()) {
//                                                                    String name = locationDoc.getString("name");
//                                                                    String description = locationDoc.getString("description");
//                                                                    Double lat = locationDoc.getDouble("latitude");
//                                                                    Double lon = locationDoc.getDouble("longitude");
//
//                                                                    if (lat != null && lon != null && name != null) {
//                                                                        // Add a marker for completed locations
//                                                                        LatLng location = new LatLng(lat, lon);
//                                                                        mMap.addMarker(new MarkerOptions()
//                                                                                .position(location)
//                                                                                .title(name)
//                                                                                .snippet(description));
//                                                                    }
//                                                                }
//                                                            });
//                                                }
//                                            }
//                                        }
//                                    });
//                        }
//                    }
//                });
//    }

//    private void fetchStatuesForMap() {
//        SharedPreferences prefs = getSharedPreferences("completed_locations", MODE_PRIVATE);
//
//        db.collection("scavengerHunts")
//                .get()
//                .addOnCompleteListener(huntTask -> {
//                    if (huntTask.isSuccessful()) {
//                        for (QueryDocumentSnapshot huntDoc : huntTask.getResult()) {
//                            String huntId = huntDoc.getId();
//
//                            db.collection("scavengerHunts")
//                                    .document(huntId)
//                                    .collection("locations")
//                                    .get()
//                                    .addOnCompleteListener(locationTask -> {
//                                        if (locationTask.isSuccessful()) {
//                                            for (QueryDocumentSnapshot locationDoc : locationTask.getResult()) {
//                                                String locationId = locationDoc.getId();
//
//                                                boolean isCompleted = prefs.getBoolean(locationId, false);
//
//                                                if (isCompleted) {
//                                                    // Get data and add pin
//                                                    String name = locationDoc.getString("name");
//                                                    String description = locationDoc.getString("description");
//                                                    Double lat = locationDoc.getDouble("latitude");
//                                                    Double lon = locationDoc.getDouble("longitude");
//
//                                                    if (lat != null && lon != null && name != null) {
//                                                        LatLng location = new LatLng(lat, lon);
//                                                        mMap.addMarker(new MarkerOptions()
//                                                                .position(location)
//                                                                .title(name)
//                                                                .snippet(description));
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    });
//                        }
//                    }
//                });
//    }

    private void fetchLocationFromFirestoreAndAddPin(String huntId, String locationId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("scavengerHunts")
                .document(huntId)
                .collection("locations")
                .document(locationId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Extract the location data from the document
                        String locationName = documentSnapshot.getString("name");
                        String description = documentSnapshot.getString("description");
                        Double latitude = documentSnapshot.getDouble("latitude");
                        Double longitude = documentSnapshot.getDouble("longitude");

                        // Add the marker if latitude and longitude are available
                        if (latitude != null && longitude != null) {
                            LatLng location = new LatLng(latitude, longitude);
                            mMap.addMarker(new MarkerOptions()
                                    .position(location)
                                    .title(locationName)

                                    .snippet(description));  // Title and snippet can be set as location name and description
                        }
                    } else {
                        Log.w("MainActivity", "Location not found in Firestore.");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("MainActivity", "Error fetching location: ", e);
                });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
//       db = FirebaseFirestore.getInstance();
//    fetchStatuesForMap();
        mMap = googleMap;
        SharedPreferences prefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
        String locationId = prefs.getString("locationId", null);  // Retrieve locationId
        String huntId = prefs.getString("huntId", null);  // Retrieve huntId

        if (locationId != null && huntId != null) {
            // Use these values to get the location data from Firestore
            fetchLocationFromFirestoreAndAddPin(huntId, locationId);
        }

        // Get the list of statues
       // fetchStatuesForMap();

        // Zoom into the campus or central poaint
        LatLng campusCenter = new LatLng(42.0265, -93.6465);  // You can set this to the center of your campus
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(campusCenter, 15));  // Adjust zoom level as needed
    }
}