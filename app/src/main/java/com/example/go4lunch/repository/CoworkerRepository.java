package com.example.go4lunch.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.model.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CoworkerRepository {

    private List<Users> usersList = new ArrayList<>();
    final MutableLiveData<List<Users>> listUsers= new MutableLiveData<>();
    private FirebaseDatabase mDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;
    private Users user;


    public MutableLiveData<List<Users>> getListUsers(){
        mAuth = FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance("https://go4lunch-5272f-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference= mDatabase.getReference("Users");
        firebaseUser=mAuth.getCurrentUser();
        try {
        usersList.clear();
        //try to get all Users into a List of User
            //possible de ne pas faire la boucle avec la bonne requête.
                //databaseReference.child("Users").get().addOnSuccessListener(dataSnapshot -> usersList= (List<Users>) dataSnapshot.getValue(Users.class));
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
