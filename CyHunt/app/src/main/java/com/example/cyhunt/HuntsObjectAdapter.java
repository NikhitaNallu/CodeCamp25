package com.example.cyhunt;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class HuntsObjectAdapter extends RecyclerView.Adapter<HuntsObjectAdapter.HuntsView> {

    private ArrayList<HuntsObject> huntDataArrayList;
    private OnHuntsListener mOnHuntsListener;
    private Context mcontext;

    public HuntsObjectAdapter(ArrayList<HuntsObject> huntDataArrayList, Context mcontext, OnHuntsListener mOnHuntsListener){
        this.huntDataArrayList = huntDataArrayList;
        this.mOnHuntsListener = mOnHuntsListener;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public HuntsView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hunts_row_hunt_object, parent, false);
        return new HuntsView(view, mOnHuntsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HuntsView holder, int position) {
        HuntsObject huntData = huntDataArrayList.get(position);
        holder.huntName.setText(huntData.getName());
        holder.huntDescription.setText(String.valueOf(huntData.getDescription()));
    }

    @Override
    public int getItemCount() {
        return huntDataArrayList.size();
    }

    public class HuntsView extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView huntName;
        private TextView huntDescription;
        OnHuntsListener onHuntsListener;

        public HuntsView(@NonNull View itemView, OnHuntsListener onHuntsListener){
            super(itemView);
            huntName = itemView.findViewById(R.id.hunt_name_txt);
            huntDescription = itemView.findViewById(R.id.hunt_description_txt);
            this.onHuntsListener = onHuntsListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onHuntsListener.onHuntsClick(getAdapterPosition());
        }
    }

    public interface OnHuntsListener{
        void onHuntsClick(int position);
    }

}
