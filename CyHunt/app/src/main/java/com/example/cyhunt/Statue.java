package com.example.cyhunt;

public class Statue {
    private String name;
    private String description;
    private double latitude;
    private double longitude;
    private boolean isQuizCompleted; // Tracks if the user completed the quiz

    public Statue(String name, String description, double latitude, double longitude) {
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isQuizCompleted = false; // Initially not completed
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean isQuizCompleted() {
        return isQuizCompleted;
    }

    public void setQuizCompleted(boolean quizCompleted) {
        isQuizCompleted = quizCompleted;
    }
}
