package com.example.go4lunch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    SignInButton btnSign;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    Button facebook_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSign = findViewById(R.id.btnsign);
        facebook_button= findViewById(R.id.facebook_button);
        googleSignIn();
        mAuth = FirebaseAuth.getInstance();


        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    // Le resultat est un résultCanceled
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent data = result.getData();
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        try {
                            // Google Sign In was successful, authenticate with Firebase
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            assert account != null;
                            firebaseAuthWithGoogle(account.getIdToken());

                        } catch (ApiException e) {
                            // Google Sign In failed, update UI appropriately
                            Toast.makeText(MainActivity.this,"Authentification Failed"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }




                    }
                });

            btnSign.setOnClickListener(v-> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            someActivityResultLauncher.launch(signInIntent);
        });
            //Peut etre pas le bon boutton
            facebook_button.setOnClickListener(v->{
               Intent intent = new Intent(MainActivity.this,FacebookAuthActivity.class);
               startActivity(intent);

            });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            startActivity(new Intent(getApplicationContext(),MapsActivity.class));
        }
    }


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                        startActivity(intent);

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(MainActivity.this,"Authentification Failed",Toast.LENGTH_LONG).show();
                    }
                });
    }



    private void googleSignIn(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




}