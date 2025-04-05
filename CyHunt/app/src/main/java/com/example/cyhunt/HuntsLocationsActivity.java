package com.example.cyhunt;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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