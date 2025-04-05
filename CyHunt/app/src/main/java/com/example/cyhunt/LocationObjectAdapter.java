package com.example.cyhunt;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class LocationObjectAdapter extends RecyclerView.Adapter<LocationObjectAdapter.LocationView> {

    private ArrayList<LocationObject> locationDataArrayList;
    private OnLocationListener mOnLocationListener;
    private Context mcontext;

    public LocationObjectAdapter(ArrayList<LocationObject> locationDataArrayList, Context mcontext, OnLocationListener mOnLocationListener){
        this.locationDataArrayList = locationDataArrayList;
        this.mOnLocationListener = mOnLocationListener;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public LocationView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_row_object, parent, false);
        return new LocationView(view, mOnLocationListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationView holder, int position) {
        LocationObject locationData = locationDataArrayList.get(position);
        holder.locationName.setText(locationData.getName());
    }

    @Override
    public int getItemCount() {
        return locationDataArrayList.size();
    }

    public class LocationView extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView locationName;
        OnLocationListener onLocationListener;

        public LocationView(@NonNull View itemView, OnLocationListener onLocationListener){
            super(itemView);
            locationName = itemView.findViewById(R.id.location_name_txt);
            this.onLocationListener = onLocationListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onLocationListener.onLocationClick(getAdapterPosition());
        }
    }

    public interface OnLocationListener{
        void onLocationClick(int position);
    }

}
