package com.example.go4lunch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;
import com.example.go4lunch.model.GooglePlaceModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GooglePlaceAdapter extends RecyclerView.Adapter<GooglePlaceAdapter.ViewHolder> {

    private List<GooglePlaceModel> googlePlaceModels;


    public GooglePlaceAdapter( List<GooglePlaceModel> googlePlaceModels) {
        this.googlePlaceModels = googlePlaceModels;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GooglePlaceAdapter.ViewHolder holder, int position) {

            GooglePlaceModel placeModel = googlePlaceModels.get(position);
            holder.placesName.setText(placeModel.getName());

    }

    @Override
    public int getItemCount() {
        if (googlePlaceModels != null)
            return googlePlaceModels.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView placesName;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            placesName=itemView.findViewById(R.id.place_Name);
        }
    }
}
