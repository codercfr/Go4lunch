package com.example.go4lunch.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.go4lunch.R;
import com.example.go4lunch.model.Users;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private final List<Users> usersListRestaurant;
    Context context;


    public RestaurantAdapter(List<Users> usersList, Context context) {
        this.usersListRestaurant=usersList;
        this.context=context;
    }

    @NonNull
    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //chager la vue crée un layout que pour le RecyclerView et donc crée un frag.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choosen_restaurant_layout,parent,false);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull RestaurantAdapter.ViewHolder holder, int position) {
        Users user=usersListRestaurant.get(position) ;
        if(user.getUsername()==null){
            holder.nameUser.setText(user.getEmail()+" "+context.getString(R.string.joining));
        }else {
            holder.nameUser.setText(user.getUsername() + " " + context.getString(R.string.joining));
        }
        try {
            Glide.with(holder.photoUser.getContext())
                    .load(user.getPhotoUser())
                    .into(holder.photoUser);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return usersListRestaurant.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView photoUser;
        private final TextView nameUser;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            photoUser=itemView.findViewById(R.id.img_restaurant_user);
            nameUser=itemView.findViewById(R.id.name_User);
        }
    }
}
