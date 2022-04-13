package com.example.go4lunch.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.go4lunch.R;
import com.example.go4lunch.ShowRestaurantActivity;
import com.example.go4lunch.model.GooglePlaceModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GooglePlaceAdapter extends RecyclerView.Adapter<GooglePlaceAdapter.ViewHolder> {

    @NonNull
    private  List<GooglePlaceModel> googlePlaceModels;
    double currentLat = -33.8670522, currrentLong = 151.1957362;
    int selected_position = 0;

    public GooglePlaceAdapter(@NotNull List<GooglePlaceModel> googlePlaceModels) {
        this.googlePlaceModels = googlePlaceModels;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item_layout,parent,false);
        return new ViewHolder(view);
    }


    // je peux start la nouvelle activité dans l'adapter pour le showactivityRestaurant
    //récupérer l'id non le détail.
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull GooglePlaceAdapter.ViewHolder holder, int position) {
        holder.itemView.setSelected(selected_position == position);
        GooglePlaceModel placeModel = googlePlaceModels.get(position);
            holder.placesName.setText(placeModel.getName());
            holder.street_Name.setText(placeModel.getVicinity());
            holder.rating.setText(placeModel.getRating().toString());
            holder.open.setText(placeModel.getBusinessStatus());
            getRatingStars(holder,position);
            getLocationToPlaces(holder,position);

            try {
             String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=150&photoreference="
                + placeModel.getPhotos().get(0).getPhotoReference() + "&sensor=true&key=AIzaSyDIC9wuMhHNNjFIr6UZfb64h1Rmauaz7hw";

             Glide.with(holder.photoPlaces.getContext())
                .load(url)
                .into(holder.photoPlaces);
            }catch (Exception exception) {
                exception.printStackTrace();
            }
             //clicker sur tout le itemview
            // toutes views a un context.
            // récupérer le place id.
            // la recherche faudra le placeDetails

             holder.itemView.setOnClickListener(view -> {
                 Intent intent = new Intent(view.getContext(),ShowRestaurantActivity.class);
                 intent.putExtra("ID",placeModel.getPlaceId());
                 try {
                     view.getContext().startActivity(intent);
                 }
                 catch (Exception exception){
                     exception.printStackTrace();
                 }
             });
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
    @SuppressLint("SetTextI18n")
    private void getLocationToPlaces(GooglePlaceAdapter.ViewHolder holder, int position){
        GooglePlaceModel placeModel = googlePlaceModels.get(position);
        Location currentLocation= new Location("currentLocation");
        currentLocation.setLatitude(currentLat);
        currentLocation.setLongitude(currrentLong);

        Location newLocation = new Location("newlocation");
        newLocation.setLatitude(placeModel.getGeometry().getLocation().getLat());
        newLocation.setLongitude(placeModel.getGeometry().getLocation().getLng());

        float distance = currentLocation.distanceTo(newLocation);
        int distanceInt= (int) distance;
        String distanceM=String.valueOf(distanceInt);
        //caster en int et mettre "m"
        holder.distancePlaces.setText(distanceM+"m");

    }

    @Override
    public int getItemCount() {
        return googlePlaceModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView placesName;
        private TextView street_Name;
        private TextView rating;
        private TextView open;
        private RatingBar ratingBar;
        private ImageView photoPlaces;
        private TextView distancePlaces;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            placesName=itemView.findViewById(R.id.place_Name);
            street_Name=itemView.findViewById(R.id.street_Name);
            rating=itemView.findViewById(R.id.rating);
            open=itemView.findViewById(R.id.open);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            photoPlaces=itemView.findViewById(R.id.photo_Places);
            distancePlaces=itemView.findViewById(R.id.distance_places);
        }

        @Override
        public void onClick(View view) {
            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
            // Updating old as well as new positions
            notifyItemChanged(selected_position);
            selected_position = getAdapterPosition();
            notifyItemChanged(selected_position);
        }
    }
}
