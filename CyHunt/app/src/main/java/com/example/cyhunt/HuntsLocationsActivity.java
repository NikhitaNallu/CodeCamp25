package com.example.cyhunt;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
    private HuntsObject hunt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hunts_locations);


        recyclerHome = findViewById(R.id.recycler_home);




        locationArrayList = new ArrayList<>();


        if(getIntent().hasExtra("selected-hunt")){
            hunt = getIntent().getParcelableExtra("selected-hunt");
            locationArrayList = hunt.getLocations();

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
            Log.e("PlannerClick", "Selected recipe is null at position: " + position);
            return;
        }

        Intent intent = new Intent(this, HuntsLocationsActivity.class);
        intent.putExtra("selected_recipe", selectedLocation);
        startActivity(intent);
    }
}