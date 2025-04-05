package com.example.cyhunt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HuntsActivity extends AppCompatActivity implements HuntsObjectAdapter.OnHuntsListener{

    RecyclerView recyclerHome;
    private ArrayList<HuntsObject> huntsArrayList;
    public ArrayList<LocationObject> huntLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hunts);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        // Set Pantry selected
        bottomNavigationView.setSelectedItemId(R.id.hunts_home);


        recyclerHome = findViewById(R.id.recycler_home);

        huntLocations = new ArrayList<>();
        huntLocations.add(new LocationObject("test", "test", "test", "test", "test", 40.748817, -73.985428, "test"));
        huntLocations.add(new LocationObject("test", "test", "test", "test", "test", 40.748817, -73.985428, "test"));
        huntLocations.add(new LocationObject("test", "test", "test", "test", "test", 40.748817, -73.985428, "test"));


        //Create new array list
        huntsArrayList = new ArrayList<>();
        huntsArrayList.add(new HuntsObject("text", "test", 2, "test", huntLocations));

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

// Enable the Up button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
          
        }

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
            Log.e("PlannerClick", "Selected recipe is null at position: " + position);
            return;
        }

        Intent intent = new Intent(this, HuntsLocationsActivity.class);
        intent.putExtra("selected-hunt", selectedHunt);
        startActivity(intent);
    }
}