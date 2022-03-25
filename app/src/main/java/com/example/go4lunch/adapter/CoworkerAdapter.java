package com.example.go4lunch.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;
import com.example.go4lunch.model.Users;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CoworkerAdapter extends RecyclerView.Adapter<CoworkerAdapter.ViewHolder> {


    private List<Users> coworkerList;

    public CoworkerAdapter(List<Users> usersList){
        this.coworkerList=usersList;
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
        holder.firebaseUserName.setText(user.getName()+"a choisit ce restaurant");
        //rajouter la photo quand sa fonctionne.
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
