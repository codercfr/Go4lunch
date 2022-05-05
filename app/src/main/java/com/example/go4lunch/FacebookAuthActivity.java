package com.example.go4lunch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class FacebookAuthActivity extends MainActivity {


    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();


        //LoginManager.getInstance().setPermissions("email","public_profile");
        //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public profile"));
        //LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
        facebook_button.setPermissions("email","public_profile");
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                handleFacebookAccessToken(loginResult.getAccessToken());
                Intent intent = new Intent(FacebookAuthActivity.this, MapsActivity.class);
                startActivity(intent);

            }

            @Override
            public void onCancel() {

            }


            @Override
            public void onError(@NotNull FacebookException error) {

                error=error;
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(FacebookAuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
}

    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent(FacebookAuthActivity.this, MapsActivity.class);
        startActivity(intent);

    }
}