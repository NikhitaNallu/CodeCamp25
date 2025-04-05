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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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

    private void fetchStatuesForMap() {
        db.collection("scavengerHunts")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot huntDoc : task.getResult()) {
                            String huntId = huntDoc.getId();

                            db.collection("scavengerHunts")
                                    .document(huntId)
                                    .collection("locations")
                                    .get()
                                    .addOnCompleteListener(locationTask -> {
                                        if (locationTask.isSuccessful()) {
                                            for (QueryDocumentSnapshot locationDoc : locationTask.getResult()) {
                                                String name = locationDoc.getString("name");
                                                String description = locationDoc.getString("description");
                                                Double lat = locationDoc.getDouble("latitude");
                                                Double lon = locationDoc.getDouble("longitude");
                                                String hint1 = locationDoc.getString("hint1");
                                                String hint2 = locationDoc.getString("hint2");
                                                String hint3 = locationDoc.getString("hint3");
                                                String image = locationDoc.getString("imageUrl");
                                                String question = locationDoc.getString("quizQuestion");
                                                String answer = locationDoc.getString("quizAnswer");
                                                String id = locationDoc.getId();

                                                // Optional: Replace this with however you check quiz status
                                                boolean isCompleted = locationDoc.getBoolean("quizCompleted") != null && locationDoc.getBoolean("quizCompleted");

                                                if (lat != null && lon != null && name != null && isCompleted) {
                                                    LatLng location = new LatLng(lat, lon);
                                                    mMap.addMarker(new MarkerOptions()
                                                            .position(location)
                                                            .title(name)
                                                            .snippet(description));
                                                }
                                            }
                                        }
                                    });
                        }
                    } else {
                        Log.e("Firestore", "Failed to fetch hunts", task.getException());
                    }
                });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
       db = FirebaseFirestore.getInstance();
    fetchStatuesForMap();
        mMap = googleMap;

        // Get the list of statues
        fetchStatuesForMap();

        // Zoom into the campus or central point
        LatLng campusCenter = new LatLng(42.0265, -93.6465);  // You can set this to the center of your campus
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(campusCenter, 15));  // Adjust zoom level as needed
    }
}