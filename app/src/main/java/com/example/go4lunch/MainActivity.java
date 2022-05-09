package com.example.go4lunch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.go4lunch.model.Users;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.firebase.ui.auth.AuthUI.TAG;

public class MainActivity extends AppCompatActivity {

    SignInButton btnSign;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    LoginButton facebook_button;
    private FirebaseDatabase mDatabase;
    private DatabaseReference databaseReference;
    private Users user;
    private Button twitter;
    private static final String EMAIL = "email";
    private CallbackManager mCallbackManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSign = findViewById(R.id.btnsign);
        facebook_button= (LoginButton) findViewById(R.id.facebook_button);
        facebook_button.setPermissions(Collections.singletonList(EMAIL));
        Button signInButton = findViewById(R.id.sign_in_byMail);
        twitter=findViewById(R.id.sign_twitter);
        mCallbackManager = CallbackManager.Factory.create();
        //googleSignIn();
        mAuth = FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance("https://go4lunch-5272f-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference= mDatabase.getReference("Users");
        googleSignIn();



    /*    ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
                new FirebaseAuthUIActivityResultContract(),
                result -> onSignInResult(result)
        );

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.TwitterBuilder().build());

// Create and launch sign-in intent

        facebook_button.setOnClickListener(v-> {
            Intent signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setIsSmartLockEnabled(false)
                    .build();
            signInLauncher.launch(signInIntent);

        });*/

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
                            try {
                                //Add the informations to Firebase.
                                //user=new Users(account.getId(),account.getFamilyName());
                                user=new Users();
                                user.setUid(account.getId());
                                user.setUsername(account.getGivenName());
                                user.setEmail(account.getEmail());
                                user.setPhotoUser(account.getPhotoUrl().toString());
                                databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                        .setValue(user);
                            }catch (Exception e){
                                Toast.makeText(this,"NOP",Toast.LENGTH_LONG);
                            }


                        } catch (ApiException e) {
                            // Google Sign In failed, update UI appropriately
                            Toast.makeText(MainActivity.this,"Authentification Failed"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
                });

// ...


            btnSign.setOnClickListener(v-> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            someActivityResultLauncher.launch(signInIntent);
        });
            //Peut etre pas le bon boutton
            facebook_button.setOnClickListener(v->{

                facebook_button.setPermissions("email", "public_profile");
                facebook_button.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {
                    }
                });
            });

            /*signInButton.setOnClickListener(view -> {
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                if(firebaseUser==null) {
                    Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
                    startActivity(intent);
                }
            });*/

            twitter.setOnClickListener(view -> {
                Intent intent= new Intent(MainActivity.this,TwitterAuthentification.class);
                startActivity(intent);
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
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser userFirebase= mAuth.getCurrentUser();
                        try {
                            //Add the informations to Firebase.
                            //user=new Users(account.getId(),account.getFamilyName());
                            user=new Users();
                            assert userFirebase != null;
                            user.setUid(userFirebase.getUid());
                            user.setUsername(userFirebase.getEmail());
                            user.setEmail(userFirebase.getEmail());
                            user.setPhotoUser(userFirebase.getPhotoUrl().toString());
                            databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                    .setValue(user);
                            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                            startActivity(intent);
                        }catch (Exception e){
                            Toast.makeText(this,"NOP",Toast.LENGTH_LONG);
                        }
                    } else {
                    }
                });
    }


   /* private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser userFirebase = FirebaseAuth.getInstance().getCurrentUser();
            try {
                //Add the informations to Firebase.
                //user=new Users(account.getId(),account.getFamilyName());
                user=new Users();
                user.setUid(userFirebase.getUid());
                user.setUsername(userFirebase.getEmail());
                user.setEmail(userFirebase.getEmail());
                user.setPhotoUser(userFirebase.getPhotoUrl().toString());
                databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                        .setValue(user);
            }catch (Exception e){
                Toast.makeText(this,"NOP",Toast.LENGTH_LONG);
            }

        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }*/



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