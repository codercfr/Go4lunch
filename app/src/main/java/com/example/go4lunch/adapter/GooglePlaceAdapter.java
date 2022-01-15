package com.example.go4lunch.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;
import com.example.go4lunch.model.GooglePlaceModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GooglePlaceAdapter extends RecyclerView.Adapter<GooglePlaceAdapter.ViewHolder> {

    @NonNull
    private  List<GooglePlaceModel> googlePlaceModels;


    public GooglePlaceAdapter(@NotNull List<GooglePlaceModel> googlePlaceModels) {
        this.googlePlaceModels = googlePlaceModels;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull GooglePlaceAdapter.ViewHolder holder, int position) {
            GooglePlaceModel placeModel = googlePlaceModels.get(position);
            holder.placesName.setText(placeModel.getName());
            holder.street_Name.setText(placeModel.getVicinity());
            holder.rating.setText(placeModel.getRating().toString());
            holder.open.setText(placeModel.getBusinessStatus());
            getRatingStars(holder,position);

    }

   public void updateTasks(@NonNull  List<GooglePlaceModel> places) {
        this.googlePlaceModels = places;
        notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    private void getRatingStars(GooglePlaceAdapter.ViewHolder holder, int position){
        GooglePlaceModel placeModel = googlePlaceModels.get(position);
        double rating=placeModel.getRating();
        int i = (int)rating;
        switch (i){
            case 1 :i=1;
            holder.ratingBar.setNumStars(1);
            break;

            case 2:i=2;
            holder.ratingBar.setNumStars(2);
            break;

            case 3:i=3;
            holder.ratingBar.setNumStars(3);
            break;

            case 4:i=4;
            holder.ratingBar.setNumStars(4);

            case 5:i=5;
            holder.ratingBar.setNumStars(5);
        }
    }

    @Override
    public int getItemCount() {
        return googlePlaceModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private  TextView placesName;
        private TextView street_Name;
        //rating bar widget pour afficher les étoiles
        private TextView rating;
        private TextView open;
        private RatingBar ratingBar;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            placesName=itemView.findViewById(R.id.place_Name);
            street_Name=itemView.findViewById(R.id.street_Name);
            rating=itemView.findViewById(R.id.rating);
            open=itemView.findViewById(R.id.open);
            ratingBar=itemView.findViewById(R.id.ratingBar);
        }
    }
}
