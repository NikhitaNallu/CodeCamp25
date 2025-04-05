package com.example.cyhunt;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class HuntsObject implements Parcelable {
    private String name;
    private String description;
    private String huntId;
    private int numLocations;

    private ArrayList<LocationObject> locations;
    private String imageUrl;

    public HuntsObject(String huntId, String name, String description, int numLocations, String imageUrl, ArrayList<LocationObject> locations) {
        this.huntId = huntId;
        this.name = name;
        this.description = description;
        this.numLocations = numLocations;
        this.imageUrl = imageUrl;
        this.locations = locations;
    }


    protected HuntsObject(Parcel in) {
        huntId = in.readString();
        name = in.readString();
        description = in.readString();
        numLocations = in.readInt();
        imageUrl = in.readString();
        locations = in.createTypedArrayList(LocationObject.CREATOR);
    }

    public static final Creator<HuntsObject> CREATOR = new Creator<HuntsObject>() {
        @Override
        public HuntsObject createFromParcel(Parcel in) {
            return new HuntsObject(in);
        }

        @Override
        public HuntsObject[] newArray(int size) {
            return new HuntsObject[size];
        }
    };

    public String gethuntId() {
        return huntId;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getNumLocations() {
        return numLocations;
    }

    public ArrayList<LocationObject> getLocations() {
        return locations;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(huntId);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(numLocations);
        dest.writeString(imageUrl);
        dest.writeTypedList(locations);
    }
}
