package com.example.cyhunt;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class StatueRepository {

    public static void saveStatueCompletion(Context context, LocationObject statue) {
        SharedPreferences prefs = context.getSharedPreferences("statue_completions", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(statue.getName(), statue.isQuizCompleted()); // Save completion state by statue name
        editor.apply();
    }

    public static List<LocationObject> getAllStatues(Context context) {
        // This list would be populated with actual data. You can fetch it from a database, API, or hardcoded values.
        List<LocationObject> locations = new ArrayList<>();

        // Example: Adding some hardcoded locations
        locations.add(new LocationObject(
                "Statue of Liberty",
                "A symbol of freedom",
                "Hint 1: It's in New York",
                "Hint 2: It's an iconic statue",
                "Hint 3: It was a gift from France",
                40.6892, -74.0445,
                "https://example.com/statue_image.jpg"
        ));

        locations.add(new LocationObject(
                "Eiffel Tower",
                "Famous iron tower in Paris",
                "Hint 1: Located in France",
                "Hint 2: It’s made of iron",
                "Hint 3: It’s in the capital of France",
                48.8584, 2.2945,
                "https://example.com/eiffel_image.jpg"
        ));

        // Add more locations as needed

        return locations;
    }
}
