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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Example: Set default camera to a college campus location
//        LatLng campusCenter = new LatLng(42.0265, -93.6465); // Replace with your campus
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(campusCenter, 16));
//
//        // Example marker
//        LatLng statueLocation = new LatLng(42.027, -93.648);
//
//        mMap.addMarker(new MarkerOptions().position(statueLocation).title("Sample Statue"));
//        mMap = googleMap;
//
//        Log.d("MapReady", "Google Map is ready!");
//
//        // Set default camera position
//        LatLng campusCenter = new LatLng(42.0265, -93.6465);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(campusCenter, 16));
//
//        // Add a marker and log the action
//        LatLng statueLocation = new LatLng(42.027, -93.648);
//        mMap.addMarker(new MarkerOptions().position(statueLocation).title("Sample Statue"));
//        Log.d("Marker", "Added marker at: " + statueLocation.toString());
        mMap = googleMap;

        // Get the list of statues
        List<LocationObject> statues = StatueRepository.getAllStatues(this);

        // Loop through statues and add pins to the map only if the quiz is completed
        for (LocationObject statue : statues) {
            if (statue.isQuizCompleted()) {
                // Add a marker for each statue that has passed the quiz
                LatLng statueLocation = new LatLng(statue.getLocationLat(), statue.getLocationLong());
                mMap.addMarker(new MarkerOptions()
                        .position(statueLocation)
                        .title(statue.getName())
                        .snippet(statue.getDescription())); // Optionally show the description in the snippet
            }
        }

        // Zoom into the campus or central point
        LatLng campusCenter = new LatLng(42.0265, -93.6465);  // You can set this to the center of your campus
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(campusCenter, 15));  // Adjust zoom level as needed
    }
}