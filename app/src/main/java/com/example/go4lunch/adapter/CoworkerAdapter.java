package com.example.go4lunch.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.go4lunch.R;
import com.example.go4lunch.ShowRestaurantActivity;
import com.example.go4lunch.model.Users;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CoworkerAdapter extends RecyclerView.Adapter<CoworkerAdapter.ViewHolder> {


    private List<Users> coworkerList;

    //demandé si autre façon d'ajouter du context.
    Context context;

    public CoworkerAdapter(List<Users> usersList,Context context){
        this.coworkerList=usersList;
        this.context=context;

    }
    @NonNull
    @NotNull
    @Override
    public CoworkerAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coworker_fragment_layout,parent,false);
        return new ViewHolder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull CoworkerAdapter.ViewHolder holder, int position) {
        Users user = coworkerList.get(position);

        if (user.getRestaurantName() == null) {
            if (user.getUsername() == null) {
                holder.firebaseUserName.setText(user.getEmail() + ""+ context.getString(R.string.coworkerChoice));
            } else {
                holder.firebaseUserName.setText(user.getUsername() + context.getString(R.string.coworkerChoice));
            }
        } else {
            if (user.getUsername() == null) {
                holder.firebaseUserName.setText(user.getEmail() + " "+context.getString(R.string.coworker_restaurant)+" " + user.getRestaurantName());
            }
            holder.firebaseUserName.setText(user.getUsername() + " "+context.getString(R.string.coworker_restaurant)+" " + user.getRestaurantName());
        }
        //rajouter la photo quand sa fonctionne.
        try {
            Glide.with(holder.firebaseUserPhoto.getContext())
                    .load(user.getPhotoUser())
                    .into(holder.firebaseUserPhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user.getRestaurantName() == null) {
            holder.itemView.setClickable(false);
        } else {
            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), ShowRestaurantActivity.class);
                intent.putExtra("ID", user.getPlaceId());
                try {
                    view.getContext().startActivity(intent);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return coworkerList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView firebaseUserName;
        private ImageView firebaseUserPhoto;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
           firebaseUserName=itemView.findViewById(R.id.name_firabase_user);
           firebaseUserPhoto=itemView.findViewById(R.id.img_firebase_user);

        }

    }

}
