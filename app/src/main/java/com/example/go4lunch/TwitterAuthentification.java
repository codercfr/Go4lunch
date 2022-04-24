package com.example.go4lunch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

public class TwitterAuthentification extends MainActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();


        OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");

        provider.addCustomParameter("lang", "fr");

        Task<AuthResult> pendingResultTask = mAuth.getPendingAuthResult();
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                    .addOnSuccessListener(
                            authResult -> startActivity(new Intent(TwitterAuthentification.this, MapsActivity.class)))
                    .addOnFailureListener(
                            e -> {
                                Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_LONG).show();
                            });
        } else {
            mAuth.startActivityForSignInWithProvider(/* activity= */ this, provider.build())
                    .addOnSuccessListener(
                            authResult -> startActivity(new Intent(TwitterAuthentification.this, MapsActivity.class)))
                    .addOnFailureListener(
                            e -> {
                                // Handle failure.
                                Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_LONG).show();
                            });
        }
    }
}
