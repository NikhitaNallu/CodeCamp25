package com.example.cyhunt;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class LocationObject implements Parcelable {
    private String locationName;
    private String locationDescription;
    private String locationHint1;
    private String locationHint2;
    private String locationHint3;
    private double locationLat;
    private double locationLong;

    private String imageUrl;


    //To-add later
    /*
    private Date expiration;
    private ImageView ingredientPicture;
    */

    public LocationObject(String name, String description, String hint1, String hint2, String hint3, double lat, double longi, String imageUrl) {
        this.locationName = name;
        this.locationDescription = description;
        this.locationHint1 = hint1;
        this.locationHint2 = hint2;
        this.locationHint3 = hint3;
        this.locationLat = lat;
        this.locationLong = longi;
        this.imageUrl = imageUrl;

    }


    protected LocationObject(Parcel in) {
        locationName = in.readString();
        locationDescription = in.readString();
        locationHint1 = in.readString();
        locationHint2 = in.readString();
        locationHint3 = in.readString();
        locationLat = in.readDouble();
        locationLong = in.readDouble();
        imageUrl = in.readString();
    }

    public static final Creator<LocationObject> CREATOR = new Creator<LocationObject>() {
        @Override
        public LocationObject createFromParcel(Parcel in) {
            return new LocationObject(in);
        }

        @Override
        public LocationObject[] newArray(int size) {
            return new LocationObject[size];
        }
    };

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return locationName;
    }

    public String getDescription() {
        return locationDescription;
    }

    public String getHint1() {
        return locationHint1;
    }

    public String getHint2() {
        return locationHint2;
    }

    public String getHint3() {
        return locationHint3;
    }

    public double getLocationLat() {
        return locationLat;
    }

    public double getLocationLong() {
        return locationLong;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(locationName);
        dest.writeString(locationDescription);
        dest.writeString(locationHint1);
        dest.writeString(locationHint2);
        dest.writeString(locationHint3);
        dest.writeDouble(locationLat);
        dest.writeDouble(locationLong);
        dest.writeString(imageUrl);
    }
}
