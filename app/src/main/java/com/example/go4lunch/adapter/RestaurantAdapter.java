package com.example.go4lunch.adapter;

import android.annotation.SuppressLint;
import android.net.Uri;
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
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private List<Users> usersListRestaurant = new ArrayList<>();

    @NonNull
    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //chager la vue crée un layout que pour le RecyclerView et donc crée un frag.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choosen_restaurant_layout,parent,false);
        return new ViewHolder(view);
    }

    public RestaurantAdapter(List<Users> usersList){
        this.usersListRestaurant= usersList;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull RestaurantAdapter.ViewHolder holder, int position) {
        Users user=usersListRestaurant.get(position) ;
        holder.nameUser.setText(user.getUsername()+" is joingning!");
        // rajouter la photo quand ca fonctionne

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


    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView photoUser;
        private TextView nameUser;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            photoUser=itemView.findViewById(R.id.img_restaurant_user);
            nameUser=itemView.findViewById(R.id.name_User);
        }
    }
}
