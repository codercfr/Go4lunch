package com.example.go4lunch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.go4lunch.model.Users;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class TwitterAuthentification extends MainActivity {

    private Users user;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://go4lunch-5272f-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference= mDatabase.getReference("Users");
        user=new Users();

        // regarder si c'est possible de passer en V2
        // tester d'activer l'élevated dans le twitter dev account -> Products en haut ELEVATED

        OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");

        provider.addCustomParameter("lang", "fr");

        Task<AuthResult> pendingResultTask = mAuth.getPendingAuthResult();
        if (pendingResultTask != null) {
            FirebaseUser userFirebase= mAuth.getCurrentUser();

            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                    .addOnSuccessListener(
                            authResult -> {
                                user.setName(Objects.requireNonNull(authResult.getAdditionalUserInfo()).getUsername());
                                user.setUsername(Objects.requireNonNull(authResult.getAdditionalUserInfo()).getUsername());
                                databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                        .setValue(user);
                                startActivity(new Intent(TwitterAuthentification.this, MapsActivity.class));
                            }
                    )
                    .addOnFailureListener(
                            e -> Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_LONG).show()
                            );
        } else {

            mAuth.startActivityForSignInWithProvider( this, provider.build())
                    .addOnSuccessListener(
                            authResult -> {
                                user.setName(Objects.requireNonNull(authResult.getAdditionalUserInfo()).getUsername());
                                user.setUsername(Objects.requireNonNull(authResult.getAdditionalUserInfo()).getUsername());
                                databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                        .setValue(user);
                                startActivity(new Intent(TwitterAuthentification.this, MapsActivity.class));
                            })
                    .addOnFailureListener(
                            e -> {
                                // Handle failure.
                                Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_LONG).show();
                            });
        }
    }
}
