package com.example.go4lunch.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CoworkerRepository {

    private final List<Users> usersList = new ArrayList<>();
    final MutableLiveData<List<Users>> listUsers= new MutableLiveData<>();
    private FirebaseUser firebaseUser;
    private Users user;


    public MutableLiveData<List<Users>> getListUsers(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://go4lunch-5272f-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference databaseReference = mDatabase.getReference("Users");
        firebaseUser= mAuth.getCurrentUser();
        try {
        usersList.clear();
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        user=ds.getValue(Users.class);
                        usersList.add(user);
                    }
                    listUsers.setValue(usersList);
                }
                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            };
            databaseReference.addListenerForSingleValueEvent(eventListener);
        }catch (Exception exception) {
            exception.printStackTrace();
        }

        return listUsers;
    }
}
