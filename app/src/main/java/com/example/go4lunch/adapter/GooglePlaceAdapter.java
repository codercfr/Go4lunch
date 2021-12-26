package com.example.go4lunch.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;
import com.example.go4lunch.databinding.PlaceItemLayoutBinding;
import com.example.go4lunch.model.GooglePlaceModel;
import com.example.go4lunch.ui.NearLocationInterface;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GooglePlaceAdapter extends RecyclerView.Adapter<GooglePlaceAdapter.ViewHolder> {

    private List<GooglePlaceModel> googlePlaceModels;
    private NearLocationInterface nearLocationInterface;

    public GooglePlaceAdapter(NearLocationInterface nearLocationInterface) {
        this.nearLocationInterface = nearLocationInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PlaceItemLayoutBinding binding = PlaceItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext())
        );
        return new GooglePlaceAdapter.ViewHolder(binding.getRoot());

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GooglePlaceAdapter.ViewHolder holder, int position) {

        if (googlePlaceModels != null) {
            GooglePlaceModel placeModel = googlePlaceModels.get(position);
            //holder.binding.setGooglePlaceModel(placeModel);
            //holder.binding.setListener(nearLocationInterface);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
